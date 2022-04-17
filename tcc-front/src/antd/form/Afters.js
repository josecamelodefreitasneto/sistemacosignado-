/* react-web-antd */
import React from 'react';
import CommonStyles from '../../app/misc/styles/CommonStyles';
import UCommons from '../../app/misc/utils/UCommons';
import {message} from 'antd';

export default class Afters {

	static email;
	static alfa;
	static numeric;

	static getAlfa() {
		if (UCommons.isEmpty(Afters.alfa)) {
			Afters.alfa = Afters.get("Significa que o campo aceita letras e numeros", "A");
		}
		return Afters.alfa;
	}

	static getNumeric() {
		if (UCommons.isEmpty(Afters.numeric)) {
			Afters.numeric = Afters.get("Significa que o campo aceita somente numeros", "#");
		}
		return Afters.numeric;
	}

	static getEmail() {
		if (UCommons.isEmpty(Afters.email)) {
			Afters.email = Afters.get("Campo do tipo e-mail", "@");
		}
		return Afters.email;
	}

	static get(a, b) {
		return <span onClick={e => message.info(a)} style={Afters.STYLE_ICON}>{b}</span>;
	}

}
Afters.STYLE_ICON = CommonStyles.POINTER.get();
