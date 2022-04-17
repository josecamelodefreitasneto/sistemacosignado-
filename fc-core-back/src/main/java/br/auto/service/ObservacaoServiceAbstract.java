package br.auto.service;

import br.auto.model.Observacao;
import br.auto.select.ObservacaoSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.ArquivoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ObservacaoServiceAbstract extends ServiceModelo<Observacao> {

	@Autowired
	protected ArquivoService arquivoService;

	@Override
	public Class<Observacao> getClasse() {
		return Observacao.class;
	}
	@Override
	public MapSO toMap(final Observacao o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getAnexo() != null) {
			map.put("anexo", arquivoService.toMap(o.getAnexo(), listas));
		}
		if (o.getTexto() != null) {
			map.put("texto", o.getTexto());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	@Override
	protected Observacao fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Observacao o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setAnexo(arquivoService.get(mp.get("anexo")));
		o.setTexto(mp.getString("texto"));
		return o;
	}
	@Override
	public Observacao newO() {
		Observacao o = new Observacao();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final Observacao o) {
		o.setTexto(tratarString(o.getTexto()));
		if (o.getTexto() == null) {
			throw new MessageException("O campo Observação > Texto é obrigatório");
		}
		if (UString.length(o.getTexto()) > 500) {
			throw new MessageException("O campo Observação > Texto aceita no máximo 500 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueTextoEntidadeRegistro(o);
		}
		validar2(o);
		if (o.getEntidade() == null) {
			throw new MessageException("O campo Observação > Entidade é obrigatório");
		}
		if (o.getRegistro() == null) {
			throw new MessageException("O campo Observação > Registro é obrigatório");
		}
		validar3(o);
	}
	public void validarUniqueTextoEntidadeRegistro(final Observacao o) {
		ObservacaoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.texto().eq(o.getTexto());
		select.entidade().eq(o.getEntidade());
		select.registro().eq(o.getRegistro());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("observacao_texto_entidade_registro"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.Observacao.idEntidade;
	}
	@Override
	public boolean utilizaObservacoes() {
		return false;
	}
	@Override
	public ResultadoConsulta consulta(final MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, Observacao> func) {
		final ObservacaoSelect<?> select = select();
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
		Lst<Observacao> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected Observacao buscaUnicoObrig(final MapSO params) {
		final ObservacaoSelect<?> select = select();
		Integer anexo = getId(params, "anexo");
		if (anexo != null) {
			select.anexo().id().eq(anexo);
		}
		Integer entidade = getId(params, "entidade");
		if (entidade != null) {
			select.entidade().id().eq(entidade);
		}
		Integer registro = params.get("registro");
		if (registro != null) select.registro().eq(registro);
		String texto = params.getString("texto");
		if (!UString.isEmpty(texto)) select.texto().eq(texto);
		Observacao o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (anexo != null) {
			s += "&& anexo = '" + anexo + "'";
		}
		if (entidade != null) {
			s += "&& entidade = '" + entidade + "'";
		}
		if (registro != null) {
			s += "&& registro = '" + registro + "'";
		}
		if (texto != null) {
			s += "&& texto = '" + texto + "'";
		}
		s = "Não foi encontrado um Observacao com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return false;
	}
	@Override
	protected Observacao setOld(final Observacao o) {
		Observacao old = newO();
		old.setAnexo(o.getAnexo());
		old.setEntidade(o.getEntidade());
		old.setRegistro(o.getRegistro());
		old.setTexto(o.getTexto());
		o.setOld(old);
		return o;
	}
	public ObservacaoSelect<?> select(final Boolean excluido) {
		ObservacaoSelect<?> o = new ObservacaoSelect<ObservacaoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public ObservacaoSelect<?> select() {
		return select(false);
	}
	@Override
	protected void setBusca(final Observacao o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final Observacao o) {
		if (o == null) return null;
		return o.getTexto();
	}
	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Observacao");
		list.add("texto;anexo.nome");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("observacao_texto_entidade_registro", "A combinação de campos Texto + Entidade + Registro não pode se repetir. Já existe um registro com esta combinação.");
	}
}
