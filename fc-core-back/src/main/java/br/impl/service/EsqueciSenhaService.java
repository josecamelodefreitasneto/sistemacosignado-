package br.impl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.auto.model.EsqueciSenha;
import br.auto.model.Usuario;
import br.auto.service.EsqueciSenhaServiceAbstract;
import br.auto.service.IDSDefault;
import br.impl.outros.ApplicationProperties;
import br.impl.outros.ThreadScope;
import gm.utils.date.Data;
import gm.utils.email.EmailSender;
import gm.utils.email.UEmail;
import gm.utils.exception.MessageException;
import gm.utils.string.UString;

@Component
public class EsqueciSenhaService extends EsqueciSenhaServiceAbstract {
	
	@Autowired UsuarioService usuarioService;
	@Autowired LoginService loginService;
	@Autowired ApplicationProperties applicationProperties;

	@Override
	protected void persistInsert(EsqueciSenha o) {
		Usuario u = usuarioService.find(loginService.find(ThreadScope.getLogin()).getUsuario().getId());
		validarSenhas(o);
		u.setSenha(o.getNovaSenha());
		usuarioService.save(u);
	}

	private void validarSenhas(EsqueciSenha o) {
		String a = o.getNovaSenha();
		String b = o.getConfirmarSenha();
		if (!UString.equals(a, b)) {
			throw new MessageException("Senhas não conferem!!");
		}
	}

	private String getCodigoRecuperacao(Usuario u) {
		String data = Data.hoje().format_dd_mm_yyyy();
		return UsuarioService.criptografarSenha(u, "recuperacao-"+data);
	}

	private Usuario getUsuario(String login) {
		Usuario u = usuarioService.findByLogin(login);
		if (u == null) {
			throw new MessageException("Usuário inválido!");
		}
		return u;
	}
	
	public void enviarEmail(String login) {
		
		if (!UEmail.isValid(login)) {
			throw new MessageException("E-email inválido: " + login);
		}
		
		try {

			Usuario u = getUsuario(login);
			String codigoRecuperacao = getCodigoRecuperacao(u);
			
			EmailSender mail = new EmailSender(applicationProperties.getEmailConta(), applicationProperties.getEmailSenha());

			mail.setAssunto("Código de recuperação");
			mail.setNomeRemetente("Sistem");
			mail.setTexto("Este é o código de recuperação de senha: " + codigoRecuperacao);
			mail.addDestinatario(login);
			mail.send();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.Usuario.idEntidade;
	}

	public void confirmarCodigo(Usuario usuario, String codigo) {
		String esperado = getCodigoRecuperacao(usuario);
		if (!UString.equals(codigo, esperado)) {
			throw new MessageException("Código inválido!");
		}
	}

	
}
