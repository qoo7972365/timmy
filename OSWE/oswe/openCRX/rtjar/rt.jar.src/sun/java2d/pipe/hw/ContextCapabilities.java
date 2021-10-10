/*     */ package sun.java2d.pipe.hw;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContextCapabilities
/*     */ {
/*     */   public static final int CAPS_EMPTY = 0;
/*     */   public static final int CAPS_RT_PLAIN_ALPHA = 2;
/*     */   public static final int CAPS_RT_TEXTURE_ALPHA = 4;
/*     */   public static final int CAPS_RT_TEXTURE_OPAQUE = 8;
/*     */   public static final int CAPS_MULTITEXTURE = 16;
/*     */   public static final int CAPS_TEXNONPOW2 = 32;
/*     */   public static final int CAPS_TEXNONSQUARE = 64;
/*     */   public static final int CAPS_PS20 = 128;
/*     */   public static final int CAPS_PS30 = 256;
/*     */   protected static final int FIRST_PRIVATE_CAP = 65536;
/*     */   protected final int caps;
/*     */   protected final String adapterId;
/*     */   
/*     */   protected ContextCapabilities(int paramInt, String paramString) {
/*  72 */     this.caps = paramInt;
/*  73 */     this.adapterId = (paramString != null) ? paramString : "unknown adapter";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAdapterId() {
/*  82 */     return this.adapterId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCaps() {
/*  91 */     return this.caps;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  96 */     StringBuffer stringBuffer = new StringBuffer("ContextCapabilities: adapter=" + this.adapterId + ", caps=");
/*     */ 
/*     */     
/*  99 */     if (this.caps == 0) {
/* 100 */       stringBuffer.append("CAPS_EMPTY");
/*     */     } else {
/* 102 */       if ((this.caps & 0x2) != 0) {
/* 103 */         stringBuffer.append("CAPS_RT_PLAIN_ALPHA|");
/*     */       }
/* 105 */       if ((this.caps & 0x4) != 0) {
/* 106 */         stringBuffer.append("CAPS_RT_TEXTURE_ALPHA|");
/*     */       }
/* 108 */       if ((this.caps & 0x8) != 0) {
/* 109 */         stringBuffer.append("CAPS_RT_TEXTURE_OPAQUE|");
/*     */       }
/* 111 */       if ((this.caps & 0x10) != 0) {
/* 112 */         stringBuffer.append("CAPS_MULTITEXTURE|");
/*     */       }
/* 114 */       if ((this.caps & 0x20) != 0) {
/* 115 */         stringBuffer.append("CAPS_TEXNONPOW2|");
/*     */       }
/* 117 */       if ((this.caps & 0x40) != 0) {
/* 118 */         stringBuffer.append("CAPS_TEXNONSQUARE|");
/*     */       }
/* 120 */       if ((this.caps & 0x80) != 0) {
/* 121 */         stringBuffer.append("CAPS_PS20|");
/*     */       }
/* 123 */       if ((this.caps & 0x100) != 0) {
/* 124 */         stringBuffer.append("CAPS_PS30|");
/*     */       }
/*     */     } 
/* 127 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/pipe/hw/ContextCapabilities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */