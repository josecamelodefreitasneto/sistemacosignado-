package br.tcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "anomes")
public class AnoMes extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "nome", nullable = false, length = 14)
	private String nome;

	@Column(name = "ano", nullable = false)
	private Integer ano;

	@Column(name = "mes", nullable = false)
	private Integer mes;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public AnoMes getOld() {
		return (AnoMes) super.getOld();
	}
}
