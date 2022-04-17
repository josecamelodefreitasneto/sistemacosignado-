/* front-constructor */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import Consulta from '../../../fc/components/Consulta';
import MudarSenhaUtils from '../../cruds/mudarSenha/MudarSenhaUtils';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class MudarSenhaConsultaAbstract extends Consulta {

	dados;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "MudarSenha";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(MudarSenhaUtils.getInstance().fromJsonList(array));
			},
			"mudar-senha/consulta",
			() => this.getTo()
		);
		this.excluido = this.newBoolean("Excluido");
		this.registroBloqueado = this.newBoolean("Registro Bloqueado");
	}
	consultar() {
		this.dados.clearItens();
		this.dados.carregar();
	}
	getTo() {
		this.checkInstance();
		const o = {};
		o.busca = this.getBusca().get();
		return o;
	}
	getDataSource() {
		if (UCommons.isEmpty(this.dados)) {
			return [];
		} else {
			return this.dados.getItens();
		}
	}
	checkInstance() {
		Sessao.checkInstance("MudarSenhaConsulta", this);
	}
}
