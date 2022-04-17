package br.tcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "rubrica")
public class Rubrica extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "tipo", nullable = false)
	private Integer tipo;

	@Column(name = "codigo", nullable = false, length = 50)
	private String codigo;

	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public Rubrica getOld() {
		return (Rubrica) super.getOld();
	}
}
