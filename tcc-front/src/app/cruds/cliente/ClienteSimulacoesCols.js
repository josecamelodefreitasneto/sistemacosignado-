/* tcc-java */
import ClienteSimulacoesColsAbstract from '../../auto/cliente/ClienteSimulacoesColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteSimulacoesCols extends ClienteSimulacoesColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteSimulacoesCols", () => new ClienteSimulacoesCols(), o => o.init());
	}
}
