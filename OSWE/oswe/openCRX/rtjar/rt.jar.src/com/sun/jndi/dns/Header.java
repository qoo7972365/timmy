/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import javax.naming.CommunicationException;
/*     */ import javax.naming.NamingException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Header
/*     */ {
/*     */   static final int HEADER_SIZE = 12;
/*     */   static final short QR_BIT = -32768;
/*     */   static final short OPCODE_MASK = 30720;
/*     */   static final int OPCODE_SHIFT = 11;
/*     */   static final short AA_BIT = 1024;
/*     */   static final short TC_BIT = 512;
/*     */   static final short RD_BIT = 256;
/*     */   static final short RA_BIT = 128;
/*     */   static final short RCODE_MASK = 15;
/*     */   int xid;
/*     */   boolean query;
/*     */   int opcode;
/*     */   boolean authoritative;
/*     */   boolean truncated;
/*     */   boolean recursionDesired;
/*     */   boolean recursionAvail;
/*     */   int rcode;
/*     */   int numQuestions;
/*     */   int numAnswers;
/*     */   int numAuthorities;
/*     */   int numAdditionals;
/*     */   
/*     */   Header(byte[] paramArrayOfbyte, int paramInt) throws NamingException {
/*  71 */     decode(paramArrayOfbyte, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void decode(byte[] paramArrayOfbyte, int paramInt) throws NamingException {
/*     */     try {
/*  81 */       boolean bool = false;
/*     */       
/*  83 */       if (paramInt < 12) {
/*  84 */         throw new CommunicationException("DNS error: corrupted message header");
/*     */       }
/*     */ 
/*     */       
/*  88 */       this.xid = getShort(paramArrayOfbyte, bool);
/*  89 */       bool += true;
/*     */ 
/*     */       
/*  92 */       short s = (short)getShort(paramArrayOfbyte, bool);
/*  93 */       bool += true;
/*  94 */       this.query = ((s & Short.MIN_VALUE) == 0);
/*  95 */       this.opcode = (s & 0x7800) >>> 11;
/*  96 */       this.authoritative = ((s & 0x400) != 0);
/*  97 */       this.truncated = ((s & 0x200) != 0);
/*  98 */       this.recursionDesired = ((s & 0x100) != 0);
/*  99 */       this.recursionAvail = ((s & 0x80) != 0);
/* 100 */       this.rcode = s & 0xF;
/*     */ 
/*     */       
/* 103 */       this.numQuestions = getShort(paramArrayOfbyte, bool);
/* 104 */       bool += true;
/* 105 */       this.numAnswers = getShort(paramArrayOfbyte, bool);
/* 106 */       bool += true;
/* 107 */       this.numAuthorities = getShort(paramArrayOfbyte, bool);
/* 108 */       bool += true;
/* 109 */       this.numAdditionals = getShort(paramArrayOfbyte, bool);
/* 110 */       bool += true;
/*     */     }
/* 112 */     catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 113 */       throw new CommunicationException("DNS error: corrupted message header");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getShort(byte[] paramArrayOfbyte, int paramInt) {
/* 123 */     return (paramArrayOfbyte[paramInt] & 0xFF) << 8 | paramArrayOfbyte[paramInt + 1] & 0xFF;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/Header.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */