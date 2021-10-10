/*      */ package java.awt.font;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.GeneralPath;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.text.AttributedString;
/*      */ import java.util.Map;
/*      */ import sun.font.AttributeValues;
/*      */ import sun.font.CoreMetrics;
/*      */ import sun.font.FontResolver;
/*      */ import sun.font.GraphicComponent;
/*      */ import sun.font.LayoutPathImpl;
/*      */ import sun.text.CodePointIterator;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class TextLayout
/*      */   implements Cloneable
/*      */ {
/*      */   private int characterCount;
/*      */   private boolean isVerticalLine = false;
/*      */   private byte baseline;
/*      */   private float[] baselineOffsets;
/*      */   private TextLine textLine;
/*  249 */   private TextLine.TextLineMetrics lineMetrics = null;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float visibleAdvance;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int hashCodeCache;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean cacheIsValid = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float justifyRatio;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final float ALREADY_JUSTIFIED = -53.9F;
/*      */ 
/*      */ 
/*      */   
/*      */   private static float dx;
/*      */ 
/*      */ 
/*      */   
/*      */   private static float dy;
/*      */ 
/*      */ 
/*      */   
/*  286 */   private Rectangle2D naturalBounds = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  292 */   private Rectangle2D boundsRect = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean caretsInLigaturesAreAllowed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class CaretPolicy
/*      */   {
/*      */     public TextHitInfo getStrongCaret(TextHitInfo param1TextHitInfo1, TextHitInfo param1TextHitInfo2, TextLayout param1TextLayout) {
/*  341 */       return param1TextLayout.getStrongHit(param1TextHitInfo1, param1TextHitInfo2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  353 */   public static final CaretPolicy DEFAULT_CARET_POLICY = new CaretPolicy();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextLayout(String paramString, Font paramFont, FontRenderContext paramFontRenderContext) {
/*  374 */     if (paramFont == null) {
/*  375 */       throw new IllegalArgumentException("Null font passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  378 */     if (paramString == null) {
/*  379 */       throw new IllegalArgumentException("Null string passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  382 */     if (paramString.length() == 0) {
/*  383 */       throw new IllegalArgumentException("Zero length string passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  386 */     Map<TextAttribute, ?> map = null;
/*  387 */     if (paramFont.hasLayoutAttributes()) {
/*  388 */       map = paramFont.getAttributes();
/*      */     }
/*      */     
/*  391 */     char[] arrayOfChar = paramString.toCharArray();
/*  392 */     if (sameBaselineUpTo(paramFont, arrayOfChar, 0, arrayOfChar.length) == arrayOfChar.length) {
/*  393 */       fastInit(arrayOfChar, paramFont, (Map)map, paramFontRenderContext);
/*      */     } else {
/*  395 */       AttributedString attributedString = (map == null) ? new AttributedString(paramString) : new AttributedString(paramString, (Map)map);
/*      */ 
/*      */       
/*  398 */       attributedString.addAttribute(TextAttribute.FONT, paramFont);
/*  399 */       standardInit(attributedString.getIterator(), arrayOfChar, paramFontRenderContext);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextLayout(String paramString, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, FontRenderContext paramFontRenderContext) {
/*  423 */     if (paramString == null) {
/*  424 */       throw new IllegalArgumentException("Null string passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  427 */     if (paramMap == null) {
/*  428 */       throw new IllegalArgumentException("Null map passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  431 */     if (paramString.length() == 0) {
/*  432 */       throw new IllegalArgumentException("Zero length string passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  435 */     char[] arrayOfChar = paramString.toCharArray();
/*  436 */     Font font = singleFont(arrayOfChar, 0, arrayOfChar.length, paramMap);
/*  437 */     if (font != null) {
/*  438 */       fastInit(arrayOfChar, font, paramMap, paramFontRenderContext);
/*      */     } else {
/*  440 */       AttributedString attributedString = new AttributedString(paramString, paramMap);
/*  441 */       standardInit(attributedString.getIterator(), arrayOfChar, paramFontRenderContext);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Font singleFont(char[] paramArrayOfchar, int paramInt1, int paramInt2, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap) {
/*  457 */     if (paramMap.get(TextAttribute.CHAR_REPLACEMENT) != null) {
/*  458 */       return null;
/*      */     }
/*      */     
/*  461 */     Font font = null;
/*      */     try {
/*  463 */       font = (Font)paramMap.get(TextAttribute.FONT);
/*      */     }
/*  465 */     catch (ClassCastException classCastException) {}
/*      */     
/*  467 */     if (font == null) {
/*  468 */       if (paramMap.get(TextAttribute.FAMILY) != null) {
/*  469 */         font = Font.getFont(paramMap);
/*  470 */         if (font.canDisplayUpTo(paramArrayOfchar, paramInt1, paramInt2) != -1) {
/*  471 */           return null;
/*      */         }
/*      */       } else {
/*  474 */         FontResolver fontResolver = FontResolver.getInstance();
/*  475 */         CodePointIterator codePointIterator = CodePointIterator.create(paramArrayOfchar, paramInt1, paramInt2);
/*  476 */         int i = fontResolver.nextFontRunIndex(codePointIterator);
/*  477 */         if (codePointIterator.charIndex() == paramInt2) {
/*  478 */           font = fontResolver.getFont(i, paramMap);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  483 */     if (sameBaselineUpTo(font, paramArrayOfchar, paramInt1, paramInt2) != paramInt2) {
/*  484 */       return null;
/*      */     }
/*      */     
/*  487 */     return font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextLayout(AttributedCharacterIterator paramAttributedCharacterIterator, FontRenderContext paramFontRenderContext) {
/*  506 */     if (paramAttributedCharacterIterator == null) {
/*  507 */       throw new IllegalArgumentException("Null iterator passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  510 */     int i = paramAttributedCharacterIterator.getBeginIndex();
/*  511 */     int j = paramAttributedCharacterIterator.getEndIndex();
/*  512 */     if (i == j) {
/*  513 */       throw new IllegalArgumentException("Zero length iterator passed to TextLayout constructor.");
/*      */     }
/*      */     
/*  516 */     int k = j - i;
/*  517 */     paramAttributedCharacterIterator.first();
/*  518 */     char[] arrayOfChar = new char[k];
/*  519 */     byte b = 0;
/*  520 */     char c = paramAttributedCharacterIterator.first();
/*  521 */     for (; c != Character.MAX_VALUE; 
/*  522 */       c = paramAttributedCharacterIterator.next())
/*      */     {
/*  524 */       arrayOfChar[b++] = c;
/*      */     }
/*      */     
/*  527 */     paramAttributedCharacterIterator.first();
/*  528 */     if (paramAttributedCharacterIterator.getRunLimit() == j) {
/*      */       
/*  530 */       Map<AttributedCharacterIterator.Attribute, Object> map = paramAttributedCharacterIterator.getAttributes();
/*  531 */       Font font = singleFont(arrayOfChar, 0, k, map);
/*  532 */       if (font != null) {
/*  533 */         fastInit(arrayOfChar, font, map, paramFontRenderContext);
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  538 */     standardInit(paramAttributedCharacterIterator, arrayOfChar, paramFontRenderContext);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   TextLayout(TextLine paramTextLine, byte paramByte, float[] paramArrayOffloat, float paramFloat) {
/*  558 */     this.characterCount = paramTextLine.characterCount();
/*  559 */     this.baseline = paramByte;
/*  560 */     this.baselineOffsets = paramArrayOffloat;
/*  561 */     this.textLine = paramTextLine;
/*  562 */     this.justifyRatio = paramFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void paragraphInit(byte paramByte, CoreMetrics paramCoreMetrics, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, char[] paramArrayOfchar) {
/*  572 */     this.baseline = paramByte;
/*      */ 
/*      */     
/*  575 */     this.baselineOffsets = TextLine.getNormalizedOffsets(paramCoreMetrics.baselineOffsets, this.baseline);
/*      */     
/*  577 */     this.justifyRatio = AttributeValues.getJustification(paramMap);
/*  578 */     NumericShaper numericShaper = AttributeValues.getNumericShaping(paramMap);
/*  579 */     if (numericShaper != null) {
/*  580 */       numericShaper.shape(paramArrayOfchar, 0, paramArrayOfchar.length);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void fastInit(char[] paramArrayOfchar, Font paramFont, Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap, FontRenderContext paramFontRenderContext) {
/*  596 */     this.isVerticalLine = false;
/*      */     
/*  598 */     LineMetrics lineMetrics = paramFont.getLineMetrics(paramArrayOfchar, 0, paramArrayOfchar.length, paramFontRenderContext);
/*  599 */     CoreMetrics coreMetrics = CoreMetrics.get(lineMetrics);
/*  600 */     byte b = (byte)coreMetrics.baselineIndex;
/*      */     
/*  602 */     if (paramMap == null) {
/*  603 */       this.baseline = b;
/*  604 */       this.baselineOffsets = coreMetrics.baselineOffsets;
/*  605 */       this.justifyRatio = 1.0F;
/*      */     } else {
/*  607 */       paragraphInit(b, coreMetrics, paramMap, paramArrayOfchar);
/*      */     } 
/*      */     
/*  610 */     this.characterCount = paramArrayOfchar.length;
/*      */     
/*  612 */     this.textLine = TextLine.fastCreateTextLine(paramFontRenderContext, paramArrayOfchar, paramFont, coreMetrics, paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void standardInit(AttributedCharacterIterator paramAttributedCharacterIterator, char[] paramArrayOfchar, FontRenderContext paramFontRenderContext) {
/*  622 */     this.characterCount = paramArrayOfchar.length;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  631 */     Map<AttributedCharacterIterator.Attribute, Object> map = paramAttributedCharacterIterator.getAttributes();
/*      */     
/*  633 */     boolean bool = TextLine.advanceToFirstFont(paramAttributedCharacterIterator);
/*      */     
/*  635 */     if (bool) {
/*  636 */       Font font = TextLine.getFontAtCurrentPos(paramAttributedCharacterIterator);
/*  637 */       int i = paramAttributedCharacterIterator.getIndex() - paramAttributedCharacterIterator.getBeginIndex();
/*  638 */       LineMetrics lineMetrics = font.getLineMetrics(paramArrayOfchar, i, i + 1, paramFontRenderContext);
/*  639 */       CoreMetrics coreMetrics = CoreMetrics.get(lineMetrics);
/*  640 */       paragraphInit((byte)coreMetrics.baselineIndex, coreMetrics, map, paramArrayOfchar);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  647 */       GraphicAttribute graphicAttribute = (GraphicAttribute)map.get(TextAttribute.CHAR_REPLACEMENT);
/*  648 */       byte b = getBaselineFromGraphic(graphicAttribute);
/*  649 */       CoreMetrics coreMetrics = GraphicComponent.createCoreMetrics(graphicAttribute);
/*  650 */       paragraphInit(b, coreMetrics, map, paramArrayOfchar);
/*      */     } 
/*      */ 
/*      */     
/*  654 */     this.textLine = TextLine.standardCreateTextLine(paramFontRenderContext, paramAttributedCharacterIterator, paramArrayOfchar, this.baselineOffsets);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void ensureCache() {
/*  663 */     if (!this.cacheIsValid) {
/*  664 */       buildCache();
/*      */     }
/*      */   }
/*      */   
/*      */   private void buildCache() {
/*  669 */     this.lineMetrics = this.textLine.getMetrics();
/*      */ 
/*      */     
/*  672 */     if (this.textLine.isDirectionLTR()) {
/*      */       
/*  674 */       int i = this.characterCount - 1;
/*  675 */       while (i != -1) {
/*  676 */         int j = this.textLine.visualToLogical(i);
/*  677 */         if (!this.textLine.isCharSpace(j)) {
/*      */           break;
/*      */         }
/*      */         
/*  681 */         i--;
/*      */       } 
/*      */       
/*  684 */       if (i == this.characterCount - 1) {
/*  685 */         this.visibleAdvance = this.lineMetrics.advance;
/*      */       }
/*  687 */       else if (i == -1) {
/*  688 */         this.visibleAdvance = 0.0F;
/*      */       } else {
/*      */         
/*  691 */         int j = this.textLine.visualToLogical(i);
/*  692 */         this
/*  693 */           .visibleAdvance = this.textLine.getCharLinePosition(j) + this.textLine.getCharAdvance(j);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  698 */       byte b = 0;
/*  699 */       while (b != this.characterCount) {
/*  700 */         int i = this.textLine.visualToLogical(b);
/*  701 */         if (!this.textLine.isCharSpace(i)) {
/*      */           break;
/*      */         }
/*      */         
/*  705 */         b++;
/*      */       } 
/*      */       
/*  708 */       if (b == this.characterCount) {
/*  709 */         this.visibleAdvance = 0.0F;
/*      */       }
/*  711 */       else if (b == 0) {
/*  712 */         this.visibleAdvance = this.lineMetrics.advance;
/*      */       } else {
/*      */         
/*  715 */         int i = this.textLine.visualToLogical(b);
/*  716 */         float f = this.textLine.getCharLinePosition(i);
/*  717 */         this.visibleAdvance = this.lineMetrics.advance - f;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  722 */     this.naturalBounds = null;
/*  723 */     this.boundsRect = null;
/*      */ 
/*      */     
/*  726 */     this.hashCodeCache = 0;
/*      */     
/*  728 */     this.cacheIsValid = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Rectangle2D getNaturalBounds() {
/*  736 */     ensureCache();
/*      */     
/*  738 */     if (this.naturalBounds == null) {
/*  739 */       this.naturalBounds = this.textLine.getItalicBounds();
/*      */     }
/*      */     
/*  742 */     return this.naturalBounds;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Object clone() {
/*      */     try {
/*  762 */       return super.clone();
/*      */     }
/*  764 */     catch (CloneNotSupportedException cloneNotSupportedException) {
/*  765 */       throw new InternalError(cloneNotSupportedException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkTextHit(TextHitInfo paramTextHitInfo) {
/*  774 */     if (paramTextHitInfo == null) {
/*  775 */       throw new IllegalArgumentException("TextHitInfo is null.");
/*      */     }
/*      */     
/*  778 */     if (paramTextHitInfo.getInsertionIndex() < 0 || paramTextHitInfo
/*  779 */       .getInsertionIndex() > this.characterCount) {
/*  780 */       throw new IllegalArgumentException("TextHitInfo is out of range");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextLayout getJustifiedLayout(float paramFloat) {
/*  801 */     if (paramFloat <= 0.0F) {
/*  802 */       throw new IllegalArgumentException("justificationWidth <= 0 passed to TextLayout.getJustifiedLayout()");
/*      */     }
/*      */     
/*  805 */     if (this.justifyRatio == -53.9F) {
/*  806 */       throw new Error("Can't justify again.");
/*      */     }
/*      */     
/*  809 */     ensureCache();
/*      */ 
/*      */     
/*  812 */     int i = this.characterCount;
/*  813 */     while (i > 0 && this.textLine.isCharWhitespace(i - 1)) {
/*  814 */       i--;
/*      */     }
/*      */     
/*  817 */     TextLine textLine = this.textLine.getJustifiedLine(paramFloat, this.justifyRatio, 0, i);
/*  818 */     if (textLine != null) {
/*  819 */       return new TextLayout(textLine, this.baseline, this.baselineOffsets, -53.9F);
/*      */     }
/*      */     
/*  822 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleJustify(float paramFloat) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getBaseline() {
/*  862 */     return this.baseline;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getBaselineOffsets() {
/*  881 */     float[] arrayOfFloat = new float[this.baselineOffsets.length];
/*  882 */     System.arraycopy(this.baselineOffsets, 0, arrayOfFloat, 0, arrayOfFloat.length);
/*  883 */     return arrayOfFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAdvance() {
/*  894 */     ensureCache();
/*  895 */     return this.lineMetrics.advance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getVisibleAdvance() {
/*  906 */     ensureCache();
/*  907 */     return this.visibleAdvance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAscent() {
/*  922 */     ensureCache();
/*  923 */     return this.lineMetrics.ascent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDescent() {
/*  937 */     ensureCache();
/*  938 */     return this.lineMetrics.descent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getLeading() {
/*  963 */     ensureCache();
/*  964 */     return this.lineMetrics.leading;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle2D getBounds() {
/*  978 */     ensureCache();
/*      */     
/*  980 */     if (this.boundsRect == null) {
/*  981 */       Rectangle2D rectangle2D = this.textLine.getVisualBounds();
/*  982 */       if (dx != 0.0F || dy != 0.0F) {
/*  983 */         rectangle2D.setRect(rectangle2D.getX() - dx, rectangle2D
/*  984 */             .getY() - dy, rectangle2D
/*  985 */             .getWidth(), rectangle2D
/*  986 */             .getHeight());
/*      */       }
/*  988 */       this.boundsRect = rectangle2D;
/*      */     } 
/*      */     
/*  991 */     Rectangle2D.Float float_ = new Rectangle2D.Float();
/*  992 */     float_.setRect(this.boundsRect);
/*      */     
/*  994 */     return float_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle getPixelBounds(FontRenderContext paramFontRenderContext, float paramFloat1, float paramFloat2) {
/* 1014 */     return this.textLine.getPixelBounds(paramFontRenderContext, paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLeftToRight() {
/* 1034 */     return this.textLine.isDirectionLTR();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isVertical() {
/* 1043 */     return this.isVerticalLine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCharacterCount() {
/* 1052 */     return this.characterCount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] getCaretInfo(int paramInt, Rectangle2D paramRectangle2D, float[] paramArrayOffloat) {
/*      */     float f1, f2, f3, f4;
/* 1132 */     if (paramInt == 0 || paramInt == this.characterCount) {
/*      */       float f7;
/*      */       
/*      */       int i;
/* 1136 */       if (paramInt == this.characterCount) {
/* 1137 */         i = this.textLine.visualToLogical(this.characterCount - 1);
/*      */         
/* 1139 */         f7 = this.textLine.getCharLinePosition(i) + this.textLine.getCharAdvance(i);
/*      */       } else {
/*      */         
/* 1142 */         i = this.textLine.visualToLogical(paramInt);
/* 1143 */         f7 = this.textLine.getCharLinePosition(i);
/*      */       } 
/* 1145 */       float f8 = this.textLine.getCharAngle(i);
/* 1146 */       float f9 = this.textLine.getCharShift(i);
/* 1147 */       f7 += f8 * f9;
/* 1148 */       f1 = f2 = f7 + f8 * this.textLine.getCharAscent(i);
/* 1149 */       f3 = f4 = f7 - f8 * this.textLine.getCharDescent(i);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1154 */       int i = this.textLine.visualToLogical(paramInt - 1);
/* 1155 */       float f7 = this.textLine.getCharAngle(i);
/*      */       
/* 1157 */       float f8 = this.textLine.getCharLinePosition(i) + this.textLine.getCharAdvance(i);
/* 1158 */       if (f7 != 0.0F) {
/* 1159 */         f8 += f7 * this.textLine.getCharShift(i);
/* 1160 */         f1 = f8 + f7 * this.textLine.getCharAscent(i);
/* 1161 */         f3 = f8 - f7 * this.textLine.getCharDescent(i);
/*      */       } else {
/*      */         
/* 1164 */         f1 = f3 = f8;
/*      */       } 
/*      */ 
/*      */       
/* 1168 */       i = this.textLine.visualToLogical(paramInt);
/* 1169 */       f7 = this.textLine.getCharAngle(i);
/* 1170 */       f8 = this.textLine.getCharLinePosition(i);
/* 1171 */       if (f7 != 0.0F) {
/* 1172 */         f8 += f7 * this.textLine.getCharShift(i);
/* 1173 */         f2 = f8 + f7 * this.textLine.getCharAscent(i);
/* 1174 */         f4 = f8 - f7 * this.textLine.getCharDescent(i);
/*      */       } else {
/*      */         
/* 1177 */         f2 = f4 = f8;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1182 */     float f5 = (f1 + f2) / 2.0F;
/* 1183 */     float f6 = (f3 + f4) / 2.0F;
/*      */     
/* 1185 */     if (paramArrayOffloat == null) {
/* 1186 */       paramArrayOffloat = new float[2];
/*      */     }
/*      */     
/* 1189 */     if (this.isVerticalLine) {
/* 1190 */       paramArrayOffloat[1] = (float)((f5 - f6) / paramRectangle2D.getWidth());
/* 1191 */       paramArrayOffloat[0] = (float)(f5 + paramArrayOffloat[1] * paramRectangle2D.getX());
/*      */     } else {
/*      */       
/* 1194 */       paramArrayOffloat[1] = (float)((f5 - f6) / paramRectangle2D.getHeight());
/* 1195 */       paramArrayOffloat[0] = (float)(f6 + paramArrayOffloat[1] * paramRectangle2D.getMaxY());
/*      */     } 
/*      */     
/* 1198 */     return paramArrayOffloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getCaretInfo(TextHitInfo paramTextHitInfo, Rectangle2D paramRectangle2D) {
/* 1219 */     ensureCache();
/* 1220 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1222 */     return getCaretInfoTestInternal(paramTextHitInfo, paramRectangle2D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float[] getCaretInfoTestInternal(TextHitInfo paramTextHitInfo, Rectangle2D paramRectangle2D) {
/*      */     double d1, d2, d3, d4;
/* 1233 */     ensureCache();
/* 1234 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1236 */     float[] arrayOfFloat = new float[6];
/*      */ 
/*      */     
/* 1239 */     getCaretInfo(hitToCaret(paramTextHitInfo), paramRectangle2D, arrayOfFloat);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1244 */     int i = paramTextHitInfo.getCharIndex();
/* 1245 */     boolean bool1 = paramTextHitInfo.isLeadingEdge();
/* 1246 */     boolean bool2 = this.textLine.isDirectionLTR();
/* 1247 */     boolean bool = !isVertical() ? true : false;
/*      */     
/* 1249 */     if (i == -1 || i == this.characterCount) {
/*      */ 
/*      */       
/* 1252 */       TextLine.TextLineMetrics textLineMetrics = this.textLine.getMetrics();
/* 1253 */       boolean bool3 = (bool2 == ((i == -1))) ? true : false;
/* 1254 */       double d = 0.0D;
/* 1255 */       if (bool) {
/* 1256 */         d1 = d3 = bool3 ? 0.0D : textLineMetrics.advance;
/* 1257 */         d2 = -textLineMetrics.ascent;
/* 1258 */         d4 = textLineMetrics.descent;
/*      */       } else {
/* 1260 */         d2 = d4 = bool3 ? 0.0D : textLineMetrics.advance;
/* 1261 */         d1 = textLineMetrics.descent;
/* 1262 */         d3 = textLineMetrics.ascent;
/*      */       } 
/*      */     } else {
/* 1265 */       CoreMetrics coreMetrics = this.textLine.getCoreMetricsAt(i);
/* 1266 */       double d5 = coreMetrics.italicAngle;
/* 1267 */       double d6 = this.textLine.getCharLinePosition(i, bool1);
/* 1268 */       if (coreMetrics.baselineIndex < 0) {
/*      */         
/* 1270 */         TextLine.TextLineMetrics textLineMetrics = this.textLine.getMetrics();
/* 1271 */         if (bool) {
/* 1272 */           d1 = d3 = d6;
/* 1273 */           if (coreMetrics.baselineIndex == -1) {
/* 1274 */             d2 = -textLineMetrics.ascent;
/* 1275 */             d4 = d2 + coreMetrics.height;
/*      */           } else {
/* 1277 */             d4 = textLineMetrics.descent;
/* 1278 */             d2 = d4 - coreMetrics.height;
/*      */           } 
/*      */         } else {
/* 1281 */           d2 = d4 = d6;
/* 1282 */           d1 = textLineMetrics.descent;
/* 1283 */           d3 = textLineMetrics.ascent;
/*      */         } 
/*      */       } else {
/*      */         
/* 1287 */         float f = this.baselineOffsets[coreMetrics.baselineIndex];
/* 1288 */         if (bool) {
/* 1289 */           d6 += d5 * coreMetrics.ssOffset;
/* 1290 */           d1 = d6 + d5 * coreMetrics.ascent;
/* 1291 */           d3 = d6 - d5 * coreMetrics.descent;
/* 1292 */           d2 = (f - coreMetrics.ascent);
/* 1293 */           d4 = (f + coreMetrics.descent);
/*      */         } else {
/* 1295 */           d6 -= d5 * coreMetrics.ssOffset;
/* 1296 */           d2 = d6 + d5 * coreMetrics.ascent;
/* 1297 */           d4 = d6 - d5 * coreMetrics.descent;
/* 1298 */           d1 = (f + coreMetrics.ascent);
/* 1299 */           d3 = (f + coreMetrics.descent);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1304 */     arrayOfFloat[2] = (float)d1;
/* 1305 */     arrayOfFloat[3] = (float)d2;
/* 1306 */     arrayOfFloat[4] = (float)d3;
/* 1307 */     arrayOfFloat[5] = (float)d4;
/*      */     
/* 1309 */     return arrayOfFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float[] getCaretInfo(TextHitInfo paramTextHitInfo) {
/* 1322 */     return getCaretInfo(paramTextHitInfo, getNaturalBounds());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int hitToCaret(TextHitInfo paramTextHitInfo) {
/* 1335 */     int i = paramTextHitInfo.getCharIndex();
/*      */     
/* 1337 */     if (i < 0)
/* 1338 */       return this.textLine.isDirectionLTR() ? 0 : this.characterCount; 
/* 1339 */     if (i >= this.characterCount) {
/* 1340 */       return this.textLine.isDirectionLTR() ? this.characterCount : 0;
/*      */     }
/*      */     
/* 1343 */     int j = this.textLine.logicalToVisual(i);
/*      */     
/* 1345 */     if (paramTextHitInfo.isLeadingEdge() != this.textLine.isCharLTR(i)) {
/* 1346 */       j++;
/*      */     }
/*      */     
/* 1349 */     return j;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private TextHitInfo caretToHit(int paramInt) {
/* 1362 */     if (paramInt == 0 || paramInt == this.characterCount) {
/*      */       
/* 1364 */       if (((paramInt == this.characterCount)) == this.textLine.isDirectionLTR()) {
/* 1365 */         return TextHitInfo.leading(this.characterCount);
/*      */       }
/*      */       
/* 1368 */       return TextHitInfo.trailing(-1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1373 */     int i = this.textLine.visualToLogical(paramInt);
/* 1374 */     boolean bool = this.textLine.isCharLTR(i);
/*      */     
/* 1376 */     return bool ? TextHitInfo.leading(i) : 
/* 1377 */       TextHitInfo.trailing(i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean caretIsValid(int paramInt) {
/* 1383 */     if (paramInt == this.characterCount || paramInt == 0) {
/* 1384 */       return true;
/*      */     }
/*      */     
/* 1387 */     int i = this.textLine.visualToLogical(paramInt);
/*      */     
/* 1389 */     if (!this.textLine.isCharLTR(i)) {
/* 1390 */       i = this.textLine.visualToLogical(paramInt - 1);
/* 1391 */       if (this.textLine.isCharLTR(i)) {
/* 1392 */         return true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1399 */     return this.textLine.caretAtOffsetIsValid(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHitInfo getNextRightHit(TextHitInfo paramTextHitInfo) {
/* 1412 */     ensureCache();
/* 1413 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1415 */     int i = hitToCaret(paramTextHitInfo);
/*      */     
/* 1417 */     if (i == this.characterCount) {
/* 1418 */       return null;
/*      */     }
/*      */     
/*      */     do {
/* 1422 */       i++;
/* 1423 */     } while (!caretIsValid(i));
/*      */     
/* 1425 */     return caretToHit(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHitInfo getNextRightHit(int paramInt, CaretPolicy paramCaretPolicy) {
/* 1444 */     if (paramInt < 0 || paramInt > this.characterCount) {
/* 1445 */       throw new IllegalArgumentException("Offset out of bounds in TextLayout.getNextRightHit()");
/*      */     }
/*      */     
/* 1448 */     if (paramCaretPolicy == null) {
/* 1449 */       throw new IllegalArgumentException("Null CaretPolicy passed to TextLayout.getNextRightHit()");
/*      */     }
/*      */     
/* 1452 */     TextHitInfo textHitInfo1 = TextHitInfo.afterOffset(paramInt);
/* 1453 */     TextHitInfo textHitInfo2 = textHitInfo1.getOtherHit();
/*      */     
/* 1455 */     TextHitInfo textHitInfo3 = getNextRightHit(paramCaretPolicy.getStrongCaret(textHitInfo1, textHitInfo2, this));
/*      */     
/* 1457 */     if (textHitInfo3 != null) {
/* 1458 */       TextHitInfo textHitInfo = getVisualOtherHit(textHitInfo3);
/* 1459 */       return paramCaretPolicy.getStrongCaret(textHitInfo, textHitInfo3, this);
/*      */     } 
/*      */     
/* 1462 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHitInfo getNextRightHit(int paramInt) {
/* 1481 */     return getNextRightHit(paramInt, DEFAULT_CARET_POLICY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHitInfo getNextLeftHit(TextHitInfo paramTextHitInfo) {
/* 1494 */     ensureCache();
/* 1495 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1497 */     int i = hitToCaret(paramTextHitInfo);
/*      */     
/* 1499 */     if (i == 0) {
/* 1500 */       return null;
/*      */     }
/*      */     
/*      */     do {
/* 1504 */       i--;
/* 1505 */     } while (!caretIsValid(i));
/*      */     
/* 1507 */     return caretToHit(i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHitInfo getNextLeftHit(int paramInt, CaretPolicy paramCaretPolicy) {
/* 1526 */     if (paramCaretPolicy == null) {
/* 1527 */       throw new IllegalArgumentException("Null CaretPolicy passed to TextLayout.getNextLeftHit()");
/*      */     }
/*      */     
/* 1530 */     if (paramInt < 0 || paramInt > this.characterCount) {
/* 1531 */       throw new IllegalArgumentException("Offset out of bounds in TextLayout.getNextLeftHit()");
/*      */     }
/*      */     
/* 1534 */     TextHitInfo textHitInfo1 = TextHitInfo.afterOffset(paramInt);
/* 1535 */     TextHitInfo textHitInfo2 = textHitInfo1.getOtherHit();
/*      */     
/* 1537 */     TextHitInfo textHitInfo3 = getNextLeftHit(paramCaretPolicy.getStrongCaret(textHitInfo1, textHitInfo2, this));
/*      */     
/* 1539 */     if (textHitInfo3 != null) {
/* 1540 */       TextHitInfo textHitInfo = getVisualOtherHit(textHitInfo3);
/* 1541 */       return paramCaretPolicy.getStrongCaret(textHitInfo, textHitInfo3, this);
/*      */     } 
/*      */     
/* 1544 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHitInfo getNextLeftHit(int paramInt) {
/* 1563 */     return getNextLeftHit(paramInt, DEFAULT_CARET_POLICY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHitInfo getVisualOtherHit(TextHitInfo paramTextHitInfo) {
/*      */     boolean bool1, bool2;
/* 1574 */     ensureCache();
/* 1575 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1577 */     int i = paramTextHitInfo.getCharIndex();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1582 */     if (i == -1 || i == this.characterCount) {
/*      */       int j;
/*      */       
/* 1585 */       if (this.textLine.isDirectionLTR() == ((i == -1))) {
/* 1586 */         j = 0;
/*      */       } else {
/*      */         
/* 1589 */         j = this.characterCount - 1;
/*      */       } 
/*      */       
/* 1592 */       bool1 = this.textLine.visualToLogical(j);
/*      */       
/* 1594 */       if (this.textLine.isDirectionLTR() == ((i == -1))) {
/*      */         
/* 1596 */         bool2 = this.textLine.isCharLTR(bool1);
/*      */       }
/*      */       else {
/*      */         
/* 1600 */         bool2 = !this.textLine.isCharLTR(bool1) ? true : false;
/*      */       } 
/*      */     } else {
/*      */       boolean bool;
/*      */       
/* 1605 */       int j = this.textLine.logicalToVisual(i);
/*      */ 
/*      */       
/* 1608 */       if (this.textLine.isCharLTR(i) == paramTextHitInfo.isLeadingEdge()) {
/* 1609 */         j--;
/* 1610 */         bool = false;
/*      */       } else {
/*      */         
/* 1613 */         j++;
/* 1614 */         bool = true;
/*      */       } 
/*      */       
/* 1617 */       if (j > -1 && j < this.characterCount) {
/* 1618 */         bool1 = this.textLine.visualToLogical(j);
/* 1619 */         bool2 = (bool == this.textLine.isCharLTR(bool1)) ? true : false;
/*      */       }
/*      */       else {
/*      */         
/* 1623 */         bool1 = (bool == this.textLine.isDirectionLTR()) ? this.characterCount : true;
/* 1624 */         bool2 = (bool1 == this.characterCount) ? true : false;
/*      */       } 
/*      */     } 
/*      */     
/* 1628 */     return bool2 ? TextHitInfo.leading(bool1) : 
/* 1629 */       TextHitInfo.trailing(bool1);
/*      */   }
/*      */   
/*      */   private double[] getCaretPath(TextHitInfo paramTextHitInfo, Rectangle2D paramRectangle2D) {
/* 1633 */     float[] arrayOfFloat = getCaretInfo(paramTextHitInfo, paramRectangle2D);
/* 1634 */     return new double[] { arrayOfFloat[2], arrayOfFloat[3], arrayOfFloat[4], arrayOfFloat[5] };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private double[] getCaretPath(int paramInt, Rectangle2D paramRectangle2D, boolean paramBoolean) {
/*      */     double d3, d4, d5, d6;
/* 1649 */     float[] arrayOfFloat = getCaretInfo(paramInt, paramRectangle2D, null);
/*      */     
/* 1651 */     double d1 = arrayOfFloat[0];
/* 1652 */     double d2 = arrayOfFloat[1];
/*      */ 
/*      */     
/* 1655 */     double d7 = -3141.59D, d8 = -2.7D;
/*      */     
/* 1657 */     double d9 = paramRectangle2D.getX();
/* 1658 */     double d10 = d9 + paramRectangle2D.getWidth();
/* 1659 */     double d11 = paramRectangle2D.getY();
/* 1660 */     double d12 = d11 + paramRectangle2D.getHeight();
/*      */     
/* 1662 */     boolean bool = false;
/*      */     
/* 1664 */     if (this.isVerticalLine) {
/*      */       
/* 1666 */       if (d2 >= 0.0D) {
/* 1667 */         d3 = d9;
/* 1668 */         d5 = d10;
/*      */       } else {
/*      */         
/* 1671 */         d5 = d9;
/* 1672 */         d3 = d10;
/*      */       } 
/*      */       
/* 1675 */       d4 = d1 + d3 * d2;
/* 1676 */       d6 = d1 + d5 * d2;
/*      */ 
/*      */ 
/*      */       
/* 1680 */       if (paramBoolean)
/*      */       {
/*      */         
/* 1683 */         d4 = d6 = d11;
/*      */ 
/*      */         
/* 1686 */         bool = true;
/* 1687 */         d4 = d11;
/* 1688 */         d8 = d11;
/* 1689 */         d7 = d5 + (d11 - d6) / d2;
/* 1690 */         if (d6 > d12) {
/* 1691 */           d6 = d12;
/*      */         }
/*      */ 
/*      */         
/* 1695 */         if (d6 > d12)
/*      */         {
/* 1697 */           d4 = d6 = d12;
/*      */ 
/*      */           
/* 1700 */           bool = true;
/* 1701 */           d6 = d12;
/* 1702 */           d8 = d12;
/* 1703 */           d7 = d3 + (d12 - d5) / d2;
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1711 */       if (d2 >= 0.0D) {
/* 1712 */         d4 = d12;
/* 1713 */         d6 = d11;
/*      */       } else {
/*      */         
/* 1716 */         d6 = d12;
/* 1717 */         d4 = d11;
/*      */       } 
/*      */       
/* 1720 */       d3 = d1 - d4 * d2;
/* 1721 */       d5 = d1 - d6 * d2;
/*      */ 
/*      */ 
/*      */       
/* 1725 */       if (paramBoolean) {
/*      */ 
/*      */         
/* 1728 */         d3 = d5 = d9;
/*      */ 
/*      */         
/* 1731 */         bool = true;
/* 1732 */         d3 = d9;
/* 1733 */         d7 = d9;
/* 1734 */         d8 = d6 - (d9 - d5) / d2;
/* 1735 */         if (d5 > d10) {
/* 1736 */           d5 = d10;
/*      */         }
/*      */ 
/*      */         
/* 1740 */         if (d5 > d10) {
/*      */           
/* 1742 */           d3 = d5 = d10;
/*      */ 
/*      */           
/* 1745 */           bool = true;
/* 1746 */           d5 = d10;
/* 1747 */           d7 = d10;
/* 1748 */           d8 = d4 - (d10 - d3) / d2;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1754 */     (new double[6])[0] = d3; (new double[6])[1] = d4; (new double[6])[2] = d7; (new double[6])[3] = d8; (new double[6])[4] = d5; (new double[6])[5] = d6; (new double[4])[0] = d3; (new double[4])[1] = d4; (new double[4])[2] = d5; (new double[4])[3] = d6; return bool ? new double[6] : new double[4];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static GeneralPath pathToShape(double[] paramArrayOfdouble, boolean paramBoolean, LayoutPathImpl paramLayoutPathImpl) {
/* 1761 */     GeneralPath generalPath = new GeneralPath(0, paramArrayOfdouble.length);
/* 1762 */     generalPath.moveTo((float)paramArrayOfdouble[0], (float)paramArrayOfdouble[1]);
/* 1763 */     for (byte b = 2; b < paramArrayOfdouble.length; b += 2) {
/* 1764 */       generalPath.lineTo((float)paramArrayOfdouble[b], (float)paramArrayOfdouble[b + 1]);
/*      */     }
/* 1766 */     if (paramBoolean) {
/* 1767 */       generalPath.closePath();
/*      */     }
/*      */     
/* 1770 */     if (paramLayoutPathImpl != null) {
/* 1771 */       generalPath = (GeneralPath)paramLayoutPathImpl.mapShape(generalPath);
/*      */     }
/* 1773 */     return generalPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getCaretShape(TextHitInfo paramTextHitInfo, Rectangle2D paramRectangle2D) {
/* 1787 */     ensureCache();
/* 1788 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 1790 */     if (paramRectangle2D == null) {
/* 1791 */       throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getCaret()");
/*      */     }
/*      */     
/* 1794 */     return pathToShape(getCaretPath(paramTextHitInfo, paramRectangle2D), false, this.textLine.getLayoutPath());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getCaretShape(TextHitInfo paramTextHitInfo) {
/* 1806 */     return getCaretShape(paramTextHitInfo, getNaturalBounds());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final TextHitInfo getStrongHit(TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2) {
/* 1823 */     byte b1 = getCharacterLevel(paramTextHitInfo1.getCharIndex());
/* 1824 */     byte b2 = getCharacterLevel(paramTextHitInfo2.getCharIndex());
/*      */     
/* 1826 */     if (b1 == b2) {
/* 1827 */       if (paramTextHitInfo2.isLeadingEdge() && !paramTextHitInfo1.isLeadingEdge()) {
/* 1828 */         return paramTextHitInfo2;
/*      */       }
/*      */       
/* 1831 */       return paramTextHitInfo1;
/*      */     } 
/*      */ 
/*      */     
/* 1835 */     return (b1 < b2) ? paramTextHitInfo1 : paramTextHitInfo2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte getCharacterLevel(int paramInt) {
/* 1849 */     if (paramInt < -1 || paramInt > this.characterCount) {
/* 1850 */       throw new IllegalArgumentException("Index is out of range in getCharacterLevel.");
/*      */     }
/*      */     
/* 1853 */     ensureCache();
/* 1854 */     if (paramInt == -1 || paramInt == this.characterCount) {
/* 1855 */       return (byte)(this.textLine.isDirectionLTR() ? 0 : 1);
/*      */     }
/*      */     
/* 1858 */     return this.textLine.getCharLevel(paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape[] getCaretShapes(int paramInt, Rectangle2D paramRectangle2D, CaretPolicy paramCaretPolicy) {
/* 1874 */     ensureCache();
/*      */     
/* 1876 */     if (paramInt < 0 || paramInt > this.characterCount) {
/* 1877 */       throw new IllegalArgumentException("Offset out of bounds in TextLayout.getCaretShapes()");
/*      */     }
/*      */     
/* 1880 */     if (paramRectangle2D == null) {
/* 1881 */       throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getCaretShapes()");
/*      */     }
/*      */     
/* 1884 */     if (paramCaretPolicy == null) {
/* 1885 */       throw new IllegalArgumentException("Null CaretPolicy passed to TextLayout.getCaretShapes()");
/*      */     }
/*      */     
/* 1888 */     Shape[] arrayOfShape = new Shape[2];
/*      */     
/* 1890 */     TextHitInfo textHitInfo1 = TextHitInfo.afterOffset(paramInt);
/*      */     
/* 1892 */     int i = hitToCaret(textHitInfo1);
/*      */     
/* 1894 */     LayoutPathImpl layoutPathImpl = this.textLine.getLayoutPath();
/* 1895 */     GeneralPath generalPath = pathToShape(getCaretPath(textHitInfo1, paramRectangle2D), false, layoutPathImpl);
/* 1896 */     TextHitInfo textHitInfo2 = textHitInfo1.getOtherHit();
/* 1897 */     int j = hitToCaret(textHitInfo2);
/*      */     
/* 1899 */     if (i == j) {
/* 1900 */       arrayOfShape[0] = generalPath;
/*      */     } else {
/*      */       
/* 1903 */       GeneralPath generalPath1 = pathToShape(getCaretPath(textHitInfo2, paramRectangle2D), false, layoutPathImpl);
/*      */       
/* 1905 */       TextHitInfo textHitInfo = paramCaretPolicy.getStrongCaret(textHitInfo1, textHitInfo2, this);
/* 1906 */       boolean bool = textHitInfo.equals(textHitInfo1);
/*      */       
/* 1908 */       if (bool) {
/* 1909 */         arrayOfShape[0] = generalPath;
/* 1910 */         arrayOfShape[1] = generalPath1;
/*      */       } else {
/*      */         
/* 1913 */         arrayOfShape[0] = generalPath1;
/* 1914 */         arrayOfShape[1] = generalPath;
/*      */       } 
/*      */     } 
/*      */     
/* 1918 */     return arrayOfShape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape[] getCaretShapes(int paramInt, Rectangle2D paramRectangle2D) {
/* 1934 */     return getCaretShapes(paramInt, paramRectangle2D, DEFAULT_CARET_POLICY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape[] getCaretShapes(int paramInt) {
/* 1949 */     return getCaretShapes(paramInt, getNaturalBounds(), DEFAULT_CARET_POLICY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GeneralPath boundingShape(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) {
/*      */     boolean bool;
/*      */     byte b1;
/*      */     int i;
/*      */     byte b2;
/* 1964 */     GeneralPath generalPath = pathToShape(paramArrayOfdouble1, false, null);
/*      */ 
/*      */ 
/*      */     
/* 1968 */     if (this.isVerticalLine) {
/* 1969 */       bool = (((paramArrayOfdouble1[1] > paramArrayOfdouble1[paramArrayOfdouble1.length - 1]) ? true : false) == ((paramArrayOfdouble2[1] > paramArrayOfdouble2[paramArrayOfdouble2.length - 1]) ? true : false)) ? true : false;
/*      */     }
/*      */     else {
/*      */       
/* 1973 */       bool = (((paramArrayOfdouble1[0] > paramArrayOfdouble1[paramArrayOfdouble1.length - 2]) ? true : false) == ((paramArrayOfdouble2[0] > paramArrayOfdouble2[paramArrayOfdouble2.length - 2]) ? true : false)) ? true : false;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1981 */     if (bool) {
/* 1982 */       b1 = paramArrayOfdouble2.length - 2;
/* 1983 */       i = -2;
/* 1984 */       b2 = -2;
/*      */     } else {
/*      */       
/* 1987 */       b1 = 0;
/* 1988 */       i = paramArrayOfdouble2.length;
/* 1989 */       b2 = 2;
/*      */     } 
/*      */     int j;
/* 1992 */     for (j = b1; j != i; j += b2) {
/* 1993 */       generalPath.lineTo((float)paramArrayOfdouble2[j], (float)paramArrayOfdouble2[j + 1]);
/*      */     }
/*      */     
/* 1996 */     generalPath.closePath();
/*      */     
/* 1998 */     return generalPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GeneralPath caretBoundingShape(int paramInt1, int paramInt2, Rectangle2D paramRectangle2D) {
/* 2007 */     if (paramInt1 > paramInt2) {
/* 2008 */       int i = paramInt1;
/* 2009 */       paramInt1 = paramInt2;
/* 2010 */       paramInt2 = i;
/*      */     } 
/*      */     
/* 2013 */     return boundingShape(getCaretPath(paramInt1, paramRectangle2D, true), 
/* 2014 */         getCaretPath(paramInt2, paramRectangle2D, true));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GeneralPath leftShape(Rectangle2D paramRectangle2D) {
/*      */     double[] arrayOfDouble1;
/* 2025 */     if (this.isVerticalLine) {
/*      */ 
/*      */       
/* 2028 */       arrayOfDouble1 = new double[] { paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getX() + paramRectangle2D.getWidth(), paramRectangle2D.getY() };
/*      */     }
/*      */     else {
/*      */       
/* 2032 */       arrayOfDouble1 = new double[] { paramRectangle2D.getX(), paramRectangle2D.getY() + paramRectangle2D.getHeight(), paramRectangle2D.getX(), paramRectangle2D.getY() };
/*      */     } 
/*      */     
/* 2035 */     double[] arrayOfDouble2 = getCaretPath(0, paramRectangle2D, true);
/*      */     
/* 2037 */     return boundingShape(arrayOfDouble1, arrayOfDouble2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GeneralPath rightShape(Rectangle2D paramRectangle2D) {
/*      */     double[] arrayOfDouble1;
/* 2046 */     if (this.isVerticalLine) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2051 */       arrayOfDouble1 = new double[] { paramRectangle2D.getX(), paramRectangle2D.getY() + paramRectangle2D.getHeight(), paramRectangle2D.getX() + paramRectangle2D.getWidth(), paramRectangle2D.getY() + paramRectangle2D.getHeight() };
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2058 */       arrayOfDouble1 = new double[] { paramRectangle2D.getX() + paramRectangle2D.getWidth(), paramRectangle2D.getY() + paramRectangle2D.getHeight(), paramRectangle2D.getX() + paramRectangle2D.getWidth(), paramRectangle2D.getY() };
/*      */     } 
/*      */ 
/*      */     
/* 2062 */     double[] arrayOfDouble2 = getCaretPath(this.characterCount, paramRectangle2D, true);
/*      */     
/* 2064 */     return boundingShape(arrayOfDouble2, arrayOfDouble1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int[] getLogicalRangesForVisualSelection(TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2) {
/* 2078 */     ensureCache();
/*      */     
/* 2080 */     checkTextHit(paramTextHitInfo1);
/* 2081 */     checkTextHit(paramTextHitInfo2);
/*      */ 
/*      */ 
/*      */     
/* 2085 */     boolean[] arrayOfBoolean = new boolean[this.characterCount];
/*      */     
/* 2087 */     int i = hitToCaret(paramTextHitInfo1);
/* 2088 */     int j = hitToCaret(paramTextHitInfo2);
/*      */     
/* 2090 */     if (i > j) {
/* 2091 */       int k = i;
/* 2092 */       i = j;
/* 2093 */       j = k;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2103 */     if (i < j) {
/* 2104 */       int k = i;
/* 2105 */       while (k < j) {
/* 2106 */         arrayOfBoolean[this.textLine.visualToLogical(k)] = true;
/* 2107 */         k++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2115 */     byte b1 = 0;
/* 2116 */     boolean bool = false;
/* 2117 */     for (byte b2 = 0; b2 < this.characterCount; b2++) {
/* 2118 */       if (arrayOfBoolean[b2] != bool) {
/* 2119 */         bool = !bool;
/* 2120 */         if (bool) {
/* 2121 */           b1++;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2126 */     int[] arrayOfInt = new int[b1 * 2];
/* 2127 */     b1 = 0;
/* 2128 */     bool = false;
/* 2129 */     for (byte b3 = 0; b3 < this.characterCount; b3++) {
/* 2130 */       if (arrayOfBoolean[b3] != bool) {
/* 2131 */         arrayOfInt[b1++] = b3;
/* 2132 */         bool = !bool;
/*      */       } 
/*      */     } 
/* 2135 */     if (bool) {
/* 2136 */       arrayOfInt[b1++] = this.characterCount;
/*      */     }
/*      */     
/* 2139 */     return arrayOfInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getVisualHighlightShape(TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2, Rectangle2D paramRectangle2D) {
/* 2182 */     ensureCache();
/*      */     
/* 2184 */     checkTextHit(paramTextHitInfo1);
/* 2185 */     checkTextHit(paramTextHitInfo2);
/*      */     
/* 2187 */     if (paramRectangle2D == null) {
/* 2188 */       throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getVisualHighlightShape()");
/*      */     }
/*      */     
/* 2191 */     GeneralPath generalPath = new GeneralPath(0);
/*      */     
/* 2193 */     int i = hitToCaret(paramTextHitInfo1);
/* 2194 */     int j = hitToCaret(paramTextHitInfo2);
/*      */     
/* 2196 */     generalPath.append(caretBoundingShape(i, j, paramRectangle2D), false);
/*      */ 
/*      */     
/* 2199 */     if (i == 0 || j == 0) {
/* 2200 */       GeneralPath generalPath1 = leftShape(paramRectangle2D);
/* 2201 */       if (!generalPath1.getBounds().isEmpty()) {
/* 2202 */         generalPath.append(generalPath1, false);
/*      */       }
/*      */     } 
/* 2205 */     if (i == this.characterCount || j == this.characterCount) {
/* 2206 */       GeneralPath generalPath1 = rightShape(paramRectangle2D);
/* 2207 */       if (!generalPath1.getBounds().isEmpty()) {
/* 2208 */         generalPath.append(generalPath1, false);
/*      */       }
/*      */     } 
/*      */     
/* 2212 */     LayoutPathImpl layoutPathImpl = this.textLine.getLayoutPath();
/* 2213 */     if (layoutPathImpl != null) {
/* 2214 */       generalPath = (GeneralPath)layoutPathImpl.mapShape(generalPath);
/*      */     }
/*      */     
/* 2217 */     return generalPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getVisualHighlightShape(TextHitInfo paramTextHitInfo1, TextHitInfo paramTextHitInfo2) {
/* 2232 */     return getVisualHighlightShape(paramTextHitInfo1, paramTextHitInfo2, getNaturalBounds());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getLogicalHighlightShape(int paramInt1, int paramInt2, Rectangle2D paramRectangle2D) {
/* 2277 */     if (paramRectangle2D == null) {
/* 2278 */       throw new IllegalArgumentException("Null Rectangle2D passed to TextLayout.getLogicalHighlightShape()");
/*      */     }
/*      */     
/* 2281 */     ensureCache();
/*      */     
/* 2283 */     if (paramInt1 > paramInt2) {
/* 2284 */       int i = paramInt1;
/* 2285 */       paramInt1 = paramInt2;
/* 2286 */       paramInt2 = i;
/*      */     } 
/*      */     
/* 2289 */     if (paramInt1 < 0 || paramInt2 > this.characterCount) {
/* 2290 */       throw new IllegalArgumentException("Range is invalid in TextLayout.getLogicalHighlightShape()");
/*      */     }
/*      */     
/* 2293 */     GeneralPath generalPath = new GeneralPath(0);
/*      */     
/* 2295 */     int[] arrayOfInt = new int[10];
/* 2296 */     byte b1 = 0;
/*      */     
/* 2298 */     if (paramInt1 < paramInt2) {
/* 2299 */       int i = paramInt1;
/*      */       do {
/* 2301 */         arrayOfInt[b1++] = hitToCaret(TextHitInfo.leading(i));
/* 2302 */         boolean bool = this.textLine.isCharLTR(i);
/*      */         
/*      */         do {
/* 2305 */           i++;
/* 2306 */         } while (i < paramInt2 && this.textLine.isCharLTR(i) == bool);
/*      */         
/* 2308 */         int j = i;
/* 2309 */         arrayOfInt[b1++] = hitToCaret(TextHitInfo.trailing(j - 1));
/*      */         
/* 2311 */         if (b1 != arrayOfInt.length)
/* 2312 */           continue;  int[] arrayOfInt1 = new int[arrayOfInt.length + 10];
/* 2313 */         System.arraycopy(arrayOfInt, 0, arrayOfInt1, 0, b1);
/* 2314 */         arrayOfInt = arrayOfInt1;
/*      */       }
/* 2316 */       while (i < paramInt2);
/*      */     } else {
/*      */       
/* 2319 */       b1 = 2;
/* 2320 */       arrayOfInt[1] = hitToCaret(TextHitInfo.leading(paramInt1)); arrayOfInt[0] = hitToCaret(TextHitInfo.leading(paramInt1));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2325 */     for (byte b2 = 0; b2 < b1; b2 += 2) {
/* 2326 */       generalPath.append(caretBoundingShape(arrayOfInt[b2], arrayOfInt[b2 + 1], paramRectangle2D), false);
/*      */     }
/*      */ 
/*      */     
/* 2330 */     if (paramInt1 != paramInt2) {
/* 2331 */       if ((this.textLine.isDirectionLTR() && paramInt1 == 0) || (!this.textLine.isDirectionLTR() && paramInt2 == this.characterCount)) {
/*      */         
/* 2333 */         GeneralPath generalPath1 = leftShape(paramRectangle2D);
/* 2334 */         if (!generalPath1.getBounds().isEmpty()) {
/* 2335 */           generalPath.append(generalPath1, false);
/*      */         }
/*      */       } 
/*      */       
/* 2339 */       if ((this.textLine.isDirectionLTR() && paramInt2 == this.characterCount) || (
/* 2340 */         !this.textLine.isDirectionLTR() && paramInt1 == 0)) {
/*      */         
/* 2342 */         GeneralPath generalPath1 = rightShape(paramRectangle2D);
/* 2343 */         if (!generalPath1.getBounds().isEmpty()) {
/* 2344 */           generalPath.append(generalPath1, false);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2349 */     LayoutPathImpl layoutPathImpl = this.textLine.getLayoutPath();
/* 2350 */     if (layoutPathImpl != null) {
/* 2351 */       generalPath = (GeneralPath)layoutPathImpl.mapShape(generalPath);
/*      */     }
/* 2353 */     return generalPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getLogicalHighlightShape(int paramInt1, int paramInt2) {
/* 2372 */     return getLogicalHighlightShape(paramInt1, paramInt2, getNaturalBounds());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getBlackBoxBounds(int paramInt1, int paramInt2) {
/* 2387 */     ensureCache();
/*      */     
/* 2389 */     if (paramInt1 > paramInt2) {
/* 2390 */       int i = paramInt1;
/* 2391 */       paramInt1 = paramInt2;
/* 2392 */       paramInt2 = i;
/*      */     } 
/*      */     
/* 2395 */     if (paramInt1 < 0 || paramInt2 > this.characterCount) {
/* 2396 */       throw new IllegalArgumentException("Invalid range passed to TextLayout.getBlackBoxBounds()");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2404 */     GeneralPath generalPath = new GeneralPath(1);
/*      */     
/* 2406 */     if (paramInt1 < this.characterCount) {
/* 2407 */       int i = paramInt1;
/* 2408 */       for (; i < paramInt2; 
/* 2409 */         i++) {
/*      */         
/* 2411 */         Rectangle2D rectangle2D = this.textLine.getCharBounds(i);
/* 2412 */         if (!rectangle2D.isEmpty()) {
/* 2413 */           generalPath.append(rectangle2D, false);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2418 */     if (dx != 0.0F || dy != 0.0F) {
/* 2419 */       AffineTransform affineTransform = AffineTransform.getTranslateInstance(dx, dy);
/* 2420 */       generalPath = (GeneralPath)affineTransform.createTransformedShape(generalPath);
/*      */     } 
/* 2422 */     LayoutPathImpl layoutPathImpl = this.textLine.getLayoutPath();
/* 2423 */     if (layoutPathImpl != null) {
/* 2424 */       generalPath = (GeneralPath)layoutPathImpl.mapShape(generalPath);
/*      */     }
/*      */ 
/*      */     
/* 2428 */     return generalPath;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float caretToPointDistance(float[] paramArrayOffloat, float paramFloat1, float paramFloat2) {
/* 2441 */     float f1 = this.isVerticalLine ? paramFloat2 : paramFloat1;
/* 2442 */     float f2 = this.isVerticalLine ? -paramFloat1 : paramFloat2;
/*      */     
/* 2444 */     return f1 - paramArrayOffloat[0] + f2 * paramArrayOffloat[1];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHitInfo hitTestChar(float paramFloat1, float paramFloat2, Rectangle2D paramRectangle2D) {
/* 2468 */     LayoutPathImpl layoutPathImpl = this.textLine.getLayoutPath();
/* 2469 */     boolean bool = false;
/* 2470 */     if (layoutPathImpl != null) {
/* 2471 */       Point2D.Float float_ = new Point2D.Float(paramFloat1, paramFloat2);
/* 2472 */       bool = layoutPathImpl.pointToPath(float_, float_);
/* 2473 */       paramFloat1 = float_.x;
/* 2474 */       paramFloat2 = float_.y;
/*      */     } 
/*      */     
/* 2477 */     if (isVertical()) {
/* 2478 */       if (paramFloat2 < paramRectangle2D.getMinY())
/* 2479 */         return TextHitInfo.leading(0); 
/* 2480 */       if (paramFloat2 >= paramRectangle2D.getMaxY()) {
/* 2481 */         return TextHitInfo.trailing(this.characterCount - 1);
/*      */       }
/*      */     } else {
/* 2484 */       if (paramFloat1 < paramRectangle2D.getMinX())
/* 2485 */         return isLeftToRight() ? TextHitInfo.leading(0) : TextHitInfo.trailing(this.characterCount - 1); 
/* 2486 */       if (paramFloat1 >= paramRectangle2D.getMaxX()) {
/* 2487 */         return isLeftToRight() ? TextHitInfo.trailing(this.characterCount - 1) : TextHitInfo.leading(0);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2501 */     double d = Double.MAX_VALUE;
/* 2502 */     byte b1 = 0;
/* 2503 */     int i = -1;
/* 2504 */     CoreMetrics coreMetrics = null;
/* 2505 */     float f1 = 0.0F, f2 = 0.0F, f3 = 0.0F, f4 = 0.0F, f5 = 0.0F, f6 = 0.0F;
/*      */     byte b2;
/* 2507 */     for (b2 = 0; b2 < this.characterCount; b2++) {
/* 2508 */       if (this.textLine.caretAtOffsetIsValid(b2)) {
/*      */ 
/*      */         
/* 2511 */         if (i == -1) {
/* 2512 */           i = b2;
/*      */         }
/* 2514 */         CoreMetrics coreMetrics1 = this.textLine.getCoreMetricsAt(b2);
/* 2515 */         if (coreMetrics1 != coreMetrics) {
/* 2516 */           coreMetrics = coreMetrics1;
/*      */           
/* 2518 */           if (coreMetrics1.baselineIndex == -1) {
/* 2519 */             f4 = -((this.textLine.getMetrics()).ascent - coreMetrics1.ascent) + coreMetrics1.ssOffset;
/* 2520 */           } else if (coreMetrics1.baselineIndex == -2) {
/* 2521 */             f4 = (this.textLine.getMetrics()).descent - coreMetrics1.descent + coreMetrics1.ssOffset;
/*      */           } else {
/* 2523 */             f4 = coreMetrics1.effectiveBaselineOffset(this.baselineOffsets) + coreMetrics1.ssOffset;
/*      */           } 
/* 2525 */           float f = (coreMetrics1.descent - coreMetrics1.ascent) / 2.0F - f4;
/* 2526 */           f5 = f * coreMetrics1.italicAngle;
/* 2527 */           f4 += f;
/* 2528 */           f6 = (f4 - paramFloat2) * (f4 - paramFloat2);
/*      */         } 
/* 2530 */         float f7 = this.textLine.getCharXPosition(b2);
/* 2531 */         float f8 = this.textLine.getCharAdvance(b2);
/* 2532 */         float f9 = f8 / 2.0F;
/* 2533 */         f7 += f9 - f5;
/*      */ 
/*      */         
/* 2536 */         double d1 = Math.sqrt((4.0F * (f7 - paramFloat1) * (f7 - paramFloat1) + f6));
/* 2537 */         if (d1 < d) {
/* 2538 */           d = d1;
/* 2539 */           b1 = b2;
/* 2540 */           i = -1;
/* 2541 */           f1 = f7; f2 = f4; f3 = coreMetrics1.italicAngle;
/*      */         } 
/*      */       } 
/* 2544 */     }  b2 = (paramFloat1 < f1 - (paramFloat2 - f2) * f3) ? 1 : 0;
/* 2545 */     boolean bool1 = (this.textLine.isCharLTR(b1) == b2) ? true : false;
/* 2546 */     if (i == -1) {
/* 2547 */       i = this.characterCount;
/*      */     }
/* 2549 */     return bool1 ? TextHitInfo.leading(b1) : 
/* 2550 */       TextHitInfo.trailing(i - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextHitInfo hitTestChar(float paramFloat1, float paramFloat2) {
/* 2568 */     return hitTestChar(paramFloat1, paramFloat2, getNaturalBounds());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2576 */     if (this.hashCodeCache == 0) {
/* 2577 */       ensureCache();
/* 2578 */       this.hashCodeCache = this.textLine.hashCode();
/*      */     } 
/* 2580 */     return this.hashCodeCache;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object paramObject) {
/* 2593 */     return (paramObject instanceof TextLayout && equals((TextLayout)paramObject));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(TextLayout paramTextLayout) {
/* 2607 */     if (paramTextLayout == null) {
/* 2608 */       return false;
/*      */     }
/* 2610 */     if (paramTextLayout == this) {
/* 2611 */       return true;
/*      */     }
/*      */     
/* 2614 */     ensureCache();
/* 2615 */     return this.textLine.equals(paramTextLayout.textLine);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2624 */     ensureCache();
/* 2625 */     return this.textLine.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(Graphics2D paramGraphics2D, float paramFloat1, float paramFloat2) {
/* 2643 */     if (paramGraphics2D == null) {
/* 2644 */       throw new IllegalArgumentException("Null Graphics2D passed to TextLayout.draw()");
/*      */     }
/*      */     
/* 2647 */     this.textLine.draw(paramGraphics2D, paramFloat1 - dx, paramFloat2 - dy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   TextLine getTextLineForTesting() {
/* 2655 */     return this.textLine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int sameBaselineUpTo(Font paramFont, char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 2667 */     return paramInt2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static byte getBaselineFromGraphic(GraphicAttribute paramGraphicAttribute) {
/* 2679 */     byte b = (byte)paramGraphicAttribute.getAlignment();
/*      */     
/* 2681 */     if (b == -2 || b == -1)
/*      */     {
/*      */       
/* 2684 */       return 0;
/*      */     }
/*      */     
/* 2687 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Shape getOutline(AffineTransform paramAffineTransform) {
/* 2700 */     ensureCache();
/* 2701 */     Shape shape = this.textLine.getOutline(paramAffineTransform);
/* 2702 */     LayoutPathImpl layoutPathImpl = this.textLine.getLayoutPath();
/* 2703 */     if (layoutPathImpl != null) {
/* 2704 */       shape = layoutPathImpl.mapShape(shape);
/*      */     }
/* 2706 */     return shape;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public LayoutPath getLayoutPath() {
/* 2716 */     return this.textLine.getLayoutPath();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void hitToPoint(TextHitInfo paramTextHitInfo, Point2D paramPoint2D) {
/*      */     boolean bool2;
/* 2735 */     if (paramTextHitInfo == null || paramPoint2D == null) {
/* 2736 */       throw new NullPointerException(((paramTextHitInfo == null) ? "hit" : "point") + " can't be null");
/*      */     }
/*      */     
/* 2739 */     ensureCache();
/* 2740 */     checkTextHit(paramTextHitInfo);
/*      */     
/* 2742 */     float f1 = 0.0F;
/* 2743 */     float f2 = 0.0F;
/*      */     
/* 2745 */     int i = paramTextHitInfo.getCharIndex();
/* 2746 */     boolean bool1 = paramTextHitInfo.isLeadingEdge();
/*      */     
/* 2748 */     if (i == -1 || i == this.textLine.characterCount()) {
/* 2749 */       bool2 = this.textLine.isDirectionLTR();
/* 2750 */       f1 = (bool2 == ((i == -1))) ? 0.0F : this.lineMetrics.advance;
/*      */     } else {
/* 2752 */       bool2 = this.textLine.isCharLTR(i);
/* 2753 */       f1 = this.textLine.getCharLinePosition(i, bool1);
/* 2754 */       f2 = this.textLine.getCharYPosition(i);
/*      */     } 
/* 2756 */     paramPoint2D.setLocation(f1, f2);
/* 2757 */     LayoutPathImpl layoutPathImpl = this.textLine.getLayoutPath();
/* 2758 */     if (layoutPathImpl != null)
/* 2759 */       layoutPathImpl.pathToPoint(paramPoint2D, (bool2 != bool1), paramPoint2D); 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/font/TextLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */