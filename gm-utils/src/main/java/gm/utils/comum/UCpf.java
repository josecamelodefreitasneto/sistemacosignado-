package gm.utils.comum;

import gm.utils.number.UNumber;
import gm.utils.string.UString;
import gm.utils.string.UStringConstants;
import gm.utils.string.UStringFiltrarCaracteres;

public class UCpf {
	
	public static String format(String cpf) {
		cpf = UStringFiltrarCaracteres.numeros(cpf);
		while (cpf.length() != 11) {
			cpf = "0" + cpf;
		}
		cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
		return cpf;
	}

	@Deprecated//utilize isValid
	public static boolean valido(String cpf) {
		return isValid(cpf);
	}
    public static boolean isValid(String cpf) {
    	if (UString.isEmpty(cpf)) {
			return false;
		}
    	cpf = UStringFiltrarCaracteres.numeros(cpf);
        if (cpf.length() != 11)  
            return false;  
        String numDig = cpf.substring(0, 9);
        if (!calcDigVerif(numDig).equals(cpf.substring(9, 11))) {
        	return false;
        }
        for (String n : UStringConstants.NUMEROS) {
        	if (cpf.replace(n, "").isEmpty()) {
        		return false;
        	}
		}
        return true;  
    }
    
    public static String mock(int i) {
    	String s = UNumber.format00(i, 3);
    	s = s + s + s;
    	int digito = 0;
    	while (!isValid(s + UNumber.format00(digito, 2))) {
    		digito++;
		}
    	return format(s + UNumber.format00(digito, 2));
    	
    }
    public static String aleatorio() {  
        String iniciais = "";  
        Integer numero;  
        for (int i = 0; i < 9; i++) {  
            numero = (int) (Math.random() * 10);  
            iniciais += numero.toString();  
        }  
        String s = iniciais + calcDigVerif(iniciais);
        s = s.substring(0, 3) + "." + s.substring(3, 6) + "." + s.substring(6, 9) + "-" + s.substring(9, 11);
        return s;  
    }  

    
    private static String calcDigVerif(String num) {  
        Integer primDig, segDig;  
        int soma = 0;
        int peso = 10;  
        for (int i = 0; i < num.length(); i++) {
        	soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;  
        }
        if (soma % 11 == 0 | soma % 11 == 1) {
        	primDig = 0;  
        } else {
        	primDig = 11 - (soma % 11);  
        }
        soma = 0;  
        peso = 11;  
        for (int i = 0; i < num.length(); i++) {
        	soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;  
        }
        soma += primDig.intValue() * 2;  
        if (soma % 11 == 0 | soma % 11 == 1) {
        	segDig = 0;  
        } else {
        	segDig = 11 - (soma % 11);  
        }
        return primDig.toString() + segDig.toString();  
    }    
}
