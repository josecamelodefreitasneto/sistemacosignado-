/* tcc-java */
import Sessao from '../../../projeto/Sessao';
import TelefoneColsAbstract from '../../auto/telefone/TelefoneColsAbstract';

export default class TelefoneCols extends TelefoneColsAbstract {

	static getInstance() {
		return Sessao.getInstance("TelefoneCols", () => new TelefoneCols(), o => o.init());
	}
}
