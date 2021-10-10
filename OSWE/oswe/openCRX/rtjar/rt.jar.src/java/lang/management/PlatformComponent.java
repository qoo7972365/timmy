/*     */ package java.lang.management;
/*     */ 
/*     */ import com.sun.management.GarbageCollectorMXBean;
/*     */ import com.sun.management.HotSpotDiagnosticMXBean;
/*     */ import com.sun.management.OperatingSystemMXBean;
/*     */ import com.sun.management.UnixOperatingSystemMXBean;
/*     */ import java.io.IOException;
/*     */ import java.lang.management.BufferPoolMXBean;
/*     */ import java.lang.management.ClassLoadingMXBean;
/*     */ import java.lang.management.CompilationMXBean;
/*     */ import java.lang.management.GarbageCollectorMXBean;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MemoryMXBean;
/*     */ import java.lang.management.MemoryManagerMXBean;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.lang.management.OperatingSystemMXBean;
/*     */ import java.lang.management.PlatformComponent;
/*     */ import java.lang.management.PlatformLoggingMXBean;
/*     */ import java.lang.management.PlatformManagedObject;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.management.MBeanServerConnection;
/*     */ import javax.management.ObjectName;
/*     */ import sun.management.ManagementFactoryHelper;
/*     */ import sun.management.Util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ enum PlatformComponent
/*     */ {
/*  66 */   CLASS_LOADING("java.lang.management.ClassLoadingMXBean", "java.lang", "ClassLoading", 
/*     */     
/*  68 */     defaultKeyProperties(), true, new MXBeanFetcher<ClassLoadingMXBean>()
/*     */     {
/*     */       public List<ClassLoadingMXBean> getMXBeans()
/*     */       {
/*  72 */         return Collections.singletonList(ManagementFactoryHelper.getClassLoadingMXBean());
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/*  79 */   COMPILATION("java.lang.management.CompilationMXBean", "java.lang", "Compilation", 
/*     */     
/*  81 */     defaultKeyProperties(), true, new MXBeanFetcher<CompilationMXBean>()
/*     */     {
/*     */       public List<CompilationMXBean> getMXBeans()
/*     */       {
/*  85 */         CompilationMXBean compilationMXBean = ManagementFactoryHelper.getCompilationMXBean();
/*  86 */         if (compilationMXBean == null) {
/*  87 */           return Collections.emptyList();
/*     */         }
/*  89 */         return Collections.singletonList(compilationMXBean);
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/*  97 */   MEMORY("java.lang.management.MemoryMXBean", "java.lang", "Memory", 
/*     */     
/*  99 */     defaultKeyProperties(), true, new MXBeanFetcher<MemoryMXBean>()
/*     */     {
/*     */       public List<MemoryMXBean> getMXBeans()
/*     */       {
/* 103 */         return Collections.singletonList(ManagementFactoryHelper.getMemoryMXBean());
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 110 */   GARBAGE_COLLECTOR("java.lang.management.GarbageCollectorMXBean", "java.lang", "GarbageCollector", 
/*     */     
/* 112 */     keyProperties(new String[] { "name" }, ), false, new MXBeanFetcher<GarbageCollectorMXBean>()
/*     */     {
/*     */       public List<GarbageCollectorMXBean> getMXBeans()
/*     */       {
/* 116 */         return 
/* 117 */           ManagementFactoryHelper.getGarbageCollectorMXBeans();
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 124 */   MEMORY_MANAGER("java.lang.management.MemoryManagerMXBean", "java.lang", "MemoryManager", 
/*     */     
/* 126 */     keyProperties(new String[] { "name" }, ), false, new MXBeanFetcher<MemoryManagerMXBean>()
/*     */     {
/*     */       public List<MemoryManagerMXBean> getMXBeans()
/*     */       {
/* 130 */         return ManagementFactoryHelper.getMemoryManagerMXBeans();
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[] { GARBAGE_COLLECTOR }),
/* 138 */   MEMORY_POOL("java.lang.management.MemoryPoolMXBean", "java.lang", "MemoryPool", 
/*     */     
/* 140 */     keyProperties(new String[] { "name" }, ), false, new MXBeanFetcher<MemoryPoolMXBean>()
/*     */     {
/*     */       public List<MemoryPoolMXBean> getMXBeans()
/*     */       {
/* 144 */         return ManagementFactoryHelper.getMemoryPoolMXBeans();
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 151 */   OPERATING_SYSTEM("java.lang.management.OperatingSystemMXBean", "java.lang", "OperatingSystem", 
/*     */     
/* 153 */     defaultKeyProperties(), true, new MXBeanFetcher<OperatingSystemMXBean>()
/*     */     {
/*     */       public List<OperatingSystemMXBean> getMXBeans()
/*     */       {
/* 157 */         return Collections.singletonList(ManagementFactoryHelper.getOperatingSystemMXBean());
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 164 */   RUNTIME("java.lang.management.RuntimeMXBean", "java.lang", "Runtime", 
/*     */     
/* 166 */     defaultKeyProperties(), true, new MXBeanFetcher<RuntimeMXBean>()
/*     */     {
/*     */       public List<RuntimeMXBean> getMXBeans()
/*     */       {
/* 170 */         return Collections.singletonList(ManagementFactoryHelper.getRuntimeMXBean());
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 177 */   THREADING("java.lang.management.ThreadMXBean", "java.lang", "Threading", 
/*     */     
/* 179 */     defaultKeyProperties(), true, new MXBeanFetcher<ThreadMXBean>()
/*     */     {
/*     */       public List<ThreadMXBean> getMXBeans()
/*     */       {
/* 183 */         return Collections.singletonList(ManagementFactoryHelper.getThreadMXBean());
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 191 */   LOGGING("java.lang.management.PlatformLoggingMXBean", "java.util.logging", "Logging", 
/*     */     
/* 193 */     defaultKeyProperties(), true, new MXBeanFetcher<PlatformLoggingMXBean>()
/*     */     {
/*     */       public List<PlatformLoggingMXBean> getMXBeans()
/*     */       {
/* 197 */         PlatformLoggingMXBean platformLoggingMXBean = ManagementFactoryHelper.getPlatformLoggingMXBean();
/* 198 */         if (platformLoggingMXBean == null) {
/* 199 */           return Collections.emptyList();
/*     */         }
/* 201 */         return Collections.singletonList(platformLoggingMXBean);
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 209 */   BUFFER_POOL("java.lang.management.BufferPoolMXBean", "java.nio", "BufferPool", 
/*     */     
/* 211 */     keyProperties(new String[] { "name" }, ), false, new MXBeanFetcher<BufferPoolMXBean>()
/*     */     {
/*     */       public List<BufferPoolMXBean> getMXBeans()
/*     */       {
/* 215 */         return ManagementFactoryHelper.getBufferPoolMXBeans();
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 225 */   SUN_GARBAGE_COLLECTOR("com.sun.management.GarbageCollectorMXBean", "java.lang", "GarbageCollector", 
/*     */     
/* 227 */     keyProperties(new String[] { "name" }, ), false, new MXBeanFetcher<GarbageCollectorMXBean>()
/*     */     {
/*     */       public List<GarbageCollectorMXBean> getMXBeans()
/*     */       {
/* 231 */         return (List)PlatformComponent.getGcMXBeanList((Class)GarbageCollectorMXBean.class);
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 239 */   SUN_OPERATING_SYSTEM("com.sun.management.OperatingSystemMXBean", "java.lang", "OperatingSystem", 
/*     */     
/* 241 */     defaultKeyProperties(), true, new MXBeanFetcher<OperatingSystemMXBean>()
/*     */     {
/*     */       public List<OperatingSystemMXBean> getMXBeans()
/*     */       {
/* 245 */         return (List)PlatformComponent.getOSMXBeanList((Class)OperatingSystemMXBean.class);
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 252 */   SUN_UNIX_OPERATING_SYSTEM("com.sun.management.UnixOperatingSystemMXBean", "java.lang", "OperatingSystem", 
/*     */     
/* 254 */     defaultKeyProperties(), true, new MXBeanFetcher<UnixOperatingSystemMXBean>()
/*     */     {
/*     */       public List<UnixOperatingSystemMXBean> getMXBeans()
/*     */       {
/* 258 */         return (List)PlatformComponent.getOSMXBeanList((Class)UnixOperatingSystemMXBean.class);
/*     */       }
/*     */     }, 
/*     */ 
/*     */ 
/*     */     
/*     */     new PlatformComponent[0]),
/* 265 */   HOTSPOT_DIAGNOSTIC("com.sun.management.HotSpotDiagnosticMXBean", "com.sun.management", "HotSpotDiagnostic", 
/*     */     
/* 267 */     defaultKeyProperties(), true, new MXBeanFetcher<HotSpotDiagnosticMXBean>()
/*     */     {
/*     */       public List<HotSpotDiagnosticMXBean> getMXBeans()
/*     */       {
/* 271 */         return Collections.singletonList(ManagementFactoryHelper.getDiagnosticMXBean());
/*     */       }
/*     */     },  new PlatformComponent[0]);
/*     */ 
/*     */   
/*     */   private final String mxbeanInterfaceName;
/*     */   
/*     */   private final String domain;
/*     */   private final String type;
/*     */   private final Set<String> keyProperties;
/*     */   private final MXBeanFetcher<?> fetcher;
/*     */   private final PlatformComponent[] subComponents;
/*     */   private final boolean singleton;
/*     */   private static Set<String> defaultKeyProps;
/*     */   private static Map<String, PlatformComponent> enumMap;
/*     */   private static final long serialVersionUID = 6992337162326171013L;
/*     */   
/*     */   private static <T extends GarbageCollectorMXBean> List<T> getGcMXBeanList(Class<T> paramClass) {
/* 289 */     List<GarbageCollectorMXBean> list = ManagementFactoryHelper.getGarbageCollectorMXBeans();
/* 290 */     ArrayList<T> arrayList = new ArrayList(list.size());
/* 291 */     for (GarbageCollectorMXBean garbageCollectorMXBean : list) {
/* 292 */       if (paramClass.isInstance(garbageCollectorMXBean)) {
/* 293 */         arrayList.add(paramClass.cast(garbageCollectorMXBean));
/*     */       }
/*     */     } 
/* 296 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T extends OperatingSystemMXBean> List<T> getOSMXBeanList(Class<T> paramClass) {
/* 305 */     OperatingSystemMXBean operatingSystemMXBean = ManagementFactoryHelper.getOperatingSystemMXBean();
/* 306 */     if (paramClass.isInstance(operatingSystemMXBean)) {
/* 307 */       return Collections.singletonList(paramClass.cast(operatingSystemMXBean));
/*     */     }
/* 309 */     return Collections.emptyList();
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
/*     */   PlatformComponent(String paramString1, String paramString2, String paramString3, Set<String> paramSet, boolean paramBoolean, MXBeanFetcher<?> paramMXBeanFetcher, PlatformComponent... paramVarArgs) {
/* 327 */     this.mxbeanInterfaceName = paramString1;
/* 328 */     this.domain = paramString2;
/* 329 */     this.type = paramString3;
/* 330 */     this.keyProperties = paramSet;
/* 331 */     this.singleton = paramBoolean;
/* 332 */     this.fetcher = paramMXBeanFetcher;
/* 333 */     this.subComponents = paramVarArgs;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Set<String> defaultKeyProperties() {
/* 338 */     if (defaultKeyProps == null) {
/* 339 */       defaultKeyProps = Collections.singleton("type");
/*     */     }
/* 341 */     return defaultKeyProps;
/*     */   }
/*     */   
/*     */   private static Set<String> keyProperties(String... paramVarArgs) {
/* 345 */     HashSet<String> hashSet = new HashSet();
/* 346 */     hashSet.add("type");
/* 347 */     for (String str : paramVarArgs) {
/* 348 */       hashSet.add(str);
/*     */     }
/* 350 */     return hashSet;
/*     */   }
/*     */   
/*     */   boolean isSingleton() {
/* 354 */     return this.singleton;
/*     */   }
/*     */   
/*     */   String getMXBeanInterfaceName() {
/* 358 */     return this.mxbeanInterfaceName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Class<? extends PlatformManagedObject> getMXBeanInterface() {
/*     */     try {
/* 365 */       return 
/* 366 */         (Class)Class.forName(this.mxbeanInterfaceName, false, PlatformManagedObject.class
/* 367 */           .getClassLoader());
/* 368 */     } catch (ClassNotFoundException classNotFoundException) {
/* 369 */       throw new AssertionError(classNotFoundException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <T extends PlatformManagedObject> List<T> getMXBeans(Class<T> paramClass) {
/* 377 */     return (List)this.fetcher.getMXBeans();
/*     */   }
/*     */ 
/*     */   
/*     */   <T extends PlatformManagedObject> T getSingletonMXBean(Class<T> paramClass) {
/* 382 */     if (!this.singleton) {
/* 383 */       throw new IllegalArgumentException(this.mxbeanInterfaceName + " can have zero or more than one instances");
/*     */     }
/*     */     
/* 386 */     List<T> list = getMXBeans(paramClass);
/* 387 */     assert list.size() == 1;
/* 388 */     return list.isEmpty() ? null : list.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <T extends PlatformManagedObject> T getSingletonMXBean(MBeanServerConnection paramMBeanServerConnection, Class<T> paramClass) throws IOException {
/* 395 */     if (!this.singleton) {
/* 396 */       throw new IllegalArgumentException(this.mxbeanInterfaceName + " can have zero or more than one instances");
/*     */     }
/*     */ 
/*     */     
/* 400 */     assert this.keyProperties.size() == 1;
/* 401 */     String str = this.domain + ":type=" + this.type;
/* 402 */     return (T)ManagementFactory.<PlatformManagedObject>newPlatformMXBeanProxy(paramMBeanServerConnection, str, paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   <T extends PlatformManagedObject> List<T> getMXBeans(MBeanServerConnection paramMBeanServerConnection, Class<T> paramClass) throws IOException {
/* 411 */     ArrayList<T> arrayList = new ArrayList();
/* 412 */     for (ObjectName objectName : getObjectNames(paramMBeanServerConnection)) {
/* 413 */       arrayList.add(
/* 414 */           ManagementFactory.newPlatformMXBeanProxy(paramMBeanServerConnection, objectName
/* 415 */             .getCanonicalName(), paramClass));
/*     */     }
/*     */ 
/*     */     
/* 419 */     return arrayList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<ObjectName> getObjectNames(MBeanServerConnection paramMBeanServerConnection) throws IOException {
/* 425 */     String str = this.domain + ":type=" + this.type;
/* 426 */     if (this.keyProperties.size() > 1)
/*     */     {
/* 428 */       str = str + ",*";
/*     */     }
/* 430 */     ObjectName objectName = Util.newObjectName(str);
/* 431 */     Set<ObjectName> set = paramMBeanServerConnection.queryNames(objectName, null);
/* 432 */     for (PlatformComponent platformComponent : this.subComponents) {
/* 433 */       set.addAll(platformComponent.getObjectNames(paramMBeanServerConnection));
/*     */     }
/* 435 */     return set;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void ensureInitialized() {
/* 441 */     if (enumMap == null) {
/* 442 */       enumMap = new HashMap<>();
/* 443 */       for (PlatformComponent platformComponent : values())
/*     */       {
/*     */         
/* 446 */         enumMap.put(platformComponent.getMXBeanInterfaceName(), platformComponent);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   static boolean isPlatformMXBean(String paramString) {
/* 452 */     ensureInitialized();
/* 453 */     return enumMap.containsKey(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends PlatformManagedObject> PlatformComponent getPlatformComponent(Class<T> paramClass) {
/* 459 */     ensureInitialized();
/* 460 */     String str = paramClass.getName();
/* 461 */     PlatformComponent platformComponent = enumMap.get(str);
/* 462 */     if (platformComponent != null && platformComponent.getMXBeanInterface() == paramClass)
/* 463 */       return platformComponent; 
/* 464 */     return null;
/*     */   }
/*     */   
/*     */   static interface MXBeanFetcher<T extends PlatformManagedObject> {
/*     */     List<T> getMXBeans();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/lang/management/PlatformComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */