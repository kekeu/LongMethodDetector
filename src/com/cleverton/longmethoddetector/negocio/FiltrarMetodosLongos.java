package com.cleverton.longmethoddetector.negocio;

import java.util.ArrayList;

import org.eclipse.jface.preference.IPreferenceStore;

import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.model.DadosClasse;
import com.cleverton.longmethoddetector.model.DadosMetodo;
import com.cleverton.longmethoddetector.model.DadosMetodoLongo;
import com.cleverton.longmethoddetector.preferences.PreferenceConstants;

public class FiltrarMetodosLongos {

	public static ArrayList<DadosMetodoLongo> filtrarPorValorLimiar(
			ArrayList<DadosClasse> dadosClasse) {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		int valorLimiar = Integer.parseInt(store.getString(PreferenceConstants.VALOR_LIMIAR));
		ArrayList<DadosMetodoLongo> listaMetodosLongos = new ArrayList<>();
		for (DadosClasse classe : dadosClasse) {
			//System.out.println(classe.getNomeClasse() +"  --  "+classe.getMetodos().size());
			//System.out.println();
			for (DadosMetodo metodo : classe.getMetodos()) {
				if (metodo.getNumeroLinhas() >= valorLimiar) {
					DadosMetodoLongo dadosML = new DadosMetodoLongo();
					dadosML.setCharFinal(metodo.getCharFinal());
					dadosML.setCharInicial(metodo.getCharInicial());
					dadosML.setDiretorioDaClasse(classe.getDiretorioDaClasse());
					dadosML.setLinhaInicial(metodo.getLinhaInicial());
					dadosML.setNomeClasse(classe.getNomeClasse());
					dadosML.setNomeMetodo(metodo.getNomeMetodo());
					dadosML.setNumeroLinhas(metodo.getNumeroLinhas());
					listaMetodosLongos.add(dadosML);
				}
			}
		}
		return listaMetodosLongos;
	}
	
	// TODO: Filtrar os métodos longos de acordo com a analise realizada no projeto de exemplo
	
}
