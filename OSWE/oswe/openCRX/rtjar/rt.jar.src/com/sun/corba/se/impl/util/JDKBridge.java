/*     */ package com.sun.corba.se.impl.util;
/*     */ 
/*     */ import com.sun.corba.se.impl.orbutil.GetPropertyAction;
/*     */ import java.net.MalformedURLException;
/*     */ import java.rmi.server.RMIClassLoader;
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
/*     */ public class JDKBridge
/*     */ {
/*     */   private static final String LOCAL_CODEBASE_KEY = "java.rmi.server.codebase";
/*     */   private static final String USE_CODEBASE_ONLY_KEY = "java.rmi.server.useCodebaseOnly";
/*     */   
/*     */   public static String getLocalCodebase() {
/*  59 */     return localCodebase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean useCodebaseOnly() {
/*  67 */     return useCodebaseOnly;
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
/*     */   public static Class loadClass(String paramString1, String paramString2, ClassLoader paramClassLoader) throws ClassNotFoundException {
/*  85 */     if (paramClassLoader == null) {
/*  86 */       return loadClassM(paramString1, paramString2, useCodebaseOnly);
/*     */     }
/*     */     try {
/*  89 */       return loadClassM(paramString1, paramString2, useCodebaseOnly);
/*  90 */     } catch (ClassNotFoundException classNotFoundException) {
/*  91 */       return paramClassLoader.loadClass(paramString1);
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
/*     */ 
/*     */   
/*     */   public static Class loadClass(String paramString1, String paramString2) throws ClassNotFoundException {
/* 107 */     return loadClass(paramString1, paramString2, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class loadClass(String paramString) throws ClassNotFoundException {
/* 118 */     return loadClass(paramString, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 123 */   private static String localCodebase = null;
/*     */   private static boolean useCodebaseOnly;
/*     */   
/*     */   static {
/* 127 */     setCodebaseProperties();
/*     */   }
/*     */   
/*     */   public static final void main(String[] paramArrayOfString) {
/* 131 */     System.out.println("1.2 VM");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setCodebaseProperties() {
/* 153 */     String str = AccessController.<String>doPrivileged((PrivilegedAction<String>)new GetPropertyAction("java.rmi.server.codebase"));
/*     */ 
/*     */     
/* 156 */     if (str != null && str.trim().length() > 0) {
/* 157 */       localCodebase = str;
/*     */     }
/*     */     
/* 160 */     str = AccessController.<String>doPrivileged((PrivilegedAction<String>)new GetPropertyAction("java.rmi.server.useCodebaseOnly"));
/*     */ 
/*     */     
/* 163 */     if (str != null && str.trim().length() > 0) {
/* 164 */       useCodebaseOnly = Boolean.valueOf(str).booleanValue();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void setLocalCodebase(String paramString) {
/* 173 */     localCodebase = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class loadClassM(String paramString1, String paramString2, boolean paramBoolean) throws ClassNotFoundException {
/*     */     try {
/* 182 */       return JDKClassLoader.loadClass(null, paramString1);
/* 183 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */       try {
/* 185 */         if (!paramBoolean && paramString2 != null) {
/* 186 */           return RMIClassLoader.loadClass(paramString2, paramString1);
/*     */         }
/*     */         
/* 189 */         return RMIClassLoader.loadClass(paramString1);
/*     */       }
/* 191 */       catch (MalformedURLException malformedURLException) {
/* 192 */         paramString1 = paramString1 + ": " + malformedURLException.toString();
/*     */ 
/*     */         
/* 195 */         throw new ClassNotFoundException(paramString1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/impl/util/JDKBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */