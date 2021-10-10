/*     */ package sun.management;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Properties;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.management.remote.JMXConnectorServer;
/*     */ import javax.management.remote.JMXServiceURL;
/*     */ import sun.management.jdp.JdpController;
/*     */ import sun.management.jdp.JdpException;
/*     */ import sun.management.jmxremote.ConnectorBootstrap;
/*     */ import sun.misc.VMSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Agent
/*     */ {
/*     */   private static Properties mgmtProps;
/*     */   private static ResourceBundle messageRB;
/*     */   private static final String CONFIG_FILE = "com.sun.management.config.file";
/*     */   private static final String SNMP_PORT = "com.sun.management.snmp.port";
/*     */   private static final String JMXREMOTE = "com.sun.management.jmxremote";
/*     */   private static final String JMXREMOTE_PORT = "com.sun.management.jmxremote.port";
/*     */   private static final String RMI_PORT = "com.sun.management.jmxremote.rmi.port";
/*     */   private static final String ENABLE_THREAD_CONTENTION_MONITORING = "com.sun.management.enableThreadContentionMonitoring";
/*     */   private static final String LOCAL_CONNECTOR_ADDRESS_PROP = "com.sun.management.jmxremote.localConnectorAddress";
/*     */   private static final String SNMP_ADAPTOR_BOOTSTRAP_CLASS_NAME = "sun.management.snmp.AdaptorBootstrap";
/*     */   private static final String JDP_DEFAULT_ADDRESS = "224.0.23.178";
/*     */   private static final int JDP_DEFAULT_PORT = 7095;
/*  83 */   private static JMXConnectorServer jmxServer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Properties parseString(String paramString) {
/*  89 */     Properties properties = new Properties();
/*  90 */     if (paramString != null && !paramString.trim().equals("")) {
/*  91 */       for (String str1 : paramString.split(",")) {
/*  92 */         String[] arrayOfString = str1.split("=", 2);
/*  93 */         String str2 = arrayOfString[0].trim();
/*  94 */         String str3 = (arrayOfString.length > 1) ? arrayOfString[1].trim() : "";
/*     */         
/*  96 */         if (!str2.startsWith("com.sun.management.")) {
/*  97 */           error("agent.err.invalid.option", str2);
/*     */         }
/*     */         
/* 100 */         properties.setProperty(str2, str3);
/*     */       } 
/*     */     }
/*     */     
/* 104 */     return properties;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void premain(String paramString) throws Exception {
/* 109 */     agentmain(paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void agentmain(String paramString) throws Exception {
/* 114 */     if (paramString == null || paramString.length() == 0) {
/* 115 */       paramString = "com.sun.management.jmxremote";
/*     */     }
/*     */     
/* 118 */     Properties properties1 = parseString(paramString);
/*     */ 
/*     */     
/* 121 */     Properties properties2 = new Properties();
/* 122 */     String str = properties1.getProperty("com.sun.management.config.file");
/* 123 */     readConfiguration(str, properties2);
/*     */ 
/*     */     
/* 126 */     properties2.putAll(properties1);
/* 127 */     startAgent(properties2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void startLocalManagementAgent() {
/* 133 */     Properties properties = VMSupport.getAgentProperties();
/*     */ 
/*     */     
/* 136 */     if (properties.get("com.sun.management.jmxremote.localConnectorAddress") == null) {
/* 137 */       JMXConnectorServer jMXConnectorServer = ConnectorBootstrap.startLocalConnectorServer();
/* 138 */       String str = jMXConnectorServer.getAddress().toString();
/*     */       
/* 140 */       properties.put("com.sun.management.jmxremote.localConnectorAddress", str);
/*     */ 
/*     */       
/*     */       try {
/* 144 */         ConnectorAddressLink.export(str);
/* 145 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 148 */         warning("agent.err.exportaddress.failed", exception.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void startRemoteManagementAgent(String paramString) throws Exception {
/* 158 */     if (jmxServer != null) {
/* 159 */       throw new RuntimeException(getText("agent.err.invalid.state", new String[] { "Agent already started" }));
/*     */     }
/*     */     
/*     */     try {
/* 163 */       Properties properties1 = parseString(paramString);
/* 164 */       Properties properties2 = new Properties();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       String str1 = System.getProperty("com.sun.management.config.file");
/* 171 */       readConfiguration(str1, properties2);
/*     */ 
/*     */ 
/*     */       
/* 175 */       Properties properties3 = System.getProperties();
/* 176 */       synchronized (properties3) {
/* 177 */         properties2.putAll(properties3);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 183 */       String str2 = properties1.getProperty("com.sun.management.config.file");
/* 184 */       if (str2 != null) {
/* 185 */         readConfiguration(str2, properties2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 191 */       properties2.putAll(properties1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 197 */       String str3 = properties2.getProperty("com.sun.management.enableThreadContentionMonitoring");
/*     */       
/* 199 */       if (str3 != null) {
/* 200 */         ManagementFactory.getThreadMXBean()
/* 201 */           .setThreadContentionMonitoringEnabled(true);
/*     */       }
/*     */       
/* 204 */       String str4 = properties2.getProperty("com.sun.management.jmxremote.port");
/* 205 */       if (str4 != null) {
/*     */         
/* 207 */         jmxServer = ConnectorBootstrap.startRemoteConnectorServer(str4, properties2);
/*     */         
/* 209 */         startDiscoveryService(properties2);
/*     */       } else {
/* 211 */         throw new AgentConfigurationError("agent.err.invalid.jmxremote.port", new String[] { "No port specified" });
/*     */       } 
/* 213 */     } catch (AgentConfigurationError agentConfigurationError) {
/* 214 */       error(agentConfigurationError);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized void stopRemoteManagementAgent() throws Exception {
/* 220 */     JdpController.stopDiscoveryService();
/*     */     
/* 222 */     if (jmxServer != null) {
/* 223 */       ConnectorBootstrap.unexportRegistry();
/*     */ 
/*     */ 
/*     */       
/* 227 */       jmxServer.stop();
/* 228 */       jmxServer = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void startAgent(Properties paramProperties) throws Exception {
/* 233 */     String str1 = paramProperties.getProperty("com.sun.management.snmp.port");
/* 234 */     String str2 = paramProperties.getProperty("com.sun.management.jmxremote");
/* 235 */     String str3 = paramProperties.getProperty("com.sun.management.jmxremote.port");
/*     */ 
/*     */ 
/*     */     
/* 239 */     String str4 = paramProperties.getProperty("com.sun.management.enableThreadContentionMonitoring");
/* 240 */     if (str4 != null) {
/* 241 */       ManagementFactory.getThreadMXBean()
/* 242 */         .setThreadContentionMonitoringEnabled(true);
/*     */     }
/*     */     
/*     */     try {
/* 246 */       if (str1 != null) {
/* 247 */         loadSnmpAgent(str1, paramProperties);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       if (str2 != null || str3 != null) {
/* 260 */         if (str3 != null) {
/*     */           
/* 262 */           jmxServer = ConnectorBootstrap.startRemoteConnectorServer(str3, paramProperties);
/* 263 */           startDiscoveryService(paramProperties);
/*     */         } 
/* 265 */         startLocalManagementAgent();
/*     */       }
/*     */     
/* 268 */     } catch (AgentConfigurationError agentConfigurationError) {
/* 269 */       error(agentConfigurationError);
/* 270 */     } catch (Exception exception) {
/* 271 */       error(exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void startDiscoveryService(Properties paramProperties) throws IOException {
/* 278 */     String str1 = paramProperties.getProperty("com.sun.management.jdp.port");
/* 279 */     String str2 = paramProperties.getProperty("com.sun.management.jdp.address");
/* 280 */     String str3 = paramProperties.getProperty("com.sun.management.jmxremote.autodiscovery");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     boolean bool = false;
/* 287 */     if (str3 == null) {
/* 288 */       bool = (str1 != null);
/*     */     } else {
/*     */       
/*     */       try {
/* 292 */         bool = Boolean.parseBoolean(str3);
/* 293 */       } catch (NumberFormatException numberFormatException) {
/* 294 */         throw new AgentConfigurationError("Couldn't parse autodiscovery argument");
/*     */       } 
/*     */     } 
/*     */     
/* 298 */     if (bool) {
/*     */       InetAddress inetAddress;
/*     */ 
/*     */       
/*     */       try {
/* 303 */         inetAddress = (str2 == null) ? InetAddress.getByName("224.0.23.178") : InetAddress.getByName(str2);
/* 304 */       } catch (UnknownHostException unknownHostException) {
/* 305 */         throw new AgentConfigurationError("Unable to broadcast to requested address", unknownHostException);
/*     */       } 
/*     */       
/* 308 */       int i = 7095;
/* 309 */       if (str1 != null) {
/*     */         try {
/* 311 */           i = Integer.parseInt(str1);
/* 312 */         } catch (NumberFormatException numberFormatException) {
/* 313 */           throw new AgentConfigurationError("Couldn't parse JDP port argument");
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 318 */       String str4 = paramProperties.getProperty("com.sun.management.jmxremote.port");
/* 319 */       String str5 = paramProperties.getProperty("com.sun.management.jmxremote.rmi.port");
/*     */       
/* 321 */       JMXServiceURL jMXServiceURL = jmxServer.getAddress();
/* 322 */       String str6 = jMXServiceURL.getHost();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 328 */       String str7 = (str5 != null) ? String.format("service:jmx:rmi://%s:%s/jndi/rmi://%s:%s/jmxrmi", new Object[] { str6, str5, str6, str4 }) : String.format("service:jmx:rmi:///jndi/rmi://%s:%s/jmxrmi", new Object[] { str6, str4 });
/*     */ 
/*     */       
/* 331 */       String str8 = paramProperties.getProperty("com.sun.management.jdp.name");
/*     */       
/*     */       try {
/* 334 */         JdpController.startDiscoveryService(inetAddress, i, str8, str7);
/*     */       }
/* 336 */       catch (JdpException jdpException) {
/* 337 */         throw new AgentConfigurationError("Couldn't start JDP service", jdpException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Properties loadManagementProperties() {
/* 343 */     Properties properties1 = new Properties();
/*     */ 
/*     */ 
/*     */     
/* 347 */     String str = System.getProperty("com.sun.management.config.file");
/* 348 */     readConfiguration(str, properties1);
/*     */ 
/*     */ 
/*     */     
/* 352 */     Properties properties2 = System.getProperties();
/* 353 */     synchronized (properties2) {
/* 354 */       properties1.putAll(properties2);
/*     */     } 
/*     */     
/* 357 */     return properties1;
/*     */   }
/*     */   
/*     */   public static synchronized Properties getManagementProperties() {
/* 361 */     if (mgmtProps == null) {
/* 362 */       String str1 = System.getProperty("com.sun.management.config.file");
/* 363 */       String str2 = System.getProperty("com.sun.management.snmp.port");
/* 364 */       String str3 = System.getProperty("com.sun.management.jmxremote");
/* 365 */       String str4 = System.getProperty("com.sun.management.jmxremote.port");
/*     */       
/* 367 */       if (str1 == null && str2 == null && str3 == null && str4 == null)
/*     */       {
/*     */         
/* 370 */         return null;
/*     */       }
/* 372 */       mgmtProps = loadManagementProperties();
/*     */     } 
/* 374 */     return mgmtProps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void loadSnmpAgent(String paramString, Properties paramProperties) {
/*     */     try {
/* 382 */       Class<?> clazz = Class.forName("sun.management.snmp.AdaptorBootstrap", true, null);
/*     */       
/* 384 */       Method method = clazz.getMethod("initialize", new Class[] { String.class, Properties.class });
/*     */       
/* 386 */       method.invoke(null, new Object[] { paramString, paramProperties });
/* 387 */     } catch (ClassNotFoundException|NoSuchMethodException|IllegalAccessException classNotFoundException) {
/*     */       
/* 389 */       throw new UnsupportedOperationException("Unsupported management property: com.sun.management.snmp.port", classNotFoundException);
/* 390 */     } catch (InvocationTargetException invocationTargetException) {
/* 391 */       Throwable throwable = invocationTargetException.getCause();
/* 392 */       if (throwable instanceof RuntimeException)
/* 393 */         throw (RuntimeException)throwable; 
/* 394 */       if (throwable instanceof Error) {
/* 395 */         throw (Error)throwable;
/*     */       }
/*     */       
/* 398 */       throw new UnsupportedOperationException("Unsupported management property: com.sun.management.snmp.port", throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void readConfiguration(String paramString, Properties paramProperties) {
/* 404 */     if (paramString == null) {
/* 405 */       String str = System.getProperty("java.home");
/* 406 */       if (str == null) {
/* 407 */         throw new Error("Can't find java.home ??");
/*     */       }
/* 409 */       StringBuffer stringBuffer = new StringBuffer(str);
/* 410 */       stringBuffer.append(File.separator).append("lib");
/* 411 */       stringBuffer.append(File.separator).append("management");
/* 412 */       stringBuffer.append(File.separator).append("management.properties");
/*     */       
/* 414 */       paramString = stringBuffer.toString();
/*     */     } 
/* 416 */     File file = new File(paramString);
/* 417 */     if (!file.exists()) {
/* 418 */       error("agent.err.configfile.notfound", paramString);
/*     */     }
/*     */     
/* 421 */     FileInputStream fileInputStream = null;
/*     */     try {
/* 423 */       fileInputStream = new FileInputStream(file);
/* 424 */       BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
/* 425 */       paramProperties.load(bufferedInputStream);
/* 426 */     } catch (FileNotFoundException fileNotFoundException) {
/* 427 */       error("agent.err.configfile.failed", fileNotFoundException.getMessage());
/* 428 */     } catch (IOException iOException) {
/* 429 */       error("agent.err.configfile.failed", iOException.getMessage());
/* 430 */     } catch (SecurityException securityException) {
/* 431 */       error("agent.err.configfile.access.denied", paramString);
/*     */     } finally {
/* 433 */       if (fileInputStream != null) {
/*     */         try {
/* 435 */           fileInputStream.close();
/* 436 */         } catch (IOException iOException) {
/* 437 */           error("agent.err.configfile.closed.failed", paramString);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void startAgent() throws Exception {
/* 444 */     String str1 = System.getProperty("com.sun.management.agent.class");
/*     */ 
/*     */ 
/*     */     
/* 448 */     if (str1 == null) {
/*     */       
/* 450 */       Properties properties = getManagementProperties();
/* 451 */       if (properties != null) {
/* 452 */         startAgent(properties);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 458 */     String[] arrayOfString = str1.split(":");
/* 459 */     if (arrayOfString.length < 1 || arrayOfString.length > 2) {
/* 460 */       error("agent.err.invalid.agentclass", "\"" + str1 + "\"");
/*     */     }
/* 462 */     String str2 = arrayOfString[0];
/* 463 */     String str3 = (arrayOfString.length == 2) ? arrayOfString[1] : null;
/*     */     
/* 465 */     if (str2 == null || str2.length() == 0) {
/* 466 */       error("agent.err.invalid.agentclass", "\"" + str1 + "\"");
/*     */     }
/*     */     
/* 469 */     if (str2 != null) {
/*     */       
/*     */       try {
/*     */         
/* 473 */         Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(str2);
/* 474 */         Method method = clazz.getMethod("premain", new Class[] { String.class });
/*     */         
/* 476 */         method.invoke(null, new Object[] { str3 });
/*     */       }
/* 478 */       catch (ClassNotFoundException classNotFoundException) {
/* 479 */         error("agent.err.agentclass.notfound", "\"" + str2 + "\"");
/* 480 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 481 */         error("agent.err.premain.notfound", "\"" + str2 + "\"");
/* 482 */       } catch (SecurityException securityException) {
/* 483 */         error("agent.err.agentclass.access.denied");
/* 484 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 487 */         String str = (exception.getCause() == null) ? exception.getMessage() : exception.getCause().getMessage();
/* 488 */         error("agent.err.agentclass.failed", str);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void error(String paramString) {
/* 494 */     String str = getText(paramString);
/* 495 */     System.err.print(getText("agent.err.error") + ": " + str);
/* 496 */     throw new RuntimeException(str);
/*     */   }
/*     */   
/*     */   public static void error(String paramString1, String paramString2) {
/* 500 */     String str = getText(paramString1);
/* 501 */     System.err.print(getText("agent.err.error") + ": " + str);
/* 502 */     System.err.println(": " + paramString2);
/* 503 */     throw new RuntimeException(str + ": " + paramString2);
/*     */   }
/*     */   
/*     */   public static void error(Exception paramException) {
/* 507 */     paramException.printStackTrace();
/* 508 */     System.err.println(getText("agent.err.exception") + ": " + paramException.toString());
/* 509 */     throw new RuntimeException(paramException);
/*     */   }
/*     */   
/*     */   public static void error(AgentConfigurationError paramAgentConfigurationError) {
/* 513 */     String str = getText(paramAgentConfigurationError.getError());
/* 514 */     String[] arrayOfString = paramAgentConfigurationError.getParams();
/*     */     
/* 516 */     System.err.print(getText("agent.err.error") + ": " + str);
/*     */     
/* 518 */     if (arrayOfString != null && arrayOfString.length != 0) {
/* 519 */       StringBuffer stringBuffer = new StringBuffer(arrayOfString[0]);
/* 520 */       for (byte b = 1; b < arrayOfString.length; b++) {
/* 521 */         stringBuffer.append(" " + arrayOfString[b]);
/*     */       }
/* 523 */       System.err.println(": " + stringBuffer);
/*     */     } 
/* 525 */     paramAgentConfigurationError.printStackTrace();
/* 526 */     throw new RuntimeException(paramAgentConfigurationError);
/*     */   }
/*     */   
/*     */   public static void warning(String paramString1, String paramString2) {
/* 530 */     System.err.print(getText("agent.err.warning") + ": " + getText(paramString1));
/* 531 */     System.err.println(": " + paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initResource() {
/*     */     try {
/* 537 */       messageRB = ResourceBundle.getBundle("sun.management.resources.agent");
/* 538 */     } catch (MissingResourceException missingResourceException) {
/* 539 */       throw new Error("Fatal: Resource for management agent is missing");
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getText(String paramString) {
/* 544 */     if (messageRB == null) {
/* 545 */       initResource();
/*     */     }
/*     */     try {
/* 548 */       return messageRB.getString(paramString);
/* 549 */     } catch (MissingResourceException missingResourceException) {
/* 550 */       return "Missing management agent resource bundle: key = \"" + paramString + "\"";
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getText(String paramString, String... paramVarArgs) {
/* 555 */     if (messageRB == null) {
/* 556 */       initResource();
/*     */     }
/* 558 */     String str = messageRB.getString(paramString);
/* 559 */     if (str == null) {
/* 560 */       str = "missing resource key: key = \"" + paramString + "\", arguments = \"{0}\", \"{1}\", \"{2}\"";
/*     */     }
/*     */     
/* 563 */     return MessageFormat.format(str, (Object[])paramVarArgs);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/Agent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */