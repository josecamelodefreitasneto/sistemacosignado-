package br.auto.model;

import br.impl.outros.EntityModelo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name="arquivoextensao")
public class ArquivoExtensao extends EntityModelo {

	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="nome", nullable=false, length=50)
	private String nome;

	@Column(name="excluido", nullable=false)
	private Boolean excluido;

	@Column(name="registrobloqueado", nullable=false)
	private Boolean registroBloqueado;

	@Column(name="busca", nullable=false, length=500)
	private String busca;

	@Override
	public ArquivoExtensao getOld() {
		return (ArquivoExtensao) super.getOld();
	}
}
