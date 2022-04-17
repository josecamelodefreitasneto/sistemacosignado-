package gm.utils.comum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.validation.constraints.Digits;

import gm.utils.classes.UClass;
import gm.utils.date.Data;
import gm.utils.email.UEmail;
import gm.utils.exception.UException;
import gm.utils.number.Numeric2;
import gm.utils.number.UInteger;
import gm.utils.number.ULong;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;

public class Aleatorio {
	
	static Random random = new Random();

	public static int get(int a, int b) {
		if (a == b) {
			return a;
		}
		int maior = UInteger.maior(a, b);
		int menor = UInteger.menor(a, b);
		a = maior - menor+1;
		a = random.nextInt(a);
		a += menor;
		return a;
	}

	public static Long getLong(int a, int b) {
		if (a == b) {
			return ULong.toLong(a);
		}
		int maior = UInteger.maior(a, b);
		int menor = UInteger.menor(a, b);
		a = maior - menor+1;
		a = random.nextInt(a);
		a += menor;
		return ULong.toLong(a);
	}

	public static <T> T get(List<T> list){
		if (UList.isEmpty(list)) {
			UException.printTrace("A lista esta vazia");
		}
		return list.get( get(0, list.size()-1) );
	}
	
	public static boolean getBoolean(){
		return UInteger.ehPar( get(0,100) );
	}

