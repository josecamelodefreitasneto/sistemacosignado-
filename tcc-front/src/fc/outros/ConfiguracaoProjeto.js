/* front-constructor */
import Servico from '../../projeto/Servico';
import UCommons from '../../app/misc/utils/UCommons';

export default class ConfiguracaoProjeto {

	static instance;
	static urlBase;

	entidades;

	constructor() {
		if (UCommons.isEmpty(ConfiguracaoProjeto.instance)) {
			ConfiguracaoProjeto.instance = this;
			this.entidades = this.startEntidades();
			if (UCommons.isEmpty(Servico.creator)) {}
		}
	}

	getEntidades() {
		return this.entidades;
	}

	static getInstance() {
		return ConfiguracaoProjeto.instance;
	}

}
