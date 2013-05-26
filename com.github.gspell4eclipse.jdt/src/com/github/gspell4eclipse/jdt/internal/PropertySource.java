package com.github.gspell4eclipse.jdt.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.ui.propertiesfileeditor.IPropertiesFilePartitions;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextUtilities;

import com.github.gspell4eclipse.engine.ISpellingRegionSelector;


@SuppressWarnings("restriction")
public class PropertySource implements ISpellingRegionSelector {

  @Override
  public List<IRegion> getTextRegions(IDocument document, IRegion[] regions) {
    List<IRegion> res = new ArrayList<>();

    for (int i = 0; i < regions.length; i++) {
      IRegion region = regions[i];
      ITypedRegion[] partitions;
      try {
        partitions = TextUtilities.computePartitioning(document, IPropertiesFilePartitions.PROPERTIES_FILE_PARTITIONING,
            region.getOffset(), region.getLength(), false);
        for (int index = 0; index < partitions.length; index++) {

          ITypedRegion partition = partitions[index];
          final String type = partition.getType();

          if (IPropertiesFilePartitions.COMMENT.equals(type)) {
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
