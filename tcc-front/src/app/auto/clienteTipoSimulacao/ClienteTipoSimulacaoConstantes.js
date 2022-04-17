/* tcc-java */
import IdText from '../../misc/utils/IdText';

export default class ClienteTipoSimulacaoConstantes {
	static getList() {
		return ClienteTipoSimulacaoConstantes.list;
	}
}
ClienteTipoSimulacaoConstantes.PELO_VALOR_DA_PARCELA = 1;
ClienteTipoSimulacaoConstantes.PELO_VALOR_DO_EMPRESTIMO = 2;
ClienteTipoSimulacaoConstantes.list = [
	new IdText(ClienteTipoSimulacaoConstantes.PELO_VALOR_DA_PARCELA, "Pelo valor da parcela"),
	new IdText(ClienteTipoSimulacaoConstantes.PELO_VALOR_DO_EMPRESTIMO, "Pelo valor do empréstimo")
];
