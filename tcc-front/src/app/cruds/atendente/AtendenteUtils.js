/* tcc-java */
import AtendenteUtilsAbstract from '../../auto/atendente/AtendenteUtilsAbstract';
import Sessao from '../../../projeto/Sessao';

export default class AtendenteUtils extends AtendenteUtilsAbstract {

	static getInstance() {
		return Sessao.getInstance("AtendenteUtils", () => new AtendenteUtils(), o => o.init());
	}
}
