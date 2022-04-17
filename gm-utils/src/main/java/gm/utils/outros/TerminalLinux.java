package gm.utils.outros;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import gm.utils.exception.UException;
import gm.utils.lambda.FTT;
import gm.utils.number.UInteger;
import gm.utils.string.ListString;
import gm.utils.string.UString;

public class TerminalLinux {
	
	private String path;
	
	private ListString exec(String s) {
		
		try {
			
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.directory(new File(path));
//			processBuilder.command("bash", "-c", "ls /home/");
			processBuilder.command("bash", "-c", s);
//			/opt/desen/gm/cs2019/extras/consolidate/consolidate-front
			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			ListString list = new ListString();
			
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				list.add(line);
			}

			int exitCode = process.waitFor();
			
			if (exitCode > 0) {
				throw new RuntimeException("Exited with error code : " + exitCode);
			}
			
			return list;
			
		} catch (Exception e) {
			throw UException.runtime(e);
		}
		
	}
	
	public void cd(String path) {
		this.path = path;
	}
	
	public void kill(int id) {
		exec("kill -9 " + id);
	}
	
	public void kill(String s) {
		s = UString.afterFirst(s, " ").trim();
		s = UString.beforeFirst(s, " ").trim();
		int id = UInteger.toInt(s);
		kill(id);
	}
	
	public void killGrep(String word, FTT<Boolean, String> func) {
		String x = "ps -ef | grep " + word;
		ListString itens = exec(x);
		itens.removeIf(s -> s.endsWith(x));
		itens.removeIf(s -> s.endsWith("grep " + word));
		for (String s : itens) {
			if (func.call(s)) {
				kill(s);
			}
		}
		
	}
	
	public static void main(String[] args) {
		TerminalLinux terminal = new TerminalLinux();
//		terminal.cd("/opt/desen/gm/cs2019/extras/consolidate/consolidate-front");
		terminal.cd("/opt/desen/gm/cs2019/extras/consolidate/consolidate-back");
		terminal.exec("mvn clean install -DskipTests");
//		terminal.exec("git pull");
//		terminal.exec("npm install").print();
//		System.out.println("----");
//		itens.print();
//		terminal.killGrep("node", s -> s.contains("node_modules") || s.contains("/usr/share/code") || s.contains("vscode"));
//		terminal.killGrep("vscode", s -> true);
		
	}
	
}
