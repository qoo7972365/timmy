/*    */ package sun.java2d.pipe;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.Shape;
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
/*    */ public class TextRenderer
/*    */   extends GlyphListPipe
/*    */ {
/*    */   CompositePipe outpipe;
/*    */   
/*    */   public TextRenderer(CompositePipe paramCompositePipe) {
/* 45 */     this.outpipe = paramCompositePipe;
/*    */   }
/*    */   
/*    */   protected void drawGlyphList(SunGraphics2D paramSunGraphics2D, GlyphList paramGlyphList) {
/* 49 */     int i = paramGlyphList.getNumGlyphs();
/* 50 */     Region region = paramSunGraphics2D.getCompClip();
/* 51 */     int j = region.getLoX();
/* 52 */     int k = region.getLoY();
/* 53 */     int m = region.getHiX();
/* 54 */     int n = region.getHiY();
/* 55 */     Object object = null;
/*    */     try {
/* 57 */       int[] arrayOfInt = paramGlyphList.getBounds();
/* 58 */       Rectangle rectangle = new Rectangle(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2] - arrayOfInt[0], arrayOfInt[3] - arrayOfInt[1]);
/*    */ 
/*    */       
/* 61 */       Shape shape = paramSunGraphics2D.untransformShape(rectangle);
/* 62 */       object = this.outpipe.startSequence(paramSunGraphics2D, shape, rectangle, arrayOfInt);
/* 63 */       for (byte b = 0; b < i; b++) {
/* 64 */         paramGlyphList.setGlyphIndex(b);
/* 65 */         int[] arrayOfInt1 = paramGlyphList.getMetrics();
/* 66 */         int i1 = arrayOfInt1[0];
/* 67 */         int i2 = arrayOfInt1[1];
/* 68 */         int i3 = arrayOfInt1[2];
/* 69 */         int i4 = i1 + i3;
/* 70 */         int i5 = i2 + arrayOfInt1[3];
/* 71 */         int i6 = 0;
/* 72 */         if (i1 < j) {
/* 73 */           i6 = j - i1;
/* 74 */           i1 = j;
/*    */         } 
/* 76 */         if (i2 < k) {
/* 77 */           i6 += (k - i2) * i3;
/* 78 */           i2 = k;
/*    */         } 
/* 80 */         if (i4 > m) i4 = m; 
/* 81 */         if (i5 > n) i5 = n; 
/* 82 */         if (i4 > i1 && i5 > i2 && this.outpipe
/* 83 */           .needTile(object, i1, i2, i4 - i1, i5 - i2)) {
/*    */           
/* 85 */           byte[] arrayOfByte = paramGlyphList.getGrayBits();
/* 86 */           this.outpipe.renderPathTile(object, arrayOfByte, i6, i3, i1, i2, i4 - i1, i5 - i2);
/*    */         } else {
/*    */           
/* 89 */           this.outpipe.skipTile(object, i1, i2);
/*    */         } 
/*    */       } 
/*    */     } finally {
/* 93 */       if (object != null)
/* 94 */         this.outpipe.endSequence(object); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/TextRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */