/* tcc-java */
import ClienteTipoSimulacaoUtilsAbstract from '../../auto/clienteTipoSimulacao/ClienteTipoSimulacaoUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteTipoSimulacaoUtils extends ClienteTipoSimulacaoUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteTipoSimulacaoUtils", () => new ClienteTipoSimulacaoUtils(), o => o.init());
	}
}
