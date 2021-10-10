/*     */ package sun.util.logging;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import sun.misc.JavaLangAccess;
/*     */ import sun.misc.SharedSecrets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlatformLogger
/*     */ {
/*     */   private static final int OFF = 2147483647;
/*     */   private static final int SEVERE = 1000;
/*     */   private static final int WARNING = 900;
/*     */   private static final int INFO = 800;
/*     */   private static final int CONFIG = 700;
/*     */   private static final int FINE = 500;
/*     */   private static final int FINER = 400;
/*     */   private static final int FINEST = 300;
/*     */   private static final int ALL = -2147483648;
/*     */   
/*     */   public enum Level
/*     */   {
/* 107 */     ALL,
/* 108 */     FINEST,
/* 109 */     FINER,
/* 110 */     FINE,
/* 111 */     CONFIG,
/* 112 */     INFO,
/* 113 */     WARNING,
/* 114 */     SEVERE,
/* 115 */     OFF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object javaLevel;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     private static final int[] LEVEL_VALUES = new int[] { Integer.MIN_VALUE, 300, 400, 500, 700, 800, 900, 1000, Integer.MAX_VALUE };
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     public int intValue() {
/* 133 */       return LEVEL_VALUES[ordinal()];
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
/* 157 */   private static final Level DEFAULT_LEVEL = Level.INFO;
/*     */ 
/*     */   
/* 160 */   private static boolean loggingEnabled = ((Boolean)AccessController.<Boolean>doPrivileged(new PrivilegedAction<Boolean>()
/*     */       {
/*     */         public Boolean run() {
/* 163 */           String str1 = System.getProperty("java.util.logging.config.class");
/* 164 */           String str2 = System.getProperty("java.util.logging.config.file");
/* 165 */           return Boolean.valueOf((str1 != null || str2 != null));
/*     */         }
/*     */       })).booleanValue();
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 173 */       Class.forName("sun.util.logging.PlatformLogger$DefaultLoggerProxy", false, PlatformLogger.class
/*     */           
/* 175 */           .getClassLoader());
/* 176 */       Class.forName("sun.util.logging.PlatformLogger$JavaLoggerProxy", false, PlatformLogger.class
/*     */           
/* 178 */           .getClassLoader());
/* 179 */     } catch (ClassNotFoundException classNotFoundException) {
/* 180 */       throw new InternalError(classNotFoundException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 185 */   private static Map<String, WeakReference<PlatformLogger>> loggers = new HashMap<>();
/*     */   
/*     */   private volatile LoggerProxy loggerProxy;
/*     */   
/*     */   private volatile JavaLoggerProxy javaLoggerProxy;
/*     */   
/*     */   public static synchronized PlatformLogger getLogger(String paramString) {
/* 192 */     PlatformLogger platformLogger = null;
/* 193 */     WeakReference<PlatformLogger> weakReference = loggers.get(paramString);
/* 194 */     if (weakReference != null) {
/* 195 */       platformLogger = weakReference.get();
/*     */     }
/* 197 */     if (platformLogger == null) {
/* 198 */       platformLogger = new PlatformLogger(paramString);
/* 199 */       loggers.put(paramString, new WeakReference<>(platformLogger));
/*     */     } 
/* 201 */     return platformLogger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void redirectPlatformLoggers() {
/* 209 */     if (loggingEnabled || !LoggingSupport.isAvailable())
/*     */       return; 
/* 211 */     loggingEnabled = true;
/* 212 */     for (Map.Entry<String, WeakReference<PlatformLogger>> entry : loggers.entrySet()) {
/* 213 */       WeakReference<PlatformLogger> weakReference = (WeakReference)entry.getValue();
/* 214 */       PlatformLogger platformLogger = weakReference.get();
/* 215 */       if (platformLogger != null) {
/* 216 */         platformLogger.redirectToJavaLoggerProxy();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void redirectToJavaLoggerProxy() {
/* 225 */     DefaultLoggerProxy defaultLoggerProxy = DefaultLoggerProxy.class.cast(this.loggerProxy);
/* 226 */     JavaLoggerProxy javaLoggerProxy = new JavaLoggerProxy(defaultLoggerProxy.name, defaultLoggerProxy.level);
/*     */     
/* 228 */     this.javaLoggerProxy = javaLoggerProxy;
/* 229 */     this.loggerProxy = javaLoggerProxy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PlatformLogger(String paramString) {
/* 238 */     if (loggingEnabled) {
/* 239 */       this.loggerProxy = this.javaLoggerProxy = new JavaLoggerProxy(paramString);
/*     */     } else {
/* 241 */       this.loggerProxy = new DefaultLoggerProxy(paramString);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 250 */     return this.loggerProxy.isEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 257 */     return this.loggerProxy.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLoggable(Level paramLevel) {
/* 265 */     if (paramLevel == null) {
/* 266 */       throw new NullPointerException();
/*     */     }
/*     */     
/* 269 */     JavaLoggerProxy javaLoggerProxy = this.javaLoggerProxy;
/* 270 */     return (javaLoggerProxy != null) ? javaLoggerProxy.isLoggable(paramLevel) : this.loggerProxy.isLoggable(paramLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Level level() {
/* 281 */     return this.loggerProxy.getLevel();
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
/*     */   public void setLevel(Level paramLevel) {
/* 297 */     this.loggerProxy.setLevel(paramLevel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void severe(String paramString) {
/* 304 */     this.loggerProxy.doLog(Level.SEVERE, paramString);
/*     */   }
/*     */   
/*     */   public void severe(String paramString, Throwable paramThrowable) {
/* 308 */     this.loggerProxy.doLog(Level.SEVERE, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public void severe(String paramString, Object... paramVarArgs) {
/* 312 */     this.loggerProxy.doLog(Level.SEVERE, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void warning(String paramString) {
/* 319 */     this.loggerProxy.doLog(Level.WARNING, paramString);
/*     */   }
/*     */   
/*     */   public void warning(String paramString, Throwable paramThrowable) {
/* 323 */     this.loggerProxy.doLog(Level.WARNING, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public void warning(String paramString, Object... paramVarArgs) {
/* 327 */     this.loggerProxy.doLog(Level.WARNING, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void info(String paramString) {
/* 334 */     this.loggerProxy.doLog(Level.INFO, paramString);
/*     */   }
/*     */   
/*     */   public void info(String paramString, Throwable paramThrowable) {
/* 338 */     this.loggerProxy.doLog(Level.INFO, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public void info(String paramString, Object... paramVarArgs) {
/* 342 */     this.loggerProxy.doLog(Level.INFO, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void config(String paramString) {
/* 349 */     this.loggerProxy.doLog(Level.CONFIG, paramString);
/*     */   }
/*     */   
/*     */   public void config(String paramString, Throwable paramThrowable) {
/* 353 */     this.loggerProxy.doLog(Level.CONFIG, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public void config(String paramString, Object... paramVarArgs) {
/* 357 */     this.loggerProxy.doLog(Level.CONFIG, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fine(String paramString) {
/* 364 */     this.loggerProxy.doLog(Level.FINE, paramString);
/*     */   }
/*     */   
/*     */   public void fine(String paramString, Throwable paramThrowable) {
/* 368 */     this.loggerProxy.doLog(Level.FINE, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public void fine(String paramString, Object... paramVarArgs) {
/* 372 */     this.loggerProxy.doLog(Level.FINE, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finer(String paramString) {
/* 379 */     this.loggerProxy.doLog(Level.FINER, paramString);
/*     */   }
/*     */   
/*     */   public void finer(String paramString, Throwable paramThrowable) {
/* 383 */     this.loggerProxy.doLog(Level.FINER, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public void finer(String paramString, Object... paramVarArgs) {
/* 387 */     this.loggerProxy.doLog(Level.FINER, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finest(String paramString) {
/* 394 */     this.loggerProxy.doLog(Level.FINEST, paramString);
/*     */   }
/*     */   
/*     */   public void finest(String paramString, Throwable paramThrowable) {
/* 398 */     this.loggerProxy.doLog(Level.FINEST, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public void finest(String paramString, Object... paramVarArgs) {
/* 402 */     this.loggerProxy.doLog(Level.FINEST, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   private static abstract class LoggerProxy
/*     */   {
/*     */     final String name;
/*     */ 
/*     */     
/*     */     protected LoggerProxy(String param1String) {
/* 412 */       this.name = param1String;
/*     */     }
/*     */     
/*     */     abstract boolean isEnabled();
/*     */     
/*     */     abstract PlatformLogger.Level getLevel();
/*     */     
/*     */     abstract void setLevel(PlatformLogger.Level param1Level);
/*     */     
/*     */     abstract void doLog(PlatformLogger.Level param1Level, String param1String);
/*     */     
/*     */     abstract void doLog(PlatformLogger.Level param1Level, String param1String, Throwable param1Throwable);
/*     */     
/*     */     abstract void doLog(PlatformLogger.Level param1Level, String param1String, Object... param1VarArgs);
/*     */     
/*     */     abstract boolean isLoggable(PlatformLogger.Level param1Level);
/*     */   }
/*     */   
/*     */   private static final class DefaultLoggerProxy extends LoggerProxy {
/*     */     volatile PlatformLogger.Level effectiveLevel;
/*     */     volatile PlatformLogger.Level level;
/*     */     
/* 434 */     private static PrintStream outputStream() { return System.err; } boolean isEnabled() { return (this.effectiveLevel != PlatformLogger.Level.OFF); }
/*     */     PlatformLogger.Level getLevel() { return this.level; }
/*     */     void setLevel(PlatformLogger.Level param1Level) { PlatformLogger.Level level = this.level;
/*     */       if (level != param1Level) {
/*     */         this.level = param1Level;
/*     */         this.effectiveLevel = deriveEffectiveLevel(param1Level);
/*     */       }  }
/* 441 */     DefaultLoggerProxy(String param1String) { super(param1String);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 519 */       this.date = new Date(); this.effectiveLevel = deriveEffectiveLevel(null); this.level = null; }
/*     */     void doLog(PlatformLogger.Level param1Level, String param1String) { if (isLoggable(param1Level))
/* 521 */         outputStream().print(format(param1Level, param1String, null));  } private synchronized String format(PlatformLogger.Level param1Level, String param1String, Throwable param1Throwable) { this.date.setTime(System.currentTimeMillis());
/* 522 */       String str = "";
/* 523 */       if (param1Throwable != null) {
/* 524 */         StringWriter stringWriter = new StringWriter();
/* 525 */         PrintWriter printWriter = new PrintWriter(stringWriter);
/* 526 */         printWriter.println();
/* 527 */         param1Throwable.printStackTrace(printWriter);
/* 528 */         printWriter.close();
/* 529 */         str = stringWriter.toString();
/*     */       } 
/*     */       
/* 532 */       return String.format(formatString, new Object[] { this.date, 
/*     */             
/* 534 */             getCallerInfo(), this.name, param1Level
/*     */             
/* 536 */             .name(), param1String, str }); }
/*     */     void doLog(PlatformLogger.Level param1Level, String param1String, Throwable param1Throwable) { if (isLoggable(param1Level))
/*     */         outputStream().print(format(param1Level, param1String, param1Throwable));  }
/*     */     void doLog(PlatformLogger.Level param1Level, String param1String, Object... param1VarArgs) { if (isLoggable(param1Level)) {
/*     */         String str = formatMessage(param1String, param1VarArgs); outputStream().print(format(param1Level, str, null));
/*     */       }  }
/*     */     boolean isLoggable(PlatformLogger.Level param1Level) { PlatformLogger.Level level = this.effectiveLevel;
/*     */       return (param1Level.intValue() >= level.intValue() && level != PlatformLogger.Level.OFF); }
/* 544 */     private PlatformLogger.Level deriveEffectiveLevel(PlatformLogger.Level param1Level) { return (param1Level == null) ? PlatformLogger.DEFAULT_LEVEL : param1Level; } private String getCallerInfo() { String str1 = null;
/* 545 */       String str2 = null;
/*     */       
/* 547 */       JavaLangAccess javaLangAccess = SharedSecrets.getJavaLangAccess();
/* 548 */       Throwable throwable = new Throwable();
/* 549 */       int i = javaLangAccess.getStackTraceDepth(throwable);
/*     */       
/* 551 */       String str3 = "sun.util.logging.PlatformLogger";
/* 552 */       boolean bool = true;
/* 553 */       for (byte b = 0; b < i; b++) {
/*     */ 
/*     */ 
/*     */         
/* 557 */         StackTraceElement stackTraceElement = javaLangAccess.getStackTraceElement(throwable, b);
/* 558 */         String str = stackTraceElement.getClassName();
/* 559 */         if (bool) {
/*     */           
/* 561 */           if (str.equals(str3)) {
/* 562 */             bool = false;
/*     */           }
/*     */         }
/* 565 */         else if (!str.equals(str3)) {
/*     */           
/* 567 */           str1 = str;
/* 568 */           str2 = stackTraceElement.getMethodName();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 574 */       if (str1 != null) {
/* 575 */         return str1 + " " + str2;
/*     */       }
/* 577 */       return this.name; }
/*     */      private String formatMessage(String param1String, Object... param1VarArgs) {
/*     */       try {
/*     */         if (param1VarArgs == null || param1VarArgs.length == 0)
/*     */           return param1String; 
/*     */         if (param1String.indexOf("{0") >= 0 || param1String.indexOf("{1") >= 0 || param1String.indexOf("{2") >= 0 || param1String.indexOf("{3") >= 0)
/*     */           return MessageFormat.format(param1String, param1VarArgs); 
/*     */         return param1String;
/*     */       } catch (Exception exception) {
/*     */         return param1String;
/*     */       } 
/*     */     } private static final String formatString = LoggingSupport.getSimpleFormat(false); private Date date; } private static final class JavaLoggerProxy extends LoggerProxy { private final Object javaLogger; static {
/* 589 */       for (PlatformLogger.Level level : PlatformLogger.Level.values()) {
/* 590 */         level.javaLevel = LoggingSupport.parseLevel(level.name());
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     JavaLoggerProxy(String param1String) {
/* 597 */       this(param1String, null);
/*     */     }
/*     */     
/*     */     JavaLoggerProxy(String param1String, PlatformLogger.Level param1Level) {
/* 601 */       super(param1String);
/* 602 */       this.javaLogger = LoggingSupport.getLogger(param1String);
/* 603 */       if (param1Level != null)
/*     */       {
/* 605 */         LoggingSupport.setLevel(this.javaLogger, param1Level.javaLevel);
/*     */       }
/*     */     }
/*     */     
/*     */     void doLog(PlatformLogger.Level param1Level, String param1String) {
/* 610 */       LoggingSupport.log(this.javaLogger, param1Level.javaLevel, param1String);
/*     */     }
/*     */     
/*     */     void doLog(PlatformLogger.Level param1Level, String param1String, Throwable param1Throwable) {
/* 614 */       LoggingSupport.log(this.javaLogger, param1Level.javaLevel, param1String, param1Throwable);
/*     */     }
/*     */     
/*     */     void doLog(PlatformLogger.Level param1Level, String param1String, Object... param1VarArgs) {
/* 618 */       if (!isLoggable(param1Level)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 623 */       byte b1 = (param1VarArgs != null) ? param1VarArgs.length : 0;
/* 624 */       String[] arrayOfString = new String[b1];
/* 625 */       for (byte b2 = 0; b2 < b1; b2++) {
/* 626 */         arrayOfString[b2] = String.valueOf(param1VarArgs[b2]);
/*     */       }
/* 628 */       LoggingSupport.log(this.javaLogger, param1Level.javaLevel, param1String, (Object[])arrayOfString);
/*     */     }
/*     */     
/*     */     boolean isEnabled() {
/* 632 */       return LoggingSupport.isLoggable(this.javaLogger, PlatformLogger.Level.OFF.javaLevel);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PlatformLogger.Level getLevel() {
/* 641 */       Object object = LoggingSupport.getLevel(this.javaLogger);
/* 642 */       if (object == null) return null;
/*     */       
/*     */       try {
/* 645 */         return PlatformLogger.Level.valueOf(LoggingSupport.getLevelName(object));
/* 646 */       } catch (IllegalArgumentException illegalArgumentException) {
/* 647 */         return PlatformLogger.Level.valueOf(LoggingSupport.getLevelValue(object));
/*     */       } 
/*     */     }
/*     */     
/*     */     void setLevel(PlatformLogger.Level param1Level) {
/* 652 */       LoggingSupport.setLevel(this.javaLogger, (param1Level == null) ? null : param1Level.javaLevel);
/*     */     }
/*     */     
/*     */     boolean isLoggable(PlatformLogger.Level param1Level) {
/* 656 */       return LoggingSupport.isLoggable(this.javaLogger, param1Level.javaLevel);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/logging/PlatformLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */