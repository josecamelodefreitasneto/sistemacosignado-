/* front-constructor */
import React from 'react';
import ArquivoEdit from '../arquivo/ArquivoEdit';
import LayoutApp from '../../../fc/components/LayoutApp';
import ObservacaoEditAbstract from '../../auto/observacao/ObservacaoEditAbstract';
import TextAreaBind from '../../../antd/TextAreaBind';
import {Card} from 'antd';
import {Col} from 'antd';
import {Row} from 'antd';

export default class ObservacaoEdit extends ObservacaoEditAbstract {

	grupo_geral_0(cps) {
		return (
			<Col lg={24} md={24} sm={24}>
				<Card size={"small"} style={LayoutApp.EMPTY.get()}>
					<Row gutter={24}>
						<div style={ObservacaoEdit.PADDING_AREA}>
							<TextAreaBind autoFocus={true} bind={cps.texto} rows={5}/>
						</div>
					</Row>
					<Row gutter={24}>
						{this.inputVinculado(cps.anexo, 6)}
					</Row>
				</Card>
			</Col>
		);
	}

	getModal() {
		const cps = this.getCampos();
		if (cps.anexo.isTrue()) {
			return (
				<ArquivoEdit
				tamanhoMaximoEmBytes={5242880}
				mensagemTamanho={"O tamanho máximo não deve exceder 5.242.880 bytes"}
				mensagemExtensoes={"São aceitos quaisquer tipos de arquivo"}
				vinculo={cps.anexo}
				isModal={true}
				/>
			);
		} else {
			throw new Error("???");
		}
	}
	setWidthForm = o => this.setState({widthForm:o});
	setAbaSelecionada = o => this.setState({abaSelecionada:o});

}
ObservacaoEdit.PADDING_AREA = LayoutApp.createStyle().padding(12).get();

ObservacaoEdit.defaultProps = ObservacaoEditAbstract.defaultProps;
