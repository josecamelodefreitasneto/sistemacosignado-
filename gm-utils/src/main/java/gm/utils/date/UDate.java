package gm.utils.date;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import gm.utils.comum.UCompare;

public class UDate {
	
	public static final SimpleDateFormat FORMAT1 = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat FORMAT2 = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat FORMAT_ANOMES = new SimpleDateFormat("yyyyMM");
	public static final SimpleDateFormat FORMAT_DD_MM_YYYY = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat FORMAT_YYYY = new SimpleDateFormat("yyyy");
	public static final SimpleDateFormat FORMAT_MM = new SimpleDateFormat("MM");
	
	public static Date toDate(String s) {
		try {
			return FORMAT2.parse(s);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static String asFormat(Date date, SimpleDateFormat format) {
		if (date == null) {
			return null;
		}
		return format.format(date);	
	}
	
	public static String asAnoMes(Date date) {
		return asFormat(date, FORMAT_ANOMES);
	}
	
	public static String asBancoFormat(Date date) {
		return asFormat(date, FORMAT1);
	}
	
    public static LocalDate toLocalDate(Date date) {
    	return date == null ? null : new java.sql.Date(date.getTime()).toLocalDate();
    }
    
    public static LocalDateTime toLocalDateTime(Timestamp time) {
    	return time != null ? time.toLocalDateTime() : null;
    }

	public static Date hoje() {
		return GregorianCalendar.getInstance().getTime();
	}
	
	public static Date addDias(Date date) {
		return date;
	}
	public static int getMonth(Date date) {
		return Integer.parseInt(asFormat(date, FORMAT_MM));
	}
	public static int getYear(Date date) {
		return Integer.parseInt(asFormat(date, FORMAT_YYYY));
	}

	public static Calendar nowCalendar() {
//		if (fake != null) {
//			return fake.getCalendar();
//		}
		return GregorianCalendar.getInstance();
	}

	public static boolean isData(Object o) {
		return isData(o.getClass());
	}
	
	public static boolean isData(Class<?> c) {
		if (c.equals(java.util.Calendar.class))
			return true;
		if (c.equals(java.util.GregorianCalendar.class))
			return true;
		if (c.equals(MyCalendar.class))
			return true;
		if (c.equals(java.util.Date.class))
			return true;
		if (c.equals(java.sql.Date.class))
			return true;
		if (c.equals(java.sql.Timestamp.class))
			return true;
		if (c.equals(Data.class))
			return true;
		return false;
	}

	public static String javaToJsDate(Date data){
		SimpleDateFormat fYear = new SimpleDateFormat("yyyy");
		SimpleDateFormat fMonth = new SimpleDateFormat("MM");
		SimpleDateFormat f = new SimpleDateFormat("dd, HH, mm, ss");
		String restante = "";
		String dataRet = "";
		String m = "";
		String y = "";
		if (data == null){
			return "";
		}
		y = fYear.format(data.getTime());
		m = fMonth.format(data.getTime());
		restante = f.format(data.getTime());
		dataRet = y + ","+ (Integer.parseInt(m)-1) +", "+ restante;
		return "new Date("+ dataRet +")";
	}
	public static boolean eq(Calendar a, Calendar b) {
		Integer x = UCompare.basicCompare(a, b);
		if (x == null) x = a.compareTo(b);
		return x == 0;
	}
	public static boolean ne(Calendar a, Calendar b) {
		return !eq(a, b);
	}

	public static String format(Calendar data) {
		Data d = Data.to(data);
		return d == null ? null : d.format_dd_mm_yyyy();
	}
	public static Calendar zeraTime(Calendar c) {
		if (c == null) {
			return null;
		}
		Data data = new Data(c);
		data.zeraTime();
		return data.getCalendar();
	}
	
	public static Integer getDiffDateInMonth(Data start, Data finish) {
        if (start == null || finish == null) {
            return null;
        }
		return start.diferenca(finish).emMeses();
	}
    public static Integer getDiffDateInMonth(Date start, Date finish) {

        if (start == null || finish == null) {
            return null;
        }

        Calendar startCalendar = new GregorianCalendar();
        startCalendar.setTime(start);
        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(finish);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
        int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

        return diffMonth;
    }

	public static Integer getDay(Data data) {
		return data == null ? null : data.getDia();
	}	
	public static Integer getMonth(Data data) {
		return data == null ? null : data.getMes();
	}	
	public static Integer getYear(Data data) {
		return data == null ? null : data.getAno();
	}	

	public static Integer diffMonth(Data a, Data b) {
		int va = a.getAno() * 12 + a.getMes();
		int vb = b.getAno() * 12 + b.getMes();
		if (va > vb) {
			int x = va;
			va = vb;
			vb = x;
		}
		return vb - va;
	}
	
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat sdfano = new SimpleDateFormat("yyyy");
	private static final Random random = new Random();
	
	public static Date aleatoria(Date inicio, Date fim) {
		
		int valorInicial = Integer.parseInt(sdf.format(inicio));
		int valorFinal = Integer.parseInt(sdf.format(fim));
		
		if (valorInicial > valorFinal) {
			int x = valorInicial;
			valorInicial = valorFinal;
			valorFinal = x;
		}
		
		int anoInicial = Integer.parseInt(sdfano.format(inicio));
		int anoFinal = Integer.parseInt(sdfano.format(fim));
		
		int dif = anoFinal - anoInicial;
		int ano = anoInicial + random.nextInt(dif);
		
		while (true) {
			
			int mes = random.nextInt(12)+1;
			int dia = random.nextInt(31)+1;
			
			int value = ano*10000 + mes*100 + dia;
			
			if (value < valorInicial || value > valorFinal) {
				continue;
			}
			
			try {
				return new GregorianCalendar(ano, mes, dia).getTime();
			} catch (Exception e) {
				continue;
			}
			
		}
		
	}	
	
}
