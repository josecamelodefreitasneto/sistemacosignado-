/* react */
import BindingString from './BindingString';

export default class BindingImage extends BindingString {

	emptyImage;

	constructor(label, emptyImage) {
		super(label, 500);
		this.emptyImage = emptyImage;
	}

	getEmptyImage() {
		return this.emptyImage;
	}

}
