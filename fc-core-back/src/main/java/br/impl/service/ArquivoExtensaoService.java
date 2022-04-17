package br.impl.service;

import org.springframework.stereotype.Component;

import br.auto.model.ArquivoExtensao;
import br.auto.service.ArquivoExtensaoServiceAbstract;

@Component
public class ArquivoExtensaoService extends ArquivoExtensaoServiceAbstract {

	public ArquivoExtensao get(final String nome) {
		ArquivoExtensao o = select().nome().eq(nome).unique();
		if (o == null) {
			o = newO();
			o.setNome(nome);
			o = this.save(o);
		}
		return o;
	}}
