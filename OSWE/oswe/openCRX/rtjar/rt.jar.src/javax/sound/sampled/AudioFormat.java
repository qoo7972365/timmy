/*     */ package javax.sound.sampled;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AudioFormat
/*     */ {
/*     */   protected Encoding encoding;
/*     */   protected float sampleRate;
/*     */   protected int sampleSizeInBits;
/*     */   protected int channels;
/*     */   protected int frameSize;
/*     */   protected float frameRate;
/*     */   protected boolean bigEndian;
/*     */   private HashMap<String, Object> properties;
/*     */   
/*     */   public AudioFormat(Encoding paramEncoding, float paramFloat1, int paramInt1, int paramInt2, int paramInt3, float paramFloat2, boolean paramBoolean) {
/* 188 */     this.encoding = paramEncoding;
/* 189 */     this.sampleRate = paramFloat1;
/* 190 */     this.sampleSizeInBits = paramInt1;
/* 191 */     this.channels = paramInt2;
/* 192 */     this.frameSize = paramInt3;
/* 193 */     this.frameRate = paramFloat2;
/* 194 */     this.bigEndian = paramBoolean;
/* 195 */     this.properties = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat(Encoding paramEncoding, float paramFloat1, int paramInt1, int paramInt2, int paramInt3, float paramFloat2, boolean paramBoolean, Map<String, Object> paramMap) {
/* 223 */     this(paramEncoding, paramFloat1, paramInt1, paramInt2, paramInt3, paramFloat2, paramBoolean);
/*     */     
/* 225 */     this.properties = new HashMap<>(paramMap);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat(float paramFloat, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {
/* 246 */     this((paramBoolean1 == true) ? Encoding.PCM_SIGNED : Encoding.PCM_UNSIGNED, paramFloat, paramInt1, paramInt2, (paramInt2 == -1 || paramInt1 == -1) ? -1 : ((paramInt1 + 7) / 8 * paramInt2), paramFloat, paramBoolean2);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Encoding getEncoding() {
/* 268 */     return this.encoding;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSampleRate() {
/* 290 */     return this.sampleRate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSampleSizeInBits() {
/* 312 */     return this.sampleSizeInBits;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChannels() {
/* 330 */     return this.channels;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrameSize() {
/* 350 */     return this.frameSize;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFrameRate() {
/* 370 */     return this.frameRate;
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
/*     */   public boolean isBigEndian() {
/* 383 */     return this.bigEndian;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> properties() {
/*     */     Map<? extends String, ?> map;
/* 401 */     if (this.properties == null) {
/* 402 */       map = (Map)new HashMap<>(0);
/*     */     } else {
/* 404 */       map = (Map)this.properties.clone();
/*     */     } 
/* 406 */     return Collections.unmodifiableMap(map);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String paramString) {
/* 427 */     if (this.properties == null) {
/* 428 */       return null;
/*     */     }
/* 430 */     return this.properties.get(paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(AudioFormat paramAudioFormat) {
/* 450 */     if (paramAudioFormat.getEncoding().equals(getEncoding()) && (paramAudioFormat
/* 451 */       .getChannels() == -1 || paramAudioFormat
/* 452 */       .getChannels() == getChannels()) && (paramAudioFormat
/* 453 */       .getSampleRate() == -1.0F || paramAudioFormat
/* 454 */       .getSampleRate() == getSampleRate()) && (paramAudioFormat
/* 455 */       .getSampleSizeInBits() == -1 || paramAudioFormat
/* 456 */       .getSampleSizeInBits() == getSampleSizeInBits()) && (paramAudioFormat
/* 457 */       .getFrameRate() == -1.0F || paramAudioFormat
/* 458 */       .getFrameRate() == getFrameRate()) && (paramAudioFormat
/* 459 */       .getFrameSize() == -1 || paramAudioFormat
/* 460 */       .getFrameSize() == getFrameSize()) && (
/* 461 */       getSampleSizeInBits() <= 8 || paramAudioFormat
/* 462 */       .isBigEndian() == isBigEndian())) {
/* 463 */       return true;
/*     */     }
/* 465 */     return false;
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
/*     */   public String toString() {
/* 477 */     String str2, str3, str4, str5, str1 = "";
/* 478 */     if (getEncoding() != null) {
/* 479 */       str1 = getEncoding().toString() + " ";
/*     */     }
/*     */ 
/*     */     
/* 483 */     if (getSampleRate() == -1.0F) {
/* 484 */       str2 = "unknown sample rate, ";
/*     */     } else {
/* 486 */       str2 = "" + getSampleRate() + " Hz, ";
/*     */     } 
/*     */ 
/*     */     
/* 490 */     if (getSampleSizeInBits() == -1.0F) {
/* 491 */       str3 = "unknown bits per sample, ";
/*     */     } else {
/* 493 */       str3 = "" + getSampleSizeInBits() + " bit, ";
/*     */     } 
/*     */ 
/*     */     
/* 497 */     if (getChannels() == 1) {
/* 498 */       str4 = "mono, ";
/*     */     }
/* 500 */     else if (getChannels() == 2) {
/* 501 */       str4 = "stereo, ";
/*     */     }
/* 503 */     else if (getChannels() == -1) {
/* 504 */       str4 = " unknown number of channels, ";
/*     */     } else {
/* 506 */       str4 = "" + getChannels() + " channels, ";
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 511 */     if (getFrameSize() == -1.0F) {
/* 512 */       str5 = "unknown frame size, ";
/*     */     } else {
/* 514 */       str5 = "" + getFrameSize() + " bytes/frame, ";
/*     */     } 
/*     */     
/* 517 */     String str6 = "";
/* 518 */     if (Math.abs(getSampleRate() - getFrameRate()) > 1.0E-5D) {
/* 519 */       if (getFrameRate() == -1.0F) {
/* 520 */         str6 = "unknown frame rate, ";
/*     */       } else {
/* 522 */         str6 = getFrameRate() + " frames/second, ";
/*     */       } 
/*     */     }
/*     */     
/* 526 */     String str7 = "";
/* 527 */     if ((getEncoding().equals(Encoding.PCM_SIGNED) || 
/* 528 */       getEncoding().equals(Encoding.PCM_UNSIGNED)) && (
/* 529 */       getSampleSizeInBits() > 8 || 
/* 530 */       getSampleSizeInBits() == -1)) {
/* 531 */       if (isBigEndian()) {
/* 532 */         str7 = "big-endian";
/*     */       } else {
/* 534 */         str7 = "little-endian";
/*     */       } 
/*     */     }
/*     */     
/* 538 */     return str1 + str2 + str3 + str4 + str5 + str6 + str7;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Encoding
/*     */   {
/* 588 */     public static final Encoding PCM_SIGNED = new Encoding("PCM_SIGNED");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 593 */     public static final Encoding PCM_UNSIGNED = new Encoding("PCM_UNSIGNED");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 600 */     public static final Encoding PCM_FLOAT = new Encoding("PCM_FLOAT");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 605 */     public static final Encoding ULAW = new Encoding("ULAW");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 610 */     public static final Encoding ALAW = new Encoding("ALAW");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Encoding(String param1String) {
/* 628 */       this.name = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean equals(Object param1Object) {
/* 638 */       if (toString() == null) {
/* 639 */         return (param1Object != null && param1Object.toString() == null);
/*     */       }
/* 641 */       if (param1Object instanceof Encoding) {
/* 642 */         return toString().equals(param1Object.toString());
/*     */       }
/* 644 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 651 */       if (toString() == null) {
/* 652 */         return 0;
/*     */       }
/* 654 */       return toString().hashCode();
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
/*     */     public final String toString() {
/* 666 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/sampled/AudioFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */