/* tcc-java */
import ClienteSimulacaoUtilsAbstract from '../../auto/clienteSimulacao/ClienteSimulacaoUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteSimulacaoUtils extends ClienteSimulacaoUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteSimulacaoUtils", () => new ClienteSimulacaoUtils(), o => o.init());
	}
}
