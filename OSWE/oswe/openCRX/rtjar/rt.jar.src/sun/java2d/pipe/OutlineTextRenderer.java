/*     */ package sun.java2d.pipe;
/*     */ 
/*     */ import java.awt.Shape;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.GlyphVector;
/*     */ import java.awt.font.TextLayout;
/*     */ import java.awt.geom.AffineTransform;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OutlineTextRenderer
/*     */   implements TextPipe
/*     */ {
/*     */   public static final int THRESHHOLD = 100;
/*     */   
/*     */   public void drawChars(SunGraphics2D paramSunGraphics2D, char[] paramArrayOfchar, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  71 */     String str = new String(paramArrayOfchar, paramInt1, paramInt2);
/*  72 */     drawString(paramSunGraphics2D, str, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawString(SunGraphics2D paramSunGraphics2D, String paramString, double paramDouble1, double paramDouble2) {
/*  77 */     if ("".equals(paramString)) {
/*     */       return;
/*     */     }
/*     */     
/*  81 */     TextLayout textLayout = new TextLayout(paramString, paramSunGraphics2D.getFont(), paramSunGraphics2D.getFontRenderContext());
/*  82 */     Shape shape = textLayout.getOutline(AffineTransform.getTranslateInstance(paramDouble1, paramDouble2));
/*     */     
/*  84 */     int i = (paramSunGraphics2D.getFontInfo()).aaHint;
/*     */     
/*  86 */     int j = -1;
/*  87 */     if (i != 1 && paramSunGraphics2D.antialiasHint != 2) {
/*     */       
/*  89 */       j = paramSunGraphics2D.antialiasHint;
/*  90 */       paramSunGraphics2D.antialiasHint = 2;
/*  91 */       paramSunGraphics2D.validatePipe();
/*  92 */     } else if (i == 1 && paramSunGraphics2D.antialiasHint != 1) {
/*     */       
/*  94 */       j = paramSunGraphics2D.antialiasHint;
/*  95 */       paramSunGraphics2D.antialiasHint = 1;
/*  96 */       paramSunGraphics2D.validatePipe();
/*     */     } 
/*     */     
/*  99 */     paramSunGraphics2D.fill(shape);
/*     */     
/* 101 */     if (j != -1) {
/* 102 */       paramSunGraphics2D.antialiasHint = j;
/* 103 */       paramSunGraphics2D.validatePipe();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawGlyphVector(SunGraphics2D paramSunGraphics2D, GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/* 111 */     Shape shape = paramGlyphVector.getOutline(paramFloat1, paramFloat2);
/* 112 */     int i = -1;
/* 113 */     FontRenderContext fontRenderContext = paramGlyphVector.getFontRenderContext();
/* 114 */     boolean bool = fontRenderContext.isAntiAliased();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     if (bool && 
/* 122 */       (paramSunGraphics2D.getGVFontInfo(paramGlyphVector.getFont(), fontRenderContext)).aaHint == 1)
/*     */     {
/* 124 */       bool = false;
/*     */     }
/*     */ 
/*     */     
/* 128 */     if (bool && paramSunGraphics2D.antialiasHint != 2) {
/* 129 */       i = paramSunGraphics2D.antialiasHint;
/* 130 */       paramSunGraphics2D.antialiasHint = 2;
/* 131 */       paramSunGraphics2D.validatePipe();
/* 132 */     } else if (!bool && paramSunGraphics2D.antialiasHint != 1) {
/* 133 */       i = paramSunGraphics2D.antialiasHint;
/* 134 */       paramSunGraphics2D.antialiasHint = 1;
/* 135 */       paramSunGraphics2D.validatePipe();
/*     */     } 
/*     */     
/* 138 */     paramSunGraphics2D.fill(shape);
/*     */     
/* 140 */     if (i != -1) {
/* 141 */       paramSunGraphics2D.antialiasHint = i;
/* 142 */       paramSunGraphics2D.validatePipe();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/OutlineTextRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */