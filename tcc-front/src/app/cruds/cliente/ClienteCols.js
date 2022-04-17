/* tcc-java */
import ClienteColsAbstract from '../../auto/cliente/ClienteColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteCols extends ClienteColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteCols", () => new ClienteCols(), o => o.init());
	}
}
