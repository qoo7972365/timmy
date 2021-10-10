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
/*    */ public final class ServantAlreadyActive
/*    */   extends UserException
/*    */ {
/*    */   public ServantAlreadyActive() {
/* 16 */     super(ServantAlreadyActiveHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServantAlreadyActive(String paramString) {
/* 22 */     super(ServantAlreadyActiveHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAPackage/ServantAlreadyActive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */