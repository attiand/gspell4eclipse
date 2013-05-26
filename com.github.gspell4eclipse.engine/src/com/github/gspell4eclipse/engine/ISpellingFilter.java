package com.github.gspell4eclipse.engine;

import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

/**
 * Defines a spelling filter, that is used to further filter out regions from 
 * selected region.
 */

public interface ISpellingFilter {

  /**
   * From a region in a document return a list of regions subject for spell
   * check.
   * 
   * @param document The document to check.
   * @param region A list of regions in the document to check.
   * @return A list of regions to check for spelling errors.
   */
  
  List<IRegion> getTextRegions(IDocument document, IRegion region);
  
}
