/* front-constructor */
import Sessao from '../../../projeto/Sessao';
import UsuarioCamposAbstract from '../../auto/usuario/UsuarioCamposAbstract';

export default class UsuarioCampos extends UsuarioCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("UsuarioCampos", () => new UsuarioCampos(), o => o.init());
	}
}
