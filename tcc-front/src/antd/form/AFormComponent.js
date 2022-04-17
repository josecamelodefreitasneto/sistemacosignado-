/* react-web-antd */
import React from 'react';
import Color from '../../app/misc/consts/fixeds/Color';
import Style from '../../app/misc/utils/Style';
import SuperComponent from '../../app/misc/components/SuperComponent';
import UString from '../../app/misc/utils/UString';
import {Col} from 'antd';
import {Form} from 'antd';
import {Fragment} from 'react';
const FormItem = Form.Item;

export default class AFormComponent extends SuperComponent {

	render() {

		const lgg = this.props.lg < 1 ? 3 : this.props.lg;
		let md = parseInt(lgg * 1.5);
		if (md > 24) {
			md = 24;
		}

		return (
			<Col lg={lgg} md={md} sm={24} id={"label-" + this.props.idComponent}>
				<FormItem label={this.getLabel()} className={"ant-form-item-with-help"}>
					{this.props.children}
					<div className={"ant-form-explain"}>
						{UString.notEmpty(this.props.error) && <label style={AFormComponent.LABEL_ERROR}>{this.props.error}</label>}
					</div>
				</FormItem>
			</Col>
		);
	}

	getLabel() {
		if (UString.isEmpty(this.props.label)) {
			return null;
		} else {
			return (
				<Fragment>
					{this.getAsterisco()}
					<label htmlFor={"input-" + this.props.idComponent}>{this.props.label}</label>
				</Fragment>
			);
		}
	}

	getAsterisco() {
		if (this.props.asterisco) {
			return <label style={AFormComponent.ASTERISCO_STYLE} htmlFor={"input-" + this.props.idComponent}>* </label>;
		} else {
			return null;
		}
	}
}
AFormComponent.LABEL_ERROR = Style.create().color(Color.red).fontSize(10).get();
AFormComponent.ASTERISCO_STYLE = Style.create().color(Color.red).fontSize(12).bold(true).get();

AFormComponent.defaultProps = {
	...SuperComponent.defaultProps,
	lg: 0,
	idComponent: 0,
	asterisco: false
}
