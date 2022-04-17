/* front-constructor */
import EsqueciSenhaUtilsAbstract from '../../auto/esqueciSenha/EsqueciSenhaUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class EsqueciSenhaUtils extends EsqueciSenhaUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("EsqueciSenhaUtils", () => new EsqueciSenhaUtils(), o => o.init());
	}
}
