package br.tcc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "atendente")
public class Atendente extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "email", nullable = false)
	private String email;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario", nullable = true)
	private Usuario usuario;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public Atendente getOld() {
		return (Atendente) super.getOld();
	}
}
