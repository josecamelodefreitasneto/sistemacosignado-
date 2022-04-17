/* front-constructor */
import BindingServiceList from './BindingServiceList';

export default class BindingSubList extends BindingServiceList {

	id;

	constructor(titleP,mergeFunctionP, carragarCallBackP, idP, uriP) {
		super(titleP, mergeFunctionP, carragarCallBackP, uriP);
		this.id = idP;
	}

	getParametros() {
		return this.id.get();
	}

	preparadoParaBusca() {
		if (this.id.isEmpty() || this.id.eq(-1)) {
			this.start();
			return false;
		} else {
			return true;
		}
	}

	setDisabled(value) {
		super.setDisabled(value);
		return this;
	}
}
