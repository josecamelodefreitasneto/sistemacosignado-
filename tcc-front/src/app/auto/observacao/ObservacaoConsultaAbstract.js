/* front-constructor */
import BindingConsultaList from '../../../fc/components/BindingConsultaList';
import Consulta from '../../../fc/components/Consulta';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class ObservacaoConsultaAbstract extends Consulta {

	dados;
	anexo;
	texto;
	excluido;
	registroBloqueado;
	init2() {
		this.nomeEntidade = "Observacao";
		this.dados = new BindingConsultaList(
			"dados",
			(a, b) => {},
			body => {
				let result = body;
				let array = result.dados;
				this.dados.addItens(ObservacaoUtils.getInstance().fromJsonList(array));
			},
			"observacao/consulta",
			() => this.getTo()
		);
		this.anexo = this.newFk("Anexo");
		this.texto = this.newString("Texto", 500);
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
		Sessao.checkInstance("ObservacaoConsulta", this);
	}
}
