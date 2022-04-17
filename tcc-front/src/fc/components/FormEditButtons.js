/* front-constructor */
import React from 'react';
import BotaoType from '../../antd/BotaoType';
import FcBotao from './FcBotao';
import LayoutApp from './LayoutApp';
import ModalCamposAlterados from './ModalCamposAlterados';
import PopoverErros from './PopoverErros';
import SuperComponent from '../../app/misc/components/SuperComponent';
import UCommons from '../../app/misc/utils/UCommons';
import {Fragment} from 'react';
import {Popconfirm} from 'antd';

export default class FormEditButtons extends SuperComponent {
	constructor(props){
		super(props);
		this.state.showModalAlteracoes = false;
	}

	render() {

		if (this.props.campos.isReadOnly()) {
			return (
				<div style={this.getStyle()}>
					{this.botaoCancelar()}
				</div>
			);
		}

		if (this.props.campos.houveAlteracoes.isTrue()) {
			return (
				<Fragment>
					<div style={this.getStyle()}>
						{this.getBotaoErro()}
						<FcBotao style={FormEditButtons.STYLE_BUTTON} type={BotaoType.normal} onClick={() => this.setShowModalAlteracoes(true)} title={"Listar Alterações"}/>
						<FcBotao style={FormEditButtons.STYLE_BUTTON} type={BotaoType.normal} onClick={this.props.onCancel} title={"Cancelar Alterações (Esc)"}/>
						<FcBotao style={FormEditButtons.STYLE_BUTTON} onClick={this.props.onSave} title={this.getSaveTitle()}/>
					</div>
					{this.getModalAlteracoes()}
				</Fragment>
			);
		} else {
			return (
				<div style={this.getStyle()}>
					{this.renderDelete()}
					{this.botaoCancelar()}
				</div>
			);
		}
	}

	botaoCancelar() {
		return <FcBotao style={FormEditButtons.STYLE_BUTTON} type={BotaoType.normal} onClick={this.props.onCancel} title={this.getCancelTitle()}/>;
	}

	renderDelete() {
		if (this.props.somenteUpdate) return null;
		if (this.props.campos.id.isEmpty()) return null;
		if (this.props.campos.id.get() < 1) return null;
		if (UCommons.isEmpty(this.props.onDelete)) return null;
		return (
			<Popconfirm
			onConfirm={this.props.onDelete}
			title={"Confirma exclusão?"}
			okText={"Sim"}
			cancelText={"Não"}
			><FcBotao style={FormEditButtons.STYLE_BUTTON} type={BotaoType.danger} title={this.getRemoveTitle()}/></Popconfirm>
		);
	}

	getModalAlteracoes() {
		if (this.state.showModalAlteracoes) {
			return <ModalCamposAlterados valores={this.props.campos.camposAlterados()} onClose={() => this.setShowModalAlteracoes(false)}/>;
		} else {
			return null;
		}
	}

	getStyle() {
		return LayoutApp.createStyle().widthPercent(100).get();
	}

	componentDidMount() {
		this.observar(this.props.campos);
	}

	getBotaoErro() {
		if (!this.props.saveJaFoiClicado) {
			return null;
		} else {
			return (
				<PopoverErros
					campos={this.props.campos}
					abaSelecionada={this.props.abaSelecionada}
					setAbaSelecionada={this.props.setAbaSelecionada}
				/>
			);
		}
	}

	getSaveTitle() {
		return this.props.vinculado ? "Confirmar (Ctrl + Enter)" : "Salvar (Ctrl + Enter)";
	}
	getCancelTitle() {
		return this.props.vinculado ? "Fechar (Esc)" : "Sair (Esc)";
	}
	getRemoveTitle() {
		return this.props.vinculado ? "Remover (Ctrl + Del)" : "Excluir (Ctrl + Del)";
	}
	setShowModalAlteracoes = o => this.setState({showModalAlteracoes:o});
}
FormEditButtons.STYLE_BUTTON = LayoutApp.createStyle().margin(5).marginRight(0);

FormEditButtons.defaultProps = {
	...SuperComponent.defaultProps,
	saveJaFoiClicado: false,
	vinculado: false,
	somenteUpdate: false
}
