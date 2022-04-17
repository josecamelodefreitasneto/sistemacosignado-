package gm.utils.comum;

import gm.utils.string.UString;

public class UBoolean {
	
	public static Boolean toBoolean(Object o) {
		if (isTrue(o)) return true;
		if (isFalse(o)) return false;
		return null;
	}
	
	public static boolean isTrue(Object o) {
		if (o == null) return false;
		if (o instanceof Object[]) {
			Object[] os = (Object[]) o;
			if (os.length == 0) return false;
			o = os[0];
			if (o == null) return false;
		}
		if (o instanceof Boolean) return Boolean.TRUE.equals(o);
		String s = UString.trimPlus(UString.toString(o));
		if (UString.isEmpty(s)) return false;
		s = s.toLowerCase();
		if (s.equals("true")) return true;
		if (s.equals("verdadeiro")) return true;
		if (s.equals("sim")) return true;
		if (s.equals("yes")) return true;
		if (s.equals("y")) return true;
		if (s.equals("s")) return true;
		if (s.equals("v")) return true;
		if (s.equals("1")) return true;
		return false;
	}
	
	public static boolean isFalse(Object o) {
		if (o == null) return false;
		if (o instanceof Object[]) {
			Object[] os = (Object[]) o;
			if (os.length == 0) return false;
			o = os[0];
			if (o == null) return false;
		}
		if (o instanceof Boolean) return Boolean.FALSE.equals(o);
		String s = UString.trimPlus(UString.toString(o));
		if (UString.isEmpty(s)) return false;
		s = s.toLowerCase();
		if (s.equals("false")) return true;
		if (s.equals("falso")) return true;
		if (s.equals("nao")) return true;
		if (s.equals("n"+UConstantes.a_til+"o")) return true;
		if (s.equals("no")) return true;
		if (s.equals("n")) return true;
		if (s.equals("0")) return true;
		return false;
	}

	public static boolean eq(Boolean a, Boolean b) {
		return a == null ? b == null : a.equals(b);
	}
	public static boolean ne(Boolean a, Boolean b) {
		return !eq(a, b);
	}
	public static String format(Boolean b) {
		if (b == null) {
			return "-";
		} else {
			return b ? "Sim" : "nao";
		}
	}
	
}
