/* front-constructor */
import ImportacaoArquivoErrosColsAbstract from '../../auto/importacaoArquivo/ImportacaoArquivoErrosColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoErrosCols extends ImportacaoArquivoErrosColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoErrosCols", () => new ImportacaoArquivoErrosCols(), o => o.init());
	}
}
