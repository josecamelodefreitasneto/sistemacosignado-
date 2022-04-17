package br.impl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.auto.model.Comando;
import br.auto.model.Login;
import br.auto.model.Usuario;
import br.auto.service.LoginServiceAbstract;
import gm.utils.comum.Aleatorio;
import gm.utils.date.Data;
import gm.utils.exception.MessageException;
import gm.utils.string.UString;

@Component
public class LoginService extends LoginServiceAbstract {
	
	@Autowired private UsuarioService usuarioService;

	private MessageException usuarioOuSenhaInvalida() {
		return new MessageException("Usuario ou senha invalida!");
	}
	
	@Transactional
	public Login exec(final Usuario usuario) {
		final Login o = newO();
		o.setUsuario(usuario);
		do {
			o.setToken(Aleatorio.getString(50));
		} while (select(null).token().eq(o.getToken()).exists());
		o.setData(Data.nowCalendar());
		insertSemAuditoria(o);
		return o;

	}
	
	@Transactional
	public Login logaSistema() {
		return exec(usuarioService.getSistema());
	}

	@Transactional
	public Login exec(final String login, final String senha) {
		Usuario usuario = usuarioService.findByLogin(login);
		if (usuario == null) {
			throw usuarioOuSenhaInvalida();
		}
		String senhaCriptografada = UsuarioService.criptografarSenha(usuario, senha);
		if (!UString.equals(senhaCriptografada, usuario.getSenha())) {
			throw usuarioOuSenhaInvalida();
		}
		return exec(usuario);
	}
	
	public void valida(final Login login, final Comando comando) {
		//TODO colocar veriricacoes de permisoes aqui
	}
	
	public Login valida(final String token, final Comando comando) {
		final Login login = select(null).token().eq(token).uniqueObrig(() -> new MessageException("login nao encontrado: " + token));
		this.valida(login, comando);
		return login;
	}
	
}
