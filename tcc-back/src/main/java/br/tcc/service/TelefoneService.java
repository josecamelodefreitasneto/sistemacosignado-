package br.tcc.service;

import br.tcc.model.Telefone;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDS;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.TelefoneSelect;
import gm.utils.comum.Lst;
import gm.utils.comum.UBoolean;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import gm.utils.outros.UTelefone;
import org.springframework.stereotype.Component;

@Component
public class TelefoneService extends ServiceModelo<Telefone> {

	@Override
	public Class<Telefone> getClasse() {
		return Telefone.class;
	}

	@Override
	public MapSO toMap(Telefone o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getDdd() != null) {
			map.put("ddd", o.getDdd());
		}
		if (o.getNome() != null) {
			map.put("nome", o.getNome());
		}
		if (o.getNumero() != null) {
			map.put("numero", o.getNumero());
		}
		if (o.getRecado() != null) {
			map.put("recado", o.getRecado());
		}
		if (o.getWhatsapp() != null) {
			map.put("whatsapp", o.getWhatsapp());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Telefone fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final Telefone o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setDdd(mp.getInt("ddd", 0));
		o.setNumero(mp.getString("numero"));
		o.setRecado(UBoolean.toBoolean(mp.get("recado")));
		o.setWhatsapp(UBoolean.toBoolean(mp.get("whatsapp")));
		return o;
	}

	@Override
	public Telefone newO() {
		Telefone o = new Telefone();
		o.setDdd(61);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(Telefone o) {
		if (o.getDdd() == null) {
			throw new MessageException("O campo Telefone > DDD é obrigatório");
		}
		o.setNumero(tratarString(o.getNumero()));
		if (o.getNumero() == null) {
			throw new MessageException("O campo Telefone > Número é obrigatório");
		}
		if (UString.length(o.getNumero()) > 9) {
			throw new MessageException("O campo Telefone > Número aceita no máximo 9 caracteres");
		}
		validar2(o);
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Telefone > Como vai aparecer é obrigatório");
		}
		if (UString.length(o.getNome()) > 28) {
			throw new MessageException("O campo Telefone > Como vai aparecer aceita no máximo 28 caracteres");
		}
		validar3(o);
	}

	@Override
	public int getIdEntidade() {
		return IDS.Telefone.idEntidade;
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
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, Telefone> func) {
		final TelefoneSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.integer(params, "ddd", select.ddd());
		FiltroConsulta.string(params, "nome", select.nome());
		FiltroConsulta.string(params, "numero", select.numero());
		FiltroConsulta.bool(params, "recado", select.recado());
		FiltroConsulta.bool(params, "whatsapp", select.whatsapp());
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
		Lst<Telefone> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected Telefone buscaUnicoObrig(MapSO params) {
		final TelefoneSelect<?> select = select();
		Integer ddd = params.get("ddd");
		if (ddd != null) select.ddd().eq(ddd);
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		String numero = params.getString("numero");
		if (!UString.isEmpty(numero)) select.numero().eq(numero);
		Boolean recado = params.get("recado");
		if (recado != null) select.recado().eq(recado);
		Boolean whatsapp = params.get("whatsapp");
		if (whatsapp != null) select.whatsapp().eq(whatsapp);
		Telefone o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (ddd != null) {
			s += "&& ddd = '" + ddd + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		if (numero != null) {
			s += "&& numero = '" + numero + "'";
		}
		if (recado != null) {
			s += "&& recado = '" + recado + "'";
		}
		if (whatsapp != null) {
			s += "&& whatsapp = '" + whatsapp + "'";
		}
		s = "Não foi encontrado um Telefone com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected Telefone setOld(Telefone o) {
		Telefone old = newO();
		old.setDdd(o.getDdd());
		old.setNome(o.getNome());
		old.setNumero(o.getNumero());
		old.setRecado(o.getRecado());
		old.setWhatsapp(o.getWhatsapp());
		o.setOld(old);
		return o;
	}

	public TelefoneSelect<?> select(Boolean excluido) {
		TelefoneSelect<?> o = new TelefoneSelect<TelefoneSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}

	public TelefoneSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(Telefone o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(Telefone o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("Telefone");
		list.add("ddd;numero;whatsapp;recado");
		return list;
	}

	@Override
	protected void beforeInsert(Telefone o) {
		setNome(o);
	}
	@Override
	protected void beforeUpdate(Telefone o) {
		setNome(o);
	}
	private void setNome(Telefone o) {
		o.setNome(UTelefone.format(o.getDdd(), o.getNumero()));
	}

}
