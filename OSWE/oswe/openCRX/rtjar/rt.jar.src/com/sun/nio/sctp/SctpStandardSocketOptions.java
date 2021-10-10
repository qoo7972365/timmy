/*     */ package com.sun.nio.sctp;
/*     */ 
/*     */ import java.net.SocketAddress;
/*     */ import jdk.Exported;
/*     */ import sun.nio.ch.sctp.SctpStdSocketOption;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Exported
/*     */ public class SctpStandardSocketOptions
/*     */ {
/*  52 */   public static final SctpSocketOption<Boolean> SCTP_DISABLE_FRAGMENTS = new SctpStdSocketOption<>("SCTP_DISABLE_FRAGMENTS", Boolean.class, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public static final SctpSocketOption<Boolean> SCTP_EXPLICIT_COMPLETE = new SctpStdSocketOption<>("SCTP_EXPLICIT_COMPLETE", Boolean.class, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   public static final SctpSocketOption<Integer> SCTP_FRAGMENT_INTERLEAVE = new SctpStdSocketOption<>("SCTP_FRAGMENT_INTERLEAVE", Integer.class, 3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   public static final SctpSocketOption<InitMaxStreams> SCTP_INIT_MAXSTREAMS = new SctpStdSocketOption<>("SCTP_INIT_MAXSTREAMS", InitMaxStreams.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 173 */   public static final SctpSocketOption<Boolean> SCTP_NODELAY = new SctpStdSocketOption<>("SCTP_NODELAY", Boolean.class, 4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 194 */   public static final SctpSocketOption<SocketAddress> SCTP_PRIMARY_ADDR = new SctpStdSocketOption<>("SCTP_PRIMARY_ADDR", SocketAddress.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 219 */   public static final SctpSocketOption<SocketAddress> SCTP_SET_PEER_PRIMARY_ADDR = new SctpStdSocketOption<>("SCTP_SET_PEER_PRIMARY_ADDR", SocketAddress.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 248 */   public static final SctpSocketOption<Integer> SO_SNDBUF = new SctpStdSocketOption<>("SO_SNDBUF", Integer.class, 5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 275 */   public static final SctpSocketOption<Integer> SO_RCVBUF = new SctpStdSocketOption<>("SO_RCVBUF", Integer.class, 6);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 306 */   public static final SctpSocketOption<Integer> SO_LINGER = new SctpStdSocketOption<>("SO_LINGER", Integer.class, 7);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Exported
/*     */   public static class InitMaxStreams
/*     */   {
/*     */     private int maxInStreams;
/*     */ 
/*     */ 
/*     */     
/*     */     private int maxOutStreams;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private InitMaxStreams(int param1Int1, int param1Int2) {
/* 325 */       this.maxInStreams = param1Int1;
/* 326 */       this.maxOutStreams = param1Int2;
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
/*     */ 
/*     */ 
/*     */     
/*     */     public static InitMaxStreams create(int param1Int1, int param1Int2) {
/* 347 */       if (param1Int2 < 0 || param1Int2 > 65535) {
/* 348 */         throw new IllegalArgumentException("Invalid maxOutStreams value");
/*     */       }
/* 350 */       if (param1Int1 < 0 || param1Int1 > 65535) {
/* 351 */         throw new IllegalArgumentException("Invalid maxInStreams value");
/*     */       }
/*     */       
/* 354 */       return new InitMaxStreams(param1Int1, param1Int2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int maxInStreams() {
/* 363 */       return this.maxInStreams;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int maxOutStreams() {
/* 372 */       return this.maxOutStreams;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 383 */       StringBuilder stringBuilder = new StringBuilder();
/* 384 */       stringBuilder.append(super.toString()).append(" [");
/* 385 */       stringBuilder.append("maxInStreams:").append(this.maxInStreams);
/* 386 */       stringBuilder.append("maxOutStreams:").append(this.maxOutStreams).append("]");
/* 387 */       return stringBuilder.toString();
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
/*     */     public boolean equals(Object param1Object) {
/* 403 */       if (param1Object != null && param1Object instanceof InitMaxStreams) {
/* 404 */         InitMaxStreams initMaxStreams = (InitMaxStreams)param1Object;
/* 405 */         if (this.maxInStreams == initMaxStreams.maxInStreams && this.maxOutStreams == initMaxStreams.maxOutStreams)
/*     */         {
/* 407 */           return true; } 
/*     */       } 
/* 409 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 417 */       return 0x7 ^ this.maxInStreams ^ this.maxOutStreams;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/nio/sctp/SctpStandardSocketOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */