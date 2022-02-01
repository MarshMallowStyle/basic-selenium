package main;

import java.time.Duration;

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
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
			WebElement searchBox = driver.findElement(By.name("q"));
			searchBox.sendKeys("Fly Away feat. Anjulie - TheFatRat");
			waitForElementValueEqual(searchBox, "Fly Away feat. Anjulie - TheFatRat", "value");
			searchBox.sendKeys(Keys.ENTER);

			// Second Page
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"hdtb-msb\"]/div[1]/div/div[2]/a")));
			WebElement videlLink = driver.findElement(By.xpath("//*[@id=\"hdtb-msb\"]/div[1]/div/div[2]/a"));
			videlLink.click();

			// Third Page
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//*[@id=\"rso\"]/div[2]/div/div/div[1]/a")));
			WebElement firstVideo = driver.findElement(By.xpath("//*[@id=\"rso\"]/div[2]/div/div/div[1]/a"));
			firstVideo.click();

			// Fourth Page
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"movie_player\"]/div[5]/button")));
			WebElement playButton = driver.findElement(By.xpath("//*[@id=\"movie_player\"]/div[5]/button"));
			playButton.click();

		} catch (Exception e) {
			throw e;
		} finally {
			Thread.sleep(5500);
			driver.quit();
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
