package gm.utils.jpa;

public interface IEntityHierarquica<T extends IEntityHierarquica<T>> {

	T getPai();
	void setPai(T o);
	
	String getHierarquia();
	void setHierarquia(String s);

	String getBreadCrumb();
	void setBreadCrumb(String s);
	
}
