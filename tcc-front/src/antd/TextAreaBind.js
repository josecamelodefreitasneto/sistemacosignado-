/* react-web-antd */
import React from 'react';
import Color from '../app/misc/consts/fixeds/Color';
import CommonStyles from '../app/misc/styles/CommonStyles';
import Style from '../app/misc/utils/Style';
import SuperComponent from '../app/misc/components/SuperComponent';
import {Input} from 'antd';
const TextArea = Input.TextArea;

export default class TextAreaBind extends SuperComponent {

	componentDidMount() {
		this.observar(this.props.bind);
	}

	render() {
		const st = CommonStyles.W100P.join(!this.props.bind.isValid() && !this.props.bind.isVirgin() ? TextAreaBind.BORDER_ERROR : CommonStyles.EMPTY).join(this.props.style);
		return (
			<TextArea
				placeholder={this.props.bind.getPlaceHolder()}
				value={this.props.bind.asString()}
				onChange={e => this.props.bind.setCast(e.target.value)}
				style={st.get()}
				onKeyPress={this.props.onKeyPress}
				onKeyDown={this.props.onKeyDown}
				disabled={this.props.bind.isDisabled()}
				autoFocus={this.props.autoFocus}
				rows={this.props.rows}
				id={this.props.id}
			/>
		);
	}
}
TextAreaBind.BORDER_ERROR = Style.create().borderColor(Color.red);

TextAreaBind.defaultProps = {
	...SuperComponent.defaultProps,
	rows: 2
}
