package com.github.gspell4eclipse.jdt.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

import com.github.gspell4eclipse.engine.SpellingUtils;


public class JavaDocTagFilter {

  private static final Pattern JAVA_DOC_TAG = Pattern.compile("@\\w+(?:\\s+\\w+)");
  
  public static List<IRegion> getRegions(IDocument document, IRegion region) {
    List<IRegion> exclude = new ArrayList<>();
    
    try {
      Matcher m = JAVA_DOC_TAG.matcher(document.get(region.getOffset(), region.getLength()));

      while (m.find()) {
        exclude.add(new Region(m.start() + region.getOffset(), m.end() - m.start()));
      }
    } catch (BadLocationException e) {
      // OK, do not exclude any regions.
    }
    
    return SpellingUtils.excludeRegions(region, exclude);
  }
}
