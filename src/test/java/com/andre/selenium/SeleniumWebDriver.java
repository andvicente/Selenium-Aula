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
import org.openqa.selenium.support.ui.Select;
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

	/**
	 * Digitar em um campo (antes limpa o campo)
	 * 
	 * @param locator
	 * @param text
	 */
	public void type(String locator, String text) {
		waitForVisible(locator);
		WebElement element = element(locator);
		element.clear();
		element.sendKeys(text);
	}

	public void click(String locator) {
		waitForVisible(locator);
		waitForClickable(locator);
		element(locator).click();
	}

	public void clickByLinkText(String text) {
		elementByLinkText(text).click();
	}

	public String getText(String locator) {
		// waitForElementPresent(locator);
		// waitForVisible(locator);
		return element(locator).getText();
	}

	public String getTextTableCell(int nLinha, int nColuna) {
		return element(locateTableCell(nLinha, nColuna)).getText();
	}

	private String locateTableCell(int nLinha, int nColuna) {
		StringBuilder locator = new StringBuilder().append("table ")
				.append("tr:nth-of-type(").append(nLinha).append(")")
				.append(" > ").append("td:nth-of-type(").append(nColuna)
				.append(")");

		return locator.toString();
	}

	/**
	 * Selecionar valor de um combo-box pelo texto
	 * 
	 * @param locator
	 * @param visibleText
	 */
	public void selectByVisibleText(String locator, String visibleText) {
		waitForClickable(locator);
		new Select(element(locator)).selectByVisibleText(visibleText);
	}

	public void selectByValue(String locator, String value) {
		waitForVisible(locator);
		waitForClickable(locator);
		new Select(element(locator)).selectByValue(value);
	}

	// Wait for something...
	private void waitForClickable(String locator) {
		defaultWait.until(ExpectedConditions.elementToBeClickable(By
				.cssSelector(locator)));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);",
				element(locator));
	}

	public void waitForVisible(final String locator) {
		defaultWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement element = element(locator);
				return element != null ? element.isDisplayed() : false;
			}
		});
	}

	public void waitForElementPresent(final String locator) {
		defaultWait.until(new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				return element(locator);
			}
		});
	}

	public void assertTitle(String title) {
		assertEquals("O titulo da pagina nao é o esperado", title,
				driver.getTitle());
	}

	private WebElement element(String cssLocator) {
		return driver.findElement(By.cssSelector(cssLocator));
	}

	private WebElement elementByLinkText(String texto) {
		return driver.findElement(By.linkText(texto));
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
