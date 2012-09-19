package com.andre.selenium.dominio;

public class Moeda {

	private String nome;
	private String horario;
	private String compra;
	private String venda;
	private String porcentagemVariacao;
	private String moedaVariacao;
	private String valorMaximo;
	private String valorMinimo;

	public Moeda() {

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getCompra() {
		return compra;
	}

	public void setCompra(String compra) {
		this.compra = compra;
	}

	public String getVenda() {
		return venda;
	}

	public void setVenda(String venda) {
		this.venda = venda;
	}

	public String getPorcentagemVariacao() {
		return porcentagemVariacao;
	}

	public void setPorcentagemVariacao(String porcentagemVariacao) {
		this.porcentagemVariacao = porcentagemVariacao;
	}

	public String getMoedaVariacao() {
		return moedaVariacao;
	}

	public void setMoedaVariacao(String moedaVariacao) {
		this.moedaVariacao = moedaVariacao;
	}

	public String getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(String valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public String getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(String valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

}
