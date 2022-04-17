package br.impl.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import br.auto.service.EntidadeServiceAbstract;

@Component
public class EntidadeService extends EntidadeServiceAbstract {

	private static Map<String, Integer> ids = new HashMap<>();
	
	public int getId(final Class<?> classe) {
		final String nomeClasse = classe.getSimpleName();
		Integer id = EntidadeService.ids.get(nomeClasse);
		if (id == null) {
			id = select(null).nomeClasse().eq(nomeClasse).uniqueObrig().getId();
			EntidadeService.ids.put(nomeClasse, id);
		}
		return id;
	}

}
