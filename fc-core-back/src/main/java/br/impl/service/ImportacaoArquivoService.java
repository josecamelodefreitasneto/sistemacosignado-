package br.impl.service;

import java.util.Map;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.auto.model.Arquivo;
import br.auto.model.ImportacaoArquivo;
import br.auto.model.ImportacaoArquivoErro;
import br.auto.select.ImportacaoArquivoErroSelect;
import br.auto.service.ImportacaoArquivoServiceAbstract;
import br.auto.service.ImportacaoArquivoStatusServiceAbstract;
import br.impl.outros.ServiceModelo;
import br.impl.outros.ThreadScope;
import br.impl.outros.ThreadScopeStart;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.USystem;
import gm.utils.exception.MessageException;
import gm.utils.map.MapSO;
import gm.utils.string.ListString;
import gm.utils.string.UString;

@Component
public class ImportacaoArquivoService extends ImportacaoArquivoServiceAbstract {
	
	public static final String INICIAR_PROCESSAMENTO = "ImportacaoArquivoService.iniciarProcessamento";
	public static final String PROCESSAR_LINHA = "ImportacaoArquivoProcessador.processarLinha";

	@Autowired Jms jms;
	@Autowired ApplicationContext context;
	@Autowired ArquivoService arquivoService;
	@Autowired ComandoService comandoService;
	@Autowired ThreadScopeStart threadScopeStart;
	@Autowired AuditoriaEntidadeService auditoriaEntidadeService;
	@Autowired ImportacaoArquivoProcessador importacaoArquivoProcessador;
	@Autowired ImportacaoArquivoStatusService importacaoArquivoStatusService;

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
		o.setStatus(ImportacaoArquivoStatusServiceAbstract.AGUARDANDO_PROCESSAMENTO);
	}
	
	@Override
	protected void afterInsert(ImportacaoArquivo o) {
//		ThreadScope.addOnSuccess(() -> importacaoArquivoProcessador.processar(o.getId()));
		jms.send(INICIAR_PROCESSAMENTO, o.getId());
	}
	
	@Override
	protected void beforeUpdate(ImportacaoArquivo o) {
		if (statusEmProcessamento(o) && o.getProcessadosComSucesso() + o.getProcessadosComErro() == o.getTotalDeLinhas()) {
			o.setStatus(ImportacaoArquivoStatusServiceAbstract.PROCESSADO);
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
		o.setStatus(ImportacaoArquivoStatusServiceAbstract.PROCESSADO);
		save(o);
	}
	
	@Transactional
	public void marcaComoProcessado(int id, Map<Integer, String> erros) {
		start(id);
		ImportacaoArquivo o = find(id);
		o.setStatus(ImportacaoArquivoStatusServiceAbstract.PROCESSADO);
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
		o.setStatus(ImportacaoArquivoStatusServiceAbstract.PROCESSADO);
		o.setProcessadosComSucesso(sucessos);
		o.setProcessadosComErro(erros);
		save(o);
	}
	
	@Transactional
	public void marcaComoProcessado(int id, ListString erros) {
		ImportacaoArquivo o = find(id);
		o.setArquivoDeErros(arquivoService.get(erros, "erros.txt"));
		o.setStatus(ImportacaoArquivoStatusServiceAbstract.PROCESSADO);
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
		o.setStatus(ImportacaoArquivoStatusServiceAbstract.EM_ANALISE);
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
			
			o.setStatus(ImportacaoArquivoStatusServiceAbstract.EM_PROCESSAMENTO);
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
