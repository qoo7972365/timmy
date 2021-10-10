/*     */ package com.sun.jmx.remote.util;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassLogger
/*     */ {
/*     */   private static final boolean ok;
/*     */   private final String className;
/*     */   private final Logger logger;
/*     */   
/*     */   static {
/*  42 */     boolean bool = false;
/*     */     try {
/*  44 */       Class<Logger> clazz = Logger.class;
/*  45 */       bool = true;
/*  46 */     } catch (Error error) {}
/*     */ 
/*     */ 
/*     */     
/*  50 */     ok = bool;
/*     */   }
/*     */   
/*     */   public ClassLogger(String paramString1, String paramString2) {
/*  54 */     if (ok) {
/*  55 */       this.logger = Logger.getLogger(paramString1);
/*     */     } else {
/*  57 */       this.logger = null;
/*  58 */     }  this.className = paramString2;
/*     */   }
/*     */   
/*     */   public final boolean traceOn() {
/*  62 */     return finerOn();
/*     */   }
/*     */   
/*     */   public final boolean debugOn() {
/*  66 */     return finestOn();
/*     */   }
/*     */   
/*     */   public final boolean warningOn() {
/*  70 */     return (ok && this.logger.isLoggable(Level.WARNING));
/*     */   }
/*     */   
/*     */   public final boolean infoOn() {
/*  74 */     return (ok && this.logger.isLoggable(Level.INFO));
/*     */   }
/*     */   
/*     */   public final boolean configOn() {
/*  78 */     return (ok && this.logger.isLoggable(Level.CONFIG));
/*     */   }
/*     */   
/*     */   public final boolean fineOn() {
/*  82 */     return (ok && this.logger.isLoggable(Level.FINE));
/*     */   }
/*     */   
/*     */   public final boolean finerOn() {
/*  86 */     return (ok && this.logger.isLoggable(Level.FINER));
/*     */   }
/*     */   
/*     */   public final boolean finestOn() {
/*  90 */     return (ok && this.logger.isLoggable(Level.FINEST));
/*     */   }
/*     */   
/*     */   public final void debug(String paramString1, String paramString2) {
/*  94 */     finest(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public final void debug(String paramString, Throwable paramThrowable) {
/*  98 */     finest(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public final void debug(String paramString1, String paramString2, Throwable paramThrowable) {
/* 102 */     finest(paramString1, paramString2, paramThrowable);
/*     */   }
/*     */   
/*     */   public final void trace(String paramString1, String paramString2) {
/* 106 */     finer(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public final void trace(String paramString, Throwable paramThrowable) {
/* 110 */     finer(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public final void trace(String paramString1, String paramString2, Throwable paramThrowable) {
/* 114 */     finer(paramString1, paramString2, paramThrowable);
/*     */   }
/*     */   
/*     */   public final void error(String paramString1, String paramString2) {
/* 118 */     severe(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public final void error(String paramString, Throwable paramThrowable) {
/* 122 */     severe(paramString, paramThrowable);
/*     */   }
/*     */   
/*     */   public final void error(String paramString1, String paramString2, Throwable paramThrowable) {
/* 126 */     severe(paramString1, paramString2, paramThrowable);
/*     */   }
/*     */   
/*     */   public final void finest(String paramString1, String paramString2) {
/* 130 */     if (ok)
/* 131 */       this.logger.logp(Level.FINEST, this.className, paramString1, paramString2); 
/*     */   }
/*     */   
/*     */   public final void finest(String paramString, Throwable paramThrowable) {
/* 135 */     if (ok)
/* 136 */       this.logger.logp(Level.FINEST, this.className, paramString, paramThrowable
/* 137 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public final void finest(String paramString1, String paramString2, Throwable paramThrowable) {
/* 141 */     if (ok) {
/* 142 */       this.logger.logp(Level.FINEST, this.className, paramString1, paramString2, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void finer(String paramString1, String paramString2) {
/* 147 */     if (ok)
/* 148 */       this.logger.logp(Level.FINER, this.className, paramString1, paramString2); 
/*     */   }
/*     */   
/*     */   public final void finer(String paramString, Throwable paramThrowable) {
/* 152 */     if (ok)
/* 153 */       this.logger.logp(Level.FINER, this.className, paramString, paramThrowable
/* 154 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public final void finer(String paramString1, String paramString2, Throwable paramThrowable) {
/* 158 */     if (ok)
/* 159 */       this.logger.logp(Level.FINER, this.className, paramString1, paramString2, paramThrowable); 
/*     */   }
/*     */   
/*     */   public final void fine(String paramString1, String paramString2) {
/* 163 */     if (ok)
/* 164 */       this.logger.logp(Level.FINE, this.className, paramString1, paramString2); 
/*     */   }
/*     */   
/*     */   public final void fine(String paramString, Throwable paramThrowable) {
/* 168 */     if (ok)
/* 169 */       this.logger.logp(Level.FINE, this.className, paramString, paramThrowable
/* 170 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public final void fine(String paramString1, String paramString2, Throwable paramThrowable) {
/* 174 */     if (ok) {
/* 175 */       this.logger.logp(Level.FINE, this.className, paramString1, paramString2, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void config(String paramString1, String paramString2) {
/* 180 */     if (ok)
/* 181 */       this.logger.logp(Level.CONFIG, this.className, paramString1, paramString2); 
/*     */   }
/*     */   
/*     */   public final void config(String paramString, Throwable paramThrowable) {
/* 185 */     if (ok)
/* 186 */       this.logger.logp(Level.CONFIG, this.className, paramString, paramThrowable
/* 187 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public final void config(String paramString1, String paramString2, Throwable paramThrowable) {
/* 191 */     if (ok) {
/* 192 */       this.logger.logp(Level.CONFIG, this.className, paramString1, paramString2, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void info(String paramString1, String paramString2) {
/* 197 */     if (ok)
/* 198 */       this.logger.logp(Level.INFO, this.className, paramString1, paramString2); 
/*     */   }
/*     */   
/*     */   public final void info(String paramString, Throwable paramThrowable) {
/* 202 */     if (ok)
/* 203 */       this.logger.logp(Level.INFO, this.className, paramString, paramThrowable
/* 204 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public final void info(String paramString1, String paramString2, Throwable paramThrowable) {
/* 208 */     if (ok) {
/* 209 */       this.logger.logp(Level.INFO, this.className, paramString1, paramString2, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void warning(String paramString1, String paramString2) {
/* 214 */     if (ok)
/* 215 */       this.logger.logp(Level.WARNING, this.className, paramString1, paramString2); 
/*     */   }
/*     */   
/*     */   public final void warning(String paramString, Throwable paramThrowable) {
/* 219 */     if (ok)
/* 220 */       this.logger.logp(Level.WARNING, this.className, paramString, paramThrowable
/* 221 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public final void warning(String paramString1, String paramString2, Throwable paramThrowable) {
/* 225 */     if (ok) {
/* 226 */       this.logger.logp(Level.WARNING, this.className, paramString1, paramString2, paramThrowable);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void severe(String paramString1, String paramString2) {
/* 231 */     if (ok)
/* 232 */       this.logger.logp(Level.SEVERE, this.className, paramString1, paramString2); 
/*     */   }
/*     */   
/*     */   public final void severe(String paramString, Throwable paramThrowable) {
/* 236 */     if (ok)
/* 237 */       this.logger.logp(Level.SEVERE, this.className, paramString, paramThrowable
/* 238 */           .toString(), paramThrowable); 
/*     */   }
/*     */   
/*     */   public final void severe(String paramString1, String paramString2, Throwable paramThrowable) {
/* 242 */     if (ok)
/* 243 */       this.logger.logp(Level.SEVERE, this.className, paramString1, paramString2, paramThrowable); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/jmx/remote/util/ClassLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */