/* front-constructor */
import React from 'react';
import EsqueciSenhaCampos from '../../../app/cruds/esqueciSenha/EsqueciSenhaCampos';
import FcBotao from '../FcBotao';
import FormEditButtons from '../FormEditButtons';
import FormGenerico from '../FormGenerico';
import FormItemInput from '../../../antd/form/FormItemInput';
import Session from '../../../app/estado/Session';
import Style from '../../../app/misc/utils/Style';

export default class EsqueciSenhaEmailView extends FormGenerico {

	session;

	getWidthModal() {
		return 50;
	}

	getTitle() {
		return "Informe o login de seu usuário";
	}

	getBody() {
		return <FormItemInput bind={this.session.login} lg={24}/>;
	}
	getFooter() {
		return (
			<div style={EsqueciSenhaEmailView.DIV_BUTTONS}>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} title={"Enviar E-mail (Enter)"} onClick={() => this.confirmar()}/>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} title={"Cancelar (Esc)"} onClick={() => this.close()}/>
			</div>
		);
	}

	ehModal() {
		return true;
	}

	close() {
		this.session.modalEsqueciSenhaEmail.set(false);
	}

	confirmar() {
		EsqueciSenhaCampos.getInstance().enviarEmail();
	}

	enter() {
		this.confirmar();
	}

	componentDidMount2() {
		this.session = Session.getInstance();
	}
	setWidthForm = o => this.setState({widthForm:o});

}
EsqueciSenhaEmailView.DIV_BUTTONS = Style.create().widthPercent(100).padding(20).get();

EsqueciSenhaEmailView.defaultProps = FormGenerico.defaultProps;
