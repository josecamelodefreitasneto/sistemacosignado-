/* tcc-java */
import CepConsultaAbstract from '../../auto/cep/CepConsultaAbstract';
import Sessao from '../../../projeto/Sessao';

export default class CepConsulta extends CepConsultaAbstract {

	static getInstance() {
		return Sessao.getInstance("CepConsulta", () => new CepConsulta(), o => o.init());
	}
}
