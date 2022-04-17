package gm.utils.number;

import java.math.BigDecimal;
import java.math.BigInteger;

import gm.utils.abstrato.IdObject;
import gm.utils.comum.UCompare;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UObject;
import gm.utils.exception.UException;
import gm.utils.string.UString;

public class UInteger {

	public static boolean equals(Integer a, Integer b) {
		if (a == null) return b == null;
		if (b == null) return false;
		if (a.equals(b)) return true;
		final int va = a+0;
		final int vb = b+0;
		return va == vb;
	}
	public static boolean ne(Integer a, Integer b) {
		return !UInteger.equals(a, b);
	}
	public static boolean in(Integer a, Integer... itens) {
		for (Integer b : itens) {
			if (equals(a, b)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInt(Object o) {
		if (UObject.isEmpty(o)) return false;
		if (o instanceof Integer) {
			return true;
		}
		String s = UString.toString(o);
		if (UString.isEmpty(s)) return false;
		s = s.trim();
		if (s.equals("0")) return true;
		if (s.replace("0", "").isEmpty()) return true;
		return UInteger.toInt(o, 0) != 0;
	}

	public static Integer toInt(String s, Integer def) {
		if (s == null) return def;
		if (UString.isEmpty(s)) return def;
		try {
			return Integer.parseInt(s.trim());
		} catch (Exception e) {
			return def;
		}
	}
	
	public static Integer toInt(Object o) {
		if (o == null) return null;
		if (UInteger.isInt(o)) {
			return UInteger.toInt(o, 0);
		} else if ( o instanceof IdObject ) {
			final IdObject io = (IdObject) o;
			return io.getId();
		} else if (o instanceof String && UString.isEmpty((String) o)) {
			return null;
		} else {
			throw UException.runtime("N"+UConstantes.a_til+"o "+UConstantes.e_agudo+" um inteiro: " + o);
		}
	}
	
	public static Integer toInt(Object o, Integer def) {
		if (o == null) {
			return def;
		}
		if (o instanceof Object[]) {
			final Object[] os = (Object[]) o;
			if (os.length == 0) {
				return def;
			}
			o = os[0];
			if (o == null) {
				return def;
			}
		}
		if (o instanceof BigInteger) {
			final BigInteger x = (BigInteger) o;
			return x.intValue();
		}
		if (o instanceof Long) {
			final Long x = (Long) o;
			return x.intValue();
		}
		if (o instanceof Integer) {
			return (Integer) o;
		}
		if (o instanceof Double) {
			Double d = (Double) o;
			int i = d.intValue();
			d = d - i;
			if (d > 0.5) {
				i++;
			}
			return i;
		}
		if (o instanceof BigDecimal) {
			final BigDecimal x = (BigDecimal) o;
			return x.intValue();
		}
		if (o instanceof Byte) {
			final Byte b = (Byte) o;
			return b.intValue();
		}

		final String s = UString.toString(o).trim();

		if ( UString.mantemSomenteNumeros(s).isEmpty() ) {
			return def;
		}
		
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {}

//		try {
//			return UDouble.toDouble(s).intValue();
//		} catch (Exception e) {}
		
		return def;

	}

	public static int maior(int a, int b) {
		return a > b ? a : b;
	}
	
	public static int menor(int a, int b) {
		return a < b ? a : b;
	}

	public static boolean ehPar(int i) {
		final Integer x = i / 2;
		final Double a = x.doubleValue();
		final Double b = i / 2.; 
		return UCompare.equals(a, b);
	}

	public static boolean isLongInt(String s) {
		if (UString.isEmpty(s)) {
			return false;
		}
		s = s.trim();
		while (!s.isEmpty()) {
			if (UInteger.isInt(s))
				return true;
			final String x = s.substring(0, 1);
			if (!UInteger.isInt(x)) {
				return false;
			}
			s = s.substring(1);
		}
		return false;
	}

	public static Integer compare(Integer a, Integer b) {
		if (a == b) {
			return 0;
		} else if (a == null) {
			return -1;
		} else if (b == null) {
			return 1;
		} else if (a < b) {
			return -1;
		} else if (b < a) {
			return 1;
		} else {
			return 0;
		}
	}
	public static String format00(Integer i, Integer casas) {
		return UNumber.format00(i, casas);
	}
	public static boolean isEmptyOrZero(Integer value) {
		return value == null || value == 0;
	}
	
}
