package arbre;

public abstract class ArbreShannon {

	//affiche (sous format texte "basique") l'arbre représenté par l'objet courant
	public String toString(){
		return affiche(0);
	}
	
	//sous-fonction récursive appelée par la précédente
	protected abstract String affiche(int prof);
}