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
/*     */ public class AudioFileFormat
/*     */ {
/*     */   private Type type;
/*     */   private int byteLength;
/*     */   private AudioFormat format;
/*     */   private int frameLength;
/*     */   private HashMap<String, Object> properties;
/*     */   
/*     */   protected AudioFileFormat(Type paramType, int paramInt1, AudioFormat paramAudioFormat, int paramInt2) {
/* 150 */     this.type = paramType;
/* 151 */     this.byteLength = paramInt1;
/* 152 */     this.format = paramAudioFormat;
/* 153 */     this.frameLength = paramInt2;
/* 154 */     this.properties = null;
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
/*     */   public AudioFileFormat(Type paramType, AudioFormat paramAudioFormat, int paramInt) {
/* 169 */     this(paramType, -1, paramAudioFormat, paramInt);
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
/*     */   public AudioFileFormat(Type paramType, AudioFormat paramAudioFormat, int paramInt, Map<String, Object> paramMap) {
/* 190 */     this(paramType, -1, paramAudioFormat, paramInt);
/* 191 */     this.properties = new HashMap<>(paramMap);
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
/*     */   public Type getType() {
/* 206 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getByteLength() {
/* 215 */     return this.byteLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AudioFormat getFormat() {
/* 223 */     return this.format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFrameLength() {
/* 232 */     return this.frameLength;
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
/*     */   public Map<String, Object> properties() {
/*     */     Map<? extends String, ?> map;
/* 249 */     if (this.properties == null) {
/* 250 */       map = (Map)new HashMap<>(0);
/*     */     } else {
/* 252 */       map = (Map)this.properties.clone();
/*     */     } 
/* 254 */     return Collections.unmodifiableMap(map);
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
/* 275 */     if (this.properties == null) {
/* 276 */       return null;
/*     */     }
/* 278 */     return this.properties.get(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 288 */     StringBuffer stringBuffer = new StringBuffer();
/*     */ 
/*     */     
/* 291 */     if (this.type != null) {
/* 292 */       stringBuffer.append(this.type.toString() + " (." + this.type.getExtension() + ") file");
/*     */     } else {
/* 294 */       stringBuffer.append("unknown file format");
/*     */     } 
/*     */     
/* 297 */     if (this.byteLength != -1) {
/* 298 */       stringBuffer.append(", byte length: " + this.byteLength);
/*     */     }
/*     */     
/* 301 */     stringBuffer.append(", data format: " + this.format);
/*     */     
/* 303 */     if (this.frameLength != -1) {
/* 304 */       stringBuffer.append(", frame length: " + this.frameLength);
/*     */     }
/*     */     
/* 307 */     return new String(stringBuffer);
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
/*     */   public static class Type
/*     */   {
/* 323 */     public static final Type WAVE = new Type("WAVE", "wav");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     public static final Type AU = new Type("AU", "au");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 333 */     public static final Type AIFF = new Type("AIFF", "aif");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     public static final Type AIFC = new Type("AIFF-C", "aifc");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 343 */     public static final Type SND = new Type("SND", "snd");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String extension;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Type(String param1String1, String param1String2) {
/* 369 */       this.name = param1String1;
/* 370 */       this.extension = param1String2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean equals(Object param1Object) {
/* 380 */       if (toString() == null) {
/* 381 */         return (param1Object != null && param1Object.toString() == null);
/*     */       }
/* 383 */       if (param1Object instanceof Type) {
/* 384 */         return toString().equals(param1Object.toString());
/*     */       }
/* 386 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 393 */       if (toString() == null) {
/* 394 */         return 0;
/*     */       }
/* 396 */       return toString().hashCode();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 405 */       return this.name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getExtension() {
/* 413 */       return this.extension;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/sound/sampled/AudioFileFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */