/* front-constructor */
import ImportacaoArquivoStatusConsultaAbstract from '../../auto/importacaoArquivoStatus/ImportacaoArquivoStatusConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoStatusConsulta extends ImportacaoArquivoStatusConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoStatusConsulta", () => new ImportacaoArquivoStatusConsulta(), o => o.init());
	}
}
