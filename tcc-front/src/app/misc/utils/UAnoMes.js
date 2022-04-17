/* react */
import UConstantes from './UConstantes';
import UInteger from './UInteger';
import UString from './UString';

export default class UAnoMes {

	static compare_mmmm_yyyy(a, b) {
		if (UString.isEmpty(a)) {
			if (UString.isEmpty(b)) {
				return 0;
			} else {
				return -1;
			}
		} else if (UString.isEmpty(b)) {
			return 1;
		}

		const inta = UAnoMes.mmmm_yyyy_toInt(a);
		const intb = UAnoMes.mmmm_yyyy_toInt(b);

		return UInteger.compare(inta, intb);

	}

	static mmmm_yyyy_toInt(s) {
		const nomeMes = UString.beforeFirst(s, "/");
		const mes = UConstantes.getMeses().uniqueObrig(o => UString.equivalente(o.text, nomeMes)).getId();
		s = UString.afterLast(s, "/") + UInteger.format00(mes, 2);
		return UInteger.toInt(s);
	}
	static idAnoMes(ano, mes) {
		return ano * 100 + mes;
	}
	static indexAnoMes(ano, mes) {
		return (ano - 1900) * 12 + mes;
	}
	static getAno(anoMes) {
		return UInteger.toInt(anoMes / 100);
	}
	static getMes(anoMes) {
		return anoMes - UAnoMes.getAno(anoMes) * 100;
	}

	static menosMeses(anoMes, meses) {
		let ano = UAnoMes.getAno(anoMes);
		let mes = UAnoMes.getMes(anoMes);
		mes -= meses;
		if (mes < 1) {
			ano--;
			mes += 12;
		}
		return UAnoMes.idAnoMes(ano, mes);
	}

	static maisMeses(anoMes, meses) {
		let ano = UAnoMes.getAno(anoMes);
		let mes = UAnoMes.getMes(anoMes);
		mes += meses;
		if (mes > 12) {
			ano++;
			mes -= 12;
		}
		return UAnoMes.idAnoMes(ano, mes);
	}

}
