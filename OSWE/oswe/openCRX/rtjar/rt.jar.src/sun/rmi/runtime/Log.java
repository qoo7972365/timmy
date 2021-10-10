/*     */ package sun.rmi.runtime;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.rmi.server.LogStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.logging.SimpleFormatter;
/*     */ import java.util.logging.StreamHandler;
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
/*     */ public abstract class Log
/*     */ {
/*  68 */   public static final Level BRIEF = Level.FINE;
/*  69 */   public static final Level VERBOSE = Level.FINER;
/*     */ 
/*     */   
/*     */   private static final LogFactory logFactory;
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  77 */     boolean bool = Boolean.valueOf(AccessController.<String>doPrivileged(new GetPropertyAction("sun.rmi.log.useOld"))).booleanValue();
/*     */ 
/*     */     
/*  80 */     logFactory = bool ? new LogStreamLogFactory() : new LoggerLogFactory();
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
/*     */   public static Log getLog(String paramString1, String paramString2, int paramInt) {
/*     */     Level level;
/* 125 */     if (paramInt < 0) {
/* 126 */       level = null;
/* 127 */     } else if (paramInt == 0) {
/* 128 */       level = Level.OFF;
/* 129 */     } else if (paramInt > 0 && paramInt <= 10) {
/*     */       
/* 131 */       level = BRIEF;
/* 132 */     } else if (paramInt > 10 && paramInt <= 20) {
/*     */ 
/*     */       
/* 135 */       level = VERBOSE;
/*     */     } else {
/* 137 */       level = Level.FINEST;
/*     */     } 
/* 139 */     return logFactory.createLog(paramString1, paramString2, level);
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
/*     */   public static Log getLog(String paramString1, String paramString2, boolean paramBoolean) {
/* 152 */     Level level = paramBoolean ? VERBOSE : null;
/* 153 */     return logFactory.createLog(paramString1, paramString2, level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static interface LogFactory
/*     */   {
/*     */     Log createLog(String param1String1, String param1String2, Level param1Level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LoggerLogFactory
/*     */     implements LogFactory
/*     */   {
/*     */     public Log createLog(String param1String1, String param1String2, Level param1Level) {
/* 172 */       Logger logger = Logger.getLogger(param1String1);
/* 173 */       return new Log.LoggerLog(logger, param1Level);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LoggerLog
/*     */     extends Log
/*     */   {
/* 184 */     private static final Handler alternateConsole = AccessController.<Handler>doPrivileged(new PrivilegedAction<Handler>()
/*     */         {
/*     */           public Handler run() {
/* 187 */             Log.InternalStreamHandler internalStreamHandler = new Log.InternalStreamHandler(System.err);
/*     */             
/* 189 */             internalStreamHandler.setLevel(Level.ALL);
/* 190 */             return internalStreamHandler;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 195 */     private Log.InternalStreamHandler copyHandler = null;
/*     */ 
/*     */     
/*     */     private final Logger logger;
/*     */ 
/*     */     
/*     */     private Log.LoggerPrintStream loggerSandwich;
/*     */ 
/*     */     
/*     */     private LoggerLog(final Logger logger, final Level level) {
/* 205 */       this.logger = logger;
/*     */       
/* 207 */       if (level != null) {
/* 208 */         AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */             {
/*     */               public Void run() {
/* 211 */                 if (!logger.isLoggable(level)) {
/* 212 */                   logger.setLevel(level);
/*     */                 }
/* 214 */                 logger.addHandler(Log.LoggerLog.alternateConsole);
/* 215 */                 return null;
/*     */               }
/*     */             });
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isLoggable(Level param1Level) {
/* 223 */       return this.logger.isLoggable(param1Level);
/*     */     }
/*     */     
/*     */     public void log(Level param1Level, String param1String) {
/* 227 */       if (isLoggable(param1Level)) {
/* 228 */         String[] arrayOfString = Log.getSource();
/* 229 */         this.logger.logp(param1Level, arrayOfString[0], arrayOfString[1], 
/* 230 */             Thread.currentThread().getName() + ": " + param1String);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void log(Level param1Level, String param1String, Throwable param1Throwable) {
/* 235 */       if (isLoggable(param1Level)) {
/* 236 */         String[] arrayOfString = Log.getSource();
/* 237 */         this.logger.logp(param1Level, arrayOfString[0], arrayOfString[1], 
/* 238 */             Thread.currentThread().getName() + ": " + param1String, param1Throwable);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public synchronized void setOutputStream(OutputStream param1OutputStream) {
/* 250 */       if (param1OutputStream != null) {
/* 251 */         if (!this.logger.isLoggable(VERBOSE)) {
/* 252 */           this.logger.setLevel(VERBOSE);
/*     */         }
/* 254 */         this.copyHandler = new Log.InternalStreamHandler(param1OutputStream);
/* 255 */         this.copyHandler.setLevel(Log.VERBOSE);
/* 256 */         this.logger.addHandler(this.copyHandler);
/*     */       } else {
/*     */         
/* 259 */         if (this.copyHandler != null) {
/* 260 */           this.logger.removeHandler(this.copyHandler);
/*     */         }
/* 262 */         this.copyHandler = null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public synchronized PrintStream getPrintStream() {
/* 267 */       if (this.loggerSandwich == null) {
/* 268 */         this.loggerSandwich = new Log.LoggerPrintStream(this.logger);
/*     */       }
/* 270 */       return this.loggerSandwich;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class InternalStreamHandler
/*     */     extends StreamHandler
/*     */   {
/*     */     InternalStreamHandler(OutputStream param1OutputStream) {
/* 280 */       super(param1OutputStream, new SimpleFormatter());
/*     */     }
/*     */     
/*     */     public void publish(LogRecord param1LogRecord) {
/* 284 */       super.publish(param1LogRecord);
/* 285 */       flush();
/*     */     }
/*     */     
/*     */     public void close() {
/* 289 */       flush();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LoggerPrintStream
/*     */     extends PrintStream
/*     */   {
/*     */     private final Logger logger;
/*     */ 
/*     */ 
/*     */     
/* 304 */     private int last = -1;
/*     */ 
/*     */     
/*     */     private final ByteArrayOutputStream bufOut;
/*     */ 
/*     */     
/*     */     private LoggerPrintStream(Logger param1Logger) {
/* 311 */       super(new ByteArrayOutputStream());
/* 312 */       this.bufOut = (ByteArrayOutputStream)this.out;
/* 313 */       this.logger = param1Logger;
/*     */     }
/*     */     
/*     */     public void write(int param1Int) {
/* 317 */       if (this.last == 13 && param1Int == 10) {
/* 318 */         this.last = -1; return;
/*     */       } 
/* 320 */       if (param1Int == 10 || param1Int == 13) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 325 */           String str = Thread.currentThread().getName() + ": " + this.bufOut.toString();
/* 326 */           this.logger.logp(Level.INFO, "LogStream", "print", str);
/*     */         } finally {
/* 328 */           this.bufOut.reset();
/*     */         } 
/*     */       } else {
/* 331 */         super.write(param1Int);
/*     */       } 
/* 333 */       this.last = param1Int;
/*     */     }
/*     */     
/*     */     public void write(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
/* 337 */       if (param1Int2 < 0) {
/* 338 */         throw new ArrayIndexOutOfBoundsException(param1Int2);
/*     */       }
/* 340 */       for (byte b = 0; b < param1Int2; b++) {
/* 341 */         write(param1ArrayOfbyte[param1Int1 + b]);
/*     */       }
/*     */     }
/*     */     
/*     */     public String toString() {
/* 346 */       return "RMI";
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
/*     */   private static class LogStreamLogFactory
/*     */     implements LogFactory
/*     */   {
/*     */     public Log createLog(String param1String1, String param1String2, Level param1Level) {
/* 361 */       LogStream logStream = null;
/* 362 */       if (param1String2 != null) {
/* 363 */         logStream = LogStream.log(param1String2);
/*     */       }
/* 365 */       return new Log.LogStreamLog(logStream, param1Level);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LogStreamLog
/*     */     extends Log
/*     */   {
/*     */     private final LogStream stream;
/*     */ 
/*     */     
/* 378 */     private int levelValue = Level.OFF.intValue();
/*     */     
/*     */     private LogStreamLog(LogStream param1LogStream, Level param1Level) {
/* 381 */       if (param1LogStream != null && param1Level != null)
/*     */       {
/*     */ 
/*     */         
/* 385 */         this.levelValue = param1Level.intValue();
/*     */       }
/* 387 */       this.stream = param1LogStream;
/*     */     }
/*     */     
/*     */     public synchronized boolean isLoggable(Level param1Level) {
/* 391 */       return (param1Level.intValue() >= this.levelValue);
/*     */     }
/*     */     
/*     */     public void log(Level param1Level, String param1String) {
/* 395 */       if (isLoggable(param1Level)) {
/* 396 */         String[] arrayOfString = Log.getSource();
/* 397 */         this.stream.println(unqualifiedName(arrayOfString[0]) + "." + arrayOfString[1] + ": " + param1String);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void log(Level param1Level, String param1String, Throwable param1Throwable) {
/* 403 */       if (isLoggable(param1Level))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 408 */         synchronized (this.stream) {
/* 409 */           String[] arrayOfString = Log.getSource();
/* 410 */           this.stream.println(unqualifiedName(arrayOfString[0]) + "." + arrayOfString[1] + ": " + param1String);
/*     */           
/* 412 */           param1Throwable.printStackTrace(this.stream);
/*     */         } 
/*     */       }
/*     */     }
/*     */     
/*     */     public PrintStream getPrintStream() {
/* 418 */       return this.stream;
/*     */     }
/*     */     
/*     */     public synchronized void setOutputStream(OutputStream param1OutputStream) {
/* 422 */       if (param1OutputStream != null) {
/* 423 */         if (VERBOSE.intValue() < this.levelValue) {
/* 424 */           this.levelValue = VERBOSE.intValue();
/*     */         }
/* 426 */         this.stream.setOutputStream(param1OutputStream);
/*     */       } else {
/*     */         
/* 429 */         this.levelValue = Level.OFF.intValue();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String unqualifiedName(String param1String) {
/* 437 */       int i = param1String.lastIndexOf(".");
/* 438 */       if (i >= 0) {
/* 439 */         param1String = param1String.substring(i + 1);
/*     */       }
/* 441 */       param1String = param1String.replace('$', '.');
/* 442 */       return param1String;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] getSource() {
/* 450 */     StackTraceElement[] arrayOfStackTraceElement = (new Exception()).getStackTrace();
/* 451 */     return new String[] { arrayOfStackTraceElement[3]
/* 452 */         .getClassName(), arrayOfStackTraceElement[3]
/* 453 */         .getMethodName() };
/*     */   }
/*     */   
/*     */   public abstract boolean isLoggable(Level paramLevel);
/*     */   
/*     */   public abstract void log(Level paramLevel, String paramString);
/*     */   
/*     */   public abstract void log(Level paramLevel, String paramString, Throwable paramThrowable);
/*     */   
/*     */   public abstract void setOutputStream(OutputStream paramOutputStream);
/*     */   
/*     */   public abstract PrintStream getPrintStream();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/rmi/runtime/Log.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */