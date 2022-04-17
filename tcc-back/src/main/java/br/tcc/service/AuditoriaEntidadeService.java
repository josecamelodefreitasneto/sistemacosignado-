package br.tcc.service;

import br.tcc.model.AuditoriaEntidade;
import br.tcc.model.AuditoriaTransacao;
import br.tcc.model.Entidade;
import br.tcc.model.Login;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.outros.ThreadScope;
import br.tcc.select.AuditoriaEntidadeSelect;
import br.tcc.service.AuditoriaTransacaoService;
import br.tcc.service.EntidadeService;
import br.tcc.service.TipoAuditoriaEntidadeService;
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
public class AuditoriaEntidadeService extends ServiceModelo<AuditoriaEntidade> {

	@Autowired EntidadeService entidadeService;

	@Autowired TipoAuditoriaEntidadeService tipoAuditoriaEntidadeService;

	@Autowired AuditoriaTransacaoService auditoriaTransacaoService;

	@Override
	public Class<AuditoriaEntidade> getClasse() {
		return AuditoriaEntidade.class;
	}

	@Override
	public MapSO toMap(AuditoriaEntidade o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected AuditoriaEntidade fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final AuditoriaEntidade o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setEntidade(find(entidadeService, mp, "entidade"));
		o.setNumeroDaOperacao(mp.getInt("numeroDaOperacao"));
		o.setRegistro(mp.getInt("registro"));
		o.setTipo(findId(tipoAuditoriaEntidadeService, mp, "tipo"));
		o.setTransacao(find(auditoriaTransacaoService, mp, "transacao"));
		return o;
	}

