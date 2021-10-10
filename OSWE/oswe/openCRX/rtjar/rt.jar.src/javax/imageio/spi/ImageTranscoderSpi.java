/*    */ package javax.imageio.spi;
/*    */ 
/*    */ import javax.imageio.ImageTranscoder;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ImageTranscoderSpi
/*    */   extends IIOServiceProvider
/*    */ {
/*    */   protected ImageTranscoderSpi() {}
/*    */   
/*    */   public ImageTranscoderSpi(String paramString1, String paramString2) {
/* 59 */     super(paramString1, paramString2);
/*    */   }
/*    */   
/*    */   public abstract String getReaderServiceProviderName();
/*    */   
/*    */   public abstract String getWriterServiceProviderName();
/*    */   
/*    */   public abstract ImageTranscoder createTranscoderInstance();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/spi/ImageTranscoderSpi.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */