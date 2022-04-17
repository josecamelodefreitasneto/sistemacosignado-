package gm.utils.outros;

import java.util.ArrayList;
import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.comum.UType;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.ListAtributos;
import gm.utils.string.StringBox;
import gm.utils.string.UString;

public class Desconstructor {

	public static void main(String[] args) {
		JcClasse jc = new JcClasse("/opt/desen/gm/cs2019/gm-utils/src/main/java/", "gm.utils.outros", "Exemplo");
		StringBox box = new StringBox();
		box.set("teste");
		new Desconstructor(box, jc);
	}
	
	
	private int count = 0;
	private final JcMetodo metodo;
	private final JcClasse jc;
	
	public Desconstructor(Object o, JcClasse jc) {
		this.jc = jc;
		this.metodo = jc.main();
		exec(o);
		jc.save();
	}
	public Desconstructor(List<?> lst, JcClasse jc) {
		this.jc = jc;
		this.metodo = jc.main();
		Class<?> classe = UClass.getClass(lst.get(0));
		jc.addImport(List.class);
		jc.addImport(ArrayList.class);
		add("List<"+classe.getSimpleName()+"> list = new ArrayList<>();");
		for (Object o : lst) {
			add("list.add(o"+exec(o)+");");	
		}
		jc.save();
	}

	public int exec(Object o) {
		
		Class<?> classe = UClass.getClass(o);
		jc.addImport(classe);
		String sn = classe.getSimpleName();
		
		final int id = count++; 
		
		String no = "o"+id;
		add(sn + " "+no+" =  new " + sn + "();");
		
		Atributos as = ListAtributos.get(o);
		as.removeStatics();
		if (as.getId() != null) {
			as.add(0, as.getId());
		}
		for (Atributo a : as) {
			Object value = a.get(o);
			if (value == null) {
				continue;
			}
			
			if (value instanceof String) {
				value = "\"" + value + "\"";
			}
			
			if (UType.isArray(value)) {
				throw new RuntimeException("???");
			} else if (a.isList()) {
				jc.addImport(ArrayList.class);
				add(no+".set" + a.upperNome() + "(new ArrayList<>());");
				List<?> list = (List<?>) value;
				if (!list.isEmpty()) {
					Object first = list.get(0);
					if (UType.isPrimitiva(first)) {
						for (Object obj : list) {
							if (obj != null) {
								add(no+".get" + a.upperNome() + "().add("+UString.toString(obj)+");");
							}
						}
					} else {
						jc.addImport(UClass.getClass(first));
						for (Object obj : list) {
							if (obj != null) {
								add(no+".get" + a.upperNome() + "().add(o"+exec(obj)+");");
							}
						}
					}
				}
				
			} else if (a.isPrimitivo()) {
				add(no+".set" + a.upperNome() + "("+value+");");
			} else {
				add(no+".set" + a.upperNome() + "(o"+exec(value)+");");
			}
		}
		
		return id;
	}

	private void add(String s) {
		metodo.add(s);
	}
	
}
