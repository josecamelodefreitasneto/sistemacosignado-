package gm.utils.comum;

import java.util.Comparator;

import gm.utils.abstrato.IdObject;
import gm.utils.abstrato.IdTextObject;
import gm.utils.date.Data;
import gm.utils.number.UInteger;
import gm.utils.reflection.Atributo;
import gm.utils.string.UString;

public class UCompare {
	
	private static class Instance implements Comparator<Object>{
		@Override
		public int compare(Object a, Object b) {
			return UCompare.compare(a, b);
		}
	}
	
	public static Instance instance = new Instance();

	public static boolean basicCompare0(Object a, Object b) {
		Integer x = basicCompare(a, b);
		return x != null && x == 0;
	}
	
	public static Integer basicCompare(Object a, Object b) {
		if (a == b) {
			return 0;
		}
		if (a == null) {
			if (b == null) {
				return 0;
			} else {
				return -1;
			}
		} else if (b == null) {
			return 1;
		}
		return null;
	}
	
	public static int compare(Object a, Object b, Atributo atributo) {
		Integer i = basicCompare(a, b);
		if (i != null) return i;
		a = atributo.get(a);
		b = atributo.get(b);
		return compare(a, b);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int compare(Object a, Object b) {
		
		Integer i = basicCompare(a, b);
		if (i != null) return i;
		
		if ((a instanceof Comparable) && (b instanceof Comparable)) {
			Comparable va = (Comparable) a;
			Comparable vb = (Comparable) b;
			return va.compareTo(vb);
		}
		
		if (a instanceof IdTextObject) {
			IdTextObject va = (IdTextObject) a;
			IdTextObject vb = (IdTextObject) a;
			String sa = va.getText();
			String sb = vb.getText();
			return UString.compare(sa, sb);
		}

		if (a instanceof IdObject) {
			IdObject va = (IdObject) a;
			IdObject vb = (IdObject) a;
			return UInteger.compare(va.getId(), vb.getId());
		}
		
		if (UType.isData(a) || UType.isData(b)) {
			return Data.to(a).compare(Data.to(b));
		}
		
		String sa = UString.toString(a);
		String sb = UString.toString(b);
		return UString.compare(sa, sb);
		
	}
	
	public static boolean equals(Object a, Object b) {
		return UCompare.compare(a, b) == 0;
	}
	public static boolean eq(Object a, Object b) {
		return equals(a,b);
	}
	public static boolean ne(Object a, Object b) {
		return !equals(a,b);
	}
	
}
