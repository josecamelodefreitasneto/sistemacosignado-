/* react */
import UCommons from '../utils/UCommons';

export default class Uncycle {

	static selectedCombo;

	static setSelectedCombo(value) {
		if (UCommons.equals(Uncycle.selectedCombo, value)) {
			return;
		} else {
			if (UCommons.notEmpty(Uncycle.selectedCombo)) {
				Uncycle.selectedCombo.forceUpdate();
			}
			Uncycle.selectedCombo = value;
			if (UCommons.notEmpty(Uncycle.selectedCombo)) {
				Uncycle.selectedCombo.forceUpdate();
			}
		}
	}

	static getSelectedCombo() {
		return Uncycle.selectedCombo;
	}

}
