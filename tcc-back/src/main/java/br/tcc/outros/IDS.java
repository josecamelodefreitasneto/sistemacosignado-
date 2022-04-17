package br.tcc.outros;

public class IDS {

	public static final class Atendente {
		public static final int idEntidade = 10001;
		public static final int nome = 10001;
		public static final int email = 10002;
		public static final int usuario = 10003;
	}
	public static final class Banco {
		public static final int idEntidade = 10003;
		public static final int codigo = 10006;
		public static final int nome = 10009;
	}
	public static final class Cep {
		public static final int idEntidade = 10004;
		public static final int numero = 10010;
		public static final int uf = 10098;
		public static final int cidade = 10099;
		public static final int bairro = 10011;
		public static final int logradouro = 10012;
	}
	public static final class Cliente {
		public static final int idEntidade = 10006;
		public static final int nome = 10015;
		public static final int cpf = 10016;
		public static final int dataDeNascimento = 10017;
		public static final int status = 10106;
		public static final int atendenteResponsavel = 10019;
		public static final int tipo = 10018;
		public static final int matricula = 10024;
		public static final int orgao = 10025;
		public static final int banco = 10100;
		public static final int agencia = 10101;
		public static final int numeroDaConta = 10102;
		public static final int telefonePrincipal = 10020;
		public static final int telefoneSecundario = 10021;
		public static final int email = 10022;
		public static final int cep = 10103;
		public static final int complemento = 10104;
		public static final int rendaBruta = 10090;
		public static final int rendaLiquida = 10065;
		public static final int margem = 10028;
		public static final int tipoDeSimulacao = 10091;
		public static final int valorDeSimulacao = 10066;
		public static final int dia = 10072;
		public static final int calcular = 10109;
	}
	public static final class ClienteRubrica {
		public static final int idEntidade = 10015;
		public static final int cliente = 10049;
		public static final int rubrica = 10050;
		public static final int valor = 10051;
	}
	public static final class ClienteSimulacao {
		public static final int idEntidade = 10019;
		public static final int cliente = 10074;
		public static final int parcelas = 10075;
		public static final int indice = 10076;
		public static final int valor = 10077;
		public static final int contratar = 10105;
		public static final int contratado = 10107;
	}
	public static final class Orgao {
		public static final int idEntidade = 10010;
		public static final int codigo = 10035;
		public static final int nome = 10093;
	}
	public static final class Rubrica {
		public static final int idEntidade = 10011;
		public static final int tipo = 10038;
		public static final int codigo = 10039;
		public static final int nome = 10040;
	}
	public static final class Telefone {
		public static final int idEntidade = 10013;
		public static final int ddd = 10042;
		public static final int numero = 10043;
		public static final int nome = 10044;
		public static final int whatsapp = 10045;
		public static final int recado = 10046;
	}
}
