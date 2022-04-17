/* react-web-antd */
import React from 'react';
import AFormItem from './AFormItem';
import {Radio} from 'antd';
const RadioGroup = Radio.Group;

export default class RadioBoolean extends AFormItem {

	getBody(bindParam, idComponent, error) {

		const b = bindParam;

		return (
			<RadioGroup
				value={b.get()}
				disabled={b.isDisabled()}
				onChange={e => bindParam.setCast(e.target.value)}
				id={"input-" + idComponent}>
				<Radio value={true}>{this.props.textoSim}</Radio>
				<Radio value={false}>{this.props.textoNao}</Radio>
				{b.allowNull() && <Radio value={""}>?</Radio>}
			</RadioGroup>
		);
	}
}

RadioBoolean.defaultProps = {
	...AFormItem.defaultProps,
	textoSim: "Sim",
	textoNao: "Não"
}
