package com.andre.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
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
		waitForElementPresent(locator);
		waitForVisible(locator);
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
		waitForVisible(locator);
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

	public void selectFrame(String id) {
		driver.switchTo().frame(id);
	}

	public void assertText(final String locator, final String value) {
		defaultWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement element = element(locator);
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);",
						element);
				String currentText = element.getText();
				if (currentText == null && value == null) {
					return true;
				}
				return currentText.contains(value);
			}
		});
	}

	public void assertElementPresent(String locator) {
		try {
			assertNotNull("Elemento esperado nao foi encontrado",
					element(locator));
		} catch (NoSuchElementException e) {
			fail("Elemento esperado nao foi encontrado");
		}
	}

	public void assertElementNotPresent(String locator) {
		try {
			assertNull("Elemento nao deveria existir", element(locator));
		} catch (NoSuchElementException e) {

		}
	}

	public void assertVisible(final String locator) {

		defaultWait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement element = element(locator);
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);",
						element);
				return element.isDisplayed();
			}
		});

		try {
			assertTrue("Elemento que deveria estar visivel esta invisivel",
					element(locator).isDisplayed());
		} catch (NoSuchElementException e) {
			fail("Elemento que deveria estar visivel nao existe no codigo da tela");
		} catch (ElementNotVisibleException e) {
			fail("Elemento que deveria estar visivel esta invisivel");
		}
	}

	public void assertNotVisible(String locator) {
		try {
			assertFalse("Elemento que deveria estar invisivel esta visivel",
					element(locator).isDisplayed());
		} catch (NoSuchElementException e) {
			fail("Elemento que deveria estar invisivel nao existe no codigo da tela");
		} catch (ElementNotVisibleException e) {

		}
	}

	public void assertNotEnable(String locator) {
		try {
			assertFalse(
					"Elemento que deveria estar desabilitado esta habilitado",
					element(locator).isEnabled());
		} catch (NoSuchElementException e) {
			fail("Elemento que deveria estar desabilitado nao existe no codigo da tela");
		}

	}

	public void assertTextPresent(String text) {
		assertTrue("O texto do elemento deveria ter sido encontrado na pagina",
				driver.getPageSource().contains(text));
	}

	public void assertTextNotPresent(String text) {
		assertFalse(
				"O texto do elemento nao deveria ter sido encontrado na pagina",
				driver.getPageSource().contains(text));
	}

	public void assertValue(String locator, String value) {
		assertEquals("O valor do elemento nao e' o esperado", value,
				element(locator).getAttribute("value"));
	}

	public void assertSelectedLabel(String locator, String value) {
		Select select = new Select(element(locator));
		assertEquals("O elemento selecionado nao e' o esperado", value, select
				.getFirstSelectedOption().getText());
	}

	public void assertChecked(String locator) {
		assertTrue("O elemento nao esta selecionado", element(locator)
				.isSelected());
	}

	public void assertNotChecked(String locator) {
		assertFalse("O elemento esta selecionado", element(locator)
				.isSelected());
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
