/* front-constructor */
import React from 'react';
import AlignItens from '../../app/misc/consts/enums/AlignItens';
import BorderStyle from '../../app/misc/consts/enums/BorderStyle';
import Color from '../../app/misc/consts/fixeds/Color';
import CommonStyles from '../../app/misc/styles/CommonStyles';
import FormEditButtons from './FormEditButtons';
import FormGenerico from './FormGenerico';
import FormItemInput from '../../antd/form/FormItemInput';
import KeyDownObservers from '../../web/misc/KeyDownObservers';
import LayoutApp from './LayoutApp';
import ModalAuditoria from './auditoria/ModalAuditoria';
import Session from '../../app/estado/Session';
import Style from '../../app/misc/utils/Style';
import TextAlign from '../../app/misc/consts/enums/TextAlign';
import UCommons from '../../app/misc/utils/UCommons';
import {AuditOutlined} from '@ant-design/icons';
import {Calendar} from 'antd';
import {Fragment} from 'react';
import {Icon} from 'antd';
import {message} from 'antd';
import {Popover} from 'antd';

export default class FormEdit extends FormGenerico {
	constructor(props){
		super(props);
		this.state.abaSelecionada = "Geral";
	}

	entidade;
	campos;
	saveJaFoiClicado = false;

	init(entidadeP, camposP) {
		this.campos = camposP;
		this.entidade = entidadeP;
		KeyDownObservers.removeByClass(this);
		this.init2();
	}

	getCampos() {
		return this.campos;
	}

	getFooter() {
		return (
			<table style={CommonStyles.W100P.get()}>
				<thead>
					<tr>
						<td style={FormEdit.TD_BUTTONS_LEFT}>
							{this.getBotoesCustomizados()}
						</td>
						<td style={FormEdit.TD_BUTTONS_RIGHT}>
							<FormEditButtons
								campos={this.getCampos()}
								onCancel={() => this.esc()}
								onSave={() => this.save()}
								onDelete={this.getOnDelete()}
								setAbaSelecionada={s => this.setAbaSelecionada(s)}
								saveJaFoiClicado={this.saveJaFoiClicado}
								vinculado={this.isVinculado()}
								somenteUpdate={this.props.somenteUpdate}
								abaSelecionada={this.state.abaSelecionada}
							/>
						</td>
					</tr>
				</thead>
			</table>
		);
	}

	getTop() {
		return (
			<table style={CommonStyles.W100P.get()}>
				<thead>
					<tr>
						<td style={FormEdit.TD_BUTTONS_LEFT}>
							{super.getTop()}
						</td>
						<td style={FormEdit.TD_BUTTONS_RIGHT}>
							{this.botaoAuditoria()}
						</td>
					</tr>
				</thead>
			</table>
		);
	}

	botaoAuditoria() {
		if (this.isAuditada() && !this.campos.isRascunho()) {
			return <AuditOutlined onClick={() => this.campos.modalAuditoria.set(true)}/>;
		} else {
			return null;
		}
	}

	getOnDelete() {
		if (!this.podeExcluir()) return null;
		return () => this.delete();
	}

	delete() {
		if (!this.podeExcluir()) return;
		if (this.esteFormEstahExibindoAlgumModal()) {
			return;
		}
		if (this.campos.houveAlteracoes.isTrue()) {
			return;
		}
		if (this.isVinculado()) {
			this.props.vinculo.clear();
		}
		if (UCommons.notEmpty(this.props.onDelete)) {
			this.props.onDelete(this.getCampos().id.get());
		}
		this.cancelar();
	}

	podeExcluir() {
		if (this.props.somenteUpdate) return false;
		if (!this.possuiPermissaoParaExcluir()) return false;
		if (this.getCampos().registroBloqueado.isTrue()) return false;
		return true;
	}

	getBotoesCustomizados() {
		return null;
	}

	componentDidMount2() {
		this.observar(this.getCampos().controleVinculado);
		this.observar(this.getCampos().modalAuditoria);

		this.componentDidMount3();
	}

	componentDidMount3() {}

	save() {
		if (this.campos.houveAlteracoes.isFalse()) {
			return false;
		}
		const salvou = this.campos.save(this.props.vinculo, () => {
			if (this.isVinculado()) {
				message.info("As alterações terão efeito quando o registro for salvo!");
			} else {
				message.success("Registro Salvo com Sucesso!");
				this.afterSave();
			}
		});
		if (salvou) {
			this.saveJaFoiClicado = false;
		} else {
			this.saveJaFoiClicado = true;
			this.forceUpdate();
			message.error("Não foi possível salvar o registro pois há impedimentos!");
		}
		return salvou;
	}

	afterSave() {}

	getBody() {
		if (this.esteFormEstahExibindoAlgumModal()) {
			return (
				<Fragment>
					{this.getTabs()}
					{this.getModalPrivate()}
				</Fragment>
			);
		} else {
			return this.getTabs();
		}
	}

	getModalPrivate() {
		if (this.campos.modalAuditoria.isTrue()) {
			return <ModalAuditoria campos={this.campos}/>;
		}
		return this.getModal();
	}

	close() {
		if (this.campos.controleVinculado.isFalse()) {
			if (this.isVinculado()) {
				this.props.vinculo.set(false);
			}
			if (UCommons.notEmpty(this.props.onClose)) {
				this.props.onClose();
			}
		}
	}

	cancelar() {
		if (!this.campos.isReadOnly() && this.campos.houveAlteracoes.isTrue()) {
			this.campos.cancelarAlteracoes();
		} else {
			this.close();
		}
	}

	confirmar() {
		if (this.esteFormEstahExibindoAlgumModal()) {
			return;
		}
		if (this.save()) {
			if (UCommons.notEmpty(this.props.onConfirm)) {
				this.props.onConfirm();
			} else if (this.isVinculado()) {
				this.close();
			}
		}
	}

	esteFormEstahExibindoAlgumModal() {
		return this.campos.controleVinculado.isTrue() || this.campos.modalAuditoria.isTrue();
	}

	inputVinculado(bind, lg) {
		return (
			<FormItemInput
				after={this.getIconVinculado(bind)}
				bind={bind}
				lg={lg}
			/>
		);
	}

	getIconVinculado(bind) {
		if (bind.isDisabled() && bind.isEmpty()) {
			return null;
		}
		return (
			<Icon
				type={"edit"}
				style={FormEdit.STYLE_ICON}
				onClick={() => bind.change()}
			/>
		);
	}

	getModal() {
		throw new Error("???");
	}

	isVinculado() {
		return UCommons.notEmpty(this.props.vinculo);
	}

	possuiPermissaoParaVer() {
		return Session.getInstance().hasPermissao(this.entidade, "ver");
	}
	possuiPermissaoParaIncluir() {
		return Session.getInstance().hasPermissao(this.entidade, "incluir");
	}
	possuiPermissaoParaExcluir() {
		return Session.getInstance().hasPermissao(this.entidade, "excluir");
	}
	inputData(bind, lg) {

		let after;

		if (bind.isDisabled()) {
			after = FormEdit.ICON_CALENDAR;
		} else {
			after = (
				<Popover
				placement={"bottomLeft"}
				content={
					<Calendar fullscreen={false} onSelect={value => bind.setMoment(value)}/>
				}>
					{FormEdit.ICON_CALENDAR}
				</Popover>
			);
		}

		return <FormItemInput after={after} bind={bind} lg={lg}/>;
	}

	getTitle() {
		const cps = this.getCampos();
		if (UCommons.isEmpty(cps)) {
			throw new Error("cps is null");
		}
		if (cps.id.isEmpty()) {
			cps.setNovo();
		}
		const s = cps.id.get() < 0 ? " - Novo" : " - " + cps.id.asString();
		return this.getTitleImpl() + s;
	}

	ctrlDel() {
		this.delete();
	}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});
}
FormEdit.ICON_CALENDAR = <Icon type={"calendar"}/>;
FormEdit.STYLE_ICON = CommonStyles.POINTER.get();
FormEdit.MT15 = LayoutApp.createStyle().marginTop(15).get();
FormEdit.TITULO_GROUP = LayoutApp.createStyle().marginTop(15).cursor("pointer").get();
FormEdit.STYLE_SUBTABLE = LayoutApp.createStyle().margin(10).get();
FormEdit.STYLE_SUBTABLE_TITLE_ROW = LayoutApp.createStyle().bold(true).backgroundColor(Color.cinzaClaro4);
FormEdit.STYLE_SUBTABLE_COL = LayoutApp.createStyle().padding(10);
FormEdit.STYLE_SUBTABLE_ROW = LayoutApp.createStyle().cursor("pointer").borderBottomStyle(BorderStyle.solid).borderBottomWidth(0.5).borderBottomColor(Color.cinzaClaro2);
FormEdit.EDIT_BUTTON = LayoutApp.createStyle().marginLeft(10);
FormEdit.TD_BUTTONS_LEFT = Style.create().alignItems(AlignItens.flexStart).textAlign(TextAlign.left).paddingTop(10).paddingBottom(10).paddingLeft(20).get();
FormEdit.TD_BUTTONS_RIGHT = Style.create().alignItems(AlignItens.flexEnd).textAlign(TextAlign.right).paddingTop(10).paddingBottom(10).paddingRight(20).get();

FormEdit.defaultProps = {
	...FormGenerico.defaultProps,
	somenteUpdate: false
}
