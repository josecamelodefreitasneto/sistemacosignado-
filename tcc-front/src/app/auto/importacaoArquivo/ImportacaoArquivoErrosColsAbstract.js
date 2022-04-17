/* front-constructor */
import Coluna from '../../../fc/components/tabela/Coluna';
import ImportacaoArquivoErroCols from '../../cruds/importacaoArquivoErro/ImportacaoArquivoErroCols';
import Sessao from '../../../projeto/Sessao';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UIdText from '../../misc/utils/UIdText';
import UInteger from '../../misc/utils/UInteger';

export default class ImportacaoArquivoErrosColsAbstract {

	LINHA;
	ERRO;
	list;
	grupos;
	init() {
		this.checkInstance();
		this.LINHA = new Coluna(90, "Linha", o => o.getLinha(), TextAlign.center).setSort((a, b) => UInteger.compareToInt(a.getLinha(), b.getLinha())).setGrupo(false).setId("ImportacaoArquivo-ErrosCols-linha");
		this.ERRO = new Coluna(300, "Erro", o => o.getErro(), TextAlign.left).setSort((a, b) => UIdText.compareText(a.getErro(), b.getErro())).setGrupo(false).setId("ImportacaoArquivo-ErrosCols-erro");
		this.list = [this.LINHA, this.ERRO];
		this.grupos = [];
		let principal = ImportacaoArquivoErroCols.getInstance();
		this.LINHA.renderItem = principal.LINHA.renderItem;
		this.ERRO.renderItem = principal.ERRO.renderItem;
		this.init2();
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ImportacaoArquivoErrosCols", this);
	}
}
