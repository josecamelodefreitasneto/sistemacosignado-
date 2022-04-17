package br.tcc.model;

import br.tcc.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MudarSenha extends EntityModelo {

	private Integer id;

	private String senhaAtual;

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
	public MudarSenha getOld() {
		return (MudarSenha) super.getOld();
	}
}
