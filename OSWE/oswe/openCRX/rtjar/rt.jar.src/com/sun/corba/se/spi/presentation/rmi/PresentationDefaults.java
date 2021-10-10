/*    */ package com.sun.corba.se.spi.presentation.rmi;
/*    */ 
/*    */ import com.sun.corba.se.impl.presentation.rmi.StubFactoryFactoryProxyImpl;
/*    */ import com.sun.corba.se.impl.presentation.rmi.StubFactoryFactoryStaticImpl;
/*    */ import com.sun.corba.se.impl.presentation.rmi.StubFactoryStaticImpl;
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
/*    */ public abstract class PresentationDefaults
/*    */ {
/* 38 */   private static StubFactoryFactoryStaticImpl staticImpl = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized PresentationManager.StubFactoryFactory getStaticStubFactoryFactory() {
/* 45 */     if (staticImpl == null) {
/* 46 */       staticImpl = new StubFactoryFactoryStaticImpl();
/*    */     }
/* 48 */     return (PresentationManager.StubFactoryFactory)staticImpl;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static PresentationManager.StubFactoryFactory getProxyStubFactoryFactory() {
/* 54 */     return (PresentationManager.StubFactoryFactory)new StubFactoryFactoryProxyImpl();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static PresentationManager.StubFactory makeStaticStubFactory(Class paramClass) {
/* 60 */     return (PresentationManager.StubFactory)new StubFactoryStaticImpl(paramClass);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/presentation/rmi/PresentationDefaults.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */