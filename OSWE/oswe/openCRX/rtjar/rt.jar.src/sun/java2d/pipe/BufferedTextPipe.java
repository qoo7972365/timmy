/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Composite;
/*     */ import sun.font.GlyphList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BufferedTextPipe
/*     */   extends GlyphListPipe
/*     */ {
/*     */   private static final int BYTES_PER_GLYPH_IMAGE = 8;
/*     */   private static final int BYTES_PER_GLYPH_POSITION = 8;
/*     */   private static final int OFFSET_CONTRAST = 8;
/*     */   private static final int OFFSET_RGBORDER = 2;
/*     */   private static final int OFFSET_SUBPIXPOS = 1;
/*     */   private static final int OFFSET_POSITIONS = 0;
/*     */   protected final RenderQueue rq;
/*     */   
/*     */   private static int createPackedParams(SunGraphics2D paramSunGraphics2D, GlyphList paramGlyphList) {
/*  60 */     return (
/*  61 */       paramGlyphList.usePositions() ? 1 : 0) << 0 | (
/*  62 */       paramGlyphList.isSubPixPos() ? 1 : 0) << 1 | (
/*  63 */       paramGlyphList.isRGBOrder() ? 1 : 0) << 2 | (paramSunGraphics2D.lcdTextContrast & 0xFF) << 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BufferedTextPipe(RenderQueue paramRenderQueue) {
/*  70 */     this.rq = paramRenderQueue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawGlyphList(SunGraphics2D paramSunGraphics2D, GlyphList paramGlyphList) {
/*  80 */     Composite composite = paramSunGraphics2D.composite;
/*  81 */     if (composite == AlphaComposite.Src)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  90 */       composite = AlphaComposite.SrcOver;
/*     */     }
/*     */     
/*  93 */     this.rq.lock();
/*     */     try {
/*  95 */       validateContext(paramSunGraphics2D, composite);
/*  96 */       enqueueGlyphList(paramSunGraphics2D, paramGlyphList);
/*     */     } finally {
/*  98 */       this.rq.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void enqueueGlyphList(final SunGraphics2D sg2d, final GlyphList gl) {
/* 106 */     RenderBuffer renderBuffer = this.rq.getBuffer();
/* 107 */     final int totalGlyphs = gl.getNumGlyphs();
/* 108 */     int j = i * 8;
/*     */     
/* 110 */     byte b = gl.usePositions() ? (i * 8) : 0;
/* 111 */     int k = 24 + j + b;
/*     */     
/* 113 */     final long[] images = gl.getImages();
/* 114 */     final float glyphListOrigX = gl.getX() + 0.5F;
/* 115 */     final float glyphListOrigY = gl.getY() + 0.5F;
/*     */ 
/*     */ 
/*     */     
/* 119 */     this.rq.addReference(gl.getStrike());
/*     */     
/* 121 */     if (k <= renderBuffer.capacity()) {
/* 122 */       if (k > renderBuffer.remaining())
/*     */       {
/* 124 */         this.rq.flushNow();
/*     */       }
/* 126 */       this.rq.ensureAlignment(20);
/* 127 */       renderBuffer.putInt(40);
/*     */       
/* 129 */       renderBuffer.putInt(i);
/* 130 */       renderBuffer.putInt(createPackedParams(sg2d, gl));
/* 131 */       renderBuffer.putFloat(f1);
/* 132 */       renderBuffer.putFloat(f2);
/*     */       
/* 134 */       renderBuffer.put(arrayOfLong, 0, i);
/* 135 */       if (gl.usePositions()) {
/* 136 */         float[] arrayOfFloat = gl.getPositions();
/* 137 */         renderBuffer.put(arrayOfFloat, 0, 2 * i);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 142 */       this.rq.flushAndInvokeNow(new Runnable() {
/*     */             public void run() {
/* 144 */               BufferedTextPipe.this.drawGlyphList(totalGlyphs, gl.usePositions(), gl
/* 145 */                   .isSubPixPos(), gl.isRGBOrder(), sg2d.lcdTextContrast, glyphListOrigX, glyphListOrigY, images, gl
/*     */ 
/*     */                   
/* 148 */                   .getPositions());
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void drawGlyphList(int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt2, float paramFloat1, float paramFloat2, long[] paramArrayOflong, float[] paramArrayOffloat);
/*     */   
/*     */   protected abstract void validateContext(SunGraphics2D paramSunGraphics2D, Composite paramComposite);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/BufferedTextPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */