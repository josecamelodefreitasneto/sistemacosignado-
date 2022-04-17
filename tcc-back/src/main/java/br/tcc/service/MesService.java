package br.tcc.service;

import br.tcc.model.Mes;
import br.tcc.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class MesService extends ServiceModelo<Mes> {

	public static final int JANEIRO = 1;

	public static final int FEVEREIRO = 2;

	public static final int MARCO = 3;

	public static final int ABRIL = 4;

	public static final int MAIO = 5;

	public static final int JUNHO = 6;

	public static final int JULHO = 7;

	public static final int AGOSTO = 8;

	public static final int SETEMBRO = 9;

	public static final int OUTUBRO = 10;

	public static final int NOVEMBRO = 11;

	public static final int DEZEMBRO = 12;

	public static final Map<Integer, Mes> map = new HashMap<>();

	@Override
	public Class<Mes> getClasse() {
		return Mes.class;
	}

	@Override
	public MapSO toMap(Mes o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected Mes fromMap(MapSO map) {
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
	protected Mes buscaUnicoObrig(MapSO params) {
		String nome = params.getString("nome");
		Lst<Mes> list = new Lst<Mes>();
		list.addAll(map.values());
		Mes o = list.unique(item -> {
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
		s = "Não foi encontrado um Mes com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	public Mes janeiro() {
		return find(JANEIRO);
	}

	public Mes fevereiro() {
		return find(FEVEREIRO);
	}

	public Mes marco() {
		return find(MARCO);
	}

	public Mes abril() {
		return find(ABRIL);
	}

	public Mes maio() {
		return find(MAIO);
	}

	public Mes junho() {
		return find(JUNHO);
	}

	public Mes julho() {
		return find(JULHO);
	}

	public Mes agosto() {
		return find(AGOSTO);
	}

	public Mes setembro() {
		return find(SETEMBRO);
	}

	public Mes outubro() {
		return find(OUTUBRO);
	}

	public Mes novembro() {
		return find(NOVEMBRO);
	}

	public Mes dezembro() {
		return find(DEZEMBRO);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	public String getText(Mes o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public Mes findNotObrig(int id) {
		return map.get(id);
	}

	static {
		map.put(JANEIRO, new Mes(JANEIRO, "Janeiro"));
		map.put(FEVEREIRO, new Mes(FEVEREIRO, "Fevereiro"));
		map.put(MARCO, new Mes(MARCO, "Março"));
		map.put(ABRIL, new Mes(ABRIL, "Abril"));
		map.put(MAIO, new Mes(MAIO, "Maio"));
		map.put(JUNHO, new Mes(JUNHO, "Junho"));
		map.put(JULHO, new Mes(JULHO, "Julho"));
		map.put(AGOSTO, new Mes(AGOSTO, "Agosto"));
		map.put(SETEMBRO, new Mes(SETEMBRO, "Setembro"));
		map.put(OUTUBRO, new Mes(OUTUBRO, "Outubro"));
		map.put(NOVEMBRO, new Mes(NOVEMBRO, "Novembro"));
		map.put(DEZEMBRO, new Mes(DEZEMBRO, "Dezembro"));
	}
}
