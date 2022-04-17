package gm.utils.string;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.List;
import java.util.stream.Collectors;

import gm.utils.comum.UAssert;
import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import gm.utils.number.Numeric;
import gm.utils.number.UInteger;

public class UString {
	
	public static boolean notEmpty(final String s) {
		return !UString.isEmpty(s);
	}
	public static boolean isEmpty(final String s) {
		return s == null || s.trim().isEmpty();
	}
	public static String toString(final Object o, final String def) {
		final String s = toString(o);
		return s == null ? def : s;
	}
	@SuppressWarnings("rawtypes")
	public static String toString(final Object o) {
		if (o == null) return null;
		if (o instanceof BigDecimal) {
			BigDecimal b = (BigDecimal) o;
			return new Numeric(b, b.precision()).toStringPonto();
		}
		final String s = o.toString();
		if (UString.isEmpty(s)) return null;
		return s;
	}
	public static String afterLast(final String s, final String substring) {
		final int i = s.lastIndexOf(substring);
		if (i == -1) return null;
		return s.substring(i + substring.length());
	}
	public static String beforeLast(final String s, final String substring) {
		final int i = s.lastIndexOf(substring);
		if (i == -1) return null;
		return s.substring(0, i);
	}
	public static String beforeLastMaiuscula(String s) {
		s = UString.reverse(s);
		while (!s.isEmpty()) {
			final String x = s.substring(0, 1);
			s = s.substring(1);
			if (UString.isEmpty(s)) {
				return null;
			}
			if (UConstantes.letrasMaiusculas.contains(x)) {
				return UString.reverse(s);		
			}
		}
		return null;
	}	
	public static String beforeFirst(final String s, final String substring) {
		final int i = s.indexOf(substring);
		if (i == -1) return null;
		return s.substring(0, i);
	}
	public static String afterFirstObrig(final String s, final String substring) {
		final String afterFirst = UString.afterFirst(s, substring);
		if (UString.isEmpty(afterFirst)) {
			throw UException.runtime("afterFirst('" + s + "', '" + substring + ") == null");
		}
		return afterFirst;
	}
	public static String afterFirst(final String s, final String substring) {
		int i = s.indexOf(substring);
		if (i == -1) return null;
		i += substring.length();
		return s.substring(i);
	}
	public static boolean equals(final String a, final String b) {
		if (a == b) return true;
		if (UString.isEmpty(a)) return UString.isEmpty(b);
		if (UString.isEmpty(b)) return false;
		return a.equals(b);
	}
	public static boolean ne(final String a, final String b) {
		return !UString.equals(a, b);
	}
	public static boolean equivalente(String a, String b) {
		if (UString.equals(a, b)) return true;
		if (UString.isEmpty(a)) return false;
		if (UString.isEmpty(b)) return false;
		a = UString.removerAcentos(a).toLowerCase().trim();
		b = UString.removerAcentos(b).toLowerCase().trim();
		return UString.equals(a, b);
	}
	public static String removerAcentos(final String s) {
		try {
			return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		} catch (final Exception e) {
			throw UException.runtime(e);
		}
	}
	public static String primeiraMaiuscula(final String s) {
//		if (s.toLowerCase().equals("uf")) return "UF";
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	public static String tratarParaSave(final String s) {
		if (UString.isEmpty(s)) {
			return null;
		}
		return s.trim();
	}
	public static String trimPlus(String s) {
		if (s == null)
			return null;
		s = s.trim();
		if (s.isEmpty())
			return s;
		s = s.replace("\t", " ");
		s = UString.replaceWhile(s, "  ", " ");
		return s;
	}
	public static String replaceWhile(String s, final String a, final String b) {
		while (s.contains(a)) {
			s = s.replace(a, b);
		}
		return s;
	}
	
	public static void clipboard(final String s) {
		try {
			final StringSelection selection = new StringSelection(s);
			final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
		} catch (final Exception e) {
			UException.printTrace(e);
		}
	}
	
	public static String clipboard() {

		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final Transferable contents = clipboard.getContents(null);

		if (contents == null)
			return null;
		if (!contents.isDataFlavorSupported(DataFlavor.stringFlavor))
			return null;

		try {
			return (String) contents.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException ex) {
			UException.printTrace(ex);
			return null;
		}

	}
	
	public static int compare(final String a, final String b) {
		if (UString.isEmpty(a)) {
			return UString.isEmpty(b) ? 0 : -1;
		} else if (UString.isEmpty(b)) {
			return 1;
		} else {
			return a.compareTo(b);
		}
	}
	
	public static boolean containsIgnoreCase(final String s, final String substring) {
		if (UString.isEmpty(s)) return false;
		if (UString.isEmpty(substring)) return false;
		return s.toLowerCase().contains(substring.toLowerCase());
	}
	
	public static String mantemSomenteNumeros(final String s) {
		return UString.mantemSomenteOsSeguintesCaracteres(s, UConstantes.numeros);
	}
	
	public static String mantemSomenteOsSeguintesCaracteres(final String s, final String... list) {
		return UString.mantemSomenteOsSeguintesCaracteres(s, ListString.newFromArray(list));
	}
	
	public static String mantemSomenteOsSeguintesCaracteres(final String s, final List<String> list) {
		return UStringFiltrarCaracteres.exec(s, list);
	}
	
	public static String mantemSomenteOsSeguintesCaracteres(final String s, final Object... list) {
		final ListString l = new ListString();
		for (final Object o : list) {
			if (o instanceof List<?>) {
				final List<?> x = (List<?>) o;
				l.add(x);
				continue;
			}
			l.add(o.toString());
		}
		return UString.mantemSomenteOsSeguintesCaracteres(s, l);
	}
	
	public static String primeiraMinuscula(String s) {
		String x = s.substring(0, 1).toLowerCase();
		s = s.substring(1);
		while (!UString.isEmpty(s) && s.substring(0, 1).equals(s.substring(0,1).toUpperCase())) {
			x += s.substring(0, 1).toLowerCase();
			s = s.substring(1);
		}
		return x + s;
	}
	
	public static String primeiraMinuscula(final Class<?> classe) {
		return UString.primeiraMinuscula(classe.getSimpleName());
	}

	public static String toNomeProprio(final String s) {
		return UString.toNomeProprio(s, false);
	}

	public static String toNomeProprio(String s, final boolean manterNumeros) {

		if (UString.isEmpty(s)) {
			return null;
		}

		s = UString.trimPlus(s);
		
		s = s.toLowerCase();

		if (manterNumeros) {
			s = UString.mantemSomenteOsSeguintesCaracteres(s, UConstantes.letrasMinusculas, " ", UConstantes.especiais, UConstantes.ESPECIAIS, UConstantes.numeros);
		} else {
			s = UString.mantemSomenteOsSeguintesCaracteres(s, UConstantes.letrasMinusculas, " ", UConstantes.especiais, UConstantes.ESPECIAIS);
		}

		s = " " + s + " ";

		for (final String letra : UConstantes.letrasMinusculas) {
			s = s.replace(" " + letra, " " + letra.toUpperCase());
		}
		for (final String letra : UConstantes.especiais ) {
			s = s.replace(" " + letra, " " + letra.toUpperCase());
		}
		for (final String x : UConstantes.PREPOSICOES) {
			s = s.replace(" " + UString.primeiraMaiuscula(x) + " ", " " + x + " ");
		}
		for (final String x : UConstantes.ARTIGOS) {
			s = s.replace(" " + UString.primeiraMaiuscula(x) + " ", " " + x + " ");
		}

		s = s.replace(" E ", " e ");
		s = s.replace(" Ou ", " ou ");

		return s.trim();

	}

	public static String toCamelCaseSepare(final Class<?> classe) {
		return UString.toCamelCaseSepare(classe.getSimpleName());
	}
	
	public static String toCamelCaseSepare(String s) {

		String x = s.substring(0, 1).toUpperCase();
		s = s.substring(1);
		s = s.replace("aa", "aA");
		String last = "";

		while (!UString.isEmpty(s)) {
			final String n = s.substring(0, 1);
			s = s.substring(1);
			if (UConstantes.letrasMaiusculas.contains(n)) {
				x += " ";
			}
			if (UInteger.isInt(n) && !UInteger.isInt(last)) {
				x += " ";
			}
			last = n;
			if (n.equals("_")) {
				x += " ";
				continue;
			}
			x += n;
		}
		
		x = UString.toNomeProprio(x, true);
		x = " " + x + " ";

		x = x.replace("ario ", ""+UConstantes.a_agudo+"rio ");
		x = x.replace("Hodometro ", "Hod"+UConstantes.o_circunflexo+"metro ");
		x = x.replace("Codigo ", "C"+UConstantes.o_agudo+"digo ");
		x = x.replace(" Debito ", " D"+UConstantes.e_agudo+"bito ");
		x = x.replace("Matricula ", "Matr"+UConstantes.i_agudo+"cula ");
		x = x.replace("Pratica ", "Pr"+UConstantes.a_agudo+"tica ");
		x = x.replace("Numero ", "N"+UConstantes.u_agudo+"mero ");
		x = x.replace("Pais ", "Pa"+UConstantes.i_agudo+"s ");
		x = x.replace("Veiculo ", "Ve"+UConstantes.i_agudo+"culo ");
		x = x.replace("Uf ", "UF ");
		x = x.replace(" U F ", " UF ");
		x = x.replace(" A ", " a ");
		x = x.replace(" O ", " o ");
		x = x.replace(" As ", " as ");
		x = x.replace(" Aos ", " aos ");
		x = x.replace(" Os ", " os ");
		x = x.replace(" Da ", " da ");
		x = x.replace(" Do ", " do ");
		x = x.replace(" Das ", " das ");
		x = x.replace(" Dos ", " dos ");
		x = x.replace(" Que ", " que ");
		x = x.replace("cao ", ""+UConstantes.cedilha+""+UConstantes.a_til+"o ");
		x = x.replace("ao ", ""+UConstantes.a_til+"o ");
		x = x.replace("oes ", ""+UConstantes.o_til+"es ");
		x = x.replace("iao ", "i"+UConstantes.a_til+"o ");
		x = x.replace("sao ", "s"+UConstantes.a_til+"o ");
		x = x.replace("aria ", ""+UConstantes.a_agudo+"ria ");
		x = x.replace("aveis ", ""+UConstantes.a_agudo+"veis ");
		x = x.replace("ario ", ""+UConstantes.a_agudo+"rio ");
		x = x.replace("icio ", ""+UConstantes.i_agudo+"cio ");
		x = x.replace("ivel ", ""+UConstantes.i_agudo+"vel ");
		x = x.replace("avel ", ""+UConstantes.a_agudo+"vel ");
		x = x.replace("ipio ", ""+UConstantes.i_agudo+"pio ");
		x = x.replace("encia ", ""+UConstantes.e_circunflexo+"ncia ");
		x = x.replace("ogico ", UConstantes.o_agudo+"gico ");
		x = x.replace(" Orgao ", " "+UConstantes.O_til+"rg"+UConstantes.a_til+"o ");
		x = x.replace("imetro", UConstantes.i_agudo+"metro");
		x = x.replace(" Em ", " em ");
		x = x.replace(" Ro ", " RO ");
		x = x.replace(" Area ", " "+UConstantes.a_agudo+"rea ");
		x = x.replace(" Agua ", " "+UConstantes.a_agudo+"gua ");
		x = x.replace(" Ultimo", " "+UConstantes.U_agudo+"ltimo");
		x = x.replace(" Unico", " "+UConstantes.U_agudo+"nico");
		x = x.replace(" Uteis ", " "+UConstantes.U_agudo+"teis ");
		x = x.replace(" Util ", " "+UConstantes.U_agudo+"til ");
		x = x.replace("Credito ", "Cr"+UConstantes.e_agudo+"dito ");
		x = x.replace(" Mae ", " M"+UConstantes.a_til+"e ");
		x = x.replace(" Ja ", " j"+UConstantes.a_agudo+" ");
		x = x.replace(" Ficara ", " Ficar"+UConstantes.a_agudo+" ");
		x = x.replace(" Valido ", " V"+UConstantes.a_agudo+"lido ");
		x = x.replace(" Seguranca ", " Seguran"+UConstantes.cedilha+"a ");
		x = x.replace(" Eh ", " "+UConstantes.E_agudo+" ");
		x = x.replace(" Pre ", " Pr"+UConstantes.e_agudo+"-");
		x = x.replace(" Nao ", " N"+UConstantes.a_til+"o ");
		x = x.replace(" Media ", " M"+UConstantes.e_agudo+"dia ");
		x = x.replace(" Acrescimo", " Acr"+UConstantes.e_agudo+"scimo");
		x = x.replace(" Endereco ", " Endere"+UConstantes.cedilha+"o ");
		x = x.replace(" Sabado", " S"+UConstantes.a_agudo+"bado");
		x = x.replace("emico ", ""+UConstantes.e_circunflexo+"mico ");
		x = x.replace(" Maxim", " M"+UConstantes.a_agudo+"xim");
		x = x.replace(" Minim", " M"+UConstantes.i_agudo+"nim");
		x = x.replace(" Apos ", " Ap"+UConstantes.o_agudo+"s ");
		x = x.replace(" Serie ", " S"+UConstantes.e_agudo+"rie ");
		x = x.replace("onteudo ", "onte"+UConstantes.u_agudo+"do ");
		x = x.replace("orio ", ""+UConstantes.o_agudo+"rio ");
		x = x.replace(" Email ", " E-mail ");
		x = x.replace(" Cnpj ", " CNPJ ");
		x = x.replace(" Cpj ", " CPF ");
		x = x.replace(" Cdi ", " CDI ");
		x = x.replace(" Tr ", " TR ");
		x = x.replace(" Selic ", " SELIC ");
		x = x.replace(" mes ", " m"+UConstantes.e_circunflexo+"s ");
		x = x.replace(" Mes ", " M"+UConstantes.e_circunflexo+"s ");
		x = x.replace(" Primeiro ", " 1"+UConstantes.o_primeiro+" ");
		x = x.replace(" Segundo ", " 2"+UConstantes.o_primeiro+" ");
		x = x.replace(" Terceiro ", " 3"+UConstantes.o_primeiro+" ");
		x = x.replace(" Primeira ", " 1"+UConstantes.a_primeira+" ");
		x = x.replace(" Segunda ", " 2"+UConstantes.a_primeira+" ");
		x = x.replace(" Terceira ", " 3"+UConstantes.a_primeira+" ");
		
		for (final String numero : UConstantes.numeros) {
			final ListString letras = UConstantes.letrasMaiusculas.union(UConstantes.letrasMinusculas);
			for (final String letra : letras) {
				x = x.replace(numero + letra, numero + " " + letra);
			}
		}
		
		x = x.trim();
		x = UString.primeiraMaiuscula(x);
		return x;

	}	

	public static String right(final String s, final int count) {
		if (s.length() < count) {
			return s;
		}
		return s.substring(s.length() - count);
	}
	
	public static String ignoreRigth(final String s) {
		return UString.ignoreRight(s, 1);
	}
	
	public static String ignoreRight(final String s, final int count) {
		return s.substring(0, s.length() - count);
	}
	
	public static String ltrim(String s) {
		if (UString.isEmpty(s)) {
			return "";
		}
		while (s.startsWith(" ") || s.startsWith("\t") || s.startsWith("\n")) {
			s = s.substring(1);
		}
		return s;
	}
	
	public static String rtrim(String s) {
		if (UString.isEmpty(s)) {
			return "";
		}
		while (s.endsWith(" ") || s.endsWith("\t") || s.endsWith("\n")) {
			s = UString.ignoreRigth(s);
		}
		return s;
	}

	
	public static boolean contains(final String s, final String... list) {
		for (final String string : list) {
			if (string != null && !"".equals(string) && s.contains(string)) {
				return true;
			}
		}
		return false;
	}

	public static String toCampoBusca(String s) {

		if (UString.isEmpty(s))
			return null;

		s = s.toLowerCase();
		s = UString.removerAcentos(s);
		s = UString.mantemSomenteOsSeguintesCaracteres(s, " ", UConstantes.letrasMinusculas, UConstantes.numeros);
		s = UString.trimPlus(s);

		s = " " + s + " ";
		s = s.replace("y", "i");
		s = s.replace("ce", "se");
		s = s.replace("ci", "si");
		s = s.replace("ch", "x");
		s = s.replace("sh", "x");
		s = s.replace("c", "k");
		s = s.replace("qui", "ki");
		s = s.replace("que", "ke");
		s = s.replace("s", "z");
		
		for (String x : UConstantes.consoantes) {
			s = s.replace("ul" + x, "u");	
		}
		
		s = s.replace("ul ", "u ");
		s = UString.trimPlus(s);

		s = s.trim();
		return s;
	
	}

	public static String textoEntreFirst(String s, final String a, final String b) {
		s = UString.afterFirst(s, a);
		if (UString.isEmpty(s)) {
			return null;
		}
		s = UString.beforeFirst(s, b);
		return s;
	}

	public static String textoEntreLast(String s, final String a, final String b) {
		s = UString.afterLast(s, a);
		if (UString.isEmpty(s)) {
			return null;
		}
		s = UString.beforeFirst(s, b);
		return s;
	}

	public static String toCamelCase(String s) {

		s = UString.removerAcentos(s);
		String xx = "";
		
		while (!s.isEmpty()) {
			final String n = s.substring(0,1);
			s = s.substring(1);
			if (n.equals(n.toUpperCase())) {
				xx += " ";
			}
			xx += n;
		}
		
		s = xx;

		final ListString caracteres = UConstantes.letrasMinusculas.copy();
		caracteres.add(UConstantes.numeros);
		caracteres.add(" ");

		while (UInteger.isInt(s.substring(0, 1))) {
			s = s.substring(1);
			if (s.isEmpty()) {
				return null;
			}
		}
		
		s = s.toLowerCase();
		s = UString.mantemSomenteOsSeguintesCaracteres(s, caracteres);
		s = UString.trimPlus(s);
		
		if (s == null) {
			return null;
		}

		s = s.replace("_", " ");
		for (final String x : UConstantes.letrasMinusculas) {
			s = s.replace(" " + x, x.toUpperCase());
		}
		for (final String x : UConstantes.numeros) {
			s = s.replace(" " + x, x);
		}
		return s;
	}
	
	private static String replacePalavraInterno(String de, final String expressao, final String resultado) {
		de = " " + de + " ";
		for (final String a : UConstantes.SIMBOLOS) {
			for (final String b : UConstantes.SIMBOLOS) {
				final String c = a + expressao + b;
				final String d = a + resultado + b;
				de = UString.replaceWhile(de, c, d);
			}
		}
		de = de.substring(1);
		de = de.substring(0, de.length() - 1);
		return de;
	}

	private static final String EXPRESSAO_TEMP = "+sb!.@#$,%*()xt-";
	
	public static String replacePalavra(String de, String expressao, final String resultado) {
		if (resultado.contains(expressao)) {
			de = UString.replacePalavraInterno(de, expressao, UString.EXPRESSAO_TEMP);
			expressao = UString.EXPRESSAO_TEMP;
		}
		return UString.replacePalavraInterno(de, expressao, resultado);
	}
	
	public static String corrigeCaracteres(String s) {
		if (UString.isEmpty(s)) {
			return null;
		}
		s = s.replace(UConstantes.A_circunflexo + UConstantes.o_primeiro, UConstantes.o_primeiro);
		return s;
	}

	public static String reverse(final String s) {
		final StringBuffer sb = new StringBuffer(s);
		sb.reverse();
		return sb.toString();
	}
	
	public static String join(final String separador, final Object... lista) {
		return new ListString(lista).join(separador);
	}
	
	public static String join(final String separador, final List<?> lista) {
		return lista.stream().map(String::valueOf).collect(Collectors.joining(separador));
	}
	
	public static boolean endsWith(final String s, final String... list) {
		for (final String string : list) if (s.endsWith(string)) return true;
		return false;
	}
	
	public static boolean startsWith(final String s, final String... list) {
		for (final String string : list) if (s.startsWith(string)) return true;
		return false;
	}

	public static String toConstant(String s) {
		if (UString.isEmpty(s)) {
			return "STRING_VAZIA";
		}
		s = s.replace("%", " percent ");
		s = s.replace("/", " barra ");
		s = s.replace(">", " maior ");
		s = s.replace("<", " menor ");
		s = s.replace("=", " igual ");
		s = s.replace("\"", " aspasduplas ");
		s = trimPlus(s);
		s = UString.removerAcentos(UString.toCamelCaseSepare(s).replace(" ", "_").toUpperCase());
		if (UInteger.isInt(s.substring(0, 1))) {
			s = "_" + s;
		}
		return s;
	}
	
	public static String repete(final String s, int vezes) {
		String result = "";
		while (vezes > 0) {
			result += s;
			vezes--;
		}
		return result;
	}
	
	public static String removeTextoEntreWhile(String s, final String inicio, final String fim) {
		String x = s;
		do {
			s = x;
			x = removeTextoEntre(s, inicio, fim);
			x = x.replace(inicio+fim, "");
		} while (!UString.equals(s, x));
		return x;
	}
	public static String removeTextoEntre(String s, final String inicio, final String fim) {
		final String before = UString.beforeLast(s, inicio);
		if (before == null) {
			return s;
		}
		String after = UString.afterLast(s, inicio);
		if (after == null) {
			return s;
		}
		after = UString.afterFirst(after, fim);
		if (after == null) {
			return s;
		}
		s = before + after;
		return s;
	}

	public static String completaComEspacosADireita(String s, final Integer casas) {
		while (s.length() < casas) {
			s += " ";
		}
		return s;
	}
	
	public static String completaComEspacosAEsquerda(String s, final Integer casas) {
		if (s == null) {
			s = "";
		}
		while (s.length() < casas) {
			s = " " + s;
		}
		return s;
	}	

	public static String completaComZerosAEsquerda(String s, final Integer casas) {
		while (s.length() < casas) {
			s = "0" + s;
		}
		return s;
	}
	
	public static boolean contemSomenteNumeros(final String s) {
		return UString.contemSomenteOsSeguintesCaracteres(s, UConstantes.numeros);
	}
	
	public static boolean contemSomenteOsSeguintesCaracteres(String s, final ListString list) {
		if (UString.isEmpty(s)) {
			return false;
		}
		s = s.toLowerCase();
		s = UString.replace(s, list, "");
		return s.isEmpty();
	}
	
	public static String replace(String em, final ListString itens, final String por) {
		for (final String string : itens) {
			em = em.replace(string, por);
		}
		return em;
	}
	
	public static boolean contemPalavra(String s, final String... list) {
		s = s.toLowerCase();
		s = UString.removerAcentos(s);
		for (final String x : UConstantes.SIMBOLOS) {
			s = s.replace(x, " ");
		}
		s = UString.trimPlus(s);
		s = " " + s + " ";
		for (final String x : list) {
			if ( s.contains(" " + x + " ") ) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checaSeSubstringEstaNaPosicao(String s, final int inicio, final String substring) {
		if ( s.length() < inicio ) {
			return false;
		}
		s = s.substring(inicio);
		return s.startsWith(substring);
	}
	
	public static final String textoScopo(String s, final String abre, final String fecha) {
		
		if (UString.isEmpty(s)) {
			return null;
		}
		
		if (UString.isEmpty(abre)) {
			throw UException.runtime("abre is empty");
		}
		
		if (UString.isEmpty(fecha)) {
			throw UException.runtime("fecha is empty");
		}
		
		if (abre.equals(fecha)) {
			throw UException.runtime("abre == fecha");
		}
		
		s = UString.afterFirst(s, abre);
		
		if (UString.isEmpty(s)) {
			return null;
		}
		
		if (!s.contains(fecha)) {
			return null;
		}
		
		String result = "";
		
		int aberturas = 1;
		
		while (aberturas > 0) {

			final int f = s.indexOf(fecha);

			if (f == -1) {
				return null;
			}

			final int a = s.indexOf(abre);
			
			if (a == -1 || a > f) {
				result += s.substring(0, f) + fecha;
				s = s.substring(f+1);
				aberturas--;
			} else {
				result += s.substring(0, a) + abre;
				s = s.substring(a+1);
				aberturas++;
			}
			
		}
		
		return UString.ignoreRight(result, 1);
		
	}

	public static int ocorrencias(String s, final String substring) {
		int i = s.length();
		s = s.replace(substring, "");
		i -= s.length();
		i = i / substring.length();
		return i;
	}

	public static boolean feminino(String s) {
		if (UString.isEmpty(s)) {
			return false;
		}
		s = UString.toCamelCaseSepare(s).toLowerCase();
		if (s.endsWith("a")) {
			return true;
		}
		if (s.endsWith(""+UConstantes.cedilha+""+UConstantes.a_til+"o")) {
			return true;
		}
		return false;
	}
	public static boolean startsWithUpper(String s) {
		if (UString.isEmpty(s)) {
			return false;
		}
		s = s.substring(0, 1).trim();
		return UConstantes.letrasMaiusculas.contains(s);
	}
	public static boolean in(final String s, final String a, final String... strings) {
		if (a.equals(s)) {
			return true;
		}
		for (final String string : strings) {
			if (s.equals(string)) {
				return true;
			}
		}
		return false;
	}
	public static int getMenorIndex(final String s, final String a, final String b) {
		
		final int index1 = s.indexOf(a);
		final int index2 = s.indexOf(b);
		
		if (index1 == -1) {
			return index2;
		} else if (index2 == -1) {
			return index1;
		} else if (index1 < index2) {
			return index1;
		} else {
			return index1;
		}
		
	}

	public static int length(final String s) {
		return UString.isEmpty(s) ? 0 : s.length();
	}
	
	public static boolean lengthIs(final String s, final int i) {
		return UString.length(s) == i;
	}

	public static String maxLength(final String s, final int max) {
		if (UString.length(s) > max) {
			return s.substring(0, max);
		} else {
			return s;
		}
	}
	
	public static String conteudoProximoParenteses(String s) {
		s = UString.afterFirst(s, "(");
		if (UString.isEmpty(s) || !s.contains(")")) {
			return null;
		}

		String r = "";
		
		int opens = 1;
		while (opens > 0) {
			String c = s.substring(0, 1);
			r += c;
			s = s.substring(1);
			if (c.contentEquals("(")) {
				opens++;
			} else if (c.contentEquals(")")) {
				opens--;
			}
		}
		
		if (r.endsWith(")")) {
			return UString.ignoreRigth(r);
		} else {
			return r;
		}
		
	}
	
	public static void main(String[] args) {
		String s = stuff("abcdef", 2, 3, "ijklmn");
		System.out.println(s);
		System.out.println("aijklmnef");
		UAssert.eq(s, "aijklmnef", "???");
	}
	
	public static String stuff(String s, int posicao, int count, String substr) {
		String x = s.substring(0, posicao-1);
		s = s.substring(posicao+count-1);
		return x + substr + s;
	}

}
