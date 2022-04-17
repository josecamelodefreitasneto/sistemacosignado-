/* front-constructor */
import ImportacaoArquivoErroUtilsAbstract from '../../auto/importacaoArquivoErro/ImportacaoArquivoErroUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoErroUtils extends ImportacaoArquivoErroUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoErroUtils", () => new ImportacaoArquivoErroUtils(), o => o.init());
	}
}
