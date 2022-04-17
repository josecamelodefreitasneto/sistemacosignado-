/* react-web-antd */
import React from 'react';
import AFormComponent from './AFormComponent';
import SuperComponent from '../../app/misc/components/SuperComponent';

export default class AFormItem extends SuperComponent {

	render() {

		if (!this.props.bind.isVisible()) return null;
		const idComponent = this.props.bind.getIdComponent();
		const error = !this.props.bind.isValid() && !this.props.bind.isVirgin();
		const errorMessage = error ? this.props.bind.getInvalidMessage() : null;

		return (
			<AFormComponent
				idComponent={idComponent}
				lg={this.props.lg}
				label={this.getLabel()}
				asterisco={this.props.bind.notNull() && !this.props.bind.isDisabled()}
				error={errorMessage}>
					{this.theBody(idComponent, error)}
			</AFormComponent>
		);

	}

	theBody(idComponent, error) {
		if (this.props.bind.getReplaceRenderBody()) {
			return this.props.bind.getRenderBody();
		}
		return this.getBody(this.props.bind, idComponent, error);
	}

	getBind() {
		return this.props.bind;
	}

	getLabel() {
		return this.props.bind.getLabel();
	}

	componentDidMount() {
		this.observar(this.props.bind);
	}
}

AFormItem.defaultProps = {
	...SuperComponent.defaultProps,
	lg: 0
}
