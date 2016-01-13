package com.cleverton.longmethoddetector.model;

public class ProblemaModel {

	private String pastaDeOrigem;
	private String nomeClasse;
	private String nomeMetodo;
	private int numeroDaLinhaInicial;
	
	public ProblemaModel(){
		
	}
	
	public ProblemaModel(String pastaDeOrigem, String nomeClasse, 
			String nomeMetodo, int numeroDaLinhaInicial) {
		this.pastaDeOrigem = pastaDeOrigem;
		this.nomeClasse = nomeClasse;
		this.nomeMetodo = nomeMetodo;
		this.numeroDaLinhaInicial = numeroDaLinhaInicial;
	}

	public String getPastaDeOrigem() {
		return pastaDeOrigem;
	}

	public void setPastaDeOrigem(String pastaDeOrigem) {
		this.pastaDeOrigem = pastaDeOrigem;
	}

	public String getNomeClasse() {
		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}

	public int getNumeroDaLinhaInicial() {
		return numeroDaLinhaInicial;
	}

	public void setNumeroDaLinhaInicial(int numeroDaLinhaInicial) {
		this.numeroDaLinhaInicial = numeroDaLinhaInicial;
	}
	
}
