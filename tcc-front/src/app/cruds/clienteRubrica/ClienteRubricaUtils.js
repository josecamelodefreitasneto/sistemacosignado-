/* tcc-java */
import ClienteRubricaUtilsAbstract from '../../auto/clienteRubrica/ClienteRubricaUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteRubricaUtils extends ClienteRubricaUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteRubricaUtils", () => new ClienteRubricaUtils(), o => o.init());
	}
}
