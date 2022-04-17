/* front-constructor */
import ObservacaoUtilsAbstract from '../../auto/observacao/ObservacaoUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ObservacaoUtils extends ObservacaoUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ObservacaoUtils", () => new ObservacaoUtils(), o => o.init());
	}
}
