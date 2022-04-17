/* react-web */
import React from 'react';
import SuperComponent from '../components/SuperComponent';

export default class IconBolinha extends SuperComponent {

	render() {
		return <svg
			width={this.props.size}
			height={this.props.size}
			role={"img"}
			version={"1.1"}
			viewBox={"0 0 "+this.props.size+" "+this.props.size}>
				<path fill={"none"} d={"M"+this.props.size+" "+this.props.size+"H0V0h"+this.props.size+"v"+this.props.size+"z"}/>
				<circle fill={"#010101"} cx={this.props.size/2} cy={this.props.size/2} r={this.props.size/3}/>
			</svg>;
	}
}

IconBolinha.defaultProps = {
	...SuperComponent.defaultProps,
	size: 0
}
