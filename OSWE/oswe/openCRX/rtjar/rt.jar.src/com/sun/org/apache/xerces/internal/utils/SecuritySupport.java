/*     */ package com.sun.org.apache.xerces.internal.utils;
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
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Properties;
/*     */ import java.util.PropertyResourceBundle;
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
/*     */ public final class SecuritySupport
/*     */ {
/*  47 */   private static final SecuritySupport securitySupport = new SecuritySupport();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecuritySupport getInstance() {
/*  53 */     return securitySupport;
/*     */   }
/*     */   
/*     */   static ClassLoader getContextClassLoader() {
/*  57 */     return 
/*  58 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  60 */             ClassLoader cl = null;
/*     */             try {
/*  62 */               cl = Thread.currentThread().getContextClassLoader();
/*  63 */             } catch (SecurityException securityException) {}
/*  64 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static ClassLoader getSystemClassLoader() {
/*  70 */     return 
/*  71 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  73 */             ClassLoader cl = null;
/*     */             try {
/*  75 */               cl = ClassLoader.getSystemClassLoader();
/*  76 */             } catch (SecurityException securityException) {}
/*  77 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static ClassLoader getParentClassLoader(final ClassLoader cl) {
/*  83 */     return 
/*  84 */       AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  86 */             ClassLoader parent = null;
/*     */             try {
/*  88 */               parent = cl.getParent();
/*  89 */             } catch (SecurityException securityException) {}
/*     */ 
/*     */ 
/*     */             
/*  93 */             return (parent == cl) ? null : parent;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static String getSystemProperty(final String propName) {
/*  99 */     return 
/* 100 */       AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/* 102 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
/*     */     try {
/* 111 */       return 
/* 112 */         AccessController.<FileInputStream>doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
/*     */             public Object run() throws FileNotFoundException {
/* 114 */               return new FileInputStream(file);
/*     */             }
/*     */           });
/* 117 */     } catch (PrivilegedActionException e) {
/* 118 */       throw (FileNotFoundException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getResourceAsStream(String name) {
/* 126 */     if (System.getSecurityManager() != null) {
/* 127 */       return getResourceAsStream(null, name);
/*     */     }
/* 129 */     return getResourceAsStream(ObjectFactory.findClassLoader(), name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getResourceAsStream(final ClassLoader cl, final String name) {
/* 136 */     return 
/* 137 */       AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/* 140 */             if (cl == null) {
/* 141 */               ris = Object.class.getResourceAsStream("/" + name);
/*     */             } else {
/* 143 */               ris = cl.getResourceAsStream(name);
/*     */             } 
/* 145 */             return ris;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceBundle getResourceBundle(String bundle) {
/* 156 */     return getResourceBundle(bundle, Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ResourceBundle getResourceBundle(final String bundle, final Locale locale) {
/* 166 */     return AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>() {
/*     */           public ResourceBundle run() {
/*     */             try {
/* 169 */               return PropertyResourceBundle.getBundle(bundle, locale);
/* 170 */             } catch (MissingResourceException e) {
/*     */               try {
/* 172 */                 return PropertyResourceBundle.getBundle(bundle, new Locale("en", "US"));
/* 173 */               } catch (MissingResourceException e2) {
/* 174 */                 throw new MissingResourceException("Could not load any resource bundle by " + bundle, bundle, "");
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean getFileExists(final File f) {
/* 183 */     return (
/* 184 */       (Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Object run() {
/* 186 */             return f.exists() ? Boolean.TRUE : Boolean.FALSE;
/*     */           }
/* 188 */         })).booleanValue();
/*     */   }
/*     */   
/*     */   static long getLastModified(final File f) {
/* 192 */     return (
/* 193 */       (Long)AccessController.<Long>doPrivileged(new PrivilegedAction<Long>() {
/*     */           public Object run() {
/* 195 */             return new Long(f.lastModified());
/*     */           }
/* 197 */         })).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sanitizePath(String uri) {
/* 207 */     if (uri == null) {
/* 208 */       return "";
/*     */     }
/* 210 */     int i = uri.lastIndexOf("/");
/* 211 */     if (i > 0) {
/* 212 */       return uri.substring(i + 1, uri.length());
/*     */     }
/* 214 */     return uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String checkAccess(String systemId, String allowedProtocols, String accessAny) throws IOException {
/*     */     String protocol;
/* 226 */     if (systemId == null || (allowedProtocols != null && allowedProtocols
/* 227 */       .equalsIgnoreCase(accessAny))) {
/* 228 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 232 */     if (systemId.indexOf(":") == -1) {
/* 233 */       protocol = "file";
/*     */     } else {
/* 235 */       URL url = new URL(systemId);
/* 236 */       protocol = url.getProtocol();
/* 237 */       if (protocol.equalsIgnoreCase("jar")) {
/* 238 */         String path = url.getPath();
/* 239 */         protocol = path.substring(0, path.indexOf(":"));
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     if (isProtocolAllowed(protocol, allowedProtocols))
/*     */     {
/* 245 */       return null;
/*     */     }
/* 247 */     return protocol;
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
/*     */   private static boolean isProtocolAllowed(String protocol, String allowedProtocols) {
/* 260 */     if (allowedProtocols == null) {
/* 261 */       return false;
/*     */     }
/* 263 */     String[] temp = allowedProtocols.split(",");
/* 264 */     for (String t : temp) {
/* 265 */       t = t.trim();
/* 266 */       if (t.equalsIgnoreCase(protocol)) {
/* 267 */         return true;
/*     */       }
/*     */     } 
/* 270 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJAXPSystemProperty(String sysPropertyId) {
/* 281 */     String accessExternal = getSystemProperty(sysPropertyId);
/* 282 */     if (accessExternal == null) {
/* 283 */       accessExternal = readJAXPProperty(sysPropertyId);
/*     */     }
/* 285 */     return accessExternal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String readJAXPProperty(String propertyId) {
/* 296 */     String value = null;
/* 297 */     InputStream is = null;
/*     */     
/* 299 */     try { if (firstTime) {
/* 300 */         synchronized (cacheProps) {
/* 301 */           if (firstTime) {
/* 302 */             String configFile = getSystemProperty("java.home") + File.separator + "lib" + File.separator + "jaxp.properties";
/*     */             
/* 304 */             File f = new File(configFile);
/* 305 */             if (getFileExists(f)) {
/* 306 */               is = getFileInputStream(f);
/* 307 */               cacheProps.load(is);
/*     */             } 
/* 309 */             firstTime = false;
/*     */           } 
/*     */         } 
/*     */       }
/* 313 */       value = cacheProps.getProperty(propertyId);
/*     */        }
/*     */     
/* 316 */     catch (Exception exception) {  }
/*     */     finally
/* 318 */     { if (is != null) {
/*     */         try {
/* 320 */           is.close();
/* 321 */         } catch (IOException iOException) {}
/*     */       } }
/*     */ 
/*     */     
/* 325 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 331 */   static final Properties cacheProps = new Properties();
/*     */   static volatile boolean firstTime = true;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xerces/internal/utils/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */