package br.tcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "cep")
public class Cep extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "numero", nullable = false, length = 10)
	private String numero;

	@Column(name = "uf", nullable = false, length = 100)
	private String uf;

	@Column(name = "cidade", nullable = false, length = 100)
	private String cidade;

	@Column(name = "bairro", nullable = false, length = 100)
	private String bairro;

	@Column(name = "logradouro", nullable = false, length = 100)
	private String logradouro;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public Cep getOld() {
		return (Cep) super.getOld();
	}
}
