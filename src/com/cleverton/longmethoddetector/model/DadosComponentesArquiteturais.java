package com.cleverton.longmethoddetector.model;

public class DadosComponentesArquiteturais {
	
	private String extendsClass;
	private String implementsArquitecture;
	private String implementsAPIJava;
	private Double mediana;
	private Double primeiroQuartil;
	private Double terceiroQuartil;
	
	public String getExtendsClass() {
		return extendsClass;
	}
	
	public void setExtendsClass(String extendsClass) {
		this.extendsClass = extendsClass;
	}
	
	public String getImplementsArquitecture() {
		return implementsArquitecture;
	}
	
	public void setImplementsArquitecture(String implementsArquitecture) {
		this.implementsArquitecture = implementsArquitecture;
	}
	
	public String getImplementsAPIJava() {
		return implementsAPIJava;
	}
	
	public void setImplementsAPIJava(String implementsAPIJava) {
		this.implementsAPIJava = implementsAPIJava;
	}
	
	public Double getMediana() {
		return mediana;
	}
	
	public void setMediana(Double mediana) {
		this.mediana = mediana;
	}
	
	public Double getPrimeiroQuartil() {
		return primeiroQuartil;
	}
	
	public void setPrimeiroQuartil(Double primeiroQuartil) {
		this.primeiroQuartil = primeiroQuartil;
	}
	
	public Double getTerceiroQuartil() {
		return terceiroQuartil;
	}
	
	public void setTerceiroQuartil(Double terceiroQuartil) {
		this.terceiroQuartil = terceiroQuartil;
	}

}
