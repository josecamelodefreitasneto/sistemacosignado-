/* react */
import BindingString from './BindingString';
import UCnpj from '../../misc/utils/UCnpj';
import UCpf from '../../misc/utils/UCpf';
import UString from '../../misc/utils/UString';

export default class BindingCpfOuCnpj extends BindingString {

	constructor() {
		super("CPF ou CNPJ", 21);
		this.setPlaceHolder("Entre com o CPF ou CNPJ");
	}

	getInvalidMessagePrivate() {
		if (UCpf.isValid(this.get()) || UCnpj.isValid(this.get())) {
			return null;
		} else {
			return "CPF ou CNPJ Inválido";
		}
	}

	formatParcial(s) {
		s = UString.mantemSomenteNumeros(s);
		if (UString.length(s) < 12) {
			return UCpf.formatParcial(s);
		} else {
			return UCnpj.formatParcial(s);
		}
	}

	isNumeric() {
		return true;
	}

}
