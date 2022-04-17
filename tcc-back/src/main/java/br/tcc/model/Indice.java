package br.tcc.model;

import java.math.BigDecimal;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Indice extends EntityModelo {

	private final Integer id;

	private final String nome;

	private final BigDecimal em12;

	private final BigDecimal em15;

	private final BigDecimal em18;

	private final BigDecimal em24;

	private final BigDecimal em30;

	private final BigDecimal em36;

	private final BigDecimal em48;

	private final BigDecimal em60;

	private final BigDecimal em72;

	private final BigDecimal em84;

	private final BigDecimal em96;

	@Override
	public void setId(final Integer id) {
		throw new RuntimeException("???");
	}

	@Override
	public Boolean getExcluido() {
		return false;
	}

	@Override
	public Boolean getRegistroBloqueado() {
		return false;
	}

	@Override
	public void setExcluido(final Boolean value) {}

	@Override
	public void setRegistroBloqueado(final Boolean value) {}

	@Override
	public Indice getOld() {
		return (Indice) super.getOld();
	}

	public Indice(final int id, final BigDecimal em12, final BigDecimal em15, final BigDecimal em18, final BigDecimal em24, final BigDecimal em30, final BigDecimal em36, final BigDecimal em48, final BigDecimal em60, final BigDecimal em72, final BigDecimal em84, final BigDecimal em96, final String nome) {
		this.id = id;
		this.em12 = em12;
		this.em15 = em15;
		this.em18 = em18;
		this.em24 = em24;
		this.em30 = em30;
		this.em36 = em36;
		this.em48 = em48;
		this.em60 = em60;
		this.em72 = em72;
		this.em84 = em84;
		this.em96 = em96;
		this.nome = nome;
	}
}
