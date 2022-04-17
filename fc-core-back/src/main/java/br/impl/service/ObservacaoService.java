package br.impl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.auto.model.Entidade;
import br.auto.model.Observacao;
import br.auto.service.ObservacaoServiceAbstract;
import gm.utils.map.MapSO;

@Component
public class ObservacaoService extends ObservacaoServiceAbstract {

	@Autowired EntidadeService entidadeService;
	
	public List<Observacao> get(final Entidade entidade, final int registro) {
		return select(null).entidade().eq(entidade).registro().eq(registro).list();
	}
	public List<Observacao> get(final int entidade, final int registro) {
		return this.get(entidadeService.find(entidade), registro);
	}
	public void save(final int entidade, final int registro, final MapSO map) {
		final List<MapSO> list = map.get("observacoes");
		if (list != null) {
			for (final MapSO item : list) {
				item.add("entidade", entidade);
				item.add("registro", registro);
				this.save(item);
			}
		}
	}
	
	@Override
	protected Observacao fromMap(final MapSO map) {
		final Observacao o = super.fromMap(map);
		o.setEntidade(entidadeService.find(map.getIntObrig("entidade")));
		o.setRegistro(map.getIntObrig("registro"));
		return o;
	}
	
}