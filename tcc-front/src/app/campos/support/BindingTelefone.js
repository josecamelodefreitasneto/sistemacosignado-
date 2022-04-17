/* react */
import BindingString from './BindingString';
import UTelefone from '../../misc/utils/UTelefone';

export default class BindingTelefone extends BindingString {

	constructor(label) {
		super(label, UTelefone.MAX_LENGTH);
	}

	getInvalidMessagePrivate() {
		if (UTelefone.isValid(this.get())) {
			return null;
		} else {
			return "Telefone inválido!";
		}
	}

	formatParcial(s) {
		return UTelefone.formatParcial(s);
	}

	isNumeric() {
		return true;
	}

}
