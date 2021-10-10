/*     */ package java.sql;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.ServiceLoader;
/*     */ import java.util.Vector;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import sun.reflect.CallerSensitive;
/*     */ import sun.reflect.Reflection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DriverManager
/*     */ {
/*  85 */   private static final CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList<>();
/*  86 */   private static volatile int loginTimeout = 0;
/*  87 */   private static volatile PrintWriter logWriter = null;
/*  88 */   private static volatile PrintStream logStream = null;
/*     */   
/*  90 */   private static final Object logSync = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 101 */     loadInitialDrivers();
/* 102 */     println("JDBC DriverManager initialized");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   static final SQLPermission SET_LOG_PERMISSION = new SQLPermission("setLog");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   static final SQLPermission DEREGISTER_DRIVER_PERMISSION = new SQLPermission("deregisterDriver");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PrintWriter getLogWriter() {
/* 134 */     return logWriter;
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
/*     */   public static void setLogWriter(PrintWriter paramPrintWriter) {
/* 169 */     SecurityManager securityManager = System.getSecurityManager();
/* 170 */     if (securityManager != null) {
/* 171 */       securityManager.checkPermission(SET_LOG_PERMISSION);
/*     */     }
/* 173 */     logStream = null;
/* 174 */     logWriter = paramPrintWriter;
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
/*     */   @CallerSensitive
/*     */   public static Connection getConnection(String paramString, Properties paramProperties) throws SQLException {
/* 208 */     return getConnection(paramString, paramProperties, Reflection.getCallerClass());
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
/*     */   @CallerSensitive
/*     */   public static Connection getConnection(String paramString1, String paramString2, String paramString3) throws SQLException {
/* 238 */     Properties properties = new Properties();
/*     */     
/* 240 */     if (paramString2 != null) {
/* 241 */       properties.put("user", paramString2);
/*     */     }
/* 243 */     if (paramString3 != null) {
/* 244 */       properties.put("password", paramString3);
/*     */     }
/*     */     
/* 247 */     return getConnection(paramString1, properties, Reflection.getCallerClass());
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
/*     */   @CallerSensitive
/*     */   public static Connection getConnection(String paramString) throws SQLException {
/* 269 */     Properties properties = new Properties();
/* 270 */     return getConnection(paramString, properties, Reflection.getCallerClass());
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
/*     */   @CallerSensitive
/*     */   public static Driver getDriver(String paramString) throws SQLException {
/* 288 */     println("DriverManager.getDriver(\"" + paramString + "\")");
/*     */     
/* 290 */     Class<?> clazz = Reflection.getCallerClass();
/*     */ 
/*     */ 
/*     */     
/* 294 */     for (DriverInfo driverInfo : registeredDrivers) {
/*     */ 
/*     */       
/* 297 */       if (isDriverAllowed(driverInfo.driver, clazz)) {
/*     */         try {
/* 299 */           if (driverInfo.driver.acceptsURL(paramString))
/*     */           {
/* 301 */             println("getDriver returning " + driverInfo.driver.getClass().getName());
/* 302 */             return driverInfo.driver;
/*     */           }
/*     */         
/* 305 */         } catch (SQLException sQLException) {}
/*     */         
/*     */         continue;
/*     */       } 
/* 309 */       println("    skipping: " + driverInfo.driver.getClass().getName());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 314 */     println("getDriver: no suitable driver");
/* 315 */     throw new SQLException("No suitable driver", "08001");
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
/*     */   public static synchronized void registerDriver(Driver paramDriver) throws SQLException {
/* 334 */     registerDriver(paramDriver, null);
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
/*     */   public static synchronized void registerDriver(Driver paramDriver, DriverAction paramDriverAction) throws SQLException {
/* 357 */     if (paramDriver != null) {
/* 358 */       registeredDrivers.addIfAbsent(new DriverInfo(paramDriver, paramDriverAction));
/*     */     } else {
/*     */       
/* 361 */       throw new NullPointerException();
/*     */     } 
/*     */     
/* 364 */     println("registerDriver: " + paramDriver);
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
/*     */   @CallerSensitive
/*     */   public static synchronized void deregisterDriver(Driver paramDriver) throws SQLException {
/* 396 */     if (paramDriver == null) {
/*     */       return;
/*     */     }
/*     */     
/* 400 */     SecurityManager securityManager = System.getSecurityManager();
/* 401 */     if (securityManager != null) {
/* 402 */       securityManager.checkPermission(DEREGISTER_DRIVER_PERMISSION);
/*     */     }
/*     */     
/* 405 */     println("DriverManager.deregisterDriver: " + paramDriver);
/*     */     
/* 407 */     DriverInfo driverInfo = new DriverInfo(paramDriver, null);
/* 408 */     if (registeredDrivers.contains(driverInfo)) {
/* 409 */       if (isDriverAllowed(paramDriver, Reflection.getCallerClass())) {
/* 410 */         DriverInfo driverInfo1 = registeredDrivers.get(registeredDrivers.indexOf(driverInfo));
/*     */ 
/*     */         
/* 413 */         if (driverInfo1.action() != null) {
/* 414 */           driverInfo1.action().deregister();
/*     */         }
/* 416 */         registeredDrivers.remove(driverInfo);
/*     */       }
/*     */       else {
/*     */         
/* 420 */         throw new SecurityException();
/*     */       } 
/*     */     } else {
/* 423 */       println("    couldn't find driver to unload");
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
/*     */   @CallerSensitive
/*     */   public static Enumeration<Driver> getDrivers() {
/* 438 */     Vector<Driver> vector = new Vector();
/*     */     
/* 440 */     Class<?> clazz = Reflection.getCallerClass();
/*     */ 
/*     */     
/* 443 */     for (DriverInfo driverInfo : registeredDrivers) {
/*     */ 
/*     */       
/* 446 */       if (isDriverAllowed(driverInfo.driver, clazz)) {
/* 447 */         vector.addElement(driverInfo.driver); continue;
/*     */       } 
/* 449 */       println("    skipping: " + driverInfo.getClass().getName());
/*     */     } 
/*     */     
/* 452 */     return vector.elements();
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
/*     */   public static void setLoginTimeout(int paramInt) {
/* 465 */     loginTimeout = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getLoginTimeout() {
/* 476 */     return loginTimeout;
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
/*     */   @Deprecated
/*     */   public static void setLogStream(PrintStream paramPrintStream) {
/* 501 */     SecurityManager securityManager = System.getSecurityManager();
/* 502 */     if (securityManager != null) {
/* 503 */       securityManager.checkPermission(SET_LOG_PERMISSION);
/*     */     }
/*     */     
/* 506 */     logStream = paramPrintStream;
/* 507 */     if (paramPrintStream != null) {
/* 508 */       logWriter = new PrintWriter(paramPrintStream);
/*     */     } else {
/* 510 */       logWriter = null;
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
/*     */   @Deprecated
/*     */   public static PrintStream getLogStream() {
/* 523 */     return logStream;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void println(String paramString) {
/* 532 */     synchronized (logSync) {
/* 533 */       if (logWriter != null) {
/* 534 */         logWriter.println(paramString);
/*     */ 
/*     */         
/* 537 */         logWriter.flush();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isDriverAllowed(Driver paramDriver, Class<?> paramClass) {
/* 547 */     ClassLoader classLoader = (paramClass != null) ? paramClass.getClassLoader() : null;
/* 548 */     return isDriverAllowed(paramDriver, classLoader);
/*     */   }
/*     */   
/*     */   private static boolean isDriverAllowed(Driver paramDriver, ClassLoader paramClassLoader) {
/* 552 */     boolean bool = false;
/* 553 */     if (paramDriver != null) {
/* 554 */       Class<?> clazz = null;
/*     */       try {
/* 556 */         clazz = Class.forName(paramDriver.getClass().getName(), true, paramClassLoader);
/* 557 */       } catch (Exception exception) {
/* 558 */         bool = false;
/*     */       } 
/*     */       
/* 561 */       bool = (clazz == paramDriver.getClass()) ? true : false;
/*     */     } 
/*     */     
/* 564 */     return bool;
/*     */   }
/*     */   
/*     */   private static void loadInitialDrivers() {
/*     */     String str;
/*     */     try {
/* 570 */       str = AccessController.<String>doPrivileged(new PrivilegedAction<String>() {
/*     */             public String run() {
/* 572 */               return System.getProperty("jdbc.drivers");
/*     */             }
/*     */           });
/* 575 */     } catch (Exception exception) {
/* 576 */       str = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 583 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/* 586 */             ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
/* 587 */             Iterator<Driver> iterator = serviceLoader.iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 602 */               while (iterator.hasNext()) {
/* 603 */                 iterator.next();
/*     */               }
/* 605 */             } catch (Throwable throwable) {}
/*     */ 
/*     */             
/* 608 */             return null;
/*     */           }
/*     */         });
/*     */     
/* 612 */     println("DriverManager.initialize: jdbc.drivers = " + str);
/*     */     
/* 614 */     if (str == null || str.equals("")) {
/*     */       return;
/*     */     }
/* 617 */     String[] arrayOfString = str.split(":");
/* 618 */     println("number of Drivers:" + arrayOfString.length);
/* 619 */     for (String str1 : arrayOfString) {
/*     */       try {
/* 621 */         println("DriverManager.Initialize: loading " + str1);
/* 622 */         Class.forName(str1, true, 
/* 623 */             ClassLoader.getSystemClassLoader());
/* 624 */       } catch (Exception exception) {
/* 625 */         println("DriverManager.Initialize: load failed: " + exception);
/*     */       } 
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
/*     */   private static Connection getConnection(String paramString, Properties paramProperties, Class<?> paramClass) throws SQLException {
/* 640 */     ClassLoader classLoader = (paramClass != null) ? paramClass.getClassLoader() : null;
/* 641 */     synchronized (DriverManager.class) {
/*     */       
/* 643 */       if (classLoader == null) {
/* 644 */         classLoader = Thread.currentThread().getContextClassLoader();
/*     */       }
/*     */     } 
/*     */     
/* 648 */     if (paramString == null) {
/* 649 */       throw new SQLException("The url cannot be null", "08001");
/*     */     }
/*     */     
/* 652 */     println("DriverManager.getConnection(\"" + paramString + "\")");
/*     */ 
/*     */ 
/*     */     
/* 656 */     SQLException sQLException = null;
/*     */     
/* 658 */     for (DriverInfo driverInfo : registeredDrivers) {
/*     */ 
/*     */       
/* 661 */       if (isDriverAllowed(driverInfo.driver, classLoader)) {
/*     */         try {
/* 663 */           println("    trying " + driverInfo.driver.getClass().getName());
/* 664 */           Connection connection = driverInfo.driver.connect(paramString, paramProperties);
/* 665 */           if (connection != null) {
/*     */             
/* 667 */             println("getConnection returning " + driverInfo.driver.getClass().getName());
/* 668 */             return connection;
/*     */           } 
/* 670 */         } catch (SQLException sQLException1) {
/* 671 */           if (sQLException == null) {
/* 672 */             sQLException = sQLException1;
/*     */           }
/*     */         } 
/*     */         continue;
/*     */       } 
/* 677 */       println("    skipping: " + driverInfo.getClass().getName());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 683 */     if (sQLException != null) {
/* 684 */       println("getConnection failed: " + sQLException);
/* 685 */       throw sQLException;
/*     */     } 
/*     */     
/* 688 */     println("getConnection: no suitable driver found for " + paramString);
/* 689 */     throw new SQLException("No suitable driver found for " + paramString, "08001");
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/sql/DriverManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */