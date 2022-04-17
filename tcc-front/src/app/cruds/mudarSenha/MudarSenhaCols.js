/* front-constructor */
import MudarSenhaColsAbstract from '../../auto/mudarSenha/MudarSenhaColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class MudarSenhaCols extends MudarSenhaColsAbstract {

	static getInstance() {
		return Sessao.getInstance("MudarSenhaCols", () => new MudarSenhaCols(), o => o.init());
	}
}
