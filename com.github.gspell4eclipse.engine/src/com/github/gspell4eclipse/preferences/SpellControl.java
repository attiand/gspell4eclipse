package com.github.gspell4eclipse.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SpellControl extends Composite {

  private final Combo languageCombo;
  
  public SpellControl(Composite parent, int style) {
    super(parent, style);
    
    Label label = new Label(this, SWT.LEFT);
    label.setText("Language");
    
    languageCombo = new Combo(this, SWT.READ_ONLY);
    
    for (String name: Languages.getLanguageList()) {
      languageCombo.add(name);      
    }
    
    GridData gridData = new GridData();
    languageCombo.setLayoutData(gridData);
    
    setLayout(new GridLayout(2, false));
  }

  public boolean canPerformOk() {
    return languageCombo.getText().length() > 0;
  }

  public String getLanguage() {
    return languageCombo.getText();
  }

  public void setLanguage(String lang) {
    languageCombo.setText(lang);
  }
}
