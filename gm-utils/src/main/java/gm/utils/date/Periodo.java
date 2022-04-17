package gm.utils.date;

import java.util.Calendar;
import java.util.Date;

import gm.utils.exception.UException;
import gm.utils.number.UInteger;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Periodo {

	Data inicio;
	Data fim;
	
	public Periodo(int ano, int mes) {
		inicio = new Data(ano, mes, 1);
		fim = new Data(ano, mes, 28);
		fim.setUltimoDiaMes();
	}
	public Periodo(Data inicio, Data fim) {
		this.inicio = inicio;
		this.fim = fim;
	}
	public Periodo(Calendar inicio, Calendar fim) {
		this( Data.newData(inicio), Data.newData(fim) );
	}
	public Periodo(Date inicio, Date fim) {
		this( Data.newData(inicio), Data.newData(fim) );
	}
	private void valida(){
		if (inicio == null) {
			throw UException.runtime(">> erro: inicio == null <<");
		}
		if (fim == null) {
			throw UException.runtime(">> erro: fim == null <<");
		}
	}
	public String sqlDia(){
		
		valida();
		
		String a = inicio.format_sqlDia();
		
		if ( inicio.eq(fim) ) {
			return " = " + a;
		}
		
		String b = fim.format_sqlDia();
		
		return " between " + a + " and " + b ;		
		
	}
	public String entre_dia(){
		
		valida();
		
		String a = inicio.format_dd_mmm_yyyy();
		
		if ( inicio.eq(fim) ) {
			return a;
		}
		
		if (inicio.getAno() == fim.getAno()) {
			
			if ( inicio.getMes() == fim.getMes() ) {
				
				if (inicio.getDia() == 1 && fim.isUltimoDiaDoMes()) {
					return inicio.format_mmmm_de_yyyy();
				}
				
				a = inicio.format_dd();
				
			} else {
				a = inicio.format_dd_mmm();	
			}
			
		}

		String b = fim.format_dd_mmm_yyyy();
		return "entre " + a + " e " + b;
		
	}
	public void zeraTime(){
		inicio.zeraTime();
		fim.zeraTime();
	}
	public int segundos(){
		long a = inicio.getCalendar().getTimeInMillis();
		long b = fim.getCalendar().getTimeInMillis();
		long c = b - a;
		int d = UInteger.toInt(c / 1000);
		return d;
	}
	
	public int minutosUteis(){
		Data data = inicio.copy();
		Data fim = this.fim.copy();
		
		int minutos = 0;
		
		if ( !data.mesmoDiaQue(fim) ) {
			
			if (data.diaUtil() && data.getHora() < 18 ) {
				
				if (data.getHora() < 8) {
					data.zeraTime();
					data.setHora(8);
				}
				
				minutos -= data.getMinuto();
				
				int h = data.getHora();
				if (h < 12) {
					h += 2;//almoco
				} else if (h < 14){
					h = 14;//dexprezar almoco
				}
				
				minutos += (18 - h) * 60;
				
			}
			
			data.zeraTime();
			data.setHora(8);
			data.add();
			
			while ( !data.mesmoDiaQue(fim) ) {
				minutos += 8*60;//8 horas uteis
				data.add();
			}
			
		}
		
		if (!fim.diaUtil()) {
			return minutos;
		}
		
		if (fim.getHora() > 18) {
			fim.zeraTime();
			fim.setHora(18);
		}
		
		if (fim.getHora() >= 12 && fim.getHora() < 14) {
			fim.zeraTime();
			fim.setHora(12);//desprezar almoco
		}

		if (data.maior(fim)) {
			return minutos;
		}
		
		if (data.getHora() < 8) {
			data.zeraTime();
			data.setHora(8);
		}
		
		if (fim.getHora() > 18) {
			minutos += (18 - data.getHora()) * 60 - data.getMinuto();
			return minutos;
		}
		
		int h = data.getHora();
		if (h < 12) {
			if (fim.getHora() > 12) {
				h += 2;//almoco
			}
		} else if (h < 14){
			h = 14;//dexprezar almoco
		}
		
		minutos += (fim.getHora() - h) * 60;
		minutos -= data.getMinuto(); 
		minutos += fim.getMinuto(); 
		
		return minutos;
		
	}
	public int minutos(){
		return UInteger.toInt(segundos() / 60);
	}
	public int horas(){
		return UInteger.toInt(minutos() / 60);
	}
	
}
