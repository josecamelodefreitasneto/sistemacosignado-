package br.tcc.service;

import br.tcc.model.ClienteStatus;
import br.tcc.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ClienteStatusService extends ServiceModelo<ClienteStatus> {

	public static final int EM_ATENDIMENTO = 1;

	public static final int EMPRESTIMO_REALIZADO = 2;

	public static final int NAO_TEM_INTERESSE = 3;

	public static final Map<Integer, ClienteStatus> map = new HashMap<>();

	@Override
	public Class<ClienteStatus> getClasse() {
		return ClienteStatus.class;
	}

	@Override
	public MapSO toMap(ClienteStatus o, final boolean listas) {
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
	protected ClienteStatus fromMap(MapSO map) {
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
	protected ClienteStatus buscaUnicoObrig(MapSO params) {
		String nome = params.getString("nome");
		Lst<ClienteStatus> list = new Lst<ClienteStatus>();
		list.addAll(map.values());
		ClienteStatus o = list.unique(item -> {
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
		s = "Não foi encontrado um ClienteStatus com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	public ClienteStatus emAtendimento() {
		return find(EM_ATENDIMENTO);
	}

	public ClienteStatus emprestimoRealizado() {
		return find(EMPRESTIMO_REALIZADO);
	}

	public ClienteStatus naoTemInteresse() {
		return find(NAO_TEM_INTERESSE);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	public String getText(ClienteStatus o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ClienteStatus findNotObrig(int id) {
		return map.get(id);
	}

	static {
		map.put(EM_ATENDIMENTO, new ClienteStatus(EM_ATENDIMENTO, "Em atendimento"));
		map.put(EMPRESTIMO_REALIZADO, new ClienteStatus(EMPRESTIMO_REALIZADO, "Empréstimo Realizado"));
		map.put(NAO_TEM_INTERESSE, new ClienteStatus(NAO_TEM_INTERESSE, "Não tem interesse"));
	}
}
