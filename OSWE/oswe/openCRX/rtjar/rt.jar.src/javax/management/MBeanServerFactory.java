/*     */ package javax.management;
/*     */ 
/*     */ import com.sun.jmx.defaults.JmxProperties;
/*     */ import com.sun.jmx.mbeanserver.GetPropertyAction;
/*     */ import java.security.AccessController;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import javax.management.loading.ClassLoaderRepository;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanServerFactory
/*     */ {
/* 101 */   private static MBeanServerBuilder builder = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void releaseMBeanServer(MBeanServer paramMBeanServer) {
/* 152 */     checkPermission("releaseMBeanServer");
/*     */     
/* 154 */     removeMBeanServer(paramMBeanServer);
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
/*     */   public static MBeanServer createMBeanServer() {
/* 192 */     return createMBeanServer(null);
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
/*     */   public static MBeanServer createMBeanServer(String paramString) {
/* 229 */     checkPermission("createMBeanServer");
/*     */     
/* 231 */     MBeanServer mBeanServer = newMBeanServer(paramString);
/* 232 */     addMBeanServer(mBeanServer);
/* 233 */     return mBeanServer;
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
/*     */   public static MBeanServer newMBeanServer() {
/* 273 */     return newMBeanServer(null);
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
/*     */   public static MBeanServer newMBeanServer(String paramString) {
/* 312 */     checkPermission("newMBeanServer");
/*     */ 
/*     */ 
/*     */     
/* 316 */     MBeanServerBuilder mBeanServerBuilder = getNewMBeanServerBuilder();
/*     */ 
/*     */     
/* 319 */     synchronized (mBeanServerBuilder) {
/*     */       
/* 321 */       MBeanServerDelegate mBeanServerDelegate = mBeanServerBuilder.newMBeanServerDelegate();
/* 322 */       if (mBeanServerDelegate == null)
/*     */       {
/*     */ 
/*     */         
/* 326 */         throw new JMRuntimeException("MBeanServerBuilder.newMBeanServerDelegate() returned null");
/*     */       }
/*     */       
/* 329 */       MBeanServer mBeanServer = mBeanServerBuilder.newMBeanServer(paramString, null, mBeanServerDelegate);
/* 330 */       if (mBeanServer == null)
/*     */       {
/*     */         
/* 333 */         throw new JMRuntimeException("MBeanServerBuilder.newMBeanServer() returned null");
/*     */       }
/* 335 */       return mBeanServer;
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
/*     */   public static synchronized ArrayList<MBeanServer> findMBeanServer(String paramString) {
/* 361 */     checkPermission("findMBeanServer");
/*     */     
/* 363 */     if (paramString == null) {
/* 364 */       return new ArrayList<>(mBeanServerList);
/*     */     }
/* 366 */     ArrayList<MBeanServer> arrayList = new ArrayList();
/* 367 */     for (MBeanServer mBeanServer : mBeanServerList) {
/* 368 */       String str = mBeanServerId(mBeanServer);
/* 369 */       if (paramString.equals(str))
/* 370 */         arrayList.add(mBeanServer); 
/*     */     } 
/* 372 */     return arrayList;
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
/*     */   public static ClassLoaderRepository getClassLoaderRepository(MBeanServer paramMBeanServer) {
/* 394 */     return paramMBeanServer.getClassLoaderRepository();
/*     */   }
/*     */   
/*     */   private static String mBeanServerId(MBeanServer paramMBeanServer) {
/*     */     try {
/* 399 */       return (String)paramMBeanServer.getAttribute(MBeanServerDelegate.DELEGATE_NAME, "MBeanServerId");
/*     */     }
/* 401 */     catch (JMException jMException) {
/* 402 */       JmxProperties.MISC_LOGGER.finest("Ignoring exception while getting MBeanServerId: " + jMException);
/*     */       
/* 404 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void checkPermission(String paramString) throws SecurityException {
/* 410 */     SecurityManager securityManager = System.getSecurityManager();
/* 411 */     if (securityManager != null) {
/* 412 */       MBeanServerPermission mBeanServerPermission = new MBeanServerPermission(paramString);
/* 413 */       securityManager.checkPermission(mBeanServerPermission);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static synchronized void addMBeanServer(MBeanServer paramMBeanServer) {
/* 418 */     mBeanServerList.add(paramMBeanServer);
/*     */   }
/*     */   
/*     */   private static synchronized void removeMBeanServer(MBeanServer paramMBeanServer) {
/* 422 */     boolean bool = mBeanServerList.remove(paramMBeanServer);
/* 423 */     if (!bool) {
/* 424 */       JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINER, MBeanServerFactory.class
/* 425 */           .getName(), "removeMBeanServer(MBeanServer)", "MBeanServer was not in list!");
/*     */ 
/*     */       
/* 428 */       throw new IllegalArgumentException("MBeanServer was not in list!");
/*     */     } 
/*     */   }
/*     */   
/* 432 */   private static final ArrayList<MBeanServer> mBeanServerList = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> loadBuilderClass(String paramString) throws ClassNotFoundException {
/* 442 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */     
/* 444 */     if (classLoader != null)
/*     */     {
/* 446 */       return classLoader.loadClass(paramString);
/*     */     }
/*     */ 
/*     */     
/* 450 */     return ReflectUtil.forName(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static MBeanServerBuilder newBuilder(Class<?> paramClass) {
/*     */     try {
/* 461 */       Object object = paramClass.newInstance();
/* 462 */       return (MBeanServerBuilder)object;
/* 463 */     } catch (RuntimeException runtimeException) {
/* 464 */       throw runtimeException;
/* 465 */     } catch (Exception exception) {
/* 466 */       String str = "Failed to instantiate a MBeanServerBuilder from " + paramClass + ": " + exception;
/*     */ 
/*     */       
/* 469 */       throw new JMRuntimeException(str, exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized void checkMBeanServerBuilder() {
/*     */     try {
/* 479 */       GetPropertyAction getPropertyAction = new GetPropertyAction("javax.management.builder.initial");
/*     */       
/* 481 */       String str = AccessController.<String>doPrivileged(getPropertyAction);
/*     */       
/*     */       try {
/*     */         Class<?> clazz;
/* 485 */         if (str == null || str.length() == 0) {
/* 486 */           clazz = MBeanServerBuilder.class;
/*     */         } else {
/* 488 */           clazz = loadBuilderClass(str);
/*     */         } 
/*     */         
/* 491 */         if (builder != null) {
/* 492 */           Class<?> clazz1 = builder.getClass();
/* 493 */           if (clazz == clazz1) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */         
/* 498 */         builder = newBuilder(clazz);
/* 499 */       } catch (ClassNotFoundException classNotFoundException) {
/* 500 */         String str1 = "Failed to load MBeanServerBuilder class " + str + ": " + classNotFoundException;
/*     */ 
/*     */         
/* 503 */         throw new JMRuntimeException(str1, classNotFoundException);
/*     */       } 
/* 505 */     } catch (RuntimeException runtimeException) {
/* 506 */       if (JmxProperties.MBEANSERVER_LOGGER.isLoggable(Level.FINEST)) {
/*     */ 
/*     */ 
/*     */         
/* 510 */         StringBuilder stringBuilder = (new StringBuilder()).append("Failed to instantiate MBeanServerBuilder: ").append(runtimeException).append("\n\t\tCheck the value of the ").append("javax.management.builder.initial").append(" property.");
/* 511 */         JmxProperties.MBEANSERVER_LOGGER.logp(Level.FINEST, MBeanServerFactory.class
/* 512 */             .getName(), "checkMBeanServerBuilder", stringBuilder
/*     */             
/* 514 */             .toString());
/*     */       } 
/* 516 */       throw runtimeException;
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
/*     */   private static synchronized MBeanServerBuilder getNewMBeanServerBuilder() {
/* 539 */     checkMBeanServerBuilder();
/* 540 */     return builder;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/management/MBeanServerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */