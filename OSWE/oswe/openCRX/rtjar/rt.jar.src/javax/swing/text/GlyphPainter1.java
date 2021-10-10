/*     */ package javax.swing.text;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.Toolkit;
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
/*     */ class GlyphPainter1
/*     */   extends GlyphView.GlyphPainter
/*     */ {
/*     */   FontMetrics metrics;
/*     */   
/*     */   public float getSpan(GlyphView paramGlyphView, int paramInt1, int paramInt2, TabExpander paramTabExpander, float paramFloat) {
/*  59 */     sync(paramGlyphView);
/*  60 */     Segment segment = paramGlyphView.getText(paramInt1, paramInt2);
/*  61 */     int[] arrayOfInt = getJustificationData(paramGlyphView);
/*  62 */     int i = Utilities.getTabbedTextWidth(paramGlyphView, segment, this.metrics, (int)paramFloat, paramTabExpander, paramInt1, arrayOfInt);
/*     */     
/*  64 */     SegmentCache.releaseSharedSegment(segment);
/*  65 */     return i;
/*     */   }
/*     */   
/*     */   public float getHeight(GlyphView paramGlyphView) {
/*  69 */     sync(paramGlyphView);
/*  70 */     return this.metrics.getHeight();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAscent(GlyphView paramGlyphView) {
/*  78 */     sync(paramGlyphView);
/*  79 */     return this.metrics.getAscent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getDescent(GlyphView paramGlyphView) {
/*  87 */     sync(paramGlyphView);
/*  88 */     return this.metrics.getDescent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void paint(GlyphView paramGlyphView, Graphics paramGraphics, Shape paramShape, int paramInt1, int paramInt2) {
/*  95 */     sync(paramGlyphView);
/*     */     
/*  97 */     TabExpander tabExpander = paramGlyphView.getTabExpander();
/*  98 */     Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/*     */ 
/*     */     
/* 101 */     int i = rectangle.x;
/* 102 */     int j = paramGlyphView.getStartOffset();
/* 103 */     int[] arrayOfInt = getJustificationData(paramGlyphView);
/* 104 */     if (j != paramInt1) {
/* 105 */       Segment segment1 = paramGlyphView.getText(j, paramInt1);
/* 106 */       int m = Utilities.getTabbedTextWidth(paramGlyphView, segment1, this.metrics, i, tabExpander, j, arrayOfInt);
/*     */       
/* 108 */       i += m;
/* 109 */       SegmentCache.releaseSharedSegment(segment1);
/*     */     } 
/*     */ 
/*     */     
/* 113 */     int k = rectangle.y + this.metrics.getHeight() - this.metrics.getDescent();
/*     */ 
/*     */     
/* 116 */     Segment segment = paramGlyphView.getText(paramInt1, paramInt2);
/* 117 */     paramGraphics.setFont(this.metrics.getFont());
/*     */     
/* 119 */     Utilities.drawTabbedText(paramGlyphView, segment, i, k, paramGraphics, tabExpander, paramInt1, arrayOfInt);
/*     */     
/* 121 */     SegmentCache.releaseSharedSegment(segment);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Shape modelToView(GlyphView paramGlyphView, int paramInt, Position.Bias paramBias, Shape paramShape) throws BadLocationException {
/* 127 */     sync(paramGlyphView);
/* 128 */     Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/* 129 */     int i = paramGlyphView.getStartOffset();
/* 130 */     int j = paramGlyphView.getEndOffset();
/* 131 */     TabExpander tabExpander = paramGlyphView.getTabExpander();
/*     */ 
/*     */     
/* 134 */     if (paramInt == j)
/*     */     {
/*     */       
/* 137 */       return new Rectangle(rectangle.x + rectangle.width, rectangle.y, 0, this.metrics
/* 138 */           .getHeight());
/*     */     }
/* 140 */     if (paramInt >= i && paramInt <= j) {
/*     */       
/* 142 */       Segment segment = paramGlyphView.getText(i, paramInt);
/* 143 */       int[] arrayOfInt = getJustificationData(paramGlyphView);
/* 144 */       int k = Utilities.getTabbedTextWidth(paramGlyphView, segment, this.metrics, rectangle.x, tabExpander, i, arrayOfInt);
/*     */       
/* 146 */       SegmentCache.releaseSharedSegment(segment);
/* 147 */       return new Rectangle(rectangle.x + k, rectangle.y, 0, this.metrics.getHeight());
/*     */     } 
/* 149 */     throw new BadLocationException("modelToView - can't convert", j);
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
/*     */   public int viewToModel(GlyphView paramGlyphView, float paramFloat1, float paramFloat2, Shape paramShape, Position.Bias[] paramArrayOfBias) {
/* 169 */     sync(paramGlyphView);
/* 170 */     Rectangle rectangle = (paramShape instanceof Rectangle) ? (Rectangle)paramShape : paramShape.getBounds();
/* 171 */     int i = paramGlyphView.getStartOffset();
/* 172 */     int j = paramGlyphView.getEndOffset();
/* 173 */     TabExpander tabExpander = paramGlyphView.getTabExpander();
/* 174 */     Segment segment = paramGlyphView.getText(i, j);
/* 175 */     int[] arrayOfInt = getJustificationData(paramGlyphView);
/* 176 */     int k = Utilities.getTabbedTextOffset(paramGlyphView, segment, this.metrics, rectangle.x, (int)paramFloat1, tabExpander, i, arrayOfInt);
/*     */ 
/*     */     
/* 179 */     SegmentCache.releaseSharedSegment(segment);
/* 180 */     int m = i + k;
/* 181 */     if (m == j)
/*     */     {
/*     */       
/* 184 */       m--;
/*     */     }
/* 186 */     paramArrayOfBias[0] = Position.Bias.Forward;
/* 187 */     return m;
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
/*     */   public int getBoundedPosition(GlyphView paramGlyphView, int paramInt, float paramFloat1, float paramFloat2) {
/* 209 */     sync(paramGlyphView);
/* 210 */     TabExpander tabExpander = paramGlyphView.getTabExpander();
/* 211 */     Segment segment = paramGlyphView.getText(paramInt, paramGlyphView.getEndOffset());
/* 212 */     int[] arrayOfInt = getJustificationData(paramGlyphView);
/* 213 */     int i = Utilities.getTabbedTextOffset(paramGlyphView, segment, this.metrics, (int)paramFloat1, (int)(paramFloat1 + paramFloat2), tabExpander, paramInt, false, arrayOfInt);
/*     */ 
/*     */     
/* 216 */     SegmentCache.releaseSharedSegment(segment);
/* 217 */     return paramInt + i;
/*     */   }
/*     */ 
/*     */   
/*     */   void sync(GlyphView paramGlyphView) {
/* 222 */     Font font = paramGlyphView.getFont();
/* 223 */     if (this.metrics == null || !font.equals(this.metrics.getFont())) {
/*     */       
/* 225 */       Container container = paramGlyphView.getContainer();
/* 226 */       this
/* 227 */         .metrics = (container != null) ? container.getFontMetrics(font) : Toolkit.getDefaultToolkit().getFontMetrics(font);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int[] getJustificationData(GlyphView paramGlyphView) {
/* 238 */     View view = paramGlyphView.getParent();
/* 239 */     int[] arrayOfInt = null;
/* 240 */     if (view instanceof ParagraphView.Row) {
/* 241 */       ParagraphView.Row row = (ParagraphView.Row)view;
/* 242 */       arrayOfInt = row.justificationData;
/*     */     } 
/* 244 */     return arrayOfInt;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/text/GlyphPainter1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */