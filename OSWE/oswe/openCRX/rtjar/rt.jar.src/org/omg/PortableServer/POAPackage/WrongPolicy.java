/*    */ package org.omg.PortableServer.POAPackage;
/*    */ 
/*    */ import org.omg.CORBA.UserException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class WrongPolicy
/*    */   extends UserException
/*    */ {
/*    */   public WrongPolicy() {
/* 16 */     super(WrongPolicyHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public WrongPolicy(String paramString) {
/* 22 */     super(WrongPolicyHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAPackage/WrongPolicy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */