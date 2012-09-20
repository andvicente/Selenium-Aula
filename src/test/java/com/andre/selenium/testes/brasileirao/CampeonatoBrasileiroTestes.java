package com.andre.selenium.testes.brasileirao;

import org.junit.Before;

import com.andre.selenium.SeleniumTest;
import com.andre.selenium.SeleniumWebDriver;

public class CampeonatoBrasileiroTestes extends SeleniumTest {

	SeleniumWebDriver selenium;

	@Before
	public void prepararCotacoes() {
		this.selenium = new SeleniumWebDriver(driver);

	}

}
