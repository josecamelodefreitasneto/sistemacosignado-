package br.tcc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.tcc.model.EsqueciSenha;
import br.tcc.model.Usuario;
import br.tcc.outros.ApplicationProperties;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ServiceModelo;
import br.tcc.outros.ThreadScope;
import gm.utils.date.Data;
import gm.utils.email.EmailSender;
import gm.utils.email.UEmail;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;

@Component
public class EsqueciSenhaService extends ServiceModelo<EsqueciSenha> {

	@Override
	public Class<EsqueciSenha> getClasse() {
		return EsqueciSenha.class;
	}

	@Override
	public MapSO toMap(EsqueciSenha o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getConfirmarSenha() != null) {
			map.put("confirmarSenha", o.getConfirmarSenha());
		}
		if (o.getNovaSenha() != null) {
			map.put("novaSenha", o.getNovaSenha());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected EsqueciSenha fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final EsqueciSenha o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setConfirmarSenha(mp.getString("confirmarSenha"));
		o.setNovaSenha(mp.getString("novaSenha"));
		return o;
	}

	@Override
	public EsqueciSenha newO() {
		EsqueciSenha o = new EsqueciSenha();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(EsqueciSenha o) {
		o.setConfirmarSenha(tratarString(o.getConfirmarSenha()));
		if (o.getConfirmarSenha() == null) {
			throw new MessageException("O campo Esqueci Senha > Confirmar Senha é obrigatório");
		}
		if (UString.length(o.getConfirmarSenha()) > 50) {
			throw new MessageException("O campo Esqueci Senha > Confirmar Senha aceita no máximo 50 caracteres");
		}
		o.setNovaSenha(tratarString(o.getNovaSenha()));
		if (o.getNovaSenha() == null) {
			throw new MessageException("O campo Esqueci Senha > Nova Senha é obrigatório");
		}
		if (UString.length(o.getNovaSenha()) > 50) {
			throw new MessageException("O campo Esqueci Senha > Nova Senha aceita no máximo 50 caracteres");
		}
		validar2(o);
		validar3(o);
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected EsqueciSenha buscaUnicoObrig(MapSO params) {
		throw new MessageException("?");
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	public String getText(EsqueciSenha o) {
		if (o == null) return null;
		return o.getConfirmarSenha();
	}

	@Override
	public EsqueciSenha findNotObrig(int id) {
		throw new MessageException("?");
	}

	@Autowired UsuarioService usuarioService;
	@Autowired LoginService loginService;
	@Autowired ApplicationProperties applicationProperties;

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
