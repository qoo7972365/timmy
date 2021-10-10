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
/*     */ public class CDRInputStream_1_1
/*     */   extends CDRInputStream_1_0
/*     */ {
/*  32 */   protected int fragmentOffset = 0;
/*     */   
/*     */   public GIOPVersion getGIOPVersion() {
/*  35 */     return GIOPVersion.V1_1;
/*     */   }
/*     */ 
/*     */   
/*     */   public CDRInputStreamBase dup() {
/*  40 */     CDRInputStreamBase cDRInputStreamBase = super.dup();
/*     */     
/*  42 */     ((CDRInputStream_1_1)cDRInputStreamBase).fragmentOffset = this.fragmentOffset;
/*     */     
/*  44 */     return cDRInputStreamBase;
/*     */   }
/*     */   
/*     */   protected int get_offset() {
/*  48 */     return this.bbwi.position() + this.fragmentOffset;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void alignAndCheck(int paramInt1, int paramInt2) {
/*  54 */     checkBlockLength(paramInt1, paramInt2);
/*     */ 
/*     */ 
/*     */     
/*  58 */     int i = computeAlignment(this.bbwi.position(), paramInt1);
/*     */     
/*  60 */     if (this.bbwi.position() + paramInt2 + i > this.bbwi.buflen) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  65 */       if (this.bbwi.position() + i == this.bbwi.buflen)
/*     */       {
/*  67 */         this.bbwi.position(this.bbwi.position() + i);
/*     */       }
/*     */       
/*  70 */       grow(paramInt1, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  76 */       i = computeAlignment(this.bbwi.position(), paramInt1);
/*     */     } 
/*     */     
/*  79 */     this.bbwi.position(this.bbwi.position() + i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void grow(int paramInt1, int paramInt2) {
/*  87 */     this.bbwi.needed = paramInt2;
/*     */ 
/*     */ 
/*     */     
/*  91 */     int i = this.bbwi.position();
/*     */     
/*  93 */     this.bbwi = this.bufferManagerRead.underflow(this.bbwi);
/*     */     
/*  95 */     if (this.bbwi.fragmented) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       this.fragmentOffset += i - this.bbwi.position();
/*     */       
/* 103 */       this.markAndResetHandler.fragmentationOccured(this.bbwi);
/*     */ 
/*     */       
/* 106 */       this.bbwi.fragmented = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class FragmentableStreamMemento
/*     */     extends CDRInputStream_1_0.StreamMemento
/*     */   {
/*     */     private int fragmentOffset_;
/*     */ 
/*     */ 
/*     */     
/*     */     public FragmentableStreamMemento() {
/* 120 */       this.fragmentOffset_ = CDRInputStream_1_1.this.fragmentOffset;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object createStreamMemento() {
/* 125 */     return new FragmentableStreamMemento();
/*     */   }
/*     */ 
/*     */   
/*     */   public void restoreInternalState(Object paramObject) {
/* 130 */     super.restoreInternalState(paramObject);
/*     */     
/* 132 */     this
/* 133 */       .fragmentOffset = ((FragmentableStreamMemento)paramObject).fragmentOffset_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public char read_wchar() {
/* 143 */     alignAndCheck(2, 2);
/*     */ 
/*     */ 
/*     */     
/* 147 */     char[] arrayOfChar = getConvertedChars(2, getWCharConverter());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     if (getWCharConverter().getNumChars() > 1) {
/* 154 */       throw this.wrapper.btcResultMoreThanOneChar();
/*     */     }
/* 156 */     return arrayOfChar[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String read_wstring() {
/* 162 */     int i = read_long();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     if (i == 0) {
/* 171 */       return new String("");
/*     */     }
/* 173 */     checkForNegativeLength(i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     i--;
/*     */     
/* 181 */     char[] arrayOfChar = getConvertedChars(i * 2, getWCharConverter());
/*     */ 
/*     */     
/* 184 */     read_short();
/*     */     
/* 186 */     return new String(arrayOfChar, 0, getWCharConverter().getNumChars());
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CDRInputStream_1_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */