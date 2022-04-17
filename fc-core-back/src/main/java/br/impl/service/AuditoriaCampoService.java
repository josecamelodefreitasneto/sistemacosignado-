package br.impl.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.auto.model.AuditoriaCampo;
import br.auto.model.AuditoriaEntidade;
import br.auto.model.Campo;
import br.auto.model.Entidade;
import br.auto.service.AuditoriaCampoBox;
import br.auto.service.AuditoriaCampoServiceAbstract;
import br.auto.service.AuditoriaEntidadeBox;
import br.impl.outros.EntityModelo;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import gm.utils.classes.UClass;
import gm.utils.date.Data;
import gm.utils.map.MapSO;
import gm.utils.number.Numeric;
import gm.utils.number.UInteger;
import gm.utils.string.UString;

@Component
public class AuditoriaCampoService extends AuditoriaCampoServiceAbstract {
	
	@Autowired ApplicationContext context;
	@Autowired CampoService campoService;

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
		final MapSO map = super.toMap(o, listas);
		
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
