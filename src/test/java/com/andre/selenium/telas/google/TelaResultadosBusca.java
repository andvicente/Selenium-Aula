package com.andre.selenium.telas.google;

import org.openqa.selenium.WebDriver;

import com.andre.selenium.SeleniumWebDriver;

public class TelaResultadosBusca {

	private SeleniumWebDriver selenium;

	public TelaResultadosBusca(WebDriver driver) {
		this.selenium = new SeleniumWebDriver(driver);
	}

	public String entrarResultados() {
		selenium.click(localizarResultadoN(1));
		return selenium.getTitle();
	}

	public String localizarResultadoN(int n) {
		return "li:nth-of-type(" + n + ").g a";
	}

}
