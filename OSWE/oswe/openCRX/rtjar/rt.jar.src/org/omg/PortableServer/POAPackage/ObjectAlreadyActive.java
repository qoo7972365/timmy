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
/*    */ public final class ObjectAlreadyActive
/*    */   extends UserException
/*    */ {
/*    */   public ObjectAlreadyActive() {
/* 16 */     super(ObjectAlreadyActiveHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectAlreadyActive(String paramString) {
/* 22 */     super(ObjectAlreadyActiveHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAPackage/ObjectAlreadyActive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */