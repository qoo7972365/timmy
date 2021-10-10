/*     */ package com.sun.istack.internal.logging;
/*     */ 
/*     */ import com.sun.istack.internal.NotNull;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Logger
/*     */ {
/*     */   private static final String WS_LOGGING_SUBSYSTEM_NAME_ROOT = "com.sun.metro";
/*     */   private static final String ROOT_WS_PACKAGE = "com.sun.xml.internal.ws.";
/*  48 */   private static final Level METHOD_CALL_LEVEL_VALUE = Level.FINEST;
/*     */ 
/*     */   
/*     */   private final String componentClassName;
/*     */   
/*     */   private final java.util.logging.Logger logger;
/*     */ 
/*     */   
/*     */   protected Logger(String systemLoggerName, String componentName) {
/*  57 */     this.componentClassName = "[" + componentName + "] ";
/*  58 */     this.logger = java.util.logging.Logger.getLogger(systemLoggerName);
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
/*     */   @NotNull
/*     */   public static Logger getLogger(@NotNull Class<?> componentClass) {
/*  77 */     return new Logger(getSystemLoggerName(componentClass), componentClass.getName());
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
/*     */   @NotNull
/*     */   public static Logger getLogger(@NotNull String customLoggerName, @NotNull Class<?> componentClass) {
/*  97 */     return new Logger(customLoggerName, componentClass.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String getSystemLoggerName(@NotNull Class<?> componentClass) {
/* 106 */     StringBuilder sb = new StringBuilder(componentClass.getPackage().getName());
/* 107 */     int lastIndexOfWsPackage = sb.lastIndexOf("com.sun.xml.internal.ws.");
/* 108 */     if (lastIndexOfWsPackage > -1) {
/* 109 */       sb.replace(0, lastIndexOfWsPackage + "com.sun.xml.internal.ws.".length(), "");
/*     */       
/* 111 */       StringTokenizer st = new StringTokenizer(sb.toString(), ".");
/* 112 */       sb = (new StringBuilder("com.sun.metro")).append(".");
/* 113 */       if (st.hasMoreTokens()) {
/* 114 */         String token = st.nextToken();
/* 115 */         if ("api".equals(token)) {
/* 116 */           token = st.nextToken();
/*     */         }
/* 118 */         sb.append(token);
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void log(Level level, String message) {
/* 126 */     if (!this.logger.isLoggable(level)) {
/*     */       return;
/*     */     }
/* 129 */     this.logger.logp(level, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void log(Level level, String message, Object param1) {
/* 133 */     if (!this.logger.isLoggable(level)) {
/*     */       return;
/*     */     }
/* 136 */     this.logger.logp(level, this.componentClassName, getCallerMethodName(), message, param1);
/*     */   }
/*     */   
/*     */   public void log(Level level, String message, Object[] params) {
/* 140 */     if (!this.logger.isLoggable(level)) {
/*     */       return;
/*     */     }
/* 143 */     this.logger.logp(level, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void log(Level level, String message, Throwable thrown) {
/* 147 */     if (!this.logger.isLoggable(level)) {
/*     */       return;
/*     */     }
/* 150 */     this.logger.logp(level, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void finest(String message) {
/* 154 */     if (!this.logger.isLoggable(Level.FINEST)) {
/*     */       return;
/*     */     }
/* 157 */     this.logger.logp(Level.FINEST, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void finest(String message, Object[] params) {
/* 161 */     if (!this.logger.isLoggable(Level.FINEST)) {
/*     */       return;
/*     */     }
/* 164 */     this.logger.logp(Level.FINEST, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void finest(String message, Throwable thrown) {
/* 168 */     if (!this.logger.isLoggable(Level.FINEST)) {
/*     */       return;
/*     */     }
/* 171 */     this.logger.logp(Level.FINEST, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void finer(String message) {
/* 175 */     if (!this.logger.isLoggable(Level.FINER)) {
/*     */       return;
/*     */     }
/* 178 */     this.logger.logp(Level.FINER, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void finer(String message, Object[] params) {
/* 182 */     if (!this.logger.isLoggable(Level.FINER)) {
/*     */       return;
/*     */     }
/* 185 */     this.logger.logp(Level.FINER, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void finer(String message, Throwable thrown) {
/* 189 */     if (!this.logger.isLoggable(Level.FINER)) {
/*     */       return;
/*     */     }
/* 192 */     this.logger.logp(Level.FINER, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void fine(String message) {
/* 196 */     if (!this.logger.isLoggable(Level.FINE)) {
/*     */       return;
/*     */     }
/* 199 */     this.logger.logp(Level.FINE, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void fine(String message, Throwable thrown) {
/* 203 */     if (!this.logger.isLoggable(Level.FINE)) {
/*     */       return;
/*     */     }
/* 206 */     this.logger.logp(Level.FINE, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void info(String message) {
/* 210 */     if (!this.logger.isLoggable(Level.INFO)) {
/*     */       return;
/*     */     }
/* 213 */     this.logger.logp(Level.INFO, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void info(String message, Object[] params) {
/* 217 */     if (!this.logger.isLoggable(Level.INFO)) {
/*     */       return;
/*     */     }
/* 220 */     this.logger.logp(Level.INFO, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void info(String message, Throwable thrown) {
/* 224 */     if (!this.logger.isLoggable(Level.INFO)) {
/*     */       return;
/*     */     }
/* 227 */     this.logger.logp(Level.INFO, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void config(String message) {
/* 231 */     if (!this.logger.isLoggable(Level.CONFIG)) {
/*     */       return;
/*     */     }
/* 234 */     this.logger.logp(Level.CONFIG, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void config(String message, Object[] params) {
/* 238 */     if (!this.logger.isLoggable(Level.CONFIG)) {
/*     */       return;
/*     */     }
/* 241 */     this.logger.logp(Level.CONFIG, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void config(String message, Throwable thrown) {
/* 245 */     if (!this.logger.isLoggable(Level.CONFIG)) {
/*     */       return;
/*     */     }
/* 248 */     this.logger.logp(Level.CONFIG, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void warning(String message) {
/* 252 */     if (!this.logger.isLoggable(Level.WARNING)) {
/*     */       return;
/*     */     }
/* 255 */     this.logger.logp(Level.WARNING, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void warning(String message, Object[] params) {
/* 259 */     if (!this.logger.isLoggable(Level.WARNING)) {
/*     */       return;
/*     */     }
/* 262 */     this.logger.logp(Level.WARNING, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void warning(String message, Throwable thrown) {
/* 266 */     if (!this.logger.isLoggable(Level.WARNING)) {
/*     */       return;
/*     */     }
/* 269 */     this.logger.logp(Level.WARNING, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public void severe(String message) {
/* 273 */     if (!this.logger.isLoggable(Level.SEVERE)) {
/*     */       return;
/*     */     }
/* 276 */     this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), message);
/*     */   }
/*     */   
/*     */   public void severe(String message, Object[] params) {
/* 280 */     if (!this.logger.isLoggable(Level.SEVERE)) {
/*     */       return;
/*     */     }
/* 283 */     this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), message, params);
/*     */   }
/*     */   
/*     */   public void severe(String message, Throwable thrown) {
/* 287 */     if (!this.logger.isLoggable(Level.SEVERE)) {
/*     */       return;
/*     */     }
/* 290 */     this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), message, thrown);
/*     */   }
/*     */   
/*     */   public boolean isMethodCallLoggable() {
/* 294 */     return this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE);
/*     */   }
/*     */   
/*     */   public boolean isLoggable(Level level) {
/* 298 */     return this.logger.isLoggable(level);
/*     */   }
/*     */   
/*     */   public void setLevel(Level level) {
/* 302 */     this.logger.setLevel(level);
/*     */   }
/*     */   
/*     */   public void entering() {
/* 306 */     if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
/*     */       return;
/*     */     }
/*     */     
/* 310 */     this.logger.entering(this.componentClassName, getCallerMethodName());
/*     */   }
/*     */   
/*     */   public void entering(Object... parameters) {
/* 314 */     if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
/*     */       return;
/*     */     }
/*     */     
/* 318 */     this.logger.entering(this.componentClassName, getCallerMethodName(), parameters);
/*     */   }
/*     */   
/*     */   public void exiting() {
/* 322 */     if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
/*     */       return;
/*     */     }
/* 325 */     this.logger.exiting(this.componentClassName, getCallerMethodName());
/*     */   }
/*     */   
/*     */   public void exiting(Object result) {
/* 329 */     if (!this.logger.isLoggable(METHOD_CALL_LEVEL_VALUE)) {
/*     */       return;
/*     */     }
/* 332 */     this.logger.exiting(this.componentClassName, getCallerMethodName(), result);
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
/*     */   public <T extends Throwable> T logSevereException(T exception, Throwable cause) {
/* 351 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 352 */       if (cause == null) {
/* 353 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } else {
/* 355 */         exception.initCause(cause);
/* 356 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage(), cause);
/*     */       } 
/*     */     }
/*     */     
/* 360 */     return exception;
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
/*     */   public <T extends Throwable> T logSevereException(T exception, boolean logCause) {
/* 382 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 383 */       if (logCause && exception.getCause() != null) {
/* 384 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
/*     */       } else {
/* 386 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 390 */     return exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Throwable> T logSevereException(T exception) {
/* 397 */     if (this.logger.isLoggable(Level.SEVERE)) {
/* 398 */       if (exception.getCause() == null) {
/* 399 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } else {
/* 401 */         this.logger.logp(Level.SEVERE, this.componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
/*     */       } 
/*     */     }
/*     */     
/* 405 */     return exception;
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
/*     */   public <T extends Throwable> T logException(T exception, Throwable cause, Level level) {
/* 425 */     if (this.logger.isLoggable(level)) {
/* 426 */       if (cause == null) {
/* 427 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } else {
/* 429 */         exception.initCause(cause);
/* 430 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage(), cause);
/*     */       } 
/*     */     }
/*     */     
/* 434 */     return exception;
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
/*     */   public <T extends Throwable> T logException(T exception, boolean logCause, Level level) {
/* 457 */     if (this.logger.isLoggable(level)) {
/* 458 */       if (logCause && exception.getCause() != null) {
/* 459 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
/*     */       } else {
/* 461 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } 
/*     */     }
/*     */     
/* 465 */     return exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Throwable> T logException(T exception, Level level) {
/* 473 */     if (this.logger.isLoggable(level)) {
/* 474 */       if (exception.getCause() == null) {
/* 475 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage());
/*     */       } else {
/* 477 */         this.logger.logp(level, this.componentClassName, getCallerMethodName(), exception.getMessage(), exception.getCause());
/*     */       } 
/*     */     }
/*     */     
/* 481 */     return exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getCallerMethodName() {
/* 491 */     return getStackMethodName(5);
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
/*     */   private static String getStackMethodName(int methodIndexInStack) {
/*     */     String methodName;
/* 505 */     StackTraceElement[] stack = Thread.currentThread().getStackTrace();
/* 506 */     if (stack.length > methodIndexInStack + 1) {
/* 507 */       methodName = stack[methodIndexInStack].getMethodName();
/*     */     } else {
/* 509 */       methodName = "UNKNOWN METHOD";
/*     */     } 
/*     */     
/* 512 */     return methodName;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/istack/internal/logging/Logger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */