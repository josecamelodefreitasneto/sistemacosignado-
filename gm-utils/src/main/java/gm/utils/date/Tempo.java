package gm.utils.date;

import gm.utils.comum.ULog;

//@Getter @Setter
public class Tempo {
	
	private int anos;
	private int meses;
	private int dias;
	private int horas;
	private int minutos;
	private int segundos;
	private boolean negativo;

	public void print() {
		ULog.debug(this);
	}

	public Tempo(int dias){
		
		while (dias >= 365) {
			dias -= 365;
			anos++;
		}
		
		while (dias >= 30) {
			dias -= 30;
			meses++;
		}
		
		this.dias = dias;
		
	}
	
	public static void main(String[] args) {
		
		Data a = new Data(2018,5,15);
		Data b = new Data(2020,5,5);

		Tempo diferenca = a.diferenca(b);
		ULog.debug( diferenca.emDias() );
		ULog.debug( diferenca.emMeses() );
		ULog.debug( diferenca );
		
	}

	private void decAnos() {
		anos--;
		meses += 12;
	}
	
	private void decMes() {
		meses--;
		dias += 30;
		if (meses == -1) {
			decAnos();
		}
	}
	private void decDias() {
		dias--;
		horas += 24;
		if (dias == -1) {
			decMes();
		}
	}
	private void decHoras() {
		horas--;
		minutos += 60;
		if (horas == -1) {
			decDias();
		}
	}
	private void decMinutos() {
		minutos--;
		segundos += 60;
		if (minutos == -1) {
			decHoras();
		}
	}
	
	public Tempo(Data a, Data b){
		
		if (a.menor(b)) {
			Data c = a;
			a = b;
			b = c;
			negativo = true;
		}
		
		anos = a.getAno() - b.getAno();
		
		meses = a.getMes() - b.getMes();
		if (meses < 0) {
			decAnos();
		}
		
		dias = a.getDia() - b.getDia();
		if ( dias < 0 ) {
			decMes();
		}
		
		horas = a.getHora() - b.getHora();
		if ( horas < 0 ) {
			decDias();
		}
		
		minutos = a.getMinuto() - b.getMinuto();
		if ( minutos < 0 ) {
			decHoras();
		}
		
		segundos = a.getSegundo() - b.getSegundo();
		if ( segundos < 0 ) {
			decMinutos();
		}
				
	}
	
	public int emHoras() {
		return horas + dias*24*60;
	}
	public int emMinutos() {
		return minutos + emHoras() * 60;
	}
	public int emSegundos() {
		return segundos + emMinutos() * 60;
	}
	public int emDias(){
		return anos * 365 + meses * 30 + dias;
	}
	public int emMeses(){
		return meses;
	}
	public int emAnos() {
		return anos;
	}
	public int emDiasReais(){
		int x = emDias();
		if (negativo) {
			return -x;
		} else {
			return x;
		}
	}
	@Override
	public String toString() {
		
		String s = " " + anos + " anos e " + meses + " meses e " + dias + " dias";
		
		s = s.replace(" 0 anos e ", " ");
		s = s.replace(" 1 anos e ", " 1 ano e ");
		s = s.replace(" 0 meses e ", " ");
		s = s.replace(" 1 meses e ", " 1 m\u00eas e ");
		s = s.replace(" e 0 dias", " ");
		s = s.replace(" 1 dias", " 1 dia");
		s = s.trim();
		
		return s;
	}

}
