/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
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
/*     */ public abstract class SpanShapeRenderer
/*     */   implements ShapeDrawPipe
/*     */ {
/*  45 */   static final RenderingEngine RenderEngine = RenderingEngine.getInstance();
/*     */   public static final int NON_RECTILINEAR_TRANSFORM_MASK = 48;
/*     */   
/*     */   public static class Composite
/*     */     extends SpanShapeRenderer {
/*     */     public Composite(CompositePipe param1CompositePipe) {
/*  51 */       this.comppipe = param1CompositePipe;
/*     */     }
/*     */     CompositePipe comppipe;
/*     */     
/*     */     public Object startSequence(SunGraphics2D param1SunGraphics2D, Shape param1Shape, Rectangle param1Rectangle, int[] param1ArrayOfint) {
/*  56 */       return this.comppipe.startSequence(param1SunGraphics2D, param1Shape, param1Rectangle, param1ArrayOfint);
/*     */     }
/*     */     
/*     */     public void renderBox(Object param1Object, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  60 */       this.comppipe.renderPathTile(param1Object, null, 0, param1Int3, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */     
/*     */     public void endSequence(Object param1Object) {
/*  64 */       this.comppipe.endSequence(param1Object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Simple
/*     */     extends SpanShapeRenderer
/*     */     implements LoopBasedPipe
/*     */   {
/*     */     public Object startSequence(SunGraphics2D param1SunGraphics2D, Shape param1Shape, Rectangle param1Rectangle, int[] param1ArrayOfint) {
/*  73 */       return param1SunGraphics2D;
/*     */     }
/*     */     
/*     */     public void renderBox(Object param1Object, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/*  77 */       SunGraphics2D sunGraphics2D = (SunGraphics2D)param1Object;
/*  78 */       SurfaceData surfaceData = sunGraphics2D.getSurfaceData();
/*  79 */       sunGraphics2D.loops.fillRectLoop.FillRect(sunGraphics2D, surfaceData, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */     
/*     */     public void endSequence(Object param1Object) {}
/*     */   }
/*     */   
/*     */   public void draw(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/*  87 */     if (paramSunGraphics2D.stroke instanceof java.awt.BasicStroke) {
/*  88 */       ShapeSpanIterator shapeSpanIterator = LoopPipe.getStrokeSpans(paramSunGraphics2D, paramShape);
/*     */       try {
/*  90 */         renderSpans(paramSunGraphics2D, paramSunGraphics2D.getCompClip(), paramShape, shapeSpanIterator);
/*     */       } finally {
/*  92 */         shapeSpanIterator.dispose();
/*     */       } 
/*     */     } else {
/*  95 */       fill(paramSunGraphics2D, paramSunGraphics2D.stroke.createStrokedShape(paramShape));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fill(SunGraphics2D paramSunGraphics2D, Shape paramShape) {
/* 104 */     if (paramShape instanceof Rectangle2D && (paramSunGraphics2D.transform
/* 105 */       .getType() & 0x30) == 0) {
/*     */       
/* 107 */       renderRect(paramSunGraphics2D, (Rectangle2D)paramShape);
/*     */       
/*     */       return;
/*     */     } 
/* 111 */     Region region = paramSunGraphics2D.getCompClip();
/* 112 */     ShapeSpanIterator shapeSpanIterator = LoopPipe.getFillSSI(paramSunGraphics2D);
/*     */     try {
/* 114 */       shapeSpanIterator.setOutputArea(region);
/* 115 */       shapeSpanIterator.appendPath(paramShape.getPathIterator(paramSunGraphics2D.transform));
/* 116 */       renderSpans(paramSunGraphics2D, region, paramShape, shapeSpanIterator);
/*     */     } finally {
/* 118 */       shapeSpanIterator.dispose();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract Object startSequence(SunGraphics2D paramSunGraphics2D, Shape paramShape, Rectangle paramRectangle, int[] paramArrayOfint);
/*     */ 
/*     */   
/*     */   public abstract void renderBox(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   public abstract void endSequence(Object paramObject);
/*     */   
/*     */   public void renderRect(SunGraphics2D paramSunGraphics2D, Rectangle2D paramRectangle2D) {
/* 131 */     double[] arrayOfDouble = { paramRectangle2D.getX(), paramRectangle2D.getY(), paramRectangle2D.getWidth(), paramRectangle2D.getHeight() };
/*     */     
/* 133 */     arrayOfDouble[2] = arrayOfDouble[2] + arrayOfDouble[0];
/* 134 */     arrayOfDouble[3] = arrayOfDouble[3] + arrayOfDouble[1];
/* 135 */     if (arrayOfDouble[2] <= arrayOfDouble[0] || arrayOfDouble[3] <= arrayOfDouble[1]) {
/*     */       return;
/*     */     }
/* 138 */     paramSunGraphics2D.transform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 2);
/* 139 */     if (arrayOfDouble[2] < arrayOfDouble[0]) {
/* 140 */       double d = arrayOfDouble[2];
/* 141 */       arrayOfDouble[2] = arrayOfDouble[0];
/* 142 */       arrayOfDouble[0] = d;
/*     */     } 
/* 144 */     if (arrayOfDouble[3] < arrayOfDouble[1]) {
/* 145 */       double d = arrayOfDouble[3];
/* 146 */       arrayOfDouble[3] = arrayOfDouble[1];
/* 147 */       arrayOfDouble[1] = d;
/*     */     } 
/* 149 */     int[] arrayOfInt = { (int)arrayOfDouble[0], (int)arrayOfDouble[1], (int)arrayOfDouble[2], (int)arrayOfDouble[3] };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     Rectangle rectangle = new Rectangle(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2] - arrayOfInt[0], arrayOfInt[3] - arrayOfInt[1]);
/*     */ 
/*     */     
/* 158 */     Region region = paramSunGraphics2D.getCompClip();
/* 159 */     region.clipBoxToBounds(arrayOfInt);
/* 160 */     if (arrayOfInt[0] >= arrayOfInt[2] || arrayOfInt[1] >= arrayOfInt[3]) {
/*     */       return;
/*     */     }
/* 163 */     Object object = startSequence(paramSunGraphics2D, paramRectangle2D, rectangle, arrayOfInt);
/* 164 */     if (region.isRectangular()) {
/* 165 */       renderBox(object, arrayOfInt[0], arrayOfInt[1], arrayOfInt[2] - arrayOfInt[0], arrayOfInt[3] - arrayOfInt[1]);
/*     */     }
/*     */     else {
/*     */       
/* 169 */       SpanIterator spanIterator = region.getSpanIterator(arrayOfInt);
/* 170 */       while (spanIterator.nextSpan(arrayOfInt)) {
/* 171 */         renderBox(object, arrayOfInt[0], arrayOfInt[1], arrayOfInt[2] - arrayOfInt[0], arrayOfInt[3] - arrayOfInt[1]);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 176 */     endSequence(object);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderSpans(SunGraphics2D paramSunGraphics2D, Region paramRegion, Shape paramShape, ShapeSpanIterator paramShapeSpanIterator) {
/* 182 */     Object object = null;
/* 183 */     int[] arrayOfInt = new int[4];
/*     */     try {
/* 185 */       paramShapeSpanIterator.getPathBox(arrayOfInt);
/* 186 */       Rectangle rectangle = new Rectangle(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2] - arrayOfInt[0], arrayOfInt[3] - arrayOfInt[1]);
/*     */ 
/*     */       
/* 189 */       paramRegion.clipBoxToBounds(arrayOfInt);
/* 190 */       if (arrayOfInt[0] >= arrayOfInt[2] || arrayOfInt[1] >= arrayOfInt[3]) {
/*     */         return;
/*     */       }
/* 193 */       paramShapeSpanIterator.intersectClipBox(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]);
/* 194 */       object = startSequence(paramSunGraphics2D, paramShape, rectangle, arrayOfInt);
/*     */       
/* 196 */       spanClipLoop(object, paramShapeSpanIterator, paramRegion, arrayOfInt);
/*     */     } finally {
/*     */       
/* 199 */       if (object != null) {
/* 200 */         endSequence(object);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void spanClipLoop(Object paramObject, SpanIterator paramSpanIterator, Region paramRegion, int[] paramArrayOfint) {
/* 207 */     if (!paramRegion.isRectangular()) {
/* 208 */       paramSpanIterator = paramRegion.filter(paramSpanIterator);
/*     */     }
/* 210 */     while (paramSpanIterator.nextSpan(paramArrayOfint)) {
/* 211 */       int i = paramArrayOfint[0];
/* 212 */       int j = paramArrayOfint[1];
/* 213 */       renderBox(paramObject, i, j, paramArrayOfint[2] - i, paramArrayOfint[3] - j);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/SpanShapeRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */