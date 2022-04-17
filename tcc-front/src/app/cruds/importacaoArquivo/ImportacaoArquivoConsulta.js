/* front-constructor */
import ImportacaoArquivoConsultaAbstract from '../../auto/importacaoArquivo/ImportacaoArquivoConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoConsulta extends ImportacaoArquivoConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoConsulta", () => new ImportacaoArquivoConsulta(), o => o.init());
	}
}
