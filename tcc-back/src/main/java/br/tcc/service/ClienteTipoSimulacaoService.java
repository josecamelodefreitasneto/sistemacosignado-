package br.tcc.service;

import br.tcc.model.ClienteTipoSimulacao;
import br.tcc.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ClienteTipoSimulacaoService extends ServiceModelo<ClienteTipoSimulacao> {

	public static final int PELO_VALOR_DA_PARCELA = 1;

	public static final int PELO_VALOR_DO_EMPRESTIMO = 2;

	public static final Map<Integer, ClienteTipoSimulacao> map = new HashMap<>();

	@Override
	public Class<ClienteTipoSimulacao> getClasse() {
		return ClienteTipoSimulacao.class;
	}

	@Override
	public MapSO toMap(ClienteTipoSimulacao o, final boolean listas) {
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
	protected ClienteTipoSimulacao fromMap(MapSO map) {
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
	protected ClienteTipoSimulacao buscaUnicoObrig(MapSO params) {
		String nome = params.getString("nome");
		Lst<ClienteTipoSimulacao> list = new Lst<ClienteTipoSimulacao>();
		list.addAll(map.values());
		ClienteTipoSimulacao o = list.unique(item -> {
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
		s = "Não foi encontrado um ClienteTipoSimulacao com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	public ClienteTipoSimulacao peloValorDaParcela() {
		return find(PELO_VALOR_DA_PARCELA);
	}

	public ClienteTipoSimulacao peloValorDoEmprestimo() {
		return find(PELO_VALOR_DO_EMPRESTIMO);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	public String getText(ClienteTipoSimulacao o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ClienteTipoSimulacao findNotObrig(int id) {
		return map.get(id);
	}

	static {
		map.put(PELO_VALOR_DA_PARCELA, new ClienteTipoSimulacao(PELO_VALOR_DA_PARCELA, "Pelo valor da parcela"));
		map.put(PELO_VALOR_DO_EMPRESTIMO, new ClienteTipoSimulacao(PELO_VALOR_DO_EMPRESTIMO, "Pelo valor do empréstimo"));
	}
}
