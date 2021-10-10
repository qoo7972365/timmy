/*     */ package com.sun.jndi.dns;
/*     */ 
/*     */ import java.util.Vector;
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
/*     */ class ResourceRecords
/*     */ {
/*  48 */   Vector<ResourceRecord> question = new Vector<>();
/*  49 */   Vector<ResourceRecord> answer = new Vector<>();
/*  50 */   Vector<ResourceRecord> authority = new Vector<>();
/*  51 */   Vector<ResourceRecord> additional = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean zoneXfer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ResourceRecords(byte[] paramArrayOfbyte, int paramInt, Header paramHeader, boolean paramBoolean) throws NamingException {
/*  68 */     if (paramBoolean) {
/*  69 */       this.answer.ensureCapacity(8192);
/*     */     }
/*  71 */     this.zoneXfer = paramBoolean;
/*  72 */     add(paramArrayOfbyte, paramInt, paramHeader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getFirstAnsType() {
/*  80 */     if (this.answer.size() == 0) {
/*  81 */       return -1;
/*     */     }
/*  83 */     return ((ResourceRecord)this.answer.firstElement()).getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getLastAnsType() {
/*  91 */     if (this.answer.size() == 0) {
/*  92 */       return -1;
/*     */     }
/*  94 */     return ((ResourceRecord)this.answer.lastElement()).getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void add(byte[] paramArrayOfbyte, int paramInt, Header paramHeader) throws NamingException {
/* 105 */     int i = 12;
/*     */     try {
/*     */       byte b;
/* 108 */       for (b = 0; b < paramHeader.numQuestions; b++) {
/* 109 */         ResourceRecord resourceRecord = new ResourceRecord(paramArrayOfbyte, paramInt, i, true, false);
/* 110 */         if (!this.zoneXfer) {
/* 111 */           this.question.addElement(resourceRecord);
/*     */         }
/* 113 */         i += resourceRecord.size();
/*     */       } 
/*     */       
/* 116 */       for (b = 0; b < paramHeader.numAnswers; b++) {
/* 117 */         ResourceRecord resourceRecord = new ResourceRecord(paramArrayOfbyte, paramInt, i, false, !this.zoneXfer);
/*     */         
/* 119 */         this.answer.addElement(resourceRecord);
/* 120 */         i += resourceRecord.size();
/*     */       } 
/*     */       
/* 123 */       if (this.zoneXfer) {
/*     */         return;
/*     */       }
/*     */       
/* 127 */       for (b = 0; b < paramHeader.numAuthorities; b++) {
/* 128 */         ResourceRecord resourceRecord = new ResourceRecord(paramArrayOfbyte, paramInt, i, false, true);
/* 129 */         this.authority.addElement(resourceRecord);
/* 130 */         i += resourceRecord.size();
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 135 */     catch (IndexOutOfBoundsException indexOutOfBoundsException) {
/* 136 */       throw new CommunicationException("DNS error: corrupted message");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/dns/ResourceRecords.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */