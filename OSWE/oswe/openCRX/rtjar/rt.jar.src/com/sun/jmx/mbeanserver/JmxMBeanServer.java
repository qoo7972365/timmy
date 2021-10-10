/*      */ package com.sun.jmx.mbeanserver;
/*      */ 
/*      */ import com.sun.jmx.defaults.JmxProperties;
/*      */ import com.sun.jmx.interceptor.DefaultMBeanServerInterceptor;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
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
/*      */ import javax.management.MBeanServerDelegate;
/*      */ import javax.management.MBeanServerPermission;
/*      */ import javax.management.NotCompliantMBeanException;
/*      */ import javax.management.NotificationFilter;
/*      */ import javax.management.NotificationListener;
/*      */ import javax.management.ObjectInstance;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.OperationsException;
/*      */ import javax.management.QueryExp;
/*      */ import javax.management.ReflectionException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class JmxMBeanServer
/*      */   implements SunJmxMBeanServer
/*      */ {
/*      */   public static final boolean DEFAULT_FAIR_LOCK_POLICY = true;
/*      */   private final MBeanInstantiator instantiator;
/*      */   private final SecureClassLoaderRepository secureClr;
/*      */   private final boolean interceptorsEnabled;
/*      */   private final MBeanServer outerShell;
/*  108 */   private volatile MBeanServer mbsInterceptor = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MBeanServerDelegate mBeanServerDelegateObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JmxMBeanServer(String paramString, MBeanServer paramMBeanServer, MBeanServerDelegate paramMBeanServerDelegate) {
/*  139 */     this(paramString, paramMBeanServer, paramMBeanServerDelegate, null, false);
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
/*      */   JmxMBeanServer(String paramString, MBeanServer paramMBeanServer, MBeanServerDelegate paramMBeanServerDelegate, boolean paramBoolean) {
/*  171 */     this(paramString, paramMBeanServer, paramMBeanServerDelegate, null, false);
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
/*      */   JmxMBeanServer(String paramString, MBeanServer paramMBeanServer, MBeanServerDelegate paramMBeanServerDelegate, MBeanInstantiator paramMBeanInstantiator, boolean paramBoolean) {
/*  196 */     this(paramString, paramMBeanServer, paramMBeanServerDelegate, paramMBeanInstantiator, paramBoolean, true);
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
/*      */   JmxMBeanServer(String paramString, MBeanServer paramMBeanServer, MBeanServerDelegate paramMBeanServerDelegate, MBeanInstantiator paramMBeanInstantiator, boolean paramBoolean1, boolean paramBoolean2) {
/*  226 */     if (paramMBeanInstantiator == null) {
/*      */       
/*  228 */       ClassLoaderRepositorySupport classLoaderRepositorySupport = new ClassLoaderRepositorySupport();
/*  229 */       paramMBeanInstantiator = new MBeanInstantiator(classLoaderRepositorySupport);
/*      */     } 
/*      */     
/*  232 */     final MBeanInstantiator fInstantiator = paramMBeanInstantiator;
/*  233 */     this
/*  234 */       .secureClr = new SecureClassLoaderRepository(AccessController.<ClassLoaderRepository>doPrivileged(new PrivilegedAction<ClassLoaderRepository>()
/*      */           {
/*      */             public ClassLoaderRepository run() {
/*  237 */               return fInstantiator.getClassLoaderRepository();
/*      */             }
/*      */           }));
/*      */     
/*  241 */     if (paramMBeanServerDelegate == null)
/*  242 */       paramMBeanServerDelegate = new MBeanServerDelegateImpl(); 
/*  243 */     if (paramMBeanServer == null) {
/*  244 */       paramMBeanServer = this;
/*      */     }
/*  246 */     this.instantiator = paramMBeanInstantiator;
/*  247 */     this.mBeanServerDelegateObject = paramMBeanServerDelegate;
/*  248 */     this.outerShell = paramMBeanServer;
/*      */     
/*  250 */     Repository repository = new Repository(paramString);
/*  251 */     this.mbsInterceptor = new DefaultMBeanServerInterceptor(paramMBeanServer, paramMBeanServerDelegate, paramMBeanInstantiator, repository);
/*      */ 
/*      */     
/*  254 */     this.interceptorsEnabled = paramBoolean1;
/*  255 */     initialize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interceptorsEnabled() {
/*  266 */     return this.interceptorsEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MBeanInstantiator getMBeanInstantiator() {
/*  277 */     if (this.interceptorsEnabled) return this.instantiator; 
/*  278 */     throw new UnsupportedOperationException("MBeanServerInterceptors are disabled.");
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
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
/*  326 */     return this.mbsInterceptor.createMBean(paramString, 
/*  327 */         cloneObjectName(paramObjectName), (Object[])null, (String[])null);
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
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName1, ObjectName paramObjectName2) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
/*  379 */     return this.mbsInterceptor.createMBean(paramString, 
/*  380 */         cloneObjectName(paramObjectName1), paramObjectName2, (Object[])null, (String[])null);
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
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException {
/*  433 */     return this.mbsInterceptor.createMBean(paramString, cloneObjectName(paramObjectName), paramArrayOfObject, paramArrayOfString);
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
/*      */   public ObjectInstance createMBean(String paramString, ObjectName paramObjectName1, ObjectName paramObjectName2, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException {
/*  488 */     return this.mbsInterceptor.createMBean(paramString, cloneObjectName(paramObjectName1), paramObjectName2, paramArrayOfObject, paramArrayOfString);
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
/*      */   public ObjectInstance registerMBean(Object paramObject, ObjectName paramObjectName) throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
/*  522 */     return this.mbsInterceptor.registerMBean(paramObject, cloneObjectName(paramObjectName));
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
/*      */   public void unregisterMBean(ObjectName paramObjectName) throws InstanceNotFoundException, MBeanRegistrationException {
/*  546 */     this.mbsInterceptor.unregisterMBean(cloneObjectName(paramObjectName));
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
/*      */   public ObjectInstance getObjectInstance(ObjectName paramObjectName) throws InstanceNotFoundException {
/*  564 */     return this.mbsInterceptor.getObjectInstance(cloneObjectName(paramObjectName));
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
/*      */   public Set<ObjectInstance> queryMBeans(ObjectName paramObjectName, QueryExp paramQueryExp) {
/*  592 */     return this.mbsInterceptor.queryMBeans(cloneObjectName(paramObjectName), paramQueryExp);
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
/*      */   public Set<ObjectName> queryNames(ObjectName paramObjectName, QueryExp paramQueryExp) {
/*  619 */     return this.mbsInterceptor.queryNames(cloneObjectName(paramObjectName), paramQueryExp);
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
/*      */   public boolean isRegistered(ObjectName paramObjectName) {
/*  638 */     return this.mbsInterceptor.isRegistered(paramObjectName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getMBeanCount() {
/*  646 */     return this.mbsInterceptor.getMBeanCount();
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
/*      */   public Object getAttribute(ObjectName paramObjectName, String paramString) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException {
/*  678 */     return this.mbsInterceptor.getAttribute(cloneObjectName(paramObjectName), paramString);
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
/*      */   public AttributeList getAttributes(ObjectName paramObjectName, String[] paramArrayOfString) throws InstanceNotFoundException, ReflectionException {
/*  705 */     return this.mbsInterceptor.getAttributes(cloneObjectName(paramObjectName), paramArrayOfString);
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
/*      */   public void setAttribute(ObjectName paramObjectName, Attribute paramAttribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
/*  739 */     this.mbsInterceptor.setAttribute(cloneObjectName(paramObjectName), 
/*  740 */         cloneAttribute(paramAttribute));
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
/*      */   public AttributeList setAttributes(ObjectName paramObjectName, AttributeList paramAttributeList) throws InstanceNotFoundException, ReflectionException {
/*  768 */     return this.mbsInterceptor.setAttributes(cloneObjectName(paramObjectName), 
/*  769 */         cloneAttributeList(paramAttributeList));
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
/*      */   public Object invoke(ObjectName paramObjectName, String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws InstanceNotFoundException, MBeanException, ReflectionException {
/*  801 */     return this.mbsInterceptor.invoke(cloneObjectName(paramObjectName), paramString, paramArrayOfObject, paramArrayOfString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultDomain() {
/*  811 */     return this.mbsInterceptor.getDefaultDomain();
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getDomains() {
/*  816 */     return this.mbsInterceptor.getDomains();
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
/*      */   public void addNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException {
/*  839 */     this.mbsInterceptor.addNotificationListener(cloneObjectName(paramObjectName), paramNotificationListener, paramNotificationFilter, paramObject);
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
/*      */   public void addNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException {
/*  862 */     this.mbsInterceptor.addNotificationListener(cloneObjectName(paramObjectName1), paramObjectName2, paramNotificationFilter, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener) throws InstanceNotFoundException, ListenerNotFoundException {
/*  870 */     this.mbsInterceptor.removeNotificationListener(cloneObjectName(paramObjectName), paramNotificationListener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(ObjectName paramObjectName, NotificationListener paramNotificationListener, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException, ListenerNotFoundException {
/*  880 */     this.mbsInterceptor.removeNotificationListener(cloneObjectName(paramObjectName), paramNotificationListener, paramNotificationFilter, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2) throws InstanceNotFoundException, ListenerNotFoundException {
/*  888 */     this.mbsInterceptor.removeNotificationListener(cloneObjectName(paramObjectName1), paramObjectName2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeNotificationListener(ObjectName paramObjectName1, ObjectName paramObjectName2, NotificationFilter paramNotificationFilter, Object paramObject) throws InstanceNotFoundException, ListenerNotFoundException {
/*  898 */     this.mbsInterceptor.removeNotificationListener(cloneObjectName(paramObjectName1), paramObjectName2, paramNotificationFilter, paramObject);
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
/*      */   public MBeanInfo getMBeanInfo(ObjectName paramObjectName) throws InstanceNotFoundException, IntrospectionException, ReflectionException {
/*  920 */     return this.mbsInterceptor.getMBeanInfo(cloneObjectName(paramObjectName));
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
/*      */   public Object instantiate(String paramString) throws ReflectionException, MBeanException {
/*  950 */     checkMBeanPermission(paramString, null, null, "instantiate");
/*      */     
/*  952 */     return this.instantiator.instantiate(paramString);
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
/*      */   public Object instantiate(String paramString, ObjectName paramObjectName) throws ReflectionException, MBeanException, InstanceNotFoundException {
/*  987 */     checkMBeanPermission(paramString, null, null, "instantiate");
/*      */     
/*  989 */     ClassLoader classLoader = this.outerShell.getClass().getClassLoader();
/*  990 */     return this.instantiator.instantiate(paramString, paramObjectName, classLoader);
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
/*      */   public Object instantiate(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, MBeanException {
/* 1025 */     checkMBeanPermission(paramString, null, null, "instantiate");
/*      */     
/* 1027 */     ClassLoader classLoader = this.outerShell.getClass().getClassLoader();
/* 1028 */     return this.instantiator.instantiate(paramString, paramArrayOfObject, paramArrayOfString, classLoader);
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
/*      */   public Object instantiate(String paramString, ObjectName paramObjectName, Object[] paramArrayOfObject, String[] paramArrayOfString) throws ReflectionException, MBeanException, InstanceNotFoundException {
/* 1068 */     checkMBeanPermission(paramString, null, null, "instantiate");
/*      */     
/* 1070 */     ClassLoader classLoader = this.outerShell.getClass().getClassLoader();
/* 1071 */     return this.instantiator.instantiate(paramString, paramObjectName, paramArrayOfObject, paramArrayOfString, classLoader);
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
/*      */   public boolean isInstanceOf(ObjectName paramObjectName, String paramString) throws InstanceNotFoundException {
/* 1091 */     return this.mbsInterceptor.isInstanceOf(cloneObjectName(paramObjectName), paramString);
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
/*      */   @Deprecated
/*      */   public ObjectInputStream deserialize(ObjectName paramObjectName, byte[] paramArrayOfbyte) throws InstanceNotFoundException, OperationsException {
/* 1116 */     ClassLoader classLoader = getClassLoaderFor(paramObjectName);
/*      */     
/* 1118 */     return this.instantiator.deserialize(classLoader, paramArrayOfbyte);
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
/*      */   @Deprecated
/*      */   public ObjectInputStream deserialize(String paramString, byte[] paramArrayOfbyte) throws OperationsException, ReflectionException {
/*      */     Class<?> clazz;
/* 1141 */     if (paramString == null) {
/* 1142 */       throw new RuntimeOperationsException(new IllegalArgumentException(), "Null className passed in parameter");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1149 */     ClassLoaderRepository classLoaderRepository = getClassLoaderRepository();
/*      */ 
/*      */     
/*      */     try {
/* 1153 */       if (classLoaderRepository == null) throw new ClassNotFoundException(paramString); 
/* 1154 */       clazz = classLoaderRepository.loadClass(paramString);
/* 1155 */     } catch (ClassNotFoundException classNotFoundException) {
/* 1156 */       throw new ReflectionException(classNotFoundException, "The given class could not be loaded by the default loader repository");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1162 */     return this.instantiator.deserialize(clazz.getClassLoader(), paramArrayOfbyte);
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
/*      */   @Deprecated
/*      */   public ObjectInputStream deserialize(String paramString, ObjectName paramObjectName, byte[] paramArrayOfbyte) throws InstanceNotFoundException, OperationsException, ReflectionException {
/* 1197 */     paramObjectName = cloneObjectName(paramObjectName);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1203 */       getClassLoader(paramObjectName);
/* 1204 */     } catch (SecurityException securityException) {
/* 1205 */       throw securityException;
/* 1206 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1209 */     ClassLoader classLoader = this.outerShell.getClass().getClassLoader();
/* 1210 */     return this.instantiator.deserialize(paramString, paramObjectName, paramArrayOfbyte, classLoader);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void initialize() {
/* 1218 */     if (this.instantiator == null) throw new IllegalStateException("instantiator must not be null.");
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1223 */       AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*      */             public Object run() throws Exception {
/* 1225 */               JmxMBeanServer.this.mbsInterceptor.registerMBean(JmxMBeanServer.this
/* 1226 */                   .mBeanServerDelegateObject, MBeanServerDelegate.DELEGATE_NAME);
/*      */               
/* 1228 */               return null;
/*      */             }
/*      */           });
/* 1231 */     } catch (SecurityException securityException) {
/* 1232 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/* 1233 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, JmxMBeanServer.class
/* 1234 */             .getName(), "initialize", "Unexpected security exception occurred", securityException);
/*      */       }
/*      */       
/* 1237 */       throw securityException;
/* 1238 */     } catch (Exception exception) {
/* 1239 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/* 1240 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, JmxMBeanServer.class
/* 1241 */             .getName(), "initialize", "Unexpected exception occurred", exception);
/*      */       }
/*      */       
/* 1244 */       throw new IllegalStateException("Can't register delegate.", exception);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1253 */     ClassLoader classLoader = this.outerShell.getClass().getClassLoader();
/* 1254 */     ModifiableClassLoaderRepository modifiableClassLoaderRepository = AccessController.<ModifiableClassLoaderRepository>doPrivileged(new PrivilegedAction<ModifiableClassLoaderRepository>()
/*      */         {
/*      */           public ModifiableClassLoaderRepository run()
/*      */           {
/* 1258 */             return JmxMBeanServer.this.instantiator.getClassLoaderRepository();
/*      */           }
/*      */         });
/*      */     
/* 1262 */     if (modifiableClassLoaderRepository != null) {
/* 1263 */       modifiableClassLoaderRepository.addClassLoader(classLoader);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1280 */       ClassLoader classLoader1 = ClassLoader.getSystemClassLoader();
/* 1281 */       if (classLoader1 != classLoader) {
/* 1282 */         modifiableClassLoaderRepository.addClassLoader(classLoader1);
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
/*      */   public synchronized MBeanServer getMBeanServerInterceptor() {
/* 1294 */     if (this.interceptorsEnabled) return this.mbsInterceptor; 
/* 1295 */     throw new UnsupportedOperationException("MBeanServerInterceptors are disabled.");
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
/*      */   public synchronized void setMBeanServerInterceptor(MBeanServer paramMBeanServer) {
/* 1308 */     if (!this.interceptorsEnabled) throw new UnsupportedOperationException("MBeanServerInterceptors are disabled.");
/*      */     
/* 1310 */     if (paramMBeanServer == null) throw new IllegalArgumentException("MBeanServerInterceptor is null");
/*      */     
/* 1312 */     this.mbsInterceptor = paramMBeanServer;
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
/*      */   public ClassLoader getClassLoaderFor(ObjectName paramObjectName) throws InstanceNotFoundException {
/* 1324 */     return this.mbsInterceptor.getClassLoaderFor(cloneObjectName(paramObjectName));
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
/*      */   public ClassLoader getClassLoader(ObjectName paramObjectName) throws InstanceNotFoundException {
/* 1336 */     return this.mbsInterceptor.getClassLoader(cloneObjectName(paramObjectName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassLoaderRepository getClassLoaderRepository() {
/* 1345 */     checkMBeanPermission(null, null, null, "getClassLoaderRepository");
/* 1346 */     return this.secureClr;
/*      */   }
/*      */   
/*      */   public MBeanServerDelegate getMBeanServerDelegate() {
/* 1350 */     if (!this.interceptorsEnabled) throw new UnsupportedOperationException("MBeanServerInterceptors are disabled.");
/*      */     
/* 1352 */     return this.mBeanServerDelegateObject;
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
/*      */   public static MBeanServerDelegate newMBeanServerDelegate() {
/* 1374 */     return new MBeanServerDelegateImpl();
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
/*      */   public static MBeanServer newMBeanServer(String paramString, MBeanServer paramMBeanServer, MBeanServerDelegate paramMBeanServerDelegate, boolean paramBoolean) {
/* 1428 */     checkNewMBeanServerPermission();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1437 */     return new JmxMBeanServer(paramString, paramMBeanServer, paramMBeanServerDelegate, null, paramBoolean, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ObjectName cloneObjectName(ObjectName paramObjectName) {
/* 1448 */     if (paramObjectName != null) {
/* 1449 */       return ObjectName.getInstance(paramObjectName);
/*      */     }
/* 1451 */     return paramObjectName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Attribute cloneAttribute(Attribute paramAttribute) {
/* 1458 */     if (paramAttribute != null && 
/* 1459 */       !paramAttribute.getClass().equals(Attribute.class)) {
/* 1460 */       return new Attribute(paramAttribute.getName(), paramAttribute.getValue());
/*      */     }
/*      */     
/* 1463 */     return paramAttribute;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeList cloneAttributeList(AttributeList paramAttributeList) {
/* 1470 */     if (paramAttributeList != null) {
/* 1471 */       List<Attribute> list = paramAttributeList.asList();
/* 1472 */       if (!paramAttributeList.getClass().equals(AttributeList.class)) {
/*      */ 
/*      */         
/* 1475 */         AttributeList attributeList = new AttributeList(list.size());
/*      */ 
/*      */ 
/*      */         
/* 1479 */         for (Attribute attribute : list)
/* 1480 */           attributeList.add(cloneAttribute(attribute)); 
/* 1481 */         return attributeList;
/*      */       } 
/*      */ 
/*      */       
/* 1485 */       for (byte b = 0; b < list.size(); b++) {
/* 1486 */         Attribute attribute = list.get(b);
/* 1487 */         if (!attribute.getClass().equals(Attribute.class)) {
/* 1488 */           paramAttributeList.set(b, cloneAttribute(attribute));
/*      */         }
/*      */       } 
/* 1491 */       return paramAttributeList;
/*      */     } 
/*      */     
/* 1494 */     return paramAttributeList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkMBeanPermission(String paramString1, String paramString2, ObjectName paramObjectName, String paramString3) throws SecurityException {
/* 1505 */     SecurityManager securityManager = System.getSecurityManager();
/* 1506 */     if (securityManager != null) {
/* 1507 */       MBeanPermission mBeanPermission = new MBeanPermission(paramString1, paramString2, paramObjectName, paramString3);
/*      */ 
/*      */ 
/*      */       
/* 1511 */       securityManager.checkPermission(mBeanPermission);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void checkNewMBeanServerPermission() {
/* 1516 */     SecurityManager securityManager = System.getSecurityManager();
/* 1517 */     if (securityManager != null) {
/* 1518 */       MBeanServerPermission mBeanServerPermission = new MBeanServerPermission("newMBeanServer");
/* 1519 */       securityManager.checkPermission(mBeanServerPermission);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/mbeanserver/JmxMBeanServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */