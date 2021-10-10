/*    */ package sun.audio;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ import javax.sound.sampled.AudioFormat;
/*    */ import javax.sound.sampled.AudioInputStream;
/*    */ import javax.sound.sampled.AudioSystem;
/*    */ import javax.sound.sampled.UnsupportedAudioFileException;
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
/*    */ public final class AudioData
/*    */ {
/* 55 */   private static final AudioFormat DEFAULT_FORMAT = new AudioFormat(AudioFormat.Encoding.ULAW, 8000.0F, 8, 1, 1, 8000.0F, true);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   AudioFormat format;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   byte[] buffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AudioData(byte[] paramArrayOfbyte) {
/* 74 */     this(DEFAULT_FORMAT, paramArrayOfbyte);
/*    */ 
/*    */     
/*    */     try {
/* 78 */       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(paramArrayOfbyte));
/* 79 */       this.format = audioInputStream.getFormat();
/* 80 */       audioInputStream.close();
/*    */     }
/* 82 */     catch (IOException iOException) {
/*    */     
/* 84 */     } catch (UnsupportedAudioFileException unsupportedAudioFileException) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   AudioData(AudioFormat paramAudioFormat, byte[] paramArrayOfbyte) {
/* 95 */     this.format = paramAudioFormat;
/* 96 */     if (paramArrayOfbyte != null)
/* 97 */       this.buffer = Arrays.copyOf(paramArrayOfbyte, paramArrayOfbyte.length); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/audio/AudioData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */