/* react-web-antd */
import React from 'react';
import SuperComponent from '../app/misc/components/SuperComponent';
import {Alert} from 'antd';
import {Spin} from 'antd';

export default class Loading extends SuperComponent {
	render() {
		return (
			<Spin tip={"Carregando..."}>
				<Alert
					message={"Por favor aguarde"}
					description={"Estamos carregando seu conteúdo"}
					type={"info"}
				/>
			</Spin>
		);
	}
}

Loading.defaultProps = SuperComponent.defaultProps;
