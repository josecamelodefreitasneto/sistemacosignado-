/* react-web-antd */
import React from 'react';
import AFormItem from './AFormItem';
import BCheckBox from '../BCheckBox';

export default class FormItemCheck extends AFormItem {

	getBody(bindP, idComponent, error) {
		return <BCheckBox bind={ bindP} id={"input-"+idComponent}>{this.props.children}</BCheckBox>;
	}

	getLabel() {
		return null;
	}

}

FormItemCheck.defaultProps = AFormItem.defaultProps;
