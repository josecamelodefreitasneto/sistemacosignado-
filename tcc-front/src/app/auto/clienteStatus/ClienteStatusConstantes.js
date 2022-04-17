/* tcc-java */
import IdText from '../../misc/utils/IdText';

export default class ClienteStatusConstantes {
	static getList() {
		return ClienteStatusConstantes.list;
	}
}
ClienteStatusConstantes.EM_ATENDIMENTO = 1;
ClienteStatusConstantes.EMPRESTIMO_REALIZADO = 2;
ClienteStatusConstantes.NAO_TEM_INTERESSE = 3;
ClienteStatusConstantes.list = [
	new IdText(ClienteStatusConstantes.EM_ATENDIMENTO, "Em atendimento"),
	new IdText(ClienteStatusConstantes.EMPRESTIMO_REALIZADO, "Empréstimo Realizado"),
	new IdText(ClienteStatusConstantes.NAO_TEM_INTERESSE, "Não tem interesse")
];
