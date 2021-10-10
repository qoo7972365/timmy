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
/*    */ public final class AdapterAlreadyExists
/*    */   extends UserException
/*    */ {
/*    */   public AdapterAlreadyExists() {
/* 16 */     super(AdapterAlreadyExistsHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AdapterAlreadyExists(String paramString) {
/* 22 */     super(AdapterAlreadyExistsHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAPackage/AdapterAlreadyExists.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */