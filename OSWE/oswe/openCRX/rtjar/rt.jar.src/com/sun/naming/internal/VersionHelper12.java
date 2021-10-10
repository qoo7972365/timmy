/*     */ package com.sun.naming.internal;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Enumeration;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Properties;
/*     */ import javax.naming.NamingEnumeration;
/*     */ import javax.naming.NamingException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class VersionHelper12
/*     */   extends VersionHelper
/*     */ {
/*     */   private static final String TRUST_URL_CODEBASE_PROPERTY = "com.sun.jndi.ldap.object.trustURLCodebase";
/*     */   
/*     */   public Class<?> loadClass(String paramString) throws ClassNotFoundException {
/*  61 */     return loadClass(paramString, getContextClassLoader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   private static final String trustURLCodebase = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */       {
/*     */         public String run() {
/*     */           try {
/*  74 */             return System.getProperty("com.sun.jndi.ldap.object.trustURLCodebase", "false");
/*     */           }
/*  76 */           catch (SecurityException securityException) {
/*  77 */             return "false";
/*     */           } 
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Class<?> loadClass(String paramString, ClassLoader paramClassLoader) throws ClassNotFoundException {
/*  91 */     return Class.forName(paramString, true, paramClassLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> loadClass(String paramString1, String paramString2) throws ClassNotFoundException, MalformedURLException {
/* 101 */     if ("true".equalsIgnoreCase(trustURLCodebase)) {
/* 102 */       ClassLoader classLoader = getContextClassLoader();
/*     */       
/* 104 */       URLClassLoader uRLClassLoader = URLClassLoader.newInstance(getUrlArray(paramString2), classLoader);
/*     */       
/* 106 */       return loadClass(paramString1, uRLClassLoader);
/*     */     } 
/* 108 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   String getJndiProperty(final int i) {
/* 113 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/*     */             try {
/* 117 */               return System.getProperty(VersionHelper.PROPS[i]);
/* 118 */             } catch (SecurityException securityException) {
/* 119 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   String[] getJndiProperties() {
/* 127 */     Properties properties = AccessController.<Properties>doPrivileged(new PrivilegedAction<Properties>()
/*     */         {
/*     */           public Properties run() {
/*     */             try {
/* 131 */               return System.getProperties();
/* 132 */             } catch (SecurityException securityException) {
/* 133 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 138 */     if (properties == null) {
/* 139 */       return null;
/*     */     }
/* 141 */     String[] arrayOfString = new String[PROPS.length];
/* 142 */     for (byte b = 0; b < PROPS.length; b++) {
/* 143 */       arrayOfString[b] = properties.getProperty(PROPS[b]);
/*     */     }
/* 145 */     return arrayOfString;
/*     */   }
/*     */   
/*     */   InputStream getResourceAsStream(final Class<?> c, final String name) {
/* 149 */     return AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>()
/*     */         {
/*     */           public InputStream run() {
/* 152 */             return c.getResourceAsStream(name);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   InputStream getJavaHomeLibStream(final String filename) {
/* 159 */     return AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>()
/*     */         {
/*     */           public InputStream run() {
/*     */             try {
/* 163 */               String str1 = System.getProperty("java.home");
/* 164 */               if (str1 == null) {
/* 165 */                 return null;
/*     */               }
/* 167 */               String str2 = str1 + File.separator + "lib" + File.separator + filename;
/*     */               
/* 169 */               return new FileInputStream(str2);
/* 170 */             } catch (Exception exception) {
/* 171 */               return null;
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   NamingEnumeration<InputStream> getResources(final ClassLoader cl, final String name) throws IOException {
/*     */     Enumeration<URL> enumeration;
/*     */     try {
/* 182 */       enumeration = AccessController.<Enumeration>doPrivileged((PrivilegedExceptionAction)new PrivilegedExceptionAction<Enumeration<URL>>()
/*     */           {
/*     */             public Enumeration<URL> run() throws IOException {
/* 185 */               return (cl == null) ? 
/* 186 */                 ClassLoader.getSystemResources(name) : cl
/* 187 */                 .getResources(name);
/*     */             }
/*     */           });
/*     */     }
/* 191 */     catch (PrivilegedActionException privilegedActionException) {
/* 192 */       throw (IOException)privilegedActionException.getException();
/*     */     } 
/* 194 */     return new InputStreamEnumeration(enumeration);
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
/*     */   ClassLoader getContextClassLoader() {
/* 207 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*     */         {
/*     */           public ClassLoader run()
/*     */           {
/* 211 */             ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 212 */             if (classLoader == null)
/*     */             {
/* 214 */               classLoader = ClassLoader.getSystemClassLoader();
/*     */             }
/*     */             
/* 217 */             return classLoader;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class InputStreamEnumeration
/*     */     implements NamingEnumeration<InputStream>
/*     */   {
/*     */     private final Enumeration<URL> urls;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 234 */     private InputStream nextElement = null;
/*     */     
/*     */     InputStreamEnumeration(Enumeration<URL> param1Enumeration) {
/* 237 */       this.urls = param1Enumeration;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private InputStream getNextElement() {
/* 245 */       return AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>()
/*     */           {
/*     */             public InputStream run() {
/* 248 */               while (VersionHelper12.InputStreamEnumeration.this.urls.hasMoreElements()) {
/*     */                 try {
/* 250 */                   return ((URL)VersionHelper12.InputStreamEnumeration.this.urls.nextElement()).openStream();
/* 251 */                 } catch (IOException iOException) {}
/*     */               } 
/*     */ 
/*     */               
/* 255 */               return null;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasMore() {
/* 262 */       if (this.nextElement != null) {
/* 263 */         return true;
/*     */       }
/* 265 */       this.nextElement = getNextElement();
/* 266 */       return (this.nextElement != null);
/*     */     }
/*     */     
/*     */     public boolean hasMoreElements() {
/* 270 */       return hasMore();
/*     */     }
/*     */     
/*     */     public InputStream next() {
/* 274 */       if (hasMore()) {
/* 275 */         InputStream inputStream = this.nextElement;
/* 276 */         this.nextElement = null;
/* 277 */         return inputStream;
/*     */       } 
/* 279 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*     */     
/*     */     public InputStream nextElement() {
/* 284 */       return next();
/*     */     }
/*     */     
/*     */     public void close() {}
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/naming/internal/VersionHelper12.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */