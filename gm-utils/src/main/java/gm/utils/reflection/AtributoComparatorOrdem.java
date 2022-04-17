package gm.utils.reflection;

import java.util.Comparator;

import gm.utils.comum.UCompare;

public class AtributoComparatorOrdem implements Comparator<Atributo> {
	
	private AtributoComparatorOrdem() {}
	
	private static AtributoComparatorOrdem instance;
	
	public static AtributoComparatorOrdem getInstance() {
		if (instance == null) {
			instance = new AtributoComparatorOrdem();
		}
		return instance;
	}
	
	@Override
	public int compare(Atributo a, Atributo b) {
		Integer ordemA = a.getOrdem();
		Integer ordemB = b.getOrdem();
		return UCompare.compare(ordemA, ordemB);
	}
	
}
