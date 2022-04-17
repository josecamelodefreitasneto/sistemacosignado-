/* tcc-java */
import CepUtilsAbstract from '../../auto/cep/CepUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class CepUtils extends CepUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("CepUtils", () => new CepUtils(), o => o.init());
	}
}
