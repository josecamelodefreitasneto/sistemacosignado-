/* tcc-java */
import ClienteSimulacaoColsAbstract from '../../auto/clienteSimulacao/ClienteSimulacaoColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteSimulacaoCols extends ClienteSimulacaoColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteSimulacaoCols", () => new ClienteSimulacaoCols(), o => o.init());
	}
}
