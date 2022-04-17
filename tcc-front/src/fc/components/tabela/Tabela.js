/* front-constructor */
import React from 'react';
import Border from '../../../app/misc/utils/Border';
import BorderStyle from '../../../app/misc/consts/enums/BorderStyle';
import Box from '../../../app/misc/utils/Box';
import Color from '../../../app/misc/consts/fixeds/Color';
import CommonStyles from '../../../app/misc/styles/CommonStyles';
import Foreach from '../../../app/misc/components/Foreach';
import GetText from '../../../app/misc/utils/GetText';
import LayoutApp from '../LayoutApp';
import Loading from '../../../antd/Loading';
import SuperComponent from '../../../app/misc/components/SuperComponent';
import UArray from '../../../app/misc/utils/UArray';
import UBoolean from '../../../app/misc/utils/UBoolean';
import UCommons from '../../../app/misc/utils/UCommons';
import UNative from '../../../app/misc/utils/UNative';
import UString from '../../../app/misc/utils/UString';
import {Checkbox} from 'antd';
import {Fragment} from 'react';
import {Icon} from 'antd';
import {List} from 'antd';
import {Popconfirm} from 'antd';
import {Popover} from 'antd';
const ListItem = List.Item;

export default class Tabela extends SuperComponent {

	static selectedTable;
	static selected;

	showEdit() {
		return UCommons.notEmpty(this.props.onClick);
	}
	showDelete() {
		return UCommons.notEmpty(this.props.onDelete);
	}

	render() {
		if (this.props.bind.carregado()) {
			return (
				<table style={Tabela.STYLE_SUBTABLE}>
					{this.getHead()}
					{this.getBody()}
				</table>
			);
		} else if (UString.notEmpty(this.props.bind.getMensagemErro())) {
			const s = "Ocorreu um erro no servico: " + this.props.bind.getMensagemErro();
			return <span style={Tabela.STYLE_ERROR.get()}>{s}</span>;
		} else {
			setTimeout(() => this.props.bind.carregar(), 250);
			return <Loading/>;
		}
	}

	getBody() {

		const itensFiltrados = this.getItensFiltrados();

		if (itensFiltrados.isEmpty()) {
			return null;
		}

		return (
			<tbody>
				<Foreach array={itensFiltrados} func={o =>
					<tr style={this.getRowStyle(o)} onMouseOut={() => this.setSelected(null)} onMouseMove={event => this.setSelected(o)}>
						{this.tdEdit(o)}
						{this.tdDelete(o)}
						<Foreach array={this.props.colunas} getKey={col => col.id} func={col =>
							<td
								onClick={this.getClickTd(o, col)}
								style={this.getStyle(col).get()}
							>{this.getRender(col, o)}</td>
						}/>
					</tr>
				}/>
			</tbody>
		);
	}
	getClickTd(o, col) {
		if (col.noClick) {
			return null;
		}
		if (this.showEdit()) {
			return () => {
				this.props.onClick(o);
				col.onEdit(o);
			};
		} else {
			return null;
		}
	}

