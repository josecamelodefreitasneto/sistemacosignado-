/* tcc-java */
import AtendenteCamposAbstract from '../../auto/atendente/AtendenteCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class AtendenteCampos extends AtendenteCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("AtendenteCampos", () => new AtendenteCampos(), o => o.init());
	}
}
