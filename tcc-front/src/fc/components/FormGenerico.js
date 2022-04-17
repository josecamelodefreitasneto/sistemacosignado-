/* front-constructor */
import React from 'react';
import Console from '../../app/misc/utils/Console';
import FooterToolbar from './FooterToolbar';
import FormKeyDownObserver from './FormKeyDownObserver';
import LayoutApp from './LayoutApp';
import UCommons from '../../app/misc/utils/UCommons';
import UDocument from '../../web/misc/UDocument';
import UNative from '../../app/misc/utils/UNative';
import {Card} from 'antd';
import {Form} from 'antd';
import {Modal} from 'antd';

export default class FormGenerico extends FormKeyDownObserver {
	constructor(props){
		super(props);
		this.state.widthForm = "100";
	}

	montado = false;

	render() {

		if (!this.montado) {
			return null;
		}

		const outroRender = this.getRender();
		if (UCommons.notEmpty(outroRender)) {
			return outroRender;
		}

		if (this.ehModal()) {
			return (
				<Modal
					title={this.getTop()}
					width={this.getWidthModal()+"%"}
					visible={true}
					style={FormGenerico.STYLE_MODAL.get()}
					bodyStyle={FormGenerico.STYLE_MODAL_BODY.get()}
					footer={this.getFooter()}
					closable={false}
					keyboard={false}
					maskClosable={false}
					destroyOnClose={true}
					onCancel={() => this.onExit()}
				>{this.getBody()}</Modal>
			);
		} else {
			return (
				<div>
					{this.miolo()}
					<FooterToolbar>
						{this.getFooter()}
					</FooterToolbar>
				</div>
			);
		}
	}

	getTop() {
		return <span>{this.getTitle()}</span>;
	}

	ehModal() {
		return this.props.isModal;
	}

	getWidthModal() {
		return this.props.widthModal;
	}

	static removeFooter() {
		if (UNative.inJava) {
			return;
		}
		const list = document.getElementsByTagName("footer");
		if (UCommons.eqeqeq(list.length, 0)) {
			setTimeout(() => FormGenerico.removeFooter());
		} else {
			list[0].remove();
		}
	}

	miolo() {
		return (
			<Card title={this.getTitle()} bordered={false} style={FormGenerico.STYLE_CARD.get()}>
				<Form hideRequiredMark={true}>
					{this.getBody()}
				</Form>
			</Card>
		);
	}
	getRender() {return null;}

	componentDidMount1() {
		window.addEventListener("resize", () => this.resizeFooterToolbar(), {passive: true});
		this.resizeFooterToolbar();
		this.componentDidMount2();
		FormGenerico.removeFooter();
		this.montado = true;
		setTimeout(() => this.forceUpdate());
	}

	componentDidMount2() {}

	componentWillUnmount2a() {
		window.removeEventListener("resize", () => this.resizeFooterToolbar());
		this.componentWillUnmount3();
	}

	componentWillUnmount3() {}

	resizeFooterToolbar() {
		const sider = UDocument.querySelectorAll(".ant-layout-sider").get(0);
		if (UCommons.notEmpty(sider)) {
			const widthCalc = "calc(100% - " + sider.style.width + ")";
			this.setWidthForm(widthCalc);
		}
	}

	close() {
		Console.log("FormGenerico.close chamado", UCommons.getClassName(this));
	}

	confirmar() {
		this.close();
	}

	cancelar() {
		this.close();
	}

	onExit() {
		this.esc();
	}

	esc() {
		this.cancelar();
	}

	ctrlEnter() {
		this.confirmar();
	}

	enter() {}

	ctrlDel() {}

	onKeyDown0(e) {
		if (e.ctrl()) {
			if (e.shift()) {} else if (e.enter()) {
				this.ctrlEnter();
			} else if (e.del()) {
				this.ctrlDel();
			}
		} else if (e.shift()) {} else if (e.esc()) {
			this.esc();
		} else if (e.enter()) {
			this.enter();
		}
	}
	setWidthForm = o => this.setState({widthForm:o});
}
FormGenerico.STYLE_MODAL = LayoutApp.createStyle().top(20).backgroundColorClear();
FormGenerico.STYLE_MODAL_BODY = LayoutApp.createStyle();
FormGenerico.STYLE_CARD = LayoutApp.createStyle().marginBottom(24);

FormGenerico.defaultProps = {
	...FormKeyDownObserver.defaultProps,
	isModal: false,
	widthModal: 96
}
