package com.github.gspell4eclipse.engine.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.ui.texteditor.spelling.SpellingProblem;
import org.xeustechnologies.googleapi.spelling.SpellCorrection;

public class GSpellingProblem extends SpellingProblem {

  private static final String NAME = "GSpell";
  
  private final String word;
  private final int offset;
  private final int length;
  private final SpellCorrection sc;
  
  public GSpellingProblem(String word, int offset, int length, SpellCorrection sc) {
    this.word = word;
    this.offset = offset;
    this.length = length;
    this.sc = sc;
  }

  @Override
  public int getOffset() {
    return offset;
  }

  @Override
  public int getLength() {
    return length;
  }

  @Override
  public String getMessage() {
    return NAME + ": the word \"" + word + "\" is misspelled, confidence: " + sc.getConfidence();
  }
  
  @Override
  public ICompletionProposal[] getProposals() {
    List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

    for (String proposal : sc.getWords()) {
      res.add(new CompletionProposal(proposal, offset, length, length));
    }
    
    return res.toArray(new ICompletionProposal[0]);
  }
}
