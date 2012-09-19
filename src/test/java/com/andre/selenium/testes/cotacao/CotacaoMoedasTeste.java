package com.andre.selenium.testes.cotacao;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import com.andre.selenium.SeleniumTest;
import com.andre.selenium.dominio.Moeda;
import com.andre.selenium.telas.cotacao.TelaCotacoesUOL;
import com.andre.selenium.telas.cotacao.TelaHistoricoMoeda;

public class CotacaoMoedasTeste extends SeleniumTest {

	TelaCotacoesUOL telaCotacoes;

	@Before
	public void prepararCotacoes() {
		this.telaCotacoes = new TelaCotacoesUOL(driver);
		this.telaCotacoes.abrir();
	}

	@Test
	public void testeCotacaoDiariaDolarPesoEuro() throws ParseException {
		Moeda peso = telaCotacoes.consultarCotacaoPesoArgentino();
		Moeda dolar = telaCotacoes.consultarCotacaoDolarComercial();
		Moeda euro = telaCotacoes.consultarCotacaoEuro();
		assertTrue(euroCotacaoMaisAlta(peso, dolar, euro));
	}

	private boolean euroCotacaoMaisAlta(Moeda peso, Moeda dolar, Moeda euro)
			throws ParseException {

		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		float vendaPeso = nf.parse(peso.getVenda()).floatValue();
		float vendaDolar = nf.parse(dolar.getVenda()).floatValue();
		float vendaEuro = nf.parse(euro.getVenda()).floatValue();

		if ((vendaEuro > vendaPeso) && (vendaEuro > vendaDolar))
			return true;
		else
			return false;
	}

	@Test
	public void testeCotacaoMaisAltaDolarUltimos20dias() {
		telaCotacoes.consultarCotacaoDolarComercial();
		TelaHistoricoMoeda historicoMoeda = new TelaHistoricoMoeda(driver);
		historicoMoeda.qualCotacaoDolarMaisAltaVenda();

	}
}
