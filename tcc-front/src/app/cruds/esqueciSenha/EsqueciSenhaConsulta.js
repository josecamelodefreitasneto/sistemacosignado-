/* front-constructor */
import EsqueciSenhaConsultaAbstract from '../../auto/esqueciSenha/EsqueciSenhaConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class EsqueciSenhaConsulta extends EsqueciSenhaConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("EsqueciSenhaConsulta", () => new EsqueciSenhaConsulta(), o => o.init());
	}
}
