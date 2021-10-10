/*     */ package com.sun.org.apache.xml.internal.security.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
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
/*     */ final class ClassLoaderUtils
/*     */ {
/*  43 */   private static final Logger log = Logger.getLogger(ClassLoaderUtils.class.getName());
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
/*     */   static URL getResource(String paramString, Class<?> paramClass) {
/*  61 */     URL uRL = Thread.currentThread().getContextClassLoader().getResource(paramString);
/*  62 */     if (uRL == null && paramString.startsWith("/"))
/*     */     {
/*     */       
/*  65 */       uRL = Thread.currentThread().getContextClassLoader().getResource(paramString
/*  66 */           .substring(1));
/*     */     }
/*     */ 
/*     */     
/*  70 */     ClassLoader classLoader = ClassLoaderUtils.class.getClassLoader();
/*  71 */     if (classLoader == null) {
/*  72 */       classLoader = ClassLoader.getSystemClassLoader();
/*     */     }
/*  74 */     if (uRL == null) {
/*  75 */       uRL = classLoader.getResource(paramString);
/*     */     }
/*  77 */     if (uRL == null && paramString.startsWith("/"))
/*     */     {
/*  79 */       uRL = classLoader.getResource(paramString.substring(1));
/*     */     }
/*     */     
/*  82 */     if (uRL == null) {
/*  83 */       ClassLoader classLoader1 = paramClass.getClassLoader();
/*     */       
/*  85 */       if (classLoader1 != null) {
/*  86 */         uRL = classLoader1.getResource(paramString);
/*     */       }
/*     */     } 
/*     */     
/*  90 */     if (uRL == null) {
/*  91 */       uRL = paramClass.getResource(paramString);
/*     */     }
/*     */     
/*  94 */     if (uRL == null && paramString != null && paramString.charAt(0) != '/') {
/*  95 */       return getResource('/' + paramString, paramClass);
/*     */     }
/*     */     
/*  98 */     return uRL;
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
/*     */   static List<URL> getResources(String paramString, Class<?> paramClass) {
/* 114 */     ArrayList<URL> arrayList = new ArrayList();
/* 115 */     Enumeration<URL> enumeration = new Enumeration<URL>() {
/*     */         public boolean hasMoreElements() {
/* 117 */           return false;
/*     */         }
/*     */         public URL nextElement() {
/* 120 */           return null;
/*     */         }
/*     */       };
/*     */     
/*     */     try {
/* 125 */       enumeration = Thread.currentThread().getContextClassLoader().getResources(paramString);
/* 126 */     } catch (IOException iOException) {
/* 127 */       if (log.isLoggable(Level.FINE)) {
/* 128 */         log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */       }
/*     */     } 
/*     */     
/* 132 */     if (!enumeration.hasMoreElements() && paramString.startsWith("/")) {
/*     */       
/*     */       try {
/*     */         
/* 136 */         enumeration = Thread.currentThread().getContextClassLoader().getResources(paramString
/* 137 */             .substring(1));
/*     */       }
/* 139 */       catch (IOException iOException) {
/* 140 */         if (log.isLoggable(Level.FINE)) {
/* 141 */           log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 147 */     ClassLoader classLoader = ClassLoaderUtils.class.getClassLoader();
/* 148 */     if (classLoader == null) {
/* 149 */       classLoader = ClassLoader.getSystemClassLoader();
/*     */     }
/* 151 */     if (!enumeration.hasMoreElements()) {
/*     */       try {
/* 153 */         enumeration = classLoader.getResources(paramString);
/* 154 */       } catch (IOException iOException) {
/* 155 */         if (log.isLoggable(Level.FINE)) {
/* 156 */           log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 161 */     if (!enumeration.hasMoreElements() && paramString.startsWith("/")) {
/*     */       
/*     */       try {
/* 164 */         enumeration = classLoader.getResources(paramString.substring(1));
/* 165 */       } catch (IOException iOException) {
/* 166 */         if (log.isLoggable(Level.FINE)) {
/* 167 */           log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 173 */     if (!enumeration.hasMoreElements()) {
/* 174 */       ClassLoader classLoader1 = paramClass.getClassLoader();
/*     */       
/* 176 */       if (classLoader1 != null) {
/*     */         try {
/* 178 */           enumeration = classLoader1.getResources(paramString);
/* 179 */         } catch (IOException iOException) {
/* 180 */           if (log.isLoggable(Level.FINE)) {
/* 181 */             log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 188 */     if (!enumeration.hasMoreElements()) {
/* 189 */       URL uRL = paramClass.getResource(paramString);
/* 190 */       if (uRL != null) {
/* 191 */         arrayList.add(uRL);
/*     */       }
/*     */     } 
/* 194 */     while (enumeration.hasMoreElements()) {
/* 195 */       arrayList.add(enumeration.nextElement());
/*     */     }
/*     */ 
/*     */     
/* 199 */     if (arrayList.isEmpty() && paramString != null && paramString.charAt(0) != '/') {
/* 200 */       return getResources('/' + paramString, paramClass);
/*     */     }
/* 202 */     return arrayList;
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
/*     */   static InputStream getResourceAsStream(String paramString, Class<?> paramClass) {
/* 214 */     URL uRL = getResource(paramString, paramClass);
/*     */     
/*     */     try {
/* 217 */       return (uRL != null) ? uRL.openStream() : null;
/* 218 */     } catch (IOException iOException) {
/* 219 */       if (log.isLoggable(Level.FINE)) {
/* 220 */         log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */       }
/* 222 */       return null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Class<?> loadClass(String paramString, Class<?> paramClass) throws ClassNotFoundException {
/*     */     try {
/* 243 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */       
/* 245 */       if (classLoader != null) {
/* 246 */         return classLoader.loadClass(paramString);
/*     */       }
/* 248 */     } catch (ClassNotFoundException classNotFoundException) {
/* 249 */       if (log.isLoggable(Level.FINE)) {
/* 250 */         log.log(Level.FINE, classNotFoundException.getMessage(), classNotFoundException);
/*     */       }
/*     */     } 
/*     */     
/* 254 */     return loadClass2(paramString, paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class<?> loadClass2(String paramString, Class<?> paramClass) throws ClassNotFoundException {
/*     */     try {
/* 260 */       return Class.forName(paramString);
/* 261 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */       try {
/* 263 */         if (ClassLoaderUtils.class.getClassLoader() != null) {
/* 264 */           return ClassLoaderUtils.class.getClassLoader().loadClass(paramString);
/*     */         }
/* 266 */       } catch (ClassNotFoundException classNotFoundException1) {
/* 267 */         if (paramClass != null && paramClass.getClassLoader() != null) {
/* 268 */           return paramClass.getClassLoader().loadClass(paramString);
/*     */         }
/*     */       } 
/* 271 */       if (log.isLoggable(Level.FINE)) {
/* 272 */         log.log(Level.FINE, classNotFoundException.getMessage(), classNotFoundException);
/*     */       }
/* 274 */       throw classNotFoundException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/utils/ClassLoaderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */