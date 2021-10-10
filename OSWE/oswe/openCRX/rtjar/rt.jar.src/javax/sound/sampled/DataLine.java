/*     */ package javax.sound.sampled;
/*     */ 
/*     */ import java.util.Arrays;
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
/*     */ public interface DataLine
/*     */   extends Line
/*     */ {
/*     */   void drain();
/*     */   
/*     */   void flush();
/*     */   
/*     */   void start();
/*     */   
/*     */   void stop();
/*     */   
/*     */   boolean isRunning();
/*     */   
/*     */   boolean isActive();
/*     */   
/*     */   AudioFormat getFormat();
/*     */   
/*     */   int getBufferSize();
/*     */   
/*     */   int available();
/*     */   
/*     */   int getFramePosition();
/*     */   
/*     */   long getLongFramePosition();
/*     */   
/*     */   long getMicrosecondPosition();
/*     */   
/*     */   float getLevel();
/*     */   
/*     */   public static class Info
/*     */     extends Line.Info
/*     */   {
/*     */     private final AudioFormat[] formats;
/*     */     private final int minBufferSize;
/*     */     private final int maxBufferSize;
/*     */     
/*     */     public Info(Class<?> param1Class, AudioFormat[] param1ArrayOfAudioFormat, int param1Int1, int param1Int2) {
/* 304 */       super(param1Class);
/*     */       
/* 306 */       if (param1ArrayOfAudioFormat == null) {
/* 307 */         this.formats = new AudioFormat[0];
/*     */       } else {
/* 309 */         this.formats = Arrays.<AudioFormat>copyOf(param1ArrayOfAudioFormat, param1ArrayOfAudioFormat.length);
/*     */       } 
/*     */       
/* 312 */       this.minBufferSize = param1Int1;
/* 313 */       this.maxBufferSize = param1Int2;
/*     */     }
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
/*     */     public Info(Class<?> param1Class, AudioFormat param1AudioFormat, int param1Int) {
/* 329 */       super(param1Class);
/*     */       
/* 331 */       if (param1AudioFormat == null) {
/* 332 */         this.formats = new AudioFormat[0];
/*     */       } else {
/* 334 */         this.formats = new AudioFormat[] { param1AudioFormat };
/*     */       } 
/*     */       
/* 337 */       this.minBufferSize = param1Int;
/* 338 */       this.maxBufferSize = param1Int;
/*     */     }
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
/*     */     public Info(Class<?> param1Class, AudioFormat param1AudioFormat) {
/* 352 */       this(param1Class, param1AudioFormat, -1);
/*     */     }
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
/*     */     public AudioFormat[] getFormats() {
/* 377 */       return Arrays.<AudioFormat>copyOf(this.formats, this.formats.length);
/*     */     }
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
/*     */     public boolean isFormatSupported(AudioFormat param1AudioFormat) {
/* 392 */       for (byte b = 0; b < this.formats.length; b++) {
/* 393 */         if (param1AudioFormat.matches(this.formats[b])) {
/* 394 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 398 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMinBufferSize() {
/* 406 */       return this.minBufferSize;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getMaxBufferSize() {
/* 415 */       return this.maxBufferSize;
/*     */     }
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
/*     */     public boolean matches(Line.Info param1Info) {
/* 431 */       if (!super.matches(param1Info)) {
/* 432 */         return false;
/*     */       }
/*     */       
/* 435 */       Info info = (Info)param1Info;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 440 */       if (getMaxBufferSize() >= 0 && info.getMaxBufferSize() >= 0 && 
/* 441 */         getMaxBufferSize() > info.getMaxBufferSize()) {
/* 442 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 446 */       if (getMinBufferSize() >= 0 && info.getMinBufferSize() >= 0 && 
/* 447 */         getMinBufferSize() < info.getMinBufferSize()) {
/* 448 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 452 */       AudioFormat[] arrayOfAudioFormat = getFormats();
/*     */       
/* 454 */       if (arrayOfAudioFormat != null)
/*     */       {
/* 456 */         for (byte b = 0; b < arrayOfAudioFormat.length; b++) {
/* 457 */           if (arrayOfAudioFormat[b] != null && 
/* 458 */             !info.isFormatSupported(arrayOfAudioFormat[b])) {
/* 459 */             return false;
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 465 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 474 */       StringBuffer stringBuffer = new StringBuffer();
/*     */       
/* 476 */       if (this.formats.length == 1 && this.formats[0] != null) {
/* 477 */         stringBuffer.append(" supporting format " + this.formats[0]);
/* 478 */       } else if ((getFormats()).length > 1) {
/* 479 */         stringBuffer.append(" supporting " + (getFormats()).length + " audio formats");
/*     */       } 
/*     */       
/* 482 */       if (this.minBufferSize != -1 && this.maxBufferSize != -1) {
/* 483 */         stringBuffer.append(", and buffers of " + this.minBufferSize + " to " + this.maxBufferSize + " bytes");
/* 484 */       } else if (this.minBufferSize != -1 && this.minBufferSize > 0) {
/* 485 */         stringBuffer.append(", and buffers of at least " + this.minBufferSize + " bytes");
/* 486 */       } else if (this.maxBufferSize != -1) {
/* 487 */         stringBuffer.append(", and buffers of up to " + this.minBufferSize + " bytes");
/*     */       } 
/*     */       
/* 490 */       return new String(super.toString() + stringBuffer);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/sampled/DataLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */