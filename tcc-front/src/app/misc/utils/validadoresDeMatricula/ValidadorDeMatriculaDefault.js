/* react */
import UNumbers from '../UNumbers';
import UString from '../UString';
import ValidadorDeMatricula from './ValidadorDeMatricula';

export default class ValidadorDeMatriculaDefault extends ValidadorDeMatricula {

	formatParcial(s) {
		s = UString.mantemSomenteNumeros(s);
		if (UString.length(s) < 2) {
			return s;
		}
		s = UString.maxLength(s, 15);
		let digito = "-" + UString.right(s, 1);
		s = UString.ignoreRight(s, 1);
		s = UNumbers.separarMilhares(s);
		return s + digito;
	}

	isValid(s) {
		return UString.notEmpty(s);
	}

}
