/* react */
import Binding from './Binding';

export default class BindingArquivo extends Binding {

	constructor(label) {
		super(label);
	}

	castFromString(s) {
		return null;
	}

	setNotNull(value) {
		super.setNotNull(value);
		return this;
	}

}
