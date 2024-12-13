package expression;

import java.util.Set;

public class Non extends Expression {

	private Expression e;
	
	public Non(Expression e) {
		this.e = e;
	}

	public boolean evalue() throws RuntimeException {
		return !e.evalue();
	}

	public Set<String> atomes() {
		return e.atomes();
	}

	public Expression remplace(String s, boolean b) {
		return new Non(e.remplace(s, b));
	}

	public Expression simplifier(){
		e = e.simplifier();
		if (e.estVrai())
			return new Constante(false);
		if (e.estFaux())
			return new Constante(true);
		return this;
	}
	
}
