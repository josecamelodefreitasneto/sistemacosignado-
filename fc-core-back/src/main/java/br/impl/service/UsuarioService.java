package br.impl.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import br.auto.model.Usuario;
import br.auto.service.UsuarioServiceAbstract;
import gm.utils.comum.Criptografar;
import gm.utils.exception.MessageException;
import gm.utils.string.UString;

@Component
public class UsuarioService extends UsuarioServiceAbstract {

	private static final String LOGIN_SYSTEM = "system@system";
	private static final String SENHA_INICIAL = "123456";

	public static String criptografarSenha(int id, String senha) {
		return Criptografar.md5("';!@#$%^&*()_+-"+id+"-"+senha+"-+_)(*&^%$#@!;");
	}
	public static String criptografarSenha(Usuario o, String senha) {
		return criptografarSenha(o.getId(), senha);
	}
	public Usuario findByLoginObrig(String login) {
		Usuario o = findByLogin(login);
		if (o == null) {
			throw new MessageException("Usuário não encontrado: " + login);
		}
		return o;
	}
	public Usuario findByLogin(String login) {
		if (UString.isEmpty(login)) {
			throw new MessageException("login == null");
		}
		return select(null).login().eq(login).unique();
	}
	@Transactional
	public int add(String nome, String login) {
		return add(nome, login, SENHA_INICIAL).getId();
	}
	public Usuario add(String nome, String login, String senha) {
		Usuario o = findByLogin(login);
		if (o != null) {
			return o;
		}
		o = newO();
		o.setLogin(login);
		o.setSenha("?");
		o.setNome(nome);
		o = save(o);
		o.setSenha(senha);
		o = save(o);
		return o;
		
	}
	
	@Override
	protected void beforeUpdate(Usuario o) {
		validar(o);
		if (!UString.equals(o.getSenha(), o.getOld().getSenha())) {
			o.setSenha(UsuarioService.criptografarSenha(o, o.getSenha()));
		}
	}

	@Transactional
	public Usuario getSistema() {
		Usuario o = findByLogin(UsuarioService.LOGIN_SYSTEM);
		if (o == null) {
			o = newO();
			o.setLogin(UsuarioService.LOGIN_SYSTEM);
			o.setSenha("?");
			o.setNome("Sistema");
			o = insertSemAuditoria(o);
		}
		return o;
	}
	
}
