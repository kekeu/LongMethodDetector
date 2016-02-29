package com.cleverton.longmethoddetector.model;

public class InformacoesMetodo {
	
	private int linhaInicial;
	private int numeroLinhas;
	private String diretorioDaClasse;
	
	public InformacoesMetodo() {
	}
	
	public InformacoesMetodo(int linhaInicial, int numeroLinhas, String diretorioDaClasse) {
		this.linhaInicial = linhaInicial;
		this.numeroLinhas = numeroLinhas;
		this.diretorioDaClasse = diretorioDaClasse;
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
}
