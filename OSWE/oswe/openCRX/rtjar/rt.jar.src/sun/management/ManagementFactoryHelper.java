/*     */ package sun.management;
/*     */ 
/*     */ import com.sun.management.DiagnosticCommandMBean;
/*     */ import com.sun.management.HotSpotDiagnosticMXBean;
/*     */ import java.lang.management.BufferPoolMXBean;
/*     */ import java.lang.management.ClassLoadingMXBean;
/*     */ import java.lang.management.CompilationMXBean;
/*     */ import java.lang.management.GarbageCollectorMXBean;
/*     */ import java.lang.management.MemoryMXBean;
/*     */ import java.lang.management.MemoryManagerMXBean;
/*     */ import java.lang.management.MemoryPoolMXBean;
/*     */ import java.lang.management.OperatingSystemMXBean;
/*     */ import java.lang.management.PlatformLoggingMXBean;
/*     */ import java.lang.management.RuntimeMXBean;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import javax.management.DynamicMBean;
/*     */ import javax.management.InstanceAlreadyExistsException;
/*     */ import javax.management.InstanceNotFoundException;
/*     */ import javax.management.MBeanRegistrationException;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.NotCompliantMBeanException;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.RuntimeOperationsException;
/*     */ import sun.misc.JavaNioAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ import sun.misc.VM;
/*     */ import sun.nio.ch.FileChannelImpl;
/*     */ import sun.util.logging.LoggingSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManagementFactoryHelper
/*     */ {
/*     */   private static VMManagement jvm;
/*  62 */   private static ClassLoadingImpl classMBean = null;
/*  63 */   private static MemoryImpl memoryMBean = null;
/*  64 */   private static ThreadImpl threadMBean = null;
/*  65 */   private static RuntimeImpl runtimeMBean = null;
/*  66 */   private static CompilationImpl compileMBean = null;
/*  67 */   private static OperatingSystemImpl osMBean = null;
/*     */   
/*     */   public static synchronized ClassLoadingMXBean getClassLoadingMXBean() {
/*  70 */     if (classMBean == null) {
/*  71 */       classMBean = new ClassLoadingImpl(jvm);
/*     */     }
/*  73 */     return classMBean;
/*     */   }
/*     */   
/*     */   public static synchronized MemoryMXBean getMemoryMXBean() {
/*  77 */     if (memoryMBean == null) {
/*  78 */       memoryMBean = new MemoryImpl(jvm);
/*     */     }
/*  80 */     return memoryMBean;
/*     */   }
/*     */   
/*     */   public static synchronized ThreadMXBean getThreadMXBean() {
/*  84 */     if (threadMBean == null) {
/*  85 */       threadMBean = new ThreadImpl(jvm);
/*     */     }
/*  87 */     return threadMBean;
/*     */   }
/*     */   
/*     */   public static synchronized RuntimeMXBean getRuntimeMXBean() {
/*  91 */     if (runtimeMBean == null) {
/*  92 */       runtimeMBean = new RuntimeImpl(jvm);
/*     */     }
/*  94 */     return runtimeMBean;
/*     */   }
/*     */   
/*     */   public static synchronized CompilationMXBean getCompilationMXBean() {
/*  98 */     if (compileMBean == null && jvm.getCompilerName() != null) {
/*  99 */       compileMBean = new CompilationImpl(jvm);
/*     */     }
/* 101 */     return compileMBean;
/*     */   }
/*     */   
/*     */   public static synchronized OperatingSystemMXBean getOperatingSystemMXBean() {
/* 105 */     if (osMBean == null) {
/* 106 */       osMBean = new OperatingSystemImpl(jvm);
/*     */     }
/* 108 */     return osMBean;
/*     */   }
/*     */   
/*     */   public static List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
/* 112 */     MemoryPoolMXBean[] arrayOfMemoryPoolMXBean = MemoryImpl.getMemoryPools();
/* 113 */     ArrayList<MemoryPoolMXBean> arrayList = new ArrayList(arrayOfMemoryPoolMXBean.length);
/* 114 */     for (MemoryPoolMXBean memoryPoolMXBean : arrayOfMemoryPoolMXBean) {
/* 115 */       arrayList.add(memoryPoolMXBean);
/*     */     }
/* 117 */     return arrayList;
/*     */   }
/*     */   
/*     */   public static List<MemoryManagerMXBean> getMemoryManagerMXBeans() {
/* 121 */     MemoryManagerMXBean[] arrayOfMemoryManagerMXBean = MemoryImpl.getMemoryManagers();
/* 122 */     ArrayList<MemoryManagerMXBean> arrayList = new ArrayList(arrayOfMemoryManagerMXBean.length);
/* 123 */     for (MemoryManagerMXBean memoryManagerMXBean : arrayOfMemoryManagerMXBean) {
/* 124 */       arrayList.add(memoryManagerMXBean);
/*     */     }
/* 126 */     return arrayList;
/*     */   }
/*     */   
/*     */   public static List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
/* 130 */     MemoryManagerMXBean[] arrayOfMemoryManagerMXBean = MemoryImpl.getMemoryManagers();
/* 131 */     ArrayList<GarbageCollectorMXBean> arrayList = new ArrayList(arrayOfMemoryManagerMXBean.length);
/* 132 */     for (MemoryManagerMXBean memoryManagerMXBean : arrayOfMemoryManagerMXBean) {
/* 133 */       if (GarbageCollectorMXBean.class.isInstance(memoryManagerMXBean)) {
/* 134 */         arrayList.add(GarbageCollectorMXBean.class.cast(memoryManagerMXBean));
/*     */       }
/*     */     } 
/* 137 */     return arrayList;
/*     */   }
/*     */   
/*     */   public static PlatformLoggingMXBean getPlatformLoggingMXBean() {
/* 141 */     if (LoggingSupport.isAvailable()) {
/* 142 */       return PlatformLoggingImpl.instance;
/*     */     }
/* 144 */     return null;
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
/*     */   static class PlatformLoggingImpl
/*     */     implements LoggingMXBean
/*     */   {
/* 167 */     static final PlatformLoggingMXBean instance = new PlatformLoggingImpl();
/*     */     
/*     */     static final String LOGGING_MXBEAN_NAME = "java.util.logging:type=Logging";
/*     */     private volatile ObjectName objname;
/*     */     
/*     */     public ObjectName getObjectName() {
/* 173 */       ObjectName objectName = this.objname;
/* 174 */       if (objectName == null) {
/* 175 */         synchronized (this) {
/* 176 */           objectName = this.objname;
/* 177 */           if (objectName == null) {
/* 178 */             objectName = Util.newObjectName("java.util.logging:type=Logging");
/* 179 */             this.objname = objectName;
/*     */           } 
/*     */         } 
/*     */       }
/* 183 */       return objectName;
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> getLoggerNames() {
/* 188 */       return LoggingSupport.getLoggerNames();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getLoggerLevel(String param1String) {
/* 193 */       return LoggingSupport.getLoggerLevel(param1String);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setLoggerLevel(String param1String1, String param1String2) {
/* 198 */       LoggingSupport.setLoggerLevel(param1String1, param1String2);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getParentLoggerName(String param1String) {
/* 203 */       return LoggingSupport.getParentLoggerName(param1String);
/*     */     }
/*     */   }
/*     */   
/* 207 */   private static List<BufferPoolMXBean> bufferPools = null; private static final String BUFFER_POOL_MXBEAN_NAME = "java.nio:type=BufferPool";
/*     */   public static synchronized List<BufferPoolMXBean> getBufferPoolMXBeans() {
/* 209 */     if (bufferPools == null) {
/* 210 */       bufferPools = new ArrayList<>(2);
/* 211 */       bufferPools.add(createBufferPoolMXBean(SharedSecrets.getJavaNioAccess()
/* 212 */             .getDirectBufferPool()));
/* 213 */       bufferPools.add(createBufferPoolMXBean(
/* 214 */             FileChannelImpl.getMappedBufferPool()));
/*     */     } 
/* 216 */     return bufferPools;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BufferPoolMXBean createBufferPoolMXBean(final JavaNioAccess.BufferPool pool) {
/* 227 */     return new BufferPoolMXBean() {
/*     */         private volatile ObjectName objname;
/*     */         
/*     */         public ObjectName getObjectName() {
/* 231 */           ObjectName objectName = this.objname;
/* 232 */           if (objectName == null) {
/* 233 */             synchronized (this) {
/* 234 */               objectName = this.objname;
/* 235 */               if (objectName == null) {
/* 236 */                 objectName = Util.newObjectName("java.nio:type=BufferPool,name=" + pool
/* 237 */                     .getName());
/* 238 */                 this.objname = objectName;
/*     */               } 
/*     */             } 
/*     */           }
/* 242 */           return objectName;
/*     */         }
/*     */         
/*     */         public String getName() {
/* 246 */           return pool.getName();
/*     */         }
/*     */         
/*     */         public long getCount() {
/* 250 */           return pool.getCount();
/*     */         }
/*     */         
/*     */         public long getTotalCapacity() {
/* 254 */           return pool.getTotalCapacity();
/*     */         }
/*     */         
/*     */         public long getMemoryUsed() {
/* 258 */           return pool.getMemoryUsed();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/* 263 */   private static HotSpotDiagnostic hsDiagMBean = null;
/* 264 */   private static HotspotRuntime hsRuntimeMBean = null;
/* 265 */   private static HotspotClassLoading hsClassMBean = null;
/* 266 */   private static HotspotThread hsThreadMBean = null;
/* 267 */   private static HotspotCompilation hsCompileMBean = null;
/* 268 */   private static HotspotMemory hsMemoryMBean = null; private static final String HOTSPOT_CLASS_LOADING_MBEAN_NAME = "sun.management:type=HotspotClassLoading"; private static final String HOTSPOT_COMPILATION_MBEAN_NAME = "sun.management:type=HotspotCompilation";
/* 269 */   private static DiagnosticCommandImpl hsDiagCommandMBean = null; private static final String HOTSPOT_MEMORY_MBEAN_NAME = "sun.management:type=HotspotMemory"; private static final String HOTSPOT_RUNTIME_MBEAN_NAME = "sun.management:type=HotspotRuntime";
/*     */   
/*     */   public static synchronized HotSpotDiagnosticMXBean getDiagnosticMXBean() {
/* 272 */     if (hsDiagMBean == null) {
/* 273 */       hsDiagMBean = new HotSpotDiagnostic();
/*     */     }
/* 275 */     return hsDiagMBean;
/*     */   }
/*     */   private static final String HOTSPOT_THREAD_MBEAN_NAME = "sun.management:type=HotspotThreading"; static final String HOTSPOT_DIAGNOSTIC_COMMAND_MBEAN_NAME = "com.sun.management:type=DiagnosticCommand"; private static final int JMM_THREAD_STATE_FLAG_MASK = -1048576;
/*     */   private static final int JMM_THREAD_STATE_FLAG_SUSPENDED = 1048576;
/*     */   private static final int JMM_THREAD_STATE_FLAG_NATIVE = 4194304;
/*     */   
/*     */   public static synchronized HotspotRuntimeMBean getHotspotRuntimeMBean() {
/* 282 */     if (hsRuntimeMBean == null) {
/* 283 */       hsRuntimeMBean = new HotspotRuntime(jvm);
/*     */     }
/* 285 */     return hsRuntimeMBean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized HotspotClassLoadingMBean getHotspotClassLoadingMBean() {
/* 292 */     if (hsClassMBean == null) {
/* 293 */       hsClassMBean = new HotspotClassLoading(jvm);
/*     */     }
/* 295 */     return hsClassMBean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized HotspotThreadMBean getHotspotThreadMBean() {
/* 302 */     if (hsThreadMBean == null) {
/* 303 */       hsThreadMBean = new HotspotThread(jvm);
/*     */     }
/* 305 */     return hsThreadMBean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized HotspotMemoryMBean getHotspotMemoryMBean() {
/* 312 */     if (hsMemoryMBean == null) {
/* 313 */       hsMemoryMBean = new HotspotMemory(jvm);
/*     */     }
/* 315 */     return hsMemoryMBean;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized DiagnosticCommandMBean getDiagnosticCommandMBean() {
/* 320 */     if (hsDiagCommandMBean == null && jvm.isRemoteDiagnosticCommandsSupported()) {
/* 321 */       hsDiagCommandMBean = new DiagnosticCommandImpl(jvm);
/*     */     }
/* 323 */     return hsDiagCommandMBean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized HotspotCompilationMBean getHotspotCompilationMBean() {
/* 330 */     if (hsCompileMBean == null) {
/* 331 */       hsCompileMBean = new HotspotCompilation(jvm);
/*     */     }
/* 333 */     return hsCompileMBean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addMBean(MBeanServer paramMBeanServer, Object paramObject, String paramString) {
/*     */     try {
/* 342 */       final ObjectName objName = Util.newObjectName(paramString);
/*     */ 
/*     */       
/* 345 */       final MBeanServer mbs0 = paramMBeanServer;
/* 346 */       final Object mbean0 = paramObject;
/* 347 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws MBeanRegistrationException, NotCompliantMBeanException {
/*     */               try {
/* 351 */                 mbs0.registerMBean(mbean0, objName);
/* 352 */                 return null;
/* 353 */               } catch (InstanceAlreadyExistsException instanceAlreadyExistsException) {
/*     */ 
/*     */ 
/*     */                 
/* 357 */                 return null;
/*     */               }  }
/*     */           });
/* 360 */     } catch (PrivilegedActionException privilegedActionException) {
/* 361 */       throw Util.newException(privilegedActionException.getException());
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
/*     */   public static HashMap<ObjectName, DynamicMBean> getPlatformDynamicMBeans() {
/* 384 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 385 */     DiagnosticCommandMBean diagnosticCommandMBean = getDiagnosticCommandMBean();
/* 386 */     if (diagnosticCommandMBean != null) {
/* 387 */       hashMap.put(Util.newObjectName("com.sun.management:type=DiagnosticCommand"), diagnosticCommandMBean);
/*     */     }
/* 389 */     return (HashMap)hashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void registerInternalMBeans(MBeanServer paramMBeanServer) {
/* 396 */     addMBean(paramMBeanServer, getHotspotClassLoadingMBean(), "sun.management:type=HotspotClassLoading");
/*     */     
/* 398 */     addMBean(paramMBeanServer, getHotspotMemoryMBean(), "sun.management:type=HotspotMemory");
/*     */     
/* 400 */     addMBean(paramMBeanServer, getHotspotRuntimeMBean(), "sun.management:type=HotspotRuntime");
/*     */     
/* 402 */     addMBean(paramMBeanServer, getHotspotThreadMBean(), "sun.management:type=HotspotThreading");
/*     */ 
/*     */ 
/*     */     
/* 406 */     if (getCompilationMXBean() != null) {
/* 407 */       addMBean(paramMBeanServer, getHotspotCompilationMBean(), "sun.management:type=HotspotCompilation");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static void unregisterMBean(MBeanServer paramMBeanServer, String paramString) {
/*     */     try {
/* 414 */       final ObjectName objName = Util.newObjectName(paramString);
/*     */ 
/*     */       
/* 417 */       final MBeanServer mbs0 = paramMBeanServer;
/* 418 */       AccessController.doPrivileged(new PrivilegedExceptionAction<Void>()
/*     */           {
/*     */             public Void run() throws MBeanRegistrationException, RuntimeOperationsException {
/*     */               try {
/* 422 */                 mbs0.unregisterMBean(objName);
/* 423 */               } catch (InstanceNotFoundException instanceNotFoundException) {}
/*     */ 
/*     */               
/* 426 */               return null;
/*     */             }
/*     */           });
/* 429 */     } catch (PrivilegedActionException privilegedActionException) {
/* 430 */       throw Util.newException(privilegedActionException.getException());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void unregisterInternalMBeans(MBeanServer paramMBeanServer) {
/* 436 */     unregisterMBean(paramMBeanServer, "sun.management:type=HotspotClassLoading");
/* 437 */     unregisterMBean(paramMBeanServer, "sun.management:type=HotspotMemory");
/* 438 */     unregisterMBean(paramMBeanServer, "sun.management:type=HotspotRuntime");
/* 439 */     unregisterMBean(paramMBeanServer, "sun.management:type=HotspotThreading");
/*     */ 
/*     */     
/* 442 */     if (getCompilationMXBean() != null) {
/* 443 */       unregisterMBean(paramMBeanServer, "sun.management:type=HotspotCompilation");
/*     */     }
/*     */   }
/*     */   
/*     */   static {
/* 448 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 451 */             System.loadLibrary("management");
/* 452 */             return null;
/*     */           }
/*     */         });
/* 455 */     jvm = new VMManagementImpl();
/*     */   }
/*     */   
/*     */   public static boolean isThreadSuspended(int paramInt) {
/* 459 */     return ((paramInt & 0x100000) != 0);
/*     */   }
/*     */   
/*     */   public static boolean isThreadRunningNative(int paramInt) {
/* 463 */     return ((paramInt & 0x400000) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Thread.State toThreadState(int paramInt) {
/* 468 */     int i = paramInt & 0xFFFFF;
/* 469 */     return VM.toThreadState(i);
/*     */   }
/*     */   
/*     */   public static interface LoggingMXBean extends PlatformLoggingMXBean, java.util.logging.LoggingMXBean {}
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/ManagementFactoryHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */