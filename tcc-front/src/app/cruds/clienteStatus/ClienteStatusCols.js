/* tcc-java */
import ClienteStatusColsAbstract from '../../auto/clienteStatus/ClienteStatusColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteStatusCols extends ClienteStatusColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteStatusCols", () => new ClienteStatusCols(), o => o.init());
	}
}
