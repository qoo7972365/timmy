/*    */ package com.sun.xml.internal.org.jvnet.mimepull;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
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
/*    */ class FactoryFinder
/*    */ {
/* 37 */   private static ClassLoader cl = FactoryFinder.class.getClassLoader();
/*    */ 
/*    */   
/*    */   static Object find(String factoryId) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
/* 41 */     String systemProp = System.getProperty(factoryId);
/* 42 */     if (systemProp != null) {
/* 43 */       return newInstance(systemProp);
/*    */     }
/*    */     
/* 46 */     String providerName = findJarServiceProviderName(factoryId);
/* 47 */     if (providerName != null && providerName.trim().length() > 0) {
/* 48 */       return newInstance(providerName);
/*    */     }
/*    */     
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   static Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
/* 56 */     Class<?> providerClass = cl.loadClass(className);
/* 57 */     Object instance = providerClass.newInstance();
/* 58 */     return instance;
/*    */   }
/*    */   
/*    */   private static String findJarServiceProviderName(String factoryId) {
/* 62 */     String factoryClassName, serviceId = "META-INF/services/" + factoryId;
/*    */     
/* 64 */     InputStream is = cl.getResourceAsStream(serviceId);
/*    */     
/* 66 */     if (is == null) {
/* 67 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 71 */     BufferedReader rd = null;
/*    */     try {
/*    */       try {
/* 74 */         rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/* 75 */       } catch (UnsupportedEncodingException e) {
/* 76 */         rd = new BufferedReader(new InputStreamReader(is));
/*    */       } 
/*    */       try {
/* 79 */         factoryClassName = rd.readLine();
/* 80 */       } catch (IOException x) {
/* 81 */         return null;
/*    */       } 
/*    */     } finally {
/* 84 */       if (rd != null) {
/*    */         try {
/* 86 */           rd.close();
/* 87 */         } catch (IOException ex) {
/* 88 */           Logger.getLogger(FactoryFinder.class.getName()).log(Level.INFO, (String)null, ex);
/*    */         } 
/*    */       }
/*    */     } 
/*    */     
/* 93 */     return factoryClassName;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/FactoryFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */