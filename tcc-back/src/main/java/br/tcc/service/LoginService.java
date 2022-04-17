package br.tcc.service;

import br.tcc.model.Comando;
import br.tcc.model.Login;
import br.tcc.model.Usuario;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.LoginSelect;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import gm.utils.comum.Aleatorio;
import gm.utils.date.Data;

@Component
public class LoginService extends ServiceModelo<Login> {

	@Override
	public Class<Login> getClasse() {
		return Login.class;
	}

	@Override
	public MapSO toMap(Login o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Login fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Login o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		return o;
	}

	@Override
	public Login newO() {
		Login o = new Login();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Login o) {
		validar2(o);
		if (o.getData() == null) {
			throw new MessageException("O campo Login > Data é obrigatório");
		}
		o.setToken(tratarString(o.getToken()));
		if (o.getToken() == null) {
			throw new MessageException("O campo Login > Token é obrigatório");
		}
		if (UString.length(o.getToken()) > 50) {
			throw new MessageException("O campo Login > Token aceita no máximo 50 caracteres");
		}
		if (o.getUsuario() == null) {
			throw new MessageException("O campo Login > Usuário é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueToken(o);
			validarUniqueDataUsuario(o);
		}
		validar3(o);
	}

	public void validarUniqueToken(Login o) {
		LoginSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.token().eq(o.getToken());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("login_token"));
		}
	}

	public void validarUniqueDataUsuario(Login o) {
		LoginSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.data().eq(o.getData());
		select.usuario().eq(o.getUsuario());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("login_data_usuario"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.Login.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Login> func) {
		final LoginSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.dateTime(params, "data", select.data());
		FiltroConsulta.string(params, "token", select.token());
		FiltroConsulta.fk(params, "usuario", select.usuario());
		FiltroConsulta.bool(params, "excluido", select.excluido());
		FiltroConsulta.bool(params, "registroBloqueado", select.registroBloqueado());
		ResultadoConsulta result = new ResultadoConsulta();
		if (pagina == null) {
			result.pagina = 1;
			result.registros = select.count();
			result.paginas = result.registros / 30;
			if (result.registros > result.paginas * 30) {
				result.paginas++;
			}
		} else {
			select.page(pagina);
			result.pagina = pagina;
		}
		select.limit(30);
		Lst<Login> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Login buscaUnicoObrig(MapSO params) {
		final LoginSelect<?> select = select();
		Calendar data = params.get("data");
		if (data != null) select.data().eq(data);
		String token = params.getString("token");
		if (!UString.isEmpty(token)) select.token().eq(token);
		Integer usuario = getId(params, "usuario");
		if (usuario != null) {
			select.usuario().id().eq(usuario);
		}
		Login o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (data != null) {
			s += "&& data = '" + data + "'";
		}
		if (token != null) {
			s += "&& token = '" + token + "'";
		}
		if (usuario != null) {
			s += "&& usuario = '" + usuario + "'";
		}
		s = "Não foi encontrado um Login com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected Login setOld(Login o) {
		Login old = newO();
		old.setData(o.getData());
		old.setToken(o.getToken());
		old.setUsuario(o.getUsuario());
		o.setOld(old);
		return o;
	}

	public LoginSelect<?> select(Boolean excluido) {
		LoginSelect<?> o = new LoginSelect<LoginSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public LoginSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Login o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Login o) {
		if (o == null) return null;
		return o.getToken();
	}

	@Override
	public ListString getTemplateImportacao() {
		return null;
	}

	static {
		CONSTRAINTS_MESSAGES.put("login_token", "O campo Token não pode se repetir. Já existe um registro com este valor.");
		CONSTRAINTS_MESSAGES.put("login_data_usuario", "A combinação de campos Data + Usuário não pode se repetir. Já existe um registro com esta combinação.");
	}

	@Autowired UsuarioService usuarioService;

	private MessageException usuarioOuSenhaInvalida() {
		return new MessageException("Usuario ou senha invalida!");
	}

	@Transactional
	public Login exec(Usuario usuario) {
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
	public Login exec(String login, final String senha) {
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

	public void valida(Login login, final Comando comando) {
		//TODO colocar veriricacoes de permisoes aqui
	}

	public Login valida(String token, final Comando comando) {
		final Login login = select(null).token().eq(token).uniqueObrig(() -> new MessageException("login nao encontrado: " + token));
		this.valida(login, comando);
		return login;
	}

}
