package gm.utils.date;

import gm.utils.comum.ULog;
import gm.utils.number.UInteger;

public class Cronometro {
	private long time;
	public Cronometro(){
		restart();
	}
	public void restart(){
		time = System.currentTimeMillis();
	}
	public long tempo(){
		return System.currentTimeMillis() - time;
	}
	public void print() {
		System.out.println("mills > " + tempo());
	}
	public static void print(Cronometro cron, String x) {
		long tempo = cron.tempo();
		if (tempo < 10) {
			return;
		}
		String s = x + " Start: " + cron.time + " / Time: " + tempo;
		ULog.debug( s );
	}
	public int segundos(){
		return UInteger.toInt(tempo() / 1000);
	}
	public int minutos() {
		return UInteger.toInt(segundos() / 60);
	}
	public int horas() {
		return UInteger.toInt(minutos() / 60);
	}
	public static Cronometro start() {
		return new Cronometro();
	}
}
