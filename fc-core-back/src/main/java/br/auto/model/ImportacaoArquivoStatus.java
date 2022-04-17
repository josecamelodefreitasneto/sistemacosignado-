package br.auto.model;

import br.impl.outros.EntityModelo;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ImportacaoArquivoStatus extends EntityModelo {

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
	public ImportacaoArquivoStatus getOld() {
		return (ImportacaoArquivoStatus) super.getOld();
	}

	public ImportacaoArquivoStatus(final int id, final String nome) {
		this.id = id;
		this.nome = nome;
	}
}
