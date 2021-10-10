/*     */ package javax.net.ssl;
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
/*     */ public abstract class SNIServerName
/*     */ {
/*     */   private final int type;
/*     */   private final byte[] encoded;
/*  56 */   private static final char[] HEXES = "0123456789ABCDEF".toCharArray();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SNIServerName(int paramInt, byte[] paramArrayOfbyte) {
/*  75 */     if (paramInt < 0) {
/*  76 */       throw new IllegalArgumentException("Server name type cannot be less than zero");
/*     */     }
/*  78 */     if (paramInt > 255) {
/*  79 */       throw new IllegalArgumentException("Server name type cannot be greater than 255");
/*     */     }
/*     */     
/*  82 */     this.type = paramInt;
/*     */     
/*  84 */     if (paramArrayOfbyte == null) {
/*  85 */       throw new NullPointerException("Server name encoded value cannot be null");
/*     */     }
/*     */     
/*  88 */     this.encoded = (byte[])paramArrayOfbyte.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getType() {
/*  98 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getEncoded() {
/* 107 */     return (byte[])this.encoded.clone();
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
/*     */   public boolean equals(Object paramObject) {
/* 119 */     if (this == paramObject) {
/* 120 */       return true;
/*     */     }
/*     */     
/* 123 */     if (getClass() != paramObject.getClass()) {
/* 124 */       return false;
/*     */     }
/*     */     
/* 127 */     SNIServerName sNIServerName = (SNIServerName)paramObject;
/* 128 */     return (this.type == sNIServerName.type && 
/* 129 */       Arrays.equals(this.encoded, sNIServerName.encoded));
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
/*     */   public int hashCode() {
/* 142 */     int i = 17;
/* 143 */     i = 31 * i + this.type;
/* 144 */     i = 31 * i + Arrays.hashCode(this.encoded);
/*     */     
/* 146 */     return i;
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
/*     */   public String toString() {
/* 183 */     if (this.type == 0) {
/* 184 */       return "type=host_name (0), value=" + toHexString(this.encoded);
/*     */     }
/* 186 */     return "type=(" + this.type + "), value=" + toHexString(this.encoded);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String toHexString(byte[] paramArrayOfbyte) {
/* 192 */     if (paramArrayOfbyte.length == 0) {
/* 193 */       return "(empty)";
/*     */     }
/*     */     
/* 196 */     StringBuilder stringBuilder = new StringBuilder(paramArrayOfbyte.length * 3 - 1);
/* 197 */     boolean bool = true;
/* 198 */     for (byte b : paramArrayOfbyte) {
/* 199 */       if (bool) {
/* 200 */         bool = false;
/*     */       } else {
/* 202 */         stringBuilder.append(':');
/*     */       } 
/*     */       
/* 205 */       int i = b & 0xFF;
/* 206 */       stringBuilder.append(HEXES[i >>> 4]);
/* 207 */       stringBuilder.append(HEXES[i & 0xF]);
/*     */     } 
/*     */     
/* 210 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/net/ssl/SNIServerName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */