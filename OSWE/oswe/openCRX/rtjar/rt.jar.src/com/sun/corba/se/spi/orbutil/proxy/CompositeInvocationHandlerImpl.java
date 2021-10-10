/*    */ package com.sun.corba.se.spi.orbutil.proxy;
/*    */ 
/*    */ import com.sun.corba.se.impl.logging.ORBUtilSystemException;
/*    */ import com.sun.corba.se.impl.presentation.rmi.DynamicAccessPermission;
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.Permission;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ public class CompositeInvocationHandlerImpl
/*    */   implements CompositeInvocationHandler
/*    */ {
/* 44 */   private Map classToInvocationHandler = new LinkedHashMap<>();
/* 45 */   private InvocationHandler defaultHandler = null;
/*    */ 
/*    */ 
/*    */   
/*    */   public void addInvocationHandler(Class<?> paramClass, InvocationHandler paramInvocationHandler) {
/* 50 */     checkAccess();
/* 51 */     this.classToInvocationHandler.put(paramClass, paramInvocationHandler);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDefaultHandler(InvocationHandler paramInvocationHandler) {
/* 56 */     checkAccess();
/* 57 */     this.defaultHandler = paramInvocationHandler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {
/* 65 */     Class<?> clazz = paramMethod.getDeclaringClass();
/*    */     
/* 67 */     InvocationHandler invocationHandler = (InvocationHandler)this.classToInvocationHandler.get(clazz);
/*    */     
/* 69 */     if (invocationHandler == null) {
/* 70 */       if (this.defaultHandler != null) {
/* 71 */         invocationHandler = this.defaultHandler;
/*    */       } else {
/* 73 */         ORBUtilSystemException oRBUtilSystemException = ORBUtilSystemException.get("util");
/*    */         
/* 75 */         throw oRBUtilSystemException.noInvocationHandler("\"" + paramMethod.toString() + "\"");
/*    */       } 
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 82 */     return invocationHandler.invoke(paramObject, paramMethod, paramArrayOfObject);
/*    */   }
/*    */   
/* 85 */   private static final DynamicAccessPermission perm = new DynamicAccessPermission("access");
/*    */   private void checkAccess() {
/* 87 */     SecurityManager securityManager = System.getSecurityManager();
/* 88 */     if (securityManager != null)
/* 89 */       securityManager.checkPermission((Permission)perm); 
/*    */   }
/*    */   
/*    */   private static final long serialVersionUID = 4571178305984833743L;
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/orbutil/proxy/CompositeInvocationHandlerImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */