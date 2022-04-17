package br.tcc.model;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity @Table(name = "clienterubrica")
public class ClienteRubrica extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente", nullable = false)
	private Cliente cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rubrica", nullable = false)
	private Rubrica rubrica;

	@Digits(integer = 9, fraction = 2)
	@Column(name = "valor", nullable = false, precision = 7, scale = 2)
	private BigDecimal valor;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	public Integer getTipo() {
		final Rubrica o0 = this.getRubrica();
		if (o0 == null) return null;
		return o0.getTipo();
	}

	@Override
	public ClienteRubrica getOld() {
		return (ClienteRubrica) super.getOld();
	}
}
