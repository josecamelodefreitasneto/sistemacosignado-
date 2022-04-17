/* react-web-antd */
import React from 'react';
import AFormItem from './AFormItem';
import SelectBind from '../SelectBind';

export default class FormItemSelect extends AFormItem {
	getBody(bindParam, idComponent, error) {
		return <SelectBind bind={ bindParam} id={"input-"+idComponent}/>;
	}
}

FormItemSelect.defaultProps = AFormItem.defaultProps;
