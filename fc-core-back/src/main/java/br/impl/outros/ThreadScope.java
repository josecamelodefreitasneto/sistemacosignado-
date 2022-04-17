package br.impl.outros;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.auto.service.AuditoriaEntidadeBox;
import gm.utils.comum.Lst;
import gm.utils.lambda.FVoidVoid;

public class ThreadScope {
	
	private int login;
	private int comando;
	private long datahora;
	private List<FVoidVoid> callOnSuccess = new ArrayList<>();
	private List<AuditoriaEntidadeBox> auditorias = new ArrayList<>();
	
	private static Map<Thread, ThreadScope> map = new HashMap<>();
	
	public ThreadScope(int login, int comando) {
		this.login = login;
		this.comando = comando;
		map.put(Thread.currentThread(), this);
		datahora = System.currentTimeMillis();
	}
	public static int getLogin() {
		return get().login;
	}
	public static ThreadScope get() {
		return map.get(Thread.currentThread());
	}
	public static void finalizarComSucesso() {
		ThreadScope o = get();
		if (o != null) {
			o.callOnSuccess.forEach(func -> func.call());
		}
	}
	public static void dispose() {
		Lst<Thread> deads = new Lst<>();
		map.keySet().forEach(o -> {
			if (!o.isAlive()) deads.add(o);
		});
		deads.forEach(o -> dispose(o));
		dispose(Thread.currentThread());
	}
	private static void dispose(Thread thread) {
		map.remove(thread);
	}
	public static void addOnSuccess(FVoidVoid func) {
		get().callOnSuccess.add(func);
	}
	public static void addAuditoria(AuditoriaEntidadeBox auditoriaEntidadeBox) {
		get().auditorias.add(auditoriaEntidadeBox);
	}
	public static int getComando() {
		return get().comando;
	}
	public static long getDataHora() {
		return get().datahora;
	}
	public static List<AuditoriaEntidadeBox> getAuditorias() {
		return get().auditorias;
	}
	public static void clear() {
		get().auditorias.clear();
		get().datahora = System.currentTimeMillis();
	}
	public static void setDataHora(long time) {
		get().datahora = time;
	}
	
}