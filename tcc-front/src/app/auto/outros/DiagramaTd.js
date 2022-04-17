/* tcc-java */
import React from 'react';
import DiagramaCols from './DiagramaCols';
import SuperComponent from '../../misc/components/SuperComponent';

export default class DiagramaTd extends SuperComponent {

	render() {
		return (
			<tr>
				<td>
					<span>{this.props.nome}</span>
				</td>
				<DiagramaCols itens={this.props.itens}/>
			</tr>
		);
	}
}

DiagramaTd.defaultProps = SuperComponent.defaultProps;
