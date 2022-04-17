package gm.utils.comum;

import gm.utils.string.ListString;
import gm.utils.string.StringBox;
import gm.utils.string.UString;

public class UNomeProprio {

	private static final ListString nomesMasculinos = ListString.newFromArray(
	"Adriano","Alessandro","Alex","Andr"+UConstantes.e_agudo+"","Ant"+UConstantes.o_circunflexo+"nio",
	"Bruno",
	"C"+UConstantes.e_agudo+"sar","Caio",
	"Diego","Daniel","David",
	"Expedito","Emanuel","Ernesto",
	"Ferdinand","Fernando","Francisco","Frank",
	"Gustavo","Gabriel",
	"H"+UConstantes.e_agudo+"lio","Henrique","Heitor",
	"Isaias","Iuri",
	"Jo"+UConstantes.a_til+"o","Jos"+UConstantes.e_agudo+"",
	"Kaio",
	"Leonardo",
	"Maicon","Marcos","Mario","Maur"+UConstantes.i_agudo+"cio","Maxuel","Maycon","Michael","Mike","Moises",
	"Natanael","N"+UConstantes.o_agudo+"e","Nildo",
	"Osvaldo","Orlando","Ot"+UConstantes.a_agudo+"vio",
	"Pedro","Pietro",
	"Quintino",
	"Ricardo","Ruan",
	"Saulo","Sandro","S"+UConstantes.e_agudo+"rgio","Samoel",
	"Tiago","Tom"+UConstantes.e_agudo+"","Tomaz",
	"Ulisses",
	"Vicente","Valter","Vanderley","Vitor",
	"Washington","Waldemar","Walisson",
	"Xavier",
	"Yuri",
	"Zaqueu"
	);
	
	private static final ListString nomesFemininos = ListString.newFromArray(
	"Adriana","Alessandra","Aline","Ana",
	"Beatriz","B"+UConstantes.a_agudo+"rbara","Bianca","Bruna",
	"Carina","Carolina","Cristiane","Carla","Carmen",
	"Daniela","D"+UConstantes.e_agudo+"bora","Diana",
	"Emanuela","Evelin",""+UConstantes.E_agudo+"dna","Elaine","Ester","Elana",
	"F"+UConstantes.a_agudo+"tima","Fernanda","Francisca",
	"Gabriela",
	"Joana","Jana"+UConstantes.i_agudo+"na","J"+UConstantes.e_agudo+"ssica",
	"Karem",
	"L"+UConstantes.i_agudo+"dia","Luciana","Laura","Luana",
	"Marcela","Maria","Marta","Miri"+UConstantes.a_til+"","Mirian","Maura",
	"N"+UConstantes.a_agudo+"dia","Neuma","Noemi",
	"Otaviana","Olga",
	"Patr"+UConstantes.i_agudo+"cia","Priscila","Paloma",
	"Rosane","Ros"+UConstantes.a_circunflexo+"ngela",
	"Su"+UConstantes.e_agudo+"len","Sabrina","Sara","Sabrina",
	"Tatiana","Telma","Ta"+UConstantes.i_agudo+"s",
	"Val"+UConstantes.e_agudo+"ria","Valentina","Vanessa","V"+UConstantes.i_agudo+"vian",
	"Xaiane",
	"Yara",
	"Z"+UConstantes.e_agudo+"lia","Za"+UConstantes.i_agudo+"ra","Zuleica","Zenilda"
	);
	
	private static final ListString nomes = nomesMasculinos.union(nomesFemininos).sort();
	
	private static final ListString sobrenomes = ListString.newFromArray(
	"Alves","Azevedo",
	"Borges","Beltr"+UConstantes.a_til+"o",
	"Cabral","Caetano","Camargo","Carvalho","Correia",
	"Dias","Durval","Dalas",
	"Ferreira","Fernandes",
	"Gamarra","Godoi",
	"Lima","Lisboa",
	"Marcondes","Marlone",
	"Novaes","Neves","Nunes",
	"Oliveira",
	"Pereira","Pinho","Paiva","Peixoto","Pontes",
	"Queiroz",
	"Ramos","Rocha","Rezende","Rabelo",
	"Santos","Silva","Souza",
	"Tavarez","Tevez","Torres",
	"Vasconcelos","Vascos","Vieira",
	"Xavier"
	).sort();
	
	private static final ListString sobrenomesInvertido;
	
	static {
		sobrenomesInvertido = sobrenomes.copy();
		sobrenomesInvertido.inverteOrdem();
	}
	
	public static String aleatorio() {
		return aleatorio(Aleatorio.getBoolean());
	}
	
	public static String aleatorio(boolean masculino) {
		return Aleatorio.get(masculino ? nomesMasculinos : nomesFemininos) + " " + Aleatorio.get(sobrenomes) + " " + Aleatorio.get(sobrenomes);
	}
	
	private static String mock(ListString itens, int index) {
		while (index >= itens.size()) {
			index -= itens.size();
		}
		return itens.get(index);
	}
	
	public static String mock(int i) {
		return mock(nomes, i) + " " + mock(sobrenomes, i) + " " + sobrenomes.get(sobrenomes.size()-i-1);
	}
	
	public static void main(String[] args) {
		ListString list = sobrenomes.sort();
		String letra = list.get(0).toLowerCase().substring(0, 1);
		while (!list.isEmpty()) {
			String r = list.remove(0);
			String l = r.toLowerCase().substring(0, 1);
			if (!l.equals(letra)) {
				letra = l;
				System.out.println();
			}
			System.out.print("\""+r+"\",");
		}
	}
	
	public static ListString CARACTERES_VALIDOS =
		UConstantes.letrasMinusculas.copy().add(UConstantes.acentuadasMinusculas).add(ListString.array("'", " ", ""+UConstantes.cedilha+""))
	;

	public static String formatParcial(String s) {

		if (UString.isEmpty(s)) {
			return "";
		}

		s = UString.replaceWhile(s, "\t", " ");

		boolean espaco = s.endsWith(" ");
		s = UString.trimPlus(s);

		while (!UString.isEmpty(s) && s.startsWith("'")) {
			s = s.substring(1);
			s = UString.trimPlus(s);
		}

		if (UString.isEmpty(s)) {
			return "";
		}

		s = s.toLowerCase();
		s = UString.mantemSomenteOsSeguintesCaracteres(s, CARACTERES_VALIDOS);

		if (UString.isEmpty(s)) {
			return "";
		}

		final StringBox box = new StringBox(s);

		CARACTERES_VALIDOS.forEach(o -> {
			box.set(UString.replaceWhile(box.get(), o+o+o, o+o));
		});

		box.set(UString.replaceWhile(box.get(), "''", "'"));
		box.set(" " + box.get() + " ");

		UConstantes.letrasMinusculas.forEach(o -> {
			box.set(UString.replaceWhile(box.get(), " " + o, " " + o.toUpperCase()));
		});

		s = box.get();

		s = UString.replaceWhile(s, " De ", " de ");
		s = UString.replaceWhile(s, " Do ", " do ");
		s = UString.replaceWhile(s, " Da ", " da ");
		s = UString.replaceWhile(s, " Dos ", " dos ");
		s = UString.replaceWhile(s, " Das ", " das ");

		s = UString.trimPlus(s);

		if (UString.length(s) < 2) {
			return s;
		}

		s = s.substring(0,1).toUpperCase() + s.substring(1);

		if (espaco) {
			s += " ";
		}

		return UString.maxLength(s, 60);

	}

	public static boolean isValido(String s) {
		s = UString.trimPlus(s);
		if (UString.length(s) < 7) {
			return false;
		}
		if (!s.contains(" ")) {
			return false;
		}
		return true;
	}	
	
}
