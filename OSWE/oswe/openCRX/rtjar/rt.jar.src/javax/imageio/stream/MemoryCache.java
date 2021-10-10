/*     */ package javax.imageio.stream;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MemoryCache
/*     */ {
/*     */   private static final int BUFFER_LENGTH = 8192;
/*  61 */   private ArrayList cache = new ArrayList();
/*     */   
/*  63 */   private long cacheStart = 0L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private long length = 0L;
/*     */   
/*     */   private byte[] getCacheBlock(long paramLong) throws IOException {
/*  71 */     long l = paramLong - this.cacheStart;
/*  72 */     if (l > 2147483647L)
/*     */     {
/*     */       
/*  75 */       throw new IOException("Cache addressing limit exceeded!");
/*     */     }
/*  77 */     return this.cache.get((int)l);
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
/*     */   public long loadFromStream(InputStream paramInputStream, long paramLong) throws IOException {
/*  89 */     if (paramLong < this.length) {
/*  90 */       return paramLong;
/*     */     }
/*     */     
/*  93 */     int i = (int)(this.length % 8192L);
/*  94 */     byte[] arrayOfByte = null;
/*     */     
/*  96 */     long l = paramLong - this.length;
/*  97 */     if (i != 0) {
/*  98 */       arrayOfByte = getCacheBlock(this.length / 8192L);
/*     */     }
/*     */     
/* 101 */     while (l > 0L) {
/* 102 */       if (arrayOfByte == null) {
/*     */         try {
/* 104 */           arrayOfByte = new byte[8192];
/* 105 */         } catch (OutOfMemoryError outOfMemoryError) {
/* 106 */           throw new IOException("No memory left for cache!");
/*     */         } 
/* 108 */         i = 0;
/*     */       } 
/*     */       
/* 111 */       int j = 8192 - i;
/* 112 */       int k = (int)Math.min(l, j);
/* 113 */       k = paramInputStream.read(arrayOfByte, i, k);
/* 114 */       if (k == -1) {
/* 115 */         return this.length;
/*     */       }
/*     */       
/* 118 */       if (i == 0) {
/* 119 */         this.cache.add(arrayOfByte);
/*     */       }
/*     */       
/* 122 */       l -= k;
/* 123 */       this.length += k;
/* 124 */       i += k;
/*     */       
/* 126 */       if (i >= 8192)
/*     */       {
/*     */         
/* 129 */         arrayOfByte = null;
/*     */       }
/*     */     } 
/*     */     
/* 133 */     return paramLong;
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
/*     */   public void writeToStream(OutputStream paramOutputStream, long paramLong1, long paramLong2) throws IOException {
/* 149 */     if (paramLong1 + paramLong2 > this.length) {
/* 150 */       throw new IndexOutOfBoundsException("Argument out of cache");
/*     */     }
/* 152 */     if (paramLong1 < 0L || paramLong2 < 0L) {
/* 153 */       throw new IndexOutOfBoundsException("Negative pos or len");
/*     */     }
/* 155 */     if (paramLong2 == 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 159 */     long l = paramLong1 / 8192L;
/* 160 */     if (l < this.cacheStart) {
/* 161 */       throw new IndexOutOfBoundsException("pos already disposed");
/*     */     }
/* 163 */     int i = (int)(paramLong1 % 8192L);
/*     */     
/* 165 */     byte[] arrayOfByte = getCacheBlock(l++);
/* 166 */     while (paramLong2 > 0L) {
/* 167 */       if (arrayOfByte == null) {
/* 168 */         arrayOfByte = getCacheBlock(l++);
/* 169 */         i = 0;
/*     */       } 
/* 171 */       int j = (int)Math.min(paramLong2, (8192 - i));
/* 172 */       paramOutputStream.write(arrayOfByte, i, j);
/* 173 */       arrayOfByte = null;
/* 174 */       paramLong2 -= j;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void pad(long paramLong) throws IOException {
/* 182 */     long l1 = this.cacheStart + this.cache.size() - 1L;
/* 183 */     long l2 = paramLong / 8192L;
/* 184 */     long l3 = l2 - l1; long l4;
/* 185 */     for (l4 = 0L; l4 < l3; l4++) {
/*     */       try {
/* 187 */         this.cache.add(new byte[8192]);
/* 188 */       } catch (OutOfMemoryError outOfMemoryError) {
/* 189 */         throw new IOException("No memory left for cache!");
/*     */       } 
/*     */     } 
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
/*     */   public void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, long paramLong) throws IOException {
/* 211 */     if (paramArrayOfbyte == null) {
/* 212 */       throw new NullPointerException("b == null!");
/*     */     }
/*     */     
/* 215 */     if (paramInt1 < 0 || paramInt2 < 0 || paramLong < 0L || paramInt1 + paramInt2 > paramArrayOfbyte.length || paramInt1 + paramInt2 < 0)
/*     */     {
/* 217 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*     */     
/* 221 */     long l = paramLong + paramInt2 - 1L;
/* 222 */     if (l >= this.length) {
/* 223 */       pad(l);
/* 224 */       this.length = l + 1L;
/*     */     } 
/*     */ 
/*     */     
/* 228 */     int i = (int)(paramLong % 8192L);
/* 229 */     while (paramInt2 > 0) {
/* 230 */       byte[] arrayOfByte = getCacheBlock(paramLong / 8192L);
/* 231 */       int j = Math.min(paramInt2, 8192 - i);
/* 232 */       System.arraycopy(paramArrayOfbyte, paramInt1, arrayOfByte, i, j);
/*     */       
/* 234 */       paramLong += j;
/* 235 */       paramInt1 += j;
/* 236 */       paramInt2 -= j;
/* 237 */       i = 0;
/*     */     } 
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
/*     */   public void write(int paramInt, long paramLong) throws IOException {
/* 253 */     if (paramLong < 0L) {
/* 254 */       throw new ArrayIndexOutOfBoundsException("pos < 0");
/*     */     }
/*     */ 
/*     */     
/* 258 */     if (paramLong >= this.length) {
/* 259 */       pad(paramLong);
/* 260 */       this.length = paramLong + 1L;
/*     */     } 
/*     */ 
/*     */     
/* 264 */     byte[] arrayOfByte = getCacheBlock(paramLong / 8192L);
/* 265 */     int i = (int)(paramLong % 8192L);
/* 266 */     arrayOfByte[i] = (byte)paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLength() {
/* 275 */     return this.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(long paramLong) throws IOException {
/* 284 */     if (paramLong >= this.length) {
/* 285 */       return -1;
/*     */     }
/*     */     
/* 288 */     byte[] arrayOfByte = getCacheBlock(paramLong / 8192L);
/* 289 */     if (arrayOfByte == null) {
/* 290 */       return -1;
/*     */     }
/*     */     
/* 293 */     return arrayOfByte[(int)(paramLong % 8192L)] & 0xFF;
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
/*     */   public void read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, long paramLong) throws IOException {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ifnonnull -> 14
/*     */     //   4: new java/lang/NullPointerException
/*     */     //   7: dup
/*     */     //   8: ldc 'b == null!'
/*     */     //   10: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   13: athrow
/*     */     //   14: iload_2
/*     */     //   15: iflt -> 43
/*     */     //   18: iload_3
/*     */     //   19: iflt -> 43
/*     */     //   22: lload #4
/*     */     //   24: lconst_0
/*     */     //   25: lcmp
/*     */     //   26: iflt -> 43
/*     */     //   29: iload_2
/*     */     //   30: iload_3
/*     */     //   31: iadd
/*     */     //   32: aload_1
/*     */     //   33: arraylength
/*     */     //   34: if_icmpgt -> 43
/*     */     //   37: iload_2
/*     */     //   38: iload_3
/*     */     //   39: iadd
/*     */     //   40: ifge -> 51
/*     */     //   43: new java/lang/IndexOutOfBoundsException
/*     */     //   46: dup
/*     */     //   47: invokespecial <init> : ()V
/*     */     //   50: athrow
/*     */     //   51: lload #4
/*     */     //   53: iload_3
/*     */     //   54: i2l
/*     */     //   55: ladd
/*     */     //   56: aload_0
/*     */     //   57: getfield length : J
/*     */     //   60: lcmp
/*     */     //   61: ifle -> 72
/*     */     //   64: new java/lang/IndexOutOfBoundsException
/*     */     //   67: dup
/*     */     //   68: invokespecial <init> : ()V
/*     */     //   71: athrow
/*     */     //   72: lload #4
/*     */     //   74: ldc2_w 8192
/*     */     //   77: ldiv
/*     */     //   78: lstore #6
/*     */     //   80: lload #4
/*     */     //   82: l2i
/*     */     //   83: sipush #8192
/*     */     //   86: irem
/*     */     //   87: istore #8
/*     */     //   89: iload_3
/*     */     //   90: ifle -> 145
/*     */     //   93: iload_3
/*     */     //   94: sipush #8192
/*     */     //   97: iload #8
/*     */     //   99: isub
/*     */     //   100: invokestatic min : (II)I
/*     */     //   103: istore #9
/*     */     //   105: aload_0
/*     */     //   106: lload #6
/*     */     //   108: dup2
/*     */     //   109: lconst_1
/*     */     //   110: ladd
/*     */     //   111: lstore #6
/*     */     //   113: invokespecial getCacheBlock : (J)[B
/*     */     //   116: astore #10
/*     */     //   118: aload #10
/*     */     //   120: iload #8
/*     */     //   122: aload_1
/*     */     //   123: iload_2
/*     */     //   124: iload #9
/*     */     //   126: invokestatic arraycopy : (Ljava/lang/Object;ILjava/lang/Object;II)V
/*     */     //   129: iload_3
/*     */     //   130: iload #9
/*     */     //   132: isub
/*     */     //   133: istore_3
/*     */     //   134: iload_2
/*     */     //   135: iload #9
/*     */     //   137: iadd
/*     */     //   138: istore_2
/*     */     //   139: iconst_0
/*     */     //   140: istore #8
/*     */     //   142: goto -> 89
/*     */     //   145: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #310	-> 0
/*     */     //   #311	-> 4
/*     */     //   #314	-> 14
/*     */     //   #316	-> 43
/*     */     //   #318	-> 51
/*     */     //   #319	-> 64
/*     */     //   #322	-> 72
/*     */     //   #323	-> 80
/*     */     //   #324	-> 89
/*     */     //   #325	-> 93
/*     */     //   #326	-> 105
/*     */     //   #327	-> 118
/*     */     //   #329	-> 129
/*     */     //   #330	-> 134
/*     */     //   #331	-> 139
/*     */     //   #332	-> 142
/*     */     //   #333	-> 145
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
/*     */   public void disposeBefore(long paramLong) {
/* 343 */     long l1 = paramLong / 8192L;
/* 344 */     if (l1 < this.cacheStart) {
/* 345 */       throw new IndexOutOfBoundsException("pos already disposed");
/*     */     }
/* 347 */     long l2 = Math.min(l1 - this.cacheStart, this.cache.size()); long l3;
/* 348 */     for (l3 = 0L; l3 < l2; l3++) {
/* 349 */       this.cache.remove(0);
/*     */     }
/* 351 */     this.cacheStart = l1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 360 */     this.cache.clear();
/* 361 */     this.cacheStart = 0L;
/* 362 */     this.length = 0L;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/stream/MemoryCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */