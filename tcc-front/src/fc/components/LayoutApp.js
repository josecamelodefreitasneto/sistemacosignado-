/* front-constructor */
import Style from '../../app/misc/utils/Style';

export default class LayoutApp {

	static createStyle() {
		return Style.create();
	}

}
LayoutApp.EMPTY = LayoutApp.createStyle().lock();
