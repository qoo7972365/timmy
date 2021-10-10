/*     */ package com.sun.org.apache.bcel.internal.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ListResourceBundle;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SecuritySupport
/*     */ {
/*  46 */   private static final SecuritySupport securitySupport = new SecuritySupport();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecuritySupport getInstance() {
/*  52 */     return securitySupport;
/*     */   }
/*     */   
/*     */   static java.lang.ClassLoader getContextClassLoader() {
/*  56 */     return AccessController.<java.lang.ClassLoader>doPrivileged(new PrivilegedAction<java.lang.ClassLoader>() {
/*     */           public Object run() {
/*  58 */             java.lang.ClassLoader cl = null;
/*     */             try {
/*  60 */               cl = Thread.currentThread().getContextClassLoader();
/*  61 */             } catch (SecurityException securityException) {}
/*     */             
/*  63 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static java.lang.ClassLoader getSystemClassLoader() {
/*  69 */     return AccessController.<java.lang.ClassLoader>doPrivileged(new PrivilegedAction<java.lang.ClassLoader>() {
/*     */           public Object run() {
/*  71 */             java.lang.ClassLoader cl = null;
/*     */             try {
/*  73 */               cl = java.lang.ClassLoader.getSystemClassLoader();
/*  74 */             } catch (SecurityException securityException) {}
/*     */             
/*  76 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static java.lang.ClassLoader getParentClassLoader(final java.lang.ClassLoader cl) {
/*  82 */     return AccessController.<java.lang.ClassLoader>doPrivileged(new PrivilegedAction<java.lang.ClassLoader>() {
/*     */           public Object run() {
/*  84 */             java.lang.ClassLoader parent = null;
/*     */             try {
/*  86 */               parent = cl.getParent();
/*  87 */             } catch (SecurityException securityException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  92 */             return (parent == cl) ? null : parent;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static String getSystemProperty(final String propName) {
/*  98 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/* 100 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
/*     */     try {
/* 108 */       return AccessController.<FileInputStream>doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
/*     */             public Object run() throws FileNotFoundException {
/* 110 */               return new FileInputStream(file);
/*     */             }
/*     */           });
/* 113 */     } catch (PrivilegedActionException e) {
/* 114 */       throw (FileNotFoundException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getResourceAsStream(String name) {
/* 123 */     if (System.getSecurityManager() != null) {
/* 124 */       return getResourceAsStream(null, name);
/*     */     }
/* 126 */     return getResourceAsStream(findClassLoader(), name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getResourceAsStream(final java.lang.ClassLoader cl, final String name) {
/* 132 */     return AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/* 135 */             if (cl == null) {
/* 136 */               ris = Object.class.getResourceAsStream("/" + name);
/*     */             } else {
/* 138 */               ris = cl.getResourceAsStream(name);
/*     */             } 
/* 140 */             return ris;
/*     */           }
/*     */         });
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
/*     */   public static ListResourceBundle getResourceBundle(String bundle) {
/* 154 */     return getResourceBundle(bundle, Locale.getDefault());
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
/*     */   public static ListResourceBundle getResourceBundle(final String bundle, final Locale locale) {
/* 167 */     return AccessController.<ListResourceBundle>doPrivileged(new PrivilegedAction<ListResourceBundle>() {
/*     */           public ListResourceBundle run() {
/*     */             try {
/* 170 */               return (ListResourceBundle)ResourceBundle.getBundle(bundle, locale);
/* 171 */             } catch (MissingResourceException e) {
/*     */               try {
/* 173 */                 return (ListResourceBundle)ResourceBundle.getBundle(bundle, new Locale("en", "US"));
/* 174 */               } catch (MissingResourceException e2) {
/* 175 */                 throw new MissingResourceException("Could not load any resource bundle by " + bundle, bundle, "");
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getFileList(final File f, final FilenameFilter filter) {
/* 184 */     return AccessController.<String[]>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/* 186 */             return f.list(filter);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static boolean getFileExists(final File f) {
/* 192 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Object run() {
/* 194 */             return f.exists() ? Boolean.TRUE : Boolean.FALSE;
/*     */           }
/* 196 */         })).booleanValue();
/*     */   }
/*     */   
/*     */   static long getLastModified(final File f) {
/* 200 */     return ((Long)AccessController.<Long>doPrivileged(new PrivilegedAction<Long>() {
/*     */           public Object run() {
/* 202 */             return new Long(f.lastModified());
/*     */           }
/* 204 */         })).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static java.lang.ClassLoader findClassLoader() {
/* 213 */     if (System.getSecurityManager() != null)
/*     */     {
/* 215 */       return null;
/*     */     }
/* 217 */     return SecuritySupport.class.getClassLoader();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/bcel/internal/util/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */