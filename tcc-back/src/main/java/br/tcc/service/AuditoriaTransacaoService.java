package br.tcc.service;

import br.tcc.model.AuditoriaTransacao;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.Log;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.outros.ThreadScope;
import br.tcc.select.AuditoriaTransacaoSelect;
import br.tcc.service.ComandoService;
import br.tcc.service.LoginService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gm.utils.date.Data;
import gm.utils.number.Numeric3;
import gm.utils.number.UInteger;

@Component
public class AuditoriaTransacaoService extends ServiceModelo<AuditoriaTransacao> {

	@Autowired ComandoService comandoService;

	@Autowired LoginService loginService;

	@Override
	public Class<AuditoriaTransacao> getClasse() {
		return AuditoriaTransacao.class;
	}

	@Override
	public MapSO toMap(AuditoriaTransacao o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected AuditoriaTransacao fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final AuditoriaTransacao o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setComando(find(comandoService, mp, "comando"));
		o.setData(mp.get("data"));
		o.setLogin(find(loginService, mp, "login"));
		o.setTempo(mp.getBigDecimal("tempo", 3));
		return o;
	}

	@Override
	public AuditoriaTransacao newO() {
		AuditoriaTransacao o = new AuditoriaTransacao();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(AuditoriaTransacao o) {
		if (o.getComando() == null) {
			throw new MessageException("O campo Auditoria Transação > Comando é obrigatório");
		}
		if (o.getData() == null) {
			throw new MessageException("O campo Auditoria Transação > Data é obrigatório");
		}
		if (o.getLogin() == null) {
			throw new MessageException("O campo Auditoria Transação > Login é obrigatório");
		}
		if (o.getTempo() == null) {
			throw new MessageException("O campo Auditoria Transação > Tempo é obrigatório");
		}
		o.setTempo(validaDecimal(o.getTempo(), 36000000, 3, false));
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueDataLogin(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueDataLogin(AuditoriaTransacao o) {
		AuditoriaTransacaoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.data().eq(o.getData());
		select.login().eq(o.getLogin());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("auditoriatransacao_data_login"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.AuditoriaTransacao.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, AuditoriaTransacao> func) {
		final AuditoriaTransacaoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "comando", select.comando());
		FiltroConsulta.dateTime(params, "data", select.data());
		FiltroConsulta.fk(params, "login", select.login());
		FiltroConsulta.decimal(params, "tempo", select.tempo());
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
		Lst<AuditoriaTransacao> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected AuditoriaTransacao buscaUnicoObrig(MapSO params) {
		final AuditoriaTransacaoSelect<?> select = select();
		Integer comando = getId(params, "comando");
		if (comando != null) {
			select.comando().id().eq(comando);
		}
		Calendar data = params.get("data");
		if (data != null) select.data().eq(data);
		Integer login = getId(params, "login");
		if (login != null) {
			select.login().id().eq(login);
		}
		BigDecimal tempo = params.get("tempo");
		if (tempo != null) select.tempo().eq(tempo);
		AuditoriaTransacao o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (comando != null) {
			s += "&& comando = '" + comando + "'";
		}
		if (data != null) {
			s += "&& data = '" + data + "'";
		}
		if (login != null) {
			s += "&& login = '" + login + "'";
		}
		if (tempo != null) {
			s += "&& tempo = '" + tempo + "'";
		}
		s = "Não foi encontrado um AuditoriaTransacao com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected AuditoriaTransacao setOld(AuditoriaTransacao o) {
		AuditoriaTransacao old = newO();
		old.setComando(o.getComando());
		old.setData(o.getData());
		old.setLogin(o.getLogin());
		old.setTempo(o.getTempo());
		o.setOld(old);
		return o;
	}

	public AuditoriaTransacaoSelect<?> select(Boolean excluido) {
		AuditoriaTransacaoSelect<?> o = new AuditoriaTransacaoSelect<AuditoriaTransacaoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public AuditoriaTransacaoSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(AuditoriaTransacao o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(AuditoriaTransacao o) {
		if (o == null) return null;
		return comandoService.getText(o.getComando());
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("AuditoriaTransacao");
		list.add("login;comando;data;tempo");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("auditoriatransacao_data_login", "A combinação de campos Data + Login não pode se repetir. Já existe um registro com esta combinação.");
	}


	public AuditoriaTransacao criarTransacao() {
		Log.info("AuditoriaTransacaoService.criarTransacao >");
		try {
			final AuditoriaTransacao o = newO();
			o.setLogin(loginService.find(ThreadScope.getLogin()));
			o.setComando(comandoService.find(ThreadScope.getComando()));
			o.setData(new Data(ThreadScope.getDataHora()).getCalendar());
			Integer milisegundos = UInteger.toInt(System.currentTimeMillis() - ThreadScope.getDataHora());
			o.setTempo(new Numeric3(milisegundos).dividido(1000).getValor());
			insertSemAuditoria(o);
			Log.info("AuditoriaTransacaoService.criarTransacao <");
			return o;
		} catch (Exception e) {
			Log.error("AuditoriaTransacaoService.criarTransacao <");
			throw e;
		}
	}

}
