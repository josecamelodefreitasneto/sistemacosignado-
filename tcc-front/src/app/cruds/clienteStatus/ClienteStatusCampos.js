/* tcc-java */
import ClienteStatusCamposAbstract from '../../auto/clienteStatus/ClienteStatusCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteStatusCampos extends ClienteStatusCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteStatusCampos", () => new ClienteStatusCampos(), o => o.init());
	}
}
