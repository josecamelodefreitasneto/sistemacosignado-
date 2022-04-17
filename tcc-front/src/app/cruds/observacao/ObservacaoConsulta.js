/* front-constructor */
import ObservacaoConsultaAbstract from '../../auto/observacao/ObservacaoConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ObservacaoConsulta extends ObservacaoConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ObservacaoConsulta", () => new ObservacaoConsulta(), o => o.init());
	}
}
