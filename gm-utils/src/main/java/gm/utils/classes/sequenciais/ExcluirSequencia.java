package gm.utils.classes.sequenciais;
import gm.utils.classes.UClass;
import gm.utils.comum.ULog;
import gm.utils.files.UFile;
import gm.utils.number.UInteger;
import gm.utils.number.UNumber;
import gm.utils.string.ListString;
import gm.utils.string.UString;
public class ExcluirSequencia {
	public static void exec(Class<?> para) {
		String name = para.getName();
		String right = UString.right(name, 3);
		name = UString.beforeLast(name, right);
		ULog.debug(right);
		ULog.debug(name);
		Integer i = UInteger.toInt(right);
		do {
			i++;
			Class<?> de = UClass.getClass(name + UNumber.format00(i, 3));
			if (de == null) {
				UFile.delete( UClass.javaFileName(para) );
				break;
			}
			exec(de, para);
			para = de;
			} while (true);
		}
//		public static void main(String[] args) {
//			UConfigFwConstructor.config();
//			exec(Install003.class);
//		}
		private static void exec(Class<?> de, Class<?> para) {
			ListString list = ListString.loadClass(de);
			list.replaceTexto("public class " + de.getSimpleName() + " ", "public class " + para.getSimpleName() + " ");
			list.replaceTexto(" " + de.getSimpleName() + "(", " " + para.getSimpleName() + "(");
			list.save(para);
		}
	}
