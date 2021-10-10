/*      */ package java.util.logging;
/*      */ 
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.security.AccessController;
/*      */ import java.security.Permission;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Properties;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.misc.JavaAWTAccess;
/*      */ import sun.misc.SharedSecrets;
/*      */ import sun.util.logging.PlatformLogger;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LogManager
/*      */ {
/*      */   private static final LogManager manager;
/*  156 */   private volatile Properties props = new Properties();
/*  157 */   private static final Level defaultLevel = Level.INFO;
/*      */ 
/*      */ 
/*      */   
/*  161 */   private final Map<Object, Integer> listenerMap = new HashMap<>();
/*      */ 
/*      */   
/*  164 */   private final LoggerContext systemContext = new SystemLoggerContext();
/*  165 */   private final LoggerContext userContext = new LoggerContext();
/*      */   private volatile Logger rootLogger;
/*      */   private volatile boolean readPrimordialConfiguration;
/*      */   private boolean initializedGlobalHandlers = true;
/*      */   private boolean deathImminent;
/*      */   private boolean initializedCalled; private volatile boolean initializationDone; private WeakHashMap<Object, LoggerContext> contextsMap; private final ReferenceQueue<Logger> loggerRefQueue; private static final int MAX_ITERATIONS = 400; private final Permission controlPermission; private static LoggingMXBean loggingMXBean; public static final String LOGGING_MXBEAN_NAME = "java.util.logging:type=Logging"; private class Cleaner extends Thread {
/*      */     private Cleaner() { setContextClassLoader(null); } public void run() { LogManager logManager = LogManager.manager; synchronized (LogManager.this) { LogManager.this.deathImminent = true; LogManager.this.initializedGlobalHandlers = true; }  LogManager.this.reset(); }
/*      */   } protected LogManager() { this(checkSubclassPermissions()); } private static Void checkSubclassPermissions() { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) { securityManager.checkPermission(new RuntimePermission("shutdownHooks")); securityManager.checkPermission(new RuntimePermission("setContextClassLoader")); }  return null; } private LogManager(Void paramVoid) { this.initializedCalled = false; this.initializationDone = false; this.contextsMap = null; this.loggerRefQueue = new ReferenceQueue<>(); this.controlPermission = new LoggingPermission("control", null); try { Runtime.getRuntime().addShutdownHook(new Cleaner()); } catch (IllegalStateException illegalStateException) {} } final void ensureLogManagerInitialized() { final LogManager owner = this; if (this.initializationDone || logManager != manager) return;  synchronized (this) { boolean bool = (this.initializedCalled == true) ? true : false; assert this.initializedCalled || !this.initializationDone : "Initialization can't be done if initialized has not been called!"; if (bool || this.initializationDone) return;  this.initializedCalled = true; try { AccessController.doPrivileged(new PrivilegedAction() { public Object run() { assert LogManager.this.rootLogger == null; assert LogManager.this.initializedCalled && !LogManager.this.initializationDone; owner.readPrimordialConfiguration(); owner.getClass(); owner.rootLogger = new LogManager.RootLogger(); owner.addLogger(owner.rootLogger); if (!owner.rootLogger.isLevelInitialized()) owner.rootLogger.setLevel(LogManager.defaultLevel);  Logger logger = Logger.global; owner.addLogger(logger); return null; } }
/*      */           ); } finally { this.initializationDone = true; }  }  } public static LogManager getLogManager() { if (manager != null) manager.ensureLogManagerInitialized();  return manager; } private void readPrimordialConfiguration() { if (!this.readPrimordialConfiguration) synchronized (this) { if (!this.readPrimordialConfiguration) { if (System.out == null) return;  this.readPrimordialConfiguration = true; try { AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() { public Void run() throws Exception { LogManager.this.readConfiguration(); PlatformLogger.redirectPlatformLoggers(); return null; } }
/*      */               ); } catch (Exception exception) { assert false : "Exception raised while reading logging configuration: " + exception; }  }  }   } @Deprecated public void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) throws SecurityException { PropertyChangeListener propertyChangeListener = Objects.<PropertyChangeListener>requireNonNull(paramPropertyChangeListener); checkPermission(); synchronized (this.listenerMap) { Integer integer = this.listenerMap.get(propertyChangeListener); integer = Integer.valueOf((integer == null) ? 1 : (integer.intValue() + 1)); this.listenerMap.put(propertyChangeListener, integer); }  } @Deprecated public void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener) throws SecurityException { checkPermission(); if (paramPropertyChangeListener != null) { PropertyChangeListener propertyChangeListener = paramPropertyChangeListener; synchronized (this.listenerMap) { Integer integer = this.listenerMap.get(propertyChangeListener); if (integer != null) { int i = integer.intValue(); if (i == 1) { this.listenerMap.remove(propertyChangeListener); } else { assert i > 1; this.listenerMap.put(propertyChangeListener, Integer.valueOf(i - 1)); }  }  }  }  } private LoggerContext getUserContext() { LoggerContext loggerContext = null; SecurityManager securityManager = System.getSecurityManager(); JavaAWTAccess javaAWTAccess = SharedSecrets.getJavaAWTAccess(); if (securityManager != null && javaAWTAccess != null) { Object object = javaAWTAccess.getAppletContext(); if (object != null) synchronized (javaAWTAccess) { if (this.contextsMap == null) this.contextsMap = new WeakHashMap<>();  loggerContext = this.contextsMap.get(object); if (loggerContext == null) { loggerContext = new LoggerContext(); this.contextsMap.put(object, loggerContext); }  }   }  return (loggerContext != null) ? loggerContext : this.userContext; } final LoggerContext getSystemContext() { return this.systemContext; } private List<LoggerContext> contexts() { ArrayList<LoggerContext> arrayList = new ArrayList(); arrayList.add(getSystemContext()); arrayList.add(getUserContext()); return arrayList; } Logger demandLogger(String paramString1, String paramString2, Class<?> paramClass) { Logger logger = getLogger(paramString1); if (logger == null) { Logger logger1 = new Logger(paramString1, paramString2, paramClass, this, false); do { if (addLogger(logger1)) return logger1;  logger = getLogger(paramString1); } while (logger == null); }  return logger; } Logger demandSystemLogger(String paramString1, String paramString2) { final Logger logger2, sysLogger = getSystemContext().demandLogger(paramString1, paramString2); do { if (addLogger(logger1)) { logger2 = logger1; } else { logger2 = getLogger(paramString1); }  } while (logger2 == null); if (logger2 != logger1 && (logger1.accessCheckedHandlers()).length == 0) { final Logger l = logger2; AccessController.doPrivileged(new PrivilegedAction<Void>() { public Void run() { for (Handler handler : l.accessCheckedHandlers()) sysLogger.addHandler(handler);  return null; } }
/*      */         ); }  return logger1; } class LoggerContext {
/*      */     private final Hashtable<String, LogManager.LoggerWeakRef> namedLoggers = new Hashtable<>(); private final LogManager.LogNode root; private LoggerContext() { this.root = new LogManager.LogNode(null, this); } final boolean requiresDefaultLoggers() { boolean bool = (getOwner() == LogManager.manager) ? true : false; if (bool) getOwner().ensureLogManagerInitialized();  return bool; } final LogManager getOwner() { return LogManager.this; } final Logger getRootLogger() { return (getOwner()).rootLogger; } final Logger getGlobalLogger() { return Logger.global; } Logger demandLogger(String param1String1, String param1String2) { LogManager logManager = getOwner(); return logManager.demandLogger(param1String1, param1String2, null); } private void ensureInitialized() { if (requiresDefaultLoggers()) { ensureDefaultLogger(getRootLogger()); ensureDefaultLogger(getGlobalLogger()); }  } synchronized Logger findLogger(String param1String) { ensureInitialized(); LogManager.LoggerWeakRef loggerWeakRef = this.namedLoggers.get(param1String); if (loggerWeakRef == null) return null;  Logger logger = loggerWeakRef.get(); if (logger == null) loggerWeakRef.dispose();  return logger; } private void ensureAllDefaultLoggers(Logger param1Logger) { if (requiresDefaultLoggers()) { String str = param1Logger.getName(); if (!str.isEmpty()) { ensureDefaultLogger(getRootLogger()); if (!"global".equals(str)) ensureDefaultLogger(getGlobalLogger());  }  }  } private void ensureDefaultLogger(Logger param1Logger) { if (!requiresDefaultLoggers() || param1Logger == null || (param1Logger != Logger.global && param1Logger != LogManager.this.rootLogger)) { assert param1Logger == null; return; }  if (!this.namedLoggers.containsKey(param1Logger.getName())) addLocalLogger(param1Logger, false);  } boolean addLocalLogger(Logger param1Logger) { return addLocalLogger(param1Logger, requiresDefaultLoggers()); } synchronized boolean addLocalLogger(Logger param1Logger, boolean param1Boolean) { if (param1Boolean) ensureAllDefaultLoggers(param1Logger);  String str = param1Logger.getName(); if (str == null) throw new NullPointerException();  LogManager.LoggerWeakRef loggerWeakRef = this.namedLoggers.get(str); if (loggerWeakRef != null) if (loggerWeakRef.get() == null) { loggerWeakRef.dispose(); } else { return false; }   LogManager logManager = getOwner(); param1Logger.setLogManager(logManager); logManager.getClass(); loggerWeakRef = new LogManager.LoggerWeakRef(param1Logger); this.namedLoggers.put(str, loggerWeakRef); Level level = logManager.getLevelProperty(str + ".level", null); if (level != null && !param1Logger.isLevelInitialized()) LogManager.doSetLevel(param1Logger, level);  processParentHandlers(param1Logger, str); LogManager.LogNode logNode1 = getNode(str); logNode1.loggerRef = loggerWeakRef; Logger logger = null; LogManager.LogNode logNode2 = logNode1.parent; while (logNode2 != null) { LogManager.LoggerWeakRef loggerWeakRef1 = logNode2.loggerRef; if (loggerWeakRef1 != null) { logger = loggerWeakRef1.get(); if (logger != null) break;  }  logNode2 = logNode2.parent; }  if (logger != null) LogManager.doSetParent(param1Logger, logger);  logNode1.walkAndSetParent(param1Logger); loggerWeakRef.setNode(logNode1); return true; } synchronized void removeLoggerRef(String param1String, LogManager.LoggerWeakRef param1LoggerWeakRef) { this.namedLoggers.remove(param1String, param1LoggerWeakRef); } synchronized Enumeration<String> getLoggerNames() { ensureInitialized(); return this.namedLoggers.keys(); } private void processParentHandlers(final Logger logger, final String name) { final LogManager owner = getOwner(); AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*      */             public Void run() { if (logger != owner.rootLogger) { boolean bool = owner.getBooleanProperty(name + ".useParentHandlers", true); if (!bool) logger.setUseParentHandlers(false);  }  return null; }
/*      */           }); int i = 1; while (true) { int j = name.indexOf(".", i); if (j < 0) break;  String str = name.substring(0, j); if (logManager.getProperty(str + ".level") != null || logManager.getProperty(str + ".handlers") != null) demandLogger(str, null);  i = j + 1; }  } LogManager.LogNode getNode(String param1String) { if (param1String == null || param1String.equals("")) return this.root;  LogManager.LogNode logNode = this.root; while (param1String.length() > 0) { String str; int i = param1String.indexOf("."); if (i > 0) { str = param1String.substring(0, i); param1String = param1String.substring(i + 1); } else { str = param1String; param1String = ""; }  if (logNode.children == null) logNode.children = new HashMap<>();  LogManager.LogNode logNode1 = logNode.children.get(str); if (logNode1 == null) { logNode1 = new LogManager.LogNode(logNode, this); logNode.children.put(str, logNode1); }  logNode = logNode1; }  return logNode; }
/*      */   } final class SystemLoggerContext extends LoggerContext {
/*      */     Logger demandLogger(String param1String1, String param1String2) { Logger logger = findLogger(param1String1); if (logger == null) { Logger logger1 = new Logger(param1String1, param1String2, null, getOwner(), true); do { if (addLocalLogger(logger1)) { logger = logger1; } else { logger = findLogger(param1String1); }  } while (logger == null); }  return logger; }
/*  181 */   } static { manager = AccessController.<LogManager>doPrivileged(new PrivilegedAction<LogManager>()
/*      */         {
/*      */           public LogManager run() {
/*  184 */             LogManager logManager = null;
/*  185 */             String str = null;
/*      */             try {
/*  187 */               str = System.getProperty("java.util.logging.manager");
/*  188 */               if (str != null) {
/*      */                 
/*      */                 try {
/*  191 */                   Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(str);
/*  192 */                   logManager = (LogManager)clazz.newInstance();
/*  193 */                 } catch (ClassNotFoundException classNotFoundException) {
/*      */                   
/*  195 */                   Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(str);
/*  196 */                   logManager = (LogManager)clazz.newInstance();
/*      */                 } 
/*      */               }
/*  199 */             } catch (Exception exception) {
/*  200 */               System.err.println("Could not load Logmanager \"" + str + "\"");
/*  201 */               exception.printStackTrace();
/*      */             } 
/*  203 */             if (logManager == null) {
/*  204 */               logManager = new LogManager();
/*      */             }
/*  206 */             return logManager;
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1701 */     loggingMXBean = null; }
/*      */   private void loadLoggerHandlers(final Logger logger, String paramString1, final String handlersPropertyName) { AccessController.doPrivileged(new PrivilegedAction() { public Object run() { String[] arrayOfString = LogManager.this.parseClassNames(handlersPropertyName); for (byte b = 0; b < arrayOfString.length; b++) { String str = arrayOfString[b]; try { Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(str); Handler handler = (Handler)clazz.newInstance(); String str1 = LogManager.this.getProperty(str + ".level"); if (str1 != null) { Level level = Level.findLevel(str1); if (level != null) { handler.setLevel(level); }
/*      */                   else { System.err.println("Can't set level for " + str); }
/*      */                    }
/*      */                  logger.addHandler(handler); }
/*      */               catch (Exception exception) { System.err.println("Can't load log handler \"" + str + "\""); System.err.println("" + exception); exception.printStackTrace(); }
/*      */                }
/*      */              return null; } }
/*      */       ); }
/*      */   final class LoggerWeakRef extends WeakReference<Logger> {
/*      */     private String name;
/*      */     private LogManager.LogNode node;
/*      */     private WeakReference<Logger> parentRef;
/*      */     private boolean disposed = false;
/*      */     LoggerWeakRef(Logger param1Logger) { super(param1Logger, LogManager.this.loggerRefQueue); this.name = param1Logger.getName(); } void dispose() { synchronized (this) { if (this.disposed)
/*      */           return;  this.disposed = true; }
/*      */        LogManager.LogNode logNode = this.node; if (logNode != null)
/*      */         synchronized (logNode.context) { logNode.context.removeLoggerRef(this.name, this); this.name = null; if (logNode.loggerRef == this)
/*      */             logNode.loggerRef = null;  this.node = null; }
/*      */           if (this.parentRef != null) { Logger logger = this.parentRef.get(); if (logger != null)
/*      */           logger.removeChildLogger(this);  this.parentRef = null; }
/*      */        } void setNode(LogManager.LogNode param1LogNode) { this.node = param1LogNode; } void setParentRef(WeakReference<Logger> param1WeakReference) { this.parentRef = param1WeakReference; }
/*      */   } final void drainLoggerRefQueueBounded() { for (byte b = 0; b < 'Ɛ' && this.loggerRefQueue != null; b++) { LoggerWeakRef loggerWeakRef = (LoggerWeakRef)this.loggerRefQueue.poll(); if (loggerWeakRef == null)
/*      */         break;  loggerWeakRef.dispose(); }
/*      */      } public boolean addLogger(Logger paramLogger) { String str = paramLogger.getName(); if (str == null)
/*      */       throw new NullPointerException();  drainLoggerRefQueueBounded(); LoggerContext loggerContext = getUserContext(); if (loggerContext.addLocalLogger(paramLogger)) { loadLoggerHandlers(paramLogger, str, str + ".handlers"); return true; }
/*      */      return false; } private static void doSetLevel(final Logger logger, final Level level) { SecurityManager securityManager = System.getSecurityManager(); if (securityManager == null) {
/*      */       logger.setLevel(level); return;
/*      */     }  AccessController.doPrivileged(new PrivilegedAction() {
/*      */           public Object run() { logger.setLevel(level); return null; }
/* 1731 */         }); } public static synchronized LoggingMXBean getLoggingMXBean() { if (loggingMXBean == null) {
/* 1732 */       loggingMXBean = new Logging();
/*      */     }
/* 1734 */     return loggingMXBean; } private static void doSetParent(final Logger logger, final Logger parent) { SecurityManager securityManager = System.getSecurityManager(); if (securityManager == null) { logger.setParent(parent); return; }  AccessController.doPrivileged(new PrivilegedAction() { public Object run() { logger.setParent(parent); return null; } }
/*      */       ); } public Logger getLogger(String paramString) { return getUserContext().findLogger(paramString); } public Enumeration<String> getLoggerNames() { return getUserContext().getLoggerNames(); } public void readConfiguration() throws IOException, SecurityException { checkPermission(); String str1 = System.getProperty("java.util.logging.config.class"); if (str1 != null) try { Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(str1); clazz.newInstance(); return; } catch (ClassNotFoundException classNotFoundException) { Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(str1); clazz.newInstance(); return; } catch (Exception exception) { System.err.println("Logging configuration class \"" + str1 + "\" failed"); System.err.println("" + exception); }   String str2 = System.getProperty("java.util.logging.config.file"); if (str2 == null) { str2 = System.getProperty("java.home"); if (str2 == null) throw new Error("Can't find java.home ??");  File file = new File(str2, "lib"); file = new File(file, "logging.properties"); str2 = file.getCanonicalPath(); }  try (FileInputStream null = new FileInputStream(str2)) { BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream); readConfiguration(bufferedInputStream); }  } public void reset() throws SecurityException { checkPermission(); synchronized (this) { this.props = new Properties(); this.initializedGlobalHandlers = true; }  for (LoggerContext loggerContext : contexts()) { Enumeration<String> enumeration = loggerContext.getLoggerNames(); while (enumeration.hasMoreElements()) { String str = enumeration.nextElement(); Logger logger = loggerContext.findLogger(str); if (logger != null) resetLogger(logger);  }  }  } private void resetLogger(Logger paramLogger) { Handler[] arrayOfHandler = paramLogger.getHandlers(); for (byte b = 0; b < arrayOfHandler.length; b++) { Handler handler = arrayOfHandler[b]; paramLogger.removeHandler(handler); try { handler.close(); } catch (Exception exception) {} }  String str = paramLogger.getName(); if (str != null && str.equals("")) { paramLogger.setLevel(defaultLevel); } else { paramLogger.setLevel(null); }  } private String[] parseClassNames(String paramString) { String str = getProperty(paramString); if (str == null) return new String[0];  str = str.trim(); int i = 0; ArrayList<String> arrayList = new ArrayList(); while (i < str.length()) { int j = i; while (j < str.length() && !Character.isWhitespace(str.charAt(j))) { if (str.charAt(j) == ',') break;  j++; }  String str1 = str.substring(i, j); i = j + 1; str1 = str1.trim(); if (str1.length() == 0) continue;  arrayList.add(str1); }  return arrayList.<String>toArray(new String[arrayList.size()]); } public void readConfiguration(InputStream paramInputStream) throws IOException, SecurityException { checkPermission(); reset(); this.props.load(paramInputStream); String[] arrayOfString = parseClassNames("config"); for (byte b = 0; b < arrayOfString.length; b++) { String str = arrayOfString[b]; try { Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(str); clazz.newInstance(); } catch (Exception exception) { System.err.println("Can't load config class \"" + str + "\""); System.err.println("" + exception); }  }  setLevelsOnExistingLoggers(); HashMap<Object, Integer> hashMap = null; synchronized (this.listenerMap) { if (!this.listenerMap.isEmpty()) hashMap = new HashMap<>(this.listenerMap);  }  if (hashMap != null) { assert Beans.isBeansPresent(); Object object = Beans.newPropertyChangeEvent(LogManager.class, null, null, null); for (Map.Entry<Object, Integer> entry : hashMap.entrySet()) { Object object1 = entry.getKey(); int i = ((Integer)entry.getValue()).intValue(); for (byte b1 = 0; b1 < i; b1++) Beans.invokePropertyChange(object1, object);  }  }  synchronized (this) { this.initializedGlobalHandlers = false; }  } public String getProperty(String paramString) { return this.props.getProperty(paramString); } String getStringProperty(String paramString1, String paramString2) { String str = getProperty(paramString1); if (str == null) return paramString2;  return str.trim(); } int getIntProperty(String paramString, int paramInt) { String str = getProperty(paramString); if (str == null) return paramInt;  try { return Integer.parseInt(str.trim()); } catch (Exception exception) { return paramInt; }  } boolean getBooleanProperty(String paramString, boolean paramBoolean) { String str = getProperty(paramString); if (str == null) return paramBoolean;  str = str.toLowerCase(); if (str.equals("true") || str.equals("1")) return true;  if (str.equals("false") || str.equals("0")) return false;  return paramBoolean; } Level getLevelProperty(String paramString, Level paramLevel) { String str = getProperty(paramString); if (str == null) return paramLevel;  Level level = Level.findLevel(str.trim()); return (level != null) ? level : paramLevel; } Filter getFilterProperty(String paramString, Filter paramFilter) { String str = getProperty(paramString); try { if (str != null) { Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(str); return (Filter)clazz.newInstance(); }  } catch (Exception exception) {} return paramFilter; } Formatter getFormatterProperty(String paramString, Formatter paramFormatter) { String str = getProperty(paramString); try { if (str != null) { Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(str); return (Formatter)clazz.newInstance(); }  } catch (Exception exception) {} return paramFormatter; } private synchronized void initializeGlobalHandlers() { if (this.initializedGlobalHandlers) return;  this.initializedGlobalHandlers = true; if (this.deathImminent) return;  loadLoggerHandlers(this.rootLogger, null, "handlers"); } void checkPermission() { SecurityManager securityManager = System.getSecurityManager(); if (securityManager != null) securityManager.checkPermission(this.controlPermission);  } public void checkAccess() throws SecurityException { checkPermission(); } private static class LogNode {
/*      */     HashMap<String, LogNode> children; LogManager.LoggerWeakRef loggerRef; LogNode parent; final LogManager.LoggerContext context; LogNode(LogNode param1LogNode, LogManager.LoggerContext param1LoggerContext) { this.parent = param1LogNode; this.context = param1LoggerContext; }
/*      */     void walkAndSetParent(Logger param1Logger) { if (this.children == null) return;  Iterator<LogNode> iterator = this.children.values().iterator(); while (iterator.hasNext()) { LogNode logNode = iterator.next(); LogManager.LoggerWeakRef loggerWeakRef = logNode.loggerRef; Logger logger = (loggerWeakRef == null) ? null : loggerWeakRef.get(); if (logger == null) { logNode.walkAndSetParent(param1Logger); continue; }  LogManager.doSetParent(logger, param1Logger); }  } }
/*      */   private final class RootLogger extends Logger {
/*      */     private RootLogger() { super("", null, null, LogManager.this, true); }
/*      */     public void log(LogRecord param1LogRecord) { LogManager.this.initializeGlobalHandlers(); super.log(param1LogRecord); }
/*      */     public void addHandler(Handler param1Handler) { LogManager.this.initializeGlobalHandlers(); super.addHandler(param1Handler); }
/*      */     public void removeHandler(Handler param1Handler) { LogManager.this.initializeGlobalHandlers(); super.removeHandler(param1Handler); }
/*      */     Handler[] accessCheckedHandlers() { LogManager.this.initializeGlobalHandlers(); return super.accessCheckedHandlers(); } }
/*      */   private synchronized void setLevelsOnExistingLoggers() { Enumeration<?> enumeration = this.props.propertyNames(); while (enumeration.hasMoreElements()) { String str1 = (String)enumeration.nextElement(); if (!str1.endsWith(".level")) continue;  int i = str1.length() - 6; String str2 = str1.substring(0, i); Level level = getLevelProperty(str1, null); if (level == null) { System.err.println("Bad level value for property: " + str1); continue; }  for (LoggerContext loggerContext : contexts()) { Logger logger = loggerContext.findLogger(str2); if (logger == null) continue;  logger.setLevel(level); }  }  }
/* 1745 */   private static class Beans { private static final Class<?> propertyChangeListenerClass = getClass("java.beans.PropertyChangeListener");
/*      */ 
/*      */     
/* 1748 */     private static final Class<?> propertyChangeEventClass = getClass("java.beans.PropertyChangeEvent");
/*      */ 
/*      */     
/* 1751 */     private static final Method propertyChangeMethod = getMethod(propertyChangeListenerClass, "propertyChange", new Class[] { propertyChangeEventClass });
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1756 */     private static final Constructor<?> propertyEventCtor = getConstructor(propertyChangeEventClass, new Class[] { Object.class, String.class, Object.class, Object.class });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private static Class<?> getClass(String param1String) {
/*      */       try {
/* 1764 */         return Class.forName(param1String, true, Beans.class.getClassLoader());
/* 1765 */       } catch (ClassNotFoundException classNotFoundException) {
/* 1766 */         return null;
/*      */       } 
/*      */     }
/*      */     private static Constructor<?> getConstructor(Class<?> param1Class, Class<?>... param1VarArgs) {
/*      */       try {
/* 1771 */         return (param1Class == null) ? null : param1Class.getDeclaredConstructor(param1VarArgs);
/* 1772 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 1773 */         throw new AssertionError(noSuchMethodException);
/*      */       } 
/*      */     }
/*      */     
/*      */     private static Method getMethod(Class<?> param1Class, String param1String, Class<?>... param1VarArgs) {
/*      */       try {
/* 1779 */         return (param1Class == null) ? null : param1Class.getMethod(param1String, param1VarArgs);
/* 1780 */       } catch (NoSuchMethodException noSuchMethodException) {
/* 1781 */         throw new AssertionError(noSuchMethodException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static boolean isBeansPresent() {
/* 1789 */       return (propertyChangeListenerClass != null && propertyChangeEventClass != null);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static Object newPropertyChangeEvent(Object param1Object1, String param1String, Object param1Object2, Object param1Object3) {
/*      */       try {
/* 1801 */         return propertyEventCtor.newInstance(new Object[] { param1Object1, param1String, param1Object2, param1Object3 });
/* 1802 */       } catch (InstantiationException|IllegalAccessException instantiationException) {
/* 1803 */         throw new AssertionError(instantiationException);
/* 1804 */       } catch (InvocationTargetException invocationTargetException) {
/* 1805 */         Throwable throwable = invocationTargetException.getCause();
/* 1806 */         if (throwable instanceof Error)
/* 1807 */           throw (Error)throwable; 
/* 1808 */         if (throwable instanceof RuntimeException)
/* 1809 */           throw (RuntimeException)throwable; 
/* 1810 */         throw new AssertionError(invocationTargetException);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static void invokePropertyChange(Object param1Object1, Object param1Object2) {
/*      */       try {
/* 1820 */         propertyChangeMethod.invoke(param1Object1, new Object[] { param1Object2 });
/* 1821 */       } catch (IllegalAccessException illegalAccessException) {
/* 1822 */         throw new AssertionError(illegalAccessException);
/* 1823 */       } catch (InvocationTargetException invocationTargetException) {
/* 1824 */         Throwable throwable = invocationTargetException.getCause();
/* 1825 */         if (throwable instanceof Error)
/* 1826 */           throw (Error)throwable; 
/* 1827 */         if (throwable instanceof RuntimeException)
/* 1828 */           throw (RuntimeException)throwable; 
/* 1829 */         throw new AssertionError(invocationTargetException);
/*      */       } 
/*      */     } }
/*      */ 
/*      */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/LogManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */