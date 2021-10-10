/*    */ package sun.java2d.loops;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import sun.font.Font2D;
/*    */ import sun.font.FontStrike;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontInfo
/*    */   implements Cloneable
/*    */ {
/*    */   public Font font;
/*    */   public Font2D font2D;
/*    */   public FontStrike fontStrike;
/*    */   public double[] devTx;
/*    */   public double[] glyphTx;
/*    */   public int pixelHeight;
/*    */   public float originX;
/*    */   public float originY;
/*    */   public int aaHint;
/*    */   public boolean lcdRGBOrder;
/*    */   public boolean lcdSubPixPos;
/*    */   
/*    */   public String mtx(double[] paramArrayOfdouble) {
/* 61 */     return "[" + paramArrayOfdouble[0] + ", " + paramArrayOfdouble[1] + ", " + paramArrayOfdouble[2] + ", " + paramArrayOfdouble[3] + "]";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object clone() {
/*    */     try {
/* 71 */       return super.clone();
/* 72 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 73 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String toString() {
/* 78 */     return "FontInfo[font=" + this.font + ", devTx=" + 
/*    */       
/* 80 */       mtx(this.devTx) + ", glyphTx=" + 
/* 81 */       mtx(this.glyphTx) + ", pixelHeight=" + this.pixelHeight + ", origin=(" + this.originX + "," + this.originY + "), aaHint=" + this.aaHint + ", lcdRGBOrder=" + (this.lcdRGBOrder ? "RGB" : "BGR") + "lcdSubPixPos=" + this.lcdSubPixPos + "]";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/FontInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */