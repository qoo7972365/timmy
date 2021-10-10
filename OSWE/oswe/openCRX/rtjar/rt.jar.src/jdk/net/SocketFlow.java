/*     */ package jdk.net;
/*     */ 
/*     */ import jdk.Exported;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public class SocketFlow
/*     */ {
/*     */   private static final int UNSET = -1;
/*     */   public static final int NORMAL_PRIORITY = 1;
/*     */   public static final int HIGH_PRIORITY = 2;
/*  53 */   private int priority = 1;
/*     */   
/*  55 */   private long bandwidth = -1L;
/*     */   
/*  57 */   private Status status = Status.NO_STATUS;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Exported
/*     */   public enum Status
/*     */   {
/*  73 */     NO_STATUS,
/*     */ 
/*     */ 
/*     */     
/*  77 */     OK,
/*     */ 
/*     */ 
/*     */     
/*  81 */     NO_PERMISSION,
/*     */ 
/*     */ 
/*     */     
/*  85 */     NOT_CONNECTED,
/*     */ 
/*     */ 
/*     */     
/*  89 */     NOT_SUPPORTED,
/*     */ 
/*     */ 
/*     */     
/*  93 */     ALREADY_CREATED,
/*     */ 
/*     */ 
/*     */     
/*  97 */     IN_PROGRESS,
/*     */ 
/*     */ 
/*     */     
/* 101 */     OTHER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SocketFlow create() {
/* 109 */     return new SocketFlow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketFlow priority(int paramInt) {
/* 120 */     if (paramInt != 1 && paramInt != 2) {
/* 121 */       throw new IllegalArgumentException("invalid priority");
/*     */     }
/* 123 */     this.priority = paramInt;
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketFlow bandwidth(long paramLong) {
/* 134 */     if (paramLong < 0L) {
/* 135 */       throw new IllegalArgumentException("invalid bandwidth");
/*     */     }
/* 137 */     this.bandwidth = paramLong;
/*     */     
/* 139 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int priority() {
/* 146 */     return this.priority;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long bandwidth() {
/* 155 */     return this.bandwidth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Status status() {
/* 163 */     return this.status;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/jdk/net/SocketFlow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */