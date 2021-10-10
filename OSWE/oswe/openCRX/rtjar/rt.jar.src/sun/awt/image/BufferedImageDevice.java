/*    */ package sun.awt.image;
/*    */ 
/*    */ import java.awt.GraphicsConfiguration;
/*    */ import java.awt.GraphicsDevice;
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
/*    */ public class BufferedImageDevice
/*    */   extends GraphicsDevice
/*    */ {
/*    */   GraphicsConfiguration gc;
/*    */   
/*    */   public BufferedImageDevice(BufferedImageGraphicsConfig paramBufferedImageGraphicsConfig) {
/* 36 */     this.gc = paramBufferedImageGraphicsConfig;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getType() {
/* 48 */     return 2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getIDstring() {
/* 58 */     return "BufferedImage";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GraphicsConfiguration[] getConfigurations() {
/* 69 */     return new GraphicsConfiguration[] { this.gc };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GraphicsConfiguration getDefaultConfiguration() {
/* 79 */     return this.gc;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/image/BufferedImageDevice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */