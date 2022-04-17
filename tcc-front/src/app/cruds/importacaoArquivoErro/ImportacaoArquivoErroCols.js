/* front-constructor */
import ImportacaoArquivoErroColsAbstract from '../../auto/importacaoArquivoErro/ImportacaoArquivoErroColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoErroCols extends ImportacaoArquivoErroColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoErroCols", () => new ImportacaoArquivoErroCols(), o => o.init());
	}
}
