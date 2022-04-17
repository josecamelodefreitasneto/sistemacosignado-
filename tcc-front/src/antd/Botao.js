/* react-web-antd */
import React from 'react';
import BotaoShape from './BotaoShape';
import BotaoSize from './BotaoSize';
import BotaoType from './BotaoType';
import CommonStyles from '../app/misc/styles/CommonStyles';
import SuperComponent from '../app/misc/components/SuperComponent';
import {Button} from 'antd';

export default class Botao extends SuperComponent {

	render() {
		return (
			<Button
			type={this.props.type.s}
			size={this.props.size.s}
			block={this.props.block}
			shape={this.props.shape.s}
			style={this.props.style.get()}
			disabled={this.props.disabled}
			ghost={this.props.ghost}
			icon={this.props.icon}
			loading={this.props.loading}
			onClick={this.props.onClick}
			id={this.props.id}
			>{this.props.title}</Button>
		);
	}
}

Botao.defaultProps = {
	...SuperComponent.defaultProps,
	type: BotaoType.primary,
	shape: BotaoShape.def,
	size: BotaoSize.def,
	block: false,
	style: CommonStyles.EMPTY,
	disabled: false,
	ghost: false,
	loading: false
}
