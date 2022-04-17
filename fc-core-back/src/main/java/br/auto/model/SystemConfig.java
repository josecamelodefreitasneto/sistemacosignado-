package br.auto.model;

import br.impl.outros.EntityModelo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name="systemconfig")
public class SystemConfig extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	private Integer id;

	@Column(name="nome", nullable=false, length=50)
	private String nome;

	@Column(name="valor", nullable=true, length=50)
	private String valor;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public SystemConfig getOld() {
		return (SystemConfig) super.getOld();
	}
}
