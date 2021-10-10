/*      */ package java.util.logging;
/*      */ 
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.concurrent.CopyOnWriteArrayList;
/*      */ import java.util.function.Supplier;
/*      */ import sun.reflect.CallerSensitive;
/*      */ import sun.reflect.Reflection;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Logger
/*      */ {
/*  219 */   private static final Handler[] emptyHandlers = new Handler[0];
/*  220 */   private static final int offValue = Level.OFF.intValue();
/*      */   static final String SYSTEM_LOGGER_RB_NAME = "sun.util.logging.resources.logging";
/*      */   
/*      */   private static final class LoggerBundle
/*      */   {
/*      */     final String resourceBundleName;
/*      */     final ResourceBundle userBundle;
/*      */     
/*      */     private LoggerBundle(String param1String, ResourceBundle param1ResourceBundle) {
/*  229 */       this.resourceBundleName = param1String;
/*  230 */       this.userBundle = param1ResourceBundle;
/*      */     }
/*      */     boolean isSystemBundle() {
/*  233 */       return "sun.util.logging.resources.logging".equals(this.resourceBundleName);
/*      */     }
/*      */     static LoggerBundle get(String param1String, ResourceBundle param1ResourceBundle) {
/*  236 */       if (param1String == null && param1ResourceBundle == null)
/*  237 */         return Logger.NO_RESOURCE_BUNDLE; 
/*  238 */       if ("sun.util.logging.resources.logging".equals(param1String) && param1ResourceBundle == null) {
/*  239 */         return Logger.SYSTEM_BUNDLE;
/*      */       }
/*  241 */       return new LoggerBundle(param1String, param1ResourceBundle);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  248 */   private static final LoggerBundle SYSTEM_BUNDLE = new LoggerBundle("sun.util.logging.resources.logging", null);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  253 */   private static final LoggerBundle NO_RESOURCE_BUNDLE = new LoggerBundle(null, null);
/*      */   
/*      */   private volatile LogManager manager;
/*      */   
/*      */   private String name;
/*  258 */   private final CopyOnWriteArrayList<Handler> handlers = new CopyOnWriteArrayList<>();
/*      */   
/*  260 */   private volatile LoggerBundle loggerBundle = NO_RESOURCE_BUNDLE;
/*      */   
/*      */   private volatile boolean useParentHandlers = true;
/*      */   
/*      */   private volatile Filter filter;
/*      */   
/*      */   private boolean anonymous;
/*      */   
/*      */   private ResourceBundle catalog;
/*      */   
/*      */   private String catalogName;
/*      */   private Locale catalogLocale;
/*  272 */   private static final Object treeLock = new Object();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile Logger parent;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ArrayList<LogManager.LoggerWeakRef> kids;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile Level levelObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private volatile int levelValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WeakReference<ClassLoader> callersClassLoaderRef;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean isSystemLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String GLOBAL_LOGGER_NAME = "global";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final Logger getGlobal() {
/*  321 */     LogManager.getLogManager();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  329 */     return global;
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
/*      */   @Deprecated
/*  354 */   public static final Logger global = new Logger("global");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Logger(String paramString1, String paramString2) {
/*  374 */     this(paramString1, paramString2, null, LogManager.getLogManager(), false);
/*      */   }
/*      */   
/*      */   Logger(String paramString1, String paramString2, Class<?> paramClass, LogManager paramLogManager, boolean paramBoolean) {
/*  378 */     this.manager = paramLogManager;
/*  379 */     this.isSystemLogger = paramBoolean;
/*  380 */     setupResourceInfo(paramString2, paramClass);
/*  381 */     this.name = paramString1;
/*  382 */     this.levelValue = Level.INFO.intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   private void setCallersClassLoaderRef(Class<?> paramClass) {
/*  387 */     ClassLoader classLoader = (paramClass != null) ? paramClass.getClassLoader() : null;
/*      */     
/*  389 */     if (classLoader != null) {
/*  390 */       this.callersClassLoaderRef = new WeakReference<>(classLoader);
/*      */     }
/*      */   }
/*      */   
/*      */   private ClassLoader getCallersClassLoader() {
/*  395 */     return (this.callersClassLoaderRef != null) ? this.callersClassLoaderRef
/*  396 */       .get() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Logger(String paramString) {
/*  405 */     this.name = paramString;
/*  406 */     this.isSystemLogger = true;
/*  407 */     this.levelValue = Level.INFO.intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void setLogManager(LogManager paramLogManager) {
/*  413 */     this.manager = paramLogManager;
/*      */   }
/*      */   
/*      */   private void checkPermission() throws SecurityException {
/*  417 */     if (!this.anonymous) {
/*  418 */       if (this.manager == null)
/*      */       {
/*  420 */         this.manager = LogManager.getLogManager();
/*      */       }
/*  422 */       this.manager.checkPermission();
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
/*      */   private static class SystemLoggerHelper
/*      */   {
/*  435 */     static boolean disableCallerCheck = getBooleanProperty("sun.util.logging.disableCallerCheck");
/*      */     private static boolean getBooleanProperty(final String key) {
/*  437 */       String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*      */           {
/*      */             public String run() {
/*  440 */               return System.getProperty(key);
/*      */             }
/*      */           });
/*  443 */       return Boolean.valueOf(str).booleanValue();
/*      */     }
/*      */   }
/*      */   
/*      */   private static Logger demandLogger(String paramString1, String paramString2, Class<?> paramClass) {
/*  448 */     LogManager logManager = LogManager.getLogManager();
/*  449 */     SecurityManager securityManager = System.getSecurityManager();
/*  450 */     if (securityManager != null && !SystemLoggerHelper.disableCallerCheck && 
/*  451 */       paramClass.getClassLoader() == null) {
/*  452 */       return logManager.demandSystemLogger(paramString1, paramString2);
/*      */     }
/*      */     
/*  455 */     return logManager.demandLogger(paramString1, paramString2, paramClass);
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
/*      */   @CallerSensitive
/*      */   public static Logger getLogger(String paramString) {
/*  502 */     return demandLogger(paramString, null, Reflection.getCallerClass());
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
/*      */   @CallerSensitive
/*      */   public static Logger getLogger(String paramString1, String paramString2) {
/*  552 */     Class<?> clazz = Reflection.getCallerClass();
/*  553 */     Logger logger = demandLogger(paramString1, paramString2, clazz);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  564 */     logger.setupResourceInfo(paramString2, clazz);
/*  565 */     return logger;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Logger getPlatformLogger(String paramString) {
/*  572 */     LogManager logManager = LogManager.getLogManager();
/*      */ 
/*      */ 
/*      */     
/*  576 */     return logManager.demandSystemLogger(paramString, "sun.util.logging.resources.logging");
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
/*      */   public static Logger getAnonymousLogger() {
/*  603 */     return getAnonymousLogger(null);
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
/*      */   @CallerSensitive
/*      */   public static Logger getAnonymousLogger(String paramString) {
/*  637 */     LogManager logManager = LogManager.getLogManager();
/*      */     
/*  639 */     logManager.drainLoggerRefQueueBounded();
/*      */     
/*  641 */     Logger logger1 = new Logger(null, paramString, Reflection.getCallerClass(), logManager, false);
/*  642 */     logger1.anonymous = true;
/*  643 */     Logger logger2 = logManager.getLogger("");
/*  644 */     logger1.doSetParent(logger2);
/*  645 */     return logger1;
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
/*      */   public ResourceBundle getResourceBundle() {
/*  664 */     return findResourceBundle(getResourceBundleName(), true);
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
/*      */   public String getResourceBundleName() {
/*  681 */     return this.loggerBundle.resourceBundleName;
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
/*      */   public void setFilter(Filter paramFilter) throws SecurityException {
/*  697 */     checkPermission();
/*  698 */     this.filter = paramFilter;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Filter getFilter() {
/*  707 */     return this.filter;
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
/*      */   public void log(LogRecord paramLogRecord) {
/*  720 */     if (!isLoggable(paramLogRecord.getLevel())) {
/*      */       return;
/*      */     }
/*  723 */     Filter filter = this.filter;
/*  724 */     if (filter != null && !filter.isLoggable(paramLogRecord)) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  731 */     Logger logger = this;
/*  732 */     while (logger != null) {
/*      */ 
/*      */       
/*  735 */       Handler[] arrayOfHandler = this.isSystemLogger ? logger.accessCheckedHandlers() : logger.getHandlers();
/*      */       
/*  737 */       for (Handler handler : arrayOfHandler) {
/*  738 */         handler.publish(paramLogRecord);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  743 */       boolean bool = this.isSystemLogger ? logger.useParentHandlers : logger.getUseParentHandlers();
/*      */       
/*  745 */       if (!bool) {
/*      */         break;
/*      */       }
/*      */       
/*  749 */       logger = this.isSystemLogger ? logger.parent : logger.getParent();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doLog(LogRecord paramLogRecord) {
/*  757 */     paramLogRecord.setLoggerName(this.name);
/*  758 */     LoggerBundle loggerBundle = getEffectiveLoggerBundle();
/*  759 */     ResourceBundle resourceBundle = loggerBundle.userBundle;
/*  760 */     String str = loggerBundle.resourceBundleName;
/*  761 */     if (str != null && resourceBundle != null) {
/*  762 */       paramLogRecord.setResourceBundleName(str);
/*  763 */       paramLogRecord.setResourceBundle(resourceBundle);
/*      */     } 
/*  765 */     log(paramLogRecord);
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
/*      */   public void log(Level paramLevel, String paramString) {
/*  784 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/*  787 */     LogRecord logRecord = new LogRecord(paramLevel, paramString);
/*  788 */     doLog(logRecord);
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
/*      */   public void log(Level paramLevel, Supplier<String> paramSupplier) {
/*  806 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/*  809 */     LogRecord logRecord = new LogRecord(paramLevel, paramSupplier.get());
/*  810 */     doLog(logRecord);
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
/*      */   public void log(Level paramLevel, String paramString, Object paramObject) {
/*  825 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/*  828 */     LogRecord logRecord = new LogRecord(paramLevel, paramString);
/*  829 */     Object[] arrayOfObject = { paramObject };
/*  830 */     logRecord.setParameters(arrayOfObject);
/*  831 */     doLog(logRecord);
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
/*      */   public void log(Level paramLevel, String paramString, Object[] paramArrayOfObject) {
/*  846 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/*  849 */     LogRecord logRecord = new LogRecord(paramLevel, paramString);
/*  850 */     logRecord.setParameters(paramArrayOfObject);
/*  851 */     doLog(logRecord);
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
/*      */   public void log(Level paramLevel, String paramString, Throwable paramThrowable) {
/*  871 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/*  874 */     LogRecord logRecord = new LogRecord(paramLevel, paramString);
/*  875 */     logRecord.setThrown(paramThrowable);
/*  876 */     doLog(logRecord);
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
/*      */   public void log(Level paramLevel, Throwable paramThrowable, Supplier<String> paramSupplier) {
/*  899 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/*  902 */     LogRecord logRecord = new LogRecord(paramLevel, paramSupplier.get());
/*  903 */     logRecord.setThrown(paramThrowable);
/*  904 */     doLog(logRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, String paramString3) {
/*  925 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/*  928 */     LogRecord logRecord = new LogRecord(paramLevel, paramString3);
/*  929 */     logRecord.setSourceClassName(paramString1);
/*  930 */     logRecord.setSourceMethodName(paramString2);
/*  931 */     doLog(logRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, Supplier<String> paramSupplier) {
/*  952 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/*  955 */     LogRecord logRecord = new LogRecord(paramLevel, paramSupplier.get());
/*  956 */     logRecord.setSourceClassName(paramString1);
/*  957 */     logRecord.setSourceMethodName(paramString2);
/*  958 */     doLog(logRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, String paramString3, Object paramObject) {
/*  977 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/*  980 */     LogRecord logRecord = new LogRecord(paramLevel, paramString3);
/*  981 */     logRecord.setSourceClassName(paramString1);
/*  982 */     logRecord.setSourceMethodName(paramString2);
/*  983 */     Object[] arrayOfObject = { paramObject };
/*  984 */     logRecord.setParameters(arrayOfObject);
/*  985 */     doLog(logRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, String paramString3, Object[] paramArrayOfObject) {
/* 1004 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/* 1007 */     LogRecord logRecord = new LogRecord(paramLevel, paramString3);
/* 1008 */     logRecord.setSourceClassName(paramString1);
/* 1009 */     logRecord.setSourceMethodName(paramString2);
/* 1010 */     logRecord.setParameters(paramArrayOfObject);
/* 1011 */     doLog(logRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, String paramString3, Throwable paramThrowable) {
/* 1035 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/* 1038 */     LogRecord logRecord = new LogRecord(paramLevel, paramString3);
/* 1039 */     logRecord.setSourceClassName(paramString1);
/* 1040 */     logRecord.setSourceMethodName(paramString2);
/* 1041 */     logRecord.setThrown(paramThrowable);
/* 1042 */     doLog(logRecord);
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
/*      */   public void logp(Level paramLevel, String paramString1, String paramString2, Throwable paramThrowable, Supplier<String> paramSupplier) {
/* 1069 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/* 1072 */     LogRecord logRecord = new LogRecord(paramLevel, paramSupplier.get());
/* 1073 */     logRecord.setSourceClassName(paramString1);
/* 1074 */     logRecord.setSourceMethodName(paramString2);
/* 1075 */     logRecord.setThrown(paramThrowable);
/* 1076 */     doLog(logRecord);
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
/*      */   private void doLog(LogRecord paramLogRecord, String paramString) {
/* 1088 */     paramLogRecord.setLoggerName(this.name);
/* 1089 */     if (paramString != null) {
/* 1090 */       paramLogRecord.setResourceBundleName(paramString);
/* 1091 */       paramLogRecord.setResourceBundle(findResourceBundle(paramString, false));
/*      */     } 
/* 1093 */     log(paramLogRecord);
/*      */   }
/*      */ 
/*      */   
/*      */   private void doLog(LogRecord paramLogRecord, ResourceBundle paramResourceBundle) {
/* 1098 */     paramLogRecord.setLoggerName(this.name);
/* 1099 */     if (paramResourceBundle != null) {
/* 1100 */       paramLogRecord.setResourceBundleName(paramResourceBundle.getBaseBundleName());
/* 1101 */       paramLogRecord.setResourceBundle(paramResourceBundle);
/*      */     } 
/* 1103 */     log(paramLogRecord);
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
/*      */   @Deprecated
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, String paramString3, String paramString4) {
/* 1131 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/* 1134 */     LogRecord logRecord = new LogRecord(paramLevel, paramString4);
/* 1135 */     logRecord.setSourceClassName(paramString1);
/* 1136 */     logRecord.setSourceMethodName(paramString2);
/* 1137 */     doLog(logRecord, paramString3);
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
/*      */   @Deprecated
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, String paramString3, String paramString4, Object paramObject) {
/* 1166 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/* 1169 */     LogRecord logRecord = new LogRecord(paramLevel, paramString4);
/* 1170 */     logRecord.setSourceClassName(paramString1);
/* 1171 */     logRecord.setSourceMethodName(paramString2);
/* 1172 */     Object[] arrayOfObject = { paramObject };
/* 1173 */     logRecord.setParameters(arrayOfObject);
/* 1174 */     doLog(logRecord, paramString3);
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
/*      */   @Deprecated
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, String paramString3, String paramString4, Object[] paramArrayOfObject) {
/* 1203 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/* 1206 */     LogRecord logRecord = new LogRecord(paramLevel, paramString4);
/* 1207 */     logRecord.setSourceClassName(paramString1);
/* 1208 */     logRecord.setSourceMethodName(paramString2);
/* 1209 */     logRecord.setParameters(paramArrayOfObject);
/* 1210 */     doLog(logRecord, paramString3);
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
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, ResourceBundle paramResourceBundle, String paramString3, Object... paramVarArgs) {
/* 1236 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/* 1239 */     LogRecord logRecord = new LogRecord(paramLevel, paramString3);
/* 1240 */     logRecord.setSourceClassName(paramString1);
/* 1241 */     logRecord.setSourceMethodName(paramString2);
/* 1242 */     if (paramVarArgs != null && paramVarArgs.length != 0) {
/* 1243 */       logRecord.setParameters(paramVarArgs);
/*      */     }
/* 1245 */     doLog(logRecord, paramResourceBundle);
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
/*      */   @Deprecated
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, String paramString3, String paramString4, Throwable paramThrowable) {
/* 1279 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/* 1282 */     LogRecord logRecord = new LogRecord(paramLevel, paramString4);
/* 1283 */     logRecord.setSourceClassName(paramString1);
/* 1284 */     logRecord.setSourceMethodName(paramString2);
/* 1285 */     logRecord.setThrown(paramThrowable);
/* 1286 */     doLog(logRecord, paramString3);
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
/*      */   public void logrb(Level paramLevel, String paramString1, String paramString2, ResourceBundle paramResourceBundle, String paramString3, Throwable paramThrowable) {
/* 1317 */     if (!isLoggable(paramLevel)) {
/*      */       return;
/*      */     }
/* 1320 */     LogRecord logRecord = new LogRecord(paramLevel, paramString3);
/* 1321 */     logRecord.setSourceClassName(paramString1);
/* 1322 */     logRecord.setSourceMethodName(paramString2);
/* 1323 */     logRecord.setThrown(paramThrowable);
/* 1324 */     doLog(logRecord, paramResourceBundle);
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
/*      */   public void entering(String paramString1, String paramString2) {
/* 1342 */     logp(Level.FINER, paramString1, paramString2, "ENTRY");
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
/*      */   public void entering(String paramString1, String paramString2, Object paramObject) {
/* 1358 */     logp(Level.FINER, paramString1, paramString2, "ENTRY {0}", paramObject);
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
/*      */   public void entering(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1375 */     String str = "ENTRY";
/* 1376 */     if (paramArrayOfObject == null) {
/* 1377 */       logp(Level.FINER, paramString1, paramString2, str);
/*      */       return;
/*      */     } 
/* 1380 */     if (!isLoggable(Level.FINER))
/* 1381 */       return;  for (byte b = 0; b < paramArrayOfObject.length; b++) {
/* 1382 */       str = str + " {" + b + "}";
/*      */     }
/* 1384 */     logp(Level.FINER, paramString1, paramString2, str, paramArrayOfObject);
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
/*      */   public void exiting(String paramString1, String paramString2) {
/* 1398 */     logp(Level.FINER, paramString1, paramString2, "RETURN");
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
/*      */   public void exiting(String paramString1, String paramString2, Object paramObject) {
/* 1415 */     logp(Level.FINER, paramString1, paramString2, "RETURN {0}", paramObject);
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
/*      */   public void throwing(String paramString1, String paramString2, Throwable paramThrowable) {
/* 1440 */     if (!isLoggable(Level.FINER)) {
/*      */       return;
/*      */     }
/* 1443 */     LogRecord logRecord = new LogRecord(Level.FINER, "THROW");
/* 1444 */     logRecord.setSourceClassName(paramString1);
/* 1445 */     logRecord.setSourceMethodName(paramString2);
/* 1446 */     logRecord.setThrown(paramThrowable);
/* 1447 */     doLog(logRecord);
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
/*      */   public void severe(String paramString) {
/* 1464 */     log(Level.SEVERE, paramString);
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
/*      */   public void warning(String paramString) {
/* 1477 */     log(Level.WARNING, paramString);
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
/*      */   public void info(String paramString) {
/* 1490 */     log(Level.INFO, paramString);
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
/*      */   public void config(String paramString) {
/* 1503 */     log(Level.CONFIG, paramString);
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
/*      */   public void fine(String paramString) {
/* 1516 */     log(Level.FINE, paramString);
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
/*      */   public void finer(String paramString) {
/* 1529 */     log(Level.FINER, paramString);
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
/*      */   public void finest(String paramString) {
/* 1542 */     log(Level.FINEST, paramString);
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
/*      */   public void severe(Supplier<String> paramSupplier) {
/* 1564 */     log(Level.SEVERE, paramSupplier);
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
/*      */   public void warning(Supplier<String> paramSupplier) {
/* 1581 */     log(Level.WARNING, paramSupplier);
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
/*      */   public void info(Supplier<String> paramSupplier) {
/* 1598 */     log(Level.INFO, paramSupplier);
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
/*      */   public void config(Supplier<String> paramSupplier) {
/* 1615 */     log(Level.CONFIG, paramSupplier);
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
/*      */   public void fine(Supplier<String> paramSupplier) {
/* 1632 */     log(Level.FINE, paramSupplier);
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
/*      */   public void finer(Supplier<String> paramSupplier) {
/* 1649 */     log(Level.FINER, paramSupplier);
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
/*      */   public void finest(Supplier<String> paramSupplier) {
/* 1666 */     log(Level.FINEST, paramSupplier);
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
/*      */   public void setLevel(Level paramLevel) throws SecurityException {
/* 1689 */     checkPermission();
/* 1690 */     synchronized (treeLock) {
/* 1691 */       this.levelObject = paramLevel;
/* 1692 */       updateEffectiveLevel();
/*      */     } 
/*      */   }
/*      */   
/*      */   final boolean isLevelInitialized() {
/* 1697 */     return (this.levelObject != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Level getLevel() {
/* 1708 */     return this.levelObject;
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
/*      */   public boolean isLoggable(Level paramLevel) {
/* 1720 */     if (paramLevel.intValue() < this.levelValue || this.levelValue == offValue) {
/* 1721 */       return false;
/*      */     }
/* 1723 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 1731 */     return this.name;
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
/*      */   public void addHandler(Handler paramHandler) throws SecurityException {
/* 1748 */     paramHandler.getClass();
/* 1749 */     checkPermission();
/* 1750 */     this.handlers.add(paramHandler);
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
/*      */   public void removeHandler(Handler paramHandler) throws SecurityException {
/* 1764 */     checkPermission();
/* 1765 */     if (paramHandler == null) {
/*      */       return;
/*      */     }
/* 1768 */     this.handlers.remove(paramHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Handler[] getHandlers() {
/* 1777 */     return accessCheckedHandlers();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   Handler[] accessCheckedHandlers() {
/* 1783 */     return this.handlers.<Handler>toArray(emptyHandlers);
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
/*      */   public void setUseParentHandlers(boolean paramBoolean) {
/* 1799 */     checkPermission();
/* 1800 */     this.useParentHandlers = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseParentHandlers() {
/* 1810 */     return this.useParentHandlers;
/*      */   }
/*      */ 
/*      */   
/*      */   private static ResourceBundle findSystemResourceBundle(final Locale locale) {
/* 1815 */     return AccessController.<ResourceBundle>doPrivileged(new PrivilegedAction<ResourceBundle>()
/*      */         {
/*      */           public ResourceBundle run() {
/*      */             try {
/* 1819 */               return ResourceBundle.getBundle("sun.util.logging.resources.logging", locale);
/*      */             }
/* 1821 */             catch (MissingResourceException missingResourceException) {
/* 1822 */               throw new InternalError(missingResourceException.toString());
/*      */             } 
/*      */           }
/*      */         });
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
/*      */   private synchronized ResourceBundle findResourceBundle(String paramString, boolean paramBoolean) {
/* 1849 */     if (paramString == null) {
/* 1850 */       return null;
/*      */     }
/*      */     
/* 1853 */     Locale locale = Locale.getDefault();
/* 1854 */     LoggerBundle loggerBundle = this.loggerBundle;
/*      */ 
/*      */     
/* 1857 */     if (loggerBundle.userBundle != null && paramString
/* 1858 */       .equals(loggerBundle.resourceBundleName))
/* 1859 */       return loggerBundle.userBundle; 
/* 1860 */     if (this.catalog != null && locale.equals(this.catalogLocale) && paramString
/* 1861 */       .equals(this.catalogName)) {
/* 1862 */       return this.catalog;
/*      */     }
/*      */     
/* 1865 */     if (paramString.equals("sun.util.logging.resources.logging")) {
/* 1866 */       this.catalog = findSystemResourceBundle(locale);
/* 1867 */       this.catalogName = paramString;
/* 1868 */       this.catalogLocale = locale;
/* 1869 */       return this.catalog;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1874 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 1875 */     if (classLoader == null) {
/* 1876 */       classLoader = ClassLoader.getSystemClassLoader();
/*      */     }
/*      */     try {
/* 1879 */       this.catalog = ResourceBundle.getBundle(paramString, locale, classLoader);
/* 1880 */       this.catalogName = paramString;
/* 1881 */       this.catalogLocale = locale;
/* 1882 */       return this.catalog;
/* 1883 */     } catch (MissingResourceException missingResourceException) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1888 */       if (paramBoolean) {
/*      */         
/* 1890 */         ClassLoader classLoader1 = getCallersClassLoader();
/*      */         
/* 1892 */         if (classLoader1 == null || classLoader1 == classLoader) {
/* 1893 */           return null;
/*      */         }
/*      */         
/*      */         try {
/* 1897 */           this.catalog = ResourceBundle.getBundle(paramString, locale, classLoader1);
/*      */           
/* 1899 */           this.catalogName = paramString;
/* 1900 */           this.catalogLocale = locale;
/* 1901 */           return this.catalog;
/* 1902 */         } catch (MissingResourceException missingResourceException1) {
/* 1903 */           return null;
/*      */         } 
/*      */       } 
/* 1906 */       return null;
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
/*      */   private synchronized void setupResourceInfo(String paramString, Class<?> paramClass) {
/* 1918 */     LoggerBundle loggerBundle = this.loggerBundle;
/* 1919 */     if (loggerBundle.resourceBundleName != null) {
/*      */ 
/*      */       
/* 1922 */       if (loggerBundle.resourceBundleName.equals(paramString)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1928 */       throw new IllegalArgumentException(loggerBundle.resourceBundleName + " != " + paramString);
/*      */     } 
/*      */ 
/*      */     
/* 1932 */     if (paramString == null) {
/*      */       return;
/*      */     }
/*      */     
/* 1936 */     setCallersClassLoaderRef(paramClass);
/* 1937 */     if (this.isSystemLogger && getCallersClassLoader() != null) {
/* 1938 */       checkPermission();
/*      */     }
/* 1940 */     if (findResourceBundle(paramString, true) == null) {
/*      */ 
/*      */ 
/*      */       
/* 1944 */       this.callersClassLoaderRef = null;
/* 1945 */       throw new MissingResourceException("Can't find " + paramString + " bundle", paramString, "");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1950 */     assert loggerBundle.userBundle == null;
/* 1951 */     this.loggerBundle = LoggerBundle.get(paramString, null);
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
/*      */   public void setResourceBundle(ResourceBundle paramResourceBundle) {
/* 1970 */     checkPermission();
/*      */ 
/*      */     
/* 1973 */     String str = paramResourceBundle.getBaseBundleName();
/*      */ 
/*      */     
/* 1976 */     if (str == null || str.isEmpty()) {
/* 1977 */       throw new IllegalArgumentException("resource bundle must have a name");
/*      */     }
/*      */     
/* 1980 */     synchronized (this) {
/* 1981 */       LoggerBundle loggerBundle = this.loggerBundle;
/*      */       
/* 1983 */       boolean bool = (loggerBundle.resourceBundleName == null || loggerBundle.resourceBundleName.equals(str)) ? true : false;
/*      */       
/* 1985 */       if (!bool) {
/* 1986 */         throw new IllegalArgumentException("can't replace resource bundle");
/*      */       }
/*      */ 
/*      */       
/* 1990 */       this.loggerBundle = LoggerBundle.get(str, paramResourceBundle);
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
/*      */   public Logger getParent() {
/* 2013 */     return this.parent;
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
/*      */   public void setParent(Logger paramLogger) {
/* 2027 */     if (paramLogger == null) {
/* 2028 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*      */     
/* 2032 */     if (this.manager == null) {
/* 2033 */       this.manager = LogManager.getLogManager();
/*      */     }
/* 2035 */     this.manager.checkPermission();
/*      */     
/* 2037 */     doSetParent(paramLogger);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void doSetParent(Logger paramLogger) {
/* 2047 */     synchronized (treeLock) {
/*      */ 
/*      */       
/* 2050 */       LogManager.LoggerWeakRef loggerWeakRef = null;
/* 2051 */       if (this.parent != null)
/*      */       {
/* 2053 */         for (Iterator<LogManager.LoggerWeakRef> iterator = this.parent.kids.iterator(); iterator.hasNext(); ) {
/* 2054 */           loggerWeakRef = iterator.next();
/* 2055 */           Logger logger = loggerWeakRef.get();
/* 2056 */           if (logger == this) {
/*      */             
/* 2058 */             iterator.remove();
/*      */             break;
/*      */           } 
/* 2061 */           loggerWeakRef = null;
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2068 */       this.parent = paramLogger;
/* 2069 */       if (this.parent.kids == null) {
/* 2070 */         this.parent.kids = new ArrayList<>(2);
/*      */       }
/* 2072 */       if (loggerWeakRef == null) {
/*      */         
/* 2074 */         this.manager.getClass(); loggerWeakRef = new LogManager.LoggerWeakRef(this.manager, this);
/*      */       } 
/* 2076 */       loggerWeakRef.setParentRef(new WeakReference<>(this.parent));
/* 2077 */       this.parent.kids.add(loggerWeakRef);
/*      */ 
/*      */ 
/*      */       
/* 2081 */       updateEffectiveLevel();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void removeChildLogger(LogManager.LoggerWeakRef paramLoggerWeakRef) {
/* 2090 */     synchronized (treeLock) {
/* 2091 */       for (Iterator<LogManager.LoggerWeakRef> iterator = this.kids.iterator(); iterator.hasNext(); ) {
/* 2092 */         LogManager.LoggerWeakRef loggerWeakRef = iterator.next();
/* 2093 */         if (loggerWeakRef == paramLoggerWeakRef) {
/* 2094 */           iterator.remove();
/*      */           return;
/*      */         } 
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
/*      */   private void updateEffectiveLevel() {
/*      */     int i;
/* 2109 */     if (this.levelObject != null) {
/* 2110 */       i = this.levelObject.intValue();
/*      */     }
/* 2112 */     else if (this.parent != null) {
/* 2113 */       i = this.parent.levelValue;
/*      */     } else {
/*      */       
/* 2116 */       i = Level.INFO.intValue();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2121 */     if (this.levelValue == i) {
/*      */       return;
/*      */     }
/*      */     
/* 2125 */     this.levelValue = i;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2130 */     if (this.kids != null) {
/* 2131 */       for (byte b = 0; b < this.kids.size(); b++) {
/* 2132 */         LogManager.LoggerWeakRef loggerWeakRef = this.kids.get(b);
/* 2133 */         Logger logger = loggerWeakRef.get();
/* 2134 */         if (logger != null) {
/* 2135 */           logger.updateEffectiveLevel();
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LoggerBundle getEffectiveLoggerBundle() {
/* 2146 */     LoggerBundle loggerBundle = this.loggerBundle;
/* 2147 */     if (loggerBundle.isSystemBundle()) {
/* 2148 */       return SYSTEM_BUNDLE;
/*      */     }
/*      */ 
/*      */     
/* 2152 */     ResourceBundle resourceBundle = getResourceBundle();
/* 2153 */     if (resourceBundle != null && resourceBundle == loggerBundle.userBundle)
/* 2154 */       return loggerBundle; 
/* 2155 */     if (resourceBundle != null) {
/*      */ 
/*      */       
/* 2158 */       String str = getResourceBundleName();
/* 2159 */       return LoggerBundle.get(str, resourceBundle);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2164 */     Logger logger = this.parent;
/* 2165 */     while (logger != null) {
/* 2166 */       LoggerBundle loggerBundle1 = logger.loggerBundle;
/* 2167 */       if (loggerBundle1.isSystemBundle()) {
/* 2168 */         return SYSTEM_BUNDLE;
/*      */       }
/* 2170 */       if (loggerBundle1.userBundle != null) {
/* 2171 */         return loggerBundle1;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2177 */       String str = this.isSystemLogger ? (logger.isSystemLogger ? loggerBundle1.resourceBundleName : null) : logger.getResourceBundleName();
/* 2178 */       if (str != null) {
/* 2179 */         return LoggerBundle.get(str, 
/* 2180 */             findResourceBundle(str, true));
/*      */       }
/* 2182 */       logger = this.isSystemLogger ? logger.parent : logger.getParent();
/*      */     } 
/* 2184 */     return NO_RESOURCE_BUNDLE;
/*      */   }
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/Logger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */