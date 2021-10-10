/*    */ package sun.java2d.loops;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Composite;
/*    */ import java.awt.CompositeContext;
/*    */ import java.awt.RenderingHints;
/*    */ import java.awt.image.ColorModel;
/*    */ import sun.java2d.SunCompositeContext;
/*    */ import sun.java2d.SurfaceData;
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
/*    */ public final class XORComposite
/*    */   implements Composite
/*    */ {
/*    */   Color xorColor;
/*    */   int xorPixel;
/*    */   int alphaMask;
/*    */   
/*    */   public XORComposite(Color paramColor, SurfaceData paramSurfaceData) {
/* 47 */     this.xorColor = paramColor;
/*    */     
/* 49 */     SurfaceType surfaceType = paramSurfaceData.getSurfaceType();
/*    */     
/* 51 */     this.xorPixel = paramSurfaceData.pixelFor(paramColor.getRGB());
/* 52 */     this.alphaMask = surfaceType.getAlphaMask();
/*    */   }
/*    */   
/*    */   public Color getXorColor() {
/* 56 */     return this.xorColor;
/*    */   }
/*    */   
/*    */   public int getXorPixel() {
/* 60 */     return this.xorPixel;
/*    */   }
/*    */   
/*    */   public int getAlphaMask() {
/* 64 */     return this.alphaMask;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public CompositeContext createContext(ColorModel paramColorModel1, ColorModel paramColorModel2, RenderingHints paramRenderingHints) {
/* 70 */     return new SunCompositeContext(this, paramColorModel1, paramColorModel2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/loops/XORComposite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */