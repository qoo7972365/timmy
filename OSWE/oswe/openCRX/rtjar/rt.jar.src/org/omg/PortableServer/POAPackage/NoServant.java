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
/*    */ public final class NoServant
/*    */   extends UserException
/*    */ {
/*    */   public NoServant() {
/* 16 */     super(NoServantHelper.id());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NoServant(String paramString) {
/* 22 */     super(NoServantHelper.id() + "  " + paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/omg/PortableServer/POAPackage/NoServant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */