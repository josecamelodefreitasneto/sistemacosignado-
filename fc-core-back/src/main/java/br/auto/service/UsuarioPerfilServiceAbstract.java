package br.auto.service;

import br.auto.model.UsuarioPerfil;
import br.auto.select.UsuarioPerfilSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.AuditoriaCampoService;
import br.impl.service.ObservacaoService;
import br.impl.service.PerfilService;
import br.impl.service.UsuarioService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class UsuarioPerfilServiceAbstract extends ServiceModelo<UsuarioPerfil> {

	@Autowired
	protected PerfilService perfilService;

	@Autowired
	protected UsuarioService usuarioService;

	@Autowired
	protected ObservacaoService observacaoService;

	@Autowired
	protected AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<UsuarioPerfil> getClasse() {
		return UsuarioPerfil.class;
	}
	@Override
	public MapSO toMap(final UsuarioPerfil o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
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
	protected final void validar(final UsuarioPerfil o) {
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
	public void validarUniquePerfilUsuario(final UsuarioPerfil o) {
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
	protected void saveObservacoes(final UsuarioPerfil o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, UsuarioPerfil> func) {
		final UsuarioPerfilSelect<?> select = select();
		Integer pagina = params.getInt("pagina");
		String busca = params.getString("busca");
		if (!UString.isEmpty(busca)) select.busca().like(UString.toCampoBusca(busca));
		ResultadoConsulta result = new ResultadoConsulta();
		if (pagina == null) {
			result.registros = select.count();
		} else {
			select.page(pagina);
		}
		select.limit(30);
		Lst<UsuarioPerfil> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected UsuarioPerfil buscaUnicoObrig(final MapSO params) {
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
	protected UsuarioPerfil setOld(final UsuarioPerfil o) {
		UsuarioPerfil old = newO();
		old.setPerfil(o.getPerfil());
		old.setUsuario(o.getUsuario());
		o.setOld(old);
		return o;
	}
	@Override
	protected boolean houveMudancas(final UsuarioPerfil o) {
		UsuarioPerfil old = o.getOld();
		if (o.getPerfil() != old.getPerfil()) return true;
		if (o.getUsuario() != old.getUsuario()) return true;
		return false;
	}
	@Override
	protected void registrarAuditoriaInsert(final UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUpdate(final UsuarioPerfil o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		UsuarioPerfil old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.UsuarioPerfil.perfil, old.getPerfil(), o.getPerfil());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.UsuarioPerfil.usuario, old.getUsuario(), o.getUsuario());
	}
	@Override
	protected void registrarAuditoriaDelete(final UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUndelete(final UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaBloqueio(final UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaDesbloqueio(final UsuarioPerfil o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}
	public UsuarioPerfilSelect<?> select(final Boolean excluido) {
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
	protected void setBusca(final UsuarioPerfil o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final UsuarioPerfil o) {
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
}
