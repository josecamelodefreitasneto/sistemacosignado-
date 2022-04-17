/* react-web-antd */
import React from 'react';
import AFormItem from './AFormItem';
import {Radio} from 'antd';
const RadioGroup = Radio.Group;

export default class RadioList extends AFormItem {

	getBody(bindParam, idComponent, error) {

		const b = bindParam;

		return (
			<RadioGroup
				value={b.get()}
				onChange={e => bindParam.setCast(e.target.value)}
				id={"input-" + idComponent}>
				{b.getItens().filter(o => o.id > 0).map(o => <Radio value={o.id}>{o.text}</Radio>)}
			</RadioGroup>
		);
	}

}

RadioList.defaultProps = AFormItem.defaultProps;
