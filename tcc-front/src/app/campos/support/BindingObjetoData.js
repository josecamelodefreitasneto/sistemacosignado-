/* react */
import Binding from './Binding';
import UData from '../../misc/utils/UData';

export default class BindingObjetoData extends Binding {

	constructor(label) {
		super(label);
		this.showTeclado = true;
	}

	modalVisible = false;

	isModalVisible() {
		return this.modalVisible;
	}

	setModalVisible(b) {
		this.modalVisible = b;
		this.notifyObservers();
	}

	asString() {
		if (this.isEmpty()) {
			return "";
		} else {
			return this.get().toString();
		}
	}

	getDate() {
		if (this.isEmpty()) {
			return UData.hoje().toDate();
		} else {
			return this.get().toDate();
		}
	}

	castFromString(s) {
		return UData.strToDate(s);
	}

}
