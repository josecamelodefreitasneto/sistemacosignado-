package br.auto.service;

import br.auto.model.Campo;
import br.auto.select.CampoSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.EntidadeService;
import br.impl.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CampoServiceAbstract extends ServiceModelo<Campo> {

	@Autowired
	protected EntidadeService entidadeService;

	@Autowired
	protected ObservacaoService observacaoService;

	@Override
	public Class<Campo> getClasse() {
		return Campo.class;
	}
	@Override
	public MapSO toMap(final Campo o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	@Override
	protected Campo fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Campo o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setEntidade(find(entidadeService, mp, "entidade"));
		o.setNome(mp.getString("nome"));
		o.setNomeNoBanco(mp.getString("nomeNoBanco"));
		o.setTipo(find(entidadeService, mp, "tipo"));
		return o;
	}
	@Override
	public Campo newO() {
		Campo o = new Campo();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final Campo o) {
		if (o.getEntidade() == null) {
			throw new MessageException("O campo Campo > Entidade é obrigatório");
		}
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Campo > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 100) {
			throw new MessageException("O campo Campo > Nome aceita no máximo 100 caracteres");
		}
		o.setNomeNoBanco(tratarString(o.getNomeNoBanco()));
		if (o.getNomeNoBanco() == null) {
			throw new MessageException("O campo Campo > Nome no Banco é obrigatório");
		}
		if (UString.length(o.getNomeNoBanco()) > 50) {
			throw new MessageException("O campo Campo > Nome no Banco aceita no máximo 50 caracteres");
		}
		if (o.getTipo() == null) {
			throw new MessageException("O campo Campo > Tipo é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNomeNoBancoEntidade(o);
		}
		validar2(o);
		validar3(o);
	}
	public void validarUniqueNomeNoBancoEntidade(final Campo o) {
		CampoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nomeNoBanco().eq(o.getNomeNoBanco());
		select.entidade().eq(o.getEntidade());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("campo_nomenobanco_entidade"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.Campo.idEntidade;
	}
	@Override
	protected void saveObservacoes(final Campo o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, Campo> func) {
		final CampoSelect<?> select = select();
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
		Lst<Campo> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected Campo buscaUnicoObrig(final MapSO params) {
		final CampoSelect<?> select = select();
		Integer entidade = getId(params, "entidade");
		if (entidade != null) {
			select.entidade().id().eq(entidade);
		}
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		String nomeNoBanco = params.getString("nomeNoBanco");
		if (!UString.isEmpty(nomeNoBanco)) select.nomeNoBanco().eq(nomeNoBanco);
		Integer tipo = getId(params, "tipo");
		if (tipo != null) {
			select.tipo().id().eq(tipo);
		}
		Campo o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (entidade != null) {
			s += "&& entidade = '" + entidade + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		if (nomeNoBanco != null) {
			s += "&& nomeNoBanco = '" + nomeNoBanco + "'";
		}
		if (tipo != null) {
			s += "&& tipo = '" + tipo + "'";
		}
		s = "Não foi encontrado um Campo com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return false;
	}
	@Override
	protected Campo setOld(final Campo o) {
		Campo old = newO();
		old.setEntidade(o.getEntidade());
		old.setNome(o.getNome());
		old.setNomeNoBanco(o.getNomeNoBanco());
		old.setTipo(o.getTipo());
		o.setOld(old);
		return o;
	}
	public CampoSelect<?> select(final Boolean excluido) {
		CampoSelect<?> o = new CampoSelect<CampoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public CampoSelect<?> select() {
		return select(false);
	}
	@Override
	protected void setBusca(final Campo o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final Campo o) {
		if (o == null) return null;
		return o.getNome();
	}
	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Campo");
		list.add("entidade;tipo;nome;nomeNoBanco");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("campo_nomenobanco_entidade", "A combinação de campos Nome no Banco + Entidade não pode se repetir. Já existe um registro com esta combinação.");
	}
}
