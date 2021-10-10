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
/*    */ public final class ObjectNotActive
/*    */   extends UserException
/*    */ {
/*    */   public ObjectNotActive() {
/* 16 */     super(ObjectNotActiveHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectNotActive(String paramString) {
/* 22 */     super(ObjectNotActiveHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAPackage/ObjectNotActive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */