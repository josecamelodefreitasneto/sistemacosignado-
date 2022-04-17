package br.tcc.service;

import br.tcc.model.Perfil;
import br.tcc.model.Usuario;
import br.tcc.model.UsuarioPerfil;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.UsuarioPerfilSelect;
import br.tcc.service.AuditoriaCampoService;
import br.tcc.service.ObservacaoService;
import br.tcc.service.PerfilService;
import br.tcc.service.UsuarioService;
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

@Component
public class UsuarioPerfilService extends ServiceModelo<UsuarioPerfil> {

	@Autowired PerfilService perfilService;

	@Autowired UsuarioService usuarioService;

	@Autowired ObservacaoService observacaoService;

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<UsuarioPerfil> getClasse() {
		return UsuarioPerfil.class;
	}

	@Override
	public MapSO toMap(UsuarioPerfil o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getPerfil() != null) {
			map.put("perfil", perfilService.toIdText(o.getPerfil()));
		}
		if (o.getUsuario() != null) {
			map.put("usuario", usuarioService.toIdText(o.getUsuario()));
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected UsuarioPerfil fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final UsuarioPerfil o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setPerfil(find(perfilService, mp, "perfil"));
		o.setUsuario(find(usuarioService, mp, "usuario"));
		return o;
	}

	@Override
	public UsuarioPerfil newO() {
		UsuarioPerfil o = new UsuarioPerfil();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(UsuarioPerfil o) {
		if (o.getPerfil() == null) {
			throw new MessageException("O campo Usuário Perfil > Perfil é obrigatório");
		}
		if (o.getUsuario() == null) {
			throw new MessageException("O campo Usuário Perfil > Usuário é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniquePerfilUsuario(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniquePerfilUsuario(UsuarioPerfil o) {
		UsuarioPerfilSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.perfil().eq(o.getPerfil());
		select.usuario().eq(o.getUsuario());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("usuarioperfil_perfil_usuario"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.UsuarioPerfil.idEntidade;
	}

	@Override
	protected void saveObservacoes(UsuarioPerfil o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, UsuarioPerfil> func) {
		final UsuarioPerfilSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "perfil", select.perfil());
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
		Lst<UsuarioPerfil> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected UsuarioPerfil buscaUnicoObrig(MapSO params) {
		final UsuarioPerfilSelect<?> select = select();
		Integer perfil = getId(params, "perfil");
		if (perfil != null) {
			select.perfil().id().eq(perfil);
		}
		Integer usuario = getId(params, "usuario");
		if (usuario != null) {
			select.usuario().id().eq(usuario);
		}
		UsuarioPerfil o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (perfil != null) {
			s += "&& perfil = '" + perfil + "'";
		}
		if (usuario != null) {
			s += "&& usuario = '" + usuario + "'";
		}
		s = "Não foi encontrado um UsuarioPerfil com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return true;
	}

	@Override
	protected UsuarioPerfil setOld(UsuarioPerfil o) {
		UsuarioPerfil old = newO();
		old.setPerfil(o.getPerfil());
		old.setUsuario(o.getUsuario());
		o.setOld(old);
		return o;
	}

	@Override
	protected boolean houveMudancas(UsuarioPerfil o) {
		UsuarioPerfil old = o.getOld();
		if (o.getPerfil() != old.getPerfil()) {
			return true;
		}
		if (o.getUsuario() != old.getUsuario()) {
			return true;
		}
		return false;
	}

	@Override
	protected void registrarAuditoriaInsert(UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUpdate(UsuarioPerfil o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		UsuarioPerfil old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.UsuarioPerfil.perfil, old.getPerfil(), o.getPerfil());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.UsuarioPerfil.usuario, old.getUsuario(), o.getUsuario());
	}

	@Override
	protected void registrarAuditoriaDelete(UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUndelete(UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaBloqueio(UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaDesbloqueio(UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}

	public UsuarioPerfilSelect<?> select(Boolean excluido) {
		UsuarioPerfilSelect<?> o = new UsuarioPerfilSelect<UsuarioPerfilSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public UsuarioPerfilSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(UsuarioPerfil o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(UsuarioPerfil o) {
		if (o == null) return null;
		return perfilService.getText(o.getPerfil());
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("UsuarioPerfil");
		list.add("usuario;perfil");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("usuarioperfil_perfil_usuario", "A combinação de campos Perfil + Usuário não pode se repetir. Já existe um registro com esta combinação.");
	}

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
