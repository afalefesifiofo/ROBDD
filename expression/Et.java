package expression;

import java.util.HashSet;
import java.util.Set;

public class Et extends Expression {

	private Expression e1, e2;
	
	public Et(Expression e1, Expression e2) {
		this.e1 = e1;
		this.e2 = e2;
	}
	
	public boolean evalue() throws RuntimeException {
		return e1.evalue() && e2.evalue();
	}

	public Set<String> atomes() {
		Set<String> s = new HashSet<String>();
		s.addAll(e1.atomes());
		s.addAll(e2.atomes());
		return s;
	}

	public Expression remplace(String s, boolean b) {
		return new Et(e1.remplace(s, b),e2.remplace(s, b));
	}
	
	public Expression simplifier(){
		e1 = e1.simplifier();
		e2 = e2.simplifier();
		if (e1.estVrai())
			return e2;
		if (e2.estVrai())
			return e1;
		if (e1.estFaux() || e2.estFaux())
			return new Constante(false);
		return this;
	}
}
