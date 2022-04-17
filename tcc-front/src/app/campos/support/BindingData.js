/* react */
import BindingString from './BindingString';
import UCommons from '../../misc/utils/UCommons';
import UData from '../../misc/utils/UData';
import UString from '../../misc/utils/UString';

export default class BindingData extends BindingString {

	constructor(label) {
		super(label, 10);
	}

	getInvalidMessagePrivate() {
		const s = this.get();
		if (UData.isValida(s)) {
			return this.getInvalidMessagePrivate2();
		} else if (s.length < 10) {
			return "Incompleta (deve conter 10 carateres)";
		} else {
			return "Inválida!";
		}
	}

	getInvalidMessagePrivate2() {
		return null;
	}

	formatParcial(s) {
		return UData.formatParcial(s);
	}

	toDate() {
		if (!this.isValid()) {
			return null;
		}
		let s = this.get();
		const dia = parseInt(s.substring(0, 2));
		s = UString.afterFirst(s, "/");
		const mes = parseInt(s.substring(0, 2))-1;
		s = UString.afterFirst(s, "/");
		const ano = parseInt(s);
		const date = new Date(ano, mes, dia, 0, 0, 0, 0);
		return date;
	}

	getToServiceImpl() {
		return this.toDate();
	}

	isNumeric() {
		return true;
	}

	setDisabled(o) {
		super.setDisabled(o);
		return this;
	}

	setMoment(value) {
		if (UCommons.isEmpty(value)) {
			this.clear();
		} else {
			this.set(value.format("DDMMYYYY"));
		}
	}

}
