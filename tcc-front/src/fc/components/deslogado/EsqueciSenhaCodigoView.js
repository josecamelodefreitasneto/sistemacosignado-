/* front-constructor */
import React from 'react';
import EsqueciSenhaCampos from '../../../app/cruds/esqueciSenha/EsqueciSenhaCampos';
import FcBotao from '../FcBotao';
import FormEditButtons from '../FormEditButtons';
import FormGenerico from '../FormGenerico';
import FormItemInput from '../../../antd/form/FormItemInput';
import Session from '../../../app/estado/Session';
import Style from '../../../app/misc/utils/Style';

export default class EsqueciSenhaCodigoView extends FormGenerico {

	session;

	getWidthModal() {
		return 50;
	}

	getTitle() {
		return "Informe o código que enviamos em seu e-mail";
	}

	getBody() {
		return <FormItemInput bind={this.session.esqueciSenhaCodigo} lg={24}/>;
	}
	getFooter() {
		return (
			<div style={EsqueciSenhaCodigoView.DIV_BUTTONS}>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} title={"Confirmar (Enter)"} onClick={() => this.confirmar()}/>
				<FcBotao style={FormEditButtons.STYLE_BUTTON} title={"Cancelar (Esc)"} onClick={() => this.close()}/>
			</div>
		);
	}

	ehModal() {
		return true;
	}

	close() {
		this.session.modalEsqueciSenhaCodigo.set(false);
	}

	confirmar() {
		EsqueciSenhaCampos.getInstance().confirmarCodigo();
	}

	enter() {
		this.confirmar();
	}

	componentDidMount2() {
		this.session = Session.getInstance();
	}
	setWidthForm = o => this.setState({widthForm:o});

}
EsqueciSenhaCodigoView.DIV_BUTTONS = Style.create().widthPercent(100).padding(20).get();

EsqueciSenhaCodigoView.defaultProps = FormGenerico.defaultProps;
