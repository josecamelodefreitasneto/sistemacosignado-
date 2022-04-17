/* tcc-java */
import ClienteSimulacaoCamposAbstract from '../../auto/clienteSimulacao/ClienteSimulacaoCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteSimulacaoCampos extends ClienteSimulacaoCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteSimulacaoCampos", () => new ClienteSimulacaoCampos(), o => o.init());
	}

}
