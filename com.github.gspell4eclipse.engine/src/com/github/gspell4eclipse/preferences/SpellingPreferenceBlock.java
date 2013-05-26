package com.github.gspell4eclipse.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.texteditor.spelling.IPreferenceStatusMonitor;
import org.eclipse.ui.texteditor.spelling.ISpellingPreferenceBlock;

import com.github.gspell4eclipse.Activator;


public class SpellingPreferenceBlock implements ISpellingPreferenceBlock {

  private SpellControl control;

  public SpellingPreferenceBlock() {
  }

  public void initialize(IPreferenceStatusMonitor statusMonitor) {
    performRevert();
  }

  public Control createControl(Composite parent) {
    return control = new SpellControl(parent, SWT.NONE);
  }

  public void setEnabled(boolean enabled) {
    control.setEnabled(enabled);
  }

  public boolean canPerformOk() {
    return control.canPerformOk();
  }

  public void performOk() {
    IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();

    preferences.setValue(PreferenceConstants.LANGUAGE, control.getLanguage());
  }

  public void performDefaults() {
    control.setLanguage("English");
  }

  public void performRevert() {
    IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();

    String lang = preferences.getString(PreferenceConstants.LANGUAGE);

    if (lang.length() > 1) {
      control.setLanguage(lang);
    } else {
      control.setLanguage(PreferenceConstants.DEFAULT_LANGUAGE);
    }
  }

  public void dispose() {
    control.dispose();
  }
}
