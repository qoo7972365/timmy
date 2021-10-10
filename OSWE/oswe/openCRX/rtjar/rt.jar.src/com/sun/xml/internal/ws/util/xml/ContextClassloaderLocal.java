/*    */ package com.sun.xml.internal.ws.util.xml;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.text.MessageFormat;
/*    */ import java.util.ResourceBundle;
/*    */ import java.util.WeakHashMap;
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
/*    */ abstract class ContextClassloaderLocal<V>
/*    */ {
/*    */   private static final String FAILED_TO_CREATE_NEW_INSTANCE = "FAILED_TO_CREATE_NEW_INSTANCE";
/* 41 */   private WeakHashMap<ClassLoader, V> CACHE = new WeakHashMap<>();
/*    */   
/*    */   public V get() throws Error {
/* 44 */     ClassLoader tccl = getContextClassLoader();
/* 45 */     V instance = this.CACHE.get(tccl);
/* 46 */     if (instance == null) {
/* 47 */       instance = createNewInstance();
/* 48 */       this.CACHE.put(tccl, instance);
/*    */     } 
/* 50 */     return instance;
/*    */   }
/*    */   
/*    */   public void set(V instance) {
/* 54 */     this.CACHE.put(getContextClassLoader(), instance);
/*    */   }
/*    */   
/*    */   protected abstract V initialValue() throws Exception;
/*    */   
/*    */   private V createNewInstance() {
/*    */     try {
/* 61 */       return initialValue();
/* 62 */     } catch (Exception e) {
/* 63 */       throw new Error(format("FAILED_TO_CREATE_NEW_INSTANCE", new Object[] { getClass().getName() }), e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static String format(String property, Object... args) {
/* 68 */     String text = ResourceBundle.getBundle(ContextClassloaderLocal.class.getName()).getString(property);
/* 69 */     return MessageFormat.format(text, args);
/*    */   }
/*    */   
/*    */   private static ClassLoader getContextClassLoader() {
/* 73 */     return 
/* 74 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*    */           public Object run() {
/* 76 */             ClassLoader cl = null;
/*    */             try {
/* 78 */               cl = Thread.currentThread().getContextClassLoader();
/* 79 */             } catch (SecurityException securityException) {}
/*    */             
/* 81 */             return cl;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/util/xml/ContextClassloaderLocal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */