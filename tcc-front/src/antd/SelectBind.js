/* react-web-antd */
import React from 'react';
import Botao from './Botao';
import BotaoType from './BotaoType';
import CommonStyles from '../app/misc/styles/CommonStyles';
import InputBind from './InputBind';
import Style from '../app/misc/utils/Style';
import SuperComponent from '../app/misc/components/SuperComponent';
import TextAlign from '../app/misc/consts/enums/TextAlign';
import UCommons from '../app/misc/utils/UCommons';
import UString from '../app/misc/utils/UString';
import {AutoComplete} from 'antd';
import {Icon} from 'antd';
const AutoCompleteOption = AutoComplete.Option;

export default class SelectBind extends SuperComponent {
	constructor(props){
		super(props);
		this.state.selectedIndex = 0;
	}

	render() {

		if (!this.props.bind.isVisible()) return null;
		if (this.props.bind.isDisabled()) {
			return this.getDisabled();
		}

		const sugestoes = this.props.bind.getSugestoes();

		const sugestao = sugestoes.get(this.state.selectedIndex);
		return (
			<AutoComplete style={CommonStyles.W100P.get()} dropdownMatchSelectWidth={false} dataSource={
				sugestoes.map(o =>
					<AutoCompleteOption value={o.value.text} open={this.props.open} key={o}>
						<Botao
							title={o.value.text}
							type={UCommons.equals(sugestao, o) ? BotaoType.primary : BotaoType.link}
							block={true}
							onClick={() => this.props.bind.selectSugestion(o)}
							style={SelectBind.BOTAO_STYLE}
						/>
					</AutoCompleteOption>
				)}>
				<InputBind
				bind={this.props.bind.input}
				style={this.props.style}
				before={this.props.before}
				after={this.getAfter()}
				small={this.props.small}
				onKeyDown={e => {
					if (UString.equals("ArrowDown", e.key)) {
						if (this.state.selectedIndex < sugestoes.length-1) {
							this.setSelectedIndex(this.state.selectedIndex+1);
						}
					} else if (UString.equals("ArrowUp", e.key)) {
						if (this.state.selectedIndex > 0) {
							this.setSelectedIndex(this.state.selectedIndex-1);
						}
					} else if (UString.equals("Enter", e.key)) {
						this.props.bind.selectSugestion(sugestoes.get(this.state.selectedIndex));
					}
					this.dropDown();
				}}

				id={this.getId()}
				/>
			</AutoComplete>
		);
	}

	getDisabled() {
		return (
			<InputBind
			bind={this.props.bind.input}
			style={this.props.style}
			before={this.props.before}
			small={this.props.small}
			id={this.getId()}
			/>
		);
	}

	getAfter() {
		if (UCommons.isEmpty(this.props.after)) {
			return <Icon type={"caret-down"} style={CommonStyles.POINTER.get()} onClick={() => this.dropDown()}/>;
		} else {
			return this.props.after;
		}
	}

	getId() {
		if (UString.notEmpty(this.props.id)) {
			return this.props.id;
		} else {
			return this.stringId;
		}
	}

	componentDidMount() {
		this.observar(this.props.bind);
		this.observar(this.props.bind.input);
	}

	dropDown() {
		document.getElementById(this.getId()).click();
	}
	setSelectedIndex = o => this.setState({selectedIndex:o});
}
SelectBind.BOTAO_STYLE = Style.create().textAlign(TextAlign.left);

SelectBind.defaultProps = {
	...SuperComponent.defaultProps,
	small: false,
	open: true
}
