/* react-web-antd */
import React from 'react';
import Style from '../app/misc/utils/Style';
import SuperComponent from '../app/misc/components/SuperComponent';
import {Checkbox} from 'antd';

export default class BCheckBox extends SuperComponent {

	render() {
		this.props.bind.addRenderObserver(this);
		return (
			<Checkbox
				checked={this.props.bind.isTrue()}
				onChange={e => this.props.bind.set(e.target.checked)}
				style={BCheckBox.STYLE_DEF}
			><span>{this.props.bind.getLabel()}</span></Checkbox>
		);
	}
}
BCheckBox.STYLE_DEF = Style.create().paddingTop(3).get();

BCheckBox.defaultProps = SuperComponent.defaultProps;
