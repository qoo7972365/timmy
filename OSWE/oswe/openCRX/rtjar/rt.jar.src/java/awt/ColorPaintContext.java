/*    */ package java.awt;
/*    */ 
/*    */ import java.awt.image.ColorModel;
/*    */ import java.awt.image.Raster;
/*    */ import java.awt.image.WritableRaster;
/*    */ import java.util.Arrays;
/*    */ import sun.awt.image.IntegerComponentRaster;
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
/*    */ class ColorPaintContext
/*    */   implements PaintContext
/*    */ {
/*    */   int color;
/*    */   WritableRaster savedTile;
/*    */   
/*    */   protected ColorPaintContext(int paramInt, ColorModel paramColorModel) {
/* 41 */     this.color = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void dispose() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   int getRGB() {
/* 60 */     return this.color;
/*    */   }
/*    */   
/*    */   public ColorModel getColorModel() {
/* 64 */     return ColorModel.getRGBdefault();
/*    */   }
/*    */   
/*    */   public synchronized Raster getRaster(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 68 */     WritableRaster writableRaster = this.savedTile;
/*    */     
/* 70 */     if (writableRaster == null || paramInt3 > writableRaster.getWidth() || paramInt4 > writableRaster.getHeight()) {
/* 71 */       writableRaster = getColorModel().createCompatibleWritableRaster(paramInt3, paramInt4);
/* 72 */       IntegerComponentRaster integerComponentRaster = (IntegerComponentRaster)writableRaster;
/* 73 */       Arrays.fill(integerComponentRaster.getDataStorage(), this.color);
/*    */       
/* 75 */       integerComponentRaster.markDirty();
/* 76 */       if (paramInt3 <= 64 && paramInt4 <= 64) {
/* 77 */         this.savedTile = writableRaster;
/*    */       }
/*    */     } 
/*    */     
/* 81 */     return writableRaster;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/ColorPaintContext.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */