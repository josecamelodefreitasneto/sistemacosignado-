package gm.utils.comum;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import gm.utils.exception.UException;
import gm.utils.number.UNumber;
import gm.utils.string.UString;

public class Criptografar {

	public static String exec(String s) {
		int length = s.length();
		for (int i = 0; i < length; i++) {
			s = "@" + s + "#";
			s = md5(s);
			s = s.substring(3);
			s = UString.reverse(s);
			s = "$" + s + "&";
			s = md5(s);
			s = s.substring(5, 25);
			s = UString.reverse(s);
			s = s.substring(3);
			s = "*" + s + "!";
			s = md5(s);
			s = "A" + s + "o";
			s = s.substring(1);
			s = md5(s);
			s = s.substring(7);
			s = UConstantes.e_agudo + s + UConstantes.a_til;
			s = md5(s);
		}
		return s;
	}
	public static String md5(Object o) {

		try {
			if (o == null) {
				throw new NullPointerException();
			}
			String input = UString.toString(o);
			if (input == null) {
				throw new NullPointerException();
			}
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes(), 0, input.length());
			String s = new BigInteger(1, digest.digest()).toString(16);
			s = UNumber.format00(s, 32);
			return s;
		} catch (NoSuchAlgorithmException e) {
			throw UException.runtime(e);
		}
	}
	
}
