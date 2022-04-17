package gm.utils.jpa.criterions;

public class QueryOperator_StringContains extends QueryOperator_String {
	
	public QueryOperator_StringContains(String campo, String value) {
		super(campo, value);
	}

	@Override
	protected boolean before() {
		return true;
	}

	@Override
	protected boolean after() {
		return true;
	}

}
