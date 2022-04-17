/* tcc-java */
import ClienteStatusUtilsAbstract from '../../auto/clienteStatus/ClienteStatusUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteStatusUtils extends ClienteStatusUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteStatusUtils", () => new ClienteStatusUtils(), o => o.init());
	}
}
