/*     */ package java.util.zip;
/*     */ 
/*     */ import java.nio.file.attribute.FileTime;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZipEntry
/*     */   implements ZipConstants, Cloneable
/*     */ {
/*     */   String name;
/*  44 */   long xdostime = -1L;
/*     */   
/*     */   FileTime mtime;
/*     */   
/*     */   FileTime atime;
/*     */   FileTime ctime;
/*  50 */   long crc = -1L;
/*  51 */   long size = -1L;
/*  52 */   long csize = -1L;
/*  53 */   int method = -1;
/*  54 */   int flag = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] extra;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   String comment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int STORED = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int DEFLATED = 8;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final long DOSTIME_BEFORE_1980 = 2162688L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final long UPPER_DOSTIME_BOUND = 4036608000000L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZipEntry(String paramString) {
/* 101 */     Objects.requireNonNull(paramString, "name");
/* 102 */     if (paramString.length() > 65535) {
/* 103 */       throw new IllegalArgumentException("entry name too long");
/*     */     }
/* 105 */     this.name = paramString;
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
/*     */   public ZipEntry(ZipEntry paramZipEntry) {
/* 118 */     Objects.requireNonNull(paramZipEntry, "entry");
/* 119 */     this.name = paramZipEntry.name;
/* 120 */     this.xdostime = paramZipEntry.xdostime;
/* 121 */     this.mtime = paramZipEntry.mtime;
/* 122 */     this.atime = paramZipEntry.atime;
/* 123 */     this.ctime = paramZipEntry.ctime;
/* 124 */     this.crc = paramZipEntry.crc;
/* 125 */     this.size = paramZipEntry.size;
/* 126 */     this.csize = paramZipEntry.csize;
/* 127 */     this.method = paramZipEntry.method;
/* 128 */     this.flag = paramZipEntry.flag;
/* 129 */     this.extra = paramZipEntry.extra;
/* 130 */     this.comment = paramZipEntry.comment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ZipEntry() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 143 */     return this.name;
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
/*     */   public void setTime(long paramLong) {
/* 164 */     this.xdostime = ZipUtils.javaToExtendedDosTime(paramLong);
/*     */ 
/*     */     
/* 167 */     if (this.xdostime != 2162688L && paramLong <= 4036608000000L) {
/* 168 */       this.mtime = null;
/*     */     } else {
/* 170 */       this.mtime = FileTime.from(paramLong, TimeUnit.MILLISECONDS);
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
/*     */   public long getTime() {
/* 191 */     if (this.mtime != null) {
/* 192 */       return this.mtime.toMillis();
/*     */     }
/* 194 */     return (this.xdostime != -1L) ? ZipUtils.extendedDosToJavaTime(this.xdostime) : -1L;
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
/*     */   public ZipEntry setLastModifiedTime(FileTime paramFileTime) {
/* 216 */     this.mtime = Objects.<FileTime>requireNonNull(paramFileTime, "lastModifiedTime");
/* 217 */     this.xdostime = ZipUtils.javaToExtendedDosTime(paramFileTime.to(TimeUnit.MILLISECONDS));
/* 218 */     return this;
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
/*     */   public FileTime getLastModifiedTime() {
/* 238 */     if (this.mtime != null)
/* 239 */       return this.mtime; 
/* 240 */     if (this.xdostime == -1L)
/* 241 */       return null; 
/* 242 */     return FileTime.from(getTime(), TimeUnit.MILLISECONDS);
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
/*     */   public ZipEntry setLastAccessTime(FileTime paramFileTime) {
/* 262 */     this.atime = Objects.<FileTime>requireNonNull(paramFileTime, "lastAccessTime");
/* 263 */     return this;
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
/*     */   public FileTime getLastAccessTime() {
/* 279 */     return this.atime;
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
/*     */   public ZipEntry setCreationTime(FileTime paramFileTime) {
/* 299 */     this.ctime = Objects.<FileTime>requireNonNull(paramFileTime, "creationTime");
/* 300 */     return this;
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
/*     */   public FileTime getCreationTime() {
/* 315 */     return this.ctime;
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
/*     */   public void setSize(long paramLong) {
/* 330 */     if (paramLong < 0L) {
/* 331 */       throw new IllegalArgumentException("invalid entry size");
/*     */     }
/* 333 */     this.size = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSize() {
/* 343 */     return this.size;
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
/*     */   public long getCompressedSize() {
/* 356 */     return this.csize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCompressedSize(long paramLong) {
/* 367 */     this.csize = paramLong;
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
/*     */   public void setCrc(long paramLong) {
/* 380 */     if (paramLong < 0L || paramLong > 4294967295L) {
/* 381 */       throw new IllegalArgumentException("invalid entry crc-32");
/*     */     }
/* 383 */     this.crc = paramLong;
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
/*     */   public long getCrc() {
/* 395 */     return this.crc;
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
/*     */   public void setMethod(int paramInt) {
/* 408 */     if (paramInt != 0 && paramInt != 8) {
/* 409 */       throw new IllegalArgumentException("invalid compression method");
/*     */     }
/* 411 */     this.method = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMethod() {
/* 421 */     return this.method;
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
/*     */   public void setExtra(byte[] paramArrayOfbyte) {
/* 443 */     setExtra0(paramArrayOfbyte, false);
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
/*     */   void setExtra0(byte[] paramArrayOfbyte, boolean paramBoolean) {
/* 455 */     if (paramArrayOfbyte != null) {
/* 456 */       if (paramArrayOfbyte.length > 65535) {
/* 457 */         throw new IllegalArgumentException("invalid extra field length");
/*     */       }
/*     */       
/* 460 */       int i = 0;
/* 461 */       int j = paramArrayOfbyte.length;
/* 462 */       while (i + 4 < j) {
/* 463 */         int n, i1; byte b; int k = ZipUtils.get16(paramArrayOfbyte, i);
/* 464 */         int m = ZipUtils.get16(paramArrayOfbyte, i + 2);
/* 465 */         i += 4;
/* 466 */         if (i + m > j)
/*     */           break; 
/* 468 */         switch (k) {
/*     */           case 1:
/* 470 */             if (paramBoolean)
/*     */             {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 477 */               if (m >= 16) {
/* 478 */                 this.size = ZipUtils.get64(paramArrayOfbyte, i);
/* 479 */                 this.csize = ZipUtils.get64(paramArrayOfbyte, i + 8);
/*     */               } 
/*     */             }
/*     */             break;
/*     */           case 10:
/* 484 */             if (m < 32)
/*     */               break; 
/* 486 */             n = i + 4;
/* 487 */             if (ZipUtils.get16(paramArrayOfbyte, n) != 1 || ZipUtils.get16(paramArrayOfbyte, n + 2) != 24)
/*     */               break; 
/* 489 */             this.mtime = ZipUtils.winTimeToFileTime(ZipUtils.get64(paramArrayOfbyte, n + 4));
/* 490 */             this.atime = ZipUtils.winTimeToFileTime(ZipUtils.get64(paramArrayOfbyte, n + 12));
/* 491 */             this.ctime = ZipUtils.winTimeToFileTime(ZipUtils.get64(paramArrayOfbyte, n + 20));
/*     */             break;
/*     */           case 21589:
/* 494 */             i1 = Byte.toUnsignedInt(paramArrayOfbyte[i]);
/* 495 */             b = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 500 */             if ((i1 & 0x1) != 0 && b + 4 <= m) {
/* 501 */               this.mtime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(paramArrayOfbyte, i + b));
/* 502 */               b += 4;
/*     */             } 
/* 504 */             if ((i1 & 0x2) != 0 && b + 4 <= m) {
/* 505 */               this.atime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(paramArrayOfbyte, i + b));
/* 506 */               b += 4;
/*     */             } 
/* 508 */             if ((i1 & 0x4) != 0 && b + 4 <= m) {
/* 509 */               this.ctime = ZipUtils.unixTimeToFileTime(ZipUtils.get32(paramArrayOfbyte, i + b));
/* 510 */               b += 4;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */         
/* 515 */         i += m;
/*     */       } 
/*     */     } 
/* 518 */     this.extra = paramArrayOfbyte;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getExtra() {
/* 529 */     return this.extra;
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
/*     */   public void setComment(String paramString) {
/* 544 */     this.comment = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getComment() {
/* 555 */     return this.comment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDirectory() {
/* 564 */     return this.name.endsWith("/");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 571 */     return getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 578 */     return this.name.hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 586 */       ZipEntry zipEntry = (ZipEntry)super.clone();
/* 587 */       zipEntry.extra = (this.extra == null) ? null : (byte[])this.extra.clone();
/* 588 */       return zipEntry;
/* 589 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/*     */       
/* 591 */       throw new InternalError(cloneNotSupportedException);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/ZipEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */