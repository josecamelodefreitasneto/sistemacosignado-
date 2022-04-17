/* front-constructor */
import React from 'react';
import BorderStyle from '../../app/misc/consts/enums/BorderStyle';
import Color from '../../app/misc/consts/fixeds/Color';
import Floats from '../../app/misc/consts/enums/Floats';
import LayoutApp from './LayoutApp';
import Position from '../../app/misc/consts/enums/Position';
import SuperComponent from '../../app/misc/components/SuperComponent';
import UCommons from '../../app/misc/utils/UCommons';
import UDocument from '../../web/misc/UDocument';

export default class FooterToolbar extends SuperComponent {
	constructor(props){
		super(props);
		this.state.width = null;
	}

	componentDidMount() {
		window.addEventListener("resize", () => this.resizeFooterToolbar());
		this.resizeFooterToolbar();
	}

	componentWillUnmount2() {
		window.removeEventListener("resize", () => this.resizeFooterToolbar());
	}

	resizeFooterToolbar() {
		const sider = UDocument.querySelectorAll(".ant-layout-sider").get(0);
		if (UCommons.notEmpty(sider)) {
			const widthCalc = "calc(100% - " + sider.style.width + ")";
			this.setWidth(widthCalc);
		}
	}

	render() {
		return (
			<div style={FooterToolbar.STYLE_TOOLBAR.copy().width(this.state.width).get()}>
				<div style={FooterToolbar.FLOAT_LEFT}>{this.props.extra}</div>
				<div style={FooterToolbar.FLOAT_RIGHT}>{this.props.children}</div>
			</div>
		);
	}
	setWidth = o => this.setState({width:o});
}
FooterToolbar.STYLE_TOOLBAR
= LayoutApp.createStyle()
.position(Position.fixed)
.right(0).bottom(0)
.zIndex(99).widthPercent(100)
.height(56)
.paddingTop(0)
.paddingRight(24)
.lineHeight(56)
.borderTopWidth(1)
.borderTopStyle(BorderStyle.solid)
.borderTopColor(Color.cinzaClaro)
	;
FooterToolbar.FLOAT_LEFT = LayoutApp.createStyle().float(Floats.left).get();
FooterToolbar.FLOAT_RIGHT = LayoutApp.createStyle().float(Floats.right).get();

FooterToolbar.defaultProps = SuperComponent.defaultProps;
