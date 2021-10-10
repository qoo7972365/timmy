/*     */ package com.sun.org.apache.xml.internal.serialize;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
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
/*     */ final class SecuritySupport
/*     */ {
/*  41 */   private static final SecuritySupport securitySupport = new SecuritySupport();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static SecuritySupport getInstance() {
/*  47 */     return securitySupport;
/*     */   }
/*     */   
/*     */   ClassLoader getContextClassLoader() {
/*  51 */     return 
/*  52 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  54 */             ClassLoader cl = null;
/*     */             try {
/*  56 */               cl = Thread.currentThread().getContextClassLoader();
/*  57 */             } catch (SecurityException securityException) {}
/*  58 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   ClassLoader getSystemClassLoader() {
/*  64 */     return 
/*  65 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  67 */             ClassLoader cl = null;
/*     */             try {
/*  69 */               cl = ClassLoader.getSystemClassLoader();
/*  70 */             } catch (SecurityException securityException) {}
/*  71 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   ClassLoader getParentClassLoader(final ClassLoader cl) {
/*  77 */     return 
/*  78 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  80 */             ClassLoader parent = null;
/*     */             try {
/*  82 */               parent = cl.getParent();
/*  83 */             } catch (SecurityException securityException) {}
/*     */ 
/*     */ 
/*     */             
/*  87 */             return (parent == cl) ? null : parent;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   String getSystemProperty(final String propName) {
/*  93 */     return 
/*  94 */       AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/*  96 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
/*     */     try {
/* 105 */       return 
/* 106 */         AccessController.<FileInputStream>doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
/*     */             public Object run() throws FileNotFoundException {
/* 108 */               return new FileInputStream(file);
/*     */             }
/*     */           });
/* 111 */     } catch (PrivilegedActionException e) {
/* 112 */       throw (FileNotFoundException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   InputStream getResourceAsStream(final ClassLoader cl, final String name) {
/* 119 */     return 
/* 120 */       AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/* 123 */             if (cl == null) {
/* 124 */               ris = ClassLoader.getSystemResourceAsStream(name);
/*     */             } else {
/* 126 */               ris = cl.getResourceAsStream(name);
/*     */             } 
/* 128 */             return ris;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   boolean getFileExists(final File f) {
/* 134 */     return (
/* 135 */       (Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Object run() {
/* 137 */             return new Boolean(f.exists());
/*     */           }
/* 139 */         })).booleanValue();
/*     */   }
/*     */   
/*     */   long getLastModified(final File f) {
/* 143 */     return (
/* 144 */       (Long)AccessController.<Long>doPrivileged(new PrivilegedAction<Long>() {
/*     */           public Object run() {
/* 146 */             return new Long(f.lastModified());
/*     */           }
/* 148 */         })).longValue();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xml/internal/serialize/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */