/*      */ package javax.management.remote.rmi;
/*      */ 
/*      */ import com.sun.jmx.mbeanserver.Util;
/*      */ import com.sun.jmx.remote.internal.ServerCommunicatorAdmin;
/*      */ import com.sun.jmx.remote.internal.ServerNotifForwarder;
/*      */ import com.sun.jmx.remote.security.JMXSubjectDomainCombiner;
/*      */ import com.sun.jmx.remote.security.SubjectDelegator;
/*      */ import com.sun.jmx.remote.util.ClassLoaderWithRepository;
/*      */ import com.sun.jmx.remote.util.ClassLogger;
/*      */ import com.sun.jmx.remote.util.EnvHelp;
/*      */ import com.sun.jmx.remote.util.OrderClassLoaders;
/*      */ import java.io.IOException;
/*      */ import java.rmi.MarshalledObject;
/*      */ import java.rmi.UnmarshalException;
/*      */ import java.rmi.server.Unreferenced;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permission;
/*      */ import java.security.Permissions;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
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
/*      */ import javax.management.MBeanPermission;
/*      */ import javax.management.MBeanRegistrationException;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.NotCompliantMBeanException;
/*      */ import javax.management.NotificationFilter;
/*      */ import javax.management.ObjectInstance;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.QueryExp;
/*      */ import javax.management.ReflectionException;
/*      */ import javax.management.RuntimeOperationsException;
/*      */ import javax.management.loading.ClassLoaderRepository;
/*      */ import javax.management.remote.JMXServerErrorException;
/*      */ import javax.management.remote.NotificationResult;
/*      */ import javax.security.auth.Subject;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RMIConnectionImpl
/*      */   implements RMIConnection, Unreferenced
/*      */ {
/*      */   public RMIConnectionImpl(RMIServerImpl paramRMIServerImpl, String paramString, ClassLoader paramClassLoader, Subject paramSubject, Map<String, ?> paramMap) {
/*  106 */     if (paramRMIServerImpl == null || paramString == null)
/*  107 */       throw new NullPointerException("Illegal null argument"); 
/*  108 */     if (paramMap == null)
/*  109 */       paramMap = Collections.emptyMap(); 
/*  110 */     this.rmiServer = paramRMIServerImpl;
/*  111 */     this.connectionId = paramString;
/*  112 */     this.defaultClassLoader = paramClassLoader;
/*      */     
/*  114 */     this.subjectDelegator = new SubjectDelegator();
/*  115 */     this.subject = paramSubject;
/*  116 */     if (paramSubject == null) {
/*  117 */       this.acc = null;
/*  118 */       this.removeCallerContext = false;
/*      */     } else {
/*  120 */       this
/*  121 */         .removeCallerContext = SubjectDelegator.checkRemoveCallerContext(paramSubject);
/*  122 */       if (this.removeCallerContext) {
/*  123 */         this
/*  124 */           .acc = JMXSubjectDomainCombiner.getDomainCombinerContext(paramSubject);
/*      */       } else {
/*  126 */         this
/*  127 */           .acc = JMXSubjectDomainCombiner.getContext(paramSubject);
/*      */       } 
/*      */     } 
/*  130 */     this.mbeanServer = paramRMIServerImpl.getMBeanServer();
/*      */     
/*  132 */     final ClassLoader dcl = paramClassLoader;
/*      */     
/*  134 */     final ClassLoaderRepository repository = AccessController.<ClassLoaderRepository>doPrivileged(new PrivilegedAction<ClassLoaderRepository>()
/*      */         {
/*      */           public ClassLoaderRepository run() {
/*  137 */             return RMIConnectionImpl.this.mbeanServer.getClassLoaderRepository();
/*      */           }
/*      */         }, 
/*  140 */         withPermissions(new Permission[] { new MBeanPermission("*", "getClassLoaderRepository") }));
/*      */     
/*  142 */     this.classLoaderWithRepository = AccessController.<ClassLoaderWithRepository>doPrivileged(new PrivilegedAction<ClassLoaderWithRepository>()
/*      */         {
/*      */           public ClassLoaderWithRepository run() {
/*  145 */             return new ClassLoaderWithRepository(repository, dcl);
/*      */           }
/*  150 */         }withPermissions(new Permission[] { new RuntimePermission("createClassLoader") }));
/*      */ 
/*      */     
/*  153 */     this
/*  154 */       .defaultContextClassLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>()
/*      */         {
/*      */           public ClassLoader run()
/*      */           {
/*  158 */             return new RMIConnectionImpl.CombinedClassLoader(Thread.currentThread().getContextClassLoader(), dcl);
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  163 */     this
/*  164 */       .serverCommunicatorAdmin = new RMIServerCommunicatorAdmin(EnvHelp.getServerConnectionTimeout(paramMap));
/*      */     
/*  166 */     this.env = paramMap;
/*      */   }
/*      */   
/*      */   private static AccessControlContext withPermissions(Permission... paramVarArgs) {
/*  170 */     Permissions permissions = new Permissions();
/*      */     
/*  172 */     for (Permission permission : paramVarArgs) {
/*  173 */       permissions.add(permission);
/*      */     }
/*      */     
/*  176 */     ProtectionDomain protectionDomain = new ProtectionDomain(null, permissions);
/*  177 */     return new AccessControlContext(new ProtectionDomain[] { protectionDomain });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private synchronized ServerNotifForwarder getServerNotifFwd() {
/*  183 */     if (this.serverNotifForwarder == null) {
/*  184 */       this
/*      */ 
/*      */         
/*  187 */         .serverNotifForwarder = new ServerNotifForwarder(this.mbeanServer, this.env, this.rmiServer.getNotifBuffer(), this.connectionId);
/*      */     }
/*  189 */     return this.serverNotifForwarder;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getConnectionId() throws IOException {
/*  194 */     return this.connectionId;
/*      */   }
/*      */   
/*      */   public void close() throws IOException {
/*  198 */     boolean bool = logger.debugOn();
/*  199 */     String str = bool ? ("[" + toString() + "]") : null;
/*      */     
/*  201 */     synchronized (this) {
/*  202 */       if (this.terminated) {
/*  203 */         if (bool) logger.debug("close", str + " already terminated.");
/*      */         
/*      */         return;
/*      */       } 
/*  207 */       if (bool) logger.debug("close", str + " closing.");
/*      */       
/*  209 */       this.terminated = true;
/*      */       
/*  211 */       if (this.serverCommunicatorAdmin != null) {
/*  212 */         this.serverCommunicatorAdmin.terminate();
/*      */       }
/*      */       
/*  215 */       if (this.serverNotifForwarder != null) {
/*  216 */         this.serverNotifForwarder.terminate();
/*      */       }
/*      */     } 
/*      */     
/*  220 */     this.rmiServer.clientClosed(this);
/*      */     
/*  222 */     if (bool) logger.debug("close", str + " closed."); 
/*      */   }
/*      */   
/*      */   public void unreferenced() {
/*  226 */     logger.debug("unreferenced", "called");
/*      */     try {
/*  228 */       close();
/*  229 */       logger.debug("unreferenced", "done");
/*  230 */     } catch (IOException iOException) {
/*  231 */       logger.fine("unreferenced", iOException);
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
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName, Subject paramSubject) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
/*      */     try {
/*  250 */       Object[] arrayOfObject = { paramString, paramObjectName };
/*      */ 
/*      */       
/*  253 */       if (logger.debugOn()) {
/*  254 */         logger.debug("createMBean(String,ObjectName)", "connectionId=" + this.connectionId + ", className=" + paramString + ", name=" + paramObjectName);
/*      */       }
/*      */ 
/*      */       
/*  258 */       return (ObjectInstance)
/*  259 */         doPrivilegedOperation(3, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  263 */     catch (PrivilegedActionException privilegedActionException) {
/*  264 */       Exception exception = extractException(privilegedActionException);
/*  265 */       if (exception instanceof ReflectionException)
/*  266 */         throw (ReflectionException)exception; 
/*  267 */       if (exception instanceof InstanceAlreadyExistsException)
/*  268 */         throw (InstanceAlreadyExistsException)exception; 
/*  269 */       if (exception instanceof MBeanRegistrationException)
/*  270 */         throw (MBeanRegistrationException)exception; 
/*  271 */       if (exception instanceof MBeanException)
/*  272 */         throw (MBeanException)exception; 
/*  273 */       if (exception instanceof NotCompliantMBeanException)
/*  274 */         throw (NotCompliantMBeanException)exception; 
/*  275 */       if (exception instanceof IOException)
/*  276 */         throw (IOException)exception; 
/*  277 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName1, ObjectName paramObjectName2, Subject paramSubject) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
/*      */     try {
/*  294 */       Object[] arrayOfObject = { paramString, paramObjectName1, paramObjectName2 };
/*      */ 
/*      */       
/*  297 */       if (logger.debugOn()) {
/*  298 */         logger.debug("createMBean(String,ObjectName,ObjectName)", "connectionId=" + this.connectionId + ", className=" + paramString + ", name=" + paramObjectName1 + ", loaderName=" + paramObjectName2);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  304 */       return (ObjectInstance)
/*  305 */         doPrivilegedOperation(5, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  309 */     catch (PrivilegedActionException privilegedActionException) {
/*  310 */       Exception exception = extractException(privilegedActionException);
/*  311 */       if (exception instanceof ReflectionException)
/*  312 */         throw (ReflectionException)exception; 
/*  313 */       if (exception instanceof InstanceAlreadyExistsException)
/*  314 */         throw (InstanceAlreadyExistsException)exception; 
/*  315 */       if (exception instanceof MBeanRegistrationException)
/*  316 */         throw (MBeanRegistrationException)exception; 
/*  317 */       if (exception instanceof MBeanException)
/*  318 */         throw (MBeanException)exception; 
/*  319 */       if (exception instanceof NotCompliantMBeanException)
/*  320 */         throw (NotCompliantMBeanException)exception; 
/*  321 */       if (exception instanceof InstanceNotFoundException)
/*  322 */         throw (InstanceNotFoundException)exception; 
/*  323 */       if (exception instanceof IOException)
/*  324 */         throw (IOException)exception; 
/*  325 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName, MarshalledObject<?> paramMarshalledObject, String[] paramArrayOfString, Subject paramSubject) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
/*  344 */     boolean bool = logger.debugOn();
/*      */     
/*  346 */     if (bool) logger.debug("createMBean(String,ObjectName,Object[],String[])", "connectionId=" + this.connectionId + ", unwrapping parameters using classLoaderWithRepository.");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  352 */     Object[] arrayOfObject = nullIsEmpty(unwrap(paramMarshalledObject, this.classLoaderWithRepository, Object[].class, paramSubject));
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  357 */       Object[] arrayOfObject1 = { paramString, paramObjectName, arrayOfObject, nullIsEmpty(paramArrayOfString) };
/*      */       
/*  359 */       if (bool) {
/*  360 */         logger.debug("createMBean(String,ObjectName,Object[],String[])", "connectionId=" + this.connectionId + ", className=" + paramString + ", name=" + paramObjectName + ", signature=" + 
/*      */ 
/*      */ 
/*      */             
/*  364 */             strings(paramArrayOfString));
/*      */       }
/*  366 */       return (ObjectInstance)
/*  367 */         doPrivilegedOperation(4, arrayOfObject1, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  371 */     catch (PrivilegedActionException privilegedActionException) {
/*  372 */       Exception exception = extractException(privilegedActionException);
/*  373 */       if (exception instanceof ReflectionException)
/*  374 */         throw (ReflectionException)exception; 
/*  375 */       if (exception instanceof InstanceAlreadyExistsException)
/*  376 */         throw (InstanceAlreadyExistsException)exception; 
/*  377 */       if (exception instanceof MBeanRegistrationException)
/*  378 */         throw (MBeanRegistrationException)exception; 
/*  379 */       if (exception instanceof MBeanException)
/*  380 */         throw (MBeanException)exception; 
/*  381 */       if (exception instanceof NotCompliantMBeanException)
/*  382 */         throw (NotCompliantMBeanException)exception; 
/*  383 */       if (exception instanceof IOException)
/*  384 */         throw (IOException)exception; 
/*  385 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName1, ObjectName paramObjectName2, MarshalledObject<?> paramMarshalledObject, String[] paramArrayOfString, Subject paramSubject) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
/*  406 */     boolean bool = logger.debugOn();
/*      */     
/*  408 */     if (bool) logger.debug("createMBean(String,ObjectName,ObjectName,Object[],String[])", "connectionId=" + this.connectionId + ", unwrapping params with MBean extended ClassLoader.");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  413 */     Object[] arrayOfObject = nullIsEmpty(unwrap(paramMarshalledObject, 
/*  414 */           getClassLoader(paramObjectName2), this.defaultClassLoader, Object[].class, paramSubject));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  421 */       Object[] arrayOfObject1 = { paramString, paramObjectName1, paramObjectName2, arrayOfObject, nullIsEmpty(paramArrayOfString) };
/*      */       
/*  423 */       if (bool) logger.debug("createMBean(String,ObjectName,ObjectName,Object[],String[])", "connectionId=" + this.connectionId + ", className=" + paramString + ", name=" + paramObjectName1 + ", loaderName=" + paramObjectName2 + ", signature=" + 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  429 */             strings(paramArrayOfString));
/*      */       
/*  431 */       return (ObjectInstance)
/*  432 */         doPrivilegedOperation(6, arrayOfObject1, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  436 */     catch (PrivilegedActionException privilegedActionException) {
/*  437 */       Exception exception = extractException(privilegedActionException);
/*  438 */       if (exception instanceof ReflectionException)
/*  439 */         throw (ReflectionException)exception; 
/*  440 */       if (exception instanceof InstanceAlreadyExistsException)
/*  441 */         throw (InstanceAlreadyExistsException)exception; 
/*  442 */       if (exception instanceof MBeanRegistrationException)
/*  443 */         throw (MBeanRegistrationException)exception; 
/*  444 */       if (exception instanceof MBeanException)
/*  445 */         throw (MBeanException)exception; 
/*  446 */       if (exception instanceof NotCompliantMBeanException)
/*  447 */         throw (NotCompliantMBeanException)exception; 
/*  448 */       if (exception instanceof InstanceNotFoundException)
/*  449 */         throw (InstanceNotFoundException)exception; 
/*  450 */       if (exception instanceof IOException)
/*  451 */         throw (IOException)exception; 
/*  452 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unregisterMBean(ObjectName paramObjectName, Subject paramSubject) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
/*      */     try {
/*  462 */       Object[] arrayOfObject = { paramObjectName };
/*      */       
/*  464 */       if (logger.debugOn()) logger.debug("unregisterMBean", "connectionId=" + this.connectionId + ", name=" + paramObjectName);
/*      */ 
/*      */ 
/*      */       
/*  468 */       doPrivilegedOperation(24, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  472 */     catch (PrivilegedActionException privilegedActionException) {
/*  473 */       Exception exception = extractException(privilegedActionException);
/*  474 */       if (exception instanceof InstanceNotFoundException)
/*  475 */         throw (InstanceNotFoundException)exception; 
/*  476 */       if (exception instanceof MBeanRegistrationException)
/*  477 */         throw (MBeanRegistrationException)exception; 
/*  478 */       if (exception instanceof IOException)
/*  479 */         throw (IOException)exception; 
/*  480 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectInstance getObjectInstance(ObjectName paramObjectName, Subject paramSubject) throws InstanceNotFoundException, IOException {
/*  490 */     checkNonNull("ObjectName", paramObjectName);
/*      */     
/*      */     try {
/*  493 */       Object[] arrayOfObject = { paramObjectName };
/*      */       
/*  495 */       if (logger.debugOn()) logger.debug("getObjectInstance", "connectionId=" + this.connectionId + ", name=" + paramObjectName);
/*      */ 
/*      */ 
/*      */       
/*  499 */       return (ObjectInstance)
/*  500 */         doPrivilegedOperation(13, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  504 */     catch (PrivilegedActionException privilegedActionException) {
/*  505 */       Exception exception = extractException(privilegedActionException);
/*  506 */       if (exception instanceof InstanceNotFoundException)
/*  507 */         throw (InstanceNotFoundException)exception; 
/*  508 */       if (exception instanceof IOException)
/*  509 */         throw (IOException)exception; 
/*  510 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<ObjectInstance> queryMBeans(ObjectName paramObjectName, MarshalledObject<?> paramMarshalledObject, Subject paramSubject) throws IOException {
/*  521 */     boolean bool = logger.debugOn();
/*      */     
/*  523 */     if (bool) logger.debug("queryMBeans", "connectionId=" + this.connectionId + " unwrapping query with defaultClassLoader.");
/*      */ 
/*      */ 
/*      */     
/*  527 */     QueryExp queryExp = unwrap(paramMarshalledObject, this.defaultContextClassLoader, QueryExp.class, paramSubject);
/*      */     
/*      */     try {
/*  530 */       Object[] arrayOfObject = { paramObjectName, queryExp };
/*      */       
/*  532 */       if (bool) logger.debug("queryMBeans", "connectionId=" + this.connectionId + ", name=" + paramObjectName + ", query=" + paramMarshalledObject);
/*      */ 
/*      */ 
/*      */       
/*  536 */       return Util.<Set<ObjectInstance>>cast(
/*  537 */           doPrivilegedOperation(17, arrayOfObject, paramSubject));
/*      */ 
/*      */     
/*      */     }
/*  541 */     catch (PrivilegedActionException privilegedActionException) {
/*  542 */       Exception exception = extractException(privilegedActionException);
/*  543 */       if (exception instanceof IOException)
/*  544 */         throw (IOException)exception; 
/*  545 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<ObjectName> queryNames(ObjectName paramObjectName, MarshalledObject<?> paramMarshalledObject, Subject paramSubject) throws IOException {
/*  556 */     boolean bool = logger.debugOn();
/*      */     
/*  558 */     if (bool) logger.debug("queryNames", "connectionId=" + this.connectionId + " unwrapping query with defaultClassLoader.");
/*      */ 
/*      */ 
/*      */     
/*  562 */     QueryExp queryExp = unwrap(paramMarshalledObject, this.defaultContextClassLoader, QueryExp.class, paramSubject);
/*      */     
/*      */     try {
/*  565 */       Object[] arrayOfObject = { paramObjectName, queryExp };
/*      */       
/*  567 */       if (bool) logger.debug("queryNames", "connectionId=" + this.connectionId + ", name=" + paramObjectName + ", query=" + paramMarshalledObject);
/*      */ 
/*      */ 
/*      */       
/*  571 */       return Util.<Set<ObjectName>>cast(
/*  572 */           doPrivilegedOperation(18, arrayOfObject, paramSubject));
/*      */ 
/*      */     
/*      */     }
/*  576 */     catch (PrivilegedActionException privilegedActionException) {
/*  577 */       Exception exception = extractException(privilegedActionException);
/*  578 */       if (exception instanceof IOException)
/*  579 */         throw (IOException)exception; 
/*  580 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRegistered(ObjectName paramObjectName, Subject paramSubject) throws IOException {
/*      */     try {
/*  587 */       Object[] arrayOfObject = { paramObjectName };
/*  588 */       return ((Boolean)
/*  589 */         doPrivilegedOperation(16, arrayOfObject, paramSubject))
/*      */ 
/*      */         
/*  592 */         .booleanValue();
/*  593 */     } catch (PrivilegedActionException privilegedActionException) {
/*  594 */       Exception exception = extractException(privilegedActionException);
/*  595 */       if (exception instanceof IOException)
/*  596 */         throw (IOException)exception; 
/*  597 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMBeanCount(Subject paramSubject) throws IOException {
/*      */     try {
/*  604 */       Object[] arrayOfObject = new Object[0];
/*      */       
/*  606 */       if (logger.debugOn()) logger.debug("getMBeanCount", "connectionId=" + this.connectionId);
/*      */ 
/*      */       
/*  609 */       return (Integer)
/*  610 */         doPrivilegedOperation(11, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  614 */     catch (PrivilegedActionException privilegedActionException) {
/*  615 */       Exception exception = extractException(privilegedActionException);
/*  616 */       if (exception instanceof IOException)
/*  617 */         throw (IOException)exception; 
/*  618 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public Object getAttribute(ObjectName paramObjectName, String paramString, Subject paramSubject) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
/*      */     try {
/*  632 */       Object[] arrayOfObject = { paramObjectName, paramString };
/*  633 */       if (logger.debugOn()) logger.debug("getAttribute", "connectionId=" + this.connectionId + ", name=" + paramObjectName + ", attribute=" + paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  638 */       return 
/*  639 */         doPrivilegedOperation(7, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  643 */     catch (PrivilegedActionException privilegedActionException) {
/*  644 */       Exception exception = extractException(privilegedActionException);
/*  645 */       if (exception instanceof MBeanException)
/*  646 */         throw (MBeanException)exception; 
/*  647 */       if (exception instanceof AttributeNotFoundException)
/*  648 */         throw (AttributeNotFoundException)exception; 
/*  649 */       if (exception instanceof InstanceNotFoundException)
/*  650 */         throw (InstanceNotFoundException)exception; 
/*  651 */       if (exception instanceof ReflectionException)
/*  652 */         throw (ReflectionException)exception; 
/*  653 */       if (exception instanceof IOException)
/*  654 */         throw (IOException)exception; 
/*  655 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeList getAttributes(ObjectName paramObjectName, String[] paramArrayOfString, Subject paramSubject) throws InstanceNotFoundException, ReflectionException, IOException {
/*      */     try {
/*  667 */       Object[] arrayOfObject = { paramObjectName, paramArrayOfString };
/*      */       
/*  669 */       if (logger.debugOn()) logger.debug("getAttributes", "connectionId=" + this.connectionId + ", name=" + paramObjectName + ", attributes=" + 
/*      */ 
/*      */             
/*  672 */             strings(paramArrayOfString));
/*      */       
/*  674 */       return (AttributeList)
/*  675 */         doPrivilegedOperation(8, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  679 */     catch (PrivilegedActionException privilegedActionException) {
/*  680 */       Exception exception = extractException(privilegedActionException);
/*  681 */       if (exception instanceof InstanceNotFoundException)
/*  682 */         throw (InstanceNotFoundException)exception; 
/*  683 */       if (exception instanceof ReflectionException)
/*  684 */         throw (ReflectionException)exception; 
/*  685 */       if (exception instanceof IOException)
/*  686 */         throw (IOException)exception; 
/*  687 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public void setAttribute(ObjectName paramObjectName, MarshalledObject<?> paramMarshalledObject, Subject paramSubject) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
/*  703 */     boolean bool = logger.debugOn();
/*      */     
/*  705 */     if (bool) logger.debug("setAttribute", "connectionId=" + this.connectionId + " unwrapping attribute with MBean extended ClassLoader.");
/*      */ 
/*      */ 
/*      */     
/*  709 */     Attribute attribute = unwrap(paramMarshalledObject, 
/*  710 */         getClassLoaderFor(paramObjectName), this.defaultClassLoader, Attribute.class, paramSubject);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  715 */       Object[] arrayOfObject = { paramObjectName, attribute };
/*      */       
/*  717 */       if (bool) logger.debug("setAttribute", "connectionId=" + this.connectionId + ", name=" + paramObjectName + ", attribute name=" + attribute
/*      */ 
/*      */             
/*  720 */             .getName());
/*      */       
/*  722 */       doPrivilegedOperation(22, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  726 */     catch (PrivilegedActionException privilegedActionException) {
/*  727 */       Exception exception = extractException(privilegedActionException);
/*  728 */       if (exception instanceof InstanceNotFoundException)
/*  729 */         throw (InstanceNotFoundException)exception; 
/*  730 */       if (exception instanceof AttributeNotFoundException)
/*  731 */         throw (AttributeNotFoundException)exception; 
/*  732 */       if (exception instanceof InvalidAttributeValueException)
/*  733 */         throw (InvalidAttributeValueException)exception; 
/*  734 */       if (exception instanceof MBeanException)
/*  735 */         throw (MBeanException)exception; 
/*  736 */       if (exception instanceof ReflectionException)
/*  737 */         throw (ReflectionException)exception; 
/*  738 */       if (exception instanceof IOException)
/*  739 */         throw (IOException)exception; 
/*  740 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public AttributeList setAttributes(ObjectName paramObjectName, MarshalledObject<?> paramMarshalledObject, Subject paramSubject) throws InstanceNotFoundException, ReflectionException, IOException {
/*  753 */     boolean bool = logger.debugOn();
/*      */     
/*  755 */     if (bool) logger.debug("setAttributes", "connectionId=" + this.connectionId + " unwrapping attributes with MBean extended ClassLoader.");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  760 */     AttributeList attributeList = unwrap(paramMarshalledObject, 
/*  761 */         getClassLoaderFor(paramObjectName), this.defaultClassLoader, AttributeList.class, paramSubject);
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  766 */       Object[] arrayOfObject = { paramObjectName, attributeList };
/*      */       
/*  768 */       if (bool) logger.debug("setAttributes", "connectionId=" + this.connectionId + ", name=" + paramObjectName + ", attribute names=" + 
/*      */ 
/*      */             
/*  771 */             RMIConnector.getAttributesNames(attributeList));
/*      */       
/*  773 */       return (AttributeList)
/*  774 */         doPrivilegedOperation(23, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  778 */     catch (PrivilegedActionException privilegedActionException) {
/*  779 */       Exception exception = extractException(privilegedActionException);
/*  780 */       if (exception instanceof InstanceNotFoundException)
/*  781 */         throw (InstanceNotFoundException)exception; 
/*  782 */       if (exception instanceof ReflectionException)
/*  783 */         throw (ReflectionException)exception; 
/*  784 */       if (exception instanceof IOException)
/*  785 */         throw (IOException)exception; 
/*  786 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public Object invoke(ObjectName paramObjectName, String paramString, MarshalledObject<?> paramMarshalledObject, String[] paramArrayOfString, Subject paramSubject) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
/*  802 */     checkNonNull("ObjectName", paramObjectName);
/*  803 */     checkNonNull("Operation name", paramString);
/*      */ 
/*      */     
/*  806 */     boolean bool = logger.debugOn();
/*      */     
/*  808 */     if (bool) logger.debug("invoke", "connectionId=" + this.connectionId + " unwrapping params with MBean extended ClassLoader.");
/*      */ 
/*      */ 
/*      */     
/*  812 */     Object[] arrayOfObject = nullIsEmpty(unwrap(paramMarshalledObject, 
/*  813 */           getClassLoaderFor(paramObjectName), this.defaultClassLoader, Object[].class, paramSubject));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  820 */       Object[] arrayOfObject1 = { paramObjectName, paramString, arrayOfObject, nullIsEmpty(paramArrayOfString) };
/*      */       
/*  822 */       if (bool) logger.debug("invoke", "connectionId=" + this.connectionId + ", name=" + paramObjectName + ", operationName=" + paramString + ", signature=" + 
/*      */ 
/*      */ 
/*      */             
/*  826 */             strings(paramArrayOfString));
/*      */       
/*  828 */       return 
/*  829 */         doPrivilegedOperation(14, arrayOfObject1, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  833 */     catch (PrivilegedActionException privilegedActionException) {
/*  834 */       Exception exception = extractException(privilegedActionException);
/*  835 */       if (exception instanceof InstanceNotFoundException)
/*  836 */         throw (InstanceNotFoundException)exception; 
/*  837 */       if (exception instanceof MBeanException)
/*  838 */         throw (MBeanException)exception; 
/*  839 */       if (exception instanceof ReflectionException)
/*  840 */         throw (ReflectionException)exception; 
/*  841 */       if (exception instanceof IOException)
/*  842 */         throw (IOException)exception; 
/*  843 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDefaultDomain(Subject paramSubject) throws IOException {
/*      */     try {
/*  850 */       Object[] arrayOfObject = new Object[0];
/*      */       
/*  852 */       if (logger.debugOn()) logger.debug("getDefaultDomain", "connectionId=" + this.connectionId);
/*      */ 
/*      */       
/*  855 */       return (String)
/*  856 */         doPrivilegedOperation(9, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  860 */     catch (PrivilegedActionException privilegedActionException) {
/*  861 */       Exception exception = extractException(privilegedActionException);
/*  862 */       if (exception instanceof IOException)
/*  863 */         throw (IOException)exception; 
/*  864 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   public String[] getDomains(Subject paramSubject) throws IOException {
/*      */     try {
/*  870 */       Object[] arrayOfObject = new Object[0];
/*      */       
/*  872 */       if (logger.debugOn()) logger.debug("getDomains", "connectionId=" + this.connectionId);
/*      */ 
/*      */       
/*  875 */       return (String[])
/*  876 */         doPrivilegedOperation(10, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  880 */     catch (PrivilegedActionException privilegedActionException) {
/*  881 */       Exception exception = extractException(privilegedActionException);
/*  882 */       if (exception instanceof IOException)
/*  883 */         throw (IOException)exception; 
/*  884 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MBeanInfo getMBeanInfo(ObjectName paramObjectName, Subject paramSubject) throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
/*  895 */     checkNonNull("ObjectName", paramObjectName);
/*      */     
/*      */     try {
/*  898 */       Object[] arrayOfObject = { paramObjectName };
/*      */       
/*  900 */       if (logger.debugOn()) logger.debug("getMBeanInfo", "connectionId=" + this.connectionId + ", name=" + paramObjectName);
/*      */ 
/*      */ 
/*      */       
/*  904 */       return (MBeanInfo)
/*  905 */         doPrivilegedOperation(12, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/*  909 */     catch (PrivilegedActionException privilegedActionException) {
/*  910 */       Exception exception = extractException(privilegedActionException);
/*  911 */       if (exception instanceof InstanceNotFoundException)
/*  912 */         throw (InstanceNotFoundException)exception; 
/*  913 */       if (exception instanceof IntrospectionException)
/*  914 */         throw (IntrospectionException)exception; 
/*  915 */       if (exception instanceof ReflectionException)
/*  916 */         throw (ReflectionException)exception; 
/*  917 */       if (exception instanceof IOException)
/*  918 */         throw (IOException)exception; 
/*  919 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInstanceOf(ObjectName paramObjectName, String paramString, Subject paramSubject) throws InstanceNotFoundException, IOException {
/*  928 */     checkNonNull("ObjectName", paramObjectName);
/*      */     
/*      */     try {
/*  931 */       Object[] arrayOfObject = { paramObjectName, paramString };
/*      */       
/*  933 */       if (logger.debugOn()) logger.debug("isInstanceOf", "connectionId=" + this.connectionId + ", name=" + paramObjectName + ", className=" + paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  938 */       return ((Boolean)
/*  939 */         doPrivilegedOperation(15, arrayOfObject, paramSubject))
/*      */ 
/*      */         
/*  942 */         .booleanValue();
/*  943 */     } catch (PrivilegedActionException privilegedActionException) {
/*  944 */       Exception exception = extractException(privilegedActionException);
/*  945 */       if (exception instanceof InstanceNotFoundException)
/*  946 */         throw (InstanceNotFoundException)exception; 
/*  947 */       if (exception instanceof IOException)
/*  948 */         throw (IOException)exception; 
/*  949 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer[] addNotificationListeners(ObjectName[] paramArrayOfObjectName, MarshalledObject[] paramArrayOfMarshalledObject, Subject[] paramArrayOfSubject) throws InstanceNotFoundException, IOException {
/*  959 */     if (paramArrayOfObjectName == null || paramArrayOfMarshalledObject == null) {
/*  960 */       throw new IllegalArgumentException("Got null arguments.");
/*      */     }
/*      */     
/*  963 */     Subject[] arrayOfSubject = (paramArrayOfSubject != null) ? paramArrayOfSubject : new Subject[paramArrayOfObjectName.length];
/*      */     
/*  965 */     if (paramArrayOfObjectName.length != paramArrayOfMarshalledObject.length || paramArrayOfMarshalledObject.length != arrayOfSubject.length)
/*      */     {
/*      */       
/*  968 */       throw new IllegalArgumentException("The value lengths of 3 parameters are not same.");
/*      */     }
/*      */     byte b;
/*  971 */     for (b = 0; b < paramArrayOfObjectName.length; b++) {
/*  972 */       if (paramArrayOfObjectName[b] == null) {
/*  973 */         throw new IllegalArgumentException("Null Object name.");
/*      */       }
/*      */     } 
/*      */     
/*  977 */     b = 0;
/*      */     
/*  979 */     NotificationFilter[] arrayOfNotificationFilter = new NotificationFilter[paramArrayOfObjectName.length];
/*      */     
/*  981 */     Integer[] arrayOfInteger = new Integer[paramArrayOfObjectName.length];
/*  982 */     boolean bool = logger.debugOn();
/*      */     
/*      */     try {
/*  985 */       for (; b < paramArrayOfObjectName.length; b++) {
/*  986 */         ClassLoader classLoader = getClassLoaderFor(paramArrayOfObjectName[b]);
/*      */         
/*  988 */         if (bool) logger.debug("addNotificationListener(ObjectName,NotificationFilter)", "connectionId=" + this.connectionId + " unwrapping filter with target extended ClassLoader.");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  993 */         arrayOfNotificationFilter[b] = 
/*  994 */           unwrap(paramArrayOfMarshalledObject[b], classLoader, this.defaultClassLoader, NotificationFilter.class, arrayOfSubject[b]);
/*      */ 
/*      */         
/*  997 */         if (bool) logger.debug("addNotificationListener(ObjectName,NotificationFilter)", "connectionId=" + this.connectionId + ", name=" + paramArrayOfObjectName[b] + ", filter=" + arrayOfNotificationFilter[b]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1003 */         arrayOfInteger[b] = (Integer)
/* 1004 */           doPrivilegedOperation(1, new Object[] { paramArrayOfObjectName[b], arrayOfNotificationFilter[b] }, arrayOfSubject[b]);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1010 */       return arrayOfInteger;
/* 1011 */     } catch (Exception exception) {
/*      */       
/* 1013 */       for (byte b1 = 0; b1 < b; b1++) {
/*      */         try {
/* 1015 */           getServerNotifFwd().removeNotificationListener(paramArrayOfObjectName[b1], arrayOfInteger[b1]);
/*      */         }
/* 1017 */         catch (Exception exception1) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1022 */       if (exception instanceof PrivilegedActionException) {
/* 1023 */         exception = extractException(exception);
/*      */       }
/*      */       
/* 1026 */       if (exception instanceof ClassCastException)
/* 1027 */         throw (ClassCastException)exception; 
/* 1028 */       if (exception instanceof IOException)
/* 1029 */         throw (IOException)exception; 
/* 1030 */       if (exception instanceof InstanceNotFoundException)
/* 1031 */         throw (InstanceNotFoundException)exception; 
/* 1032 */       if (exception instanceof RuntimeException) {
/* 1033 */         throw (RuntimeException)exception;
/*      */       }
/* 1035 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public void addNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2, MarshalledObject<?> paramMarshalledObject1, MarshalledObject<?> paramMarshalledObject2, Subject paramSubject) throws InstanceNotFoundException, IOException {
/* 1048 */     checkNonNull("Target MBean name", paramObjectName1);
/* 1049 */     checkNonNull("Listener MBean name", paramObjectName2);
/*      */ 
/*      */ 
/*      */     
/* 1053 */     boolean bool = logger.debugOn();
/*      */     
/* 1055 */     ClassLoader classLoader = getClassLoaderFor(paramObjectName1);
/*      */     
/* 1057 */     if (bool) logger.debug("addNotificationListener(ObjectName,ObjectName,NotificationFilter,Object)", "connectionId=" + this.connectionId + " unwrapping filter with target extended ClassLoader.");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1063 */     NotificationFilter notificationFilter = unwrap(paramMarshalledObject1, classLoader, this.defaultClassLoader, NotificationFilter.class, paramSubject);
/*      */     
/* 1065 */     if (bool) logger.debug("addNotificationListener(ObjectName,ObjectName,NotificationFilter,Object)", "connectionId=" + this.connectionId + " unwrapping handback with target extended ClassLoader.");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1071 */     String str = (String)unwrap(paramMarshalledObject2, classLoader, this.defaultClassLoader, Object.class, paramSubject);
/*      */     
/*      */     try {
/* 1074 */       Object[] arrayOfObject = { paramObjectName1, paramObjectName2, notificationFilter, str };
/*      */ 
/*      */       
/* 1077 */       if (bool) logger.debug("addNotificationListener(ObjectName,ObjectName,NotificationFilter,Object)", "connectionId=" + this.connectionId + ", name=" + paramObjectName1 + ", listenerName=" + paramObjectName2 + ", filter=" + notificationFilter + ", handback=" + str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1085 */       doPrivilegedOperation(2, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/* 1089 */     catch (PrivilegedActionException privilegedActionException) {
/* 1090 */       Exception exception = extractException(privilegedActionException);
/* 1091 */       if (exception instanceof InstanceNotFoundException)
/* 1092 */         throw (InstanceNotFoundException)exception; 
/* 1093 */       if (exception instanceof IOException)
/* 1094 */         throw (IOException)exception; 
/* 1095 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public void removeNotificationListeners(ObjectName paramObjectName, Integer[] paramArrayOfInteger, Subject paramSubject) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
/* 1107 */     if (paramObjectName == null || paramArrayOfInteger == null) {
/* 1108 */       throw new IllegalArgumentException("Illegal null parameter");
/*      */     }
/* 1110 */     for (byte b = 0; b < paramArrayOfInteger.length; b++) {
/* 1111 */       if (paramArrayOfInteger[b] == null) {
/* 1112 */         throw new IllegalArgumentException("Null listener ID");
/*      */       }
/*      */     } 
/*      */     try {
/* 1116 */       Object[] arrayOfObject = { paramObjectName, paramArrayOfInteger };
/*      */       
/* 1118 */       if (logger.debugOn()) logger.debug("removeNotificationListener(ObjectName,Integer[])", "connectionId=" + this.connectionId + ", name=" + paramObjectName + ", listenerIDs=" + 
/*      */ 
/*      */ 
/*      */             
/* 1122 */             objects((Object[])paramArrayOfInteger));
/*      */       
/* 1124 */       doPrivilegedOperation(19, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/* 1128 */     catch (PrivilegedActionException privilegedActionException) {
/* 1129 */       Exception exception = extractException(privilegedActionException);
/* 1130 */       if (exception instanceof InstanceNotFoundException)
/* 1131 */         throw (InstanceNotFoundException)exception; 
/* 1132 */       if (exception instanceof ListenerNotFoundException)
/* 1133 */         throw (ListenerNotFoundException)exception; 
/* 1134 */       if (exception instanceof IOException)
/* 1135 */         throw (IOException)exception; 
/* 1136 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public void removeNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2, Subject paramSubject) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
/* 1148 */     checkNonNull("Target MBean name", paramObjectName1);
/* 1149 */     checkNonNull("Listener MBean name", paramObjectName2);
/*      */     
/*      */     try {
/* 1152 */       Object[] arrayOfObject = { paramObjectName1, paramObjectName2 };
/*      */       
/* 1154 */       if (logger.debugOn()) logger.debug("removeNotificationListener(ObjectName,ObjectName)", "connectionId=" + this.connectionId + ", name=" + paramObjectName1 + ", listenerName=" + paramObjectName2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1160 */       doPrivilegedOperation(20, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/* 1164 */     catch (PrivilegedActionException privilegedActionException) {
/* 1165 */       Exception exception = extractException(privilegedActionException);
/* 1166 */       if (exception instanceof InstanceNotFoundException)
/* 1167 */         throw (InstanceNotFoundException)exception; 
/* 1168 */       if (exception instanceof ListenerNotFoundException)
/* 1169 */         throw (ListenerNotFoundException)exception; 
/* 1170 */       if (exception instanceof IOException)
/* 1171 */         throw (IOException)exception; 
/* 1172 */       throw newIOException("Got unexpected server exception: " + exception, exception);
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
/*      */   public void removeNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2, MarshalledObject<?> paramMarshalledObject1, MarshalledObject<?> paramMarshalledObject2, Subject paramSubject) throws InstanceNotFoundException, ListenerNotFoundException, IOException {
/* 1187 */     checkNonNull("Target MBean name", paramObjectName1);
/* 1188 */     checkNonNull("Listener MBean name", paramObjectName2);
/*      */ 
/*      */ 
/*      */     
/* 1192 */     boolean bool = logger.debugOn();
/*      */     
/* 1194 */     ClassLoader classLoader = getClassLoaderFor(paramObjectName1);
/*      */     
/* 1196 */     if (bool) logger.debug("removeNotificationListener(ObjectName,ObjectName,NotificationFilter,Object)", "connectionId=" + this.connectionId + " unwrapping filter with target extended ClassLoader.");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1202 */     NotificationFilter notificationFilter = unwrap(paramMarshalledObject1, classLoader, this.defaultClassLoader, NotificationFilter.class, paramSubject);
/*      */     
/* 1204 */     if (bool) logger.debug("removeNotificationListener(ObjectName,ObjectName,NotificationFilter,Object)", "connectionId=" + this.connectionId + " unwrapping handback with target extended ClassLoader.");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1210 */     String str = (String)unwrap(paramMarshalledObject2, classLoader, this.defaultClassLoader, Object.class, paramSubject);
/*      */     
/*      */     try {
/* 1213 */       Object[] arrayOfObject = { paramObjectName1, paramObjectName2, notificationFilter, str };
/*      */ 
/*      */       
/* 1216 */       if (bool) logger.debug("removeNotificationListener(ObjectName,ObjectName,NotificationFilter,Object)", "connectionId=" + this.connectionId + ", name=" + paramObjectName1 + ", listenerName=" + paramObjectName2 + ", filter=" + notificationFilter + ", handback=" + str);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1224 */       doPrivilegedOperation(21, arrayOfObject, paramSubject);
/*      */ 
/*      */     
/*      */     }
/* 1228 */     catch (PrivilegedActionException privilegedActionException) {
/* 1229 */       Exception exception = extractException(privilegedActionException);
/* 1230 */       if (exception instanceof InstanceNotFoundException)
/* 1231 */         throw (InstanceNotFoundException)exception; 
/* 1232 */       if (exception instanceof ListenerNotFoundException)
/* 1233 */         throw (ListenerNotFoundException)exception; 
/* 1234 */       if (exception instanceof IOException)
/* 1235 */         throw (IOException)exception; 
/* 1236 */       throw newIOException("Got unexpected server exception: " + exception, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NotificationResult fetchNotifications(long paramLong1, int paramInt, long paramLong2) throws IOException {
/* 1245 */     if (logger.debugOn()) logger.debug("fetchNotifications", "connectionId=" + this.connectionId + ", timeout=" + paramLong2);
/*      */ 
/*      */ 
/*      */     
/* 1249 */     if (paramInt < 0 || paramLong2 < 0L) {
/* 1250 */       throw new IllegalArgumentException("Illegal negative argument");
/*      */     }
/*      */     
/* 1253 */     boolean bool = this.serverCommunicatorAdmin.reqIncoming();
/*      */     try {
/* 1255 */       if (bool) {
/*      */ 
/*      */ 
/*      */         
/* 1259 */         if (logger.debugOn()) logger.debug("fetchNotifications", "The notification server has been closed, returns null to force the client to stop fetching");
/*      */ 
/*      */         
/* 1262 */         return null;
/*      */       } 
/* 1264 */       final long csn = paramLong1;
/* 1265 */       final int mn = paramInt;
/* 1266 */       final long t = paramLong2;
/* 1267 */       PrivilegedAction<NotificationResult> privilegedAction = new PrivilegedAction<NotificationResult>()
/*      */         {
/*      */           public NotificationResult run() {
/* 1270 */             return RMIConnectionImpl.this.getServerNotifFwd().fetchNotifs(csn, t, mn);
/*      */           }
/*      */         };
/* 1273 */       if (this.acc == null) {
/* 1274 */         return privilegedAction.run();
/*      */       }
/* 1276 */       return AccessController.<NotificationResult>doPrivileged(privilegedAction, this.acc);
/*      */     } finally {
/* 1278 */       this.serverCommunicatorAdmin.rspOutgoing();
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
/*      */   public String toString() {
/* 1293 */     return super.toString() + ": connectionId=" + this.connectionId;
/*      */   }
/*      */ 
/*      */   
/*      */   private class PrivilegedOperation
/*      */     implements PrivilegedExceptionAction<Object>
/*      */   {
/*      */     private int operation;
/*      */     private Object[] params;
/*      */     
/*      */     public PrivilegedOperation(int param1Int, Object[] param1ArrayOfObject) {
/* 1304 */       this.operation = param1Int;
/* 1305 */       this.params = param1ArrayOfObject;
/*      */     }
/*      */     
/*      */     public Object run() throws Exception {
/* 1309 */       return RMIConnectionImpl.this.doOperation(this.operation, this.params);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private class RMIServerCommunicatorAdmin
/*      */     extends ServerCommunicatorAdmin
/*      */   {
/*      */     public RMIServerCommunicatorAdmin(long param1Long) {
/* 1321 */       super(param1Long);
/*      */     }
/*      */     
/*      */     protected void doStop() {
/*      */       try {
/* 1326 */         RMIConnectionImpl.this.close();
/* 1327 */       } catch (IOException iOException) {
/* 1328 */         RMIConnectionImpl.logger.warning("RMIServerCommunicatorAdmin-doStop", "Failed to close: " + iOException);
/*      */         
/* 1330 */         RMIConnectionImpl.logger.debug("RMIServerCommunicatorAdmin-doStop", iOException);
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
/*      */   private ClassLoader getClassLoader(final ObjectName name) throws InstanceNotFoundException {
/*      */     try {
/* 1344 */       return 
/* 1345 */         AccessController.<ClassLoader>doPrivileged(new PrivilegedExceptionAction<ClassLoader>()
/*      */           {
/*      */             public ClassLoader run() throws InstanceNotFoundException {
/* 1348 */               return RMIConnectionImpl.this.mbeanServer.getClassLoader(name);
/*      */             }
/*      */           }, 
/* 1351 */           withPermissions(new Permission[] { new MBeanPermission("*", "getClassLoader") }));
/*      */     }
/* 1353 */     catch (PrivilegedActionException privilegedActionException) {
/* 1354 */       throw (InstanceNotFoundException)extractException(privilegedActionException);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ClassLoader getClassLoaderFor(final ObjectName name) throws InstanceNotFoundException {
/*      */     try {
/* 1361 */       return 
/* 1362 */         AccessController.<ClassLoader>doPrivileged(new PrivilegedExceptionAction()
/*      */           {
/*      */             public Object run() throws InstanceNotFoundException {
/* 1365 */               return RMIConnectionImpl.this.mbeanServer.getClassLoaderFor(name);
/*      */             }
/*      */           }, 
/* 1368 */           withPermissions(new Permission[] { new MBeanPermission("*", "getClassLoaderFor") }));
/*      */     }
/* 1370 */     catch (PrivilegedActionException privilegedActionException) {
/* 1371 */       throw (InstanceNotFoundException)extractException(privilegedActionException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object doPrivilegedOperation(int paramInt, Object[] paramArrayOfObject, Subject paramSubject) throws PrivilegedActionException, IOException {
/* 1380 */     this.serverCommunicatorAdmin.reqIncoming();
/*      */     
/*      */     try {
/*      */       AccessControlContext accessControlContext;
/* 1384 */       if (paramSubject == null) {
/* 1385 */         accessControlContext = this.acc;
/*      */       } else {
/* 1387 */         if (this.subject == null)
/*      */         {
/*      */ 
/*      */           
/* 1391 */           throw new SecurityException("Subject delegation cannot be enabled unless an authenticated subject is put in place");
/*      */         }
/* 1393 */         accessControlContext = this.subjectDelegator.delegatedContext(this.acc, paramSubject, this.removeCallerContext);
/*      */       } 
/*      */ 
/*      */       
/* 1397 */       PrivilegedOperation privilegedOperation = new PrivilegedOperation(paramInt, paramArrayOfObject);
/*      */       
/* 1399 */       if (accessControlContext == null) {
/*      */         try {
/* 1401 */           return privilegedOperation.run();
/* 1402 */         } catch (Exception exception) {
/* 1403 */           if (exception instanceof RuntimeException)
/* 1404 */             throw (RuntimeException)exception; 
/* 1405 */           throw new PrivilegedActionException(exception);
/*      */         } 
/*      */       }
/* 1408 */       return AccessController.doPrivileged(privilegedOperation, accessControlContext);
/*      */     }
/* 1410 */     catch (Error error) {
/* 1411 */       throw new JMXServerErrorException(error.toString(), error);
/*      */     } finally {
/* 1413 */       this.serverCommunicatorAdmin.rspOutgoing();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Object doOperation(int paramInt, Object[] paramArrayOfObject) throws Exception {
/* 1420 */     switch (paramInt) {
/*      */       
/*      */       case 3:
/* 1423 */         return this.mbeanServer.createMBean((String)paramArrayOfObject[0], (ObjectName)paramArrayOfObject[1]);
/*      */ 
/*      */       
/*      */       case 5:
/* 1427 */         return this.mbeanServer.createMBean((String)paramArrayOfObject[0], (ObjectName)paramArrayOfObject[1], (ObjectName)paramArrayOfObject[2]);
/*      */ 
/*      */ 
/*      */       
/*      */       case 4:
/* 1432 */         return this.mbeanServer.createMBean((String)paramArrayOfObject[0], (ObjectName)paramArrayOfObject[1], (Object[])paramArrayOfObject[2], (String[])paramArrayOfObject[3]);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 6:
/* 1438 */         return this.mbeanServer.createMBean((String)paramArrayOfObject[0], (ObjectName)paramArrayOfObject[1], (ObjectName)paramArrayOfObject[2], (Object[])paramArrayOfObject[3], (String[])paramArrayOfObject[4]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 7:
/* 1445 */         return this.mbeanServer.getAttribute((ObjectName)paramArrayOfObject[0], (String)paramArrayOfObject[1]);
/*      */ 
/*      */       
/*      */       case 8:
/* 1449 */         return this.mbeanServer.getAttributes((ObjectName)paramArrayOfObject[0], (String[])paramArrayOfObject[1]);
/*      */ 
/*      */       
/*      */       case 9:
/* 1453 */         return this.mbeanServer.getDefaultDomain();
/*      */       
/*      */       case 10:
/* 1456 */         return this.mbeanServer.getDomains();
/*      */       
/*      */       case 11:
/* 1459 */         return this.mbeanServer.getMBeanCount();
/*      */       
/*      */       case 12:
/* 1462 */         return this.mbeanServer.getMBeanInfo((ObjectName)paramArrayOfObject[0]);
/*      */       
/*      */       case 13:
/* 1465 */         return this.mbeanServer.getObjectInstance((ObjectName)paramArrayOfObject[0]);
/*      */       
/*      */       case 14:
/* 1468 */         return this.mbeanServer.invoke((ObjectName)paramArrayOfObject[0], (String)paramArrayOfObject[1], (Object[])paramArrayOfObject[2], (String[])paramArrayOfObject[3]);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 15:
/* 1474 */         return this.mbeanServer.isInstanceOf((ObjectName)paramArrayOfObject[0], (String)paramArrayOfObject[1]) ? Boolean.TRUE : Boolean.FALSE;
/*      */ 
/*      */ 
/*      */       
/*      */       case 16:
/* 1479 */         return this.mbeanServer.isRegistered((ObjectName)paramArrayOfObject[0]) ? Boolean.TRUE : Boolean.FALSE;
/*      */ 
/*      */       
/*      */       case 17:
/* 1483 */         return this.mbeanServer.queryMBeans((ObjectName)paramArrayOfObject[0], (QueryExp)paramArrayOfObject[1]);
/*      */ 
/*      */       
/*      */       case 18:
/* 1487 */         return this.mbeanServer.queryNames((ObjectName)paramArrayOfObject[0], (QueryExp)paramArrayOfObject[1]);
/*      */ 
/*      */       
/*      */       case 22:
/* 1491 */         this.mbeanServer.setAttribute((ObjectName)paramArrayOfObject[0], (Attribute)paramArrayOfObject[1]);
/*      */         
/* 1493 */         return null;
/*      */       
/*      */       case 23:
/* 1496 */         return this.mbeanServer.setAttributes((ObjectName)paramArrayOfObject[0], (AttributeList)paramArrayOfObject[1]);
/*      */ 
/*      */       
/*      */       case 24:
/* 1500 */         this.mbeanServer.unregisterMBean((ObjectName)paramArrayOfObject[0]);
/* 1501 */         return null;
/*      */       
/*      */       case 1:
/* 1504 */         return getServerNotifFwd().addNotificationListener((ObjectName)paramArrayOfObject[0], (NotificationFilter)paramArrayOfObject[1]);
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1509 */         this.mbeanServer.addNotificationListener((ObjectName)paramArrayOfObject[0], (ObjectName)paramArrayOfObject[1], (NotificationFilter)paramArrayOfObject[2], paramArrayOfObject[3]);
/*      */ 
/*      */ 
/*      */         
/* 1513 */         return null;
/*      */       
/*      */       case 19:
/* 1516 */         getServerNotifFwd().removeNotificationListener((ObjectName)paramArrayOfObject[0], (Integer[])paramArrayOfObject[1]);
/*      */ 
/*      */         
/* 1519 */         return null;
/*      */       
/*      */       case 20:
/* 1522 */         this.mbeanServer.removeNotificationListener((ObjectName)paramArrayOfObject[0], (ObjectName)paramArrayOfObject[1]);
/*      */         
/* 1524 */         return null;
/*      */       
/*      */       case 21:
/* 1527 */         this.mbeanServer.removeNotificationListener((ObjectName)paramArrayOfObject[0], (ObjectName)paramArrayOfObject[1], (NotificationFilter)paramArrayOfObject[2], paramArrayOfObject[3]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1532 */         return null;
/*      */     } 
/*      */     
/* 1535 */     throw new IllegalArgumentException("Invalid operation");
/*      */   }
/*      */   
/*      */   private static class SetCcl
/*      */     implements PrivilegedExceptionAction<ClassLoader> {
/*      */     private final ClassLoader classLoader;
/*      */     
/*      */     SetCcl(ClassLoader param1ClassLoader) {
/* 1543 */       this.classLoader = param1ClassLoader;
/*      */     }
/*      */     
/*      */     public ClassLoader run() {
/* 1547 */       Thread thread = Thread.currentThread();
/* 1548 */       ClassLoader classLoader = thread.getContextClassLoader();
/* 1549 */       thread.setContextClassLoader(this.classLoader);
/* 1550 */       return classLoader;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> T unwrap(MarshalledObject<?> paramMarshalledObject, ClassLoader paramClassLoader, Class<T> paramClass, Subject paramSubject) throws IOException {
/* 1559 */     if (paramMarshalledObject == null) {
/* 1560 */       return null;
/*      */     }
/*      */     try {
/* 1563 */       ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new SetCcl(paramClassLoader));
/*      */       try {
/*      */         AccessControlContext accessControlContext;
/* 1566 */         if (paramSubject == null) {
/* 1567 */           accessControlContext = this.acc;
/*      */         } else {
/* 1569 */           if (this.subject == null)
/*      */           {
/*      */ 
/*      */             
/* 1573 */             throw new SecurityException("Subject delegation cannot be enabled unless an authenticated subject is put in place");
/*      */           }
/* 1575 */           accessControlContext = this.subjectDelegator.delegatedContext(this.acc, paramSubject, this.removeCallerContext);
/*      */         } 
/*      */         
/* 1578 */         if (accessControlContext != null) {
/* 1579 */           return (T)AccessController.doPrivileged(() -> paramClass.cast(paramMarshalledObject.get()), accessControlContext);
/*      */         }
/*      */ 
/*      */         
/* 1583 */         return paramClass.cast(paramMarshalledObject.get());
/*      */       } finally {
/*      */         
/* 1586 */         AccessController.doPrivileged(new SetCcl(classLoader));
/*      */       } 
/* 1588 */     } catch (PrivilegedActionException privilegedActionException) {
/* 1589 */       Exception exception = extractException(privilegedActionException);
/* 1590 */       if (exception instanceof IOException) {
/* 1591 */         throw (IOException)exception;
/*      */       }
/* 1593 */       if (exception instanceof ClassNotFoundException) {
/* 1594 */         throw new UnmarshalException(exception.toString(), exception);
/*      */       }
/* 1596 */       logger.warning("unwrap", "Failed to unmarshall object: " + exception);
/* 1597 */       logger.debug("unwrap", exception);
/* 1598 */     } catch (ClassNotFoundException classNotFoundException) {
/* 1599 */       logger.warning("unwrap", "Failed to unmarshall object: " + classNotFoundException);
/* 1600 */       logger.debug("unwrap", classNotFoundException);
/* 1601 */       throw new UnmarshalException(classNotFoundException.toString(), classNotFoundException);
/*      */     } 
/* 1603 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private <T> T unwrap(MarshalledObject<?> paramMarshalledObject, final ClassLoader cl1, final ClassLoader cl2, Class<T> paramClass, Subject paramSubject) throws IOException {
/* 1612 */     if (paramMarshalledObject == null) {
/* 1613 */       return null;
/*      */     }
/*      */     try {
/* 1616 */       ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedExceptionAction<ClassLoader>()
/*      */           {
/*      */             public ClassLoader run() throws Exception {
/* 1619 */               return new RMIConnectionImpl.CombinedClassLoader(Thread.currentThread().getContextClassLoader(), new OrderClassLoaders(cl1, cl2));
/*      */             }
/*      */           });
/*      */ 
/*      */       
/* 1624 */       return unwrap(paramMarshalledObject, classLoader, paramClass, paramSubject);
/* 1625 */     } catch (PrivilegedActionException privilegedActionException) {
/* 1626 */       Exception exception = extractException(privilegedActionException);
/* 1627 */       if (exception instanceof IOException) {
/* 1628 */         throw (IOException)exception;
/*      */       }
/* 1630 */       if (exception instanceof ClassNotFoundException) {
/* 1631 */         throw new UnmarshalException(exception.toString(), exception);
/*      */       }
/* 1633 */       logger.warning("unwrap", "Failed to unmarshall object: " + exception);
/* 1634 */       logger.debug("unwrap", exception);
/*      */       
/* 1636 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static IOException newIOException(String paramString, Throwable paramThrowable) {
/* 1645 */     IOException iOException = new IOException(paramString);
/* 1646 */     return EnvHelp.<IOException>initCause(iOException, paramThrowable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Exception extractException(Exception paramException) {
/* 1654 */     while (paramException instanceof PrivilegedActionException) {
/* 1655 */       paramException = ((PrivilegedActionException)paramException).getException();
/*      */     }
/* 1657 */     return paramException;
/*      */   }
/*      */   
/* 1660 */   private static final Object[] NO_OBJECTS = new Object[0];
/* 1661 */   private static final String[] NO_STRINGS = new String[0]; private final Subject subject;
/*      */   private final SubjectDelegator subjectDelegator;
/*      */   private final boolean removeCallerContext;
/*      */   private final AccessControlContext acc;
/*      */   private final RMIServerImpl rmiServer;
/*      */   private final MBeanServer mbeanServer;
/*      */   private final ClassLoader defaultClassLoader;
/*      */   private final ClassLoader defaultContextClassLoader;
/*      */   private final ClassLoaderWithRepository classLoaderWithRepository;
/*      */   
/*      */   private static Object[] nullIsEmpty(Object[] paramArrayOfObject) {
/* 1672 */     return (paramArrayOfObject == null) ? NO_OBJECTS : paramArrayOfObject;
/*      */   }
/*      */   
/*      */   private static String[] nullIsEmpty(String[] paramArrayOfString) {
/* 1676 */     return (paramArrayOfString == null) ? NO_STRINGS : paramArrayOfString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkNonNull(String paramString, Object paramObject) {
/* 1687 */     if (paramObject == null) {
/* 1688 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException(paramString + " must not be null");
/*      */       
/* 1690 */       throw new RuntimeOperationsException(illegalArgumentException);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean terminated = false;
/*      */ 
/*      */   
/*      */   private final String connectionId;
/*      */ 
/*      */   
/*      */   private final ServerCommunicatorAdmin serverCommunicatorAdmin;
/*      */ 
/*      */   
/*      */   private static final int ADD_NOTIFICATION_LISTENERS = 1;
/*      */ 
/*      */   
/*      */   private static final int ADD_NOTIFICATION_LISTENER_OBJECTNAME = 2;
/*      */ 
/*      */   
/*      */   private static final int CREATE_MBEAN = 3;
/*      */ 
/*      */   
/*      */   private static final int CREATE_MBEAN_PARAMS = 4;
/*      */ 
/*      */   
/*      */   private static final int CREATE_MBEAN_LOADER = 5;
/*      */ 
/*      */   
/*      */   private static final int CREATE_MBEAN_LOADER_PARAMS = 6;
/*      */ 
/*      */   
/*      */   private static final int GET_ATTRIBUTE = 7;
/*      */ 
/*      */   
/*      */   private static final int GET_ATTRIBUTES = 8;
/*      */ 
/*      */   
/*      */   private static final int GET_DEFAULT_DOMAIN = 9;
/*      */ 
/*      */   
/*      */   private static final int GET_DOMAINS = 10;
/*      */ 
/*      */   
/*      */   private static final int GET_MBEAN_COUNT = 11;
/*      */ 
/*      */   
/*      */   private static final int GET_MBEAN_INFO = 12;
/*      */ 
/*      */   
/*      */   private static final int GET_OBJECT_INSTANCE = 13;
/*      */ 
/*      */   
/*      */   private static final int INVOKE = 14;
/*      */ 
/*      */   
/*      */   private static final int IS_INSTANCE_OF = 15;
/*      */ 
/*      */   
/*      */   private static final int IS_REGISTERED = 16;
/*      */ 
/*      */   
/*      */   private static final int QUERY_MBEANS = 17;
/*      */ 
/*      */   
/*      */   private static final int QUERY_NAMES = 18;
/*      */ 
/*      */   
/*      */   private static final int REMOVE_NOTIFICATION_LISTENER = 19;
/*      */ 
/*      */   
/*      */   private static final int REMOVE_NOTIFICATION_LISTENER_OBJECTNAME = 20;
/*      */ 
/*      */   
/*      */   private static final int REMOVE_NOTIFICATION_LISTENER_OBJECTNAME_FILTER_HANDBACK = 21;
/*      */ 
/*      */   
/*      */   private static final int SET_ATTRIBUTE = 22;
/*      */ 
/*      */   
/*      */   private static final int SET_ATTRIBUTES = 23;
/*      */ 
/*      */   
/*      */   private static final int UNREGISTER_MBEAN = 24;
/*      */ 
/*      */   
/*      */   private ServerNotifForwarder serverNotifForwarder;
/*      */ 
/*      */   
/*      */   private Map<String, ?> env;
/*      */ 
/*      */   
/*      */   private static String objects(Object[] paramArrayOfObject) {
/* 1784 */     if (paramArrayOfObject == null) {
/* 1785 */       return "null";
/*      */     }
/* 1787 */     return Arrays.<Object>asList(paramArrayOfObject).toString();
/*      */   }
/*      */   
/*      */   private static String strings(String[] paramArrayOfString) {
/* 1791 */     return objects((Object[])paramArrayOfString);
/*      */   }
/*      */   
/* 1794 */   private static final ClassLogger logger = new ClassLogger("javax.management.remote.rmi", "RMIConnectionImpl");
/*      */   
/*      */   private static final class CombinedClassLoader extends ClassLoader {
/*      */     final ClassLoaderWrapper defaultCL;
/*      */     
/*      */     private static final class ClassLoaderWrapper extends ClassLoader {
/*      */       ClassLoaderWrapper(ClassLoader param2ClassLoader) {
/* 1801 */         super(param2ClassLoader);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       protected Class<?> loadClass(String param2String, boolean param2Boolean) throws ClassNotFoundException {
/* 1807 */         return super.loadClass(param2String, param2Boolean);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private CombinedClassLoader(ClassLoader param1ClassLoader1, ClassLoader param1ClassLoader2) {
/* 1814 */       super(param1ClassLoader1);
/* 1815 */       this.defaultCL = new ClassLoaderWrapper(param1ClassLoader2);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     protected Class<?> loadClass(String param1String, boolean param1Boolean) throws ClassNotFoundException {
/* 1821 */       ReflectUtil.checkPackageAccess(param1String);
/*      */       try {
/* 1823 */         super.loadClass(param1String, param1Boolean);
/* 1824 */       } catch (Exception exception1) {
/* 1825 */         for (Exception exception2 = exception1; exception2 != null; throwable = exception2.getCause()) {
/* 1826 */           Throwable throwable; if (exception2 instanceof SecurityException) {
/* 1827 */             throw (exception2 == exception1) ? (SecurityException)exception2 : new SecurityException(exception2.getMessage(), exception1);
/*      */           }
/*      */         } 
/*      */       } 
/* 1831 */       return this.defaultCL.loadClass(param1String, param1Boolean);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/remote/rmi/RMIConnectionImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */