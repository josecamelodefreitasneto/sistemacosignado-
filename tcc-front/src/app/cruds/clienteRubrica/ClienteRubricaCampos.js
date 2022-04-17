/* tcc-java */
import ClienteRubricaCamposAbstract from '../../auto/clienteRubrica/ClienteRubricaCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteRubricaCampos extends ClienteRubricaCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteRubricaCampos", () => new ClienteRubricaCampos(), o => o.init());
	}
}
