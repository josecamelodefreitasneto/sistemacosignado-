/* tcc-java */
import IdText from '../../misc/utils/IdText';

export default class ClienteTipoConstantes {
	static getList() {
		return ClienteTipoConstantes.list;
	}
}
ClienteTipoConstantes.SERVIDOR = 1;
ClienteTipoConstantes.PENSIONISTA = 2;
ClienteTipoConstantes.list = [
	new IdText(ClienteTipoConstantes.SERVIDOR, "Servidor"),
	new IdText(ClienteTipoConstantes.PENSIONISTA, "Pensionista")
];
