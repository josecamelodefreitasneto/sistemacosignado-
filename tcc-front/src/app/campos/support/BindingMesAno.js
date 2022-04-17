/* react */
import BindingString from './BindingString';
import UData from '../../misc/utils/UData';

export default class BindingMesAno extends BindingString {

	constructor(label) {
		super(label, 10);
	}

	getInvalidMessagePrivate() {
		let s = this.get();
		if (UData.isValida("01/"+s)) {
			return this.getInvalidMessagePrivate2();
		} else if (s.length < 7) {
			return "Incompleta (deve conter 7 carateres)";
		} else {
			return "Inválida!";
		}
	}

	getInvalidMessagePrivate2() {
		return null;
	}

	formatParcial(s) {
		return UData.formatParcialMesAno(s);
	}

	isNumeric() {
		return true;
	}

}
