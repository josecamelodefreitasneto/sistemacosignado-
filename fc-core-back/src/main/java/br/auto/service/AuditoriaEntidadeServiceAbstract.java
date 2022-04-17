package br.auto.service;

import br.auto.model.AuditoriaEntidade;
import br.auto.select.AuditoriaEntidadeSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.AuditoriaTransacaoService;
import br.impl.service.EntidadeService;
import br.impl.service.TipoAuditoriaEntidadeService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AuditoriaEntidadeServiceAbstract extends ServiceModelo<AuditoriaEntidade> {

	@Autowired
	protected EntidadeService entidadeService;

	@Autowired
	protected TipoAuditoriaEntidadeService tipoAuditoriaEntidadeService;

	@Autowired
	protected AuditoriaTransacaoService auditoriaTransacaoService;

	@Override
	public Class<AuditoriaEntidade> getClasse() {
		return AuditoriaEntidade.class;
	}
	@Override
	public MapSO toMap(final AuditoriaEntidade o, final boolean listas) {
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
	protected final void validar(final AuditoriaEntidade o) {
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
	public void validarUniqueNumeroDaOperacaoTransacaoEntidadeTipoRegistro(final AuditoriaEntidade o) {
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
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, AuditoriaEntidade> func) {
		final AuditoriaEntidadeSelect<?> select = select();
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
		Lst<AuditoriaEntidade> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected AuditoriaEntidade buscaUnicoObrig(final MapSO params) {
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
	protected AuditoriaEntidade setOld(final AuditoriaEntidade o) {
		AuditoriaEntidade old = newO();
		old.setEntidade(o.getEntidade());
		old.setNumeroDaOperacao(o.getNumeroDaOperacao());
		old.setRegistro(o.getRegistro());
		old.setTipo(o.getTipo());
		old.setTransacao(o.getTransacao());
		o.setOld(old);
		return o;
	}
	public static boolean tipoInclusao(final AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeServiceAbstract.INCLUSAO;
	}
	public boolean tipoInclusao(final int id) {
		return tipoInclusao(find(id));
	}
	public static boolean tipoAlteracao(final AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeServiceAbstract.ALTERACAO;
	}
	public boolean tipoAlteracao(final int id) {
		return tipoAlteracao(find(id));
	}
	public static boolean tipoExclusao(final AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeServiceAbstract.EXCLUSAO;
	}
	public boolean tipoExclusao(final int id) {
		return tipoExclusao(find(id));
	}
	public static boolean tipoExecucao(final AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeServiceAbstract.EXECUCAO;
	}
	public boolean tipoExecucao(final int id) {
		return tipoExecucao(find(id));
	}
	public static boolean tipoRecuperacao(final AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeServiceAbstract.RECUPERACAO;
	}
	public boolean tipoRecuperacao(final int id) {
		return tipoRecuperacao(find(id));
	}
	public static boolean tipoBloqueio(final AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeServiceAbstract.BLOQUEIO;
	}
	public boolean tipoBloqueio(final int id) {
		return tipoBloqueio(find(id));
	}
	public static boolean tipoDesbloqueio(final AuditoriaEntidade o) {
		return o.getTipo() == TipoAuditoriaEntidadeServiceAbstract.DESBLOQUEIO;
	}
	public boolean tipoDesbloqueio(final int id) {
		return tipoDesbloqueio(find(id));
	}
	public AuditoriaEntidadeSelect<?> select(final Boolean excluido) {
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
	protected void setBusca(final AuditoriaEntidade o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final AuditoriaEntidade o) {
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
}
