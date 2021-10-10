/*    */ package sun.awt.image;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
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
/*    */ public class BufImgSurfaceManager
/*    */   extends SurfaceManager
/*    */ {
/*    */   protected BufferedImage bImg;
/*    */   protected SurfaceData sdDefault;
/*    */   
/*    */   public BufImgSurfaceManager(BufferedImage paramBufferedImage) {
/* 54 */     this.bImg = paramBufferedImage;
/* 55 */     this.sdDefault = BufImgSurfaceData.createData(paramBufferedImage);
/*    */   }
/*    */   
/*    */   public SurfaceData getPrimarySurfaceData() {
/* 59 */     return this.sdDefault;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SurfaceData restoreContents() {
/* 67 */     return this.sdDefault;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/BufImgSurfaceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */