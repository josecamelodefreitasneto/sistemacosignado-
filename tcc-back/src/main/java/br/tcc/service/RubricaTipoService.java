package br.tcc.service;

import br.tcc.model.RubricaTipo;
import br.tcc.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class RubricaTipoService extends ServiceModelo<RubricaTipo> {

	public static final int REMUNERACAO = 1;

	public static final int DESCONTO = 2;

	public static final Map<Integer, RubricaTipo> map = new HashMap<>();

	@Override
	public Class<RubricaTipo> getClasse() {
		return RubricaTipo.class;
	}

	@Override
	public MapSO toMap(RubricaTipo o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected RubricaTipo fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		if (id == null || id < 1) {
			return null;
		} else {
			return find(id);
		}
	}

	@Override
	public int getIdEntidade() {
		throw new MessageException("?");
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected RubricaTipo buscaUnicoObrig(MapSO params) {
		String nome = params.getString("nome");
		Lst<RubricaTipo> list = new Lst<RubricaTipo>();
		list.addAll(map.values());
		RubricaTipo o = list.unique(item -> {
			if (!UString.isEmpty(nome) && !UString.equals(item.getNome(), nome)) {
				return false;
			}
			return true;
		});
		if (o != null) {
			return o;
		}
		String s = "";
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		s = "Não foi encontrado um RubricaTipo com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	public RubricaTipo remuneracao() {
		return find(REMUNERACAO);
	}

	public RubricaTipo desconto() {
		return find(DESCONTO);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	public String getText(RubricaTipo o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public RubricaTipo findNotObrig(int id) {
		return map.get(id);
	}

	static {
		map.put(REMUNERACAO, new RubricaTipo(REMUNERACAO, "Remuneração"));
		map.put(DESCONTO, new RubricaTipo(DESCONTO, "Desconto"));
	}

}
