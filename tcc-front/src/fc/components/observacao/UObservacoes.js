/* front-constructor */
import UArray from '../../../app/misc/utils/UArray';
import UCommons from '../../../app/misc/utils/UCommons';
import UInteger from '../../../app/misc/utils/UInteger';
import UString from '../../../app/misc/utils/UString';

export default class UObservacoes {

	static clonar(de, para) {
		if (UCommons.notEmpty(de.getObservacoes())) {} else {
			de.setObservacoes(null);
			para.setObservacoes(null);
		}
	}

	static equalsObservacoes(a, b) {
		return UObservacoes.equalsArray(a.getObservacoes(), b.getObservacoes());
	}

	static equalsArray(a, b) {
		if (UArray.isEmpty(a)) return UArray.isEmpty(b);
		if (UArray.isEmpty(b)) return false;
		if (!UInteger.equals(a.size(), b.size())) return false;
		for (let i = 0; i < a.size(); i++) {
			if (!UObservacoes.equalsObs(a.get(i), b.get(i))) return false;
		}
		return true;
	}

	static equalsObs(a, b) {
		if (!UInteger.equals(a.getId(), b.getId())) return false;
		if (!UString.equals(a.getTexto(), b.getTexto())) return false;
		if (UCommons.isEmpty(a.getAnexo())) return UCommons.isEmpty(b.getAnexo());
		if (UCommons.isEmpty(b.getAnexo())) return false;
		if (!UInteger.equals(a.getAnexo().getId(), b.getAnexo().getId())) return false;
		return true;
	}

}
