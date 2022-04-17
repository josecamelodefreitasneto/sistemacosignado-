package br.impl.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.auto.model.Comando;
import br.auto.model.Entidade;
import br.auto.service.ComandoServiceAbstract;

@Component
public class ComandoService extends ComandoServiceAbstract {
	
	@Autowired EntidadeService entidadeService; 

	@Transactional
	public Comando get(int idEntidade, String nome) {
		return get(entidadeService.find(idEntidade), nome);
	}
	
	@Transactional
	public int getId(int idEntidade, String nome) {
		return get(idEntidade, nome).getId();
	}

	@Transactional
	public Comando get(Entidade entidade, String nome) {
		Comando o = select(null).entidade().eq(entidade).nome().eq(nome).unique();
		if (o == null) {
			o = newO();
			o.setNome(nome);
			o.setEntidade(entidade);
			insertSemAuditoria(o);
		}
		return o;
	}
	
}
