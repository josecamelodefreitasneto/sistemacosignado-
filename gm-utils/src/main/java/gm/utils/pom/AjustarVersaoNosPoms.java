package gm.utils.pom;

import java.io.File;
import java.util.List;

import gm.utils.comum.UList;
import gm.utils.files.UFile;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class AjustarVersaoNosPoms {

	public static void main(String[] args) {
		Pom pom = new Pom("/opt/desen/gm/cs2019/gm-utils");
		exec(pom);
	}
	
	public static void exec(Pom pomPrincipal) {
		
		String version = pomPrincipal.getVersion();
		String groupId = pomPrincipal.getGroupId();
		String artifactId = pomPrincipal.getArtifactId();
		
		List<File> list = UFile.getAllFiles("/opt/desen/git/mobile/");
		list.addAll(UFile.getAllFiles("/opt/desen/gm/cs2019"));
		list = UList.filter(list, o -> o.getName().equals("pom.xml"));
		
		String jarVersao = artifactId + "/"+version+"/"+artifactId+"-"+version+".jar";
		
		for (File file : list) {
			Pom pom = new Pom(file);
			pom.setVersion(groupId, artifactId, version);
			pom.save();
			
			String outro = file.toString().replace("pom.xml", ".factorypath");
			if (UFile.exists(outro)) {
				ListString load = new ListString().load(outro);
				String s = load.toString("");
				while (s.contains("/"+artifactId+"-")) {
					s = UString.afterFirst(s, "/"+artifactId+"-");
					String v = UString.beforeFirst(s, ".jar");
					load.replaceTexto(artifactId + "/"+v+"/"+artifactId+"-"+v+".jar", "[[VERSAO]]");
				}
				load.replaceTexto("[[VERSAO]]", jarVersao);
				load.save();
			}
		}
		
	}	
	
}
