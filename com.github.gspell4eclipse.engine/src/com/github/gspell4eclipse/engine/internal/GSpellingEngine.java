package com.github.gspell4eclipse.engine.internal;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.statushandlers.StatusManager;
import org.eclipse.ui.texteditor.spelling.ISpellingEngine;
import org.eclipse.ui.texteditor.spelling.ISpellingProblemCollector;
import org.eclipse.ui.texteditor.spelling.SpellingContext;
import org.xeustechnologies.googleapi.spelling.Configuration;
import org.xeustechnologies.googleapi.spelling.Language;
import org.xeustechnologies.googleapi.spelling.SpellChecker;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;
import org.xeustechnologies.googleapi.spelling.SpellResponse;

import com.github.gspell4eclipse.Activator;
import com.github.gspell4eclipse.engine.ISpellingRegionSelector;
import com.github.gspell4eclipse.engine.TagFilter;
import com.github.gspell4eclipse.preferences.Languages;
import com.github.gspell4eclipse.preferences.PreferenceConstants;


public class GSpellingEngine implements ISpellingEngine, IPropertyChangeListener {

  private final static int maxProblem = 100;

  private final SpellChecker checker;

  public GSpellingEngine() {
    Configuration config = new Configuration();
    
    checker = new SpellChecker(config);
    checker.setOverHttps(true);

    // The org.xeustechnologies.googleapi package has a logger that is enabled by default
    if(! Activator.getDefault().isDebugging()){
      Logger logger = Logger.getLogger( SpellChecker.class );    
      logger.setLevel(Level.FATAL);
    }
    
    Activator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
    
    setLanguage();
  }

  public void check(IDocument document, IRegion[] regions, SpellingContext context, ISpellingProblemCollector collector,
      IProgressMonitor monitor) {

    int nProblems = 0;
    TagFilter filter = new TagFilter();

    if (context.getContentType() != null) {
      ISpellingRegionSelector spellingSelector = RegionSelectors.getInstance().getRegionSelector(context.getContentType());

      if (spellingSelector != null) {
        for (IRegion spellRegion : spellingSelector.getTextRegions(document, regions)) {
          for (IRegion region : filter.getTextRegions(document, spellRegion)) {
            if (monitor != null && monitor.isCanceled()) {
              return;
            }

            try {
              String section = document.get(region.getOffset(), region.getLength());

              // Avoid send unnecessary requests
              if(hasWord(section)) {
                SpellResponse spellResponse = checker.check(section);

                if (spellResponse != null && spellResponse.getCorrections() != null) {
                  for (SpellCorrection sc : spellResponse.getCorrections()) {
                    if (nProblems++ >= maxProblem) {
                      return;
                    }

                    int offset = sc.getOffset() + region.getOffset();
                    int length = sc.getLength();

                    collector.accept(new GSpellingProblem(document.get(offset, length), offset, length, sc));
                  }
                }
              }
            } catch (BadLocationException e) {
              IStatus status = new Status(Status.WARNING, Activator.PLUGIN_ID, Status.OK, "Can't spell check region", e);
              StatusManager.getManager().handle(status, StatusManager.LOG);
            }
          }
        }
      }
    }
  }

  /**
   * Check if the supplied string contains a word. 
   * 
   * @param str The string to check.
   * @return <code>true</code> if the supplied string contains a word.
   */
  
  private boolean hasWord(String str) {
    if(str.length() > 1){
      for (int i = 0; i < str.length(); i++) {
        if (! Character.isWhitespace(str.charAt(i))) {
          return true;
        }
      }
    }
    
    return false;
  }

  @Override
  public void propertyChange(PropertyChangeEvent event) {
    setLanguage();
  }

  private void setLanguage() {
    IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
    String pref = preferences.getString(PreferenceConstants.LANGUAGE);
    
    if(pref != null) {
      Language lang = Languages.getLanguageCode(pref);
      
      if(lang != null) {
        checker.setLanguage(lang);  
      }
    }
  }
}
