/*      */ package sun.print;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Font;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.Image;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Polygon;
/*      */ import java.awt.Shape;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Arc2D;
/*      */ import java.awt.geom.Ellipse2D;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.PathIterator;
/*      */ import java.awt.geom.Point2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.RoundRectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.BufferedImageOp;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.DataBuffer;
/*      */ import java.awt.image.DataBufferInt;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.IndexColorModel;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.SampleModel;
/*      */ import java.awt.image.SinglePixelPackedSampleModel;
/*      */ import java.awt.image.VolatileImage;
/*      */ import java.awt.image.WritableRaster;
/*      */ import java.awt.print.PageFormat;
/*      */ import java.awt.print.Printable;
/*      */ import java.awt.print.PrinterException;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Map;
/*      */ import sun.awt.image.SunWritableRaster;
/*      */ import sun.awt.image.ToolkitImage;
/*      */ import sun.font.CompositeFont;
/*      */ import sun.font.Font2D;
/*      */ import sun.font.Font2DHandle;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.font.PhysicalFont;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class PathGraphics
/*      */   extends ProxyGraphics2D
/*      */ {
/*      */   private Printable mPainter;
/*      */   private PageFormat mPageFormat;
/*      */   private int mPageIndex;
/*      */   private boolean mCanRedraw;
/*      */   protected boolean printingGlyphVector;
/*      */   
/*      */   protected PathGraphics(Graphics2D paramGraphics2D, PrinterJob paramPrinterJob, Printable paramPrintable, PageFormat paramPageFormat, int paramInt, boolean paramBoolean) {
/*   97 */     super(paramGraphics2D, paramPrinterJob);
/*      */     
/*   99 */     this.mPainter = paramPrintable;
/*  100 */     this.mPageFormat = paramPageFormat;
/*  101 */     this.mPageIndex = paramInt;
/*  102 */     this.mCanRedraw = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Printable getPrintable() {
/*  110 */     return this.mPainter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PageFormat getPageFormat() {
/*  118 */     return this.mPageFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getPageIndex() {
/*  125 */     return this.mPageIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canDoRedraws() {
/*  136 */     return this.mCanRedraw;
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
/*      */   public abstract void redrawRegion(Rectangle2D paramRectangle2D, double paramDouble1, double paramDouble2, Shape paramShape, AffineTransform paramAffineTransform) throws PrinterException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  160 */     Paint paint = getPaint();
/*      */     
/*      */     try {
/*  163 */       AffineTransform affineTransform = getTransform();
/*  164 */       if (getClip() != null) {
/*  165 */         deviceClip(getClip().getPathIterator(affineTransform));
/*      */       }
/*      */       
/*  168 */       deviceDrawLine(paramInt1, paramInt2, paramInt3, paramInt4, (Color)paint);
/*      */     }
/*  170 */     catch (ClassCastException classCastException) {
/*  171 */       throw new IllegalArgumentException("Expected a Color instance");
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
/*      */   public void drawRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  194 */     Paint paint = getPaint();
/*      */     
/*      */     try {
/*  197 */       AffineTransform affineTransform = getTransform();
/*  198 */       if (getClip() != null) {
/*  199 */         deviceClip(getClip().getPathIterator(affineTransform));
/*      */       }
/*      */       
/*  202 */       deviceFrameRect(paramInt1, paramInt2, paramInt3, paramInt4, (Color)paint);
/*      */     }
/*  204 */     catch (ClassCastException classCastException) {
/*  205 */       throw new IllegalArgumentException("Expected a Color instance");
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
/*      */   public void fillRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  231 */     Paint paint = getPaint();
/*      */     
/*      */     try {
/*  234 */       AffineTransform affineTransform = getTransform();
/*  235 */       if (getClip() != null) {
/*  236 */         deviceClip(getClip().getPathIterator(affineTransform));
/*      */       }
/*      */       
/*  239 */       deviceFillRect(paramInt1, paramInt2, paramInt3, paramInt4, (Color)paint);
/*      */     }
/*  241 */     catch (ClassCastException classCastException) {
/*  242 */       throw new IllegalArgumentException("Expected a Color instance");
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
/*      */   public void clearRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  267 */     fill(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4), getBackground());
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
/*      */   public void drawRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  289 */     draw(new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
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
/*      */   public void fillRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  314 */     fill(new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
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
/*      */   public void drawOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  338 */     draw(new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
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
/*      */   public void fillOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  354 */     fill(new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
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
/*      */   public void drawArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  396 */     draw(new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 0));
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
/*      */   public void fillArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  441 */     fill(new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 2));
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
/*      */   public void drawPolyline(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  465 */     if (paramInt > 0) {
/*  466 */       float f1 = paramArrayOfint1[0];
/*  467 */       float f2 = paramArrayOfint2[0];
/*  468 */       for (byte b = 1; b < paramInt; b++) {
/*  469 */         float f3 = paramArrayOfint1[b];
/*  470 */         float f4 = paramArrayOfint2[b];
/*  471 */         draw(new Line2D.Float(f1, f2, f3, f4));
/*  472 */         f1 = f3;
/*  473 */         f2 = f4;
/*      */       } 
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
/*      */   public void drawPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  502 */     draw(new Polygon(paramArrayOfint1, paramArrayOfint2, paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawPolygon(Polygon paramPolygon) {
/*  513 */     draw(paramPolygon);
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
/*      */   public void fillPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  539 */     fill(new Polygon(paramArrayOfint1, paramArrayOfint2, paramInt));
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
/*      */   public void fillPolygon(Polygon paramPolygon) {
/*  554 */     fill(paramPolygon);
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
/*      */   public void drawString(String paramString, int paramInt1, int paramInt2) {
/*  570 */     drawString(paramString, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void drawString(String paramString, float paramFloat1, float paramFloat2) {
/*  574 */     if (paramString.length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  578 */     TextLayout textLayout = new TextLayout(paramString, getFont(), getFontRenderContext());
/*  579 */     textLayout.draw(this, paramFloat1, paramFloat2);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawString(String paramString, float paramFloat1, float paramFloat2, Font paramFont, FontRenderContext paramFontRenderContext, float paramFloat3) {
/*  584 */     TextLayout textLayout = new TextLayout(paramString, paramFont, paramFontRenderContext);
/*      */ 
/*      */     
/*  587 */     Shape shape = textLayout.getOutline(AffineTransform.getTranslateInstance(paramFloat1, paramFloat2));
/*  588 */     fill(shape);
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
/*      */   public void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2) {
/*  605 */     drawString(paramAttributedCharacterIterator, paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */   public void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, float paramFloat1, float paramFloat2) {
/*  609 */     if (paramAttributedCharacterIterator == null) {
/*  610 */       throw new NullPointerException("attributedcharacteriterator is null");
/*      */     }
/*      */ 
/*      */     
/*  614 */     TextLayout textLayout = new TextLayout(paramAttributedCharacterIterator, getFontRenderContext());
/*  615 */     textLayout.draw(this, paramFloat1, paramFloat2);
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
/*      */   public void drawGlyphVector(GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/*  642 */     if (this.printingGlyphVector) {
/*  643 */       assert !this.printingGlyphVector;
/*  644 */       fill(paramGlyphVector.getOutline(paramFloat1, paramFloat2));
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/*  649 */       this.printingGlyphVector = true;
/*  650 */       if (RasterPrinterJob.shapeTextProp || 
/*  651 */         !printedSimpleGlyphVector(paramGlyphVector, paramFloat1, paramFloat2)) {
/*  652 */         fill(paramGlyphVector.getOutline(paramFloat1, paramFloat2));
/*      */       }
/*      */     } finally {
/*  655 */       this.printingGlyphVector = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*  660 */   protected static SoftReference<Hashtable<Font2DHandle, Object>> fontMapRef = new SoftReference<>(null);
/*      */   
/*      */   protected int platformFontCount(Font paramFont, String paramString) {
/*  663 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean printGlyphVector(GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/*  672 */     return false;
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
/*      */   boolean printedSimpleGlyphVector(GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/*      */     Hashtable<Object, Object> hashtable;
/*  690 */     int i = paramGlyphVector.getLayoutFlags();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  698 */     if (i != 0 && i != 2) {
/*  699 */       return printGlyphVector(paramGlyphVector, paramFloat1, paramFloat2);
/*      */     }
/*      */     
/*  702 */     Font font = paramGlyphVector.getFont();
/*  703 */     Font2D font2D = FontUtilities.getFont2D(font);
/*  704 */     if (font2D.handle.font2D != font2D)
/*      */     {
/*  706 */       return false;
/*      */     }
/*      */     
/*  709 */     synchronized (PathGraphics.class) {
/*  710 */       hashtable = (Hashtable)fontMapRef.get();
/*  711 */       if (hashtable == null) {
/*  712 */         hashtable = new Hashtable<>();
/*  713 */         fontMapRef = new SoftReference(hashtable);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  718 */     int j = paramGlyphVector.getNumGlyphs();
/*  719 */     int[] arrayOfInt1 = paramGlyphVector.getGlyphCodes(0, j, null);
/*      */     
/*  721 */     char[] arrayOfChar1 = null;
/*  722 */     char[][] arrayOfChar = (char[][])null;
/*  723 */     CompositeFont compositeFont = null;
/*      */ 
/*      */     
/*  726 */     synchronized (hashtable) {
/*  727 */       if (font2D instanceof CompositeFont) {
/*  728 */         compositeFont = (CompositeFont)font2D;
/*  729 */         int m = compositeFont.getNumSlots();
/*  730 */         arrayOfChar = (char[][])hashtable.get(font2D.handle);
/*  731 */         if (arrayOfChar == null) {
/*  732 */           arrayOfChar = new char[m][];
/*  733 */           hashtable.put(font2D.handle, arrayOfChar);
/*      */         } 
/*  735 */         for (byte b = 0; b < j; b++) {
/*  736 */           int n = arrayOfInt1[b] >>> 24;
/*  737 */           if (n >= m) {
/*  738 */             return false;
/*      */           }
/*  740 */           if (arrayOfChar[n] == null) {
/*  741 */             PhysicalFont physicalFont = compositeFont.getSlotFont(n);
/*  742 */             char[] arrayOfChar3 = (char[])hashtable.get(physicalFont.handle);
/*  743 */             if (arrayOfChar3 == null) {
/*  744 */               arrayOfChar3 = getGlyphToCharMapForFont(physicalFont);
/*      */             }
/*  746 */             arrayOfChar[n] = arrayOfChar3;
/*      */           } 
/*      */         } 
/*      */       } else {
/*  750 */         arrayOfChar1 = (char[])hashtable.get(font2D.handle);
/*  751 */         if (arrayOfChar1 == null) {
/*  752 */           arrayOfChar1 = getGlyphToCharMapForFont(font2D);
/*  753 */           hashtable.put(font2D.handle, arrayOfChar1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  758 */     char[] arrayOfChar2 = new char[j];
/*  759 */     if (compositeFont != null) {
/*  760 */       for (byte b = 0; b < j; b++) {
/*  761 */         char c; int m = arrayOfInt1[b];
/*  762 */         char[] arrayOfChar3 = arrayOfChar[m >>> 24];
/*  763 */         m &= 0xFFFFFF;
/*  764 */         if (arrayOfChar3 == null) {
/*  765 */           return false;
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  783 */         if (m == 65535)
/*  784 */         { c = '\n'; }
/*  785 */         else { if (m < 0 || m >= arrayOfChar3.length) {
/*  786 */             return false;
/*      */           }
/*  788 */           c = arrayOfChar3[m]; }
/*      */         
/*  790 */         if (c != Character.MAX_VALUE) {
/*  791 */           arrayOfChar2[b] = c;
/*      */         } else {
/*  793 */           return false;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  797 */       for (byte b = 0; b < j; b++) {
/*  798 */         char c; int m = arrayOfInt1[b];
/*      */         
/*  800 */         if (m == 65535)
/*  801 */         { c = '\n'; }
/*  802 */         else { if (m < 0 || m >= arrayOfChar1.length) {
/*  803 */             return false;
/*      */           }
/*  805 */           c = arrayOfChar1[m]; }
/*      */         
/*  807 */         if (c != Character.MAX_VALUE) {
/*  808 */           arrayOfChar2[b] = c;
/*      */         } else {
/*  810 */           return false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  815 */     FontRenderContext fontRenderContext1 = paramGlyphVector.getFontRenderContext();
/*  816 */     GlyphVector glyphVector = font.createGlyphVector(fontRenderContext1, arrayOfChar2);
/*  817 */     if (glyphVector.getNumGlyphs() != j) {
/*  818 */       return printGlyphVector(paramGlyphVector, paramFloat1, paramFloat2);
/*      */     }
/*  820 */     int[] arrayOfInt2 = glyphVector.getGlyphCodes(0, j, null);
/*      */ 
/*      */ 
/*      */     
/*  824 */     for (byte b1 = 0; b1 < j; b1++) {
/*  825 */       if (arrayOfInt1[b1] != arrayOfInt2[b1]) {
/*  826 */         return printGlyphVector(paramGlyphVector, paramFloat1, paramFloat2);
/*      */       }
/*      */     } 
/*      */     
/*  830 */     FontRenderContext fontRenderContext2 = getFontRenderContext();
/*  831 */     boolean bool = fontRenderContext1.equals(fontRenderContext2);
/*      */ 
/*      */ 
/*      */     
/*  835 */     if (!bool && fontRenderContext1
/*  836 */       .usesFractionalMetrics() == fontRenderContext2.usesFractionalMetrics()) {
/*  837 */       AffineTransform affineTransform1 = fontRenderContext1.getTransform();
/*  838 */       AffineTransform affineTransform2 = getTransform();
/*  839 */       double[] arrayOfDouble1 = new double[4];
/*  840 */       double[] arrayOfDouble2 = new double[4];
/*  841 */       affineTransform1.getMatrix(arrayOfDouble1);
/*  842 */       affineTransform2.getMatrix(arrayOfDouble2);
/*  843 */       bool = true;
/*  844 */       for (byte b = 0; b < 4; b++) {
/*  845 */         if (arrayOfDouble1[b] != arrayOfDouble2[b]) {
/*  846 */           bool = false;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  852 */     String str = new String(arrayOfChar2, 0, j);
/*  853 */     int k = platformFontCount(font, str);
/*  854 */     if (k == 0) {
/*  855 */       return false;
/*      */     }
/*      */     
/*  858 */     float[] arrayOfFloat = paramGlyphVector.getGlyphPositions(0, j, null);
/*      */ 
/*      */     
/*  861 */     boolean bool1 = ((i & 0x2) == 0 || samePositions(glyphVector, arrayOfInt2, arrayOfInt1, arrayOfFloat)) ? true : false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  879 */     Point2D point2D = paramGlyphVector.getGlyphPosition(j);
/*  880 */     float f = (float)point2D.getX();
/*  881 */     boolean bool2 = false;
/*  882 */     if (font.hasLayoutAttributes() && this.printingGlyphVector && bool1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  898 */       Map<TextAttribute, ?> map = font.getAttributes();
/*  899 */       Object object = map.get(TextAttribute.TRACKING);
/*      */       
/*  901 */       boolean bool3 = (object != null && object instanceof Number && ((Number)object).floatValue() != 0.0F) ? true : false;
/*      */       
/*  903 */       if (bool3) {
/*  904 */         bool1 = false;
/*      */       } else {
/*  906 */         Rectangle2D rectangle2D = font.getStringBounds(str, fontRenderContext1);
/*  907 */         float f1 = (float)rectangle2D.getWidth();
/*  908 */         if (Math.abs(f1 - f) > 1.0E-5D) {
/*  909 */           bool2 = true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  914 */     if (bool && bool1 && !bool2) {
/*  915 */       drawString(str, paramFloat1, paramFloat2, font, fontRenderContext1, 0.0F);
/*  916 */       return true;
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
/*  928 */     if (k == 1 && canDrawStringToWidth() && bool1) {
/*  929 */       drawString(str, paramFloat1, paramFloat2, font, fontRenderContext1, f);
/*  930 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  938 */     if (FontUtilities.isComplexText(arrayOfChar2, 0, arrayOfChar2.length)) {
/*  939 */       return printGlyphVector(paramGlyphVector, paramFloat1, paramFloat2);
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
/*  953 */     if (j > 10 && printGlyphVector(paramGlyphVector, paramFloat1, paramFloat2)) {
/*  954 */       return true;
/*      */     }
/*      */     
/*  957 */     for (byte b2 = 0; b2 < j; b2++) {
/*  958 */       String str1 = new String(arrayOfChar2, b2, 1);
/*  959 */       drawString(str1, paramFloat1 + arrayOfFloat[b2 * 2], paramFloat2 + arrayOfFloat[b2 * 2 + 1], font, fontRenderContext1, 0.0F);
/*      */     } 
/*      */     
/*  962 */     return true;
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
/*      */   private boolean samePositions(GlyphVector paramGlyphVector, int[] paramArrayOfint1, int[] paramArrayOfint2, float[] paramArrayOffloat) {
/*  976 */     int i = paramGlyphVector.getNumGlyphs();
/*  977 */     float[] arrayOfFloat = paramGlyphVector.getGlyphPositions(0, i, null);
/*      */ 
/*      */     
/*  980 */     if (i != paramArrayOfint1.length || paramArrayOfint2.length != paramArrayOfint1.length || paramArrayOffloat.length != arrayOfFloat.length)
/*      */     {
/*      */       
/*  983 */       return false;
/*      */     }
/*      */     
/*  986 */     for (byte b = 0; b < i; b++) {
/*  987 */       if (paramArrayOfint1[b] != paramArrayOfint2[b] || arrayOfFloat[b] != paramArrayOffloat[b]) {
/*  988 */         return false;
/*      */       }
/*      */     } 
/*  991 */     return true;
/*      */   }
/*      */   
/*      */   protected boolean canDrawStringToWidth() {
/*  995 */     return false;
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
/*      */   private static char[] getGlyphToCharMapForFont(Font2D paramFont2D) {
/* 1008 */     int i = paramFont2D.getNumGlyphs();
/* 1009 */     int j = paramFont2D.getMissingGlyphCode();
/* 1010 */     char[] arrayOfChar = new char[i];
/*      */     
/*      */     char c;
/* 1013 */     for (c = Character.MIN_VALUE; c < i; c++) {
/* 1014 */       arrayOfChar[c] = Character.MAX_VALUE;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1022 */     for (c = Character.MIN_VALUE; c < '￿'; c = (char)(c + 1)) {
/* 1023 */       if (c < '?' || c > '?') {
/*      */ 
/*      */ 
/*      */         
/* 1027 */         int k = paramFont2D.charToGlyph(c);
/* 1028 */         if (k != j && k >= 0 && k < i && arrayOfChar[k] == Character.MAX_VALUE)
/*      */         {
/*      */ 
/*      */           
/* 1032 */           arrayOfChar[k] = c; } 
/*      */       } 
/*      */     } 
/* 1035 */     return arrayOfChar;
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
/*      */   public void draw(Shape paramShape) {
/* 1054 */     fill(getStroke().createStrokedShape(paramShape));
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
/*      */   public void fill(Shape paramShape) {
/* 1070 */     Paint paint = getPaint();
/*      */     
/*      */     try {
/* 1073 */       fill(paramShape, (Color)paint);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1081 */     catch (ClassCastException classCastException) {
/* 1082 */       throw new IllegalArgumentException("Expected a Color instance");
/*      */     } 
/*      */   }
/*      */   
/*      */   public void fill(Shape paramShape, Color paramColor) {
/* 1087 */     AffineTransform affineTransform = getTransform();
/*      */     
/* 1089 */     if (getClip() != null) {
/* 1090 */       deviceClip(getClip().getPathIterator(affineTransform));
/*      */     }
/* 1092 */     deviceFill(paramShape.getPathIterator(affineTransform), paramColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void deviceFill(PathIterator paramPathIterator, Color paramColor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void deviceClip(PathIterator paramPathIterator);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void deviceFrameRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void deviceDrawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void deviceFillRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected BufferedImage getBufferedImage(Image paramImage) {
/* 1131 */     if (paramImage instanceof BufferedImage)
/*      */     {
/* 1133 */       return (BufferedImage)paramImage; } 
/* 1134 */     if (paramImage instanceof ToolkitImage)
/*      */     {
/*      */ 
/*      */       
/* 1138 */       return ((ToolkitImage)paramImage).getBufferedImage(); } 
/* 1139 */     if (paramImage instanceof VolatileImage)
/*      */     {
/*      */       
/* 1142 */       return ((VolatileImage)paramImage).getSnapshot();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1149 */     return null;
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
/*      */   protected boolean hasTransparentPixels(BufferedImage paramBufferedImage) {
/* 1161 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/*      */ 
/*      */     
/* 1164 */     boolean bool = (colorModel == null) ? true : ((colorModel.getTransparency() != 1) ? true : false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1174 */     if (bool && paramBufferedImage != null && (
/* 1175 */       paramBufferedImage.getType() == 2 || paramBufferedImage
/* 1176 */       .getType() == 3)) {
/* 1177 */       DataBuffer dataBuffer = paramBufferedImage.getRaster().getDataBuffer();
/* 1178 */       SampleModel sampleModel = paramBufferedImage.getRaster().getSampleModel();
/* 1179 */       if (dataBuffer instanceof DataBufferInt && sampleModel instanceof SinglePixelPackedSampleModel) {
/*      */         
/* 1181 */         SinglePixelPackedSampleModel singlePixelPackedSampleModel = (SinglePixelPackedSampleModel)sampleModel;
/*      */ 
/*      */ 
/*      */         
/* 1185 */         int[] arrayOfInt = SunWritableRaster.stealData((DataBufferInt)dataBuffer, 0);
/* 1186 */         int i = paramBufferedImage.getMinX();
/* 1187 */         int j = paramBufferedImage.getMinY();
/* 1188 */         int k = paramBufferedImage.getWidth();
/* 1189 */         int m = paramBufferedImage.getHeight();
/* 1190 */         int n = singlePixelPackedSampleModel.getScanlineStride();
/* 1191 */         boolean bool1 = false;
/* 1192 */         for (int i1 = j; i1 < j + m; i1++) {
/* 1193 */           int i2 = i1 * n;
/* 1194 */           for (int i3 = i; i3 < i + k; i3++) {
/* 1195 */             if ((arrayOfInt[i2 + i3] & 0xFF000000) != -16777216) {
/* 1196 */               bool1 = true;
/*      */               break;
/*      */             } 
/*      */           } 
/* 1200 */           if (bool1) {
/*      */             break;
/*      */           }
/*      */         } 
/* 1204 */         if (!bool1) {
/* 1205 */           bool = false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1211 */     return bool;
/*      */   }
/*      */   
/*      */   protected boolean isBitmaskTransparency(BufferedImage paramBufferedImage) {
/* 1215 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/* 1216 */     return (colorModel != null && colorModel
/* 1217 */       .getTransparency() == 2);
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
/*      */   protected boolean drawBitmaskImage(BufferedImage paramBufferedImage, AffineTransform paramAffineTransform, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*      */     int m, n, i1;
/* 1230 */     ColorModel colorModel = paramBufferedImage.getColorModel();
/*      */ 
/*      */ 
/*      */     
/* 1234 */     if (!(colorModel instanceof IndexColorModel)) {
/* 1235 */       return false;
/*      */     }
/* 1237 */     IndexColorModel indexColorModel = (IndexColorModel)colorModel;
/*      */ 
/*      */     
/* 1240 */     if (colorModel.getTransparency() != 2) {
/* 1241 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1246 */     if (paramColor != null && paramColor.getAlpha() < 128) {
/* 1247 */       return false;
/*      */     }
/*      */     
/* 1250 */     if ((paramAffineTransform.getType() & 0xFFFFFFF4) != 0)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1255 */       return false;
/*      */     }
/*      */     
/* 1258 */     if ((getTransform().getType() & 0xFFFFFFF4) != 0)
/*      */     {
/*      */ 
/*      */ 
/*      */       
/* 1263 */       return false;
/*      */     }
/*      */     
/* 1266 */     BufferedImage bufferedImage = null;
/* 1267 */     WritableRaster writableRaster = paramBufferedImage.getRaster();
/* 1268 */     int i = indexColorModel.getTransparentPixel();
/* 1269 */     byte[] arrayOfByte = new byte[indexColorModel.getMapSize()];
/* 1270 */     indexColorModel.getAlphas(arrayOfByte);
/* 1271 */     if (i >= 0) {
/* 1272 */       arrayOfByte[i] = 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1279 */     int j = writableRaster.getWidth();
/* 1280 */     int k = writableRaster.getHeight();
/* 1281 */     if (paramInt1 > j || paramInt2 > k) {
/* 1282 */       return false;
/*      */     }
/*      */     
/* 1285 */     if (paramInt1 + paramInt3 > j) {
/* 1286 */       m = j;
/* 1287 */       i1 = m - paramInt1;
/*      */     } else {
/* 1289 */       m = paramInt1 + paramInt3;
/* 1290 */       i1 = paramInt3;
/*      */     } 
/* 1292 */     if (paramInt2 + paramInt4 > k) {
/* 1293 */       n = k;
/* 1294 */       int i3 = n - paramInt2;
/*      */     } else {
/* 1296 */       n = paramInt2 + paramInt4;
/* 1297 */       int i3 = paramInt4;
/*      */     } 
/* 1299 */     int[] arrayOfInt = new int[i1];
/* 1300 */     for (int i2 = paramInt2; i2 < n; i2++) {
/* 1301 */       int i3 = -1;
/* 1302 */       writableRaster.getPixels(paramInt1, i2, i1, 1, arrayOfInt);
/* 1303 */       for (int i4 = paramInt1; i4 < m; i4++) {
/* 1304 */         if (arrayOfByte[arrayOfInt[i4 - paramInt1]] == 0) {
/* 1305 */           if (i3 >= 0) {
/* 1306 */             bufferedImage = paramBufferedImage.getSubimage(i3, i2, i4 - i3, 1);
/*      */             
/* 1308 */             paramAffineTransform.translate(i3, i2);
/* 1309 */             drawImageToPlatform(bufferedImage, paramAffineTransform, paramColor, 0, 0, i4 - i3, 1, true);
/*      */             
/* 1311 */             paramAffineTransform.translate(-i3, -i2);
/* 1312 */             i3 = -1;
/*      */           } 
/* 1314 */         } else if (i3 < 0) {
/* 1315 */           i3 = i4;
/*      */         } 
/*      */       } 
/* 1318 */       if (i3 >= 0) {
/* 1319 */         bufferedImage = paramBufferedImage.getSubimage(i3, i2, m - i3, 1);
/*      */         
/* 1321 */         paramAffineTransform.translate(i3, i2);
/* 1322 */         drawImageToPlatform(bufferedImage, paramAffineTransform, paramColor, 0, 0, m - i3, 1, true);
/*      */         
/* 1324 */         paramAffineTransform.translate(-i3, -i2);
/*      */       } 
/*      */     } 
/* 1327 */     return true;
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
/*      */   protected abstract boolean drawImageToPlatform(Image paramImage, AffineTransform paramAffineTransform, Color paramColor, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/* 1396 */     return drawImage(paramImage, paramInt1, paramInt2, (Color)null, paramImageObserver);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ImageObserver paramImageObserver) {
/* 1437 */     return drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, (Color)null, paramImageObserver);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, Color paramColor, ImageObserver paramImageObserver) {
/*      */     boolean bool;
/* 1481 */     if (paramImage == null) {
/* 1482 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1486 */     int i = paramImage.getWidth(null);
/* 1487 */     int j = paramImage.getHeight(null);
/*      */     
/* 1489 */     if (i < 0 || j < 0) {
/* 1490 */       bool = false;
/*      */     } else {
/* 1492 */       bool = drawImage(paramImage, paramInt1, paramInt2, i, j, paramColor, paramImageObserver);
/*      */     } 
/*      */     
/* 1495 */     return bool;
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, ImageObserver paramImageObserver) {
/*      */     boolean bool;
/* 1543 */     if (paramImage == null) {
/* 1544 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1548 */     int i = paramImage.getWidth(null);
/* 1549 */     int j = paramImage.getHeight(null);
/*      */     
/* 1551 */     if (i < 0 || j < 0) {
/* 1552 */       bool = false;
/*      */     } else {
/* 1554 */       bool = drawImage(paramImage, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, 0, 0, i, j, paramImageObserver);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1560 */     return bool;
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, ImageObserver paramImageObserver) {
/* 1615 */     return drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, (Color)null, paramImageObserver);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Color paramColor, ImageObserver paramImageObserver) {
/* 1681 */     if (paramImage == null) {
/* 1682 */       return true;
/*      */     }
/* 1684 */     int i = paramImage.getWidth(null);
/* 1685 */     int j = paramImage.getHeight(null);
/*      */     
/* 1687 */     if (i < 0 || j < 0) {
/* 1688 */       return true;
/*      */     }
/*      */     
/* 1691 */     int k = paramInt7 - paramInt5;
/* 1692 */     int m = paramInt8 - paramInt6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1700 */     float f1 = (paramInt3 - paramInt1) / k;
/* 1701 */     float f2 = (paramInt4 - paramInt2) / m;
/* 1702 */     AffineTransform affineTransform = new AffineTransform(f1, 0.0F, 0.0F, f2, paramInt1 - paramInt5 * f1, paramInt2 - paramInt6 * f2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1714 */     int n = 0;
/* 1715 */     if (paramInt7 < paramInt5) {
/* 1716 */       n = paramInt5;
/* 1717 */       paramInt5 = paramInt7;
/* 1718 */       paramInt7 = n;
/*      */     } 
/* 1720 */     if (paramInt8 < paramInt6) {
/* 1721 */       n = paramInt6;
/* 1722 */       paramInt6 = paramInt8;
/* 1723 */       paramInt8 = n;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1729 */     if (paramInt5 < 0) {
/* 1730 */       paramInt5 = 0;
/* 1731 */     } else if (paramInt5 > i) {
/* 1732 */       paramInt5 = i;
/*      */     } 
/* 1734 */     if (paramInt7 < 0) {
/* 1735 */       paramInt7 = 0;
/* 1736 */     } else if (paramInt7 > i) {
/* 1737 */       paramInt7 = i;
/*      */     } 
/* 1739 */     if (paramInt6 < 0) {
/* 1740 */       paramInt6 = 0;
/* 1741 */     } else if (paramInt6 > j) {
/* 1742 */       paramInt6 = j;
/*      */     } 
/* 1744 */     if (paramInt8 < 0) {
/* 1745 */       paramInt8 = 0;
/* 1746 */     } else if (paramInt8 > j) {
/* 1747 */       paramInt8 = j;
/*      */     } 
/*      */     
/* 1750 */     k = paramInt7 - paramInt5;
/* 1751 */     m = paramInt8 - paramInt6;
/*      */     
/* 1753 */     if (k <= 0 || m <= 0) {
/* 1754 */       return true;
/*      */     }
/*      */     
/* 1757 */     return drawImageToPlatform(paramImage, affineTransform, paramColor, paramInt5, paramInt6, k, m, false);
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
/*      */   public boolean drawImage(Image paramImage, AffineTransform paramAffineTransform, ImageObserver paramImageObserver) {
/*      */     boolean bool;
/* 1788 */     if (paramImage == null) {
/* 1789 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1793 */     int i = paramImage.getWidth(null);
/* 1794 */     int j = paramImage.getHeight(null);
/*      */     
/* 1796 */     if (i < 0 || j < 0) {
/* 1797 */       bool = false;
/*      */     } else {
/* 1799 */       bool = drawImageToPlatform(paramImage, paramAffineTransform, (Color)null, 0, 0, i, j, false);
/*      */     } 
/*      */ 
/*      */     
/* 1803 */     return bool;
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
/*      */   public void drawImage(BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, int paramInt1, int paramInt2) {
/* 1829 */     if (paramBufferedImage == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1833 */     int i = paramBufferedImage.getWidth(null);
/* 1834 */     int j = paramBufferedImage.getHeight(null);
/*      */     
/* 1836 */     if (paramBufferedImageOp != null) {
/* 1837 */       paramBufferedImage = paramBufferedImageOp.filter(paramBufferedImage, null);
/*      */     }
/* 1839 */     if (i <= 0 || j <= 0) {
/*      */       return;
/*      */     }
/* 1842 */     AffineTransform affineTransform = new AffineTransform(1.0F, 0.0F, 0.0F, 1.0F, paramInt1, paramInt2);
/* 1843 */     drawImageToPlatform(paramBufferedImage, affineTransform, (Color)null, 0, 0, i, j, false);
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
/*      */   public void drawRenderedImage(RenderedImage paramRenderedImage, AffineTransform paramAffineTransform) {
/* 1871 */     if (paramRenderedImage == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1875 */     BufferedImage bufferedImage = null;
/* 1876 */     int i = paramRenderedImage.getWidth();
/* 1877 */     int j = paramRenderedImage.getHeight();
/*      */     
/* 1879 */     if (i <= 0 || j <= 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1883 */     if (paramRenderedImage instanceof BufferedImage) {
/* 1884 */       bufferedImage = (BufferedImage)paramRenderedImage;
/*      */     } else {
/* 1886 */       bufferedImage = new BufferedImage(i, j, 2);
/*      */       
/* 1888 */       Graphics2D graphics2D = bufferedImage.createGraphics();
/* 1889 */       graphics2D.drawRenderedImage(paramRenderedImage, paramAffineTransform);
/*      */     } 
/*      */     
/* 1892 */     drawImageToPlatform(bufferedImage, paramAffineTransform, (Color)null, 0, 0, i, j, false);
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/PathGraphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */