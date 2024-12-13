package expression;

import java.util.HashSet;
import java.util.Set;

public class Atome extends Expression {

	private String name;

	public Atome(String s) {
		this.name = new String(s);
	}

	public boolean evalue() throws RuntimeException {
		throw new RuntimeException("L'expression ne peut pas être évaluée car elle contient (au moins) l'atome "+name);
	}

	public Set<String> atomes() {
		Set<String> s = new HashSet<String>();
		s.add(name);
		return s;
	}
	public Expression remplace(String s, boolean b) {
        if (this.name.equals(s)) {
            return new Constante(b);  // Replace the atom with the corresponding constant (true or false)
        } else {
            return this;  // If it's not the atom we're replacing, return the expression unchanged
        }
    }
	

	public Expression simplifier(){
		return this;
	}

}
