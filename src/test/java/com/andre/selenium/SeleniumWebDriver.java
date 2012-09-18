package com.andre.selenium;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWebDriver {

	private static final long DEFAULT_TIME_WAIT = 45;
	public WebDriver driver;
	private Wait<WebDriver> defaultWait;
	private JavascriptExecutor jsExecutor;

	public SeleniumWebDriver(WebDriver driver) {
		this.driver = driver;
		this.jsExecutor = (JavascriptExecutor) driver;
		this.defaultWait = new WebDriverWait(driver, DEFAULT_TIME_WAIT);
	}

	public void type(String locator, String text) {
		waitForVisible(locator);
		WebElement element = element(locator);
		element.clear();
		element.sendKeys(text);
	}

	public void waitForVisible(final String locator) {
		defaultWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement element = element(locator);
				return element != null ? element.isDisplayed() : false;
			}
		});
	}

	public void click(String locator) {
		waitForVisible(locator);
		waitForClickable(locator);
		element(locator).click();
	}

	private void waitForClickable(String locator) {
		defaultWait.until(ExpectedConditions.elementToBeClickable(By
				.cssSelector(locator)));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);",
				element(locator));
	}

	public void assertTitle(String title) {
		assertEquals("O titulo da pagina nao é o esperado", title,
				driver.getTitle());
	}

	private WebElement element(String cssLocator) {
		return driver.findElement(By.cssSelector(cssLocator));
	}

	public void takeScreenshot() {
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File screenshot = ((TakesScreenshot) augmentedDriver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File("./seleniumTeste.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTitle() {
		return this.driver.getTitle();
	}

}
