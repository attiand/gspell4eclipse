package com.github.gspell4eclipse.jdt.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.ui.text.IJavaPartitions;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextUtilities;

import com.github.gspell4eclipse.engine.ISpellingRegionSelector;


public class JavaSource implements ISpellingRegionSelector {

  @Override
  public List<IRegion> getTextRegions(IDocument document, IRegion[] regions) {
    List<IRegion> res = new ArrayList<>();

    for (int i = 0; i < regions.length; i++) {
      IRegion region = regions[i];
      ITypedRegion[] partitions;
      try {
        partitions = TextUtilities.computePartitioning(document, IJavaPartitions.JAVA_PARTITIONING, region.getOffset(), region.getLength(),
            false);
        for (int index = 0; index < partitions.length; index++) {

          ITypedRegion partition = partitions[index];
          final String type = partition.getType();

          if(type.equals(IJavaPartitions.JAVA_DOC)) {
            res.addAll(JavaDocTagFilter.getRegions(document, partition));
          }
          else if (!type.equals(IDocument.DEFAULT_CONTENT_TYPE) && !type.equals(IJavaPartitions.JAVA_CHARACTER)) {
            res.add(partition);
          }
          
        }
      } catch (BadLocationException e) {
        // OK
      }
    }
    return res;
  }
}
