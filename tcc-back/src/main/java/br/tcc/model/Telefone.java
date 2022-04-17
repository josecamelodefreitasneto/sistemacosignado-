package br.tcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "telefone")
public class Telefone extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ddd", nullable = false)
	private Integer ddd;

	@Column(name = "numero", nullable = false, length = 9)
	private String numero;

	@Column(name = "nome", nullable = false, length = 28)
	private String nome;

	@Column(name = "whatsapp", nullable = true)
	private Boolean whatsapp;

	@Column(name = "recado", nullable = true)
	private Boolean recado;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public Telefone getOld() {
		return (Telefone) super.getOld();
	}
}
