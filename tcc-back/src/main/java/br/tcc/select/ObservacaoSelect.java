package br.tcc.select;

import br.tcc.model.Observacao;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectString;

public class ObservacaoSelect<ORIGEM> extends SelectBase<ORIGEM, Observacao, ObservacaoSelect<ORIGEM>> {
	public ObservacaoSelect(final ORIGEM origem, Criterio<?> criterio, final String prefixo) {
		super(origem, criterio, prefixo, Observacao.class);
	}
	public SelectInteger<ObservacaoSelect<?>> id() {
		return new SelectInteger<>(this, "id");
	}
	public SelectString<ObservacaoSelect<?>> texto() {
		return new SelectString<>(this, "texto");
	}
	public ArquivoSelect<ObservacaoSelect<?>> anexo() {
		return new ArquivoSelect<>(this, getC(), getPrefixo() + ".anexo" );
	}
	public EntidadeSelect<ObservacaoSelect<?>> entidade() {
		return new EntidadeSelect<>(this, getC(), getPrefixo() + ".entidade" );
	}
	public SelectInteger<ObservacaoSelect<?>> registro() {
		return new SelectInteger<>(this, "registro");
	}
	public SelectBoolean<ObservacaoSelect<?>> excluido() {
		return new SelectBoolean<>(this, "excluido");
	}
	public SelectBoolean<ObservacaoSelect<?>> registroBloqueado() {
		return new SelectBoolean<>(this, "registroBloqueado");
	}
	public SelectString<ObservacaoSelect<?>> busca() {
		return new SelectString<>(this, "busca");
	}
	public ObservacaoSelect<?> asc() {
		return id().asc();
	}
}
