/* tcc-java */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import ClienteTipoSimulacaoUtils from '../../cruds/clienteTipoSimulacao/ClienteTipoSimulacaoUtils';
import Consulta from '../../../fc/components/Consulta';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ClienteTipoSimulacaoConsultaAbstract extends Consulta {

	dados;
	nome;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "ClienteTipoSimulacao";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(ClienteTipoSimulacaoUtils.getInstance().fromJsonList(array));
			},
			"cliente-tipo-simulacao/consulta",
			() => this.getTo()
		);
		this.nome = this.newString("Nome", 24);
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
		Sessao.checkInstance("ClienteTipoSimulacaoConsulta", this);
	}
}
