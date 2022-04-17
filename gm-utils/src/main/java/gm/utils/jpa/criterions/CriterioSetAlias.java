package gm.utils.jpa.criterions;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.NotExpression;
import org.hibernate.criterion.Order;
import org.hibernate.internal.CriteriaImpl.CriterionEntry;
import org.hibernate.sql.JoinType;

import gm.utils.comum.UExpression;
import gm.utils.comum.UList;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class CriterioSetAlias {
	
	private static Atributo notExpressionAtributoCriterion = ListAtributos.get(NotExpression.class).get("criterion"); 

	public static void set(Criteria c, ListString aliases, List<Order> orderBy) {

		ListString aliasesInterno = new ListString();
		List<Object> expressions = new ArrayList<>();
		expressions.addAll(getExpressions(c));
		
		if (!UList.isEmpty(orderBy)) {
			expressions.addAll(orderBy);
		}
		
		for (Object expression : expressions) {
			
			if (expression instanceof NotExpression) {
				expression = notExpressionAtributoCriterion.get(expression);
			}
			
			String campo = UExpression.getString("propertyName", expression);
			if (!campo.contains(".")) {
				continue;
			}
			ListString campos = ListString.byDelimiter(campo, ".");
			campos.removeLast();
			campo = "." + campo + ".";
			String hierarquia = "";
			for (String s : campos) {
				s = UString.primeiraMinuscula(s);
				String alias = UString.primeiraMaiuscula(s);

				campo = campo.replace("." + s + ".", "." + alias + ".");

				if (!hierarquia.isEmpty()) {
					s = hierarquia + "." + s;
				}

				hierarquia += alias;

				if (aliasesInterno.contains(hierarquia)) {
					continue;
				}

				aliasesInterno.add(hierarquia);
				aliases.addIfNotContains(hierarquia);
				c.createAlias(s, hierarquia, JoinType.LEFT_OUTER_JOIN);

			}
			campo = campo.substring(0, campo.length() - 1);
			campo = UString.afterLast(campo, ".");
			campo = hierarquia + "." + campo;
			UExpression.setValue(expression, "propertyName", campo);
		}
		
	}
	
	public static String addAlias(ListString superAliases, String campo, Criteria c) {
		if (!campo.contains(".")) {
			return campo;
		}
		ListString aliases = new ListString();
		ListString campos = ListString.byDelimiter(campo, ".");
		campos.removeLast();
		campo = "." + campo + ".";
		String hierarquia = "";
		for (String s : campos) {
			s = UString.primeiraMinuscula(s);
			String alias = UString.primeiraMaiuscula(s);

			campo = campo.replace("." + s + ".", "." + alias + ".");

			if (!hierarquia.isEmpty()) {
				s = hierarquia + "." + s;
			}
			hierarquia += alias;
			if (aliases.contains(hierarquia)) {
				continue;
			}
			aliases.add(hierarquia);
			boolean exists = superAliases.contains(hierarquia);
			if (!exists) {
				c.createAlias(s, hierarquia, JoinType.LEFT_OUTER_JOIN);
			}
		}
		campo = campo.substring(0, campo.length() - 1);
		campo = UString.afterLast(campo, ".");
		campo = hierarquia + "." + campo;
		return campo;		
	}	

	protected static List<Criterion> getExpressions(Criteria c) {
		@SuppressWarnings("unchecked")
		List<CriterionEntry> list = (List<CriterionEntry>) UExpression.getValue("criterionEntries", c);
		List<Criterion> expressions = new ArrayList<>();
		for (CriterionEntry entry : list) {
			Object criterion = UExpression.getValue("criterion", entry);
			Criterio.add(expressions, criterion);
		}
		return expressions;
	}

	public static String putAliasInEL(String campo) {
		if (!campo.contains(".")) {
			return campo;
		}
		ListString campos = ListString.byDelimiter(campo, ".");
		String ultimo = campos.removeLast();
		campo = "";
		for (String string : campos) {
			campo += UString.primeiraMaiuscula(string) + ".";
		}
		campo += ultimo;
		return campo;
	}
		
}
