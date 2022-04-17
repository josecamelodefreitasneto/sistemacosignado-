package br.tcc.model;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClienteStatus extends EntityModelo {

	private final Integer id;

	private final String nome;

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
	public ClienteStatus getOld() {
		return (ClienteStatus) super.getOld();
	}

	public ClienteStatus(final int id, final String nome) {
		this.id = id;
		this.nome = nome;
	}
}
