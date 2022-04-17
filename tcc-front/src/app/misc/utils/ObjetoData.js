/* react */
import UCommons from './UCommons';
import UData from './UData';
import UString from './UString';

export default class ObjetoData {

	static MESES_31 = [1,3,5,7,8,10,12];

	dia;
	mes;
	ano;

	isValida() {
		if (this.isIncompleta()) {
			return false;
		}
		if (this.dia < 1 || this.dia > 31) {
			return false;
		}
		if (this.mes < 1 || this.mes > 12) {
			return false;
		}
		if (this.ano < 1920) {
			return false;
		}
		if (this.ano > UData.hoje().ano+10) {
			return false;
		}
		if (UCommons.equals(this.dia, 31)) {
			return ObjetoData.MESES_31.contains(this.mes);
		}
		if (UCommons.equals(this.mes, 2)) {
			if (UCommons.equals(this.dia, 30)) {
				return false;
			} else if (UCommons.equals(this.dia, 29)) {
				return UCommons.equals(this.ano % 4, 0);
			} else {
				return true;
			}
		}
		return true;
	}

	isIncompleta() {
		if (UCommons.isEmpty(this.dia)) {
			return true;
		}
		if (UCommons.isEmpty(this.mes)) {
			return true;
		}
		if (UCommons.isEmpty(this.ano)) {
			return true;
		}
		return false;
	}

	toString() {
		return UString.format00(this.dia, 2) + "/" + UString.format00(this.mes, 2) + "/" + this.ano;
	}

	toDate() {
		return new Date(this.ano, this.mes-1, this.dia, 0, 0, 0, 0);
	}

}
