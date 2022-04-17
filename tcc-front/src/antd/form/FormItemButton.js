/* react-web-antd */
import React from 'react';
import AFormItem from './AFormItem';
import Botao from '../Botao';
import Style from '../../app/misc/utils/Style';

export default class FormItemButton extends AFormItem {

	getBody(bindParam, idComponent, error) {
		return (
			<Botao
				style={FormItemButton.ESTILO}
				title={bindParam.getLabel()}
				onClick={() => this.change()}
				id={"input-"+idComponent}>
				{this.props.children}
			</Botao>
		);
	}

	getLabel() {
		return null;
	}

	change() {
		let b = this.getBind();
		b.change();
	}
}
FormItemButton.ESTILO = Style.create().widthPercent(100).marginTop(25);

FormItemButton.defaultProps = AFormItem.defaultProps;
