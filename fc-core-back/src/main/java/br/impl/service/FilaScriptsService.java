package br.impl.service;

import org.springframework.stereotype.Component;

import br.auto.model.FilaScripts;
import br.auto.service.FilaScriptsServiceAbstract;

@Component
public class FilaScriptsService extends FilaScriptsServiceAbstract {

	public void add(final String sql) {
		final FilaScripts o = newO();
		o.setSql(sql);
		this.save(o);
	}
	
}
