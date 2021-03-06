package br.tcc.service;

import br.tcc.model.ImportacaoArquivoStatus;
import br.tcc.outros.ServiceModelo;
import br.tcc.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.UString;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImportacaoArquivoStatusService extends ServiceModelo<ImportacaoArquivoStatus> {

	public static final int AGUARDANDO_PROCESSAMENTO = 1;

	public static final int EM_ANALISE = 2;

	public static final int EM_PROCESSAMENTO = 3;

	public static final int PROCESSADO = 4;

	public static final Map<Integer, ImportacaoArquivoStatus> map = new HashMap<>();

	@Autowired ObservacaoService observacaoService;

	@Override
	public Class<ImportacaoArquivoStatus> getClasse() {
		return ImportacaoArquivoStatus.class;
	}

	@Override
	public MapSO toMap(ImportacaoArquivoStatus o, final boolean listas) {
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
	protected ImportacaoArquivoStatus fromMap(MapSO map) {
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
	protected void saveObservacoes(ImportacaoArquivoStatus o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	protected ImportacaoArquivoStatus buscaUnicoObrig(MapSO params) {
		String nome = params.getString("nome");
		Lst<ImportacaoArquivoStatus> list = new Lst<ImportacaoArquivoStatus>();
		list.addAll(map.values());
		ImportacaoArquivoStatus o = list.unique(item -> {
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
		s = "N??o foi encontrado um ImportacaoArquivoStatus com os seguintes crit??rios:" + s.substring(2);
		throw new MessageException(s);
	}

	public ImportacaoArquivoStatus aguardandoProcessamento() {
		return find(AGUARDANDO_PROCESSAMENTO);
	}

	public ImportacaoArquivoStatus emAnalise() {
		return find(EM_ANALISE);
	}

	public ImportacaoArquivoStatus emProcessamento() {
		return find(EM_PROCESSAMENTO);
	}

	public ImportacaoArquivoStatus processado() {
		return find(PROCESSADO);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	public String getText(ImportacaoArquivoStatus o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ImportacaoArquivoStatus findNotObrig(int id) {
		return map.get(id);
	}

	static {
		map.put(AGUARDANDO_PROCESSAMENTO, new ImportacaoArquivoStatus(AGUARDANDO_PROCESSAMENTO, "Aguardando Processamento"));
		map.put(EM_ANALISE, new ImportacaoArquivoStatus(EM_ANALISE, "Em An??lise"));
		map.put(EM_PROCESSAMENTO, new ImportacaoArquivoStatus(EM_PROCESSAMENTO, "Em Processamento"));
		map.put(PROCESSADO, new ImportacaoArquivoStatus(PROCESSADO, "Processado"));
	}
}
