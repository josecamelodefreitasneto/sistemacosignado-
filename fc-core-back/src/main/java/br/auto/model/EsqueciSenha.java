package br.auto.model;

import br.impl.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EsqueciSenha extends EntityModelo {

	private Integer id;

	private String novaSenha;

	private String confirmarSenha;

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
	public EsqueciSenha getOld() {
		return (EsqueciSenha) super.getOld();
	}
}
