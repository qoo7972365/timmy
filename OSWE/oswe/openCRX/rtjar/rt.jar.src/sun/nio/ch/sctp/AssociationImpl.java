/*    */ package sun.nio.ch.sctp;
/*    */ 
/*    */ import com.sun.nio.sctp.Association;
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
/*    */ public class AssociationImpl
/*    */   extends Association
/*    */ {
/*    */   public AssociationImpl(int paramInt1, int paramInt2, int paramInt3) {
/* 36 */     super(paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 41 */     StringBuffer stringBuffer = new StringBuffer(super.toString());
/* 42 */     return stringBuffer.append("[associationID:")
/* 43 */       .append(associationID())
/* 44 */       .append(", maxIn:")
/* 45 */       .append(maxInboundStreams())
/* 46 */       .append(", maxOut:")
/* 47 */       .append(maxOutboundStreams())
/* 48 */       .append("]")
/* 49 */       .toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/sctp/AssociationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */