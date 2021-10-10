/*      */ package sun.management.jmxremote;
/*      */ 
/*      */ import com.sun.jmx.remote.internal.RMIExporter;
/*      */ import com.sun.jmx.remote.security.JMXPluggableAuthenticator;
/*      */ import com.sun.jmx.remote.util.ClassLogger;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.lang.management.ManagementFactory;
/*      */ import java.net.InetAddress;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.ServerSocket;
/*      */ import java.net.Socket;
/*      */ import java.net.UnknownHostException;
/*      */ import java.rmi.NoSuchObjectException;
/*      */ import java.rmi.Remote;
/*      */ import java.rmi.RemoteException;
/*      */ import java.rmi.registry.Registry;
/*      */ import java.rmi.server.RMIClientSocketFactory;
/*      */ import java.rmi.server.RMIServerSocketFactory;
/*      */ import java.rmi.server.RemoteObject;
/*      */ import java.rmi.server.UnicastRemoteObject;
/*      */ import java.security.KeyStore;
/*      */ import java.security.Principal;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.remote.JMXAuthenticator;
/*      */ import javax.management.remote.JMXConnectorServer;
/*      */ import javax.management.remote.JMXConnectorServerFactory;
/*      */ import javax.management.remote.JMXServiceURL;
/*      */ import javax.net.ssl.KeyManagerFactory;
/*      */ import javax.net.ssl.SSLContext;
/*      */ import javax.net.ssl.SSLSocket;
/*      */ import javax.net.ssl.SSLSocketFactory;
/*      */ import javax.net.ssl.TrustManagerFactory;
/*      */ import javax.rmi.ssl.SslRMIClientSocketFactory;
/*      */ import javax.rmi.ssl.SslRMIServerSocketFactory;
/*      */ import javax.security.auth.Subject;
/*      */ import sun.management.Agent;
/*      */ import sun.management.AgentConfigurationError;
/*      */ import sun.management.ConnectorAddressLink;
/*      */ import sun.management.FileSystem;
/*      */ import sun.rmi.server.UnicastRef;
/*      */ import sun.rmi.server.UnicastServerRef;
/*      */ import sun.rmi.server.UnicastServerRef2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ConnectorBootstrap
/*      */ {
/*      */   public static interface DefaultValues
/*      */   {
/*      */     public static final String PORT = "0";
/*      */     public static final String CONFIG_FILE_NAME = "management.properties";
/*      */     public static final String USE_SSL = "true";
/*      */     public static final String USE_LOCAL_ONLY = "true";
/*      */     public static final String USE_REGISTRY_SSL = "false";
/*      */     public static final String USE_AUTHENTICATION = "true";
/*      */     public static final String PASSWORD_FILE_NAME = "jmxremote.password";
/*      */     public static final String ACCESS_FILE_NAME = "jmxremote.access";
/*      */     public static final String SSL_NEED_CLIENT_AUTH = "false";
/*      */   }
/*      */   
/*      */   public static interface PropertyNames
/*      */   {
/*      */     public static final String PORT = "com.sun.management.jmxremote.port";
/*      */     public static final String HOST = "com.sun.management.jmxremote.host";
/*      */     public static final String RMI_PORT = "com.sun.management.jmxremote.rmi.port";
/*      */     public static final String CONFIG_FILE_NAME = "com.sun.management.config.file";
/*      */     public static final String USE_LOCAL_ONLY = "com.sun.management.jmxremote.local.only";
/*      */     public static final String USE_SSL = "com.sun.management.jmxremote.ssl";
/*      */     public static final String USE_REGISTRY_SSL = "com.sun.management.jmxremote.registry.ssl";
/*      */     public static final String USE_AUTHENTICATION = "com.sun.management.jmxremote.authenticate";
/*      */     public static final String PASSWORD_FILE_NAME = "com.sun.management.jmxremote.password.file";
/*      */     public static final String ACCESS_FILE_NAME = "com.sun.management.jmxremote.access.file";
/*      */     public static final String LOGIN_CONFIG_NAME = "com.sun.management.jmxremote.login.config";
/*      */     public static final String SSL_ENABLED_CIPHER_SUITES = "com.sun.management.jmxremote.ssl.enabled.cipher.suites";
/*      */     public static final String SSL_ENABLED_PROTOCOLS = "com.sun.management.jmxremote.ssl.enabled.protocols";
/*      */     public static final String SSL_NEED_CLIENT_AUTH = "com.sun.management.jmxremote.ssl.need.client.auth";
/*      */     public static final String SSL_CONFIG_FILE_NAME = "com.sun.management.jmxremote.ssl.config.file";
/*      */   }
/*      */   
/*      */   private static class JMXConnectorServerData
/*      */   {
/*      */     JMXConnectorServer jmxConnectorServer;
/*      */     JMXServiceURL jmxRemoteURL;
/*      */     
/*      */     public JMXConnectorServerData(JMXConnectorServer param1JMXConnectorServer, JMXServiceURL param1JMXServiceURL) {
/*  154 */       this.jmxConnectorServer = param1JMXConnectorServer;
/*  155 */       this.jmxRemoteURL = param1JMXServiceURL;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PermanentExporter
/*      */     implements RMIExporter
/*      */   {
/*      */     Remote firstExported;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private PermanentExporter() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Remote exportObject(Remote param1Remote, int param1Int, RMIClientSocketFactory param1RMIClientSocketFactory, RMIServerSocketFactory param1RMIServerSocketFactory) throws RemoteException {
/*      */       UnicastServerRef unicastServerRef;
/*  187 */       synchronized (this) {
/*  188 */         if (this.firstExported == null) {
/*  189 */           this.firstExported = param1Remote;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  194 */       if (param1RMIClientSocketFactory == null && param1RMIServerSocketFactory == null) {
/*  195 */         unicastServerRef = new UnicastServerRef(param1Int);
/*      */       } else {
/*  197 */         unicastServerRef = new UnicastServerRef2(param1Int, param1RMIClientSocketFactory, param1RMIServerSocketFactory);
/*      */       } 
/*  199 */       return unicastServerRef.exportObject(param1Remote, null, true);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean unexportObject(Remote param1Remote, boolean param1Boolean) throws NoSuchObjectException {
/*  205 */       return UnicastRemoteObject.unexportObject(param1Remote, param1Boolean);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class AccessFileCheckerAuthenticator
/*      */     implements JMXAuthenticator
/*      */   {
/*      */     private final Map<String, Object> environment;
/*      */     
/*      */     private final Properties properties;
/*      */     private final String accessFile;
/*      */     
/*      */     public AccessFileCheckerAuthenticator(Map<String, Object> param1Map) throws IOException {
/*  219 */       this.environment = param1Map;
/*  220 */       this.accessFile = (String)param1Map.get("jmx.remote.x.access.file");
/*  221 */       this.properties = propertiesFromFile(this.accessFile);
/*      */     }
/*      */     
/*      */     public Subject authenticate(Object param1Object) {
/*  225 */       JMXPluggableAuthenticator jMXPluggableAuthenticator = new JMXPluggableAuthenticator(this.environment);
/*      */       
/*  227 */       Subject subject = jMXPluggableAuthenticator.authenticate(param1Object);
/*  228 */       checkAccessFileEntries(subject);
/*  229 */       return subject;
/*      */     }
/*      */     
/*      */     private void checkAccessFileEntries(Subject param1Subject) {
/*  233 */       if (param1Subject == null) {
/*  234 */         throw new SecurityException("Access denied! No matching entries found in the access file [" + this.accessFile + "] as the authenticated Subject is null");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  239 */       Set<Principal> set = param1Subject.getPrincipals();
/*  240 */       for (Principal principal : set) {
/*  241 */         if (this.properties.containsKey(principal.getName())) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */       
/*  246 */       HashSet<String> hashSet = new HashSet();
/*  247 */       for (Principal principal : set) {
/*  248 */         hashSet.add(principal.getName());
/*      */       }
/*  250 */       throw new SecurityException("Access denied! No entries found in the access file [" + this.accessFile + "] for any of the authenticated identities " + hashSet);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static Properties propertiesFromFile(String param1String) throws IOException {
/*  258 */       Properties properties = new Properties();
/*  259 */       if (param1String == null) {
/*  260 */         return properties;
/*      */       }
/*  262 */       try (FileInputStream null = new FileInputStream(param1String)) {
/*  263 */         properties.load(fileInputStream);
/*      */       } 
/*  265 */       return properties;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  275 */   private static Registry registry = null;
/*      */ 
/*      */   
/*      */   public static void unexportRegistry() {
/*      */     try {
/*  280 */       if (registry != null) {
/*  281 */         UnicastRemoteObject.unexportObject(registry, true);
/*  282 */         registry = null;
/*      */       } 
/*  284 */     } catch (NoSuchObjectException noSuchObjectException) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized JMXConnectorServer initialize() {
/*  303 */     Properties properties = Agent.loadManagementProperties();
/*  304 */     if (properties == null) {
/*  305 */       return null;
/*      */     }
/*      */     
/*  308 */     String str = properties.getProperty("com.sun.management.jmxremote.port");
/*  309 */     return startRemoteConnectorServer(str, properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized JMXConnectorServer initialize(String paramString, Properties paramProperties) {
/*  319 */     return startRemoteConnectorServer(paramString, paramProperties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static synchronized JMXConnectorServer startRemoteConnectorServer(String paramString, Properties paramProperties) {
/*      */     int i;
/*      */     try {
/*  331 */       i = Integer.parseInt(paramString);
/*  332 */     } catch (NumberFormatException numberFormatException) {
/*  333 */       throw new AgentConfigurationError("agent.err.invalid.jmxremote.port", numberFormatException, new String[] { paramString });
/*      */     } 
/*  335 */     if (i < 0) {
/*  336 */       throw new AgentConfigurationError("agent.err.invalid.jmxremote.port", new String[] { paramString });
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  342 */     int j = 0;
/*  343 */     String str1 = paramProperties.getProperty("com.sun.management.jmxremote.rmi.port");
/*      */     try {
/*  345 */       if (str1 != null) {
/*  346 */         j = Integer.parseInt(str1);
/*      */       }
/*  348 */     } catch (NumberFormatException numberFormatException) {
/*  349 */       throw new AgentConfigurationError("agent.err.invalid.jmxremote.rmi.port", numberFormatException, new String[] { str1 });
/*      */     } 
/*  351 */     if (j < 0) {
/*  352 */       throw new AgentConfigurationError("agent.err.invalid.jmxremote.rmi.port", new String[] { str1 });
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  357 */     String str2 = paramProperties.getProperty("com.sun.management.jmxremote.authenticate", "true");
/*      */ 
/*      */     
/*  360 */     boolean bool1 = Boolean.valueOf(str2).booleanValue();
/*      */ 
/*      */ 
/*      */     
/*  364 */     String str3 = paramProperties.getProperty("com.sun.management.jmxremote.ssl", "true");
/*      */ 
/*      */     
/*  367 */     boolean bool2 = Boolean.valueOf(str3).booleanValue();
/*      */ 
/*      */ 
/*      */     
/*  371 */     String str4 = paramProperties.getProperty("com.sun.management.jmxremote.registry.ssl", "false");
/*      */ 
/*      */     
/*  374 */     boolean bool3 = Boolean.valueOf(str4).booleanValue();
/*      */ 
/*      */     
/*  377 */     String str5 = paramProperties.getProperty("com.sun.management.jmxremote.ssl.enabled.cipher.suites");
/*  378 */     String[] arrayOfString1 = null;
/*  379 */     if (str5 != null) {
/*  380 */       StringTokenizer stringTokenizer = new StringTokenizer(str5, ",");
/*  381 */       int k = stringTokenizer.countTokens();
/*  382 */       arrayOfString1 = new String[k];
/*  383 */       for (byte b = 0; b < k; b++) {
/*  384 */         arrayOfString1[b] = stringTokenizer.nextToken();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  389 */     String str6 = paramProperties.getProperty("com.sun.management.jmxremote.ssl.enabled.protocols");
/*  390 */     String[] arrayOfString2 = null;
/*  391 */     if (str6 != null) {
/*  392 */       StringTokenizer stringTokenizer = new StringTokenizer(str6, ",");
/*  393 */       int k = stringTokenizer.countTokens();
/*  394 */       arrayOfString2 = new String[k];
/*  395 */       for (byte b = 0; b < k; b++) {
/*  396 */         arrayOfString2[b] = stringTokenizer.nextToken();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  401 */     String str7 = paramProperties.getProperty("com.sun.management.jmxremote.ssl.need.client.auth", "false");
/*      */ 
/*      */     
/*  404 */     boolean bool4 = Boolean.valueOf(str7).booleanValue();
/*      */ 
/*      */ 
/*      */     
/*  408 */     String str8 = paramProperties.getProperty("com.sun.management.jmxremote.ssl.config.file");
/*      */     
/*  410 */     String str9 = null;
/*  411 */     String str10 = null;
/*  412 */     String str11 = null;
/*      */ 
/*      */     
/*  415 */     if (bool1) {
/*      */ 
/*      */ 
/*      */       
/*  419 */       str9 = paramProperties.getProperty("com.sun.management.jmxremote.login.config");
/*      */       
/*  421 */       if (str9 == null) {
/*      */ 
/*      */         
/*  424 */         str10 = paramProperties.getProperty("com.sun.management.jmxremote.password.file", 
/*  425 */             getDefaultFileName("jmxremote.password"));
/*  426 */         checkPasswordFile(str10);
/*      */       } 
/*      */ 
/*      */       
/*  430 */       str11 = paramProperties.getProperty("com.sun.management.jmxremote.access.file", 
/*  431 */           getDefaultFileName("jmxremote.access"));
/*  432 */       checkAccessFile(str11);
/*      */     } 
/*      */ 
/*      */     
/*  436 */     String str12 = paramProperties.getProperty("com.sun.management.jmxremote.host");
/*      */     
/*  438 */     if (log.debugOn()) {
/*  439 */       log.debug("startRemoteConnectorServer", 
/*  440 */           Agent.getText("jmxremote.ConnectorBootstrap.starting") + "\n\t" + "com.sun.management.jmxremote.port" + "=" + i + ((str12 == null) ? "" : ("\n\tcom.sun.management.jmxremote.host=" + str12)) + "\n\t" + "com.sun.management.jmxremote.rmi.port" + "=" + j + "\n\t" + "com.sun.management.jmxremote.ssl" + "=" + bool2 + "\n\t" + "com.sun.management.jmxremote.registry.ssl" + "=" + bool3 + "\n\t" + "com.sun.management.jmxremote.ssl.config.file" + "=" + str8 + "\n\t" + "com.sun.management.jmxremote.ssl.enabled.cipher.suites" + "=" + str5 + "\n\t" + "com.sun.management.jmxremote.ssl.enabled.protocols" + "=" + str6 + "\n\t" + "com.sun.management.jmxremote.ssl.need.client.auth" + "=" + bool4 + "\n\t" + "com.sun.management.jmxremote.authenticate" + "=" + bool1 + (bool1 ? ((str9 == null) ? ("\n\tcom.sun.management.jmxremote.password.file=" + str10) : ("\n\tcom.sun.management.jmxremote.login.config=" + str9)) : ("\n\t" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  458 */           Agent.getText("jmxremote.ConnectorBootstrap.noAuthentication"))) + (bool1 ? ("\n\tcom.sun.management.jmxremote.access.file=" + str11) : "") + "");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  464 */     MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
/*  465 */     JMXConnectorServer jMXConnectorServer = null;
/*  466 */     JMXServiceURL jMXServiceURL = null;
/*      */     try {
/*  468 */       JMXConnectorServerData jMXConnectorServerData = exportMBeanServer(mBeanServer, i, j, bool2, bool3, str8, arrayOfString1, arrayOfString2, bool4, bool1, str9, str10, str11, str12);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  474 */       jMXConnectorServer = jMXConnectorServerData.jmxConnectorServer;
/*  475 */       jMXServiceURL = jMXConnectorServerData.jmxRemoteURL;
/*  476 */       log.config("startRemoteConnectorServer", 
/*  477 */           Agent.getText("jmxremote.ConnectorBootstrap.ready", new String[] {
/*  478 */               jMXServiceURL.toString() }));
/*  479 */     } catch (Exception exception) {
/*  480 */       throw new AgentConfigurationError("agent.err.exception", exception, new String[] { exception.toString() });
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  485 */       HashMap<Object, Object> hashMap = new HashMap<>();
/*  486 */       hashMap.put("remoteAddress", jMXServiceURL.toString());
/*  487 */       hashMap.put("authenticate", str2);
/*  488 */       hashMap.put("ssl", str3);
/*  489 */       hashMap.put("sslRegistry", str4);
/*  490 */       hashMap.put("sslNeedClientAuth", str7);
/*  491 */       ConnectorAddressLink.exportRemote(hashMap);
/*  492 */     } catch (Exception exception) {
/*      */ 
/*      */ 
/*      */       
/*  496 */       log.debug("startRemoteConnectorServer", exception);
/*      */     } 
/*  498 */     return jMXConnectorServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static JMXConnectorServer startLocalConnectorServer() {
/*  508 */     System.setProperty("java.rmi.server.randomIDs", "true");
/*      */ 
/*      */     
/*  511 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*  512 */     hashMap.put("com.sun.jmx.remote.rmi.exporter", new PermanentExporter());
/*  513 */     hashMap.put("jmx.remote.rmi.server.credential.types", new String[] { String[].class
/*  514 */           .getName(), String.class.getName() });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  519 */     String str = "localhost";
/*  520 */     InetAddress inetAddress = null;
/*      */     try {
/*  522 */       inetAddress = InetAddress.getByName(str);
/*  523 */       str = inetAddress.getHostAddress();
/*  524 */     } catch (UnknownHostException unknownHostException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  529 */     if (inetAddress == null || !inetAddress.isLoopbackAddress()) {
/*  530 */       str = "127.0.0.1";
/*      */     }
/*      */     
/*  533 */     MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
/*      */     try {
/*  535 */       JMXServiceURL jMXServiceURL = new JMXServiceURL("rmi", str, 0);
/*      */       
/*  537 */       Properties properties = Agent.getManagementProperties();
/*  538 */       if (properties == null) {
/*  539 */         properties = new Properties();
/*      */       }
/*  541 */       String str1 = properties.getProperty("com.sun.management.jmxremote.local.only", "true");
/*      */       
/*  543 */       boolean bool = Boolean.valueOf(str1).booleanValue();
/*  544 */       if (bool) {
/*  545 */         hashMap.put("jmx.remote.rmi.server.socket.factory", new LocalRMIServerSocketFactory());
/*      */       }
/*      */ 
/*      */       
/*  549 */       JMXConnectorServer jMXConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(jMXServiceURL, (Map)hashMap, mBeanServer);
/*  550 */       jMXConnectorServer.start();
/*  551 */       return jMXConnectorServer;
/*  552 */     } catch (Exception exception) {
/*  553 */       throw new AgentConfigurationError("agent.err.exception", exception, new String[] { exception.toString() });
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void checkPasswordFile(String paramString) {
/*  558 */     if (paramString == null || paramString.length() == 0) {
/*  559 */       throw new AgentConfigurationError("agent.err.password.file.notset");
/*      */     }
/*  561 */     File file = new File(paramString);
/*  562 */     if (!file.exists()) {
/*  563 */       throw new AgentConfigurationError("agent.err.password.file.notfound", new String[] { paramString });
/*      */     }
/*      */     
/*  566 */     if (!file.canRead()) {
/*  567 */       throw new AgentConfigurationError("agent.err.password.file.not.readable", new String[] { paramString });
/*      */     }
/*      */     
/*  570 */     FileSystem fileSystem = FileSystem.open();
/*      */     try {
/*  572 */       if (fileSystem.supportsFileSecurity(file) && 
/*  573 */         !fileSystem.isAccessUserOnly(file)) {
/*  574 */         String str = Agent.getText("jmxremote.ConnectorBootstrap.password.readonly", new String[] { paramString });
/*      */         
/*  576 */         log.config("startRemoteConnectorServer", str);
/*  577 */         throw new AgentConfigurationError("agent.err.password.file.access.notrestricted", new String[] { paramString });
/*      */       }
/*      */     
/*      */     }
/*  581 */     catch (IOException iOException) {
/*  582 */       throw new AgentConfigurationError("agent.err.password.file.read.failed", iOException, new String[] { paramString });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkAccessFile(String paramString) {
/*  588 */     if (paramString == null || paramString.length() == 0) {
/*  589 */       throw new AgentConfigurationError("agent.err.access.file.notset");
/*      */     }
/*  591 */     File file = new File(paramString);
/*  592 */     if (!file.exists()) {
/*  593 */       throw new AgentConfigurationError("agent.err.access.file.notfound", new String[] { paramString });
/*      */     }
/*      */     
/*  596 */     if (!file.canRead()) {
/*  597 */       throw new AgentConfigurationError("agent.err.access.file.not.readable", new String[] { paramString });
/*      */     }
/*      */   }
/*      */   
/*      */   private static void checkRestrictedFile(String paramString) {
/*  602 */     if (paramString == null || paramString.length() == 0) {
/*  603 */       throw new AgentConfigurationError("agent.err.file.not.set");
/*      */     }
/*  605 */     File file = new File(paramString);
/*  606 */     if (!file.exists()) {
/*  607 */       throw new AgentConfigurationError("agent.err.file.not.found", new String[] { paramString });
/*      */     }
/*  609 */     if (!file.canRead()) {
/*  610 */       throw new AgentConfigurationError("agent.err.file.not.readable", new String[] { paramString });
/*      */     }
/*  612 */     FileSystem fileSystem = FileSystem.open();
/*      */     try {
/*  614 */       if (fileSystem.supportsFileSecurity(file) && 
/*  615 */         !fileSystem.isAccessUserOnly(file)) {
/*  616 */         String str = Agent.getText("jmxremote.ConnectorBootstrap.file.readonly", new String[] { paramString });
/*      */ 
/*      */         
/*  619 */         log.config("startRemoteConnectorServer", str);
/*  620 */         throw new AgentConfigurationError("agent.err.file.access.not.restricted", new String[] { paramString });
/*      */       }
/*      */     
/*      */     }
/*  624 */     catch (IOException iOException) {
/*  625 */       throw new AgentConfigurationError("agent.err.file.read.failed", iOException, new String[] { paramString });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getDefaultFileName(String paramString) {
/*  636 */     String str = File.separator;
/*  637 */     return System.getProperty("java.home") + str + "lib" + str + "management" + str + paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static SslRMIServerSocketFactory createSslRMIServerSocketFactory(String paramString1, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean, String paramString2) {
/*  648 */     if (paramString1 == null) {
/*  649 */       return new HostAwareSslSocketFactory(paramArrayOfString1, paramArrayOfString2, paramBoolean, paramString2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  654 */     checkRestrictedFile(paramString1);
/*      */     
/*      */     try {
/*  657 */       Properties properties = new Properties();
/*  658 */       try (FileInputStream null = new FileInputStream(paramString1)) {
/*  659 */         BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
/*  660 */         properties.load(bufferedInputStream);
/*      */       } 
/*      */       
/*  663 */       String str1 = properties.getProperty("javax.net.ssl.keyStore");
/*      */       
/*  665 */       String str2 = properties.getProperty("javax.net.ssl.keyStorePassword", "");
/*      */       
/*  667 */       String str3 = properties.getProperty("javax.net.ssl.trustStore");
/*      */       
/*  669 */       String str4 = properties.getProperty("javax.net.ssl.trustStorePassword", "");
/*      */       
/*  671 */       char[] arrayOfChar1 = null;
/*  672 */       if (str2.length() != 0) {
/*  673 */         arrayOfChar1 = str2.toCharArray();
/*      */       }
/*      */       
/*  676 */       char[] arrayOfChar2 = null;
/*  677 */       if (str4.length() != 0) {
/*  678 */         arrayOfChar2 = str4.toCharArray();
/*      */       }
/*      */       
/*  681 */       KeyStore keyStore1 = null;
/*  682 */       if (str1 != null) {
/*  683 */         keyStore1 = KeyStore.getInstance(KeyStore.getDefaultType());
/*  684 */         try (FileInputStream null = new FileInputStream(str1)) {
/*  685 */           keyStore1.load(fileInputStream1, arrayOfChar1);
/*      */         } 
/*      */       } 
/*  688 */       KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
/*  689 */           KeyManagerFactory.getDefaultAlgorithm());
/*  690 */       keyManagerFactory.init(keyStore1, arrayOfChar1);
/*      */       
/*  692 */       KeyStore keyStore2 = null;
/*  693 */       if (str3 != null) {
/*  694 */         keyStore2 = KeyStore.getInstance(KeyStore.getDefaultType());
/*  695 */         try (FileInputStream null = new FileInputStream(str3)) {
/*  696 */           keyStore2.load(fileInputStream1, arrayOfChar2);
/*      */         } 
/*      */       } 
/*  699 */       TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
/*  700 */           TrustManagerFactory.getDefaultAlgorithm());
/*  701 */       trustManagerFactory.init(keyStore2);
/*      */       
/*  703 */       SSLContext sSLContext = SSLContext.getInstance("SSL");
/*  704 */       sSLContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
/*      */       
/*  706 */       return new HostAwareSslSocketFactory(sSLContext, paramArrayOfString1, paramArrayOfString2, paramBoolean, paramString2);
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  711 */     catch (Exception exception) {
/*  712 */       throw new AgentConfigurationError("agent.err.exception", exception, new String[] { exception.toString() });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static JMXConnectorServerData exportMBeanServer(MBeanServer paramMBeanServer, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, String paramString1, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean3, boolean paramBoolean4, String paramString2, String paramString3, String paramString4, String paramString5) throws IOException, MalformedURLException {
/*      */     HostAwareSocketFactory hostAwareSocketFactory;
/*  737 */     System.setProperty("java.rmi.server.randomIDs", "true");
/*      */     
/*  739 */     JMXServiceURL jMXServiceURL1 = new JMXServiceURL("rmi", paramString5, paramInt2);
/*      */     
/*  741 */     HashMap<Object, Object> hashMap = new HashMap<>();
/*      */     
/*  743 */     PermanentExporter permanentExporter = new PermanentExporter();
/*      */     
/*  745 */     hashMap.put("com.sun.jmx.remote.rmi.exporter", permanentExporter);
/*  746 */     hashMap.put("jmx.remote.rmi.server.credential.types", new String[] { String[].class
/*  747 */           .getName(), String.class.getName() });
/*      */ 
/*      */     
/*  750 */     boolean bool = (paramString5 != null && !paramBoolean1) ? true : false;
/*      */     
/*  752 */     if (paramBoolean4) {
/*  753 */       if (paramString2 != null) {
/*  754 */         hashMap.put("jmx.remote.x.login.config", paramString2);
/*      */       }
/*  756 */       if (paramString3 != null) {
/*  757 */         hashMap.put("jmx.remote.x.password.file", paramString3);
/*      */       }
/*      */       
/*  760 */       hashMap.put("jmx.remote.x.access.file", paramString4);
/*      */       
/*  762 */       if (hashMap.get("jmx.remote.x.password.file") != null || hashMap
/*  763 */         .get("jmx.remote.x.login.config") != null) {
/*  764 */         hashMap.put("jmx.remote.authenticator", new AccessFileCheckerAuthenticator((Map)hashMap));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  769 */     SslRMIClientSocketFactory sslRMIClientSocketFactory = null;
/*  770 */     SslRMIServerSocketFactory sslRMIServerSocketFactory = null;
/*      */     
/*  772 */     if (paramBoolean1 || paramBoolean2) {
/*  773 */       sslRMIClientSocketFactory = new SslRMIClientSocketFactory();
/*  774 */       sslRMIServerSocketFactory = createSslRMIServerSocketFactory(paramString1, paramArrayOfString1, paramArrayOfString2, paramBoolean3, paramString5);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  779 */     if (paramBoolean1) {
/*  780 */       hashMap.put("jmx.remote.rmi.client.socket.factory", sslRMIClientSocketFactory);
/*      */       
/*  782 */       hashMap.put("jmx.remote.rmi.server.socket.factory", sslRMIServerSocketFactory);
/*      */     } 
/*      */ 
/*      */     
/*  786 */     if (bool) {
/*  787 */       hostAwareSocketFactory = new HostAwareSocketFactory(paramString5);
/*  788 */       hashMap.put("jmx.remote.rmi.server.socket.factory", hostAwareSocketFactory);
/*      */     } 
/*      */ 
/*      */     
/*  792 */     JMXConnectorServer jMXConnectorServer = null;
/*      */     
/*      */     try {
/*  795 */       jMXConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(jMXServiceURL1, (Map)hashMap, paramMBeanServer);
/*  796 */       jMXConnectorServer.start();
/*  797 */     } catch (IOException iOException) {
/*  798 */       if (jMXConnectorServer == null || jMXConnectorServer.getAddress() == null) {
/*  799 */         throw new AgentConfigurationError("agent.err.connector.server.io.error", iOException, new String[] { jMXServiceURL1
/*  800 */               .toString() });
/*      */       }
/*  802 */       throw new AgentConfigurationError("agent.err.connector.server.io.error", iOException, new String[] { jMXConnectorServer
/*  803 */             .getAddress().toString() });
/*      */     } 
/*      */ 
/*      */     
/*  807 */     if (paramBoolean2) {
/*  808 */       registry = new SingleEntryRegistry(paramInt1, sslRMIClientSocketFactory, hostAwareSocketFactory, "jmxrmi", permanentExporter.firstExported);
/*      */     
/*      */     }
/*  811 */     else if (bool) {
/*  812 */       registry = new SingleEntryRegistry(paramInt1, sslRMIClientSocketFactory, hostAwareSocketFactory, "jmxrmi", permanentExporter.firstExported);
/*      */     }
/*      */     else {
/*      */       
/*  816 */       registry = new SingleEntryRegistry(paramInt1, "jmxrmi", permanentExporter.firstExported);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  823 */     int i = ((UnicastRef)((RemoteObject)registry).getRef()).getLiveRef().getPort();
/*  824 */     String str = String.format("service:jmx:rmi:///jndi/rmi://%s:%d/jmxrmi", new Object[] { jMXServiceURL1
/*  825 */           .getHost(), Integer.valueOf(i) });
/*  826 */     JMXServiceURL jMXServiceURL2 = new JMXServiceURL(str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  836 */     return new JMXConnectorServerData(jMXConnectorServer, jMXServiceURL2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  845 */   private static final ClassLogger log = new ClassLogger(ConnectorBootstrap.class
/*  846 */       .getPackage().getName(), "ConnectorBootstrap");
/*      */   
/*      */   private static class HostAwareSocketFactory
/*      */     implements RMIServerSocketFactory
/*      */   {
/*      */     private final String bindAddress;
/*      */     
/*      */     private HostAwareSocketFactory(String param1String) {
/*  854 */       this.bindAddress = param1String;
/*      */     }
/*      */ 
/*      */     
/*      */     public ServerSocket createServerSocket(int param1Int) throws IOException {
/*  859 */       if (this.bindAddress == null) {
/*  860 */         return new ServerSocket(param1Int);
/*      */       }
/*      */       try {
/*  863 */         InetAddress inetAddress = InetAddress.getByName(this.bindAddress);
/*  864 */         return new ServerSocket(param1Int, 0, inetAddress);
/*  865 */       } catch (UnknownHostException unknownHostException) {
/*  866 */         return new ServerSocket(param1Int);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class HostAwareSslSocketFactory
/*      */     extends SslRMIServerSocketFactory
/*      */   {
/*      */     private final String bindAddress;
/*      */     
/*      */     private final String[] enabledCipherSuites;
/*      */     
/*      */     private final String[] enabledProtocols;
/*      */     private final boolean needClientAuth;
/*      */     private final SSLContext context;
/*      */     
/*      */     private HostAwareSslSocketFactory(String[] param1ArrayOfString1, String[] param1ArrayOfString2, boolean param1Boolean, String param1String) throws IllegalArgumentException {
/*  884 */       this((SSLContext)null, param1ArrayOfString1, param1ArrayOfString2, param1Boolean, param1String);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private HostAwareSslSocketFactory(SSLContext param1SSLContext, String[] param1ArrayOfString1, String[] param1ArrayOfString2, boolean param1Boolean, String param1String) throws IllegalArgumentException {
/*  892 */       this.context = param1SSLContext;
/*  893 */       this.bindAddress = param1String;
/*  894 */       this.enabledProtocols = param1ArrayOfString2;
/*  895 */       this.enabledCipherSuites = param1ArrayOfString1;
/*  896 */       this.needClientAuth = param1Boolean;
/*  897 */       checkValues(param1SSLContext, param1ArrayOfString1, param1ArrayOfString2);
/*      */     }
/*      */ 
/*      */     
/*      */     public ServerSocket createServerSocket(int param1Int) throws IOException {
/*  902 */       if (this.bindAddress != null) {
/*      */         try {
/*  904 */           InetAddress inetAddress = InetAddress.getByName(this.bindAddress);
/*  905 */           return new ConnectorBootstrap.SslServerSocket(param1Int, 0, inetAddress, this.context, this.enabledCipherSuites, this.enabledProtocols, this.needClientAuth);
/*      */         }
/*  907 */         catch (UnknownHostException unknownHostException) {
/*  908 */           return new ConnectorBootstrap.SslServerSocket(param1Int, this.context, this.enabledCipherSuites, this.enabledProtocols, this.needClientAuth);
/*      */         } 
/*      */       }
/*      */       
/*  912 */       return new ConnectorBootstrap.SslServerSocket(param1Int, this.context, this.enabledCipherSuites, this.enabledProtocols, this.needClientAuth);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static void checkValues(SSLContext param1SSLContext, String[] param1ArrayOfString1, String[] param1ArrayOfString2) throws IllegalArgumentException {
/*  926 */       SSLSocketFactory sSLSocketFactory = (param1SSLContext == null) ? (SSLSocketFactory)SSLSocketFactory.getDefault() : param1SSLContext.getSocketFactory();
/*  927 */       SSLSocket sSLSocket = null;
/*  928 */       if (param1ArrayOfString1 != null || param1ArrayOfString2 != null) {
/*      */         try {
/*  930 */           sSLSocket = (SSLSocket)sSLSocketFactory.createSocket();
/*  931 */         } catch (Exception exception) {
/*      */ 
/*      */           
/*  934 */           throw (IllegalArgumentException)(new IllegalArgumentException("Unable to check if the cipher suites and protocols to enable are supported"))
/*  935 */             .initCause(exception);
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  943 */       if (param1ArrayOfString1 != null) {
/*  944 */         sSLSocket.setEnabledCipherSuites(param1ArrayOfString1);
/*      */       }
/*  946 */       if (param1ArrayOfString2 != null) {
/*  947 */         sSLSocket.setEnabledProtocols(param1ArrayOfString2);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class SslServerSocket
/*      */     extends ServerSocket
/*      */   {
/*      */     private static SSLSocketFactory defaultSSLSocketFactory;
/*      */     
/*      */     private final String[] enabledCipherSuites;
/*      */     
/*      */     private final String[] enabledProtocols;
/*      */     private final boolean needClientAuth;
/*      */     private final SSLContext context;
/*      */     
/*      */     private SslServerSocket(int param1Int, SSLContext param1SSLContext, String[] param1ArrayOfString1, String[] param1ArrayOfString2, boolean param1Boolean) throws IOException {
/*  965 */       super(param1Int);
/*  966 */       this.enabledProtocols = param1ArrayOfString2;
/*  967 */       this.enabledCipherSuites = param1ArrayOfString1;
/*  968 */       this.needClientAuth = param1Boolean;
/*  969 */       this.context = param1SSLContext;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private SslServerSocket(int param1Int1, int param1Int2, InetAddress param1InetAddress, SSLContext param1SSLContext, String[] param1ArrayOfString1, String[] param1ArrayOfString2, boolean param1Boolean) throws IOException {
/*  979 */       super(param1Int1, param1Int2, param1InetAddress);
/*  980 */       this.enabledProtocols = param1ArrayOfString2;
/*  981 */       this.enabledCipherSuites = param1ArrayOfString1;
/*  982 */       this.needClientAuth = param1Boolean;
/*  983 */       this.context = param1SSLContext;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Socket accept() throws IOException {
/*  990 */       SSLSocketFactory sSLSocketFactory = (this.context == null) ? getDefaultSSLSocketFactory() : this.context.getSocketFactory();
/*  991 */       Socket socket = super.accept();
/*  992 */       SSLSocket sSLSocket = (SSLSocket)sSLSocketFactory.createSocket(socket, socket
/*  993 */           .getInetAddress().getHostName(), socket
/*  994 */           .getPort(), true);
/*  995 */       sSLSocket.setUseClientMode(false);
/*  996 */       if (this.enabledCipherSuites != null) {
/*  997 */         sSLSocket.setEnabledCipherSuites(this.enabledCipherSuites);
/*      */       }
/*  999 */       if (this.enabledProtocols != null) {
/* 1000 */         sSLSocket.setEnabledProtocols(this.enabledProtocols);
/*      */       }
/* 1002 */       sSLSocket.setNeedClientAuth(this.needClientAuth);
/* 1003 */       return sSLSocket;
/*      */     }
/*      */     
/*      */     private static synchronized SSLSocketFactory getDefaultSSLSocketFactory() {
/* 1007 */       if (defaultSSLSocketFactory == null) {
/* 1008 */         defaultSSLSocketFactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
/* 1009 */         return defaultSSLSocketFactory;
/*      */       } 
/* 1011 */       return defaultSSLSocketFactory;
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/jmxremote/ConnectorBootstrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */