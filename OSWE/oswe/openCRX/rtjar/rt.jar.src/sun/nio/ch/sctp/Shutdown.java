/*    */ package sun.nio.ch.sctp;
/*    */ 
/*    */ import com.sun.nio.sctp.Association;
/*    */ import com.sun.nio.sctp.ShutdownNotification;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Shutdown
/*    */   extends ShutdownNotification
/*    */   implements SctpNotification
/*    */ {
/*    */   private Association association;
/*    */   private int assocId;
/*    */   
/*    */   private Shutdown(int paramInt) {
/* 43 */     this.assocId = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public int assocId() {
/* 48 */     return this.assocId;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAssociation(Association paramAssociation) {
/* 53 */     this.association = paramAssociation;
/*    */   }
/*    */ 
/*    */   
/*    */   public Association association() {
/* 58 */     assert this.association != null;
/* 59 */     return this.association;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 64 */     StringBuilder stringBuilder = new StringBuilder();
/* 65 */     stringBuilder.append(super.toString()).append(" [");
/* 66 */     stringBuilder.append("Association:").append(this.association).append("]");
/* 67 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/Shutdown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */