/* tcc-java */
import ClienteRubricasColsAbstract from '../../auto/cliente/ClienteRubricasColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteRubricasCols extends ClienteRubricasColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteRubricasCols", () => new ClienteRubricasCols(), o => o.init());
	}
}
