package gm.utils.number;

import java.math.BigDecimal;

import gm.utils.comum.UConstantes;
import gm.utils.comum.UObject;
import gm.utils.exception.UException;

public class UBigDecimal {

	public static BigDecimal toMoney(Object o) {
		return toBigDecimal(o, 2);
	}

	public static BigDecimal toBigDecimal(Integer i) {
		return toBigDecimal(Double.valueOf(i));
	}

	public static BigDecimal toBigDecimal(String s) {
		return toBigDecimal(Double.valueOf(s));
	}

	public static BigDecimal toBigDecimal(Double x) {
		if (x == null) {
			return BigDecimal.ZERO;
		}
		return BigDecimal.valueOf(x);
	}
	
	public static void main(String[] args) {
		System.out.println(format(BigDecimal.ZERO, 4));
	}
	
	public static String format(BigDecimal o, int precision) {
		if (o == null) return null;
		o = toBigDecimal(o, precision);
		return o.toString();
	}

	@SuppressWarnings("rawtypes")
	public static BigDecimal toBigDecimal(Object o, int casas) {
		
		if (UObject.isEmpty(o)) {
			return BigDecimal.ZERO;
		}

		if (o instanceof Numeric) {
			Numeric n = (Numeric) o;
			return n.getValor();
		}

		String s = o.toString();

		try {
			s = s.replace(",", "");
			BigDecimal bd = new BigDecimal(s);
			bd = bd.setScale(casas, UNumber.ROUNDING_MODE);
			return bd;
		} catch (Exception e) {
			throw UException.runtime("N"+UConstantes.a_til+"o foi poss"+UConstantes.i_agudo+"vel converter '" + s + "' para BigDecimal");
		}
	}
	
	public static boolean eq(BigDecimal a, BigDecimal b) {
		return a == null ? b == null : a.equals(b); 
	}
	public static boolean ne(BigDecimal a, BigDecimal b) {
		return !eq(a,b);
	}
}
