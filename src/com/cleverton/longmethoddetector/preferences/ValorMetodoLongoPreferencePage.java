package com.cleverton.longmethoddetector.preferences;

import org.eclipse.jface.preference.*;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.IWorkbench;
import com.cleverton.longmethoddetector.Activator;
import com.cleverton.longmethoddetector.model.ProviderModel;
import com.cleverton.longmethoddetector.negocio.AtualizadorInformacoesMetodoLongo;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class ValorMetodoLongoPreferencePage extends FieldEditorPreferencePage 
implements IWorkbenchPreferencePage {

	private DirectoryFieldEditor projetoExemploDirectory;
	private RadioGroupFieldEditor escolhaRadioGroup;
	private IntegerFieldEditor valorLimiarField;

	public static final String OPCAOPROJETOEXEMPLO = "projetoExemplo";
	public static final String OPCAOVALORLIMIAR = "valorLimiar";

	private String[][] opcoesRadioGroup = {{ "Usar projeto como exemplo", OPCAOPROJETOEXEMPLO }, 
			{"valor Limiar", OPCAOVALORLIMIAR }};

	public ValorMetodoLongoPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {
		escolhaRadioGroup = new RadioGroupFieldEditor(PreferenceConstants.USAR_P_EXEMPLO_V_LIMIAR,
				"Escolha o tipo de valor a ser utilizado: ", 1, opcoesRadioGroup
				, getFieldEditorParent(), true);

		projetoExemploDirectory = new DirectoryFieldEditor(PreferenceConstants.PROJETO_EXEMPLO, 
				"Diretório do projeto: ", getFieldEditorParent());

		valorLimiarField = new IntegerFieldEditor(PreferenceConstants.VALOR_LIMIAR, "Valor limiar:", 
				getFieldEditorParent());

		addField(escolhaRadioGroup);
		addField(projetoExemploDirectory);
		addField(valorLimiarField);

		changeFieldsPorPreferences();
	}

	public void changeFieldsPorPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		if (store.getString(PreferenceConstants.USAR_P_EXEMPLO_V_LIMIAR).equals(OPCAOPROJETOEXEMPLO)) {
			projetoExemploDirectory.setEnabled(true, getFieldEditorParent());
			valorLimiarField.setEnabled(false, getFieldEditorParent());
		} else {
			projetoExemploDirectory.setEnabled(false, getFieldEditorParent());
			valorLimiarField.setEnabled(true, getFieldEditorParent());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getSource() == escolhaRadioGroup) {
			if (event.getNewValue().toString().equals(OPCAOPROJETOEXEMPLO)) {
				projetoExemploDirectory.setEnabled(true, getFieldEditorParent());
				valorLimiarField.setEnabled(false, getFieldEditorParent());
			} else {
				projetoExemploDirectory.setEnabled(false, getFieldEditorParent());
				valorLimiarField.setEnabled(true, getFieldEditorParent());
			}
		}
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		changeFieldsPorPreferences();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		ProviderModel.INSTANCE.dadosComponentesArquiteturais = null;
		AtualizadorInformacoesMetodoLongo.refreshAll();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}