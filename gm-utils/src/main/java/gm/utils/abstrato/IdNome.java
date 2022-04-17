package gm.utils.abstrato;
import gm.utils.number.UInteger;
import gm.utils.string.UString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class IdNome {
	
	private Integer id;
	private String nome;
	
	public String getText() {
		return nome;
	}
	public String getDescricao() {
		return nome;
	}
	public void setDescricao(String value) {
		if (value != null) {
			setNome(value);
		}
	}
	public void setText(String value) {
		setDescricao(value);
	}
	public String getCodigo() {
		return UString.toString(getId());
	}
	public void setCodigo(String value) {
		if (UInteger.isInt(value)) {
			setId(UInteger.toInt(value));
		}
	}
}
