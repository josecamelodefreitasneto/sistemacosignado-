package br.auto.model;

import br.impl.outros.EntityModelo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name="entidade")
public class Entidade extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	private Integer id;

	@Column(name="nome", nullable=false, length=100)
	private String nome;

	@Column(name="nomeclasse", nullable=false, length=100)
	private String nomeClasse;

	@Column(name="primitivo", nullable=false)
	private Boolean primitivo;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public Entidade getOld() {
		return (Entidade) super.getOld();
	}
}
