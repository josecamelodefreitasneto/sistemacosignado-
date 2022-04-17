/* front-constructor */
import React from 'react';
import BotaoType from '../../antd/BotaoType';
import FcBotao from './FcBotao';
import FormEditButtons from './FormEditButtons';
import FormGenerico from './FormGenerico';
import FormKeyDownObserver from './FormKeyDownObserver';
import LayoutApp from './LayoutApp';
import {Modal} from 'antd';

export default class ModalConfirmar extends FormKeyDownObserver {

	onKeyDown0(e) {
		if (e.esc()) {
			this.props.onCancel();
		} else if (e.enter()) {
			this.props.onConfirm();
		}
	}

	render() {
		return (
			<Modal
				title={this.props.title}
				visible={true}
				bodyStyle={FormGenerico.STYLE_MODAL_BODY.get()}
				closable={false}
				keyboard={false}
				maskClosable={false}
				destroyOnClose={true}
				footer={this.getBotoes()}
			>{this.props.children}</Modal>
		);
	}

	getBotoes() {
		return (
			<div style={this.getStyle()}>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} type={BotaoType.normal} onClick={this.props.onCancel} title={"Cancelar (Esc)"}/>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} type={BotaoType.primary} onClick={this.props.onConfirm} title={"Confirmar (Enter)"}/>
			</div>
		);
	}

	getStyle() {
		return LayoutApp.createStyle().widthPercent(100).padding(10).get();
	}
}

ModalConfirmar.defaultProps = FormKeyDownObserver.defaultProps;
