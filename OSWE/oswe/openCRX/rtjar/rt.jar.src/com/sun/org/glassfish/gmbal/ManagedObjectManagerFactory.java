/*     */ package com.sun.org.glassfish.gmbal;
/*     */ 
/*     */ import com.sun.org.glassfish.gmbal.util.GenericConstructor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import javax.management.ObjectName;
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
/*     */ public final class ManagedObjectManagerFactory
/*     */ {
/*  46 */   private static GenericConstructor<ManagedObjectManager> objectNameCons = new GenericConstructor(ManagedObjectManager.class, "com.sun.org.glassfish.gmbal.impl.ManagedObjectManagerImpl", new Class[] { ObjectName.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private static GenericConstructor<ManagedObjectManager> stringCons = new GenericConstructor(ManagedObjectManager.class, "com.sun.org.glassfish.gmbal.impl.ManagedObjectManagerImpl", new Class[] { String.class });
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
/*     */   public static Method getMethod(final Class<?> cls, final String name, Class<?>... types) {
/*     */     try {
/*  71 */       return AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */           {
/*     */             public Method run() throws Exception {
/*  74 */               return cls.getDeclaredMethod(name, types);
/*     */             }
/*     */           });
/*  77 */     } catch (PrivilegedActionException ex) {
/*  78 */       throw new GmbalException("Unexpected exception", ex);
/*  79 */     } catch (SecurityException exc) {
/*  80 */       throw new GmbalException("Unexpected exception", exc);
/*     */     } 
/*     */   }
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
/*     */   public static ManagedObjectManager createStandalone(String domain) {
/*  94 */     ManagedObjectManager result = (ManagedObjectManager)stringCons.create(new Object[] { domain });
/*  95 */     if (result == null) {
/*  96 */       return ManagedObjectManagerNOPImpl.self;
/*     */     }
/*  98 */     return result;
/*     */   }
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
/*     */   public static ManagedObjectManager createFederated(ObjectName rootParentName) {
/* 116 */     ManagedObjectManager result = (ManagedObjectManager)objectNameCons.create(new Object[] { rootParentName });
/* 117 */     if (result == null) {
/* 118 */       return ManagedObjectManagerNOPImpl.self;
/*     */     }
/* 120 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ManagedObjectManager createNOOP() {
/* 130 */     return ManagedObjectManagerNOPImpl.self;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/glassfish/gmbal/ManagedObjectManagerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */