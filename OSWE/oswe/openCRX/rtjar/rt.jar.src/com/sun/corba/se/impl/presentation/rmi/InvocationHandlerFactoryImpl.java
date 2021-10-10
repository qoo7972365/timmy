/*     */ package com.sun.corba.se.impl.presentation.rmi;
/*     */ 
/*     */ import com.sun.corba.se.spi.orbutil.proxy.CompositeInvocationHandler;
/*     */ import com.sun.corba.se.spi.orbutil.proxy.CompositeInvocationHandlerImpl;
/*     */ import com.sun.corba.se.spi.orbutil.proxy.DelegateInvocationHandlerImpl;
/*     */ import com.sun.corba.se.spi.orbutil.proxy.InvocationHandlerFactory;
/*     */ import com.sun.corba.se.spi.orbutil.proxy.LinkedInvocationHandler;
/*     */ import com.sun.corba.se.spi.presentation.rmi.DynamicStub;
/*     */ import com.sun.corba.se.spi.presentation.rmi.PresentationManager;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import org.omg.CORBA.Object;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvocationHandlerFactoryImpl
/*     */   implements InvocationHandlerFactory
/*     */ {
/*     */   private final PresentationManager.ClassData classData;
/*     */   private final PresentationManager pm;
/*     */   private Class[] proxyInterfaces;
/*     */   
/*     */   public InvocationHandlerFactoryImpl(PresentationManager paramPresentationManager, PresentationManager.ClassData paramClassData) {
/*  58 */     this.classData = paramClassData;
/*  59 */     this.pm = paramPresentationManager;
/*     */ 
/*     */     
/*  62 */     Class[] arrayOfClass = paramClassData.getIDLNameTranslator().getInterfaces();
/*  63 */     this.proxyInterfaces = new Class[arrayOfClass.length + 1];
/*  64 */     for (byte b = 0; b < arrayOfClass.length; b++) {
/*  65 */       this.proxyInterfaces[b] = arrayOfClass[b];
/*     */     }
/*  67 */     this.proxyInterfaces[arrayOfClass.length] = DynamicStub.class;
/*     */   }
/*     */ 
/*     */   
/*     */   private class CustomCompositeInvocationHandlerImpl
/*     */     extends CompositeInvocationHandlerImpl
/*     */     implements LinkedInvocationHandler, Serializable
/*     */   {
/*     */     private transient DynamicStub stub;
/*     */     
/*     */     public void setProxy(Proxy param1Proxy) {
/*  78 */       ((DynamicStubImpl)this.stub).setSelf((DynamicStub)param1Proxy);
/*     */     }
/*     */ 
/*     */     
/*     */     public Proxy getProxy() {
/*  83 */       return (Proxy)((DynamicStubImpl)this.stub).getSelf();
/*     */     }
/*     */ 
/*     */     
/*     */     public CustomCompositeInvocationHandlerImpl(DynamicStub param1DynamicStub) {
/*  88 */       this.stub = param1DynamicStub;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object writeReplace() throws ObjectStreamException {
/* 100 */       return this.stub;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InvocationHandler getInvocationHandler() {
/* 107 */     DynamicStubImpl dynamicStubImpl = new DynamicStubImpl(this.classData.getTypeIds());
/*     */     
/* 109 */     return getInvocationHandler(dynamicStubImpl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   InvocationHandler getInvocationHandler(DynamicStub paramDynamicStub) {
/* 120 */     final InvocationHandler dynamicStubHandler = DelegateInvocationHandlerImpl.create(paramDynamicStub);
/*     */ 
/*     */ 
/*     */     
/* 124 */     StubInvocationHandlerImpl stubInvocationHandlerImpl = new StubInvocationHandlerImpl(this.pm, this.classData, (Object)paramDynamicStub);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     final CustomCompositeInvocationHandlerImpl handler = new CustomCompositeInvocationHandlerImpl(paramDynamicStub);
/*     */ 
/*     */     
/* 132 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 135 */             handler.addInvocationHandler(DynamicStub.class, dynamicStubHandler);
/*     */             
/* 137 */             handler.addInvocationHandler(Object.class, dynamicStubHandler);
/*     */             
/* 139 */             handler.addInvocationHandler(Object.class, dynamicStubHandler);
/*     */             
/* 141 */             return null;
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     customCompositeInvocationHandlerImpl.setDefaultHandler((InvocationHandler)stubInvocationHandlerImpl);
/*     */     
/* 161 */     return (InvocationHandler)customCompositeInvocationHandlerImpl;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class[] getProxyInterfaces() {
/* 166 */     return this.proxyInterfaces;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/presentation/rmi/InvocationHandlerFactoryImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */