/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.GeneralPath;
/*     */ import java.awt.geom.Line2D;
/*     */ import java.awt.geom.RoundRectangle2D;
/*     */ import sun.java2d.SunGraphics2D;
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
/*     */ public class PixelToShapeConverter
/*     */   implements PixelDrawPipe, PixelFillPipe
/*     */ {
/*     */   ShapeDrawPipe outpipe;
/*     */   
/*     */   public PixelToShapeConverter(ShapeDrawPipe paramShapeDrawPipe) {
/*  47 */     this.outpipe = paramShapeDrawPipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLine(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  52 */     this.outpipe.draw(paramSunGraphics2D, new Line2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  57 */     this.outpipe.draw(paramSunGraphics2D, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  62 */     this.outpipe.fill(paramSunGraphics2D, new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  68 */     this.outpipe.draw(paramSunGraphics2D, new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  74 */     this.outpipe.fill(paramSunGraphics2D, new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  79 */     this.outpipe.draw(paramSunGraphics2D, new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  84 */     this.outpipe.fill(paramSunGraphics2D, new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  90 */     this.outpipe.draw(paramSunGraphics2D, new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  97 */     this.outpipe.fill(paramSunGraphics2D, new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Shape makePoly(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt, boolean paramBoolean) {
/* 103 */     GeneralPath generalPath = new GeneralPath(0);
/* 104 */     if (paramInt > 0) {
/* 105 */       generalPath.moveTo(paramArrayOfint1[0], paramArrayOfint2[0]);
/* 106 */       for (byte b = 1; b < paramInt; b++) {
/* 107 */         generalPath.lineTo(paramArrayOfint1[b], paramArrayOfint2[b]);
/*     */       }
/* 109 */       if (paramBoolean) {
/* 110 */         generalPath.closePath();
/*     */       }
/*     */     } 
/* 113 */     return generalPath;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolyline(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 119 */     this.outpipe.draw(paramSunGraphics2D, makePoly(paramArrayOfint1, paramArrayOfint2, paramInt, false));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 125 */     this.outpipe.draw(paramSunGraphics2D, makePoly(paramArrayOfint1, paramArrayOfint2, paramInt, true));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 131 */     this.outpipe.fill(paramSunGraphics2D, makePoly(paramArrayOfint1, paramArrayOfint2, paramInt, true));
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/PixelToShapeConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */