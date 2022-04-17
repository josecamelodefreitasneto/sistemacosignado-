/* react-web */
import React from 'react';
import SuperComponent from '../components/SuperComponent';

export default class Icone extends SuperComponent {

	render() {
		return (
			<svg
				/*.ariaHidden()*/
				width={120}
				height={160}
				role={"img"}
				version={"1.1"}
				viewBox={"0 0 120 160"}>
				<path d={this.props.pathCode}/>
			</svg>
		);
	}
}

Icone.defaultProps = SuperComponent.defaultProps;
