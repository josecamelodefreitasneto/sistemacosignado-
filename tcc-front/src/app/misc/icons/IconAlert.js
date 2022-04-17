/* react-web */
import React from 'react';
import Icone from './Icone';
import SuperComponent from '../components/SuperComponent';

export default class IconAlert extends SuperComponent {

	render() {
		return <Icone pathCode={IconAlert.pathCode}/>;
	}

}
IconAlert.pathCode = "M12 9H7v5H5V9H0V7h5V2h2v5h5v2z";

IconAlert.defaultProps = SuperComponent.defaultProps;
