/* react */
import BindingBoolean from './BindingBoolean';

export default class BindingIgnorar extends BindingBoolean {

	bind;

	constructor(label, bind) {
		super();
		this.setLabel(label);
		this.bind = bind;
	}

	afterSet() {
		this.bind.setDisabled(this.isTrue());
	}

}
