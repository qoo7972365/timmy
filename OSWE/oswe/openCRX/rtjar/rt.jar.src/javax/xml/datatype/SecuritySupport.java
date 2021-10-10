/*     */ package javax.xml.datatype;
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
/*     */   ClassLoader getContextClassLoader() {
/*  44 */     return 
/*  45 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  47 */             ClassLoader cl = null;
/*     */             try {
/*  49 */               cl = Thread.currentThread().getContextClassLoader();
/*  50 */             } catch (SecurityException securityException) {}
/*  51 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   String getSystemProperty(final String propName) {
/*  57 */     return 
/*  58 */       AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/*  60 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
/*     */     try {
/*  69 */       return 
/*  70 */         AccessController.<FileInputStream>doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
/*     */             public Object run() throws FileNotFoundException {
/*  72 */               return new FileInputStream(file);
/*     */             }
/*     */           });
/*  75 */     } catch (PrivilegedActionException e) {
/*  76 */       throw (FileNotFoundException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   InputStream getResourceAsStream(final ClassLoader cl, final String name) {
/*  83 */     return 
/*  84 */       AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/*  87 */             if (cl == null) {
/*  88 */               ris = Object.class.getResourceAsStream(name);
/*     */             } else {
/*  90 */               ris = cl.getResourceAsStream(name);
/*     */             } 
/*  92 */             return ris;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   boolean doesFileExist(final File f) {
/*  98 */     return (
/*  99 */       (Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Object run() {
/* 101 */             return new Boolean(f.exists());
/*     */           }
/* 103 */         })).booleanValue();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/datatype/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */