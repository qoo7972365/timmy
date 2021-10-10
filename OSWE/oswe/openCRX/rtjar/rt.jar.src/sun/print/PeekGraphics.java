/*      */ package sun.print;
/*      */ 
/*      */ import java.awt.Color;
/*      */ import java.awt.Composite;
/*      */ import java.awt.Font;
/*      */ import java.awt.FontMetrics;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.GraphicsConfiguration;
/*      */ import java.awt.Image;
/*      */ import java.awt.Paint;
/*      */ import java.awt.Rectangle;
/*      */ import java.awt.RenderingHints;
/*      */ import java.awt.Shape;
/*      */ import java.awt.Stroke;
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Line2D;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.RoundRectangle2D;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.awt.image.BufferedImageOp;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.RenderedImage;
/*      */ import java.awt.image.renderable.RenderableImage;
/*      */ import java.awt.print.PrinterGraphics;
/*      */ import java.awt.print.PrinterJob;
/*      */ import java.text.AttributedCharacterIterator;
/*      */ import java.util.Map;
/*      */ import sun.java2d.Spans;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PeekGraphics
/*      */   extends Graphics2D
/*      */   implements PrinterGraphics, ImageObserver, Cloneable
/*      */ {
/*      */   Graphics2D mGraphics;
/*      */   PrinterJob mPrinterJob;
/*   86 */   private Spans mDrawingArea = new Spans();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   92 */   private PeekMetrics mPrintMetrics = new PeekMetrics();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean mAWTDrawingOnly = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeekGraphics(Graphics2D paramGraphics2D, PrinterJob paramPrinterJob) {
/*  108 */     this.mGraphics = paramGraphics2D;
/*  109 */     this.mPrinterJob = paramPrinterJob;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Graphics2D getDelegate() {
/*  117 */     return this.mGraphics;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDelegate(Graphics2D paramGraphics2D) {
/*  125 */     this.mGraphics = paramGraphics2D;
/*      */   }
/*      */   
/*      */   public PrinterJob getPrinterJob() {
/*  129 */     return this.mPrinterJob;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAWTDrawingOnly() {
/*  140 */     this.mAWTDrawingOnly = true;
/*      */   }
/*      */   
/*      */   public boolean getAWTDrawingOnly() {
/*  144 */     return this.mAWTDrawingOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Spans getDrawingArea() {
/*  152 */     return this.mDrawingArea;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GraphicsConfiguration getDeviceConfiguration() {
/*  159 */     return ((RasterPrinterJob)this.mPrinterJob).getPrinterGraphicsConfig();
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
/*      */   public Graphics create() {
/*  172 */     PeekGraphics peekGraphics = null;
/*      */     
/*      */     try {
/*  175 */       peekGraphics = (PeekGraphics)clone();
/*  176 */       peekGraphics.mGraphics = (Graphics2D)this.mGraphics.create();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  182 */     catch (CloneNotSupportedException cloneNotSupportedException) {}
/*      */ 
/*      */ 
/*      */     
/*  186 */     return peekGraphics;
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
/*      */   public void translate(int paramInt1, int paramInt2) {
/*  202 */     this.mGraphics.translate(paramInt1, paramInt2);
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
/*      */   public void translate(double paramDouble1, double paramDouble2) {
/*  217 */     this.mGraphics.translate(paramDouble1, paramDouble2);
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
/*      */   public void rotate(double paramDouble) {
/*  235 */     this.mGraphics.rotate(paramDouble);
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
/*      */   public void rotate(double paramDouble1, double paramDouble2, double paramDouble3) {
/*  254 */     this.mGraphics.rotate(paramDouble1, paramDouble2, paramDouble3);
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
/*      */   public void scale(double paramDouble1, double paramDouble2) {
/*  269 */     this.mGraphics.scale(paramDouble1, paramDouble2);
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
/*      */   public void shear(double paramDouble1, double paramDouble2) {
/*  288 */     this.mGraphics.shear(paramDouble1, paramDouble2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getColor() {
/*  299 */     return this.mGraphics.getColor();
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
/*      */   public void setColor(Color paramColor) {
/*  312 */     this.mGraphics.setColor(paramColor);
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
/*      */   public void setPaintMode() {
/*  324 */     this.mGraphics.setPaintMode();
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
/*      */   public void setXORMode(Color paramColor) {
/*  344 */     this.mGraphics.setXORMode(paramColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont() {
/*  355 */     return this.mGraphics.getFont();
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
/*      */   public void setFont(Font paramFont) {
/*  370 */     this.mGraphics.setFont(paramFont);
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
/*      */   public FontMetrics getFontMetrics(Font paramFont) {
/*  383 */     return this.mGraphics.getFontMetrics(paramFont);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FontRenderContext getFontRenderContext() {
/*  391 */     return this.mGraphics.getFontRenderContext();
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
/*      */   public Rectangle getClipBounds() {
/*  406 */     return this.mGraphics.getClipBounds();
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
/*      */   public void clipRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  425 */     this.mGraphics.clipRect(paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   public void setClip(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  442 */     this.mGraphics.setClip(paramInt1, paramInt2, paramInt3, paramInt4);
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
/*      */   public Shape getClip() {
/*  456 */     return this.mGraphics.getClip();
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
/*      */   public void setClip(Shape paramShape) {
/*  474 */     this.mGraphics.setClip(paramShape);
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
/*      */   public void copyArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  514 */     addStrokeShape(new Line2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*  515 */     this.mPrintMetrics.draw(this);
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
/*      */   public void fillRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  542 */     addDrawingRect(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*  543 */     this.mPrintMetrics.fill(this);
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
/*      */   public void clearRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  568 */     Rectangle2D.Float float_ = new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4);
/*  569 */     addDrawingRect(float_);
/*  570 */     this.mPrintMetrics.clear(this);
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
/*  592 */     addStrokeShape(new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*  593 */     this.mPrintMetrics.draw(this);
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
/*      */   public void fillRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  616 */     Rectangle2D.Float float_ = new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4);
/*  617 */     addDrawingRect(float_);
/*  618 */     this.mPrintMetrics.fill(this);
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
/*      */   public void drawOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  640 */     addStrokeShape(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*  641 */     this.mPrintMetrics.draw(this);
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
/*  657 */     Rectangle2D.Float float_ = new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4);
/*  658 */     addDrawingRect(float_);
/*  659 */     this.mPrintMetrics.fill(this);
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
/*      */   public void drawArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  696 */     addStrokeShape(new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*  697 */     this.mPrintMetrics.draw(this);
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
/*      */   public void fillArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  732 */     Rectangle2D.Float float_ = new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4);
/*  733 */     addDrawingRect(float_);
/*  734 */     this.mPrintMetrics.fill(this);
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
/*      */   public void drawPolyline(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  752 */     if (paramInt > 0) {
/*  753 */       int i = paramArrayOfint1[0];
/*  754 */       int j = paramArrayOfint2[0];
/*      */       
/*  756 */       for (byte b = 1; b < paramInt; b++) {
/*  757 */         drawLine(i, j, paramArrayOfint1[b], paramArrayOfint2[b]);
/*  758 */         i = paramArrayOfint1[b];
/*  759 */         j = paramArrayOfint2[b];
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
/*      */   public void drawPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  787 */     if (paramInt > 0) {
/*  788 */       drawPolyline(paramArrayOfint1, paramArrayOfint2, paramInt);
/*  789 */       drawLine(paramArrayOfint1[paramInt - 1], paramArrayOfint2[paramInt - 1], paramArrayOfint1[0], paramArrayOfint2[0]);
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
/*      */   public void fillPolygon(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/*  818 */     if (paramInt > 0) {
/*  819 */       int i = paramArrayOfint1[0];
/*  820 */       int j = paramArrayOfint2[0];
/*  821 */       int k = paramArrayOfint1[0];
/*  822 */       int m = paramArrayOfint2[0];
/*      */       
/*  824 */       for (byte b = 1; b < paramInt; b++) {
/*      */         
/*  826 */         if (paramArrayOfint1[b] < i) {
/*  827 */           i = paramArrayOfint1[b];
/*  828 */         } else if (paramArrayOfint1[b] > k) {
/*  829 */           k = paramArrayOfint1[b];
/*      */         } 
/*      */         
/*  832 */         if (paramArrayOfint2[b] < j) {
/*  833 */           j = paramArrayOfint2[b];
/*  834 */         } else if (paramArrayOfint2[b] > m) {
/*  835 */           m = paramArrayOfint2[b];
/*      */         } 
/*      */       } 
/*      */       
/*  839 */       addDrawingRect(i, j, (k - i), (m - j));
/*      */     } 
/*      */     
/*  842 */     this.mPrintMetrics.fill(this);
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
/*      */   public void drawString(String paramString, int paramInt1, int paramInt2) {
/*  861 */     drawString(paramString, paramInt1, paramInt2);
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
/*      */   public void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, int paramInt1, int paramInt2) {
/*  887 */     drawString(paramAttributedCharacterIterator, paramInt1, paramInt2);
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
/*      */   public void drawString(AttributedCharacterIterator paramAttributedCharacterIterator, float paramFloat1, float paramFloat2) {
/*  912 */     if (paramAttributedCharacterIterator == null) {
/*  913 */       throw new NullPointerException("AttributedCharacterIterator is null");
/*      */     }
/*      */ 
/*      */     
/*  917 */     TextLayout textLayout = new TextLayout(paramAttributedCharacterIterator, getFontRenderContext());
/*  918 */     textLayout.draw(this, paramFloat1, paramFloat2);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver) {
/*  950 */     if (paramImage == null) {
/*  951 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  957 */     ImageWaiter imageWaiter = new ImageWaiter(paramImage);
/*      */     
/*  959 */     addDrawingRect(paramInt1, paramInt2, imageWaiter.getWidth(), imageWaiter.getHeight());
/*  960 */     this.mPrintMetrics.drawImage(this, paramImage);
/*      */     
/*  962 */     return this.mGraphics.drawImage(paramImage, paramInt1, paramInt2, paramImageObserver);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, ImageObserver paramImageObserver) {
/* 1004 */     if (paramImage == null) {
/* 1005 */       return true;
/*      */     }
/* 1007 */     addDrawingRect(paramInt1, paramInt2, paramInt3, paramInt4);
/* 1008 */     this.mPrintMetrics.drawImage(this, paramImage);
/*      */     
/* 1010 */     return this.mGraphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramImageObserver);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, Color paramColor, ImageObserver paramImageObserver) {
/* 1049 */     if (paramImage == null) {
/* 1050 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1056 */     ImageWaiter imageWaiter = new ImageWaiter(paramImage);
/*      */     
/* 1058 */     addDrawingRect(paramInt1, paramInt2, imageWaiter.getWidth(), imageWaiter.getHeight());
/* 1059 */     this.mPrintMetrics.drawImage(this, paramImage);
/*      */     
/* 1061 */     return this.mGraphics.drawImage(paramImage, paramInt1, paramInt2, paramColor, paramImageObserver);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor, ImageObserver paramImageObserver) {
/* 1109 */     if (paramImage == null) {
/* 1110 */       return true;
/*      */     }
/*      */     
/* 1113 */     addDrawingRect(paramInt1, paramInt2, paramInt3, paramInt4);
/* 1114 */     this.mPrintMetrics.drawImage(this, paramImage);
/*      */     
/* 1116 */     return this.mGraphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramColor, paramImageObserver);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, ImageObserver paramImageObserver) {
/* 1172 */     if (paramImage == null) {
/* 1173 */       return true;
/*      */     }
/*      */     
/* 1176 */     int i = paramInt3 - paramInt1;
/* 1177 */     int j = paramInt4 - paramInt2;
/*      */     
/* 1179 */     addDrawingRect(paramInt1, paramInt2, i, j);
/* 1180 */     this.mPrintMetrics.drawImage(this, paramImage);
/*      */     
/* 1182 */     return this.mGraphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramImageObserver);
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
/*      */   public boolean drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, Color paramColor, ImageObserver paramImageObserver) {
/* 1247 */     if (paramImage == null) {
/* 1248 */       return true;
/*      */     }
/*      */     
/* 1251 */     int i = paramInt3 - paramInt1;
/* 1252 */     int j = paramInt4 - paramInt2;
/*      */     
/* 1254 */     addDrawingRect(paramInt1, paramInt2, i, j);
/* 1255 */     this.mPrintMetrics.drawImage(this, paramImage);
/*      */     
/* 1257 */     return this.mGraphics.drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramColor, paramImageObserver);
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
/*      */   public void drawRenderedImage(RenderedImage paramRenderedImage, AffineTransform paramAffineTransform) {
/* 1284 */     if (paramRenderedImage == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1288 */     this.mPrintMetrics.drawImage(this, paramRenderedImage);
/* 1289 */     this.mDrawingArea.addInfinite();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawRenderableImage(RenderableImage paramRenderableImage, AffineTransform paramAffineTransform) {
/* 1296 */     if (paramRenderableImage == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1300 */     this.mPrintMetrics.drawImage(this, paramRenderableImage);
/* 1301 */     this.mDrawingArea.addInfinite();
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
/*      */   public void dispose() {
/* 1333 */     this.mGraphics.dispose();
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
/*      */   public void finalize() {}
/*      */ 
/*      */ 
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
/* 1359 */     addStrokeShape(paramShape);
/* 1360 */     this.mPrintMetrics.draw(this);
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
/*      */   public boolean drawImage(Image paramImage, AffineTransform paramAffineTransform, ImageObserver paramImageObserver) {
/* 1388 */     if (paramImage == null) {
/* 1389 */       return true;
/*      */     }
/*      */     
/* 1392 */     this.mDrawingArea.addInfinite();
/* 1393 */     this.mPrintMetrics.drawImage(this, paramImage);
/*      */     
/* 1395 */     return this.mGraphics.drawImage(paramImage, paramAffineTransform, paramImageObserver);
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
/*      */   public void drawImage(BufferedImage paramBufferedImage, BufferedImageOp paramBufferedImageOp, int paramInt1, int paramInt2) {
/* 1436 */     if (paramBufferedImage == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1440 */     this.mPrintMetrics.drawImage(this, paramBufferedImage);
/* 1441 */     this.mDrawingArea.addInfinite();
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
/*      */   public void drawString(String paramString, float paramFloat1, float paramFloat2) {
/* 1464 */     if (paramString.length() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1468 */     FontRenderContext fontRenderContext = getFontRenderContext();
/* 1469 */     Rectangle2D rectangle2D = getFont().getStringBounds(paramString, fontRenderContext);
/* 1470 */     addDrawingRect(rectangle2D, paramFloat1, paramFloat2);
/* 1471 */     this.mPrintMetrics.drawText(this);
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
/*      */   public void drawGlyphVector(GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/* 1493 */     Rectangle2D rectangle2D = paramGlyphVector.getLogicalBounds();
/* 1494 */     addDrawingRect(rectangle2D, paramFloat1, paramFloat2);
/* 1495 */     this.mPrintMetrics.drawText(this);
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
/*      */   public void fill(Shape paramShape) {
/* 1512 */     addDrawingRect(paramShape.getBounds());
/* 1513 */     this.mPrintMetrics.fill(this);
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
/*      */   public boolean hit(Rectangle paramRectangle, Shape paramShape, boolean paramBoolean) {
/* 1540 */     return this.mGraphics.hit(paramRectangle, paramShape, paramBoolean);
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
/*      */   public void setComposite(Composite paramComposite) {
/* 1554 */     this.mGraphics.setComposite(paramComposite);
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
/*      */   public void setPaint(Paint paramPaint) {
/* 1567 */     this.mGraphics.setPaint(paramPaint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStroke(Stroke paramStroke) {
/* 1577 */     this.mGraphics.setStroke(paramStroke);
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
/*      */   public void setRenderingHint(RenderingHints.Key paramKey, Object paramObject) {
/* 1590 */     this.mGraphics.setRenderingHint(paramKey, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getRenderingHint(RenderingHints.Key paramKey) {
/* 1600 */     return this.mGraphics.getRenderingHint(paramKey);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderingHints(Map<?, ?> paramMap) {
/* 1611 */     this.mGraphics.setRenderingHints(paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRenderingHints(Map<?, ?> paramMap) {
/* 1622 */     this.mGraphics.addRenderingHints(paramMap);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RenderingHints getRenderingHints() {
/* 1632 */     return this.mGraphics.getRenderingHints();
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
/*      */   public void transform(AffineTransform paramAffineTransform) {
/* 1654 */     this.mGraphics.transform(paramAffineTransform);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransform(AffineTransform paramAffineTransform) {
/* 1665 */     this.mGraphics.setTransform(paramAffineTransform);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AffineTransform getTransform() {
/* 1674 */     return this.mGraphics.getTransform();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Paint getPaint() {
/* 1683 */     return this.mGraphics.getPaint();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Composite getComposite() {
/* 1691 */     return this.mGraphics.getComposite();
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
/*      */   public void setBackground(Color paramColor) {
/* 1707 */     this.mGraphics.setBackground(paramColor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Color getBackground() {
/* 1715 */     return this.mGraphics.getBackground();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Stroke getStroke() {
/* 1723 */     return this.mGraphics.getStroke();
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
/*      */   public void clip(Shape paramShape) {
/* 1736 */     this.mGraphics.clip(paramShape);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitsDrawingArea(Rectangle paramRectangle) {
/* 1746 */     return this.mDrawingArea.intersects((float)paramRectangle.getMinY(), 
/* 1747 */         (float)paramRectangle.getMaxY());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PeekMetrics getMetrics() {
/* 1755 */     return this.mPrintMetrics;
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
/*      */   private void addDrawingRect(Rectangle2D paramRectangle2D, float paramFloat1, float paramFloat2) {
/* 1767 */     addDrawingRect((float)(paramRectangle2D.getX() + paramFloat1), 
/* 1768 */         (float)(paramRectangle2D.getY() + paramFloat2), 
/* 1769 */         (float)paramRectangle2D.getWidth(), 
/* 1770 */         (float)paramRectangle2D.getHeight());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDrawingRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 1776 */     Rectangle2D.Float float_ = new Rectangle2D.Float(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/* 1777 */     addDrawingRect(float_);
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
/*      */   private void addDrawingRect(Rectangle2D paramRectangle2D) {
/* 1795 */     AffineTransform affineTransform = getTransform();
/*      */     
/* 1797 */     Shape shape = affineTransform.createTransformedShape(paramRectangle2D);
/*      */     
/* 1799 */     Rectangle2D rectangle2D = shape.getBounds2D();
/*      */     
/* 1801 */     this.mDrawingArea.add((float)rectangle2D.getMinY(), 
/* 1802 */         (float)rectangle2D.getMaxY());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addStrokeShape(Shape paramShape) {
/* 1812 */     Shape shape = getStroke().createStrokedShape(paramShape);
/* 1813 */     addDrawingRect(shape.getBounds2D());
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
/*      */   public synchronized boolean imageUpdate(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
/* 1826 */     boolean bool = false;
/*      */     
/* 1828 */     if ((paramInt1 & 0x3) != 0) {
/* 1829 */       bool = true;
/* 1830 */       notify();
/*      */     } 
/*      */     
/* 1833 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized int getImageWidth(Image paramImage) {
/* 1841 */     while (paramImage.getWidth(this) == -1) {
/*      */       try {
/* 1843 */         wait();
/* 1844 */       } catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1849 */     return paramImage.getWidth(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized int getImageHeight(Image paramImage) {
/* 1857 */     while (paramImage.getHeight(this) == -1) {
/*      */       try {
/* 1859 */         wait();
/* 1860 */       } catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1865 */     return paramImage.getHeight(this);
/*      */   }
/*      */ 
/*      */   
/*      */   protected class ImageWaiter
/*      */     implements ImageObserver
/*      */   {
/*      */     private int mWidth;
/*      */     
/*      */     private int mHeight;
/*      */     
/*      */     private boolean badImage = false;
/*      */     
/*      */     ImageWaiter(Image param1Image) {
/* 1879 */       waitForDimensions(param1Image);
/*      */     }
/*      */     
/*      */     public int getWidth() {
/* 1883 */       return this.mWidth;
/*      */     }
/*      */     
/*      */     public int getHeight() {
/* 1887 */       return this.mHeight;
/*      */     }
/*      */     
/*      */     private synchronized void waitForDimensions(Image param1Image) {
/* 1891 */       this.mHeight = param1Image.getHeight(this);
/* 1892 */       this.mWidth = param1Image.getWidth(this);
/* 1893 */       while (!this.badImage && (this.mWidth < 0 || this.mHeight < 0)) {
/*      */         try {
/* 1895 */           Thread.sleep(50L);
/* 1896 */         } catch (InterruptedException interruptedException) {}
/*      */ 
/*      */         
/* 1899 */         this.mHeight = param1Image.getHeight(this);
/* 1900 */         this.mWidth = param1Image.getWidth(this);
/*      */       } 
/* 1902 */       if (this.badImage) {
/* 1903 */         this.mHeight = 0;
/* 1904 */         this.mWidth = 0;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public synchronized boolean imageUpdate(Image param1Image, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5) {
/* 1911 */       boolean bool = ((param1Int1 & 0xC2) != 0) ? true : false;
/* 1912 */       this.badImage = ((param1Int1 & 0xC0) != 0);
/*      */       
/* 1914 */       return bool;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/print/PeekGraphics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */