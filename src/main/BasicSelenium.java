package main;

import java.time.Duration;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasicSelenium {
	private static Logger logger = Logger.getLogger(BasicSelenium.class);
	static WebDriver driver = null;

	public static void main(String[] args) throws Exception {

		try {
			String root = System.getProperty("user.dir");
			String filepath = "\\driver\\chromedriver-97.exe";

			System.setProperty("webdriver.chrome.driver", root + filepath);

			ChromeOptions options = new ChromeOptions();
			options.addArguments("--start-maximized");
			options.addArguments("incognito");
			driver = new ChromeDriver(options);
			driver.get("http://www.google.com");

			// First Page
			WebDriverWait wait10 = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebDriverWait wait25 = new WebDriverWait(driver, Duration.ofSeconds(25));
			wait10.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			WebElement searchBox = driver.findElement(By.name("q"));
			String searchText = "เตลิด puimekster";
			searchBox.sendKeys(searchText);
			waitForElementValueEqual(searchBox, searchText, "value");
			searchBox.sendKeys(Keys.ENTER);

			// Second Page
			wait10.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@id=\"tsf\"]/div[1]/div[1]/div[2]/div[1]/div/div[2]/input")));
			List<WebElement> menuList = driver.findElements(By.xpath("//*[@id=\"hdtb-msb\"]/div[1]/div/div"));
			WebElement videoMenu = null;
			for (WebElement menu : menuList) {
				if (menu.getText() != null && ("วิดีโอ".equals(menu.getText()) || "Videos".equals(menu.getText()))) {
					videoMenu = menu;
				}
			}
			if(videoMenu != null) {
				videoMenu.click();
			}

			// Third Page
			wait10.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"rso\"]/div")));
			List<WebElement> orderList = driver.findElements(By.xpath("//*[@id=\"rso\"]/div"));
			if (!orderList.isEmpty()) {
				orderList.get(0).findElement(By.xpath(".//div/div/div[1]/a")).click();
			}

			// Fourth Page
			wait25.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"movie_player\"]/div")));
			List<WebElement> videoContentList = driver.findElements(By.xpath("//*[@id=\"movie_player\"]/div"));

			WebElement playButton = null;
			for (WebElement webElement : videoContentList) {
				if ("ytp-cued-thumbnail-overlay".equals(webElement.getAttribute("class"))) {
					playButton = webElement.findElement(By.xpath(".//button"));
				}
			}
			if(playButton != null) {
				playButton.click();
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		} finally {
			Runtime.getRuntime().exec("taskkill /im chromedriver-97.exe /f");
		}
	}

	public static void waitForElementValueEqual(WebElement webElement, String textCompare, String attributeName) {
		Wait<WebElement> wait = new FluentWait<>(webElement);
		wait.until(element -> {
			String value = element.getAttribute(attributeName);
			return textCompare.equals(value);
		});
	}

}
