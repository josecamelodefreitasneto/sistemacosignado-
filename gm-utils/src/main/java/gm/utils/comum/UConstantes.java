package gm.utils.comum;

import gm.utils.string.ListString;

public class UConstantes {
	
	public static final int REGISTROS_POR_PAGINA_DEFAULT = 30;

	public static final int _1MB = 1048576;//bytes
	public static final int _5MB = _1MB*5;
	public static final int _10MB = _5MB*2;
	public static final int _40MB = _10MB*4;
	
	public static final String a_primeira = "\u00aa";
	public static final String o_primeiro = "\u00ba";

	public static final String a_ordinal = a_primeira;
	public static final String o_ordinal = o_primeiro;
	
	public static final String a_agudo = "\u00e1";
	public static final String A_agudo = "\u00c1";
	
	public static final String a_crase = "\u00e0";
	public static final String A_crase = "\u00c0";
	
	public static final String a_circunflexo = "\u00e2";
	public static final String A_circunflexo = "\u00c2";
	
	public static final String a_til = "\u00e3";
	public static final String A_til = "\u00c3";
	
	public static final String a_trema = "\u00e4";
	public static final String A_trema = "\u00c4";
	
	public static final String cifrao = "\u0024";
	
	public static final String e_agudo = "\u00e9";
	public static final String E_agudo = "\u00c9";

	public static final String e_circunflexo = "\u00ea";
	public static final String E_circunflexo = "\u00ca";
	
	public static final String e_crase = "\u00e8";
	public static final String E_crase = "\u00c8";
	
	public static final String e_trema = "\u00eb";
	public static final String E_trema = "\u00cb";

	public static final String e_til = "\u1ebd";
	public static final String E_til = "\u1ebc";
	
	public static final String i_agudo = "\u00ed";
	public static final String I_agudo = "\u00cd";
	
	public static final String i_crase = "\u00ec";
	public static final String I_crase = "\u00cc";
	
	public static final String i_circunflexo = "\u00ee";
	public static final String I_circunflexo = "\u00ce";
	
	public static final String i_trema = "\u00ef";
	public static final String I_trema = "\u00cf";

	public static final String i_til = "\u0129";
	public static final String I_til = "\u0128";
	
	public static final String o_agudo = "\u00f3";
	public static final String O_agudo = "\u00d3";
	
	public static final String o_crase = "\u00f2";
	public static final String O_crase = "\u00d2";
	
	public static final String o_circunflexo = "\u00f4";
	public static final String O_circunflexo = "\u00d4";
	
	public static final String o_til = "\u00f5";
	public static final String O_til = "\u00d5";
	
	public static final String o_trema = "\u00f6";
	public static final String O_trema = "\u00d6";
	
	public static final String u_agudo = "\u00fa";
	public static final String U_agudo = "\u00da";
	
	public static final String u_crase = "\u00f9";
	public static final String U_crase = "\u00d9";

	public static final String u_til = "\u0169";
	public static final String U_til = "\u0168";
	
	public static final String u_circunflexo = "\u00fb";
	public static final String U_circunflexo = "\u00db";
	
	public static final String u_trema = "\u00fc";
	public static final String U_trema = "\u00dc";
	
	public static final String cedilha = "\u00e7";
	public static final String CEDILHA = "\u00c7";
	
	public static final String n_til = "\u00f1";
	public static final String N_til = "\u00d1";
	
	public static final String e_comercial = "\u0026";
	public static final String aspa_simples = "\u0027";
	public static final String aspas_duplas = "\"";
	public static final String double_s = "\u00a7";
	
	public static final String maior_maior = "\u00BB";	
	public static final String menor_menor = "\u00AB";
	
	public static final String x_fechar = "\u00D7";	
	
	public static final String cao = cedilha + a_til + "o";

	public static final ListString SIMBOLOS = ListString.newFromArray(
			" ",".",",",";",":","(",")","<",">","{", "}","[","]","!","@","#","$","%",
			"&","*","-","_","=","+","/","?","\\","|","\"","'","`","^","~","\n","\t");

	public static ListString numeros = ListString.newFromArray("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
	public static ListString letrasMaiusculas = ListString.newFromArray("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
	public static ListString letrasMinusculas = ListString.newFromArray("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
	public static ListString meses = ListString.newFromArray("Janeiro", "Fevereiro", "Mar"+cedilha+"o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
	public static ListString mesesAbreviados = ListString.newFromArray("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez");
	public static ListString abreviacoesSemana = ListString.array("Dom","Seg","Ter","Qua","Qui","Sex","S"+a_agudo+"b");
	
	public static final ListString acentuadasMinusculas = ListString.newFromArray(
			a_agudo, a_crase, a_til, a_circunflexo, a_trema,
			e_agudo, e_crase, e_til, e_circunflexo, e_trema,
			i_agudo, i_crase, i_til, i_circunflexo, i_trema,
			o_agudo, o_crase, o_til, o_circunflexo, o_trema,
			u_agudo, u_crase, u_til, u_circunflexo, u_trema
		);

		public static final ListString acentuadasMaiusculas = ListString.newFromArray(
			A_agudo, A_crase, A_til, A_circunflexo, A_trema,
			E_agudo, E_crase, E_til, E_circunflexo, E_trema,
			I_agudo, I_crase, I_til, I_circunflexo, I_trema,
			O_agudo, O_crase, O_til, O_circunflexo, O_trema,
			U_agudo, U_crase, U_til, U_circunflexo, U_trema
		);	
	
	public static final ListString vogais = ListString.array("a", "e", "i", "o", "u");
	public static final ListString VOGAIS = ListString.array("A", "E", "I", "O", "U");
	public static final ListString CONSOANTES = letrasMaiusculas.menos(VOGAIS);
	public static final ListString consoantes = letrasMinusculas.menos(vogais);
	
	public static final ListString especiais = ListString.array(
			a_agudo,a_circunflexo,a_crase,a_til,a_trema,aspa_simples,cedilha,e_circunflexo,
			e_comercial,e_crase,e_agudo,i_agudo,i_circunflexo,i_crase,i_trema,n_til,o_agudo,
			o_circunflexo,o_crase,o_til,o_trema,u_agudo,u_circunflexo,u_crase,u_trema
	);
	public static final ListString ESPECIAIS = ListString.array(
			A_agudo,A_circunflexo,A_crase,A_til,A_trema,CEDILHA,E_agudo,E_circunflexo,
			E_crase,E_trema,I_agudo,I_circunflexo,I_crase,I_trema,N_til,O_agudo,
			O_circunflexo,O_crase,O_til,O_trema,U_agudo,U_circunflexo,U_crase
	);
	public static final ListString PREPOSICOES = ListString.array(
			"a", "ante", "ap"+o_agudo+"s", "at"+e_agudo, "com", "contra",
			"de", "desde", "em", "entre", "para", "por", "perante", "sem", 
			"sob", "sobre", "traz", "do", "da", "dos", "das", 
			"dum", "duma", "duns", "dumas", "no", "na", "nos", "nas");
	public static final ListString ARTIGOS = ListString.newFromArray("a", "o", "as", "os", "um", "uma", "uns", "umas");
	public static final ListString ALFA = ListString.newListString(letrasMaiusculas, letrasMinusculas, numeros);

	
//	public static void main(String[] args) {
//		System.out.println(geraCodigoUnicode('0'));
//	}
//	
//	public static String geraCodigoUnicode(char letra) {
//	    String hexa = Integer.toHexString( (int)letra );
//
//	    String prefix;
//	    if( hexa.length() == 1 ) {
//	        prefix = "\\u000";
//	    } else if( hexa.length() == 2 ) {
//	        prefix = "\\u00";
//	    } else if( hexa.length() == 3 ) {
//	        prefix = "\\u0";
//	    } else {
//	        prefix = "\\u";
//	    }
//
//	    return prefix + hexa;
//	}	
	
}
