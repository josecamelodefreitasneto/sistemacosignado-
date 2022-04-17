package br.tcc.service;

import br.tcc.model.Atendente;
import br.tcc.model.Usuario;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDS;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.outros.ThreadScope;
import br.tcc.select.AtendenteSelect;
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
import org.springframework.stereotype.Component;

@Component
public class AtendenteService extends ServiceModelo<Atendente> {

	@Autowired ObservacaoService observacaoService;

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<Atendente> getClasse() {
		return Atendente.class;
	}

	@Override
	public MapSO toMap(Atendente o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getEmail() != null) {
			map.put("email", UString.toString(o.getEmail()));
		}
		if (o.getNome() != null) {
			map.put("nome", UString.toString(o.getNome()));
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Atendente fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Atendente o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setEmail(mp.getString("email"));
		o.setNome(mp.getString("nome"));
		return o;
	}

	@Override
	public Atendente newO() {
		Atendente o = new Atendente();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Atendente o) {
		o.setEmail(tratarEmail(o.getEmail()));
		if (o.getEmail() == null) {
			throw new MessageException("O campo Atendente > E-mail é obrigatório");
		}
		if (UString.length(o.getEmail()) > 50) {
			throw new MessageException("O campo Atendente > E-mail aceita no máximo 50 caracteres");
		}
		o.setNome(tratarNomeProprio(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Atendente > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 50) {
			throw new MessageException("O campo Atendente > Nome aceita no máximo 50 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueEmail(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueEmail(Atendente o) {
		AtendenteSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.email().eq(o.getEmail());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("atendente_email"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDS.Atendente.idEntidade;
	}

	@Override
	protected void saveObservacoes(Atendente o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Atendente> func) {
		final AtendenteSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.email(params, "email", select.email());
		FiltroConsulta.nomeProprio(params, "nome", select.nome());
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
		Lst<Atendente> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Atendente buscaUnicoObrig(MapSO params) {
		final AtendenteSelect<?> select = select();
		String email = params.getString("email");
		if (!UString.isEmpty(email)) select.email().eq(email);
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		Integer usuario = getId(params, "usuario");
		if (usuario != null) {
			select.usuario().id().eq(usuario);
		}
		Atendente o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (email != null) {
			s += "&& email = '" + email + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		if (usuario != null) {
			s += "&& usuario = '" + usuario + "'";
		}
		s = "Não foi encontrado um Atendente com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return true;
	}

	@Override
	protected Atendente setOld(Atendente o) {
		Atendente old = newO();
		old.setEmail(o.getEmail());
		old.setNome(o.getNome());
		old.setUsuario(o.getUsuario());
		o.setOld(old);
		return o;
	}

	@Override
	protected boolean houveMudancas(Atendente o) {
		Atendente old = o.getOld();
		if (!UString.equals(o.getEmail(), old.getEmail())) {
			return true;
		}
		if (!UString.equals(o.getNome(), old.getNome())) {
			return true;
		}
		if (o.getUsuario() != old.getUsuario()) {
			return true;
		}
		return false;
	}

	@Override
	protected void registrarAuditoriaInsert(Atendente o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUpdate(Atendente o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		Atendente old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Atendente.email, old.getEmail(), o.getEmail());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Atendente.nome, old.getNome(), o.getNome());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDS.Atendente.usuario, old.getUsuario(), o.getUsuario());
	}

	@Override
	protected void registrarAuditoriaDelete(Atendente o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUndelete(Atendente o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaBloqueio(Atendente o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaDesbloqueio(Atendente o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}

	public AtendenteSelect<?> select(Boolean excluido) {
		AtendenteSelect<?> o = new AtendenteSelect<AtendenteSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		o.nome().asc();
		return o;
	}

	public AtendenteSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Atendente o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Atendente o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Atendente");
		list.add("nome;email");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("atendente_email", "O campo E-mail não pode se repetir. Já existe um registro com este valor.");
	}

	@Autowired Perfis perfis;
	@Autowired UsuarioService usuarioService;
	@Autowired LoginService loginService;
	@Autowired UsuarioPerfilService usuarioPerfilService;

	@Override
	protected void beforeInsertAfterValidate(Atendente o) {
		int id = usuarioService.add(o.getNome(), o.getEmail());
		Usuario usuario = new Usuario();
		usuario.setId(id);
		o.setUsuario(usuario);
	}

	@Override
	protected void afterUpdate(Atendente o) {
		if (!o.getEmail().contentEquals(o.getOld().getEmail())) {
			Usuario u = usuarioService.find(o.getUsuario().getId());
			u.setLogin(o.getEmail());
			usuarioService.save(u);
		}
	}

	@Override
	protected void afterInsert(Atendente o) {
		usuarioPerfilService.add(o.getUsuario(), perfis.atendente());
	}

	public boolean isAtendente() {
		return getAtendente() != null;
	}
	public Atendente getAtendente() {
		Usuario usuario = loginService.find(ThreadScope.getLogin()).getUsuario();
		return select().usuario().eq(usuario).unique();
	}

	@Override
	protected void afterDelete(Atendente o) {
		usuarioService.delete(o.getUsuario());
	}
}
