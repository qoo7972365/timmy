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
/*    */ public final class ServantNotActive
/*    */   extends UserException
/*    */ {
/*    */   public ServantNotActive() {
/* 16 */     super(ServantNotActiveHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ServantNotActive(String paramString) {
/* 22 */     super(ServantNotActiveHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAPackage/ServantNotActive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */