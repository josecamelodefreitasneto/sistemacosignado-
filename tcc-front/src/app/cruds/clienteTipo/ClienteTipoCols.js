/* tcc-java */
import ClienteTipoColsAbstract from '../../auto/clienteTipo/ClienteTipoColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ClienteTipoCols extends ClienteTipoColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ClienteTipoCols", () => new ClienteTipoCols(), o => o.init());
	}
}
