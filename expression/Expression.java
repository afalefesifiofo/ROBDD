package expression;

import arbre.*;
import robdd.*;

import java.util.LinkedList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;



public abstract class Expression {

	//renvoie la liste (non ordonnées) des atomes associées à l'objet courant
	public abstract Set<String> atomes();

	//renvoie la valeur (booléenne) de l'objet courant.
	//Si celui-ci ne peut pas être évalué (présence d'atomes), on lève une exception.
	public abstract boolean evalue() throws RuntimeException;

	//remplace l'atome s de l'objet courant par le booléen b
	//renvoie l'expression correspondante
	public abstract Expression remplace(String s, boolean b);

	//renvoie une version simplifiée de l'expression this
	//selon les règles du tableau du TD 5, partie 2.3
	public abstract Expression simplifier();

	public boolean estVrai() {
		try {
			return this.evalue();
		} catch (RuntimeException e) {
			return false;
		}
	}
	
	public boolean estFaux() {
		try {
			return !this.evalue();
		} catch (RuntimeException e) {
			return false;
		}
	}
	

	//construit l'arbre de shannon correspondant à l'expression courante en prenant comme ordre l'ordre indiqué par l'argument ordre_atomes
	public ArbreShannon arbre(List<String> ordre_atomes) {
		if (this.atomes().isEmpty()) {
			return new FeuilleShannon(evalue());
		} else {
			assert !ordre_atomes.isEmpty() : "Toutes les variables n'apparaissaient pas dans ordre_atomes !";
			List<String> ordre_atomes2 = new LinkedList<String>(ordre_atomes); //copie pour que arbre(ordre) ne modifie pas ordre
			String name = ordre_atomes2.remove(0);
			Expression e1 = remplace(name, false);
			Expression e2 = remplace(name, true);
			return new NoeudShannon(name, e1.arbre(ordre_atomes2), e2.arbre(ordre_atomes2));
		}
	}

	//renvoie le ROBDD correspondant à l'expression courante en prenant comme ordre l'ordre indiqué par l'argument ordre_atomes 
	public ROBDD robdd(List<String> atomes_ordonnes) {
		// On commence par vérifier que la liste atomes_ordonnes 
		// contient bien tous les atomes de l'expression courante.
		if (!this.atomes().equals(new HashSet<String>(atomes_ordonnes))){
			System.err.println("robdd: atomes_ordonnes ne contient pas tous les atomes de l'expression, ou en contient en trop.");
			System.exit(0);
		}
		// On crée un ROBDD vide
		ROBDD R = new ROBDD();
		// On construit le ROBDD
		this.construireROBDD(R,atomes_ordonnes);
		return R;
	}
	
	//fonction récursive qui renvoie le noeud ROBDD associé à l'expression courante (this) et construit le ROBDD G
	//La liste atomes_ordonnes contient la liste des atomes présents dans l'expression courante et indique leur ordre (pour pouvoir faire l'équivalent de la fonction max_variable du TD) 
	private int construireROBDD(ROBDD G, List<String> atomes_ordonnes) {
		// Semplifica l'espressione corrente
		Expression F = this.simplifier();
	
		// Caso base: l'espressione è costante (VRAI ou FAUX)
		if (F.estVrai()) {
			return ROBDD.idTrue; // Restituisci l'ID per VRAI
		}
		if (F.estFaux()) {
			return ROBDD.idFalse; // Restituisci l'ID per FAUX
		}
	
		// Determina l'atomo più prioritario nell'ordine
		String atomeMax = atomes_ordonnes.get(0);
		List<String> sousListeAtomes = atomes_ordonnes.subList(1, atomes_ordonnes.size());
	
		// Crea le espressioni figlie con l'atomo assegnato a false e true
		Expression Fg = F.remplace(atomeMax, false).simplifier();
		Expression Fd = F.remplace(atomeMax, true).simplifier();
	
		// Costruisce ricorsivamente i figli del nodo corrente
		int fg = Fg.construireROBDD(G, sousListeAtomes);
		int fd = Fd.construireROBDD(G, sousListeAtomes);
	
		// Se i figli sono uguali, non aggiungiamo un nuovo nodo
		if (fg == fd) {
			return fg;
		}
	
		// Verifica se il nodo già esiste nel ROBDD
		int index = G.obtenirROBDDIndex(atomeMax, fg, fd);
		if (index != -1) {
			return index; // Nodo già presente
		}
	
		// Aggiunge un nuovo nodo e restituisce il suo indice
		Noeud_ROBDD nouveauNoeud = new Noeud_ROBDD(atomeMax, fg, fd);
		G.ajouter(nouveauNoeud);
		return G.nb_noeuds() - 1; // Indice del nodo appena aggiunto
	}
	
	
	//renvoie le ROBDD correspondant à l'expression courante avec un ordre aléatoire (donné par this.atomes())
	public ROBDD robdd() {
		return robdd(new LinkedList<String>(this.atomes()));
	}

}
