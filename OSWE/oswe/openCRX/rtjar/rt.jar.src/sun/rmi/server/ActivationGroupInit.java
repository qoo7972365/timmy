/*    */ package sun.rmi.server;
/*    */ 
/*    */ import java.rmi.activation.ActivationGroup;
/*    */ import java.rmi.activation.ActivationGroupDesc;
/*    */ import java.rmi.activation.ActivationGroupID;
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
/*    */ public abstract class ActivationGroupInit
/*    */ {
/*    */   public static void main(String[] paramArrayOfString) {
/*    */     try {
/* 61 */       if (System.getSecurityManager() == null) {
/* 62 */         System.setSecurityManager(new SecurityManager());
/*    */       }
/*    */       
/* 65 */       MarshalInputStream marshalInputStream = new MarshalInputStream(System.in);
/* 66 */       ActivationGroupID activationGroupID = (ActivationGroupID)marshalInputStream.readObject();
/* 67 */       ActivationGroupDesc activationGroupDesc = (ActivationGroupDesc)marshalInputStream.readObject();
/* 68 */       long l = marshalInputStream.readLong();
/*    */ 
/*    */       
/* 71 */       ActivationGroup.createGroup(activationGroupID, activationGroupDesc, l);
/* 72 */     } catch (Exception exception) {
/* 73 */       System.err.println("Exception in starting ActivationGroupInit:");
/* 74 */       exception.printStackTrace();
/*    */     } finally {
/*    */       try {
/* 77 */         System.in.close();
/*    */       
/*    */       }
/* 80 */       catch (Exception exception) {}
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/server/ActivationGroupInit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */