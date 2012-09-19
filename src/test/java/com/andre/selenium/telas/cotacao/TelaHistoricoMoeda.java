package com.andre.selenium.telas.cotacao;

import java.util.ArrayList;
import java.util.Collections;

import org.openqa.selenium.WebDriver;

import com.andre.selenium.SeleniumWebDriver;

public class TelaHistoricoMoeda {

	private SeleniumWebDriver selenium;

	public TelaHistoricoMoeda(WebDriver driver) {
		this.selenium = new SeleniumWebDriver(driver);
	}

	public String qualCotacaoDolarMaisAltaVenda() {
		selenium.clickByLinkText("Histórico da moeda");
		ArrayList list = new ArrayList();

		for (int i = 2; i <= 20; i++) {
			list.add(getCelulaTabelaHistoricoMoeda(i, 3));
		}
		Collections.sort(list);

		return null;

	}

	private String getCelulaTabelaHistoricoMoeda(int nLinha, int nColuna) {
		return selenium.getText(localizarCelulaTabelaHistoricoMoeda(nLinha,
				nColuna));
	}

	private String localizarCelulaTabelaHistoricoMoeda(int nLinha, int nColuna) {
		return "table.tabela-historica tbody tr:nth-of-type(" + nLinha
				+ ") td.venda";
	}

}
