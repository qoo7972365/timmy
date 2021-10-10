/*    */ package com.sun.jndi.ldap;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URLClassLoader;
/*    */ import java.security.AccessControlContext;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import sun.misc.SharedSecrets;
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
/*    */ final class VersionHelper12
/*    */   extends VersionHelper
/*    */ {
/*    */   private static final String TRUST_URL_CODEBASE_PROPERTY = "com.sun.jndi.ldap.object.trustURLCodebase";
/*    */   
/* 44 */   private static final String trustURLCodebase = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*    */       {
/*    */         public String run() {
/* 47 */           return System.getProperty("com.sun.jndi.ldap.object.trustURLCodebase", "false");
/*    */         }
/*    */       });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ClassLoader getURLClassLoader(String[] paramArrayOfString) throws MalformedURLException {
/* 57 */     ClassLoader classLoader = getContextClassLoader();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 63 */     if (paramArrayOfString != null && "true".equalsIgnoreCase(trustURLCodebase)) {
/* 64 */       return URLClassLoader.newInstance(getUrlArray(paramArrayOfString), classLoader);
/*    */     }
/* 66 */     return classLoader;
/*    */   }
/*    */ 
/*    */   
/*    */   Class<?> loadClass(String paramString) throws ClassNotFoundException {
/* 71 */     ClassLoader classLoader = getContextClassLoader();
/* 72 */     return Class.forName(paramString, true, classLoader);
/*    */   }
/*    */   
/*    */   private ClassLoader getContextClassLoader() {
/* 76 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*    */         {
/*    */           public ClassLoader run() {
/* 79 */             return Thread.currentThread().getContextClassLoader();
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   Thread createThread(final Runnable r) {
/* 86 */     final AccessControlContext acc = AccessController.getContext();
/*    */ 
/*    */     
/* 89 */     return AccessController.<Thread>doPrivileged(new PrivilegedAction<Thread>()
/*    */         {
/*    */           public Thread run() {
/* 92 */             return SharedSecrets.getJavaLangAccess()
/* 93 */               .newThreadWithAcc(r, acc);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/VersionHelper12.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */