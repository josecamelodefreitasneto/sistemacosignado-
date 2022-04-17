/* tcc-java */
import Sessao from '../../../projeto/Sessao';
import TelefoneUtilsAbstract from '../../auto/telefone/TelefoneUtilsAbstract';

export default class TelefoneUtils extends TelefoneUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("TelefoneUtils", () => new TelefoneUtils(), o => o.init());
	}
}
