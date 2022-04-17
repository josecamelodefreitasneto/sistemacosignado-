/* tcc-java */
import React from 'react';
import ClienteEditAbstract from '../../auto/cliente/ClienteEditAbstract';
import FormItemButton from '../../../antd/form/FormItemButton';

export default class ClienteEdit extends ClienteEditAbstract {
	botaoCalcular() {
		if (this.getCampos().statusEmAtendimento()) {
			return <FormItemButton bind={this.getCampos().botaoCalcular}/>;
		} else {
			return null;
		}
	}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});
}

ClienteEdit.defaultProps = ClienteEditAbstract.defaultProps;
