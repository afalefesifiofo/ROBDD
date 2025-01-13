package robdd;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//Représente un ROBDD sous forme de liste de Noeud_ROBDD
public class ROBDD {

	//Constantes pour les numéros des "feuilles" VRAI et FAUX
	public static final int idFalse = 0;
	public static final int idTrue = 1;

	//liste représentant le ROBDD
	private List<Noeud_ROBDD> R;
	
	//construit un ROBDD vide
	public ROBDD(){
		R = new LinkedList<Noeud_ROBDD>();
	}
	//ajoute le Noeud_ROBDD n au ROBDD courant
	public void ajouter(Noeud_ROBDD n) {
		R.add(n);
	}

		
	//renvoie le nombre de noeuds du ROBDD
	public int nb_noeuds() {
		return R.size()+2; // longueur de la liste R + les 2 noeuds correspondants à VRAI et FAUX
	}

	@Override
	public String toString() {
		return R.toString();
	}
	
	// renvoie l'index, dans la liste R,  du noeud BDD associé à la variable nom et dont les fils droit et gauche sont d'indices respectifs fd et fg.
	// Si ce noeud n'existe pas dans le diagramme, la fonction renvoie -1.
	public int obtenirROBDDIndex(String nom, int fg, int fd) {
		for (Noeud_ROBDD n : R) {
			//TODO 
		}
		return -1;
	}

	public Noeud_ROBDD trouvernoeud(int idv){
		for (Noeud_ROBDD noeudRobdd:R){
			if (noeudRobdd.getIdFilsGauche()==idv || noeudRobdd.getIdFilsDroit()==idv){
				return noeudRobdd;
			}
		}
		throw new RuntimeException("Noeud non trouver avec id:"+idv);


	}

	
	public String trouve_sat() {
		// Identifiant initial pour commencer la recherche de l'assignation satisfaisante
		int idv = 1;
		// StringBuilder pour construire l'assignation satisfaisante
		StringBuilder sat = new StringBuilder();
		// Tant que l'identifiant actuel n'est pas le dernier nœud dans le ROBDD
		while (idv != this.nb_noeuds() - 1) {
			// Trouver le nœud correspondant à l'identifiant actuel
			Noeud_ROBDD noeudRobdd = trouvernoeud(idv);
			// Si le fils gauche du nœud est l'identifiant actuel, ajouter la négation de la variable au résultat
			if (noeudRobdd.getIdFilsGauche() == idv) {
				sat.append("¬").append(noeudRobdd.getNom()).append(" ");
			} else {
				// Sinon, ajouter la variable au résultat
				sat.append(noeudRobdd.getNom()).append(" ");
			}
			// Mettre à jour l'identifiant actuel avec l'identifiant du nœud actuel
			idv = noeudRobdd.getId();
			// Si l'identifiant actuel atteint la constante False (0), alors le ROBDD est non satisfiable
			if (idv == 0) {
				return "Le ROBDD est non sat";
			}
		}
		// Retourner l'assignation satisfaisante sous forme de chaîne
		return sat.toString();
	}
	
}
