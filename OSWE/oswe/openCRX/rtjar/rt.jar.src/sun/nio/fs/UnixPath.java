/*     */ package sun.nio.fs;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.net.URI;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.CoderResult;
/*     */ import java.nio.charset.CodingErrorAction;
/*     */ import java.nio.file.FileSystem;
/*     */ import java.nio.file.FileSystemException;
/*     */ import java.nio.file.InvalidPathException;
/*     */ import java.nio.file.LinkOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.ProviderMismatchException;
/*     */ import java.nio.file.WatchEvent;
/*     */ import java.nio.file.WatchKey;
/*     */ import java.nio.file.WatchService;
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
/*     */ class UnixPath
/*     */   extends AbstractPath
/*     */ {
/*  46 */   private static ThreadLocal<SoftReference<CharsetEncoder>> encoder = new ThreadLocal<>();
/*     */ 
/*     */   
/*     */   private final UnixFileSystem fs;
/*     */ 
/*     */   
/*     */   private final byte[] path;
/*     */ 
/*     */   
/*     */   private volatile String stringValue;
/*     */ 
/*     */   
/*     */   private int hash;
/*     */ 
/*     */   
/*     */   private volatile int[] offsets;
/*     */ 
/*     */   
/*     */   UnixPath(UnixFileSystem paramUnixFileSystem, byte[] paramArrayOfbyte) {
/*  65 */     this.fs = paramUnixFileSystem;
/*  66 */     this.path = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */   
/*     */   UnixPath(UnixFileSystem paramUnixFileSystem, String paramString) {
/*  71 */     this(paramUnixFileSystem, encode(paramUnixFileSystem, normalizeAndCheck(paramString)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String normalizeAndCheck(String paramString) {
/*  77 */     int i = paramString.length();
/*  78 */     char c = Character.MIN_VALUE;
/*  79 */     for (byte b = 0; b < i; b++) {
/*  80 */       char c1 = paramString.charAt(b);
/*  81 */       if (c1 == '/' && c == '/')
/*  82 */         return normalize(paramString, i, b - 1); 
/*  83 */       checkNotNul(paramString, c1);
/*  84 */       c = c1;
/*     */     } 
/*  86 */     if (c == '/')
/*  87 */       return normalize(paramString, i, i - 1); 
/*  88 */     return paramString;
/*     */   }
/*     */   
/*     */   private static void checkNotNul(String paramString, char paramChar) {
/*  92 */     if (paramChar == '\000')
/*  93 */       throw new InvalidPathException(paramString, "Nul character not allowed"); 
/*     */   }
/*     */   
/*     */   private static String normalize(String paramString, int paramInt1, int paramInt2) {
/*  97 */     if (paramInt1 == 0)
/*  98 */       return paramString; 
/*  99 */     int i = paramInt1;
/* 100 */     for (; i > 0 && paramString.charAt(i - 1) == '/'; i--);
/* 101 */     if (i == 0)
/* 102 */       return "/"; 
/* 103 */     StringBuilder stringBuilder = new StringBuilder(paramString.length());
/* 104 */     if (paramInt2 > 0)
/* 105 */       stringBuilder.append(paramString.substring(0, paramInt2)); 
/* 106 */     char c = Character.MIN_VALUE;
/* 107 */     for (int j = paramInt2; j < i; j++) {
/* 108 */       char c1 = paramString.charAt(j);
/* 109 */       if (c1 != '/' || c != '/') {
/*     */         
/* 111 */         checkNotNul(paramString, c1);
/* 112 */         stringBuilder.append(c1);
/* 113 */         c = c1;
/*     */       } 
/* 115 */     }  return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static byte[] encode(UnixFileSystem paramUnixFileSystem, String paramString) {
/*     */     boolean bool;
/* 120 */     SoftReference<CharsetEncoder> softReference = encoder.get();
/* 121 */     CharsetEncoder charsetEncoder = (softReference != null) ? softReference.get() : null;
/* 122 */     if (charsetEncoder == null) {
/*     */ 
/*     */       
/* 125 */       charsetEncoder = Util.jnuEncoding().newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
/* 126 */       encoder.set(new SoftReference<>(charsetEncoder));
/*     */     } 
/*     */     
/* 129 */     char[] arrayOfChar = paramUnixFileSystem.normalizeNativePath(paramString.toCharArray());
/*     */ 
/*     */     
/* 132 */     byte[] arrayOfByte = new byte[(int)(arrayOfChar.length * charsetEncoder.maxBytesPerChar())];
/*     */ 
/*     */     
/* 135 */     ByteBuffer byteBuffer = ByteBuffer.wrap(arrayOfByte);
/* 136 */     CharBuffer charBuffer = CharBuffer.wrap(arrayOfChar);
/* 137 */     charsetEncoder.reset();
/* 138 */     CoderResult coderResult = charsetEncoder.encode(charBuffer, byteBuffer, true);
/*     */     
/* 140 */     if (!coderResult.isUnderflow()) {
/* 141 */       bool = true;
/*     */     } else {
/* 143 */       coderResult = charsetEncoder.flush(byteBuffer);
/* 144 */       bool = !coderResult.isUnderflow() ? true : false;
/*     */     } 
/* 146 */     if (bool) {
/* 147 */       throw new InvalidPathException(paramString, "Malformed input or input contains unmappable characters");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 152 */     int i = byteBuffer.position();
/* 153 */     if (i != arrayOfByte.length) {
/* 154 */       arrayOfByte = Arrays.copyOf(arrayOfByte, i);
/*     */     }
/* 156 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   byte[] asByteArray() {
/* 161 */     return this.path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getByteArrayForSysCalls() {
/* 168 */     if (getFileSystem().needToResolveAgainstDefaultDirectory()) {
/* 169 */       return resolve(getFileSystem().defaultDirectory(), this.path);
/*     */     }
/* 171 */     if (!isEmpty()) {
/* 172 */       return this.path;
/*     */     }
/*     */     
/* 175 */     return new byte[] { 46 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String getPathForExceptionMessage() {
/* 183 */     return toString();
/*     */   }
/*     */ 
/*     */   
/*     */   String getPathForPermissionCheck() {
/* 188 */     if (getFileSystem().needToResolveAgainstDefaultDirectory()) {
/* 189 */       return Util.toString(getByteArrayForSysCalls());
/*     */     }
/* 191 */     return toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static UnixPath toUnixPath(Path paramPath) {
/* 197 */     if (paramPath == null)
/* 198 */       throw new NullPointerException(); 
/* 199 */     if (!(paramPath instanceof UnixPath))
/* 200 */       throw new ProviderMismatchException(); 
/* 201 */     return (UnixPath)paramPath;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initOffsets() {
/* 206 */     if (this.offsets == null) {
/*     */ 
/*     */ 
/*     */       
/* 210 */       byte b1 = 0;
/* 211 */       byte b2 = 0;
/* 212 */       if (isEmpty()) {
/*     */         
/* 214 */         b1 = 1;
/*     */       } else {
/* 216 */         while (b2 < this.path.length) {
/* 217 */           byte b = this.path[b2++];
/* 218 */           if (b != 47) {
/* 219 */             b1++;
/* 220 */             while (b2 < this.path.length && this.path[b2] != 47) {
/* 221 */               b2++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 227 */       int[] arrayOfInt = new int[b1];
/* 228 */       b1 = 0;
/* 229 */       b2 = 0;
/* 230 */       while (b2 < this.path.length) {
/* 231 */         byte b = this.path[b2];
/* 232 */         if (b == 47) {
/* 233 */           b2++; continue;
/*     */         } 
/* 235 */         arrayOfInt[b1++] = b2++;
/* 236 */         while (b2 < this.path.length && this.path[b2] != 47) {
/* 237 */           b2++;
/*     */         }
/*     */       } 
/* 240 */       synchronized (this) {
/* 241 */         if (this.offsets == null) {
/* 242 */           this.offsets = arrayOfInt;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isEmpty() {
/* 249 */     return (this.path.length == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private UnixPath emptyPath() {
/* 254 */     return new UnixPath(getFileSystem(), new byte[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public UnixFileSystem getFileSystem() {
/* 259 */     return this.fs;
/*     */   }
/*     */ 
/*     */   
/*     */   public UnixPath getRoot() {
/* 264 */     if (this.path.length > 0 && this.path[0] == 47) {
/* 265 */       return getFileSystem().rootDirectory();
/*     */     }
/* 267 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public UnixPath getFileName() {
/* 273 */     initOffsets();
/*     */     
/* 275 */     int i = this.offsets.length;
/*     */ 
/*     */     
/* 278 */     if (i == 0) {
/* 279 */       return null;
/*     */     }
/*     */     
/* 282 */     if (i == 1 && this.path.length > 0 && this.path[0] != 47) {
/* 283 */       return this;
/*     */     }
/* 285 */     int j = this.offsets[i - 1];
/* 286 */     int k = this.path.length - j;
/* 287 */     byte[] arrayOfByte = new byte[k];
/* 288 */     System.arraycopy(this.path, j, arrayOfByte, 0, k);
/* 289 */     return new UnixPath(getFileSystem(), arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   public UnixPath getParent() {
/* 294 */     initOffsets();
/*     */     
/* 296 */     int i = this.offsets.length;
/* 297 */     if (i == 0)
/*     */     {
/* 299 */       return null;
/*     */     }
/* 301 */     int j = this.offsets[i - 1] - 1;
/* 302 */     if (j <= 0)
/*     */     {
/* 304 */       return getRoot();
/*     */     }
/* 306 */     byte[] arrayOfByte = new byte[j];
/* 307 */     System.arraycopy(this.path, 0, arrayOfByte, 0, j);
/* 308 */     return new UnixPath(getFileSystem(), arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNameCount() {
/* 313 */     initOffsets();
/* 314 */     return this.offsets.length;
/*     */   }
/*     */   
/*     */   public UnixPath getName(int paramInt) {
/*     */     int j;
/* 319 */     initOffsets();
/* 320 */     if (paramInt < 0)
/* 321 */       throw new IllegalArgumentException(); 
/* 322 */     if (paramInt >= this.offsets.length) {
/* 323 */       throw new IllegalArgumentException();
/*     */     }
/* 325 */     int i = this.offsets[paramInt];
/*     */     
/* 327 */     if (paramInt == this.offsets.length - 1) {
/* 328 */       j = this.path.length - i;
/*     */     } else {
/* 330 */       j = this.offsets[paramInt + 1] - i - 1;
/*     */     } 
/*     */ 
/*     */     
/* 334 */     byte[] arrayOfByte = new byte[j];
/* 335 */     System.arraycopy(this.path, i, arrayOfByte, 0, j);
/* 336 */     return new UnixPath(getFileSystem(), arrayOfByte);
/*     */   }
/*     */   
/*     */   public UnixPath subpath(int paramInt1, int paramInt2) {
/*     */     int j;
/* 341 */     initOffsets();
/*     */     
/* 343 */     if (paramInt1 < 0)
/* 344 */       throw new IllegalArgumentException(); 
/* 345 */     if (paramInt1 >= this.offsets.length)
/* 346 */       throw new IllegalArgumentException(); 
/* 347 */     if (paramInt2 > this.offsets.length)
/* 348 */       throw new IllegalArgumentException(); 
/* 349 */     if (paramInt1 >= paramInt2) {
/* 350 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */     
/* 354 */     int i = this.offsets[paramInt1];
/*     */     
/* 356 */     if (paramInt2 == this.offsets.length) {
/* 357 */       j = this.path.length - i;
/*     */     } else {
/* 359 */       j = this.offsets[paramInt2] - i - 1;
/*     */     } 
/*     */ 
/*     */     
/* 363 */     byte[] arrayOfByte = new byte[j];
/* 364 */     System.arraycopy(this.path, i, arrayOfByte, 0, j);
/* 365 */     return new UnixPath(getFileSystem(), arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAbsolute() {
/* 370 */     return (this.path.length > 0 && this.path[0] == 47);
/*     */   }
/*     */   
/*     */   private static byte[] resolve(byte[] paramArrayOfbyte1, byte[] paramArrayOfbyte2) {
/*     */     byte[] arrayOfByte;
/* 375 */     int i = paramArrayOfbyte1.length;
/* 376 */     int j = paramArrayOfbyte2.length;
/* 377 */     if (j == 0)
/* 378 */       return paramArrayOfbyte1; 
/* 379 */     if (i == 0 || paramArrayOfbyte2[0] == 47) {
/* 380 */       return paramArrayOfbyte2;
/*     */     }
/* 382 */     if (i == 1 && paramArrayOfbyte1[0] == 47) {
/* 383 */       arrayOfByte = new byte[j + 1];
/* 384 */       arrayOfByte[0] = 47;
/* 385 */       System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, 1, j);
/*     */     } else {
/* 387 */       arrayOfByte = new byte[i + 1 + j];
/* 388 */       System.arraycopy(paramArrayOfbyte1, 0, arrayOfByte, 0, i);
/* 389 */       arrayOfByte[paramArrayOfbyte1.length] = 47;
/* 390 */       System.arraycopy(paramArrayOfbyte2, 0, arrayOfByte, i + 1, j);
/*     */     } 
/* 392 */     return arrayOfByte;
/*     */   }
/*     */ 
/*     */   
/*     */   public UnixPath resolve(Path paramPath) {
/* 397 */     byte[] arrayOfByte1 = (toUnixPath(paramPath)).path;
/* 398 */     if (arrayOfByte1.length > 0 && arrayOfByte1[0] == 47)
/* 399 */       return (UnixPath)paramPath; 
/* 400 */     byte[] arrayOfByte2 = resolve(this.path, arrayOfByte1);
/* 401 */     return new UnixPath(getFileSystem(), arrayOfByte2);
/*     */   }
/*     */   
/*     */   UnixPath resolve(byte[] paramArrayOfbyte) {
/* 405 */     return resolve(new UnixPath(getFileSystem(), paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public UnixPath relativize(Path paramPath) {
/* 410 */     UnixPath unixPath = toUnixPath(paramPath);
/* 411 */     if (unixPath.equals(this)) {
/* 412 */       return emptyPath();
/*     */     }
/*     */     
/* 415 */     if (isAbsolute() != unixPath.isAbsolute()) {
/* 416 */       throw new IllegalArgumentException("'other' is different type of Path");
/*     */     }
/*     */     
/* 419 */     if (isEmpty()) {
/* 420 */       return unixPath;
/*     */     }
/* 422 */     int i = getNameCount();
/* 423 */     int j = unixPath.getNameCount();
/*     */ 
/*     */     
/* 426 */     int k = (i > j) ? j : i;
/* 427 */     byte b1 = 0;
/* 428 */     while (b1 < k && 
/* 429 */       getName(b1).equals(unixPath.getName(b1)))
/*     */     {
/* 431 */       b1++;
/*     */     }
/*     */     
/* 434 */     int m = i - b1;
/* 435 */     if (b1 < j) {
/*     */       
/* 437 */       UnixPath unixPath1 = unixPath.subpath(b1, j);
/* 438 */       if (m == 0) {
/* 439 */         return unixPath1;
/*     */       }
/*     */       
/* 442 */       boolean bool = unixPath.isEmpty();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 447 */       int n = m * 3 + unixPath1.path.length;
/* 448 */       if (bool) {
/* 449 */         assert unixPath1.isEmpty();
/* 450 */         n--;
/*     */       } 
/* 452 */       byte[] arrayOfByte1 = new byte[n];
/* 453 */       byte b = 0;
/* 454 */       while (m > 0) {
/* 455 */         arrayOfByte1[b++] = 46;
/* 456 */         arrayOfByte1[b++] = 46;
/* 457 */         if (bool) {
/* 458 */           if (m > 1) arrayOfByte1[b++] = 47; 
/*     */         } else {
/* 460 */           arrayOfByte1[b++] = 47;
/*     */         } 
/* 462 */         m--;
/*     */       } 
/* 464 */       System.arraycopy(unixPath1.path, 0, arrayOfByte1, b, unixPath1.path.length);
/* 465 */       return new UnixPath(getFileSystem(), arrayOfByte1);
/*     */     } 
/*     */     
/* 468 */     byte[] arrayOfByte = new byte[m * 3 - 1];
/* 469 */     byte b2 = 0;
/* 470 */     while (m > 0) {
/* 471 */       arrayOfByte[b2++] = 46;
/* 472 */       arrayOfByte[b2++] = 46;
/*     */       
/* 474 */       if (m > 1)
/* 475 */         arrayOfByte[b2++] = 47; 
/* 476 */       m--;
/*     */     } 
/* 478 */     return new UnixPath(getFileSystem(), arrayOfByte);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Path normalize() {
/* 484 */     int i = getNameCount();
/* 485 */     if (i == 0 || isEmpty()) {
/* 486 */       return this;
/*     */     }
/* 488 */     boolean[] arrayOfBoolean = new boolean[i];
/* 489 */     int[] arrayOfInt = new int[i];
/* 490 */     int j = i;
/* 491 */     boolean bool = false;
/* 492 */     boolean bool1 = isAbsolute();
/*     */ 
/*     */     
/*     */     int k;
/*     */ 
/*     */     
/* 498 */     for (k = 0; k < i; k++) {
/* 499 */       int i1, n = this.offsets[k];
/*     */       
/* 501 */       if (k == this.offsets.length - 1) {
/* 502 */         i1 = this.path.length - n;
/*     */       } else {
/* 504 */         i1 = this.offsets[k + 1] - n - 1;
/*     */       } 
/* 506 */       arrayOfInt[k] = i1;
/*     */       
/* 508 */       if (this.path[n] == 46) {
/* 509 */         if (i1 == 1) {
/* 510 */           arrayOfBoolean[k] = true;
/* 511 */           j--;
/*     */         
/*     */         }
/* 514 */         else if (this.path[n + 1] == 46) {
/* 515 */           bool = true;
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 521 */     if (bool) {
/*     */       
/*     */       do {
/* 524 */         k = j;
/* 525 */         byte b = -1;
/* 526 */         for (byte b3 = 0; b3 < i; b3++) {
/* 527 */           if (!arrayOfBoolean[b3])
/*     */           {
/*     */ 
/*     */             
/* 531 */             if (arrayOfInt[b3] != 2) {
/* 532 */               b = b3;
/*     */             }
/*     */             else {
/*     */               
/* 536 */               int n = this.offsets[b3];
/* 537 */               if (this.path[n] != 46 || this.path[n + 1] != 46) {
/* 538 */                 b = b3;
/*     */ 
/*     */ 
/*     */               
/*     */               }
/* 543 */               else if (b >= 0) {
/*     */ 
/*     */                 
/* 546 */                 arrayOfBoolean[b] = true;
/* 547 */                 arrayOfBoolean[b3] = true;
/* 548 */                 j -= 2;
/* 549 */                 b = -1;
/*     */               
/*     */               }
/* 552 */               else if (bool1) {
/* 553 */                 boolean bool2 = false;
/* 554 */                 for (byte b4 = 0; b4 < b3; b4++) {
/* 555 */                   if (!arrayOfBoolean[b4]) {
/* 556 */                     bool2 = true;
/*     */                     break;
/*     */                   } 
/*     */                 } 
/* 560 */                 if (!bool2) {
/*     */                   
/* 562 */                   arrayOfBoolean[b3] = true;
/* 563 */                   j--;
/*     */                 } 
/*     */               } 
/*     */             }  } 
/*     */         } 
/* 568 */       } while (k > j);
/*     */     }
/*     */ 
/*     */     
/* 572 */     if (j == i) {
/* 573 */       return this;
/*     */     }
/*     */     
/* 576 */     if (j == 0) {
/* 577 */       return bool1 ? getFileSystem().rootDirectory() : emptyPath();
/*     */     }
/*     */ 
/*     */     
/* 581 */     k = j - 1;
/* 582 */     if (bool1) {
/* 583 */       k++;
/*     */     }
/* 585 */     for (byte b1 = 0; b1 < i; b1++) {
/* 586 */       if (!arrayOfBoolean[b1])
/* 587 */         k += arrayOfInt[b1]; 
/*     */     } 
/* 589 */     byte[] arrayOfByte = new byte[k];
/*     */ 
/*     */     
/* 592 */     int m = 0;
/* 593 */     if (bool1)
/* 594 */       arrayOfByte[m++] = 47; 
/* 595 */     for (byte b2 = 0; b2 < i; b2++) {
/* 596 */       if (!arrayOfBoolean[b2]) {
/* 597 */         System.arraycopy(this.path, this.offsets[b2], arrayOfByte, m, arrayOfInt[b2]);
/* 598 */         m += arrayOfInt[b2];
/* 599 */         if (--j > 0) {
/* 600 */           arrayOfByte[m++] = 47;
/*     */         }
/*     */       } 
/*     */     } 
/* 604 */     return new UnixPath(getFileSystem(), arrayOfByte);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean startsWith(Path paramPath) {
/* 609 */     if (!(Objects.requireNonNull((T)paramPath) instanceof UnixPath))
/* 610 */       return false; 
/* 611 */     UnixPath unixPath = (UnixPath)paramPath;
/*     */ 
/*     */     
/* 614 */     if (unixPath.path.length > this.path.length) {
/* 615 */       return false;
/*     */     }
/* 617 */     int i = getNameCount();
/* 618 */     int j = unixPath.getNameCount();
/*     */ 
/*     */     
/* 621 */     if (j == 0 && isAbsolute()) {
/* 622 */       return !unixPath.isEmpty();
/*     */     }
/*     */ 
/*     */     
/* 626 */     if (j > i) {
/* 627 */       return false;
/*     */     }
/*     */     
/* 630 */     if (j == i && this.path.length != unixPath.path.length)
/*     */     {
/* 632 */       return false;
/*     */     }
/*     */     
/*     */     byte b;
/* 636 */     for (b = 0; b < j; b++) {
/* 637 */       Integer integer1 = Integer.valueOf(this.offsets[b]);
/* 638 */       Integer integer2 = Integer.valueOf(unixPath.offsets[b]);
/* 639 */       if (!integer1.equals(integer2)) {
/* 640 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 644 */     b = 0;
/* 645 */     while (b < unixPath.path.length) {
/* 646 */       if (this.path[b] != unixPath.path[b])
/* 647 */         return false; 
/* 648 */       b++;
/*     */     } 
/*     */ 
/*     */     
/* 652 */     if (b < this.path.length && this.path[b] != 47) {
/* 653 */       return false;
/*     */     }
/* 655 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean endsWith(Path paramPath) {
/* 660 */     if (!(Objects.requireNonNull((T)paramPath) instanceof UnixPath))
/* 661 */       return false; 
/* 662 */     UnixPath unixPath = (UnixPath)paramPath;
/*     */     
/* 664 */     int i = this.path.length;
/* 665 */     int j = unixPath.path.length;
/*     */ 
/*     */     
/* 668 */     if (j > i) {
/* 669 */       return false;
/*     */     }
/*     */     
/* 672 */     if (i > 0 && j == 0) {
/* 673 */       return false;
/*     */     }
/*     */     
/* 676 */     if (unixPath.isAbsolute() && !isAbsolute()) {
/* 677 */       return false;
/*     */     }
/* 679 */     int k = getNameCount();
/* 680 */     int m = unixPath.getNameCount();
/*     */ 
/*     */     
/* 683 */     if (m > k) {
/* 684 */       return false;
/*     */     }
/*     */     
/* 687 */     if (m == k) {
/* 688 */       if (k == 0)
/* 689 */         return true; 
/* 690 */       int i2 = i;
/* 691 */       if (isAbsolute() && !unixPath.isAbsolute())
/* 692 */         i2--; 
/* 693 */       if (j != i2) {
/* 694 */         return false;
/*     */       }
/*     */     }
/* 697 */     else if (unixPath.isAbsolute()) {
/* 698 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 703 */     int n = this.offsets[k - m];
/* 704 */     int i1 = unixPath.offsets[0];
/* 705 */     if (j - i1 != i - n)
/* 706 */       return false; 
/* 707 */     while (i1 < j) {
/* 708 */       if (this.path[n++] != unixPath.path[i1++]) {
/* 709 */         return false;
/*     */       }
/*     */     } 
/* 712 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Path paramPath) {
/* 717 */     int i = this.path.length;
/* 718 */     int j = ((UnixPath)paramPath).path.length;
/*     */     
/* 720 */     int k = Math.min(i, j);
/* 721 */     byte[] arrayOfByte1 = this.path;
/* 722 */     byte[] arrayOfByte2 = ((UnixPath)paramPath).path;
/*     */     
/* 724 */     byte b = 0;
/* 725 */     while (b < k) {
/* 726 */       int m = arrayOfByte1[b] & 0xFF;
/* 727 */       int n = arrayOfByte2[b] & 0xFF;
/* 728 */       if (m != n) {
/* 729 */         return m - n;
/*     */       }
/* 731 */       b++;
/*     */     } 
/* 733 */     return i - j;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/* 738 */     if (paramObject != null && paramObject instanceof UnixPath) {
/* 739 */       return (compareTo((Path)paramObject) == 0);
/*     */     }
/* 741 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 747 */     int i = this.hash;
/* 748 */     if (i == 0) {
/* 749 */       for (byte b = 0; b < this.path.length; b++) {
/* 750 */         i = 31 * i + (this.path[b] & 0xFF);
/*     */       }
/* 752 */       this.hash = i;
/*     */     } 
/* 754 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 760 */     if (this.stringValue == null) {
/* 761 */       this.stringValue = this.fs.normalizeJavaPath(Util.toString(this.path));
/*     */     }
/* 763 */     return this.stringValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int openForAttributeAccess(boolean paramBoolean) throws IOException {
/* 770 */     int i = 0;
/* 771 */     if (!paramBoolean)
/*     */     {
/*     */       
/* 774 */       i |= 0x20000;
/*     */     }
/*     */     try {
/* 777 */       return UnixNativeDispatcher.open(this, i, 0);
/* 778 */     } catch (UnixException unixException) {
/*     */       
/* 780 */       if (getFileSystem().isSolaris() && unixException.errno() == 22) {
/* 781 */         unixException.setError(40);
/*     */       }
/* 783 */       if (unixException.errno() == 40) {
/* 784 */         throw new FileSystemException(getPathForExceptionMessage(), null, unixException
/* 785 */             .getMessage() + " or unable to access attributes of symbolic link");
/*     */       }
/* 787 */       unixException.rethrowAsIOException(this);
/* 788 */       return -1;
/*     */     } 
/*     */   }
/*     */   
/*     */   void checkRead() {
/* 793 */     SecurityManager securityManager = System.getSecurityManager();
/* 794 */     if (securityManager != null)
/* 795 */       securityManager.checkRead(getPathForPermissionCheck()); 
/*     */   }
/*     */   
/*     */   void checkWrite() {
/* 799 */     SecurityManager securityManager = System.getSecurityManager();
/* 800 */     if (securityManager != null)
/* 801 */       securityManager.checkWrite(getPathForPermissionCheck()); 
/*     */   }
/*     */   
/*     */   void checkDelete() {
/* 805 */     SecurityManager securityManager = System.getSecurityManager();
/* 806 */     if (securityManager != null) {
/* 807 */       securityManager.checkDelete(getPathForPermissionCheck());
/*     */     }
/*     */   }
/*     */   
/*     */   public UnixPath toAbsolutePath() {
/* 812 */     if (isAbsolute()) {
/* 813 */       return this;
/*     */     }
/*     */ 
/*     */     
/* 817 */     SecurityManager securityManager = System.getSecurityManager();
/* 818 */     if (securityManager != null) {
/* 819 */       securityManager.checkPropertyAccess("user.dir");
/*     */     }
/* 821 */     return new UnixPath(getFileSystem(), 
/* 822 */         resolve(getFileSystem().defaultDirectory(), this.path));
/*     */   }
/*     */ 
/*     */   
/*     */   public Path toRealPath(LinkOption... paramVarArgs) throws IOException {
/* 827 */     checkRead();
/*     */     
/* 829 */     UnixPath unixPath1 = toAbsolutePath();
/*     */ 
/*     */     
/* 832 */     if (Util.followLinks(paramVarArgs)) {
/*     */       try {
/* 834 */         byte[] arrayOfByte = UnixNativeDispatcher.realpath(unixPath1);
/* 835 */         return new UnixPath(getFileSystem(), arrayOfByte);
/* 836 */       } catch (UnixException unixException) {
/* 837 */         unixException.rethrowAsIOException(this);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 843 */     UnixPath unixPath2 = this.fs.rootDirectory();
/* 844 */     for (byte b = 0; b < unixPath1.getNameCount(); b++) {
/* 845 */       UnixPath unixPath = unixPath1.getName(b);
/*     */ 
/*     */       
/* 848 */       if ((unixPath.asByteArray()).length == 1 && unixPath.asByteArray()[0] == 46) {
/*     */         continue;
/*     */       }
/*     */       
/* 852 */       if ((unixPath.asByteArray()).length == 2 && unixPath.asByteArray()[0] == 46 && unixPath
/* 853 */         .asByteArray()[1] == 46) {
/*     */         
/* 855 */         UnixFileAttributes unixFileAttributes = null;
/*     */         try {
/* 857 */           unixFileAttributes = UnixFileAttributes.get(unixPath2, false);
/* 858 */         } catch (UnixException unixException) {
/* 859 */           unixException.rethrowAsIOException(unixPath2);
/*     */         } 
/* 861 */         if (!unixFileAttributes.isSymbolicLink()) {
/* 862 */           unixPath2 = unixPath2.getParent();
/* 863 */           if (unixPath2 == null) {
/* 864 */             unixPath2 = this.fs.rootDirectory();
/*     */           }
/*     */           continue;
/*     */         } 
/*     */       } 
/* 869 */       unixPath2 = unixPath2.resolve(unixPath);
/*     */       
/*     */       continue;
/*     */     } 
/*     */     try {
/* 874 */       UnixFileAttributes.get(unixPath2, false);
/* 875 */     } catch (UnixException unixException) {
/* 876 */       unixException.rethrowAsIOException(unixPath2);
/*     */     } 
/* 878 */     return unixPath2;
/*     */   }
/*     */ 
/*     */   
/*     */   public URI toUri() {
/* 883 */     return UnixUriUtils.toUri(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WatchKey register(WatchService paramWatchService, WatchEvent.Kind<?>[] paramArrayOfKind, WatchEvent.Modifier... paramVarArgs) throws IOException {
/* 892 */     if (paramWatchService == null)
/* 893 */       throw new NullPointerException(); 
/* 894 */     if (!(paramWatchService instanceof AbstractWatchService))
/* 895 */       throw new ProviderMismatchException(); 
/* 896 */     checkRead();
/* 897 */     return ((AbstractWatchService)paramWatchService).register(this, paramArrayOfKind, paramVarArgs);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/fs/UnixPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */