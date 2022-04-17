/* front-constructor */
import ImportacaoArquivoStatusUtilsAbstract from '../../auto/importacaoArquivoStatus/ImportacaoArquivoStatusUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoStatusUtils extends ImportacaoArquivoStatusUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoStatusUtils", () => new ImportacaoArquivoStatusUtils(), o => o.init());
	}
}
