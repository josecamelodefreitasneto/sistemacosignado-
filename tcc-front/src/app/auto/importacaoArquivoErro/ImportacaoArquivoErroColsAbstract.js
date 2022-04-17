/* front-constructor */
import Coluna from '../../../fc/components/tabela/Coluna';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UIdText from '../../misc/utils/UIdText';
import UInteger from '../../misc/utils/UInteger';

export default class ImportacaoArquivoErroColsAbstract {

	IMPORTACAO_ARQUIVO;
	LINHA;
	ERRO;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.IMPORTACAO_ARQUIVO = new Coluna(300, "Importação Arquivo", o => o.getImportacaoArquivo(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getImportacaoArquivo(), b.getImportacaoArquivo())).setGrupo(false).setId("ImportacaoArquivoErro-Cols-importacaoArquivo");
		this.LINHA = new Coluna(90, "Linha", o => o.getLinha(), TextAlign.center).setSort((a, b) => UInteger.compareToInt(a.getLinha(), b.getLinha())).setGrupo(false).setId("ImportacaoArquivoErro-Cols-linha");
		this.ERRO = new Coluna(300, "Erro", o => o.getErro(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getErro(), b.getErro())).setGrupo(false).setId("ImportacaoArquivoErro-Cols-erro");
		this.list = [this.IMPORTACAO_ARQUIVO, this.LINHA, this.ERRO];
		this.grupos = [];
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ImportacaoArquivoErroCols", this);
	}
}
