package br.tcc.service;

import br.tcc.model.TipoAuditoriaEntidade;
import br.tcc.outros.ServiceModelo;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class TipoAuditoriaEntidadeService extends ServiceModelo<TipoAuditoriaEntidade> {

	public static final int INCLUSAO = 1;

	public static final int ALTERACAO = 2;

	public static final int EXCLUSAO = 4;

	public static final int EXECUCAO = 3;

	public static final int RECUPERACAO = 5;

	public static final int BLOQUEIO = 6;

	public static final int DESBLOQUEIO = 7;

	public static final Map<Integer, TipoAuditoriaEntidade> map = new HashMap<>();

	@Override
	public Class<TipoAuditoriaEntidade> getClasse() {
		return TipoAuditoriaEntidade.class;
	}

	@Override
	public MapSO toMap(TipoAuditoriaEntidade o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected TipoAuditoriaEntidade fromMap(MapSO map) {
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
	protected TipoAuditoriaEntidade buscaUnicoObrig(MapSO params) {
		String nome = params.getString("nome");
		Lst<TipoAuditoriaEntidade> list = new Lst<TipoAuditoriaEntidade>();
		list.addAll(map.values());
		TipoAuditoriaEntidade o = list.unique(item -> {
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
		s = "Não foi encontrado um TipoAuditoriaEntidade com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	public TipoAuditoriaEntidade inclusao() {
		return find(INCLUSAO);
	}

	public TipoAuditoriaEntidade alteracao() {
		return find(ALTERACAO);
	}

	public TipoAuditoriaEntidade exclusao() {
		return find(EXCLUSAO);
	}

	public TipoAuditoriaEntidade execucao() {
		return find(EXECUCAO);
	}

	public TipoAuditoriaEntidade recuperacao() {
		return find(RECUPERACAO);
	}

	public TipoAuditoriaEntidade bloqueio() {
		return find(BLOQUEIO);
	}

	public TipoAuditoriaEntidade desbloqueio() {
		return find(DESBLOQUEIO);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	public String getText(TipoAuditoriaEntidade o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public TipoAuditoriaEntidade findNotObrig(int id) {
		return map.get(id);
	}

	static {
		map.put(INCLUSAO, new TipoAuditoriaEntidade(INCLUSAO, "Inclusão"));
		map.put(ALTERACAO, new TipoAuditoriaEntidade(ALTERACAO, "Alteração"));
		map.put(EXCLUSAO, new TipoAuditoriaEntidade(EXCLUSAO, "Exclusão"));
		map.put(EXECUCAO, new TipoAuditoriaEntidade(EXECUCAO, "Execução"));
		map.put(RECUPERACAO, new TipoAuditoriaEntidade(RECUPERACAO, "Recuperação"));
		map.put(BLOQUEIO, new TipoAuditoriaEntidade(BLOQUEIO, "Bloqueio"));
		map.put(DESBLOQUEIO, new TipoAuditoriaEntidade(DESBLOQUEIO, "Desbloqueio"));
	}
}
