package com.cleverton.longmethoddetector.model;

public class InformacoesMetodoModel {
	
	private int linhaInicial;
	private int numeroLinhas;
	private String diretorioDaClasse;
	private String nomeMetodo;
	
	public InformacoesMetodoModel() {
	}
	
	public InformacoesMetodoModel(int linhaInicial, int numeroLinhas, String diretorioDaClasse, 
			String nomeMetodo) {
		this.linhaInicial = linhaInicial;
		this.numeroLinhas = numeroLinhas;
		this.diretorioDaClasse = diretorioDaClasse;
		this.nomeMetodo = nomeMetodo;
	}

	public int getLinhaInicial() {
		return linhaInicial;
	}

	public void setLinhaInicial(int linhaInicial) {
		this.linhaInicial = linhaInicial;
	}

	public int getNumeroLinhas() {
		return numeroLinhas;
	}

	public void setNumeroLinhas(int numeroLinhas) {
		this.numeroLinhas = numeroLinhas;
	}

	public String getDiretorioDaClasse() {
		return diretorioDaClasse;
	}

	public void setDiretorioDaClasse(String diretorioDaClasse) {
		this.diretorioDaClasse = diretorioDaClasse;
	}

	public String getNomeMetodo() {
		return nomeMetodo;
	}

	public void setNomeMetodo(String nomeMetodo) {
		this.nomeMetodo = nomeMetodo;
	}
}
