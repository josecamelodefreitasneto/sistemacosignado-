/* front-constructor */
import React from 'react';
import BindingString from '../../app/campos/support/BindingString';
import FcBotao from './FcBotao';
import FormEditButtons from './FormEditButtons';
import FormGenerico from './FormGenerico';
import LayoutApp from './LayoutApp';
import TextAreaBind from '../../antd/TextAreaBind';
import {message} from 'antd';

export default class ModalMotivo extends FormGenerico {
	bind;

	ehModal() {
		return true;
	}

	getTitle() {
		return this.props.title;
	}

	getWidthModal() {
		return 50;
	}

	componentDidMount2() {
		this.bind = new BindingString("Digite um texto de até "+this.props.length+" caracteres", this.props.length);
	}

	getBody() {
		return <TextAreaBind autoFocus={true} bind={this.bind} rows={5}/>;
	}

	getFooter() {
		const style = LayoutApp.createStyle().widthPercent(100).padding(10).get();
		return (
			<div style={style}>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} onClick={this.props.onClose} title={"Fechar (Esc)"}/>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} onClick={() => this.confirmar()} title={"Confirmar (Ctrl+Enter)"}/>
			</div>
		);
	}

	close() {
		this.props.onClose();
	}
	confirmar() {
		if (this.bind.isEmpty()) {
			message.error("Texto é obrigatório!");
		} else {
			this.props.onConfirm(this.bind.get());
		}
	}
	setWidthForm = o => this.setState({widthForm:o});
}

ModalMotivo.defaultProps = {
	...FormGenerico.defaultProps,
	length: 0
}
