package gm.utils.comum;

import gm.utils.number.UNumber;
import gm.utils.string.UString;

public class UCep {
	
	public static String formatParcial(String s) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = UString.mantemSomenteNumeros(s);

		if (UString.isEmpty(s)) {
			return "";
		}

		if (UString.length(s) < 3) {
			return s;
		} else if (UString.length(s) > 8) {
			s = s.substring(0, 8);
		}

		String x = s.substring(0, 2) + ".";
		s = s.substring(2);

		if (UString.length(s) > 3) {
			x += s.substring(0, 3) + "-";
			s = s.substring(3);
		}

		return x + s;

	}

	public static void main(String[] args) {
		//System.out.println(s);(formatParcial(""));
		//System.out.println(s);(formatParcial("7"));
		//System.out.println(s);(formatParcial("72"));
		//System.out.println(s);(formatParcial("723"));
		//System.out.println(s);(formatParcial("7231"));
		//System.out.println(s);(formatParcial("72318"));
		//System.out.println(s);(formatParcial("723180"));
		//System.out.println(s);(formatParcial("7231802"));
		//System.out.println(s);(formatParcial("72318024"));
		System.out.println(formatParcial("723180240"));
	}

	public static boolean isValid(String s) {
		s = UString.mantemSomenteNumeros(s);
		if (!UString.lengthIs(s, 8)) {
			return false;
		}
		return true;
	}

	public static String getBairro(String s) {
		return "Um Bairro Mock para o cep " + s;
	}

	public static String mock(int i) {
		return "72.318-"+UNumber.format00(i, 3);
	}
	
}
