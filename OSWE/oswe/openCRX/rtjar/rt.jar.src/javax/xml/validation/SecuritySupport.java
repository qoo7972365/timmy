/*     */ package javax.xml.validation;
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
/*     */ 
/*     */ class SecuritySupport
/*     */ {
/*     */   ClassLoader getContextClassLoader() {
/*  46 */     return 
/*  47 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  49 */             ClassLoader cl = null;
/*     */             
/*  51 */             cl = Thread.currentThread().getContextClassLoader();
/*     */             
/*  53 */             if (cl == null)
/*  54 */               cl = ClassLoader.getSystemClassLoader(); 
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
/*     */   InputStream getURLInputStream(final URL url) throws IOException {
/*     */     try {
/*  88 */       return 
/*  89 */         AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>() {
/*     */             public Object run() throws IOException {
/*  91 */               return url.openStream();
/*     */             }
/*     */           });
/*  94 */     } catch (PrivilegedActionException e) {
/*  95 */       throw (IOException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   URL getResourceAsURL(final ClassLoader cl, final String name) {
/* 102 */     return 
/* 103 */       AccessController.<URL>doPrivileged(new PrivilegedAction<URL>() {
/*     */           public Object run() {
/*     */             URL url;
/* 106 */             if (cl == null) {
/* 107 */               url = Object.class.getResource(name);
/*     */             } else {
/* 109 */               url = cl.getResource(name);
/*     */             } 
/* 111 */             return url;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Enumeration getResources(final ClassLoader cl, final String name) throws IOException {
/*     */     try {
/* 120 */       return 
/* 121 */         AccessController.<Enumeration>doPrivileged(new PrivilegedExceptionAction<Enumeration>() {
/*     */             public Object run() throws IOException {
/*     */               Enumeration<URL> enumeration;
/* 124 */               if (cl == null) {
/* 125 */                 enumeration = ClassLoader.getSystemResources(name);
/*     */               } else {
/* 127 */                 enumeration = cl.getResources(name);
/*     */               } 
/* 129 */               return enumeration;
/*     */             }
/*     */           });
/* 132 */     } catch (PrivilegedActionException e) {
/* 133 */       throw (IOException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   InputStream getResourceAsStream(final ClassLoader cl, final String name) {
/* 140 */     return 
/* 141 */       AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/* 144 */             if (cl == null) {
/* 145 */               ris = Object.class.getResourceAsStream(name);
/*     */             } else {
/* 147 */               ris = cl.getResourceAsStream(name);
/*     */             } 
/* 149 */             return ris;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   boolean doesFileExist(final File f) {
/* 155 */     return (
/* 156 */       (Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Object run() {
/* 158 */             return new Boolean(f.exists());
/*     */           }
/* 160 */         })).booleanValue();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/validation/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */