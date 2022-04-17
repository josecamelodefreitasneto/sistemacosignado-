/* tcc-java */
import ClienteTipoConsultaAbstract from '../../auto/clienteTipo/ClienteTipoConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteTipoConsulta extends ClienteTipoConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteTipoConsulta", () => new ClienteTipoConsulta(), o => o.init());
	}
}
