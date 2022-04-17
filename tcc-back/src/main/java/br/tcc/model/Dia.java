package br.tcc.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "dia")
public class Dia extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = false)
	private Calendar data;

	@Column(name = "nome", nullable = false, length = 50)
	private String nome;

	@Column(name = "diadasemana", nullable = false)
	private Integer diaDaSemana;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "anomes", nullable = true)
	private AnoMes anoMes;

	@Column(name = "feriado", nullable = false)
	private Boolean feriado;

	@Column(name = "diautil", nullable = false)
	private Boolean diaUtil;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public Dia getOld() {
		return (Dia) super.getOld();
	}
}
