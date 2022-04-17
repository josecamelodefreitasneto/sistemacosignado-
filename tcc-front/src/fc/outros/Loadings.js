/* front-constructor */
import BindingBoolean from '../../app/campos/support/BindingBoolean';
import UCommons from '../../app/misc/utils/UCommons';

export default class Loadings extends BindingBoolean {

	constructor() {
		super();
	}

	static instance;

	static getInstance() {
		if (UCommons.isEmpty(Loadings.instance)) {
			Loadings.instance = new Loadings();
		}
		return Loadings.instance;
	}

	static count = 0;

	static inc() {
		Loadings.count++;
		if (UCommons.equals(Loadings.count, 1)) {
			Loadings.getInstance().set(true);
		}
	}
	static dec() {
		if (Loadings.show()) {
			Loadings.count--;
			if (UCommons.equals(Loadings.count, 0)) {
				Loadings.getInstance().set(false);
			}
		} else {
			throw new Error("???");
		}
	}
	static show() {
		return Loadings.count > 0;
	}

}
