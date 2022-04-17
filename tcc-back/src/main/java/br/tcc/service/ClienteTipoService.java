package br.tcc.service;

import br.tcc.model.ClienteTipo;
import br.tcc.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ClienteTipoService extends ServiceModelo<ClienteTipo> {

	public static final int SERVIDOR = 1;

	public static final int PENSIONISTA = 2;

	public static final Map<Integer, ClienteTipo> map = new HashMap<>();

	@Override
	public Class<ClienteTipo> getClasse() {
		return ClienteTipo.class;
	}

	@Override
	public MapSO toMap(ClienteTipo o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getNome() != null) {
			map.put("nome", o.getNome());
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected ClienteTipo fromMap(MapSO map) {
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
	protected ClienteTipo buscaUnicoObrig(MapSO params) {
		String nome = params.getString("nome");
		Lst<ClienteTipo> list = new Lst<ClienteTipo>();
		list.addAll(map.values());
		ClienteTipo o = list.unique(item -> {
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
		s = "Não foi encontrado um ClienteTipo com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	public ClienteTipo servidor() {
		return find(SERVIDOR);
	}

	public ClienteTipo pensionista() {
		return find(PENSIONISTA);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	public String getText(ClienteTipo o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ClienteTipo findNotObrig(int id) {
		return map.get(id);
	}

	static {
		map.put(SERVIDOR, new ClienteTipo(SERVIDOR, "Servidor"));
		map.put(PENSIONISTA, new ClienteTipo(PENSIONISTA, "Pensionista"));
	}
}
