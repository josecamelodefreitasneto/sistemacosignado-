package gm.utils.outros;

import gm.utils.lambda.FVoidVoid;

public class UThread {
	public static Thread exec(FVoidVoid func) {
		final Thread o = new Thread() {
			@Override
			public void run() {
				func.call();
			}
		};
		o.start();
		return o;		
	}
}
