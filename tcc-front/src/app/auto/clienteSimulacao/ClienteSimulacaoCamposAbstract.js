/* tcc-java */
import Binding from '../../campos/support/Binding';
import ClienteSimulacaoConsulta from '../../cruds/clienteSimulacao/ClienteSimulacaoConsulta';
import ClienteSimulacaoUtils from '../../cruds/clienteSimulacao/ClienteSimulacaoUtils';
import Console from '../../misc/utils/Console';
import EntityCampos from '../../../fc/components/EntityCampos';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class ClienteSimulacaoCamposAbstract extends EntityCampos {

	cliente;
	parcelas;
	indice;
	valor;
	contratar;
	contratado;
	original;
	to;
	botaoContratar;
	getEntidade() {
		return "ClienteSimulacao";
	}
	getEntidadePath() {
		return "cliente-simulacao";
	}
	initImpl() {
		this.cliente = this.newFk("Cliente","cliente", true, "Geral");
		this.parcelas = this.newInteger("Parcelas", 99999, false, "Geral").setDisabled(true);
		this.indice = this.newDecimal("Indice", 1, 5, true, false, "Geral").setDisabled(true);
		this.valor = this.newMoney("Valor", 7, true, false, "Geral").setDisabled(true);
		this.contratar = this.newBoolean("Contratar", false, "Geral").setDisabled(true);
		this.contratado = this.newBoolean("Contratado", true, "Geral").setDisabled(true);
		this.botaoContratar = this.newBotao("Contratar", "contratar", () => this.contratarCallBack());
		this.init2();
		this.construido = true;
	}
	setCampos(o) {
		if (UCommons.isEmpty(o)) {
			throw new Error("o === null");
		}
		this.checkInstance();
		Binding.notificacoesDesligadasInc();
		this.disabledObservers = true;
		this.original = o;
		this.to = ClienteSimulacaoUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.cliente.setUnique(this.to.getCliente());
		this.contratado.set(this.to.getContratado());
		this.contratar.set(this.to.getContratar());
		this.indice.set(this.to.getIndice());
		this.parcelas.set(this.to.getParcelas());
		this.valor.setDouble(this.to.getValor());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.cliente.setStartValue(this.cliente.get());
		this.contratado.setStartValue(this.contratado.get());
		this.contratar.setStartValue(this.contratar.get());
		this.indice.setStartValue(this.indice.get());
		this.parcelas.setStartValue(this.parcelas.get());
		this.valor.setStartValue(this.valor.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		let readOnly = this.isReadOnly();
		this.cliente.setDisabled(readOnly);
		this.setCampos2(o);
		this.reiniciar();
	}
	setCampos2(o) {}
	getTo() {
		this.checkInstance();
		this.to.setId(this.id.get());
		this.to.setCliente(this.cliente.get());
		this.to.setContratado(this.contratado.get());
		this.to.setIndice(this.indice.get());
		this.to.setParcelas(this.parcelas.get());
		this.to.setValor(this.valor.getDouble());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = ClienteSimulacaoUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = ClienteSimulacaoConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				ClienteSimulacaoUtils.getInstance().merge(o, itemGrid);
			}
		}
		return o;
	}
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return UString.toString(o.getCliente());
	}
	observacoesEdit(o) {
		throw new Error("???");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !ClienteSimulacaoUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return ClienteSimulacaoUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	contratarCallBack() {
		Console.log("ClienteSimulacaoCampos","contratar");
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteSimulacaoCampos", this);
	}
}
