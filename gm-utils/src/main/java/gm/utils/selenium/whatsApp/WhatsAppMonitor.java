package gm.utils.selenium.whatsApp;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import gm.utils.comum.USystem;
import gm.utils.files.UFile;
import gm.utils.outros.UThread;
import gm.utils.string.ListString;

public class WhatsAppMonitor {
	
private static final String PATH = "/tmp/whatsApp/";

	private final WhatsApp whatsApp = new WhatsApp();
	
	static {
		UFile.criaDiretorio(PATH);
	}
	
	private static long ultimoEnvio = 0;
	
	public WhatsAppMonitor() {
		
		whatsApp.getBrowser().setClosed(true);
		
		List<File> files = UFile.getAllFiles(PATH);
		
		if (!files.isEmpty()) {
			USystem.sleepSegundos(15);//para ler o qrcode
			for (File file : files) exec(file);
		}
		
		manterAtividade();
		
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();
			
			UFile.criaDiretorio(PATH);
			
			Path path = Paths.get(PATH);
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
			
			 WatchKey key;
		        while ((key = watchService.take()) != null) {
		            for (WatchEvent<?> event : key.pollEvents()) {
		            	exec(new File(PATH + event.context()));
		            }
		            key.reset();
		        }			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	private void manterAtividade() {
		UThread.exec(() -> {
			while (true) {
				if (System.currentTimeMillis() - ultimoEnvio > 300_000) {
					send("+55 61 9255-9810", "Mantendo Atividade " + count);
				} else {
					USystem.sleepMinutos(1);
				}
			}
		});
	}

	private void exec(File file) {
		ListString list = new ListString().load(file);
    	whatsApp.setContato(list.remove(0));
    	whatsApp.enviarMensagem(list.remove(0));
    	UFile.delete(file);
	}
	
	private static int count = 0;
	
	public synchronized static void send(String contato, String message) {
		ListString.array(contato, message).save(PATH + (count) + ".txt").print();
		count++;
		ultimoEnvio = System.currentTimeMillis();
	}

	public static void main(String[] args) {
		new WhatsAppMonitor();
//		USystem.sleepSegundos(15);
//		send("+55 61 9255-9810", "Teste");
//		send("Mauricio Gamarra", "Mensagem de Teste enviada automaticamente pelo Boot. :p " + count);
	}
	
}
