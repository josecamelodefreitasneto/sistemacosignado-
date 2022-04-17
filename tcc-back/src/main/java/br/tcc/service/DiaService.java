package br.tcc.service;

import br.tcc.model.AnoMes;
import br.tcc.model.Dia;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.DiaSelect;
import br.tcc.service.AnoMesService;
import gm.utils.comum.Lst;
import gm.utils.comum.UBoolean;
import gm.utils.date.Data;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.number.UInteger;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class DiaService extends ServiceModelo<Dia> {

	@Autowired AnoMesService anoMesService;

	@Override
	public Class<Dia> getClasse() {
		return Dia.class;
	}

	@Override
	public MapSO toMap(Dia o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Dia fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Dia o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setAnoMes(find(anoMesService, mp, "anoMes"));
		o.setData(Data.getCalendar(Data.unformat("[dd]/[mm]/[yyyy]", mp.get("data"))));
		o.setDiaDaSemana(mp.getInt("diaDaSemana"));
		o.setFeriado(UBoolean.toBoolean(mp.get("feriado")));
		o.setNome(mp.getString("nome"));
		return o;
	}

	@Override
	public Dia newO() {
		Dia o = new Dia();
		o.setDiaUtil(false);
		o.setFeriado(false);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Dia o) {
		if (o.getData() == null) {
			throw new MessageException("O campo Dia > Data é obrigatório");
		}
		if (o.getDiaDaSemana() == null) {
			throw new MessageException("O campo Dia > Dia da Semana é obrigatório");
		}
		if (o.getFeriado() == null) {
			throw new MessageException("O campo Dia > Feriado é obrigatório");
		}
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Dia > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 50) {
			throw new MessageException("O campo Dia > Nome aceita no máximo 50 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueData(o);
			validarUniqueNome(o);
		}
		validar2(o);
		if (o.getDiaUtil() == null) {
			throw new MessageException("O campo Dia > Dia Útil é obrigatório");
		}
		o.setDiaUtil(UInteger.ne(o.getDiaDaSemana(), 1) && UInteger.ne(o.getDiaDaSemana(), 7) && ! UBoolean.isTrue(o.getFeriado()));
		validar3(o);
	}

	public void validarUniqueData(Dia o) {
		DiaSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.data().eq(o.getData());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("dia_data"));
		}
	}

	public void validarUniqueNome(Dia o) {
		DiaSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("dia_nome"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.Dia.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Dia> func) {
		final DiaSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "anoMes", select.anoMes());
		FiltroConsulta.date(params, "data", select.data());
		FiltroConsulta.integer(params, "diaDaSemana", select.diaDaSemana());
		FiltroConsulta.bool(params, "diaUtil", select.diaUtil());
		FiltroConsulta.bool(params, "feriado", select.feriado());
		FiltroConsulta.string(params, "nome", select.nome());
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
		Lst<Dia> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Dia buscaUnicoObrig(MapSO params) {
		final DiaSelect<?> select = select();
		Integer anoMes = getId(params, "anoMes");
		if (anoMes != null) {
			select.anoMes().id().eq(anoMes);
		}
		Calendar data = params.get("data");
		if (data != null) select.data().eq(data);
		Integer diaDaSemana = params.get("diaDaSemana");
		if (diaDaSemana != null) select.diaDaSemana().eq(diaDaSemana);
		Boolean diaUtil = params.get("diaUtil");
		if (diaUtil != null) select.diaUtil().eq(diaUtil);
		Boolean feriado = params.get("feriado");
		if (feriado != null) select.feriado().eq(feriado);
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		Dia o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (anoMes != null) {
			s += "&& anoMes = '" + anoMes + "'";
		}
		if (data != null) {
			s += "&& data = '" + data + "'";
		}
		if (diaDaSemana != null) {
			s += "&& diaDaSemana = '" + diaDaSemana + "'";
		}
		if (diaUtil != null) {
			s += "&& diaUtil = '" + diaUtil + "'";
		}
		if (feriado != null) {
			s += "&& feriado = '" + feriado + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		s = "Não foi encontrado um Dia com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected Dia setOld(Dia o) {
		Dia old = newO();
		old.setAnoMes(o.getAnoMes());
		old.setData(o.getData());
		old.setDiaDaSemana(o.getDiaDaSemana());
		old.setDiaUtil(o.getDiaUtil());
		old.setFeriado(o.getFeriado());
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}

	public DiaSelect<?> select(Boolean excluido) {
		DiaSelect<?> o = new DiaSelect<DiaSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public DiaSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Dia o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Dia o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Dia");
		list.add("data;nome;diaDaSemana;anoMes;feriado");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("dia_data", "O campo Data não pode se repetir. Já existe um registro com este valor.");
		CONSTRAINTS_MESSAGES.put("dia_nome", "O campo Nome não pode se repetir. Já existe um registro com este valor.");
	}


	@Transactional
	public void atualizar() {
		Data data = new Data(2017, 1, 1);
		while (!data.isHoje()) {
			get(data);
			data.add();
		}
		Data hoje = Data.hoje();
		while (data.getMes() == hoje.getMes()) {
			get(data);
			data.add();
		}
	}

	public Dia get(Data data) {
		final int id = UInteger.toInt(data.format("[yyyy][mm][dd]"));
		Dia o = findNotObrig(id);
		if (o == null) {
			o = newO();
			o.setId(id);
			o.setData(data.getCalendar());
			o.setNome(data.format("[dd]/[mm]/[yyyy]"));
			o.setAnoMes(anoMesService.get(data.getAno(), data.getMes()));
			o.setDiaDaSemana(data.getDiaSemana());
			o.setFeriado(data.feriadoRecessoNaoFuncionamento());
			o.setDiaUtil(data.diaUtil());
			insertSemAuditoria(o);
		}
		return o;
	}

	public Dia getTerceiroDiaUtil(AnoMes anoMes) {
		return select(null).anoMes().eq(anoMes).diaUtil().eq(true).id().asc().skip(2).first();
	}

	public Dia getProximoDiaUtil(AnoMes anoMes) {
		return select(null).anoMes().eq(anoMes).diaUtil().eq(true).data().maiorOuIgual(Data.hoje()).id().asc().first();
	}

	public static boolean ehMaior(Dia a, Dia b) {
		if (a == null) {
			return false;
		} else if (b == null) {
			return true;
		} else {
			return a.getId() > b.getId();
		}
	}

}
