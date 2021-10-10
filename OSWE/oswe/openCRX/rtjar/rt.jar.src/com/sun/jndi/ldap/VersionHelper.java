/*    */ package com.sun.jndi.ldap;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
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
/*    */ abstract class VersionHelper
/*    */ {
/* 33 */   private static VersionHelper helper = null;
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/*    */     try {
/* 39 */       Class.forName("java.net.URLClassLoader");
/* 40 */       Class.forName("java.security.PrivilegedAction");
/*    */ 
/*    */       
/* 43 */       helper = (VersionHelper)Class.forName("com.sun.jndi.ldap.VersionHelper12").newInstance();
/* 44 */     } catch (Exception exception) {}
/*    */ 
/*    */ 
/*    */     
/* 48 */     if (helper == null) {
/*    */       
/*    */       try {
/*    */         
/* 52 */         helper = (VersionHelper)Class.forName("com.sun.jndi.ldap.VersionHelper11").newInstance();
/* 53 */       } catch (Exception exception) {}
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   static VersionHelper getVersionHelper() {
/* 60 */     return helper;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static URL[] getUrlArray(String[] paramArrayOfString) throws MalformedURLException {
/* 68 */     URL[] arrayOfURL = new URL[paramArrayOfString.length];
/* 69 */     for (byte b = 0; b < arrayOfURL.length; b++) {
/* 70 */       arrayOfURL[b] = new URL(paramArrayOfString[b]);
/*    */     }
/* 72 */     return arrayOfURL;
/*    */   }
/*    */   
/*    */   abstract ClassLoader getURLClassLoader(String[] paramArrayOfString) throws MalformedURLException;
/*    */   
/*    */   abstract Class<?> loadClass(String paramString) throws ClassNotFoundException;
/*    */   
/*    */   abstract Thread createThread(Runnable paramRunnable);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jndi/ldap/VersionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */