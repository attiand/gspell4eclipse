package com.github.gspell4eclipse.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.xeustechnologies.googleapi.spelling.Language;

public class Languages {

  private static final SortedMap<String, Language> languages = new TreeMap<>();
  
  static {
    languages.put("Danish", Language.DANISH);
    languages.put("Dutch", Language.DUTCH);
    languages.put("English", Language.ENGLISH);
    languages.put("Finnish", Language.FINNISH);
    languages.put("French", Language.FRENCH);
    languages.put("German", Language.GERMAN);
    languages.put("Italian", Language.ITALIAN);
    languages.put("Polish", Language.POLISH);
    languages.put("Portuguese", Language.PORTUGUESE);
    languages.put("Spanish", Language.SPANISH);
    languages.put("Swedish", Language.SWEDISH);    
  }
  
  public static List<String> getLanguageList() {
    return new ArrayList<String>(languages.keySet());
  }

  /**
   * Get the language code with the specified name.
   * 
   * @param language The language name to get.
   * @return The language code or <code>null</code> if the language was not found.
   */
  
  public static Language getLanguageCode(String language) {
    return languages.get(language);
  }
}
