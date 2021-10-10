/*     */ package sun.font;
/*     */ 
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.MappedByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import sun.java2d.Disposer;
/*     */ import sun.java2d.DisposerRecord;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Type1Font
/*     */   extends FileFont
/*     */ {
/*     */   private static class T1DisposerRecord
/*     */     implements DisposerRecord
/*     */   {
/*  81 */     String fileName = null;
/*     */     
/*     */     T1DisposerRecord(String param1String) {
/*  84 */       this.fileName = param1String;
/*     */     }
/*     */     
/*     */     public synchronized void dispose() {
/*  88 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */           {
/*     */             public Object run()
/*     */             {
/*  92 */               if (Type1Font.T1DisposerRecord.this.fileName != null) {
/*  93 */                 (new File(Type1Font.T1DisposerRecord.this.fileName)).delete();
/*     */               }
/*  95 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/* 101 */   WeakReference bufferRef = new WeakReference(null);
/*     */   
/* 103 */   private String psName = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private static HashMap styleAbbreviationsMapping = new HashMap<>();
/* 110 */   private static HashSet styleNameTokes = new HashSet();
/*     */   
/*     */   private static final int PSEOFTOKEN = 0;
/*     */   private static final int PSNAMETOKEN = 1;
/*     */   private static final int PSSTRINGTOKEN = 2;
/*     */   
/*     */   static {
/* 117 */     String[] arrayOfString1 = { "Black", "Bold", "Book", "Demi", "Heavy", "Light", "Meduium", "Nord", "Poster", "Regular", "Super", "Thin", "Compressed", "Condensed", "Compact", "Extended", "Narrow", "Inclined", "Italic", "Kursiv", "Oblique", "Upright", "Sloped", "Semi", "Ultra", "Extra", "Alternate", "Alternate", "Deutsche Fraktur", "Expert", "Inline", "Ornaments", "Outline", "Roman", "Rounded", "Script", "Shaded", "Swash", "Titling", "Typewriter" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     String[] arrayOfString2 = { "Blk", "Bd", "Bk", "Dm", "Hv", "Lt", "Md", "Nd", "Po", "Rg", "Su", "Th", "Cm", "Cn", "Ct", "Ex", "Nr", "Ic", "It", "Ks", "Obl", "Up", "Sl", "Sm", "Ult", "X", "A", "Alt", "Dfr", "Exp", "In", "Or", "Ou", "Rm", "Rd", "Scr", "Sh", "Sw", "Ti", "Typ" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     String[] arrayOfString3 = { "Black", "Bold", "Book", "Demi", "Heavy", "Light", "Medium", "Nord", "Poster", "Regular", "Super", "Thin", "Compressed", "Condensed", "Compact", "Extended", "Narrow", "Inclined", "Italic", "Kursiv", "Oblique", "Upright", "Sloped", "Slanted", "Semi", "Ultra", "Extra" };
/*     */ 
/*     */     
/*     */     byte b;
/*     */ 
/*     */     
/* 140 */     for (b = 0; b < arrayOfString1.length; b++) {
/* 141 */       styleAbbreviationsMapping.put(arrayOfString2[b], arrayOfString1[b]);
/*     */     }
/* 143 */     for (b = 0; b < arrayOfString3.length; b++) {
/* 144 */       styleNameTokes.add(arrayOfString3[b]);
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
/*     */   public Type1Font(String paramString, Object paramObject) throws FontFormatException {
/* 157 */     this(paramString, paramObject, false);
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
/*     */   public Type1Font(String paramString, Object paramObject, boolean paramBoolean) throws FontFormatException {
/* 169 */     super(paramString, paramObject);
/* 170 */     this.fontRank = 4;
/* 171 */     this.checkedNatives = true;
/*     */     try {
/* 173 */       verify();
/* 174 */     } catch (Throwable throwable) {
/* 175 */       if (paramBoolean) {
/* 176 */         T1DisposerRecord t1DisposerRecord = new T1DisposerRecord(paramString);
/* 177 */         Disposer.addObjectRecord(this.bufferRef, t1DisposerRecord);
/* 178 */         this.bufferRef = null;
/*     */       } 
/* 180 */       if (throwable instanceof FontFormatException) {
/* 181 */         throw (FontFormatException)throwable;
/*     */       }
/* 183 */       throw new FontFormatException("Unexpected runtime exception.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized ByteBuffer getBuffer() throws FontFormatException {
/* 189 */     MappedByteBuffer mappedByteBuffer = this.bufferRef.get();
/* 190 */     if (mappedByteBuffer == null) {
/*     */       
/*     */       try {
/*     */         
/* 194 */         RandomAccessFile randomAccessFile = AccessController.<RandomAccessFile>doPrivileged(new PrivilegedAction<RandomAccessFile>()
/*     */             {
/*     */               public Object run() {
/*     */                 try {
/* 198 */                   return new RandomAccessFile(Type1Font.this.platName, "r");
/* 199 */                 } catch (FileNotFoundException fileNotFoundException) {
/*     */                   
/* 201 */                   return null;
/*     */                 }  }
/*     */             });
/* 204 */         FileChannel fileChannel = randomAccessFile.getChannel();
/* 205 */         this.fileSize = (int)fileChannel.size();
/* 206 */         mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, this.fileSize);
/* 207 */         mappedByteBuffer.position(0);
/* 208 */         this.bufferRef = new WeakReference<>(mappedByteBuffer);
/* 209 */         fileChannel.close();
/* 210 */       } catch (NullPointerException nullPointerException) {
/* 211 */         throw new FontFormatException(nullPointerException.toString());
/* 212 */       } catch (ClosedChannelException closedChannelException) {
/*     */ 
/*     */ 
/*     */         
/* 216 */         Thread.interrupted();
/* 217 */         return getBuffer();
/* 218 */       } catch (IOException iOException) {
/* 219 */         throw new FontFormatException(iOException.toString());
/*     */       } 
/*     */     }
/* 222 */     return mappedByteBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void close() {}
/*     */ 
/*     */   
/*     */   void readFile(ByteBuffer paramByteBuffer) {
/* 230 */     RandomAccessFile randomAccessFile = null;
/*     */ 
/*     */ 
/*     */     
/* 234 */     try { randomAccessFile = AccessController.<RandomAccessFile>doPrivileged(new PrivilegedAction<RandomAccessFile>()
/*     */           {
/*     */             public Object run() {
/*     */               try {
/* 238 */                 return new RandomAccessFile(Type1Font.this.platName, "r");
/* 239 */               } catch (FileNotFoundException fileNotFoundException) {
/*     */                 
/* 241 */                 return null;
/*     */               }  }
/*     */           });
/* 244 */       FileChannel fileChannel = randomAccessFile.getChannel();
/* 245 */       while (paramByteBuffer.remaining() > 0 && fileChannel.read(paramByteBuffer) != -1); }
/* 246 */     catch (NullPointerException nullPointerException) {  }
/* 247 */     catch (ClosedChannelException closedChannelException)
/*     */     { try {
/* 249 */         if (randomAccessFile != null) {
/* 250 */           randomAccessFile.close();
/* 251 */           randomAccessFile = null;
/*     */         } 
/* 253 */       } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 258 */       Thread.interrupted();
/* 259 */       readFile(paramByteBuffer); }
/* 260 */     catch (IOException iOException) {  }
/*     */     finally
/* 262 */     { if (randomAccessFile != null) {
/*     */         try {
/* 264 */           randomAccessFile.close();
/* 265 */         } catch (IOException iOException) {}
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized ByteBuffer readBlock(int paramInt1, int paramInt2) {
/* 272 */     ByteBuffer byteBuffer = null;
/*     */     try {
/* 274 */       byteBuffer = getBuffer();
/* 275 */       if (paramInt1 > this.fileSize) {
/* 276 */         paramInt1 = this.fileSize;
/*     */       }
/* 278 */       byteBuffer.position(paramInt1);
/* 279 */       return byteBuffer.slice();
/* 280 */     } catch (FontFormatException fontFormatException) {
/* 281 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void verify() throws FontFormatException {
/* 291 */     ByteBuffer byteBuffer = getBuffer();
/* 292 */     if (byteBuffer.capacity() < 6) {
/* 293 */       throw new FontFormatException("short file");
/*     */     }
/* 295 */     int i = byteBuffer.get(0) & 0xFF;
/* 296 */     if ((byteBuffer.get(0) & 0xFF) == 128) {
/* 297 */       verifyPFB(byteBuffer);
/* 298 */       byteBuffer.position(6);
/*     */     } else {
/* 300 */       verifyPFA(byteBuffer);
/* 301 */       byteBuffer.position(0);
/*     */     } 
/* 303 */     initNames(byteBuffer);
/* 304 */     if (this.familyName == null || this.fullName == null) {
/* 305 */       throw new FontFormatException("Font name not found");
/*     */     }
/* 307 */     setStyle();
/*     */   }
/*     */   
/*     */   public int getFileSize() {
/* 311 */     if (this.fileSize == 0) {
/*     */       try {
/* 313 */         getBuffer();
/* 314 */       } catch (FontFormatException fontFormatException) {}
/*     */     }
/*     */     
/* 317 */     return this.fileSize;
/*     */   }
/*     */   
/*     */   private void verifyPFA(ByteBuffer paramByteBuffer) throws FontFormatException {
/* 321 */     if (paramByteBuffer.getShort() != 9505) {
/* 322 */       throw new FontFormatException("bad pfa font");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void verifyPFB(ByteBuffer paramByteBuffer) throws FontFormatException {
/* 329 */     int i = 0; try {
/*     */       int j;
/*     */       while (true) {
/* 332 */         j = paramByteBuffer.getShort(i) & 0xFFFF;
/* 333 */         if (j == 32769 || j == 32770)
/* 334 */         { paramByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
/* 335 */           int k = paramByteBuffer.getInt(i + 2);
/* 336 */           paramByteBuffer.order(ByteOrder.BIG_ENDIAN);
/* 337 */           if (k <= 0) {
/* 338 */             throw new FontFormatException("bad segment length");
/*     */           }
/* 340 */           i += k + 6; continue; }  break;
/* 341 */       }  if (j == 32771) {
/*     */         return;
/*     */       }
/* 344 */       throw new FontFormatException("bad pfb file");
/*     */     }
/* 346 */     catch (BufferUnderflowException bufferUnderflowException) {
/* 347 */       throw new FontFormatException(bufferUnderflowException.toString());
/* 348 */     } catch (Exception exception) {
/* 349 */       throw new FontFormatException(exception.toString());
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
/*     */   private void initNames(ByteBuffer paramByteBuffer) throws FontFormatException {
/* 367 */     boolean bool = false;
/* 368 */     String str = null;
/*     */ 
/*     */     
/*     */     try {
/* 372 */       while ((this.fullName == null || this.familyName == null || this.psName == null || str == null) && !bool) {
/* 373 */         int i = nextTokenType(paramByteBuffer);
/* 374 */         if (i == 1) {
/* 375 */           int j = paramByteBuffer.position();
/* 376 */           if (paramByteBuffer.get(j) == 70) {
/* 377 */             String str1 = getSimpleToken(paramByteBuffer);
/* 378 */             if ("FullName".equals(str1)) {
/* 379 */               if (nextTokenType(paramByteBuffer) == 2)
/* 380 */                 this.fullName = getString(paramByteBuffer);  continue;
/*     */             } 
/* 382 */             if ("FamilyName".equals(str1)) {
/* 383 */               if (nextTokenType(paramByteBuffer) == 2)
/* 384 */                 this.familyName = getString(paramByteBuffer);  continue;
/*     */             } 
/* 386 */             if ("FontName".equals(str1)) {
/* 387 */               if (nextTokenType(paramByteBuffer) == 1)
/* 388 */                 this.psName = getSimpleToken(paramByteBuffer);  continue;
/*     */             } 
/* 390 */             if ("FontType".equals(str1)) {
/*     */ 
/*     */ 
/*     */               
/* 394 */               String str2 = getSimpleToken(paramByteBuffer);
/* 395 */               if ("def".equals(getSimpleToken(paramByteBuffer)))
/* 396 */                 str = str2; 
/*     */             } 
/*     */             continue;
/*     */           } 
/* 400 */           while (paramByteBuffer.get() > 32); continue;
/*     */         } 
/* 402 */         if (i == 0) {
/* 403 */           bool = true;
/*     */         }
/*     */       } 
/* 406 */     } catch (Exception exception) {
/* 407 */       throw new FontFormatException(exception.toString());
/*     */     } 
/*     */ 
/*     */     
/* 411 */     if (!"1".equals(str)) {
/* 412 */       throw new FontFormatException("Unsupported font type");
/*     */     }
/*     */     
/* 415 */     if (this.psName == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       paramByteBuffer.position(0);
/* 422 */       if (paramByteBuffer.getShort() != 9505)
/*     */       {
/* 424 */         paramByteBuffer.position(8);
/*     */       }
/*     */ 
/*     */       
/* 428 */       String str1 = getSimpleToken(paramByteBuffer);
/* 429 */       if (!str1.startsWith("FontType1-") && !str1.startsWith("PS-AdobeFont-")) {
/* 430 */         throw new FontFormatException("Unsupported font format [" + str1 + "]");
/*     */       }
/* 432 */       this.psName = getSimpleToken(paramByteBuffer);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 438 */     if (bool)
/*     */     {
/* 440 */       if (this.fullName != null) {
/* 441 */         this.familyName = fullName2FamilyName(this.fullName);
/* 442 */       } else if (this.familyName != null) {
/* 443 */         this.fullName = this.familyName;
/*     */       } else {
/* 445 */         this.fullName = psName2FullName(this.psName);
/* 446 */         this.familyName = psName2FamilyName(this.psName);
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
/*     */   private String fullName2FamilyName(String paramString) {
/* 458 */     int i = paramString.length();
/*     */     
/* 460 */     while (i > 0) {
/* 461 */       int j = i - 1;
/* 462 */       while (j > 0 && paramString.charAt(j) != ' ') {
/* 463 */         j--;
/*     */       }
/*     */       
/* 466 */       if (!isStyleToken(paramString.substring(j + 1, i))) {
/* 467 */         return paramString.substring(0, i);
/*     */       }
/* 469 */       i = j;
/*     */     } 
/*     */     
/* 472 */     return paramString;
/*     */   }
/*     */   
/*     */   private String expandAbbreviation(String paramString) {
/* 476 */     if (styleAbbreviationsMapping.containsKey(paramString))
/* 477 */       return (String)styleAbbreviationsMapping.get(paramString); 
/* 478 */     return paramString;
/*     */   }
/*     */   
/*     */   private boolean isStyleToken(String paramString) {
/* 482 */     return styleNameTokes.contains(paramString);
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
/*     */   private String psName2FullName(String paramString) {
/*     */     String str;
/* 495 */     int i = paramString.indexOf("-");
/* 496 */     if (i >= 0) {
/* 497 */       str = expandName(paramString.substring(0, i), false);
/* 498 */       str = str + " " + expandName(paramString.substring(i + 1), true);
/*     */     } else {
/* 500 */       str = expandName(paramString, false);
/*     */     } 
/*     */     
/* 503 */     return str;
/*     */   }
/*     */   
/*     */   private String psName2FamilyName(String paramString) {
/* 507 */     String str = paramString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 516 */     if (str.indexOf("-") > 0) {
/* 517 */       str = str.substring(0, str.indexOf("-"));
/*     */     }
/*     */     
/* 520 */     return expandName(str, false);
/*     */   }
/*     */   
/*     */   private int nextCapitalLetter(String paramString, int paramInt) {
/* 524 */     for (; paramInt >= 0 && paramInt < paramString.length(); paramInt++) {
/* 525 */       if (paramString.charAt(paramInt) >= 'A' && paramString.charAt(paramInt) <= 'Z')
/* 526 */         return paramInt; 
/*     */     } 
/* 528 */     return -1;
/*     */   }
/*     */   
/*     */   private String expandName(String paramString, boolean paramBoolean) {
/* 532 */     StringBuffer stringBuffer = new StringBuffer(paramString.length() + 10);
/* 533 */     int i = 0;
/*     */     
/* 535 */     while (i < paramString.length()) {
/* 536 */       int j = nextCapitalLetter(paramString, i + 1);
/* 537 */       if (j < 0) {
/* 538 */         j = paramString.length();
/*     */       }
/*     */       
/* 541 */       if (i != 0) {
/* 542 */         stringBuffer.append(" ");
/*     */       }
/*     */       
/* 545 */       if (paramBoolean) {
/* 546 */         stringBuffer.append(expandAbbreviation(paramString.substring(i, j)));
/*     */       } else {
/* 548 */         stringBuffer.append(paramString.substring(i, j));
/*     */       } 
/* 550 */       i = j;
/*     */     } 
/*     */     
/* 553 */     return stringBuffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private byte skip(ByteBuffer paramByteBuffer) {
/* 558 */     byte b = paramByteBuffer.get();
/* 559 */     label15: while (b == 37) {
/*     */       while (true) {
/* 561 */         b = paramByteBuffer.get();
/* 562 */         if (b != 13) { if (b == 10)
/*     */             continue label15;  continue; }
/*     */          continue label15;
/*     */       } 
/*     */     } 
/* 567 */     while (b <= 32) {
/* 568 */       b = paramByteBuffer.get();
/*     */     }
/* 570 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int nextTokenType(ByteBuffer paramByteBuffer) {
/*     */     try {
/* 581 */       byte b = skip(paramByteBuffer);
/*     */       
/*     */       while (true) {
/* 584 */         if (b == 47)
/* 585 */           return 1; 
/* 586 */         if (b == 40)
/* 587 */           return 2; 
/* 588 */         if (b == 13 || b == 10) {
/* 589 */           b = skip(paramByteBuffer); continue;
/*     */         } 
/* 591 */         b = paramByteBuffer.get();
/*     */       }
/*     */     
/* 594 */     } catch (BufferUnderflowException bufferUnderflowException) {
/* 595 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getSimpleToken(ByteBuffer paramByteBuffer) {
/* 603 */     while (paramByteBuffer.get() <= 32);
/* 604 */     int i = paramByteBuffer.position() - 1;
/* 605 */     while (paramByteBuffer.get() > 32);
/* 606 */     int j = paramByteBuffer.position();
/* 607 */     byte[] arrayOfByte = new byte[j - i - 1];
/* 608 */     paramByteBuffer.position(i);
/* 609 */     paramByteBuffer.get(arrayOfByte);
/*     */     try {
/* 611 */       return new String(arrayOfByte, "US-ASCII");
/* 612 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 613 */       return new String(arrayOfByte);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getString(ByteBuffer paramByteBuffer) {
/* 618 */     int i = paramByteBuffer.position();
/* 619 */     while (paramByteBuffer.get() != 41);
/* 620 */     int j = paramByteBuffer.position();
/* 621 */     byte[] arrayOfByte = new byte[j - i - 1];
/* 622 */     paramByteBuffer.position(i);
/* 623 */     paramByteBuffer.get(arrayOfByte);
/*     */     try {
/* 625 */       return new String(arrayOfByte, "US-ASCII");
/* 626 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 627 */       return new String(arrayOfByte);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPostscriptName() {
/* 633 */     return this.psName;
/*     */   }
/*     */   
/*     */   protected synchronized FontScaler getScaler() {
/* 637 */     if (this.scaler == null) {
/* 638 */       this.scaler = FontScaler.getScaler(this, 0, false, this.fileSize);
/*     */     }
/*     */     
/* 641 */     return this.scaler;
/*     */   }
/*     */   
/*     */   CharToGlyphMapper getMapper() {
/* 645 */     if (this.mapper == null) {
/* 646 */       this.mapper = new Type1GlyphMapper(this);
/*     */     }
/* 648 */     return this.mapper;
/*     */   }
/*     */   
/*     */   public int getNumGlyphs() {
/*     */     try {
/* 653 */       return getScaler().getNumGlyphs();
/* 654 */     } catch (FontScalerException fontScalerException) {
/* 655 */       this.scaler = FontScaler.getNullScaler();
/* 656 */       return getNumGlyphs();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getMissingGlyphCode() {
/*     */     try {
/* 662 */       return getScaler().getMissingGlyphCode();
/* 663 */     } catch (FontScalerException fontScalerException) {
/* 664 */       this.scaler = FontScaler.getNullScaler();
/* 665 */       return getMissingGlyphCode();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getGlyphCode(char paramChar) {
/*     */     try {
/* 671 */       return getScaler().getGlyphCode(paramChar);
/* 672 */     } catch (FontScalerException fontScalerException) {
/* 673 */       this.scaler = FontScaler.getNullScaler();
/* 674 */       return getGlyphCode(paramChar);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 679 */     return "** Type1 Font: Family=" + this.familyName + " Name=" + this.fullName + " style=" + this.style + " fileName=" + 
/* 680 */       getPublicFileName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/Type1Font.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */