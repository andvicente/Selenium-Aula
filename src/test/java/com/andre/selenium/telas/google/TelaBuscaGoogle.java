package com.andre.selenium.telas.google;

import org.openqa.selenium.WebDriver;

import com.andre.selenium.SeleniumWebDriver;

public class TelaBuscaGoogle {

	private SeleniumWebDriver selenium;
	private static final String URL = "http://www.google.com.br";

	public TelaBuscaGoogle(WebDriver driver) {
		this.selenium = new SeleniumWebDriver(driver);
	}

	public void abrir() {
		selenium.driver.get(URL);
	}

	public TelaResultadosBusca buscaGoogle(String busca) {
		selenium.type("input[name='q']", busca);
		selenium.click("button#gbqfb");
		selenium.takeScreenshot();
		return new TelaResultadosBusca(selenium.driver);

	}
}
