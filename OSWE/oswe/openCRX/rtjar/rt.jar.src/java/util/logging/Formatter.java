/*     */ package java.util.logging;
/*     */ 
/*     */ import java.text.MessageFormat;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Formatter
/*     */ {
/*     */   public abstract String format(LogRecord paramLogRecord);
/*     */   
/*     */   public String getHead(Handler paramHandler) {
/*  75 */     return "";
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
/*     */   public String getTail(Handler paramHandler) {
/*  88 */     return "";
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
/*     */   public synchronized String formatMessage(LogRecord paramLogRecord) {
/* 114 */     String str = paramLogRecord.getMessage();
/* 115 */     ResourceBundle resourceBundle = paramLogRecord.getResourceBundle();
/* 116 */     if (resourceBundle != null) {
/*     */       try {
/* 118 */         str = resourceBundle.getString(paramLogRecord.getMessage());
/* 119 */       } catch (MissingResourceException missingResourceException) {
/*     */         
/* 121 */         str = paramLogRecord.getMessage();
/*     */       } 
/*     */     }
/*     */     
/*     */     try {
/* 126 */       Object[] arrayOfObject = paramLogRecord.getParameters();
/* 127 */       if (arrayOfObject == null || arrayOfObject.length == 0)
/*     */       {
/* 129 */         return str;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       if (str.indexOf("{0") >= 0 || str.indexOf("{1") >= 0 || str
/* 137 */         .indexOf("{2") >= 0 || str.indexOf("{3") >= 0) {
/* 138 */         return MessageFormat.format(str, arrayOfObject);
/*     */       }
/* 140 */       return str;
/*     */     }
/* 142 */     catch (Exception exception) {
/*     */       
/* 144 */       return str;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/Formatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */