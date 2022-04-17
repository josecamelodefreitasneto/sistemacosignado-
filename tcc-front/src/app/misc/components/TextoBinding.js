/* react-web */
import React from 'react';
import Finais from './Finais';
import Style from '../utils/Style';
import SuperComponent from './SuperComponent';
import Texto from './Texto';
import UCommons from '../utils/UCommons';
import UString from '../utils/UString';
import {Fragment} from 'react';

export default class TextoBinding extends SuperComponent {

	render() {

		if (UCommons.isEmpty(this.props.binding)) {
			return Finais.noBinding;
		}

		this.props.binding.addRenderObserver(this);

		let s = this.props.binding.asString();
		if (UCommons.notEmpty(this.props.format)) {
			s = this.props.format(s);
		}
		if (UString.isEmpty(s)) {
			return <Fragment/>;
		} else {
			return <Texto style={Style.create().fontSize(this.props.size).bold(this.props.bold).color(this.props.color)} label={s}/>;
		}
	}
}

TextoBinding.defaultProps = {
	...SuperComponent.defaultProps,
	size: 12,
	bold: false
}
