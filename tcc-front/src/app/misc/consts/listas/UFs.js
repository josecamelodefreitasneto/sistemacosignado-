/* react */
import IdText from '../../utils/IdText';

export default class UFs {

	static main(args) {
		UFs.UFS_BRASILEIRAS.forEach(o => console.log("\"" + o.text + "\","));
	}

}
UFs.UFS_BRASILEIRAS = [
	IdText.VAZIO
	, new IdText(1, "AC - Acre").setSigla("AC")
	, new IdText(2, "AL - Alagoas").setSigla("AL")
	, new IdText(3, "AP - Amapá").setSigla("AP")
	, new IdText(4, "AM - Amazonas").setSigla("AM")
	, new IdText(5, "BA - Bahia").setSigla("BA")
	, new IdText(6, "CE - Ceará").setSigla("CE")
	, new IdText(7, "DF - Distrito Federal").setSigla("DF")
	, new IdText(8, "ES - Espírito Santo").setSigla("ES")
	, new IdText(9, "GO - Goiás").setSigla("GO")
	, new IdText(10, "MA - Maranhão").setSigla("MA")
	, new IdText(11, "MT - Mato Grosso").setSigla("MT")
	, new IdText(12, "MS - Mato Grosso do Sul").setSigla("MS")
	, new IdText(13, "MG - Minas Gerais").setSigla("MG")
	, new IdText(14, "PA - Pará").setSigla("PA")
	, new IdText(15, "PB - Paraíba").setSigla("PB")
	, new IdText(16, "PR - Paraná").setSigla("PR")
	, new IdText(17, "PE - Pernambuco").setSigla("PE")
	, new IdText(18, "PI - Piauí").setSigla("PI")
	, new IdText(19, "RJ - Rio de Janeiro").setSigla("RJ")
	, new IdText(20, "RN - Rio Grande do Norte").setSigla("RN")
	, new IdText(21, "RS - Rio Grande do Sul").setSigla("RS")
	, new IdText(22, "RO - Rondônia").setSigla("RO")
	, new IdText(23, "RR - Roraima").setSigla("RR")
	, new IdText(24, "SC - Santa Catarina").setSigla("SC")
	, new IdText(25, "SP - São Paulo").setSigla("SP")
	, new IdText(26, "SE - Sergipe").setSigla("SE")
	, new IdText(27, "TO - Tocantins").setSigla("TO")
];
