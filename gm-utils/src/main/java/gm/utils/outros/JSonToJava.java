package gm.utils.outros;

import gm.utils.string.ListString;
import gm.utils.string.UString;

public class JSonToJava {

	public static void main(String[] args) {
		ListString list = ListString.clipboard();
//		list.eachRemoveBefore("\"", true);
//		list.eachRemoveAfter("\"", true);
		list.trimPlus();
//		list = toStringSqlDeclaration(list);
		list.removeWhites();
		list.print();
		list.toClipboard();
	}
	
	public static ListString toStringSqlDeclaration(String s) {
		ListString list = new ListString();
		list.add(s);
		return toStringSqlDeclaration(list);
	}
	
	public static ListString toStringSqlDeclaration(ListString original) {
		
		original.addLeft(" ");
		original.addRight(" ");
		
		ListString list = new ListString();
		list.add("String sql = \"\"");
		
		for (String s : original) {
			
			if (UString.isEmpty(s)) {
				list.add();
				continue;
			}
			
			s = UString.rtrim(s);
			if (s.contains("--")) {
				s = s.replace("--", "/*");
				s += "*/";
			}
			
			s = s.replace("\t", "  ");
			
			s = "\t+ \" " + s;
			s += "\"";
			
			list.add(s);
			
		}
		list.add(";");
		return list;
	}
}
