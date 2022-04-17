package br.tcc.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "login")
public class Login extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario", nullable = false)
	private Usuario usuario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	private Calendar data;

	@Column(name = "token", nullable = false, length = 50)
	private String token;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public Login getOld() {
		return (Login) super.getOld();
	}
}
