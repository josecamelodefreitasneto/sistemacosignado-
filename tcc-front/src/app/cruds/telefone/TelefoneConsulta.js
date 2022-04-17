/* tcc-java */
import Sessao from '../../../projeto/Sessao';
import TelefoneConsultaAbstract from '../../auto/telefone/TelefoneConsultaAbstract';

export default class TelefoneConsulta extends TelefoneConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("TelefoneConsulta", () => new TelefoneConsulta(), o => o.init());
	}
}
