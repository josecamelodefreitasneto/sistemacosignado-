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

@Getter @Setter @Entity @Table(name="perfilcomando")
public class PerfilComando extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="perfil", nullable=false)
	private Perfil perfil;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="comando", nullable=false)
	private Comando comando;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public PerfilComando getOld() {
		return (PerfilComando) super.getOld();
	}
}
