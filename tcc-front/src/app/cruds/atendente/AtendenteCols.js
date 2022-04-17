/* tcc-java */
import AtendenteColsAbstract from '../../auto/atendente/AtendenteColsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class AtendenteCols extends AtendenteColsAbstract {

	static getInstance() {
		return Sessao.getInstance("AtendenteCols", () => new AtendenteCols(), o => o.init());
	}
}
