/* tcc-java */
import CepColsAbstract from '../../auto/cep/CepColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class CepCols extends CepColsAbstract {

	static getInstance() {
		return Sessao.getInstance("CepCols", () => new CepCols(), o => o.init());
	}
}
