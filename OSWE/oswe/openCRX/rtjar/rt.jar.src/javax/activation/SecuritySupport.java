/*     */ package javax.activation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
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
/*     */   public static ClassLoader getContextClassLoader() {
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
/*     */   
/*     */   public static InputStream getResourceAsStream(final Class c, final String name) throws IOException {
/*     */     try {
/*  60 */       return 
/*  61 */         AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>() {
/*     */             public Object run() throws IOException {
/*  63 */               return c.getResourceAsStream(name);
/*     */             }
/*     */           });
/*  66 */     } catch (PrivilegedActionException e) {
/*  67 */       throw (IOException)e.getException();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static URL[] getResources(final ClassLoader cl, final String name) {
/*  72 */     return 
/*  73 */       AccessController.<URL[]>doPrivileged(new PrivilegedAction<URL>() {
/*     */           public Object run() {
/*  75 */             URL[] ret = null;
/*     */             
/*  77 */             try { List<URL> v = new ArrayList();
/*  78 */               Enumeration<URL> e = cl.getResources(name);
/*  79 */               while (e != null && e.hasMoreElements()) {
/*  80 */                 URL url = e.nextElement();
/*  81 */                 if (url != null)
/*  82 */                   v.add(url); 
/*     */               } 
/*  84 */               if (v.size() > 0) {
/*  85 */                 ret = new URL[v.size()];
/*  86 */                 ret = v.<URL>toArray(ret);
/*     */               }  }
/*  88 */             catch (IOException iOException) {  }
/*  89 */             catch (SecurityException securityException) {}
/*  90 */             return ret;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static URL[] getSystemResources(final String name) {
/*  96 */     return 
/*  97 */       AccessController.<URL[]>doPrivileged(new PrivilegedAction<URL>() {
/*     */           public Object run() {
/*  99 */             URL[] ret = null;
/*     */             
/* 101 */             try { List<URL> v = new ArrayList();
/* 102 */               Enumeration<URL> e = ClassLoader.getSystemResources(name);
/* 103 */               while (e != null && e.hasMoreElements()) {
/* 104 */                 URL url = e.nextElement();
/* 105 */                 if (url != null)
/* 106 */                   v.add(url); 
/*     */               } 
/* 108 */               if (v.size() > 0) {
/* 109 */                 ret = new URL[v.size()];
/* 110 */                 ret = v.<URL>toArray(ret);
/*     */               }  }
/* 112 */             catch (IOException iOException) {  }
/* 113 */             catch (SecurityException securityException) {}
/* 114 */             return ret;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static InputStream openStream(final URL url) throws IOException {
/*     */     try {
/* 121 */       return 
/* 122 */         AccessController.<InputStream>doPrivileged(new PrivilegedExceptionAction<InputStream>() {
/*     */             public Object run() throws IOException {
/* 124 */               return url.openStream();
/*     */             }
/*     */           });
/* 127 */     } catch (PrivilegedActionException e) {
/* 128 */       throw (IOException)e.getException();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/activation/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */