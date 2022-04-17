/* front-constructor */
import React from 'react';
import FcBotao from './FcBotao';
import SuperComponent from '../../app/misc/components/SuperComponent';
import {Popconfirm} from 'antd';

export default class BotaoComConfirmacao extends SuperComponent {

	render() {
		return (
			<Popconfirm
			onConfirm={this.props.onConfirm}
			title={this.props.pergunta}
			okText={this.props.textSim}
			cancelText={this.props.textNao}
			><FcBotao title={this.props.title}/></Popconfirm>
		);
	}
}

BotaoComConfirmacao.defaultProps = {
	...SuperComponent.defaultProps,
	pergunta: "Confirma?",
	textSim: "Sim",
	textNao: "Não"
}
