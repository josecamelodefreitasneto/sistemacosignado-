/* front-constructor */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UBoolean from '../../misc/utils/UBoolean';
import UIdText from '../../misc/utils/UIdText';
import UInteger from '../../misc/utils/UInteger';
import UString from '../../misc/utils/UString';

export default class ImportacaoArquivoColsAbstract {

	ARQUIVO;
	DELIMITADOR;
	ATUALIZAR_REGISTROS_EXISTENTES;
	ENTIDADE;
	STATUS;
	TOTAL_DE_LINHAS;
	PROCESSADOS_COM_SUCESSO;
	PROCESSADOS_COM_ERRO;
	ARQUIVO_DE_ERROS;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.ARQUIVO = new Coluna(300, "Arquivo", o => o.getArquivo(), TextAlign.left).setSort((a, b) => 0).setGrupo(false).setId("ImportacaoArquivo-Cols-arquivo");
		this.DELIMITADOR = new Coluna(6, "Delimitador", o => o.getDelimitador(), TextAlign.left).setSort((a, b) => UString.compare(a.getDelimitador(), b.getDelimitador())).setGrupo(false).setId("ImportacaoArquivo-Cols-delimitador");
		this.ATUALIZAR_REGISTROS_EXISTENTES = new Coluna(100, "Atualizar Registros Existentes", o => o.getAtualizarRegistrosExistentes(), TextAlign.center).setSort((a, b) => UBoolean.compare(a.getAtualizarRegistrosExistentes(), b.getAtualizarRegistrosExistentes())).setGrupo(false).setId("ImportacaoArquivo-Cols-atualizarRegistrosExistentes");
		this.ENTIDADE = new Coluna(300, "Entidade", o => o.getEntidade(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getEntidade(), b.getEntidade())).setGrupo(true).setId("ImportacaoArquivo-Cols-entidade");
		this.STATUS = new Coluna(300, "Status", o => o.getStatus(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getStatus(), b.getStatus())).setGrupo(true).setId("ImportacaoArquivo-Cols-status");
		this.TOTAL_DE_LINHAS = new Coluna(45, "Total de Linhas", o => o.getTotalDeLinhas(), TextAlign.center).setSort((a, b) => UInteger.compareToInt(a.getTotalDeLinhas(), b.getTotalDeLinhas())).setGrupo(true).setId("ImportacaoArquivo-Cols-totalDeLinhas");
		this.PROCESSADOS_COM_SUCESSO = new Coluna(45, "Processados com Sucesso", o => o.getProcessadosComSucesso(), TextAlign.center).setSort((a, b) => UInteger.compareToInt(a.getProcessadosComSucesso(), b.getProcessadosComSucesso())).setGrupo(true).setId("ImportacaoArquivo-Cols-processadosComSucesso");
		this.PROCESSADOS_COM_ERRO = new Coluna(45, "Processados com Erro", o => o.getProcessadosComErro(), TextAlign.center).setSort((a, b) => UInteger.compareToInt(a.getProcessadosComErro(), b.getProcessadosComErro())).setGrupo(true).setId("ImportacaoArquivo-Cols-processadosComErro");
		this.ARQUIVO_DE_ERROS = new Coluna(300, "Arquivo de Erros", o => o.getArquivoDeErros(), TextAlign.left).setSort((a, b) => 0).setGrupo(true).setId("ImportacaoArquivo-Cols-arquivoDeErros");
		this.list = [this.ARQUIVO, this.DELIMITADOR, this.ATUALIZAR_REGISTROS_EXISTENTES, this.ENTIDADE, this.STATUS, this.TOTAL_DE_LINHAS, this.PROCESSADOS_COM_SUCESSO, this.PROCESSADOS_COM_ERRO, this.ARQUIVO_DE_ERROS];
		this.grupos = [new Coluna(1035, "Dados de Progresso", null, TextAlign.center).setCols(6)];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ImportacaoArquivoCols", this);
	}
}
