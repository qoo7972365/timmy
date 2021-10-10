/*    */ package com.sun.corba.se.impl.presentation.rmi;
/*    */ 
/*    */ import com.sun.corba.se.spi.orbutil.proxy.InvocationHandlerFactory;
/*    */ import com.sun.corba.se.spi.orbutil.proxy.LinkedInvocationHandler;
/*    */ import com.sun.corba.se.spi.presentation.rmi.DynamicStub;
/*    */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Proxy;
/*    */ import org.omg.CORBA.Object;
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
/*    */ public class StubFactoryProxyImpl
/*    */   extends StubFactoryDynamicBase
/*    */ {
/*    */   public StubFactoryProxyImpl(PresentationManager.ClassData paramClassData, ClassLoader paramClassLoader) {
/* 41 */     super(paramClassData, paramClassLoader);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object makeStub() {
/* 48 */     InvocationHandlerFactory invocationHandlerFactory = this.classData.getInvocationHandlerFactory();
/*    */     
/* 50 */     LinkedInvocationHandler linkedInvocationHandler = (LinkedInvocationHandler)invocationHandlerFactory.getInvocationHandler();
/* 51 */     Class[] arrayOfClass = invocationHandlerFactory.getProxyInterfaces();
/* 52 */     DynamicStub dynamicStub = (DynamicStub)Proxy.newProxyInstance(this.loader, arrayOfClass, (InvocationHandler)linkedInvocationHandler);
/*    */     
/* 54 */     linkedInvocationHandler.setProxy((Proxy)dynamicStub);
/* 55 */     return (Object)dynamicStub;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/StubFactoryProxyImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */