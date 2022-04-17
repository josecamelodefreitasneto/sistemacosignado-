package br.impl.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import br.auto.model.Perfil;
import br.auto.model.Usuario;
import br.auto.model.UsuarioPerfil;
import br.auto.service.UsuarioPerfilServiceAbstract;
import gm.utils.comum.Lst;

@Component
public class UsuarioPerfilService extends UsuarioPerfilServiceAbstract {

	@Transactional
	public void add(Usuario usuario, Perfil perfil) {
		UsuarioPerfil o = select().usuario().eq(usuario).perfil().eq(perfil).unique();
		if (o == null) {
			o = newO();
			o.setUsuario(usuario);
			o.setPerfil(perfil);
			save(o);
		}
	}

	public Lst<UsuarioPerfil> list(Usuario usuario) {
		return select(null).usuario().eq(usuario).list();
	}

	public boolean findByPerfilUsuarioExists(Perfil perfil, Usuario usuario) {
		return select(null).perfil().eq(perfil).usuario().eq(usuario).exists();
	}
	
}
