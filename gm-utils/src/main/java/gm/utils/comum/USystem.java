package gm.utils.comum;

import java.net.InetAddress;

import gm.utils.exception.UException;
import gm.utils.lambda.FVoidVoid;
import gm.utils.string.UString;

public class USystem {

	public static void sleepHoras(final double horas) {
		USystem.sleepMinutos(horas * 60);
	}
	public static void sleepMinutos(final double minutos) {
		USystem.sleepSegundos(minutos * 60);
	}
	public static void sleepSegundos(final double segundos) {
		final long d = (long) (segundos * 1000);
		USystem.sleepMiliSegundos(d);
	}
	public static void sleepMiliSegundos(final long milisegundos) {
		try {
			Thread.sleep(milisegundos);
		} catch (final InterruptedException e) {
			UException.printTrace(e);
		}
	}

	private static String hostName;
	
	public static String hostName() {
		if (USystem.hostName != null) {
			return USystem.hostName;
		}
		try {	
			USystem.hostName = InetAddress.getLocalHost().getHostName();
			return USystem.hostName;
		} catch (final Exception e) {
			final String s = e.getMessage();
			
			if (s.contains(":")) {
				final String a = UString.beforeFirst(s, ":");
				if (s.contains(":")) {
					final String b = UString.beforeFirst(s, ":");
					if (a.equals(b)) {
						USystem.hostName = a;
						return USystem.hostName; 
					}
				}
			}
			
			throw UException.runtime(e);
		}
	}
	
	public static int timeoutsEmExecucao = 0;
	
	public static void setTimeout(final FVoidVoid f, final int milisegundos) {
		USystem.setTimeout(f, milisegundos, true);
	}
	
	private static void setTimeout(final Thread threadOrigem, final FVoidVoid f, final int milisegundos, final boolean count) {

//		final long currentTimeMillis = System.currentTimeMillis();

		if (count) {
			USystem.timeoutsEmExecucao++;
		}
		
		new Thread() {
			@Override
			public void run() {
				try {
					USystem.sleepMiliSegundos(milisegundos);
//					while (System.currentTimeMillis() - currentTimeMillis < milisegundos) {
//						
//					}
					if (threadOrigem.isAlive()) {
						USystem.setTimeout(threadOrigem, f, 500, count);
					} else {
						f.call();
					}
				} finally {
					if (count) {
						USystem.timeoutsEmExecucao--;
					}
				}
			}
		}.start();
		
	}
	
	public static void setTimeout(final FVoidVoid f, final int milisegundos, final boolean count) {
		USystem.setTimeout(Thread.currentThread(), f, milisegundos, count);
	}
	
	public static String userHome() {
		return System.getProperty("user.home");
	}
	
}
