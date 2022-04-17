/* tcc-java */
import Binding from '../../campos/support/Binding';
import ClienteRubricaConsulta from '../../cruds/clienteRubrica/ClienteRubricaConsulta';
import ClienteRubricaUtils from '../../cruds/clienteRubrica/ClienteRubricaUtils';
import EntityCampos from '../../../fc/components/EntityCampos';
import ObservacaoCampos from '../../cruds/observacao/ObservacaoCampos';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Post from '../../../projeto/Post';
import RubricaConstantes from '../rubrica/RubricaConstantes';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';

export default class ClienteRubricaCamposAbstract extends EntityCampos {

	cliente;
	tipo;
	rubrica;
	valor;
	original;
	to;
	getEntidade() {
		return "ClienteRubrica";
	}
	getEntidadePath() {
		return "cliente-rubrica";
	}
	initImpl() {
		this.cliente = this.newFk("Cliente","cliente", true, "Geral");
		this.tipo = this.newFk("Tipo","rubrica-tipo", false, "Geral").setDisabled(true);
		this.rubrica = this.newList("Rubrica", RubricaConstantes.getList(), true, "Geral");
		this.valor = this.newMoney("Valor", 7, false, true, "Geral");
		this.observacoes = this.createSubList(
			"Observacoes", "observacoes"
			, (de, para) => ObservacaoUtils.getInstance().merge(de, para)
			, obj => {
				let array = obj;
				this.original.setObservacoes([]);
				array.forEach(json => this.original.getObservacoes().add(ObservacaoUtils.getInstance().fromJson(json)));
				this.setObservacoes();
				if (UCommons.notEmpty(this.original.getOriginal())) {
					this.original.getOriginal().setObservacoes(ObservacaoUtils.getInstance().clonarList(this.original.getObservacoes()));
				}
			}
		);
		this.observacoes.setOnConfirm(() => {
			this.observacoes.add(ObservacaoCampos.getInstance().getTo());
			this.refreshObservacoesHouveMudancas();
		});
		this.observacoes.setOnClear(() => {
			this.observacoes.remove(ObservacaoCampos.getInstance().getTo());
			this.refreshObservacoesHouveMudancas();
		});
		this.rubrica.addFunctionObserver(() => {
			if (this.rubrica.isVirgin()) {
				return;
			}
			if (this.rubrica.isEmpty()) {
				this.tipo.clear();
			} else {
				new Post(this.getEntidadePath() + "/rubrica-lookups", this.rubrica.getId(), res => {
					let result = res.body;
					this.tipo.setUnique(result.tipo);
				}).run();
			}
		});
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
		this.to = ClienteRubricaUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.cliente.setUnique(this.to.getCliente());
		this.rubrica.set(this.to.getRubrica());
		this.tipo.setUnique(this.to.getTipo());
		this.valor.setDouble(this.to.getValor());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.cliente.setStartValue(this.cliente.get());
		this.rubrica.setStartValue(this.rubrica.get());
		this.tipo.setStartValue(this.tipo.get());
		this.valor.setStartValue(this.valor.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		this.setObservacoes();
		let readOnly = this.isReadOnly();
		this.cliente.setDisabled(readOnly);
		this.rubrica.setDisabled(readOnly);
		this.valor.setDisabled(readOnly);
		this.setCampos2(o);
		this.reiniciar();
	}
	setObservacoes() {
		if (UCommons.isEmpty(this.original.getObservacoes())) {
			this.observacoes.clearItens();
		} else {
			this.observacoes.clearItens2();
			let readOnly = this.isReadOnly();
			this.original.getObservacoes().forEach(item => {
				let o = ObservacaoUtils.getInstance().clonar(item);
				if (readOnly) o.setRegistroBloqueado(true);
				this.observacoes.add(o);
			});
		}
	}
	setCampos2(o) {}
	getTo() {
		this.checkInstance();
		this.to.setId(this.id.get());
		this.to.setCliente(this.cliente.get());
		this.to.setRubrica(this.rubrica.get());
		this.to.setTipo(this.tipo.get());
		this.to.setValor(this.valor.getDouble());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		this.to.setObservacoes(this.observacoes.getItens());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = ClienteRubricaUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = ClienteRubricaConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				ClienteRubricaUtils.getInstance().merge(o, itemGrid);
			}
		}
		return o;
	}
	refreshObservacoesHouveMudancas() {
		this.to.setObservacoesHouveMudancas(!ObservacaoUtils.getInstance().equalsList(this.observacoes.getItens(), this.original.getObservacoes()));
		if (this.to.getObservacoesHouveMudancas()) {
			this.observacoes.getItens().forEach(o => {
				let ori = this.original.getObservacoes().byId(o.getId());
				o.setHouveMudancas(!ObservacaoUtils.getInstance().equals(o, ori));
			});
		}
	}
	static getText(o) {
		if (UCommons.isEmpty(o)) {
			return null;
		}
		return UString.toString(o.getCliente());
	}
	observacoesEdit(o) {
		ObservacaoCampos.getInstance().setCampos(o);
		this.observacoes.setTrue("edit-observacoes-ClienteRubrica");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !ClienteRubricaUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return ClienteRubricaUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	rubrica_00596PensaoCivil() {
		return this.rubrica.equals(RubricaConstantes._00596_PENSAO_CIVIL);
	}
	rubrica_55986PensaoMilitar() {
		return this.rubrica.equals(RubricaConstantes._55986_PENSAO_MILITAR);
	}
	rubrica_53341ServidorCivil() {
		return this.rubrica.equals(RubricaConstantes._53341_SERVIDOR_CIVIL);
	}
	rubrica_98020ContribuicaoPlanoSeguroSocialPensionista() {
		return this.rubrica.equals(RubricaConstantes._98020_CONTRIBUICAO_PLANO_SEGURO_SOCIAL_PENSIONISTA);
	}
	rubrica_99015ImpostoDeRendaAposentadoPensionista() {
		return this.rubrica.equals(RubricaConstantes._99015_IMPOSTO_DE_RENDA_APOSENTADO_PENSIONISTA);
	}
	rubrica_93389IrrfImpostoDeRendaRetidoNaFonte() {
		return this.rubrica.equals(RubricaConstantes._93389_IRRF_IMPOSTO_DE_RENDA_RETIDO_NA_FONTE);
	}
	rubrica_95534PssRpgsPrevidenciaSocial() {
		return this.rubrica.equals(RubricaConstantes._95534_PSS_RPGS_PREVIDENCIA_SOCIAL);
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("ClienteRubricaCampos", this);
	}
}
