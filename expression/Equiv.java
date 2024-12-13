package expression;

import java.util.HashSet;
import java.util.Set;

public class Equiv extends Expression {

	private Expression e1, e2;
	
	public Equiv(Expression e1, Expression e2) {
			this.e1 = e1;
			this.e2 = e2;
	}
		
	public Expression simplifier(){
		e1 = e1.simplifier();
		e2 = e2.simplifier();
		if (e1.estVrai())
			return e2;
		if (e2.estVrai())
			return e1;
		if (e1.estFaux())
			return (new Non(e2)).simplifier();
		if (e2.estFaux())
			return (new Non(e1)).simplifier();
		return this;
	}

	public boolean evalue() throws RuntimeException {
		// Evaluate the equivalence as true if both sub-expressions evaluate to the same boolean value.
		return e1.evalue() == e2.evalue();
	}
	
	public Set<String> atomes() {
		// Combine the atoms (variables) from both sub-expressions into a single set.
		Set<String> s = new HashSet<>();
		s.addAll(e1.atomes());
		s.addAll(e2.atomes());
		return s;
	}
	
	public Expression remplace(String s, boolean b) {
		// Replace the specified variable in both sub-expressions and return a new equivalence expression.
		return new Equiv(e1.remplace(s, b), e2.remplace(s, b));
	}
	

}
