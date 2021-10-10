/*     */ package java.util.zip;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashSet;
/*     */ import java.util.Vector;
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
/*     */ public class ZipOutputStream
/*     */   extends DeflaterOutputStream
/*     */   implements ZipConstants
/*     */ {
/*  56 */   private static final boolean inhibitZip64 = Boolean.parseBoolean(
/*  57 */       AccessController.<String>doPrivileged(new GetPropertyAction("jdk.util.zip.inhibitZip64", "false")));
/*     */   private XEntry current;
/*     */   
/*     */   private static class XEntry {
/*     */     final ZipEntry entry;
/*     */     final long offset;
/*     */     
/*     */     public XEntry(ZipEntry param1ZipEntry, long param1Long) {
/*  65 */       this.entry = param1ZipEntry;
/*  66 */       this.offset = param1Long;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  71 */   private Vector<XEntry> xentries = new Vector<>();
/*  72 */   private HashSet<String> names = new HashSet<>();
/*  73 */   private CRC32 crc = new CRC32();
/*  74 */   private long written = 0L;
/*  75 */   private long locoff = 0L;
/*     */   private byte[] comment;
/*  77 */   private int method = 8;
/*     */   private boolean finished;
/*     */   private boolean closed = false;
/*     */   private final ZipCoder zc;
/*     */   public static final int STORED = 0;
/*     */   public static final int DEFLATED = 8;
/*     */   
/*     */   private static int version(ZipEntry paramZipEntry) throws ZipException {
/*  85 */     switch (paramZipEntry.method) { case 8:
/*  86 */         return 20;
/*  87 */       case 0: return 10; }
/*  88 */      throw new ZipException("unsupported compression method");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureOpen() throws IOException {
/*  96 */     if (this.closed) {
/*  97 */       throw new IOException("Stream closed");
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
/*     */   
/*     */   public ZipOutputStream(OutputStream paramOutputStream) {
/* 119 */     this(paramOutputStream, StandardCharsets.UTF_8);
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
/*     */   public ZipOutputStream(OutputStream paramOutputStream, Charset paramCharset) {
/* 133 */     super(paramOutputStream, new Deflater(-1, true));
/* 134 */     if (paramCharset == null)
/* 135 */       throw new NullPointerException("charset is null"); 
/* 136 */     this.zc = ZipCoder.get(paramCharset);
/* 137 */     this.usesDefaultDeflater = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setComment(String paramString) {
/* 147 */     if (paramString != null) {
/* 148 */       this.comment = this.zc.getBytes(paramString);
/* 149 */       if (this.comment.length > 65535) {
/* 150 */         throw new IllegalArgumentException("ZIP file comment too long.");
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
/*     */   public void setMethod(int paramInt) {
/* 163 */     if (paramInt != 8 && paramInt != 0) {
/* 164 */       throw new IllegalArgumentException("invalid compression method");
/*     */     }
/* 166 */     this.method = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevel(int paramInt) {
/* 176 */     this.def.setLevel(paramInt);
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
/*     */   public void putNextEntry(ZipEntry paramZipEntry) throws IOException {
/* 190 */     ensureOpen();
/* 191 */     if (this.current != null) {
/* 192 */       closeEntry();
/*     */     }
/* 194 */     if (paramZipEntry.xdostime == -1L)
/*     */     {
/*     */       
/* 197 */       paramZipEntry.setTime(System.currentTimeMillis());
/*     */     }
/* 199 */     if (paramZipEntry.method == -1) {
/* 200 */       paramZipEntry.method = this.method;
/*     */     }
/*     */     
/* 203 */     paramZipEntry.flag = 0;
/* 204 */     switch (paramZipEntry.method) {
/*     */ 
/*     */       
/*     */       case 8:
/* 208 */         if (paramZipEntry.size == -1L || paramZipEntry.csize == -1L || paramZipEntry.crc == -1L) {
/* 209 */           paramZipEntry.flag = 8;
/*     */         }
/*     */         break;
/*     */ 
/*     */       
/*     */       case 0:
/* 215 */         if (paramZipEntry.size == -1L) {
/* 216 */           paramZipEntry.size = paramZipEntry.csize;
/* 217 */         } else if (paramZipEntry.csize == -1L) {
/* 218 */           paramZipEntry.csize = paramZipEntry.size;
/* 219 */         } else if (paramZipEntry.size != paramZipEntry.csize) {
/* 220 */           throw new ZipException("STORED entry where compressed != uncompressed size");
/*     */         } 
/*     */         
/* 223 */         if (paramZipEntry.size == -1L || paramZipEntry.crc == -1L) {
/* 224 */           throw new ZipException("STORED entry missing size, compressed size, or crc-32");
/*     */         }
/*     */         break;
/*     */       
/*     */       default:
/* 229 */         throw new ZipException("unsupported compression method");
/*     */     } 
/* 231 */     if (!this.names.add(paramZipEntry.name)) {
/* 232 */       throw new ZipException("duplicate entry: " + paramZipEntry.name);
/*     */     }
/* 234 */     if (this.zc.isUTF8())
/* 235 */       paramZipEntry.flag |= 0x800; 
/* 236 */     this.current = new XEntry(paramZipEntry, this.written);
/* 237 */     this.xentries.add(this.current);
/* 238 */     writeLOC(this.current);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeEntry() throws IOException {
/* 248 */     ensureOpen();
/* 249 */     if (this.current != null) {
/* 250 */       ZipEntry zipEntry = this.current.entry;
/* 251 */       switch (zipEntry.method) {
/*     */         case 8:
/* 253 */           this.def.finish();
/* 254 */           while (!this.def.finished()) {
/* 255 */             deflate();
/*     */           }
/* 257 */           if ((zipEntry.flag & 0x8) == 0) {
/*     */             
/* 259 */             if (zipEntry.size != this.def.getBytesRead()) {
/* 260 */               throw new ZipException("invalid entry size (expected " + zipEntry.size + " but got " + this.def
/*     */                   
/* 262 */                   .getBytesRead() + " bytes)");
/*     */             }
/* 264 */             if (zipEntry.csize != this.def.getBytesWritten()) {
/* 265 */               throw new ZipException("invalid entry compressed size (expected " + zipEntry.csize + " but got " + this.def
/*     */                   
/* 267 */                   .getBytesWritten() + " bytes)");
/*     */             }
/* 269 */             if (zipEntry.crc != this.crc.getValue()) {
/* 270 */               throw new ZipException("invalid entry CRC-32 (expected 0x" + 
/*     */                   
/* 272 */                   Long.toHexString(zipEntry.crc) + " but got 0x" + 
/* 273 */                   Long.toHexString(this.crc.getValue()) + ")");
/*     */             }
/*     */           } else {
/* 276 */             zipEntry.size = this.def.getBytesRead();
/* 277 */             zipEntry.csize = this.def.getBytesWritten();
/* 278 */             zipEntry.crc = this.crc.getValue();
/* 279 */             writeEXT(zipEntry);
/*     */           } 
/* 281 */           this.def.reset();
/* 282 */           this.written += zipEntry.csize;
/*     */           break;
/*     */         
/*     */         case 0:
/* 286 */           if (zipEntry.size != this.written - this.locoff) {
/* 287 */             throw new ZipException("invalid entry size (expected " + zipEntry.size + " but got " + (this.written - this.locoff) + " bytes)");
/*     */           }
/*     */ 
/*     */           
/* 291 */           if (zipEntry.crc != this.crc.getValue()) {
/* 292 */             throw new ZipException("invalid entry crc-32 (expected 0x" + 
/*     */                 
/* 294 */                 Long.toHexString(zipEntry.crc) + " but got 0x" + 
/* 295 */                 Long.toHexString(this.crc.getValue()) + ")");
/*     */           }
/*     */           break;
/*     */         default:
/* 299 */           throw new ZipException("invalid compression method");
/*     */       } 
/* 301 */       this.crc.reset();
/* 302 */       this.current = null;
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
/*     */   public synchronized void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 318 */     ensureOpen();
/* 319 */     if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 > paramArrayOfbyte.length - paramInt2)
/* 320 */       throw new IndexOutOfBoundsException(); 
/* 321 */     if (paramInt2 == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 325 */     if (this.current == null) {
/* 326 */       throw new ZipException("no current ZIP entry");
/*     */     }
/* 328 */     ZipEntry zipEntry = this.current.entry;
/* 329 */     switch (zipEntry.method) {
/*     */       case 8:
/* 331 */         super.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */         break;
/*     */       case 0:
/* 334 */         this.written += paramInt2;
/* 335 */         if (this.written - this.locoff > zipEntry.size) {
/* 336 */           throw new ZipException("attempt to write past end of STORED entry");
/*     */         }
/*     */         
/* 339 */         this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
/*     */         break;
/*     */       default:
/* 342 */         throw new ZipException("invalid compression method");
/*     */     } 
/* 344 */     this.crc.update(paramArrayOfbyte, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finish() throws IOException {
/* 355 */     ensureOpen();
/* 356 */     if (this.finished) {
/*     */       return;
/*     */     }
/* 359 */     if (this.current != null) {
/* 360 */       closeEntry();
/*     */     }
/*     */     
/* 363 */     long l = this.written;
/* 364 */     for (XEntry xEntry : this.xentries)
/* 365 */       writeCEN(xEntry); 
/* 366 */     writeEND(l, this.written - l);
/* 367 */     this.finished = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 376 */     if (!this.closed) {
/* 377 */       super.close();
/* 378 */       this.closed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeLOC(XEntry paramXEntry) throws IOException {
/* 386 */     ZipEntry zipEntry = paramXEntry.entry;
/* 387 */     int i = zipEntry.flag;
/* 388 */     boolean bool = false;
/* 389 */     int j = getExtraLen(zipEntry.extra);
/*     */     
/* 391 */     writeInt(67324752L);
/* 392 */     if ((i & 0x8) == 8) {
/* 393 */       writeShort(version(zipEntry));
/* 394 */       writeShort(i);
/* 395 */       writeShort(zipEntry.method);
/* 396 */       writeInt(zipEntry.xdostime);
/*     */ 
/*     */       
/* 399 */       writeInt(0L);
/* 400 */       writeInt(0L);
/* 401 */       writeInt(0L);
/*     */     } else {
/* 403 */       if (zipEntry.csize >= 4294967295L || zipEntry.size >= 4294967295L) {
/* 404 */         bool = true;
/* 405 */         writeShort(45);
/*     */       } else {
/* 407 */         writeShort(version(zipEntry));
/*     */       } 
/* 409 */       writeShort(i);
/* 410 */       writeShort(zipEntry.method);
/* 411 */       writeInt(zipEntry.xdostime);
/* 412 */       writeInt(zipEntry.crc);
/* 413 */       if (bool) {
/* 414 */         writeInt(4294967295L);
/* 415 */         writeInt(4294967295L);
/* 416 */         j += 20;
/*     */       } else {
/* 418 */         writeInt(zipEntry.csize);
/* 419 */         writeInt(zipEntry.size);
/*     */       } 
/*     */     } 
/* 422 */     byte[] arrayOfByte = this.zc.getBytes(zipEntry.name);
/* 423 */     writeShort(arrayOfByte.length);
/*     */     
/* 425 */     byte b = 0;
/* 426 */     int k = 0;
/* 427 */     if (zipEntry.mtime != null) {
/* 428 */       b += true;
/* 429 */       k |= 0x1;
/*     */     } 
/* 431 */     if (zipEntry.atime != null) {
/* 432 */       b += true;
/* 433 */       k |= 0x2;
/*     */     } 
/* 435 */     if (zipEntry.ctime != null) {
/* 436 */       b += true;
/* 437 */       k |= 0x4;
/*     */     } 
/* 439 */     if (k != 0)
/* 440 */       j += b + 5; 
/* 441 */     writeShort(j);
/* 442 */     writeBytes(arrayOfByte, 0, arrayOfByte.length);
/* 443 */     if (bool) {
/* 444 */       writeShort(1);
/* 445 */       writeShort(16);
/* 446 */       writeLong(zipEntry.size);
/* 447 */       writeLong(zipEntry.csize);
/*     */     } 
/* 449 */     if (k != 0) {
/* 450 */       writeShort(21589);
/* 451 */       writeShort(b + 1);
/* 452 */       writeByte(k);
/* 453 */       if (zipEntry.mtime != null)
/* 454 */         writeInt(ZipUtils.fileTimeToUnixTime(zipEntry.mtime)); 
/* 455 */       if (zipEntry.atime != null)
/* 456 */         writeInt(ZipUtils.fileTimeToUnixTime(zipEntry.atime)); 
/* 457 */       if (zipEntry.ctime != null)
/* 458 */         writeInt(ZipUtils.fileTimeToUnixTime(zipEntry.ctime)); 
/*     */     } 
/* 460 */     writeExtra(zipEntry.extra);
/* 461 */     this.locoff = this.written;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEXT(ZipEntry paramZipEntry) throws IOException {
/* 468 */     writeInt(134695760L);
/* 469 */     writeInt(paramZipEntry.crc);
/* 470 */     if (paramZipEntry.csize >= 4294967295L || paramZipEntry.size >= 4294967295L) {
/* 471 */       writeLong(paramZipEntry.csize);
/* 472 */       writeLong(paramZipEntry.size);
/*     */     } else {
/* 474 */       writeInt(paramZipEntry.csize);
/* 475 */       writeInt(paramZipEntry.size);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeCEN(XEntry paramXEntry) throws IOException {
/*     */     byte[] arrayOfByte2;
/* 484 */     ZipEntry zipEntry = paramXEntry.entry;
/* 485 */     int i = zipEntry.flag;
/* 486 */     int j = version(zipEntry);
/* 487 */     long l1 = zipEntry.csize;
/* 488 */     long l2 = zipEntry.size;
/* 489 */     long l3 = paramXEntry.offset;
/* 490 */     byte b = 0;
/* 491 */     boolean bool = false;
/*     */     
/* 493 */     if (zipEntry.csize >= 4294967295L) {
/* 494 */       l1 = 4294967295L;
/* 495 */       b += true;
/* 496 */       bool = true;
/*     */     } 
/* 498 */     if (zipEntry.size >= 4294967295L) {
/* 499 */       l2 = 4294967295L;
/* 500 */       b += true;
/* 501 */       bool = true;
/*     */     } 
/* 503 */     if (paramXEntry.offset >= 4294967295L) {
/* 504 */       l3 = 4294967295L;
/* 505 */       b += true;
/* 506 */       bool = true;
/*     */     } 
/* 508 */     writeInt(33639248L);
/* 509 */     if (bool) {
/* 510 */       writeShort(45);
/* 511 */       writeShort(45);
/*     */     } else {
/* 513 */       writeShort(j);
/* 514 */       writeShort(j);
/*     */     } 
/* 516 */     writeShort(i);
/* 517 */     writeShort(zipEntry.method);
/* 518 */     writeInt(zipEntry.xdostime);
/* 519 */     writeInt(zipEntry.crc);
/* 520 */     writeInt(l1);
/* 521 */     writeInt(l2);
/* 522 */     byte[] arrayOfByte1 = this.zc.getBytes(zipEntry.name);
/* 523 */     writeShort(arrayOfByte1.length);
/*     */     
/* 525 */     int k = getExtraLen(zipEntry.extra);
/* 526 */     if (bool) {
/* 527 */       k += b + 4;
/*     */     }
/*     */ 
/*     */     
/* 531 */     int m = 0;
/* 532 */     if (zipEntry.mtime != null) {
/* 533 */       k += 4;
/* 534 */       m |= 0x1;
/*     */     } 
/* 536 */     if (zipEntry.atime != null) {
/* 537 */       m |= 0x2;
/*     */     }
/* 539 */     if (zipEntry.ctime != null) {
/* 540 */       m |= 0x4;
/*     */     }
/* 542 */     if (m != 0) {
/* 543 */       k += 5;
/*     */     }
/* 545 */     writeShort(k);
/*     */     
/* 547 */     if (zipEntry.comment != null) {
/* 548 */       arrayOfByte2 = this.zc.getBytes(zipEntry.comment);
/* 549 */       writeShort(Math.min(arrayOfByte2.length, 65535));
/*     */     } else {
/* 551 */       arrayOfByte2 = null;
/* 552 */       writeShort(0);
/*     */     } 
/* 554 */     writeShort(0);
/* 555 */     writeShort(0);
/* 556 */     writeInt(0L);
/* 557 */     writeInt(l3);
/* 558 */     writeBytes(arrayOfByte1, 0, arrayOfByte1.length);
/*     */ 
/*     */     
/* 561 */     if (bool) {
/* 562 */       writeShort(1);
/* 563 */       writeShort(b);
/* 564 */       if (l2 == 4294967295L)
/* 565 */         writeLong(zipEntry.size); 
/* 566 */       if (l1 == 4294967295L)
/* 567 */         writeLong(zipEntry.csize); 
/* 568 */       if (l3 == 4294967295L)
/* 569 */         writeLong(paramXEntry.offset); 
/*     */     } 
/* 571 */     if (m != 0) {
/* 572 */       writeShort(21589);
/* 573 */       if (zipEntry.mtime != null) {
/* 574 */         writeShort(5);
/* 575 */         writeByte(m);
/* 576 */         writeInt(ZipUtils.fileTimeToUnixTime(zipEntry.mtime));
/*     */       } else {
/* 578 */         writeShort(1);
/* 579 */         writeByte(m);
/*     */       } 
/*     */     } 
/* 582 */     writeExtra(zipEntry.extra);
/* 583 */     if (arrayOfByte2 != null) {
/* 584 */       writeBytes(arrayOfByte2, 0, Math.min(arrayOfByte2.length, 65535));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEND(long paramLong1, long paramLong2) throws IOException {
/* 592 */     int i = 0;
/* 593 */     long l1 = paramLong2;
/* 594 */     long l2 = paramLong1;
/* 595 */     if (l1 >= 4294967295L) {
/* 596 */       l1 = 4294967295L;
/* 597 */       i = 1;
/*     */     } 
/* 599 */     if (l2 >= 4294967295L) {
/* 600 */       l2 = 4294967295L;
/* 601 */       i = 1;
/*     */     } 
/* 603 */     int j = this.xentries.size();
/* 604 */     if (j >= 65535) {
/* 605 */       i |= !inhibitZip64 ? 1 : 0;
/* 606 */       if (i != 0) {
/* 607 */         j = 65535;
/*     */       }
/*     */     } 
/* 610 */     if (i != 0) {
/* 611 */       long l = this.written;
/*     */       
/* 613 */       writeInt(101075792L);
/* 614 */       writeLong(44L);
/* 615 */       writeShort(45);
/* 616 */       writeShort(45);
/* 617 */       writeInt(0L);
/* 618 */       writeInt(0L);
/* 619 */       writeLong(this.xentries.size());
/* 620 */       writeLong(this.xentries.size());
/* 621 */       writeLong(paramLong2);
/* 622 */       writeLong(paramLong1);
/*     */ 
/*     */       
/* 625 */       writeInt(117853008L);
/* 626 */       writeInt(0L);
/* 627 */       writeLong(l);
/* 628 */       writeInt(1L);
/*     */     } 
/* 630 */     writeInt(101010256L);
/* 631 */     writeShort(0);
/* 632 */     writeShort(0);
/* 633 */     writeShort(j);
/* 634 */     writeShort(j);
/* 635 */     writeInt(l1);
/* 636 */     writeInt(l2);
/* 637 */     if (this.comment != null) {
/* 638 */       writeShort(this.comment.length);
/* 639 */       writeBytes(this.comment, 0, this.comment.length);
/*     */     } else {
/* 641 */       writeShort(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getExtraLen(byte[] paramArrayOfbyte) {
/* 649 */     if (paramArrayOfbyte == null)
/* 650 */       return 0; 
/* 651 */     int i = 0;
/* 652 */     int j = paramArrayOfbyte.length;
/* 653 */     int k = 0;
/* 654 */     while (k + 4 <= j) {
/* 655 */       int m = ZipUtils.get16(paramArrayOfbyte, k);
/* 656 */       int n = ZipUtils.get16(paramArrayOfbyte, k + 2);
/* 657 */       if (n < 0 || k + 4 + n > j) {
/*     */         break;
/*     */       }
/* 660 */       if (m == 21589 || m == 1) {
/* 661 */         i += n + 4;
/*     */       }
/* 663 */       k += n + 4;
/*     */     } 
/* 665 */     return j - i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeExtra(byte[] paramArrayOfbyte) throws IOException {
/* 675 */     if (paramArrayOfbyte != null) {
/* 676 */       int i = paramArrayOfbyte.length;
/* 677 */       int j = 0;
/* 678 */       while (j + 4 <= i) {
/* 679 */         int k = ZipUtils.get16(paramArrayOfbyte, j);
/* 680 */         int m = ZipUtils.get16(paramArrayOfbyte, j + 2);
/* 681 */         if (m < 0 || j + 4 + m > i) {
/* 682 */           writeBytes(paramArrayOfbyte, j, i - j);
/*     */           return;
/*     */         } 
/* 685 */         if (k != 21589 && k != 1) {
/* 686 */           writeBytes(paramArrayOfbyte, j, m + 4);
/*     */         }
/* 688 */         j += m + 4;
/*     */       } 
/* 690 */       if (j < i) {
/* 691 */         writeBytes(paramArrayOfbyte, j, i - j);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeByte(int paramInt) throws IOException {
/* 700 */     OutputStream outputStream = this.out;
/* 701 */     outputStream.write(paramInt & 0xFF);
/* 702 */     this.written++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeShort(int paramInt) throws IOException {
/* 709 */     OutputStream outputStream = this.out;
/* 710 */     outputStream.write(paramInt >>> 0 & 0xFF);
/* 711 */     outputStream.write(paramInt >>> 8 & 0xFF);
/* 712 */     this.written += 2L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeInt(long paramLong) throws IOException {
/* 719 */     OutputStream outputStream = this.out;
/* 720 */     outputStream.write((int)(paramLong >>> 0L & 0xFFL));
/* 721 */     outputStream.write((int)(paramLong >>> 8L & 0xFFL));
/* 722 */     outputStream.write((int)(paramLong >>> 16L & 0xFFL));
/* 723 */     outputStream.write((int)(paramLong >>> 24L & 0xFFL));
/* 724 */     this.written += 4L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeLong(long paramLong) throws IOException {
/* 731 */     OutputStream outputStream = this.out;
/* 732 */     outputStream.write((int)(paramLong >>> 0L & 0xFFL));
/* 733 */     outputStream.write((int)(paramLong >>> 8L & 0xFFL));
/* 734 */     outputStream.write((int)(paramLong >>> 16L & 0xFFL));
/* 735 */     outputStream.write((int)(paramLong >>> 24L & 0xFFL));
/* 736 */     outputStream.write((int)(paramLong >>> 32L & 0xFFL));
/* 737 */     outputStream.write((int)(paramLong >>> 40L & 0xFFL));
/* 738 */     outputStream.write((int)(paramLong >>> 48L & 0xFFL));
/* 739 */     outputStream.write((int)(paramLong >>> 56L & 0xFFL));
/* 740 */     this.written += 8L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeBytes(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws IOException {
/* 747 */     this.out.write(paramArrayOfbyte, paramInt1, paramInt2);
/* 748 */     this.written += paramInt2;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/zip/ZipOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */