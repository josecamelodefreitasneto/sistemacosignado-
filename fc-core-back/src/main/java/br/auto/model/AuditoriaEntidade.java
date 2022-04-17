package br.auto.model;

import br.impl.outros.EntityModelo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name="auditoriaentidade")
public class AuditoriaEntidade extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="transacao", nullable=false)
	private AuditoriaTransacao transacao;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entidade", nullable=false)
	private Entidade entidade;

	@Column(name="tipo", nullable=false)
	private Integer tipo;

	@Column(name="registro", nullable=false)
	private Integer registro;

	@Column(name="numerodaoperacao", nullable=false)
	private Integer numeroDaOperacao;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public AuditoriaEntidade getOld() {
		return (AuditoriaEntidade) super.getOld();
	}
}
