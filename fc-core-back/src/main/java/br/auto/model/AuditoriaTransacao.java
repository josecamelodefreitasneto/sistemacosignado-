package br.auto.model;

import br.impl.outros.EntityModelo;
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
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name="auditoriatransacao")
public class AuditoriaTransacao extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="login", nullable=false)
	private Login login;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="comando", nullable=false)
	private Comando comando;

	@Column(name="data", nullable=false)
	@Temporal(value=TemporalType.TIMESTAMP)
	private Calendar data;

	@Digits(integer=12, fraction=3)
	@Column(name="tempo", nullable=false, precision=9, scale=3)
	private BigDecimal tempo;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public AuditoriaTransacao getOld() {
		return (AuditoriaTransacao) super.getOld();
	}
}
