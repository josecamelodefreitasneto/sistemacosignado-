/* tcc-java */
import ClienteTipoUtilsAbstract from '../../auto/clienteTipo/ClienteTipoUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteTipoUtils extends ClienteTipoUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteTipoUtils", () => new ClienteTipoUtils(), o => o.init());
	}
}
