/*     */ package java.util.logging;
/*     */ 
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.GregorianCalendar;
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
/*     */ public class XMLFormatter
/*     */   extends Formatter
/*     */ {
/*  47 */   private LogManager manager = LogManager.getLogManager();
/*     */ 
/*     */   
/*     */   private void a2(StringBuilder paramStringBuilder, int paramInt) {
/*  51 */     if (paramInt < 10) {
/*  52 */       paramStringBuilder.append('0');
/*     */     }
/*  54 */     paramStringBuilder.append(paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   private void appendISO8601(StringBuilder paramStringBuilder, long paramLong) {
/*  59 */     GregorianCalendar gregorianCalendar = new GregorianCalendar();
/*  60 */     gregorianCalendar.setTimeInMillis(paramLong);
/*  61 */     paramStringBuilder.append(gregorianCalendar.get(1));
/*  62 */     paramStringBuilder.append('-');
/*  63 */     a2(paramStringBuilder, gregorianCalendar.get(2) + 1);
/*  64 */     paramStringBuilder.append('-');
/*  65 */     a2(paramStringBuilder, gregorianCalendar.get(5));
/*  66 */     paramStringBuilder.append('T');
/*  67 */     a2(paramStringBuilder, gregorianCalendar.get(11));
/*  68 */     paramStringBuilder.append(':');
/*  69 */     a2(paramStringBuilder, gregorianCalendar.get(12));
/*  70 */     paramStringBuilder.append(':');
/*  71 */     a2(paramStringBuilder, gregorianCalendar.get(13));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void escape(StringBuilder paramStringBuilder, String paramString) {
/*  78 */     if (paramString == null) {
/*  79 */       paramString = "<null>";
/*     */     }
/*  81 */     for (byte b = 0; b < paramString.length(); b++) {
/*  82 */       char c = paramString.charAt(b);
/*  83 */       if (c == '<') {
/*  84 */         paramStringBuilder.append("&lt;");
/*  85 */       } else if (c == '>') {
/*  86 */         paramStringBuilder.append("&gt;");
/*  87 */       } else if (c == '&') {
/*  88 */         paramStringBuilder.append("&amp;");
/*     */       } else {
/*  90 */         paramStringBuilder.append(c);
/*     */       } 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public String format(LogRecord paramLogRecord) {
/* 106 */     StringBuilder stringBuilder = new StringBuilder(500);
/* 107 */     stringBuilder.append("<record>\n");
/*     */     
/* 109 */     stringBuilder.append("  <date>");
/* 110 */     appendISO8601(stringBuilder, paramLogRecord.getMillis());
/* 111 */     stringBuilder.append("</date>\n");
/*     */     
/* 113 */     stringBuilder.append("  <millis>");
/* 114 */     stringBuilder.append(paramLogRecord.getMillis());
/* 115 */     stringBuilder.append("</millis>\n");
/*     */     
/* 117 */     stringBuilder.append("  <sequence>");
/* 118 */     stringBuilder.append(paramLogRecord.getSequenceNumber());
/* 119 */     stringBuilder.append("</sequence>\n");
/*     */     
/* 121 */     String str = paramLogRecord.getLoggerName();
/* 122 */     if (str != null) {
/* 123 */       stringBuilder.append("  <logger>");
/* 124 */       escape(stringBuilder, str);
/* 125 */       stringBuilder.append("</logger>\n");
/*     */     } 
/*     */     
/* 128 */     stringBuilder.append("  <level>");
/* 129 */     escape(stringBuilder, paramLogRecord.getLevel().toString());
/* 130 */     stringBuilder.append("</level>\n");
/*     */     
/* 132 */     if (paramLogRecord.getSourceClassName() != null) {
/* 133 */       stringBuilder.append("  <class>");
/* 134 */       escape(stringBuilder, paramLogRecord.getSourceClassName());
/* 135 */       stringBuilder.append("</class>\n");
/*     */     } 
/*     */     
/* 138 */     if (paramLogRecord.getSourceMethodName() != null) {
/* 139 */       stringBuilder.append("  <method>");
/* 140 */       escape(stringBuilder, paramLogRecord.getSourceMethodName());
/* 141 */       stringBuilder.append("</method>\n");
/*     */     } 
/*     */     
/* 144 */     stringBuilder.append("  <thread>");
/* 145 */     stringBuilder.append(paramLogRecord.getThreadID());
/* 146 */     stringBuilder.append("</thread>\n");
/*     */     
/* 148 */     if (paramLogRecord.getMessage() != null) {
/*     */       
/* 150 */       String str1 = formatMessage(paramLogRecord);
/* 151 */       stringBuilder.append("  <message>");
/* 152 */       escape(stringBuilder, str1);
/* 153 */       stringBuilder.append("</message>");
/* 154 */       stringBuilder.append("\n");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 159 */     ResourceBundle resourceBundle = paramLogRecord.getResourceBundle();
/*     */     try {
/* 161 */       if (resourceBundle != null && resourceBundle.getString(paramLogRecord.getMessage()) != null) {
/* 162 */         stringBuilder.append("  <key>");
/* 163 */         escape(stringBuilder, paramLogRecord.getMessage());
/* 164 */         stringBuilder.append("</key>\n");
/* 165 */         stringBuilder.append("  <catalog>");
/* 166 */         escape(stringBuilder, paramLogRecord.getResourceBundleName());
/* 167 */         stringBuilder.append("</catalog>\n");
/*     */       } 
/* 169 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 173 */     Object[] arrayOfObject = paramLogRecord.getParameters();
/*     */ 
/*     */     
/* 176 */     if (arrayOfObject != null && arrayOfObject.length != 0 && paramLogRecord
/* 177 */       .getMessage().indexOf("{") == -1) {
/* 178 */       for (byte b = 0; b < arrayOfObject.length; b++) {
/* 179 */         stringBuilder.append("  <param>");
/*     */         try {
/* 181 */           escape(stringBuilder, arrayOfObject[b].toString());
/* 182 */         } catch (Exception exception) {
/* 183 */           stringBuilder.append("???");
/*     */         } 
/* 185 */         stringBuilder.append("</param>\n");
/*     */       } 
/*     */     }
/*     */     
/* 189 */     if (paramLogRecord.getThrown() != null) {
/*     */       
/* 191 */       Throwable throwable = paramLogRecord.getThrown();
/* 192 */       stringBuilder.append("  <exception>\n");
/* 193 */       stringBuilder.append("    <message>");
/* 194 */       escape(stringBuilder, throwable.toString());
/* 195 */       stringBuilder.append("</message>\n");
/* 196 */       StackTraceElement[] arrayOfStackTraceElement = throwable.getStackTrace();
/* 197 */       for (byte b = 0; b < arrayOfStackTraceElement.length; b++) {
/* 198 */         StackTraceElement stackTraceElement = arrayOfStackTraceElement[b];
/* 199 */         stringBuilder.append("    <frame>\n");
/* 200 */         stringBuilder.append("      <class>");
/* 201 */         escape(stringBuilder, stackTraceElement.getClassName());
/* 202 */         stringBuilder.append("</class>\n");
/* 203 */         stringBuilder.append("      <method>");
/* 204 */         escape(stringBuilder, stackTraceElement.getMethodName());
/* 205 */         stringBuilder.append("</method>\n");
/*     */         
/* 207 */         if (stackTraceElement.getLineNumber() >= 0) {
/* 208 */           stringBuilder.append("      <line>");
/* 209 */           stringBuilder.append(stackTraceElement.getLineNumber());
/* 210 */           stringBuilder.append("</line>\n");
/*     */         } 
/* 212 */         stringBuilder.append("    </frame>\n");
/*     */       } 
/* 214 */       stringBuilder.append("  </exception>\n");
/*     */     } 
/*     */     
/* 217 */     stringBuilder.append("</record>\n");
/* 218 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHead(Handler paramHandler) {
/*     */     String str;
/* 228 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     
/* 230 */     stringBuilder.append("<?xml version=\"1.0\"");
/*     */     
/* 232 */     if (paramHandler != null) {
/* 233 */       str = paramHandler.getEncoding();
/*     */     } else {
/* 235 */       str = null;
/*     */     } 
/*     */     
/* 238 */     if (str == null)
/*     */     {
/* 240 */       str = Charset.defaultCharset().name();
/*     */     }
/*     */     
/*     */     try {
/* 244 */       Charset charset = Charset.forName(str);
/* 245 */       str = charset.name();
/* 246 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 251 */     stringBuilder.append(" encoding=\"");
/* 252 */     stringBuilder.append(str);
/* 253 */     stringBuilder.append("\"");
/* 254 */     stringBuilder.append(" standalone=\"no\"?>\n");
/* 255 */     stringBuilder.append("<!DOCTYPE log SYSTEM \"logger.dtd\">\n");
/* 256 */     stringBuilder.append("<log>\n");
/* 257 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTail(Handler paramHandler) {
/* 267 */     return "</log>\n";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/XMLFormatter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */