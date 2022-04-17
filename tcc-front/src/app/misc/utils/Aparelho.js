/* react */
import UCommons from './UCommons';

export default class Aparelho {

	getAlturaTecladoCustomizado() {
		return 0;
	}

	getAlturaTeclado() {
		let x = this.getAlturaTecladoCustomizado();
		if (UCommons.eqeqeq(x, 0)) {
			return this.getScreenHeight() / 4;
		} else {
			return x;
		}
	}

	getAlturaDisponivel() {
		return this.getScreenHeight() - this.getBarraSuperiorHeight() - this.getBarraSuperiorHeight();
	}
	getScala() {
		return 1;
	}
}
