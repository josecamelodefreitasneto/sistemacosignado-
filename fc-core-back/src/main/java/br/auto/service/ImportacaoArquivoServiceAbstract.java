package br.auto.service;

import br.auto.model.ImportacaoArquivo;
import br.auto.model.ImportacaoArquivoErro;
import br.auto.select.ImportacaoArquivoSelect;
import br.impl.outros.ResultadoConsulta;
import br.impl.outros.ServiceModelo;
import br.impl.service.ArquivoService;
import br.impl.service.AuditoriaCampoService;
import br.impl.service.EntidadeService;
import br.impl.service.ImportacaoArquivoErroService;
import br.impl.service.ImportacaoArquivoStatusService;
import br.impl.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.comum.UBoolean;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ImportacaoArquivoServiceAbstract extends ServiceModelo<ImportacaoArquivo> {

	@Autowired
	protected ArquivoService arquivoService;

	@Autowired
	protected EntidadeService entidadeService;

	@Autowired
	protected ImportacaoArquivoStatusService importacaoArquivoStatusService;

	@Autowired
	protected ImportacaoArquivoErroService importacaoArquivoErroService;

	@Autowired
	protected ObservacaoService observacaoService;

	@Autowired
	protected AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<ImportacaoArquivo> getClasse() {
		return ImportacaoArquivo.class;
	}
	@Override
	public MapSO toMap(final ImportacaoArquivo o, final boolean listas) {
		MapSO map = new MapSO();
		map.put("id", o.getId());
		if (o.getArquivo() != null) {
			map.put("arquivo", arquivoService.toMap(o.getArquivo(), listas));
		}
		if (o.getArquivoDeErros() != null) {
			map.put("arquivoDeErros", arquivoService.toMap(o.getArquivoDeErros(), listas));
		}
		if (o.getAtualizarRegistrosExistentes() != null) {
			map.put("atualizarRegistrosExistentes", o.getAtualizarRegistrosExistentes());
		}
		if (o.getDelimitador() != null) {
			map.put("delimitador", o.getDelimitador());
		}
		if (o.getEntidade() != null) {
			map.put("entidade", entidadeService.toIdText(o.getEntidade()));
		}
		if (o.getProcessadosComErro() != null) {
			map.put("processadosComErro", o.getProcessadosComErro());
		}
		if (o.getProcessadosComSucesso() != null) {
			map.put("processadosComSucesso", o.getProcessadosComSucesso());
		}
		if (o.getStatus() != null) {
			map.put("status", importacaoArquivoStatusService.toIdText(o.getStatus()));
		}
		if (o.getTotalDeLinhas() != null) {
			map.put("totalDeLinhas", o.getTotalDeLinhas());
		}
		if (listas) {
			map.put("erros", importacaoArquivoErroService.toMapList(getErros(o)));
		}
		map.put("excluido", o.getExcluido());
		map.put("registroBloqueado", o.getRegistroBloqueado());
		return map;
	}
	private Lst<ImportacaoArquivoErro> getErros(final ImportacaoArquivo o) {
		return importacaoArquivoErroService.select().importacaoArquivo().eq(o).list();
	}
	@Override
	protected ImportacaoArquivo fromMap(MapSO map) {
		final MapSO mp = new MapSO(map);
		Integer id = mp.getInt("id");
		final ImportacaoArquivo o;
		if (id == null || id < 1) {
			o = newO();
		} else {
			o = find(id);
		}
		o.setArquivo(arquivoService.get(mp.get("arquivo")));
		o.setAtualizarRegistrosExistentes(UBoolean.toBoolean(mp.get("atualizarRegistrosExistentes")));
		o.setDelimitador(mp.getString("delimitador"));
		o.setEntidade(find(entidadeService, mp, "entidade"));
		return o;
	}
	@Override
	public ImportacaoArquivo newO() {
		ImportacaoArquivo o = new ImportacaoArquivo();
		o.setAtualizarRegistrosExistentes(false);
		o.setDelimitador(";");
		o.setProcessadosComErro(0);
		o.setProcessadosComSucesso(0);
		o.setTotalDeLinhas(0);
		o.setExcluido(false);
		o.setRegistroBloqueado(false);
		return o;
	}
	@Override
	protected final void validar(final ImportacaoArquivo o) {
		if (o.getArquivo() == null) {
			throw new MessageException("O campo Importação Arquivo > Arquivo é obrigatório");
		}
		if (o.getAtualizarRegistrosExistentes() == null) {
			throw new MessageException("O campo Importação Arquivo > Atualizar Registros Existentes é obrigatório");
		}
		o.setDelimitador(tratarString(o.getDelimitador()));
		if (o.getDelimitador() == null) {
			throw new MessageException("O campo Importação Arquivo > Delimitador é obrigatório");
		}
		if (UString.length(o.getDelimitador()) > 3) {
			throw new MessageException("O campo Importação Arquivo > Delimitador aceita no máximo 3 caracteres");
		}
		if (o.getEntidade() == null) {
			throw new MessageException("O campo Importação Arquivo > Entidade é obrigatório");
		}
		if (!o.isIgnorarUniquesAoPersistir()) {
			validarUniqueArquivoEntidade(o);
		}
		validar2(o);
		if (o.getProcessadosComErro() == null) {
			throw new MessageException("O campo Importação Arquivo > Processados com Erro é obrigatório");
		}
		if (o.getProcessadosComSucesso() == null) {
			throw new MessageException("O campo Importação Arquivo > Processados com Sucesso é obrigatório");
		}
		if (o.getStatus() == null) {
			throw new MessageException("O campo Importação Arquivo > Status é obrigatório");
		}
		if (o.getTotalDeLinhas() == null) {
			throw new MessageException("O campo Importação Arquivo > Total de Linhas é obrigatório");
		}
		validar3(o);
	}
	public void validarUniqueArquivoEntidade(final ImportacaoArquivo o) {
		ImportacaoArquivoSelect<?> select = select();
		if (o.getId() != null) select.ne(o);
		select.arquivo().eq(o.getArquivo());
		select.entidade().eq(o.getEntidade());
		if (select.exists()) {
			throw new MessageException(CONSTRAINTS_MESSAGES.get("importacaoarquivo_arquivo_entidade"));
		}
	}
	@Override
	public int getIdEntidade() {
		return IDSDefault.ImportacaoArquivo.idEntidade;
	}
	@Override
	protected void saveListas(final ImportacaoArquivo o, final MapSO map) {
		List<MapSO> list;
		final int id = o.getId();
		list = map.get("erros");
		if (list != null) {
			for (final MapSO item : list) {
				item.put("importacaoArquivo", id);
				if (item.isTrue("excluido")) {
					if (!item.isEmpty("id")) {
						importacaoArquivoErroService.delete(item.id());
					}
				} else {
					importacaoArquivoErroService.save(item);
				}
			}
		}
	}
	@Override
	protected void saveObservacoes(final ImportacaoArquivo o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}
	@Override
	public ResultadoConsulta consulta(final MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}
	@Override
	protected ResultadoConsulta consultaBase(final MapSO params, FTT<MapSO, ImportacaoArquivo> func) {
		final ImportacaoArquivoSelect<?> select = select();
		Integer pagina = params.getInt("pagina");
		String busca = params.getString("busca");
		if (!UString.isEmpty(busca)) select.busca().like(UString.toCampoBusca(busca));
		ResultadoConsulta result = new ResultadoConsulta();
		if (pagina == null) {
			result.registros = select.count();
		} else {
			select.page(pagina);
		}
		select.limit(30);
		Lst<ImportacaoArquivo> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}
	@Override
	protected ImportacaoArquivo buscaUnicoObrig(final MapSO params) {
		final ImportacaoArquivoSelect<?> select = select();
		Integer arquivo = getId(params, "arquivo");
		if (arquivo != null) {
			select.arquivo().id().eq(arquivo);
		}
		Integer arquivoDeErros = getId(params, "arquivoDeErros");
		if (arquivoDeErros != null) {
			select.arquivoDeErros().id().eq(arquivoDeErros);
		}
		Boolean atualizarRegistrosExistentes = params.get("atualizarRegistrosExistentes");
		if (atualizarRegistrosExistentes != null) select.atualizarRegistrosExistentes().eq(atualizarRegistrosExistentes);
		String delimitador = params.getString("delimitador");
		if (!UString.isEmpty(delimitador)) select.delimitador().eq(delimitador);
		Integer entidade = getId(params, "entidade");
		if (entidade != null) {
			select.entidade().id().eq(entidade);
		}
		Integer processadosComErro = params.get("processadosComErro");
		if (processadosComErro != null) select.processadosComErro().eq(processadosComErro);
		Integer processadosComSucesso = params.get("processadosComSucesso");
		if (processadosComSucesso != null) select.processadosComSucesso().eq(processadosComSucesso);
		Integer status = getId(params, "status");
		if (status != null) {
			select.status().eq(status);
		}
		Integer totalDeLinhas = params.get("totalDeLinhas");
		if (totalDeLinhas != null) select.totalDeLinhas().eq(totalDeLinhas);
		ImportacaoArquivo o = select.unique();
		if (o != null) {
			return o;
		}
		String s = "";
		if (arquivo != null) {
			s += "&& arquivo = '" + arquivo + "'";
		}
		if (arquivoDeErros != null) {
			s += "&& arquivoDeErros = '" + arquivoDeErros + "'";
		}
		if (atualizarRegistrosExistentes != null) {
			s += "&& atualizarRegistrosExistentes = '" + atualizarRegistrosExistentes + "'";
		}
		if (delimitador != null) {
			s += "&& delimitador = '" + delimitador + "'";
		}
		if (entidade != null) {
			s += "&& entidade = '" + entidade + "'";
		}
		if (processadosComErro != null) {
			s += "&& processadosComErro = '" + processadosComErro + "'";
		}
		if (processadosComSucesso != null) {
			s += "&& processadosComSucesso = '" + processadosComSucesso + "'";
		}
		if (status != null) {
			s += "&& status = '" + status + "'";
		}
		if (totalDeLinhas != null) {
			s += "&& totalDeLinhas = '" + totalDeLinhas + "'";
		}
		s = "Não foi encontrado um ImportacaoArquivo com os seguintes critérios:" + s.substring(2);
		throw new MessageException(s);
	}
	@Override
	public boolean auditar() {
		return true;
	}
	@Override
	protected ImportacaoArquivo setOld(final ImportacaoArquivo o) {
		ImportacaoArquivo old = newO();
		old.setArquivo(o.getArquivo());
		old.setArquivoDeErros(o.getArquivoDeErros());
		old.setAtualizarRegistrosExistentes(o.getAtualizarRegistrosExistentes());
		old.setDelimitador(o.getDelimitador());
		old.setEntidade(o.getEntidade());
		old.setProcessadosComErro(o.getProcessadosComErro());
		old.setProcessadosComSucesso(o.getProcessadosComSucesso());
		old.setStatus(o.getStatus());
		old.setTotalDeLinhas(o.getTotalDeLinhas());
		o.setOld(old);
		return o;
	}
	@Override
	protected boolean houveMudancas(final ImportacaoArquivo o) {
		ImportacaoArquivo old = o.getOld();
		if (o.getArquivo() != old.getArquivo()) return true;
		if (o.getArquivoDeErros() != old.getArquivoDeErros()) return true;
		if (o.getAtualizarRegistrosExistentes() != old.getAtualizarRegistrosExistentes()) return true;
		if (!UString.equals(o.getDelimitador(), old.getDelimitador())) return true;
		if (o.getEntidade() != old.getEntidade()) return true;
		if (o.getProcessadosComErro() != old.getProcessadosComErro()) return true;
		if (o.getProcessadosComSucesso() != old.getProcessadosComSucesso()) return true;
		if (o.getStatus() != old.getStatus()) return true;
		if (o.getTotalDeLinhas() != old.getTotalDeLinhas()) return true;
		return false;
	}
	@Override
	protected void registrarAuditoriaInsert(final ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUpdate(final ImportacaoArquivo o) {
		AuditoriaEntidadeBox auditoriaEntidadeBox = AuditoriaEntidadeBox.novoUpdate(getIdEntidade(), o.getId());
		ImportacaoArquivo old = o.getOld();
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivo.arquivo, old.getArquivo(), o.getArquivo());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivo.arquivoDeErros, old.getArquivoDeErros(), o.getArquivoDeErros());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivo.atualizarRegistrosExistentes, old.getAtualizarRegistrosExistentes(), o.getAtualizarRegistrosExistentes());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivo.delimitador, old.getDelimitador(), o.getDelimitador());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivo.entidade, old.getEntidade(), o.getEntidade());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivo.processadosComErro, old.getProcessadosComErro(), o.getProcessadosComErro());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivo.processadosComSucesso, old.getProcessadosComSucesso(), o.getProcessadosComSucesso());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivo.status, old.getStatus(), o.getStatus());
		auditoriaCampoService.registrar(auditoriaEntidadeBox, IDSDefault.ImportacaoArquivo.totalDeLinhas, old.getTotalDeLinhas(), o.getTotalDeLinhas());
	}
	@Override
	protected void registrarAuditoriaDelete(final ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaUndelete(final ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaBloqueio(final ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}
	@Override
	protected void registrarAuditoriaDesbloqueio(final ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}
	public static boolean statusAguardandoProcessamento(final ImportacaoArquivo o) {
		return o.getStatus() == ImportacaoArquivoStatusServiceAbstract.AGUARDANDO_PROCESSAMENTO;
	}
	public boolean statusAguardandoProcessamento(final int id) {
		return statusAguardandoProcessamento(find(id));
	}
	public static boolean statusEmAnalise(final ImportacaoArquivo o) {
		return o.getStatus() == ImportacaoArquivoStatusServiceAbstract.EM_ANALISE;
	}
	public boolean statusEmAnalise(final int id) {
		return statusEmAnalise(find(id));
	}
	public static boolean statusEmProcessamento(final ImportacaoArquivo o) {
		return o.getStatus() == ImportacaoArquivoStatusServiceAbstract.EM_PROCESSAMENTO;
	}
	public boolean statusEmProcessamento(final int id) {
		return statusEmProcessamento(find(id));
	}
	public static boolean statusProcessado(final ImportacaoArquivo o) {
		return o.getStatus() == ImportacaoArquivoStatusServiceAbstract.PROCESSADO;
	}
	public boolean statusProcessado(final int id) {
		return statusProcessado(find(id));
	}
	public ImportacaoArquivoSelect<?> select(final Boolean excluido) {
		ImportacaoArquivoSelect<?> o = new ImportacaoArquivoSelect<ImportacaoArquivoSelect<?>>(null, super.criterio(), null);
		if (excluido != null) {
			o.excluido().eq(excluido);
		}
		return o;
	}
	public ImportacaoArquivoSelect<?> select() {
		return select(false);
	}
	@Override
	protected void setBusca(final ImportacaoArquivo o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}
	@Override
	public String getText(final ImportacaoArquivo o) {
		if (o == null) return null;
		return o.getDelimitador();
	}
	@Override
	public ListString getTemplateImportacao() {
		ListString list = new ListString();
		list.add("ImportacaoArquivo");
		list.add("arquivo.nome;delimitador;atualizarRegistrosExistentes;entidade");
		return list;
	}

	static {
		CONSTRAINTS_MESSAGES.put("importacaoarquivo_arquivo_entidade", "A combinação de campos Arquivo + Entidade não pode se repetir. Já existe um registro com esta combinação.");
	}
}
