/* front-constructor */
import Sessao from '../../../projeto/Sessao';
import UsuarioColsAbstract from '../../auto/usuario/UsuarioColsAbstract';

export default class UsuarioCols extends UsuarioColsAbstract {

	static getInstance() {
		return Sessao.getInstance("UsuarioCols", () => new UsuarioCols(), o => o.init());
	}
}
