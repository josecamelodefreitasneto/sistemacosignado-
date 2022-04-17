/* front-constructor */
import ObservacaoColsAbstract from '../../auto/observacao/ObservacaoColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ObservacaoCols extends ObservacaoColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ObservacaoCols", () => new ObservacaoCols(), o => o.init());
	}
}
