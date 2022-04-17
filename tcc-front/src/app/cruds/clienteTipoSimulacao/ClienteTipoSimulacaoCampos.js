/* tcc-java */
import ClienteTipoSimulacaoCamposAbstract from '../../auto/clienteTipoSimulacao/ClienteTipoSimulacaoCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteTipoSimulacaoCampos extends ClienteTipoSimulacaoCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteTipoSimulacaoCampos", () => new ClienteTipoSimulacaoCampos(), o => o.init());
	}
}
