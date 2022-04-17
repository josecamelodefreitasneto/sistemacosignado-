package gm.utils.booleans;

import gm.utils.string.ListString;
import gm.utils.string.UString;

public class ExpressaoBooleana {
	
	private String value;
	private ListString itens = new ListString();
	
	private int resolve(String s) {
		
		while (s.contains("(")) {
			String conteudo = UString.beforeFirst(UString.afterLast(s, "("), ")");
			s = s.replace("("+conteudo+")", "#" + resolve(conteudo));
		}

		ListString palavras = ListString.separaPalavras(s);
		
		while (palavras.size() > 1) {
			String left = palavras.remove(0);
			String sinal = palavras.remove(0);
			String right = palavras.remove(0);
			String x = left + " " + sinal + " " + right;
			
		}
		
		return 0;
		
	}
	
	
	public static void main(String[] args) {
		
		
		
		
	}
	
//	a == (b > c)
	
}
