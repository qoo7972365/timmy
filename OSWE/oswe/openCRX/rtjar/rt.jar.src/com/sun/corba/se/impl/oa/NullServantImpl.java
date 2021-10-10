/*    */ package com.sun.corba.se.impl.oa;
/*    */ 
/*    */ import com.sun.corba.se.spi.oa.NullServant;
/*    */ import org.omg.CORBA.SystemException;
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
/*    */ public class NullServantImpl
/*    */   implements NullServant
/*    */ {
/*    */   private SystemException sysex;
/*    */   
/*    */   public NullServantImpl(SystemException paramSystemException) {
/* 38 */     this.sysex = paramSystemException;
/*    */   }
/*    */ 
/*    */   
/*    */   public SystemException getException() {
/* 43 */     return this.sysex;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/oa/NullServantImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */