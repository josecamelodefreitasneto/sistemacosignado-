/* tcc-java */
import ClienteUtilsAbstract from '../../auto/cliente/ClienteUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteUtils extends ClienteUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteUtils", () => new ClienteUtils(), o => o.init());
	}
}
