/* tcc-java */
import Sessao from '../../../projeto/Sessao';
import TelefoneCamposAbstract from '../../auto/telefone/TelefoneCamposAbstract';
import UTelefone from '../../misc/utils/UTelefone';

export default class TelefoneCampos extends TelefoneCamposAbstract {

	init2() {
		this.ddd.addFunctionObserver(() => this.ajustarNome());
		this.numero.addFunctionObserver(() => this.ajustarNome());
	}

	ajustarNome() {
		this.nome.set(UTelefone.formatComDdd(this.ddd.get(), this.numero.get()));
	}

	static getInstance() {
		return Sessao.getInstance("TelefoneCampos", () => new TelefoneCampos(), o => o.init());
	}
}
