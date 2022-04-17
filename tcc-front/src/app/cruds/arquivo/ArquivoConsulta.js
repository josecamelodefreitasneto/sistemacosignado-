/* front-constructor */
import ArquivoConsultaAbstract from '../../auto/arquivo/ArquivoConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ArquivoConsulta extends ArquivoConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ArquivoConsulta", () => new ArquivoConsulta(), o => o.init());
	}
}
