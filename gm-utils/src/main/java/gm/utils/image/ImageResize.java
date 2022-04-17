package gm.utils.image;

import java.io.File;

import gm.utils.comum.UAssert;
import gm.utils.comum.UConstantes;
import gm.utils.exception.UException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class ImageResize {
	
	protected ImageResize(){}
	
	private String origem;
	private String destino;
	private Integer altura;
	private Integer largura;
	protected abstract void execute();
	public boolean exec(){
		UAssert.notEmpty(getOrigem(), "Origem n"+UConstantes.a_til+"o informada");
		UAssert.notEmpty(getDestino(), "Destino n"+UConstantes.a_til+"o informado");
		if (getAltura() == null && getLargura() == null) {
			throw UException.runtime("Largura ou Altura deve ser informada");
		}
		File file = new File(getOrigem());
		UAssert.isTrue(file.exists(), "O arquivo de origem n"+UConstantes.a_til+"o existe");
		try {
			execute();
			return true;
		} catch (Exception e) {
			throw UException.runtime("N"+UConstantes.a_til+"o foi poss"+UConstantes.i_agudo+"vel converter o arquivo", e);
		}
	}
	
	public static ImageResize novo(){
		return new ImageResizeJava();
	}  
	
}

