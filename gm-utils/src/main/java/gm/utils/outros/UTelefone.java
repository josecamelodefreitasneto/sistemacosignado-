package gm.utils.outros;

import gm.utils.comum.Aleatorio;
import gm.utils.number.UInteger;
import gm.utils.string.UString;

public class UTelefone {

	public static final int MAX_LENGTH = 20;

	public static String formatParcial(String s) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = UString.maxLength(UString.mantemSomenteNumeros(s), MAX_LENGTH);

		if (UString.isEmpty(s)) {
			return "";
		}

		if (UString.length(s) < 2) {
			return "(" + s;
		}

		String x = "(" + s.substring(0, 2);
		s = s.substring(2);

		if (UString.isEmpty(s)) {
			return x;
		}

		x += ") ";

		s = x + formatNumero(s);
		s = UString.maxLength(s, MAX_LENGTH);

		return s;

	}

	private static String formatNumero(String s) {
		s = UString.mantemSomenteNumeros(s);
		if (UString.isEmpty(s)) {
			return "";
		}
		String x = "";
		if (UString.length(s) > 4) {
			while (UString.length(s) > 4) {
				x = "-" + UString.right(s, 4) + x;
				s = UString.ignoreRight(s, 4);
			}
		}
		s = s + x;
		return s;
	}

	public static boolean isValid(String s) {
		s = formatParcial(s);
		if (UString.length(s) < 14) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(format(55,61,"992559810"));
		System.out.println(format(null,61,"992559810"));
		System.out.println(format(null,null,"992559810858"));
		System.out.println(format(null,null,"9810858"));
		System.out.println(format(55,null,"992559810"));
	}

	public static String format(Integer ddi, Integer ddd, String numero) {
		String s = "";
		if (ddi != null) {
			s = "+" + ddi + " ";
		}
		return s + format(ddd, numero);
	}

	public static String format(Integer ddd, String numero) {
		String s = "";
		if (ddd != null) {
			s += "(" + ddd + ") ";
		}
		s += formatNumero(numero);
		return s.trim();
	}
	
	public static String aleatorio() {
		String s = "" + Aleatorio.get(1, 9);
		for (int i = 0; i < 10; i++) {
			s += Aleatorio.get(1, 9);
		}
		return formatParcial(s);
	}

	public static String mock(int i) {
		String s = UInteger.ehPar(i) ? "619" : "218";
		s += i;
		while (s.length() < 11) {
			s += (10-s.length());
		}
		return formatParcial(s);
	}
	
}
