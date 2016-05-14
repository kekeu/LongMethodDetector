package com.cleverton.longmethoddetector.preferences;

import org.eclipse.jface.dialogs.MessageDialog;
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
	private RadioGroupFieldEditor escolhaProjetoExemploValorLimiarRG;
	private IntegerFieldEditor valorLimiarField;
	private IntegerFieldEditor porcentagemProjetoExemploField;
	private BooleanFieldEditor usarInteresseBooleanField;
	
	public static final String OPCAOPROJETOEXEMPLO = "projetoExemplo";
	public static final String OPCAOVALORLIMIAR = "valorLimiar";
	public static final String OPCAOCALCULARGERAL = "geral";
	public static final String OPCAOCALCULARPORPREOCUPACAO = "porPreocupacao";

	private String[][] opcoesProjetoRG = {
			{"Extract threshold values of a sample project", OPCAOPROJETOEXEMPLO }, 
			{"Use generics threshold values", OPCAOVALORLIMIAR }};

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
		escolhaProjetoExemploValorLimiarRG = new RadioGroupFieldEditor(PreferenceConstants.USAR_P_EXEMPLO_V_LIMIAR,
				"&Choose the approach to code review: ", 1, opcoesProjetoRG, getFieldEditorParent(), true);
		
		projetoExemploDirectory = new DirectoryFieldEditor(PreferenceConstants.PROJETO_EXEMPLO, 
				"&Sample Project Folder:  ", getFieldEditorParent());

		porcentagemProjetoExemploField = new IntegerFieldEditor(PreferenceConstants
				.PORCENTAGEM_PROJETO_EXEMPLO, "&Percentile considered to Extract  Threshold Values: ", 
						getFieldEditorParent());
		
		usarInteresseBooleanField = new BooleanFieldEditor(PreferenceConstants.USAR_PREOCUPACAO_ARQUITETURAL,
		        "&Consider architectural concerns", getFieldEditorParent());

		valorLimiarField = new IntegerFieldEditor(PreferenceConstants.VALOR_LIMIAR, "LOC/Method: ", 
				getFieldEditorParent());
		
		addField(escolhaProjetoExemploValorLimiarRG);
		addField(valorLimiarField);
		addField(projetoExemploDirectory);
		addField(porcentagemProjetoExemploField);
		addField(usarInteresseBooleanField);
		
		changeFieldsPorPreferences();
	}

	public void changeFieldsPorPreferences() {
		if (getPreferenceStore().getString(PreferenceConstants.USAR_P_EXEMPLO_V_LIMIAR).equals(OPCAOPROJETOEXEMPLO)) {
			habilitarCamposProjetoExemplo();
		} else {
			habilitarCamposValorLimiar();
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getSource() == escolhaProjetoExemploValorLimiarRG) {
			if (event.getNewValue().toString().equals(OPCAOPROJETOEXEMPLO)) {
				habilitarCamposProjetoExemplo();
			} else {
				habilitarCamposValorLimiar();
			}
		}
	}

	private void habilitarCamposProjetoExemplo() {
		projetoExemploDirectory.setEnabled(true, getFieldEditorParent());
		valorLimiarField.setEnabled(false, getFieldEditorParent());
		porcentagemProjetoExemploField.setEnabled(true, getFieldEditorParent());
		usarInteresseBooleanField.setEnabled(true, getFieldEditorParent());
	}

	private void habilitarCamposValorLimiar() {
		projetoExemploDirectory.setEnabled(false, getFieldEditorParent());
		valorLimiarField.setEnabled(true, getFieldEditorParent());
		porcentagemProjetoExemploField.setEnabled(false, getFieldEditorParent());
		usarInteresseBooleanField.setEnabled(false, getFieldEditorParent());
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		habilitarCamposValorLimiar();
	}

	@Override
	public boolean performOk() {
		if (temErrosCamposPreferencesPage()) {
			return false;
		}
		return super.performOk();
	}

	@Override
	protected void performApply() {
		if (temErrosCamposPreferencesPage()) {
			return;
		}
		super.performApply();
	}

	public boolean temErrosCamposPreferencesPage() {
		MessageDialog dialog = null;
		if (projetoExemploDirectory.getLabelControl(getFieldEditorParent()).getEnabled() &&
				(projetoExemploDirectory.getStringValue() == null || 
				projetoExemploDirectory.getStringValue().equals(""))) {
			dialog = new MessageDialog(null, "Preferences Page", null, 
					"Selecione um projeto para ser usado na analise como Projeto de Exemplo.", 
					MessageDialog.INFORMATION, new String[] {"OK"}, 0);
			dialog.open();
			return true;
		}
		if (porcentagemProjetoExemploField.getLabelControl(getFieldEditorParent()).getEnabled() &&
				(porcentagemProjetoExemploField.getIntValue() < 1 || 
				porcentagemProjetoExemploField.getIntValue() > 99)) {
			dialog = new MessageDialog(null, "Preferences Page", null, 
					"Selecione um valor de porcentagem entre 1% e 99%.", 
					MessageDialog.INFORMATION, new String[] {"OK"}, 0);
			dialog.open();
			return true;
		}
		return false;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		ProviderModel.INSTANCE.dadosComponentesArquiteturais = null;
		ProviderModel.INSTANCE.valorLimiarGlobal = 0;
		ProviderModel.INSTANCE.medianaGlobal = 0;
		AtualizadorInformacoesMetodoLongo.refreshAll();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

}