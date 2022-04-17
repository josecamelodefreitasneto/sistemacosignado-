/* react-web */
import React from 'react';
import AlignItens from '../consts/enums/AlignItens';
import Color from '../consts/fixeds/Color';
import Style from '../utils/Style';
import SuperComponent from './SuperComponent';
import TextAlign from '../consts/enums/TextAlign';
import UCommons from '../utils/UCommons';
import UString from '../utils/UString';

export default class Texto extends SuperComponent {

	render() {

		if (UString.isEmpty(this.props.label)) {
			return null;
		}

		const st = Style.create().join(this.props.style);
		if (!UCommons.isEmpty(this.props.bold)) {
			st.bold(this.props.bold);
		}
		if (!UCommons.isEmpty(this.props.center)) {
			st.textAlign(TextAlign.center);
			st.alignItems(AlignItens.center);
		}
		if (!UCommons.isEmpty(this.props.widthPercent)) {
			st.widthPercent(this.props.widthPercent);
		}
		if (!UCommons.isEmpty(this.props.fontSize)) {
			st.fontSize(this.props.fontSize);
		}
		if (this.props.red) {
			st.color(Color.red);
		}
		if (UCommons.notEmpty(this.props.blueOrRed)) {
			if (this.props.blueOrRed) {
				st.color(Color.blue);
			} else {
				st.color(Color.red);
			}
		}
		if (UCommons.notEmpty(this.props.color)) {
			st.color(this.props.color);
		}

		let margins = st.copy();
		st.marginClear();
		st.marginBottomClear();
		st.marginLeftClear();
		st.marginRightClear();
		st.marginTopClear();

		st.paddingClear();
		st.paddingBottomClear();
		st.paddingLeftClear();
		st.paddingRightClear();
		st.paddingTopClear();

		return <div style={margins.get()}><span children={this.props.label} style={st.get()} className={this.props.className}/></div>;
	}
}

Texto.defaultProps = {
	...SuperComponent.defaultProps,
	red: false
}
