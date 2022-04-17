/* front-constructor */
import Sessao from '../../../projeto/Sessao';
import UsuarioConsultaAbstract from '../../auto/usuario/UsuarioConsultaAbstract';

export default class UsuarioConsulta extends UsuarioConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("UsuarioConsulta", () => new UsuarioConsulta(), o => o.init());
	}
}
