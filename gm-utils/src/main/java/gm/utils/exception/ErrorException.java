package gm.utils.exception;

public class ErrorException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private boolean jaImpresso = false; 
	
	public ErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ErrorException(String message) {
		super(message);
	}

	public ErrorException(Throwable e) {
		super(e);
	}

	@Override
	public void printStackTrace() {
		if (!jaImpresso) {
			super.printStackTrace();
			jaImpresso = true;
		}
	}
	

}
