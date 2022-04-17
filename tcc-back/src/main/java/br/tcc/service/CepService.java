package br.tcc.service;

import br.tcc.model.Cep;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDS;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.CepSelect;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CepService extends ServiceModelo<Cep> {

	@Override
	public Class<Cep> getClasse() {
		return Cep.class;
	}

	@Override
	public MapSO toMap(Cep o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getBairro() != null) {
			map.put("bairro", o.getBairro());
		}
		if (o.getCidade() != null) {
			map.put("cidade", o.getCidade());
		}
		if (o.getLogradouro() != null) {
			map.put("logradouro", o.getLogradouro());
		}
		if (o.getNumero() != null) {
			map.put("numero", o.getNumero());
		}
		if (o.getUf() != null) {
			map.put("uf", o.getUf());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Cep fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Cep o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setBairro(mp.getString("bairro"));
		o.setCidade(mp.getString("cidade"));
		o.setLogradouro(mp.getString("logradouro"));
		o.setNumero(mp.getString("numero"));
		o.setUf(mp.getString("uf"));
		return o;
	}

	@Override
	public Cep newO() {
		Cep o = new Cep();
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Cep o) {
		o.setBairro(tratarString(o.getBairro()));
		if (o.getBairro() == null) {
			throw new MessageException("O campo Cep > Bairro é obrigatório");
		}
		if (UString.length(o.getBairro()) > 100) {
			throw new MessageException("O campo Cep > Bairro aceita no máximo 100 caracteres");
		}
		o.setCidade(tratarString(o.getCidade()));
		if (o.getCidade() == null) {
			throw new MessageException("O campo Cep > Cidade é obrigatório");
		}
		if (UString.length(o.getCidade()) > 100) {
			throw new MessageException("O campo Cep > Cidade aceita no máximo 100 caracteres");
		}
		o.setLogradouro(tratarString(o.getLogradouro()));
		if (o.getLogradouro() == null) {
			throw new MessageException("O campo Cep > Logradouro é obrigatório");
		}
		if (UString.length(o.getLogradouro()) > 100) {
			throw new MessageException("O campo Cep > Logradouro aceita no máximo 100 caracteres");
		}
		o.setNumero(tratarString(o.getNumero()));
		if (o.getNumero() == null) {
			throw new MessageException("O campo Cep > Número é obrigatório");
		}
		if (UString.length(o.getNumero()) > 10) {
			throw new MessageException("O campo Cep > Número aceita no máximo 10 caracteres");
		}
		o.setUf(tratarString(o.getUf()));
		if (o.getUf() == null) {
			throw new MessageException("O campo Cep > UF é obrigatório");
		}
		if (UString.length(o.getUf()) > 100) {
			throw new MessageException("O campo Cep > UF aceita no máximo 100 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNumero(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueNumero(Cep o) {
		CepSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.numero().eq(o.getNumero());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("cep_numero"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDS.Cep.idEntidade;
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
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Cep> func) {
		final CepSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.string(params, "bairro", select.bairro());
		FiltroConsulta.string(params, "cidade", select.cidade());
		FiltroConsulta.string(params, "logradouro", select.logradouro());
		FiltroConsulta.string(params, "numero", select.numero());
		FiltroConsulta.string(params, "uf", select.uf());
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
		Lst<Cep> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Cep buscaUnicoObrig(MapSO params) {

		final CepSelect<?> select = select();
		String bairro = params.getString("bairro");
		if (!UString.isEmpty(bairro)) select.bairro().eq(bairro);
		String cidade = params.getString("cidade");
		if (!UString.isEmpty(cidade)) select.cidade().eq(cidade);
		String logradouro = params.getString("logradouro");
		if (!UString.isEmpty(logradouro)) select.logradouro().eq(logradouro);
		String numero = params.getString("numero");
		if (!UString.isEmpty(numero)) select.numero().eq(numero);
		String uf = params.getString("uf");
		if (!UString.isEmpty(uf)) select.uf().eq(uf);
		Cep o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (bairro != null) {
			s += "&& bairro = '" + bairro + "'";
		}
		if (cidade != null) {
			s += "&& cidade = '" + cidade + "'";
		}
		if (logradouro != null) {
			s += "&& logradouro = '" + logradouro + "'";
		}
		if (numero != null) {
			s += "&& numero = '" + numero + "'";
		}
		if (uf != null) {
			s += "&& uf = '" + uf + "'";
		}
		s = "Não foi encontrado um Cep com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected Cep setOld(Cep o) {
		Cep old = newO();
		old.setBairro(o.getBairro());
		old.setCidade(o.getCidade());
		old.setLogradouro(o.getLogradouro());
		old.setNumero(o.getNumero());
		old.setUf(o.getUf());
		o.setOld(old);
		return o;
	}

	public CepSelect<?> select(Boolean excluido) {
		CepSelect<?> o = new CepSelect<CepSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public CepSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Cep o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Cep o) {
		if (o == null) return null;
		return o.getNumero();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Cep");
		list.add("numero;uf;cidade;bairro;logradouro");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("cep_numero", "O campo Número não pode se repetir. Já existe um registro com este valor.");
	}
}
