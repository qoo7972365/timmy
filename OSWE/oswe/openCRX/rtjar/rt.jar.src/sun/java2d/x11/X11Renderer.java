/*     */ package sun.java2d.x11;
/*     */ 
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Path2D;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.pipe.LoopPipe;
/*     */ import sun.java2d.pipe.PixelDrawPipe;
/*     */ import sun.java2d.pipe.PixelFillPipe;
/*     */ import sun.java2d.pipe.Region;
/*     */ import sun.java2d.pipe.ShapeDrawPipe;
/*     */ import sun.java2d.pipe.ShapeSpanIterator;
/*     */ import sun.java2d.pipe.SpanIterator;
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
/*     */ public class X11Renderer
/*     */   implements PixelDrawPipe, PixelFillPipe, ShapeDrawPipe
/*     */ {
/*     */   public static X11Renderer getInstance() {
/*  52 */     return GraphicsPrimitive.tracingEnabled() ? new X11TracingRenderer() : new X11Renderer();
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
/*     */   private final long validate(SunGraphics2D paramSunGraphics2D) {
/*  75 */     X11SurfaceData x11SurfaceData = (X11SurfaceData)paramSunGraphics2D.surfaceData;
/*  76 */     return x11SurfaceData.getRenderGC(paramSunGraphics2D.getCompClip(), paramSunGraphics2D.compositeState, paramSunGraphics2D.composite, paramSunGraphics2D.pixel);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void XDrawLine(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   
/*     */   public void drawLine(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  85 */     SunToolkit.awtLock();
/*     */     try {
/*  87 */       long l = validate(paramSunGraphics2D);
/*  88 */       int i = paramSunGraphics2D.transX;
/*  89 */       int j = paramSunGraphics2D.transY;
/*  90 */       XDrawLine(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramInt1 + i, paramInt2 + j, paramInt3 + i, paramInt4 + j);
/*     */     } finally {
/*     */       
/*  93 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void XDrawRect(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   
/*     */   public void drawRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 103 */     SunToolkit.awtLock();
/*     */     try {
/* 105 */       long l = validate(paramSunGraphics2D);
/* 106 */       XDrawRect(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */     } finally {
/*     */       
/* 109 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void XDrawRoundRect(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 121 */     SunToolkit.awtLock();
/*     */     try {
/* 123 */       long l = validate(paramSunGraphics2D);
/* 124 */       XDrawRoundRect(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     finally {
/*     */       
/* 128 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void XDrawOval(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   
/*     */   public void drawOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 138 */     SunToolkit.awtLock();
/*     */     try {
/* 140 */       long l = validate(paramSunGraphics2D);
/* 141 */       XDrawOval(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */     } finally {
/*     */       
/* 144 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void XDrawArc(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 156 */     SunToolkit.awtLock();
/*     */     try {
/* 158 */       long l = validate(paramSunGraphics2D);
/* 159 */       XDrawArc(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     finally {
/*     */       
/* 163 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void XDrawPoly(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt3, boolean paramBoolean);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolyline(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 176 */     SunToolkit.awtLock();
/*     */     try {
/* 178 */       long l = validate(paramSunGraphics2D);
/* 179 */       XDrawPoly(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramSunGraphics2D.transX, paramSunGraphics2D.transY, paramArrayOfint1, paramArrayOfint2, paramInt, false);
/*     */     }
/*     */     finally {
/*     */       
/* 183 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 191 */     SunToolkit.awtLock();
/*     */     try {
/* 193 */       long l = validate(paramSunGraphics2D);
/* 194 */       XDrawPoly(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramSunGraphics2D.transX, paramSunGraphics2D.transY, paramArrayOfint1, paramArrayOfint2, paramInt, true);
/*     */     }
/*     */     finally {
/*     */       
/* 198 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void XFillRect(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   
/*     */   public void fillRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 208 */     SunToolkit.awtLock();
/*     */     try {
/* 210 */       long l = validate(paramSunGraphics2D);
/* 211 */       XFillRect(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */     } finally {
/*     */       
/* 214 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void XFillRoundRect(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 226 */     SunToolkit.awtLock();
/*     */     try {
/* 228 */       long l = validate(paramSunGraphics2D);
/* 229 */       XFillRoundRect(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     finally {
/*     */       
/* 233 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void XFillOval(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   
/*     */   public void fillOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 243 */     SunToolkit.awtLock();
/*     */     try {
/* 245 */       long l = validate(paramSunGraphics2D);
/* 246 */       XFillOval(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4);
/*     */     } finally {
/*     */       
/* 249 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void XFillArc(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 261 */     SunToolkit.awtLock();
/*     */     try {
/* 263 */       long l = validate(paramSunGraphics2D);
/* 264 */       XFillArc(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramInt1 + paramSunGraphics2D.transX, paramInt2 + paramSunGraphics2D.transY, paramInt3, paramInt4, paramInt5, paramInt6);
/*     */     }
/*     */     finally {
/*     */       
/* 268 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   native void XFillPoly(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 281 */     SunToolkit.awtLock();
/*     */     try {
/* 283 */       long l = validate(paramSunGraphics2D);
/* 284 */       XFillPoly(paramSunGraphics2D.surfaceData.getNativeOps(), l, paramSunGraphics2D.transX, paramSunGraphics2D.transY, paramArrayOfint1, paramArrayOfint2, paramInt);
/*     */     } finally {
/*     */       
/* 287 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void XFillSpans(long paramLong1, long paramLong2, SpanIterator paramSpanIterator, long paramLong3, int paramInt1, int paramInt2);
/*     */ 
/*     */   
/*     */   native void XDoPath(SunGraphics2D paramSunGraphics2D, long paramLong1, long paramLong2, int paramInt1, int paramInt2, Path2D.Float paramFloat, boolean paramBoolean);
/*     */ 
/*     */   
/*     */   private void doPath(SunGraphics2D paramSunGraphics2D, Shape paramShape, boolean paramBoolean) {
/*     */     Path2D.Float float_;
/*     */     boolean bool1, bool2;
/* 302 */     if (paramSunGraphics2D.transformState <= 1) {
/* 303 */       if (paramShape instanceof Path2D.Float) {
/* 304 */         float_ = (Path2D.Float)paramShape;
/*     */       } else {
/* 306 */         float_ = new Path2D.Float(paramShape);
/*     */       } 
/* 308 */       bool1 = paramSunGraphics2D.transX;
/* 309 */       bool2 = paramSunGraphics2D.transY;
/*     */     } else {
/* 311 */       float_ = new Path2D.Float(paramShape, paramSunGraphics2D.transform);
/* 312 */       bool1 = false;
/* 313 */       bool2 = false;
/*     */     } 
/* 315 */     SunToolkit.awtLock();
/*     */     try {
/* 317 */       long l = validate(paramSunGraphics2D);
/* 318 */       XDoPath(paramSunGraphics2D, paramSunGraphics2D.surfaceData.getNativeOps(), l, bool1, bool2, float_, paramBoolean);
/*     */     } finally {
/*     */       
/* 321 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void draw(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 326 */     if (paramSunGraphics2D.strokeState == 0) {
/*     */       
/* 328 */       if (paramShape instanceof Polygon && paramSunGraphics2D.transformState < 3) {
/*     */ 
/*     */         
/* 331 */         Polygon polygon = (Polygon)paramShape;
/* 332 */         drawPolygon(paramSunGraphics2D, polygon.xpoints, polygon.ypoints, polygon.npoints);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 338 */       doPath(paramSunGraphics2D, paramShape, false);
/* 339 */     } else if (paramSunGraphics2D.strokeState < 3) {
/*     */ 
/*     */ 
/*     */       
/* 343 */       ShapeSpanIterator shapeSpanIterator = LoopPipe.getStrokeSpans(paramSunGraphics2D, paramShape);
/*     */       try {
/* 345 */         SunToolkit.awtLock();
/*     */         try {
/* 347 */           long l = validate(paramSunGraphics2D);
/* 348 */           XFillSpans(paramSunGraphics2D.surfaceData.getNativeOps(), l, shapeSpanIterator, shapeSpanIterator
/* 349 */               .getNativeIterator(), 0, 0);
/*     */         } finally {
/*     */           
/* 352 */           SunToolkit.awtUnlock();
/*     */         } 
/*     */       } finally {
/* 355 */         shapeSpanIterator.dispose();
/*     */       } 
/*     */     } else {
/* 358 */       fill(paramSunGraphics2D, paramSunGraphics2D.stroke.createStrokedShape(paramShape));
/*     */     } 
/*     */   } public void fill(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/*     */     AffineTransform affineTransform;
/*     */     byte b1, b2;
/* 363 */     if (paramSunGraphics2D.strokeState == 0) {
/*     */       
/* 365 */       if (paramShape instanceof Polygon && paramSunGraphics2D.transformState < 3) {
/*     */ 
/*     */         
/* 368 */         Polygon polygon = (Polygon)paramShape;
/* 369 */         fillPolygon(paramSunGraphics2D, polygon.xpoints, polygon.ypoints, polygon.npoints);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 375 */       doPath(paramSunGraphics2D, paramShape, true);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 381 */     if (paramSunGraphics2D.transformState < 3) {
/*     */       
/* 383 */       affineTransform = null;
/* 384 */       b1 = paramSunGraphics2D.transX;
/* 385 */       b2 = paramSunGraphics2D.transY;
/*     */     } else {
/*     */       
/* 388 */       affineTransform = paramSunGraphics2D.transform;
/* 389 */       b1 = b2 = 0;
/*     */     } 
/*     */     
/* 392 */     ShapeSpanIterator shapeSpanIterator = LoopPipe.getFillSSI(paramSunGraphics2D);
/*     */ 
/*     */     
/*     */     try {
/* 396 */       Region region = paramSunGraphics2D.getCompClip();
/* 397 */       shapeSpanIterator.setOutputAreaXYXY(region.getLoX() - b1, region
/* 398 */           .getLoY() - b2, region
/* 399 */           .getHiX() - b1, region
/* 400 */           .getHiY() - b2);
/* 401 */       shapeSpanIterator.appendPath(paramShape.getPathIterator(affineTransform));
/* 402 */       SunToolkit.awtLock();
/*     */       try {
/* 404 */         long l = validate(paramSunGraphics2D);
/* 405 */         XFillSpans(paramSunGraphics2D.surfaceData.getNativeOps(), l, shapeSpanIterator, shapeSpanIterator
/* 406 */             .getNativeIterator(), b1, b2);
/*     */       } finally {
/*     */         
/* 409 */         SunToolkit.awtUnlock();
/*     */       } 
/*     */     } finally {
/* 412 */       shapeSpanIterator.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   native void devCopyArea(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */   
/*     */   public static class X11TracingRenderer
/*     */     extends X11Renderer
/*     */   {
/*     */     void XDrawLine(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 425 */       GraphicsPrimitive.tracePrimitive("X11DrawLine");
/* 426 */       super.XDrawLine(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */     
/*     */     void XDrawRect(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 431 */       GraphicsPrimitive.tracePrimitive("X11DrawRect");
/* 432 */       super.XDrawRect(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void XDrawRoundRect(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 438 */       GraphicsPrimitive.tracePrimitive("X11DrawRoundRect");
/* 439 */       super.XDrawRoundRect(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */     }
/*     */ 
/*     */     
/*     */     void XDrawOval(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 444 */       GraphicsPrimitive.tracePrimitive("X11DrawOval");
/* 445 */       super.XDrawOval(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void XDrawArc(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 451 */       GraphicsPrimitive.tracePrimitive("X11DrawArc");
/* 452 */       super.XDrawArc(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void XDrawPoly(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int3, boolean param1Boolean) {
/* 460 */       GraphicsPrimitive.tracePrimitive("X11DrawPoly");
/* 461 */       super.XDrawPoly(param1Long1, param1Long2, param1Int1, param1Int2, param1ArrayOfint1, param1ArrayOfint2, param1Int3, param1Boolean);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void XDoPath(SunGraphics2D param1SunGraphics2D, long param1Long1, long param1Long2, int param1Int1, int param1Int2, Path2D.Float param1Float, boolean param1Boolean) {
/* 468 */       GraphicsPrimitive.tracePrimitive(param1Boolean ? "X11FillPath" : "X11DrawPath");
/*     */ 
/*     */       
/* 471 */       super.XDoPath(param1SunGraphics2D, param1Long1, param1Long2, param1Int1, param1Int2, param1Float, param1Boolean);
/*     */     }
/*     */ 
/*     */     
/*     */     void XFillRect(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 476 */       GraphicsPrimitive.tracePrimitive("X11FillRect");
/* 477 */       super.XFillRect(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void XFillRoundRect(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 483 */       GraphicsPrimitive.tracePrimitive("X11FillRoundRect");
/* 484 */       super.XFillRoundRect(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */     }
/*     */ 
/*     */     
/*     */     void XFillOval(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 489 */       GraphicsPrimitive.tracePrimitive("X11FillOval");
/* 490 */       super.XFillOval(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void XFillArc(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 496 */       GraphicsPrimitive.tracePrimitive("X11FillArc");
/* 497 */       super.XFillArc(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void XFillPoly(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int3) {
/* 505 */       GraphicsPrimitive.tracePrimitive("X11FillPoly");
/* 506 */       super.XFillPoly(param1Long1, param1Long2, param1Int1, param1Int2, param1ArrayOfint1, param1ArrayOfint2, param1Int3);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void XFillSpans(long param1Long1, long param1Long2, SpanIterator param1SpanIterator, long param1Long3, int param1Int1, int param1Int2) {
/* 512 */       GraphicsPrimitive.tracePrimitive("X11FillSpans");
/* 513 */       super.XFillSpans(param1Long1, param1Long2, param1SpanIterator, param1Long3, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void devCopyArea(long param1Long1, long param1Long2, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 521 */       GraphicsPrimitive.tracePrimitive("X11CopyArea");
/* 522 */       super.devCopyArea(param1Long1, param1Long2, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/x11/X11Renderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */