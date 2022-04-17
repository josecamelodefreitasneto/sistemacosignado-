/* react */
import BindingString from './BindingString';
import UCpf from '../../misc/utils/UCpf';
import UString from '../../misc/utils/UString';

export default class BindingCpf extends BindingString {

	constructor(label) {
		super(label, 14);
		this.showTeclado = true;
	}

	getInvalidMessagePrivate() {
		let s = this.get();
		if (UCpf.isValid(s)) {
			return null;
		} else if (UString.lengthIs(s, 14)) {
			return "Inválido";
		} else {
			return UString.length(UString.mantemSomenteNumeros(s)) + " de 11 números";

		}
	}

	formatParcial(s) {
		return UCpf.formatParcial(s);
	}

	isNumeric() {
		return true;
	}

}
