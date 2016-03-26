package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;
import java.util.Collections;

import com.cleverton.longmethoddetector.model.DadosMetodo;

public class MedianaQuartis {
	
	public ArrayList<DadosMetodo> listaOrdemCrescentePorNumeroLinhas(
			ArrayList<DadosMetodo> metodosDesordenados) {
		ArrayList<DadosMetodo> metodosOrdenados = metodosDesordenados;
		Collections.sort(metodosOrdenados, (obj1, obj2) -> {
			DadosMetodo m1 = (DadosMetodo) obj1;
			DadosMetodo m2 = (DadosMetodo) obj2;
			return m1.getNumeroLinhas() < m2.getNumeroLinhas() ? -1 : 
				(m1.getNumeroLinhas() > m2.getNumeroLinhas() ? +1 : 0);
		});
		return metodosOrdenados;
	}
	
	public double calcularMediana(ArrayList<DadosMetodo> metodos) {
		double retorno = 0;
		int numeroElementosLista = metodos.size();
		if (numeroElementosLista % 2 == 0) {
			retorno = (metodos.get(numeroElementosLista/2).getNumeroLinhas() + 
					metodos.get((numeroElementosLista/2) - 1).getNumeroLinhas()) / 2; 
		} else {
			retorno = metodos.get((numeroElementosLista-1)/2).getNumeroLinhas(); 
		}
		return retorno;
	}

	public int primeiroQuartil(ArrayList<DadosMetodo> metodosOrdenados) {
		int posicaoReal = (int) 0.25*(metodosOrdenados.size()+1);
		int posicaoInteira = (int) Math.ceil(0.25*(metodosOrdenados.size()+1));
		if (posicaoInteira == posicaoReal) {
			return metodosOrdenados.get(posicaoInteira - 1).getNumeroLinhas();
		} else {
			return (metodosOrdenados.get(posicaoInteira).getNumeroLinhas() + 
					metodosOrdenados.get(posicaoInteira).getNumeroLinhas() - 1)/2;
		}
	}	
	
	public int terceiroQuartil(ArrayList<DadosMetodo> metodosOrdenados) {
		double posicaoReal = 0.75*(metodosOrdenados.size()+1);
		int posicaoInteira = (int) Math.ceil(0.75*(metodosOrdenados.size()+1));
		if (posicaoInteira == posicaoReal) {
			return metodosOrdenados.get(posicaoInteira - 1).getNumeroLinhas();
		} else {
			return (metodosOrdenados.get(posicaoInteira).getNumeroLinhas() + 
					metodosOrdenados.get(posicaoInteira).getNumeroLinhas() - 1)/2;
		}
	}
	
	public boolean eCompreendidoPrimeiroTerceiroQuartil(int numero, 
			int primeiroQuartil, int terceiroQuartil) {
		if (numero >= primeiroQuartil && numero <= terceiroQuartil) {
			return true;
		}
		return false;
	}
}
