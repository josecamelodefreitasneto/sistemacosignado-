/* front-constructor */
import ImportacaoArquivoErroCamposAbstract from '../../auto/importacaoArquivoErro/ImportacaoArquivoErroCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoErroCampos extends ImportacaoArquivoErroCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoErroCampos", () => new ImportacaoArquivoErroCampos(), o => o.init());
	}
}
