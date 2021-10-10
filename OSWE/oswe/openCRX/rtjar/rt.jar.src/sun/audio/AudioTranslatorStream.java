/*    */ package sun.audio;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public final class AudioTranslatorStream
/*    */   extends NativeAudioStream
/*    */ {
/* 37 */   private final int length = 0;
/*    */   
/*    */   public AudioTranslatorStream(InputStream paramInputStream) throws IOException {
/* 40 */     super(paramInputStream);
/*    */     
/* 42 */     throw new InvalidAudioFormatException();
/*    */   }
/*    */   
/*    */   public int getLength() {
/* 46 */     return 0;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/audio/AudioTranslatorStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */