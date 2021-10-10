/*     */ package com.sun.jmx.remote.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.rmi.NoSuchObjectException;
/*     */ import java.rmi.Remote;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Properties;
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
/*     */ public final class IIOPHelper
/*     */ {
/*     */   private static final String IMPL_CLASS = "com.sun.jmx.remote.protocol.iiop.IIOPProxyImpl";
/*     */   
/*  47 */   private static final IIOPProxy proxy = AccessController.<IIOPProxy>doPrivileged(new PrivilegedAction<IIOPProxy>() {
/*     */         public IIOPProxy run() {
/*     */           try {
/*  50 */             Class<?> clazz = Class.forName("com.sun.jmx.remote.protocol.iiop.IIOPProxyImpl", true, IIOPHelper.class
/*  51 */                 .getClassLoader());
/*  52 */             return (IIOPProxy)clazz.newInstance();
/*  53 */           } catch (ClassNotFoundException classNotFoundException) {
/*  54 */             return null;
/*  55 */           } catch (InstantiationException instantiationException) {
/*  56 */             throw new AssertionError(instantiationException);
/*  57 */           } catch (IllegalAccessException illegalAccessException) {
/*  58 */             throw new AssertionError(illegalAccessException);
/*     */           } 
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAvailable() {
/*  66 */     return (proxy != null);
/*     */   }
/*     */   
/*     */   private static void ensureAvailable() {
/*  70 */     if (proxy == null) {
/*  71 */       throw new AssertionError("Should not here");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isStub(Object paramObject) {
/*  78 */     return (proxy == null) ? false : proxy.isStub(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getDelegate(Object paramObject) {
/*  85 */     ensureAvailable();
/*  86 */     return proxy.getDelegate(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDelegate(Object paramObject1, Object paramObject2) {
/*  93 */     ensureAvailable();
/*  94 */     proxy.setDelegate(paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getOrb(Object paramObject) {
/* 105 */     ensureAvailable();
/* 106 */     return proxy.getOrb(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void connect(Object paramObject1, Object paramObject2) throws IOException {
/* 115 */     if (proxy == null)
/* 116 */       throw new IOException("Connection to ORB failed, RMI/IIOP not available"); 
/* 117 */     proxy.connect(paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isOrb(Object paramObject) {
/* 124 */     return (proxy == null) ? false : proxy.isOrb(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object createOrb(String[] paramArrayOfString, Properties paramProperties) throws IOException {
/* 133 */     if (proxy == null)
/* 134 */       throw new IOException("ORB initialization failed, RMI/IIOP not available"); 
/* 135 */     return proxy.createOrb(paramArrayOfString, paramProperties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object stringToObject(Object paramObject, String paramString) {
/* 143 */     ensureAvailable();
/* 144 */     return proxy.stringToObject(paramObject, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String objectToString(Object paramObject1, Object paramObject2) {
/* 151 */     ensureAvailable();
/* 152 */     return proxy.objectToString(paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T narrow(Object paramObject, Class<T> paramClass) {
/* 160 */     ensureAvailable();
/* 161 */     return proxy.narrow(paramObject, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void exportObject(Remote paramRemote) throws IOException {
/* 168 */     if (proxy == null)
/* 169 */       throw new IOException("RMI object cannot be exported, RMI/IIOP not available"); 
/* 170 */     proxy.exportObject(paramRemote);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unexportObject(Remote paramRemote) throws IOException {
/* 177 */     if (proxy == null)
/* 178 */       throw new NoSuchObjectException("Object not exported"); 
/* 179 */     proxy.unexportObject(paramRemote);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Remote toStub(Remote paramRemote) throws IOException {
/* 186 */     if (proxy == null)
/* 187 */       throw new NoSuchObjectException("Object not exported"); 
/* 188 */     return proxy.toStub(paramRemote);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/internal/IIOPHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */