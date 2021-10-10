/*     */ package javax.xml.xpath;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Enumeration;
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
/*  45 */     return 
/*  46 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  48 */             ClassLoader cl = null;
/*     */             try {
/*  50 */               cl = Thread.currentThread().getContextClassLoader();
/*  51 */             } catch (SecurityException securityException) {}
/*  52 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   String getSystemProperty(final String propName) {
/*  58 */     return 
/*  59 */       AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/*  61 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
/*     */     try {
/*  70 */       return 
/*  71 */         AccessController.<FileInputStream>doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
/*     */             public Object run() throws FileNotFoundException {
/*  73 */               return new FileInputStream(file);
/*     */             }
/*     */           });
/*  76 */     } catch (PrivilegedActionException e) {
/*  77 */       throw (FileNotFoundException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   InputStream getURLInputStream(final URL url) throws IOException {
/*     */     try {
/*  85 */       return 
/*  86 */         AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>() {
/*     */             public Object run() throws IOException {
/*  88 */               return url.openStream();
/*     */             }
/*     */           });
/*  91 */     } catch (PrivilegedActionException e) {
/*  92 */       throw (IOException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   URL getResourceAsURL(final ClassLoader cl, final String name) {
/*  99 */     return 
/* 100 */       AccessController.<URL>doPrivileged(new PrivilegedAction<URL>() {
/*     */           public Object run() {
/*     */             URL url;
/* 103 */             if (cl == null) {
/* 104 */               url = Object.class.getResource(name);
/*     */             } else {
/* 106 */               url = cl.getResource(name);
/*     */             } 
/* 108 */             return url;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Enumeration getResources(final ClassLoader cl, final String name) throws IOException {
/*     */     try {
/* 117 */       return 
/* 118 */         AccessController.<Enumeration>doPrivileged(new PrivilegedExceptionAction<Enumeration>() {
/*     */             public Object run() throws IOException {
/*     */               Enumeration<URL> enumeration;
/* 121 */               if (cl == null) {
/* 122 */                 enumeration = ClassLoader.getSystemResources(name);
/*     */               } else {
/* 124 */                 enumeration = cl.getResources(name);
/*     */               } 
/* 126 */               return enumeration;
/*     */             }
/*     */           });
/* 129 */     } catch (PrivilegedActionException e) {
/* 130 */       throw (IOException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   InputStream getResourceAsStream(final ClassLoader cl, final String name) {
/* 137 */     return 
/* 138 */       AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/* 141 */             if (cl == null) {
/* 142 */               ris = Object.class.getResourceAsStream(name);
/*     */             } else {
/* 144 */               ris = cl.getResourceAsStream(name);
/*     */             } 
/* 146 */             return ris;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   boolean doesFileExist(final File f) {
/* 152 */     return (
/* 153 */       (Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Object run() {
/* 155 */             return new Boolean(f.exists());
/*     */           }
/* 157 */         })).booleanValue();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/xpath/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */