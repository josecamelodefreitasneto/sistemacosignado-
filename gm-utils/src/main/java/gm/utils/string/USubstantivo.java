package gm.utils.string;

public class USubstantivo {
	public static boolean masculino(String s) {
		s = UString.beforeFirst(UString.trimPlus(s) + " ", " ").toLowerCase();
		if (s.endsWith("o")) {
			return true;
		} else if (s.equals("uf")) {
			return false;
		} else {
			return false;
		}
	}
}
