package gm.utils.outros;

import gm.utils.string.ListString;

public class ToListString {
	public static void main(String[] args) {
		ListString list = ListString.clipboard();
		list.addLeft("list.add(\"");
		list.addRight("\");");
		list.print();
	}
}
