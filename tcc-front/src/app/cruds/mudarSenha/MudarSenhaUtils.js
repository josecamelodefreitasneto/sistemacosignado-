/* front-constructor */
import MudarSenhaUtilsAbstract from '../../auto/mudarSenha/MudarSenhaUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class MudarSenhaUtils extends MudarSenhaUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("MudarSenhaUtils", () => new MudarSenhaUtils(), o => o.init());
	}
}
