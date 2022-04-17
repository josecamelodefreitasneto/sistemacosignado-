/* react */
import BindingString from './BindingString';
import UMoney from '../../misc/utils/UMoney';
import UString from '../../misc/utils/UString';

export default class BindingValor extends BindingString {

	constructor(label, maxValue) {
		super(label, UString.length(UString.toString(maxValue)));
	}

	useR$() {
		return false;
	}

	getInvalidMessagePrivate() {
		if (UMoney.isValid(this.get())) {
			return this.getInvalidMessagePrivate2();
		} else {
			return "Valor inválido!";
		}
	}

	getInvalidMessagePrivate2() {
		return null;
	}

	formatParcial(s) {
		return UMoney.formatParcial(s, this.useR$(), this.getMaxLength());
	}

	toInt() {
		if (this.isEmpty()) {
			return 0;
		} else {
			return parseInt(UString.mantemSomenteNumeros(this.get()));
		}
	}

	isNumeric() {
		return true;
	}

}
