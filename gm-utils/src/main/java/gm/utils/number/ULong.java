package gm.utils.number;

import java.math.BigInteger;

import gm.utils.string.UString;

public class ULong {

	public static Long toLong(Object o) {
		if (o == null) {
			return null;
		}
		Class<?> classe = o.getClass();
		if (classe.equals(Integer.class)) {
			Integer i = (Integer) o;
			return i.longValue();
		}
		if (classe.equals(BigInteger.class)) {
			BigInteger i = (BigInteger) o;
			return i.longValue();
		}
		String s = o.toString();
		if (UString.isEmpty(s)) {
			return null;
		}
		return Long.parseLong(s);
	}

	public static boolean isLong(Object o) {
		if (o == null) {
			return false;
		} else if (o instanceof Long) {
			return true;
		} else if (o instanceof Integer) {
			return true;
		} else if (o instanceof BigInteger) {
			return true;
		} else if (o instanceof String) {
			try {
				Long.parseLong(o.toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
	
}
