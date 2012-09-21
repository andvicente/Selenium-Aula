package com.andre.selenium.telas.cotacao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.andre.selenium.SeleniumWebDriver;

public class TelaHistoricoMoeda {

	private SeleniumWebDriver selenium;
	private SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");

	public TelaHistoricoMoeda(SeleniumWebDriver selenium) {
		this.selenium = selenium;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getCotacaoMoedaMaisBaixaMaisAlta() {
		selenium.clickByLinkText(localizarHistoricoMoeda());
		selenium.selectFrame(localizarFrameHistorico());

		selenium.type(localizarDataInicial(), "01/01/2012");
		Date hoje = new Date();
		String diaHoje = formatadorData.format(hoje);
		selenium.type(localizarDataFinal(), diaHoje);
		selenium.click(localizarBotaoOKIntervaloData());
		selenium.selectByVisibleText(localizarComboQtdeLinhas(), "50 Linhas");

		Map<String, String> cotacoes = getCotacoesMoedaHistoricoUltimos50dias();
		Map<String, String> cotacoesOrdenadas = sortByComparator(cotacoes);
		return getCotacaoMaisAltaMaisBaixa(cotacoesOrdenadas);

	}

	private Map<String, String> getCotacoesMoedaHistoricoUltimos50dias() {
		Map<String, String> cotacoes = new HashMap<String, String>();

		for (int i = 1; i <= 50; i++) {
			String data = getCelulaTabelaHistoricoMoeda(i, 1);
			String venda = getCelulaTabelaHistoricoMoeda(i, 3);
			cotacoes.put(data, venda);
			System.out.println(i + " Cotação: " + data + " " + venda);
		}
		return cotacoes;
	}

	/**
	 * Retorna a cotação mais baixa e mais alta de uma moeda e sua respectivas
	 * datas.
	 * 
	 * @param cotacoesOrdenadas
	 * @return String (Cotacao Mais Baixa e Mais Alta)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList getCotacaoMaisAltaMaisBaixa(
			Map<String, String> cotacoesOrdenadas) {
		int i = 0;
		String cotacaoMaisAlta = "";
		String cotacaoMaisBaixa = "";
		ArrayList cotacaoMaisBaixaAlta = new ArrayList();
		for (Map.Entry entry : cotacoesOrdenadas.entrySet()) {
			if (i == 0)
				cotacaoMaisBaixa = entry.getKey() + " " + entry.getValue();
			if (i == 49)
				cotacaoMaisAlta = entry.getKey() + " " + entry.getValue();
			i++;
		}
		cotacaoMaisBaixaAlta.add(cotacaoMaisBaixa);
		cotacaoMaisBaixaAlta.add(cotacaoMaisAlta);
		return cotacaoMaisBaixaAlta;
	}

	private String getCelulaTabelaHistoricoMoeda(int nLinha, int nColuna) {
		return selenium.getText(localizarCelulaTabelaHistoricoMoeda(nLinha,
				nColuna));
	}

	// MÉTODOS DE LOCALIZAÇÃO DE ELEMENTOS DA TELA

	private String localizarCelulaTabelaHistoricoMoeda(int nLinha, int nColuna) {
		return "table.tabela-historica tbody tr:nth-of-type(" + nLinha
				+ ") td:nth-of-type(" + nColuna + ")";
	}

	private String localizarHistoricoMoeda() {
		return "Histórico da moeda";
	}

	private String localizarFrameHistorico() {
		return "frame-historico";
	}

	private String localizarDataInicial() {
		return "div.consulta input#begin";
	}

	private String localizarDataFinal() {
		return "div.consulta input#end";
	}

	private String localizarBotaoOKIntervaloData() {
		return "input.botao3";
	}

	private String localizarComboQtdeLinhas() {
		return "select#linhas";
	}

	private static Map sortByComparator(Map unsortMap) {

		List list = new LinkedList(unsortMap.entrySet());

		System.out.print("Ordenando Cotações... \n");
		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		System.out.print("FIM Ordenação Cotações... \n");

		// put sorted list into map again
		Map sortedMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
}
