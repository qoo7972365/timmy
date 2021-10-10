/*     */ package java.net;
/*     */ 
/*     */ import java.io.ObjectStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Inet4Address
/*     */   extends InetAddress
/*     */ {
/*     */   static final int INADDRSZ = 4;
/*     */   private static final long serialVersionUID = 3286316764910316507L;
/*     */   
/*     */   static {
/*  98 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   Inet4Address() {
/* 103 */     (holder()).hostName = null;
/* 104 */     (holder()).address = 0;
/* 105 */     (holder()).family = 1;
/*     */   }
/*     */   
/*     */   Inet4Address(String paramString, byte[] paramArrayOfbyte) {
/* 109 */     (holder()).hostName = paramString;
/* 110 */     (holder()).family = 1;
/* 111 */     if (paramArrayOfbyte != null && 
/* 112 */       paramArrayOfbyte.length == 4) {
/* 113 */       int i = paramArrayOfbyte[3] & 0xFF;
/* 114 */       i |= paramArrayOfbyte[2] << 8 & 0xFF00;
/* 115 */       i |= paramArrayOfbyte[1] << 16 & 0xFF0000;
/* 116 */       i |= paramArrayOfbyte[0] << 24 & 0xFF000000;
/* 117 */       (holder()).address = i;
/*     */     } 
/*     */     
/* 120 */     (holder()).originalHostName = paramString;
/*     */   }
/*     */   Inet4Address(String paramString, int paramInt) {
/* 123 */     (holder()).hostName = paramString;
/* 124 */     (holder()).family = 1;
/* 125 */     (holder()).address = paramInt;
/* 126 */     (holder()).originalHostName = paramString;
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
/*     */   private Object writeReplace() throws ObjectStreamException {
/* 139 */     InetAddress inetAddress = new InetAddress();
/* 140 */     (inetAddress.holder()).hostName = holder().getHostName();
/* 141 */     (inetAddress.holder()).address = holder().getAddress();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     (inetAddress.holder()).family = 2;
/*     */     
/* 151 */     return inetAddress;
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
/*     */   public boolean isMulticastAddress() {
/* 163 */     return ((holder().getAddress() & 0xF0000000) == -536870912);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnyLocalAddress() {
/* 173 */     return (holder().getAddress() == 0);
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
/*     */   public boolean isLoopbackAddress() {
/* 185 */     byte[] arrayOfByte = getAddress();
/* 186 */     return (arrayOfByte[0] == Byte.MAX_VALUE);
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
/*     */   public boolean isLinkLocalAddress() {
/* 201 */     int i = holder().getAddress();
/* 202 */     return ((i >>> 24 & 0xFF) == 169 && (i >>> 16 & 0xFF) == 254);
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
/*     */   public boolean isSiteLocalAddress() {
/* 218 */     int i = holder().getAddress();
/* 219 */     return ((i >>> 24 & 0xFF) == 10 || ((i >>> 24 & 0xFF) == 172 && (i >>> 16 & 0xF0) == 16) || ((i >>> 24 & 0xFF) == 192 && (i >>> 16 & 0xFF) == 168));
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
/*     */   public boolean isMCGlobal() {
/* 236 */     byte[] arrayOfByte = getAddress();
/* 237 */     return ((arrayOfByte[0] & 0xFF) >= 224 && (arrayOfByte[0] & 0xFF) <= 238 && ((arrayOfByte[0] & 0xFF) != 224 || arrayOfByte[1] != 0 || arrayOfByte[2] != 0));
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
/*     */   public boolean isMCNodeLocal() {
/* 252 */     return false;
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
/*     */   public boolean isMCLinkLocal() {
/* 265 */     int i = holder().getAddress();
/* 266 */     return ((i >>> 24 & 0xFF) == 224 && (i >>> 16 & 0xFF) == 0 && (i >>> 8 & 0xFF) == 0);
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
/*     */   public boolean isMCSiteLocal() {
/* 281 */     int i = holder().getAddress();
/* 282 */     return ((i >>> 24 & 0xFF) == 239 && (i >>> 16 & 0xFF) == 255);
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
/*     */   public boolean isMCOrgLocal() {
/* 297 */     int i = holder().getAddress();
/* 298 */     return ((i >>> 24 & 0xFF) == 239 && (i >>> 16 & 0xFF) >= 192 && (i >>> 16 & 0xFF) <= 195);
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
/*     */   public byte[] getAddress() {
/* 311 */     int i = holder().getAddress();
/* 312 */     byte[] arrayOfByte = new byte[4];
/*     */     
/* 314 */     arrayOfByte[0] = (byte)(i >>> 24 & 0xFF);
/* 315 */     arrayOfByte[1] = (byte)(i >>> 16 & 0xFF);
/* 316 */     arrayOfByte[2] = (byte)(i >>> 8 & 0xFF);
/* 317 */     arrayOfByte[3] = (byte)(i & 0xFF);
/* 318 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHostAddress() {
/* 328 */     return numericToTextFormat(getAddress());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 337 */     return holder().getAddress();
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
/*     */   public boolean equals(Object paramObject) {
/* 357 */     return (paramObject != null && paramObject instanceof Inet4Address && ((InetAddress)paramObject)
/* 358 */       .holder().getAddress() == holder().getAddress());
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
/*     */   static String numericToTextFormat(byte[] paramArrayOfbyte) {
/* 373 */     return (paramArrayOfbyte[0] & 0xFF) + "." + (paramArrayOfbyte[1] & 0xFF) + "." + (paramArrayOfbyte[2] & 0xFF) + "." + (paramArrayOfbyte[3] & 0xFF);
/*     */   }
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/Inet4Address.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */