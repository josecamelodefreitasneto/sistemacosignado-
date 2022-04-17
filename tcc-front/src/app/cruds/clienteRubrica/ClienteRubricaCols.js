/* tcc-java */
import ClienteRubricaColsAbstract from '../../auto/clienteRubrica/ClienteRubricaColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteRubricaCols extends ClienteRubricaColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteRubricaCols", () => new ClienteRubricaCols(), o => o.init());
	}
}
