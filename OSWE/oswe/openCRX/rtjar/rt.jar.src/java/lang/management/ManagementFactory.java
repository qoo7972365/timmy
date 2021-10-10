/*     */ package java.lang.management;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.management.ClassLoadingMXBean;
/*     */ import java.lang.management.CompilationMXBean;
/*     */ import java.lang.management.GarbageCollectorMXBean;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MemoryMXBean;
/*     */ import java.lang.management.MemoryManagerMXBean;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.lang.management.OperatingSystemMXBean;
/*     */ import java.lang.management.PlatformComponent;
/*     */ import java.lang.management.PlatformManagedObject;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.management.DynamicMBean;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.JMX;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.MBeanServerConnection;
/*     */ import javax.management.MBeanServerFactory;
/*     */ import javax.management.MBeanServerPermission;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.NotificationEmitter;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.StandardEmitterMBean;
/*     */ import javax.management.StandardMBean;
/*     */ import sun.management.ExtendedPlatformComponent;
/*     */ import sun.management.ManagementFactoryHelper;
/*     */ import sun.misc.VM;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManagementFactory
/*     */ {
/*     */   public static final String CLASS_LOADING_MXBEAN_NAME = "java.lang:type=ClassLoading";
/*     */   public static final String COMPILATION_MXBEAN_NAME = "java.lang:type=Compilation";
/*     */   public static final String MEMORY_MXBEAN_NAME = "java.lang:type=Memory";
/*     */   public static final String OPERATING_SYSTEM_MXBEAN_NAME = "java.lang:type=OperatingSystem";
/*     */   public static final String RUNTIME_MXBEAN_NAME = "java.lang:type=Runtime";
/*     */   public static final String THREAD_MXBEAN_NAME = "java.lang:type=Threading";
/*     */   public static final String GARBAGE_COLLECTOR_MXBEAN_DOMAIN_TYPE = "java.lang:type=GarbageCollector";
/*     */   public static final String MEMORY_MANAGER_MXBEAN_DOMAIN_TYPE = "java.lang:type=MemoryManager";
/*     */   public static final String MEMORY_POOL_MXBEAN_DOMAIN_TYPE = "java.lang:type=MemoryPool";
/*     */   private static MBeanServer platformMBeanServer;
/*     */   private static final String NOTIF_EMITTER = "javax.management.NotificationEmitter";
/*     */   
/*     */   public static ClassLoadingMXBean getClassLoadingMXBean() {
/* 319 */     return ManagementFactoryHelper.getClassLoadingMXBean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MemoryMXBean getMemoryMXBean() {
/* 329 */     return ManagementFactoryHelper.getMemoryMXBean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThreadMXBean getThreadMXBean() {
/* 339 */     return ManagementFactoryHelper.getThreadMXBean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RuntimeMXBean getRuntimeMXBean() {
/* 350 */     return ManagementFactoryHelper.getRuntimeMXBean();
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
/*     */   public static CompilationMXBean getCompilationMXBean() {
/* 363 */     return ManagementFactoryHelper.getCompilationMXBean();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static OperatingSystemMXBean getOperatingSystemMXBean() {
/* 374 */     return ManagementFactoryHelper.getOperatingSystemMXBean();
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
/*     */   public static List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
/* 387 */     return ManagementFactoryHelper.getMemoryPoolMXBeans();
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
/*     */   public static List<MemoryManagerMXBean> getMemoryManagerMXBeans() {
/* 400 */     return ManagementFactoryHelper.getMemoryManagerMXBeans();
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
/*     */   public static List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
/* 416 */     return ManagementFactoryHelper.getGarbageCollectorMXBeans();
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
/*     */   
/*     */   public static synchronized MBeanServer getPlatformMBeanServer() {
/* 462 */     SecurityManager securityManager = System.getSecurityManager();
/* 463 */     if (securityManager != null) {
/* 464 */       MBeanServerPermission mBeanServerPermission = new MBeanServerPermission("createMBeanServer");
/* 465 */       securityManager.checkPermission(mBeanServerPermission);
/*     */     } 
/*     */     
/* 468 */     if (platformMBeanServer == null) {
/* 469 */       platformMBeanServer = MBeanServerFactory.createMBeanServer();
/* 470 */       for (PlatformComponent platformComponent : PlatformComponent.values()) {
/*     */         
/* 472 */         List<? extends PlatformManagedObject> list = platformComponent.getMXBeans(platformComponent.getMXBeanInterface());
/* 473 */         for (PlatformManagedObject platformManagedObject : list) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 483 */           if (!platformMBeanServer.isRegistered(platformManagedObject.getObjectName())) {
/* 484 */             addMXBean(platformMBeanServer, platformManagedObject);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 489 */       HashMap hashMap = ManagementFactoryHelper.getPlatformDynamicMBeans();
/* 490 */       for (Map.Entry entry : hashMap.entrySet()) {
/* 491 */         addDynamicMBean(platformMBeanServer, (DynamicMBean)entry.getValue(), (ObjectName)entry.getKey());
/*     */       }
/*     */       
/* 494 */       for (PlatformManagedObject platformManagedObject : ExtendedPlatformComponent.getMXBeans()) {
/* 495 */         if (!platformMBeanServer.isRegistered(platformManagedObject.getObjectName())) {
/* 496 */           addMXBean(platformMBeanServer, platformManagedObject);
/*     */         }
/*     */       } 
/*     */     } 
/* 500 */     return platformMBeanServer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T newPlatformMXBeanProxy(MBeanServerConnection paramMBeanServerConnection, String paramString, Class<T> paramClass) throws IOException {
/* 601 */     final Class<T> cls = paramClass;
/*     */     
/* 603 */     ClassLoader classLoader = AccessController.<ClassLoader>doPrivileged(new PrivilegedAction<ClassLoader>() {
/*     */           public ClassLoader run() {
/* 605 */             return cls.getClassLoader();
/*     */           }
/*     */         });
/* 608 */     if (!VM.isSystemDomainLoader(classLoader)) {
/* 609 */       throw new IllegalArgumentException(paramString + " is not a platform MXBean");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 614 */       ObjectName objectName = new ObjectName(paramString);
/*     */       
/* 616 */       String str = paramClass.getName();
/* 617 */       if (!paramMBeanServerConnection.isInstanceOf(objectName, str)) {
/* 618 */         throw new IllegalArgumentException(paramString + " is not an instance of " + paramClass);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 624 */       boolean bool = paramMBeanServerConnection.isInstanceOf(objectName, "javax.management.NotificationEmitter");
/*     */ 
/*     */       
/* 627 */       return JMX.newMXBeanProxy(paramMBeanServerConnection, objectName, paramClass, bool);
/*     */     }
/* 629 */     catch (InstanceNotFoundException|javax.management.MalformedObjectNameException instanceNotFoundException) {
/* 630 */       throw new IllegalArgumentException(instanceNotFoundException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends PlatformManagedObject> T getPlatformMXBean(Class<T> paramClass) {
/* 664 */     PlatformComponent platformComponent = PlatformComponent.getPlatformComponent(paramClass);
/* 665 */     if (platformComponent == null) {
/* 666 */       PlatformManagedObject platformManagedObject = ExtendedPlatformComponent.getMXBean(paramClass);
/* 667 */       if (platformManagedObject != null) {
/* 668 */         return (T)platformManagedObject;
/*     */       }
/* 670 */       throw new IllegalArgumentException(paramClass.getName() + " is not a platform management interface");
/*     */     } 
/*     */     
/* 673 */     if (!platformComponent.isSingleton()) {
/* 674 */       throw new IllegalArgumentException(paramClass.getName() + " can have zero or more than one instances");
/*     */     }
/*     */     
/* 677 */     return platformComponent.getSingletonMXBean(paramClass);
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
/*     */   public static <T extends PlatformManagedObject> List<T> getPlatformMXBeans(Class<T> paramClass) {
/* 704 */     PlatformComponent platformComponent = PlatformComponent.getPlatformComponent(paramClass);
/* 705 */     if (platformComponent == null) {
/* 706 */       PlatformManagedObject platformManagedObject = ExtendedPlatformComponent.getMXBean(paramClass);
/* 707 */       if (platformManagedObject != null) {
/* 708 */         return Collections.singletonList((T)platformManagedObject);
/*     */       }
/* 710 */       throw new IllegalArgumentException(paramClass.getName() + " is not a platform management interface");
/*     */     } 
/*     */     
/* 713 */     return Collections.unmodifiableList(platformComponent.getMXBeans(paramClass));
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
/*     */   public static <T extends PlatformManagedObject> T getPlatformMXBean(MBeanServerConnection paramMBeanServerConnection, Class<T> paramClass) throws IOException {
/* 756 */     PlatformComponent platformComponent = PlatformComponent.getPlatformComponent(paramClass);
/* 757 */     if (platformComponent == null) {
/* 758 */       PlatformManagedObject platformManagedObject = ExtendedPlatformComponent.getMXBean(paramClass);
/* 759 */       if (platformManagedObject != null) {
/* 760 */         ObjectName objectName = platformManagedObject.getObjectName();
/* 761 */         return (T)newPlatformMXBeanProxy(paramMBeanServerConnection, objectName
/* 762 */             .getCanonicalName(), paramClass);
/*     */       } 
/*     */       
/* 765 */       throw new IllegalArgumentException(paramClass.getName() + " is not a platform management interface");
/*     */     } 
/*     */     
/* 768 */     if (!platformComponent.isSingleton()) {
/* 769 */       throw new IllegalArgumentException(paramClass.getName() + " can have zero or more than one instances");
/*     */     }
/* 771 */     return platformComponent.getSingletonMXBean(paramMBeanServerConnection, paramClass);
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
/*     */   public static <T extends PlatformManagedObject> List<T> getPlatformMXBeans(MBeanServerConnection paramMBeanServerConnection, Class<T> paramClass) throws IOException {
/* 807 */     PlatformComponent platformComponent = PlatformComponent.getPlatformComponent(paramClass);
/* 808 */     if (platformComponent == null) {
/* 809 */       PlatformManagedObject platformManagedObject = ExtendedPlatformComponent.getMXBean(paramClass);
/* 810 */       if (platformManagedObject != null) {
/* 811 */         ObjectName objectName = platformManagedObject.getObjectName();
/* 812 */         PlatformManagedObject platformManagedObject1 = newPlatformMXBeanProxy(paramMBeanServerConnection, objectName
/* 813 */             .getCanonicalName(), paramClass);
/* 814 */         return Collections.singletonList((T)platformManagedObject1);
/*     */       } 
/* 816 */       throw new IllegalArgumentException(paramClass.getName() + " is not a platform management interface");
/*     */     } 
/*     */     
/* 819 */     return Collections.unmodifiableList(platformComponent.getMXBeans(paramMBeanServerConnection, paramClass));
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
/*     */   public static Set<Class<? extends PlatformManagedObject>> getPlatformManagementInterfaces() {
/* 838 */     HashSet<Class<? extends PlatformManagedObject>> hashSet = new HashSet();
/*     */     
/* 840 */     for (PlatformComponent platformComponent : PlatformComponent.values()) {
/* 841 */       hashSet.add(platformComponent.getMXBeanInterface());
/*     */     }
/* 843 */     return Collections.unmodifiableSet(hashSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMXBean(final MBeanServer mbs, final PlatformManagedObject pmo) {
/*     */     try {
/* 855 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/*     */             {
/*     */               DynamicMBean dynamicMBean;
/* 860 */               if (pmo instanceof DynamicMBean) {
/* 861 */                 dynamicMBean = DynamicMBean.class.cast(pmo);
/* 862 */               } else if (pmo instanceof NotificationEmitter) {
/* 863 */                 dynamicMBean = new StandardEmitterMBean((T)pmo, null, true, (NotificationEmitter)pmo);
/*     */               } else {
/* 865 */                 dynamicMBean = new StandardMBean((T)pmo, null, true);
/*     */               } 
/*     */               
/* 868 */               mbs.registerMBean(dynamicMBean, pmo.getObjectName());
/* 869 */               return null;
/*     */             }
/*     */           });
/* 872 */     } catch (PrivilegedActionException privilegedActionException) {
/* 873 */       throw new RuntimeException(privilegedActionException.getException());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addDynamicMBean(final MBeanServer mbs, final DynamicMBean dmbean, final ObjectName on) {
/*     */     try {
/* 884 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             
/*     */             public Void run() throws InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException
/*     */             {
/* 889 */               mbs.registerMBean(dmbean, on);
/* 890 */               return null;
/*     */             }
/*     */           });
/* 893 */     } catch (PrivilegedActionException privilegedActionException) {
/* 894 */       throw new RuntimeException(privilegedActionException.getException());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/ManagementFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */