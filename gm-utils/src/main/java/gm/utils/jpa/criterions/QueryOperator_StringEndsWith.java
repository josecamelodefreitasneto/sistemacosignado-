package gm.utils.jpa.criterions;

public class QueryOperator_StringEndsWith extends QueryOperator_String {
	
	public QueryOperator_StringEndsWith(String campo, String value) {
		super(campo, value);
	}

	@Override
	protected boolean before() {
		return true;
	}

	@Override
	protected boolean after() {
		return false;
	}

}
