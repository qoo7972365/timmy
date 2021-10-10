/*     */ package sun.security.provider;
/*     */ 
/*     */ import java.nio.ByteOrder;
/*     */ import java.security.AccessController;
/*     */ import sun.misc.Unsafe;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ByteArrayAccess
/*     */ {
/*  62 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean littleEndianUnaligned;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean bigEndian;
/*     */ 
/*     */ 
/*     */   
/*  74 */   private static final int byteArrayOfs = unsafe.arrayBaseOffset(byte[].class);
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  79 */     boolean bool = (unsafe.arrayIndexScale(byte[].class) == 1 && unsafe.arrayIndexScale(int[].class) == 4 && unsafe.arrayIndexScale(long[].class) == 8 && (byteArrayOfs & 0x3) == 0) ? true : false;
/*     */ 
/*     */     
/*  82 */     ByteOrder byteOrder = ByteOrder.nativeOrder();
/*     */     
/*  84 */     littleEndianUnaligned = (bool && unaligned() && byteOrder == ByteOrder.LITTLE_ENDIAN);
/*  85 */     bigEndian = (bool && byteOrder == ByteOrder.BIG_ENDIAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean unaligned() {
/*  95 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("os.arch", ""));
/*  96 */     return (str.equals("i386") || str.equals("x86") || str.equals("amd64") || str
/*  97 */       .equals("x86_64") || str.equals("ppc64") || str.equals("ppc64le") || str
/*  98 */       .equals("aarch64"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void b2iLittle(byte[] paramArrayOfbyte, int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3) {
/* 105 */     if (paramInt1 < 0 || paramArrayOfbyte.length - paramInt1 < paramInt3 || paramInt2 < 0 || paramArrayOfint.length - paramInt2 < paramInt3 / 4)
/*     */     {
/* 107 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 109 */     if (littleEndianUnaligned) {
/* 110 */       paramInt1 += byteArrayOfs;
/* 111 */       paramInt3 += paramInt1;
/* 112 */       while (paramInt1 < paramInt3) {
/* 113 */         paramArrayOfint[paramInt2++] = unsafe.getInt(paramArrayOfbyte, paramInt1);
/* 114 */         paramInt1 += 4;
/*     */       } 
/* 116 */     } else if (bigEndian && (paramInt1 & 0x3) == 0) {
/* 117 */       paramInt1 += byteArrayOfs;
/* 118 */       paramInt3 += paramInt1;
/* 119 */       while (paramInt1 < paramInt3) {
/* 120 */         paramArrayOfint[paramInt2++] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, paramInt1));
/* 121 */         paramInt1 += 4;
/*     */       } 
/*     */     } else {
/* 124 */       paramInt3 += paramInt1;
/* 125 */       while (paramInt1 < paramInt3) {
/* 126 */         paramArrayOfint[paramInt2++] = paramArrayOfbyte[paramInt1] & 0xFF | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 8 | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 16 | paramArrayOfbyte[paramInt1 + 3] << 24;
/*     */ 
/*     */ 
/*     */         
/* 130 */         paramInt1 += 4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void b2iLittle64(byte[] paramArrayOfbyte, int paramInt, int[] paramArrayOfint) {
/* 137 */     if (paramInt < 0 || paramArrayOfbyte.length - paramInt < 64 || paramArrayOfint.length < 16)
/*     */     {
/* 139 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 141 */     if (littleEndianUnaligned) {
/* 142 */       paramInt += byteArrayOfs;
/* 143 */       paramArrayOfint[0] = unsafe.getInt(paramArrayOfbyte, paramInt);
/* 144 */       paramArrayOfint[1] = unsafe.getInt(paramArrayOfbyte, (paramInt + 4));
/* 145 */       paramArrayOfint[2] = unsafe.getInt(paramArrayOfbyte, (paramInt + 8));
/* 146 */       paramArrayOfint[3] = unsafe.getInt(paramArrayOfbyte, (paramInt + 12));
/* 147 */       paramArrayOfint[4] = unsafe.getInt(paramArrayOfbyte, (paramInt + 16));
/* 148 */       paramArrayOfint[5] = unsafe.getInt(paramArrayOfbyte, (paramInt + 20));
/* 149 */       paramArrayOfint[6] = unsafe.getInt(paramArrayOfbyte, (paramInt + 24));
/* 150 */       paramArrayOfint[7] = unsafe.getInt(paramArrayOfbyte, (paramInt + 28));
/* 151 */       paramArrayOfint[8] = unsafe.getInt(paramArrayOfbyte, (paramInt + 32));
/* 152 */       paramArrayOfint[9] = unsafe.getInt(paramArrayOfbyte, (paramInt + 36));
/* 153 */       paramArrayOfint[10] = unsafe.getInt(paramArrayOfbyte, (paramInt + 40));
/* 154 */       paramArrayOfint[11] = unsafe.getInt(paramArrayOfbyte, (paramInt + 44));
/* 155 */       paramArrayOfint[12] = unsafe.getInt(paramArrayOfbyte, (paramInt + 48));
/* 156 */       paramArrayOfint[13] = unsafe.getInt(paramArrayOfbyte, (paramInt + 52));
/* 157 */       paramArrayOfint[14] = unsafe.getInt(paramArrayOfbyte, (paramInt + 56));
/* 158 */       paramArrayOfint[15] = unsafe.getInt(paramArrayOfbyte, (paramInt + 60));
/* 159 */     } else if (bigEndian && (paramInt & 0x3) == 0) {
/* 160 */       paramInt += byteArrayOfs;
/* 161 */       paramArrayOfint[0] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, paramInt));
/* 162 */       paramArrayOfint[1] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 4)));
/* 163 */       paramArrayOfint[2] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 8)));
/* 164 */       paramArrayOfint[3] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 12)));
/* 165 */       paramArrayOfint[4] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 16)));
/* 166 */       paramArrayOfint[5] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 20)));
/* 167 */       paramArrayOfint[6] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 24)));
/* 168 */       paramArrayOfint[7] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 28)));
/* 169 */       paramArrayOfint[8] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 32)));
/* 170 */       paramArrayOfint[9] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 36)));
/* 171 */       paramArrayOfint[10] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 40)));
/* 172 */       paramArrayOfint[11] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 44)));
/* 173 */       paramArrayOfint[12] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 48)));
/* 174 */       paramArrayOfint[13] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 52)));
/* 175 */       paramArrayOfint[14] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 56)));
/* 176 */       paramArrayOfint[15] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 60)));
/*     */     } else {
/* 178 */       b2iLittle(paramArrayOfbyte, paramInt, paramArrayOfint, 0, 64);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void i2bLittle(int[] paramArrayOfint, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 186 */     if (paramInt1 < 0 || paramArrayOfint.length - paramInt1 < paramInt3 / 4 || paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < paramInt3)
/*     */     {
/* 188 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 190 */     if (littleEndianUnaligned) {
/* 191 */       paramInt2 += byteArrayOfs;
/* 192 */       paramInt3 += paramInt2;
/* 193 */       while (paramInt2 < paramInt3) {
/* 194 */         unsafe.putInt(paramArrayOfbyte, paramInt2, paramArrayOfint[paramInt1++]);
/* 195 */         paramInt2 += 4;
/*     */       } 
/* 197 */     } else if (bigEndian && (paramInt2 & 0x3) == 0) {
/* 198 */       paramInt2 += byteArrayOfs;
/* 199 */       paramInt3 += paramInt2;
/* 200 */       while (paramInt2 < paramInt3) {
/* 201 */         unsafe.putInt(paramArrayOfbyte, paramInt2, Integer.reverseBytes(paramArrayOfint[paramInt1++]));
/* 202 */         paramInt2 += 4;
/*     */       } 
/*     */     } else {
/* 205 */       paramInt3 += paramInt2;
/* 206 */       while (paramInt2 < paramInt3) {
/* 207 */         int i = paramArrayOfint[paramInt1++];
/* 208 */         paramArrayOfbyte[paramInt2++] = (byte)i;
/* 209 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 8);
/* 210 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 16);
/* 211 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 24);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void i2bLittle4(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/* 218 */     if (paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < 4) {
/* 219 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 221 */     if (littleEndianUnaligned) {
/* 222 */       unsafe.putInt(paramArrayOfbyte, (byteArrayOfs + paramInt2), paramInt1);
/* 223 */     } else if (bigEndian && (paramInt2 & 0x3) == 0) {
/* 224 */       unsafe.putInt(paramArrayOfbyte, (byteArrayOfs + paramInt2), Integer.reverseBytes(paramInt1));
/*     */     } else {
/* 226 */       paramArrayOfbyte[paramInt2] = (byte)paramInt1;
/* 227 */       paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 >> 8);
/* 228 */       paramArrayOfbyte[paramInt2 + 2] = (byte)(paramInt1 >> 16);
/* 229 */       paramArrayOfbyte[paramInt2 + 3] = (byte)(paramInt1 >> 24);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void b2iBig(byte[] paramArrayOfbyte, int paramInt1, int[] paramArrayOfint, int paramInt2, int paramInt3) {
/* 237 */     if (paramInt1 < 0 || paramArrayOfbyte.length - paramInt1 < paramInt3 || paramInt2 < 0 || paramArrayOfint.length - paramInt2 < paramInt3 / 4)
/*     */     {
/* 239 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 241 */     if (littleEndianUnaligned) {
/* 242 */       paramInt1 += byteArrayOfs;
/* 243 */       paramInt3 += paramInt1;
/* 244 */       while (paramInt1 < paramInt3) {
/* 245 */         paramArrayOfint[paramInt2++] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, paramInt1));
/* 246 */         paramInt1 += 4;
/*     */       } 
/* 248 */     } else if (bigEndian && (paramInt1 & 0x3) == 0) {
/* 249 */       paramInt1 += byteArrayOfs;
/* 250 */       paramInt3 += paramInt1;
/* 251 */       while (paramInt1 < paramInt3) {
/* 252 */         paramArrayOfint[paramInt2++] = unsafe.getInt(paramArrayOfbyte, paramInt1);
/* 253 */         paramInt1 += 4;
/*     */       } 
/*     */     } else {
/* 256 */       paramInt3 += paramInt1;
/* 257 */       while (paramInt1 < paramInt3) {
/* 258 */         paramArrayOfint[paramInt2++] = paramArrayOfbyte[paramInt1 + 3] & 0xFF | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 8 | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 16 | paramArrayOfbyte[paramInt1] << 24;
/*     */ 
/*     */ 
/*     */         
/* 262 */         paramInt1 += 4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void b2iBig64(byte[] paramArrayOfbyte, int paramInt, int[] paramArrayOfint) {
/* 269 */     if (paramInt < 0 || paramArrayOfbyte.length - paramInt < 64 || paramArrayOfint.length < 16)
/*     */     {
/* 271 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 273 */     if (littleEndianUnaligned) {
/* 274 */       paramInt += byteArrayOfs;
/* 275 */       paramArrayOfint[0] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, paramInt));
/* 276 */       paramArrayOfint[1] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 4)));
/* 277 */       paramArrayOfint[2] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 8)));
/* 278 */       paramArrayOfint[3] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 12)));
/* 279 */       paramArrayOfint[4] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 16)));
/* 280 */       paramArrayOfint[5] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 20)));
/* 281 */       paramArrayOfint[6] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 24)));
/* 282 */       paramArrayOfint[7] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 28)));
/* 283 */       paramArrayOfint[8] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 32)));
/* 284 */       paramArrayOfint[9] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 36)));
/* 285 */       paramArrayOfint[10] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 40)));
/* 286 */       paramArrayOfint[11] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 44)));
/* 287 */       paramArrayOfint[12] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 48)));
/* 288 */       paramArrayOfint[13] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 52)));
/* 289 */       paramArrayOfint[14] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 56)));
/* 290 */       paramArrayOfint[15] = Integer.reverseBytes(unsafe.getInt(paramArrayOfbyte, (paramInt + 60)));
/* 291 */     } else if (bigEndian && (paramInt & 0x3) == 0) {
/* 292 */       paramInt += byteArrayOfs;
/* 293 */       paramArrayOfint[0] = unsafe.getInt(paramArrayOfbyte, paramInt);
/* 294 */       paramArrayOfint[1] = unsafe.getInt(paramArrayOfbyte, (paramInt + 4));
/* 295 */       paramArrayOfint[2] = unsafe.getInt(paramArrayOfbyte, (paramInt + 8));
/* 296 */       paramArrayOfint[3] = unsafe.getInt(paramArrayOfbyte, (paramInt + 12));
/* 297 */       paramArrayOfint[4] = unsafe.getInt(paramArrayOfbyte, (paramInt + 16));
/* 298 */       paramArrayOfint[5] = unsafe.getInt(paramArrayOfbyte, (paramInt + 20));
/* 299 */       paramArrayOfint[6] = unsafe.getInt(paramArrayOfbyte, (paramInt + 24));
/* 300 */       paramArrayOfint[7] = unsafe.getInt(paramArrayOfbyte, (paramInt + 28));
/* 301 */       paramArrayOfint[8] = unsafe.getInt(paramArrayOfbyte, (paramInt + 32));
/* 302 */       paramArrayOfint[9] = unsafe.getInt(paramArrayOfbyte, (paramInt + 36));
/* 303 */       paramArrayOfint[10] = unsafe.getInt(paramArrayOfbyte, (paramInt + 40));
/* 304 */       paramArrayOfint[11] = unsafe.getInt(paramArrayOfbyte, (paramInt + 44));
/* 305 */       paramArrayOfint[12] = unsafe.getInt(paramArrayOfbyte, (paramInt + 48));
/* 306 */       paramArrayOfint[13] = unsafe.getInt(paramArrayOfbyte, (paramInt + 52));
/* 307 */       paramArrayOfint[14] = unsafe.getInt(paramArrayOfbyte, (paramInt + 56));
/* 308 */       paramArrayOfint[15] = unsafe.getInt(paramArrayOfbyte, (paramInt + 60));
/*     */     } else {
/* 310 */       b2iBig(paramArrayOfbyte, paramInt, paramArrayOfint, 0, 64);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void i2bBig(int[] paramArrayOfint, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 318 */     if (paramInt1 < 0 || paramArrayOfint.length - paramInt1 < paramInt3 / 4 || paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < paramInt3)
/*     */     {
/* 320 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 322 */     if (littleEndianUnaligned) {
/* 323 */       paramInt2 += byteArrayOfs;
/* 324 */       paramInt3 += paramInt2;
/* 325 */       while (paramInt2 < paramInt3) {
/* 326 */         unsafe.putInt(paramArrayOfbyte, paramInt2, Integer.reverseBytes(paramArrayOfint[paramInt1++]));
/* 327 */         paramInt2 += 4;
/*     */       } 
/* 329 */     } else if (bigEndian && (paramInt2 & 0x3) == 0) {
/* 330 */       paramInt2 += byteArrayOfs;
/* 331 */       paramInt3 += paramInt2;
/* 332 */       while (paramInt2 < paramInt3) {
/* 333 */         unsafe.putInt(paramArrayOfbyte, paramInt2, paramArrayOfint[paramInt1++]);
/* 334 */         paramInt2 += 4;
/*     */       } 
/*     */     } else {
/* 337 */       paramInt3 += paramInt2;
/* 338 */       while (paramInt2 < paramInt3) {
/* 339 */         int i = paramArrayOfint[paramInt1++];
/* 340 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 24);
/* 341 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 16);
/* 342 */         paramArrayOfbyte[paramInt2++] = (byte)(i >> 8);
/* 343 */         paramArrayOfbyte[paramInt2++] = (byte)i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void i2bBig4(int paramInt1, byte[] paramArrayOfbyte, int paramInt2) {
/* 350 */     if (paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < 4) {
/* 351 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 353 */     if (littleEndianUnaligned) {
/* 354 */       unsafe.putInt(paramArrayOfbyte, (byteArrayOfs + paramInt2), Integer.reverseBytes(paramInt1));
/* 355 */     } else if (bigEndian && (paramInt2 & 0x3) == 0) {
/* 356 */       unsafe.putInt(paramArrayOfbyte, (byteArrayOfs + paramInt2), paramInt1);
/*     */     } else {
/* 358 */       paramArrayOfbyte[paramInt2] = (byte)(paramInt1 >> 24);
/* 359 */       paramArrayOfbyte[paramInt2 + 1] = (byte)(paramInt1 >> 16);
/* 360 */       paramArrayOfbyte[paramInt2 + 2] = (byte)(paramInt1 >> 8);
/* 361 */       paramArrayOfbyte[paramInt2 + 3] = (byte)paramInt1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void b2lBig(byte[] paramArrayOfbyte, int paramInt1, long[] paramArrayOflong, int paramInt2, int paramInt3) {
/* 369 */     if (paramInt1 < 0 || paramArrayOfbyte.length - paramInt1 < paramInt3 || paramInt2 < 0 || paramArrayOflong.length - paramInt2 < paramInt3 / 8)
/*     */     {
/* 371 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 373 */     if (littleEndianUnaligned) {
/* 374 */       paramInt1 += byteArrayOfs;
/* 375 */       paramInt3 += paramInt1;
/* 376 */       while (paramInt1 < paramInt3) {
/* 377 */         paramArrayOflong[paramInt2++] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, paramInt1));
/* 378 */         paramInt1 += 8;
/*     */       } 
/* 380 */     } else if (bigEndian && (paramInt1 & 0x3) == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 386 */       paramInt1 += byteArrayOfs;
/* 387 */       paramInt3 += paramInt1;
/* 388 */       while (paramInt1 < paramInt3) {
/* 389 */         paramArrayOflong[paramInt2++] = unsafe
/* 390 */           .getInt(paramArrayOfbyte, paramInt1) << 32L | unsafe
/* 391 */           .getInt(paramArrayOfbyte, (paramInt1 + 4)) & 0xFFFFFFFFL;
/* 392 */         paramInt1 += 8;
/*     */       } 
/*     */     } else {
/* 395 */       paramInt3 += paramInt1;
/* 396 */       while (paramInt1 < paramInt3) {
/* 397 */         int i = paramArrayOfbyte[paramInt1 + 3] & 0xFF | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 8 | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 16 | paramArrayOfbyte[paramInt1] << 24;
/*     */ 
/*     */ 
/*     */         
/* 401 */         paramInt1 += 4;
/* 402 */         int j = paramArrayOfbyte[paramInt1 + 3] & 0xFF | (paramArrayOfbyte[paramInt1 + 2] & 0xFF) << 8 | (paramArrayOfbyte[paramInt1 + 1] & 0xFF) << 16 | paramArrayOfbyte[paramInt1] << 24;
/*     */ 
/*     */ 
/*     */         
/* 406 */         paramArrayOflong[paramInt2++] = i << 32L | j & 0xFFFFFFFFL;
/* 407 */         paramInt1 += 4;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void b2lBig128(byte[] paramArrayOfbyte, int paramInt, long[] paramArrayOflong) {
/* 414 */     if (paramInt < 0 || paramArrayOfbyte.length - paramInt < 128 || paramArrayOflong.length < 16)
/*     */     {
/* 416 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 418 */     if (littleEndianUnaligned) {
/* 419 */       paramInt += byteArrayOfs;
/* 420 */       paramArrayOflong[0] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, paramInt));
/* 421 */       paramArrayOflong[1] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 8)));
/* 422 */       paramArrayOflong[2] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 16)));
/* 423 */       paramArrayOflong[3] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 24)));
/* 424 */       paramArrayOflong[4] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 32)));
/* 425 */       paramArrayOflong[5] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 40)));
/* 426 */       paramArrayOflong[6] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 48)));
/* 427 */       paramArrayOflong[7] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 56)));
/* 428 */       paramArrayOflong[8] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 64)));
/* 429 */       paramArrayOflong[9] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 72)));
/* 430 */       paramArrayOflong[10] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 80)));
/* 431 */       paramArrayOflong[11] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 88)));
/* 432 */       paramArrayOflong[12] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 96)));
/* 433 */       paramArrayOflong[13] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 104)));
/* 434 */       paramArrayOflong[14] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 112)));
/* 435 */       paramArrayOflong[15] = Long.reverseBytes(unsafe.getLong(paramArrayOfbyte, (paramInt + 120)));
/*     */     } else {
/*     */       
/* 438 */       b2lBig(paramArrayOfbyte, paramInt, paramArrayOflong, 0, 128);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void l2bBig(long[] paramArrayOflong, int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3) {
/* 446 */     if (paramInt1 < 0 || paramArrayOflong.length - paramInt1 < paramInt3 / 8 || paramInt2 < 0 || paramArrayOfbyte.length - paramInt2 < paramInt3)
/*     */     {
/* 448 */       throw new ArrayIndexOutOfBoundsException();
/*     */     }
/* 450 */     paramInt3 += paramInt2;
/* 451 */     while (paramInt2 < paramInt3) {
/* 452 */       long l = paramArrayOflong[paramInt1++];
/* 453 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 56L);
/* 454 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 48L);
/* 455 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 40L);
/* 456 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 32L);
/* 457 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 24L);
/* 458 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 16L);
/* 459 */       paramArrayOfbyte[paramInt2++] = (byte)(int)(l >> 8L);
/* 460 */       paramArrayOfbyte[paramInt2++] = (byte)(int)l;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/security/provider/ByteArrayAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */