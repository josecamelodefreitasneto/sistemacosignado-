package br.auto.model;

import br.impl.outros.EntityModelo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name="campo")
public class Campo extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="entidade", nullable=false)
	private Entidade entidade;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tipo", nullable=false)
	private Entidade tipo;

	@Column(name="nome", nullable=false, length=100)
	private String nome;

	@Column(name="nomenobanco", nullable=false, length=50)
	private String nomeNoBanco;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public Campo getOld() {
		return (Campo) super.getOld();
	}
}
