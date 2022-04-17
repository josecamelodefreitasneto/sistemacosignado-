package gm.utils.comum;

import java.util.List;

import gm.utils.date.Data;

public abstract class CachedList<T> {

	public static final int MINUTOS_PADRAO = 120;
	
	private List<T> list;
	private Data data;
	private final int minutos;
	
	public CachedList(int minutos) {
		this.minutos = minutos;
	}
	public CachedList() {
		this(MINUTOS_PADRAO);
	}
	
	public List<T> get() {
		if (expirado() ) {
			atualiarSync();
		}
		return list;
	}
	
	private synchronized void atualiarSync() {
		if (expirado()) {
			this.list = atualizar();
			this.data = Data.now();
		}
	}
	
	private boolean expirado() {
		return data == null || data.jaPassouMinutos(minutos);
	}
	
	protected abstract List<T> atualizar();

	public void clear() {
		data = null;
	} 
	
}
