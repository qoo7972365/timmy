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
/*     */ public class CDROutputStream_1_1
/*     */   extends CDROutputStream_1_0
/*     */ {
/*  46 */   protected int fragmentOffset = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void alignAndReserve(int paramInt1, int paramInt2) {
/*  58 */     int i = computeAlignment(paramInt1);
/*     */     
/*  60 */     if (this.bbwi.position() + paramInt2 + i > this.bbwi.buflen) {
/*  61 */       grow(paramInt1, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  70 */       i = computeAlignment(paramInt1);
/*     */     } 
/*     */     
/*  73 */     this.bbwi.position(this.bbwi.position() + i);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void grow(int paramInt1, int paramInt2) {
/*  78 */     int i = this.bbwi.position();
/*     */     
/*  80 */     super.grow(paramInt1, paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     if (this.bbwi.fragmented) {
/*     */ 
/*     */       
/*  88 */       this.bbwi.fragmented = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       this.fragmentOffset += i - this.bbwi.position();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int get_offset() {
/*  99 */     return this.bbwi.position() + this.fragmentOffset;
/*     */   }
/*     */   
/*     */   public GIOPVersion getGIOPVersion() {
/* 103 */     return GIOPVersion.V1_1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write_wchar(char paramChar) {
/* 112 */     CodeSetConversion.CTBConverter cTBConverter = getWCharConverter();
/*     */     
/* 114 */     cTBConverter.convert(paramChar);
/*     */     
/* 116 */     if (cTBConverter.getNumBytes() != 2) {
/* 117 */       throw this.wrapper.badGiop11Ctb(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/* 119 */     alignAndReserve(cTBConverter.getAlignment(), cTBConverter
/* 120 */         .getNumBytes());
/*     */     
/* 122 */     this.parent.write_octet_array(cTBConverter.getBytes(), 0, cTBConverter
/*     */         
/* 124 */         .getNumBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   public void write_wstring(String paramString) {
/* 129 */     if (paramString == null) {
/* 130 */       throw this.wrapper.nullParam(CompletionStatus.COMPLETED_MAYBE);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     int i = paramString.length() + 1;
/*     */     
/* 138 */     write_long(i);
/*     */     
/* 140 */     CodeSetConversion.CTBConverter cTBConverter = getWCharConverter();
/*     */     
/* 142 */     cTBConverter.convert(paramString);
/*     */     
/* 144 */     internalWriteOctetArray(cTBConverter.getBytes(), 0, cTBConverter.getNumBytes());
/*     */ 
/*     */     
/* 147 */     write_short((short)0);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/encoding/CDROutputStream_1_1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */