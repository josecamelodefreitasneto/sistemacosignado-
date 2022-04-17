/* front-constructor */
import AuditoriaItem from './auditoria/AuditoriaItem';
import Binding from '../../app/campos/support/Binding';
import BindingBoolean from '../../app/campos/support/BindingBoolean';
import BindingCep from '../../app/campos/support/BindingCep';
import BindingCpf from '../../app/campos/support/BindingCpf';
import BindingData from '../../app/campos/support/BindingData';
import BindingDecimal from '../../app/campos/support/BindingDecimal';
import BindingEmail from '../../app/campos/support/BindingEmail';
import BindingFk from '../../app/campos/support/BindingFk';
import BindingId from '../../app/campos/support/BindingId';
import BindingInteger from '../../app/campos/support/BindingInteger';
import BindingList from '../../app/campos/support/BindingList';
import BindingMoney from '../../app/campos/support/BindingMoney';
import BindingNomeProprio from '../../app/campos/support/BindingNomeProprio';
import BindingSenha from '../../app/campos/support/BindingSenha';
import BindingString from '../../app/campos/support/BindingString';
import BindingSubList from './BindingSubList';
import BindingTelefone from '../../app/campos/support/BindingTelefone';
import BindingVinculado from '../../app/campos/support/BindingVinculado';
import CampoAlterado from '../outros/CampoAlterado';
import Permissao from './Permissao';
import Post from '../../projeto/Post';
import SearchService from './SearchService';
import UCommons from '../../app/misc/utils/UCommons';
import UInteger from '../../app/misc/utils/UInteger';
import {message} from 'antd';

export default class EntityCampos extends BindingBoolean {

	listBindings;
	controleVinculado;

	id;
	excluido;
	registroBloqueado;
	modalAuditoria = BindingBoolean.novo("");
	observacoes;
	alditoria;
	auditoriaItem;
	permissao;

	houveAlteracoes;
	disabledObservers = false;
	construido = false;

	static novos = 0;
	afterSaveObservers = [];

	constructor() {
		super();
		this.permissao = new Permissao(this.getEntidade());
	}

	init() {
		this.listBindings = [];
		this.controleVinculado = this.newBoolean("controleVinculado", true, "Sistema").setStartValue(false);
		this.id = new BindingId("id");
		this.add(this.id, false, "Geral").setMinimo(-999999999);
		this.houveAlteracoes = BindingBoolean.novo("houveAlteracoes").setStartValue(false);
		this.excluido = this.newBoolean("Excluído", true, "Sistema").setDisabled(true);
		this.registroBloqueado = this.newBoolean("Registro Bloqueado", true, "Sistema").setDisabled(true);
		this.initImpl();

		this.alditoria = this.createSubList("Auditoria", "auditoria"
			, null
			, obj => {
				let array = obj;
				this.alditoria.addItens(array.map(json => {
					let o = new AuditoriaItem();
					o.id = json.id;
					o.idTipo = json.idTipo;
					o.tipo = json.tipo;
					o.usuario = json.usuario;
					o.data = json.data;
					o.tempo = json.tempo;
					return o;
				}));
			}
		);

	}

	isRascunho() {
		return this.id.isEmpty() || this.id.get() < 1;
	}

	static excluir(entidadePathP, idP, onSuccess) {
		new Post(entidadePathP + "/excluir", idP, res => {
			if (UCommons.notEmpty(onSuccess)) {
				message.info("Registro excluído com sucesso!");
				onSuccess();
			}
		}).run();
	}

	pronto() {
		return !this.disabledObservers && this.construido;
	}

	isReadOnly() {
		return this.excluido.get() || this.registroBloqueado.get();
	}

	calcChange(bind) {
		if (this.pronto()) {
			this.houveAlteracoes.set(this.houveMudancas());
		}
	}

	reiniciar() {
		this.houveAlteracoes.set(false);
		this.disabledObservers = false;
		Binding.notificacoesDesligadasDec();
		this.alditoria.clearItens();
		this.listBindings.forEach(o => o.setVirgin(true));
		this.notifyObservers();
	}

	notify(o) {
		this.notifyObservers();
	}

	touch() {
		this.listBindings.forEach(campo => campo.setVirgin(false));
	}
	haImpedimentos() {
		return this.listBindings.filter(campo => !campo.isValid()).length > 0;
	}
	getErros() {
		return this.listBindings.filter(campo => !campo.isValid() && !campo.isVirgin());
	}
	getImpedimentos() {
		return this.listBindings.filter(campo => !campo.isValid()).reduce((s, campo) => s + ";" + campo.getLabel() + ":" + campo.getInvalidMessage(), "");
	}
	add(campo, notNull, aba) {
		this.listBindings.add(campo);
		campo.setNotNull(notNull);
		campo.setAtribute("aba", aba);
		campo.addObserver(this);
		campo.addFunctionTObserver(bind => this.calcChange(bind));
		return campo;
	}

	newCpf(nomeP, notNull, aba) {
		return this.add(new BindingCpf(nomeP), notNull, aba);
	}
	newData(nomeP, notNull, aba) {
		return this.add(new BindingData(nomeP), notNull, aba);
	}
	newEmail(nomeP, notNull, aba) {
		return this.add(new BindingEmail(nomeP), notNull, aba);
	}
	newImagem(nomeP, notNull, aba) {
		return this.add(new BindingString(nomeP, 100), notNull, aba);
	}
	newBoolean(nomeP, notNull, aba) {
		return this.add(BindingBoolean.novo(nomeP), notNull, aba);
	}
	newBotaoFront(nomeP, onClick) {
		let o = BindingBoolean.novo(nomeP);
		o.setNotNull(false);
		o.setStartValue(false);
		o.addFunctionObserver(onClick);
		return o;
	}
	newBotao(nomeP, nomeMetodo, callBack) {
		let o = BindingBoolean.novo(nomeP);
		o.setNotNull(false);
		o.setStartValue(false);
		o.addFunctionObserver(() => {
			new Post(this.getEntidadePath() + "/" + nomeMetodo, this.id.get(), res => {
				this.setJson(res.body);
				callBack();
			}).run();
		});
		return o;
	}
	newCep(nomeP, notNull, aba) {
		return this.add(new BindingCep(nomeP), notNull, aba);
	}
	newTelefone(nomeP, notNull, aba) {
		return this.add(new BindingTelefone(nomeP), notNull, aba);
	}
	newNomeProprio(nomeP, notNull, aba) {
		return this.add(new BindingNomeProprio(nomeP), notNull, aba);
	}
	newString(nomeP, size, notNull, aba) {
		return this.add(new BindingString(nomeP, size), notNull, aba);
	}
	newSenha(nomeP, size, notNull, aba) {
		return this.add(new BindingSenha(nomeP, size, false), notNull, aba);
	}
	newVinculado(nomeP, notNull, aba) {
		let o = this.add(BindingVinculado.novo(nomeP), notNull, aba);
		o.setStartValue(false);
		o.addFunctionObserver(() => this.controleVinculado.set(o.get()));
		return o;
	}
	newDecimal(nomeP, inteiros, decimais, nullIfZeroWhenDisabled, notNull, aba) {
		return this.add(new BindingDecimal(nomeP, inteiros, decimais).setNullIfZeroWhenDisabled(nullIfZeroWhenDisabled), notNull, aba);
	}
	newMoney(nomeP, inteirosP, nullIfZeroWhenDisabled, notNull, aba) {
		return this.add(new BindingMoney(nomeP, inteirosP).setNullIfZeroWhenDisabled(nullIfZeroWhenDisabled), notNull, aba);
	}
	newInteger(nomeP, maximo, notNull, aba) {
		return this.add(new BindingInteger(nomeP, maximo), notNull, aba);
	}
	newList(nomeP, itensP, notNull, aba) {
		return this.add(new BindingList(nomeP).setItens(itensP), notNull, aba);
	}
	createSubList(titleP, nomeCampoP,mergeFunction, carragarCallBack) {
		let o = new BindingSubList(titleP, mergeFunction, carragarCallBack, this.id, this.getEntidadePath() + "/get-" + nomeCampoP);
		o.setStartValue(false);
		o.addFunctionObserver(() => this.controleVinculado.set(o.get()));
		return o;
	}
	newSubList(titleP, nomeCampoP,mergeFunction, carragarCallBack, notNull, aba, disabled) {
		let o = this.createSubList(titleP, nomeCampoP, mergeFunction, carragarCallBack);
		this.add(o, notNull, aba);
		if (disabled) {
			o.setDisabled(true);
		}
		return o;
	}
	newFk(nomeP, entidadeP, notNull, aba) {
		return this.add(new BindingFk(nomeP, new SearchService(entidadeP + "/consulta-select", null)), notNull, aba);
	}
	save(vinculo, onSuccess) {
		if (this.haImpedimentos()) {
			this.touch();
			return false;
		} else {
			if (UCommons.notEmpty(vinculo)) {
				vinculo.confirm();
				this.afterSave(onSuccess);
			} else {
				new Post(this.getEntidadePath() + "/save", this.getTo(), res => {
					this.setJson(res.body);
					this.afterSave(onSuccess);
				}).setOnError(m => message.error(m)).run();
			}
			return true;
		}
	}

	afterSave(onSuccess) {
		if (UCommons.notEmpty(onSuccess)) {
			onSuccess();
		}
		this.afterSaveObservers.forEach(func => func());
	}

	buscarCampoLookup(entidadePath, campo, idP, onSuccess) {
		if (UInteger.isEmptyOrZero(idP)) {
			onSuccess(null);
		} else {
			new Post(entidadePath + "/lookup-"+campo, idP, res => {
				onSuccess(res.body);
			}).run();
		}
	}

	edit(idP, onSuccess) {
		new Post(this.getEntidadePath() +  "/edit", idP, res => {
			let o = this.setJson(res.body);
			if (UCommons.notEmpty(onSuccess)) onSuccess(o);
		}).run();
	}

	static idNovo = 0;

	setNovo() {
		let value = --EntityCampos.idNovo;
		this.getOriginal().setId(value);
		this.id.set(value);
	}

	detalharAuditoria(o) {
		this.auditoriaItem = o;
		if (UCommons.isEmpty(o.alteracoes)) {
			new Post("auditoria-campo/consulta", o.id, res => {
				let result = res.body;
				let array = result.dados;
				this.auditoriaItem.alteracoes = array.map(obj => {
					let item = new CampoAlterado();
					item.setId(obj.id);
					item.setCampo(obj.campo);
					item.setDe(obj.de);
					item.setPara(obj.para);
					return item;
				});
				this.alditoria.set(true);
			}).run();
		} else {
			this.alditoria.set(true);
		}
	}

	post(uriP, params, onSuccess) {
		new Post(this.getEntidadePath() + "/" + uriP, params, onSuccess).run();
	}

	round(bind, decimais, nullIfZero) {
		let o = bind.toJsNumeric(decimais);
		if (nullIfZero && o.isZero()) {
			return null;
		} else {
			return o;
		}
	}
	callComando(path) {
		new Post(this.getEntidadePath() + "/" + path, this.id.get(), res => {
			this.setJson(res.body);
		}).setOnError(m => message.error(m)).run();
	}

}
