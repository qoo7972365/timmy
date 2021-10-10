/*     */ package com.sun.corba.se.impl.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.rmi.Remote;
/*     */ import java.rmi.RemoteException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
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
/*     */ class ObjectStreamClassCorbaExt
/*     */ {
/*     */   static final boolean isAbstractInterface(Class<?> paramClass) {
/*  65 */     if (!paramClass.isInterface() || Remote.class
/*  66 */       .isAssignableFrom(paramClass)) {
/*  67 */       return false;
/*     */     }
/*  69 */     Method[] arrayOfMethod = paramClass.getMethods();
/*  70 */     for (byte b = 0; b < arrayOfMethod.length; b++) {
/*  71 */       Class[] arrayOfClass = arrayOfMethod[b].getExceptionTypes();
/*  72 */       boolean bool = false;
/*  73 */       for (byte b1 = 0; b1 < arrayOfClass.length && !bool; b1++) {
/*  74 */         if (RemoteException.class == arrayOfClass[b1] || Throwable.class == arrayOfClass[b1] || Exception.class == arrayOfClass[b1] || IOException.class == arrayOfClass[b1])
/*     */         {
/*     */ 
/*     */           
/*  78 */           bool = true;
/*     */         }
/*     */       } 
/*  81 */       if (!bool) {
/*  82 */         return false;
/*     */       }
/*     */     } 
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final boolean isAny(String paramString) {
/*  93 */     boolean bool = false;
/*     */     
/*  95 */     if (paramString != null && (paramString
/*  96 */       .equals("Ljava/lang/Object;") || paramString
/*  97 */       .equals("Ljava/io/Serializable;") || paramString
/*  98 */       .equals("Ljava/io/Externalizable;"))) {
/*  99 */       bool = true;
/*     */     }
/* 101 */     return (bool == true);
/*     */   }
/*     */   
/*     */   private static final Method[] getDeclaredMethods(final Class clz) {
/* 105 */     return AccessController.<Method[]>doPrivileged(new PrivilegedAction<Method>() {
/*     */           public Object run() {
/* 107 */             return clz.getDeclaredMethods();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/io/ObjectStreamClassCorbaExt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */