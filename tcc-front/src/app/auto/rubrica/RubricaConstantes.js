/* tcc-java */
import IdText from '../../misc/utils/IdText';

export default class RubricaConstantes {
	static getList() {
		return RubricaConstantes.list;
	}
}
RubricaConstantes._00596_PENSAO_CIVIL = 596;
RubricaConstantes._55986_PENSAO_MILITAR = 55986;
RubricaConstantes._53341_SERVIDOR_CIVIL = 53341;
RubricaConstantes._98020_CONTRIBUICAO_PLANO_SEGURO_SOCIAL_PENSIONISTA = 98020;
RubricaConstantes._99015_IMPOSTO_DE_RENDA_APOSENTADO_PENSIONISTA = 99015;
RubricaConstantes._93389_IRRF_IMPOSTO_DE_RENDA_RETIDO_NA_FONTE = 93389;
RubricaConstantes._95534_PSS_RPGS_PREVIDENCIA_SOCIAL = 95534;
RubricaConstantes.list = [
	new IdText(RubricaConstantes._00596_PENSAO_CIVIL, "00596 - Pensão Civil"),
	new IdText(RubricaConstantes._55986_PENSAO_MILITAR, "55986 - Pensão Militar"),
	new IdText(RubricaConstantes._53341_SERVIDOR_CIVIL, "53341 - Servidor Civil"),
	new IdText(RubricaConstantes._98020_CONTRIBUICAO_PLANO_SEGURO_SOCIAL_PENSIONISTA, "98020 - Contribuição Plano Seguro Social - Pensionista"),
	new IdText(RubricaConstantes._99015_IMPOSTO_DE_RENDA_APOSENTADO_PENSIONISTA, "99015 - Imposto de Renda Aposentado/Pensionista"),
	new IdText(RubricaConstantes._93389_IRRF_IMPOSTO_DE_RENDA_RETIDO_NA_FONTE, "93389 - IRRF - Imposto de Renda Retido na Fonte"),
	new IdText(RubricaConstantes._95534_PSS_RPGS_PREVIDENCIA_SOCIAL, "95534 - PSS/RPGS - Previdência Social")
];
