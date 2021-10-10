/*     */ package com.sun.xml.internal.ws.api.pipe;
/*     */ 
/*     */ import com.sun.xml.internal.ws.api.message.Packet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NextAction
/*     */ {
/*     */   int kind;
/*     */   Tube next;
/*     */   Packet packet;
/*     */   Throwable throwable;
/*     */   Runnable onExitRunnable;
/*     */   static final int INVOKE = 0;
/*     */   static final int INVOKE_AND_FORGET = 1;
/*     */   static final int RETURN = 2;
/*     */   static final int THROW = 3;
/*     */   static final int SUSPEND = 4;
/*     */   static final int THROW_ABORT_RESPONSE = 5;
/*     */   static final int ABORT_RESPONSE = 6;
/*     */   static final int INVOKE_ASYNC = 7;
/*     */   
/*     */   private void set(int k, Tube v, Packet p, Throwable t) {
/*  66 */     this.kind = k;
/*  67 */     this.next = v;
/*  68 */     this.packet = p;
/*  69 */     this.throwable = t;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invoke(Tube next, Packet p) {
/*  79 */     set(0, next, p, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeAndForget(Tube next, Packet p) {
/*  89 */     set(1, next, p, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void returnWith(Packet response) {
/*  97 */     set(2, null, response, null);
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
/*     */   public void throwException(Packet response, Throwable t) {
/* 111 */     set(2, null, response, t);
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
/*     */   public void throwException(Throwable t) {
/* 124 */     assert t instanceof RuntimeException || t instanceof Error;
/* 125 */     set(3, null, null, t);
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
/*     */   public void throwExceptionAbortResponse(Throwable t) {
/* 137 */     set(5, null, null, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void abortResponse(Packet response) {
/* 147 */     set(6, null, response, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invokeAsync(Tube next, Packet p) {
/* 158 */     set(7, next, p, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suspend() {
/* 167 */     suspend(null, null);
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
/*     */   public void suspend(Runnable onExitRunnable) {
/* 181 */     suspend(null, onExitRunnable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void suspend(Tube next) {
/* 191 */     suspend(next, null);
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
/*     */   public void suspend(Tube next, Runnable onExitRunnable) {
/* 208 */     set(4, next, null, null);
/* 209 */     this.onExitRunnable = onExitRunnable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube getNext() {
/* 216 */     return this.next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNext(Tube next) {
/* 223 */     this.next = next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getPacket() {
/* 231 */     return this.packet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Throwable getThrowable() {
/* 239 */     return this.throwable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 247 */     StringBuilder buf = new StringBuilder();
/* 248 */     buf.append(super.toString()).append(" [");
/* 249 */     buf.append("kind=").append(getKindString()).append(',');
/* 250 */     buf.append("next=").append(this.next).append(',');
/* 251 */     buf.append("packet=").append((this.packet != null) ? this.packet.toShortString() : null).append(',');
/* 252 */     buf.append("throwable=").append(this.throwable).append(']');
/* 253 */     return buf.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKindString() {
/* 260 */     switch (this.kind) { case 0:
/* 261 */         return "INVOKE";
/* 262 */       case 1: return "INVOKE_AND_FORGET";
/* 263 */       case 2: return "RETURN";
/* 264 */       case 3: return "THROW";
/* 265 */       case 4: return "SUSPEND";
/* 266 */       case 5: return "THROW_ABORT_RESPONSE";
/* 267 */       case 6: return "ABORT_RESPONSE";
/* 268 */       case 7: return "INVOKE_ASYNC"; }
/* 269 */      throw new AssertionError(this.kind);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/api/pipe/NextAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */