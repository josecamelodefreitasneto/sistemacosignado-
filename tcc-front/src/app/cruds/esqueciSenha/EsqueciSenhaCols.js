/* front-constructor */
import EsqueciSenhaColsAbstract from '../../auto/esqueciSenha/EsqueciSenhaColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class EsqueciSenhaCols extends EsqueciSenhaColsAbstract {

	static getInstance() {
		return Sessao.getInstance("EsqueciSenhaCols", () => new EsqueciSenhaCols(), o => o.init());
	}
}