	public static <T> List<T> getAmostragem(List<T> list) {
		
		try {
			
			@SuppressWarnings("unchecked")
			List<T> list2 = UClass.newInstance(list.getClass());
			list2.addAll(list);
			
			int quantidade = get(1, list.size());
			
			while (list2.size() > quantidade) {
				list2.remove( get(list) );
			}
			
			return list2;
			
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}
	public static String getIntString(int casas) {

		String s = "";
		
		while (s.length() < casas) {
			s += get(0,10000000);
		}
		
		while (s.length() > casas) {
			s = s.substring(1);
		}
		
		return s;
		
	}

	public static String getString(int casasMinimas, int casasMaximas) {
		Integer casas = get(casasMinimas, casasMaximas);
		return getString(casas);
	}
	
	public static String getString(int casas) {
		return getString(UConstantes.ALFA, casas);
	}

	private static String getString(ListString opcoes, int casas) {
		String s = "";
		while (s.length() < casas) {
			s += get( opcoes );
		}
		return s;
	}
	
	public static String getSenha(int casas) {
		ListString opcoes = UConstantes.ALFA.copy();
		opcoes.addAll(UConstantes.SIMBOLOS);
		opcoes.remove(" ");
		return getString(opcoes, casas);
	}
	
	public static String getString() {
		int casas = get(1, 100);
		return getString(casas);
	}
	
	public static String getIntMascarado(Integer... mask) {
		List<Integer> asList = Arrays.asList(mask);
		String s = "";
		for (Integer i : asList) {
			s += ".";
			
			while (i > 0) {
				i--;
				s += get(0, 9);
			}
		}
		return s.substring(1);
	}

	public static String getPalavra() {
		
		if (silabas == null) {
			
			silabas = new ListString();
			for (String c : UConstantes.consoantes) {
				for (String v : UConstantes.vogais) {
					silabas.add(c+v);
				}
			}
		}
		String s = "";
		for (int i = 0; i < get(1,5); i++) {
			s += get(silabas);
		}
		return s;
	}

	public static ListString getPalavras(int quantidadeDePalavras) {
		ListString palavras = new ListString();
		while(quantidadeDePalavras!=0){
			if (silabas == null) {
				silabas = new ListString();
				for (String c : UConstantes.consoantes) {
					for (String v : UConstantes.vogais) {
						silabas.add(c+v);
					}
				}
			}
			String s = "";
			for (int i = 0; i < get(1,5); i++) {
				s += get(silabas);
			}
			palavras.add(s);
			quantidadeDePalavras--;
		}
		return palavras;
	}
	
	public static String getTexto(int tamanho) {
		String s = "";
		while ( s.length() < tamanho ) {
			s += " " + getPalavra();
		}
		s = s.substring(0, tamanho).trim();
		return s;
	}
	
	private static ListString silabas;
	private static final ListString paises = ListString.newFromArray(
			"Afeganist"+UConstantes.a_til+"o", UConstantes.A_agudo+"frica do Sul", "Akrotiri", "Alb"+UConstantes.a_circunflexo+"nia", "Alemanha", "Andorra", "Angola", "Anguila"
//			, "Brasil"
			, "BurquinaFaso", "Bur"+UConstantes.u_agudo+"ndi", "But"+UConstantes.a_til+"o", "CaboVerde", "Camar"+UConstantes.o_til+"es", "Camboja", "Canad"+UConstantes.a_agudo+""
			, "Ant"+UConstantes.a_agudo+"rctida", "Ant"+UConstantes.i_agudo+"guaeBarbuda", "Ar"+UConstantes.a_agudo+"biaSaudita", "Arg"+UConstantes.e_agudo+"lia", "Argentina", "Arm"+UConstantes.e_agudo+"nia", "Aruba"
			, "Austr"+UConstantes.a_agudo+"lia", ""+UConstantes.A_agudo+"ustria", "Azerbaij"+UConstantes.a_til+"o", "Baamas", "Bangladeche", "Barbados", "Bar"+UConstantes.e_agudo+"m", "B"+UConstantes.e_agudo+"lgica"
			, "Belize", "Benim", "Bermudas", "Bielorr"+UConstantes.u_agudo+"ssia", "Birm"+UConstantes.a_circunflexo+"nia", "Bol"+UConstantes.i_agudo+"via", "B"+UConstantes.o_agudo+"snia e Herzegovina"
			, "Catar", "Cazaquist"+UConstantes.a_til+"o", "Chade", "Chile", "China", "Col"+UConstantes.o_circunflexo+"mbia", "Comores", "Coreia do Norte"
			, "Coreia do Sul", "Costa do Marfim", "CostaRica", "Cro"+UConstantes.a_agudo+"cia", "Cuba", "Dinamarca", "Dom"+UConstantes.i_agudo+"nica", "Egipto"
			, "Emiratos "+UConstantes.A_agudo+"rabes Unidos", "Equador", "Eritreia", "Eslov"+UConstantes.a_agudo+"quia", "Eslov"+UConstantes.e_agudo+"nia", "Espanha", "Estados Unidos"
			, "Est"+UConstantes.o_agudo+"nia", "Eti"+UConstantes.o_agudo+"pia", "Faro"+UConstantes.e_agudo+"", "Fiji", "Filipinas", "Finl"+UConstantes.a_circunflexo+"ndia", "Fran"+UConstantes.cedilha+"a", "Gab"+UConstantes.a_til+"o", "G"+UConstantes.a_circunflexo+"mbia", "Gana"
			, "GazaStrip", "Ge"+UConstantes.o_agudo+"rgia", "Gibraltar", "Granada", "Gr"+UConstantes.e_agudo+"cia", "Gronel"+UConstantes.a_circunflexo+"ndia", "Guame", "Guatemala"
			, "Guernsey", "Guiana", "Guin"+UConstantes.e_agudo+"", "Guin"+UConstantes.e_agudo+"Equatorial", "Guin"+UConstantes.e_agudo+"-Bissau", "Haiti", "Honduras", "HongKong"
			, "Hungria", "I"+UConstantes.e_agudo+"men", ""+UConstantes.I_agudo+"ndia", "Indon"+UConstantes.e_agudo+"sia", "Ir"+UConstantes.a_til+"o", "Iraque", "Irlanda", "Isl"+UConstantes.a_circunflexo+"ndia"
			, "Israel", "It"+UConstantes.a_agudo+"lia", "Jamaica", "JanMayen", "Jap"+UConstantes.a_til+"o", "Jersey", "Jibuti", "Jord"+UConstantes.a_circunflexo+"nia", "Kuwait", "Laos"
			, "Lesoto", "Let"+UConstantes.o_agudo+"nia", "L"+UConstantes.i_agudo+"bano", "Lib"+UConstantes.e_agudo+"ria", "L"+UConstantes.i_agudo+"bia", "Listenstaine", "Litu"+UConstantes.a_circunflexo+"nia", "Luxemburgo", "Macau"
			, "Maced"+UConstantes.o_agudo+"nia", "Madag"+UConstantes.a_agudo+"scar", "Mal"+UConstantes.a_agudo+"sia", "Mal"+UConstantes.a_agudo+"vi", "Maldivas", "Mali", "Malta", "Man,Isleof"
			, "Marrocos", "Maur"+UConstantes.i_agudo+"cia", "Maurit"+UConstantes.a_circunflexo+"nia", "Mayotte", "M"+UConstantes.e_agudo+"xico", "Micron"+UConstantes.e_agudo+"sia", "Mo"+UConstantes.cedilha+"ambique"
			, "Mold"+UConstantes.a_agudo+"via", "M"+UConstantes.o_agudo+"naco", "Mong"+UConstantes.o_agudo+"lia", "Montenegro", "Nam"+UConstantes.i_agudo+"bia"
			, "NavassaIsland", "Nepal", "Nicar"+UConstantes.a_agudo+"gua", "N"+UConstantes.i_agudo+"ger", "Nig"+UConstantes.e_agudo+"ria", "Niue", "Noruega", "NovaCaled"+UConstantes.o_agudo+"nia"
			, "NovaZel"+UConstantes.a_circunflexo+"ndia", "Om"+UConstantes.a_til+"", "Pa"+UConstantes.i_agudo+"sesBaixos", "Palau", "Panam"+UConstantes.a_agudo+"", "Papua-NovaGuin"+UConstantes.e_agudo+"", "Paquist"+UConstantes.a_til+"o"
			, "ParacelIslands", "Paraguai", "Peru", "Pitcairn", "Polin"+UConstantes.e_agudo+"sia Francesa", "Pol"+UConstantes.o_agudo+"nia", "PortoRico", "Portugal"
			, "Qu"+UConstantes.e_agudo+"nia", "Quirguizist"+UConstantes.a_til+"o", "Quirib"+UConstantes.a_agudo+"ti", "ReinoUnido", "Rep"+UConstantes.u_agudo+"blica Checa"
			, "Rep"+UConstantes.u_agudo+"blicaDominicana", "Rom"+UConstantes.e_agudo+"nia", "Ruanda", "R"+UConstantes.u_agudo+"ssia", "Salvador", "Samoa"
			, "S"+UConstantes.a_til+"oCrist"+UConstantes.o_agudo+"v"+UConstantes.a_til+"oeNeves", "S"+UConstantes.a_til+"oMarinho", "S"+UConstantes.a_til+"oPedroeMiquelon", "S"+UConstantes.a_til+"o Tom"+UConstantes.e_agudo+" e Pr"+UConstantes.i_agudo+"ncipe"
			, "S"+UConstantes.a_til+"oVicenteeGranadinas", "SaraOcidental", "Seicheles", "Senegal", "SerraLeoa", "S"+UConstantes.e_agudo+"rvia", "Singapura"
			, "S"+UConstantes.i_agudo+"ria", "Som"+UConstantes.a_agudo+"lia", "SriLanca", "Sud"+UConstantes.a_til+"o", "Su"+UConstantes.e_agudo+"cia", "Su"+UConstantes.i_agudo+""+UConstantes.cedilha+"a", "Suriname"
			, "Tail"+UConstantes.a_circunflexo+"ndia", "Taiwan", "Tajiquist"+UConstantes.a_til+"o", "Tanz"+UConstantes.a_circunflexo+"nia", "Timor Leste"
			, "Togo", "Tokelau", "Tonga", "Trindade e Tobago", "Tun"+UConstantes.i_agudo+"sia", "Turquemenist"+UConstantes.a_til+"o", "Turquia", "Tuvalu"
			, "Ucr"+UConstantes.a_circunflexo+"nia", "Uganda", "Uruguai", "Usbequist"+UConstantes.a_til+"o", "Vanuatu", "Vaticano", "Venezuela"
			, "Vietname", "WakeIsland", "WalliseFutuna", "WestBank", "Z"+UConstantes.a_circunflexo+"mbia", "Zimbabu"+UConstantes.e_agudo+"");		
	
	
	private static final ListString empresasRazaoSocial = ListString.newFromArray("Zest Ltda","M Republic Relations SA."
			,"Add Solutions Inc.","Geekyard Organiza"+UConstantes.cedilha+""+UConstantes.o_til+"es SA","Computec Opera"+UConstantes.cedilha+""+UConstantes.o_til+"es T"+UConstantes.e_agudo+"cnicas - SA","MicroInform"+UConstantes.a_agudo+"tica Rei Solutions Inc."
			, "Mydas Tecnologia Ltda.", "Zatos Solu"+UConstantes.cedilha+""+UConstantes.o_til+"es em Tecnologia e Empreendimentos LTDA."
			,"Vitech Inform"+UConstantes.a_agudo+"tica Solutions SA Corp.","Martins Lopes Corp. Ltda.","Magalh"+UConstantes.a_til+"es & Mombrum Sociedade Jur"+UConstantes.i_agudo+"dica Inc.");

	private static final ListString empresasNomeFantasia = ListString.newFromArray("Zest Turismo & Eventos","Republic Relations"
			,"Solu"+UConstantes.cedilha+""+UConstantes.a_til+"o - Manunten"+UConstantes.cedilha+""+UConstantes.a_til+"o de Qualidade","Geekyard","Computec","Rei do Micro", "Mydas Tecnologia"
			, "Zatos Solu"+UConstantes.cedilha+""+UConstantes.o_til+"es em Tecnologia","Vitech Inform"+UConstantes.a_agudo+"tica","Escrit"+UConstantes.o_agudo+"rio de Advocacia Martins Lopes","Magalh"+UConstantes.a_til+"es & Mombrum");
	
	private static final ListString provedores = ListString.newFromArray("gmail.com","bol.com","facebook.com","bing.com","outlook.com","uol.com","hotmail.com","yahoo.com","terra.com","globo.com");
	
	public static String getRazaoSocial() {
		return get(empresasRazaoSocial);
	}

	public static String getNomeFantasiaDeAcordoComRazaoSocial(String razaoSocial) {
		return empresasNomeFantasia.get(empresasRazaoSocial.indexOf(new String(razaoSocial)));
	}
	
	public static String getNomeFantasia() {
		return get(empresasNomeFantasia);
	}

	@Deprecated//chamar direto
	public static String getNome() {
		return UNomeProprio.aleatorio();
	}
	
	public static String getCidade() {
		return get(cidades); 
	}
	
	public static String getPaises() {
		return get(paises); 
	}
	
	public static String getUrl() {
		return "www." + get(provedores);
	}

	@Deprecated//chame direto
	public static String email() {
		return UEmail.aleatorio();
	}

	@Deprecated//chame direto
	public static String getEmail() {
		return UEmail.aleatorio();
	}
	
	private static final ListString cidades = ListString.newFromArray(
	  ""+UConstantes.A_agudo+"guas Claras", "Brazl"+UConstantes.a_circunflexo+"ndia"
	, "Candangol"+UConstantes.a_circunflexo+"ndia", "Ceil"+UConstantes.a_circunflexo+"ndia", "Gama", "Guar"+UConstantes.a_agudo+""
	, "N"+UConstantes.u_agudo+"cleo Bandeirante", "Parano"+UConstantes.a_agudo+"", "Planaltina"
	, "Recanto das Emas", "Riacho Fundo", "Samambaia"
	, "Santa Maria", "S"+UConstantes.a_til+"o Sebasti"+UConstantes.a_til+"o", "SMPW", "Sobradinho", "Taguatinga", "Vicente Pires");
	
	private static final ListString LETRAS = ListString.newFromArray("A","B","C","D","E","F");
	
	public static String getEndereco() {
		String s = "";
		s += "Quadra " + get(1,999);
		s += " - Conjunto " + get(LETRAS);
		s += " - Casa " + get(1,30);
		s += " - " + get(cidades);
		return s;
	}
	public static Integer getInteger() {
		return random.nextInt();
	}
	public static Long getLong() {
		return random.nextLong();
	}
	
	public static void sleepMiliSegundos(){
		USystem.sleepMiliSegundos( get(1, 1000) );
	}
	
	public static void sena(){
		for (int i = 0; i < 8; i++) {
			ULog.debug( "," + get(1, 80) );
			sleepMiliSegundos();
		}
	}
	
	public static Double getDouble() {
		return getBigDecimal().doubleValue();
	}
	
	public static Data getData() {
		return getData(2010, Data.now().getAno());
	}
	
	public static Data getData(int anoInicial, int anoFinal) {
		
		int ano = get(anoInicial, anoFinal);
		int mes = get(1, 12);
		int hora = get(0, 23);
		int minuto = get(0, 59);
		int segundo = get(0, 59);
		int dia = get(1, 28);

		while (true) {
			try {
				return new Data(ano, mes, dia, hora, minuto, segundo);
			} catch (Throwable e) {
				dia--;
			}
		}
		
	}
	public static Calendar getCalendar() {
		return getData().getCalendar();
	}
	public static BigDecimal getBigDecimal(int precision, int scale) {
		
		try {
			ULog.debug(">>>" + precision + " - " + scale);
			String s = getIntString(precision - scale) + "." + getIntString(scale);
			return new BigDecimal(s);
		} catch (Exception e) {
			ULog.error( "getBigDecimal("+precision+","+scale+")");
			throw UException.runtime(e);
		}
		
	}
	public static Numeric2 numeric2() {
		return new Numeric2(getBigDecimal());
	}
	public static BigDecimal getBigDecimal() {
		int precision = get(2, 7);
		return getBigDecimal(precision, 2);
	}
	public static Object get(Class<?> type) {
		if ( type.equals(Integer.class) ) {
			return getInteger();
		}
		if ( type.equals(String.class) ) {
			return getString();
		}
		if ( type.equals(Boolean.class) ) {
			return getBoolean();
		}
		if ( type.equals(Double.class) ) {
			return getDouble();
		}
		if ( type.equals(Long.class) ) {
			return getLong();
		}
		if ( type.equals(Calendar.class) ) {
			return getCalendar();
		}
//		if ( type.equals(BigDecimal.class) ) {
//			return getBigDecimal();
//		}
		if (type.isAssignableFrom(BigDecimal.class)) {
			return getBigDecimal();
		}
//		if ( type.getSimpleName().equals("int") ) {
//			return getInteger();
//		}
		if (type.isAssignableFrom(int.class)) {
			return getInteger();
		}
//		if ( type.getSimpleName().equals("double") ) {
//			return getDouble();
//		}
		if (type.isAssignableFrom(double.class)) {
			return getDouble();
		}
//		if ( type.getSimpleName().equals("boolean") ) {
//			return getBoolean();
//		}
		if (type.isAssignableFrom(boolean.class)) {
			return getBoolean();
		}
//		if ( type.getSimpleName().equals("long") ) {
//			return getLong();
//		}
		if (type.isAssignableFrom(long.class)) {
			return getLong();
		}
		ULog.debug(type.getSimpleName());
		throw UException.runtime("N"+UConstantes.a_til+"o sei tratar o tipo " + type);
	}
	public static <T> List<T> getList(Class<T> classe, int quantidade) {
		
		Atributos atributos = ListAtributos.get(classe);
		
		Object value = null;
		
		List<T> list = new ArrayList<>();
		
		for (int i = 0; i < quantidade; i++) {
			T o = UClass.newInstance(classe);
			for (Atributo atributo : atributos) {
				
				if (atributo.isString()) {
					value = classe.getSimpleName() + "." + atributo.nome() + "." + i + " xxx " + getString(5);
				} else if( atributo.isArray() ){
					
					Object[] v = (Object[]) atributo.get(o);
					
					if (v == null) {
						continue;
					}
					
					String s = atributo.getType().getName().substring(2);
					s = s.replace(";", "");
					Class<?> classe2 = UClass.getClass(s);
					
					if (classe2.equals(String.class)) {
						for (int j = 0; j < v.length; j++) {
							value = classe.getSimpleName() + "." + atributo.nome() + "["+j+"] " + i;
							v[j] = value;
						}
					} else {
						for (int j = 0; j < v.length; j++) {
							value = get(classe2);
							v[j] = value;
						}
					}
					
					continue;
					
				} else {
					value = get( atributo.getType() );
				}
				atributo.set(o, value);
			}
			list.add(o);
		}
		return list;
	}
	
	public Long telefone(){
		String s = "";
		for (int i = 0; i < 10; i++) {
			s = s+i;
		}
		return ULong.toLong(s);
	}

	public static String getFrase(int palavras) {
		String s = "";
		for (int i = 0; i < palavras; i++) {
			s += getPalavra() + " ";
		}
		return s;
	}

	public static BigDecimal getBigDecimal(Digits digits) {
		return getBigDecimal(digits.integer(), digits.fraction());
//		return getBigDecimal(digits.fraction(), digits.integer());
	}

	static final ListString ceps = ListString.newFromArray(
			"23036-380","26510-757","69900-415","57045-085","69550-230","68909-537","41810-230","60733-134","72220-530","75349-980","38540-970","79680-970","78480-970","68440-970","58125-970","53690-970","64000-140","82640-460","25243-425","59064-120","96214-660","76870-485","69312-034","89226-189","79824-302","13525-970");

	public static String getCep() {
		return get(ceps);
	}

	static final ListString titulos = ListString.newFromArray(
			"543153210159",	"636705000159",	"808736102089",	"316737221040",	"083413621260",	"416776651856",	"071075571074",	"357416712160",	"325485432070",	"661231082097",	"454540432046",	"665774222020",	"046434332003",	"668472182097",	"842644402070",	"317687450507",	"653382480507",	"465606670590",	"343288560574",	"118618430515",	"574617430540",	"654757030582");	

	public static String getTituloEleitor() {
		return get(titulos);
	}

	static final ListString rgs = ListString.newFromArray(
			"65.794.488-9","85.937.188-8","02.098.181-8","83.949.034-3","25.123.479-4","66.356.494-3","97.111.436-5","12.507.941-2","59.911.515-4","10.942.030-5","09.055.826-1","58.482.712-0","63.203.000-8","66.783.469-2","30.532.847-5","12.499.988-8","36.609.630-8","46.122.867-1","99.634.856-6","36.702.386-6","49.321.008-8","76.624.745-4","56.189.515-6","59.962.205-2","28.115.414-4","75.077.849-0","00.267.391-5","08.543.957-5","27.318.229-8","78.875.450-6","50.081.180-5","44.388.262-9","50.265.232-9","37.501.186-9","69.265.509-8","09.834.359-2");	
	                                                             
	public static String getRg() {
		return get(rgs);
	}
	
//	public static Uf uf(){
//		return get(FwSelect.uf().limit(20).list());
//	}
	
	public static String getCPF() {
		return UCpf.aleatorio();
	}

}
