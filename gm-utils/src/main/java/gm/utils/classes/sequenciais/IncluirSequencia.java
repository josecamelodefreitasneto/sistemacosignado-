package gm.utils.classes.sequenciais;
import gm.utils.classes.UClass;
import gm.utils.comum.ULog;
import gm.utils.number.UInteger;
import gm.utils.number.UNumber;
import gm.utils.string.ListString;
import gm.utils.string.UString;
public class IncluirSequencia {
	private String name;
	private int getIndex(Class<?> para) {
		String s = para.getName();
		String right = UString.right(s, 3);
		name = UString.beforeLast(s, right);
		Integer i = UInteger.toInt(right);
		return i;
	}
	public IncluirSequencia(Class<?> para) {
		int inicio = getIndex(para);
		Class<?> ultima = para;
		int i = inicio;
		while (true) {
			i++;
			Class<?> x = UClass.getClass(name + UNumber.format00(i, 3));
			if (x == null) {
				break;
			} else {
				ultima = x;
			}
		}
		while (i > inicio) {
			exec(ultima, i);
			i--;
			ultima = UClass.getClass(name + UNumber.format00(i-1, 3));
		}
	}
	public static void main(String[] args) {
//		new IncluirSequencia(InstallCore009.class);
	}
	public void exec(Class<?> classe, int para) {
		String name = UString.afterLast(this.name, ".");
		String nomeDe = name + UNumber.format00(para-1, 3);
		String nomePara = name + UNumber.format00(para, 3);
		String fileName = UClass.javaFileName(classe);
		fileName = fileName.replace(nomeDe, nomePara);
		ULog.debug( nomeDe + " >> " + fileName );
		ListString list = ListString.loadClass(classe);
		list.replaceTexto("public class " + nomeDe + " ", "public class " + nomePara + " ");
		list.save(fileName);
	}
}
