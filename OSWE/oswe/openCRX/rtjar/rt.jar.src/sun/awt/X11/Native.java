/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Vector;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Native
/*     */ {
/*  40 */   private static Unsafe unsafe = XlibWrapper.unsafe;
/*     */   
/*     */   static int longSize;
/*     */   
/*     */   static int dataModel;
/*     */   
/*     */   static {
/*  47 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public Object run() {
/*  50 */             return System.getProperty("sun.arch.data.model");
/*     */           }
/*     */         });
/*     */     try {
/*  54 */       dataModel = Integer.parseInt(str);
/*  55 */     } catch (Exception exception) {
/*  56 */       dataModel = 32;
/*     */     } 
/*  58 */     if (dataModel == 32) {
/*  59 */       longSize = 4;
/*     */     } else {
/*  61 */       longSize = 8;
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
/*     */   static boolean getBool(long paramLong) {
/*  73 */     return (getInt(paramLong) != 0);
/*  74 */   } static boolean getBool(long paramLong, int paramInt) { return (getInt(paramLong, paramInt) != 0); }
/*  75 */   static void putBool(long paramLong, boolean paramBoolean) { putInt(paramLong, paramBoolean ? 1 : 0); } static void putBool(long paramLong, int paramInt, boolean paramBoolean) {
/*  76 */     putInt(paramLong, paramInt, paramBoolean ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getByteSize() {
/*  82 */     return 1; } static byte getByte(long paramLong) {
/*  83 */     return unsafe.getByte(paramLong);
/*     */   }
/*     */   static byte getByte(long paramLong, int paramInt) {
/*  86 */     return getByte(paramLong + paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   static void putByte(long paramLong, byte paramByte) {
/*  91 */     unsafe.putByte(paramLong, paramByte);
/*     */   }
/*     */   static void putByte(long paramLong, int paramInt, byte paramByte) {
/*  94 */     putByte(paramLong + paramInt, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static byte[] toBytes(long paramLong, int paramInt) {
/* 103 */     if (paramLong == 0L) {
/* 104 */       return null;
/*     */     }
/* 106 */     byte[] arrayOfByte = new byte[paramInt];
/* 107 */     for (byte b = 0; b < paramInt; b++, paramLong++) {
/* 108 */       arrayOfByte[b] = getByte(paramLong);
/*     */     }
/* 110 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long toData(byte[] paramArrayOfbyte) {
/* 117 */     if (paramArrayOfbyte == null) {
/* 118 */       return 0L;
/*     */     }
/* 120 */     long l = XlibWrapper.unsafe.allocateMemory(paramArrayOfbyte.length);
/* 121 */     for (byte b = 0; b < paramArrayOfbyte.length; b++) {
/* 122 */       putByte(l + b, paramArrayOfbyte[b]);
/*     */     }
/* 124 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getUByteSize() {
/* 130 */     return 1; } static short getUByte(long paramLong) {
/* 131 */     return (short)(0xFF & unsafe.getByte(paramLong));
/*     */   }
/*     */   static short getUByte(long paramLong, int paramInt) {
/* 134 */     return getUByte(paramLong + paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void putUByte(long paramLong, short paramShort) {
/* 140 */     unsafe.putByte(paramLong, (byte)paramShort);
/*     */   }
/*     */   static void putUByte(long paramLong, int paramInt, short paramShort) {
/* 143 */     putUByte(paramLong + paramInt, paramShort);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static short[] toUBytes(long paramLong, int paramInt) {
/* 154 */     if (paramLong == 0L) {
/* 155 */       return null;
/*     */     }
/* 157 */     short[] arrayOfShort = new short[paramInt];
/* 158 */     for (byte b = 0; b < paramInt; b++, paramLong++) {
/* 159 */       arrayOfShort[b] = getUByte(paramLong);
/*     */     }
/* 161 */     return arrayOfShort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long toUData(short[] paramArrayOfshort) {
/* 169 */     if (paramArrayOfshort == null) {
/* 170 */       return 0L;
/*     */     }
/* 172 */     long l = XlibWrapper.unsafe.allocateMemory(paramArrayOfshort.length);
/* 173 */     for (byte b = 0; b < paramArrayOfshort.length; b++) {
/* 174 */       putUByte(l + b, paramArrayOfshort[b]);
/*     */     }
/* 176 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getShortSize() {
/* 182 */     return 2; } static short getShort(long paramLong) {
/* 183 */     return unsafe.getShort(paramLong);
/*     */   }
/*     */   
/*     */   static void putShort(long paramLong, short paramShort) {
/* 187 */     unsafe.putShort(paramLong, paramShort);
/*     */   } static void putShort(long paramLong, int paramInt, short paramShort) {
/* 189 */     putShort(paramLong + (paramInt * getShortSize()), paramShort);
/*     */   }
/*     */   static long toData(short[] paramArrayOfshort) {
/* 192 */     if (paramArrayOfshort == null) {
/* 193 */       return 0L;
/*     */     }
/* 195 */     long l = XlibWrapper.unsafe.allocateMemory((paramArrayOfshort.length * getShortSize()));
/* 196 */     for (byte b = 0; b < paramArrayOfshort.length; b++) {
/* 197 */       putShort(l, b, paramArrayOfshort[b]);
/*     */     }
/* 199 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getUShortSize() {
/* 205 */     return 2;
/*     */   } static int getUShort(long paramLong) {
/* 207 */     return 0xFFFF & unsafe.getShort(paramLong);
/*     */   }
/*     */   
/*     */   static void putUShort(long paramLong, int paramInt) {
/* 211 */     unsafe.putShort(paramLong, (short)paramInt);
/*     */   } static void putUShort(long paramLong, int paramInt1, int paramInt2) {
/* 213 */     putUShort(paramLong + (paramInt1 * getShortSize()), paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long toUData(int[] paramArrayOfint) {
/* 222 */     if (paramArrayOfint == null) {
/* 223 */       return 0L;
/*     */     }
/* 225 */     long l = XlibWrapper.unsafe.allocateMemory((paramArrayOfint.length * getShortSize()));
/* 226 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 227 */       putUShort(l, b, paramArrayOfint[b]);
/*     */     }
/* 229 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getIntSize() {
/* 235 */     return 4;
/* 236 */   } static int getInt(long paramLong) { return unsafe.getInt(paramLong); } static int getInt(long paramLong, int paramInt) {
/* 237 */     return getInt(paramLong + (getIntSize() * paramInt));
/*     */   }
/*     */   
/*     */   static void putInt(long paramLong, int paramInt) {
/* 241 */     unsafe.putInt(paramLong, paramInt);
/*     */   } static void putInt(long paramLong, int paramInt1, int paramInt2) {
/* 243 */     putInt(paramLong + (paramInt1 * getIntSize()), paramInt2);
/*     */   }
/*     */   static long toData(int[] paramArrayOfint) {
/* 246 */     if (paramArrayOfint == null) {
/* 247 */       return 0L;
/*     */     }
/* 249 */     long l = XlibWrapper.unsafe.allocateMemory((paramArrayOfint.length * getIntSize()));
/* 250 */     for (byte b = 0; b < paramArrayOfint.length; b++) {
/* 251 */       putInt(l, b, paramArrayOfint[b]);
/*     */     }
/* 253 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static int getUIntSize() {
/* 259 */     return 4;
/* 260 */   } static long getUInt(long paramLong) { return 0xFFFFFFFFL & unsafe.getInt(paramLong); } static long getUInt(long paramLong, int paramInt) {
/* 261 */     return getUInt(paramLong + (getIntSize() * paramInt));
/*     */   }
/*     */   
/*     */   static void putUInt(long paramLong1, long paramLong2) {
/* 265 */     unsafe.putInt(paramLong1, (int)paramLong2);
/*     */   } static void putUInt(long paramLong1, int paramInt, long paramLong2) {
/* 267 */     putUInt(paramLong1 + (paramInt * getIntSize()), paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long toUData(long[] paramArrayOflong) {
/* 276 */     if (paramArrayOflong == null) {
/* 277 */       return 0L;
/*     */     }
/* 279 */     long l = XlibWrapper.unsafe.allocateMemory((paramArrayOflong.length * getIntSize()));
/* 280 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 281 */       putUInt(l, b, paramArrayOflong[b]);
/*     */     }
/* 283 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getLongSize() {
/* 290 */     return longSize;
/*     */   }
/*     */   static long getLong(long paramLong) {
/* 293 */     if (XlibWrapper.dataModel == 32) {
/* 294 */       return unsafe.getInt(paramLong);
/*     */     }
/* 296 */     return unsafe.getLong(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void putLong(long paramLong1, long paramLong2) {
/* 305 */     if (XlibWrapper.dataModel == 32) {
/* 306 */       unsafe.putInt(paramLong1, (int)paramLong2);
/*     */     } else {
/* 308 */       unsafe.putLong(paramLong1, paramLong2);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void putLong(long paramLong1, int paramInt, long paramLong2) {
/* 313 */     putLong(paramLong1 + (paramInt * getLongSize()), paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getLong(long paramLong, int paramInt) {
/* 320 */     return getLong(paramLong + (paramInt * getLongSize()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void put(long paramLong, long[] paramArrayOflong) {
/* 327 */     for (byte b = 0; b < paramArrayOflong.length; b++, paramLong += getLongSize()) {
/* 328 */       putLong(paramLong, paramArrayOflong[b]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void putLong(long paramLong, Vector<Long> paramVector) {
/* 337 */     for (byte b = 0; b < paramVector.size(); b++, paramLong += getLongSize()) {
/* 338 */       putLong(paramLong, ((Long)paramVector.elementAt(b)).longValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void putLongReverse(long paramLong, Vector<Long> paramVector) {
/* 347 */     for (int i = paramVector.size() - 1; i >= 0; i--, paramLong += getLongSize()) {
/* 348 */       putLong(paramLong, ((Long)paramVector.elementAt(i)).longValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long[] toLongs(long paramLong, int paramInt) {
/* 358 */     if (paramLong == 0L) {
/* 359 */       return null;
/*     */     }
/* 361 */     long[] arrayOfLong = new long[paramInt];
/* 362 */     for (byte b = 0; b < paramInt; b++, paramLong += getLongSize()) {
/* 363 */       arrayOfLong[b] = getLong(paramLong);
/*     */     }
/* 365 */     return arrayOfLong;
/*     */   }
/*     */   static long toData(long[] paramArrayOflong) {
/* 368 */     if (paramArrayOflong == null) {
/* 369 */       return 0L;
/*     */     }
/* 371 */     long l = XlibWrapper.unsafe.allocateMemory((paramArrayOflong.length * getLongSize()));
/* 372 */     for (byte b = 0; b < paramArrayOflong.length; b++) {
/* 373 */       putLong(l, b, paramArrayOflong[b]);
/*     */     }
/* 375 */     return l;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getULong(long paramLong) {
/* 383 */     if (XlibWrapper.dataModel == 32)
/*     */     {
/* 385 */       return unsafe.getInt(paramLong) & 0xFFFFFFFFL;
/*     */     }
/*     */     
/* 388 */     return unsafe.getLong(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   static void putULong(long paramLong1, long paramLong2) {
/* 393 */     putLong(paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long allocateLongArray(int paramInt) {
/* 400 */     return unsafe.allocateMemory((getLongSize() * paramInt));
/*     */   }
/*     */ 
/*     */   
/*     */   static long getWindow(long paramLong) {
/* 405 */     return getLong(paramLong);
/*     */   }
/*     */   static long getWindow(long paramLong, int paramInt) {
/* 408 */     return getLong(paramLong + (getWindowSize() * paramInt));
/*     */   }
/*     */   
/*     */   static void putWindow(long paramLong1, long paramLong2) {
/* 412 */     putLong(paramLong1, paramLong2);
/*     */   }
/*     */   
/*     */   static void putWindow(long paramLong1, int paramInt, long paramLong2) {
/* 416 */     putLong(paramLong1, paramInt, paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getWindowSize() {
/* 424 */     return getLongSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getCard32(long paramLong) {
/* 435 */     return getLong(paramLong);
/*     */   }
/*     */   static void putCard32(long paramLong1, long paramLong2) {
/* 438 */     putLong(paramLong1, paramLong2);
/*     */   }
/*     */   static long getCard32(long paramLong, int paramInt) {
/* 441 */     return getLong(paramLong, paramInt);
/*     */   }
/*     */   static void putCard32(long paramLong1, int paramInt, long paramLong2) {
/* 444 */     putLong(paramLong1, paramInt, paramLong2);
/*     */   }
/*     */   static int getCard32Size() {
/* 447 */     return getLongSize();
/*     */   }
/*     */   static long[] card32ToArray(long paramLong, int paramInt) {
/* 450 */     return toLongs(paramLong, paramInt);
/*     */   }
/*     */   static long card32ToData(long[] paramArrayOflong) {
/* 453 */     return toData(paramArrayOflong);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/Native.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */