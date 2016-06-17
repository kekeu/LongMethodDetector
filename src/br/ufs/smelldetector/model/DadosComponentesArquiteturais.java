package br.ufs.smelldetector.model;

public class DadosComponentesArquiteturais {
	
	private String extendsClass;
	private String implementsArquitecture;
	private String implementsAPIJava;
	private int mediana;
	private int primeiroQuartil;
	private int terceiroQuartil;
	
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
	
	public int getMediana() {
		return mediana;
	}
	
	public void setMediana(int mediana) {
		this.mediana = mediana;
	}
	
	public int getPrimeiroQuartil() {
		return primeiroQuartil;
	}
	
	public void setPrimeiroQuartil(int primeiroQuartil) {
		this.primeiroQuartil = primeiroQuartil;
	}
	
	public int getTerceiroQuartil() {
		return terceiroQuartil;
	}
	
	public void setTerceiroQuartil(int terceiroQuartil) {
		this.terceiroQuartil = terceiroQuartil;
	}

}
