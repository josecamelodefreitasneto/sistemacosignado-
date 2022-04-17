package br.tcc.service;

import br.tcc.model.AnoMes;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.select.AnoMesSelect;
import br.tcc.service.MesService;
import gm.utils.comum.Lst;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import org.springframework.stereotype.Component;
import gm.utils.date.Data;

@Component
public class AnoMesService extends ServiceModelo<AnoMes> {

	@Autowired MesService mesService;

	@Override
	public Class<AnoMes> getClasse() {
		return AnoMes.class;
	}

	@Override
	public MapSO toMap(AnoMes o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}

	@Override
	protected AnoMes fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final AnoMes o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setAno(mp.getInt("ano", 0));
		o.setMes(findId(mesService, mp, "mes"));
		o.setNome(mp.getString("nome"));
		return o;
	}

	@Override
	public AnoMes newO() {
		AnoMes o = new AnoMes();
		o.setAno(0);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}

	@Override
	protected final void validar(AnoMes o) {
		if (o.getAno() == null) {
			throw new MessageException("O campo Ano Mês > Ano é obrigatório");
		}
		if (o.getMes() == null) {
			throw new MessageException("O campo Ano Mês > Mês é obrigatório");
		}
		o.setNome(tratarString(o.getNome()));
		if (o.getNome() == null) {
			throw new MessageException("O campo Ano Mês > Nome é obrigatório");
		}
		if (UString.length(o.getNome()) > 14) {
			throw new MessageException("O campo Ano Mês > Nome aceita no máximo 14 caracteres");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueNome(o);
			validarUniqueMesAno(o);
		}
		validar2(o);
		validar3(o);
	}

	public void validarUniqueNome(AnoMes o) {
		AnoMesSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.nome().eq(o.getNome());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("anomes_nome"));
		}
	}

	public void validarUniqueMesAno(AnoMes o) {
		AnoMesSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.mes().eq(o.getMes());
		select.ano().eq(o.getAno());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("anomes_mes_ano"));
		}
	}

	@Override
	public int getIdEntidade() {
		return IDSDefault.AnoMes.idEntidade;
	}

	@Override
	public boolean utilizaObservacoes() {
		return false;
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, AnoMes> func) {
		final AnoMesSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.integer(params, "ano", select.ano());
		FiltroConsulta.fk(params, "mes", select.mes());
		FiltroConsulta.string(params, "nome", select.nome());
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
		Lst<AnoMes> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected AnoMes buscaUnicoObrig(MapSO params) {
		final AnoMesSelect<?> select = select();
		Integer ano = params.get("ano");
		if (ano != null) select.ano().eq(ano);
		Integer mes = getId(params, "mes");
		if (mes != null) {
			select.mes().eq(mes);
		}
		String nome = params.getString("nome");
		if (!UString.isEmpty(nome)) select.nome().eq(nome);
		AnoMes o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (ano != null) {
			s += "&& ano = '" + ano + "'";
		}
		if (mes != null) {
			s += "&& mes = '" + mes + "'";
		}
		if (nome != null) {
			s += "&& nome = '" + nome + "'";
		}
		s = "Não foi encontrado um AnoMes com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}

	@Override
	public boolean auditar() {
		return false;
	}

	@Override
	protected AnoMes setOld(AnoMes o) {
		AnoMes old = newO();
		old.setAno(o.getAno());
		old.setMes(o.getMes());
		old.setNome(o.getNome());
		o.setOld(old);
		return o;
	}

	public static boolean mesJaneiro(AnoMes o) {
		return o.getMes() == MesService.JANEIRO;
	}

	public boolean mesJaneiro(int id) {
		return mesJaneiro(find(id));
	}

	public static boolean mesFevereiro(AnoMes o) {
		return o.getMes() == MesService.FEVEREIRO;
	}

	public boolean mesFevereiro(int id) {
		return mesFevereiro(find(id));
	}

	public static boolean mesMarco(AnoMes o) {
		return o.getMes() == MesService.MARCO;
	}

	public boolean mesMarco(int id) {
		return mesMarco(find(id));
	}

	public static boolean mesAbril(AnoMes o) {
		return o.getMes() == MesService.ABRIL;
	}

	public boolean mesAbril(int id) {
		return mesAbril(find(id));
	}

	public static boolean mesMaio(AnoMes o) {
		return o.getMes() == MesService.MAIO;
	}

	public boolean mesMaio(int id) {
		return mesMaio(find(id));
	}

	public static boolean mesJunho(AnoMes o) {
		return o.getMes() == MesService.JUNHO;
	}

	public boolean mesJunho(int id) {
		return mesJunho(find(id));
	}

	public static boolean mesJulho(AnoMes o) {
		return o.getMes() == MesService.JULHO;
	}

	public boolean mesJulho(int id) {
		return mesJulho(find(id));
	}

	public static boolean mesAgosto(AnoMes o) {
		return o.getMes() == MesService.AGOSTO;
	}

	public boolean mesAgosto(int id) {
		return mesAgosto(find(id));
	}

	public static boolean mesSetembro(AnoMes o) {
		return o.getMes() == MesService.SETEMBRO;
	}

	public boolean mesSetembro(int id) {
		return mesSetembro(find(id));
	}

	public static boolean mesOutubro(AnoMes o) {
		return o.getMes() == MesService.OUTUBRO;
	}

	public boolean mesOutubro(int id) {
		return mesOutubro(find(id));
	}

	public static boolean mesNovembro(AnoMes o) {
		return o.getMes() == MesService.NOVEMBRO;
	}

	public boolean mesNovembro(int id) {
		return mesNovembro(find(id));
	}

	public static boolean mesDezembro(AnoMes o) {
		return o.getMes() == MesService.DEZEMBRO;
	}

	public boolean mesDezembro(int id) {
		return mesDezembro(find(id));
	}

	public AnoMesSelect<?> select(Boolean excluido) {
		AnoMesSelect<?> o = new AnoMesSelect<AnoMesSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		o.ano().asc();
		o.mes().asc();
		return o;
	}

	public AnoMesSelect<?> select() {
		return select(false);
	}

	@Override
	protected void setBusca(AnoMes o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(AnoMes o) {
		if (o == null) return null;
		return o.getNome();
	}

	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("AnoMes");
		list.add("nome;ano;mes");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("anomes_nome", "O campo Nome não pode se repetir. Já existe um registro com este valor.");
		CONSTRAINTS_MESSAGES.put("anomes_mes_ano", "A combinação de campos Mês + Ano não pode se repetir. Já existe um registro com esta combinação.");
	}

	public AnoMes get(int ano, final int mes) {
		final int id = ano * 100 + mes;
		AnoMes o = findNotObrig(id);
		if (o == null) {
			o = new AnoMes();
			o.setId(id);
			o.setAno(ano);
			o.setMes(mes);
			o.setNome(new Data(o.getAno(), mes, 1).format("[mmm]/[yyyy]"));
			o = insertSemAuditoria(o);
		}
		return o;
	}

	@Transactional
	public void atualizar() {

		final Data hoje = Data.hoje();

		for (int ano = 2017; ano < hoje.getAno(); ano++) {
			for (int mes = 1; mes < 13; mes++) {
				get(ano, mes);
			}
		}

		for (int mes = 1; mes <= hoje.getMes(); mes++) {
			get(hoje.getAno(), mes);
		}
	}

}
