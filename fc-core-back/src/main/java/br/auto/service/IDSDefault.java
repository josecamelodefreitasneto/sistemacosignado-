package br.auto.service;

public class IDSDefault {

	public static final class Arquivo {
		public static final int idEntidade = 8;
		public static final int nome = 4;
		public static final int tamanho = 5;
		public static final int path = 6;
		public static final int extensao = 7;
		public static final int checksum = 8;
	}
	public static final class ArquivoExtensao {
		public static final int idEntidade = 9;
		public static final int nome = 9;
	}
	public static final class ArquivoPath {
		public static final int idEntidade = 10;
		public static final int nome = 10;
		public static final int extensao = 11;
		public static final int itens = 12;
	}
	public static final class AuditoriaCampo {
		public static final int idEntidade = 11;
		public static final int auditoriaEntidade = 13;
		public static final int campo = 14;
		public static final int de = 15;
		public static final int para = 16;
	}
	public static final class AuditoriaEntidade {
		public static final int idEntidade = 12;
		public static final int transacao = 17;
		public static final int entidade = 18;
		public static final int tipo = 19;
		public static final int registro = 20;
		public static final int numeroDaOperacao = 21;
	}
	public static final class AuditoriaTransacao {
		public static final int idEntidade = 13;
		public static final int login = 22;
		public static final int comando = 23;
		public static final int data = 24;
		public static final int tempo = 25;
	}
	public static final class Campo {
		public static final int idEntidade = 14;
		public static final int entidade = 26;
		public static final int tipo = 27;
		public static final int nome = 28;
		public static final int nomeNoBanco = 29;
	}
	public static final class Comando {
		public static final int idEntidade = 15;
		public static final int entidade = 30;
		public static final int nome = 31;
	}
	public static final class Entidade {
		public static final int idEntidade = 17;
		public static final int nome = 38;
		public static final int nomeClasse = 39;
		public static final int primitivo = 40;
	}
	public static final class FilaScripts {
		public static final int idEntidade = 18;
		public static final int operacao = 41;
		public static final int sql = 42;
	}
	public static final class ImportacaoArquivo {
		public static final int idEntidade = 19;
		public static final int arquivo = 43;
		public static final int delimitador = 44;
		public static final int atualizarRegistrosExistentes = 45;
		public static final int entidade = 46;
		public static final int status = 47;
		public static final int totalDeLinhas = 48;
		public static final int processadosComSucesso = 49;
		public static final int processadosComErro = 50;
		public static final int arquivoDeErros = 51;
	}
	public static final class ImportacaoArquivoErro {
		public static final int idEntidade = 20;
		public static final int importacaoArquivo = 52;
		public static final int linha = 53;
		public static final int erro = 54;
	}
	public static final class ImportacaoArquivoErroMensagem {
		public static final int idEntidade = 21;
		public static final int mensagem = 55;
	}
	public static final class Login {
		public static final int idEntidade = 23;
		public static final int usuario = 57;
		public static final int data = 58;
		public static final int token = 59;
	}
	public static final class Observacao {
		public static final int idEntidade = 25;
		public static final int texto = 61;
		public static final int anexo = 62;
		public static final int entidade = 63;
		public static final int registro = 64;
	}
	public static final class Perfil {
		public static final int idEntidade = 26;
		public static final int nome = 65;
	}
	public static final class PerfilComando {
		public static final int idEntidade = 27;
		public static final int perfil = 66;
		public static final int comando = 67;
	}
	public static final class SystemConfig {
		public static final int idEntidade = 28;
		public static final int nome = 68;
		public static final int valor = 69;
	}
	public static final class Usuario {
		public static final int idEntidade = 30;
		public static final int nome = 71;
		public static final int login = 72;
		public static final int senha = 73;
	}
	public static final class UsuarioPerfil {
		public static final int idEntidade = 31;
		public static final int usuario = 74;
		public static final int perfil = 75;
	}
}
