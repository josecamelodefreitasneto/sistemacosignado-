/* front-constructor */
import React from 'react';
import LayoutApp from './LayoutApp';
import SuperComponent from '../../app/misc/components/SuperComponent';
import {Card} from 'antd';
import {Fragment} from 'react';
import {Icon} from 'antd';

export default class GroupCard extends SuperComponent {
	constructor(props){
		super(props);
		this.state.expandido = true;
	}

	render() {
		return (
			<Card
			title={this.props.title}
			size={"small"}
			style={GroupCard.TITULO_GROUP}
			className={"card-table"}
			extra={this.getExtra()}
			>{this.getChildren()}</Card>
		);
	}

	getExtra() {
		return (
			<Fragment>
				{this.props.extra}
				<Icon type={this.state.expandido ? "caret-up" : "caret-down"}
				onClick={() => this.setExpandido(!this.state.expandido)}
				/>
			</Fragment>
		);
	}

	getChildren() {
		if (this.state.expandido) {
			return this.props.children;
		} else {
			return null;
		}
	}

	componentDidMount() {
		if (this.props.reprimido) {
			this.setExpandido(false);
		}
	}
	setExpandido = o => this.setState({expandido:o});
}
GroupCard.TITULO_GROUP = LayoutApp.createStyle().marginTop(15).get();

GroupCard.defaultProps = {
	...SuperComponent.defaultProps,
	reprimido: false
}
