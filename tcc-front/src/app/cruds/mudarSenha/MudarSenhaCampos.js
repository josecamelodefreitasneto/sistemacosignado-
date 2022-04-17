/* front-constructor */
import MudarSenhaCamposAbstract from '../../auto/mudarSenha/MudarSenhaCamposAbstract';
import Sessao from '../../../projeto/Sessao';
import Session from '../../estado/Session';

export default class MudarSenhaCampos extends MudarSenhaCamposAbstract {

	init2() {
		this.afterSaveObservers.add(() => Session.getInstance().mudarSenha.set(false));
	}

	static getInstance() {
		return Sessao.getInstance("MudarSenhaCampos", () => new MudarSenhaCampos(), o => o.init());
	}
}
