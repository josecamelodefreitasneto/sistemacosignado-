/* react */
import BindingList from './BindingList';
import BindingString from './BindingString';
import UCep from '../../misc/utils/UCep';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class BindingCep extends BindingString {

	encontradoNosCorreios = false;
	buscando = false;
	bairro = new BindingList("Bairro").setNotNull(false).setDisabled(true).withPlaceHolder("Informe um CEP válido");
	uf = new BindingList("UF").setNotNull(false).setDisabled(true).withPlaceHolder("Informe um CEP válido");
	logradouro = new BindingString("Logradouro", 100).setNotNull(false).setDisabled(true).withPlaceHolder("Informe um CEP válido");
	static service;

	constructor(label) {
		super(label, 10);
		this.bairro.setDisabled(true).setNotNull(false);
		this.uf.setDisabled(true).setNotNull(false);
		this.logradouro.setDisabled(true).setNotNull(false);
		this.showTeclado = true;
	}

	getInvalidMessagePrivate() {
		if (this.buscando) {
			return null;
		}
		if (!UCep.isValid(this.get())) {
			return "CEP Inválido!";
		}
		if (UCommons.isEmpty(BindingCep.service)) {
			return "Não existe um servico de busca de CEP configurado!";
		}
		if (!this.encontradoNosCorreios) {
			return "Não encontrado nos correios";
		}
		return null;
	}

	setEncontradoNosCorreios(encontradoNosCorreiosParam) {
		this.encontradoNosCorreios = encontradoNosCorreiosParam;
		this.notifyObservers();
	}

	formatParcial(s) {
		return UCep.formatParcial(s);
	}

	afterSet() {

		this.encontradoNosCorreios = false;
		this.buscando = false;
		const s = this.get();
		this.bairro.set(null);
		this.uf.set(null);
		this.logradouro.set(null);

		if (UCep.isValid(s)) {
			if (UCommons.notEmpty(BindingCep.service)) {
				this.buscando = true;
				BindingCep.service.exec(UString.mantemSomenteNumeros(s), o => {
					if (UCommons.isEmpty(o)) {
						this.bairro.set(null);
						this.uf.set(null);
						this.logradouro.set(null);
					} else {
						this.bairro.setUnique(o.bairro);
						this.uf.setUnique(o.uf);
						this.logradouro.set(o.logradouro);
						this.encontradoNosCorreios = true;
					}
				});
			}
		}
	}

}
