package gm.utils.string;

import java.util.ArrayList;
import java.util.List;

public class UStringFiltrarCaracteres {
	
	private static boolean isEmpty(String s) {
		return s == null || s.trim().isEmpty();
	}
	public static String exec(String s, List<String> list) {

		String s2 = "";

		while (!isEmpty(s)) {
			String x = s.substring(0, 1);
			s = s.substring(1);
//			if (" ".equals(x)) {
//				System.out.println(x);
//			}
			if (list.contains(x)) {
				s2 += x;
			}
		}

		return s2;

	}
	public static String numeros(String s) {
		return exec(s, UStringConstants.NUMEROS);
	}
	public static String exec(String s, String... list) {
		List<String> l = new ArrayList<>();
		for (String o : list) {
			l.add(o);
		}
		return exec(s, l);
	}	
	
}
