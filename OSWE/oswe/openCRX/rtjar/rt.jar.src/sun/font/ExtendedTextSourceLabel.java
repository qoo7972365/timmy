/*      */ package sun.font;
/*      */ 
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.Shape;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphJustificationInfo;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.util.Map;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class ExtendedTextSourceLabel
/*      */   extends ExtendedTextLabel
/*      */   implements Decoration.Label
/*      */ {
/*      */   TextSource source;
/*      */   private Decoration decorator;
/*      */   private Font font;
/*      */   private AffineTransform baseTX;
/*      */   private CoreMetrics cm;
/*      */   Rectangle2D lb;
/*      */   Rectangle2D ab;
/*      */   Rectangle2D vb;
/*      */   Rectangle2D ib;
/*      */   StandardGlyphVector gv;
/*      */   float[] charinfo;
/*      */   private static final int posx = 0;
/*      */   private static final int posy = 1;
/*      */   private static final int advx = 2;
/*      */   private static final int advy = 3;
/*      */   private static final int visx = 4;
/*      */   private static final int visy = 5;
/*      */   private static final int visw = 6;
/*      */   private static final int vish = 7;
/*      */   private static final int numvals = 8;
/*      */   
/*      */   public ExtendedTextSourceLabel(TextSource paramTextSource, Decoration paramDecoration) {
/*   78 */     this.source = paramTextSource;
/*   79 */     this.decorator = paramDecoration;
/*   80 */     finishInit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExtendedTextSourceLabel(TextSource paramTextSource, ExtendedTextSourceLabel paramExtendedTextSourceLabel, int paramInt) {
/*   90 */     this.source = paramTextSource;
/*   91 */     this.decorator = paramExtendedTextSourceLabel.decorator;
/*   92 */     finishInit();
/*      */   }
/*      */   
/*      */   private void finishInit() {
/*   96 */     this.font = this.source.getFont();
/*      */     
/*   98 */     Map<TextAttribute, ?> map = this.font.getAttributes();
/*   99 */     this.baseTX = AttributeValues.getBaselineTransform(map);
/*  100 */     if (this.baseTX == null) {
/*  101 */       this.cm = this.source.getCoreMetrics();
/*      */     } else {
/*  103 */       AffineTransform affineTransform = AttributeValues.getCharTransform(map);
/*  104 */       if (affineTransform == null) {
/*  105 */         affineTransform = new AffineTransform();
/*      */       }
/*  107 */       this.font = this.font.deriveFont(affineTransform);
/*      */       
/*  109 */       LineMetrics lineMetrics = this.font.getLineMetrics(this.source.getChars(), this.source.getStart(), this.source
/*  110 */           .getStart() + this.source.getLength(), this.source.getFRC());
/*  111 */       this.cm = CoreMetrics.get(lineMetrics);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle2D getLogicalBounds() {
/*  119 */     return getLogicalBounds(0.0F, 0.0F);
/*      */   }
/*      */   
/*      */   public Rectangle2D getLogicalBounds(float paramFloat1, float paramFloat2) {
/*  123 */     if (this.lb == null) {
/*  124 */       this.lb = createLogicalBounds();
/*      */     }
/*  126 */     return new Rectangle2D.Float((float)(this.lb.getX() + paramFloat1), 
/*  127 */         (float)(this.lb.getY() + paramFloat2), 
/*  128 */         (float)this.lb.getWidth(), 
/*  129 */         (float)this.lb.getHeight());
/*      */   }
/*      */   
/*      */   public float getAdvance() {
/*  133 */     if (this.lb == null) {
/*  134 */       this.lb = createLogicalBounds();
/*      */     }
/*  136 */     return (float)this.lb.getWidth();
/*      */   }
/*      */   
/*      */   public Rectangle2D getVisualBounds(float paramFloat1, float paramFloat2) {
/*  140 */     if (this.vb == null) {
/*  141 */       this.vb = this.decorator.getVisualBounds(this);
/*      */     }
/*  143 */     return new Rectangle2D.Float((float)(this.vb.getX() + paramFloat1), 
/*  144 */         (float)(this.vb.getY() + paramFloat2), 
/*  145 */         (float)this.vb.getWidth(), 
/*  146 */         (float)this.vb.getHeight());
/*      */   }
/*      */   
/*      */   public Rectangle2D getAlignBounds(float paramFloat1, float paramFloat2) {
/*  150 */     if (this.ab == null) {
/*  151 */       this.ab = createAlignBounds();
/*      */     }
/*  153 */     return new Rectangle2D.Float((float)(this.ab.getX() + paramFloat1), 
/*  154 */         (float)(this.ab.getY() + paramFloat2), 
/*  155 */         (float)this.ab.getWidth(), 
/*  156 */         (float)this.ab.getHeight());
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle2D getItalicBounds(float paramFloat1, float paramFloat2) {
/*  161 */     if (this.ib == null) {
/*  162 */       this.ib = createItalicBounds();
/*      */     }
/*  164 */     return new Rectangle2D.Float((float)(this.ib.getX() + paramFloat1), 
/*  165 */         (float)(this.ib.getY() + paramFloat2), 
/*  166 */         (float)this.ib.getWidth(), 
/*  167 */         (float)this.ib.getHeight());
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle getPixelBounds(FontRenderContext paramFontRenderContext, float paramFloat1, float paramFloat2) {
/*  172 */     return getGV().getPixelBounds(paramFontRenderContext, paramFloat1, paramFloat2);
/*      */   }
/*      */   
/*      */   public boolean isSimple() {
/*  176 */     return (this.decorator == Decoration.getPlainDecoration() && this.baseTX == null);
/*      */   }
/*      */ 
/*      */   
/*      */   public AffineTransform getBaselineTransform() {
/*  181 */     return this.baseTX;
/*      */   }
/*      */   
/*      */   public Shape handleGetOutline(float paramFloat1, float paramFloat2) {
/*  185 */     return getGV().getOutline(paramFloat1, paramFloat2);
/*      */   }
/*      */   
/*      */   public Shape getOutline(float paramFloat1, float paramFloat2) {
/*  189 */     return this.decorator.getOutline(this, paramFloat1, paramFloat2);
/*      */   }
/*      */   
/*      */   public void handleDraw(Graphics2D paramGraphics2D, float paramFloat1, float paramFloat2) {
/*  193 */     paramGraphics2D.drawGlyphVector(getGV(), paramFloat1, paramFloat2);
/*      */   }
/*      */   
/*      */   public void draw(Graphics2D paramGraphics2D, float paramFloat1, float paramFloat2) {
/*  197 */     this.decorator.drawTextAndDecorations(this, paramGraphics2D, paramFloat1, paramFloat2);
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
/*      */   protected Rectangle2D createLogicalBounds() {
/*  225 */     return getGV().getLogicalBounds();
/*      */   }
/*      */   
/*      */   public Rectangle2D handleGetVisualBounds() {
/*  229 */     return getGV().getVisualBounds();
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
/*      */   protected Rectangle2D createAlignBounds() {
/*  243 */     float[] arrayOfFloat = getCharinfo();
/*      */     
/*  245 */     float f1 = 0.0F;
/*  246 */     float f2 = -this.cm.ascent;
/*  247 */     float f3 = 0.0F;
/*  248 */     float f4 = this.cm.ascent + this.cm.descent;
/*      */     
/*  250 */     if (this.charinfo == null || this.charinfo.length == 0) {
/*  251 */       return new Rectangle2D.Float(f1, f2, f3, f4);
/*      */     }
/*      */     
/*  254 */     boolean bool = ((this.source.getLayoutFlags() & 0x8) == 0) ? true : false;
/*  255 */     int i = arrayOfFloat.length - 8;
/*  256 */     if (bool) {
/*  257 */       while (i > 0 && arrayOfFloat[i + 6] == 0.0F) {
/*  258 */         i -= 8;
/*      */       }
/*      */     }
/*      */     
/*  262 */     if (i >= 0) {
/*  263 */       byte b = 0;
/*  264 */       while (b < i && (arrayOfFloat[b + 2] == 0.0F || (!bool && arrayOfFloat[b + 6] == 0.0F))) {
/*  265 */         b += 8;
/*      */       }
/*      */       
/*  268 */       f1 = Math.max(0.0F, arrayOfFloat[b + 0]);
/*  269 */       f3 = arrayOfFloat[i + 0] + arrayOfFloat[i + 2] - f1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  290 */     return new Rectangle2D.Float(f1, f2, f3, f4);
/*      */   }
/*      */   
/*      */   public Rectangle2D createItalicBounds() {
/*  294 */     float f1 = this.cm.italicAngle;
/*      */     
/*  296 */     Rectangle2D rectangle2D = getLogicalBounds();
/*  297 */     float f2 = (float)rectangle2D.getMinX();
/*  298 */     float f3 = -this.cm.ascent;
/*  299 */     float f4 = (float)rectangle2D.getMaxX();
/*  300 */     float f5 = this.cm.descent;
/*  301 */     if (f1 != 0.0F) {
/*  302 */       if (f1 > 0.0F) {
/*  303 */         f2 -= f1 * (f5 - this.cm.ssOffset);
/*  304 */         f4 -= f1 * (f3 - this.cm.ssOffset);
/*      */       } else {
/*  306 */         f2 -= f1 * (f3 - this.cm.ssOffset);
/*  307 */         f4 -= f1 * (f5 - this.cm.ssOffset);
/*      */       } 
/*      */     }
/*  310 */     return new Rectangle2D.Float(f2, f3, f4 - f2, f5 - f3);
/*      */   }
/*      */   
/*      */   private final StandardGlyphVector getGV() {
/*  314 */     if (this.gv == null) {
/*  315 */       this.gv = createGV();
/*      */     }
/*      */     
/*  318 */     return this.gv;
/*      */   }
/*      */   
/*      */   protected StandardGlyphVector createGV() {
/*  322 */     FontRenderContext fontRenderContext = this.source.getFRC();
/*  323 */     int i = this.source.getLayoutFlags();
/*  324 */     char[] arrayOfChar = this.source.getChars();
/*  325 */     int j = this.source.getStart();
/*  326 */     int k = this.source.getLength();
/*      */     
/*  328 */     GlyphLayout glyphLayout = GlyphLayout.get(null);
/*  329 */     this.gv = glyphLayout.layout(this.font, fontRenderContext, arrayOfChar, j, k, i, null);
/*  330 */     GlyphLayout.done(glyphLayout);
/*      */     
/*  332 */     return this.gv;
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
/*      */   public int getNumCharacters() {
/*  348 */     return this.source.getLength();
/*      */   }
/*      */   
/*      */   public CoreMetrics getCoreMetrics() {
/*  352 */     return this.cm;
/*      */   }
/*      */   
/*      */   public float getCharX(int paramInt) {
/*  356 */     validate(paramInt);
/*  357 */     float[] arrayOfFloat = getCharinfo();
/*  358 */     int i = l2v(paramInt) * 8 + 0;
/*  359 */     if (arrayOfFloat == null || i >= arrayOfFloat.length) {
/*  360 */       return 0.0F;
/*      */     }
/*  362 */     return arrayOfFloat[i];
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharY(int paramInt) {
/*  367 */     validate(paramInt);
/*  368 */     float[] arrayOfFloat = getCharinfo();
/*  369 */     int i = l2v(paramInt) * 8 + 1;
/*  370 */     if (arrayOfFloat == null || i >= arrayOfFloat.length) {
/*  371 */       return 0.0F;
/*      */     }
/*  373 */     return arrayOfFloat[i];
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCharAdvance(int paramInt) {
/*  378 */     validate(paramInt);
/*  379 */     float[] arrayOfFloat = getCharinfo();
/*  380 */     int i = l2v(paramInt) * 8 + 2;
/*  381 */     if (arrayOfFloat == null || i >= arrayOfFloat.length) {
/*  382 */       return 0.0F;
/*      */     }
/*  384 */     return arrayOfFloat[i];
/*      */   }
/*      */ 
/*      */   
/*      */   public Rectangle2D handleGetCharVisualBounds(int paramInt) {
/*  389 */     validate(paramInt);
/*  390 */     float[] arrayOfFloat = getCharinfo();
/*  391 */     paramInt = l2v(paramInt) * 8;
/*  392 */     if (arrayOfFloat == null || paramInt + 7 >= arrayOfFloat.length) {
/*  393 */       return new Rectangle2D.Float();
/*      */     }
/*  395 */     return new Rectangle2D.Float(arrayOfFloat[paramInt + 4], arrayOfFloat[paramInt + 5], arrayOfFloat[paramInt + 6], arrayOfFloat[paramInt + 7]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rectangle2D getCharVisualBounds(int paramInt, float paramFloat1, float paramFloat2) {
/*  404 */     Rectangle2D rectangle2D = this.decorator.getCharVisualBounds(this, paramInt);
/*  405 */     if (paramFloat1 != 0.0F || paramFloat2 != 0.0F) {
/*  406 */       rectangle2D.setRect(rectangle2D.getX() + paramFloat1, rectangle2D
/*  407 */           .getY() + paramFloat2, rectangle2D
/*  408 */           .getWidth(), rectangle2D
/*  409 */           .getHeight());
/*      */     }
/*  411 */     return rectangle2D;
/*      */   }
/*      */   
/*      */   private void validate(int paramInt) {
/*  415 */     if (paramInt < 0)
/*  416 */       throw new IllegalArgumentException("index " + paramInt + " < 0"); 
/*  417 */     if (paramInt >= this.source.getLength()) {
/*  418 */       throw new IllegalArgumentException("index " + paramInt + " < " + this.source.getLength());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int logicalToVisual(int paramInt) {
/*  470 */     validate(paramInt);
/*  471 */     return l2v(paramInt);
/*      */   }
/*      */   
/*      */   public int visualToLogical(int paramInt) {
/*  475 */     validate(paramInt);
/*  476 */     return v2l(paramInt);
/*      */   }
/*      */   
/*      */   public int getLineBreakIndex(int paramInt, float paramFloat) {
/*  480 */     float[] arrayOfFloat = getCharinfo();
/*  481 */     int i = this.source.getLength();
/*  482 */     paramInt--;
/*  483 */     while (paramFloat >= 0.0F && ++paramInt < i) {
/*  484 */       int j = l2v(paramInt) * 8 + 2;
/*  485 */       if (j >= arrayOfFloat.length) {
/*      */         break;
/*      */       }
/*  488 */       float f = arrayOfFloat[j];
/*  489 */       paramFloat -= f;
/*      */     } 
/*      */     
/*  492 */     return paramInt;
/*      */   }
/*      */   
/*      */   public float getAdvanceBetween(int paramInt1, int paramInt2) {
/*  496 */     float f = 0.0F;
/*      */     
/*  498 */     float[] arrayOfFloat = getCharinfo();
/*  499 */     paramInt1--;
/*  500 */     while (++paramInt1 < paramInt2) {
/*  501 */       int i = l2v(paramInt1) * 8 + 2;
/*  502 */       if (i >= arrayOfFloat.length) {
/*      */         break;
/*      */       }
/*  505 */       f += arrayOfFloat[i];
/*      */     } 
/*      */     
/*  508 */     return f;
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
/*      */   public boolean caretAtOffsetIsValid(int paramInt) {
/*  520 */     if (paramInt == 0 || paramInt == this.source.getLength()) {
/*  521 */       return true;
/*      */     }
/*  523 */     char c = this.source.getChars()[this.source.getStart() + paramInt];
/*  524 */     if (c == '\t' || c == '\n' || c == '\r') {
/*  525 */       return true;
/*      */     }
/*  527 */     int i = l2v(paramInt);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  537 */     int j = i * 8 + 2;
/*  538 */     float[] arrayOfFloat = getCharinfo();
/*  539 */     if (arrayOfFloat == null || j >= arrayOfFloat.length) {
/*  540 */       return false;
/*      */     }
/*  542 */     return (arrayOfFloat[j] != 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   private final float[] getCharinfo() {
/*  547 */     if (this.charinfo == null) {
/*  548 */       this.charinfo = createCharinfo();
/*      */     }
/*  550 */     return this.charinfo;
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
/*      */   protected float[] createCharinfo() {
/*  619 */     StandardGlyphVector standardGlyphVector = getGV();
/*  620 */     float[] arrayOfFloat = null;
/*      */     try {
/*  622 */       arrayOfFloat = standardGlyphVector.getGlyphInfo();
/*      */     }
/*  624 */     catch (Exception exception) {
/*  625 */       System.out.println(this.source);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  634 */     int i = standardGlyphVector.getNumGlyphs();
/*  635 */     if (i == 0) {
/*  636 */       return arrayOfFloat;
/*      */     }
/*  638 */     int[] arrayOfInt = standardGlyphVector.getGlyphCharIndices(0, i, null);
/*      */     
/*  640 */     boolean bool1 = false;
/*  641 */     if (bool1) {
/*  642 */       System.err.println("number of glyphs: " + i);
/*  643 */       for (byte b = 0; b < i; b++) {
/*  644 */         System.err.println("g: " + b + ", x: " + arrayOfFloat[b * 8 + 0] + ", a: " + arrayOfFloat[b * 8 + 2] + ", n: " + arrayOfInt[b]);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  651 */     int j = arrayOfInt[0];
/*  652 */     int k = j;
/*  653 */     int m = 0;
/*  654 */     int n = 0;
/*  655 */     int i1 = 0;
/*  656 */     int i2 = 0;
/*  657 */     int i3 = 0;
/*  658 */     int i4 = i;
/*  659 */     byte b1 = 8;
/*  660 */     byte b2 = 1;
/*      */     
/*  662 */     boolean bool2 = ((this.source.getLayoutFlags() & 0x1) == 0) ? true : false;
/*  663 */     if (!bool2) {
/*  664 */       j = arrayOfInt[i - 1];
/*  665 */       k = j;
/*  666 */       m = 0;
/*  667 */       n = arrayOfFloat.length - 8;
/*  668 */       i1 = 0;
/*  669 */       i2 = arrayOfFloat.length - 8;
/*  670 */       i3 = i - 1;
/*  671 */       i4 = -1;
/*  672 */       b1 = -8;
/*  673 */       b2 = -1;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  693 */     float f1 = 0.0F, f2 = 0.0F, f3 = 0.0F, f4 = 0.0F, f5 = 0.0F, f6 = 0.0F;
/*  694 */     float f7 = 0.0F;
/*      */ 
/*      */     
/*  697 */     boolean bool3 = false;
/*      */     
/*  699 */     while (i3 != i4) {
/*      */       
/*  701 */       boolean bool = false;
/*  702 */       byte b = 0;
/*      */       
/*  704 */       j = arrayOfInt[i3];
/*  705 */       k = j;
/*      */ 
/*      */       
/*  708 */       i3 += b2;
/*  709 */       i2 += b1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  715 */       while (i3 != i4 && (arrayOfFloat[i2 + 2] == 0.0F || j != m || arrayOfInt[i3] <= k || k - j > b)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  721 */         if (!bool) {
/*  722 */           int i5 = i2 - b1;
/*      */           
/*  724 */           f1 = arrayOfFloat[i5 + 0];
/*  725 */           f2 = f1 + arrayOfFloat[i5 + 2];
/*  726 */           f3 = arrayOfFloat[i5 + 4];
/*  727 */           f4 = arrayOfFloat[i5 + 5];
/*  728 */           f5 = f3 + arrayOfFloat[i5 + 6];
/*  729 */           f6 = f4 + arrayOfFloat[i5 + 7];
/*      */           
/*  731 */           bool = true;
/*      */         } 
/*      */ 
/*      */         
/*  735 */         b++;
/*      */ 
/*      */         
/*  738 */         float f8 = arrayOfFloat[i2 + 2];
/*  739 */         if (f8 != 0.0F) {
/*  740 */           float f = arrayOfFloat[i2 + 0];
/*  741 */           f1 = Math.min(f1, f);
/*  742 */           f2 = Math.max(f2, f + f8);
/*      */         } 
/*      */ 
/*      */         
/*  746 */         float f9 = arrayOfFloat[i2 + 6];
/*  747 */         if (f9 != 0.0F) {
/*  748 */           float f10 = arrayOfFloat[i2 + 4];
/*  749 */           float f11 = arrayOfFloat[i2 + 5];
/*  750 */           f3 = Math.min(f3, f10);
/*  751 */           f4 = Math.min(f4, f11);
/*  752 */           f5 = Math.max(f5, f10 + f9);
/*  753 */           f6 = Math.max(f6, f11 + arrayOfFloat[i2 + 7]);
/*      */         } 
/*      */ 
/*      */         
/*  757 */         j = Math.min(j, arrayOfInt[i3]);
/*  758 */         k = Math.max(k, arrayOfInt[i3]);
/*      */ 
/*      */         
/*  761 */         i3 += b2;
/*  762 */         i2 += b1;
/*      */       } 
/*      */ 
/*      */       
/*  766 */       if (bool1) {
/*  767 */         System.out.println("minIndex = " + j + ", maxIndex = " + k);
/*      */       }
/*      */       
/*  770 */       m = k + 1;
/*      */ 
/*      */       
/*  773 */       arrayOfFloat[n + 1] = f7;
/*  774 */       arrayOfFloat[n + 3] = 0.0F;
/*      */       
/*  776 */       if (bool) {
/*      */         
/*  778 */         arrayOfFloat[n + 0] = f1;
/*  779 */         arrayOfFloat[n + 2] = f2 - f1;
/*  780 */         arrayOfFloat[n + 4] = f3;
/*  781 */         arrayOfFloat[n + 5] = f4;
/*  782 */         arrayOfFloat[n + 6] = f5 - f3;
/*  783 */         arrayOfFloat[n + 7] = f6 - f4;
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  788 */         if (k - j < b) {
/*  789 */           bool3 = true;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  795 */         if (j < k) {
/*  796 */           if (!bool2)
/*      */           {
/*  798 */             f2 = f1;
/*      */           }
/*  800 */           f5 -= f3;
/*  801 */           f6 -= f4;
/*      */           
/*  803 */           int i5 = j, i6 = n / 8;
/*      */           
/*  805 */           while (j < k) {
/*  806 */             j++;
/*  807 */             i1 += b2;
/*  808 */             n += b1;
/*      */             
/*  810 */             if ((n < 0 || n >= arrayOfFloat.length) && 
/*  811 */               bool1) System.out.println("minIndex = " + i5 + ", maxIndex = " + k + ", cp = " + i6);
/*      */ 
/*      */             
/*  814 */             arrayOfFloat[n + 0] = f2;
/*  815 */             arrayOfFloat[n + 1] = f7;
/*  816 */             arrayOfFloat[n + 2] = 0.0F;
/*  817 */             arrayOfFloat[n + 3] = 0.0F;
/*  818 */             arrayOfFloat[n + 4] = f3;
/*  819 */             arrayOfFloat[n + 5] = f4;
/*  820 */             arrayOfFloat[n + 6] = f5;
/*  821 */             arrayOfFloat[n + 7] = f6;
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  826 */         bool = false;
/*  827 */       } else if (bool3) {
/*      */         
/*  829 */         int i5 = i2 - b1;
/*      */         
/*  831 */         arrayOfFloat[n + 0] = arrayOfFloat[i5 + 0];
/*  832 */         arrayOfFloat[n + 2] = arrayOfFloat[i5 + 2];
/*  833 */         arrayOfFloat[n + 4] = arrayOfFloat[i5 + 4];
/*  834 */         arrayOfFloat[n + 5] = arrayOfFloat[i5 + 5];
/*  835 */         arrayOfFloat[n + 6] = arrayOfFloat[i5 + 6];
/*  836 */         arrayOfFloat[n + 7] = arrayOfFloat[i5 + 7];
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  841 */       n += b1;
/*  842 */       i1 += b2;
/*      */     } 
/*      */     
/*  845 */     if (bool3 && !bool2) {
/*      */ 
/*      */       
/*  848 */       n -= b1;
/*  849 */       System.arraycopy(arrayOfFloat, n, arrayOfFloat, 0, arrayOfFloat.length - n);
/*      */     } 
/*      */     
/*  852 */     if (bool1) {
/*  853 */       char[] arrayOfChar = this.source.getChars();
/*  854 */       int i5 = this.source.getStart();
/*  855 */       int i6 = this.source.getLength();
/*  856 */       System.out.println("char info for " + i6 + " characters");
/*  857 */       for (byte b = 0; b < i6 * 8;) {
/*  858 */         System.out.println(" ch: " + Integer.toHexString(arrayOfChar[i5 + v2l(b / 8)]) + " x: " + arrayOfFloat[b++] + " y: " + arrayOfFloat[b++] + " xa: " + arrayOfFloat[b++] + " ya: " + arrayOfFloat[b++] + " l: " + arrayOfFloat[b++] + " t: " + arrayOfFloat[b++] + " w: " + arrayOfFloat[b++] + " h: " + arrayOfFloat[b++]);
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
/*  870 */     return arrayOfFloat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int l2v(int paramInt) {
/*  879 */     return ((this.source.getLayoutFlags() & 0x1) == 0) ? paramInt : (this.source.getLength() - 1 - paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int v2l(int paramInt) {
/*  888 */     return ((this.source.getLayoutFlags() & 0x1) == 0) ? paramInt : (this.source.getLength() - 1 - paramInt);
/*      */   }
/*      */   
/*      */   public TextLineComponent getSubset(int paramInt1, int paramInt2, int paramInt3) {
/*  892 */     return new ExtendedTextSourceLabel(this.source.getSubSource(paramInt1, paramInt2 - paramInt1, paramInt3), this.decorator);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  897 */     return this.source.toString(false);
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
/*      */   public int getNumJustificationInfos() {
/*  931 */     return getGV().getNumGlyphs();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getJustificationInfos(GlyphJustificationInfo[] paramArrayOfGlyphJustificationInfo, int paramInt1, int paramInt2, int paramInt3) {
/*  941 */     StandardGlyphVector standardGlyphVector = getGV();
/*      */     
/*  943 */     float[] arrayOfFloat = getCharinfo();
/*      */     
/*  945 */     float f = standardGlyphVector.getFont().getSize2D();
/*      */     
/*  947 */     GlyphJustificationInfo glyphJustificationInfo1 = new GlyphJustificationInfo(0.0F, false, 3, 0.0F, 0.0F, false, 3, 0.0F, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  952 */     GlyphJustificationInfo glyphJustificationInfo2 = new GlyphJustificationInfo(f, true, 1, 0.0F, f, true, 1, 0.0F, f / 4.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  957 */     GlyphJustificationInfo glyphJustificationInfo3 = new GlyphJustificationInfo(f, true, 2, f, f, false, 3, 0.0F, 0.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  962 */     char[] arrayOfChar = this.source.getChars();
/*  963 */     int i = this.source.getStart();
/*      */ 
/*      */ 
/*      */     
/*  967 */     int j = standardGlyphVector.getNumGlyphs();
/*  968 */     int k = 0;
/*  969 */     int m = j;
/*  970 */     boolean bool = ((this.source.getLayoutFlags() & 0x1) == 0) ? true : false;
/*  971 */     if (paramInt2 != 0 || paramInt3 != this.source.getLength()) {
/*  972 */       if (bool) {
/*  973 */         k = paramInt2;
/*  974 */         m = paramInt3;
/*      */       } else {
/*  976 */         k = j - paramInt3;
/*  977 */         m = j - paramInt2;
/*      */       } 
/*      */     }
/*      */     
/*  981 */     for (byte b = 0; b < j; b++) {
/*  982 */       GlyphJustificationInfo glyphJustificationInfo = null;
/*  983 */       if (b >= k && b < m) {
/*  984 */         if (arrayOfFloat[b * 8 + 2] == 0.0F) {
/*  985 */           glyphJustificationInfo = glyphJustificationInfo1;
/*      */         } else {
/*  987 */           int n = v2l(b);
/*  988 */           char c = arrayOfChar[i + n];
/*  989 */           if (Character.isWhitespace(c)) {
/*  990 */             glyphJustificationInfo = glyphJustificationInfo2;
/*      */           }
/*  992 */           else if ((c >= '一' && c < 'ꀀ') || (c >= '가' && c < 'ힰ') || (c >= '豈' && c < 'ﬀ')) {
/*      */ 
/*      */ 
/*      */             
/*  996 */             glyphJustificationInfo = glyphJustificationInfo3;
/*      */           } else {
/*  998 */             glyphJustificationInfo = glyphJustificationInfo1;
/*      */           } 
/*      */         } 
/*      */       }
/* 1002 */       paramArrayOfGlyphJustificationInfo[paramInt1 + b] = glyphJustificationInfo;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TextLineComponent applyJustificationDeltas(float[] paramArrayOffloat, int paramInt, boolean[] paramArrayOfboolean) {
/* 1011 */     float[] arrayOfFloat1 = (float[])getCharinfo().clone();
/*      */ 
/*      */     
/* 1014 */     paramArrayOfboolean[0] = false;
/*      */ 
/*      */ 
/*      */     
/* 1018 */     StandardGlyphVector standardGlyphVector = (StandardGlyphVector)getGV().clone();
/* 1019 */     float[] arrayOfFloat2 = standardGlyphVector.getGlyphPositions(null);
/* 1020 */     int i = standardGlyphVector.getNumGlyphs();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1035 */     char[] arrayOfChar = this.source.getChars();
/* 1036 */     int j = this.source.getStart();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1042 */     float f = 0.0F;
/* 1043 */     for (byte b = 0; b < i; b++) {
/* 1044 */       if (Character.isWhitespace(arrayOfChar[j + v2l(b)])) {
/* 1045 */         arrayOfFloat2[b * 2] = arrayOfFloat2[b * 2] + f;
/*      */         
/* 1047 */         float f1 = paramArrayOffloat[paramInt + b * 2] + paramArrayOffloat[paramInt + b * 2 + 1];
/*      */         
/* 1049 */         arrayOfFloat1[b * 8 + 0] = arrayOfFloat1[b * 8 + 0] + f;
/* 1050 */         arrayOfFloat1[b * 8 + 4] = arrayOfFloat1[b * 8 + 4] + f;
/* 1051 */         arrayOfFloat1[b * 8 + 2] = arrayOfFloat1[b * 8 + 2] + f1;
/*      */         
/* 1053 */         f += f1;
/*      */       } else {
/* 1055 */         f += paramArrayOffloat[paramInt + b * 2];
/*      */         
/* 1057 */         arrayOfFloat2[b * 2] = arrayOfFloat2[b * 2] + f;
/* 1058 */         arrayOfFloat1[b * 8 + 0] = arrayOfFloat1[b * 8 + 0] + f;
/* 1059 */         arrayOfFloat1[b * 8 + 4] = arrayOfFloat1[b * 8 + 4] + f;
/*      */         
/* 1061 */         f += paramArrayOffloat[paramInt + b * 2 + 1];
/*      */       } 
/*      */     } 
/* 1064 */     arrayOfFloat2[i * 2] = arrayOfFloat2[i * 2] + f;
/*      */     
/* 1066 */     standardGlyphVector.setGlyphPositions(arrayOfFloat2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1076 */     ExtendedTextSourceLabel extendedTextSourceLabel = new ExtendedTextSourceLabel(this.source, this.decorator);
/* 1077 */     extendedTextSourceLabel.gv = standardGlyphVector;
/* 1078 */     extendedTextSourceLabel.charinfo = arrayOfFloat1;
/*      */     
/* 1080 */     return extendedTextSourceLabel;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/ExtendedTextSourceLabel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */