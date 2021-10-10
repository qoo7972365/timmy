/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.GlyphVector;
/*     */ import java.awt.font.TextLayout;
/*     */ import sun.font.GlyphList;
/*     */ import sun.java2d.SunGraphics2D;
/*     */ import sun.java2d.SurfaceData;
/*     */ import sun.java2d.loops.FontInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GlyphListPipe
/*     */   implements TextPipe
/*     */ {
/*     */   public void drawString(SunGraphics2D paramSunGraphics2D, String paramString, double paramDouble1, double paramDouble2) {
/*     */     float f1, f2;
/*  50 */     FontInfo fontInfo = paramSunGraphics2D.getFontInfo();
/*  51 */     if (fontInfo.pixelHeight > 100) {
/*  52 */       SurfaceData.outlineTextRenderer.drawString(paramSunGraphics2D, paramString, paramDouble1, paramDouble2);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  57 */     if (paramSunGraphics2D.transformState >= 3) {
/*  58 */       double[] arrayOfDouble = { paramDouble1 + fontInfo.originX, paramDouble2 + fontInfo.originY };
/*  59 */       paramSunGraphics2D.transform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 1);
/*  60 */       f1 = (float)arrayOfDouble[0];
/*  61 */       f2 = (float)arrayOfDouble[1];
/*     */     } else {
/*  63 */       f1 = (float)(paramDouble1 + fontInfo.originX + paramSunGraphics2D.transX);
/*  64 */       f2 = (float)(paramDouble2 + fontInfo.originY + paramSunGraphics2D.transY);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     GlyphList glyphList = GlyphList.getInstance();
/*  71 */     if (glyphList.setFromString(fontInfo, paramString, f1, f2)) {
/*  72 */       drawGlyphList(paramSunGraphics2D, glyphList);
/*  73 */       glyphList.dispose();
/*     */     } else {
/*  75 */       glyphList.dispose();
/*     */       
/*  77 */       TextLayout textLayout = new TextLayout(paramString, paramSunGraphics2D.getFont(), paramSunGraphics2D.getFontRenderContext());
/*  78 */       textLayout.draw(paramSunGraphics2D, (float)paramDouble1, (float)paramDouble2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawChars(SunGraphics2D paramSunGraphics2D, char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*     */     float f1, f2;
/*  86 */     FontInfo fontInfo = paramSunGraphics2D.getFontInfo();
/*     */     
/*  88 */     if (fontInfo.pixelHeight > 100) {
/*  89 */       SurfaceData.outlineTextRenderer.drawChars(paramSunGraphics2D, paramArrayOfchar, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     if (paramSunGraphics2D.transformState >= 3) {
/*  94 */       double[] arrayOfDouble = { (paramInt3 + fontInfo.originX), (paramInt4 + fontInfo.originY) };
/*  95 */       paramSunGraphics2D.transform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 1);
/*  96 */       f1 = (float)arrayOfDouble[0];
/*  97 */       f2 = (float)arrayOfDouble[1];
/*     */     } else {
/*  99 */       f1 = paramInt3 + fontInfo.originX + paramSunGraphics2D.transX;
/* 100 */       f2 = paramInt4 + fontInfo.originY + paramSunGraphics2D.transY;
/*     */     } 
/* 102 */     GlyphList glyphList = GlyphList.getInstance();
/* 103 */     if (glyphList.setFromChars(fontInfo, paramArrayOfchar, paramInt1, paramInt2, f1, f2)) {
/* 104 */       drawGlyphList(paramSunGraphics2D, glyphList);
/* 105 */       glyphList.dispose();
/*     */     } else {
/* 107 */       glyphList.dispose();
/*     */ 
/*     */       
/* 110 */       TextLayout textLayout = new TextLayout(new String(paramArrayOfchar, paramInt1, paramInt2), paramSunGraphics2D.getFont(), paramSunGraphics2D.getFontRenderContext());
/* 111 */       textLayout.draw(paramSunGraphics2D, paramInt3, paramInt4);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGlyphVector(SunGraphics2D paramSunGraphics2D, GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/* 119 */     FontRenderContext fontRenderContext = paramGlyphVector.getFontRenderContext();
/* 120 */     FontInfo fontInfo = paramSunGraphics2D.getGVFontInfo(paramGlyphVector.getFont(), fontRenderContext);
/* 121 */     if (fontInfo.pixelHeight > 100) {
/* 122 */       SurfaceData.outlineTextRenderer.drawGlyphVector(paramSunGraphics2D, paramGlyphVector, paramFloat1, paramFloat2);
/*     */       return;
/*     */     } 
/* 125 */     if (paramSunGraphics2D.transformState >= 3) {
/* 126 */       double[] arrayOfDouble = { paramFloat1, paramFloat2 };
/* 127 */       paramSunGraphics2D.transform.transform(arrayOfDouble, 0, arrayOfDouble, 0, 1);
/* 128 */       paramFloat1 = (float)arrayOfDouble[0];
/* 129 */       paramFloat2 = (float)arrayOfDouble[1];
/*     */     } else {
/* 131 */       paramFloat1 += paramSunGraphics2D.transX;
/* 132 */       paramFloat2 += paramSunGraphics2D.transY;
/*     */     } 
/*     */     
/* 135 */     GlyphList glyphList = GlyphList.getInstance();
/* 136 */     glyphList.setFromGlyphVector(fontInfo, paramGlyphVector, paramFloat1, paramFloat2);
/* 137 */     drawGlyphList(paramSunGraphics2D, glyphList, fontInfo.aaHint);
/* 138 */     glyphList.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void drawGlyphList(SunGraphics2D paramSunGraphics2D, GlyphList paramGlyphList);
/*     */   
/*     */   protected void drawGlyphList(SunGraphics2D paramSunGraphics2D, GlyphList paramGlyphList, int paramInt) {
/* 145 */     drawGlyphList(paramSunGraphics2D, paramGlyphList);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/GlyphListPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */