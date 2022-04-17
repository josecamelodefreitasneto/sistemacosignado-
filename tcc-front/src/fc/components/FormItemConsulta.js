/* front-constructor */
import React from 'react';
import BCheckBox from '../../antd/BCheckBox';
import BindingBooleanFiltro from '../../app/campos/support/BindingBooleanFiltro';
import InputBind from '../../antd/InputBind';
import LayoutApp from './LayoutApp';
import SuperComponent from '../../app/misc/components/SuperComponent';
import TextAlign from '../../app/misc/consts/enums/TextAlign';
import UCommons from '../../app/misc/utils/UCommons';
import {Col} from 'antd';
import {Fragment} from 'react';
import {Row} from 'antd';

export default class FormItemConsulta extends SuperComponent {

	render() {
		if (UCommons.isEmpty(this.props.bind2)) {
			return (
				<Row>
					<Col lg={6} style={FormItemConsulta.STYLE_COL_TEXT}>
						{this.getLabel(this.props.bind1)}
					</Col>
					<Col lg={5}>
						{this.getInput(this.props.bind1)}
					</Col>
				</Row>
			);
		} else {
			return (
				<Row>
					<Col lg={6} style={FormItemConsulta.STYLE_COL_TEXT}>
						{this.getLabel(this.props.bind1)}
					</Col>
					<Col lg={5} style={FormItemConsulta.STYLE_ALIGN_LEFT}>
						{this.getInput(this.props.bind1)}
					</Col>
					<Col lg={4} style={FormItemConsulta.STYLE_COL_TEXT}>
						{this.getLabel(this.props.bind2)}
					</Col>
					<Col lg={5} style={FormItemConsulta.STYLE_ALIGN_LEFT}>
						{this.getInput(this.props.bind2)}
					</Col>
				</Row>
			);
		}
	}

	getLabel(bind) {
		return <span>{bind.getLabel() + ": "}</span>;
	}

	getInput(bind) {
		if (UCommons.instanceOf(bind, BindingBooleanFiltro)) {
			let b = bind;
			return (
				<Fragment>
					<BCheckBox bind={b.sim}/>
					<BCheckBox bind={b.nao}/>
				</Fragment>
			);
		} else {
			return <InputBind small={true} bind={bind}/>;
		}

	}

	componentDidMount() {
		this.observar(this.props.bind1);
		if (UCommons.notEmpty(this.props.bind2)) {
			this.observar(this.props.bind2);
		}
	}
}
FormItemConsulta.STYLE_ALIGN_LEFT = LayoutApp.createStyle().textAlign(TextAlign.left).get();
FormItemConsulta.STYLE_COL_TEXT = LayoutApp.createStyle().textAlign(TextAlign.right).paddingRight(5).get();

FormItemConsulta.defaultProps = SuperComponent.defaultProps;
