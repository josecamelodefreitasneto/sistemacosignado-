/* tcc-java */
import AtendenteConsulta from '../../cruds/atendente/AtendenteConsulta';
import AtendenteUtils from '../../cruds/atendente/AtendenteUtils';
import Binding from '../../campos/support/Binding';
import EntityCampos from '../../../fc/components/EntityCampos';
import ObservacaoCampos from '../../cruds/observacao/ObservacaoCampos';
import ObservacaoUtils from '../../cruds/observacao/ObservacaoUtils';
import Sessao from '../../../projeto/Sessao';
import UCommons from '../../misc/utils/UCommons';

export default class AtendenteCamposAbstract extends EntityCampos {

	nome;
	email;
	original;
	to;
	getEntidade() {
		return "Atendente";
	}
	getEntidadePath() {
		return "atendente";
	}
	initImpl() {
		this.nome = this.newNomeProprio("Nome", true, "Geral");
		this.email = this.newEmail("E-mail", true, "Geral");
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
		this.to = AtendenteUtils.getInstance().clonar(o);
		this.id.clear();
		this.id.set(this.to.getId());
		this.email.set(this.to.getEmail());
		this.nome.set(this.to.getNome());
		this.excluido.set(this.to.getExcluido());
		this.registroBloqueado.set(this.to.getRegistroBloqueado());
		this.id.setStartValue(this.to.getId());
		this.email.setStartValue(this.email.get());
		this.nome.setStartValue(this.nome.get());
		this.excluido.setStartValue(this.excluido.get());
		this.registroBloqueado.setStartValue(this.registroBloqueado.get());
		this.setObservacoes();
		let readOnly = this.isReadOnly();
		this.email.setDisabled(readOnly);
		this.nome.setDisabled(readOnly);
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
		this.to.setEmail(this.email.get());
		this.to.setNome(this.nome.get());
		this.to.setExcluido(this.excluido.get());
		this.to.setRegistroBloqueado(this.registroBloqueado.get());
		this.to.setObservacoes(this.observacoes.getItens());
		return this.to;
	}
	setJson(obj) {
		this.checkInstance();
		let json = obj;
		let o = AtendenteUtils.getInstance().fromJson(json);
		this.setCampos(o);
		let itensGrid = AtendenteConsulta.getInstance().getDataSource();
		if (UCommons.notEmpty(itensGrid)) {
			let itemGrid = itensGrid.byId(o.getId());
			if (UCommons.notEmpty(itemGrid)) {
				AtendenteUtils.getInstance().merge(o, itemGrid);
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
		return o.getNome();
	}
	observacoesEdit(o) {
		ObservacaoCampos.getInstance().setCampos(o);
		this.observacoes.setTrue("edit-observacoes-Atendente");
	}
	houveMudancas() {
		if (UCommons.isEmpty(this.original)) {
			return false;
		}
		return !AtendenteUtils.getInstance().equals(this.original, this.getTo());
	}
	camposAlterados() {
		return AtendenteUtils.getInstance().camposAlterados(this.original, this.getTo());
	}
	cancelarAlteracoes() {
		this.setCampos(this.original);
	}
	getOriginal() {
		return this.original;
	}
	init2() {}
	checkInstance() {
		Sessao.checkInstance("AtendenteCampos", this);
	}
}
