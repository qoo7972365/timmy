/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.impl.logging.OMGSystemException;
/*     */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.nio.charset.CharacterCodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetDecoder;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ import java.nio.charset.MalformedInputException;
/*     */ import java.nio.charset.UnmappableCharacterException;
/*     */ import java.nio.charset.UnsupportedCharsetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CodeSetConversion
/*     */ {
/*     */   private static CodeSetConversion implementation;
/*     */   private static final int FALLBACK_CODESET = 0;
/*     */   private CodeSetCache cache;
/*     */   
/*     */   public static abstract class CTBConverter
/*     */   {
/*     */     public abstract void convert(char param1Char);
/*     */     
/*     */     public abstract void convert(String param1String);
/*     */     
/*     */     public abstract int getNumBytes();
/*     */     
/*     */     public abstract float getMaxBytesPerChar();
/*     */     
/*     */     public abstract boolean isFixedWidthEncoding();
/*     */     
/*     */     public abstract int getAlignment();
/*     */     
/*     */     public abstract byte[] getBytes();
/*     */   }
/*     */   
/*     */   public static abstract class BTCConverter
/*     */   {
/*     */     public abstract boolean isFixedWidthEncoding();
/*     */     
/*     */     public abstract int getFixedCharWidth();
/*     */     
/*     */     public abstract int getNumChars();
/*     */     
/*     */     public abstract char[] getChars(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2);
/*     */   }
/*     */   
/*     */   private class JavaCTBConverter
/*     */     extends CTBConverter
/*     */   {
/* 137 */     private ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.encoding");
/*     */ 
/*     */     
/* 140 */     private OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private CharsetEncoder ctb;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int alignment;
/*     */ 
/*     */ 
/*     */     
/* 154 */     private char[] chars = null;
/*     */ 
/*     */     
/* 157 */     private int numBytes = 0;
/*     */ 
/*     */ 
/*     */     
/* 161 */     private int numChars = 0;
/*     */ 
/*     */ 
/*     */     
/*     */     private ByteBuffer buffer;
/*     */ 
/*     */ 
/*     */     
/*     */     private OSFCodeSetRegistry.Entry codeset;
/*     */ 
/*     */ 
/*     */     
/*     */     public JavaCTBConverter(OSFCodeSetRegistry.Entry param1Entry, int param1Int) {
/*     */       try {
/* 175 */         this.ctb = CodeSetConversion.this.cache.getCharToByteConverter(param1Entry.getName());
/* 176 */         if (this.ctb == null) {
/* 177 */           Charset charset = Charset.forName(param1Entry.getName());
/* 178 */           this.ctb = charset.newEncoder();
/* 179 */           CodeSetConversion.this.cache.setConverter(param1Entry.getName(), this.ctb);
/*     */         } 
/* 181 */       } catch (IllegalCharsetNameException illegalCharsetNameException) {
/*     */ 
/*     */ 
/*     */         
/* 185 */         throw this.wrapper.invalidCtbConverterName(illegalCharsetNameException, param1Entry.getName());
/* 186 */       } catch (UnsupportedCharsetException unsupportedCharsetException) {
/*     */ 
/*     */ 
/*     */         
/* 190 */         throw this.wrapper.invalidCtbConverterName(unsupportedCharsetException, param1Entry.getName());
/*     */       } 
/*     */       
/* 193 */       this.codeset = param1Entry;
/* 194 */       this.alignment = param1Int;
/*     */     }
/*     */     
/*     */     public final float getMaxBytesPerChar() {
/* 198 */       return this.ctb.maxBytesPerChar();
/*     */     }
/*     */     
/*     */     public void convert(char param1Char) {
/* 202 */       if (this.chars == null) {
/* 203 */         this.chars = new char[1];
/*     */       }
/*     */       
/* 206 */       this.chars[0] = param1Char;
/* 207 */       this.numChars = 1;
/*     */       
/* 209 */       convertCharArray();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void convert(String param1String) {
/* 217 */       if (this.chars == null || this.chars.length < param1String.length()) {
/* 218 */         this.chars = new char[param1String.length()];
/*     */       }
/* 220 */       this.numChars = param1String.length();
/*     */       
/* 222 */       param1String.getChars(0, this.numChars, this.chars, 0);
/*     */       
/* 224 */       convertCharArray();
/*     */     }
/*     */     
/*     */     public final int getNumBytes() {
/* 228 */       return this.numBytes;
/*     */     }
/*     */     
/*     */     public final int getAlignment() {
/* 232 */       return this.alignment;
/*     */     }
/*     */     
/*     */     public final boolean isFixedWidthEncoding() {
/* 236 */       return this.codeset.isFixedWidth();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] getBytes() {
/* 243 */       return this.buffer.array();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void convertCharArray() {
/*     */       try {
/* 262 */         this.buffer = this.ctb.encode(CharBuffer.wrap(this.chars, 0, this.numChars));
/*     */ 
/*     */ 
/*     */         
/* 266 */         this.numBytes = this.buffer.limit();
/*     */       }
/* 268 */       catch (IllegalStateException illegalStateException) {
/*     */         
/* 270 */         throw this.wrapper.ctbConverterFailure(illegalStateException);
/* 271 */       } catch (MalformedInputException malformedInputException) {
/*     */         
/* 273 */         throw this.wrapper.badUnicodePair(malformedInputException);
/* 274 */       } catch (UnmappableCharacterException unmappableCharacterException) {
/*     */ 
/*     */         
/* 277 */         throw this.omgWrapper.charNotInCodeset(unmappableCharacterException);
/* 278 */       } catch (CharacterCodingException characterCodingException) {
/*     */         
/* 280 */         throw this.wrapper.ctbConverterFailure(characterCodingException);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class UTF16CTBConverter
/*     */     extends JavaCTBConverter
/*     */   {
/*     */     public UTF16CTBConverter() {
/* 293 */       super(OSFCodeSetRegistry.UTF_16, 2);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public UTF16CTBConverter(boolean param1Boolean) {
/* 299 */       super(param1Boolean ? OSFCodeSetRegistry.UTF_16LE : OSFCodeSetRegistry.UTF_16BE, 2);
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
/*     */   private class JavaBTCConverter
/*     */     extends BTCConverter
/*     */   {
/* 313 */     private ORBUtilSystemException wrapper = ORBUtilSystemException.get("rpc.encoding");
/*     */ 
/*     */     
/* 316 */     private OMGSystemException omgWrapper = OMGSystemException.get("rpc.encoding");
/*     */     
/*     */     protected CharsetDecoder btc;
/*     */     
/*     */     private char[] buffer;
/*     */     
/*     */     private int resultingNumChars;
/*     */     
/*     */     private OSFCodeSetRegistry.Entry codeset;
/*     */     
/*     */     public JavaBTCConverter(OSFCodeSetRegistry.Entry param1Entry) {
/* 327 */       this.btc = getConverter(param1Entry.getName());
/*     */       
/* 329 */       this.codeset = param1Entry;
/*     */     }
/*     */     
/*     */     public final boolean isFixedWidthEncoding() {
/* 333 */       return this.codeset.isFixedWidth();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int getFixedCharWidth() {
/* 340 */       return this.codeset.getMaxBytesPerChar();
/*     */     }
/*     */     
/*     */     public final int getNumChars() {
/* 344 */       return this.resultingNumChars;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public char[] getChars(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/*     */       try {
/* 361 */         ByteBuffer byteBuffer = ByteBuffer.wrap(param1ArrayOfbyte, param1Int1, param1Int2);
/* 362 */         CharBuffer charBuffer = this.btc.decode(byteBuffer);
/*     */ 
/*     */ 
/*     */         
/* 366 */         this.resultingNumChars = charBuffer.limit();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 374 */         if (charBuffer.limit() == charBuffer.capacity()) {
/* 375 */           this.buffer = charBuffer.array();
/*     */         } else {
/* 377 */           this.buffer = new char[charBuffer.limit()];
/* 378 */           charBuffer.get(this.buffer, 0, charBuffer.limit()).position(0);
/*     */         } 
/*     */         
/* 381 */         return this.buffer;
/*     */       }
/* 383 */       catch (IllegalStateException illegalStateException) {
/*     */         
/* 385 */         throw this.wrapper.btcConverterFailure(illegalStateException);
/* 386 */       } catch (MalformedInputException malformedInputException) {
/*     */         
/* 388 */         throw this.wrapper.badUnicodePair(malformedInputException);
/* 389 */       } catch (UnmappableCharacterException unmappableCharacterException) {
/*     */ 
/*     */         
/* 392 */         throw this.omgWrapper.charNotInCodeset(unmappableCharacterException);
/* 393 */       } catch (CharacterCodingException characterCodingException) {
/*     */         
/* 395 */         throw this.wrapper.btcConverterFailure(characterCodingException);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected CharsetDecoder getConverter(String param1String) {
/* 406 */       CharsetDecoder charsetDecoder = null;
/*     */       try {
/* 408 */         charsetDecoder = CodeSetConversion.this.cache.getByteToCharConverter(param1String);
/*     */         
/* 410 */         if (charsetDecoder == null) {
/* 411 */           Charset charset = Charset.forName(param1String);
/* 412 */           charsetDecoder = charset.newDecoder();
/* 413 */           CodeSetConversion.this.cache.setConverter(param1String, charsetDecoder);
/*     */         }
/*     */       
/* 416 */       } catch (IllegalCharsetNameException illegalCharsetNameException) {
/*     */ 
/*     */         
/* 419 */         throw this.wrapper.invalidBtcConverterName(illegalCharsetNameException, param1String);
/*     */       } 
/*     */       
/* 422 */       return charsetDecoder;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class UTF16BTCConverter
/*     */     extends JavaBTCConverter
/*     */   {
/*     */     private boolean defaultToLittleEndian;
/*     */ 
/*     */     
/*     */     private boolean converterUsesBOM = true;
/*     */ 
/*     */     
/*     */     private static final char UTF16_BE_MARKER = '﻿';
/*     */ 
/*     */     
/*     */     private static final char UTF16_LE_MARKER = '￾';
/*     */ 
/*     */ 
/*     */     
/*     */     public UTF16BTCConverter(boolean param1Boolean) {
/* 445 */       super(OSFCodeSetRegistry.UTF_16);
/*     */       
/* 447 */       this.defaultToLittleEndian = param1Boolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public char[] getChars(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/* 452 */       if (hasUTF16ByteOrderMarker(param1ArrayOfbyte, param1Int1, param1Int2)) {
/* 453 */         if (!this.converterUsesBOM) {
/* 454 */           switchToConverter(OSFCodeSetRegistry.UTF_16);
/*     */         }
/* 456 */         this.converterUsesBOM = true;
/*     */         
/* 458 */         return super.getChars(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */       } 
/* 460 */       if (this.converterUsesBOM) {
/* 461 */         if (this.defaultToLittleEndian) {
/* 462 */           switchToConverter(OSFCodeSetRegistry.UTF_16LE);
/*     */         } else {
/* 464 */           switchToConverter(OSFCodeSetRegistry.UTF_16BE);
/*     */         } 
/* 466 */         this.converterUsesBOM = false;
/*     */       } 
/*     */       
/* 469 */       return super.getChars(param1ArrayOfbyte, param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean hasUTF16ByteOrderMarker(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/* 479 */       if (param1Int2 >= 4) {
/*     */         
/* 481 */         int i = param1ArrayOfbyte[param1Int1] & 0xFF;
/* 482 */         int j = param1ArrayOfbyte[param1Int1 + 1] & 0xFF;
/*     */         
/* 484 */         char c = (char)(i << 8 | j << 0);
/*     */         
/* 486 */         return (c == '﻿' || c == '￾');
/*     */       } 
/* 488 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void switchToConverter(OSFCodeSetRegistry.Entry param1Entry) {
/* 500 */       this.btc = getConverter(param1Entry.getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CTBConverter getCTBConverter(OSFCodeSetRegistry.Entry paramEntry) {
/* 510 */     boolean bool = !paramEntry.isFixedWidth() ? true : paramEntry.getMaxBytesPerChar();
/*     */     
/* 512 */     return new JavaCTBConverter(paramEntry, bool);
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
/*     */   public CTBConverter getCTBConverter(OSFCodeSetRegistry.Entry paramEntry, boolean paramBoolean1, boolean paramBoolean2) {
/* 535 */     if (paramEntry == OSFCodeSetRegistry.UCS_2) {
/* 536 */       return new UTF16CTBConverter(paramBoolean1);
/*     */     }
/*     */     
/* 539 */     if (paramEntry == OSFCodeSetRegistry.UTF_16) {
/* 540 */       if (paramBoolean2) {
/* 541 */         return new UTF16CTBConverter();
/*     */       }
/* 543 */       return new UTF16CTBConverter(paramBoolean1);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 558 */     boolean bool = !paramEntry.isFixedWidth() ? true : paramEntry.getMaxBytesPerChar();
/*     */     
/* 560 */     return new JavaCTBConverter(paramEntry, bool);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BTCConverter getBTCConverter(OSFCodeSetRegistry.Entry paramEntry) {
/* 567 */     return new JavaBTCConverter(paramEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BTCConverter getBTCConverter(OSFCodeSetRegistry.Entry paramEntry, boolean paramBoolean) {
/* 576 */     if (paramEntry == OSFCodeSetRegistry.UTF_16 || paramEntry == OSFCodeSetRegistry.UCS_2)
/*     */     {
/*     */       
/* 579 */       return new UTF16BTCConverter(paramBoolean);
/*     */     }
/* 581 */     return new JavaBTCConverter(paramEntry);
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
/*     */   private int selectEncoding(CodeSetComponentInfo.CodeSetComponent paramCodeSetComponent1, CodeSetComponentInfo.CodeSetComponent paramCodeSetComponent2) {
/* 599 */     int i = paramCodeSetComponent2.nativeCodeSet;
/*     */     
/* 601 */     if (i == 0) {
/* 602 */       if (paramCodeSetComponent2.conversionCodeSets.length > 0) {
/* 603 */         i = paramCodeSetComponent2.conversionCodeSets[0];
/*     */       } else {
/* 605 */         return 0;
/*     */       } 
/*     */     }
/* 608 */     if (paramCodeSetComponent1.nativeCodeSet == i)
/*     */     {
/* 610 */       return i;
/*     */     }
/*     */     
/*     */     byte b;
/*     */     
/* 615 */     for (b = 0; b < paramCodeSetComponent1.conversionCodeSets.length; b++) {
/* 616 */       if (i == paramCodeSetComponent1.conversionCodeSets[b])
/*     */       {
/*     */         
/* 619 */         return i;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 625 */     for (b = 0; b < paramCodeSetComponent2.conversionCodeSets.length; b++) {
/* 626 */       if (paramCodeSetComponent1.nativeCodeSet == paramCodeSetComponent2.conversionCodeSets[b])
/*     */       {
/*     */         
/* 629 */         return paramCodeSetComponent1.nativeCodeSet;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 636 */     for (b = 0; b < paramCodeSetComponent2.conversionCodeSets.length; b++) {
/* 637 */       for (byte b1 = 0; b1 < paramCodeSetComponent1.conversionCodeSets.length; b1++) {
/* 638 */         if (paramCodeSetComponent2.conversionCodeSets[b] == paramCodeSetComponent1.conversionCodeSets[b1]) {
/* 639 */           return paramCodeSetComponent2.conversionCodeSets[b];
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 652 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodeSetComponentInfo.CodeSetContext negotiate(CodeSetComponentInfo paramCodeSetComponentInfo1, CodeSetComponentInfo paramCodeSetComponentInfo2) {
/* 662 */     int i = selectEncoding(paramCodeSetComponentInfo1.getCharComponent(), paramCodeSetComponentInfo2
/* 663 */         .getCharComponent());
/*     */     
/* 665 */     if (i == 0) {
/* 666 */       i = OSFCodeSetRegistry.UTF_8.getNumber();
/*     */     }
/*     */ 
/*     */     
/* 670 */     int j = selectEncoding(paramCodeSetComponentInfo1.getWCharComponent(), paramCodeSetComponentInfo2
/* 671 */         .getWCharComponent());
/*     */     
/* 673 */     if (j == 0) {
/* 674 */       j = OSFCodeSetRegistry.UTF_16.getNumber();
/*     */     }
/*     */     
/* 677 */     return new CodeSetComponentInfo.CodeSetContext(i, j);
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
/*     */   private CodeSetConversion() {
/* 706 */     this.cache = new CodeSetCache();
/*     */   }
/*     */   
/*     */   private static class CodeSetConversionHolder {
/*     */     static final CodeSetConversion csc = new CodeSetConversion();
/*     */   }
/*     */   
/*     */   public static final CodeSetConversion impl() {
/*     */     return CodeSetConversionHolder.csc;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CodeSetConversion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */