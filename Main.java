import java.util.LinkedList;
import java.util.List;

import robdd.ROBDD;
import expression.*;

public class Main {

	public static void main(String[] args) {
		//EXEMPLE 
		Expression exp = new Et(new Atome("x"),new Atome("y")); // représente (x ^ y)
		System.out.println(exp.atomes()); // affiche la liste des atomes (=variables booléennes) présents dans exp
		exp = exp.remplace("x",true); // exp vaut maintenant (true ^ y)
		//System.out.println(exp.evalue()); // <- erreur car (true ^ y) ne peut pas être évalué
		exp = exp.remplace("y",false); // exp vaut maintenant (true ^ false)
		System.out.println(exp.evalue());

		Expression exp2 = new Et(
            new Equiv(new Atome("x1"), new Atome("y1")),
            new Equiv(new Atome("x2"), new Atome("y2"))
        );

        // Print the set of atoms in the expression
        System.out.println("Atoms: " + exp2.atomes());

        // Replace the atoms with boolean values and evaluate the expression
        exp = exp.remplace("x1", true); // Replace x1 with true
        exp = exp.remplace("y1", false); // Replace y1 with false
        exp = exp.remplace("x2", true); // Replace x2 with true
        exp = exp.remplace("y2", true); // Replace y2 with true

        // Print the evaluation result of the expression
        System.out.println("Evaluation: " + exp.evalue());

		// Tree for order y1, x1, x2, y2
        List<String> ordine1 = new LinkedList<String>();
        ordine1.add("x1");
        ordine1.add("y1");
        ordine1.add("x2");
        ordine1.add("y2");
        System.out.println("\nTree for order  y1, x1, x2, y2:");
        System.out.println(exp2.arbre(ordine1));

		List<String> ordine2 = new LinkedList<String>();
        ordine2.add("x1");
        ordine2.add("x2");
        ordine2.add("y1");
        ordine2.add("y2");
		System.out.println("\nTree for order2");
        System.out.println(exp2.arbre(ordine2));
		/* 
		//Affichage de l'arbre associé à l'expression exp pour l'ordre x > y 
		List<String> ordre_atomes = new LinkedList<String>();
		ordre_atomes.add("x");
		ordre_atomes.add("y");
		System.out.println("\n Arbre de exp : \n" + exp.arbre(ordre_atomes)); // <- que se passe-t-il ? 
		Expression exp2 = new Et(new Atome("x"),new Atome("y")); // représente (x ^ y)
		System.out.println("\n Arbre de exp2 : \n" + exp2.arbre(ordre_atomes));		
	*/
	}
}
