/*     */ package javax.management.remote;
/*     */ 
/*     */ import com.sun.jmx.remote.util.ClassLogger;
/*     */ import com.sun.jmx.remote.util.EnvHelp;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.management.MBeanServer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JMXConnectorServerFactory
/*     */ {
/*     */   public static final String DEFAULT_CLASS_LOADER = "jmx.remote.default.class.loader";
/*     */   public static final String DEFAULT_CLASS_LOADER_NAME = "jmx.remote.default.class.loader.name";
/*     */   public static final String PROTOCOL_PROVIDER_PACKAGES = "jmx.remote.protocol.provider.pkgs";
/*     */   public static final String PROTOCOL_PROVIDER_CLASS_LOADER = "jmx.remote.protocol.provider.class.loader";
/*     */   private static final String PROTOCOL_PROVIDER_DEFAULT_PACKAGE = "com.sun.jmx.remote.protocol";
/* 202 */   private static final ClassLogger logger = new ClassLogger("javax.management.remote.misc", "JMXConnectorServerFactory");
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
/*     */   private static JMXConnectorServer getConnectorServerAsService(ClassLoader paramClassLoader, JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap, MBeanServer paramMBeanServer) throws IOException {
/* 217 */     Iterator<JMXConnectorServerProvider> iterator = JMXConnectorFactory.getProviderIterator(JMXConnectorServerProvider.class, paramClassLoader);
/*     */     
/* 219 */     IOException iOException = null;
/* 220 */     while (iterator.hasNext()) {
/*     */       try {
/* 222 */         return ((JMXConnectorServerProvider)iterator.next()).newJMXConnectorServer(paramJMXServiceURL, paramMap, paramMBeanServer);
/* 223 */       } catch (JMXProviderException jMXProviderException) {
/* 224 */         throw jMXProviderException;
/* 225 */       } catch (Exception exception) {
/* 226 */         if (logger.traceOn()) {
/* 227 */           logger.trace("getConnectorAsService", "URL[" + paramJMXServiceURL + "] Service provider exception: " + exception);
/*     */         }
/*     */         
/* 230 */         if (!(exception instanceof MalformedURLException) && 
/* 231 */           iOException == null) {
/* 232 */           if (exception instanceof IOException) {
/* 233 */             iOException = (IOException)exception; continue;
/*     */           } 
/* 235 */           iOException = EnvHelp.<IOException>initCause(new IOException(exception
/* 236 */                 .getMessage()), exception);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 243 */     if (iOException == null) {
/* 244 */       return null;
/*     */     }
/* 246 */     throw iOException;
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
/*     */   public static JMXConnectorServer newJMXConnectorServer(JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap, MBeanServer paramMBeanServer) throws IOException {
/*     */     HashMap<String, Object> hashMap;
/* 292 */     if (paramMap == null) {
/* 293 */       HashMap<Object, Object> hashMap1 = new HashMap<>();
/*     */     } else {
/* 295 */       EnvHelp.checkAttributes(paramMap);
/* 296 */       hashMap = new HashMap<>(paramMap);
/*     */     } 
/*     */     
/* 299 */     Class<JMXConnectorServerProvider> clazz = JMXConnectorServerProvider.class;
/*     */ 
/*     */     
/* 302 */     ClassLoader classLoader = JMXConnectorFactory.resolveClassLoader(hashMap);
/* 303 */     String str = paramJMXServiceURL.getProtocol();
/*     */ 
/*     */ 
/*     */     
/* 307 */     JMXConnectorServerProvider jMXConnectorServerProvider = JMXConnectorFactory.<JMXConnectorServerProvider>getProvider(paramJMXServiceURL, hashMap, "ServerProvider", clazz, classLoader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     IOException iOException = null;
/* 314 */     if (jMXConnectorServerProvider == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 319 */       if (classLoader != null) {
/*     */         
/*     */         try {
/* 322 */           JMXConnectorServer jMXConnectorServer = getConnectorServerAsService(classLoader, paramJMXServiceURL, hashMap, paramMBeanServer);
/*     */ 
/*     */ 
/*     */           
/* 326 */           if (jMXConnectorServer != null)
/* 327 */             return jMXConnectorServer; 
/* 328 */         } catch (JMXProviderException jMXProviderException) {
/* 329 */           throw jMXProviderException;
/* 330 */         } catch (IOException iOException1) {
/* 331 */           iOException = iOException1;
/*     */         } 
/*     */       }
/*     */       
/* 335 */       jMXConnectorServerProvider = JMXConnectorFactory.<JMXConnectorServerProvider>getProvider(str, "com.sun.jmx.remote.protocol", JMXConnectorFactory.class
/*     */ 
/*     */           
/* 338 */           .getClassLoader(), "ServerProvider", clazz);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 343 */     if (jMXConnectorServerProvider == null) {
/* 344 */       MalformedURLException malformedURLException = new MalformedURLException("Unsupported protocol: " + str);
/*     */       
/* 346 */       if (iOException == null) {
/* 347 */         throw malformedURLException;
/*     */       }
/* 349 */       throw (MalformedURLException)EnvHelp.initCause(malformedURLException, iOException);
/*     */     } 
/*     */ 
/*     */     
/* 353 */     Map<String, Object> map = Collections.unmodifiableMap(hashMap);
/*     */     
/* 355 */     return jMXConnectorServerProvider.newJMXConnectorServer(paramJMXServiceURL, map, paramMBeanServer);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/JMXConnectorServerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */