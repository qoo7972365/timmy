/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.CompositeType;
/*     */ import sun.java2d.loops.MaskFill;
/*     */ import sun.java2d.loops.SurfaceType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BufferedMaskFill
/*     */   extends MaskFill
/*     */ {
/*     */   protected final RenderQueue rq;
/*     */   
/*     */   protected BufferedMaskFill(RenderQueue paramRenderQueue, SurfaceType paramSurfaceType1, CompositeType paramCompositeType, SurfaceType paramSurfaceType2) {
/*  68 */     super(paramSurfaceType1, paramCompositeType, paramSurfaceType2);
/*  69 */     this.rq = paramRenderQueue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void MaskFill(SunGraphics2D paramSunGraphics2D, SurfaceData paramSurfaceData, Composite paramComposite, final int x, final int y, final int w, final int h, final byte[] mask, final int maskoff, final int maskscan) {
/*  79 */     AlphaComposite alphaComposite = (AlphaComposite)paramComposite;
/*  80 */     if (alphaComposite.getRule() != 3) {
/*  81 */       paramComposite = AlphaComposite.SrcOver;
/*     */     }
/*     */     
/*  84 */     this.rq.lock(); try {
/*     */       byte b;
/*  86 */       validateContext(paramSunGraphics2D, paramComposite, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  91 */       if (mask != null) {
/*     */ 
/*     */         
/*  94 */         b = mask.length + 3 & 0xFFFFFFFC;
/*     */       } else {
/*     */         
/*  97 */         b = 0;
/*     */       } 
/*  99 */       int i = 32 + b;
/*     */       
/* 101 */       RenderBuffer renderBuffer = this.rq.getBuffer();
/* 102 */       if (i <= renderBuffer.capacity()) {
/* 103 */         if (i > renderBuffer.remaining())
/*     */         {
/* 105 */           this.rq.flushNow();
/*     */         }
/*     */         
/* 108 */         renderBuffer.putInt(32);
/*     */         
/* 110 */         renderBuffer.putInt(x).putInt(y).putInt(w).putInt(h);
/* 111 */         renderBuffer.putInt(maskoff);
/* 112 */         renderBuffer.putInt(maskscan);
/* 113 */         renderBuffer.putInt(b);
/* 114 */         if (mask != null)
/*     */         {
/* 116 */           int j = b - mask.length;
/* 117 */           renderBuffer.put(mask);
/* 118 */           if (j != 0) {
/* 119 */             renderBuffer.position((renderBuffer.position() + j));
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 125 */         this.rq.flushAndInvokeNow(new Runnable() {
/*     */               public void run() {
/* 127 */                 BufferedMaskFill.this.maskFill(x, y, w, h, maskoff, maskscan, mask.length, mask);
/*     */               }
/*     */             });
/*     */       } 
/*     */     } finally {
/*     */       
/* 133 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void maskFill(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, byte[] paramArrayOfbyte);
/*     */   
/*     */   protected abstract void validateContext(SunGraphics2D paramSunGraphics2D, Composite paramComposite, int paramInt);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/BufferedMaskFill.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */