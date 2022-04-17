/* front-constructor */
import ImportacaoArquivoUtilsAbstract from '../../auto/importacaoArquivo/ImportacaoArquivoUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoUtils extends ImportacaoArquivoUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoUtils", () => new ImportacaoArquivoUtils(), o => o.init());
	}
}
