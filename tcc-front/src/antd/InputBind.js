/* react-web-antd */
import React from 'react';
import Color from '../app/misc/consts/fixeds/Color';
import Style from '../app/misc/utils/Style';
import SuperComponent from '../app/misc/components/SuperComponent';
import UCommons from '../app/misc/utils/UCommons';
import {Input} from 'antd';

export default class InputBind extends SuperComponent {

	componentDidMount() {
		this.observar(this.props.bind);
	}

	render() {
		return (
			<Input
				placeholder={this.props.bind.getPlaceHolder()}
				addonBefore={this.props.before}
				addonAfter={this.props.after}
				value={this.props.bind.asString()}
				onChange={e => this.props.bind.setCast(e.target.value)}
				size={this.props.small ? "small" : null}
				style={this.getStyle()}
				onKeyPress={this.props.onKeyPress}
				onKeyDown={this.props.onKeyDown}
				disabled={this.props.bind.isDisabled()}
				allowClear={true}
				autoComplete={"off"}
				name={this.props.id}
				id={this.props.id}
			/>
		);
	}

	getStyle() {

		const st = Style.create().widthPercent(100).join(this.props.style);

		if (UCommons.notEmpty(this.props.bind)) {
			st.marginTop(5);
		}

		if (!this.props.bind.isValid() && !this.props.bind.isVirgin()) {
			return st.borderColor(Color.red).get();
		} else {
			return st.get();
		}
	}
}

InputBind.defaultProps = {
	...SuperComponent.defaultProps,
	small: false
}
