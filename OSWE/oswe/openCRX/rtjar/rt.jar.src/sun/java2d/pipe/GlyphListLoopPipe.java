/*    */ package sun.java2d.pipe;
/*    */ 
/*    */ import sun.font.GlyphList;
/*    */ import sun.java2d.SunGraphics2D;
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
/*    */ public abstract class GlyphListLoopPipe
/*    */   extends GlyphListPipe
/*    */   implements LoopBasedPipe
/*    */ {
/*    */   protected void drawGlyphList(SunGraphics2D paramSunGraphics2D, GlyphList paramGlyphList, int paramInt) {
/* 44 */     switch (paramInt) {
/*    */       case 1:
/* 46 */         paramSunGraphics2D.loops.drawGlyphListLoop
/* 47 */           .DrawGlyphList(paramSunGraphics2D, paramSunGraphics2D.surfaceData, paramGlyphList);
/*    */         return;
/*    */       case 2:
/* 50 */         paramSunGraphics2D.loops.drawGlyphListAALoop
/* 51 */           .DrawGlyphListAA(paramSunGraphics2D, paramSunGraphics2D.surfaceData, paramGlyphList);
/*    */         return;
/*    */       case 4:
/*    */       case 6:
/* 55 */         paramSunGraphics2D.loops.drawGlyphListLCDLoop
/* 56 */           .DrawGlyphListLCD(paramSunGraphics2D, paramSunGraphics2D.surfaceData, paramGlyphList);
/*    */         return;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/GlyphListLoopPipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */