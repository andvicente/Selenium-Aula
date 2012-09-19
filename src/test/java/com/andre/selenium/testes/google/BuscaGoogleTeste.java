package com.andre.selenium.testes.google;

import org.junit.Before;
import org.junit.Test;

import com.andre.selenium.SeleniumTest;
import com.andre.selenium.telas.google.TelaBuscaGoogle;

public class BuscaGoogleTeste extends SeleniumTest {

	@Before
	public void antesTestes() {

	}

	@Test
	public void testandoBuscaAndreAbeVicente() {
		TelaBuscaGoogle telaGoogle = new TelaBuscaGoogle(driver);
		telaGoogle.abrir();
		String titulo = telaGoogle.buscaGoogle("André Abe Vicente")
				.entrarResultados();
		assertEquals("André Abe Vicente - Brasil | LinkedIn", titulo);
	}

}
