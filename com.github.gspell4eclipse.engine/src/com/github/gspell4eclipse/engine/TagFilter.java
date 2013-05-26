package com.github.gspell4eclipse.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

/**
 * Implements a filter that excludes any XML like element.   
 */

public class TagFilter implements ISpellingFilter {

  private static final Pattern XML_TAGS = Pattern.compile("</?\\w+>");

  @Override
  public List<IRegion> getTextRegions(IDocument document, IRegion region) {
    List<IRegion> exclude = new ArrayList<>();

    try {
      Matcher m = XML_TAGS.matcher(document.get(region.getOffset(), region.getLength()));

      while (m.find()) {
        exclude.add(new Region(m.start() + region.getOffset(), m.end() - m.start()));
      }
    } catch (BadLocationException e) {
      // OK, do not exclude any regions.
    }

    return SpellingUtils.excludeRegions(region, exclude);
  }
}
