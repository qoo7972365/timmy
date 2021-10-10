/*    */ package com.sun.corba.se.spi.orbutil.proxy;
/*    */ 
/*    */ import com.sun.corba.se.impl.presentation.rmi.DynamicAccessPermission;
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.Permission;
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
/*    */ 
/*    */ public abstract class DelegateInvocationHandlerImpl
/*    */ {
/*    */   public static InvocationHandler create(final Object delegate) {
/* 45 */     SecurityManager securityManager = System.getSecurityManager();
/* 46 */     if (securityManager != null) {
/* 47 */       securityManager.checkPermission((Permission)new DynamicAccessPermission("access"));
/*    */     }
/* 49 */     return new InvocationHandler()
/*    */       {
/*    */ 
/*    */         
/*    */         public Object invoke(Object param1Object, Method param1Method, Object[] param1ArrayOfObject) throws Throwable
/*    */         {
/*    */           try {
/* 56 */             return param1Method.invoke(delegate, param1ArrayOfObject);
/* 57 */           } catch (InvocationTargetException invocationTargetException) {
/*    */ 
/*    */             
/* 60 */             throw invocationTargetException.getCause();
/*    */           } 
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/proxy/DelegateInvocationHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */