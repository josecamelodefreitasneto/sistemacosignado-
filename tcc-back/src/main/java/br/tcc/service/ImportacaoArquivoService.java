package br.tcc.service;

import br.tcc.model.Arquivo;
import br.tcc.model.ImportacaoArquivo;
import br.tcc.model.ImportacaoArquivoErro;
import br.tcc.outros.AuditoriaEntidadeBox;
import br.tcc.outros.FiltroConsulta;
import br.tcc.outros.IDSDefault;
import br.tcc.outros.ResultadoConsulta;
import br.tcc.outros.ServiceModelo;
import br.tcc.outros.ThreadScope;
import br.tcc.outros.ThreadScopeStart;
import br.tcc.select.ImportacaoArquivoErroSelect;
import br.tcc.select.ImportacaoArquivoSelect;
import br.tcc.service.ArquivoService;
import br.tcc.service.AuditoriaCampoService;
import br.tcc.service.EntidadeService;
import br.tcc.service.ImportacaoArquivoErroService;
import br.tcc.service.ImportacaoArquivoStatusService;
import br.tcc.service.ObservacaoService;
import gm.utils.comum.Lst;
import gm.utils.comum.UBoolean;
import gm.utils.exception.MessageException;
import gm.utils.lambda.FTT;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import gm.utils.classes.UClass;
import gm.utils.comum.USystem;

@Component
public class ImportacaoArquivoService extends ServiceModelo<ImportacaoArquivo> {

	@Autowired ArquivoService arquivoService;

	@Autowired EntidadeService entidadeService;

	@Autowired ImportacaoArquivoStatusService importacaoArquivoStatusService;

	@Autowired ImportacaoArquivoErroService importacaoArquivoErroService;

	@Autowired ObservacaoService observacaoService;

	@Autowired AuditoriaCampoService auditoriaCampoService;

	@Override
	public Class<ImportacaoArquivo> getClasse() {
		return ImportacaoArquivo.class;
	}

	@Override
	public MapSO toMap(ImportacaoArquivo o, final boolean listas) {
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

	private Lst<ImportacaoArquivoErro> getErros(ImportacaoArquivo o) {
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
	protected final void validar(ImportacaoArquivo o) {
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

	public void validarUniqueArquivoEntidade(ImportacaoArquivo o) {
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
	protected void saveListas(ImportacaoArquivo o, final MapSO map) {
		List<MapSO> list;
		final int id = o.getId();
		list = map.get("erros");
		if (list != null) {
			for (MapSO item : list) {
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
	protected void saveObservacoes(ImportacaoArquivo o, final MapSO map) {
		observacaoService.save(getIdEntidade(), o.getId(), map);
	}

	@Override
	public ResultadoConsulta consulta(MapSO params) {
		return consultaBase(params, o -> toMap(o, false));
	}

	@Override
	protected ResultadoConsulta consultaBase(Integer pagina, final List<Integer> ignorar, final String busca, final MapSO params, FTT<MapSO, ImportacaoArquivo> func) {
		final ImportacaoArquivoSelect<?> select = select(null);
		if (UString.notEmpty(busca)) {
			select.busca().like(busca);
		}
		if (!ignorar.isEmpty()) {
			select.id().notIn(ignorar);
		}
		FiltroConsulta.fk(params, "arquivo", select.arquivo());
		FiltroConsulta.fk(params, "arquivoDeErros", select.arquivoDeErros());
		FiltroConsulta.bool(params, "atualizarRegistrosExistentes", select.atualizarRegistrosExistentes());
		FiltroConsulta.string(params, "delimitador", select.delimitador());
		FiltroConsulta.fk(params, "entidade", select.entidade());
		FiltroConsulta.integer(params, "processadosComErro", select.processadosComErro());
		FiltroConsulta.integer(params, "processadosComSucesso", select.processadosComSucesso());
		FiltroConsulta.fk(params, "status", select.status());
		FiltroConsulta.integer(params, "totalDeLinhas", select.totalDeLinhas());
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
		Lst<ImportacaoArquivo> list = select.list();
		result.dados = list.map(o -> func.call(o));
		return result;
	}

	@Override
	protected ImportacaoArquivo buscaUnicoObrig(MapSO params) {
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
	protected ImportacaoArquivo setOld(ImportacaoArquivo o) {
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
	protected boolean houveMudancas(ImportacaoArquivo o) {
		ImportacaoArquivo old = o.getOld();
		if (o.getArquivo() != old.getArquivo()) {
			return true;
		}
		if (o.getArquivoDeErros() != old.getArquivoDeErros()) {
			return true;
		}
		if (o.getAtualizarRegistrosExistentes() != old.getAtualizarRegistrosExistentes()) {
			return true;
		}
		if (!UString.equals(o.getDelimitador(), old.getDelimitador())) {
			return true;
		}
		if (o.getEntidade() != old.getEntidade()) {
			return true;
		}
		if (o.getProcessadosComErro() != old.getProcessadosComErro()) {
			return true;
		}
		if (o.getProcessadosComSucesso() != old.getProcessadosComSucesso()) {
			return true;
		}
		if (o.getStatus() != old.getStatus()) {
			return true;
		}
		if (o.getTotalDeLinhas() != old.getTotalDeLinhas()) {
			return true;
		}
		return false;
	}

	@Override
	protected void registrarAuditoriaInsert(ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoInsert(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUpdate(ImportacaoArquivo o) {
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
	protected void registrarAuditoriaDelete(ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoDelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaUndelete(ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoUndelete(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaBloqueio(ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoBloqueio(getIdEntidade(), o.getId());
	}

	@Override
	protected void registrarAuditoriaDesbloqueio(ImportacaoArquivo o) {
		AuditoriaEntidadeBox.novoDesbloqueio(getIdEntidade(), o.getId());
	}

	public static boolean statusAguardandoProcessamento(ImportacaoArquivo o) {
		return o.getStatus() == ImportacaoArquivoStatusService.AGUARDANDO_PROCESSAMENTO;
	}

	public boolean statusAguardandoProcessamento(int id) {
		return statusAguardandoProcessamento(find(id));
	}

	public static boolean statusEmAnalise(ImportacaoArquivo o) {
		return o.getStatus() == ImportacaoArquivoStatusService.EM_ANALISE;
	}

	public boolean statusEmAnalise(int id) {
		return statusEmAnalise(find(id));
	}

	public static boolean statusEmProcessamento(ImportacaoArquivo o) {
		return o.getStatus() == ImportacaoArquivoStatusService.EM_PROCESSAMENTO;
	}

	public boolean statusEmProcessamento(int id) {
		return statusEmProcessamento(find(id));
	}

	public static boolean statusProcessado(ImportacaoArquivo o) {
		return o.getStatus() == ImportacaoArquivoStatusService.PROCESSADO;
	}

	public boolean statusProcessado(int id) {
		return statusProcessado(find(id));
	}

	public ImportacaoArquivoSelect<?> select(Boolean excluido) {
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
	protected void setBusca(ImportacaoArquivo o) {
		String s = getText(o);
		s = UString.toCampoBusca(s);
		o.setBusca(s);
	}

	@Override
	public String getText(ImportacaoArquivo o) {
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

	public static final String INICIAR_PROCESSAMENTO = "ImportacaoArquivoService.iniciarProcessamento";
	public static final String PROCESSAR_LINHA = "ImportacaoArquivoProcessador.processarLinha";

	@Autowired Jms jms;
	@Autowired ApplicationContext context;
	@Autowired ComandoService comandoService;
	@Autowired ThreadScopeStart threadScopeStart;
	@Autowired AuditoriaEntidadeService auditoriaEntidadeService;
	@Autowired ImportacaoArquivoProcessador importacaoArquivoProcessador;

	private static int idComando = 0;

	private int getIdComando() {
		if (idComando == 0) {
			idComando = comandoService.getId(getIdEntidade(), "processar");
		}
		return idComando;
	}
	@Override
	protected void beforeInsert(ImportacaoArquivo o) {
		if (o.getEntidade() == null) {
			throw new MessageException("Campo entidade é obrigatório!");
		}
		ListString list = loadArquivo(o);
		if (list.isEmpty()) {
			throw new MessageException("Arquivo não contém dados!");
		}
		if (!list.get(0).contentEquals(o.getEntidade().getNomeClasse())) {
			throw new MessageException("O arquivo não pertence a esta tabela!");
		}
		o.setStatus(ImportacaoArquivoStatusService.AGUARDANDO_PROCESSAMENTO);
	}

	@Override
	protected void afterInsert(ImportacaoArquivo o) {
//		ThreadScope.addOnSuccess(() -> importacaoArquivoProcessador.processar(o.getId()));
		jms.send(INICIAR_PROCESSAMENTO, o.getId());
	}

	@Override
	protected void beforeUpdate(ImportacaoArquivo o) {
		if (statusEmProcessamento(o) && o.getProcessadosComSucesso() + o.getProcessadosComErro() == o.getTotalDeLinhas()) {
			o.setStatus(ImportacaoArquivoStatusService.PROCESSADO);
		}
	}

	public int getLoginInsert(int id) {
		return auditoriaEntidadeService.getLoginInsert(getIdEntidade(), id);
	}

	public ListString loadArquivo(int id) {
		return loadArquivo(find(id));
	}

	private ListString loadArquivo(ImportacaoArquivo o) {
		Arquivo arquivo = o.getArquivo();
		ListString list = new ListString().load(arquivoService.getFileName(arquivo));
		return list;
	}

	@Transactional
	public void marcaComoProcessado(int id) {
		ImportacaoArquivo o = find(id);
		o.setStatus(ImportacaoArquivoStatusService.PROCESSADO);
		save(o);
	}

	@Transactional
	public void marcaComoProcessado(int id, Map<Integer, String> erros) {
		start(id);
		ImportacaoArquivo o = find(id);
		o.setStatus(ImportacaoArquivoStatusService.PROCESSADO);
		o.setProcessadosComSucesso(o.getTotalDeLinhas() - erros.size());
		o.setProcessadosComErro(erros.size());
		save(o);
		for (int linha : erros.keySet()) {
			importacaoArquivoErroService.add(o, linha, erros.get(linha));
		}
	}


	@Transactional
	public void marcaComoProcessado(int id, int sucessos, int erros) {
		ImportacaoArquivo o = find(id);
		o.setStatus(ImportacaoArquivoStatusService.PROCESSADO);
		o.setProcessadosComSucesso(sucessos);
		o.setProcessadosComErro(erros);
		save(o);
	}

	@Transactional
	public void marcaComoProcessado(int id, ListString erros) {
		ImportacaoArquivo o = find(id);
		o.setArquivoDeErros(arquivoService.get(erros, "erros.txt"));
		o.setStatus(ImportacaoArquivoStatusService.PROCESSADO);
		save(o);
	}

	public ServiceModelo<?> getServico(int id) {
		ImportacaoArquivo o = find(id);
		Class<? extends ServiceModelo<?>> classe = UClass.getClassObrig("br.impl.service." + o.getEntidade().getNomeClasse() + "Service");
		ServiceModelo<?> service = context.getBean(classe);
		return service;
	}

	public String getDelimitador(int id) {
		return find(id).getDelimitador();
	}

	@Transactional
	public void setSucessos(int id, int value) {
		ImportacaoArquivo o = find(id);
		o.setProcessadosComSucesso(value);
		getEntityServiceBox().persist(o);
	}

	@Transactional
	public void setErros(int id, int value) {
		ImportacaoArquivo o = find(id);
		o.setProcessadosComErro(value);
		getEntityServiceBox().persist(o);
	}

	@Transactional
	public void gerarArquivoDeErros(int id) {

		ImportacaoArquivo o = find(id);

		ImportacaoArquivoErroSelect<?> select = importacaoArquivoErroService.select();
		select.importacaoArquivo().eq(o);
		select.erro().id().ne(ImportacaoArquivoErroMensagemService.LINHA_REPETIDA_ID);
		Lst<ImportacaoArquivoErro> erros = select.list();

		ListString arquivo = loadArquivo(o);

		ListString list = new ListString();
		list.add(arquivo.remove(0));
		list.add(arquivo.get(0));

		for (ImportacaoArquivoErro erro : erros) {
			list.add(arquivo.get(erro.getLinha()));
		}

		o.setArquivoDeErros(arquivoService.get(list, "erros.csv"));
		save(o);

	}

	@Transactional
	public void processar(importacaoArquivoPreProcessamento preProcessamento) {

		start(preProcessamento.id);

		for (MapSO map : preProcessamento.validados) {
			map.put("ignorarUniquesAoPersistir", true);
			try {
				preProcessamento.service.save(map);
			} catch (Exception e) {
				preProcessamento.linhaErro = map.getIntObrig("numero-linha");
				preProcessamento.erros.put(map.getIntObrig("numero-linha"), getMessage(e));
				ThreadScope.dispose();
				throw e;
			}
		}

//		o.setStatus(importacaoArquivoStatusService.processado());
//		o.setProcessadosComErro(o.getProcessadosComErro() + erros);
//		o.setProcessadosComSucesso(o.getProcessadosComSucesso() + sucessos);
//		save(o);

		ThreadScope.finalizarComSucesso();
		ThreadScope.dispose();

	}

	private String getMessage(Exception e) {

		if (e.getCause() instanceof PersistenceException) {
			PersistenceException pe = (PersistenceException) e.getCause();
			if (pe.getCause() instanceof ConstraintViolationException) {
				ConstraintViolationException ce = (ConstraintViolationException) pe.getCause();
				String s = ce.getConstraintName();
				s = UString.afterFirst(s, "_");
				s = CONSTRAINTS_MESSAGES.get(s);
				if (UString.notEmpty(s)) {
					return s;
				}
			}
		}

		return e.getMessage();
	}
	@Transactional
	public ListString getList(int id) {

		if (statusProcessado(id)) {
			return null;
		}

		start(id);

		ListString list = loadArquivo(id);

		if (list.isEmpty()) {
			marcaComoProcessado(id);
			return null;
		}

		ImportacaoArquivo o = find(id);
		o.setStatus(ImportacaoArquivoStatusService.EM_ANALISE);
		o.setTotalDeLinhas(list.size()-2);
		save(o);

		ThreadScope.finalizarComSucesso();
		ThreadScope.dispose();

		return list;

	}

	@Transactional
	public importacaoArquivoPreProcessamento preProcessar(int id, ListString list) {

		try {

			start(id);

			list.remove(0);
			String delimitador = getDelimitador(id);
			String linhaCampos = list.remove(0);

			importacaoArquivoPreProcessamento pre = new importacaoArquivoPreProcessamento();
			pre.id = id;
			pre.service = getServico(id);

			int numeroLinha = 0;
			ListString linhaJaProcessadas = new ListString();

			ListString campos = ListString.split(linhaCampos, delimitador);
			campos.trimPlus();

			ImportacaoArquivo o = find(id);
			int erros = 0;

			for (String s : list) {

				s = s.replace(delimitador + delimitador, delimitador + "null" + delimitador);
				if (s.endsWith(delimitador)) {
					s += "null";
				}

				numeroLinha++;

				if (linhaJaProcessadas.contains(s)) {
					pre.erros.put(numeroLinha, ImportacaoArquivoErroMensagemService.LINHA_REPETIDA_TEXT);
					continue;
				}

				linhaJaProcessadas.add(s);

				ListString values = ListString.split(s, delimitador);
				if (values.size() != campos.size()) {
					pre.erros.put(numeroLinha, "numero de campos (" + values.size() + ") diverge do cabeçalho (" + campos.size() + ")");
					continue;
				}

				MapSO map = new MapSO();

				add(campos.copy(), values, map);

				try {
					pre.service.validar(map);
					map.put("numero-linha", numeroLinha);
					pre.validados.add(map);
				} catch (Exception e) {
					e.printStackTrace();
					pre.erros.put(numeroLinha, e.getMessage());
				}

			}

			o.setStatus(ImportacaoArquivoStatusService.EM_PROCESSAMENTO);
			o.setProcessadosComErro(erros);
			save(o);

			ThreadScope.finalizarComSucesso();
			ThreadScope.dispose();

			return pre;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	private void start(int id) {
		int login = getLoginInsert(id);
		int comando = getIdComando();
		ThreadScope.dispose();
		USystem.sleepSegundos(1);//para evitar a duplicidade do registro de transacao
		threadScopeStart.start(login, comando);
	}

	private void add(ListString campos, ListString values, MapSO map) {

		while (!campos.isEmpty()) {

			String campo = campos.remove(0);

			if (campo.contains(".")) {
				String chave = UString.beforeFirst(campo, ".");
				ListString newCampos = new ListString();
				newCampos.add(UString.afterFirst(campo, "."));
				while (!campos.isEmpty() && campos.get(0).startsWith(chave + ".")) {
					newCampos.add(UString.afterFirst(campos.remove(0), "."));
				}
				ListString newValues = new ListString();
				for (int i = 0; i < newCampos.size(); i++) {
					newValues.add(values.remove(0));
				}
				if (!newValues.toString("").replace("null", "").isEmpty()) {
					MapSO newMap = new MapSO();
					add(newCampos, newValues, newMap);
					map.put(chave, newMap);
				}
			} else {
				String value = values.remove(0);
				if ("null".contentEquals(value)) {
					map.put(campo, null);
				} else {
					map.put(campo, value);
				}
			}
		}
	}

	static {
		CONSTRAINTS_MESSAGES.put("importacaoarquivo_arquivo_entidade", "Este aquivo já foi importado anteriormente!");
	}
}
