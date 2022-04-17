/* react-web */
import React from 'react';
import Color from '../consts/fixeds/Color';
import Display from '../consts/enums/Display';
import Style from '../utils/Style';
import SuperComponent from './SuperComponent';

export default class Input extends SuperComponent {

	static DIV_STYLE = Style.create().widthPercent(100);
	static INPUT_STYLE = Style.create().widthPercent(100).fontSize(12);
	static LABEL_STYLE = Style.create().widthPercent(100);
	static LABEL_STYLE_ERRO = Style.create().widthPercent(100).color(Color.red);

	render() {
		this.props.bind.addRenderObserver(this);
		if (!this.props.bind.isVisible()) {
			return null;
		}
		return (
			<div style={Input.DIV_STYLE.join(this.props.divStyle).get()}>
				<span style={Input.LABEL_STYLE.join(this.props.labelStyle).get()}>{this.props.bind.getLabel()}</span>
				<div style={Style.create().display(Display.flex).get()}>
					{this.props.before}
					<input
						value={this.props.bind.asString()}
						onChange={e => this.props.bind.setCast(e.target.value)}
						disabled={this.props.bind.isDisabled()}
						type={this.props.bind.isNumeric() ? "tel" : this.props.type}
						style={Input.INPUT_STYLE.join(this.props.inputStyle).get()}
					/>
					{this.props.after}
				</div>
				<span style={Input.LABEL_STYLE_ERRO.get()}>{this.props.bind.getInvalidMessage()}</span>
			</div>
		);
	}
}

Input.defaultProps = {
	...SuperComponent.defaultProps,
	type: "text"
}
