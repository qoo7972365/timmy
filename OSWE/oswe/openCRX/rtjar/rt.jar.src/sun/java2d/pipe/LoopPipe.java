/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.BasicStroke;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Path2D;
/*     */ import java.awt.geom.RoundRectangle2D;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.DrawParallelogram;
/*     */ import sun.java2d.loops.FillParallelogram;
/*     */ import sun.java2d.loops.FillSpans;
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
/*     */ public class LoopPipe
/*     */   implements PixelDrawPipe, PixelFillPipe, ParallelogramPipe, ShapeDrawPipe, LoopBasedPipe
/*     */ {
/*  55 */   static final RenderingEngine RenderEngine = RenderingEngine.getInstance();
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLine(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  60 */     int i = paramSunGraphics2D.transX;
/*  61 */     int j = paramSunGraphics2D.transY;
/*  62 */     paramSunGraphics2D.loops.drawLineLoop.DrawLine(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramInt1 + i, paramInt2 + j, paramInt3 + i, paramInt4 + j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  70 */     paramSunGraphics2D.loops.drawRectLoop.DrawRect(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  80 */     paramSunGraphics2D.shapepipe.draw(paramSunGraphics2D, new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  88 */     paramSunGraphics2D.shapepipe.draw(paramSunGraphics2D, new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  95 */     paramSunGraphics2D.shapepipe.draw(paramSunGraphics2D, new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolyline(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 104 */     int[] arrayOfInt = { paramInt };
/* 105 */     paramSunGraphics2D.loops.drawPolygonsLoop.DrawPolygons(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramArrayOfint1, paramArrayOfint2, arrayOfInt, 1, paramSunGraphics2D.transX, paramSunGraphics2D.transY, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 116 */     int[] arrayOfInt = { paramInt };
/* 117 */     paramSunGraphics2D.loops.drawPolygonsLoop.DrawPolygons(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramArrayOfint1, paramArrayOfint2, arrayOfInt, 1, paramSunGraphics2D.transX, paramSunGraphics2D.transY, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 127 */     paramSunGraphics2D.loops.fillRectLoop.FillRect(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 137 */     paramSunGraphics2D.shapepipe.fill(paramSunGraphics2D, new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 145 */     paramSunGraphics2D.shapepipe.fill(paramSunGraphics2D, new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 152 */     paramSunGraphics2D.shapepipe.fill(paramSunGraphics2D, new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 161 */     ShapeSpanIterator shapeSpanIterator = getFillSSI(paramSunGraphics2D);
/*     */     
/*     */     try {
/* 164 */       shapeSpanIterator.setOutputArea(paramSunGraphics2D.getCompClip());
/* 165 */       shapeSpanIterator.appendPoly(paramArrayOfint1, paramArrayOfint2, paramInt, paramSunGraphics2D.transX, paramSunGraphics2D.transY);
/* 166 */       fillSpans(paramSunGraphics2D, shapeSpanIterator);
/*     */     } finally {
/* 168 */       shapeSpanIterator.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 174 */     if (paramSunGraphics2D.strokeState == 0) {
/*     */       Path2D.Float float_;
/*     */       
/*     */       boolean bool1, bool2;
/* 178 */       if (paramSunGraphics2D.transformState <= 1) {
/* 179 */         if (paramShape instanceof Path2D.Float) {
/* 180 */           float_ = (Path2D.Float)paramShape;
/*     */         } else {
/* 182 */           float_ = new Path2D.Float(paramShape);
/*     */         } 
/* 184 */         bool1 = paramSunGraphics2D.transX;
/* 185 */         bool2 = paramSunGraphics2D.transY;
/*     */       } else {
/* 187 */         float_ = new Path2D.Float(paramShape, paramSunGraphics2D.transform);
/* 188 */         bool1 = false;
/* 189 */         bool2 = false;
/*     */       } 
/* 191 */       paramSunGraphics2D.loops.drawPathLoop.DrawPath(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), bool1, bool2, float_);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 196 */     if (paramSunGraphics2D.strokeState == 3) {
/* 197 */       fill(paramSunGraphics2D, paramSunGraphics2D.stroke.createStrokedShape(paramShape));
/*     */       
/*     */       return;
/*     */     } 
/* 201 */     ShapeSpanIterator shapeSpanIterator = getStrokeSpans(paramSunGraphics2D, paramShape);
/*     */     
/*     */     try {
/* 204 */       fillSpans(paramSunGraphics2D, shapeSpanIterator);
/*     */     } finally {
/* 206 */       shapeSpanIterator.dispose();
/*     */     } 
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
/*     */   public static ShapeSpanIterator getFillSSI(SunGraphics2D paramSunGraphics2D) {
/* 232 */     boolean bool = (paramSunGraphics2D.stroke instanceof BasicStroke && paramSunGraphics2D.strokeHint != 2) ? true : false;
/*     */     
/* 234 */     return new ShapeSpanIterator(bool);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ShapeSpanIterator getStrokeSpans(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 267 */     ShapeSpanIterator shapeSpanIterator = new ShapeSpanIterator(false);
/*     */     
/*     */     try {
/* 270 */       shapeSpanIterator.setOutputArea(paramSunGraphics2D.getCompClip());
/* 271 */       shapeSpanIterator.setRule(1);
/*     */       
/* 273 */       BasicStroke basicStroke = (BasicStroke)paramSunGraphics2D.stroke;
/* 274 */       boolean bool1 = (paramSunGraphics2D.strokeState <= 1) ? true : false;
/* 275 */       boolean bool2 = (paramSunGraphics2D.strokeHint != 2) ? true : false;
/*     */ 
/*     */       
/* 278 */       RenderEngine.strokeTo(paramShape, paramSunGraphics2D.transform, basicStroke, bool1, bool2, false, shapeSpanIterator);
/*     */     
/*     */     }
/* 281 */     catch (Throwable throwable) {
/* 282 */       shapeSpanIterator.dispose();
/* 283 */       shapeSpanIterator = null;
/* 284 */       throw new InternalError("Unable to Stroke shape (" + throwable
/* 285 */           .getMessage() + ")", throwable);
/*     */     } 
/* 287 */     return shapeSpanIterator;
/*     */   }
/*     */   
/*     */   public void fill(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 291 */     if (paramSunGraphics2D.strokeState == 0) {
/*     */       Path2D.Float float_;
/*     */       
/*     */       boolean bool1, bool2;
/* 295 */       if (paramSunGraphics2D.transformState <= 1) {
/* 296 */         if (paramShape instanceof Path2D.Float) {
/* 297 */           float_ = (Path2D.Float)paramShape;
/*     */         } else {
/* 299 */           float_ = new Path2D.Float(paramShape);
/*     */         } 
/* 301 */         bool1 = paramSunGraphics2D.transX;
/* 302 */         bool2 = paramSunGraphics2D.transY;
/*     */       } else {
/* 304 */         float_ = new Path2D.Float(paramShape, paramSunGraphics2D.transform);
/* 305 */         bool1 = false;
/* 306 */         bool2 = false;
/*     */       } 
/* 308 */       paramSunGraphics2D.loops.fillPathLoop.FillPath(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), bool1, bool2, float_);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 313 */     ShapeSpanIterator shapeSpanIterator = getFillSSI(paramSunGraphics2D);
/*     */     try {
/* 315 */       shapeSpanIterator.setOutputArea(paramSunGraphics2D.getCompClip());
/* 316 */       AffineTransform affineTransform = (paramSunGraphics2D.transformState == 0) ? null : paramSunGraphics2D.transform;
/*     */ 
/*     */ 
/*     */       
/* 320 */       shapeSpanIterator.appendPath(paramShape.getPathIterator(affineTransform));
/* 321 */       fillSpans(paramSunGraphics2D, shapeSpanIterator);
/*     */     } finally {
/* 323 */       shapeSpanIterator.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void fillSpans(SunGraphics2D paramSunGraphics2D, SpanIterator paramSpanIterator) {
/* 331 */     if (paramSunGraphics2D.clipState == 2) {
/* 332 */       paramSpanIterator = paramSunGraphics2D.clipRegion.filter(paramSpanIterator);
/*     */     }
/*     */     else {
/*     */       
/* 336 */       FillSpans fillSpans = paramSunGraphics2D.loops.fillSpansLoop;
/* 337 */       if (fillSpans != null) {
/* 338 */         fillSpans.FillSpans(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramSpanIterator);
/*     */         return;
/*     */       } 
/*     */     } 
/* 342 */     int[] arrayOfInt = new int[4];
/* 343 */     SurfaceData surfaceData = paramSunGraphics2D.getSurfaceData();
/* 344 */     while (paramSpanIterator.nextSpan(arrayOfInt)) {
/* 345 */       int i = arrayOfInt[0];
/* 346 */       int j = arrayOfInt[1];
/* 347 */       int k = arrayOfInt[2] - i;
/* 348 */       int m = arrayOfInt[3] - j;
/* 349 */       paramSunGraphics2D.loops.fillRectLoop.FillRect(paramSunGraphics2D, surfaceData, i, j, k, m);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillParallelogram(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10) {
/* 360 */     FillParallelogram fillParallelogram = paramSunGraphics2D.loops.fillParallelogramLoop;
/* 361 */     fillParallelogram.FillParallelogram(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10);
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
/*     */   public void drawParallelogram(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12) {
/* 373 */     DrawParallelogram drawParallelogram = paramSunGraphics2D.loops.drawParallelogramLoop;
/* 374 */     drawParallelogram.DrawParallelogram(paramSunGraphics2D, paramSunGraphics2D.getSurfaceData(), paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/LoopPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */