package gm.utils.number;

import java.math.BigInteger;

import gm.utils.string.UString;

public class UBigInteger {

	public static BigInteger toBigInteger(Object o) {
		if (o == null) {
			return null;
		}
		String s = o.toString();
		if (UString.isEmpty(s)) {
			return null;
		}
		if (UString.contemSomenteNumeros(s)) {
			return new BigInteger(s);
		} else {
			throw new RuntimeException("Nao foi possivel converter para BigInteger: " + s);
		}
	}

}
