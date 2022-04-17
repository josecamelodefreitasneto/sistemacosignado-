/* react */
import React from 'react';
export default class SuperInpureComponent extends React.PureComponent {

	constructor(props) {
		super(props);
		this.state = {};
	}
}

SuperInpureComponent.defaultProps = React.PureComponent.defaultProps;
