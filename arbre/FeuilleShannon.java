package arbre;

public class FeuilleShannon extends ArbreShannon {
	//valeur de la feuille
	private boolean v;
	
	public FeuilleShannon(boolean v) {
		this.v = v;
	}

	
	public String affiche(int prof){
		String s = "|";
		for(int k=0;k<(prof-1);k++)
			s += "   |";
		s += "->";
		s += (v? "1": "0") + "\n";
		return s;
	}
}
