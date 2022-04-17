/* tcc-java */
import AtendenteConsultaAbstract from '../../auto/atendente/AtendenteConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class AtendenteConsulta extends AtendenteConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("AtendenteConsulta", () => new AtendenteConsulta(), o => o.init());
	}
}
