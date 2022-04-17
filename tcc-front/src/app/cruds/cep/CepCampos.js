/* tcc-java */
import CepCamposAbstract from '../../auto/cep/CepCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class CepCampos extends CepCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("CepCampos", () => new CepCampos(), o => o.init());
	}
}
