/* react */
import IdText from './IdText';
import UArray from './UArray';

export default class UConstantes {

	static MESES;

	static getMeses() {
		if (UArray.isEmpty(UConstantes.MESES)) {
			UConstantes.MESES = [
				new IdText(1, "Janeiro"),
				new IdText(2, "Fevereiro"),
				new IdText(3, "Março"),
				new IdText(4, "Abril"),
				new IdText(5, "Maio"),
				new IdText(6, "Junho"),
				new IdText(7, "Julho"),
				new IdText(8, "Agosto"),
				new IdText(9, "Setembro"),
				new IdText(10, "Outubro"),
				new IdText(11, "Novembro"),
				new IdText(12, "Dezembro")
			];
		}
		return UConstantes.MESES;
	}

	static MESES_INVERTIDOS;
	static getMesesInvertidos() {
		if (UArray.isEmpty(UConstantes.MESES_INVERTIDOS)) {
			UConstantes.MESES_INVERTIDOS = UConstantes.getMeses().copy().sortByIdReverse();
		}
		return UConstantes.MESES_INVERTIDOS;
	}

}
UConstantes.numeros = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9"];
UConstantes.caracteresNumericos = UConstantes.numeros.concat([".", ","]);
UConstantes.letrasMaiusculas = ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"];
UConstantes.letrasMinusculas = ["a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"];
UConstantes.acentuadasMinusculas = [
	"á","à","ã","â","ä",
	"é","è","ẽ","ê","ë",
	"í","ì","ĩ","î","ï",
	"ó","ò","õ","ô","ö",
	"ú","ù","ũ","û","ü"
];
UConstantes.acentuadasMaiusculas = [
	"Á","À","Ã","Â","Ä",
	"É","È","Ẽ","Ê","Ë",
	"Í","Ì","Ĩ","Î","Ï",
	"Ó","Ò","Õ","Ô","Ö",
	"Ú","Ù","Ũ","Û","Ü"
];
