/*    */ package com.sun.corba.se.impl.presentation.rmi;
/*    */ 
/*    */ import com.sun.corba.se.impl.util.Utility;
/*    */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
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
/*    */ public abstract class StubFactoryFactoryBase
/*    */   implements PresentationManager.StubFactoryFactory
/*    */ {
/*    */   public String getStubName(String paramString) {
/* 42 */     return Utility.stubName(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubFactoryFactoryBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */