/* tcc-java */
import ClienteTipoCamposAbstract from '../../auto/clienteTipo/ClienteTipoCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteTipoCampos extends ClienteTipoCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteTipoCampos", () => new ClienteTipoCampos(), o => o.init());
	}
}
