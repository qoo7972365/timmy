/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamHandler
/*     */   extends Handler
/*     */ {
/*     */   private OutputStream output;
/*     */   private boolean doneHeader;
/*     */   private volatile Writer writer;
/*     */   
/*     */   private void configure() {
/*  84 */     LogManager logManager = LogManager.getLogManager();
/*  85 */     String str = getClass().getName();
/*     */     
/*  87 */     setLevel(logManager.getLevelProperty(str + ".level", Level.INFO));
/*  88 */     setFilter(logManager.getFilterProperty(str + ".filter", null));
/*  89 */     setFormatter(logManager.getFormatterProperty(str + ".formatter", new SimpleFormatter()));
/*     */     try {
/*  91 */       setEncoding(logManager.getStringProperty(str + ".encoding", null));
/*  92 */     } catch (Exception exception) {
/*     */       try {
/*  94 */         setEncoding((String)null);
/*  95 */       } catch (Exception exception1) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamHandler() {
/* 106 */     this.sealed = false;
/* 107 */     configure();
/* 108 */     this.sealed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StreamHandler(OutputStream paramOutputStream, Formatter paramFormatter) {
/* 119 */     this.sealed = false;
/* 120 */     configure();
/* 121 */     setFormatter(paramFormatter);
/* 122 */     setOutputStream(paramOutputStream);
/* 123 */     this.sealed = true;
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
/*     */   protected synchronized void setOutputStream(OutputStream paramOutputStream) throws SecurityException {
/* 138 */     if (paramOutputStream == null) {
/* 139 */       throw new NullPointerException();
/*     */     }
/* 141 */     flushAndClose();
/* 142 */     this.output = paramOutputStream;
/* 143 */     this.doneHeader = false;
/* 144 */     String str = getEncoding();
/* 145 */     if (str == null) {
/* 146 */       this.writer = new OutputStreamWriter(this.output);
/*     */     } else {
/*     */       try {
/* 149 */         this.writer = new OutputStreamWriter(this.output, str);
/* 150 */       } catch (UnsupportedEncodingException unsupportedEncodingException) {
/*     */ 
/*     */         
/* 153 */         throw new Error("Unexpected exception " + unsupportedEncodingException);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setEncoding(String paramString) throws SecurityException, UnsupportedEncodingException {
/* 174 */     super.setEncoding(paramString);
/* 175 */     if (this.output == null) {
/*     */       return;
/*     */     }
/*     */     
/* 179 */     flush();
/* 180 */     if (paramString == null) {
/* 181 */       this.writer = new OutputStreamWriter(this.output);
/*     */     } else {
/* 183 */       this.writer = new OutputStreamWriter(this.output, paramString);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void publish(LogRecord paramLogRecord) {
/*     */     String str;
/* 206 */     if (!isLoggable(paramLogRecord)) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 211 */       str = getFormatter().format(paramLogRecord);
/* 212 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 215 */       reportError(null, exception, 5);
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 220 */       if (!this.doneHeader) {
/* 221 */         this.writer.write(getFormatter().getHead(this));
/* 222 */         this.doneHeader = true;
/*     */       } 
/* 224 */       this.writer.write(str);
/* 225 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 228 */       reportError(null, exception, 1);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLoggable(LogRecord paramLogRecord) {
/* 246 */     if (this.writer == null || paramLogRecord == null) {
/* 247 */       return false;
/*     */     }
/* 249 */     return super.isLoggable(paramLogRecord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() {
/* 257 */     if (this.writer != null) {
/*     */       try {
/* 259 */         this.writer.flush();
/* 260 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 263 */         reportError(null, exception, 2);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private synchronized void flushAndClose() throws SecurityException {
/* 269 */     checkPermission();
/* 270 */     if (this.writer != null) {
/*     */       try {
/* 272 */         if (!this.doneHeader) {
/* 273 */           this.writer.write(getFormatter().getHead(this));
/* 274 */           this.doneHeader = true;
/*     */         } 
/* 276 */         this.writer.write(getFormatter().getTail(this));
/* 277 */         this.writer.flush();
/* 278 */         this.writer.close();
/* 279 */       } catch (Exception exception) {
/*     */ 
/*     */         
/* 282 */         reportError(null, exception, 3);
/*     */       } 
/* 284 */       this.writer = null;
/* 285 */       this.output = null;
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
/*     */ 
/*     */   
/*     */   public synchronized void close() throws SecurityException {
/* 302 */     flushAndClose();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/StreamHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */