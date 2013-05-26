package com.github.gspell4eclipse.engine;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

/**
 * Implements some spelling utilities. 
 */

public class SpellingUtils {

  /**
   * Excludes a list of regions from a main region. The return list will
   * contains one more region than the exclude list if the exclude list contains
   * one items or more.
   * 
   * @param region
   *          The original region.
   * @param exclude
   *          The list of regions to exclude.
   * @return A list of regions covering all positions that is not covered by the
   *         regions in the exclude list.
   */

  public static List<IRegion> excludeRegions(IRegion region, List<IRegion> exclude) {
    List<IRegion> res = new ArrayList<>();

    if (exclude.isEmpty()) {
      res.add(region);
      return res;
    }

    int index = region.getOffset();

    for (int i = 0; i < exclude.size(); i++) {
      int offset = exclude.get(i).getOffset();
      res.add(new Region(index, offset - index));
      index = offset + exclude.get(i).getLength();
    }

    res.add(new Region(index, region.getOffset() + region.getLength() - index));

    return res;
  }
}
