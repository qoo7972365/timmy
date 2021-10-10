/*     */ package javax.xml.ws.spi;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ class FactoryFinder
/*     */ {
/*     */   private static final String OSGI_SERVICE_LOADER_CLASS_NAME = "com.sun.org.glassfish.hk2.osgiresourcelocator.ServiceLoader";
/*     */   
/*     */   private static Object newInstance(String className, ClassLoader classLoader) {
/*     */     try {
/*  46 */       Class spiClass = safeLoadClass(className, classLoader);
/*  47 */       return spiClass.newInstance();
/*  48 */     } catch (ClassNotFoundException x) {
/*  49 */       throw new WebServiceException("Provider " + className + " not found", x);
/*     */     }
/*  51 */     catch (Exception x) {
/*  52 */       throw new WebServiceException("Provider " + className + " could not be instantiated: " + x, x);
/*     */     } 
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
/*     */   static Object find(String factoryId, String fallbackClassName) {
/*     */     ClassLoader classLoader;
/*  80 */     if (isOsgi()) {
/*  81 */       return lookupUsingOSGiServiceLoader(factoryId);
/*     */     }
/*     */     
/*     */     try {
/*  85 */       classLoader = Thread.currentThread().getContextClassLoader();
/*  86 */     } catch (Exception x) {
/*  87 */       throw new WebServiceException(x.toString(), x);
/*     */     } 
/*     */     
/*  90 */     String serviceId = "META-INF/services/" + factoryId;
/*     */     
/*  92 */     BufferedReader rd = null;
/*     */     
/*     */     try { InputStream is;
/*  95 */       if (classLoader == null) {
/*  96 */         is = ClassLoader.getSystemResourceAsStream(serviceId);
/*     */       } else {
/*  98 */         is = classLoader.getResourceAsStream(serviceId);
/*     */       } 
/*     */       
/* 101 */       if (is != null) {
/* 102 */         rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/*     */         
/* 104 */         String factoryClassName = rd.readLine();
/*     */         
/* 106 */         if (factoryClassName != null && 
/* 107 */           !"".equals(factoryClassName)) {
/* 108 */           return newInstance(factoryClassName, classLoader);
/*     */         }
/*     */       }  }
/* 111 */     catch (Exception exception) {  }
/*     */     finally
/* 113 */     { close(rd); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 118 */     FileInputStream inStream = null;
/*     */     
/* 120 */     try { String javah = System.getProperty("java.home");
/* 121 */       String configFile = javah + File.separator + "lib" + File.separator + "jaxws.properties";
/*     */       
/* 123 */       File f = new File(configFile);
/* 124 */       if (f.exists()) {
/* 125 */         Properties props = new Properties();
/* 126 */         inStream = new FileInputStream(f);
/* 127 */         props.load(inStream);
/* 128 */         String factoryClassName = props.getProperty(factoryId);
/* 129 */         return newInstance(factoryClassName, classLoader);
/*     */       }  }
/* 131 */     catch (Exception exception) {  }
/*     */     finally
/* 133 */     { close(inStream); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 139 */       String systemProp = System.getProperty(factoryId);
/* 140 */       if (systemProp != null) {
/* 141 */         return newInstance(systemProp, classLoader);
/*     */       }
/* 143 */     } catch (SecurityException securityException) {}
/*     */ 
/*     */     
/* 146 */     if (fallbackClassName == null) {
/* 147 */       throw new WebServiceException("Provider for " + factoryId + " cannot be found", null);
/*     */     }
/*     */ 
/*     */     
/* 151 */     return newInstance(fallbackClassName, classLoader);
/*     */   }
/*     */   
/*     */   private static void close(Closeable closeable) {
/* 155 */     if (closeable != null) {
/*     */       try {
/* 157 */         closeable.close();
/* 158 */       } catch (IOException iOException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class safeLoadClass(String className, ClassLoader classLoader) throws ClassNotFoundException {
/*     */     try {
/* 170 */       SecurityManager s = System.getSecurityManager();
/* 171 */       if (s != null) {
/* 172 */         int i = className.lastIndexOf('.');
/* 173 */         if (i != -1) {
/* 174 */           s.checkPackageAccess(className.substring(0, i));
/*     */         }
/*     */       } 
/*     */       
/* 178 */       if (classLoader == null) {
/* 179 */         return Class.forName(className);
/*     */       }
/* 181 */       return classLoader.loadClass(className);
/* 182 */     } catch (SecurityException se) {
/*     */       
/* 184 */       if ("com.sun.xml.internal.ws.spi.ProviderImpl".equals(className))
/* 185 */         return Class.forName(className); 
/* 186 */       throw se;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isOsgi() {
/*     */     try {
/* 194 */       Class.forName("com.sun.org.glassfish.hk2.osgiresourcelocator.ServiceLoader");
/* 195 */       return true;
/* 196 */     } catch (ClassNotFoundException classNotFoundException) {
/*     */       
/* 198 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Object lookupUsingOSGiServiceLoader(String factoryId) {
/*     */     try {
/* 204 */       Class<?> serviceClass = Class.forName(factoryId);
/* 205 */       Class[] args = { serviceClass };
/* 206 */       Class<?> target = Class.forName("com.sun.org.glassfish.hk2.osgiresourcelocator.ServiceLoader");
/* 207 */       Method m = target.getMethod("lookupProviderInstances", new Class[] { Class.class });
/* 208 */       Iterator iter = ((Iterable)m.invoke(null, (Object[])args)).iterator();
/* 209 */       return iter.hasNext() ? iter.next() : null;
/* 210 */     } catch (Exception ignored) {
/*     */       
/* 212 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/xml/ws/spi/FactoryFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */