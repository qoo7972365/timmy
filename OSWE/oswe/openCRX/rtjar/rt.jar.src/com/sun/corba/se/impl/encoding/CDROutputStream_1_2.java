/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ import org.omg.CORBA.CompletionStatus;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CDROutputStream_1_2
/*     */   extends CDROutputStream_1_1
/*     */ {
/*     */   protected boolean primitiveAcrossFragmentedChunk = false;
/*     */   protected boolean specialChunk = false;
/*     */   private boolean headerPadding;
/*     */   
/*     */   protected void handleSpecialChunkBegin(int paramInt) {
/*  91 */     if (this.inBlock && paramInt + this.bbwi.position() > this.bbwi.buflen) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  96 */       int i = this.bbwi.position();
/*  97 */       this.bbwi.position(this.blockSizeIndex - 4);
/*     */ 
/*     */       
/* 100 */       writeLongWithoutAlign(i - this.blockSizeIndex + paramInt);
/* 101 */       this.bbwi.position(i);
/*     */ 
/*     */ 
/*     */       
/* 105 */       this.specialChunk = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleSpecialChunkEnd() {
/* 112 */     if (this.inBlock && this.specialChunk) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       this.inBlock = false;
/* 118 */       this.blockSizeIndex = -1;
/* 119 */       this.blockSizePosition = -1;
/*     */ 
/*     */ 
/*     */       
/* 123 */       start_block();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       this.specialChunk = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkPrimitiveAcrossFragmentedChunk() {
/* 135 */     if (this.primitiveAcrossFragmentedChunk) {
/* 136 */       this.primitiveAcrossFragmentedChunk = false;
/*     */       
/* 138 */       this.inBlock = false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       this.blockSizeIndex = -1;
/* 144 */       this.blockSizePosition = -1;
/*     */ 
/*     */       
/* 147 */       start_block();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write_octet(byte paramByte) {
/* 153 */     super.write_octet(paramByte);
/* 154 */     checkPrimitiveAcrossFragmentedChunk();
/*     */   }
/*     */   
/*     */   public void write_short(short paramShort) {
/* 158 */     super.write_short(paramShort);
/* 159 */     checkPrimitiveAcrossFragmentedChunk();
/*     */   }
/*     */   
/*     */   public void write_long(int paramInt) {
/* 163 */     super.write_long(paramInt);
/* 164 */     checkPrimitiveAcrossFragmentedChunk();
/*     */   }
/*     */   
/*     */   public void write_longlong(long paramLong) {
/* 168 */     super.write_longlong(paramLong);
/* 169 */     checkPrimitiveAcrossFragmentedChunk();
/*     */   }
/*     */ 
/*     */   
/*     */   void setHeaderPadding(boolean paramBoolean) {
/* 174 */     this.headerPadding = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void alignAndReserve(int paramInt1, int paramInt2) {
/* 185 */     if (this.headerPadding == true) {
/* 186 */       this.headerPadding = false;
/* 187 */       alignOnBoundary(8);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     this.bbwi.position(this.bbwi.position() + computeAlignment(paramInt1));
/*     */     
/* 199 */     if (this.bbwi.position() + paramInt2 > this.bbwi.buflen) {
/* 200 */       grow(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void grow(int paramInt1, int paramInt2) {
/* 206 */     int i = this.bbwi.position();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     boolean bool = (this.inBlock && !this.specialChunk) ? true : false;
/* 218 */     if (bool) {
/* 219 */       int j = this.bbwi.position();
/*     */       
/* 221 */       this.bbwi.position(this.blockSizeIndex - 4);
/*     */       
/* 223 */       writeLongWithoutAlign(j - this.blockSizeIndex + paramInt2);
/*     */       
/* 225 */       this.bbwi.position(j);
/*     */     } 
/*     */     
/* 228 */     this.bbwi.needed = paramInt2;
/* 229 */     this.bufferManagerWrite.overflow(this.bbwi);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 236 */     if (this.bbwi.fragmented) {
/*     */ 
/*     */       
/* 239 */       this.bbwi.fragmented = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       this.fragmentOffset += i - this.bbwi.position();
/*     */ 
/*     */ 
/*     */       
/* 249 */       if (bool) {
/* 250 */         this.primitiveAcrossFragmentedChunk = true;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public GIOPVersion getGIOPVersion() {
/* 256 */     return GIOPVersion.V1_2;
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
/*     */   public void write_wchar(char paramChar) {
/* 269 */     CodeSetConversion.CTBConverter cTBConverter = getWCharConverter();
/*     */     
/* 271 */     cTBConverter.convert(paramChar);
/*     */     
/* 273 */     handleSpecialChunkBegin(1 + cTBConverter.getNumBytes());
/*     */     
/* 275 */     write_octet((byte)cTBConverter.getNumBytes());
/*     */     
/* 277 */     byte[] arrayOfByte = cTBConverter.getBytes();
/*     */ 
/*     */ 
/*     */     
/* 281 */     internalWriteOctetArray(arrayOfByte, 0, cTBConverter.getNumBytes());
/*     */     
/* 283 */     handleSpecialChunkEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public void write_wchar_array(char[] paramArrayOfchar, int paramInt1, int paramInt2) {
/* 288 */     if (paramArrayOfchar == null) {
/* 289 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*     */     
/* 292 */     CodeSetConversion.CTBConverter cTBConverter = getWCharConverter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     int i = 0;
/*     */ 
/*     */ 
/*     */     
/* 302 */     int j = (int)Math.ceil((cTBConverter.getMaxBytesPerChar() * paramInt2));
/* 303 */     byte[] arrayOfByte = new byte[j + paramInt2];
/*     */     
/* 305 */     for (byte b = 0; b < paramInt2; b++) {
/*     */       
/* 307 */       cTBConverter.convert(paramArrayOfchar[paramInt1 + b]);
/*     */ 
/*     */       
/* 310 */       arrayOfByte[i++] = (byte)cTBConverter.getNumBytes();
/*     */ 
/*     */       
/* 313 */       System.arraycopy(cTBConverter.getBytes(), 0, arrayOfByte, i, cTBConverter
/*     */           
/* 315 */           .getNumBytes());
/*     */       
/* 317 */       i += cTBConverter.getNumBytes();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 323 */     handleSpecialChunkBegin(i);
/*     */ 
/*     */ 
/*     */     
/* 327 */     internalWriteOctetArray(arrayOfByte, 0, i);
/*     */     
/* 329 */     handleSpecialChunkEnd();
/*     */   }
/*     */   
/*     */   public void write_wstring(String paramString) {
/* 333 */     if (paramString == null) {
/* 334 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 341 */     if (paramString.length() == 0) {
/* 342 */       write_long(0);
/*     */       
/*     */       return;
/*     */     } 
/* 346 */     CodeSetConversion.CTBConverter cTBConverter = getWCharConverter();
/*     */     
/* 348 */     cTBConverter.convert(paramString);
/*     */     
/* 350 */     handleSpecialChunkBegin(computeAlignment(4) + 4 + cTBConverter.getNumBytes());
/*     */     
/* 352 */     write_long(cTBConverter.getNumBytes());
/*     */ 
/*     */     
/* 355 */     internalWriteOctetArray(cTBConverter.getBytes(), 0, cTBConverter.getNumBytes());
/*     */     
/* 357 */     handleSpecialChunkEnd();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CDROutputStream_1_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */