/* front-constructor */
import ArquivoColsAbstract from '../../auto/arquivo/ArquivoColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ArquivoCols extends ArquivoColsAbstract {

	static getInstance() {
		return Sessao.getInstance("ArquivoCols", () => new ArquivoCols(), o => o.init());
	}
}
