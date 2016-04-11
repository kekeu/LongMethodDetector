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

	// TODO: Verificar calculo
	public static int primeiroQuartil(ArrayList<Integer> metodosOrdenados) {
		if (metodosOrdenados.size() == 0) {
			return 0;
		}
		if (metodosOrdenados.size() == 1) {
			return metodosOrdenados.get(0);
		}
		double posicaoReal = (metodosOrdenados.size()+1)/4;
		int posicaoInteira = (int)posicaoReal;
		if (posicaoReal == posicaoInteira) {
			if (posicaoInteira == 0) {
				metodosOrdenados.get(posicaoInteira);
			}
			return metodosOrdenados.get(posicaoInteira - 1);
		} else {
			if (posicaoInteira == 0) {
				return (metodosOrdenados.get(0) + metodosOrdenados.get(1))/2;
			}
			if (posicaoInteira == metodosOrdenados.size()) {
				return (metodosOrdenados.get(posicaoInteira-2) + metodosOrdenados.get(posicaoInteira-1))/2;
			}
			return (metodosOrdenados.get(posicaoInteira) + 
					metodosOrdenados.get(posicaoInteira - 1))/2;
		}
	}	
	
	public static int terceiroQuartil(ArrayList<Integer> metodosOrdenados, int porcentagemLimiar) {
		if (metodosOrdenados.size() == 0) {
			return 0;
		}
		if (metodosOrdenados.size() == 1) {
			return metodosOrdenados.get(0);
		}
		double posicaoReal = ((double)porcentagemLimiar/100)*(metodosOrdenados.size()+1);
		int posicaoInteira = (int)posicaoReal;
		if (posicaoReal == posicaoInteira) {
			if (posicaoInteira == 0) {
				metodosOrdenados.get(posicaoInteira);
			}
			if (posicaoInteira >= metodosOrdenados.size()) {
				return metodosOrdenados.get(metodosOrdenados.size() - 1);
			}
			return metodosOrdenados.get(posicaoInteira - 1);
		} else {
			if (posicaoInteira == 0) {
				return (metodosOrdenados.get(0) + metodosOrdenados.get(1))/2;
			}
			if (posicaoInteira == metodosOrdenados.size()) {
				return (metodosOrdenados.get(posicaoInteira-2) + metodosOrdenados.get(posicaoInteira-1))/2;
			}
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
