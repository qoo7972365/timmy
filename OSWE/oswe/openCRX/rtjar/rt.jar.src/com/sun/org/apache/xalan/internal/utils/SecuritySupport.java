/*     */ package com.sun.org.apache.xalan.internal.utils;
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
/*     */ import java.util.ListResourceBundle;
/*     */ import java.util.Locale;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SecuritySupport
/*     */ {
/*  51 */   private static final SecuritySupport securitySupport = new SecuritySupport();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecuritySupport getInstance() {
/*  57 */     return securitySupport;
/*     */   }
/*     */   
/*     */   public static ClassLoader getContextClassLoader() {
/*  61 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  63 */             ClassLoader cl = null;
/*     */             try {
/*  65 */               cl = Thread.currentThread().getContextClassLoader();
/*  66 */             } catch (SecurityException securityException) {}
/*     */             
/*  68 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static ClassLoader getSystemClassLoader() {
/*  74 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  76 */             ClassLoader cl = null;
/*     */             try {
/*  78 */               cl = ClassLoader.getSystemClassLoader();
/*  79 */             } catch (SecurityException securityException) {}
/*     */             
/*  81 */             return cl;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static ClassLoader getParentClassLoader(final ClassLoader cl) {
/*  87 */     return AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public Object run() {
/*  89 */             ClassLoader parent = null;
/*     */             try {
/*  91 */               parent = cl.getParent();
/*  92 */             } catch (SecurityException securityException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  97 */             return (parent == cl) ? null : parent;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static String getSystemProperty(final String propName) {
/* 103 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/* 105 */             return System.getProperty(propName);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static String getSystemProperty(final String propName, final String def) {
/* 111 */     return AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */           public Object run() {
/* 113 */             return System.getProperty(propName, def);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static FileInputStream getFileInputStream(final File file) throws FileNotFoundException {
/*     */     try {
/* 121 */       return AccessController.<FileInputStream>doPrivileged(new PrivilegedExceptionAction<FileInputStream>() {
/*     */             public Object run() throws FileNotFoundException {
/* 123 */               return new FileInputStream(file);
/*     */             }
/*     */           });
/* 126 */     } catch (PrivilegedActionException e) {
/* 127 */       throw (FileNotFoundException)e.getException();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getResourceAsStream(String name) {
/* 136 */     if (System.getSecurityManager() != null) {
/* 137 */       return getResourceAsStream(null, name);
/*     */     }
/* 139 */     return getResourceAsStream(ObjectFactory.findClassLoader(), name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static InputStream getResourceAsStream(final ClassLoader cl, final String name) {
/* 145 */     return AccessController.<InputStream>doPrivileged(new PrivilegedAction<InputStream>() {
/*     */           public Object run() {
/*     */             InputStream ris;
/* 148 */             if (cl == null) {
/* 149 */               ris = Object.class.getResourceAsStream("/" + name);
/*     */             } else {
/* 151 */               ris = cl.getResourceAsStream(name);
/*     */             } 
/* 153 */             return ris;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListResourceBundle getResourceBundle(String bundle) {
/* 164 */     return getResourceBundle(bundle, Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ListResourceBundle getResourceBundle(final String bundle, final Locale locale) {
/* 174 */     return AccessController.<ListResourceBundle>doPrivileged(new PrivilegedAction<ListResourceBundle>() {
/*     */           public ListResourceBundle run() {
/*     */             try {
/* 177 */               return (ListResourceBundle)ResourceBundle.getBundle(bundle, locale);
/* 178 */             } catch (MissingResourceException e) {
/*     */               try {
/* 180 */                 return (ListResourceBundle)ResourceBundle.getBundle(bundle, new Locale("en", "US"));
/* 181 */               } catch (MissingResourceException e2) {
/* 182 */                 throw new MissingResourceException("Could not load any resource bundle by " + bundle, bundle, "");
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean getFileExists(final File f) {
/* 191 */     return ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>() {
/*     */           public Object run() {
/* 193 */             return f.exists() ? Boolean.TRUE : Boolean.FALSE;
/*     */           }
/* 195 */         })).booleanValue();
/*     */   }
/*     */   
/*     */   static long getLastModified(final File f) {
/* 199 */     return ((Long)AccessController.<Long>doPrivileged(new PrivilegedAction<Long>() {
/*     */           public Object run() {
/* 201 */             return new Long(f.lastModified());
/*     */           }
/* 203 */         })).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String sanitizePath(String uri) {
/* 213 */     if (uri == null) {
/* 214 */       return "";
/*     */     }
/* 216 */     int i = uri.lastIndexOf("/");
/* 217 */     if (i > 0) {
/* 218 */       return uri.substring(i + 1, uri.length());
/*     */     }
/* 220 */     return "";
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
/* 232 */     if (systemId == null || (allowedProtocols != null && allowedProtocols
/* 233 */       .equalsIgnoreCase(accessAny))) {
/* 234 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 238 */     if (systemId.indexOf(":") == -1) {
/* 239 */       protocol = "file";
/*     */     } else {
/* 241 */       URL url = new URL(systemId);
/* 242 */       protocol = url.getProtocol();
/* 243 */       if (protocol.equalsIgnoreCase("jar")) {
/* 244 */         String path = url.getPath();
/* 245 */         protocol = path.substring(0, path.indexOf(":"));
/*     */       } 
/*     */     } 
/*     */     
/* 249 */     if (isProtocolAllowed(protocol, allowedProtocols))
/*     */     {
/* 251 */       return null;
/*     */     }
/* 253 */     return protocol;
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
/* 266 */     if (allowedProtocols == null) {
/* 267 */       return false;
/*     */     }
/* 269 */     String[] temp = allowedProtocols.split(",");
/* 270 */     for (String t : temp) {
/* 271 */       t = t.trim();
/* 272 */       if (t.equalsIgnoreCase(protocol)) {
/* 273 */         return true;
/*     */       }
/*     */     } 
/* 276 */     return false;
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
/* 287 */     String accessExternal = getSystemProperty(sysPropertyId);
/* 288 */     if (accessExternal == null) {
/* 289 */       accessExternal = readJAXPProperty(sysPropertyId);
/*     */     }
/* 291 */     return accessExternal;
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
/* 302 */     String value = null;
/* 303 */     InputStream is = null;
/*     */     
/* 305 */     try { if (firstTime) {
/* 306 */         synchronized (cacheProps) {
/* 307 */           if (firstTime) {
/* 308 */             String configFile = getSystemProperty("java.home") + File.separator + "lib" + File.separator + "jaxp.properties";
/*     */             
/* 310 */             File f = new File(configFile);
/* 311 */             if (getFileExists(f)) {
/* 312 */               is = getFileInputStream(f);
/* 313 */               cacheProps.load(is);
/*     */             } 
/* 315 */             firstTime = false;
/*     */           } 
/*     */         } 
/*     */       }
/* 319 */       value = cacheProps.getProperty(propertyId);
/*     */        }
/*     */     
/* 322 */     catch (Exception exception) {  }
/*     */     finally
/* 324 */     { if (is != null) {
/*     */         try {
/* 326 */           is.close();
/* 327 */         } catch (IOException iOException) {}
/*     */       } }
/*     */ 
/*     */     
/* 331 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 337 */   static final Properties cacheProps = new Properties();
/*     */   static volatile boolean firstTime = true;
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/org/apache/xalan/internal/utils/SecuritySupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */