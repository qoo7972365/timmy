/*    */ package org.omg.PortableServer.POAManagerPackage;
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
/*    */ public final class AdapterInactive
/*    */   extends UserException
/*    */ {
/*    */   public AdapterInactive() {
/* 16 */     super(AdapterInactiveHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AdapterInactive(String paramString) {
/* 22 */     super(AdapterInactiveHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAManagerPackage/AdapterInactive.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */