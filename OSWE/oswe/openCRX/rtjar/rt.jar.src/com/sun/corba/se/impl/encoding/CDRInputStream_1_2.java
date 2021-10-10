/*     */ package com.sun.corba.se.impl.encoding;
/*     */ 
/*     */ import com.sun.corba.se.spi.ior.iiop.GIOPVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CDRInputStream_1_2
/*     */   extends CDRInputStream_1_1
/*     */ {
/*     */   protected boolean headerPadding;
/*     */   protected boolean restoreHeaderPadding;
/*     */   
/*     */   void setHeaderPadding(boolean paramBoolean) {
/*  43 */     this.headerPadding = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mark(int paramInt) {
/*  50 */     super.mark(paramInt);
/*  51 */     this.restoreHeaderPadding = this.headerPadding;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  55 */     super.reset();
/*  56 */     this.headerPadding = this.restoreHeaderPadding;
/*  57 */     this.restoreHeaderPadding = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CDRInputStreamBase dup() {
/*  64 */     CDRInputStreamBase cDRInputStreamBase = super.dup();
/*  65 */     ((CDRInputStream_1_2)cDRInputStreamBase).headerPadding = this.headerPadding;
/*  66 */     return cDRInputStreamBase;
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
/*     */   protected void alignAndCheck(int paramInt1, int paramInt2) {
/*  78 */     if (this.headerPadding == true) {
/*  79 */       this.headerPadding = false;
/*  80 */       alignOnBoundary(8);
/*     */     } 
/*     */     
/*  83 */     checkBlockLength(paramInt1, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     int i = computeAlignment(this.bbwi.position(), paramInt1);
/*  95 */     this.bbwi.position(this.bbwi.position() + i);
/*     */     
/*  97 */     if (this.bbwi.position() + paramInt2 > this.bbwi.buflen) {
/*  98 */       grow(1, paramInt2);
/*     */     }
/*     */   }
/*     */   
/*     */   public GIOPVersion getGIOPVersion() {
/* 103 */     return GIOPVersion.V1_2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public char read_wchar() {
/* 109 */     byte b = read_octet();
/*     */     
/* 111 */     char[] arrayOfChar = getConvertedChars(b, getWCharConverter());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     if (getWCharConverter().getNumChars() > 1) {
/* 118 */       throw this.wrapper.btcResultMoreThanOneChar();
/*     */     }
/* 120 */     return arrayOfChar[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String read_wstring() {
/* 129 */     int i = read_long();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (i == 0) {
/* 136 */       return new String("");
/*     */     }
/* 138 */     checkForNegativeLength(i);
/*     */     
/* 140 */     return new String(getConvertedChars(i, getWCharConverter()), 0, 
/*     */         
/* 142 */         getWCharConverter().getNumChars());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CDRInputStream_1_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */