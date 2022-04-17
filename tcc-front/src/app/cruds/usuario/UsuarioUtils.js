/* front-constructor */
import Sessao from '../../../projeto/Sessao';
import UsuarioUtilsAbstract from '../../auto/usuario/UsuarioUtilsAbstract';

export default class UsuarioUtils extends UsuarioUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("UsuarioUtils", () => new UsuarioUtils(), o => o.init());
	}
}
