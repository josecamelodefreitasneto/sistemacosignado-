package br.auto.service;

import br.auto.model.AuditoriaCampo;
import br.auto.select.AuditoriaCampoSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.AuditoriaEntidadeService;
import br.impl.service.CampoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AuditoriaCampoServiceAbstract extends ServiceModelo<AuditoriaCampo> {

	@Autowired
	protected AuditoriaEntidadeService auditoriaEntidadeService;

	@Autowired
	protected CampoService campoService;

	@Override
	public Class<AuditoriaCampo> getClasse() {
		return AuditoriaCampo.class;
	}
	@Override
	public MapSO toMap(final AuditoriaCampo o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	@Override
	protected AuditoriaCampo fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final AuditoriaCampo o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setAuditoriaEntidade(find(auditoriaEntidadeService, mp, "auditoriaEntidade"));
		o.setCampo(find(campoService, mp, "campo"));
		o.setDe(mp.getString("de"));
		o.setPara(mp.getString("para"));
		return o;
	}
	@Override
	public AuditoriaCampo newO() {
		AuditoriaCampo o = new AuditoriaCampo();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final AuditoriaCampo o) {
		if (o.getAuditoriaEntidade() == null) {
			throw new MessageException("O campo Auditoria Campo > Auditoria Entidade é obrigatório");
		}
		if (o.getCampo() == null) {
			throw new MessageException("O campo Auditoria Campo > Campo é obrigatório");
		}
		o.setDe(tratarString(o.getDe()));
		if (UString.length(o.getDe()) > 8000) {
			throw new MessageException("O campo Auditoria Campo > De aceita no máximo 8000 caracteres");
		}
		o.setPara(tratarString(o.getPara()));
		if (UString.length(o.getPara()) > 8000) {
			throw new MessageException("O campo Auditoria Campo > Para aceita no máximo 8000 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueCampoAuditoriaEntidade(o);
		}
		validar2(o);
		validar3(o);
	}
	public void validarUniqueCampoAuditoriaEntidade(final AuditoriaCampo o) {
		AuditoriaCampoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.campo().eq(o.getCampo());
		select.auditoriaEntidade().eq(o.getAuditoriaEntidade());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("auditoriacampo_campo_auditoriaentidade"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.AuditoriaCampo.idEntidade;
	}
	@Override
	public boolean utilizaObservacoes() {
		return false;
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, AuditoriaCampo> func) {
		final AuditoriaCampoSelect<?> select = select();
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
		Lst<AuditoriaCampo> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected AuditoriaCampo buscaUnicoObrig(final MapSO params) {
		final AuditoriaCampoSelect<?> select = select();
		Integer auditoriaEntidade = getId(params, "auditoriaEntidade");
		if (auditoriaEntidade != null) {
			select.auditoriaEntidade().id().eq(auditoriaEntidade);
		}
		Integer campo = getId(params, "campo");
		if (campo != null) {
			select.campo().id().eq(campo);
		}
		String de = params.getString("de");
		if (!UString.isEmpty(de)) select.de().eq(de);
		String para = params.getString("para");
		if (!UString.isEmpty(para)) select.para().eq(para);
		AuditoriaCampo o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (auditoriaEntidade != null) {
			s += "&& auditoriaEntidade = '" + auditoriaEntidade + "'";
		}
		if (campo != null) {
			s += "&& campo = '" + campo + "'";
		}
		if (de != null) {
			s += "&& de = '" + de + "'";
		}
		if (para != null) {
			s += "&& para = '" + para + "'";
		}
		s = "Não foi encontrado um AuditoriaCampo com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return false;
	}
	@Override
	protected AuditoriaCampo setOld(final AuditoriaCampo o) {
		AuditoriaCampo old = newO();
		old.setAuditoriaEntidade(o.getAuditoriaEntidade());
		old.setCampo(o.getCampo());
		old.setDe(o.getDe());
		old.setPara(o.getPara());
		o.setOld(old);
		return o;
	}
	public AuditoriaCampoSelect<?> select(final Boolean excluido) {
		AuditoriaCampoSelect<?> o = new AuditoriaCampoSelect<AuditoriaCampoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public AuditoriaCampoSelect<?> select() {
		return select(false);
	}
	@Override
	protected void setBusca(final AuditoriaCampo o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final AuditoriaCampo o) {
		if (o == null) return null;
		return auditoriaEntidadeService.getText(o.getAuditoriaEntidade());
	}
	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("AuditoriaCampo");
		list.add("auditoriaEntidade;campo;de;para");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("auditoriacampo_campo_auditoriaentidade", "A combinação de campos Campo + Auditoria Entidade não pode se repetir. Já existe um registro com esta combinação.");
	}
}
