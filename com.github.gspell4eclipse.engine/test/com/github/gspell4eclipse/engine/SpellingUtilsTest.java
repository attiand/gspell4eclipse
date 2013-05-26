package com.github.gspell4eclipse.engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.junit.Test;

import com.github.gspell4eclipse.engine.SpellingUtils;

public class SpellingUtilsTest {

  @Test
  public void testExcludeZero() {
    
    List<IRegion> expected = new ArrayList<>();
    expected.add(new Region(0, 4));
    
    List<IRegion> exclude = new ArrayList<>();
    
    assertEquals(expected, SpellingUtils.excludeRegions(new Region(0, 4), exclude));
  }  
  
  @Test
  public void testExcludeOne() {
    
    List<IRegion> expected = new ArrayList<>();
    expected.add(new Region(0, 2));
    expected.add(new Region(4, 2));
    
    List<IRegion> exclude = new ArrayList<>();
    exclude.add(new Region(2, 2));    
    
    assertEquals(expected, SpellingUtils.excludeRegions(new Region(0, 6), exclude));
  }
  
  @Test
  public void testExcludeTwo() {
    
    List<IRegion> expected = new ArrayList<>();
    expected.add(new Region(0, 2));
    expected.add(new Region(4, 2));
    expected.add(new Region(8, 2));
    
    List<IRegion> exclude = new ArrayList<>();
    exclude.add(new Region(2, 2));    
    exclude.add(new Region(6, 2));    
    
    assertEquals(expected, SpellingUtils.excludeRegions(new Region(0, 10), exclude));
  }
  
  @Test
  public void testExcludeAsymmetrical() {  
    
    List<IRegion> expected = new ArrayList<>();
    expected.add(new Region(0, 1));
    expected.add(new Region(3, 3));
    expected.add(new Region(10, 5));
    
    List<IRegion> exclude = new ArrayList<>();
    exclude.add(new Region(1, 2));    
    exclude.add(new Region(6, 4));    
    
    assertEquals(expected, SpellingUtils.excludeRegions(new Region(0, 15), exclude));
  }  

  @Test
  public void testExcludeBase() {
    
    List<IRegion> expected = new ArrayList<>();
    expected.add(new Region(3, 2));
    expected.add(new Region(7, 5));
    
    List<IRegion> exclude = new ArrayList<>();
    exclude.add(new Region(5, 2));    
    
    assertEquals(expected, SpellingUtils.excludeRegions(new Region(3, 9), exclude));
  }  
  
  @Test
  public void testExcludeBigBase() {
    
    List<IRegion> expected = new ArrayList<>();
    expected.add(new Region(22, 37));
    expected.add(new Region(70, 5));
    
    List<IRegion> exclude = new ArrayList<>();
    exclude.add(new Region(59, 11));    
    
    assertEquals(expected, SpellingUtils.excludeRegions(new Region(22, 53), exclude));
  }  
}
