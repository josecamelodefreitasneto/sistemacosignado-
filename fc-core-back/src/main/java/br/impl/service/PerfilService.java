package br.impl.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import br.auto.model.Perfil;
import br.auto.service.PerfilServiceAbstract;

@Component
public class PerfilService extends PerfilServiceAbstract {
	
	@Transactional
	public Perfil add(int id, String nome) {
		if (exists(id)) {
			return find(id);
		} else {
			Perfil o = newO();
			o.setId(id);
			o.setNome(nome);
			o = insertSemAuditoria(o);
			return o;
		}
	}
	
}