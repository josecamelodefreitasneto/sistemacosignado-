/* front-constructor */
import ImportacaoArquivoErroConsultaAbstract from '../../auto/importacaoArquivoErro/ImportacaoArquivoErroConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ImportacaoArquivoErroConsulta extends ImportacaoArquivoErroConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ImportacaoArquivoErroConsulta", () => new ImportacaoArquivoErroConsulta(), o => o.init());
	}
}
