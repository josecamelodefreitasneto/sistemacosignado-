/* tcc-java */
import IdText from '../../misc/utils/IdText';

export default class RubricaTipoConstantes {
	static getList() {
		return RubricaTipoConstantes.list;
	}
}
RubricaTipoConstantes.REMUNERACAO = 1;
RubricaTipoConstantes.DESCONTO = 2;
RubricaTipoConstantes.list = [
	new IdText(RubricaTipoConstantes.REMUNERACAO, "Remuneração"),
	new IdText(RubricaTipoConstantes.DESCONTO, "Desconto")
];
