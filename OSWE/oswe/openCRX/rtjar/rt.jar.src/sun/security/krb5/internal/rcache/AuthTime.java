/*     */ package sun.security.krb5.internal.rcache;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.SeekableByteChannel;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuthTime
/*     */ {
/*     */   final int ctime;
/*     */   final int cusec;
/*     */   final String client;
/*     */   final String server;
/*     */   
/*     */   public AuthTime(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/*  60 */     this.ctime = paramInt1;
/*  61 */     this.cusec = paramInt2;
/*  62 */     this.client = paramString1;
/*  63 */     this.server = paramString2;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  68 */     return String.format("%d/%06d/----/%s", new Object[] { Integer.valueOf(this.ctime), Integer.valueOf(this.cusec), this.client });
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
/*     */   private static String readStringWithLength(SeekableByteChannel paramSeekableByteChannel) throws IOException {
/*  81 */     ByteBuffer byteBuffer = ByteBuffer.allocate(4);
/*  82 */     byteBuffer.order(ByteOrder.nativeOrder());
/*  83 */     paramSeekableByteChannel.read(byteBuffer);
/*  84 */     byteBuffer.flip();
/*  85 */     int i = byteBuffer.getInt();
/*  86 */     if (i > 1024)
/*     */     {
/*  88 */       throw new IOException("Invalid string length");
/*     */     }
/*  90 */     byteBuffer = ByteBuffer.allocate(i);
/*  91 */     if (paramSeekableByteChannel.read(byteBuffer) != i) {
/*  92 */       throw new IOException("Not enough string");
/*     */     }
/*  94 */     byte[] arrayOfByte = byteBuffer.array();
/*  95 */     return (arrayOfByte[i - 1] == 0) ? new String(arrayOfByte, 0, i - 1, StandardCharsets.UTF_8) : new String(arrayOfByte, StandardCharsets.UTF_8);
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
/*     */   public static AuthTime readFrom(SeekableByteChannel paramSeekableByteChannel) throws IOException {
/* 107 */     String str1 = readStringWithLength(paramSeekableByteChannel);
/* 108 */     String str2 = readStringWithLength(paramSeekableByteChannel);
/* 109 */     ByteBuffer byteBuffer = ByteBuffer.allocate(8);
/* 110 */     paramSeekableByteChannel.read(byteBuffer);
/* 111 */     byteBuffer.order(ByteOrder.nativeOrder());
/* 112 */     int i = byteBuffer.getInt(0);
/* 113 */     int j = byteBuffer.getInt(4);
/* 114 */     if (str1.isEmpty()) {
/* 115 */       StringTokenizer stringTokenizer = new StringTokenizer(str2, " :");
/* 116 */       if (stringTokenizer.countTokens() != 6) {
/* 117 */         throw new IOException("Incorrect rcache style");
/*     */       }
/* 119 */       stringTokenizer.nextToken();
/* 120 */       String str = stringTokenizer.nextToken();
/* 121 */       stringTokenizer.nextToken();
/* 122 */       str1 = stringTokenizer.nextToken();
/* 123 */       stringTokenizer.nextToken();
/* 124 */       str2 = stringTokenizer.nextToken();
/* 125 */       return new AuthTimeWithHash(str1, str2, j, i, str);
/*     */     } 
/*     */     
/* 128 */     return new AuthTime(str1, str2, j, i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected byte[] encode0(String paramString1, String paramString2) {
/* 137 */     byte[] arrayOfByte1 = paramString1.getBytes(StandardCharsets.UTF_8);
/* 138 */     byte[] arrayOfByte2 = paramString2.getBytes(StandardCharsets.UTF_8);
/* 139 */     byte[] arrayOfByte3 = new byte[1];
/* 140 */     int i = 4 + arrayOfByte1.length + 1 + 4 + arrayOfByte2.length + 1 + 4 + 4;
/*     */     
/* 142 */     ByteBuffer byteBuffer = ByteBuffer.allocate(i).order(ByteOrder.nativeOrder());
/* 143 */     byteBuffer.putInt(arrayOfByte1.length + 1).put(arrayOfByte1).put(arrayOfByte3)
/* 144 */       .putInt(arrayOfByte2.length + 1).put(arrayOfByte2).put(arrayOfByte3)
/* 145 */       .putInt(this.cusec).putInt(this.ctime);
/* 146 */     return byteBuffer.array();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] encode(boolean paramBoolean) {
/* 154 */     return encode0(this.client, this.server);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/krb5/internal/rcache/AuthTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */