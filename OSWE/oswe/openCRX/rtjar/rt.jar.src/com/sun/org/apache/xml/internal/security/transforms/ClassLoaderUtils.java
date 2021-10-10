/*     */ package com.sun.org.apache.xml.internal.security.transforms;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ClassLoaderUtils
/*     */ {
/*  46 */   private static final Logger log = Logger.getLogger(ClassLoaderUtils.class.getName());
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
/*  64 */     URL uRL = Thread.currentThread().getContextClassLoader().getResource(paramString);
/*  65 */     if (uRL == null && paramString.startsWith("/"))
/*     */     {
/*     */       
/*  68 */       uRL = Thread.currentThread().getContextClassLoader().getResource(paramString
/*  69 */           .substring(1));
/*     */     }
/*     */ 
/*     */     
/*  73 */     ClassLoader classLoader = ClassLoaderUtils.class.getClassLoader();
/*  74 */     if (classLoader == null) {
/*  75 */       classLoader = ClassLoader.getSystemClassLoader();
/*     */     }
/*  77 */     if (uRL == null) {
/*  78 */       uRL = classLoader.getResource(paramString);
/*     */     }
/*  80 */     if (uRL == null && paramString.startsWith("/"))
/*     */     {
/*  82 */       uRL = classLoader.getResource(paramString.substring(1));
/*     */     }
/*     */     
/*  85 */     if (uRL == null) {
/*  86 */       ClassLoader classLoader1 = paramClass.getClassLoader();
/*     */       
/*  88 */       if (classLoader1 != null) {
/*  89 */         uRL = classLoader1.getResource(paramString);
/*     */       }
/*     */     } 
/*     */     
/*  93 */     if (uRL == null) {
/*  94 */       uRL = paramClass.getResource(paramString);
/*     */     }
/*     */     
/*  97 */     if (uRL == null && paramString != null && paramString.charAt(0) != '/') {
/*  98 */       return getResource('/' + paramString, paramClass);
/*     */     }
/*     */     
/* 101 */     return uRL;
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
/* 117 */     ArrayList<URL> arrayList = new ArrayList();
/* 118 */     Enumeration<URL> enumeration = new Enumeration<URL>() {
/*     */         public boolean hasMoreElements() {
/* 120 */           return false;
/*     */         }
/*     */         public URL nextElement() {
/* 123 */           return null;
/*     */         }
/*     */       };
/*     */     
/*     */     try {
/* 128 */       enumeration = Thread.currentThread().getContextClassLoader().getResources(paramString);
/* 129 */     } catch (IOException iOException) {
/* 130 */       if (log.isLoggable(Level.FINE)) {
/* 131 */         log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */       }
/*     */     } 
/*     */     
/* 135 */     if (!enumeration.hasMoreElements() && paramString.startsWith("/")) {
/*     */       
/*     */       try {
/*     */         
/* 139 */         enumeration = Thread.currentThread().getContextClassLoader().getResources(paramString
/* 140 */             .substring(1));
/*     */       }
/* 142 */       catch (IOException iOException) {
/* 143 */         if (log.isLoggable(Level.FINE)) {
/* 144 */           log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 150 */     ClassLoader classLoader = ClassLoaderUtils.class.getClassLoader();
/* 151 */     if (classLoader == null) {
/* 152 */       classLoader = ClassLoader.getSystemClassLoader();
/*     */     }
/* 154 */     if (!enumeration.hasMoreElements()) {
/*     */       try {
/* 156 */         enumeration = classLoader.getResources(paramString);
/* 157 */       } catch (IOException iOException) {
/* 158 */         if (log.isLoggable(Level.FINE)) {
/* 159 */           log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 164 */     if (!enumeration.hasMoreElements() && paramString.startsWith("/")) {
/*     */       
/*     */       try {
/* 167 */         enumeration = classLoader.getResources(paramString.substring(1));
/* 168 */       } catch (IOException iOException) {
/* 169 */         if (log.isLoggable(Level.FINE)) {
/* 170 */           log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 176 */     if (!enumeration.hasMoreElements()) {
/* 177 */       ClassLoader classLoader1 = paramClass.getClassLoader();
/*     */       
/* 179 */       if (classLoader1 != null) {
/*     */         try {
/* 181 */           enumeration = classLoader1.getResources(paramString);
/* 182 */         } catch (IOException iOException) {
/* 183 */           if (log.isLoggable(Level.FINE)) {
/* 184 */             log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 191 */     if (!enumeration.hasMoreElements()) {
/* 192 */       URL uRL = paramClass.getResource(paramString);
/* 193 */       if (uRL != null) {
/* 194 */         arrayList.add(uRL);
/*     */       }
/*     */     } 
/* 197 */     while (enumeration.hasMoreElements()) {
/* 198 */       arrayList.add(enumeration.nextElement());
/*     */     }
/*     */ 
/*     */     
/* 202 */     if (arrayList.isEmpty() && paramString != null && paramString.charAt(0) != '/') {
/* 203 */       return getResources('/' + paramString, paramClass);
/*     */     }
/* 205 */     return arrayList;
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
/* 217 */     URL uRL = getResource(paramString, paramClass);
/*     */     
/*     */     try {
/* 220 */       return (uRL != null) ? uRL.openStream() : null;
/* 221 */     } catch (IOException iOException) {
/* 222 */       if (log.isLoggable(Level.FINE)) {
/* 223 */         log.log(Level.FINE, iOException.getMessage(), iOException);
/*     */       }
/* 225 */       return null;
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
/* 246 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */       
/* 248 */       if (classLoader != null) {
/* 249 */         return classLoader.loadClass(paramString);
/*     */       }
/* 251 */     } catch (ClassNotFoundException classNotFoundException) {
/* 252 */       if (log.isLoggable(Level.FINE)) {
/* 253 */         log.log(Level.FINE, classNotFoundException.getMessage(), classNotFoundException);
/*     */       }
/*     */     } 
/*     */     
/* 257 */     return loadClass2(paramString, paramClass);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class<?> loadClass2(String paramString, Class<?> paramClass) throws ClassNotFoundException {
/*     */     try {
/* 263 */       return Class.forName(paramString);
/* 264 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */       try {
/* 266 */         if (ClassLoaderUtils.class.getClassLoader() != null) {
/* 267 */           return ClassLoaderUtils.class.getClassLoader().loadClass(paramString);
/*     */         }
/* 269 */       } catch (ClassNotFoundException classNotFoundException1) {
/* 270 */         if (paramClass != null && paramClass.getClassLoader() != null) {
/* 271 */           return paramClass.getClassLoader().loadClass(paramString);
/*     */         }
/*     */       } 
/* 274 */       if (log.isLoggable(Level.FINE)) {
/* 275 */         log.log(Level.FINE, classNotFoundException.getMessage(), classNotFoundException);
/*     */       }
/* 277 */       throw classNotFoundException;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/security/transforms/ClassLoaderUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */