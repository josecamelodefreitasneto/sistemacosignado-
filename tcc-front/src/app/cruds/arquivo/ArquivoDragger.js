/* front-constructor */
import React from 'react';
import AntdUploadDragger from '../../../antd/AntdUploadDragger';
import ArquivoCampos from './ArquivoCampos';
import ArquivoUtils from './ArquivoUtils';
import LayoutApp from '../../../fc/components/LayoutApp';
import ResourcesFc from '../../../resources/ResourcesFc';
import SuperComponent from '../../misc/components/SuperComponent';
import TextAlign from '../../misc/consts/enums/TextAlign';
import UCommons from '../../misc/utils/UCommons';
import UString from '../../misc/utils/UString';
import {message} from 'antd';
import {Row} from 'antd';

export default class ArquivoDragger extends SuperComponent {
	constructor(props){
		super(props);
		this.state.uploading = false;
	}
	mensagemErro;

	render() {
		return (
			<Row gutter={24}>
				{this.dragger()}
				{this.preview()}
			</Row>
		);
	}

	dragger() {
		const cps = this.getCampos();

		if (cps.isReadOnly()) {
			return null;
		}

		return (
			<AntdUploadDragger
			beforeUpload={file => this.validarExtensao(file) && this.validarTamanho(file)}
			mensagemExtensoes={this.props.mensagemExtensoes}
			mensagemTamanho={this.props.mensagemTamanho}
			onChange={info => {
				if (UString.equals(info.file.status, "uploading")) {
					this.setUploading(true);
				} else if (UString.equals(info.file.status, "done")) {
					const reader = new FileReader();
					reader.addEventListener("load", () => {
						if (!cps.id.isEmpty() && cps.id.get() > 0) {
							cps.setCampos(ArquivoUtils.getInstance().novo());
						}
						cps.nome.set(info.file.name);
						cps.uri.set(reader.result);
						cps.type.set(info.file.type);
						cps.tamanho.set(info.file.size);
						this.setUploading(false);
					});
					reader.readAsDataURL(info.file.originFileObj);
				} else if (UString.equals(info.file.status, "error")) {
					this.setUploading(false);
					message.error("Erro ao tentar carregar o arquivo: " + this.mensagemErro);
				}
			}}/>
		);
	}

	getCampos() {
		return ArquivoCampos.getInstance();
	}

	preview() {

		if (this.state.uploading) {
			return (
				<div style={ArquivoDragger.STYLE_DIV}>
					<span>Carregando...</span>
				</div>
			);
		}

		const cps = this.getCampos();

		if (cps.nome.isEmpty()) {
			return null;
		}

		return (
			<div style={ArquivoDragger.STYLE_DIV}>
				<Row gutter={24}>
					{this.getImg(cps)}
				</Row>
				<Row gutter={24}>
					<span>{cps.nome.asString() + " - " + cps.tamanho.asString() + " bytes"}</span>
				</Row>
			</div>
		);

	}

	getImg(cps) {

		if (cps.uri.isEmpty()) {
			return <span>uri is empty</span>;
		}

		if ((cps.houveMudancas() || cps.id.get() < 1) && cps.type.isEmpty()) {
			return <span>Tipo inválido!</span>;
		}

		const uri = cps.uri.asString();

		if (cps.isImage()) {
			return <img src={uri} alt={"avatar"} style={ArquivoDragger.IMAGE_STYLE}/>;
		}

		if (cps.houveMudancas() || cps.id.get() < 1) {
			return <img src={cps.getIcone()} alt={"avatar"} style={ArquivoDragger.ICON_STYLE}/>;
		}

		if (cps.isPdf()) {
			return (
				<a href={uri} target={"_blank"}>
					<img src={ResourcesFc.pdf} alt={"avatar"} style={ArquivoDragger.ICON_STYLE}/>
				</a>
			);
		} else {
			return (
				<a href={uri.replace("/download/", "/download/stream/")} download={cps.nome.get()}>
					<img src={cps.getIcone()} alt={"avatar"} style={ArquivoDragger.ICON_STYLE}/>
				</a>
			);
		}

	}

	componentDidMount() {
		this.observar(this.getCampos());
	}

	validarTamanho(file) {
		if (file.size > this.props.tamanhoMaximoEmBytes) {
			message.error(this.props.mensagemTamanho);
			return false;
		}
		return true;
	}

	validarExtensao(file) {
		if (UString.isEmpty(file.type)) {
			file.status = "error";
			this.mensagemErro = "Aquivos sem extensão não são permitidos!";
			return false;
		}
		if (UCommons.isEmpty(this.props.extensoes)) {
			return true;
		}
		const extensao = UString.afterLast(file.name, ".");
		if (this.props.extensoes.contains(extensao) === this.props.invalidas) {
			file.status = "error";
			this.mensagemErro = this.props.mensagemExtensoes;
			return false;
		} else {
			return true;
		}
	}
	setUploading = o => this.setState({uploading:o});

}
ArquivoDragger.STYLE_DIV = LayoutApp.createStyle().widthPercent(100).textAlign(TextAlign.center).marginTop(10).get();
ArquivoDragger.IMAGE_STYLE = LayoutApp.createStyle().maxHeight(600).get();
ArquivoDragger.ICON_STYLE = LayoutApp.createStyle().height(50).margin(20).get();

ArquivoDragger.defaultProps = {
	...SuperComponent.defaultProps,
	tamanhoMaximoEmBytes: 0,
	invalidas: false
}
