/*    */ package com.sun.corba.se.impl.presentation.rmi;
/*    */ 
/*    */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
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
/*    */ public class StubFactoryFactoryProxyImpl
/*    */   extends StubFactoryFactoryDynamicBase
/*    */ {
/*    */   public PresentationManager.StubFactory makeDynamicStubFactory(PresentationManager paramPresentationManager, final PresentationManager.ClassData classData, final ClassLoader classLoader) {
/* 38 */     return 
/* 39 */       AccessController.<PresentationManager.StubFactory>doPrivileged((PrivilegedAction)new PrivilegedAction<StubFactoryProxyImpl>()
/*    */         {
/*    */           public StubFactoryProxyImpl run() {
/* 42 */             return new StubFactoryProxyImpl(classData, classLoader);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubFactoryFactoryProxyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */