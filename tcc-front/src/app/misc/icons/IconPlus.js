/* react-web */
import React from 'react';
import Icone from './Icone';
import SuperComponent from '../components/SuperComponent';

export default class IconPlus extends SuperComponent {

	render() {
		return <Icone pathCode={IconPlus.pathCode}/>;
	}

}
IconPlus.pathCode = "M12 9H7v5H5V9H0V7h5V2h2v5h5v2z";

IconPlus.defaultProps = SuperComponent.defaultProps;
