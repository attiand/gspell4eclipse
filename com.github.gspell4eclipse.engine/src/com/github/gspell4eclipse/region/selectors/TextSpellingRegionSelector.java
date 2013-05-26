package com.github.gspell4eclipse.region.selectors;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import com.github.gspell4eclipse.engine.ISpellingRegionSelector;


public class TextSpellingRegionSelector implements ISpellingRegionSelector {

  @Override
  public List<IRegion> getTextRegions(IDocument document, IRegion[] regions) {
    return Arrays.asList(regions);
  }
}
