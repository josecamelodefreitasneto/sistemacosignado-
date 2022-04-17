package br.tcc.model;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "cliente")
public class Cliente extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "cpf", nullable = false, length = 14)
	private String cpf;

	@Temporal(TemporalType.DATE)
	@Column(name = "datadenascimento", nullable = true)
	private Calendar dataDeNascimento;

	@Column(name = "status", nullable = false)
	private Integer status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "atendenteresponsavel", nullable = true)
	private Atendente atendenteResponsavel;

	@Column(name = "tipo", nullable = false)
	private Integer tipo;

	@Column(name = "matricula", nullable = true, length = 50)
	private String matricula;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgao", nullable = true)
	private Orgao orgao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "banco", nullable = true)
	private Banco banco;

	@Column(name = "agencia", nullable = true, length = 50)
	private String agencia;

	@Column(name = "numerodaconta", nullable = true, length = 50)
	private String numeroDaConta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "telefoneprincipal", nullable = true)
	private Telefone telefonePrincipal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "telefonesecundario", nullable = true)
	private Telefone telefoneSecundario;

	@Column(name = "email", nullable = true)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cep", nullable = true)
	private Cep cep;

	@Column(name = "complemento", nullable = true, length = 50)
	private String complemento;

	@Digits(integer = 9, fraction = 2)
	@Column(name = "rendabruta", nullable = true, precision = 7, scale = 2)
	private BigDecimal rendaBruta;

	@Digits(integer = 9, fraction = 2)
	@Column(name = "rendaliquida", nullable = true, precision = 7, scale = 2)
	private BigDecimal rendaLiquida;

	@Digits(integer = 9, fraction = 2)
	@Column(name = "margem", nullable = true, precision = 7, scale = 2)
	private BigDecimal margem;

	@Column(name = "tipodesimulacao", nullable = true)
	private Integer tipoDeSimulacao;

	@Digits(integer = 9, fraction = 2)
	@Column(name = "valordesimulacao", nullable = true, precision = 7, scale = 2)
	private BigDecimal valorDeSimulacao;

	@Column(name = "dia", nullable = true)
	private Integer dia;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	public String getUf() {
		final Cep o0 = this.getCep();
		if (o0 == null) return null;
		return o0.getUf();
	}

	public String getCidade() {
		final Cep o0 = this.getCep();
		if (o0 == null) return null;
		return o0.getCidade();
	}

	public String getBairro() {
		final Cep o0 = this.getCep();
		if (o0 == null) return null;
		return o0.getBairro();
	}

	public String getLogradouro() {
		final Cep o0 = this.getCep();
		if (o0 == null) return null;
		return o0.getLogradouro();
	}

	@Override
	public Cliente getOld() {
		return (Cliente) super.getOld();
	}
}
