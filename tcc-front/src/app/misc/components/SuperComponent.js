/* react */
import React from 'react';
import BoxComponent from './BoxComponent';
import StartPrototypes from '../utils/StartPrototypes';
import UCommons from '../utils/UCommons';
import UNative from '../utils/UNative';

export default class SuperComponent extends React.PureComponent {
	static countComponents = 0;
	idCount = 0;
	stringId;
	itensQueObservo;

	constructor(props) {
		super(props);
		this.state = {};
		this.idCount = SuperComponent.countComponents++;
		this.stringId = UCommons.getClassName(this) + this.idCount;
	}

	componentWillUnmount() {
		if (UCommons.notEmpty(this.itensQueObservo)) {
			this.itensQueObservo.forEach(o => o.removeRenderObserver(this));
		}
		this.componentWillUnmount2();
	}

	newBox(startValue) {
		return new BoxComponent(this, startValue);
	}

	componentWillUnmount2() {}

	observar(o) {

		if (UNative.inJava) {
			/*verificar se esta vindo de um didMount*/
		}

		if (UCommons.isEmpty(this.itensQueObservo)) {
			this.itensQueObservo = [];
		}
		this.itensQueObservo.add(o);
		o.addRenderObserver(this);
	}

}
SuperComponent.start = StartPrototypes.exec();

SuperComponent.defaultProps = React.PureComponent.defaultProps;
