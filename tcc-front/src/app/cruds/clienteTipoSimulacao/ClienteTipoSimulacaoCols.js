/* tcc-java */
import ClienteTipoSimulacaoColsAbstract from '../../auto/clienteTipoSimulacao/ClienteTipoSimulacaoColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteTipoSimulacaoCols extends ClienteTipoSimulacaoColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteTipoSimulacaoCols", () => new ClienteTipoSimulacaoCols(), o => o.init());
	}
}
