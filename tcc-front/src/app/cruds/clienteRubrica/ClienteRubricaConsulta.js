/* tcc-java */
import ClienteRubricaConsultaAbstract from '../../auto/clienteRubrica/ClienteRubricaConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteRubricaConsulta extends ClienteRubricaConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteRubricaConsulta", () => new ClienteRubricaConsulta(), o => o.init());
	}
}
