/* front-constructor */
import React from 'react';
import BorderStyle from '../../app/misc/consts/enums/BorderStyle';
import BotaoSize from '../../antd/BotaoSize';
import BotaoType from '../../antd/BotaoType';
import Color from '../../app/misc/consts/fixeds/Color';
import FcBotao from './FcBotao';
import LayoutApp from './LayoutApp';
import SuperComponent from '../../app/misc/components/SuperComponent';
import TextAlign from '../../app/misc/consts/enums/TextAlign';
import UCommons from '../../app/misc/utils/UCommons';
import UInteger from '../../app/misc/utils/UInteger';
import UString from '../../app/misc/utils/UString';
import {Popover} from 'antd';

export default class PopoverErros extends SuperComponent {

	render() {
		const erros = this.props.campos.getErros();
		if (erros.isEmpty()) {
			return null;
		} else {
			return (
				<Popover
					title={"Campos com erro"}
					trigger={"click"}
					getPopupContainer={o => UCommons.isEmpty(o) || UCommons.isEmpty(o.parentNode) ? o : o.parentNode}
					content={
						erros.map(campo =>
							<li style={PopoverErros.STYLE_LI} key={campo.getLabel()}>
								<FcBotao
									title={campo.getAtribute("aba") + " - " + campo.getLabel()}
									type={BotaoType.link}
									block={true}
									style={PopoverErros.STYLE_BOTAO_INFO_1}
									onClick={() => this.focusCampo(campo)}
								/>

								<FcBotao
									title={campo.getInvalidMessage()}
									type={BotaoType.link}
									size={BotaoSize.small}
									block={true}
									style={PopoverErros.STYLE_BOTAO_INFO_2}
									onClick={() => this.focusCampo(campo)}
								/>
							</li>
						)
					}>
					<FcBotao
						icon={"exclamation-circle"}
						type={BotaoType.link}
						style={PopoverErros.ERROR_STYLE}
						title={UInteger.equals(erros.length, 1) ? "1 impedimento" : erros.length + " impedimentos"}
					/>
				</Popover>
			);
		}
	}

	componentDidMount() {
		this.observar(this.props.campos);
	}

	focusCampo(campo) {
		const aba = campo.getAtribute("aba");
		if (UString.equals(aba, this.props.abaSelecionada)) {
			const labelNode = document.getElementById("label-"+campo.getIdComponent());
			if (UCommons.notEmpty(labelNode)) {
				labelNode.scrollIntoView(true);
				const inputId = "input-"+campo.getIdComponent();
				const element = document.getElementById(inputId);
				if (UCommons.isEmpty(element)) {
					throw new Error("Nao encontrado: " + inputId);
				}
				element.focus();
			}
		} else {
			this.props.setAbaSelecionada(aba);
			setTimeout(() => this.focusCampo(campo), 250);
		}
	}
}
PopoverErros.STYLE_BOTAO_INFO_1 = LayoutApp.createStyle().textAlign(TextAlign.left).color(Color.black);
PopoverErros.STYLE_BOTAO_INFO_2 = LayoutApp.createStyle().textAlign(TextAlign.left).color(Color.red).fontSize(10);
PopoverErros.STYLE_LI = LayoutApp.createStyle().borderBottomColor(Color.cinzaClaro).borderBottomStyle(BorderStyle.solid).borderBottomWidth(1).get();
PopoverErros.ERROR_STYLE = LayoutApp.createStyle().color(Color.red);

PopoverErros.defaultProps = SuperComponent.defaultProps;
