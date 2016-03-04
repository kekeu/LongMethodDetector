package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;
import java.util.Collections;

import com.cleverton.longmethoddetector.model.InformacoesMetodoModel;

public class MedianaQuartis {
	
	public float calcularMediana(ArrayList<InformacoesMetodoModel> metodos) {
		float retorno = 0;
		int numeroElementosLista = metodos.size();
		if (numeroElementosLista % 2 == 0) {
			retorno = (metodos.get(numeroElementosLista/2).getNumeroLinhas() + 
					metodos.get((numeroElementosLista/2) + 1).getNumeroLinhas()) / 2; 
		} else {
			retorno = metodos.get(((numeroElementosLista-1)/2) + 1).getNumeroLinhas(); 
		}
		return retorno;
	}
	
	public ArrayList<InformacoesMetodoModel> listaOrdemCrescentePorNumeroLinhas(
			ArrayList<InformacoesMetodoModel> metodosDesordenados) {
		ArrayList<InformacoesMetodoModel> metodosOrdenados = metodosDesordenados;
		Collections.sort(metodosOrdenados, (obj1, obj2) -> {
			InformacoesMetodoModel m1 = (InformacoesMetodoModel) obj1;
			InformacoesMetodoModel m2 = (InformacoesMetodoModel) obj2;
			return m1.getNumeroLinhas() < m2.getNumeroLinhas() ? -1 : 
				(m1.getNumeroLinhas() > m2.getNumeroLinhas() ? +1 : 0);
		});
		return metodosOrdenados;
	}

	public int primeiroQuartil(ArrayList<InformacoesMetodoModel> metodosOrdenados) {
		int posicao = (int) Math.round(0.25*(metodosOrdenados.size()+1));
		return metodosOrdenados.get(posicao).getNumeroLinhas();
	}
	
	// TODO: verificar calculo do primeiro e terceiro quartil
	public int terceiroQuartil(ArrayList<InformacoesMetodoModel> metodosOrdenados) {
		int posicao = (int) Math.round(0.75*(metodosOrdenados.size()+1));
		return metodosOrdenados.get(posicao).getNumeroLinhas();
	}
	
	public boolean eCompreendidoPrimeiroTerceiroQuartil(int numero, 
			int primeiroQuartil, int terceiroQuartil) {
		if (numero >= primeiroQuartil && numero <= terceiroQuartil) {
			return true;
		}
		return false;
	}
}
