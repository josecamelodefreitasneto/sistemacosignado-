package gm.utils.abstrato;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SimpleIdObject implements IdObject{
	private Integer id;
	private String text;
	public SimpleIdObject() {}
	public SimpleIdObject(Integer id, String text) {
		setId(id);
		setText(text);
	}
}
