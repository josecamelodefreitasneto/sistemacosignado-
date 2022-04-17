/* tcc-java */
import React from 'react';
import Color from '../../misc/consts/fixeds/Color';
import DiagramaCols from './DiagramaCols';
import Style from '../../misc/utils/Style';
import SuperComponent from '../../misc/components/SuperComponent';

export default class DiagramaTitulo extends SuperComponent {

	render() {
		return (
			<tr>
				<td style={DiagramaTitulo.STYLE}>
					<span>{this.props.nome}</span>
				</td>
				<DiagramaCols itens={this.props.itens}/>
			</tr>
		);
	}
}
DiagramaTitulo.STYLE = Style.create().backgroundColor(Color.blue).get();

DiagramaTitulo.defaultProps = SuperComponent.defaultProps;
