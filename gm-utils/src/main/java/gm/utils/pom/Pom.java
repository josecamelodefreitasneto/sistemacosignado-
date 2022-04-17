package gm.utils.pom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gm.utils.comum.UList;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class Pom {

	private ListString original;
	private String TEXTO;
	private File file;
	
	public Pom(File file) {
		this.file = file;
		load();
	}

	private void load() {
		original = new ListString().load(file);
		original.replaceTexto("    ", "\t");
		ListString list = original.copy();
		list.removeTextEntre("<!--", "-->");
		list.trimPlus();
		String s = list.toString(" ").replace("> <", "><");
		s = UString.removeTextoEntreWhile(s, "<!--", "-->");
		
		if (s.contains("</parent>")) {
			String ss = UString.afterFirst(s, "<parent>");
			ss = UString.beforeFirst(ss, "</parent>");
			s = s.replace("<parent>"+ss+"</parent>", "");
		}
		
		TEXTO = s;
	}
	
	public Pom(String path) {
		this(new File(path+"/pom.xml"));
	}
	
	public String getVersion() {
		String s = UString.afterFirst(TEXTO, "<version>");
		s = UString.beforeFirst(s, "</version>");
		return s;
	}
	
	public String getGroupId() {
		String s = UString.afterFirst(TEXTO, "<groupId>");
		s = UString.beforeFirst(s, "</groupId>");
		return s;
	}

	public String getArtifactId() {
		String s = UString.afterFirst(TEXTO, "<artifactId>");
		s = UString.beforeFirst(s, "</artifactId>");
		return s;
	}
	
	public List<PomPlugin> getPlugins() {
		List<PomPlugin> list = new ArrayList<>();
		String s = UString.textoEntreFirst(TEXTO, "<plugins>", "</plugins>");
		while (UString.notEmpty(s)) {
			String ss = UString.textoEntreFirst(s, "<plugin>", "</plugin>");
			s = UString.afterFirst(s, "</plugin>");
			PomPlugin o = new PomPlugin();
			o.groupId = UString.textoEntreFirst(ss, "<groupId>", "</groupId>");
			o.artifactId = UString.textoEntreFirst(ss, "<artifactId>", "</artifactId>");
			o.version = UString.textoEntreFirst(ss, "<version>", "</version>");
			list.add(o);
		}
		return list;
	}
	
	public PomPlugin getPlugin(String groupId, String artifactId) {
		return UList.filterUnique(getPlugins(), o -> o.groupId.equals(groupId) && o.artifactId.equals(artifactId));
	}
	
	public String getVersion(String groupId, String artifactId) {
		String s = "<groupId>"+groupId+"</groupId><artifactId>"+artifactId+"</artifactId><version>";
		s = UString.afterFirst(TEXTO, s);
		if (UString.isEmpty(s)) {
			return null;
		}
		s = UString.beforeFirst(s, "</version>");
		return s;
	}
	public void setVersion(String versao) {
		setVersion(getGroupId(), getArtifactId(), versao);
	}
	public void setVersion(String groupId, String artifactId, String versao) {
		
		String version = getVersion(groupId, artifactId);
		
		if (version == null || versao.equals(version)) {
			return;
		}
		
		ListString velho = new ListString();
		velho.add("<groupId>"+groupId+"</groupId>");
		velho.add("<artifactId>"+artifactId+"</artifactId>");
		velho.add("<version>"+version+"</version>");
		
		ListString novo = new ListString();
		novo.add("<groupId>"+groupId+"</groupId>");
		novo.add("<artifactId>"+artifactId+"</artifactId>");
		novo.add("<version>"+versao+"</version>");
		
		original.replace(velho, novo);
		
		velho = new ListString();
		velho.add("</dependency>");
		velho.add("");
		velho.add("<dependency>");
		
		novo = new ListString();
		novo.add("</dependency>");
		novo.add("<dependency>");
		
		original.replace(velho, novo);
		
	}
	
	public void save() {
		original.save();
		load();
	}
	
}
