package com.github.gspell4eclipse.engine;

import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

/**
 * Defines a spelling region selector. Selects interesting regions to check spelling in.  
 */

public interface ISpellingRegionSelector {

  /**
   * Return a list of interesting text region from a list of regions for 
   * this content type.
   * 
   * @param document The document to check.
   * @param regions A list of regions in the document to check.
   * @return A list of regions to check for spelling errors.
   */
  
  List<IRegion> getTextRegions(IDocument document, IRegion[] regions);
}
