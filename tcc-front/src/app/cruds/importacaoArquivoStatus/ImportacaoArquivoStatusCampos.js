/* front-constructor */
import ImportacaoArquivoStatusCamposAbstract from '../../auto/importacaoArquivoStatus/ImportacaoArquivoStatusCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoStatusCampos extends ImportacaoArquivoStatusCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoStatusCampos", () => new ImportacaoArquivoStatusCampos(), o => o.init());
	}
}
