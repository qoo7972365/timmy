/*     */ package sun.awt.X11;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.security.AccessController;
/*     */ import java.text.FieldPosition;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Date;
/*     */ import java.util.logging.Formatter;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogManager;
/*     */ import java.util.logging.LogRecord;
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
/*     */ public class XAWTFormatter
/*     */   extends Formatter
/*     */ {
/*  38 */   Date dat = new Date();
/*     */   
/*     */   private static final String format = "{0,date} {0,time}";
/*     */   private MessageFormat formatter;
/*  42 */   private Object[] args = new Object[1];
/*     */ 
/*     */ 
/*     */   
/*  46 */   private String lineSeparator = AccessController.<String>doPrivileged(new GetPropertyAction("line.separator"));
/*     */   
/*     */   boolean displayFullRecord = false;
/*     */   boolean useANSI = false;
/*     */   boolean showDate = true;
/*     */   boolean showLevel = true;
/*     */   boolean swapMethodClass = false;
/*     */   
/*     */   public XAWTFormatter() {
/*  55 */     this.displayFullRecord = "true".equals(LogManager.getLogManager().getProperty("XAWTFormatter.displayFullRecord"));
/*  56 */     this.useANSI = "true".equals(LogManager.getLogManager().getProperty("XAWTFormatter.useANSI"));
/*  57 */     this.showDate = !"false".equals(LogManager.getLogManager().getProperty("XAWTFormatter.showDate"));
/*  58 */     this.showLevel = !"false".equals(LogManager.getLogManager().getProperty("XAWTFormatter.showLevel"));
/*  59 */     this.swapMethodClass = "true".equals(LogManager.getLogManager().getProperty("XAWTFormatter.swapMethodClass"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String format(LogRecord paramLogRecord) {
/*  68 */     StringBuffer stringBuffer = new StringBuffer();
/*  69 */     if (this.useANSI) {
/*  70 */       Level level = paramLogRecord.getLevel();
/*  71 */       if (Level.FINEST.equals(level)) {
/*  72 */         stringBuffer.append("\033[36m");
/*  73 */       } else if (Level.FINER.equals(level)) {
/*  74 */         stringBuffer.append("\033[32m");
/*  75 */       } else if (Level.FINE.equals(level)) {
/*  76 */         stringBuffer.append("\033[34m");
/*     */       } 
/*     */     } 
/*  79 */     if (this.displayFullRecord) {
/*  80 */       if (this.showDate) {
/*     */         
/*  82 */         this.dat.setTime(paramLogRecord.getMillis());
/*  83 */         this.args[0] = this.dat;
/*  84 */         StringBuffer stringBuffer1 = new StringBuffer();
/*  85 */         if (this.formatter == null) {
/*  86 */           this.formatter = new MessageFormat("{0,date} {0,time}");
/*     */         }
/*  88 */         this.formatter.format(this.args, stringBuffer1, (FieldPosition)null);
/*  89 */         stringBuffer.append(stringBuffer1);
/*  90 */         stringBuffer.append(" ");
/*     */       } else {
/*  92 */         stringBuffer.append("    ");
/*     */       } 
/*  94 */       if (this.swapMethodClass) {
/*  95 */         if (paramLogRecord.getSourceMethodName() != null) {
/*  96 */           stringBuffer.append(" \033[35m");
/*  97 */           stringBuffer.append(paramLogRecord.getSourceMethodName());
/*  98 */           stringBuffer.append("\033[30m ");
/*     */         } 
/* 100 */         if (paramLogRecord.getSourceClassName() != null) {
/* 101 */           stringBuffer.append(paramLogRecord.getSourceClassName());
/*     */         } else {
/* 103 */           stringBuffer.append(paramLogRecord.getLoggerName());
/*     */         } 
/*     */       } else {
/* 106 */         if (paramLogRecord.getSourceClassName() != null) {
/* 107 */           stringBuffer.append(paramLogRecord.getSourceClassName());
/*     */         } else {
/* 109 */           stringBuffer.append(paramLogRecord.getLoggerName());
/*     */         } 
/* 111 */         if (paramLogRecord.getSourceMethodName() != null) {
/* 112 */           stringBuffer.append(" \033[35m");
/* 113 */           stringBuffer.append(paramLogRecord.getSourceMethodName());
/* 114 */           stringBuffer.append("\033[30m");
/*     */         } 
/*     */       } 
/* 117 */       stringBuffer.append(this.lineSeparator);
/*     */     } 
/* 119 */     if (this.useANSI) {
/* 120 */       Level level = paramLogRecord.getLevel();
/* 121 */       if (Level.FINEST.equals(level)) {
/* 122 */         stringBuffer.append("\033[36m");
/* 123 */       } else if (Level.FINER.equals(level)) {
/* 124 */         stringBuffer.append("\033[32m");
/* 125 */       } else if (Level.FINE.equals(level)) {
/* 126 */         stringBuffer.append("\033[34m");
/*     */       } 
/*     */     } 
/* 129 */     if (this.showLevel) {
/* 130 */       stringBuffer.append(paramLogRecord.getLevel().getLocalizedName());
/* 131 */       stringBuffer.append(": ");
/*     */     } 
/* 133 */     String str = formatMessage(paramLogRecord);
/* 134 */     stringBuffer.append(str);
/* 135 */     stringBuffer.append(this.lineSeparator);
/* 136 */     if (paramLogRecord.getThrown() != null) {
/*     */       try {
/* 138 */         StringWriter stringWriter = new StringWriter();
/* 139 */         PrintWriter printWriter = new PrintWriter(stringWriter);
/* 140 */         paramLogRecord.getThrown().printStackTrace(printWriter);
/* 141 */         printWriter.close();
/* 142 */         stringBuffer.append(stringWriter.toString());
/* 143 */       } catch (Exception exception) {}
/*     */     }
/*     */     
/* 146 */     if (this.useANSI) {
/* 147 */       stringBuffer.append("\033[30m");
/*     */     }
/* 149 */     return stringBuffer.toString();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XAWTFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */