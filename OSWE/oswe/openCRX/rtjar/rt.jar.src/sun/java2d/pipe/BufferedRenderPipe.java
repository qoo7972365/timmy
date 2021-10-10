/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.Polygon;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.geom.Arc2D;
/*     */ import java.awt.geom.Ellipse2D;
/*     */ import java.awt.geom.Path2D;
/*     */ import java.awt.geom.RoundRectangle2D;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.loops.ProcessPath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BufferedRenderPipe
/*     */   implements PixelDrawPipe, PixelFillPipe, ShapeDrawPipe, ParallelogramPipe
/*     */ {
/*  58 */   ParallelogramPipe aapgrampipe = new AAParallelogramPipe();
/*     */   
/*     */   static final int BYTES_PER_POLY_POINT = 8;
/*     */   
/*     */   static final int BYTES_PER_SCANLINE = 12;
/*     */   static final int BYTES_PER_SPAN = 16;
/*     */   protected RenderQueue rq;
/*     */   protected RenderBuffer buf;
/*     */   private BufferedDrawHandler drawHandler;
/*     */   
/*     */   public BufferedRenderPipe(RenderQueue paramRenderQueue) {
/*  69 */     this.rq = paramRenderQueue;
/*  70 */     this.buf = paramRenderQueue.getBuffer();
/*  71 */     this.drawHandler = new BufferedDrawHandler();
/*     */   }
/*     */   
/*     */   public ParallelogramPipe getAAParallelogramPipe() {
/*  75 */     return this.aapgrampipe;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void validateContext(SunGraphics2D paramSunGraphics2D);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void validateContextAA(SunGraphics2D paramSunGraphics2D);
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawLine(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  89 */     int i = paramSunGraphics2D.transX;
/*  90 */     int j = paramSunGraphics2D.transY;
/*  91 */     this.rq.lock();
/*     */     try {
/*  93 */       validateContext(paramSunGraphics2D);
/*  94 */       this.rq.ensureCapacity(20);
/*  95 */       this.buf.putInt(10);
/*  96 */       this.buf.putInt(paramInt1 + i);
/*  97 */       this.buf.putInt(paramInt2 + j);
/*  98 */       this.buf.putInt(paramInt3 + i);
/*  99 */       this.buf.putInt(paramInt4 + j);
/*     */     } finally {
/* 101 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 108 */     this.rq.lock();
/*     */     try {
/* 110 */       validateContext(paramSunGraphics2D);
/* 111 */       this.rq.ensureCapacity(20);
/* 112 */       this.buf.putInt(11);
/* 113 */       this.buf.putInt(paramInt1 + paramSunGraphics2D.transX);
/* 114 */       this.buf.putInt(paramInt2 + paramSunGraphics2D.transY);
/* 115 */       this.buf.putInt(paramInt3);
/* 116 */       this.buf.putInt(paramInt4);
/*     */     } finally {
/* 118 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 125 */     this.rq.lock();
/*     */     try {
/* 127 */       validateContext(paramSunGraphics2D);
/* 128 */       this.rq.ensureCapacity(20);
/* 129 */       this.buf.putInt(20);
/* 130 */       this.buf.putInt(paramInt1 + paramSunGraphics2D.transX);
/* 131 */       this.buf.putInt(paramInt2 + paramSunGraphics2D.transY);
/* 132 */       this.buf.putInt(paramInt3);
/* 133 */       this.buf.putInt(paramInt4);
/*     */     } finally {
/* 135 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 143 */     draw(paramSunGraphics2D, new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillRoundRect(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 151 */     fill(paramSunGraphics2D, new RoundRectangle2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 158 */     draw(paramSunGraphics2D, new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillOval(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 164 */     fill(paramSunGraphics2D, new Ellipse2D.Float(paramInt1, paramInt2, paramInt3, paramInt4));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 171 */     draw(paramSunGraphics2D, new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillArc(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/* 180 */     fill(paramSunGraphics2D, new Arc2D.Float(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawPoly(final SunGraphics2D sg2d, final int[] xPoints, final int[] yPoints, final int nPoints, final boolean isClosed) {
/* 189 */     if (xPoints == null || yPoints == null) {
/* 190 */       throw new NullPointerException("coordinate array");
/*     */     }
/* 192 */     if (xPoints.length < nPoints || yPoints.length < nPoints) {
/* 193 */       throw new ArrayIndexOutOfBoundsException("coordinate array");
/*     */     }
/*     */     
/* 196 */     if (nPoints < 2) {
/*     */       return;
/*     */     }
/* 199 */     if (nPoints == 2 && !isClosed) {
/*     */       
/* 201 */       drawLine(sg2d, xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
/*     */       
/*     */       return;
/*     */     } 
/* 205 */     this.rq.lock();
/*     */     try {
/* 207 */       validateContext(sg2d);
/*     */       
/* 209 */       int i = nPoints * 8;
/* 210 */       int j = 20 + i;
/*     */       
/* 212 */       if (j <= this.buf.capacity()) {
/* 213 */         if (j > this.buf.remaining())
/*     */         {
/* 215 */           this.rq.flushNow();
/*     */         }
/* 217 */         this.buf.putInt(12);
/*     */         
/* 219 */         this.buf.putInt(nPoints);
/* 220 */         this.buf.putInt(isClosed ? 1 : 0);
/* 221 */         this.buf.putInt(sg2d.transX);
/* 222 */         this.buf.putInt(sg2d.transY);
/*     */         
/* 224 */         this.buf.put(xPoints, 0, nPoints);
/* 225 */         this.buf.put(yPoints, 0, nPoints);
/*     */       }
/*     */       else {
/*     */         
/* 229 */         this.rq.flushAndInvokeNow(new Runnable() {
/*     */               public void run() {
/* 231 */                 BufferedRenderPipe.this.drawPoly(xPoints, yPoints, nPoints, isClosed, sg2d.transX, sg2d.transY);
/*     */               }
/*     */             });
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 238 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawPoly(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolyline(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 250 */     drawPoly(paramSunGraphics2D, paramArrayOfint1, paramArrayOfint2, paramInt, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 257 */     drawPoly(paramSunGraphics2D, paramArrayOfint1, paramArrayOfint2, paramInt, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillPolygon(SunGraphics2D paramSunGraphics2D, int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt) {
/* 264 */     fill(paramSunGraphics2D, new Polygon(paramArrayOfint1, paramArrayOfint2, paramInt));
/*     */   }
/*     */   
/*     */   private class BufferedDrawHandler extends ProcessPath.DrawHandler {
/*     */     private int scanlineCount;
/*     */     private int scanlineCountIndex;
/*     */     private int remainingScanlines;
/*     */     
/*     */     BufferedDrawHandler() {
/* 273 */       super(0, 0, 0, 0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void validate(SunGraphics2D param1SunGraphics2D) {
/* 281 */       Region region = param1SunGraphics2D.getCompClip();
/* 282 */       setBounds(region.getLoX(), region.getLoY(), region
/* 283 */           .getHiX(), region.getHiY(), param1SunGraphics2D.strokeHint);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drawLine(int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 293 */       BufferedRenderPipe.this.rq.ensureCapacity(20);
/* 294 */       BufferedRenderPipe.this.buf.putInt(10);
/* 295 */       BufferedRenderPipe.this.buf.putInt(param1Int1);
/* 296 */       BufferedRenderPipe.this.buf.putInt(param1Int2);
/* 297 */       BufferedRenderPipe.this.buf.putInt(param1Int3);
/* 298 */       BufferedRenderPipe.this.buf.putInt(param1Int4);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawPixel(int param1Int1, int param1Int2) {
/* 303 */       BufferedRenderPipe.this.rq.ensureCapacity(12);
/* 304 */       BufferedRenderPipe.this.buf.putInt(13);
/* 305 */       BufferedRenderPipe.this.buf.putInt(param1Int1);
/* 306 */       BufferedRenderPipe.this.buf.putInt(param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void resetFillPath() {
/* 318 */       BufferedRenderPipe.this.buf.putInt(14);
/* 319 */       this.scanlineCountIndex = BufferedRenderPipe.this.buf.position();
/* 320 */       BufferedRenderPipe.this.buf.putInt(0);
/* 321 */       this.scanlineCount = 0;
/* 322 */       this.remainingScanlines = BufferedRenderPipe.this.buf.remaining() / 12;
/*     */     }
/*     */     
/*     */     private void updateScanlineCount() {
/* 326 */       BufferedRenderPipe.this.buf.putInt(this.scanlineCountIndex, this.scanlineCount);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void startFillPath() {
/* 334 */       BufferedRenderPipe.this.rq.ensureCapacity(20);
/* 335 */       resetFillPath();
/*     */     }
/*     */     
/*     */     public void drawScanline(int param1Int1, int param1Int2, int param1Int3) {
/* 339 */       if (this.remainingScanlines == 0) {
/* 340 */         updateScanlineCount();
/* 341 */         BufferedRenderPipe.this.rq.flushNow();
/* 342 */         resetFillPath();
/*     */       } 
/* 344 */       BufferedRenderPipe.this.buf.putInt(param1Int1);
/* 345 */       BufferedRenderPipe.this.buf.putInt(param1Int2);
/* 346 */       BufferedRenderPipe.this.buf.putInt(param1Int3);
/* 347 */       this.scanlineCount++;
/* 348 */       this.remainingScanlines--;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void endFillPath() {
/* 356 */       updateScanlineCount();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawPath(SunGraphics2D paramSunGraphics2D, Path2D.Float paramFloat, int paramInt1, int paramInt2) {
/* 363 */     this.rq.lock();
/*     */     try {
/* 365 */       validateContext(paramSunGraphics2D);
/* 366 */       this.drawHandler.validate(paramSunGraphics2D);
/* 367 */       ProcessPath.drawPath(this.drawHandler, paramFloat, paramInt1, paramInt2);
/*     */     } finally {
/* 369 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillPath(SunGraphics2D paramSunGraphics2D, Path2D.Float paramFloat, int paramInt1, int paramInt2) {
/* 376 */     this.rq.lock();
/*     */     try {
/* 378 */       validateContext(paramSunGraphics2D);
/* 379 */       this.drawHandler.validate(paramSunGraphics2D);
/* 380 */       this.drawHandler.startFillPath();
/* 381 */       ProcessPath.fillPath(this.drawHandler, paramFloat, paramInt1, paramInt2);
/* 382 */       this.drawHandler.endFillPath();
/*     */     } finally {
/* 384 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private native int fillSpans(RenderQueue paramRenderQueue, long paramLong1, int paramInt1, int paramInt2, SpanIterator paramSpanIterator, long paramLong2, int paramInt3, int paramInt4);
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fillSpans(SunGraphics2D paramSunGraphics2D, SpanIterator paramSpanIterator, int paramInt1, int paramInt2) {
/* 396 */     this.rq.lock();
/*     */     try {
/* 398 */       validateContext(paramSunGraphics2D);
/* 399 */       this.rq.ensureCapacity(24);
/* 400 */       int i = fillSpans(this.rq, this.buf.getAddress(), this.buf
/* 401 */           .position(), this.buf.capacity(), paramSpanIterator, paramSpanIterator
/* 402 */           .getNativeIterator(), paramInt1, paramInt2);
/*     */       
/* 404 */       this.buf.position(i);
/*     */     } finally {
/* 406 */       this.rq.unlock();
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
/* 417 */     this.rq.lock();
/*     */     try {
/* 419 */       validateContext(paramSunGraphics2D);
/* 420 */       this.rq.ensureCapacity(28);
/* 421 */       this.buf.putInt(22);
/* 422 */       this.buf.putFloat((float)paramDouble5);
/* 423 */       this.buf.putFloat((float)paramDouble6);
/* 424 */       this.buf.putFloat((float)paramDouble7);
/* 425 */       this.buf.putFloat((float)paramDouble8);
/* 426 */       this.buf.putFloat((float)paramDouble9);
/* 427 */       this.buf.putFloat((float)paramDouble10);
/*     */     } finally {
/* 429 */       this.rq.unlock();
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
/*     */   public void drawParallelogram(SunGraphics2D paramSunGraphics2D, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12) {
/* 441 */     this.rq.lock();
/*     */     try {
/* 443 */       validateContext(paramSunGraphics2D);
/* 444 */       this.rq.ensureCapacity(36);
/* 445 */       this.buf.putInt(15);
/* 446 */       this.buf.putFloat((float)paramDouble5);
/* 447 */       this.buf.putFloat((float)paramDouble6);
/* 448 */       this.buf.putFloat((float)paramDouble7);
/* 449 */       this.buf.putFloat((float)paramDouble8);
/* 450 */       this.buf.putFloat((float)paramDouble9);
/* 451 */       this.buf.putFloat((float)paramDouble10);
/* 452 */       this.buf.putFloat((float)paramDouble11);
/* 453 */       this.buf.putFloat((float)paramDouble12);
/*     */     } finally {
/* 455 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private class AAParallelogramPipe
/*     */     implements ParallelogramPipe
/*     */   {
/*     */     private AAParallelogramPipe() {}
/*     */ 
/*     */     
/*     */     public void fillParallelogram(SunGraphics2D param1SunGraphics2D, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10) {
/* 467 */       BufferedRenderPipe.this.rq.lock();
/*     */       try {
/* 469 */         BufferedRenderPipe.this.validateContextAA(param1SunGraphics2D);
/* 470 */         BufferedRenderPipe.this.rq.ensureCapacity(28);
/* 471 */         BufferedRenderPipe.this.buf.putInt(23);
/* 472 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double5);
/* 473 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double6);
/* 474 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double7);
/* 475 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double8);
/* 476 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double9);
/* 477 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double10);
/*     */       } finally {
/* 479 */         BufferedRenderPipe.this.rq.unlock();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void drawParallelogram(SunGraphics2D param1SunGraphics2D, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10, double param1Double11, double param1Double12) {
/* 491 */       BufferedRenderPipe.this.rq.lock();
/*     */       try {
/* 493 */         BufferedRenderPipe.this.validateContextAA(param1SunGraphics2D);
/* 494 */         BufferedRenderPipe.this.rq.ensureCapacity(36);
/* 495 */         BufferedRenderPipe.this.buf.putInt(16);
/* 496 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double5);
/* 497 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double6);
/* 498 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double7);
/* 499 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double8);
/* 500 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double9);
/* 501 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double10);
/* 502 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double11);
/* 503 */         BufferedRenderPipe.this.buf.putFloat((float)param1Double12);
/*     */       } finally {
/* 505 */         BufferedRenderPipe.this.rq.unlock();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void draw(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 511 */     if (paramSunGraphics2D.strokeState == 0) {
/* 512 */       Path2D.Float float_; boolean bool1, bool2; if (paramShape instanceof Polygon && 
/* 513 */         paramSunGraphics2D.transformState < 3) {
/* 514 */         Polygon polygon = (Polygon)paramShape;
/* 515 */         drawPolygon(paramSunGraphics2D, polygon.xpoints, polygon.ypoints, polygon.npoints);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 521 */       if (paramSunGraphics2D.transformState <= 1) {
/* 522 */         if (paramShape instanceof Path2D.Float) {
/* 523 */           float_ = (Path2D.Float)paramShape;
/*     */         } else {
/* 525 */           float_ = new Path2D.Float(paramShape);
/*     */         } 
/* 527 */         bool1 = paramSunGraphics2D.transX;
/* 528 */         bool2 = paramSunGraphics2D.transY;
/*     */       } else {
/* 530 */         float_ = new Path2D.Float(paramShape, paramSunGraphics2D.transform);
/* 531 */         bool1 = false;
/* 532 */         bool2 = false;
/*     */       } 
/* 534 */       drawPath(paramSunGraphics2D, float_, bool1, bool2);
/* 535 */     } else if (paramSunGraphics2D.strokeState < 3) {
/* 536 */       ShapeSpanIterator shapeSpanIterator = LoopPipe.getStrokeSpans(paramSunGraphics2D, paramShape);
/*     */       try {
/* 538 */         fillSpans(paramSunGraphics2D, shapeSpanIterator, 0, 0);
/*     */       } finally {
/* 540 */         shapeSpanIterator.dispose();
/*     */       } 
/*     */     } else {
/* 543 */       fill(paramSunGraphics2D, paramSunGraphics2D.stroke.createStrokedShape(paramShape));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fill(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/*     */     byte b1, b2;
/*     */     AffineTransform affineTransform;
/* 550 */     if (paramSunGraphics2D.strokeState == 0) {
/*     */       Path2D.Float float_;
/*     */ 
/*     */       
/* 554 */       if (paramSunGraphics2D.transformState <= 1) {
/* 555 */         if (paramShape instanceof Path2D.Float) {
/* 556 */           float_ = (Path2D.Float)paramShape;
/*     */         } else {
/* 558 */           float_ = new Path2D.Float(paramShape);
/*     */         } 
/* 560 */         b1 = paramSunGraphics2D.transX;
/* 561 */         b2 = paramSunGraphics2D.transY;
/*     */       } else {
/* 563 */         float_ = new Path2D.Float(paramShape, paramSunGraphics2D.transform);
/* 564 */         b1 = 0;
/* 565 */         b2 = 0;
/*     */       } 
/* 567 */       fillPath(paramSunGraphics2D, float_, b1, b2);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 572 */     if (paramSunGraphics2D.transformState <= 1) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 577 */       affineTransform = null;
/* 578 */       b1 = paramSunGraphics2D.transX;
/* 579 */       b2 = paramSunGraphics2D.transY;
/*     */     } else {
/*     */       
/* 582 */       affineTransform = paramSunGraphics2D.transform;
/* 583 */       b1 = b2 = 0;
/*     */     } 
/*     */     
/* 586 */     ShapeSpanIterator shapeSpanIterator = LoopPipe.getFillSSI(paramSunGraphics2D);
/*     */ 
/*     */     
/*     */     try {
/* 590 */       Region region = paramSunGraphics2D.getCompClip();
/* 591 */       shapeSpanIterator.setOutputAreaXYXY(region.getLoX() - b1, region
/* 592 */           .getLoY() - b2, region
/* 593 */           .getHiX() - b1, region
/* 594 */           .getHiY() - b2);
/* 595 */       shapeSpanIterator.appendPath(paramShape.getPathIterator(affineTransform));
/* 596 */       fillSpans(paramSunGraphics2D, shapeSpanIterator, b1, b2);
/*     */     } finally {
/* 598 */       shapeSpanIterator.dispose();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/BufferedRenderPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */