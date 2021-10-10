/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.Date;
/*     */ import sun.util.logging.LoggingSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleFormatter
/*     */   extends Formatter
/*     */ {
/*  62 */   private static final String format = LoggingSupport.getSimpleFormat();
/*  63 */   private final Date dat = new Date();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String format(LogRecord paramLogRecord) {
/*     */     String str1;
/* 141 */     this.dat.setTime(paramLogRecord.getMillis());
/*     */     
/* 143 */     if (paramLogRecord.getSourceClassName() != null) {
/* 144 */       str1 = paramLogRecord.getSourceClassName();
/* 145 */       if (paramLogRecord.getSourceMethodName() != null) {
/* 146 */         str1 = str1 + " " + paramLogRecord.getSourceMethodName();
/*     */       }
/*     */     } else {
/* 149 */       str1 = paramLogRecord.getLoggerName();
/*     */     } 
/* 151 */     String str2 = formatMessage(paramLogRecord);
/* 152 */     String str3 = "";
/* 153 */     if (paramLogRecord.getThrown() != null) {
/* 154 */       StringWriter stringWriter = new StringWriter();
/* 155 */       PrintWriter printWriter = new PrintWriter(stringWriter);
/* 156 */       printWriter.println();
/* 157 */       paramLogRecord.getThrown().printStackTrace(printWriter);
/* 158 */       printWriter.close();
/* 159 */       str3 = stringWriter.toString();
/*     */     } 
/* 161 */     return String.format(format, new Object[] { this.dat, str1, paramLogRecord
/*     */ 
/*     */           
/* 164 */           .getLoggerName(), paramLogRecord
/* 165 */           .getLevel().getLocalizedLevelName(), str2, str3 });
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/SimpleFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */