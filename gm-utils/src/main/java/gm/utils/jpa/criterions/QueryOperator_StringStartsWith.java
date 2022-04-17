package gm.utils.jpa.criterions;

public class QueryOperator_StringStartsWith extends QueryOperator_String {
	
	public QueryOperator_StringStartsWith(String campo, String value) {
		super(campo, value);
	}

	@Override
	protected boolean before() {
		return false;
	}

	@Override
	protected boolean after() {
		return true;
	}
	
}
