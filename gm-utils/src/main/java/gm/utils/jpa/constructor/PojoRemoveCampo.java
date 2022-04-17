package gm.utils.jpa.constructor;

import java.util.function.Predicate;

public class PojoRemoveCampo implements Predicate<PojoCampo>{
	private String nome;
	public PojoRemoveCampo(String nome){
		this.nome = nome;
	}
	@Override
	public boolean test(PojoCampo t) {
		return t.getNome().equals(nome);
	}
}
