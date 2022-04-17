package gm.utils.selenium.contatosGoogle;

import gm.utils.selenium.Selenium;
import lombok.Getter;

@Getter
public class ContatosGoogle {
	
	private final Selenium browser;
	
	public ContatosGoogle() {
		this(new Selenium());
	}
	
	public ContatosGoogle(Selenium browser) {
		this.browser = browser;
		browser.setIncognito(false);
		browser.start();
//		browser.get("https://contacts.google.com/?hl=pt-BR&tab=rC");
		browser.setClosed(true);
		
//		WebElement input = browser.byProperty("input", "type", "email");
//		input.sendKeys("francisco.gamarra@gmail.com");
//		browser.click(browser.byText("span", "Proxima"));
	}

	public static void main(String[] args) {
		new ContatosGoogle();
	}
	
}
