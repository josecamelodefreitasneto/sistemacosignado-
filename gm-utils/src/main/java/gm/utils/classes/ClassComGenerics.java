package gm.utils.classes;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClassComGenerics {
	private Class<?> classe;
	private List<ClassComGenerics> generics;
	
	@Override
	public String toString() {
		
		String nome = classe == null ? "?" : classe.getSimpleName();
		
		if (generics == null) {
			return nome;
		}
		String s = "";
		for (ClassComGenerics o : generics) {
			s += ", " + o.toString();
		}
		return nome + "<" + s.substring(2) + ">";
	}
	
}
