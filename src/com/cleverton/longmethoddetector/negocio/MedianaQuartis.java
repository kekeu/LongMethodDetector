package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;
import java.util.Collections;

public class MedianaQuartis {
	
	public static ArrayList<Integer> ordernarOrdemCrescente(
			ArrayList<Integer> metodosDesordenados) {
		ArrayList<Integer> metodosOrdenados = metodosDesordenados;
		Collections.sort(metodosOrdenados);
		return metodosOrdenados;
	}
	
	public static int calcularMediana(ArrayList<Integer> metodos) {
		if (metodos.size() == 0) {
			return 0;
		}
		int numeroElementosLista = metodos.size();
		if (numeroElementosLista % 2 == 0) {
			return (metodos.get(numeroElementosLista/2) + 
					metodos.get((numeroElementosLista/2) - 1)) / 2; 
		} else {
			return metodos.get((numeroElementosLista-1)/2); 
		}
		
	}

	public static int primeiroQuartil(ArrayList<Integer> metodosOrdenados) {
		if (metodosOrdenados.size() == 0) {
			return 0;
		}
		double posicaoReal = (metodosOrdenados.size()+1)/4;
		int posicaoInteira = (int)posicaoReal;
		if (posicaoReal == posicaoInteira) {
			return metodosOrdenados.get(posicaoInteira == 0? posicaoInteira : posicaoInteira - 1);
		} else {
			return (metodosOrdenados.get(posicaoInteira) + 
					metodosOrdenados.get(posicaoInteira - 1))/2;
		}
	}	
	
	public static int terceiroQuartil(ArrayList<Integer> metodosOrdenados) {
		if (metodosOrdenados.size() == 0) {
			return 0;
		}
		double posicaoReal = (3*(metodosOrdenados.size()+1))/4;
		int posicaoInteira = (int)posicaoReal;
		if (posicaoReal == posicaoInteira) {
			return metodosOrdenados.get(posicaoInteira - 1);
		} else {
			return (metodosOrdenados.get(posicaoInteira) + 
					metodosOrdenados.get(posicaoInteira - 1))/2;
		}
	}
	
	public static boolean eCompreendidoPrimeiroTerceiroQuartil(int numero, 
			int primeiroQuartil, int terceiroQuartil) {
		if (numero >= primeiroQuartil && numero <= terceiroQuartil) {
			return true;
		}
		return false;
	}
	
	public static boolean eMaiorQueTerceiroQuartil(int numero, int terceiroQuartil) {
		if (numero > terceiroQuartil) {
			return true;
		}
		return false;
	}
}
