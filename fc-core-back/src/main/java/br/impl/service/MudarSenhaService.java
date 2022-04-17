package br.impl.service;

import br.auto.model.MudarSenha;
import br.auto.model.Usuario;
import br.auto.service.IDSDefault;
import br.auto.service.MudarSenhaServiceAbstract;
import br.impl.outros.ThreadScope;
import gm.utils.exception.MessageException;
import gm.utils.string.UString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MudarSenhaService extends MudarSenhaServiceAbstract {
	
	@Autowired LoginService loginService;
	@Autowired UsuarioService usuarioService;

	@Override
	protected void persistInsert(MudarSenha o) {
		if (UString.isEmpty(o.getSenhaAtual())) {
			throw new MessageException("Senha atual incorreta!");
		}
		if (UString.isEmpty(o.getNovaSenha())) {
			throw new MessageException("Nova Senha incorreta!");
		}
		if (!UString.equals(o.getNovaSenha(), o.getConfirmarSenha())) {
			throw new MessageException("Senhas não conferem!");
		}
		if (UString.equals(o.getNovaSenha(), o.getSenhaAtual())) {
			throw new MessageException("Mesma Senha!");
		}
		Usuario u = usuarioService.find(loginService.find(ThreadScope.getLogin()).getUsuario().getId());
		
		if (!UString.equals(o.getSenhaAtual(), u.getSenha())) {
			String a = UsuarioService.criptografarSenha(u, o.getSenhaAtual());
			if (!UString.equals(a, u.getSenha())) {
				throw new MessageException("Senha atual incorreta!");	
			}
		}
		
		u.setSenha(o.getNovaSenha());
		usuarioService.save(u);
	}
	
	@Override
	public int getIdEntidade() {
		return IDSDefault.Usuario.idEntidade;
	}
	
}