	getRowStyle(o) {
		if (o === Tabela.selected) {
			return Tabela.STYLE_ROW_SELECTED.get();
		} else {
			return Tabela.STYLE_ROW.get();
		}
	}
	getRender(col, o) {
		const obj = col.get(o);
		if (UCommons.isEmpty(col.renderItem)) {
			if (UCommons.isEmpty(obj)) {
				return null;
			} else {
				const value = GetText.get(obj);
				if (UString.isEmpty(value)) {
					return null;
				} else {
					return <span>{value}</span>;
				}
			}
		} else {
			return col.renderItem(obj);
		}
	}
	tdEdit(o) {
		if (this.showEdit()) {
			let icon;
			if (UCommons.isEmpty(this.props.funcGetEditIcon)) {
				icon = Tabela.ICONE_EDIT;
			} else {
				icon = this.props.funcGetEditIcon(o);
			}

			return (
				<td style={Tabela.STYLE_COL_FIXED.get()} onClick={() => this.props.onClick(o)}>{icon}</td>
			);
		} else {
			return null;
		}
	}
	tdDelete(o) {
		if (this.showDelete()) {
			return (
				<td style={Tabela.STYLE_COL_FIXED.get()}>
					<Popconfirm
						onConfirm={() => this.props.onDelete(o)}
						title={"Confirma exclusão?"}
						okText={"Sim"}
						cancelText={"Não"}
					>{Tabela.ICONE_DELETE}</Popconfirm>
				</td>
			);
		} else {
			return null;
		}
	}
	getItensFiltrados() {
		return this.props.bind.getItens().filter(o => {
			if (UBoolean.isTrue(UNative.getAtributo(o, "excluido"))) {
				return false;
			}
			const box = new Box(false);
			this.props.colunas.forEach(coluna => {
				if (box.get()) {
					return;
				}
				const value = coluna.get(o);
				if (coluna.itensFiltrados.contains(value)) {
					box.set(true);
				}
			});
			return !box.get();
		});
	}
	getHead() {
		return (
			<thead>
				<tr style={Tabela.STYLE_SUBTABLE_TITLE_ROW.get()}>
					{this.showEdit() &&
						<td rowSpan={2} style={Tabela.STYLE_COL_FIXED.get()}/>
					}
					{this.showDelete() &&
						<td rowSpan={2} style={Tabela.STYLE_COL_FIXED.get()}/>
					}
					<Foreach array={this.colunasSemGrupo()} getKey={o => o.id} func={o => this.getTitulo(o, 2)}/>
					<Foreach array={this.props.colunasGrupo} getKey={o => o.id} func={o => {
						return (
							<td colSpan={o.cols} style={this.getTitleStyle(o).textAlignCenter().get()}>
								<span>{o.title}</span>
							</td>
						);
					}}/>
				</tr>
				<tr style={Tabela.STYLE_SUBTABLE_TITLE_ROW.get()}>
					<Foreach array={this.colunasComGrupo()} getKey={o => o.id} func={o => this.getTitulo(o, 2)}/>
				</tr>
			</thead>
		);
	}
	getTitulo(o, rowSpan) {
		return (
			<Popover
			trigger={"click"}
			content={this.getIcones(o)}
			placement={"topLeft"}>
				<td rowSpan={rowSpan} style={this.getTitleStyle(o).get()}>
					{this.getTitle(o)}
				</td>
			</Popover>
		);
	}
	getIcones(o) {
		return (
			<Fragment>
				<Icon type={o.sorted ? "caret-up" : "caret-down"} onClick={() => this.sort(o)} style={Tabela.STYLE_COL_FIXED.get()}/>
				<Popover
				placement={"right"}
				content={this.getFilters(o)}
				trigger={"click"}
				><Icon type={"filter"} style={Tabela.STYLE_COL_FIXED.get()}/></Popover>
			</Fragment>
		);
	}

	getFilters(o) {
		const itens = this.props.bind.getItens().copy();
		itens.sort(o.sort);

		const list = itens.distinct(item => o.get(item));
		return (
			<Fragment>
				<List dataSource={list} renderItem={item => {
					const s = GetText.get(item);
					const filtrado = o.itensFiltrados.contains(item);
					return (
						<ListItem>
							<Checkbox
							checked={!filtrado}
							onChange={e => {
								if (filtrado) {
									o.itensFiltrados.removeObject(item);
								} else {
									o.itensFiltrados.add(item);
								}
								this.forceUpdate();
							}}
							><span>{s}</span></Checkbox>
						</ListItem>
					);
				}}/>
				<ListItem>
					<Checkbox checked={true} onChange={e => {
						o.itensFiltrados.clear();
						this.forceUpdate();
					}}><span>*</span></Checkbox>
					<Checkbox checked={false} onChange={e => {
						o.itensFiltrados.clear();
						list.forEach(item => o.itensFiltrados.add(item));
						this.forceUpdate();
					}}><span>*</span></Checkbox>
				</ListItem>
			</Fragment>
		);
	}

	getTitle(o) {
		return <span>{o.title}</span>;
	}

	sort(o) {
		const itens = this.props.bind.getItens();
		if (o.sorted) {
			itens.sort((a, b) => -o.sort(a, b));
		} else {
			itens.sort(o.sort);
		}
		o.sorted = !o.sorted;
		this.forceUpdate();
	}

	getTitleStyle(o) {

		const result = Tabela.STYLE_TITLE_COL.copy();

		if (this.colunasSemGrupo().isEmpty()) {
			if (UCommons.eqeqeq(this.props.colunasGrupo.get(0), o) || UCommons.eqeqeq(this.colunasComGrupo().get(0), o)) {
				result.borderLeftStyle(BorderStyle.none);
			}
		} else {
			if (UCommons.eqeqeq(this.colunasSemGrupo().get(0), o)) {
				result.borderLeftStyle(BorderStyle.none);
			}
		}

		if (UArray.isEmpty(this.props.colunasGrupo)) {
			if (UCommons.eqeqeq(this.colunasSemGrupo().getLast(), o)) {
				result.borderRightStyle(BorderStyle.none);
			}
		} else {
			if (UCommons.eqeqeq(this.props.colunasGrupo.getLast(), o) || UCommons.eqeqeq(this.colunasComGrupo().getLast(), o)) {
				result.borderRightStyle(BorderStyle.none);
			}
		}

		result.textAlign(o.textAlign);
		result.width(o.width);
		result.cursor("pointer");
		return result;
	}

	getStyle(o) {

		const result = Tabela.STYLE_SUBTABLE_COL.copy();
		result.textAlign(o.textAlign);

		if (this.showEdit()) {
			result.cursor("pointer");
		}

		return result;
	}

	onKeyDown(e) {

		if (!this.isTableSelected() || UCommons.isEmpty(Tabela.selected)) {
			return;
		}

		const itensFiltrados = this.getItensFiltrados();
		if (itensFiltrados.length < 2) {
			return;
		}
		if (e.arrowDown() || e.arrowUp()) {
			let index = itensFiltrados.indexOf(Tabela.selected);
			if (index < 0) {
				return;
			}
			if (e.arrowUp()) {
				if (index > 0) {
					index--;
					this.setSelected(itensFiltrados.get(index));
				} else {
					this.setSelected(itensFiltrados.get(itensFiltrados.length-1));
				}
			} else if (e.arrowDown()) {
				if (index < itensFiltrados.length-1) {
					index++;
					this.setSelected(itensFiltrados.get(index));
				} else {
					this.setSelected(itensFiltrados.get(0));
				}
			}

		}
	}

	isTableSelected() {
		return Tabela.selectedTable === this;
	}

	setSelected(o) {
		Tabela.selected = o;
		if (UCommons.notEmpty(Tabela.selectedTable) && !this.isTableSelected()) {
			Tabela.selectedTable.forceUpdate();
		}
		if (UCommons.notEmpty(o)) {
			Tabela.selectedTable = this;
		} else {
			Tabela.selectedTable = null;
		}
		this.forceUpdate();
	}

	componentDidMount() {
		this.observar(this.props.bind);
		if (UCommons.notEmpty(this.props.formKeyDown)) {
			this.props.formKeyDown.subKeyObservers.add(this);
		}
	}
	colunasSemGrupo() {
		return this.props.colunas.filter(o => !o.grupo);
	}
	colunasComGrupo() {
		return this.props.colunas.filter(o => o.grupo);
	}

}
Tabela.border = new Border().withColor(Color.cinzaClaro2);
Tabela.ICON_WIDTH = 25;
Tabela.STYLE_ERROR = LayoutApp.createStyle().margin(10).bold(true).color(Color.red);
Tabela.STYLE_COL_FIXED = LayoutApp.createStyle().borderBottom(Tabela.border).paddingTop(10).paddingBottom(10).width(Tabela.ICON_WIDTH).maxWidth(Tabela.ICON_WIDTH).minWidth(Tabela.ICON_WIDTH);
Tabela.STYLE_ICON_EDIT = CommonStyles.POINTER.copy().margin(5).color(Color.blue);
Tabela.STYLE_ICON_DELETE = CommonStyles.POINTER.copy().margin(5).color(Color.red);
Tabela.STYLE_ROW = LayoutApp.createStyle().border(Tabela.border).borderLeftStyle(BorderStyle.none).borderRightStyle(BorderStyle.none);
Tabela.STYLE_ROW_SELECTED = Tabela.STYLE_ROW.copy().backgroundColor(Color.cinzaClaro4);
Tabela.STYLE_SUBTABLE_TITLE_ROW = Tabela.STYLE_ROW.copy().bold(true);
Tabela.STYLE_SUBTABLE_COL = LayoutApp.createStyle().padding(5).paddingLeft(10).paddingRight(10);
Tabela.STYLE_TITLE_COL = LayoutApp.createStyle().padding(5).paddingLeft(10).paddingRight(10).border(Tabela.border);
Tabela.STYLE_SUBTABLE = LayoutApp.createStyle().widthPercent(100).textAlignCenter().get();
Tabela.ICONE_DELETE = <Icon type={"delete"} theme={"twoTone"} twoToneColor={Color.red} style={Tabela.STYLE_ICON_DELETE.get()}/>;
Tabela.ICONE_EDIT = <Icon type={"edit"} theme={"twoTone"} style={Tabela.STYLE_ICON_EDIT.get()}/>;
Tabela.ICONE_SEARCH = <Icon type={"search"} style={Tabela.STYLE_ICON_EDIT.get()}/>;
Tabela.ICONE_VAZIO = <Icon style={Tabela.STYLE_ICON_EDIT.get()}/>;

Tabela.defaultProps = SuperComponent.defaultProps;
