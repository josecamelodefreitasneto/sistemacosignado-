/* react */
import Aparelho from '../Aparelho';

export default class AparelhoIPhone5se extends Aparelho {

	getScreenWidth() {
		return 320;
	}

	getScreenHeight() {
		return 568;
	}

	getBarraSuperiorHeight() {
		return 30;
	}

	getBarraInferiorHeight() {
		return 0;
	}

	getScala() {
		return 0.8;
	}

}
