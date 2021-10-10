/*     */ package javax.sound.sampled.spi;
/*     */ 
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import javax.sound.sampled.AudioInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class FormatConversionProvider
/*     */ {
/*     */   public abstract AudioFormat.Encoding[] getSourceEncodings();
/*     */   
/*     */   public abstract AudioFormat.Encoding[] getTargetEncodings();
/*     */   
/*     */   public boolean isSourceEncodingSupported(AudioFormat.Encoding paramEncoding) {
/*  84 */     AudioFormat.Encoding[] arrayOfEncoding = getSourceEncodings();
/*     */     
/*  86 */     for (byte b = 0; b < arrayOfEncoding.length; b++) {
/*  87 */       if (paramEncoding.equals(arrayOfEncoding[b])) {
/*  88 */         return true;
/*     */       }
/*     */     } 
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTargetEncodingSupported(AudioFormat.Encoding paramEncoding) {
/* 103 */     AudioFormat.Encoding[] arrayOfEncoding = getTargetEncodings();
/*     */     
/* 105 */     for (byte b = 0; b < arrayOfEncoding.length; b++) {
/* 106 */       if (paramEncoding.equals(arrayOfEncoding[b])) {
/* 107 */         return true;
/*     */       }
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract AudioFormat.Encoding[] getTargetEncodings(AudioFormat paramAudioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConversionSupported(AudioFormat.Encoding paramEncoding, AudioFormat paramAudioFormat) {
/* 134 */     AudioFormat.Encoding[] arrayOfEncoding = getTargetEncodings(paramAudioFormat);
/*     */     
/* 136 */     for (byte b = 0; b < arrayOfEncoding.length; b++) {
/* 137 */       if (paramEncoding.equals(arrayOfEncoding[b])) {
/* 138 */         return true;
/*     */       }
/*     */     } 
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract AudioFormat[] getTargetFormats(AudioFormat.Encoding paramEncoding, AudioFormat paramAudioFormat);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConversionSupported(AudioFormat paramAudioFormat1, AudioFormat paramAudioFormat2) {
/* 166 */     AudioFormat[] arrayOfAudioFormat = getTargetFormats(paramAudioFormat1.getEncoding(), paramAudioFormat2);
/*     */     
/* 168 */     for (byte b = 0; b < arrayOfAudioFormat.length; b++) {
/* 169 */       if (paramAudioFormat1.matches(arrayOfAudioFormat[b])) {
/* 170 */         return true;
/*     */       }
/*     */     } 
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   public abstract AudioInputStream getAudioInputStream(AudioFormat.Encoding paramEncoding, AudioInputStream paramAudioInputStream);
/*     */   
/*     */   public abstract AudioInputStream getAudioInputStream(AudioFormat paramAudioFormat, AudioInputStream paramAudioInputStream);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/sampled/spi/FormatConversionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */