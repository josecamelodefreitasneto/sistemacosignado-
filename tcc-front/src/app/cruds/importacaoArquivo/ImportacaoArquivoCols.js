/* front-constructor */
import ImportacaoArquivoColsAbstract from '../../auto/importacaoArquivo/ImportacaoArquivoColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoCols extends ImportacaoArquivoColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoCols", () => new ImportacaoArquivoCols(), o => o.init());
	}
}
