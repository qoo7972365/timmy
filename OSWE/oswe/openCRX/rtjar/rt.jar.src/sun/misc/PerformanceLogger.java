/*     */ package sun.misc;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Vector;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PerformanceLogger
/*     */ {
/*     */   private static final int START_INDEX = 0;
/*     */   private static final int LAST_RESERVED = 0;
/*     */   private static boolean perfLoggingOn = false;
/*     */   private static boolean useNanoTime = false;
/*     */   private static Vector<TimeData> times;
/*  82 */   private static String logFileName = null;
/*  83 */   private static Writer logWriter = null;
/*     */   
/*     */   private static long baseTime;
/*     */   
/*     */   static {
/*  88 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.perflog"));
/*     */     
/*  90 */     if (str != null) {
/*  91 */       perfLoggingOn = true;
/*     */ 
/*     */ 
/*     */       
/*  95 */       String str1 = AccessController.<String>doPrivileged(new GetPropertyAction("sun.perflog.nano"));
/*     */       
/*  97 */       if (str1 != null) {
/*  98 */         useNanoTime = true;
/*     */       }
/*     */ 
/*     */       
/* 102 */       if (str.regionMatches(true, 0, "file:", 0, 5)) {
/* 103 */         logFileName = str.substring(5);
/*     */       }
/* 105 */       if (logFileName != null && 
/* 106 */         logWriter == null) {
/* 107 */         AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */             {
/*     */               public Void run() {
/*     */                 try {
/* 111 */                   File file = new File(PerformanceLogger.logFileName);
/* 112 */                   file.createNewFile();
/* 113 */                   PerformanceLogger.logWriter = new FileWriter(file);
/* 114 */                 } catch (Exception exception) {
/* 115 */                   System.out.println(exception + ": Creating logfile " + PerformanceLogger
/* 116 */                       .logFileName + ".  Log to console");
/*     */                 } 
/*     */                 
/* 119 */                 return null;
/*     */               }
/*     */             });
/*     */       }
/*     */       
/* 124 */       if (logWriter == null) {
/* 125 */         logWriter = new OutputStreamWriter(System.out);
/*     */       }
/*     */     } 
/* 128 */     times = new Vector<>(10);
/*     */     
/* 130 */     for (byte b = 0; b; b++) {
/* 131 */       times.add(new TimeData("Time " + b + " not set", 0L));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean loggingEnabled() {
/* 142 */     return perfLoggingOn;
/*     */   }
/*     */ 
/*     */   
/*     */   static class TimeData
/*     */   {
/*     */     String message;
/*     */     
/*     */     long time;
/*     */ 
/*     */     
/*     */     TimeData(String param1String, long param1Long) {
/* 154 */       this.message = param1String;
/* 155 */       this.time = param1Long;
/*     */     }
/*     */     
/*     */     String getMessage() {
/* 159 */       return this.message;
/*     */     }
/*     */     
/*     */     long getTime() {
/* 163 */       return this.time;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long getCurrentTime() {
/* 171 */     if (useNanoTime) {
/* 172 */       return System.nanoTime();
/*     */     }
/* 174 */     return System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setStartTime(String paramString) {
/* 185 */     if (loggingEnabled()) {
/* 186 */       long l = getCurrentTime();
/* 187 */       setStartTime(paramString, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setBaseTime(long paramLong) {
/* 196 */     if (loggingEnabled()) {
/* 197 */       baseTime = paramLong;
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
/*     */   public static void setStartTime(String paramString, long paramLong) {
/* 209 */     if (loggingEnabled()) {
/* 210 */       times.set(0, new TimeData(paramString, paramLong));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getStartTime() {
/* 220 */     if (loggingEnabled()) {
/* 221 */       return ((TimeData)times.get(0)).getTime();
/*     */     }
/* 223 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int setTime(String paramString) {
/* 232 */     if (loggingEnabled()) {
/* 233 */       long l = getCurrentTime();
/* 234 */       return setTime(paramString, l);
/*     */     } 
/* 236 */     return 0;
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
/*     */   public static int setTime(String paramString, long paramLong) {
/* 249 */     if (loggingEnabled())
/*     */     {
/*     */ 
/*     */       
/* 253 */       synchronized (times) {
/* 254 */         times.add(new TimeData(paramString, paramLong));
/* 255 */         return times.size() - 1;
/*     */       } 
/*     */     }
/* 258 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long getTimeAtIndex(int paramInt) {
/* 266 */     if (loggingEnabled()) {
/* 267 */       return ((TimeData)times.get(paramInt)).getTime();
/*     */     }
/* 269 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMessageAtIndex(int paramInt) {
/* 277 */     if (loggingEnabled()) {
/* 278 */       return ((TimeData)times.get(paramInt)).getMessage();
/*     */     }
/* 280 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void outputLog(Writer paramWriter) {
/* 288 */     if (loggingEnabled()) {
/*     */       try {
/* 290 */         synchronized (times) {
/* 291 */           for (byte b = 0; b < times.size(); b++) {
/* 292 */             TimeData timeData = times.get(b);
/* 293 */             if (timeData != null) {
/* 294 */               paramWriter.write(b + " " + timeData.getMessage() + ": " + (timeData
/* 295 */                   .getTime() - baseTime) + "\n");
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 300 */         paramWriter.flush();
/* 301 */       } catch (Exception exception) {
/* 302 */         System.out.println(exception + ": Writing performance log to " + paramWriter);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void outputLog() {
/* 313 */     outputLog(logWriter);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/PerformanceLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */