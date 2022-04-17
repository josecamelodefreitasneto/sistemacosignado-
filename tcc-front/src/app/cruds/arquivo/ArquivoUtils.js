/* front-constructor */
import ArquivoUtilsAbstract from '../../auto/arquivo/ArquivoUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class ArquivoUtils extends ArquivoUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("ArquivoUtils", () => new ArquivoUtils(), o => o.init());
	}
}
