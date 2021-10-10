/*    */ package com.sun.activation.registries;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LogSupport
/*    */ {
/*    */   private static boolean debug = false;
/*    */   private static Logger logger;
/* 37 */   private static final Level level = Level.FINE;
/*    */   
/*    */   static {
/*    */     try {
/* 41 */       debug = Boolean.getBoolean("javax.activation.debug");
/* 42 */     } catch (Throwable throwable) {}
/*    */ 
/*    */     
/* 45 */     logger = Logger.getLogger("javax.activation");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void log(String msg) {
/* 56 */     if (debug)
/* 57 */       System.out.println(msg); 
/* 58 */     logger.log(level, msg);
/*    */   }
/*    */   
/*    */   public static void log(String msg, Throwable t) {
/* 62 */     if (debug)
/* 63 */       System.out.println(msg + "; Exception: " + t); 
/* 64 */     logger.log(level, msg, t);
/*    */   }
/*    */   
/*    */   public static boolean isLoggable() {
/* 68 */     return (debug || logger.isLoggable(level));
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/activation/registries/LogSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */