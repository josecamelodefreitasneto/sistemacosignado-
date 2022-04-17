/* tcc-java */
import ClienteConsultaAbstract from '../../auto/cliente/ClienteConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteConsulta extends ClienteConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteConsulta", () => new ClienteConsulta(), o => o.init());
	}
}
