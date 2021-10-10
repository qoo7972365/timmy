/*     */ package com.sun.jndi.ldap;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.naming.CommunicationException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LdapRequest
/*     */ {
/*  36 */   private static final BerDecoder EOF = new BerDecoder(new byte[0], -1, 0);
/*     */   
/*     */   LdapRequest next;
/*     */   
/*     */   final int msgId;
/*     */   private final BlockingQueue<BerDecoder> replies;
/*     */   private volatile boolean cancelled;
/*     */   private volatile boolean closed;
/*     */   private volatile boolean completed;
/*     */   private final boolean pauseAfterReceipt;
/*     */   
/*     */   LdapRequest(int paramInt1, boolean paramBoolean, int paramInt2) {
/*  48 */     this.msgId = paramInt1;
/*  49 */     this.pauseAfterReceipt = paramBoolean;
/*  50 */     if (paramInt2 == -1) {
/*  51 */       this.replies = new LinkedBlockingQueue<>();
/*     */     } else {
/*  53 */       this.replies = new LinkedBlockingQueue<>(8 * paramInt2 / 10);
/*     */     } 
/*     */   }
/*     */   
/*     */   void cancel() {
/*  58 */     this.cancelled = true;
/*  59 */     this.replies.offer(EOF);
/*     */   }
/*     */   
/*     */   synchronized void close() {
/*  63 */     this.closed = true;
/*  64 */     this.replies.offer(EOF);
/*     */   }
/*     */   
/*     */   private boolean isClosed() {
/*  68 */     return (this.closed && (this.replies.size() == 0 || this.replies.peek() == EOF));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized boolean addReplyBer(BerDecoder paramBerDecoder) {
/*  74 */     if (this.cancelled || this.closed) {
/*  75 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  80 */       paramBerDecoder.parseSeq(null);
/*  81 */       paramBerDecoder.parseInt();
/*  82 */       this.completed = (paramBerDecoder.peekByte() == 101);
/*  83 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/*  86 */     paramBerDecoder.reset();
/*     */ 
/*     */     
/*     */     try {
/*  90 */       this.replies.put(paramBerDecoder);
/*  91 */     } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */     
/*  95 */     return this.pauseAfterReceipt;
/*     */   }
/*     */ 
/*     */   
/*     */   BerDecoder getReplyBer(long paramLong) throws CommunicationException, InterruptedException {
/* 100 */     if (this.cancelled) {
/* 101 */       throw new CommunicationException("Request: " + this.msgId + " cancelled");
/*     */     }
/*     */     
/* 104 */     if (isClosed()) {
/* 105 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 109 */     BerDecoder berDecoder = (paramLong > 0L) ? this.replies.poll(paramLong, TimeUnit.MILLISECONDS) : this.replies.take();
/*     */     
/* 111 */     if (this.cancelled) {
/* 112 */       throw new CommunicationException("Request: " + this.msgId + " cancelled");
/*     */     }
/*     */ 
/*     */     
/* 116 */     return (berDecoder == EOF) ? null : berDecoder;
/*     */   }
/*     */   
/*     */   boolean hasSearchCompleted() {
/* 120 */     return this.completed;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/LdapRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */