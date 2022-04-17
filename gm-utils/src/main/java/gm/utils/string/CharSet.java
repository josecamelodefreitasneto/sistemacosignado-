package gm.utils.string;

import lombok.Getter;

public enum CharSet {
	
	UTF8("UTF-8"),
	UTF16("UTF-16"),
	UTF16LE("UTF-16LE"),
	UTF16BE("UTF-16BE"),
	ISO88591("ISO-8859-1"),
	USASCII("US-ASCII"),
	;
	
	@Getter
	private String nome;

	CharSet(String nome){
		this.nome = nome;
	}
}
