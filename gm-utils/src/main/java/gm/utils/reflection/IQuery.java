package gm.utils.reflection;

import java.util.List;

public interface IQuery {
	List<Object[]> selectArray(String sql);
}
