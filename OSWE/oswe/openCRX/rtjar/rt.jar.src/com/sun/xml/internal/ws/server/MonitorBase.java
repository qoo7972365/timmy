/*     */ package com.sun.xml.internal.ws.server;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import com.sun.org.glassfish.external.amx.AMXGlassfish;
/*     */ import com.sun.org.glassfish.gmbal.Description;
/*     */ import com.sun.org.glassfish.gmbal.InheritedAttributes;
/*     */ import com.sun.org.glassfish.gmbal.ManagedData;
/*     */ import com.sun.org.glassfish.gmbal.ManagedObjectManager;
/*     */ import com.sun.org.glassfish.gmbal.ManagedObjectManagerFactory;
/*     */ import com.sun.xml.internal.ws.api.EndpointAddress;
/*     */ import com.sun.xml.internal.ws.api.config.management.policy.ManagedClientAssertion;
/*     */ import com.sun.xml.internal.ws.api.config.management.policy.ManagedServiceAssertion;
/*     */ import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;
/*     */ import com.sun.xml.internal.ws.api.server.Container;
/*     */ import com.sun.xml.internal.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.internal.ws.client.Stub;
/*     */ import java.io.IOException;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.management.ObjectName;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MonitorBase
/*     */ {
/*  63 */   private static final Logger logger = Logger.getLogger("com.sun.xml.internal.ws.monitoring");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ManagedObjectManager createManagedObjectManager(WSEndpoint endpoint) {
/*  91 */     String rootName = endpoint.getServiceName().getLocalPart() + "-" + endpoint.getPortName().getLocalPart();
/*     */     
/*  93 */     if (rootName.equals("-")) {
/*  94 */       rootName = "provider";
/*     */     }
/*     */ 
/*     */     
/*  98 */     String contextPath = getContextPath(endpoint);
/*  99 */     if (contextPath != null) {
/* 100 */       rootName = contextPath + "-" + rootName;
/*     */     }
/*     */ 
/*     */     
/* 104 */     ManagedServiceAssertion assertion = ManagedServiceAssertion.getAssertion(endpoint);
/* 105 */     if (assertion != null) {
/* 106 */       String id = assertion.getId();
/* 107 */       if (id != null) {
/* 108 */         rootName = id;
/*     */       }
/* 110 */       if (assertion.monitoringAttribute() == ManagementAssertion.Setting.OFF) {
/* 111 */         return disabled("This endpoint", rootName);
/*     */       }
/*     */     } 
/*     */     
/* 115 */     if (endpointMonitoring.equals(ManagementAssertion.Setting.OFF)) {
/* 116 */       return disabled("Global endpoint", rootName);
/*     */     }
/* 118 */     return createMOMLoop(rootName, 0);
/*     */   }
/*     */   
/*     */   private String getContextPath(WSEndpoint endpoint) {
/*     */     try {
/* 123 */       Container container = endpoint.getContainer();
/*     */       
/* 125 */       Method getSPI = container.getClass().getDeclaredMethod("getSPI", new Class[] { Class.class });
/* 126 */       getSPI.setAccessible(true);
/*     */       
/* 128 */       Class<?> servletContextClass = Class.forName("javax.servlet.ServletContext");
/*     */       
/* 130 */       Object servletContext = getSPI.invoke(container, new Object[] { servletContextClass });
/* 131 */       if (servletContext != null) {
/* 132 */         Method getContextPath = servletContextClass.getDeclaredMethod("getContextPath", new Class[0]);
/* 133 */         getContextPath.setAccessible(true);
/* 134 */         return (String)getContextPath.invoke(servletContext, new Object[0]);
/*     */       } 
/* 136 */       return null;
/* 137 */     } catch (Throwable t) {
/* 138 */       logger.log(Level.FINEST, "getContextPath", t);
/*     */       
/* 140 */       return null;
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
/*     */   @NotNull
/*     */   public ManagedObjectManager createManagedObjectManager(Stub stub) {
/* 157 */     EndpointAddress ea = stub.requestContext.getEndpointAddress();
/* 158 */     if (ea == null) {
/* 159 */       return ManagedObjectManagerFactory.createNOOP();
/*     */     }
/*     */     
/* 162 */     String rootName = ea.toString();
/*     */ 
/*     */     
/* 165 */     ManagedClientAssertion assertion = ManagedClientAssertion.getAssertion(stub.getPortInfo());
/* 166 */     if (assertion != null) {
/* 167 */       String id = assertion.getId();
/* 168 */       if (id != null) {
/* 169 */         rootName = id;
/*     */       }
/* 171 */       if (assertion.monitoringAttribute() == ManagementAssertion.Setting.OFF)
/* 172 */         return disabled("This client", rootName); 
/* 173 */       if (assertion.monitoringAttribute() == ManagementAssertion.Setting.ON && clientMonitoring != ManagementAssertion.Setting.OFF)
/*     */       {
/* 175 */         return createMOMLoop(rootName, 0);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     if (clientMonitoring == ManagementAssertion.Setting.NOT_SET || clientMonitoring == ManagementAssertion.Setting.OFF)
/*     */     {
/*     */       
/* 182 */       return disabled("Global client", rootName);
/*     */     }
/* 184 */     return createMOMLoop(rootName, 0);
/*     */   }
/*     */   @NotNull
/*     */   private ManagedObjectManager disabled(String x, String rootName) {
/* 188 */     String msg = x + " monitoring disabled. " + rootName + " will not be monitored";
/* 189 */     logger.log(Level.CONFIG, msg);
/* 190 */     return ManagedObjectManagerFactory.createNOOP();
/*     */   }
/*     */   @NotNull
/*     */   private ManagedObjectManager createMOMLoop(String rootName, int unique) {
/* 194 */     boolean isFederated = (AMXGlassfish.getGlassfishVersion() != null);
/* 195 */     ManagedObjectManager mom = createMOM(isFederated);
/* 196 */     mom = initMOM(mom);
/* 197 */     mom = createRoot(mom, rootName, unique);
/* 198 */     return mom;
/*     */   }
/*     */   @NotNull
/*     */   private ManagedObjectManager createMOM(boolean isFederated) {
/*     */     try {
/* 203 */       return new RewritingMOM(isFederated ? 
/* 204 */           ManagedObjectManagerFactory.createFederated(AMXGlassfish.DEFAULT
/* 205 */             .serverMon(AMXGlassfish.DEFAULT.dasName())) : 
/*     */           
/* 207 */           ManagedObjectManagerFactory.createStandalone("com.sun.metro"));
/* 208 */     } catch (Throwable t) {
/* 209 */       if (isFederated) {
/* 210 */         logger.log(Level.CONFIG, "Problem while attempting to federate with GlassFish AMX monitoring.  Trying standalone.", t);
/* 211 */         return createMOM(false);
/*     */       } 
/* 213 */       logger.log(Level.WARNING, "Ignoring exception - starting up without monitoring", t);
/* 214 */       return ManagedObjectManagerFactory.createNOOP();
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private ManagedObjectManager initMOM(ManagedObjectManager mom) {
/*     */     try {
/* 221 */       if (typelibDebug != -1) {
/* 222 */         mom.setTypelibDebug(typelibDebug);
/*     */       }
/* 224 */       if (registrationDebug.equals("FINE")) {
/* 225 */         mom.setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel.FINE);
/* 226 */       } else if (registrationDebug.equals("NORMAL")) {
/* 227 */         mom.setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel.NORMAL);
/*     */       } else {
/* 229 */         mom.setRegistrationDebug(ManagedObjectManager.RegistrationDebugLevel.NONE);
/*     */       } 
/*     */       
/* 232 */       mom.setRuntimeDebug(runtimeDebug);
/*     */ 
/*     */ 
/*     */       
/* 236 */       mom.suppressDuplicateRootReport(true);
/*     */       
/* 238 */       mom.stripPrefix(new String[] { "com.sun.xml.internal.ws.server", "com.sun.xml.internal.ws.rx.rm.runtime.sequence" });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 243 */       mom.addAnnotation(WebServiceFeature.class, (Annotation)DummyWebServiceFeature.class.getAnnotation(ManagedData.class));
/* 244 */       mom.addAnnotation(WebServiceFeature.class, (Annotation)DummyWebServiceFeature.class.getAnnotation(Description.class));
/* 245 */       mom.addAnnotation(WebServiceFeature.class, (Annotation)DummyWebServiceFeature.class.getAnnotation(InheritedAttributes.class));
/*     */ 
/*     */ 
/*     */       
/* 249 */       mom.suspendJMXRegistration();
/*     */     }
/* 251 */     catch (Throwable t) {
/*     */       try {
/* 253 */         mom.close();
/* 254 */       } catch (IOException e) {
/* 255 */         logger.log(Level.CONFIG, "Ignoring exception caught when closing unused ManagedObjectManager", e);
/*     */       } 
/* 257 */       logger.log(Level.WARNING, "Ignoring exception - starting up without monitoring", t);
/* 258 */       return ManagedObjectManagerFactory.createNOOP();
/*     */     } 
/* 260 */     return mom;
/*     */   }
/*     */   
/*     */   private ManagedObjectManager createRoot(ManagedObjectManager mom, String rootName, int unique) {
/* 264 */     String name = rootName + ((unique == 0) ? "" : ("-" + String.valueOf(unique)));
/*     */     try {
/* 266 */       Object ignored = mom.createRoot(this, name);
/* 267 */       if (ignored != null) {
/* 268 */         ObjectName ignoredName = mom.getObjectName(mom.getRoot());
/*     */         
/* 270 */         if (ignoredName != null) {
/* 271 */           logger.log(Level.INFO, "Metro monitoring rootname successfully set to: {0}", ignoredName);
/*     */         }
/* 273 */         return mom;
/*     */       } 
/*     */       try {
/* 276 */         mom.close();
/* 277 */       } catch (IOException e) {
/* 278 */         logger.log(Level.CONFIG, "Ignoring exception caught when closing unused ManagedObjectManager", e);
/*     */       } 
/* 280 */       String basemsg = "Duplicate Metro monitoring rootname: " + name + " : ";
/* 281 */       if (unique > maxUniqueEndpointRootNameRetries) {
/* 282 */         String str = basemsg + "Giving up.";
/* 283 */         logger.log(Level.INFO, str);
/* 284 */         return ManagedObjectManagerFactory.createNOOP();
/*     */       } 
/* 286 */       String msg = basemsg + "Will try to make unique";
/* 287 */       logger.log(Level.CONFIG, msg);
/* 288 */       return createMOMLoop(rootName, ++unique);
/* 289 */     } catch (Throwable t) {
/* 290 */       logger.log(Level.WARNING, "Error while creating monitoring root with name: " + rootName, t);
/* 291 */       return ManagedObjectManagerFactory.createNOOP();
/*     */     } 
/*     */   }
/*     */   
/* 295 */   private static ManagementAssertion.Setting clientMonitoring = ManagementAssertion.Setting.NOT_SET;
/* 296 */   private static ManagementAssertion.Setting endpointMonitoring = ManagementAssertion.Setting.NOT_SET;
/* 297 */   private static int typelibDebug = -1;
/* 298 */   private static String registrationDebug = "NONE";
/*     */   private static boolean runtimeDebug = false;
/* 300 */   private static int maxUniqueEndpointRootNameRetries = 100;
/*     */   private static final String monitorProperty = "com.sun.xml.internal.ws.monitoring.";
/*     */   
/*     */   private static ManagementAssertion.Setting propertyToSetting(String propName) {
/* 304 */     String s = System.getProperty(propName);
/* 305 */     if (s == null) {
/* 306 */       return ManagementAssertion.Setting.NOT_SET;
/*     */     }
/* 308 */     s = s.toLowerCase();
/* 309 */     if (s.equals("false") || s.equals("off"))
/* 310 */       return ManagementAssertion.Setting.OFF; 
/* 311 */     if (s.equals("true") || s.equals("on")) {
/* 312 */       return ManagementAssertion.Setting.ON;
/*     */     }
/* 314 */     return ManagementAssertion.Setting.NOT_SET;
/*     */   }
/*     */   
/*     */   static {
/*     */     try {
/* 319 */       endpointMonitoring = propertyToSetting("com.sun.xml.internal.ws.monitoring.endpoint");
/*     */       
/* 321 */       clientMonitoring = propertyToSetting("com.sun.xml.internal.ws.monitoring.client");
/*     */       
/* 323 */       Integer i = Integer.getInteger("com.sun.xml.internal.ws.monitoring.typelibDebug");
/* 324 */       if (i != null) {
/* 325 */         typelibDebug = i.intValue();
/*     */       }
/*     */       
/* 328 */       String s = System.getProperty("com.sun.xml.internal.ws.monitoring.registrationDebug");
/* 329 */       if (s != null) {
/* 330 */         registrationDebug = s.toUpperCase();
/*     */       }
/*     */       
/* 333 */       s = System.getProperty("com.sun.xml.internal.ws.monitoring.runtimeDebug");
/* 334 */       if (s != null && s.toLowerCase().equals("true")) {
/* 335 */         runtimeDebug = true;
/*     */       }
/*     */       
/* 338 */       i = Integer.getInteger("com.sun.xml.internal.ws.monitoring.maxUniqueEndpointRootNameRetries");
/* 339 */       if (i != null) {
/* 340 */         maxUniqueEndpointRootNameRetries = i.intValue();
/*     */       }
/* 342 */     } catch (Exception e) {
/* 343 */       logger.log(Level.WARNING, "Error while reading monitoring properties", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/ws/server/MonitorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */