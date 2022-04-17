/* front-constructor */
import React from 'react';
import AlignItens from '../../../app/misc/consts/enums/AlignItens';
import BotaoType from '../../../antd/BotaoType';
import Color from '../../../app/misc/consts/fixeds/Color';
import CommonStyles from '../../../app/misc/styles/CommonStyles';
import FcBotao from '../FcBotao';
import FlexDirection from '../../../app/misc/consts/enums/FlexDirection';
import LayoutApp from '../LayoutApp';
import Loading from '../../../antd/Loading';
import ObservacaoUtils from '../../../app/cruds/observacao/ObservacaoUtils';
import Style from '../../../app/misc/utils/Style';
import SuperComponent from '../../../app/misc/components/SuperComponent';
import UCommons from '../../../app/misc/utils/UCommons';
import UString from '../../../app/misc/utils/UString';
import {Icon} from 'antd';
import {List} from 'antd';
const ListItemMeta = List.Item.Meta;
const ListItem = List.Item;

export default class ObservacaoView extends SuperComponent {

	render() {
		if (this.props.campos.observacoes.carregado()) {
			return this.render0();
		} else if (UString.notEmpty(this.props.campos.observacoes.getMensagemErro())) {
			const s = "Ocorreu um erro no servico: " + this.props.campos.observacoes.getMensagemErro();
			return <span style={ObservacaoView.STYLE_ERROR.get()}>{s}</span>;
		} else {
			setTimeout(() => this.props.campos.observacoes.carregar(), 250);
			return <Loading/>;
		}
	}

	render0() {
		return (
			<div style={Style.create().padding(20).get()}>
				{!this.props.campos.isReadOnly() &&
					<ListItem style={ObservacaoView.SUPER_BUTTONS}>
						<FcBotao title={"Novo"} type={BotaoType.normal} onClick={() => this.props.campos.observacoesEdit(ObservacaoUtils.getInstance().novo())}/>
					</ListItem>
				}
				<List
					itemLayout={"horizontal"}
					dataSource={this.props.campos.observacoes.getItens()}
					renderItem={item => {
						const o = item;
						return (
							<ListItem>
								<ListItemMeta
									title={<span>{o.getTexto()}</span>}
								/>
								{UCommons.notEmpty(o.getAnexo()) && <Icon type={"paper-clip"}/>}
								<Icon type={"edit"} theme={"twoTone"} style={ObservacaoView.STYLE_ICON_EDIT.get()} onClick={() => this.props.campos.observacoesEdit(o)}/>
							</ListItem>
						);
					}}
				/>
			</div>
		);
	}

	componentDidMount() {
		this.observar(this.props.campos.observacoes);
	}
}
ObservacaoView.SUPER_BUTTONS = LayoutApp.createStyle().alignItems(AlignItens.flexEnd).flexDirection(FlexDirection.column).get();
ObservacaoView.STYLE_ICON_EDIT = CommonStyles.POINTER.copy().margin(5).color(Color.blue);
ObservacaoView.STYLE_ERROR = LayoutApp.createStyle().margin(10).bold(true).color(Color.red);

ObservacaoView.defaultProps = SuperComponent.defaultProps;
