package br.tcc.service;

import br.tcc.model.Usuario;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.UsuarioSelect;
import br.tcc.service.AuditoriaCampoService;
import br.tcc.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import gm.utils.comum.Criptografar;

@Component
public class UsuarioService extends ServiceModelo<Usuario> {

	@Autowired ObservacaoService observacaoService;

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<Usuario> getClasse() {
		return Usuario.class;
	}

	@Override
	public MapSO toMap(Usuario o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getLogin() != null) {
			map.put("login", UString.toString(o.getLogin()));
		}
		if (o.getNome() != null) {
			map.put("nome", o.getNome());
		}
		if (o.getSenha() != null) {
			map.put("senha", o.getSenha());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Usuario fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Usuario o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setLogin(mp.getString("login"));
		o.setNome(mp.getString("nome"));
		o.setSenha(mp.getString("senha"));
		return o;
	}

	@Override
	public Usuario newO() {
		Usuario o = new Usuario();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Usuario o) {
		o.setLogin(tratarEmail(o.getLogin()));
		if (o.getLogin() == null) {
			throw new MessageException("O campo Usuário > Login é obrigatório");
		}
		if (UString.length(o.getLogin()) > 50) {
			throw new MessageException("O campo Usuário > Login aceita no máximo 50 caracteres");
		}
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Usuário > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 60) {
			throw new MessageException("O campo Usuário > Nome aceita no máximo 60 caracteres");
		}
		o.setSenha(tratarString(o.getSenha()));
		if (o.getSenha() == null) {
			throw new MessageException("O campo Usuário > Senha é obrigatório");
		}
		if (UString.length(o.getSenha()) > 50) {
			throw new MessageException("O campo Usuário > Senha aceita no máximo 50 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueLogin(o);
			validarUniqueNome(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueLogin(Usuario o) {
		UsuarioSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.login().eq(o.getLogin());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("usuario_login"));
		}
	}

	public void validarUniqueNome(Usuario o) {
		UsuarioSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("usuario_nome"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.Usuario.idEntidade;
	}

	@Override
	protected void saveObservacoes(Usuario o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Usuario> func) {
		final UsuarioSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.email(params, "login", select.login());
		FiltroConsulta.string(params, "nome", select.nome());
		FiltroConsulta.senha(params, "senha", select.senha());
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
		Lst<Usuario> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Usuario buscaUnicoObrig(MapSO params) {
		final UsuarioSelect<?> select = select();
		String login = params.getString("login");
		if (!UString.isEmpty(login)) select.login().eq(login);
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		String senha = params.getString("senha");
		if (!UString.isEmpty(senha)) select.senha().eq(senha);
		Usuario o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (login != null) {
			s += "&& login = '" + login + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		if (senha != null) {
			s += "&& senha = '" + senha + "'";
		}
		s = "Não foi encontrado um Usuario com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return true;
	}

	@Override
	protected Usuario setOld(Usuario o) {
		Usuario old = newO();
		old.setLogin(o.getLogin());
		old.setNome(o.getNome());
		old.setSenha(o.getSenha());
		o.setOld(old);
		return o;
	}

	@Override
	protected boolean houveMudancas(Usuario o) {
		Usuario old = o.getOld();
		if (!UString.equals(o.getLogin(), old.getLogin())) {
			return true;
		}
		if (!UString.equals(o.getNome(), old.getNome())) {
			return true;
		}
		if (!UString.equals(o.getSenha(), old.getSenha())) {
			return true;
		}
		return false;
	}

	@Override
	protected void registrarAuditoriaInsert(Usuario o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUpdate(Usuario o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		Usuario old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.Usuario.login, old.getLogin(), o.getLogin());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.Usuario.nome, old.getNome(), o.getNome());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.Usuario.senha, old.getSenha(), o.getSenha());
	}

	@Override
	protected void registrarAuditoriaDelete(Usuario o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUndelete(Usuario o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaBloqueio(Usuario o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaDesbloqueio(Usuario o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}

	public UsuarioSelect<?> select(Boolean excluido) {
		UsuarioSelect<?> o = new UsuarioSelect<UsuarioSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public UsuarioSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Usuario o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Usuario o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Usuario");
		list.add("nome;login;senha");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("usuario_login", "O campo Login não pode se repetir. Já existe um registro com este valor.");
		CONSTRAINTS_MESSAGES.put("usuario_nome", "O campo Nome não pode se repetir. Já existe um registro com este valor.");
	}

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
