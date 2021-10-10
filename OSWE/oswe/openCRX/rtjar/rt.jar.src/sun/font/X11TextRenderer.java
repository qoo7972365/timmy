/*    */ package sun.font;
/*    */ 
/*    */ import java.awt.font.FontRenderContext;
/*    */ import java.awt.font.GlyphVector;
/*    */ import sun.awt.SunToolkit;
/*    */ import sun.java2d.SunGraphics2D;
/*    */ import sun.java2d.SurfaceData;
/*    */ import sun.java2d.loops.FontInfo;
/*    */ import sun.java2d.loops.GraphicsPrimitive;
/*    */ import sun.java2d.pipe.GlyphListPipe;
/*    */ import sun.java2d.pipe.Region;
/*    */ import sun.java2d.x11.X11SurfaceData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class X11TextRenderer
/*    */   extends GlyphListPipe
/*    */ {
/*    */   public void drawGlyphVector(SunGraphics2D paramSunGraphics2D, GlyphVector paramGlyphVector, float paramFloat1, float paramFloat2) {
/* 53 */     FontRenderContext fontRenderContext = paramGlyphVector.getFontRenderContext();
/* 54 */     FontInfo fontInfo = paramSunGraphics2D.getGVFontInfo(paramGlyphVector.getFont(), fontRenderContext);
/* 55 */     switch (fontInfo.aaHint) {
/*    */       case 1:
/* 57 */         super.drawGlyphVector(paramSunGraphics2D, paramGlyphVector, paramFloat1, paramFloat2);
/*    */         return;
/*    */       case 2:
/* 60 */         SurfaceData.aaTextRenderer.drawGlyphVector(paramSunGraphics2D, paramGlyphVector, paramFloat1, paramFloat2);
/*    */         return;
/*    */       case 4:
/*    */       case 6:
/* 64 */         SurfaceData.lcdTextRenderer.drawGlyphVector(paramSunGraphics2D, paramGlyphVector, paramFloat1, paramFloat2);
/*    */         return;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   native void doDrawGlyphList(long paramLong1, long paramLong2, Region paramRegion, GlyphList paramGlyphList);
/*    */ 
/*    */   
/*    */   protected void drawGlyphList(SunGraphics2D paramSunGraphics2D, GlyphList paramGlyphList) {
/* 74 */     SunToolkit.awtLock();
/*    */     try {
/* 76 */       X11SurfaceData x11SurfaceData = (X11SurfaceData)paramSunGraphics2D.surfaceData;
/* 77 */       Region region = paramSunGraphics2D.getCompClip();
/* 78 */       long l = x11SurfaceData.getRenderGC(region, 0, null, paramSunGraphics2D.pixel);
/*    */       
/* 80 */       doDrawGlyphList(x11SurfaceData.getNativeOps(), l, region, paramGlyphList);
/*    */     } finally {
/* 82 */       SunToolkit.awtUnlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   public X11TextRenderer traceWrap() {
/* 87 */     return new Tracer();
/*    */   }
/*    */   
/*    */   public static class Tracer
/*    */     extends X11TextRenderer
/*    */   {
/*    */     void doDrawGlyphList(long param1Long1, long param1Long2, Region param1Region, GlyphList param1GlyphList) {
/* 94 */       GraphicsPrimitive.tracePrimitive("X11DrawGlyphs");
/* 95 */       super.doDrawGlyphList(param1Long1, param1Long2, param1Region, param1GlyphList);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/X11TextRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */