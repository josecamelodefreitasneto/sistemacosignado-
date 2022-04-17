/* front-constructor */
import React from 'react';
import ArquivoDragger from './ArquivoDragger';
import ArquivoEditAbstract from '../../auto/arquivo/ArquivoEditAbstract';

export default class ArquivoEdit extends ArquivoEditAbstract {
	mensagemErro;

	getTabs() {
		return (
			<ArquivoDragger
				extensoes={this.props.extensoes}
				invalidas={this.props.invalidas}
				mensagemExtensoes={this.props.mensagemExtensoes}
				mensagemTamanho={this.props.mensagemTamanho}
				tamanhoMaximoEmBytes={this.props.tamanhoMaximoEmBytes}
			/>
		);
	}

	getTitleImpl() {
		return "Fazer Upload de Arquivo";
	}

	componentDidMount3() {
		this.observar(this.getCampos());
	}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});

}

ArquivoEdit.defaultProps = {
	...ArquivoEditAbstract.defaultProps,
	tamanhoMaximoEmBytes: 0,
	invalidas: false
}
