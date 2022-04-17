/* tcc-java */
import ClienteSimulacaoConsultaAbstract from '../../auto/clienteSimulacao/ClienteSimulacaoConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteSimulacaoConsulta extends ClienteSimulacaoConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteSimulacaoConsulta", () => new ClienteSimulacaoConsulta(), o => o.init());
	}
}
