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

@Getter @Setter @Entity @Table(name = "clientesimulacao")
public class ClienteSimulacao extends EntityModelo {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente", nullable = false)
	private Cliente cliente;

	@Column(name = "parcelas", nullable = true)
	private Integer parcelas;

	@Digits(integer = 6, fraction = 5)
	@Column(name = "indice", nullable = true, precision = 1, scale = 5)
	private BigDecimal indice;

	@Digits(integer = 9, fraction = 2)
	@Column(name = "valor", nullable = true, precision = 7, scale = 2)
	private BigDecimal valor;

	@Column(name = "contratado", nullable = false)
	private Boolean contratado;

	@Column(name = "excluido", nullable = false)
	private Boolean excluido;

	@Column(name = "registrobloqueado", nullable = false)
	private Boolean registroBloqueado;

	@Column(name = "busca", nullable = false, length = 500)
	private String busca;

	@Override
	public ClienteSimulacao getOld() {
		return (ClienteSimulacao) super.getOld();
	}
}
