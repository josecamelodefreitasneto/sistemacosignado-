package br.tcc.service;

import br.tcc.model.Entidade;
import br.tcc.model.Observacao;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.ObservacaoSelect;
import br.tcc.service.ArquivoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObservacaoService extends ServiceModelo<Observacao> {

	@Autowired ArquivoService arquivoService;

	@Override
	public Class<Observacao> getClasse() {
		return Observacao.class;
	}

	@Override
	public MapSO toMap(Observacao o, final boolean listas) {
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
		o.setEntidade(entidadeService.find(map.getIntObrig("entidade")));
		o.setRegistro(map.getIntObrig("registro"));
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
	protected final void validar(Observacao o) {
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

	public void validarUniqueTextoEntidadeRegistro(Observacao o) {
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
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Observacao> func) {
		final ObservacaoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "anexo", select.anexo());
		FiltroConsulta.fk(params, "entidade", select.entidade());
		FiltroConsulta.integer(params, "registro", select.registro());
		FiltroConsulta.string(params, "texto", select.texto());
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
		Lst<Observacao> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Observacao buscaUnicoObrig(MapSO params) {
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
	protected Observacao setOld(Observacao o) {
		Observacao old = newO();
		old.setAnexo(o.getAnexo());
		old.setEntidade(o.getEntidade());
		old.setRegistro(o.getRegistro());
		old.setTexto(o.getTexto());
		o.setOld(old);
		return o;
	}

	public ObservacaoSelect<?> select(Boolean excluido) {
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
	protected void setBusca(Observacao o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Observacao o) {
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

	@Autowired EntidadeService entidadeService;

	public List<Observacao> get(Entidade entidade, final int registro) {
		return select(null).entidade().eq(entidade).registro().eq(registro).list();
	}
	public List<Observacao> get(int entidade, final int registro) {
		return this.get(entidadeService.find(entidade), registro);
	}
	public void save(int entidade, final int registro, final MapSO map) {
		final List<MapSO> list = map.get("observacoes");
		if (list != null) {
			for (MapSO item : list) {
				item.add("entidade", entidade);
				item.add("registro", registro);
				this.save(item);
			}
		}
	}

}
