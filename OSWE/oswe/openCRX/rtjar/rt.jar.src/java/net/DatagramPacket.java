/*     */ package java.net;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DatagramPacket
/*     */ {
/*     */   byte[] buf;
/*     */   int offset;
/*     */   int length;
/*     */   int bufLength;
/*     */   InetAddress address;
/*     */   int port;
/*     */   
/*     */   static {
/*  49 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  52 */             System.loadLibrary("net");
/*  53 */             return null;
/*     */           }
/*     */         });
/*  56 */     init();
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
/*     */   public DatagramPacket(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/*  84 */     setData(paramArrayOfbyte, paramInt1, paramInt2);
/*  85 */     this.address = null;
/*  86 */     this.port = -1;
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
/*     */   public DatagramPacket(byte[] paramArrayOfbyte, int paramInt) {
/* 100 */     this(paramArrayOfbyte, 0, paramInt);
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
/*     */   public DatagramPacket(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, InetAddress paramInetAddress, int paramInt3) {
/* 121 */     setData(paramArrayOfbyte, paramInt1, paramInt2);
/* 122 */     setAddress(paramInetAddress);
/* 123 */     setPort(paramInt3);
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
/*     */   public DatagramPacket(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, SocketAddress paramSocketAddress) {
/* 143 */     setData(paramArrayOfbyte, paramInt1, paramInt2);
/* 144 */     setSocketAddress(paramSocketAddress);
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
/*     */   public DatagramPacket(byte[] paramArrayOfbyte, int paramInt1, InetAddress paramInetAddress, int paramInt2) {
/* 161 */     this(paramArrayOfbyte, 0, paramInt1, paramInetAddress, paramInt2);
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
/*     */   public DatagramPacket(byte[] paramArrayOfbyte, int paramInt, SocketAddress paramSocketAddress) {
/* 178 */     this(paramArrayOfbyte, 0, paramInt, paramSocketAddress);
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
/*     */   public synchronized InetAddress getAddress() {
/* 191 */     return this.address;
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
/*     */   public synchronized int getPort() {
/* 203 */     return this.port;
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
/*     */   public synchronized byte[] getData() {
/* 215 */     return this.buf;
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
/*     */   public synchronized int getOffset() {
/* 228 */     return this.offset;
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
/*     */   public synchronized int getLength() {
/* 240 */     return this.length;
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
/*     */   public synchronized void setData(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 264 */     if (paramInt2 < 0 || paramInt1 < 0 || paramInt2 + paramInt1 < 0 || paramInt2 + paramInt1 > paramArrayOfbyte.length)
/*     */     {
/*     */       
/* 267 */       throw new IllegalArgumentException("illegal length or offset");
/*     */     }
/* 269 */     this.buf = paramArrayOfbyte;
/* 270 */     this.length = paramInt2;
/* 271 */     this.bufLength = paramInt2;
/* 272 */     this.offset = paramInt1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setAddress(InetAddress paramInetAddress) {
/* 283 */     this.address = paramInetAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setPort(int paramInt) {
/* 294 */     if (paramInt < 0 || paramInt > 65535) {
/* 295 */       throw new IllegalArgumentException("Port out of range:" + paramInt);
/*     */     }
/* 297 */     this.port = paramInt;
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
/*     */   public synchronized void setSocketAddress(SocketAddress paramSocketAddress) {
/* 312 */     if (paramSocketAddress == null || !(paramSocketAddress instanceof InetSocketAddress))
/* 313 */       throw new IllegalArgumentException("unsupported address type"); 
/* 314 */     InetSocketAddress inetSocketAddress = (InetSocketAddress)paramSocketAddress;
/* 315 */     if (inetSocketAddress.isUnresolved())
/* 316 */       throw new IllegalArgumentException("unresolved address"); 
/* 317 */     setAddress(inetSocketAddress.getAddress());
/* 318 */     setPort(inetSocketAddress.getPort());
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
/*     */   public synchronized SocketAddress getSocketAddress() {
/* 330 */     return new InetSocketAddress(getAddress(), getPort());
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
/*     */   public synchronized void setData(byte[] paramArrayOfbyte) {
/* 348 */     if (paramArrayOfbyte == null) {
/* 349 */       throw new NullPointerException("null packet buffer");
/*     */     }
/* 351 */     this.buf = paramArrayOfbyte;
/* 352 */     this.offset = 0;
/* 353 */     this.length = paramArrayOfbyte.length;
/* 354 */     this.bufLength = paramArrayOfbyte.length;
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
/*     */   public synchronized void setLength(int paramInt) {
/* 376 */     if (paramInt + this.offset > this.buf.length || paramInt < 0 || paramInt + this.offset < 0)
/*     */     {
/* 378 */       throw new IllegalArgumentException("illegal length");
/*     */     }
/* 380 */     this.length = paramInt;
/* 381 */     this.bufLength = this.length;
/*     */   }
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/net/DatagramPacket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */