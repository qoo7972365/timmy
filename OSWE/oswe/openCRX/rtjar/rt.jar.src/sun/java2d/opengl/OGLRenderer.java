/*     */ package sun.java2d.opengl;
/*     */ 
/*     */ import java.awt.geom.Path2D;
/*     */ import sun.java2d.InvalidPipeException;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.loops.GraphicsPrimitive;
/*     */ import sun.java2d.pipe.BufferedRenderPipe;
/*     */ import sun.java2d.pipe.ParallelogramPipe;
/*     */ import sun.java2d.pipe.RenderQueue;
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
/*     */ class OGLRenderer
/*     */   extends BufferedRenderPipe
/*     */ {
/*     */   OGLRenderer(RenderQueue paramRenderQueue) {
/*  42 */     super(paramRenderQueue);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void validateContext(SunGraphics2D paramSunGraphics2D) {
/*     */     OGLSurfaceData oGLSurfaceData;
/*  48 */     boolean bool = (paramSunGraphics2D.paint.getTransparency() == 1) ? true : false;
/*     */ 
/*     */     
/*     */     try {
/*  52 */       oGLSurfaceData = (OGLSurfaceData)paramSunGraphics2D.surfaceData;
/*  53 */     } catch (ClassCastException classCastException) {
/*  54 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*  56 */     OGLContext.validateContext(oGLSurfaceData, oGLSurfaceData, paramSunGraphics2D
/*  57 */         .getCompClip(), paramSunGraphics2D.composite, null, paramSunGraphics2D.paint, paramSunGraphics2D, bool);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void validateContextAA(SunGraphics2D paramSunGraphics2D) {
/*     */     OGLSurfaceData oGLSurfaceData;
/*  63 */     boolean bool = false;
/*     */     
/*     */     try {
/*  66 */       oGLSurfaceData = (OGLSurfaceData)paramSunGraphics2D.surfaceData;
/*  67 */     } catch (ClassCastException classCastException) {
/*  68 */       throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */     } 
/*  70 */     OGLContext.validateContext(oGLSurfaceData, oGLSurfaceData, paramSunGraphics2D
/*  71 */         .getCompClip(), paramSunGraphics2D.composite, null, paramSunGraphics2D.paint, paramSunGraphics2D, bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void copyArea(SunGraphics2D paramSunGraphics2D, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6) {
/*  78 */     this.rq.lock();
/*     */     try {
/*     */       OGLSurfaceData oGLSurfaceData;
/*  81 */       boolean bool = (paramSunGraphics2D.surfaceData.getTransparency() == 1) ? true : false;
/*     */ 
/*     */       
/*     */       try {
/*  85 */         oGLSurfaceData = (OGLSurfaceData)paramSunGraphics2D.surfaceData;
/*  86 */       } catch (ClassCastException classCastException) {
/*  87 */         throw new InvalidPipeException("wrong surface data type: " + paramSunGraphics2D.surfaceData);
/*     */       } 
/*  89 */       OGLContext.validateContext(oGLSurfaceData, oGLSurfaceData, paramSunGraphics2D
/*  90 */           .getCompClip(), paramSunGraphics2D.composite, null, null, null, bool);
/*     */ 
/*     */       
/*  93 */       this.rq.ensureCapacity(28);
/*  94 */       this.buf.putInt(30);
/*  95 */       this.buf.putInt(paramInt1).putInt(paramInt2).putInt(paramInt3).putInt(paramInt4);
/*  96 */       this.buf.putInt(paramInt5).putInt(paramInt6);
/*     */     } finally {
/*  98 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected native void drawPoly(int[] paramArrayOfint1, int[] paramArrayOfint2, int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
/*     */ 
/*     */   
/*     */   OGLRenderer traceWrap() {
/* 108 */     return new Tracer(this);
/*     */   }
/*     */   
/*     */   private class Tracer
/*     */     extends OGLRenderer {
/*     */     Tracer(OGLRenderer param1OGLRenderer1) {
/* 114 */       super(param1OGLRenderer1.rq);
/* 115 */       this.oglr = param1OGLRenderer1;
/*     */     } private OGLRenderer oglr;
/*     */     public ParallelogramPipe getAAParallelogramPipe() {
/* 118 */       final ParallelogramPipe realpipe = this.oglr.getAAParallelogramPipe();
/* 119 */       return new ParallelogramPipe()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void fillParallelogram(SunGraphics2D param2SunGraphics2D, double param2Double1, double param2Double2, double param2Double3, double param2Double4, double param2Double5, double param2Double6, double param2Double7, double param2Double8, double param2Double9, double param2Double10)
/*     */           {
/* 127 */             GraphicsPrimitive.tracePrimitive("OGLFillAAParallelogram");
/* 128 */             realpipe.fillParallelogram(param2SunGraphics2D, param2Double1, param2Double2, param2Double3, param2Double4, param2Double5, param2Double6, param2Double7, param2Double8, param2Double9, param2Double10);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void drawParallelogram(SunGraphics2D param2SunGraphics2D, double param2Double1, double param2Double2, double param2Double3, double param2Double4, double param2Double5, double param2Double6, double param2Double7, double param2Double8, double param2Double9, double param2Double10, double param2Double11, double param2Double12) {
/* 140 */             GraphicsPrimitive.tracePrimitive("OGLDrawAAParallelogram");
/* 141 */             realpipe.drawParallelogram(param2SunGraphics2D, param2Double1, param2Double2, param2Double3, param2Double4, param2Double5, param2Double6, param2Double7, param2Double8, param2Double9, param2Double10, param2Double11, param2Double12);
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void validateContext(SunGraphics2D param1SunGraphics2D) {
/* 149 */       this.oglr.validateContext(param1SunGraphics2D);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drawLine(SunGraphics2D param1SunGraphics2D, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 154 */       GraphicsPrimitive.tracePrimitive("OGLDrawLine");
/* 155 */       this.oglr.drawLine(param1SunGraphics2D, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */     public void drawRect(SunGraphics2D param1SunGraphics2D, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 158 */       GraphicsPrimitive.tracePrimitive("OGLDrawRect");
/* 159 */       this.oglr.drawRect(param1SunGraphics2D, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void drawPoly(SunGraphics2D param1SunGraphics2D, int[] param1ArrayOfint1, int[] param1ArrayOfint2, int param1Int, boolean param1Boolean) {
/* 165 */       GraphicsPrimitive.tracePrimitive("OGLDrawPoly");
/* 166 */       this.oglr.drawPoly(param1SunGraphics2D, param1ArrayOfint1, param1ArrayOfint2, param1Int, param1Boolean);
/*     */     }
/*     */     public void fillRect(SunGraphics2D param1SunGraphics2D, int param1Int1, int param1Int2, int param1Int3, int param1Int4) {
/* 169 */       GraphicsPrimitive.tracePrimitive("OGLFillRect");
/* 170 */       this.oglr.fillRect(param1SunGraphics2D, param1Int1, param1Int2, param1Int3, param1Int4);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawPath(SunGraphics2D param1SunGraphics2D, Path2D.Float param1Float, int param1Int1, int param1Int2) {
/* 175 */       GraphicsPrimitive.tracePrimitive("OGLDrawPath");
/* 176 */       this.oglr.drawPath(param1SunGraphics2D, param1Float, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void fillPath(SunGraphics2D param1SunGraphics2D, Path2D.Float param1Float, int param1Int1, int param1Int2) {
/* 181 */       GraphicsPrimitive.tracePrimitive("OGLFillPath");
/* 182 */       this.oglr.fillPath(param1SunGraphics2D, param1Float, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void fillSpans(SunGraphics2D param1SunGraphics2D, SpanIterator param1SpanIterator, int param1Int1, int param1Int2) {
/* 187 */       GraphicsPrimitive.tracePrimitive("OGLFillSpans");
/* 188 */       this.oglr.fillSpans(param1SunGraphics2D, param1SpanIterator, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void fillParallelogram(SunGraphics2D param1SunGraphics2D, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10) {
/* 197 */       GraphicsPrimitive.tracePrimitive("OGLFillParallelogram");
/* 198 */       this.oglr.fillParallelogram(param1SunGraphics2D, param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6, param1Double7, param1Double8, param1Double9, param1Double10);
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
/*     */     public void drawParallelogram(SunGraphics2D param1SunGraphics2D, double param1Double1, double param1Double2, double param1Double3, double param1Double4, double param1Double5, double param1Double6, double param1Double7, double param1Double8, double param1Double9, double param1Double10, double param1Double11, double param1Double12) {
/* 210 */       GraphicsPrimitive.tracePrimitive("OGLDrawParallelogram");
/* 211 */       this.oglr.drawParallelogram(param1SunGraphics2D, param1Double1, param1Double2, param1Double3, param1Double4, param1Double5, param1Double6, param1Double7, param1Double8, param1Double9, param1Double10, param1Double11, param1Double12);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void copyArea(SunGraphics2D param1SunGraphics2D, int param1Int1, int param1Int2, int param1Int3, int param1Int4, int param1Int5, int param1Int6) {
/* 218 */       GraphicsPrimitive.tracePrimitive("OGLCopyArea");
/* 219 */       this.oglr.copyArea(param1SunGraphics2D, param1Int1, param1Int2, param1Int3, param1Int4, param1Int5, param1Int6);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/opengl/OGLRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */