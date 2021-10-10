/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IOUtils
/*     */ {
/*     */   private static final int DEFAULT_BUFFER_SIZE = 8192;
/*     */   private static final int MAX_BUFFER_SIZE = 2147483639;
/*     */   
/*     */   public static byte[] readExactlyNBytes(InputStream paramInputStream, int paramInt) throws IOException {
/*  71 */     if (paramInt < 0) {
/*  72 */       throw new IOException("length cannot be negative: " + paramInt);
/*     */     }
/*  74 */     byte[] arrayOfByte = readNBytes(paramInputStream, paramInt);
/*  75 */     if (arrayOfByte.length < paramInt) {
/*  76 */       throw new EOFException();
/*     */     }
/*  78 */     return arrayOfByte;
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
/*     */   public static byte[] readAllBytes(InputStream paramInputStream) throws IOException {
/* 116 */     return readNBytes(paramInputStream, 2147483647);
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
/*     */   public static byte[] readNBytes(InputStream paramInputStream, int paramInt) throws IOException {
/*     */     int k;
/* 166 */     if (paramInt < 0) {
/* 167 */       throw new IllegalArgumentException("len < 0");
/*     */     }
/*     */     
/* 170 */     ArrayList<byte[]> arrayList = null;
/* 171 */     byte[] arrayOfByte = null;
/* 172 */     int i = 0;
/* 173 */     int j = paramInt;
/*     */     
/*     */     do {
/* 176 */       byte[] arrayOfByte1 = new byte[Math.min(j, 8192)];
/* 177 */       int n = 0;
/*     */       
/*     */       while (true) {
/* 180 */         if ((k = paramInputStream.read(arrayOfByte1, n, 
/* 181 */             Math.min(arrayOfByte1.length - n, j))) > 0) {
/* 182 */           n += k;
/* 183 */           j -= k; continue;
/*     */         }  break;
/*     */       } 
/* 186 */       if (n <= 0)
/* 187 */         continue;  if (2147483639 - i < n) {
/* 188 */         throw new OutOfMemoryError("Required array size too large");
/*     */       }
/* 190 */       i += n;
/* 191 */       if (arrayOfByte == null) {
/* 192 */         arrayOfByte = arrayOfByte1;
/*     */       } else {
/* 194 */         if (arrayList == null) {
/* 195 */           arrayList = new ArrayList();
/* 196 */           arrayList.add(arrayOfByte);
/*     */         } 
/* 198 */         arrayList.add(arrayOfByte1);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 203 */     while (k >= 0 && j > 0);
/*     */     
/* 205 */     if (arrayList == null) {
/* 206 */       if (arrayOfByte == null) {
/* 207 */         return new byte[0];
/*     */       }
/* 209 */       return (arrayOfByte.length == i) ? arrayOfByte : 
/* 210 */         Arrays.copyOf(arrayOfByte, i);
/*     */     } 
/*     */     
/* 213 */     arrayOfByte = new byte[i];
/* 214 */     int m = 0;
/* 215 */     j = i;
/* 216 */     for (byte[] arrayOfByte1 : arrayList) {
/* 217 */       int n = Math.min(arrayOfByte1.length, j);
/* 218 */       System.arraycopy(arrayOfByte1, 0, arrayOfByte, m, n);
/* 219 */       m += n;
/* 220 */       j -= n;
/*     */     } 
/*     */     
/* 223 */     return arrayOfByte;
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
/*     */   public static int readNBytes(InputStream paramInputStream, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 271 */     Objects.requireNonNull(paramArrayOfbyte);
/* 272 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt2 > paramArrayOfbyte.length - paramInt1)
/* 273 */       throw new IndexOutOfBoundsException(); 
/* 274 */     int i = 0;
/* 275 */     while (i < paramInt2) {
/* 276 */       int j = paramInputStream.read(paramArrayOfbyte, paramInt1 + i, paramInt2 - i);
/* 277 */       if (j < 0)
/*     */         break; 
/* 279 */       i += j;
/*     */     } 
/* 281 */     return i;
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
/*     */   public static byte[] readFully(InputStream paramInputStream, int paramInt, boolean paramBoolean) throws IOException {
/* 304 */     if (paramInt < 0) {
/* 305 */       throw new IOException("length cannot be negative: " + paramInt);
/*     */     }
/* 307 */     if (paramBoolean) {
/* 308 */       return readExactlyNBytes(paramInputStream, paramInt);
/*     */     }
/* 310 */     return readNBytes(paramInputStream, paramInt);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/IOUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */