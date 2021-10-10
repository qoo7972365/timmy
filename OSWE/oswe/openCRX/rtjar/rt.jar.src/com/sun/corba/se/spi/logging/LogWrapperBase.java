/*    */ package com.sun.corba.se.spi.logging;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.LogRecord;
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
/*    */ public abstract class LogWrapperBase
/*    */ {
/*    */   protected Logger logger;
/*    */   protected String loggerName;
/*    */   
/*    */   protected LogWrapperBase(Logger paramLogger) {
/* 39 */     this.logger = paramLogger;
/* 40 */     this.loggerName = paramLogger.getName();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void doLog(Level paramLevel, String paramString, Object[] paramArrayOfObject, Class paramClass, Throwable paramThrowable) {
/* 46 */     LogRecord logRecord = new LogRecord(paramLevel, paramString);
/* 47 */     if (paramArrayOfObject != null)
/* 48 */       logRecord.setParameters(paramArrayOfObject); 
/* 49 */     inferCaller(paramClass, logRecord);
/* 50 */     logRecord.setThrown(paramThrowable);
/* 51 */     logRecord.setLoggerName(this.loggerName);
/* 52 */     logRecord.setResourceBundle(this.logger.getResourceBundle());
/* 53 */     this.logger.log(logRecord);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void inferCaller(Class paramClass, LogRecord paramLogRecord) {
/* 61 */     StackTraceElement[] arrayOfStackTraceElement = (new Throwable()).getStackTrace();
/* 62 */     StackTraceElement stackTraceElement = null;
/* 63 */     String str1 = paramClass.getName();
/* 64 */     String str2 = LogWrapperBase.class.getName();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 69 */     byte b = 0;
/* 70 */     while (b < arrayOfStackTraceElement.length) {
/* 71 */       stackTraceElement = arrayOfStackTraceElement[b];
/* 72 */       String str = stackTraceElement.getClassName();
/* 73 */       if (!str.equals(str1) && !str.equals(str2)) {
/*    */         break;
/*    */       }
/*    */       
/* 77 */       b++;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 82 */     if (b < arrayOfStackTraceElement.length) {
/* 83 */       paramLogRecord.setSourceClassName(stackTraceElement.getClassName());
/* 84 */       paramLogRecord.setSourceMethodName(stackTraceElement.getMethodName());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doLog(Level paramLevel, String paramString, Class paramClass, Throwable paramThrowable) {
/* 90 */     doLog(paramLevel, paramString, null, paramClass, paramThrowable);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/corba/se/spi/logging/LogWrapperBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */