/*     */ package sun.nio.ch.sctp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResultContainer
/*     */ {
/*     */   static final int NOTHING = 0;
/*     */   static final int MESSAGE = 1;
/*     */   static final int SEND_FAILED = 2;
/*     */   static final int ASSOCIATION_CHANGED = 3;
/*     */   static final int PEER_ADDRESS_CHANGED = 4;
/*     */   static final int SHUTDOWN = 5;
/*     */   private Object value;
/*     */   private int type;
/*     */   
/*     */   int type() {
/*  46 */     return this.type;
/*     */   }
/*     */   
/*     */   boolean hasSomething() {
/*  50 */     return (type() != 0);
/*     */   }
/*     */   
/*     */   boolean isNotification() {
/*  54 */     return (type() != 1 && type() != 0);
/*     */   }
/*     */   
/*     */   void clear() {
/*  58 */     this.type = 0;
/*  59 */     this.value = null;
/*     */   }
/*     */   
/*     */   SctpNotification notification() {
/*  63 */     assert type() != 1 && type() != 0;
/*     */     
/*  65 */     return (SctpNotification)this.value;
/*     */   }
/*     */   
/*     */   MessageInfoImpl getMessageInfo() {
/*  69 */     assert type() == 1;
/*     */     
/*  71 */     if (this.value instanceof MessageInfoImpl) {
/*  72 */       return (MessageInfoImpl)this.value;
/*     */     }
/*  74 */     return null;
/*     */   }
/*     */   
/*     */   SendFailed getSendFailed() {
/*  78 */     assert type() == 2;
/*     */     
/*  80 */     if (this.value instanceof SendFailed) {
/*  81 */       return (SendFailed)this.value;
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   AssociationChange getAssociationChanged() {
/*  87 */     assert type() == 3;
/*     */     
/*  89 */     if (this.value instanceof AssociationChange) {
/*  90 */       return (AssociationChange)this.value;
/*     */     }
/*  92 */     return null;
/*     */   }
/*     */   
/*     */   PeerAddrChange getPeerAddressChanged() {
/*  96 */     assert type() == 4;
/*     */     
/*  98 */     if (this.value instanceof PeerAddrChange) {
/*  99 */       return (PeerAddrChange)this.value;
/*     */     }
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   Shutdown getShutdown() {
/* 105 */     assert type() == 5;
/*     */     
/* 107 */     if (this.value instanceof Shutdown) {
/* 108 */       return (Shutdown)this.value;
/*     */     }
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 115 */     StringBuilder stringBuilder = new StringBuilder();
/* 116 */     stringBuilder.append("Type: ");
/* 117 */     switch (this.type) { case 0:
/* 118 */         stringBuilder.append("NOTHING"); break;
/* 119 */       case 1: stringBuilder.append("MESSAGE"); break;
/* 120 */       case 2: stringBuilder.append("SEND FAILED"); break;
/* 121 */       case 3: stringBuilder.append("ASSOCIATION CHANGE"); break;
/* 122 */       case 4: stringBuilder.append("PEER ADDRESS CHANGE"); break;
/* 123 */       case 5: stringBuilder.append("SHUTDOWN"); break;
/* 124 */       default: stringBuilder.append("Unknown result type"); break; }
/*     */     
/* 126 */     stringBuilder.append(", Value: ");
/* 127 */     stringBuilder.append((this.value == null) ? "null" : this.value.toString());
/* 128 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/ResultContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */