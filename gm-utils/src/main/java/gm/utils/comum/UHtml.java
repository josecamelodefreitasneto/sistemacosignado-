package gm.utils.comum;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.number.UInteger;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class UHtml {

//	http://tunes.org/wiki/html_20special_20characters_20and_20symbols.html
	
	private static Map<String, String> map;
	
	public static Map<String, String> map(){
		
		if (map != null) {
			return map;
		}
		
		map = new HashMap<>();
		
		map.put(UConstantes.a_agudo,"&aacute;");
		map.put(UConstantes.A_agudo,"&Aacute;");
		map.put(UConstantes.a_til,"&atilde;");
		map.put(UConstantes.A_til,"&Atilde;");
		map.put(UConstantes.a_circunflexo,"&acirc;");
		map.put(UConstantes.A_circunflexo,"&Acirc;");
		map.put(UConstantes.a_crase,"&agrave;");
		map.put(UConstantes.A_crase,"&Agrave;");
		map.put(UConstantes.A_trema,"&Auml;");
		map.put(UConstantes.a_primeira,"&ordf;");
		map.put(UConstantes.o_primeiro,"&ordm;");
		map.put(UConstantes.e_agudo,"&eacute;");
		map.put(UConstantes.E_agudo,"&Eacute;");
		map.put(UConstantes.e_circunflexo,"&ecirc;");
		map.put(UConstantes.E_circunflexo,"&Ecirc;");
		map.put(UConstantes.i_agudo,"&iacute;");
		map.put(UConstantes.I_agudo,"&Iacute;");
		map.put(UConstantes.o_agudo,"&oacute;");
		map.put(UConstantes.O_agudo,"&Oacute;");
		map.put(UConstantes.o_til,"&otilde;");
		map.put(UConstantes.O_til,"&Otilde;");
		map.put(UConstantes.o_circunflexo,"&ocirc;");
		map.put(UConstantes.O_circunflexo,"&Ocirc;");
		map.put(UConstantes.u_agudo,"&uacute;");
		map.put(UConstantes.U_agudo,"&Uacute;");
		map.put(UConstantes.cedilha,"&ccedil;");
		map.put(UConstantes.CEDILHA,"&Ccedil;");
		map.put(UConstantes.maior_maior,"&raquo;");
		map.put(UConstantes.menor_menor,"&laquo;");
		map.put(UConstantes.u_trema,"&uuml;");
		map.put(UConstantes.x_fechar,"&times;");
		
		return map;
		
	}
	
	public static String removeHtml(String s){
		if (UString.isEmpty(s)) {
			return null;
		}
		s = s.replaceAll("\\<.*?>","");
		s = s.replace("&rdquo;", UConstantes.aspas_duplas);
		s = s.replace("&bull;", "-");
		s = s.replace("&minus;", "-");
		s = s.replace("&ndash;", "-");
		s = s.replace("&rsquo;", "'");
		s = s.replace("&sect;", UConstantes.double_s);
		s = s.replace("&lsquo;", UConstantes.aspa_simples);
		s = s.replace("c&#807;", UConstantes.cedilha);
		s = s.replace("a&#771;", UConstantes.a_til);
		s = s.replace("e&#769;", UConstantes.e_agudo);
		s = s.replace("&sup2;", "2");
		s = s.replace("&shy;", "");
		s = s.replace("&amp;", "&");
		s = s.replace("&nbsp;", " ");
		s = s.replace("&Egrave;", UConstantes.E_agudo);
		s = s.replace("&deg;", UConstantes.o_primeiro);
		s = s.replace("&ordm;", UConstantes.o_primeiro);
				
		for (String key : map().keySet()) {
			s = s.replace(map.get(key), key);	
			s = s.replace(map.get(key), key);	
			s = s.replace(map.get(key), key);	
		}
		
		if (UString.isEmpty(s)) {
			return null;
		}
		
		return s;
	}

	public static String replaceSpecialChars(String s){
		if (UString.isEmpty(s)) {
			return null;
		}
		for (String key : map().keySet()) {
			s = s.replace(key, map.get(key));	
		}
		return s;
	}
	
	public static void main(String[] args) {
		ListString list = ListString.clipboard();
		for (String key : map().keySet()) {
			list.replaceTexto(key, map.get(key));	
		}
		list.print();
		list.toClipboard();
	}	
	
	public static String decimalToRomano(int valor) {
		String[] romanos = new String[] { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		int[] div = new int[] { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		StringBuilder saida = new StringBuilder();
		StringBuilder termos;
		int res = 0;
		int i = 0;
		do {
			res = valor / div[i];
			termos = new StringBuilder();
			for (int j = 1; j <= res; j++) {
				termos.append(romanos[i]);
			}
			saida.append(termos);
			valor = valor % div[i];
			i++;
		} while (valor != 0);
		return saida.toString();
	}

	public static long romanoToDecimal(String romano) {
		String[] r = new String[] { "CM", "CD", "XC", "XL", "IX", "IV", "M", "D", "C", "L", "X", "V", "I" };
		int[] d = new int[] { 900, 400, 90, 40, 9, 4, 1000, 500, 100, 50, 10, 5, 1 };
		String valor = romano;
		for (int i = 0; i < r.length; i++) {
			if (valor.contains(r[i])) {
				valor = valor.replace(r[i], d[i] + "#");
			}
		}
		Object[] decimais = valor.split("#");
		long total = 0;
		for (int i = 0; i < decimais.length; i++) {
			if (decimais[i].toString() != null) {
				total += UInteger.toInt(decimais[i].toString(), 0);
			}
		}
		return total;
	}
	
	public static Boolean contains(String item, List<String> list) {
		return list.contains(item);
	}		

	public static String removeAtributos(String s) {
		s = removeAtributosTag("p", s);
		s = removeAtributosTag("span", s);
		return s;
	}
	
	public static String removeAtributosTag(String tag, String s){
		while (s.contains("<"+tag+" ")) {
			String before = UString.beforeFirst(s, "<"+tag+" ");
			String x = UString.afterFirst(s, "<"+tag+" ");
			x = UString.afterFirst(x, ">");
			s = before + "<"+tag+">" + x; 
		}
		return s;
	}
	
	public static ListString daPasta(String pasta, boolean subPastas){
		return daPasta(new File(pasta), subPastas);
	}
	
	public static ListString daPasta(File pasta, boolean subPastas){
		
		ListString list = new ListString();
		for (File file : pasta.listFiles()) {
			
			if (file.isDirectory()) {
				if (subPastas) {
					list.addAll( daPasta(file, true) );
				}
			} else {
				String s = file.toString();
				if (s.endsWith(".html")) {
					list.add(s);
				}
			}
			
		}
		return list;
		
	}
	
}
