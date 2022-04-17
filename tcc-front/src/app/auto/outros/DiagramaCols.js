/* tcc-java */
import React from 'react';
import Border from '../../misc/utils/Border';
import Color from '../../misc/consts/fixeds/Color';
import Diagrama from './Diagrama';
import Foreach from '../../misc/components/Foreach';
import Style from '../../misc/utils/Style';
import SuperComponent from '../../misc/components/SuperComponent';

export default class DiagramaCols extends SuperComponent {

	static getTable(l1, l2, l3, l4, l5) {
		return (
			<table style={Style.create().margin(0).padding(0).widthPercent(100).get()}>
				<tbody style={Style.create().margin(0).padding(0).get()}>
					{l1}
					{l2}
					{l3}
					{l4}
					{l5}
				</tbody>
			</table>
		);
	}

	static HTD = 5;
	static WTD = 2;

	static linha(o1, o2, o3, o4, o5) {
		return (
			<tr style={Style.create().margin(0).padding(0).minHeight(DiagramaCols.HTD).height(DiagramaCols.HTD).get()}>
				<td style={DiagramaCols.st(o1)}/>
				<td style={DiagramaCols.st(o2)}/>
				<td style={DiagramaCols.st(o3)}/>
				<td style={DiagramaCols.st(o4)}/>
				<td style={DiagramaCols.st(o5)}/>
			</tr>
		);
	}

	static st(o) {
		return Style.create().margin(0).padding(0).minHeight(DiagramaCols.HTD).height(DiagramaCols.HTD).minWidth(DiagramaCols.WTD).width(DiagramaCols.WTD).join(o).get();
	}

	render() {

		if (this.props.itens.size() !== Diagrama.COLS) {
			let s = this.props.itens.size() + "  " + Diagrama.COLS;
			return <td><span>{s}</span></td>;
		}

		return (
			<Foreach array={this.props.itens} func={o => {
				return <td style={DiagramaCols.STYLED.get()}>{DiagramaCols.FORMA.get(o)}</td>;
			}}/>
		);
	}
}
DiagramaCols.BORDA_RED = new Border().withColor(Color.red).withWidth(1.0);
DiagramaCols.BORDA_BLUE = new Border().withColor(Color.blue).withWidth(1.0);
DiagramaCols._VAI = Style.create().borderBottom(DiagramaCols.BORDA_RED);
DiagramaCols._VEM = Style.create().borderTop(DiagramaCols.BORDA_BLUE);
DiagramaCols._VCR = Style.create().borderTop(DiagramaCols.BORDA_BLUE).borderRight(DiagramaCols.BORDA_RED);
DiagramaCols._SOB = Style.create().borderRight(DiagramaCols.BORDA_RED);
DiagramaCols._QSO = Style.create().borderRight(DiagramaCols.BORDA_RED).borderBottom(DiagramaCols.BORDA_RED);
DiagramaCols._QVI = Style.create().borderRight(DiagramaCols.BORDA_RED).borderTop(DiagramaCols.BORDA_BLUE);
DiagramaCols.VAI = DiagramaCols.getTable(
	DiagramaCols.linha(null, null, null, null, null),
	DiagramaCols.linha(null, null, null, null, null),
	DiagramaCols.linha(DiagramaCols._VAI, DiagramaCols._VAI, DiagramaCols._VAI, DiagramaCols._VAI, DiagramaCols._VAI),
	DiagramaCols.linha(null, null, null, null, null),
	DiagramaCols.linha(null, null, null, null, null)
);
DiagramaCols.VEM = DiagramaCols.getTable(
	DiagramaCols.linha(null, null, null, null, null),
	DiagramaCols.linha(null, null, null, null, null),
	DiagramaCols.linha(DiagramaCols._VEM, DiagramaCols._VEM, DiagramaCols._VEM, DiagramaCols._VEM, DiagramaCols._VEM),
	DiagramaCols.linha(null, null, null, null, null),
	DiagramaCols.linha(null, null, null, null, null)
);
DiagramaCols.VEM_CRUZADO = DiagramaCols.getTable(
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(DiagramaCols._VEM, DiagramaCols._VEM, DiagramaCols._VCR, DiagramaCols._VEM, DiagramaCols._VEM),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null)
);
DiagramaCols.SOBE = DiagramaCols.getTable(
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null)
);
DiagramaCols.QUINA_SOBE = DiagramaCols.getTable(
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(DiagramaCols._VAI, DiagramaCols._VAI, DiagramaCols._QSO, null, null),
	DiagramaCols.linha(null, null, null, null, null),
	DiagramaCols.linha(null, null, null, null, null)
);
DiagramaCols.QUINA_VIRA = DiagramaCols.getTable(
	DiagramaCols.linha(null, null, null, null, null),
	DiagramaCols.linha(null, null, null, null, null),
	DiagramaCols.linha(DiagramaCols._VEM, DiagramaCols._VEM, DiagramaCols._QVI, null, null),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null),
	DiagramaCols.linha(null, null, DiagramaCols._SOB, null, null)
);
DiagramaCols.FORMA = [
	null, DiagramaCols.VAI, DiagramaCols.QUINA_SOBE, DiagramaCols.SOBE, DiagramaCols.QUINA_VIRA, DiagramaCols.VEM, null, null, DiagramaCols.VEM_CRUZADO, DiagramaCols.QUINA_VIRA
];
DiagramaCols.STYLED = Style.create().minWidth(DiagramaCols.WTD*5).minHeight(DiagramaCols.HTD*5).margin(0).padding(0);

DiagramaCols.defaultProps = SuperComponent.defaultProps;
