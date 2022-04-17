/* front-constructor */
import React from 'react';
import Botao from '../../antd/Botao';
import BotaoShape from '../../antd/BotaoShape';
import BotaoSize from '../../antd/BotaoSize';
import BotaoType from '../../antd/BotaoType';
import Style from '../../app/misc/utils/Style';
import SuperComponent from '../../app/misc/components/SuperComponent';
import Tema from '../outros/Tema';
import UCommons from '../../app/misc/utils/UCommons';

export default class FcBotao extends SuperComponent {

	render() {

		let st = Style.create();

		if (UCommons.eqeqeq(this.props.type, BotaoType.primary)) {
			st = st.join(Tema.buttonPrimary);
		}
		st = st.join(this.props.style);

		return (
			<Botao
			type={this.props.type}
			size={this.props.size}
			block={this.props.block}
			shape={this.props.shape}
			style={st}
			disabled={this.props.disabled}
			ghost={this.props.ghost}
			icon={this.props.icon}
			loading={this.props.loading}
			onClick={this.props.onClick}
			id={this.props.id}
			title={this.props.title}
			/>
		);
	}
}

FcBotao.defaultProps = {
	...SuperComponent.defaultProps,
	type: BotaoType.primary,
	shape: BotaoShape.def,
	size: BotaoSize.def,
	block: false,
	disabled: false,
	ghost: false,
	loading: false
}
