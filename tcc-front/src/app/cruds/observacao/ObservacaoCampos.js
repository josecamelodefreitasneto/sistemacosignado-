/* front-constructor */
import ObservacaoCamposAbstract from '../../auto/observacao/ObservacaoCamposAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ObservacaoCampos extends ObservacaoCamposAbstract {

	static getInstance() {
		return Sessao.getInstance("ObservacaoCampos", () => new ObservacaoCampos(), o => o.init());
	}

}
