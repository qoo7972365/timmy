/*     */ package java.awt.font;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.text.AttributedCharacterIterator;
/*     */ import java.text.Bidi;
/*     */ import java.text.BreakIterator;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Map;
/*     */ import sun.font.AttributeValues;
/*     */ import sun.font.BidiUtils;
/*     */ import sun.font.TextLabelFactory;
/*     */ import sun.font.TextLineComponent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class TextMeasurer
/*     */   implements Cloneable
/*     */ {
/* 101 */   private static float EST_LINES = 2.1F;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FontRenderContext fFrc;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int fStart;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private char[] fChars;
/*     */ 
/*     */ 
/*     */   
/*     */   private Bidi fBidi;
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] fLevels;
/*     */ 
/*     */ 
/*     */   
/*     */   private TextLineComponent[] fComponents;
/*     */ 
/*     */ 
/*     */   
/*     */   private int fComponentStart;
/*     */ 
/*     */ 
/*     */   
/*     */   private int fComponentLimit;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean haveLayoutWindow;
/*     */ 
/*     */ 
/*     */   
/* 144 */   private BreakIterator fLineBreak = null;
/* 145 */   private CharArrayIterator charIter = null;
/* 146 */   int layoutCount = 0;
/* 147 */   int layoutCharCount = 0;
/*     */   
/*     */   private StyledParagraph fParagraph;
/*     */   
/*     */   private boolean fIsDirectionLTR;
/*     */   
/*     */   private byte fBaseline;
/*     */   
/*     */   private float[] fBaselineOffsets;
/* 156 */   private float fJustifyRatio = 1.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int formattedChars;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object clone() {
/*     */     TextMeasurer textMeasurer;
/*     */     try {
/* 174 */       textMeasurer = (TextMeasurer)super.clone();
/*     */     }
/* 176 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/* 177 */       throw new Error();
/*     */     } 
/* 179 */     if (this.fComponents != null) {
/* 180 */       textMeasurer.fComponents = (TextLineComponent[])this.fComponents.clone();
/*     */     }
/* 182 */     return textMeasurer;
/*     */   }
/*     */   
/*     */   private void invalidateComponents() {
/* 186 */     this.fComponentStart = this.fComponentLimit = this.fChars.length;
/* 187 */     this.fComponents = null;
/* 188 */     this.haveLayoutWindow = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initAll(AttributedCharacterIterator paramAttributedCharacterIterator) {
/* 197 */     this.fStart = paramAttributedCharacterIterator.getBeginIndex();
/*     */ 
/*     */     
/* 200 */     this.fChars = new char[paramAttributedCharacterIterator.getEndIndex() - this.fStart];
/*     */     
/* 202 */     byte b = 0;
/* 203 */     char c = paramAttributedCharacterIterator.first();
/* 204 */     for (; c != Character.MAX_VALUE; 
/* 205 */       c = paramAttributedCharacterIterator.next())
/*     */     {
/* 207 */       this.fChars[b++] = c;
/*     */     }
/*     */     
/* 210 */     paramAttributedCharacterIterator.first();
/*     */     
/* 212 */     this.fBidi = new Bidi(paramAttributedCharacterIterator);
/* 213 */     if (this.fBidi.isLeftToRight()) {
/* 214 */       this.fBidi = null;
/*     */     }
/*     */     
/* 217 */     paramAttributedCharacterIterator.first();
/* 218 */     Map<AttributedCharacterIterator.Attribute, Object> map = paramAttributedCharacterIterator.getAttributes();
/* 219 */     NumericShaper numericShaper = AttributeValues.getNumericShaping(map);
/* 220 */     if (numericShaper != null) {
/* 221 */       numericShaper.shape(this.fChars, 0, this.fChars.length);
/*     */     }
/*     */     
/* 224 */     this.fParagraph = new StyledParagraph(paramAttributedCharacterIterator, this.fChars);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 232 */     this.fJustifyRatio = AttributeValues.getJustification(map);
/*     */     
/* 234 */     boolean bool = TextLine.advanceToFirstFont(paramAttributedCharacterIterator);
/*     */     
/* 236 */     if (bool) {
/* 237 */       Font font = TextLine.getFontAtCurrentPos(paramAttributedCharacterIterator);
/* 238 */       int i = paramAttributedCharacterIterator.getIndex() - paramAttributedCharacterIterator.getBeginIndex();
/* 239 */       LineMetrics lineMetrics = font.getLineMetrics(this.fChars, i, i + 1, this.fFrc);
/* 240 */       this.fBaseline = (byte)lineMetrics.getBaselineIndex();
/* 241 */       this.fBaselineOffsets = lineMetrics.getBaselineOffsets();
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 248 */       GraphicAttribute graphicAttribute = (GraphicAttribute)map.get(TextAttribute.CHAR_REPLACEMENT);
/* 249 */       this.fBaseline = TextLayout.getBaselineFromGraphic(graphicAttribute);
/* 250 */       Hashtable<Object, Object> hashtable = new Hashtable<>(5, 0.9F);
/* 251 */       Font font = new Font((Map)hashtable);
/* 252 */       LineMetrics lineMetrics = font.getLineMetrics(" ", 0, 1, this.fFrc);
/* 253 */       this.fBaselineOffsets = lineMetrics.getBaselineOffsets();
/*     */     } 
/* 255 */     this.fBaselineOffsets = TextLine.getNormalizedOffsets(this.fBaselineOffsets, this.fBaseline);
/*     */ 
/*     */     
/* 258 */     invalidateComponents();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateComponents(int paramInt1, int paramInt2) {
/* 267 */     if (this.collectStats) {
/* 268 */       this.formattedChars += paramInt2 - paramInt1;
/*     */     }
/* 270 */     boolean bool = false;
/* 271 */     TextLabelFactory textLabelFactory = new TextLabelFactory(this.fFrc, this.fChars, this.fBidi, bool);
/*     */     
/* 273 */     int[] arrayOfInt = null;
/*     */     
/* 275 */     if (this.fBidi != null) {
/* 276 */       this.fLevels = BidiUtils.getLevels(this.fBidi);
/* 277 */       int[] arrayOfInt1 = BidiUtils.createVisualToLogicalMap(this.fLevels);
/* 278 */       arrayOfInt = BidiUtils.createInverseMap(arrayOfInt1);
/* 279 */       this.fIsDirectionLTR = this.fBidi.baseIsLeftToRight();
/*     */     } else {
/*     */       
/* 282 */       this.fLevels = null;
/* 283 */       this.fIsDirectionLTR = true;
/*     */     } 
/*     */     
/*     */     try {
/* 287 */       this.fComponents = TextLine.getComponents(this.fParagraph, this.fChars, paramInt1, paramInt2, arrayOfInt, this.fLevels, textLabelFactory);
/*     */     
/*     */     }
/* 290 */     catch (IllegalArgumentException illegalArgumentException) {
/* 291 */       System.out.println("startingAt=" + paramInt1 + "; endingAt=" + paramInt2);
/* 292 */       System.out.println("fComponentLimit=" + this.fComponentLimit);
/* 293 */       throw illegalArgumentException;
/*     */     } 
/*     */     
/* 296 */     this.fComponentStart = paramInt1;
/* 297 */     this.fComponentLimit = paramInt2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int calcLineBreak(int paramInt, float paramFloat) {
/* 307 */     int i = paramInt;
/* 308 */     float f = paramFloat;
/*     */ 
/*     */     
/* 311 */     int j = this.fComponentStart;
/*     */     byte b;
/* 313 */     for (b = 0; b < this.fComponents.length; b++) {
/* 314 */       int k = j + this.fComponents[b].getNumCharacters();
/* 315 */       if (k > i) {
/*     */         break;
/*     */       }
/*     */       
/* 319 */       j = k;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 325 */     for (; b < this.fComponents.length; b++) {
/*     */       
/* 327 */       TextLineComponent textLineComponent = this.fComponents[b];
/* 328 */       int k = textLineComponent.getNumCharacters();
/*     */       
/* 330 */       int m = textLineComponent.getLineBreakIndex(i - j, f);
/* 331 */       if (m == k && b < this.fComponents.length) {
/* 332 */         f -= textLineComponent.getAdvanceBetween(i - j, m);
/* 333 */         j += k;
/* 334 */         i = j;
/*     */       } else {
/*     */         
/* 337 */         return j + m;
/*     */       } 
/*     */     } 
/*     */     
/* 341 */     if (this.fComponentLimit < this.fChars.length) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 347 */       generateComponents(paramInt, this.fChars.length);
/* 348 */       return calcLineBreak(paramInt, paramFloat);
/*     */     } 
/*     */     
/* 351 */     return this.fChars.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int trailingCdWhitespaceStart(int paramInt1, int paramInt2) {
/* 365 */     if (this.fLevels != null) {
/*     */       
/* 367 */       byte b = (byte)(this.fIsDirectionLTR ? 0 : 1);
/* 368 */       for (int i = paramInt2; --i >= paramInt1;) {
/* 369 */         if (this.fLevels[i] % 2 == b || 
/* 370 */           Character.getDirectionality(this.fChars[i]) != 12) {
/* 371 */           return ++i;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 376 */     return paramInt1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TextLineComponent[] makeComponentsOnRange(int paramInt1, int paramInt2) {
/*     */     byte b2;
/* 386 */     int i = trailingCdWhitespaceStart(paramInt1, paramInt2);
/*     */ 
/*     */     
/* 389 */     int j = this.fComponentStart;
/*     */     byte b1;
/* 391 */     for (b1 = 0; b1 < this.fComponents.length; b1++) {
/* 392 */       int i2 = j + this.fComponents[b1].getNumCharacters();
/* 393 */       if (i2 > paramInt1) {
/*     */         break;
/*     */       }
/*     */       
/* 397 */       j = i2;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 405 */     boolean bool = false;
/* 406 */     int m = j;
/* 407 */     int n = b1; int i1;
/* 408 */     for (i1 = 1; i1; n++) {
/* 409 */       b2 = m + this.fComponents[n].getNumCharacters();
/* 410 */       if (i > Math.max(m, paramInt1) && i < 
/* 411 */         Math.min(b2, paramInt2)) {
/* 412 */         bool = true;
/*     */       }
/* 414 */       if (b2 >= paramInt2) {
/* 415 */         i1 = 0;
/*     */       } else {
/*     */         
/* 418 */         m = b2;
/*     */       } 
/*     */     } 
/* 421 */     int k = n - b1;
/* 422 */     if (bool) {
/* 423 */       k++;
/*     */     }
/*     */ 
/*     */     
/* 427 */     TextLineComponent[] arrayOfTextLineComponent = new TextLineComponent[k];
/* 428 */     m = 0;
/* 429 */     n = paramInt1;
/*     */     
/* 431 */     i1 = i;
/*     */ 
/*     */     
/* 434 */     if (i1 == paramInt1) {
/* 435 */       b2 = this.fIsDirectionLTR ? 0 : 1;
/*     */       
/* 437 */       i1 = paramInt2;
/*     */     } else {
/*     */       
/* 440 */       b2 = 2;
/*     */     } 
/*     */     
/* 443 */     while (n < paramInt2) {
/*     */       
/* 445 */       int i2 = this.fComponents[b1].getNumCharacters();
/* 446 */       int i3 = j + i2;
/*     */       
/* 448 */       int i4 = Math.max(n, j);
/* 449 */       int i5 = Math.min(i1, i3);
/*     */       
/* 451 */       arrayOfTextLineComponent[m++] = this.fComponents[b1].getSubset(i4 - j, i5 - j, b2);
/*     */ 
/*     */ 
/*     */       
/* 455 */       n += i5 - i4;
/* 456 */       if (n == i1) {
/* 457 */         i1 = paramInt2;
/* 458 */         b2 = this.fIsDirectionLTR ? 0 : 1;
/*     */       } 
/*     */       
/* 461 */       if (n == i3) {
/* 462 */         b1++;
/* 463 */         j = i3;
/*     */       } 
/*     */     } 
/*     */     
/* 467 */     return arrayOfTextLineComponent;
/*     */   }
/*     */ 
/*     */   
/*     */   private TextLine makeTextLineOnRange(int paramInt1, int paramInt2) {
/* 472 */     int[] arrayOfInt = null;
/* 473 */     byte[] arrayOfByte = null;
/*     */     
/* 475 */     if (this.fBidi != null) {
/* 476 */       Bidi bidi = this.fBidi.createLineBidi(paramInt1, paramInt2);
/* 477 */       arrayOfByte = BidiUtils.getLevels(bidi);
/* 478 */       int[] arrayOfInt1 = BidiUtils.createVisualToLogicalMap(arrayOfByte);
/* 479 */       arrayOfInt = BidiUtils.createInverseMap(arrayOfInt1);
/*     */     } 
/*     */     
/* 482 */     TextLineComponent[] arrayOfTextLineComponent = makeComponentsOnRange(paramInt1, paramInt2);
/*     */     
/* 484 */     return new TextLine(this.fFrc, arrayOfTextLineComponent, this.fBaselineOffsets, this.fChars, paramInt1, paramInt2, arrayOfInt, arrayOfByte, this.fIsDirectionLTR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureComponents(int paramInt1, int paramInt2) {
/* 498 */     if (paramInt1 < this.fComponentStart || paramInt2 > this.fComponentLimit) {
/* 499 */       generateComponents(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void makeLayoutWindow(int paramInt) {
/* 505 */     int i = paramInt;
/* 506 */     int j = this.fChars.length;
/*     */ 
/*     */     
/* 509 */     if (this.layoutCount > 0 && !this.haveLayoutWindow) {
/* 510 */       float f = Math.max(this.layoutCharCount / this.layoutCount, 1);
/* 511 */       j = Math.min(paramInt + (int)(f * EST_LINES), this.fChars.length);
/*     */     } 
/*     */     
/* 514 */     if (paramInt > 0 || j < this.fChars.length) {
/* 515 */       if (this.charIter == null) {
/* 516 */         this.charIter = new CharArrayIterator(this.fChars);
/*     */       } else {
/*     */         
/* 519 */         this.charIter.reset(this.fChars);
/*     */       } 
/* 521 */       if (this.fLineBreak == null) {
/* 522 */         this.fLineBreak = BreakIterator.getLineInstance();
/*     */       }
/* 524 */       this.fLineBreak.setText(this.charIter);
/* 525 */       if (paramInt > 0 && 
/* 526 */         !this.fLineBreak.isBoundary(paramInt)) {
/* 527 */         i = this.fLineBreak.preceding(paramInt);
/*     */       }
/*     */       
/* 530 */       if (j < this.fChars.length && 
/* 531 */         !this.fLineBreak.isBoundary(j)) {
/* 532 */         j = this.fLineBreak.following(j);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 537 */     ensureComponents(i, j);
/* 538 */     this.haveLayoutWindow = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineBreakIndex(int paramInt, float paramFloat) {
/* 558 */     int i = paramInt - this.fStart;
/*     */     
/* 560 */     if (!this.haveLayoutWindow || i < this.fComponentStart || i >= this.fComponentLimit)
/*     */     {
/*     */       
/* 563 */       makeLayoutWindow(i);
/*     */     }
/*     */     
/* 566 */     return calcLineBreak(i, paramFloat) + this.fStart;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAdvanceBetween(int paramInt1, int paramInt2) {
/* 587 */     int i = paramInt1 - this.fStart;
/* 588 */     int j = paramInt2 - this.fStart;
/*     */     
/* 590 */     ensureComponents(i, j);
/* 591 */     TextLine textLine = makeTextLineOnRange(i, j);
/* 592 */     return (textLine.getMetrics()).advance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextLayout getLayout(int paramInt1, int paramInt2) {
/* 612 */     int i = paramInt1 - this.fStart;
/* 613 */     int j = paramInt2 - this.fStart;
/*     */     
/* 615 */     ensureComponents(i, j);
/* 616 */     TextLine textLine = makeTextLineOnRange(i, j);
/*     */     
/* 618 */     if (j < this.fChars.length) {
/* 619 */       this.layoutCharCount += paramInt2 - paramInt1;
/* 620 */       this.layoutCount++;
/*     */     } 
/*     */     
/* 623 */     return new TextLayout(textLine, this.fBaseline, this.fBaselineOffsets, this.fJustifyRatio);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextMeasurer(AttributedCharacterIterator paramAttributedCharacterIterator, FontRenderContext paramFontRenderContext)
/*     */   {
/* 629 */     this.formattedChars = 0;
/*     */     
/* 631 */     this.collectStats = false;
/*     */     this.fFrc = paramFontRenderContext;
/*     */     initAll(paramAttributedCharacterIterator); } private void printStats() {
/* 634 */     System.out.println("formattedChars: " + this.formattedChars);
/*     */     
/* 636 */     this.collectStats = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean wantStats = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean collectStats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertChar(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt) {
/* 663 */     if (this.collectStats) {
/* 664 */       printStats();
/*     */     }
/* 666 */     if (wantStats) {
/* 667 */       this.collectStats = true;
/*     */     }
/*     */     
/* 670 */     this.fStart = paramAttributedCharacterIterator.getBeginIndex();
/* 671 */     int i = paramAttributedCharacterIterator.getEndIndex();
/* 672 */     if (i - this.fStart != this.fChars.length + 1) {
/* 673 */       initAll(paramAttributedCharacterIterator);
/*     */     }
/*     */     
/* 676 */     char[] arrayOfChar = new char[i - this.fStart];
/* 677 */     int j = paramInt - this.fStart;
/* 678 */     System.arraycopy(this.fChars, 0, arrayOfChar, 0, j);
/*     */     
/* 680 */     char c = paramAttributedCharacterIterator.setIndex(paramInt);
/* 681 */     arrayOfChar[j] = c;
/* 682 */     System.arraycopy(this.fChars, j, arrayOfChar, j + 1, i - paramInt - 1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 687 */     this.fChars = arrayOfChar;
/*     */     
/* 689 */     if (this.fBidi != null || Bidi.requiresBidi(arrayOfChar, j, j + 1) || paramAttributedCharacterIterator
/* 690 */       .getAttribute(TextAttribute.BIDI_EMBEDDING) != null) {
/*     */       
/* 692 */       this.fBidi = new Bidi(paramAttributedCharacterIterator);
/* 693 */       if (this.fBidi.isLeftToRight()) {
/* 694 */         this.fBidi = null;
/*     */       }
/*     */     } 
/*     */     
/* 698 */     this.fParagraph = StyledParagraph.insertChar(paramAttributedCharacterIterator, this.fChars, paramInt, this.fParagraph);
/*     */ 
/*     */ 
/*     */     
/* 702 */     invalidateComponents();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteChar(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt) {
/* 729 */     this.fStart = paramAttributedCharacterIterator.getBeginIndex();
/* 730 */     int i = paramAttributedCharacterIterator.getEndIndex();
/* 731 */     if (i - this.fStart != this.fChars.length - 1) {
/* 732 */       initAll(paramAttributedCharacterIterator);
/*     */     }
/*     */     
/* 735 */     char[] arrayOfChar = new char[i - this.fStart];
/* 736 */     int j = paramInt - this.fStart;
/*     */     
/* 738 */     System.arraycopy(this.fChars, 0, arrayOfChar, 0, paramInt - this.fStart);
/* 739 */     System.arraycopy(this.fChars, j + 1, arrayOfChar, j, i - paramInt);
/* 740 */     this.fChars = arrayOfChar;
/*     */     
/* 742 */     if (this.fBidi != null) {
/* 743 */       this.fBidi = new Bidi(paramAttributedCharacterIterator);
/* 744 */       if (this.fBidi.isLeftToRight()) {
/* 745 */         this.fBidi = null;
/*     */       }
/*     */     } 
/*     */     
/* 749 */     this.fParagraph = StyledParagraph.deleteChar(paramAttributedCharacterIterator, this.fChars, paramInt, this.fParagraph);
/*     */ 
/*     */ 
/*     */     
/* 753 */     invalidateComponents();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   char[] getChars() {
/* 762 */     return this.fChars;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/font/TextMeasurer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */