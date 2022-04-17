package gm.utils.exception;

import gm.utils.config.UConfig;
import gm.utils.string.CorretorOrtografico;

public class MessageException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public MessageException (String mensagem){
		super(CorretorOrtografico.exec(mensagem));
	}

	@Override
	public void printStackTrace() {
		try {
			if (UConfig.get() != null && UConfig.get().emDesenvolvimento()) {
				UException.trata(this);
				super.printStackTrace();
			}
		} catch (Exception e) {}
	}
	
}
