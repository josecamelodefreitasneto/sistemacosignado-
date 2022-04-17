package gm.utils.rest;

import java.io.PrintStream;

import gm.utils.map.MapSO;
import lombok.Getter;

@Getter
public class ResponseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private MapSO body;
	private int statusCode;
	private boolean jaImpresso = false;
	public ResponseException(String message, int statusCode, MapSO body) {
		super(message);
		this.statusCode = statusCode;
		this.body = body;
	}
	@Override
	public void printStackTrace(PrintStream s) {
		if (jaImpresso) return;
		jaImpresso = true;
		System.out.println("---------------------------------");
		System.out.println("statusCode: " + statusCode);
		body.print();
		super.printStackTrace(s);
	}
//	@Override
//	public void printStackTrace(PrintStream s) {
//		this.printStackTrace();
//	}
//	@Override
//	public void printStackTrace(PrintWriter s) {
//		this.printStackTrace();
//	}
}
