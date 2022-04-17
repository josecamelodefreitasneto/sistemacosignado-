/* @author Gamarra - francisco.gamarra@gmail.com */
/* front-constructor */
import UCommons from '../../app/misc/utils/UCommons';

export default class EntityMock {

	getListGrid(params) {
		return null;
	}
	search(params) {
		return null;
	}
	getSubList(comando, id) {
		throw new Error("???");
	}
	get(comando, params) {
		throw new Error("???: " + comando + " - " + UCommons.getClassName(this));
	}
}
