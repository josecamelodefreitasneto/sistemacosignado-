/* react */
import BindingString from './BindingString';
import UNomeProprio from '../../misc/utils/UNomeProprio';

export default class BindingNomeProprio extends BindingString {

	constructor(label) {
		super(label, 60);
	}

	formatParcial(s) {
		return UNomeProprio.formatParcial(s);
	}

	getInvalidMessagePrivate() {

		let s = this.get().trim();

		if (!s.contains(" ")) {
			return "Incompleto";
		}

		return null;
	}

}
