package gm.utils.string;

import gm.utils.comum.UConstantes;

public class CorretorOrtografico {

	private static final String cao = UConstantes.cedilha + UConstantes.a_til + "o";
	private static final String orio = UConstantes.o_agudo + "rio";
	private static final String ario = UConstantes.a_agudo + "rio";
	private static final String itona = UConstantes.i_agudo + "tona";

	public static String exec(String s) {
		s = UString.replacePalavra(s, "eh", UConstantes.e_agudo);
		s = UString.replacePalavra(s, "Eh", UConstantes.E_agudo);
		for (final String b : UConstantes.SIMBOLOS) {
			s = s.replace("cao"+b, cao + b);
			s = s.replace("orio"+b, orio + b);
			s = s.replace("ario"+b, ario + b);
			s = s.replace("itona"+b, itona + b);
		}
		return s;
	}
	
	public static void main(String[] args) {
		System.out.println(exec("isso eh demais"));
		System.out.println(exec("Eh isso eh demais"));
	}
	
}
