package br.tcc.outros;

import br.tcc.service.ConsultaOperadorService;
import gm.utils.comum.UCpf;
import gm.utils.date.Data;
import gm.utils.email.UEmail;
import gm.utils.exception.MessageException;
import gm.utils.jpa.select.SelectBase;
import gm.utils.jpa.select.SelectBoolean;
import gm.utils.jpa.select.SelectDate;
import gm.utils.jpa.select.SelectInteger;
import gm.utils.jpa.select.SelectNumeric15;
import gm.utils.jpa.select.SelectNumeric2;
import gm.utils.jpa.select.SelectNumeric3;
import gm.utils.jpa.select.SelectNumeric4;
import gm.utils.jpa.select.SelectNumeric5;
import gm.utils.jpa.select.SelectString;
import gm.utils.jpa.select.SelectTyped;
import gm.utils.map.MapSO;
import gm.utils.number.ListInteger;
import gm.utils.number.Numeric15;
import gm.utils.number.Numeric2;
import gm.utils.number.Numeric3;
import gm.utils.number.Numeric4;
import gm.utils.number.Numeric5;

public class FiltroConsulta {
	
	private MapSO o;
	private int operador;
	private boolean negacao;
	
	public static FiltroConsulta get(MapSO params, String campo, SelectTyped<?,?> select) {

		FiltroConsulta fc = new FiltroConsulta();
		fc.o = params.getSub(campo);
		
		if (fc.o == null) {
			return null;
		}
		
		fc.operador = fc.o.getIntObrig("operador");
		fc.negacao = fc.o.getBooleanObrig("negacao");
		
		if (fc.operador == ConsultaOperadorService.VAZIOS) {
			if (fc.negacao) {
				select.isNotNull();
			} else {
				select.isNull();
			}
			return null;
		}
		
		
		return fc;
	}
	
	public static void string(MapSO params, String campo, SelectString<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		string(select, o);
		
		return;
		
	}

	private static void string(SelectString<?> select, FiltroConsulta o) {
		String a = o.o.getStringObrig("a");
		
		if (o.negacao) {
			
			if (o.operador == ConsultaOperadorService.COMECA_COM) {
				select.notStartsWith(a);
			} else if (o.operador == ConsultaOperadorService.TERMINA_COM) {
				select.notEndsWith(a);
			} else if (o.operador == ConsultaOperadorService.CONTEM) {
				select.notLike(a);
			} else if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.COMECA_COM) {
				select.startsWith(a);
			} else if (o.operador == ConsultaOperadorService.TERMINA_COM) {
				select.endsWith(a);
			} else if (o.operador == ConsultaOperadorService.CONTEM) {
				select.like(a);
			} else if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}
	
	
	
	public static void cpf(MapSO params, String campo, SelectString<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		String a = o.o.getStringObrig("a");
		
		if (!UCpf.isValid(a)) {
			throw new MessageException("CPF Inválido: " + a);	
		}
		
		a = UCpf.format(a);
		
		if (o.negacao) {
			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else {
				throw exceptionOperador(o.operador);
			}
		} else {
			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else {
				throw exceptionOperador(o.operador);
			}
		}
		
	}

	public static void email(MapSO params, String campo, SelectString<?> select) {

		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		String a = o.o.getStringObrig("a");
		
		if (!UEmail.isValid(a)) {
			throw new MessageException("E-mail Inválido: " + a);
		}
		
		string(select, o);
		
	}
	
	public static void nomeProprio(MapSO params, String campo, SelectString<?> select) {
		string(params, campo, select);
	}

	public static void date(MapSO params, String campo, SelectDate<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		if (o.operador == ConsultaOperadorService.HOJE) {
			if (o.negacao) {
				select.isNotHoje();
			} else {
				select.isHoje();
			}
			return;
		}
		
		Data a = o.o.getDataObrig("a");
		a.zeraTime();
		
		if (o.negacao) {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.menor(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.maior(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Data b = o.o.getDataObrig("b");
				b.zeraTime();
				select.naoEntre(a, b);
			} else if (o.operador == ConsultaOperadorService.DESMEMBRAR) {
				throw new MessageException("Não implementado");
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.maiorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.menorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Data b = o.o.getDataObrig("b");
				b.zeraTime();
				select.entre(a, b);
			} else if (o.operador == ConsultaOperadorService.DESMEMBRAR) {
				throw new MessageException("Não implementado");
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}
	
	public static void dateTime(MapSO params, String campo, SelectDate<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		if (o.operador == ConsultaOperadorService.HOJE) {
			
			if (o.negacao) {
				select.isNotHoje();
			} else {
				select.isHoje();
			}
			
			return;
		}
		
		Data a = o.o.getDataObrig("a");
		
		if (o.negacao) {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.menor(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.maior(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Data b = o.o.getDataObrig("b");
				select.naoEntre(a, b);
			} else if (o.operador == ConsultaOperadorService.DESMEMBRAR) {
				throw new MessageException("Não implementado");
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.maiorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.menorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Data b = o.o.getDataObrig("b");
				select.entre(a, b);
			} else if (o.operador == ConsultaOperadorService.DESMEMBRAR) {
				throw new MessageException("Não implementado");
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}
	
	public static void fk(MapSO params, String campo, SelectInteger<?> select) {

		FiltroConsulta fc = get(params, campo, select);
		
		if (fc == null) {
			return;
		}
		
		if (fc.operador == ConsultaOperadorService.EM) {
			
			ListInteger ids = ListInteger.byDelimiter(fc.o.getStringObrig("a"), ",");
			
			if (fc.negacao) {
				if (ids.contains(0)) {
					ids.remover(0);
					select.notIn(ids).or().isNotNull();
				} else {
					select.notIn(ids);
				}
			} else {
				if (ids.contains(0)) {
					ids.remover(0);
					select.in(ids).or().isNull();
				} else {
					select.in(ids);
				}
			}
			
		} else {
			throw exceptionOperador(fc.operador);
		}
		
	}

	public static void fk(MapSO params, String campo, SelectBase<?, ?, ?> select) {
		
		MapSO o = params.getSub(campo);
		
		if (o == null) {
			return;
		}
		
		int operador = o.getIntObrig("operador");
		boolean negacao = o.getBooleanObrig("negacao");
		
		if (operador == ConsultaOperadorService.VAZIOS) {
			if (negacao) {
				select.isNotNull();
			} else {
				select.isNull();
			}
			return;
		}

		if (operador == ConsultaOperadorService.EM) {
			
			ListInteger ids = ListInteger.byDelimiter(o.getStringObrig("a"), ",");
			
			if (negacao) {
				if (ids.contains(0)) {
					ids.remover(0);
					select.id().notIn(ids);
					select.or().isNotNull();
				} else {
					select.id().notIn(ids);
				}
			} else {
				if (ids.contains(0)) {
					ids.remover(0);
					select.id().in(ids);
					select.or().isNull();
				} else {
					select.id().in(ids);
				}
			}
			
		} else {
			throw exceptionOperador(operador);
		}
		
	}
	
	public static void money(MapSO params, String campo, SelectNumeric2<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		Numeric2 a = o.o.getNumeric2Obrig("a");
		
		if (o.negacao) {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.menor(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.maior(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric2 b = o.o.getNumeric2Obrig("b");
				select.naoEntre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.maiorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.menorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric2 b = o.o.getNumeric2Obrig("b");
				select.entre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}
	
	public static void decimal(MapSO params, String campo, SelectNumeric2<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		Numeric2 a = o.o.getNumeric2Obrig("a");
		
		if (o.negacao) {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.menor(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.maior(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric2 b = o.o.getNumeric2Obrig("b");
				select.naoEntre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.maiorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.menorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric2 b = o.o.getNumeric2Obrig("b");
				select.entre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}
	
	public static void decimal(MapSO params, String campo, SelectNumeric3<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		Numeric3 a = o.o.getNumeric3Obrig("a");
		
		if (o.negacao) {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.menor(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.maior(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric3 b = o.o.getNumeric3Obrig("b");
				select.naoEntre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.maiorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.menorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric3 b = o.o.getNumeric3Obrig("b");
				select.entre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}
	
	public static void decimal(MapSO params, String campo, SelectNumeric4<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		Numeric4 a = o.o.getNumeric4Obrig("a");
		
		if (o.negacao) {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.menor(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.maior(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric4 b = o.o.getNumeric4Obrig("b");
				select.naoEntre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.maiorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.menorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric4 b = o.o.getNumeric4Obrig("b");
				select.entre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}
	
	public static void decimal(MapSO params, String campo, SelectNumeric5<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		Numeric5 a = o.o.getNumeric5Obrig("a");
		
		if (o.negacao) {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.menor(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.maior(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric5 b = o.o.getNumeric5Obrig("b");
				select.naoEntre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.maiorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.menorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric5 b = o.o.getNumeric5Obrig("b");
				select.entre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}

	public static void decimal(MapSO params, String campo, SelectNumeric15<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		Numeric15 a = o.o.getNumeric15Obrig("a");
		
		if (o.negacao) {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.menor(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.maior(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric15 b = o.o.getNumeric15Obrig("b");
				select.naoEntre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.maiorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.menorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				Numeric15 b = o.o.getNumeric15Obrig("b");
				select.entre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}
	
	public static void integer(MapSO params, String campo, SelectInteger<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		int a = o.o.getIntObrig("a");
		
		if (o.negacao) {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.ne(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.menor(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.maior(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				int b = o.o.getIntObrig("b");
				select.naoEntre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		} else {

			if (o.operador == ConsultaOperadorService.IGUAL) {
				select.eq(a);
			} else if (o.operador == ConsultaOperadorService.MAIOR_OU_IGUAL) {
				select.maiorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.MENOR_OU_IGUAL) {
				select.menorOuIgual(a);
			} else if (o.operador == ConsultaOperadorService.ENTRE) {
				int b = o.o.getIntObrig("b");
				select.entre(a, b);
			} else {
				throw exceptionOperador(o.operador);
			}
			
		}
		
	}

	public static void bool(MapSO params, String campo, SelectBoolean<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		if (o.operador == ConsultaOperadorService.SIM) {
			select.eq(true);
		} else if (o.operador == ConsultaOperadorService.NAO) {
			select.eq(false);
		} else {
			throw exceptionOperador(o.operador);
		}
		
	}
	
	public static void senha(MapSO params, String campo, SelectString<?> select) {
		
		FiltroConsulta o = get(params, campo, select);
		
		if (o == null) {
			return;
		}
		
		throw exceptionOperador(o.operador);
		
	}

	private static RuntimeException exceptionOperador(int operador) {
		return new RuntimeException("Operador não tratado: " + operador);
	}

}
