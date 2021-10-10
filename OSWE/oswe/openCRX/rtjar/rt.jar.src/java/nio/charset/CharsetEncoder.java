/*     */ package java.nio.charset;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.nio.BufferOverflowException;
/*     */ import java.nio.BufferUnderflowException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.CharBuffer;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CharsetEncoder
/*     */ {
/*     */   private final Charset charset;
/*     */   private final float averageBytesPerChar;
/*     */   private final float maxBytesPerChar;
/*     */   private byte[] replacement;
/* 144 */   private CodingErrorAction malformedInputAction = CodingErrorAction.REPORT;
/*     */   
/* 146 */   private CodingErrorAction unmappableCharacterAction = CodingErrorAction.REPORT;
/*     */   
/*     */   private static final int ST_RESET = 0;
/*     */   
/*     */   private static final int ST_CODING = 1;
/*     */   
/*     */   private static final int ST_END = 2;
/*     */   
/*     */   private static final int ST_FLUSHED = 3;
/*     */   
/* 156 */   private int state = 0;
/*     */   
/* 158 */   private static String[] stateNames = new String[] { "RESET", "CODING", "CODING_END", "FLUSHED" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WeakReference<CharsetDecoder> cachedDecoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CharsetEncoder(Charset paramCharset, float paramFloat1, float paramFloat2) {
/* 233 */     this(paramCharset, paramFloat1, paramFloat2, new byte[] { 63 });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Charset charset() {
/* 244 */     return this.charset;
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
/*     */   public final byte[] replacement() {
/* 258 */     return Arrays.copyOf(this.replacement, this.replacement.length);
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
/*     */   public final CharsetEncoder replaceWith(byte[] paramArrayOfbyte) {
/* 288 */     if (paramArrayOfbyte == null)
/* 289 */       throw new IllegalArgumentException("Null replacement"); 
/* 290 */     int i = paramArrayOfbyte.length;
/* 291 */     if (i == 0)
/* 292 */       throw new IllegalArgumentException("Empty replacement"); 
/* 293 */     if (i > this.maxBytesPerChar) {
/* 294 */       throw new IllegalArgumentException("Replacement too long");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 299 */     if (!isLegalReplacement(paramArrayOfbyte))
/* 300 */       throw new IllegalArgumentException("Illegal replacement"); 
/* 301 */     this.replacement = Arrays.copyOf(paramArrayOfbyte, paramArrayOfbyte.length);
/*     */     
/* 303 */     implReplaceWith(this.replacement);
/* 304 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void implReplaceWith(byte[] paramArrayOfbyte) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected CharsetEncoder(Charset paramCharset, float paramFloat1, float paramFloat2, byte[] paramArrayOfbyte) {
/* 321 */     this.cachedDecoder = null;
/*     */     this.charset = paramCharset;
/*     */     if (paramFloat1 <= 0.0F) {
/*     */       throw new IllegalArgumentException("Non-positive averageBytesPerChar");
/*     */     }
/*     */     if (paramFloat2 <= 0.0F) {
/*     */       throw new IllegalArgumentException("Non-positive maxBytesPerChar");
/*     */     }
/*     */     if (!Charset.atBugLevel("1.4") && paramFloat1 > paramFloat2) {
/*     */       throw new IllegalArgumentException("averageBytesPerChar exceeds maxBytesPerChar");
/*     */     }
/*     */     this.replacement = paramArrayOfbyte;
/*     */     this.averageBytesPerChar = paramFloat1;
/*     */     this.maxBytesPerChar = paramFloat2;
/*     */     replaceWith(paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLegalReplacement(byte[] paramArrayOfbyte) {
/* 340 */     WeakReference<CharsetDecoder> weakReference = this.cachedDecoder;
/* 341 */     CharsetDecoder charsetDecoder = null;
/* 342 */     if (weakReference == null || (charsetDecoder = weakReference.get()) == null) {
/* 343 */       charsetDecoder = charset().newDecoder();
/* 344 */       charsetDecoder.onMalformedInput(CodingErrorAction.REPORT);
/* 345 */       charsetDecoder.onUnmappableCharacter(CodingErrorAction.REPORT);
/* 346 */       this.cachedDecoder = new WeakReference<>(charsetDecoder);
/*     */     } else {
/* 348 */       charsetDecoder.reset();
/*     */     } 
/* 350 */     ByteBuffer byteBuffer = ByteBuffer.wrap(paramArrayOfbyte);
/* 351 */     CharBuffer charBuffer = CharBuffer.allocate(
/* 352 */         (int)(byteBuffer.remaining() * charsetDecoder.maxCharsPerByte()));
/* 353 */     CoderResult coderResult = charsetDecoder.decode(byteBuffer, charBuffer, true);
/* 354 */     return !coderResult.isError();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodingErrorAction malformedInputAction() {
/* 365 */     return this.malformedInputAction;
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
/*     */   public final CharsetEncoder onMalformedInput(CodingErrorAction paramCodingErrorAction) {
/* 382 */     if (paramCodingErrorAction == null)
/* 383 */       throw new IllegalArgumentException("Null action"); 
/* 384 */     this.malformedInputAction = paramCodingErrorAction;
/* 385 */     implOnMalformedInput(paramCodingErrorAction);
/* 386 */     return this;
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
/*     */   protected void implOnMalformedInput(CodingErrorAction paramCodingErrorAction) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CodingErrorAction unmappableCharacterAction() {
/* 407 */     return this.unmappableCharacterAction;
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
/*     */   public final CharsetEncoder onUnmappableCharacter(CodingErrorAction paramCodingErrorAction) {
/* 426 */     if (paramCodingErrorAction == null)
/* 427 */       throw new IllegalArgumentException("Null action"); 
/* 428 */     this.unmappableCharacterAction = paramCodingErrorAction;
/* 429 */     implOnUnmappableCharacter(paramCodingErrorAction);
/* 430 */     return this;
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
/*     */   protected void implOnUnmappableCharacter(CodingErrorAction paramCodingErrorAction) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float averageBytesPerChar() {
/* 453 */     return this.averageBytesPerChar;
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
/*     */   public final float maxBytesPerChar() {
/* 465 */     return this.maxBytesPerChar;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CoderResult encode(CharBuffer paramCharBuffer, ByteBuffer paramByteBuffer, boolean paramBoolean) {
/* 569 */     boolean bool = paramBoolean ? true : true;
/* 570 */     if (this.state != 0 && this.state != 1 && (!paramBoolean || this.state != 2))
/*     */     {
/* 572 */       throwIllegalStateException(this.state, bool); } 
/* 573 */     this.state = bool;
/*     */     
/*     */     while (true) {
/*     */       CoderResult coderResult;
/*     */       
/*     */       try {
/* 579 */         coderResult = encodeLoop(paramCharBuffer, paramByteBuffer);
/* 580 */       } catch (BufferUnderflowException bufferUnderflowException) {
/* 581 */         throw new CoderMalfunctionError(bufferUnderflowException);
/* 582 */       } catch (BufferOverflowException bufferOverflowException) {
/* 583 */         throw new CoderMalfunctionError(bufferOverflowException);
/*     */       } 
/*     */       
/* 586 */       if (coderResult.isOverflow()) {
/* 587 */         return coderResult;
/*     */       }
/* 589 */       if (coderResult.isUnderflow()) {
/* 590 */         if (paramBoolean && paramCharBuffer.hasRemaining()) {
/* 591 */           coderResult = CoderResult.malformedForLength(paramCharBuffer.remaining());
/*     */         } else {
/*     */           
/* 594 */           return coderResult;
/*     */         } 
/*     */       }
/*     */       
/* 598 */       CodingErrorAction codingErrorAction = null;
/* 599 */       if (coderResult.isMalformed()) {
/* 600 */         codingErrorAction = this.malformedInputAction;
/* 601 */       } else if (coderResult.isUnmappable()) {
/* 602 */         codingErrorAction = this.unmappableCharacterAction;
/*     */       } else {
/* 604 */         assert false : coderResult.toString();
/*     */       } 
/* 606 */       if (codingErrorAction == CodingErrorAction.REPORT) {
/* 607 */         return coderResult;
/*     */       }
/* 609 */       if (codingErrorAction == CodingErrorAction.REPLACE) {
/* 610 */         if (paramByteBuffer.remaining() < this.replacement.length)
/* 611 */           return CoderResult.OVERFLOW; 
/* 612 */         paramByteBuffer.put(this.replacement);
/*     */       } 
/*     */       
/* 615 */       if (codingErrorAction == CodingErrorAction.IGNORE || codingErrorAction == CodingErrorAction.REPLACE) {
/*     */ 
/*     */         
/* 618 */         paramCharBuffer.position(paramCharBuffer.position() + coderResult.length());
/*     */         continue;
/*     */       } 
/*     */       assert false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CoderResult flush(ByteBuffer paramByteBuffer) {
/* 667 */     if (this.state == 2) {
/* 668 */       CoderResult coderResult = implFlush(paramByteBuffer);
/* 669 */       if (coderResult.isUnderflow())
/* 670 */         this.state = 3; 
/* 671 */       return coderResult;
/*     */     } 
/*     */     
/* 674 */     if (this.state != 3) {
/* 675 */       throwIllegalStateException(this.state, 3);
/*     */     }
/* 677 */     return CoderResult.UNDERFLOW;
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
/*     */   protected CoderResult implFlush(ByteBuffer paramByteBuffer) {
/* 695 */     return CoderResult.UNDERFLOW;
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
/*     */   public final CharsetEncoder reset() {
/* 709 */     implReset();
/* 710 */     this.state = 0;
/* 711 */     return this;
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
/*     */   protected void implReset() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ByteBuffer encode(CharBuffer paramCharBuffer) throws CharacterCodingException {
/* 794 */     int i = (int)(paramCharBuffer.remaining() * averageBytesPerChar());
/* 795 */     ByteBuffer byteBuffer = ByteBuffer.allocate(i);
/*     */     
/* 797 */     if (i == 0 && paramCharBuffer.remaining() == 0)
/* 798 */       return byteBuffer; 
/* 799 */     reset();
/*     */     
/*     */     while (true) {
/* 802 */       CoderResult coderResult = paramCharBuffer.hasRemaining() ? encode(paramCharBuffer, byteBuffer, true) : CoderResult.UNDERFLOW;
/* 803 */       if (coderResult.isUnderflow()) {
/* 804 */         coderResult = flush(byteBuffer);
/*     */       }
/* 806 */       if (coderResult.isUnderflow())
/*     */         break; 
/* 808 */       if (coderResult.isOverflow()) {
/* 809 */         i = 2 * i + 1;
/* 810 */         ByteBuffer byteBuffer1 = ByteBuffer.allocate(i);
/* 811 */         byteBuffer.flip();
/* 812 */         byteBuffer1.put(byteBuffer);
/* 813 */         byteBuffer = byteBuffer1;
/*     */         continue;
/*     */       } 
/* 816 */       coderResult.throwException();
/*     */     } 
/* 818 */     byteBuffer.flip();
/* 819 */     return byteBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canEncode(CharBuffer paramCharBuffer) {
/* 901 */     if (this.state == 3) {
/* 902 */       reset();
/* 903 */     } else if (this.state != 0) {
/* 904 */       throwIllegalStateException(this.state, 1);
/* 905 */     }  CodingErrorAction codingErrorAction1 = malformedInputAction();
/* 906 */     CodingErrorAction codingErrorAction2 = unmappableCharacterAction();
/*     */     try {
/* 908 */       onMalformedInput(CodingErrorAction.REPORT);
/* 909 */       onUnmappableCharacter(CodingErrorAction.REPORT);
/* 910 */       encode(paramCharBuffer);
/* 911 */     } catch (CharacterCodingException characterCodingException) {
/* 912 */       return false;
/*     */     } finally {
/* 914 */       onMalformedInput(codingErrorAction1);
/* 915 */       onUnmappableCharacter(codingErrorAction2);
/* 916 */       reset();
/*     */     } 
/* 918 */     return true;
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
/*     */   public boolean canEncode(char paramChar) {
/* 948 */     CharBuffer charBuffer = CharBuffer.allocate(1);
/* 949 */     charBuffer.put(paramChar);
/* 950 */     charBuffer.flip();
/* 951 */     return canEncode(charBuffer);
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
/*     */   public boolean canEncode(CharSequence paramCharSequence) {
/*     */     CharBuffer charBuffer;
/* 981 */     if (paramCharSequence instanceof CharBuffer) {
/* 982 */       charBuffer = ((CharBuffer)paramCharSequence).duplicate();
/*     */     } else {
/* 984 */       charBuffer = CharBuffer.wrap(paramCharSequence.toString());
/* 985 */     }  return canEncode(charBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwIllegalStateException(int paramInt1, int paramInt2) {
/* 992 */     throw new IllegalStateException("Current state = " + stateNames[paramInt1] + ", new state = " + stateNames[paramInt2]);
/*     */   }
/*     */   
/*     */   protected abstract CoderResult encodeLoop(CharBuffer paramCharBuffer, ByteBuffer paramByteBuffer);
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/nio/charset/CharsetEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */