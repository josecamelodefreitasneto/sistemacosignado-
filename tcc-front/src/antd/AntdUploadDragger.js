/* react-web-antd */
import React from 'react';
import SuperComponent from '../app/misc/components/SuperComponent';
import {Upload} from 'antd';
const UploadDragger = Upload.Dragger;

export default class AntdUploadDragger extends SuperComponent {

	render() {
		return (
			<UploadDragger
			name={"file"}
			showUploadList={false}
			multiple={false}
			beforeUpload={this.props.beforeUpload}
			onChange={this.props.onChange}
			customRequest={o => setTimeout(() => o.onSuccess("ok"))}>
				<p className={"ant-upload-text"}>Clique ou solte um arquivo nesta área</p>
				<p className={"ant-upload-hint"}>{this.props.mensagemExtensoes}</p>
				<p className={"ant-upload-hint"}>{this.props.mensagemTamanho}</p>
			</UploadDragger>
		);
	}
}

AntdUploadDragger.defaultProps = SuperComponent.defaultProps;