	@Override
	public AuditoriaEntidade newO() {
		AuditoriaEntidade o = new AuditoriaEntidade();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(AuditoriaEntidade o) {
		if (o.getEntidade() == null) {
			throw new MessageException("O campo Auditoria Entidade > Entidade é obrigatório");
		}
		if (o.getNumeroDaOperacao() == null) {
			throw new MessageException("O campo Auditoria Entidade > Número da Operação é obrigatório");
		}
		if (o.getRegistro() == null) {
			throw new MessageException("O campo Auditoria Entidade > Registro é obrigatório");
		}
		if (o.getTipo() == null) {
			throw new MessageException("O campo Auditoria Entidade > Tipo é obrigatório");
		}
		if (o.getTransacao() == null) {
			throw new MessageException("O campo Auditoria Entidade > Transação é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNumeroDaOperacaoTransacaoEntidadeTipoRegistro(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueNumeroDaOperacaoTransacaoEntidadeTipoRegistro(AuditoriaEntidade o) {
		AuditoriaEntidadeSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.numeroDaOperacao().eq(o.getNumeroDaOperacao());
		select.transacao().eq(o.getTransacao());
		select.entidade().eq(o.getEntidade());
		select.tipo().eq(o.getTipo());
		select.registro().eq(o.getRegistro());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("auditoriaentidade_numerodaoperacao_transacao_entidade_tipo_registro"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.AuditoriaEntidade.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, AuditoriaEntidade> func) {
		final AuditoriaEntidadeSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "entidade", select.entidade());
		FiltroConsulta.integer(params, "numeroDaOperacao", select.numeroDaOperacao());
		FiltroConsulta.integer(params, "registro", select.registro());
		FiltroConsulta.fk(params, "tipo", select.tipo());
		FiltroConsulta.fk(params, "transacao", select.transacao());
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
		Lst<AuditoriaEntidade> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected AuditoriaEntidade buscaUnicoObrig(MapSO params) {
		final AuditoriaEntidadeSelect<?> select = select();
		Integer entidade = getId(params, "entidade");
		if (entidade != null) {
			select.entidade().id().eq(entidade);
		}
		Integer numeroDaOperacao = params.get("numeroDaOperacao");
		if (numeroDaOperacao != null) select.numeroDaOperacao().eq(numeroDaOperacao);
		Integer registro = params.get("registro");
		if (registro != null) select.registro().eq(registro);
		Integer tipo = getId(params, "tipo");
		if (tipo != null) {
			select.tipo().eq(tipo);
		}
		Integer transacao = getId(params, "transacao");
		if (transacao != null) {
			select.transacao().id().eq(transacao);
		}
		AuditoriaEntidade o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (entidade != null) {
			s += "&& entidade = '" + entidade + "'";
		}
		if (numeroDaOperacao != null) {
			s += "&& numeroDaOperacao = '" + numeroDaOperacao + "'";
		}
		if (registro != null) {
			s += "&& registro = '" + registro + "'";
		}
		if (tipo != null) {
			s += "&& tipo = '" + tipo + "'";
		}
		if (transacao != null) {
			s += "&& transacao = '" + transacao + "'";
		}
		s = "Não foi encontrado um AuditoriaEntidade com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected AuditoriaEntidade setOld(AuditoriaEntidade o) {
		AuditoriaEntidade old = newO();
		old.setEntidade(o.getEntidade());
		old.setNumeroDaOperacao(o.getNumeroDaOperacao());
		old.setRegistro(o.getRegistro());
		old.setTipo(o.getTipo());
		old.setTransacao(o.getTransacao());
		o.setOld(old);
		return o;
	}

	public static boolean tipoInclusao(AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeService.INCLUSAO;
	}

	public boolean tipoInclusao(int id) {
		return tipoInclusao(find(id));
	}

	public static boolean tipoAlteracao(AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeService.ALTERACAO;
	}

	public boolean tipoAlteracao(int id) {
		return tipoAlteracao(find(id));
	}

	public static boolean tipoExclusao(AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeService.EXCLUSAO;
	}

	public boolean tipoExclusao(int id) {
		return tipoExclusao(find(id));
	}

	public static boolean tipoExecucao(AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeService.EXECUCAO;
	}

	public boolean tipoExecucao(int id) {
		return tipoExecucao(find(id));
	}

	public static boolean tipoRecuperacao(AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeService.RECUPERACAO;
	}

	public boolean tipoRecuperacao(int id) {
		return tipoRecuperacao(find(id));
	}

	public static boolean tipoBloqueio(AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeService.BLOQUEIO;
	}

	public boolean tipoBloqueio(int id) {
		return tipoBloqueio(find(id));
	}

	public static boolean tipoDesbloqueio(AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeService.DESBLOQUEIO;
	}

	public boolean tipoDesbloqueio(int id) {
		return tipoDesbloqueio(find(id));
	}

	public AuditoriaEntidadeSelect<?> select(Boolean excluido) {
		AuditoriaEntidadeSelect<?> o = new AuditoriaEntidadeSelect<AuditoriaEntidadeSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public AuditoriaEntidadeSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(AuditoriaEntidade o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(AuditoriaEntidade o) {
		if (o == null) return null;
		return entidadeService.getText(o.getEntidade());
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("AuditoriaEntidade");
		list.add("transacao;entidade;tipo;registro;numeroDaOperacao");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("auditoriaentidade_numerodaoperacao_transacao_entidade_tipo_registro", "A combinação de campos Número da Operação + Transação + Entidade + Tipo + Registro não pode se repetir. Já existe um registro com esta combinação.");
	}

	@Autowired TipoAuditoriaEntidadeService tipoLogEntidadeService;
	@Autowired AuditoriaCampoService auditoriaCampoService;
	@Autowired CampoService campoService;

	@Transactional
	public void registrar() {

		List<AuditoriaEntidadeBox> auditorias = ThreadScope.getAuditorias();

		if (auditorias.isEmpty()) {
			return;
		}

		auditorias.removeIf(o -> o.getTipo() == TipoAuditoriaEntidadeService.ALTERACAO && o.getCampos().isEmpty());

		if (auditorias.isEmpty()) {
			return;
		}

		AuditoriaTransacao transacao = auditoriaTransacaoService.criarTransacao();

		MapSO contadorOperacoes = new MapSO();

		for (AuditoriaEntidadeBox box : auditorias) {
			AuditoriaEntidade o = newO();
			o.setTransacao(transacao);
			o.setEntidade(entidadeService.find(box.getEntidade()));
			o.setRegistro(box.getRegistro());
			o.setTipo(box.getTipo());
			String key = box.getEntidade() + "-" + box.getTipo() + "-" + box.getRegistro();
			Integer numeroDaOperacao = contadorOperacoes.get(key);
			if (numeroDaOperacao == null) {
				numeroDaOperacao = 1;
			} else {
				numeroDaOperacao++;
			}
			contadorOperacoes.put(key, numeroDaOperacao);
			o.setNumeroDaOperacao(numeroDaOperacao);
			o = insertSemAuditoria(o);
			auditoriaCampoService.registrar(o, box.getCampos());
		}

		ThreadScope.clear();

	}

	public Lst<AuditoriaEntidade> list(Entidade entidade, int registro) {
		return select(null).entidade().eq(entidade).registro().eq(registro).list();
	}

	@Transactional
	public int getLoginInsert(int entidade, int registro) {
		AuditoriaEntidadeSelect<?> select = select();
		select.entidade().id().eq(entidade);
		select.registro().eq(registro);
		select.tipo().eq(TipoAuditoriaEntidadeService.INCLUSAO);
		AuditoriaEntidade auditoriaEntidade = select.unique();
		Login login = auditoriaEntidade.getTransacao().getLogin();
		return login.getId();
	}

}
