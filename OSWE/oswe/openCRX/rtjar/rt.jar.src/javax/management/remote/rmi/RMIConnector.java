/*      */ package javax.management.remote.rmi;
/*      */ 
/*      */ import com.sun.jmx.mbeanserver.Util;
/*      */ import com.sun.jmx.remote.internal.ClientCommunicatorAdmin;
/*      */ import com.sun.jmx.remote.internal.ClientListenerInfo;
/*      */ import com.sun.jmx.remote.internal.ClientNotifForwarder;
/*      */ import com.sun.jmx.remote.internal.IIOPHelper;
/*      */ import com.sun.jmx.remote.internal.ProxyRef;
/*      */ import com.sun.jmx.remote.util.ClassLogger;
/*      */ import com.sun.jmx.remote.util.EnvHelp;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.NotSerializableException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamClass;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationHandler;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.net.MalformedURLException;
/*      */ import java.rmi.MarshalException;
/*      */ import java.rmi.MarshalledObject;
/*      */ import java.rmi.NoSuchObjectException;
/*      */ import java.rmi.Remote;
/*      */ import java.rmi.RemoteException;
/*      */ import java.rmi.ServerException;
/*      */ import java.rmi.server.RMIClientSocketFactory;
/*      */ import java.rmi.server.RemoteObject;
/*      */ import java.rmi.server.RemoteObjectInvocationHandler;
/*      */ import java.rmi.server.RemoteRef;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.stream.Collectors;
/*      */ import javax.management.Attribute;
/*      */ import javax.management.AttributeList;
/*      */ import javax.management.AttributeNotFoundException;
/*      */ import javax.management.InstanceAlreadyExistsException;
/*      */ import javax.management.InstanceNotFoundException;
/*      */ import javax.management.IntrospectionException;
/*      */ import javax.management.InvalidAttributeValueException;
/*      */ import javax.management.ListenerNotFoundException;
/*      */ import javax.management.MBeanException;
/*      */ import javax.management.MBeanInfo;
/*      */ import javax.management.MBeanRegistrationException;
/*      */ import javax.management.MBeanServerConnection;
/*      */ import javax.management.MBeanServerDelegate;
/*      */ import javax.management.NotCompliantMBeanException;
/*      */ import javax.management.Notification;
/*      */ import javax.management.NotificationBroadcasterSupport;
/*      */ import javax.management.NotificationFilter;
/*      */ import javax.management.NotificationFilterSupport;
/*      */ import javax.management.NotificationListener;
/*      */ import javax.management.ObjectInstance;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.QueryExp;
/*      */ import javax.management.ReflectionException;
/*      */ import javax.management.remote.JMXAddressable;
/*      */ import javax.management.remote.JMXConnectionNotification;
/*      */ import javax.management.remote.JMXConnector;
/*      */ import javax.management.remote.JMXServiceURL;
/*      */ import javax.management.remote.NotificationResult;
/*      */ import javax.naming.InitialContext;
/*      */ import javax.naming.NamingException;
/*      */ import javax.rmi.ssl.SslRMIClientSocketFactory;
/*      */ import javax.security.auth.Subject;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.rmi.server.UnicastRef2;
/*      */ import sun.rmi.transport.LiveRef;
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
/*      */ public class RMIConnector
/*      */   implements JMXConnector, Serializable, JMXAddressable
/*      */ {
/*      */   static {
/*      */     Class clazz1, clazz2;
/*      */     Constructor constructor;
/*      */   }
/*      */   
/*  123 */   private static final ClassLogger logger = new ClassLogger("javax.management.remote.rmi", "RMIConnector");
/*      */ 
/*      */   
/*      */   private static final long serialVersionUID = 817323035842634473L;
/*      */ 
/*      */   
/*      */   private RMIConnector(RMIServer paramRMIServer, JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap) {
/*  130 */     if (paramRMIServer == null && paramJMXServiceURL == null) throw new IllegalArgumentException("rmiServer and jmxServiceURL both null");
/*      */     
/*  132 */     initTransients();
/*      */     
/*  134 */     this.rmiServer = paramRMIServer;
/*  135 */     this.jmxServiceURL = paramJMXServiceURL;
/*  136 */     if (paramMap == null) {
/*  137 */       this.env = Collections.emptyMap();
/*      */     } else {
/*  139 */       EnvHelp.checkAttributes(paramMap);
/*  140 */       this.env = Collections.unmodifiableMap(paramMap);
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
/*      */   public RMIConnector(JMXServiceURL paramJMXServiceURL, Map<String, ?> paramMap) {
/*  186 */     this(null, paramJMXServiceURL, paramMap);
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
/*      */   public RMIConnector(RMIServer paramRMIServer, Map<String, ?> paramMap) {
/*  201 */     this(paramRMIServer, null, paramMap);
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
/*      */   public String toString() {
/*  215 */     StringBuilder stringBuilder = new StringBuilder(getClass().getName());
/*  216 */     stringBuilder.append(":");
/*  217 */     if (this.rmiServer != null) {
/*  218 */       stringBuilder.append(" rmiServer=").append(this.rmiServer.toString());
/*      */     }
/*  220 */     if (this.jmxServiceURL != null) {
/*  221 */       if (this.rmiServer != null) stringBuilder.append(","); 
/*  222 */       stringBuilder.append(" jmxServiceURL=").append(this.jmxServiceURL.toString());
/*      */     } 
/*  224 */     return stringBuilder.toString();
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
/*      */   public JMXServiceURL getAddress() {
/*  236 */     return this.jmxServiceURL;
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
/*      */   public void connect() throws IOException {
/*  249 */     connect(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized void connect(Map<String, ?> paramMap) throws IOException {
/*  259 */     boolean bool = logger.traceOn();
/*  260 */     String str = bool ? ("[" + toString() + "]") : null;
/*      */     
/*  262 */     if (this.terminated) {
/*  263 */       logger.trace("connect", str + " already closed.");
/*  264 */       throw new IOException("Connector closed");
/*      */     } 
/*  266 */     if (this.connected) {
/*  267 */       logger.trace("connect", str + " already connected.");
/*      */       
/*      */       return;
/*      */     } 
/*      */     try {
/*  272 */       if (bool) logger.trace("connect", str + " connecting...");
/*      */ 
/*      */ 
/*      */       
/*  276 */       HashMap<Object, Object> hashMap = new HashMap<>((this.env == null) ? Collections.emptyMap() : this.env);
/*      */ 
/*      */       
/*  279 */       if (paramMap != null) {
/*  280 */         EnvHelp.checkAttributes(paramMap);
/*  281 */         hashMap.putAll(paramMap);
/*      */       } 
/*      */ 
/*      */       
/*  285 */       if (bool) logger.trace("connect", str + " finding stub...");
/*      */       
/*  287 */       RMIServer rMIServer = (this.rmiServer != null) ? this.rmiServer : findRMIServer(this.jmxServiceURL, (Map)hashMap);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  292 */       String str1 = (String)hashMap.get("jmx.remote.x.check.stub");
/*  293 */       boolean bool1 = EnvHelp.computeBooleanFromString(str1);
/*      */       
/*  295 */       if (bool1) checkStub(rMIServer, rmiServerImplStubClass);
/*      */ 
/*      */       
/*  298 */       if (bool) logger.trace("connect", str + " connecting stub..."); 
/*  299 */       rMIServer = connectStub(rMIServer, (Map)hashMap);
/*  300 */       str = bool ? ("[" + toString() + "]") : null;
/*      */ 
/*      */       
/*  303 */       if (bool)
/*  304 */         logger.trace("connect", str + " getting connection..."); 
/*  305 */       Object object = hashMap.get("jmx.remote.credentials");
/*      */       
/*      */       try {
/*  308 */         this.connection = getConnection(rMIServer, object, bool1);
/*  309 */       } catch (RemoteException remoteException) {
/*  310 */         if (this.jmxServiceURL != null) {
/*  311 */           String str2 = this.jmxServiceURL.getProtocol();
/*  312 */           String str3 = this.jmxServiceURL.getURLPath();
/*      */           
/*  314 */           if ("rmi".equals(str2) && str3
/*  315 */             .startsWith("/jndi/iiop:")) {
/*  316 */             MalformedURLException malformedURLException = new MalformedURLException("Protocol is rmi but JNDI scheme is iiop: " + this.jmxServiceURL);
/*      */             
/*  318 */             malformedURLException.initCause(remoteException);
/*  319 */             throw malformedURLException;
/*      */           } 
/*      */         } 
/*  322 */         throw remoteException;
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  328 */       if (bool)
/*  329 */         logger.trace("connect", str + " getting class loader..."); 
/*  330 */       this.defaultClassLoader = EnvHelp.resolveClientClassLoader((Map)hashMap);
/*      */       
/*  332 */       hashMap.put("jmx.remote.default.class.loader", this.defaultClassLoader);
/*      */ 
/*      */       
/*  335 */       this.rmiNotifClient = new RMINotifClient(this.defaultClassLoader, (Map)hashMap);
/*      */       
/*  337 */       this.env = (Map)hashMap;
/*  338 */       long l = EnvHelp.getConnectionCheckPeriod((Map)hashMap);
/*  339 */       this.communicatorAdmin = new RMIClientCommunicatorAdmin(l);
/*      */       
/*  341 */       this.connected = true;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  346 */       this.connectionId = getConnectionId();
/*      */       
/*  348 */       JMXConnectionNotification jMXConnectionNotification = new JMXConnectionNotification("jmx.remote.connection.opened", this, this.connectionId, this.clientNotifSeqNo++, "Successful connection", null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  355 */       sendNotification(jMXConnectionNotification);
/*      */       
/*  357 */       if (bool) logger.trace("connect", str + " done..."); 
/*  358 */     } catch (IOException iOException) {
/*  359 */       if (bool)
/*  360 */         logger.trace("connect", str + " failed to connect: " + iOException); 
/*  361 */       throw iOException;
/*  362 */     } catch (RuntimeException runtimeException) {
/*  363 */       if (bool)
/*  364 */         logger.trace("connect", str + " failed to connect: " + runtimeException); 
/*  365 */       throw runtimeException;
/*  366 */     } catch (NamingException namingException) {
/*  367 */       String str1 = "Failed to retrieve RMIServer stub: " + namingException;
/*  368 */       if (bool) logger.trace("connect", str + " " + str1); 
/*  369 */       throw (IOException)EnvHelp.initCause(new IOException(str1), namingException);
/*      */     } 
/*      */   }
/*      */   
/*      */   public synchronized String getConnectionId() throws IOException {
/*  374 */     if (this.terminated || !this.connected) {
/*  375 */       if (logger.traceOn()) {
/*  376 */         logger.trace("getConnectionId", "[" + toString() + "] not connected.");
/*      */       }
/*      */       
/*  379 */       throw new IOException("Not connected");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  384 */     return this.connection.getConnectionId();
/*      */   }
/*      */ 
/*      */   
/*      */   public synchronized MBeanServerConnection getMBeanServerConnection() throws IOException {
/*  389 */     return getMBeanServerConnection(null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public synchronized MBeanServerConnection getMBeanServerConnection(Subject paramSubject) throws IOException {
/*  396 */     if (this.terminated) {
/*  397 */       if (logger.traceOn()) {
/*  398 */         logger.trace("getMBeanServerConnection", "[" + toString() + "] already closed.");
/*      */       }
/*  400 */       throw new IOException("Connection closed");
/*  401 */     }  if (!this.connected) {
/*  402 */       if (logger.traceOn()) {
/*  403 */         logger.trace("getMBeanServerConnection", "[" + toString() + "] is not connected.");
/*      */       }
/*  405 */       throw new IOException("Not connected");
/*      */     } 
/*      */     
/*  408 */     return getConnectionWithSubject(paramSubject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addConnectionNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) {
/*  415 */     if (paramNotificationListener == null)
/*  416 */       throw new NullPointerException("listener"); 
/*  417 */     this.connectionBroadcaster.addNotificationListener(paramNotificationListener, paramNotificationFilter, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeConnectionNotificationListener(NotificationListener paramNotificationListener) throws ListenerNotFoundException {
/*  424 */     if (paramNotificationListener == null)
/*  425 */       throw new NullPointerException("listener"); 
/*  426 */     this.connectionBroadcaster.removeNotificationListener(paramNotificationListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeConnectionNotificationListener(NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws ListenerNotFoundException {
/*  434 */     if (paramNotificationListener == null)
/*  435 */       throw new NullPointerException("listener"); 
/*  436 */     this.connectionBroadcaster.removeNotificationListener(paramNotificationListener, paramNotificationFilter, paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendNotification(Notification paramNotification) {
/*  441 */     this.connectionBroadcaster.sendNotification(paramNotification);
/*      */   }
/*      */   
/*      */   public synchronized void close() throws IOException {
/*  445 */     close(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized void close(boolean paramBoolean) throws IOException {
/*  451 */     boolean bool1 = logger.traceOn();
/*  452 */     boolean bool2 = logger.debugOn();
/*  453 */     String str1 = bool1 ? ("[" + toString() + "]") : null;
/*      */     
/*  455 */     if (!paramBoolean)
/*      */     {
/*      */       
/*  458 */       if (this.terminated) {
/*  459 */         if (this.closeException == null) {
/*  460 */           if (bool1) logger.trace("close", str1 + " already closed."); 
/*      */           return;
/*      */         } 
/*      */       } else {
/*  464 */         this.terminated = true;
/*      */       } 
/*      */     }
/*      */     
/*  468 */     if (this.closeException != null && bool1)
/*      */     {
/*      */       
/*  471 */       if (bool1) {
/*  472 */         logger.trace("close", str1 + " had failed: " + this.closeException);
/*  473 */         logger.trace("close", str1 + " attempting to close again.");
/*      */       } 
/*      */     }
/*      */     
/*  477 */     String str2 = null;
/*  478 */     if (this.connected) {
/*  479 */       str2 = this.connectionId;
/*      */     }
/*      */     
/*  482 */     this.closeException = null;
/*      */     
/*  484 */     if (bool1) logger.trace("close", str1 + " closing.");
/*      */     
/*  486 */     if (this.communicatorAdmin != null) {
/*  487 */       this.communicatorAdmin.terminate();
/*      */     }
/*      */     
/*  490 */     if (this.rmiNotifClient != null) {
/*      */       try {
/*  492 */         this.rmiNotifClient.terminate();
/*  493 */         if (bool1) logger.trace("close", str1 + " RMI Notification client terminated.");
/*      */       
/*  495 */       } catch (RuntimeException runtimeException) {
/*  496 */         this.closeException = runtimeException;
/*  497 */         if (bool1) logger.trace("close", str1 + " Failed to terminate RMI Notification client: " + runtimeException);
/*      */         
/*  499 */         if (bool2) logger.debug("close", runtimeException);
/*      */       
/*      */       } 
/*      */     }
/*  503 */     if (this.connection != null) {
/*      */       try {
/*  505 */         this.connection.close();
/*  506 */         if (bool1) logger.trace("close", str1 + " closed."); 
/*  507 */       } catch (NoSuchObjectException noSuchObjectException) {
/*      */       
/*  509 */       } catch (IOException iOException) {
/*  510 */         this.closeException = iOException;
/*  511 */         if (bool1) logger.trace("close", str1 + " Failed to close RMIServer: " + iOException);
/*      */         
/*  513 */         if (bool2) logger.debug("close", iOException);
/*      */       
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  519 */     this.rmbscMap.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  525 */     if (str2 != null) {
/*  526 */       JMXConnectionNotification jMXConnectionNotification = new JMXConnectionNotification("jmx.remote.connection.closed", this, str2, this.clientNotifSeqNo++, "Client has been closed", null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  533 */       sendNotification(jMXConnectionNotification);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  538 */     if (this.closeException != null) {
/*  539 */       if (bool1) logger.trace("close", str1 + " failed to close: " + this.closeException);
/*      */       
/*  541 */       if (this.closeException instanceof IOException)
/*  542 */         throw (IOException)this.closeException; 
/*  543 */       if (this.closeException instanceof RuntimeException)
/*  544 */         throw (RuntimeException)this.closeException; 
/*  545 */       IOException iOException = new IOException("Failed to close: " + this.closeException);
/*      */       
/*  547 */       throw (IOException)EnvHelp.initCause(iOException, this.closeException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Integer addListenerWithSubject(ObjectName paramObjectName, MarshalledObject<NotificationFilter> paramMarshalledObject, Subject paramSubject, boolean paramBoolean) throws InstanceNotFoundException, IOException {
/*  558 */     boolean bool = logger.debugOn();
/*  559 */     if (bool) {
/*  560 */       logger.debug("addListenerWithSubject", "(ObjectName,MarshalledObject,Subject)");
/*      */     }
/*      */     
/*  563 */     ObjectName[] arrayOfObjectName = { paramObjectName };
/*      */     
/*  565 */     MarshalledObject[] arrayOfMarshalledObject = Util.<MarshalledObject[]>cast(new MarshalledObject[] { paramMarshalledObject });
/*  566 */     Subject[] arrayOfSubject = { paramSubject };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  571 */     Integer[] arrayOfInteger = addListenersWithSubjects(arrayOfObjectName, (MarshalledObject<NotificationFilter>[])arrayOfMarshalledObject, arrayOfSubject, paramBoolean);
/*      */ 
/*      */     
/*  574 */     if (bool) logger.debug("addListenerWithSubject", "listenerID=" + arrayOfInteger[0]);
/*      */     
/*  576 */     return arrayOfInteger[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Integer[] addListenersWithSubjects(ObjectName[] paramArrayOfObjectName, MarshalledObject<NotificationFilter>[] paramArrayOfMarshalledObject, Subject[] paramArrayOfSubject, boolean paramBoolean) throws InstanceNotFoundException, IOException {
/*  586 */     boolean bool = logger.debugOn();
/*  587 */     if (bool) {
/*  588 */       logger.debug("addListenersWithSubjects", "(ObjectName[],MarshalledObject[],Subject[])");
/*      */     }
/*      */     
/*  591 */     ClassLoader classLoader = pushDefaultClassLoader();
/*  592 */     Integer[] arrayOfInteger = null;
/*      */     
/*      */     try {
/*  595 */       arrayOfInteger = this.connection.addNotificationListeners(paramArrayOfObjectName, (MarshalledObject[])paramArrayOfMarshalledObject, paramArrayOfSubject);
/*      */     
/*      */     }
/*  598 */     catch (NoSuchObjectException noSuchObjectException) {
/*      */       
/*  600 */       if (paramBoolean) {
/*  601 */         this.communicatorAdmin.gotIOException(noSuchObjectException);
/*      */         
/*  603 */         arrayOfInteger = this.connection.addNotificationListeners(paramArrayOfObjectName, (MarshalledObject[])paramArrayOfMarshalledObject, paramArrayOfSubject);
/*      */       }
/*      */       else {
/*      */         
/*  607 */         throw noSuchObjectException;
/*      */       } 
/*  609 */     } catch (IOException iOException) {
/*      */       
/*  611 */       this.communicatorAdmin.gotIOException(iOException);
/*      */     } finally {
/*  613 */       popDefaultClassLoader(classLoader);
/*      */     } 
/*      */     
/*  616 */     if (bool) logger.debug("addListenersWithSubjects", "registered " + ((arrayOfInteger == null) ? 0 : arrayOfInteger.length) + " listener(s)");
/*      */ 
/*      */     
/*  619 */     return arrayOfInteger;
/*      */   }
/*      */ 
/*      */   
/*      */   private class RemoteMBeanServerConnection
/*      */     implements MBeanServerConnection
/*      */   {
/*      */     private Subject delegationSubject;
/*      */     
/*      */     public RemoteMBeanServerConnection() {
/*  629 */       this(null);
/*      */     }
/*      */     
/*      */     public RemoteMBeanServerConnection(Subject param1Subject) {
/*  633 */       this.delegationSubject = param1Subject;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectInstance createMBean(String param1String, ObjectName param1ObjectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
/*  644 */       if (RMIConnector.logger.debugOn()) {
/*  645 */         RMIConnector.logger.debug("createMBean(String,ObjectName)", "className=" + param1String + ", name=" + param1ObjectName);
/*      */       }
/*      */ 
/*      */       
/*  649 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  651 */         return RMIConnector.this.connection.createMBean(param1String, param1ObjectName, this.delegationSubject);
/*      */       
/*      */       }
/*  654 */       catch (IOException iOException) {
/*  655 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  657 */         return RMIConnector.this.connection.createMBean(param1String, param1ObjectName, this.delegationSubject);
/*      */       }
/*      */       finally {
/*      */         
/*  661 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
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
/*      */     public ObjectInstance createMBean(String param1String, ObjectName param1ObjectName1, ObjectName param1ObjectName2) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
/*  676 */       if (RMIConnector.logger.debugOn()) {
/*  677 */         RMIConnector.logger.debug("createMBean(String,ObjectName,ObjectName)", "className=" + param1String + ", name=" + param1ObjectName1 + ", loaderName=" + param1ObjectName2 + ")");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  682 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  684 */         return RMIConnector.this.connection.createMBean(param1String, param1ObjectName1, param1ObjectName2, this.delegationSubject);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  689 */       catch (IOException iOException) {
/*  690 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  692 */         return RMIConnector.this.connection.createMBean(param1String, param1ObjectName1, param1ObjectName2, this.delegationSubject);
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/*  698 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
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
/*      */     public ObjectInstance createMBean(String param1String, ObjectName param1ObjectName, Object[] param1ArrayOfObject, String[] param1ArrayOfString) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
/*  712 */       if (RMIConnector.logger.debugOn()) {
/*  713 */         RMIConnector.logger.debug("createMBean(String,ObjectName,Object[],String[])", "className=" + param1String + ", name=" + param1ObjectName + ", signature=" + RMIConnector
/*      */             
/*  715 */             .strings(param1ArrayOfString));
/*      */       }
/*  717 */       MarshalledObject marshalledObject = new MarshalledObject(param1ArrayOfObject);
/*      */       
/*  719 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  721 */         return RMIConnector.this.connection.createMBean(param1String, param1ObjectName, marshalledObject, param1ArrayOfString, this.delegationSubject);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  726 */       catch (IOException iOException) {
/*  727 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  729 */         return RMIConnector.this.connection.createMBean(param1String, param1ObjectName, marshalledObject, param1ArrayOfString, this.delegationSubject);
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/*  735 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
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
/*      */     
/*      */     public ObjectInstance createMBean(String param1String, ObjectName param1ObjectName1, ObjectName param1ObjectName2, Object[] param1ArrayOfObject, String[] param1ArrayOfString) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
/*  751 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("createMBean(String,ObjectName,ObjectName,Object[],String[])", "className=" + param1String + ", name=" + param1ObjectName1 + ", loaderName=" + param1ObjectName2 + ", signature=" + RMIConnector
/*      */ 
/*      */             
/*  754 */             .strings(param1ArrayOfString));
/*      */       
/*  756 */       MarshalledObject marshalledObject = new MarshalledObject(param1ArrayOfObject);
/*      */       
/*  758 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  760 */         return RMIConnector.this.connection.createMBean(param1String, param1ObjectName1, param1ObjectName2, marshalledObject, param1ArrayOfString, this.delegationSubject);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  766 */       catch (IOException iOException) {
/*  767 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  769 */         return RMIConnector.this.connection.createMBean(param1String, param1ObjectName1, param1ObjectName2, marshalledObject, param1ArrayOfString, this.delegationSubject);
/*      */ 
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/*  776 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void unregisterMBean(ObjectName param1ObjectName) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
/*  784 */       if (RMIConnector.logger.debugOn()) {
/*  785 */         RMIConnector.logger.debug("unregisterMBean", "name=" + param1ObjectName);
/*      */       }
/*  787 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  789 */         RMIConnector.this.connection.unregisterMBean(param1ObjectName, this.delegationSubject);
/*  790 */       } catch (IOException iOException) {
/*  791 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  793 */         RMIConnector.this.connection.unregisterMBean(param1ObjectName, this.delegationSubject);
/*      */       } finally {
/*  795 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public ObjectInstance getObjectInstance(ObjectName param1ObjectName) throws InstanceNotFoundException, IOException {
/*  802 */       if (RMIConnector.logger.debugOn()) {
/*  803 */         RMIConnector.logger.debug("getObjectInstance", "name=" + param1ObjectName);
/*      */       }
/*  805 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  807 */         return RMIConnector.this.connection.getObjectInstance(param1ObjectName, this.delegationSubject);
/*  808 */       } catch (IOException iOException) {
/*  809 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  811 */         return RMIConnector.this.connection.getObjectInstance(param1ObjectName, this.delegationSubject);
/*      */       } finally {
/*  813 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<ObjectInstance> queryMBeans(ObjectName param1ObjectName, QueryExp param1QueryExp) throws IOException {
/*  820 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("queryMBeans", "name=" + param1ObjectName + ", query=" + param1QueryExp);
/*      */ 
/*      */       
/*  823 */       MarshalledObject<QueryExp> marshalledObject = new MarshalledObject<>(param1QueryExp);
/*      */       
/*  825 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  827 */         return RMIConnector.this.connection.queryMBeans(param1ObjectName, marshalledObject, this.delegationSubject);
/*  828 */       } catch (IOException iOException) {
/*  829 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  831 */         return RMIConnector.this.connection.queryMBeans(param1ObjectName, marshalledObject, this.delegationSubject);
/*      */       } finally {
/*  833 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<ObjectName> queryNames(ObjectName param1ObjectName, QueryExp param1QueryExp) throws IOException {
/*  840 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("queryNames", "name=" + param1ObjectName + ", query=" + param1QueryExp);
/*      */ 
/*      */       
/*  843 */       MarshalledObject<QueryExp> marshalledObject = new MarshalledObject<>(param1QueryExp);
/*      */       
/*  845 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  847 */         return RMIConnector.this.connection.queryNames(param1ObjectName, marshalledObject, this.delegationSubject);
/*  848 */       } catch (IOException iOException) {
/*  849 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  851 */         return RMIConnector.this.connection.queryNames(param1ObjectName, marshalledObject, this.delegationSubject);
/*      */       } finally {
/*  853 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isRegistered(ObjectName param1ObjectName) throws IOException {
/*  859 */       if (RMIConnector.logger.debugOn()) {
/*  860 */         RMIConnector.logger.debug("isRegistered", "name=" + param1ObjectName);
/*      */       }
/*  862 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  864 */         return RMIConnector.this.connection.isRegistered(param1ObjectName, this.delegationSubject);
/*  865 */       } catch (IOException iOException) {
/*  866 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  868 */         return RMIConnector.this.connection.isRegistered(param1ObjectName, this.delegationSubject);
/*      */       } finally {
/*  870 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public Integer getMBeanCount() throws IOException {
/*  876 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("getMBeanCount", "");
/*      */       
/*  878 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  880 */         return RMIConnector.this.connection.getMBeanCount(this.delegationSubject);
/*  881 */       } catch (IOException iOException) {
/*  882 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  884 */         return RMIConnector.this.connection.getMBeanCount(this.delegationSubject);
/*      */       } finally {
/*  886 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getAttribute(ObjectName param1ObjectName, String param1String) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
/*  897 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("getAttribute", "name=" + param1ObjectName + ", attribute=" + param1String);
/*      */ 
/*      */ 
/*      */       
/*  901 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  903 */         return RMIConnector.this.connection.getAttribute(param1ObjectName, param1String, this.delegationSubject);
/*      */       
/*      */       }
/*  906 */       catch (IOException iOException) {
/*  907 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  909 */         return RMIConnector.this.connection.getAttribute(param1ObjectName, param1String, this.delegationSubject);
/*      */       }
/*      */       finally {
/*      */         
/*  913 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeList getAttributes(ObjectName param1ObjectName, String[] param1ArrayOfString) throws InstanceNotFoundException, ReflectionException, IOException {
/*  922 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("getAttributes", "name=" + param1ObjectName + ", attributes=" + RMIConnector
/*      */             
/*  924 */             .strings(param1ArrayOfString));
/*      */       
/*  926 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  928 */         return RMIConnector.this.connection.getAttributes(param1ObjectName, param1ArrayOfString, this.delegationSubject);
/*      */ 
/*      */       
/*      */       }
/*  932 */       catch (IOException iOException) {
/*  933 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  935 */         return RMIConnector.this.connection.getAttributes(param1ObjectName, param1ArrayOfString, this.delegationSubject);
/*      */       }
/*      */       finally {
/*      */         
/*  939 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
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
/*      */     public void setAttribute(ObjectName param1ObjectName, Attribute param1Attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
/*  953 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("setAttribute", "name=" + param1ObjectName + ", attribute name=" + param1Attribute
/*      */             
/*  955 */             .getName());
/*      */       
/*  957 */       MarshalledObject<Attribute> marshalledObject = new MarshalledObject<>(param1Attribute);
/*      */       
/*  959 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  961 */         RMIConnector.this.connection.setAttribute(param1ObjectName, marshalledObject, this.delegationSubject);
/*  962 */       } catch (IOException iOException) {
/*  963 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  965 */         RMIConnector.this.connection.setAttribute(param1ObjectName, marshalledObject, this.delegationSubject);
/*      */       } finally {
/*  967 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public AttributeList setAttributes(ObjectName param1ObjectName, AttributeList param1AttributeList) throws InstanceNotFoundException, ReflectionException, IOException {
/*  977 */       if (RMIConnector.logger.debugOn()) {
/*  978 */         RMIConnector.logger.debug("setAttributes", "name=" + param1ObjectName + ", attribute names=" + 
/*      */             
/*  980 */             RMIConnector.getAttributesNames(param1AttributeList));
/*      */       }
/*      */       
/*  983 */       MarshalledObject<AttributeList> marshalledObject = new MarshalledObject<>(param1AttributeList);
/*      */       
/*  985 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/*  987 */         return RMIConnector.this.connection.setAttributes(param1ObjectName, marshalledObject, this.delegationSubject);
/*      */       
/*      */       }
/*  990 */       catch (IOException iOException) {
/*  991 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/*  993 */         return RMIConnector.this.connection.setAttributes(param1ObjectName, marshalledObject, this.delegationSubject);
/*      */       }
/*      */       finally {
/*      */         
/*  997 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
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
/*      */     public Object invoke(ObjectName param1ObjectName, String param1String, Object[] param1ArrayOfObject, String[] param1ArrayOfString) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
/* 1011 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("invoke", "name=" + param1ObjectName + ", operationName=" + param1String + ", signature=" + RMIConnector
/*      */ 
/*      */             
/* 1014 */             .strings(param1ArrayOfString));
/*      */       
/* 1016 */       MarshalledObject marshalledObject = new MarshalledObject(param1ArrayOfObject);
/*      */       
/* 1018 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/* 1020 */         return RMIConnector.this.connection.invoke(param1ObjectName, param1String, marshalledObject, param1ArrayOfString, this.delegationSubject);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1025 */       catch (IOException iOException) {
/* 1026 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1028 */         return RMIConnector.this.connection.invoke(param1ObjectName, param1String, marshalledObject, param1ArrayOfString, this.delegationSubject);
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/* 1034 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getDefaultDomain() throws IOException {
/* 1041 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("getDefaultDomain", "");
/*      */       
/* 1043 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/* 1045 */         return RMIConnector.this.connection.getDefaultDomain(this.delegationSubject);
/* 1046 */       } catch (IOException iOException) {
/* 1047 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1049 */         return RMIConnector.this.connection.getDefaultDomain(this.delegationSubject);
/*      */       } finally {
/* 1051 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */     
/*      */     public String[] getDomains() throws IOException {
/* 1056 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("getDomains", "");
/*      */       
/* 1058 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/* 1060 */         return RMIConnector.this.connection.getDomains(this.delegationSubject);
/* 1061 */       } catch (IOException iOException) {
/* 1062 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1064 */         return RMIConnector.this.connection.getDomains(this.delegationSubject);
/*      */       } finally {
/* 1066 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public MBeanInfo getMBeanInfo(ObjectName param1ObjectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
/* 1076 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("getMBeanInfo", "name=" + param1ObjectName); 
/* 1077 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/* 1079 */         return RMIConnector.this.connection.getMBeanInfo(param1ObjectName, this.delegationSubject);
/* 1080 */       } catch (IOException iOException) {
/* 1081 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1083 */         return RMIConnector.this.connection.getMBeanInfo(param1ObjectName, this.delegationSubject);
/*      */       } finally {
/* 1085 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isInstanceOf(ObjectName param1ObjectName, String param1String) throws InstanceNotFoundException, IOException {
/* 1094 */       if (RMIConnector.logger.debugOn()) {
/* 1095 */         RMIConnector.logger.debug("isInstanceOf", "name=" + param1ObjectName + ", className=" + param1String);
/*      */       }
/*      */       
/* 1098 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/* 1100 */         return RMIConnector.this.connection.isInstanceOf(param1ObjectName, param1String, this.delegationSubject);
/*      */       
/*      */       }
/* 1103 */       catch (IOException iOException) {
/* 1104 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1106 */         return RMIConnector.this.connection.isInstanceOf(param1ObjectName, param1String, this.delegationSubject);
/*      */       }
/*      */       finally {
/*      */         
/* 1110 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void addNotificationListener(ObjectName param1ObjectName1, ObjectName param1ObjectName2, NotificationFilter param1NotificationFilter, Object param1Object) throws InstanceNotFoundException, IOException {
/* 1121 */       if (RMIConnector.logger.debugOn()) {
/* 1122 */         RMIConnector.logger.debug("addNotificationListener(ObjectName,ObjectName,NotificationFilter,Object)", "name=" + param1ObjectName1 + ", listener=" + param1ObjectName2 + ", filter=" + param1NotificationFilter + ", handback=" + param1Object);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1127 */       MarshalledObject<NotificationFilter> marshalledObject = new MarshalledObject<>(param1NotificationFilter);
/*      */       
/* 1129 */       MarshalledObject marshalledObject1 = new MarshalledObject(param1Object);
/*      */       
/* 1131 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/* 1133 */         RMIConnector.this.connection.addNotificationListener(param1ObjectName1, param1ObjectName2, marshalledObject, marshalledObject1, this.delegationSubject);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1138 */       catch (IOException iOException) {
/* 1139 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1141 */         RMIConnector.this.connection.addNotificationListener(param1ObjectName1, param1ObjectName2, marshalledObject, marshalledObject1, this.delegationSubject);
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/* 1147 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeNotificationListener(ObjectName param1ObjectName1, ObjectName param1ObjectName2) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
/* 1157 */       if (RMIConnector.logger.debugOn()) RMIConnector.logger.debug("removeNotificationListener(ObjectName,ObjectName)", "name=" + param1ObjectName1 + ", listener=" + param1ObjectName2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1162 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/* 1164 */         RMIConnector.this.connection.removeNotificationListener(param1ObjectName1, param1ObjectName2, this.delegationSubject);
/*      */       
/*      */       }
/* 1167 */       catch (IOException iOException) {
/* 1168 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1170 */         RMIConnector.this.connection.removeNotificationListener(param1ObjectName1, param1ObjectName2, this.delegationSubject);
/*      */       }
/*      */       finally {
/*      */         
/* 1174 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeNotificationListener(ObjectName param1ObjectName1, ObjectName param1ObjectName2, NotificationFilter param1NotificationFilter, Object param1Object) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
/* 1185 */       if (RMIConnector.logger.debugOn()) {
/* 1186 */         RMIConnector.logger.debug("removeNotificationListener(ObjectName,ObjectName,NotificationFilter,Object)", "name=" + param1ObjectName1 + ", listener=" + param1ObjectName2 + ", filter=" + param1NotificationFilter + ", handback=" + param1Object);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1193 */       MarshalledObject<NotificationFilter> marshalledObject = new MarshalledObject<>(param1NotificationFilter);
/*      */       
/* 1195 */       MarshalledObject marshalledObject1 = new MarshalledObject(param1Object);
/*      */       
/* 1197 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/* 1199 */         RMIConnector.this.connection.removeNotificationListener(param1ObjectName1, param1ObjectName2, marshalledObject, marshalledObject1, this.delegationSubject);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1204 */       catch (IOException iOException) {
/* 1205 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1207 */         RMIConnector.this.connection.removeNotificationListener(param1ObjectName1, param1ObjectName2, marshalledObject, marshalledObject1, this.delegationSubject);
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/* 1213 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
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
/*      */     public void addNotificationListener(ObjectName param1ObjectName, NotificationListener param1NotificationListener, NotificationFilter param1NotificationFilter, Object param1Object) throws InstanceNotFoundException, IOException {
/* 1226 */       boolean bool = RMIConnector.logger.debugOn();
/*      */       
/* 1228 */       if (bool) {
/* 1229 */         RMIConnector.logger.debug("addNotificationListener(ObjectName,NotificationListener,NotificationFilter,Object)", "name=" + param1ObjectName + ", listener=" + param1NotificationListener + ", filter=" + param1NotificationFilter + ", handback=" + param1Object);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1238 */       Integer integer = RMIConnector.this.addListenerWithSubject(param1ObjectName, new MarshalledObject<>(param1NotificationFilter), this.delegationSubject, true);
/*      */ 
/*      */       
/* 1241 */       RMIConnector.this.rmiNotifClient.addNotificationListener(integer, param1ObjectName, param1NotificationListener, param1NotificationFilter, param1Object, this.delegationSubject);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeNotificationListener(ObjectName param1ObjectName, NotificationListener param1NotificationListener) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
/* 1252 */       boolean bool = RMIConnector.logger.debugOn();
/*      */       
/* 1254 */       if (bool) RMIConnector.logger.debug("removeNotificationListener(ObjectName,NotificationListener)", "name=" + param1ObjectName + ", listener=" + param1NotificationListener);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1260 */       Integer[] arrayOfInteger = RMIConnector.this.rmiNotifClient.removeNotificationListener(param1ObjectName, param1NotificationListener);
/*      */       
/* 1262 */       if (bool) RMIConnector.logger.debug("removeNotificationListener", "listenerIDs=" + RMIConnector
/* 1263 */             .objects((Object[])arrayOfInteger));
/*      */       
/* 1265 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       
/*      */       try {
/* 1268 */         RMIConnector.this.connection.removeNotificationListeners(param1ObjectName, arrayOfInteger, this.delegationSubject);
/*      */       
/*      */       }
/* 1271 */       catch (IOException iOException) {
/* 1272 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1274 */         RMIConnector.this.connection.removeNotificationListeners(param1ObjectName, arrayOfInteger, this.delegationSubject);
/*      */       }
/*      */       finally {
/*      */         
/* 1278 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void removeNotificationListener(ObjectName param1ObjectName, NotificationListener param1NotificationListener, NotificationFilter param1NotificationFilter, Object param1Object) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
/* 1290 */       boolean bool = RMIConnector.logger.debugOn();
/*      */       
/* 1292 */       if (bool) {
/* 1293 */         RMIConnector.logger.debug("removeNotificationListener(ObjectName,NotificationListener,NotificationFilter,Object)", "name=" + param1ObjectName + ", listener=" + param1NotificationListener + ", filter=" + param1NotificationFilter + ", handback=" + param1Object);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1302 */       Integer integer = RMIConnector.this.rmiNotifClient.removeNotificationListener(param1ObjectName, param1NotificationListener, param1NotificationFilter, param1Object);
/*      */ 
/*      */       
/* 1305 */       if (bool) RMIConnector.logger.debug("removeNotificationListener", "listenerID=" + integer);
/*      */ 
/*      */       
/* 1308 */       ClassLoader classLoader = RMIConnector.this.pushDefaultClassLoader();
/*      */       try {
/* 1310 */         RMIConnector.this.connection.removeNotificationListeners(param1ObjectName, new Integer[] { integer }, this.delegationSubject);
/*      */       
/*      */       }
/* 1313 */       catch (IOException iOException) {
/* 1314 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1316 */         RMIConnector.this.connection.removeNotificationListeners(param1ObjectName, new Integer[] { integer }, this.delegationSubject);
/*      */       }
/*      */       finally {
/*      */         
/* 1320 */         RMIConnector.this.popDefaultClassLoader(classLoader);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class RMINotifClient
/*      */     extends ClientNotifForwarder
/*      */   {
/*      */     public RMINotifClient(ClassLoader param1ClassLoader, Map<String, ?> param1Map) {
/* 1329 */       super(param1ClassLoader, param1Map);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected NotificationResult fetchNotifs(long param1Long1, int param1Int, long param1Long2) throws IOException, ClassNotFoundException {
/* 1337 */       boolean bool = false;
/*      */       
/*      */       while (true) {
/*      */         try {
/* 1341 */           return RMIConnector.this.connection.fetchNotifications(param1Long1, param1Int, param1Long2);
/*      */         
/*      */         }
/* 1344 */         catch (IOException iOException) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1349 */           rethrowDeserializationException(iOException);
/*      */           
/*      */           try {
/* 1352 */             RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */           }
/* 1354 */           catch (IOException iOException1) {
/* 1355 */             boolean bool1 = false;
/*      */             
/* 1357 */             synchronized (this) {
/* 1358 */               if (RMIConnector.this.terminated)
/*      */               {
/* 1360 */                 throw iOException; } 
/* 1361 */               if (bool) {
/* 1362 */                 bool1 = true;
/*      */               }
/*      */             } 
/*      */             
/* 1366 */             if (bool1) {
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
/* 1386 */               JMXConnectionNotification jMXConnectionNotification = new JMXConnectionNotification("jmx.remote.connection.failed", this, RMIConnector.this.connectionId, RMIConnector.this.clientNotifSeqNo++, "Failed to communicate with the server: " + iOException.toString(), iOException);
/*      */ 
/*      */               
/* 1389 */               RMIConnector.this.sendNotification(jMXConnectionNotification);
/*      */               
/*      */               try {
/* 1392 */                 RMIConnector.this.close(true);
/* 1393 */               } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */               
/* 1397 */               throw iOException;
/*      */             } 
/*      */ 
/*      */             
/* 1401 */             bool = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void rethrowDeserializationException(IOException param1IOException) throws ClassNotFoundException, IOException {
/* 1411 */       if (param1IOException instanceof java.rmi.UnmarshalException) {
/* 1412 */         throw param1IOException;
/*      */       }
/* 1414 */       if (param1IOException instanceof MarshalException) {
/*      */ 
/*      */         
/* 1417 */         MarshalException marshalException = (MarshalException)param1IOException;
/* 1418 */         if (marshalException.detail instanceof NotSerializableException) {
/* 1419 */           throw (NotSerializableException)marshalException.detail;
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Integer addListenerForMBeanRemovedNotif() throws IOException, InstanceNotFoundException {
/*      */       Integer[] arrayOfInteger;
/* 1428 */       NotificationFilterSupport notificationFilterSupport = new NotificationFilterSupport();
/*      */       
/* 1430 */       notificationFilterSupport.enableType("JMX.mbean.unregistered");
/*      */       
/* 1432 */       MarshalledObject<NotificationFilterSupport> marshalledObject = new MarshalledObject<>(notificationFilterSupport);
/*      */ 
/*      */ 
/*      */       
/* 1436 */       ObjectName[] arrayOfObjectName = { MBeanServerDelegate.DELEGATE_NAME };
/*      */ 
/*      */       
/* 1439 */       MarshalledObject[] arrayOfMarshalledObject = Util.<MarshalledObject[]>cast(new MarshalledObject[] { marshalledObject });
/* 1440 */       Subject[] arrayOfSubject = { null };
/*      */       
/*      */       try {
/* 1443 */         arrayOfInteger = RMIConnector.this.connection.addNotificationListeners(arrayOfObjectName, arrayOfMarshalledObject, arrayOfSubject);
/*      */ 
/*      */       
/*      */       }
/* 1447 */       catch (IOException iOException) {
/* 1448 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */ 
/*      */         
/* 1451 */         arrayOfInteger = RMIConnector.this.connection.addNotificationListeners(arrayOfObjectName, arrayOfMarshalledObject, arrayOfSubject);
/*      */       } 
/*      */ 
/*      */       
/* 1455 */       return arrayOfInteger[0];
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected void removeListenerForMBeanRemovedNotif(Integer param1Integer) throws IOException, InstanceNotFoundException, ListenerNotFoundException {
/*      */       try {
/* 1462 */         RMIConnector.this.connection.removeNotificationListeners(MBeanServerDelegate.DELEGATE_NAME, new Integer[] { param1Integer }, null);
/*      */ 
/*      */       
/*      */       }
/* 1466 */       catch (IOException iOException) {
/* 1467 */         RMIConnector.this.communicatorAdmin.gotIOException(iOException);
/*      */         
/* 1469 */         RMIConnector.this.connection.removeNotificationListeners(MBeanServerDelegate.DELEGATE_NAME, new Integer[] { param1Integer }, null);
/*      */       } 
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
/*      */ 
/*      */     
/*      */     protected void lostNotifs(String param1String, long param1Long) {
/* 1486 */       JMXConnectionNotification jMXConnectionNotification = new JMXConnectionNotification("jmx.remote.connection.notifs.lost", RMIConnector.this, RMIConnector.this.connectionId, RMIConnector.this.clientNotifCounter++, param1String, Long.valueOf(param1Long));
/* 1487 */       RMIConnector.this.sendNotification(jMXConnectionNotification);
/*      */     }
/*      */   }
/*      */   
/*      */   private class RMIClientCommunicatorAdmin extends ClientCommunicatorAdmin {
/*      */     public RMIClientCommunicatorAdmin(long param1Long) {
/* 1493 */       super(param1Long);
/*      */     }
/*      */ 
/*      */     
/*      */     public void gotIOException(IOException param1IOException) throws IOException {
/* 1498 */       if (param1IOException instanceof NoSuchObjectException) {
/*      */         
/* 1500 */         super.gotIOException(param1IOException);
/*      */ 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*      */       try {
/* 1507 */         RMIConnector.this.connection.getDefaultDomain(null);
/* 1508 */       } catch (IOException iOException) {
/* 1509 */         boolean bool = false;
/*      */         
/* 1511 */         synchronized (this) {
/* 1512 */           if (!RMIConnector.this.terminated) {
/* 1513 */             RMIConnector.this.terminated = true;
/*      */             
/* 1515 */             bool = true;
/*      */           } 
/*      */         } 
/*      */         
/* 1519 */         if (bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1528 */           JMXConnectionNotification jMXConnectionNotification = new JMXConnectionNotification("jmx.remote.connection.failed", this, RMIConnector.this.connectionId, RMIConnector.this.clientNotifSeqNo++, "Failed to communicate with the server: " + param1IOException.toString(), param1IOException);
/*      */ 
/*      */           
/* 1531 */           RMIConnector.this.sendNotification(jMXConnectionNotification);
/*      */           
/*      */           try {
/* 1534 */             RMIConnector.this.close(true);
/* 1535 */           } catch (Exception exception) {}
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1543 */       if (param1IOException instanceof ServerException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1553 */         Throwable throwable = ((ServerException)param1IOException).detail;
/*      */         
/* 1555 */         if (throwable instanceof IOException)
/* 1556 */           throw (IOException)throwable; 
/* 1557 */         if (throwable instanceof RuntimeException) {
/* 1558 */           throw (RuntimeException)throwable;
/*      */         }
/*      */       } 
/*      */       
/* 1562 */       throw param1IOException;
/*      */     }
/*      */     
/*      */     public void reconnectNotificationListeners(ClientListenerInfo[] param1ArrayOfClientListenerInfo) throws IOException {
/* 1566 */       int i = param1ArrayOfClientListenerInfo.length;
/*      */ 
/*      */       
/* 1569 */       ClientListenerInfo[] arrayOfClientListenerInfo = new ClientListenerInfo[i];
/*      */       
/* 1571 */       Subject[] arrayOfSubject = new Subject[i];
/* 1572 */       ObjectName[] arrayOfObjectName = new ObjectName[i];
/* 1573 */       NotificationListener[] arrayOfNotificationListener = new NotificationListener[i];
/* 1574 */       NotificationFilter[] arrayOfNotificationFilter = new NotificationFilter[i];
/*      */       
/* 1576 */       MarshalledObject[] arrayOfMarshalledObject = Util.<MarshalledObject[]>cast(new MarshalledObject[i]);
/* 1577 */       Object[] arrayOfObject = new Object[i];
/*      */       byte b;
/* 1579 */       for (b = 0; b < i; b++) {
/* 1580 */         arrayOfSubject[b] = param1ArrayOfClientListenerInfo[b].getDelegationSubject();
/* 1581 */         arrayOfObjectName[b] = param1ArrayOfClientListenerInfo[b].getObjectName();
/* 1582 */         arrayOfNotificationListener[b] = param1ArrayOfClientListenerInfo[b].getListener();
/* 1583 */         arrayOfNotificationFilter[b] = param1ArrayOfClientListenerInfo[b].getNotificationFilter();
/* 1584 */         arrayOfMarshalledObject[b] = new MarshalledObject<>(arrayOfNotificationFilter[b]);
/* 1585 */         arrayOfObject[b] = param1ArrayOfClientListenerInfo[b].getHandback();
/*      */       } 
/*      */       
/*      */       try {
/* 1589 */         Integer[] arrayOfInteger = RMIConnector.this.addListenersWithSubjects(arrayOfObjectName, (MarshalledObject<NotificationFilter>[])arrayOfMarshalledObject, arrayOfSubject, false);
/*      */         
/* 1591 */         for (b = 0; b < i; b++) {
/* 1592 */           arrayOfClientListenerInfo[b] = new ClientListenerInfo(arrayOfInteger[b], arrayOfObjectName[b], arrayOfNotificationListener[b], arrayOfNotificationFilter[b], arrayOfObject[b], arrayOfSubject[b]);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1600 */         RMIConnector.this.rmiNotifClient.postReconnection(arrayOfClientListenerInfo);
/*      */         
/*      */         return;
/* 1603 */       } catch (InstanceNotFoundException instanceNotFoundException) {
/*      */ 
/*      */ 
/*      */         
/* 1607 */         byte b1 = 0;
/* 1608 */         for (b = 0; b < i; b++) {
/*      */           try {
/* 1610 */             Integer integer = RMIConnector.this.addListenerWithSubject(arrayOfObjectName[b], new MarshalledObject<>(arrayOfNotificationFilter[b]), arrayOfSubject[b], false);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1615 */             arrayOfClientListenerInfo[b1++] = new ClientListenerInfo(integer, arrayOfObjectName[b], arrayOfNotificationListener[b], arrayOfNotificationFilter[b], arrayOfObject[b], arrayOfSubject[b]);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 1621 */           catch (InstanceNotFoundException instanceNotFoundException1) {
/* 1622 */             RMIConnector.logger.warning("reconnectNotificationListeners", "Can't reconnect listener for " + arrayOfObjectName[b]);
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 1628 */         if (b1 != i) {
/* 1629 */           ClientListenerInfo[] arrayOfClientListenerInfo1 = arrayOfClientListenerInfo;
/* 1630 */           arrayOfClientListenerInfo = new ClientListenerInfo[b1];
/* 1631 */           System.arraycopy(arrayOfClientListenerInfo1, 0, arrayOfClientListenerInfo, 0, b1);
/*      */         } 
/*      */         
/* 1634 */         RMIConnector.this.rmiNotifClient.postReconnection(arrayOfClientListenerInfo);
/*      */         return;
/*      */       } 
/*      */     } protected void checkConnection() throws IOException {
/* 1638 */       if (RMIConnector.logger.debugOn()) {
/* 1639 */         RMIConnector.logger.debug("RMIClientCommunicatorAdmin-checkConnection", "Calling the method getDefaultDomain.");
/*      */       }
/*      */       
/* 1642 */       RMIConnector.this.connection.getDefaultDomain(null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void doStart() throws IOException {
/*      */       try {
/* 1650 */         rMIServer = (RMIConnector.this.rmiServer != null) ? RMIConnector.this.rmiServer : RMIConnector.this.findRMIServer(RMIConnector.this.jmxServiceURL, RMIConnector.this.env);
/* 1651 */       } catch (NamingException namingException) {
/* 1652 */         throw new IOException("Failed to get a RMI stub: " + namingException);
/*      */       } 
/*      */ 
/*      */       
/* 1656 */       RMIServer rMIServer = RMIConnector.connectStub(rMIServer, RMIConnector.this.env);
/*      */ 
/*      */       
/* 1659 */       Object object = RMIConnector.this.env.get("jmx.remote.credentials");
/* 1660 */       RMIConnector.this.connection = rMIServer.newClient(object);
/*      */ 
/*      */       
/* 1663 */       ClientListenerInfo[] arrayOfClientListenerInfo = RMIConnector.this.rmiNotifClient.preReconnection();
/*      */       
/* 1665 */       reconnectNotificationListeners(arrayOfClientListenerInfo);
/*      */       
/* 1667 */       RMIConnector.this.connectionId = RMIConnector.this.getConnectionId();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1673 */       JMXConnectionNotification jMXConnectionNotification = new JMXConnectionNotification("jmx.remote.connection.opened", this, RMIConnector.this.connectionId, RMIConnector.this.clientNotifSeqNo++, "Reconnected to server", null);
/*      */ 
/*      */       
/* 1676 */       RMIConnector.this.sendNotification(jMXConnectionNotification);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void doStop() {
/*      */       try {
/* 1682 */         RMIConnector.this.close();
/* 1683 */       } catch (IOException iOException) {
/* 1684 */         RMIConnector.logger.warning("RMIClientCommunicatorAdmin-doStop", "Failed to call the method close():" + iOException);
/*      */         
/* 1686 */         RMIConnector.logger.debug("RMIClientCommunicatorAdmin-doStop", iOException);
/*      */       } 
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
/*      */   static RMIServer connectStub(RMIServer paramRMIServer, Map<String, ?> paramMap) throws IOException {
/* 1740 */     if (IIOPHelper.isStub(paramRMIServer)) {
/*      */       try {
/* 1742 */         IIOPHelper.getOrb(paramRMIServer);
/* 1743 */       } catch (UnsupportedOperationException unsupportedOperationException) {
/*      */         
/* 1745 */         IIOPHelper.connect(paramRMIServer, resolveOrb(paramMap));
/*      */       } 
/*      */     }
/* 1748 */     return paramRMIServer;
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
/*      */ 
/*      */ 
/*      */   
/*      */   static Object resolveOrb(Map<String, ?> paramMap) throws IOException {
/* 1774 */     if (paramMap != null) {
/* 1775 */       Object object1 = paramMap.get("java.naming.corba.orb");
/* 1776 */       if (object1 != null && !IIOPHelper.isOrb(object1)) {
/* 1777 */         throw new IllegalArgumentException("java.naming.corba.orb must be an instance of org.omg.CORBA.ORB.");
/*      */       }
/* 1779 */       if (object1 != null) return object1;
/*      */     
/*      */     } 
/* 1782 */     T t = (orb == null) ? null : (T)orb.get();
/* 1783 */     if (t != null) return t;
/*      */ 
/*      */     
/* 1786 */     Object object = IIOPHelper.createOrb((String[])null, (Properties)null);
/* 1787 */     orb = new WeakReference(object);
/* 1788 */     return object;
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException {
/* 1804 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 1806 */     if (this.rmiServer == null && this.jmxServiceURL == null) throw new InvalidObjectException("rmiServer and jmxServiceURL both null");
/*      */ 
/*      */     
/* 1809 */     initTransients();
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
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream) throws IOException {
/* 1845 */     if (this.rmiServer == null && this.jmxServiceURL == null) throw new InvalidObjectException("rmiServer and jmxServiceURL both null.");
/*      */     
/* 1847 */     connectStub(this.rmiServer, this.env);
/* 1848 */     paramObjectOutputStream.defaultWriteObject();
/*      */   }
/*      */ 
/*      */   
/*      */   private void initTransients() {
/* 1853 */     this.rmbscMap = new WeakHashMap<>();
/* 1854 */     this.connected = false;
/* 1855 */     this.terminated = false;
/*      */     
/* 1857 */     this.connectionBroadcaster = new NotificationBroadcasterSupport();
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
/*      */   private static void checkStub(Remote paramRemote, Class<?> paramClass) {
/* 1869 */     if (paramRemote.getClass() != paramClass) {
/* 1870 */       if (!Proxy.isProxyClass(paramRemote.getClass())) {
/* 1871 */         throw new SecurityException("Expecting a " + paramClass
/* 1872 */             .getName() + " stub!");
/*      */       }
/* 1874 */       InvocationHandler invocationHandler = Proxy.getInvocationHandler(paramRemote);
/* 1875 */       if (invocationHandler.getClass() != RemoteObjectInvocationHandler.class) {
/* 1876 */         throw new SecurityException("Expecting a dynamic proxy instance with a " + RemoteObjectInvocationHandler.class
/*      */             
/* 1878 */             .getName() + " invocation handler!");
/*      */       }
/*      */       
/* 1881 */       paramRemote = (Remote)invocationHandler;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1888 */     RemoteRef remoteRef = ((RemoteObject)paramRemote).getRef();
/* 1889 */     if (remoteRef.getClass() != UnicastRef2.class) {
/* 1890 */       throw new SecurityException("Expecting a " + UnicastRef2.class
/* 1891 */           .getName() + " remote reference in stub!");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1897 */     LiveRef liveRef = ((UnicastRef2)remoteRef).getLiveRef();
/* 1898 */     RMIClientSocketFactory rMIClientSocketFactory = liveRef.getClientSocketFactory();
/* 1899 */     if (rMIClientSocketFactory == null || rMIClientSocketFactory.getClass() != SslRMIClientSocketFactory.class) {
/* 1900 */       throw new SecurityException("Expecting a " + SslRMIClientSocketFactory.class
/* 1901 */           .getName() + " RMI client socket factory in stub!");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private RMIServer findRMIServer(JMXServiceURL paramJMXServiceURL, Map<String, Object> paramMap) throws NamingException, IOException {
/* 1912 */     boolean bool = RMIConnectorServer.isIiopURL(paramJMXServiceURL, true);
/* 1913 */     if (bool)
/*      */     {
/* 1915 */       paramMap.put("java.naming.corba.orb", resolveOrb(paramMap));
/*      */     }
/*      */     
/* 1918 */     String str1 = paramJMXServiceURL.getURLPath();
/* 1919 */     int i = str1.indexOf(';');
/* 1920 */     if (i < 0) i = str1.length(); 
/* 1921 */     if (str1.startsWith("/jndi/"))
/* 1922 */       return findRMIServerJNDI(str1.substring(6, i), paramMap, bool); 
/* 1923 */     if (str1.startsWith("/stub/"))
/* 1924 */       return findRMIServerJRMP(str1.substring(6, i), paramMap, bool); 
/* 1925 */     if (str1.startsWith("/ior/")) {
/* 1926 */       if (!IIOPHelper.isAvailable())
/* 1927 */         throw new IOException("iiop protocol not available"); 
/* 1928 */       return findRMIServerIIOP(str1.substring(5, i), paramMap, bool);
/*      */     } 
/* 1930 */     String str2 = "URL path must begin with /jndi/ or /stub/ or /ior/: " + str1;
/*      */     
/* 1932 */     throw new MalformedURLException(str2);
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
/*      */   private RMIServer findRMIServerJNDI(String paramString, Map<String, ?> paramMap, boolean paramBoolean) throws NamingException {
/* 1953 */     InitialContext initialContext = new InitialContext(EnvHelp.mapToHashtable(paramMap));
/*      */     
/* 1955 */     Object object = initialContext.lookup(paramString);
/* 1956 */     initialContext.close();
/*      */     
/* 1958 */     if (paramBoolean) {
/* 1959 */       return narrowIIOPServer(object);
/*      */     }
/* 1961 */     return narrowJRMPServer(object);
/*      */   }
/*      */ 
/*      */   
/*      */   private static RMIServer narrowJRMPServer(Object paramObject) {
/* 1966 */     return (RMIServer)paramObject;
/*      */   }
/*      */   
/*      */   private static RMIServer narrowIIOPServer(Object paramObject) {
/*      */     try {
/* 1971 */       return (RMIServer)IIOPHelper.narrow(paramObject, RMIServer.class);
/* 1972 */     } catch (ClassCastException classCastException) {
/* 1973 */       if (logger.traceOn()) {
/* 1974 */         logger.trace("narrowIIOPServer", "Failed to narrow objref=" + paramObject + ": " + classCastException);
/*      */       }
/* 1976 */       if (logger.debugOn()) logger.debug("narrowIIOPServer", classCastException); 
/* 1977 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private RMIServer findRMIServerIIOP(String paramString, Map<String, ?> paramMap, boolean paramBoolean) {
/* 1983 */     Object object1 = paramMap.get("java.naming.corba.orb");
/* 1984 */     Object object2 = IIOPHelper.stringToObject(object1, paramString);
/* 1985 */     return (RMIServer)IIOPHelper.narrow(object2, RMIServer.class);
/*      */   }
/*      */ 
/*      */   
/*      */   private RMIServer findRMIServerJRMP(String paramString, Map<String, ?> paramMap, boolean paramBoolean) throws IOException {
/*      */     byte[] arrayOfByte;
/*      */     Object object;
/*      */     try {
/* 1993 */       arrayOfByte = base64ToByteArray(paramString);
/* 1994 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 1995 */       throw new MalformedURLException("Bad BASE64 encoding: " + illegalArgumentException
/* 1996 */           .getMessage());
/*      */     } 
/* 1998 */     ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/*      */     
/* 2000 */     ClassLoader classLoader = EnvHelp.resolveClientClassLoader(paramMap);
/* 2001 */     ObjectInputStream objectInputStream = (classLoader == null) ? new ObjectInputStream(byteArrayInputStream) : new ObjectInputStreamWithLoader(byteArrayInputStream, classLoader);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2007 */       object = objectInputStream.readObject();
/* 2008 */     } catch (ClassNotFoundException classNotFoundException) {
/* 2009 */       throw new MalformedURLException("Class not found: " + classNotFoundException);
/*      */     } 
/* 2011 */     return (RMIServer)object;
/*      */   }
/*      */   
/*      */   private static final class ObjectInputStreamWithLoader extends ObjectInputStream {
/*      */     private final ClassLoader loader;
/*      */     
/*      */     ObjectInputStreamWithLoader(InputStream param1InputStream, ClassLoader param1ClassLoader) throws IOException {
/* 2018 */       super(param1InputStream);
/* 2019 */       this.loader = param1ClassLoader;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Class<?> resolveClass(ObjectStreamClass param1ObjectStreamClass) throws IOException, ClassNotFoundException {
/* 2025 */       String str = param1ObjectStreamClass.getName();
/* 2026 */       ReflectUtil.checkPackageAccess(str);
/* 2027 */       return Class.forName(str, false, this.loader);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private MBeanServerConnection getConnectionWithSubject(Subject paramSubject) {
/* 2034 */     MBeanServerConnection mBeanServerConnection = null;
/*      */     
/* 2036 */     if (paramSubject == null) {
/* 2037 */       if (this.nullSubjectConnRef == null || (
/* 2038 */         mBeanServerConnection = this.nullSubjectConnRef.get()) == null) {
/* 2039 */         mBeanServerConnection = new RemoteMBeanServerConnection(null);
/* 2040 */         this.nullSubjectConnRef = new WeakReference<>(mBeanServerConnection);
/*      */       } 
/*      */     } else {
/* 2043 */       WeakReference<MBeanServerConnection> weakReference = this.rmbscMap.get(paramSubject);
/* 2044 */       if (weakReference == null || (mBeanServerConnection = weakReference.get()) == null) {
/* 2045 */         mBeanServerConnection = new RemoteMBeanServerConnection(paramSubject);
/* 2046 */         this.rmbscMap.put(paramSubject, new WeakReference<>(mBeanServerConnection));
/*      */       } 
/*      */     } 
/* 2049 */     return mBeanServerConnection;
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
/* 2117 */   private static final String rmiServerImplStubClassName = RMIServer.class
/* 2118 */     .getName() + "Impl_Stub";
/*      */   private static final Class<?> rmiServerImplStubClass;
/* 2120 */   private static final String rmiConnectionImplStubClassName = RMIConnection.class
/* 2121 */     .getName() + "Impl_Stub";
/*      */   private static final Class<?> rmiConnectionImplStubClass;
/*      */   private static final String pRefClassName = "com.sun.jmx.remote.internal.PRef";
/*      */   private static final Constructor<?> proxyRefConstructor;
/*      */   private static final String iiopConnectionStubClassName = "org.omg.stub.javax.management.remote.rmi._RMIConnection_Stub";
/*      */   private static final String proxyStubClassName = "com.sun.jmx.remote.protocol.iiop.ProxyStub";
/*      */   private static final String ProxyInputStreamClassName = "com.sun.jmx.remote.protocol.iiop.ProxyInputStream";
/*      */   private static final String pInputStreamClassName = "com.sun.jmx.remote.protocol.iiop.PInputStream";
/*      */   private static final Class<?> proxyStubClass;
/*      */   private static final byte[] base64ToInt;
/*      */   private final RMIServer rmiServer;
/*      */   private final JMXServiceURL jmxServiceURL;
/*      */   private transient Map<String, Object> env;
/*      */   private transient ClassLoader defaultClassLoader;
/*      */   private transient RMIConnection connection;
/*      */   private transient String connectionId;
/*      */   
/*      */   static
/*      */   {
/* 2140 */     final byte[] pRefByteCode = NoCallStackClassLoader.stringToBytes("Êþº¾\000\000\000.\000\027\n\000\005\000\r\t\000\004\000\016\013\000\017\000\020\007\000\021\007\000\022\001\000\006<init>\001\000\036(Ljava/rmi/server/RemoteRef;)V\001\000\004Code\001\000\006invoke\001\000S(Ljava/rmi/Remote;Ljava/lang/reflect/Method;[Ljava/lang/Object;J)Ljava/lang/Object;\001\000\nExceptions\007\000\023\f\000\006\000\007\f\000\024\000\025\007\000\026\f\000\t\000\n\001\000 com/sun/jmx/remote/internal/PRef\001\000$com/sun/jmx/remote/internal/ProxyRef\001\000\023java/lang/Exception\001\000\003ref\001\000\033Ljava/rmi/server/RemoteRef;\001\000\031java/rmi/server/RemoteRef\000!\000\004\000\005\000\000\000\000\000\002\000\001\000\006\000\007\000\001\000\b\000\000\000\022\000\002\000\002\000\000\000\006*+·\000\001±\000\000\000\000\000\001\000\t\000\n\000\002\000\b\000\000\000\033\000\006\000\006\000\000\000\017*´\000\002+,-\026\004¹\000\003\006\000°\000\000\000\000\000\013\000\000\000\004\000\001\000\f\000\000");
/* 2141 */     PrivilegedExceptionAction<Constructor<?>> privilegedExceptionAction = new PrivilegedExceptionAction<Constructor<?>>()
/*      */       {
/*      */         public Constructor<?> run() throws Exception {
/* 2144 */           Class<RMIConnector> clazz = RMIConnector.class;
/* 2145 */           ClassLoader classLoader = clazz.getClassLoader();
/*      */           
/* 2147 */           ProtectionDomain protectionDomain = clazz.getProtectionDomain();
/* 2148 */           String[] arrayOfString = { ProxyRef.class.getName() };
/* 2149 */           NoCallStackClassLoader noCallStackClassLoader = new NoCallStackClassLoader("com.sun.jmx.remote.internal.PRef", pRefByteCode, arrayOfString, classLoader, protectionDomain);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2155 */           Class<?> clazz1 = noCallStackClassLoader.loadClass("com.sun.jmx.remote.internal.PRef");
/* 2156 */           return clazz1.getConstructor(new Class[] { RemoteRef.class });
/*      */         }
/*      */       };
/*      */ 
/*      */     
/*      */     try {
/* 2162 */       clazz1 = Class.forName(rmiServerImplStubClassName);
/* 2163 */     } catch (Exception null) {
/* 2164 */       logger.error("<clinit>", "Failed to instantiate " + rmiServerImplStubClassName + ": " + clazz2);
/*      */ 
/*      */       
/* 2167 */       logger.debug("<clinit>", (Throwable)clazz2);
/* 2168 */       clazz1 = null;
/*      */     } 
/* 2170 */     rmiServerImplStubClass = clazz1;
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 2175 */       clazz2 = Class.forName(rmiConnectionImplStubClassName);
/* 2176 */       constructor = AccessController.<Constructor>doPrivileged((PrivilegedExceptionAction)privilegedExceptionAction);
/* 2177 */     } catch (Exception exception) {
/* 2178 */       logger.error("<clinit>", "Failed to initialize proxy reference constructor for " + rmiConnectionImplStubClassName + ": " + exception);
/*      */ 
/*      */       
/* 2181 */       logger.debug("<clinit>", exception);
/* 2182 */       clazz2 = null;
/* 2183 */       constructor = null;
/*      */     } 
/* 2185 */     rmiConnectionImplStubClass = clazz2;
/* 2186 */     proxyRefConstructor = constructor;
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
/* 2370 */     byte[] arrayOfByte2 = NoCallStackClassLoader.stringToBytes("Êþº¾\000\000\0003\000+\n\000\f\000\030\007\000\031\n\000\f\000\032\n\000\002\000\033\007\000\034\n\000\005\000\035\n\000\005\000\036\n\000\005\000\037\n\000\002\000 \n\000\f\000!\007\000\"\007\000#\001\000\006<init>\001\000\003()V\001\000\004Code\001\000\007_invoke\001\000K(Lorg/omg/CORBA/portable/OutputStream;)Lorg/omg/CORBA/portable/InputStream;\001\000\rStackMapTable\007\000\034\001\000\nExceptions\007\000$\001\000\r_releaseReply\001\000'(Lorg/omg/CORBA/portable/InputStream;)V\f\000\r\000\016\001\000-com/sun/jmx/remote/protocol/iiop/PInputStream\f\000\020\000\021\f\000\r\000\027\001\000+org/omg/CORBA/portable/ApplicationException\f\000%\000&\f\000'\000(\f\000\r\000)\f\000*\000&\f\000\026\000\027\001\000*com/sun/jmx/remote/protocol/iiop/ProxyStub\001\000<org/omg/stub/javax/management/remote/rmi/_RMIConnection_Stub\001\000)org/omg/CORBA/portable/RemarshalException\001\000\016getInputStream\001\000&()Lorg/omg/CORBA/portable/InputStream;\001\000\005getId\001\000\024()Ljava/lang/String;\001\0009(Ljava/lang/String;Lorg/omg/CORBA/portable/InputStream;)V\001\000\025getProxiedInputStream\000!\000\013\000\f\000\000\000\000\000\003\000\001\000\r\000\016\000\001\000\017\000\000\000\021\000\001\000\001\000\000\000\005*·\000\001±\000\000\000\000\000\001\000\020\000\021\000\002\000\017\000\000\000G\000\004\000\004\000\000\000'»\000\002Y*+·\000\003·\000\004°M»\000\002Y,¶\000\006·\000\004N»\000\005Y,¶\000\007-·\000\b¿\000\001\000\000\000\f\000\r\000\005\000\001\000\022\000\000\000\006\000\001M\007\000\023\000\024\000\000\000\006\000\002\000\005\000\025\000\001\000\026\000\027\000\001\000\017\000\000\000'\000\002\000\002\000\000\000\022+Æ\000\013+À\000\002¶\000\tL*+·\000\n±\000\000\000\001\000\022\000\000\000\003\000\001\f\000\000");
/*      */     
/* 2372 */     byte[] arrayOfByte3 = NoCallStackClassLoader.stringToBytes("Êþº¾\000\000\0003\000\036\n\000\007\000\017\t\000\006\000\020\n\000\021\000\022\n\000\006\000\023\n\000\024\000\025\007\000\026\007\000\027\001\000\006<init>\001\000'(Lorg/omg/CORBA/portable/InputStream;)V\001\000\004Code\001\000\bread_any\001\000\025()Lorg/omg/CORBA/Any;\001\000\nread_value\001\000)(Ljava/lang/Class;)Ljava/io/Serializable;\f\000\b\000\t\f\000\030\000\031\007\000\032\f\000\013\000\f\f\000\033\000\034\007\000\035\f\000\r\000\016\001\000-com/sun/jmx/remote/protocol/iiop/PInputStream\001\0001com/sun/jmx/remote/protocol/iiop/ProxyInputStream\001\000\002in\001\000$Lorg/omg/CORBA/portable/InputStream;\001\000\"org/omg/CORBA/portable/InputStream\001\000\006narrow\001\000*()Lorg/omg/CORBA_2_3/portable/InputStream;\001\000&org/omg/CORBA_2_3/portable/InputStream\000!\000\006\000\007\000\000\000\000\000\003\000\001\000\b\000\t\000\001\000\n\000\000\000\022\000\002\000\002\000\000\000\006*+·\000\001±\000\000\000\000\000\001\000\013\000\f\000\001\000\n\000\000\000\024\000\001\000\001\000\000\000\b*´\000\002¶\000\003°\000\000\000\000\000\001\000\r\000\016\000\001\000\n\000\000\000\025\000\002\000\002\000\000\000\t*¶\000\004+¶\000\005°\000\000\000\000\000\000");
/* 2373 */     final String[] classNames = { "com.sun.jmx.remote.protocol.iiop.ProxyStub", "com.sun.jmx.remote.protocol.iiop.PInputStream" };
/* 2374 */     final byte[][] byteCodes = { arrayOfByte2, arrayOfByte3 };
/* 2375 */     final String[] otherClassNames = { "org.omg.stub.javax.management.remote.rmi._RMIConnection_Stub", "com.sun.jmx.remote.protocol.iiop.ProxyInputStream" };
/*      */ 
/*      */ 
/*      */     
/* 2379 */     if (IIOPHelper.isAvailable()) {
/* 2380 */       Class clazz; PrivilegedExceptionAction<Class<?>> privilegedExceptionAction1 = new PrivilegedExceptionAction<Class<?>>()
/*      */         {
/*      */           public Class<?> run() throws Exception {
/* 2383 */             Class<RMIConnector> clazz = RMIConnector.class;
/* 2384 */             ClassLoader classLoader = clazz.getClassLoader();
/*      */             
/* 2386 */             ProtectionDomain protectionDomain = clazz.getProtectionDomain();
/* 2387 */             NoCallStackClassLoader noCallStackClassLoader = new NoCallStackClassLoader(classNames, byteCodes, otherClassNames, classLoader, protectionDomain);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 2393 */             return noCallStackClassLoader.loadClass("com.sun.jmx.remote.protocol.iiop.ProxyStub");
/*      */           }
/*      */         };
/*      */       
/*      */       try {
/* 2398 */         clazz = AccessController.<Class<?>>doPrivileged(privilegedExceptionAction1);
/* 2399 */       } catch (Exception exception) {
/* 2400 */         logger.error("<clinit>", "Unexpected exception making shadow IIOP stub class: " + exception);
/*      */         
/* 2402 */         logger.debug("<clinit>", exception);
/* 2403 */         clazz = null;
/*      */       } 
/* 2405 */       proxyStubClass = clazz;
/*      */     } else {
/* 2407 */       proxyStubClass = null;
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
/* 2526 */     base64ToInt = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
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
/* 2623 */     orb = null; } private static RMIConnection shadowJrmpStub(RemoteObject paramRemoteObject) throws InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException { RemoteRef remoteRef1 = paramRemoteObject.getRef(); RemoteRef remoteRef2 = (RemoteRef)proxyRefConstructor.newInstance(new Object[] { remoteRef1 }); Constructor<?> constructor = rmiConnectionImplStubClass.getConstructor(new Class[] { RemoteRef.class }); Object[] arrayOfObject = { remoteRef2 }; return (RMIConnection)constructor.newInstance(arrayOfObject); } private static RMIConnection shadowIiopStub(Object paramObject) throws InstantiationException, IllegalAccessException { RMIConnection rMIConnection = null; try { rMIConnection = AccessController.doPrivileged(new PrivilegedExceptionAction() { public Object run() throws Exception { return RMIConnector.proxyStubClass.newInstance(); } }
/*      */         ); } catch (PrivilegedActionException privilegedActionException) { throw new InternalError(); }  IIOPHelper.setDelegate(rMIConnection, IIOPHelper.getDelegate(paramObject)); return rMIConnection; } private static RMIConnection getConnection(RMIServer paramRMIServer, Object paramObject, boolean paramBoolean) throws IOException { RMIConnection rMIConnection = paramRMIServer.newClient(paramObject); if (paramBoolean) checkStub(rMIConnection, rmiConnectionImplStubClass);  try { if (rMIConnection.getClass() == rmiConnectionImplStubClass) return shadowJrmpStub((RemoteObject)rMIConnection);  if (rMIConnection.getClass().getName().equals("org.omg.stub.javax.management.remote.rmi._RMIConnection_Stub")) return shadowIiopStub(rMIConnection);  logger.trace("getConnection", "Did not wrap " + rMIConnection.getClass() + " to foil stack search for classes: class loading semantics may be incorrect"); } catch (Exception exception) { logger.error("getConnection", "Could not wrap " + rMIConnection.getClass() + " to foil stack search for classes: class loading semantics may be incorrect: " + exception); logger.debug("getConnection", exception); }  return rMIConnection; } private static byte[] base64ToByteArray(String paramString) { int i = paramString.length(); int j = i / 4; if (4 * j != i) throw new IllegalArgumentException("String length must be a multiple of four.");  byte b1 = 0; int k = j; if (i != 0) { if (paramString.charAt(i - 1) == '=') { b1++; k--; }  if (paramString.charAt(i - 2) == '=') b1++;  }  byte[] arrayOfByte = new byte[3 * j - b1]; byte b2 = 0, b3 = 0; int m; for (m = 0; m < k; m++) { int n = base64toInt(paramString.charAt(b2++)); int i1 = base64toInt(paramString.charAt(b2++)); int i2 = base64toInt(paramString.charAt(b2++)); int i3 = base64toInt(paramString.charAt(b2++)); arrayOfByte[b3++] = (byte)(n << 2 | i1 >> 4); arrayOfByte[b3++] = (byte)(i1 << 4 | i2 >> 2); arrayOfByte[b3++] = (byte)(i2 << 6 | i3); }  if (b1 != 0) { m = base64toInt(paramString.charAt(b2++)); int n = base64toInt(paramString.charAt(b2++)); arrayOfByte[b3++] = (byte)(m << 2 | n >> 4); if (b1 == 1) { int i1 = base64toInt(paramString.charAt(b2++)); arrayOfByte[b3++] = (byte)(n << 4 | i1 >> 2); }  }  return arrayOfByte; } private static int base64toInt(char paramChar) { byte b; if (paramChar >= base64ToInt.length) { b = -1; } else { b = base64ToInt[paramChar]; }  if (b < 0) throw new IllegalArgumentException("Illegal character " + paramChar);  return b; } private ClassLoader pushDefaultClassLoader() { final Thread t = Thread.currentThread(); ClassLoader classLoader = thread.getContextClassLoader(); if (this.defaultClassLoader != null) AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */             public Void run() { t.setContextClassLoader(RMIConnector.this.defaultClassLoader); return null; }
/*      */           });  return classLoader; } private void popDefaultClassLoader(final ClassLoader old) { AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */           public Void run() { Thread.currentThread().setContextClassLoader(old); return null; }
/* 2628 */         }); } private transient long clientNotifSeqNo = 0L; private transient WeakHashMap<Subject, WeakReference<MBeanServerConnection>> rmbscMap; private static String objects(Object[] paramArrayOfObject) { if (paramArrayOfObject == null) {
/* 2629 */       return "null";
/*      */     }
/* 2631 */     return Arrays.<Object>asList(paramArrayOfObject).toString(); }
/*      */   
/*      */   private transient WeakReference<MBeanServerConnection> nullSubjectConnRef = null; private transient RMINotifClient rmiNotifClient; private transient long clientNotifCounter = 0L; private transient boolean connected; private transient boolean terminated; private transient Exception closeException; private transient NotificationBroadcasterSupport connectionBroadcaster; private transient ClientCommunicatorAdmin communicatorAdmin; private static volatile WeakReference<Object> orb;
/*      */   private static String strings(String[] paramArrayOfString) {
/* 2635 */     return objects((Object[])paramArrayOfString);
/*      */   }
/*      */   
/*      */   static String getAttributesNames(AttributeList paramAttributeList) {
/* 2639 */     return (paramAttributeList != null) ? paramAttributeList
/* 2640 */       .asList().stream()
/* 2641 */       .map(Attribute::getName)
/* 2642 */       .collect(Collectors.joining(", ", "[", "]")) : "[]";
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/rmi/RMIConnector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */