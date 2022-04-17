/* tcc-java */
import ClienteStatusConsultaAbstract from '../../auto/clienteStatus/ClienteStatusConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteStatusConsulta extends ClienteStatusConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteStatusConsulta", () => new ClienteStatusConsulta(), o => o.init());
	}
}
