/*     */ package sun.security.krb5.internal;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import sun.misc.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TCPClient
/*     */   extends NetClient
/*     */ {
/*     */   private Socket tcpSocket;
/*     */   private BufferedOutputStream out;
/*     */   private BufferedInputStream in;
/*     */   
/*     */   TCPClient(String paramString, int paramInt1, int paramInt2) throws IOException {
/*  62 */     this.tcpSocket = new Socket();
/*  63 */     this.tcpSocket.connect(new InetSocketAddress(paramString, paramInt1), paramInt2);
/*  64 */     this.out = new BufferedOutputStream(this.tcpSocket.getOutputStream());
/*  65 */     this.in = new BufferedInputStream(this.tcpSocket.getInputStream());
/*  66 */     this.tcpSocket.setSoTimeout(paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void send(byte[] paramArrayOfbyte) throws IOException {
/*  71 */     byte[] arrayOfByte = new byte[4];
/*  72 */     intToNetworkByteOrder(paramArrayOfbyte.length, arrayOfByte, 0, 4);
/*  73 */     this.out.write(arrayOfByte);
/*     */     
/*  75 */     this.out.write(paramArrayOfbyte);
/*  76 */     this.out.flush();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] receive() throws IOException {
/*  81 */     byte[] arrayOfByte = new byte[4];
/*  82 */     int i = readFully(arrayOfByte, 4);
/*     */     
/*  84 */     if (i != 4) {
/*  85 */       if (Krb5.DEBUG) {
/*  86 */         System.out.println(">>>DEBUG: TCPClient could not read length field");
/*     */       }
/*     */       
/*  89 */       return null;
/*     */     } 
/*     */     
/*  92 */     int j = networkByteOrderToInt(arrayOfByte, 0, 4);
/*  93 */     if (Krb5.DEBUG) {
/*  94 */       System.out.println(">>>DEBUG: TCPClient reading " + j + " bytes");
/*     */     }
/*     */     
/*  97 */     if (j <= 0) {
/*  98 */       if (Krb5.DEBUG) {
/*  99 */         System.out.println(">>>DEBUG: TCPClient zero or negative length field: " + j);
/*     */       }
/*     */       
/* 102 */       return null;
/*     */     } 
/*     */     
/*     */     try {
/* 106 */       return IOUtils.readExactlyNBytes(this.in, j);
/* 107 */     } catch (IOException iOException) {
/* 108 */       if (Krb5.DEBUG) {
/* 109 */         System.out.println(">>>DEBUG: TCPClient could not read complete packet (" + j + "/" + i + ")");
/*     */       }
/*     */ 
/*     */       
/* 113 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 119 */     this.tcpSocket.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readFully(byte[] paramArrayOfbyte, int paramInt) throws IOException {
/* 127 */     int i = 0;
/*     */     
/* 129 */     while (paramInt > 0) {
/* 130 */       int j = this.in.read(paramArrayOfbyte, i, paramInt);
/*     */       
/* 132 */       if (j == -1) {
/* 133 */         return !i ? -1 : i;
/*     */       }
/* 135 */       i += j;
/* 136 */       paramInt -= j;
/*     */     } 
/* 138 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int networkByteOrderToInt(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
/* 146 */     if (paramInt2 > 4) {
/* 147 */       throw new IllegalArgumentException("Cannot handle more than 4 bytes");
/*     */     }
/*     */ 
/*     */     
/* 151 */     int i = 0;
/*     */     
/* 153 */     for (byte b = 0; b < paramInt2; b++) {
/* 154 */       i <<= 8;
/* 155 */       i |= paramArrayOfbyte[paramInt1 + b] & 0xFF;
/*     */     } 
/* 157 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void intToNetworkByteOrder(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 166 */     if (paramInt3 > 4) {
/* 167 */       throw new IllegalArgumentException("Cannot handle more than 4 bytes");
/*     */     }
/*     */ 
/*     */     
/* 171 */     for (int i = paramInt3 - 1; i >= 0; i--) {
/* 172 */       paramArrayOfbyte[paramInt2 + i] = (byte)(paramInt1 & 0xFF);
/* 173 */       paramInt1 >>>= 8;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/TCPClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */