/* front-constructor */
import ImportacaoArquivoStatusColsAbstract from '../../auto/importacaoArquivoStatus/ImportacaoArquivoStatusColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoStatusCols extends ImportacaoArquivoStatusColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoStatusCols", () => new ImportacaoArquivoStatusCols(), o => o.init());
	}
}
