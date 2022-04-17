package gm.utils.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import gm.utils.comum.USystem;
import gm.utils.robo.Robo;
import lombok.Setter;

public class Selenium {

	static {
//        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        System.setProperty("webdriver.chrome.driver", "/opt/desen/gm/cs2019/gm-utils/src/main/resources/chromedriver83");
	}

	@Setter private boolean closed = false;
	@Setter private boolean visible = true;
	@Setter private boolean incognito = true;
	private WebDriver driver;
	private long ultimaAtividade = 0;

	public void fecharSeInativo() {
		USystem.setTimeout(() -> {
			if (closed) {
				return;
			} else if (System.currentTimeMillis() - ultimaAtividade > 15000) {
				quit();
			} else {
				fecharSeInativo();
			}
		}, 1000);
	}
	
	private void setUltimaAtividade() {
		ultimaAtividade = System.currentTimeMillis();
	}
	
	private void esperar() {
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(webDriver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete"));
	}
	
	public Selenium start() {
    	ChromeOptions options = new ChromeOptions();
    	if (!visible) options.addArguments("--headless");
    	if (!incognito) options.addArguments("--incognito");
    	
    	options.addArguments("start-maximized");
//    	options.addArguments("user-data-dir=/home/gamarra/.config/google-chrome");
    	
//    	chromeOptions.addArguments("--no-sandbox");
		this.driver = new ChromeDriver(options);
//		driver.manage().window().setPosition(new Point(0,0));
//		driver.manage().window().maximize();
		fecharSeInativo();
		return this;
	}
	
	public void get(String url) {
		setUltimaAtividade();
		driver.get(url);
		esperar();
	}
	
	private WebElement by(By by) {
		setUltimaAtividade();
		return driver.findElement(by);
	}
	private List<WebElement> bys(By by) {
		setUltimaAtividade();
		return driver.findElements(by);
	}
	public List<WebElement> bysClass(String s) {
		return bys(By.className(s));
	}
	public WebElement byId(String s) {
		return by(By.id(s));
	}
	public WebElement byName(String s) {
		return by(By.name(s));
	}
	public WebElement byProperty(String tag, String property, String s) {
		return by(By.xpath("//"+tag+"[contains(@"+property+",'"+s+"')]"));
	}
	public List<WebElement> bysProperty(String tag, String property, String s) {
		return bys(By.xpath("//"+tag+"[contains(@"+property+",'"+s+"')]"));
	}
	public WebElement byTitle(String tag, String s) {
		return by(By.xpath("//"+tag+"[contains(@title,'"+s+"')]"));
	}
	public WebElement byText(String tag, String s) {
		return by(By.xpath("//"+tag+"[contains(text(),'"+s+"')]"));
	}
	public List<WebElement> classe(String s) {
		return bys(By.className(s));
	}
	private int margemLeftSO() {
		return 72;
	}
	private int margemTopBrowser() {
		return 157;
	}
	public void mouseMove(WebElement o) {
		Point location = o.getLocation();
		Robo.move(location.x + 10 + margemLeftSO(), location.y + 10 + margemTopBrowser());			
	}
	
	public void clickNative(WebElement o) {
		setUltimaAtividade();
		mouseMove(o);
		Robo.click();
		esperar();
	}

	public void click(WebElement o) {
		setUltimaAtividade();
		try {
			o.click();
			esperar();
		} catch (Exception e) {
			clickNative(o);
		}
	}

	public void select(WebElement o, String s) {
		setUltimaAtividade();

		try {
			new Select(o).selectByVisibleText(s);
		} catch (Exception e) {
			WebElement o2 = byTitle("span", s);
			if (o2 != null) {
				click(o2);
			} else {
				throw new RuntimeException("nao encontrado element: " + s);
			}
		}
		esperar();
	}

	public void tinymce(String s) {
		((JavascriptExecutor) driver).executeScript("tinyMCE.activeEditor.setContent('"+s+"')");
	}

	public void quit() {
		closed = true;
		try {
			driver.close();
		} catch (Exception e) {}
		try {
			driver.quit();
		} catch (Exception e) {}
	}
	
}
