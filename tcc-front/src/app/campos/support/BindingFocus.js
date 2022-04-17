/* react */
import Binding from './Binding';

export default class BindingFocus extends Binding {

	constructor(label) {
		super(label);
	}

	castFromString(s) {
		throw new Error("nao deve ser invocado");
	}

}
