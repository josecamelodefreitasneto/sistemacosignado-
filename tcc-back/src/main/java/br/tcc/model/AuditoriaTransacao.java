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

@Getter @Setter @Entity @Table(name = "auditoriatransacao")
public class AuditoriaTransacao extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "login", nullable = false)
	private Login login;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "comando", nullable = false)
	private Comando comando;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	private Calendar data;

	@Digits(integer = 36000003, fraction = 3)
	@Column(name = "tempo", nullable = false, precision = 36000000, scale = 3)
	private BigDecimal tempo;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public AuditoriaTransacao getOld() {
		return (AuditoriaTransacao) super.getOld();
	}
}
