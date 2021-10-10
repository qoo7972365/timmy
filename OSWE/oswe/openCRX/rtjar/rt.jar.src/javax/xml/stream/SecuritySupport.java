/*     */ package javax.xml.stream;
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
/*     */ 
/*     */ 
/*     */ class SecuritySupport
/*     */ {
/*     */   ClassLoader getContextClassLoader() throws SecurityException {
/*  44 */     return 
/*  45 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  47 */             ClassLoader cl = null;
/*     */             
/*  49 */             cl = Thread.currentThread().getContextClassLoader();
/*     */ 
/*     */             
/*  52 */             if (cl == null) {
/*  53 */               cl = ClassLoader.getSystemClassLoader();
/*     */             }
/*  55 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   String getSystemProperty(final String propName) {
/*  61 */     return 
/*  62 */       AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/*  64 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
/*     */     try {
/*  73 */       return 
/*  74 */         AccessController.<FileInputStream>doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
/*     */             public Object run() throws FileNotFoundException {
/*  76 */               return new FileInputStream(file);
/*     */             }
/*     */           });
/*  79 */     } catch (PrivilegedActionException e) {
/*  80 */       throw (FileNotFoundException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   InputStream getResourceAsStream(final ClassLoader cl, final String name) {
/*  87 */     return 
/*  88 */       AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/*  91 */             if (cl == null) {
/*  92 */               ris = Object.class.getResourceAsStream(name);
/*     */             } else {
/*  94 */               ris = cl.getResourceAsStream(name);
/*     */             } 
/*  96 */             return ris;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   boolean doesFileExist(final File f) {
/* 102 */     return (
/* 103 */       (Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Object run() {
/* 105 */             return new Boolean(f.exists());
/*     */           }
/* 107 */         })).booleanValue();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/stream/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */