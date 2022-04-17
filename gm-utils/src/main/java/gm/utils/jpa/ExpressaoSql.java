package gm.utils.jpa;

import lombok.Getter;

@Getter
public class ExpressaoSql {
	private String value;
	public ExpressaoSql(String value) {
		this.value = value;
	}
}
