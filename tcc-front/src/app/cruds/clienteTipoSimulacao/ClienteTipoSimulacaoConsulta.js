/* tcc-java */
import ClienteTipoSimulacaoConsultaAbstract from '../../auto/clienteTipoSimulacao/ClienteTipoSimulacaoConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteTipoSimulacaoConsulta extends ClienteTipoSimulacaoConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteTipoSimulacaoConsulta", () => new ClienteTipoSimulacaoConsulta(), o => o.init());
	}
}
