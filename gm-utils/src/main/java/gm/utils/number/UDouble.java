package gm.utils.number;

import java.math.BigDecimal;
import java.math.BigInteger;

import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.string.UString;

public class UDouble {

	public static Double toDouble(final Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Double) {
			return (Double) o;
		}
		if (o instanceof BigInteger) {
			final BigInteger b = (BigInteger) o;
			return b.doubleValue();
		}
		if (o instanceof BigDecimal) {
			final BigDecimal b = (BigDecimal) o;
			return b.doubleValue();
		}
//		if (o instanceof Numeric2) {
//			Numeric2 b = (Numeric2) o;
//			return b.toDouble();
//		}
		if (o instanceof Integer) {
			final Integer b = (Integer) o;
			return b.doubleValue();
		}
		if (o instanceof Long) {
			final Long b = (Long) o;
			return b.doubleValue();
		}

		if (o instanceof String) {
			final String s = (String) o;
			return UDouble.toDouble(s);
		}
//		if (o instanceof MapSO) {
//			final MapSO map = (MapSO) o;
//			if (map.containsKey("valor") && map.containsKey("casas")) {
//				
//			}
//		}
		throw UException.runtime("N"+UConstantes.a_til+"o sei tratar: " + o.getClass());
	}

	public static Double toDouble(String s) {
		if (UString.isEmpty(s)) {
			return null;
		}
		s = s.trim();
		if (s.endsWith(",00.0")) {
			s = s.replace(",00.0", "");
		}
		if (s.endsWith(",00")) {
			s = s.replace(",00", "");
		}
		if (UString.isEmpty(s)) {
			return null;
		}
		if (s.contains(".")) {
			while (s.endsWith("0")) {
				s = s.substring(0, s.length() - 1);
			}
			if (s.endsWith(".")) {
				s += "0";
			}
		} else {
			if ( UInteger.isInt(s) ) {
				return UInteger.toInt(s, 0).doubleValue();
			}
			s += ".0";
		}
		final Double d = Double.parseDouble(s);
		final String string = d.toString();
		if (!string.equals(s)) {
			throw UException.runtime("deu dif!!! : " + d + " ; " + s);
		}
		return d;
	}
	
	public static String format(final Double d, final int casas) {

		if (d == 0) {
			return "";
		}
		
		String s = d.toString();
		final String afterFirst = UString.afterFirst(s, ".");
		s = s.replace(".", ",");
		
		if (afterFirst.length() == casas) {
			return s;
		}
		
		if (afterFirst.length() < casas) {
			s += UString.repete("0", casas - afterFirst.length());
			return s;
		}
		
		while (s.length() - s.indexOf(",") < casas + 1) {
			s += "0";
		}

		return s;

	}

	public static boolean isEmptyOrZero(final Double value) {
		return value == null || value == 0;
	}
	
}
