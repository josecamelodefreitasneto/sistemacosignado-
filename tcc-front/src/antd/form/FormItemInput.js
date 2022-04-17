/* react-web-antd */
import React from 'react';
import AFormItem from './AFormItem';
import InputBind from '../InputBind';

export default class FormItemInput extends AFormItem {

	getBody(bindParam, idComponent, error) {
		return <InputBind bind={bindParam} after={this.props.after} id={"input-"+idComponent}>{this.props.children}</InputBind>;
	}
}

FormItemInput.defaultProps = AFormItem.defaultProps;
