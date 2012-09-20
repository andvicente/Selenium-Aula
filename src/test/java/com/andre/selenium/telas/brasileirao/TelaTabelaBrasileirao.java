package com.andre.selenium.telas.brasileirao;

import com.andre.selenium.SeleniumWebDriver;

public class TelaTabelaBrasileirao {

	private SeleniumWebDriver selenium;
	private static final String URL = "http://economia.uol.com.br/cotacoes/cambio.jhtm";

	public TelaTabelaBrasileirao(SeleniumWebDriver selenium) {
		this.selenium = selenium;
	}

	public void abrir() {
		selenium.driver.get(URL);
		selenium.assertTitle("Classifica&ccedil;&atilde;o do Campeonato Brasileiro 2012 de Futebol - UOL Esporte");
	}
}
