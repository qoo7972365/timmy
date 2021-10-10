/*     */ package sun.nio.ch.sctp;
/*     */ 
/*     */ import com.sun.nio.sctp.Association;
/*     */ import com.sun.nio.sctp.AssociationChangeNotification;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssociationChange
/*     */   extends AssociationChangeNotification
/*     */   implements SctpNotification
/*     */ {
/*     */   private static final int SCTP_COMM_UP = 1;
/*     */   private static final int SCTP_COMM_LOST = 2;
/*     */   private static final int SCTP_RESTART = 3;
/*     */   private static final int SCTP_SHUTDOWN = 4;
/*     */   private static final int SCTP_CANT_START = 5;
/*     */   private Association association;
/*     */   private int assocId;
/*     */   private AssociationChangeNotification.AssocChangeEvent event;
/*     */   private int maxOutStreams;
/*     */   private int maxInStreams;
/*     */   
/*     */   private AssociationChange(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/*  58 */     switch (paramInt2) {
/*     */       case 1:
/*  60 */         this.event = AssociationChangeNotification.AssocChangeEvent.COMM_UP;
/*     */         break;
/*     */       case 2:
/*  63 */         this.event = AssociationChangeNotification.AssocChangeEvent.COMM_LOST;
/*     */         break;
/*     */       case 3:
/*  66 */         this.event = AssociationChangeNotification.AssocChangeEvent.RESTART;
/*     */         break;
/*     */       case 4:
/*  69 */         this.event = AssociationChangeNotification.AssocChangeEvent.SHUTDOWN;
/*     */         break;
/*     */       case 5:
/*  72 */         this.event = AssociationChangeNotification.AssocChangeEvent.CANT_START;
/*     */         break;
/*     */       default:
/*  75 */         throw new AssertionError("Unknown Association Change Event type: " + paramInt2);
/*     */     } 
/*     */ 
/*     */     
/*  79 */     this.assocId = paramInt1;
/*  80 */     this.maxOutStreams = paramInt3;
/*  81 */     this.maxInStreams = paramInt4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int assocId() {
/*  86 */     return this.assocId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAssociation(Association paramAssociation) {
/*  91 */     this.association = paramAssociation;
/*     */   }
/*     */ 
/*     */   
/*     */   public Association association() {
/*  96 */     assert this.association != null;
/*  97 */     return this.association;
/*     */   }
/*     */ 
/*     */   
/*     */   public AssociationChangeNotification.AssocChangeEvent event() {
/* 102 */     return this.event;
/*     */   }
/*     */   
/*     */   int maxOutStreams() {
/* 106 */     return this.maxOutStreams;
/*     */   }
/*     */   
/*     */   int maxInStreams() {
/* 110 */     return this.maxInStreams;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 115 */     StringBuilder stringBuilder = new StringBuilder();
/* 116 */     stringBuilder.append(super.toString()).append(" [");
/* 117 */     stringBuilder.append("Association:").append(this.association);
/* 118 */     stringBuilder.append(", Event: ").append(this.event).append("]");
/* 119 */     return stringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/AssociationChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */