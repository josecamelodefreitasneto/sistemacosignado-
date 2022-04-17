package br.tcc.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.tcc.model.AuditoriaCampo;
import br.tcc.model.AuditoriaEntidade;
import br.tcc.model.Campo;
import br.tcc.model.Entidade;
import br.tcc.outros.AuditoriaCampoBox;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.EntityModelo;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.AuditoriaCampoSelect;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.date.Data;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.number.Numeric;
import gm.utils.number.UInteger;
import gm.utils.string.ListString;
import gm.utils.string.UString;

@Component
public class AuditoriaCampoService extends ServiceModelo<AuditoriaCampo> {

	@Autowired AuditoriaEntidadeService auditoriaEntidadeService;

	@Autowired CampoService campoService;

	@Override
	public Class<AuditoriaCampo> getClasse() {
		return AuditoriaCampo.class;
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
	protected final void validar(AuditoriaCampo o) {
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

	public void validarUniqueCampoAuditoriaEntidade(AuditoriaCampo o) {
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
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, AuditoriaCampo> func) {
		final AuditoriaCampoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "auditoriaEntidade", select.auditoriaEntidade());
		FiltroConsulta.fk(params, "campo", select.campo());
		FiltroConsulta.string(params, "de", select.de());
		FiltroConsulta.string(params, "para", select.para());
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
		Lst<AuditoriaCampo> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected AuditoriaCampo buscaUnicoObrig(MapSO params) {
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
	protected AuditoriaCampo setOld(AuditoriaCampo o) {
		AuditoriaCampo old = newO();
		old.setAuditoriaEntidade(o.getAuditoriaEntidade());
		old.setCampo(o.getCampo());
		old.setDe(o.getDe());
		old.setPara(o.getPara());
		o.setOld(old);
		return o;
	}

	public AuditoriaCampoSelect<?> select(Boolean excluido) {
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
	protected void setBusca(AuditoriaCampo o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(AuditoriaCampo o) {
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

	@Autowired ApplicationContext context;

	public void registrar(AuditoriaEntidadeBox auditoriaEntidadeBox, int idCampo, String de, String para) {
		if (de == para) return;
		if (UString.equals(de, para)) return;
		auditoriaEntidadeBox.add(idCampo, de, para);
	}
	public void registrar(AuditoriaEntidadeBox auditoriaEntidadeBox, int idCampo, Integer de, Integer para) {
		if (UInteger.equals(de, para)) return;
		this.registrar(auditoriaEntidadeBox, idCampo, toString(de), toString(para));
	}
	private static String toString(Object o) {
		if (o == null) return null;
		final String s = UString.toString(o);
		if (UString.isEmpty(s)) return null;
		return s.trim();
	}
	private static String toString(Boolean b) {
		if (b == null) return null;
		return b ? "Verdadeiro" : "Falso";
	}
	public void registrar(AuditoriaEntidadeBox auditoriaEntidadeBox, int idCampo, EntityModelo de, EntityModelo para) {
		this.registrar(auditoriaEntidadeBox, idCampo, getId(de), getId(para));
	}
	private static Integer getId(EntityModelo o) {
		return o == null ? null : o.getId();
	}
	public void registrar(AuditoriaEntidadeBox auditoriaEntidadeBox, int idCampo, BigDecimal de, BigDecimal para, int precision) {
		if (de == para) return;
		this.registrar(auditoriaEntidadeBox, idCampo, formatNumeric(de, precision), formatNumeric(para, precision));
	}

	public void registrar(AuditoriaEntidadeBox auditoriaEntidadeBox, int idCampo, Calendar de, Calendar para) {
		if (de == para) return;
		this.registrar(auditoriaEntidadeBox, idCampo, formatCalendar(de), formatCalendar(para));
	}

	private String formatNumeric(BigDecimal o, int precision) {
		if (o == null) return null;
		return Numeric.toNumeric(o, precision).toStringPonto();
	}
	private String formatCalendar(Calendar o) {
		if (o == null) return null;
		final Data data = new Data(o);
		if (data.getSegundo() > 0) {
			return data.format_dd_mm_yyyy_hh_mm_ss();
		} else if (data.getHora() == 0 && data.getMinuto() == 0) {
			return data.format_dd_mm_yyyy();
		} else {
			return data.format_dd_mm_yyyy_hh_mm();
		}
	}

	public void registrar(AuditoriaEntidadeBox auditoriaEntidadeBox, int idCampo, Boolean de, Boolean para) {
		if (de == para) return;
		this.registrar(auditoriaEntidadeBox, idCampo, toString(de), toString(para));
	}

	@Override
	public ResultadoConsulta consulta(MapSO map) {
		ResultadoConsulta o = new ResultadoConsulta();
		AuditoriaEntidade auditoriaEntidade = auditoriaEntidadeService.find(map.id());
		o.dados = super.toMapList("auditoriaEntidade", auditoriaEntidade);
		return o;
	}

	@Override
	public MapSO toMap(AuditoriaCampo o, boolean listas) {

		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		
		Campo campo = o.getCampo();
		map.put("campo", campo.getNome());

		if (campo.getTipo().getPrimitivo()) {
			map.put("de", UString.toString(o.getDe()));
			map.put("para", UString.toString(o.getPara()));
		} else {
			map.put("de", getText(campo.getTipo(), o.getDe()));
			map.put("para", getText(campo.getTipo(), o.getPara()));
		}

		return map;
	}
	private String getText(Entidade tipo, String key) {
		if (UString.isEmpty(key)) return null;
		int id = UInteger.toInt(key);
		Class<? extends ServiceModelo<?>> serviceClass = UClass.getClassObrig("br.impl.service."+tipo.getNomeClasse()+"Service");
		ServiceModelo<?> service = context.getBean(serviceClass);
		return service.getText(id);
	}
	public void registrar(AuditoriaEntidade auditoriaEntidade, List<AuditoriaCampoBox> campos) {
		for (AuditoriaCampoBox box : campos) {
			AuditoriaCampo o = newO();
			o.setAuditoriaEntidade(auditoriaEntidade);
			o.setCampo(campoService.find(box.getIdCampo()));
			o.setDe(box.getDe());
			o.setPara(box.getPara());
			o.setBusca("*");
			insertSemAuditoria(o);
		}
	}

}
