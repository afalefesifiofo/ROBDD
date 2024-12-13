package expression;

import java.util.HashSet;
import java.util.Set;

public class Implique extends Expression {

	private Expression e1, e2;

	public Implique(Expression e1, Expression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public Set<String> atomes() {
		Set<String> s = new HashSet<String>();
		s.addAll(e1.atomes());
		s.addAll(e2.atomes());
		return s;
	}

	public boolean evalue() throws RuntimeException {
		if (!e1.evalue())
			return true;
		else
			return e2.evalue();
	}

	public Expression remplace(String s, boolean b) {
		return new Implique(e1.remplace(s, b), e2.remplace(s, b));
	}

	public Expression simplifier(){
		e1 = e1.simplifier();
		e2 = e2.simplifier();
		if (e2.estFaux())
			return (new Non(e1)).simplifier();
		if (e1.estVrai())
			return e2;
		if (e2.estVrai() || e1.estFaux())
			return new Constante(true);
		return this;
	}

}
