package gm.utils.selenium.whatsApp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import gm.utils.comum.UConstantes;
import gm.utils.comum.UList;
import gm.utils.comum.USystem;
import gm.utils.date.Data;
import gm.utils.selenium.Selenium;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import lombok.Getter;

@Getter
public class WhatsApp {
	
	private final Selenium browser;
//	String contato = "+55 61 9255-9810";
//	String contato = "Iuri Cooper";
	private String contato = "Fred Cooperforte";
	private WebElement input;
//	https://contacts.google.com/?hl=pt-BR&tab=rC
	private Map<String, String> velhasMensagens = new HashMap<>();
	
	public WhatsApp() {
		this(new Selenium());
	}
	
	public WhatsApp(Selenium browser) {
		this.browser = browser;
		browser.setIncognito(false);
		browser.start();
		browser.get("https://web.whatsapp.com/");
		browser.setClosed(true);
		
		//aguardar a leitura do qrCode
		USystem.sleepSegundos(10);
		
//		while (true) {
//			
//			List<File> allFiles = UFile.getAllFiles("/tmp/WhatsApp");
//			for (File file : allFiles) {
//				ListString list = new ListString().load(file);
//				digitarNomeDoContato();
//				selecionarContato();
//				encontrarDivInput();
//				conversar();
//			}
//			
//			USystem.sleepSegundos(2);	
//		}
		
		
		
//		data-pre-plain-text
//		browser.setClosed(false);

	}
	
	public void setContato(String s) {
		this.contato = s;
		digitarNomeDoContato();
		selecionarContato();
		encontrarDivInput();
		velhasMensagens = getNovasMensagens();
	}
	
	private void conversar() {
		while (true) {
			USystem.sleepSegundos(2);
			Map<String, String> novasMensagens = getNovasMensagens();
			if (!novasMensagens.isEmpty()) {
				for (String key : novasMensagens.keySet()) {
					String msg = novasMensagens.get(key);
					velhasMensagens.put(key, msg);	
					enviarMensagem("Recebi sua mensagem ("+msg+") em " + Data.now().format_dd_mm_yyyy_hh_mm_ss());
				}
			}
		}
		
	}
	
	private Map<String, String> getNovasMensagens() {
		Map<String, String> list = new HashMap<>();
		try {
			List<WebElement> divs = browser.bysProperty("div", "class", "copyable-text");
			for (WebElement div : divs) {
				USystem.sleepMiliSegundos(250);
				String key = UString.trimPlus(div.getAttribute("data-pre-plain-text"));
				if (UString.isEmpty(key) || key.toLowerCase().contains("gamarra")) {
					continue;
				}
				WebElement span1 = div.findElement(By.tagName("span"));
				WebElement span2 = span1.findElement(By.tagName("span"));
				String mensagem = span2.getText();
				key += mensagem;
				if (!velhasMensagens.containsKey(key)) {
					list.put(key, mensagem);
				}
			}
		} catch (Exception e) {
			USystem.sleepMiliSegundos(500);
		}
		return list;
	}

	public void enviarMensagem(String string) {
		input.sendKeys(string);
		WebElement botaoEnviar = browser.byProperty("span", "data-icon", "send");
		browser.click(botaoEnviar);
	}

	private void encontrarDivInput() {
		input = UList.getLast(browser.bysClass("selectable-text"));
	}

	private void selecionarContato() {
		WebElement o = null;
		do {
			try {
				o = browser.byTitle("span", contato);
			} catch (Exception e) {}
		} while (o == null);
		browser.click(o);
	}

	private void digitarNomeDoContato() {
		WebElement o = null;
		do {
			try {
				o = browser.byTitle("input", "Procurar ou come"+UConstantes.cedilha+"ar uma nova conversa");
			} catch (Exception e) {}
		} while (o == null);
		o.sendKeys(contato);
	}

	public static void main(String[] args) {
//		new WhatsApp();
		ListString list = new ListString();
		list.add("+55 61 9255-9810");
//		list.add("Fransuelo");
		list.add("Teste");
	}
	
}
