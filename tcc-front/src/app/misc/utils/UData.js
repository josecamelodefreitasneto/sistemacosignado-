/* react */
import IdText from './IdText';
import ObjetoData from './ObjetoData';
import UCommons from './UCommons';
import UInteger from './UInteger';
import UString from './UString';

export default class UData {

	static constroi(primeiro, ultimo) {
		const list = [];
		while (primeiro <= ultimo) {
			list.push(new IdText(primeiro, ""+primeiro));
			primeiro++;
		}
		return list;
	}

	static MESES = [
		new IdText(1, "Janeiro"), new IdText(2, "Fevereiro"), new IdText(3, "Março"),
		new IdText(4, "Abril"), new IdText(5, "Maio"), new IdText(6, "Junho"),
		new IdText(7, "Julho"), new IdText(8, "Agosto"), new IdText(9, "Setembro"),
		new IdText(10, "Outubro"), new IdText(11, "Novembro"), new IdText(12, "Dezembro")
	];

	static getIdMes(nome) {
		return UData.MESES.uniqueObrig(o => UString.equivalente(nome, o.text)).getId();
	}
	static compareNomeMes(a, b) {
		if (UString.isEmpty(a)) {
			if (UString.isEmpty(b)) {
				return 0;
			} else {
				return -1;
			}
		} else if (UString.isEmpty(b)) {
			return 1;
		} else {
			const va = UData.getIdMes(a);
			const vb = UData.getIdMes(b);
			return UInteger.compare(va, vb);
		}
	}

	static hoje() {
		return UData.toData(new Date());
	}

	static toData(date) {
		const data = new ObjetoData();
		data.ano = date.getYear() + 1900;
		data.mes = date.getMonth() + 1;
		data.dia = date.getDate();
		return data;
	}

	static menorQueHoje(o) {
		const hoje = UData.hoje();
		if (o.ano > hoje.ano) {
			return false;
		}
		if (o.ano < hoje.ano) {
			return true;
		}
		if (o.mes > hoje.mes) {
			return false;
		}
		if (o.mes < hoje.mes) {
			return true;
		}
		if (o.dia > hoje.dia) {
			return false;
		}
		if (o.dia < hoje.dia) {
			return true;
		}
		return false;
	}

	static isAnoMesValido(s) {
		s = UData.formatParcialMesAno(s);
		if (!UString.lengthIs(s, 7)) {
			return false;
		} else {
			return UData.isValida("01/" + s);
		}
	}
	static isValida(s) {
		const o = UData.strToDate(s);
		if (UCommons.isEmpty(o)) {
			return false;
		} else {
			return o.isValida();
		}
	}

	static strToDate(s) {

		if (!UString.lengthIs(s, 10)) {
			return null;
		}

		s = UString.mantemSomenteNumeros(s);

		if (!UString.lengthIs(s, 8)) {
			return null;
		}

		const o = new ObjetoData();
		o.dia = parseInt(s.substring(0, 2));
		s = s.substring(2);
		o.mes = parseInt(s.substring(0, 2));
		s = s.substring(2);
		o.ano = parseInt(s);

		return o;

	}

	static parcialValida(s) {

		s = UString.mantemSomenteNumeros(s);

		if (UString.isEmpty(s)) {
			return true;
		}

		if (parseInt(s.substring(0, 1)) > 3) {
			return false;
		}

		if (UString.lengthIs(s, 1)) {
			return true;
		}

		const dia = parseInt(s.substring(0, 2));

		if (dia > 31 || UInteger.equals(dia, 0)) {
			return false;
		}

		if (UString.lengthIs(s, 2)) {
			return true;
		}

		s = s.substring(2);

		if (parseInt(s.substring(0, 1)) > 1) {
			return false;
		}

		if (UString.lengthIs(s, 1)) {
			return true;
		}

		const mes = parseInt(s.substring(0, 2));

		if (mes > 12 || UInteger.equals(mes, 0)) {
			return false;
		}

		if (UInteger.equals(dia, 31) && !UData.MESES31.contains(mes)) {
			return false;
		}

		if (UCommons.equals(mes, 2) && dia > 29) {
			return false;
		}

		if (UString.lengthIs(s, 2)) {
			return true;
		}

		s = s.substring(2);

		const c0 = parseInt(s.substring(0,1));

		if (c0 < 1 || c0 > 2) {
			return false;
		}

		s = s.substring(1);

		if (UString.isEmpty(s)) {
			return true;
		}

		const c1 = parseInt(s.substring(0,1));
		s = s.substring(1);

		if (UCommons.equals(c0, 1)) {
			if (UCommons.notEquals(c1, 9)) {
				return false;
			}
			if (UString.isEmpty(s)) {
				return true;
			}
			const c2 = parseInt(s.substring(0,1));
			if (c2 < 3) {
				return false;
			}

		} else {

			if (UCommons.notEquals(c1, 0)) {
				return false;
			}
			if (UString.isEmpty(s)) {
				return true;
			}

			if (UString.lengthIs(s, 1)) {
				const c2 = parseInt(s);
				if ((UData.hoje().ano - 2000)/10 < c2) {
					return false;
				}
			} else {
				s = "20" + s.substring(0, 2);
				if (UData.hoje().ano < parseInt(s)) {
					return false;
				}
			}
		}

		return true;

	}

	static formatParcialMesAno(s) {
		s = UString.mantemSomenteNumeros(s);
		if (UString.isEmpty(s)) {
			return "";
		}
		return UData.formatParcial("01" + s).substring(3);
	}

	static formatParcial(s) {
		s = UString.mantemSomenteNumeros(s);
		if (UString.isEmpty(s)) {
			return "";
		}
		while (s.startsWith("00")) {
			s = s.substring(1);
		}
		if (s.equals("0")) {
			return "0";
		}
		if (parseInt(s.substring(0, 1)) > 3) {
			s = "0" + s;
		}
		if (UString.lengthIs(s, 1)) {
			return s;
		}
		const dia = parseInt(s.substring(0,2));

		if (dia > 31) {
			return "3";
		}

		const sdia = UString.format00(dia, 2);
		s = s.substring(2);

		if (UString.isEmpty(s)) {
			return sdia;
		}

		/* mes */

		while (s.startsWith("00")) {
			s = s.substring(1);
		}
		if (s.equals("0")) {
			return sdia + "/0";
		}
		if (parseInt(s.substring(0, 1)) > 1) {
			s = "0" + s;
		}
		if (UString.lengthIs(s, 1)) {
			return sdia + "/" + s;
		}
		let mes = parseInt(s.substring(0,2));
		if (mes > 12) {
			s = "0" + s;
			mes = parseInt(s.substring(0,2));
		}

		const smes = UString.format00(mes, 2);

		if (dia > 29 && UInteger.equals(mes, 2)) {
			return sdia + "/0";
		}
		if (UInteger.equals(dia, 31)) {
			if (!UData.MESES31.contains(mes)) {
				return sdia + "/" + smes.substring(0, 1);
			}
		}

		s = s.substring(2);
		let result = sdia + "/" + smes;

		if (UString.isEmpty(s)) {
			return result;
		}

		/* ano */

		if (s.startsWith("2")) {
			result += "/2";
			s = s.substring(1);
			if (UString.isEmpty(s) || !s.startsWith("0")) {
				return result;
			} else {
				result = result + UString.maxLength(s, 3);
			}
		} else if (s.equals("1") || s.equals("19")) {
			return result + "/" + s;
		} else if (!s.startsWith("19")) {
			result = result + "/19" + UString.maxLength(s, 2);
		} else {
			result = result + "/" + UString.maxLength(s, 4);
		}

		if (UInteger.equals(mes, 2) && UInteger.equals(dia, 29) && UString.lengthIs(result, 10)) {
			const ano = parseInt(UString.afterLast(result, "/"));
			if (!UCommons.equals(ano % 4, 0)) {
				result = UString.ignoreRight(result, 1);
			}
		}

		return result;

	}

	static formatDataHora(data) {
		if (UCommons.isEmpty(data)) {
			return null;
		}
		let dia = data.getDate();
		let mes = data.getMonth() + 1;
		let ano = data.getYear() + 1900;
		let hora = data.getHours();
		let minuto = data.getMinutes();
		let segundo = data.getSeconds();
		return UInteger.format00(dia, 2) + "/" + UInteger.format00(mes, 2) + "/" + ano + " " + UInteger.format00(hora, 2) + ":" + UInteger.format00(minuto, 2) + ":" + UInteger.format00(segundo, 2);
	}

	static test(s, esperado) {
		const x = UData.formatParcial(s);
		if (!esperado.equals(x)) {
			console.log(s + " - " + esperado + " - " + x);
		}
	}
	static main(args) {

		console.log(UData.getIdMes("Fevereiro"));
		console.log(UData.getIdMes("Marco"));

	}

}
UData.MESES31 = [1, 3, 5, 7, 8, 10, 12];
UData.DIAS = UData.constroi(1, 31);
UData.ANOS = UData.constroi(1970, UData.hoje().ano);
