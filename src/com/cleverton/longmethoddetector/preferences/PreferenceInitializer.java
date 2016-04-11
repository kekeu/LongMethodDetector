package com.cleverton.longmethoddetector.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.cleverton.longmethoddetector.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.USAR_P_EXEMPLO_V_LIMIAR, "valorLimiar");
		store.setDefault(PreferenceConstants.VALOR_LIMIAR, 10);
		store.setDefault(PreferenceConstants.PORCENTAGEM_PROJETO_EXEMPLO, 75);
	}

}
