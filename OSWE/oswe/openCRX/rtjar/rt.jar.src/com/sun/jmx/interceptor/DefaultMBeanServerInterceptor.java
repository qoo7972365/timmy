/*      */ package com.sun.jmx.interceptor;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.mbeanserver.DynamicMBean2;
/*      */ import com.sun.jmx.mbeanserver.Introspector;
/*      */ import com.sun.jmx.mbeanserver.MBeanInstantiator;
/*      */ import com.sun.jmx.mbeanserver.ModifiableClassLoaderRepository;
/*      */ import com.sun.jmx.mbeanserver.NamedObject;
/*      */ import com.sun.jmx.mbeanserver.Repository;
/*      */ import com.sun.jmx.mbeanserver.Util;
/*      */ import com.sun.jmx.remote.util.EnvHelp;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.ProtectionDomain;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashSet;
/*      */ import java.util.Set;
/*      */ import java.util.WeakHashMap;
/*      */ import java.util.logging.Level;
/*      */ import javax.management.Attribute;
/*      */ import javax.management.AttributeList;
/*      */ import javax.management.AttributeNotFoundException;
/*      */ import javax.management.DynamicMBean;
/*      */ import javax.management.InstanceAlreadyExistsException;
/*      */ import javax.management.InstanceNotFoundException;
/*      */ import javax.management.IntrospectionException;
/*      */ import javax.management.InvalidAttributeValueException;
/*      */ import javax.management.JMRuntimeException;
/*      */ import javax.management.ListenerNotFoundException;
/*      */ import javax.management.MBeanException;
/*      */ import javax.management.MBeanInfo;
/*      */ import javax.management.MBeanPermission;
/*      */ import javax.management.MBeanRegistration;
/*      */ import javax.management.MBeanRegistrationException;
/*      */ import javax.management.MBeanServer;
/*      */ import javax.management.MBeanServerDelegate;
/*      */ import javax.management.MBeanServerNotification;
/*      */ import javax.management.MBeanTrustPermission;
/*      */ import javax.management.NotCompliantMBeanException;
/*      */ import javax.management.Notification;
/*      */ import javax.management.NotificationBroadcaster;
/*      */ import javax.management.NotificationEmitter;
/*      */ import javax.management.NotificationFilter;
/*      */ import javax.management.NotificationListener;
/*      */ import javax.management.ObjectInstance;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.OperationsException;
/*      */ import javax.management.QueryEval;
/*      */ import javax.management.QueryExp;
/*      */ import javax.management.ReflectionException;
/*      */ import javax.management.RuntimeErrorException;
/*      */ import javax.management.RuntimeMBeanException;
/*      */ import javax.management.RuntimeOperationsException;
/*      */ import javax.management.loading.ClassLoaderRepository;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DefaultMBeanServerInterceptor
/*      */   implements MBeanServerInterceptor
/*      */ {
/*      */   private final transient MBeanInstantiator instantiator;
/*  121 */   private transient MBeanServer server = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient MBeanServerDelegate delegate;
/*      */ 
/*      */ 
/*      */   
/*      */   private final transient Repository repository;
/*      */ 
/*      */   
/*  132 */   private final transient WeakHashMap<ListenerWrapper, WeakReference<ListenerWrapper>> listenerWrappers = new WeakHashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String domain;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<ObjectName> beingUnregistered;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
/*  185 */     return createMBean(paramString, paramObjectName, (Object[])null, (String[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName1, ObjectName paramObjectName2) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
/*  195 */     return createMBean(paramString, paramObjectName1, paramObjectName2, (Object[])null, (String[])null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
/*      */     try {
/*  206 */       return createMBean(paramString, paramObjectName, null, true, paramArrayOfObject, paramArrayOfString);
/*      */     }
/*  208 */     catch (InstanceNotFoundException instanceNotFoundException) {
/*      */ 
/*      */       
/*  211 */       throw (IllegalArgumentException)EnvHelp.initCause(new IllegalArgumentException("Unexpected exception: " + instanceNotFoundException), instanceNotFoundException);
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
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName1, ObjectName paramObjectName2, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
/*  223 */     return createMBean(paramString, paramObjectName1, paramObjectName2, false, paramArrayOfObject, paramArrayOfString);
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
/*      */   private ObjectInstance createMBean(String paramString, ObjectName paramObjectName1, ObjectName paramObjectName2, boolean paramBoolean, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
/*      */     Class<?> clazz;
/*  237 */     if (paramString == null) {
/*  238 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("The class name cannot be null");
/*      */       
/*  240 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred during MBean creation");
/*      */     } 
/*      */ 
/*      */     
/*  244 */     if (paramObjectName1 != null) {
/*  245 */       if (paramObjectName1.isPattern()) {
/*      */ 
/*      */         
/*  248 */         IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Invalid name->" + paramObjectName1.toString());
/*      */         
/*  250 */         throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred during MBean creation");
/*      */       } 
/*      */       
/*  253 */       paramObjectName1 = nonDefaultDomain(paramObjectName1);
/*      */     } 
/*      */     
/*  256 */     checkMBeanPermission(paramString, (String)null, (ObjectName)null, "instantiate");
/*  257 */     checkMBeanPermission(paramString, (String)null, paramObjectName1, "registerMBean");
/*      */ 
/*      */     
/*  260 */     if (paramBoolean) {
/*  261 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/*  262 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/*  263 */             .getName(), "createMBean", "ClassName = " + paramString + ", ObjectName = " + paramObjectName1);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  268 */       clazz = this.instantiator.findClassWithDefaultLoaderRepository(paramString);
/*  269 */     } else if (paramObjectName2 == null) {
/*  270 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/*  271 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/*  272 */             .getName(), "createMBean", "ClassName = " + paramString + ", ObjectName = " + paramObjectName1 + ", Loader name = null");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  277 */       clazz = this.instantiator.findClass(paramString, this.server
/*  278 */           .getClass().getClassLoader());
/*      */     } else {
/*  280 */       paramObjectName2 = nonDefaultDomain(paramObjectName2);
/*      */       
/*  282 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/*  283 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/*  284 */             .getName(), "createMBean", "ClassName = " + paramString + ", ObjectName = " + paramObjectName1 + ", Loader name = " + paramObjectName2);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  290 */       clazz = this.instantiator.findClass(paramString, paramObjectName2);
/*      */     } 
/*      */     
/*  293 */     checkMBeanTrustPermission(clazz);
/*      */ 
/*      */     
/*  296 */     Introspector.testCreation(clazz);
/*      */ 
/*      */     
/*  299 */     Introspector.checkCompliance(clazz);
/*      */     
/*  301 */     Object object = this.instantiator.instantiate(clazz, paramArrayOfObject, paramArrayOfString, this.server
/*  302 */         .getClass().getClassLoader());
/*      */     
/*  304 */     String str = getNewMBeanClassName(object);
/*      */     
/*  306 */     return registerObject(str, object, paramObjectName1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectInstance registerMBean(Object paramObject, ObjectName paramObjectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/*  315 */     Class<?> clazz = paramObject.getClass();
/*      */     
/*  317 */     Introspector.checkCompliance(clazz);
/*      */     
/*  319 */     String str = getNewMBeanClassName(paramObject);
/*      */     
/*  321 */     checkMBeanPermission(str, (String)null, paramObjectName, "registerMBean");
/*  322 */     checkMBeanTrustPermission(clazz);
/*      */     
/*  324 */     return registerObject(str, paramObject, paramObjectName);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getNewMBeanClassName(Object paramObject) throws NotCompliantMBeanException {
/*  329 */     if (paramObject instanceof DynamicMBean) {
/*  330 */       String str; DynamicMBean dynamicMBean = (DynamicMBean)paramObject;
/*      */       
/*      */       try {
/*  333 */         str = dynamicMBean.getMBeanInfo().getClassName();
/*  334 */       } catch (Exception exception) {
/*      */         
/*  336 */         NotCompliantMBeanException notCompliantMBeanException = new NotCompliantMBeanException("Bad getMBeanInfo()");
/*      */         
/*  338 */         notCompliantMBeanException.initCause(exception);
/*  339 */         throw notCompliantMBeanException;
/*      */       } 
/*  341 */       if (str == null)
/*      */       {
/*  343 */         throw new NotCompliantMBeanException("MBeanInfo has null class name");
/*      */       }
/*  345 */       return str;
/*      */     } 
/*  347 */     return paramObject.getClass().getName();
/*      */   }
/*      */   
/*  350 */   public DefaultMBeanServerInterceptor(MBeanServer paramMBeanServer, MBeanServerDelegate paramMBeanServerDelegate, MBeanInstantiator paramMBeanInstantiator, Repository paramRepository) { this.beingUnregistered = new HashSet<>(); if (paramMBeanServer == null)
/*      */       throw new IllegalArgumentException("outer MBeanServer cannot be null");  if (paramMBeanServerDelegate == null)
/*      */       throw new IllegalArgumentException("MBeanServerDelegate cannot be null");  if (paramMBeanInstantiator == null)
/*      */       throw new IllegalArgumentException("MBeanInstantiator cannot be null");  if (paramRepository == null)
/*      */       throw new IllegalArgumentException("Repository cannot be null");  this.server = paramMBeanServer; this.delegate = paramMBeanServerDelegate; this.instantiator = paramMBeanInstantiator;
/*      */     this.repository = paramRepository;
/*  356 */     this.domain = paramRepository.getDefaultDomain(); } public void unregisterMBean(ObjectName paramObjectName) throws InstanceNotFoundException, MBeanRegistrationException { if (paramObjectName == null) {
/*  357 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Object name cannot be null");
/*      */       
/*  359 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to unregister the MBean");
/*      */     } 
/*      */ 
/*      */     
/*  363 */     paramObjectName = nonDefaultDomain(paramObjectName);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  401 */     synchronized (this.beingUnregistered) {
/*  402 */       while (this.beingUnregistered.contains(paramObjectName)) {
/*      */         try {
/*  404 */           this.beingUnregistered.wait();
/*  405 */         } catch (InterruptedException interruptedException) {
/*  406 */           throw new MBeanRegistrationException(interruptedException, interruptedException.toString());
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  411 */       this.beingUnregistered.add(paramObjectName);
/*      */     } 
/*      */     
/*      */     try {
/*  415 */       exclusiveUnregisterMBean(paramObjectName);
/*      */     } finally {
/*  417 */       synchronized (this.beingUnregistered) {
/*  418 */         this.beingUnregistered.remove(paramObjectName);
/*  419 */         this.beingUnregistered.notifyAll();
/*      */       } 
/*      */     }  }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void exclusiveUnregisterMBean(ObjectName paramObjectName) throws InstanceNotFoundException, MBeanRegistrationException {
/*  427 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/*      */ 
/*      */     
/*  430 */     checkMBeanPermission(dynamicMBean, (String)null, paramObjectName, "unregisterMBean");
/*      */     
/*  432 */     if (dynamicMBean instanceof MBeanRegistration) {
/*  433 */       preDeregisterInvoke((MBeanRegistration)dynamicMBean);
/*      */     }
/*  435 */     Object object = getResource(dynamicMBean);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  448 */     ResourceContext resourceContext = unregisterFromRepository(object, dynamicMBean, paramObjectName);
/*      */     
/*      */     try {
/*  451 */       if (dynamicMBean instanceof MBeanRegistration)
/*  452 */         postDeregisterInvoke(paramObjectName, (MBeanRegistration)dynamicMBean); 
/*      */     } finally {
/*  454 */       resourceContext.done();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectInstance getObjectInstance(ObjectName paramObjectName) throws InstanceNotFoundException {
/*  461 */     paramObjectName = nonDefaultDomain(paramObjectName);
/*  462 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/*      */     
/*  464 */     checkMBeanPermission(dynamicMBean, (String)null, paramObjectName, "getObjectInstance");
/*      */     
/*  466 */     String str = getClassName(dynamicMBean);
/*      */     
/*  468 */     return new ObjectInstance(paramObjectName, str);
/*      */   }
/*      */   
/*      */   public Set<ObjectInstance> queryMBeans(ObjectName paramObjectName, QueryExp paramQueryExp) {
/*  472 */     SecurityManager securityManager = System.getSecurityManager();
/*  473 */     if (securityManager != null) {
/*      */ 
/*      */       
/*  476 */       checkMBeanPermission((String)null, (String)null, (ObjectName)null, "queryMBeans");
/*      */ 
/*      */ 
/*      */       
/*  480 */       Set<ObjectInstance> set = queryMBeansImpl(paramObjectName, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  486 */       HashSet<ObjectInstance> hashSet = new HashSet(set.size());
/*  487 */       for (ObjectInstance objectInstance : set) {
/*      */         try {
/*  489 */           checkMBeanPermission(objectInstance.getClassName(), (String)null, objectInstance
/*  490 */               .getObjectName(), "queryMBeans");
/*  491 */           hashSet.add(objectInstance);
/*  492 */         } catch (SecurityException securityException) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  499 */       return filterListOfObjectInstances(hashSet, paramQueryExp);
/*      */     } 
/*      */ 
/*      */     
/*  503 */     return queryMBeansImpl(paramObjectName, paramQueryExp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<ObjectInstance> queryMBeansImpl(ObjectName paramObjectName, QueryExp paramQueryExp) {
/*  511 */     Set<NamedObject> set = this.repository.query(paramObjectName, paramQueryExp);
/*      */     
/*  513 */     return objectInstancesFromFilteredNamedObjects(set, paramQueryExp);
/*      */   }
/*      */   
/*      */   public Set<ObjectName> queryNames(ObjectName paramObjectName, QueryExp paramQueryExp) {
/*      */     Set<ObjectName> set;
/*  518 */     SecurityManager securityManager = System.getSecurityManager();
/*  519 */     if (securityManager != null) {
/*      */ 
/*      */       
/*  522 */       checkMBeanPermission((String)null, (String)null, (ObjectName)null, "queryNames");
/*      */ 
/*      */ 
/*      */       
/*  526 */       Set<ObjectInstance> set1 = queryMBeansImpl(paramObjectName, null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  532 */       HashSet<ObjectInstance> hashSet = new HashSet(set1.size());
/*  533 */       for (ObjectInstance objectInstance : set1) {
/*      */         try {
/*  535 */           checkMBeanPermission(objectInstance.getClassName(), (String)null, objectInstance
/*  536 */               .getObjectName(), "queryNames");
/*  537 */           hashSet.add(objectInstance);
/*  538 */         } catch (SecurityException securityException) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  546 */       Set<ObjectInstance> set2 = filterListOfObjectInstances(hashSet, paramQueryExp);
/*  547 */       set = new HashSet(set2.size());
/*  548 */       for (ObjectInstance objectInstance : set2) {
/*  549 */         set.add(objectInstance.getObjectName());
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  554 */       set = queryNamesImpl(paramObjectName, paramQueryExp);
/*      */     } 
/*  556 */     return set;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<ObjectName> queryNamesImpl(ObjectName paramObjectName, QueryExp paramQueryExp) {
/*  562 */     Set<NamedObject> set = this.repository.query(paramObjectName, paramQueryExp);
/*      */     
/*  564 */     return objectNamesFromFilteredNamedObjects(set, paramQueryExp);
/*      */   }
/*      */   
/*      */   public boolean isRegistered(ObjectName paramObjectName) {
/*  568 */     if (paramObjectName == null) {
/*  569 */       throw new RuntimeOperationsException(new IllegalArgumentException("Object name cannot be null"), "Object name cannot be null");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  574 */     paramObjectName = nonDefaultDomain(paramObjectName);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  579 */     return this.repository.contains(paramObjectName);
/*      */   }
/*      */   
/*      */   public String[] getDomains() {
/*  583 */     SecurityManager securityManager = System.getSecurityManager();
/*  584 */     if (securityManager != null) {
/*      */ 
/*      */       
/*  587 */       checkMBeanPermission((String)null, (String)null, (ObjectName)null, "getDomains");
/*      */ 
/*      */ 
/*      */       
/*  591 */       String[] arrayOfString = this.repository.getDomains();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  596 */       ArrayList<String> arrayList = new ArrayList(arrayOfString.length);
/*  597 */       for (byte b = 0; b < arrayOfString.length; b++) {
/*      */         try {
/*  599 */           ObjectName objectName = Util.newObjectName(arrayOfString[b] + ":x=x");
/*  600 */           checkMBeanPermission((String)null, (String)null, objectName, "getDomains");
/*  601 */           arrayList.add(arrayOfString[b]);
/*  602 */         } catch (SecurityException securityException) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  609 */       return arrayList.<String>toArray(new String[arrayList.size()]);
/*      */     } 
/*  611 */     return this.repository.getDomains();
/*      */   }
/*      */ 
/*      */   
/*      */   public Integer getMBeanCount() {
/*  616 */     return this.repository.getCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getAttribute(ObjectName paramObjectName, String paramString) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
/*  623 */     if (paramObjectName == null) {
/*  624 */       throw new RuntimeOperationsException(new IllegalArgumentException("Object name cannot be null"), "Exception occurred trying to invoke the getter on the MBean");
/*      */     }
/*      */ 
/*      */     
/*  628 */     if (paramString == null) {
/*  629 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), "Exception occurred trying to invoke the getter on the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  634 */     paramObjectName = nonDefaultDomain(paramObjectName);
/*      */     
/*  636 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/*  637 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/*  638 */           .getName(), "getAttribute", "Attribute = " + paramString + ", ObjectName = " + paramObjectName);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  643 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/*  644 */     checkMBeanPermission(dynamicMBean, paramString, paramObjectName, "getAttribute");
/*      */     
/*      */     try {
/*  647 */       return dynamicMBean.getAttribute(paramString);
/*  648 */     } catch (AttributeNotFoundException attributeNotFoundException) {
/*  649 */       throw attributeNotFoundException;
/*  650 */     } catch (Throwable throwable) {
/*  651 */       rethrowMaybeMBeanException(throwable);
/*  652 */       throw new AssertionError();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public AttributeList getAttributes(ObjectName paramObjectName, String[] paramArrayOfString) throws InstanceNotFoundException, ReflectionException {
/*      */     String[] arrayOfString;
/*  659 */     if (paramObjectName == null) {
/*  660 */       throw new RuntimeOperationsException(new IllegalArgumentException("ObjectName name cannot be null"), "Exception occurred trying to invoke the getter on the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  665 */     if (paramArrayOfString == null) {
/*  666 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attributes cannot be null"), "Exception occurred trying to invoke the getter on the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  671 */     paramObjectName = nonDefaultDomain(paramObjectName);
/*      */     
/*  673 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/*  674 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/*  675 */           .getName(), "getAttributes", "ObjectName = " + paramObjectName);
/*      */     }
/*      */ 
/*      */     
/*  679 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/*      */     
/*  681 */     SecurityManager securityManager = System.getSecurityManager();
/*  682 */     if (securityManager == null) {
/*  683 */       arrayOfString = paramArrayOfString;
/*      */     } else {
/*  685 */       String str = getClassName(dynamicMBean);
/*      */ 
/*      */ 
/*      */       
/*  689 */       checkMBeanPermission(str, (String)null, paramObjectName, "getAttribute");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  694 */       ArrayList<String> arrayList = new ArrayList(paramArrayOfString.length);
/*      */       
/*  696 */       for (String str1 : paramArrayOfString) {
/*      */         try {
/*  698 */           checkMBeanPermission(str, str1, paramObjectName, "getAttribute");
/*  699 */           arrayList.add(str1);
/*  700 */         } catch (SecurityException securityException) {}
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  705 */       arrayOfString = arrayList.<String>toArray(new String[arrayList.size()]);
/*      */     } 
/*      */     
/*      */     try {
/*  709 */       return dynamicMBean.getAttributes(arrayOfString);
/*  710 */     } catch (Throwable throwable) {
/*  711 */       rethrow(throwable);
/*  712 */       throw new AssertionError();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttribute(ObjectName paramObjectName, Attribute paramAttribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
/*  721 */     if (paramObjectName == null) {
/*  722 */       throw new RuntimeOperationsException(new IllegalArgumentException("ObjectName name cannot be null"), "Exception occurred trying to invoke the setter on the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  727 */     if (paramAttribute == null) {
/*  728 */       throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), "Exception occurred trying to invoke the setter on the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  733 */     paramObjectName = nonDefaultDomain(paramObjectName);
/*      */     
/*  735 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/*  736 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/*  737 */           .getName(), "setAttribute", "ObjectName = " + paramObjectName + ", Attribute = " + paramAttribute
/*      */           
/*  739 */           .getName());
/*      */     }
/*      */     
/*  742 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/*  743 */     checkMBeanPermission(dynamicMBean, paramAttribute.getName(), paramObjectName, "setAttribute");
/*      */     
/*      */     try {
/*  746 */       dynamicMBean.setAttribute(paramAttribute);
/*  747 */     } catch (AttributeNotFoundException attributeNotFoundException) {
/*  748 */       throw attributeNotFoundException;
/*  749 */     } catch (InvalidAttributeValueException invalidAttributeValueException) {
/*  750 */       throw invalidAttributeValueException;
/*  751 */     } catch (Throwable throwable) {
/*  752 */       rethrowMaybeMBeanException(throwable);
/*  753 */       throw new AssertionError();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AttributeList setAttributes(ObjectName paramObjectName, AttributeList paramAttributeList) throws InstanceNotFoundException, ReflectionException {
/*      */     AttributeList attributeList;
/*  761 */     if (paramObjectName == null) {
/*  762 */       throw new RuntimeOperationsException(new IllegalArgumentException("ObjectName name cannot be null"), "Exception occurred trying to invoke the setter on the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  767 */     if (paramAttributeList == null) {
/*  768 */       throw new RuntimeOperationsException(new IllegalArgumentException("AttributeList  cannot be null"), "Exception occurred trying to invoke the setter on the MBean");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  773 */     paramObjectName = nonDefaultDomain(paramObjectName);
/*      */     
/*  775 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/*      */     
/*  777 */     SecurityManager securityManager = System.getSecurityManager();
/*  778 */     if (securityManager == null) {
/*  779 */       attributeList = paramAttributeList;
/*      */     } else {
/*  781 */       String str = getClassName(dynamicMBean);
/*      */ 
/*      */ 
/*      */       
/*  785 */       checkMBeanPermission(str, (String)null, paramObjectName, "setAttribute");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  790 */       attributeList = new AttributeList(paramAttributeList.size());
/*  791 */       for (Attribute attribute : paramAttributeList.asList()) {
/*      */         try {
/*  793 */           checkMBeanPermission(str, attribute.getName(), paramObjectName, "setAttribute");
/*      */           
/*  795 */           attributeList.add(attribute);
/*  796 */         } catch (SecurityException securityException) {}
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  802 */       return dynamicMBean.setAttributes(attributeList);
/*  803 */     } catch (Throwable throwable) {
/*  804 */       rethrow(throwable);
/*  805 */       throw new AssertionError();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object invoke(ObjectName paramObjectName, String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws InstanceNotFoundException, MBeanException, ReflectionException {
/*  814 */     paramObjectName = nonDefaultDomain(paramObjectName);
/*      */     
/*  816 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/*  817 */     checkMBeanPermission(dynamicMBean, paramString, paramObjectName, "invoke");
/*      */     try {
/*  819 */       return dynamicMBean.invoke(paramString, paramArrayOfObject, paramArrayOfString);
/*  820 */     } catch (Throwable throwable) {
/*  821 */       rethrowMaybeMBeanException(throwable);
/*  822 */       throw new AssertionError();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void rethrow(Throwable paramThrowable) throws ReflectionException {
/*      */     try {
/*  831 */       throw paramThrowable;
/*  832 */     } catch (ReflectionException reflectionException) {
/*  833 */       throw reflectionException;
/*  834 */     } catch (RuntimeOperationsException runtimeOperationsException) {
/*  835 */       throw runtimeOperationsException;
/*  836 */     } catch (RuntimeErrorException runtimeErrorException) {
/*  837 */       throw runtimeErrorException;
/*  838 */     } catch (RuntimeException runtimeException) {
/*  839 */       throw new RuntimeMBeanException(runtimeException, runtimeException.toString());
/*  840 */     } catch (Error error) {
/*  841 */       throw new RuntimeErrorException(error, error.toString());
/*  842 */     } catch (Throwable throwable) {
/*      */       
/*  844 */       throw new RuntimeException("Unexpected exception", throwable);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void rethrowMaybeMBeanException(Throwable paramThrowable) throws ReflectionException, MBeanException {
/*  850 */     if (paramThrowable instanceof MBeanException)
/*  851 */       throw (MBeanException)paramThrowable; 
/*  852 */     rethrow(paramThrowable);
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
/*      */   private ObjectInstance registerObject(String paramString, Object paramObject, ObjectName paramObjectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/*  891 */     if (paramObject == null) {
/*  892 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Cannot add null object");
/*      */       
/*  894 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to register the MBean");
/*      */     } 
/*      */ 
/*      */     
/*  898 */     DynamicMBean dynamicMBean = Introspector.makeDynamicMBean(paramObject);
/*      */     
/*  900 */     return registerDynamicMBean(paramString, dynamicMBean, paramObjectName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ObjectInstance registerDynamicMBean(String paramString, DynamicMBean paramDynamicMBean, ObjectName paramObjectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/*  911 */     paramObjectName = nonDefaultDomain(paramObjectName);
/*      */     
/*  913 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/*  914 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/*  915 */           .getName(), "registerMBean", "ObjectName = " + paramObjectName);
/*      */     }
/*      */ 
/*      */     
/*  919 */     ObjectName objectName = preRegister(paramDynamicMBean, this.server, paramObjectName);
/*      */ 
/*      */ 
/*      */     
/*  923 */     boolean bool1 = false;
/*  924 */     boolean bool2 = false;
/*  925 */     ResourceContext resourceContext = null;
/*      */     
/*      */     try {
/*  928 */       if (paramDynamicMBean instanceof DynamicMBean2) {
/*      */         try {
/*  930 */           ((DynamicMBean2)paramDynamicMBean).preRegister2(this.server, objectName);
/*  931 */           bool2 = true;
/*  932 */         } catch (Exception exception) {
/*  933 */           if (exception instanceof RuntimeException)
/*  934 */             throw (RuntimeException)exception; 
/*  935 */           if (exception instanceof InstanceAlreadyExistsException)
/*  936 */             throw (InstanceAlreadyExistsException)exception; 
/*  937 */           throw new RuntimeException(exception);
/*      */         } 
/*      */       }
/*      */       
/*  941 */       if (objectName != paramObjectName && objectName != null)
/*      */       {
/*  943 */         objectName = ObjectName.getInstance(nonDefaultDomain(objectName));
/*      */       }
/*      */       
/*  946 */       checkMBeanPermission(paramString, (String)null, objectName, "registerMBean");
/*      */       
/*  948 */       if (objectName == null) {
/*  949 */         IllegalArgumentException illegalArgumentException = new IllegalArgumentException("No object name specified");
/*      */         
/*  951 */         throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to register the MBean");
/*      */       } 
/*      */ 
/*      */       
/*  955 */       Object object = getResource(paramDynamicMBean);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  966 */       resourceContext = registerWithRepository(object, paramDynamicMBean, objectName);
/*      */ 
/*      */       
/*  969 */       bool2 = false;
/*  970 */       bool1 = true;
/*      */     } finally {
/*      */       
/*      */       try {
/*  974 */         postRegister(objectName, paramDynamicMBean, bool1, bool2);
/*      */       } finally {
/*  976 */         if (bool1 && resourceContext != null) resourceContext.done(); 
/*      */       } 
/*      */     } 
/*  979 */     return new ObjectInstance(objectName, paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void throwMBeanRegistrationException(Throwable paramThrowable, String paramString) throws MBeanRegistrationException {
/*  984 */     if (paramThrowable instanceof RuntimeException) {
/*  985 */       throw new RuntimeMBeanException((RuntimeException)paramThrowable, "RuntimeException thrown " + paramString);
/*      */     }
/*  987 */     if (paramThrowable instanceof Error) {
/*  988 */       throw new RuntimeErrorException((Error)paramThrowable, "Error thrown " + paramString);
/*      */     }
/*  990 */     if (paramThrowable instanceof MBeanRegistrationException)
/*  991 */       throw (MBeanRegistrationException)paramThrowable; 
/*  992 */     if (paramThrowable instanceof Exception) {
/*  993 */       throw new MBeanRegistrationException((Exception)paramThrowable, "Exception thrown " + paramString);
/*      */     }
/*      */     
/*  996 */     throw new RuntimeException(paramThrowable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ObjectName preRegister(DynamicMBean paramDynamicMBean, MBeanServer paramMBeanServer, ObjectName paramObjectName) throws InstanceAlreadyExistsException, MBeanRegistrationException {
/* 1003 */     ObjectName objectName = null;
/*      */     
/*      */     try {
/* 1006 */       if (paramDynamicMBean instanceof MBeanRegistration)
/* 1007 */         objectName = ((MBeanRegistration)paramDynamicMBean).preRegister(paramMBeanServer, paramObjectName); 
/* 1008 */     } catch (Throwable throwable) {
/* 1009 */       throwMBeanRegistrationException(throwable, "in preRegister method");
/*      */     } 
/*      */     
/* 1012 */     if (objectName != null) return objectName; 
/* 1013 */     return paramObjectName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void postRegister(ObjectName paramObjectName, DynamicMBean paramDynamicMBean, boolean paramBoolean1, boolean paramBoolean2) {
/* 1020 */     if (paramBoolean2 && paramDynamicMBean instanceof DynamicMBean2)
/* 1021 */       ((DynamicMBean2)paramDynamicMBean).registerFailed(); 
/*      */     try {
/* 1023 */       if (paramDynamicMBean instanceof MBeanRegistration)
/* 1024 */         ((MBeanRegistration)paramDynamicMBean).postRegister(Boolean.valueOf(paramBoolean1)); 
/* 1025 */     } catch (RuntimeException runtimeException) {
/* 1026 */       JmxProperties.MBEANSERVER_LOGGER.fine("While registering MBean [" + paramObjectName + "]: Exception thrown by postRegister: rethrowing <" + runtimeException + ">, but keeping the MBean registered");
/*      */ 
/*      */       
/* 1029 */       throw new RuntimeMBeanException(runtimeException, "RuntimeException thrown in postRegister method: rethrowing <" + runtimeException + ">, but keeping the MBean registered");
/*      */     
/*      */     }
/* 1032 */     catch (Error error) {
/* 1033 */       JmxProperties.MBEANSERVER_LOGGER.fine("While registering MBean [" + paramObjectName + "]: Error thrown by postRegister: rethrowing <" + error + ">, but keeping the MBean registered");
/*      */ 
/*      */       
/* 1036 */       throw new RuntimeErrorException(error, "Error thrown in postRegister method: rethrowing <" + error + ">, but keeping the MBean registered");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void preDeregisterInvoke(MBeanRegistration paramMBeanRegistration) throws MBeanRegistrationException {
/*      */     try {
/* 1045 */       paramMBeanRegistration.preDeregister();
/* 1046 */     } catch (Throwable throwable) {
/* 1047 */       throwMBeanRegistrationException(throwable, "in preDeregister method");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void postDeregisterInvoke(ObjectName paramObjectName, MBeanRegistration paramMBeanRegistration) {
/*      */     try {
/* 1054 */       paramMBeanRegistration.postDeregister();
/* 1055 */     } catch (RuntimeException runtimeException) {
/* 1056 */       JmxProperties.MBEANSERVER_LOGGER.fine("While unregistering MBean [" + paramObjectName + "]: Exception thrown by postDeregister: rethrowing <" + runtimeException + ">, although the MBean is succesfully unregistered");
/*      */ 
/*      */ 
/*      */       
/* 1060 */       throw new RuntimeMBeanException(runtimeException, "RuntimeException thrown in postDeregister method: rethrowing <" + runtimeException + ">, although the MBean is sucessfully unregistered");
/*      */ 
/*      */     
/*      */     }
/* 1064 */     catch (Error error) {
/* 1065 */       JmxProperties.MBEANSERVER_LOGGER.fine("While unregistering MBean [" + paramObjectName + "]: Error thrown by postDeregister: rethrowing <" + error + ">, although the MBean is succesfully unregistered");
/*      */ 
/*      */ 
/*      */       
/* 1069 */       throw new RuntimeErrorException(error, "Error thrown in postDeregister method: rethrowing <" + error + ">, although the MBean is sucessfully unregistered");
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
/*      */   private DynamicMBean getMBean(ObjectName paramObjectName) throws InstanceNotFoundException {
/* 1083 */     if (paramObjectName == null) {
/* 1084 */       throw new RuntimeOperationsException(new IllegalArgumentException("Object name cannot be null"), "Exception occurred trying to get an MBean");
/*      */     }
/*      */ 
/*      */     
/* 1088 */     DynamicMBean dynamicMBean = this.repository.retrieve(paramObjectName);
/* 1089 */     if (dynamicMBean == null) {
/* 1090 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 1091 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/* 1092 */             .getName(), "getMBean", paramObjectName + " : Found no object");
/*      */       }
/*      */       
/* 1095 */       throw new InstanceNotFoundException(paramObjectName.toString());
/*      */     } 
/* 1097 */     return dynamicMBean;
/*      */   }
/*      */   
/*      */   private static Object getResource(DynamicMBean paramDynamicMBean) {
/* 1101 */     if (paramDynamicMBean instanceof DynamicMBean2) {
/* 1102 */       return ((DynamicMBean2)paramDynamicMBean).getResource();
/*      */     }
/* 1104 */     return paramDynamicMBean;
/*      */   }
/*      */   
/*      */   private ObjectName nonDefaultDomain(ObjectName paramObjectName) {
/* 1108 */     if (paramObjectName == null || paramObjectName.getDomain().length() > 0) {
/* 1109 */       return paramObjectName;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1118 */     String str = this.domain + paramObjectName;
/*      */     
/* 1120 */     return Util.newObjectName(str);
/*      */   }
/*      */   
/*      */   public String getDefaultDomain() {
/* 1124 */     return this.domain;
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
/*      */   public void addNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException {
/* 1179 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 1180 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/* 1181 */           .getName(), "addNotificationListener", "ObjectName = " + paramObjectName);
/*      */     }
/*      */ 
/*      */     
/* 1185 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/* 1186 */     checkMBeanPermission(dynamicMBean, (String)null, paramObjectName, "addNotificationListener");
/*      */ 
/*      */     
/* 1189 */     NotificationBroadcaster notificationBroadcaster = (NotificationBroadcaster)getNotificationBroadcaster(paramObjectName, dynamicMBean, (Class)NotificationBroadcaster.class);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1195 */     if (paramNotificationListener == null) {
/* 1196 */       throw new RuntimeOperationsException(new IllegalArgumentException("Null listener"), "Null listener");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1201 */     NotificationListener notificationListener = getListenerWrapper(paramNotificationListener, paramObjectName, dynamicMBean, true);
/* 1202 */     notificationBroadcaster.addNotificationListener(notificationListener, paramNotificationFilter, paramObject);
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
/*      */   public void addNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException {
/* 1217 */     DynamicMBean dynamicMBean = getMBean(paramObjectName2);
/* 1218 */     Object object = getResource(dynamicMBean);
/* 1219 */     if (!(object instanceof NotificationListener)) {
/* 1220 */       throw new RuntimeOperationsException(new IllegalArgumentException(paramObjectName2
/* 1221 */             .getCanonicalName()), "The MBean " + paramObjectName2
/* 1222 */           .getCanonicalName() + "does not implement the NotificationListener interface");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1229 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 1230 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/* 1231 */           .getName(), "addNotificationListener", "ObjectName = " + paramObjectName1 + ", Listener = " + paramObjectName2);
/*      */     }
/*      */ 
/*      */     
/* 1235 */     this.server.addNotificationListener(paramObjectName1, (NotificationListener)object, paramNotificationFilter, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener) throws InstanceNotFoundException, ListenerNotFoundException {
/* 1242 */     removeNotificationListener(paramObjectName, paramNotificationListener, null, null, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException, ListenerNotFoundException {
/* 1250 */     removeNotificationListener(paramObjectName, paramNotificationListener, paramNotificationFilter, paramObject, false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2) throws InstanceNotFoundException, ListenerNotFoundException {
/* 1256 */     NotificationListener notificationListener = getListener(paramObjectName2);
/*      */     
/* 1258 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 1259 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/* 1260 */           .getName(), "removeNotificationListener", "ObjectName = " + paramObjectName1 + ", Listener = " + paramObjectName2);
/*      */     }
/*      */ 
/*      */     
/* 1264 */     this.server.removeNotificationListener(paramObjectName1, notificationListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException, ListenerNotFoundException {
/* 1273 */     NotificationListener notificationListener = getListener(paramObjectName2);
/*      */     
/* 1275 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 1276 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/* 1277 */           .getName(), "removeNotificationListener", "ObjectName = " + paramObjectName1 + ", Listener = " + paramObjectName2);
/*      */     }
/*      */ 
/*      */     
/* 1281 */     this.server.removeNotificationListener(paramObjectName1, notificationListener, paramNotificationFilter, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private NotificationListener getListener(ObjectName paramObjectName) throws ListenerNotFoundException {
/*      */     DynamicMBean dynamicMBean;
/*      */     try {
/* 1291 */       dynamicMBean = getMBean(paramObjectName);
/* 1292 */     } catch (InstanceNotFoundException instanceNotFoundException) {
/* 1293 */       throw (ListenerNotFoundException)EnvHelp.initCause(new ListenerNotFoundException(instanceNotFoundException
/* 1294 */             .getMessage()), instanceNotFoundException);
/*      */     } 
/*      */     
/* 1297 */     Object object = getResource(dynamicMBean);
/* 1298 */     if (!(object instanceof NotificationListener)) {
/*      */       
/* 1300 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException(paramObjectName.getCanonicalName());
/*      */ 
/*      */       
/* 1303 */       String str = "MBean " + paramObjectName.getCanonicalName() + " does not implement " + NotificationListener.class.getName();
/* 1304 */       throw new RuntimeOperationsException(illegalArgumentException, str);
/*      */     } 
/* 1306 */     return (NotificationListener)object;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject, boolean paramBoolean) throws InstanceNotFoundException, ListenerNotFoundException {
/* 1316 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 1317 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/* 1318 */           .getName(), "removeNotificationListener", "ObjectName = " + paramObjectName);
/*      */     }
/*      */ 
/*      */     
/* 1322 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/* 1323 */     checkMBeanPermission(dynamicMBean, (String)null, paramObjectName, "removeNotificationListener");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1330 */     Class clazz = (Class)(paramBoolean ? NotificationBroadcaster.class : NotificationEmitter.class);
/*      */ 
/*      */     
/* 1333 */     NotificationBroadcaster notificationBroadcaster = (NotificationBroadcaster)getNotificationBroadcaster(paramObjectName, dynamicMBean, clazz);
/*      */ 
/*      */     
/* 1336 */     NotificationListener notificationListener = getListenerWrapper(paramNotificationListener, paramObjectName, dynamicMBean, false);
/*      */     
/* 1338 */     if (notificationListener == null) {
/* 1339 */       throw new ListenerNotFoundException("Unknown listener");
/*      */     }
/* 1341 */     if (paramBoolean) {
/* 1342 */       notificationBroadcaster.removeNotificationListener(notificationListener);
/*      */     } else {
/* 1344 */       NotificationEmitter notificationEmitter = (NotificationEmitter)notificationBroadcaster;
/* 1345 */       notificationEmitter.removeNotificationListener(notificationListener, paramNotificationFilter, paramObject);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <T extends NotificationBroadcaster> T getNotificationBroadcaster(ObjectName paramObjectName, Object paramObject, Class<T> paramClass) {
/* 1354 */     if (paramClass.isInstance(paramObject))
/* 1355 */       return paramClass.cast(paramObject); 
/* 1356 */     if (paramObject instanceof DynamicMBean2)
/* 1357 */       paramObject = ((DynamicMBean2)paramObject).getResource(); 
/* 1358 */     if (paramClass.isInstance(paramObject)) {
/* 1359 */       return paramClass.cast(paramObject);
/*      */     }
/* 1361 */     IllegalArgumentException illegalArgumentException = new IllegalArgumentException(paramObjectName.getCanonicalName());
/*      */ 
/*      */     
/* 1364 */     String str = "MBean " + paramObjectName.getCanonicalName() + " does not implement " + paramClass.getName();
/* 1365 */     throw new RuntimeOperationsException(illegalArgumentException, str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MBeanInfo getMBeanInfo(ObjectName paramObjectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException {
/*      */     MBeanInfo mBeanInfo;
/* 1375 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/*      */     
/*      */     try {
/* 1378 */       mBeanInfo = dynamicMBean.getMBeanInfo();
/* 1379 */     } catch (RuntimeMBeanException runtimeMBeanException) {
/* 1380 */       throw runtimeMBeanException;
/* 1381 */     } catch (RuntimeErrorException runtimeErrorException) {
/* 1382 */       throw runtimeErrorException;
/* 1383 */     } catch (RuntimeException runtimeException) {
/* 1384 */       throw new RuntimeMBeanException(runtimeException, "getMBeanInfo threw RuntimeException");
/*      */     }
/* 1386 */     catch (Error error) {
/* 1387 */       throw new RuntimeErrorException(error, "getMBeanInfo threw Error");
/*      */     } 
/* 1389 */     if (mBeanInfo == null) {
/* 1390 */       throw new JMRuntimeException("MBean " + paramObjectName + "has no MBeanInfo");
/*      */     }
/*      */     
/* 1393 */     checkMBeanPermission(mBeanInfo.getClassName(), (String)null, paramObjectName, "getMBeanInfo");
/*      */     
/* 1395 */     return mBeanInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInstanceOf(ObjectName paramObjectName, String paramString) throws InstanceNotFoundException {
/* 1401 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/* 1402 */     checkMBeanPermission(dynamicMBean, (String)null, paramObjectName, "isInstanceOf");
/*      */     
/*      */     try {
/* 1405 */       Object object = getResource(dynamicMBean);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1410 */       String str = (object instanceof DynamicMBean) ? getClassName((DynamicMBean)object) : object.getClass().getName();
/*      */       
/* 1412 */       if (str.equals(paramString))
/* 1413 */         return true; 
/* 1414 */       ClassLoader classLoader = object.getClass().getClassLoader();
/*      */       
/* 1416 */       Class<?> clazz1 = Class.forName(paramString, false, classLoader);
/* 1417 */       if (clazz1.isInstance(object)) {
/* 1418 */         return true;
/*      */       }
/* 1420 */       Class<?> clazz2 = Class.forName(str, false, classLoader);
/* 1421 */       return clazz1.isAssignableFrom(clazz2);
/* 1422 */     } catch (Exception exception) {
/*      */       
/* 1424 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/* 1425 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, DefaultMBeanServerInterceptor.class
/* 1426 */             .getName(), "isInstanceOf", "Exception calling isInstanceOf", exception);
/*      */       }
/*      */       
/* 1429 */       return false;
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
/*      */   public ClassLoader getClassLoaderFor(ObjectName paramObjectName) throws InstanceNotFoundException {
/* 1444 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/* 1445 */     checkMBeanPermission(dynamicMBean, (String)null, paramObjectName, "getClassLoaderFor");
/* 1446 */     return getResource(dynamicMBean).getClass().getClassLoader();
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
/*      */   public ClassLoader getClassLoader(ObjectName paramObjectName) throws InstanceNotFoundException {
/* 1459 */     if (paramObjectName == null) {
/* 1460 */       checkMBeanPermission((String)null, (String)null, (ObjectName)null, "getClassLoader");
/* 1461 */       return this.server.getClass().getClassLoader();
/*      */     } 
/*      */     
/* 1464 */     DynamicMBean dynamicMBean = getMBean(paramObjectName);
/* 1465 */     checkMBeanPermission(dynamicMBean, (String)null, paramObjectName, "getClassLoader");
/*      */     
/* 1467 */     Object object = getResource(dynamicMBean);
/*      */ 
/*      */     
/* 1470 */     if (!(object instanceof ClassLoader)) {
/* 1471 */       throw new InstanceNotFoundException(paramObjectName.toString() + " is not a classloader");
/*      */     }
/*      */     
/* 1474 */     return (ClassLoader)object;
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
/*      */   private void sendNotification(String paramString, ObjectName paramObjectName) {
/* 1489 */     MBeanServerNotification mBeanServerNotification = new MBeanServerNotification(paramString, MBeanServerDelegate.DELEGATE_NAME, 0L, paramObjectName);
/*      */ 
/*      */     
/* 1492 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 1493 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/* 1494 */           .getName(), "sendNotification", paramString + " " + paramObjectName);
/*      */     }
/*      */ 
/*      */     
/* 1498 */     this.delegate.sendNotification(mBeanServerNotification);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<ObjectName> objectNamesFromFilteredNamedObjects(Set<NamedObject> paramSet, QueryExp paramQueryExp) {
/* 1507 */     HashSet<ObjectName> hashSet = new HashSet();
/*      */     
/* 1509 */     if (paramQueryExp == null) {
/* 1510 */       for (NamedObject namedObject : paramSet) {
/* 1511 */         hashSet.add(namedObject.getName());
/*      */       }
/*      */     } else {
/*      */       
/* 1515 */       MBeanServer mBeanServer = QueryEval.getMBeanServer();
/* 1516 */       paramQueryExp.setMBeanServer(this.server);
/*      */       try {
/* 1518 */         for (NamedObject namedObject : paramSet) {
/*      */           boolean bool;
/*      */           try {
/* 1521 */             bool = paramQueryExp.apply(namedObject.getName());
/* 1522 */           } catch (Exception exception) {
/* 1523 */             bool = false;
/*      */           } 
/* 1525 */           if (bool) {
/* 1526 */             hashSet.add(namedObject.getName());
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/* 1539 */         paramQueryExp.setMBeanServer(mBeanServer);
/*      */       } 
/*      */     } 
/* 1542 */     return hashSet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Set<ObjectInstance> objectInstancesFromFilteredNamedObjects(Set<NamedObject> paramSet, QueryExp paramQueryExp) {
/* 1551 */     HashSet<ObjectInstance> hashSet = new HashSet();
/*      */     
/* 1553 */     if (paramQueryExp == null) {
/* 1554 */       for (NamedObject namedObject : paramSet) {
/* 1555 */         DynamicMBean dynamicMBean = namedObject.getObject();
/* 1556 */         String str = safeGetClassName(dynamicMBean);
/* 1557 */         hashSet.add(new ObjectInstance(namedObject.getName(), str));
/*      */       } 
/*      */     } else {
/*      */       
/* 1561 */       MBeanServer mBeanServer = QueryEval.getMBeanServer();
/* 1562 */       paramQueryExp.setMBeanServer(this.server);
/*      */       try {
/* 1564 */         for (NamedObject namedObject : paramSet) {
/* 1565 */           boolean bool; DynamicMBean dynamicMBean = namedObject.getObject();
/*      */           
/*      */           try {
/* 1568 */             bool = paramQueryExp.apply(namedObject.getName());
/* 1569 */           } catch (Exception exception) {
/* 1570 */             bool = false;
/*      */           } 
/* 1572 */           if (bool) {
/* 1573 */             String str = safeGetClassName(dynamicMBean);
/* 1574 */             hashSet.add(new ObjectInstance(namedObject.getName(), str));
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */         
/* 1587 */         paramQueryExp.setMBeanServer(mBeanServer);
/*      */       } 
/*      */     } 
/* 1590 */     return hashSet;
/*      */   }
/*      */   
/*      */   private static String safeGetClassName(DynamicMBean paramDynamicMBean) {
/*      */     try {
/* 1595 */       return getClassName(paramDynamicMBean);
/* 1596 */     } catch (Exception exception) {
/* 1597 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/* 1598 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, DefaultMBeanServerInterceptor.class
/* 1599 */             .getName(), "safeGetClassName", "Exception getting MBean class name", exception);
/*      */       }
/*      */ 
/*      */       
/* 1603 */       return null;
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
/*      */   private Set<ObjectInstance> filterListOfObjectInstances(Set<ObjectInstance> paramSet, QueryExp paramQueryExp) {
/* 1615 */     if (paramQueryExp == null) {
/* 1616 */       return paramSet;
/*      */     }
/* 1618 */     HashSet<ObjectInstance> hashSet = new HashSet();
/*      */ 
/*      */     
/* 1621 */     for (ObjectInstance objectInstance : paramSet) {
/* 1622 */       boolean bool = false;
/* 1623 */       MBeanServer mBeanServer = QueryEval.getMBeanServer();
/* 1624 */       paramQueryExp.setMBeanServer(this.server);
/*      */       try {
/* 1626 */         bool = paramQueryExp.apply(objectInstance.getObjectName());
/* 1627 */       } catch (Exception exception) {
/* 1628 */         bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       finally {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1639 */         paramQueryExp.setMBeanServer(mBeanServer);
/*      */       } 
/* 1641 */       if (bool) {
/* 1642 */         hashSet.add(objectInstance);
/*      */       }
/*      */     } 
/* 1645 */     return hashSet;
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
/*      */   private NotificationListener getListenerWrapper(NotificationListener paramNotificationListener, ObjectName paramObjectName, DynamicMBean paramDynamicMBean, boolean paramBoolean) {
/* 1669 */     Object object = getResource(paramDynamicMBean);
/* 1670 */     ListenerWrapper listenerWrapper = new ListenerWrapper(paramNotificationListener, paramObjectName, object);
/* 1671 */     synchronized (this.listenerWrappers) {
/* 1672 */       WeakReference<NotificationListener> weakReference = (WeakReference)this.listenerWrappers.get(listenerWrapper);
/* 1673 */       if (weakReference != null) {
/* 1674 */         NotificationListener notificationListener = weakReference.get();
/* 1675 */         if (notificationListener != null)
/* 1676 */           return notificationListener; 
/*      */       } 
/* 1678 */       if (paramBoolean) {
/* 1679 */         weakReference = new WeakReference<>(listenerWrapper);
/* 1680 */         this.listenerWrappers.put(listenerWrapper, weakReference);
/* 1681 */         return listenerWrapper;
/*      */       } 
/* 1683 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Object instantiate(String paramString) throws ReflectionException, MBeanException {
/* 1689 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Object instantiate(String paramString, ObjectName paramObjectName) throws ReflectionException, MBeanException, InstanceNotFoundException {
/* 1695 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */   
/*      */   public Object instantiate(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, MBeanException {
/* 1700 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object instantiate(String paramString, ObjectName paramObjectName, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, MBeanException, InstanceNotFoundException {
/* 1707 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectInputStream deserialize(ObjectName paramObjectName, byte[] paramArrayOfbyte) throws InstanceNotFoundException, OperationsException {
/* 1712 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */   
/*      */   public ObjectInputStream deserialize(String paramString, byte[] paramArrayOfbyte) throws OperationsException, ReflectionException {
/* 1717 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ObjectInputStream deserialize(String paramString, ObjectName paramObjectName, byte[] paramArrayOfbyte) throws InstanceNotFoundException, OperationsException, ReflectionException {
/* 1723 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */   
/*      */   public ClassLoaderRepository getClassLoaderRepository() {
/* 1727 */     throw new UnsupportedOperationException("Not supported yet.");
/*      */   }
/*      */   private static class ListenerWrapper implements NotificationListener { private NotificationListener listener; private ObjectName name;
/*      */     private Object mbean;
/*      */     
/*      */     ListenerWrapper(NotificationListener param1NotificationListener, ObjectName param1ObjectName, Object param1Object) {
/* 1733 */       this.listener = param1NotificationListener;
/* 1734 */       this.name = param1ObjectName;
/* 1735 */       this.mbean = param1Object;
/*      */     }
/*      */ 
/*      */     
/*      */     public void handleNotification(Notification param1Notification, Object param1Object) {
/* 1740 */       if (param1Notification != null && 
/* 1741 */         param1Notification.getSource() == this.mbean) {
/* 1742 */         param1Notification.setSource(this.name);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1754 */       this.listener.handleNotification(param1Notification, param1Object);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object param1Object) {
/* 1759 */       if (!(param1Object instanceof ListenerWrapper))
/* 1760 */         return false; 
/* 1761 */       ListenerWrapper listenerWrapper = (ListenerWrapper)param1Object;
/* 1762 */       return (listenerWrapper.listener == this.listener && listenerWrapper.mbean == this.mbean && listenerWrapper.name
/* 1763 */         .equals(this.name));
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
/*      */     public int hashCode() {
/* 1776 */       return System.identityHashCode(this.listener) ^ 
/* 1777 */         System.identityHashCode(this.mbean);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getClassName(DynamicMBean paramDynamicMBean) {
/* 1801 */     if (paramDynamicMBean instanceof DynamicMBean2) {
/* 1802 */       return ((DynamicMBean2)paramDynamicMBean).getClassName();
/*      */     }
/* 1804 */     return paramDynamicMBean.getMBeanInfo().getClassName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkMBeanPermission(DynamicMBean paramDynamicMBean, String paramString1, ObjectName paramObjectName, String paramString2) {
/* 1811 */     SecurityManager securityManager = System.getSecurityManager();
/* 1812 */     if (securityManager != null) {
/* 1813 */       checkMBeanPermission(safeGetClassName(paramDynamicMBean), paramString1, paramObjectName, paramString2);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkMBeanPermission(String paramString1, String paramString2, ObjectName paramObjectName, String paramString3) {
/* 1824 */     SecurityManager securityManager = System.getSecurityManager();
/* 1825 */     if (securityManager != null) {
/* 1826 */       MBeanPermission mBeanPermission = new MBeanPermission(paramString1, paramString2, paramObjectName, paramString3);
/*      */ 
/*      */ 
/*      */       
/* 1830 */       securityManager.checkPermission(mBeanPermission);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkMBeanTrustPermission(final Class<?> theClass) throws SecurityException {
/* 1836 */     SecurityManager securityManager = System.getSecurityManager();
/* 1837 */     if (securityManager != null) {
/* 1838 */       MBeanTrustPermission mBeanTrustPermission = new MBeanTrustPermission("register");
/* 1839 */       PrivilegedAction<ProtectionDomain> privilegedAction = new PrivilegedAction<ProtectionDomain>()
/*      */         {
/*      */           public ProtectionDomain run() {
/* 1842 */             return theClass.getProtectionDomain();
/*      */           }
/*      */         };
/* 1845 */       ProtectionDomain protectionDomain = AccessController.<ProtectionDomain>doPrivileged(privilegedAction);
/* 1846 */       AccessControlContext accessControlContext = new AccessControlContext(new ProtectionDomain[] { protectionDomain });
/*      */       
/* 1848 */       securityManager.checkPermission(mBeanTrustPermission, accessControlContext);
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
/*      */   private static interface ResourceContext
/*      */     extends Repository.RegistrationContext
/*      */   {
/* 1869 */     public static final ResourceContext NONE = new ResourceContext()
/*      */       {
/*      */         public void done() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void registering() {}
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void unregistered() {}
/*      */       };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void done();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ResourceContext registerWithRepository(Object paramObject, DynamicMBean paramDynamicMBean, ObjectName paramObjectName) throws InstanceAlreadyExistsException, MBeanRegistrationException {
/* 1895 */     ResourceContext resourceContext = makeResourceContextFor(paramObject, paramObjectName);
/*      */ 
/*      */     
/* 1898 */     this.repository.addMBean(paramDynamicMBean, paramObjectName, resourceContext);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1904 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 1905 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/* 1906 */           .getName(), "addObject", "Send create notification of object " + paramObjectName
/*      */           
/* 1908 */           .getCanonicalName());
/*      */     }
/*      */     
/* 1911 */     sendNotification("JMX.mbean.registered", paramObjectName);
/*      */ 
/*      */ 
/*      */     
/* 1915 */     return resourceContext;
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
/*      */   private ResourceContext unregisterFromRepository(Object paramObject, DynamicMBean paramDynamicMBean, ObjectName paramObjectName) throws InstanceNotFoundException {
/* 1937 */     ResourceContext resourceContext = makeResourceContextFor(paramObject, paramObjectName);
/*      */ 
/*      */     
/* 1940 */     this.repository.remove(paramObjectName, resourceContext);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1945 */     if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINER)) {
/* 1946 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, DefaultMBeanServerInterceptor.class
/* 1947 */           .getName(), "unregisterMBean", "Send delete notification of object " + paramObjectName
/*      */           
/* 1949 */           .getCanonicalName());
/*      */     }
/*      */     
/* 1952 */     sendNotification("JMX.mbean.unregistered", paramObjectName);
/*      */     
/* 1954 */     return resourceContext;
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
/*      */   private void addClassLoader(ClassLoader paramClassLoader, ObjectName paramObjectName) {
/* 1976 */     ModifiableClassLoaderRepository modifiableClassLoaderRepository = getInstantiatorCLR();
/* 1977 */     if (modifiableClassLoaderRepository == null) {
/* 1978 */       IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Dynamic addition of class loaders is not supported");
/*      */ 
/*      */ 
/*      */       
/* 1982 */       throw new RuntimeOperationsException(illegalArgumentException, "Exception occurred trying to register the MBean as a class loader");
/*      */     } 
/*      */ 
/*      */     
/* 1986 */     modifiableClassLoaderRepository.addClassLoader(paramObjectName, paramClassLoader);
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
/*      */   private void removeClassLoader(ClassLoader paramClassLoader, ObjectName paramObjectName) {
/* 2001 */     if (paramClassLoader != this.server.getClass().getClassLoader()) {
/* 2002 */       ModifiableClassLoaderRepository modifiableClassLoaderRepository = getInstantiatorCLR();
/* 2003 */       if (modifiableClassLoaderRepository != null) {
/* 2004 */         modifiableClassLoaderRepository.removeClassLoader(paramObjectName);
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
/*      */   private ResourceContext createClassLoaderContext(final ClassLoader loader, final ObjectName logicalName) {
/* 2027 */     return new ResourceContext()
/*      */       {
/*      */         public void registering() {
/* 2030 */           DefaultMBeanServerInterceptor.this.addClassLoader(loader, logicalName);
/*      */         }
/*      */         
/*      */         public void unregistered() {
/* 2034 */           DefaultMBeanServerInterceptor.this.removeClassLoader(loader, logicalName);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public void done() {}
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ResourceContext makeResourceContextFor(Object paramObject, ObjectName paramObjectName) {
/* 2054 */     if (paramObject instanceof ClassLoader) {
/* 2055 */       return createClassLoaderContext((ClassLoader)paramObject, paramObjectName);
/*      */     }
/*      */     
/* 2058 */     return ResourceContext.NONE;
/*      */   }
/*      */   
/*      */   private ModifiableClassLoaderRepository getInstantiatorCLR() {
/* 2062 */     return AccessController.<ModifiableClassLoaderRepository>doPrivileged(new PrivilegedAction<ModifiableClassLoaderRepository>()
/*      */         {
/*      */           public ModifiableClassLoaderRepository run() {
/* 2065 */             return (DefaultMBeanServerInterceptor.this.instantiator != null) ? DefaultMBeanServerInterceptor.this.instantiator.getClassLoaderRepository() : null;
/*      */           }
/*      */         });
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/interceptor/DefaultMBeanServerInterceptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */