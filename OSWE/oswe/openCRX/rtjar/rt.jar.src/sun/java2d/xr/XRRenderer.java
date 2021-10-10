/*     */ package sun.java2d.xr;
/*     */ 
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Path2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.geom.RoundRectangle2D;
/*     */ import sun.awt.SunToolkit;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.loops.ProcessPath;
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
/*     */ public class XRRenderer
/*     */   implements PixelDrawPipe, PixelFillPipe, ShapeDrawPipe
/*     */ {
/*     */   XRDrawHandler drawHandler;
/*     */   MaskTileManager tileManager;
/*     */   XRDrawLine lineGen;
/*     */   GrowableRectArray rectBuffer;
/*     */   
/*     */   public XRRenderer(MaskTileManager paramMaskTileManager) {
/*  61 */     this.tileManager = paramMaskTileManager;
/*  62 */     this.rectBuffer = paramMaskTileManager.getMainTile().getRects();
/*     */     
/*  64 */     this.drawHandler = new XRDrawHandler();
/*  65 */     this.lineGen = new XRDrawLine();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final void validateSurface(SunGraphics2D paramSunGraphics2D) {
/*     */     XRSurfaceData xRSurfaceData;
/*     */     try {
/*  75 */       xRSurfaceData = (XRSurfaceData)paramSunGraphics2D.surfaceData;
/*  76 */     } catch (ClassCastException classCastException) {
/*  77 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*  79 */     xRSurfaceData.validateAsDestination(paramSunGraphics2D, paramSunGraphics2D.getCompClip());
/*  80 */     xRSurfaceData.maskBuffer.validateCompositeState(paramSunGraphics2D.composite, paramSunGraphics2D.transform, paramSunGraphics2D.paint, paramSunGraphics2D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawLine(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  85 */     Region region = paramSunGraphics2D.getCompClip();
/*  86 */     int i = Region.clipAdd(paramInt1, paramSunGraphics2D.transX);
/*  87 */     int j = Region.clipAdd(paramInt2, paramSunGraphics2D.transY);
/*  88 */     int k = Region.clipAdd(paramInt3, paramSunGraphics2D.transX);
/*  89 */     int m = Region.clipAdd(paramInt4, paramSunGraphics2D.transY);
/*     */     
/*  91 */     SunToolkit.awtLock();
/*     */     try {
/*  93 */       validateSurface(paramSunGraphics2D);
/*  94 */       this.lineGen.rasterizeLine(this.rectBuffer, i, j, k, m, region
/*  95 */           .getLoX(), region.getLoY(), region
/*  96 */           .getHiX(), region.getHiY(), true, true);
/*  97 */       this.tileManager.fillMask((XRSurfaceData)paramSunGraphics2D.surfaceData);
/*     */     } finally {
/*  99 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 105 */     draw(paramSunGraphics2D, new Rectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawPolyline(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 110 */     Path2D.Float float_ = new Path2D.Float();
/* 111 */     if (paramInt > 1) {
/* 112 */       float_.moveTo(paramArrayOfint1[0], paramArrayOfint2[0]);
/* 113 */       for (byte b = 1; b < paramInt; b++) {
/* 114 */         float_.lineTo(paramArrayOfint1[b], paramArrayOfint2[b]);
/*     */       }
/*     */     } 
/*     */     
/* 118 */     draw(paramSunGraphics2D, float_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 123 */     draw(paramSunGraphics2D, new Polygon(paramArrayOfint1, paramArrayOfint2, paramInt));
/*     */   }
/*     */   
/*     */   public void fillRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 127 */     paramInt1 = Region.clipAdd(paramInt1, paramSunGraphics2D.transX);
/* 128 */     paramInt2 = Region.clipAdd(paramInt2, paramSunGraphics2D.transY);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (paramInt1 > 32767 || paramInt2 > 32767) {
/*     */       return;
/*     */     }
/*     */     
/* 139 */     int i = Region.dimAdd(paramInt1, paramInt3);
/* 140 */     int j = Region.dimAdd(paramInt2, paramInt4);
/*     */     
/* 142 */     if (i < -32768 || j < -32768) {
/*     */       return;
/*     */     }
/*     */     
/* 146 */     paramInt1 = XRUtils.clampToShort(paramInt1);
/* 147 */     paramInt2 = XRUtils.clampToShort(paramInt2);
/* 148 */     paramInt3 = XRUtils.clampToUShort(i - paramInt1);
/* 149 */     paramInt4 = XRUtils.clampToUShort(j - paramInt2);
/*     */     
/* 151 */     if (paramInt3 == 0 || paramInt4 == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     SunToolkit.awtLock();
/*     */     try {
/* 157 */       validateSurface(paramSunGraphics2D);
/* 158 */       this.rectBuffer.pushRectValues(paramInt1, paramInt2, paramInt3, paramInt4);
/* 159 */       this.tileManager.fillMask((XRSurfaceData)paramSunGraphics2D.surfaceData);
/*     */     } finally {
/* 161 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 167 */     fill(paramSunGraphics2D, new Polygon(paramArrayOfint1, paramArrayOfint2, paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 173 */     draw(paramSunGraphics2D, new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 180 */     fill(paramSunGraphics2D, new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 186 */     draw(paramSunGraphics2D, new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 191 */     fill(paramSunGraphics2D, new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 197 */     draw(paramSunGraphics2D, new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 204 */     fill(paramSunGraphics2D, new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 2));
/*     */   }
/*     */ 
/*     */   
/*     */   private class XRDrawHandler
/*     */     extends ProcessPath.DrawHandler
/*     */   {
/*     */     DirtyRegion region;
/*     */     
/*     */     XRDrawHandler() {
/* 214 */       super(0, 0, 0, 0);
/* 215 */       this.region = new DirtyRegion();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void validate(SunGraphics2D param1SunGraphics2D) {
/* 223 */       Region region = param1SunGraphics2D.getCompClip();
/* 224 */       setBounds(region.getLoX(), region.getLoY(), region
/* 225 */           .getHiX(), region.getHiY(), param1SunGraphics2D.strokeHint);
/* 226 */       XRRenderer.this.validateSurface(param1SunGraphics2D);
/*     */     }
/*     */     
/*     */     public void drawLine(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 230 */       this.region.setDirtyLineRegion(param1Int1, param1Int2, param1Int3, param1Int4);
/* 231 */       int i = this.region.x2 - this.region.x;
/* 232 */       int j = this.region.y2 - this.region.y;
/*     */       
/* 234 */       if (i == 0 || j == 0) {
/*     */ 
/*     */         
/* 237 */         XRRenderer.this.rectBuffer.pushRectValues(this.region.x, this.region.y, this.region.x2 - this.region.x + 1, this.region.y2 - this.region.y + 1);
/*     */       }
/* 239 */       else if (i == 1 && j == 1) {
/*     */ 
/*     */         
/* 242 */         XRRenderer.this.rectBuffer.pushRectValues(param1Int1, param1Int2, 1, 1);
/* 243 */         XRRenderer.this.rectBuffer.pushRectValues(param1Int3, param1Int4, 1, 1);
/*     */       } else {
/* 245 */         XRRenderer.this.lineGen.rasterizeLine(XRRenderer.this.rectBuffer, param1Int1, param1Int2, param1Int3, param1Int4, 0, 0, 0, 0, false, false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawPixel(int param1Int1, int param1Int2) {
/* 251 */       XRRenderer.this.rectBuffer.pushRectValues(param1Int1, param1Int2, 1, 1);
/*     */     }
/*     */     
/*     */     public void drawScanline(int param1Int1, int param1Int2, int param1Int3) {
/* 255 */       XRRenderer.this.rectBuffer.pushRectValues(param1Int1, param1Int3, param1Int2 - param1Int1 + 1, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawPath(SunGraphics2D paramSunGraphics2D, Path2D.Float paramFloat, int paramInt1, int paramInt2) {
/* 261 */     SunToolkit.awtLock();
/*     */     try {
/* 263 */       validateSurface(paramSunGraphics2D);
/* 264 */       this.drawHandler.validate(paramSunGraphics2D);
/* 265 */       ProcessPath.drawPath(this.drawHandler, paramFloat, paramInt1, paramInt2);
/* 266 */       this.tileManager.fillMask((XRSurfaceData)paramSunGraphics2D.surfaceData);
/*     */     } finally {
/* 268 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fillPath(SunGraphics2D paramSunGraphics2D, Path2D.Float paramFloat, int paramInt1, int paramInt2) {
/* 274 */     SunToolkit.awtLock();
/*     */     try {
/* 276 */       validateSurface(paramSunGraphics2D);
/* 277 */       this.drawHandler.validate(paramSunGraphics2D);
/* 278 */       ProcessPath.fillPath(this.drawHandler, paramFloat, paramInt1, paramInt2);
/* 279 */       this.tileManager.fillMask((XRSurfaceData)paramSunGraphics2D.surfaceData);
/*     */     } finally {
/* 281 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fillSpans(SunGraphics2D paramSunGraphics2D, SpanIterator paramSpanIterator, int paramInt1, int paramInt2) {
/* 287 */     SunToolkit.awtLock();
/*     */     try {
/* 289 */       validateSurface(paramSunGraphics2D);
/* 290 */       int[] arrayOfInt = new int[4];
/* 291 */       while (paramSpanIterator.nextSpan(arrayOfInt)) {
/* 292 */         this.rectBuffer.pushRectValues(arrayOfInt[0] + paramInt1, arrayOfInt[1] + paramInt2, arrayOfInt[2] - arrayOfInt[0], arrayOfInt[3] - arrayOfInt[1]);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 297 */       this.tileManager.fillMask((XRSurfaceData)paramSunGraphics2D.surfaceData);
/*     */     } finally {
/* 299 */       SunToolkit.awtUnlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void draw(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 304 */     if (paramSunGraphics2D.strokeState == 0) {
/*     */       Path2D.Float float_;
/*     */       boolean bool1, bool2;
/* 307 */       if (paramSunGraphics2D.transformState <= 1) {
/* 308 */         if (paramShape instanceof Path2D.Float) {
/* 309 */           float_ = (Path2D.Float)paramShape;
/*     */         } else {
/* 311 */           float_ = new Path2D.Float(paramShape);
/*     */         } 
/* 313 */         bool1 = paramSunGraphics2D.transX;
/* 314 */         bool2 = paramSunGraphics2D.transY;
/*     */       } else {
/* 316 */         float_ = new Path2D.Float(paramShape, paramSunGraphics2D.transform);
/* 317 */         bool1 = false;
/* 318 */         bool2 = false;
/*     */       } 
/* 320 */       drawPath(paramSunGraphics2D, float_, bool1, bool2);
/* 321 */     } else if (paramSunGraphics2D.strokeState < 3) {
/* 322 */       ShapeSpanIterator shapeSpanIterator = LoopPipe.getStrokeSpans(paramSunGraphics2D, paramShape);
/*     */       try {
/* 324 */         fillSpans(paramSunGraphics2D, shapeSpanIterator, 0, 0);
/*     */       } finally {
/* 326 */         shapeSpanIterator.dispose();
/*     */       } 
/*     */     } else {
/* 329 */       fill(paramSunGraphics2D, paramSunGraphics2D.stroke.createStrokedShape(paramShape));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fill(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/*     */     byte b1, b2;
/*     */     AffineTransform affineTransform;
/* 336 */     if (paramSunGraphics2D.strokeState == 0) {
/*     */       Path2D.Float float_;
/*     */ 
/*     */       
/* 340 */       if (paramSunGraphics2D.transformState <= 1) {
/* 341 */         if (paramShape instanceof Path2D.Float) {
/* 342 */           float_ = (Path2D.Float)paramShape;
/*     */         } else {
/* 344 */           float_ = new Path2D.Float(paramShape);
/*     */         } 
/* 346 */         b1 = paramSunGraphics2D.transX;
/* 347 */         b2 = paramSunGraphics2D.transY;
/*     */       } else {
/* 349 */         float_ = new Path2D.Float(paramShape, paramSunGraphics2D.transform);
/* 350 */         b1 = 0;
/* 351 */         b2 = 0;
/*     */       } 
/* 353 */       fillPath(paramSunGraphics2D, float_, b1, b2);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 358 */     if (paramSunGraphics2D.transformState <= 1) {
/*     */       
/* 360 */       affineTransform = null;
/* 361 */       b1 = paramSunGraphics2D.transX;
/* 362 */       b2 = paramSunGraphics2D.transY;
/*     */     } else {
/*     */       
/* 365 */       affineTransform = paramSunGraphics2D.transform;
/* 366 */       b1 = b2 = 0;
/*     */     } 
/*     */     
/* 369 */     ShapeSpanIterator shapeSpanIterator = LoopPipe.getFillSSI(paramSunGraphics2D);
/*     */ 
/*     */     
/*     */     try {
/* 373 */       Region region = paramSunGraphics2D.getCompClip();
/* 374 */       shapeSpanIterator.setOutputAreaXYXY(region.getLoX() - b1, region
/* 375 */           .getLoY() - b2, region
/* 376 */           .getHiX() - b1, region
/* 377 */           .getHiY() - b2);
/* 378 */       shapeSpanIterator.appendPath(paramShape.getPathIterator(affineTransform));
/* 379 */       fillSpans(paramSunGraphics2D, shapeSpanIterator, b1, b2);
/*     */     } finally {
/* 381 */       shapeSpanIterator.dispose();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XRRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */