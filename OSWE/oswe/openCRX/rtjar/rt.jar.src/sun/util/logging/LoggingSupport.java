/*     */ package sun.util.logging;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoggingSupport
/*     */ {
/*  49 */   private static final LoggingProxy proxy = AccessController.<LoggingProxy>doPrivileged(new PrivilegedAction<LoggingProxy>()
/*     */       {
/*     */         public LoggingProxy run()
/*     */         {
/*     */           try {
/*  54 */             Class<?> clazz = Class.forName("java.util.logging.LoggingProxyImpl", true, null);
/*  55 */             Field field = clazz.getDeclaredField("INSTANCE");
/*  56 */             field.setAccessible(true);
/*  57 */             return (LoggingProxy)field.get(null);
/*  58 */           } catch (ClassNotFoundException classNotFoundException) {
/*  59 */             return null;
/*  60 */           } catch (NoSuchFieldException noSuchFieldException) {
/*  61 */             throw new AssertionError(noSuchFieldException);
/*  62 */           } catch (IllegalAccessException illegalAccessException) {
/*  63 */             throw new AssertionError(illegalAccessException);
/*     */           } 
/*     */         }
/*     */       });
/*     */   
/*     */   private static final String DEFAULT_FORMAT = "%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp %2$s%n%4$s: %5$s%6$s%n";
/*     */   
/*     */   public static boolean isAvailable() {
/*  71 */     return (proxy != null);
/*     */   }
/*     */   private static final String FORMAT_PROP_KEY = "java.util.logging.SimpleFormatter.format";
/*     */   private static void ensureAvailable() {
/*  75 */     if (proxy == null)
/*  76 */       throw new AssertionError("Should not here"); 
/*     */   }
/*     */   
/*     */   public static List<String> getLoggerNames() {
/*  80 */     ensureAvailable();
/*  81 */     return proxy.getLoggerNames();
/*     */   }
/*     */   public static String getLoggerLevel(String paramString) {
/*  84 */     ensureAvailable();
/*  85 */     return proxy.getLoggerLevel(paramString);
/*     */   }
/*     */   
/*     */   public static void setLoggerLevel(String paramString1, String paramString2) {
/*  89 */     ensureAvailable();
/*  90 */     proxy.setLoggerLevel(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public static String getParentLoggerName(String paramString) {
/*  94 */     ensureAvailable();
/*  95 */     return proxy.getParentLoggerName(paramString);
/*     */   }
/*     */   
/*     */   public static Object getLogger(String paramString) {
/*  99 */     ensureAvailable();
/* 100 */     return proxy.getLogger(paramString);
/*     */   }
/*     */   
/*     */   public static Object getLevel(Object paramObject) {
/* 104 */     ensureAvailable();
/* 105 */     return proxy.getLevel(paramObject);
/*     */   }
/*     */   
/*     */   public static void setLevel(Object paramObject1, Object paramObject2) {
/* 109 */     ensureAvailable();
/* 110 */     proxy.setLevel(paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */   public static boolean isLoggable(Object paramObject1, Object paramObject2) {
/* 114 */     ensureAvailable();
/* 115 */     return proxy.isLoggable(paramObject1, paramObject2);
/*     */   }
/*     */   
/*     */   public static void log(Object paramObject1, Object paramObject2, String paramString) {
/* 119 */     ensureAvailable();
/* 120 */     proxy.log(paramObject1, paramObject2, paramString);
/*     */   }
/*     */   
/*     */   public static void log(Object paramObject1, Object paramObject2, String paramString, Throwable paramThrowable) {
/* 124 */     ensureAvailable();
/* 125 */     proxy.log(paramObject1, paramObject2, paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public static void log(Object paramObject1, Object paramObject2, String paramString, Object... paramVarArgs) {
/* 129 */     ensureAvailable();
/* 130 */     proxy.log(paramObject1, paramObject2, paramString, paramVarArgs);
/*     */   }
/*     */   
/*     */   public static Object parseLevel(String paramString) {
/* 134 */     ensureAvailable();
/* 135 */     return proxy.parseLevel(paramString);
/*     */   }
/*     */   
/*     */   public static String getLevelName(Object paramObject) {
/* 139 */     ensureAvailable();
/* 140 */     return proxy.getLevelName(paramObject);
/*     */   }
/*     */   
/*     */   public static int getLevelValue(Object paramObject) {
/* 144 */     ensureAvailable();
/* 145 */     return proxy.getLevelValue(paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getSimpleFormat() {
/* 153 */     return getSimpleFormat(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getSimpleFormat(boolean paramBoolean) {
/* 160 */     String str = AccessController.<String>doPrivileged(new PrivilegedAction<String>()
/*     */         {
/*     */           public String run() {
/* 163 */             return System.getProperty("java.util.logging.SimpleFormatter.format");
/*     */           }
/*     */         });
/*     */     
/* 167 */     if (paramBoolean && proxy != null && str == null) {
/* 168 */       str = proxy.getProperty("java.util.logging.SimpleFormatter.format");
/*     */     }
/*     */     
/* 171 */     if (str != null) {
/*     */       
/*     */       try {
/* 174 */         String.format(str, new Object[] { new Date(), "", "", "", "", "" });
/* 175 */       } catch (IllegalArgumentException illegalArgumentException) {
/*     */         
/* 177 */         str = "%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp %2$s%n%4$s: %5$s%6$s%n";
/*     */       } 
/*     */     } else {
/* 180 */       str = "%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tS %1$Tp %2$s%n%4$s: %5$s%6$s%n";
/*     */     } 
/* 182 */     return str;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/util/logging/LoggingSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */