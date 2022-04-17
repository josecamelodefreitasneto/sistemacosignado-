/* react */
import Aparelho from '../Aparelho';

export default class AparelhoGalaxyS5 extends Aparelho {

	getScreenWidth() {
		return 360;
	}

	getScreenHeight() {
		return 640;
	}

	getBarraSuperiorHeight() {
		return 30;
	}

	getBarraInferiorHeight() {
		return 0;
	}

	getScala() {
		return 1;
	}

}
