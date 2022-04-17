/* front-constructor */
import MudarSenhaConsultaAbstract from '../../auto/mudarSenha/MudarSenhaConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class MudarSenhaConsulta extends MudarSenhaConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("MudarSenhaConsulta", () => new MudarSenhaConsulta(), o => o.init());
	}
}
