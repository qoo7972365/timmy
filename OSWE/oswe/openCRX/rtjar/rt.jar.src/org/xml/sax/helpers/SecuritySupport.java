/*     */ package org.xml.sax.helpers;
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
/*     */ class SecuritySupport
/*     */ {
/*     */   ClassLoader getContextClassLoader() throws SecurityException {
/*  42 */     return 
/*  43 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  45 */             ClassLoader cl = null;
/*     */             
/*  47 */             cl = Thread.currentThread().getContextClassLoader();
/*     */ 
/*     */             
/*  50 */             if (cl == null) {
/*  51 */               cl = ClassLoader.getSystemClassLoader();
/*     */             }
/*  53 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   String getSystemProperty(final String propName) {
/*  59 */     return 
/*  60 */       AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/*  62 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
/*     */     try {
/*  71 */       return 
/*  72 */         AccessController.<FileInputStream>doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
/*     */             public Object run() throws FileNotFoundException {
/*  74 */               return new FileInputStream(file);
/*     */             }
/*     */           });
/*  77 */     } catch (PrivilegedActionException e) {
/*  78 */       throw (FileNotFoundException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   InputStream getResourceAsStream(final ClassLoader cl, final String name) {
/*  85 */     return 
/*  86 */       AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/*  89 */             if (cl == null) {
/*  90 */               ris = Object.class.getResourceAsStream(name);
/*     */             } else {
/*  92 */               ris = cl.getResourceAsStream(name);
/*     */             } 
/*  94 */             return ris;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   boolean doesFileExist(final File f) {
/* 100 */     return (
/* 101 */       (Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Object run() {
/* 103 */             return new Boolean(f.exists());
/*     */           }
/* 105 */         })).booleanValue();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/org/xml/sax/helpers/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */