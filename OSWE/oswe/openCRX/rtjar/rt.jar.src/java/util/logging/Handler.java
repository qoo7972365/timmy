/*     */ package java.util.logging;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.IllegalCharsetNameException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Handler
/*     */ {
/*  49 */   private static final int offValue = Level.OFF.intValue();
/*  50 */   private final LogManager manager = LogManager.getLogManager();
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Filter filter;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile Formatter formatter;
/*     */ 
/*     */   
/*  61 */   private volatile Level logLevel = Level.ALL;
/*  62 */   private volatile ErrorManager errorManager = new ErrorManager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile String encoding;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean sealed = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void publish(LogRecord paramLogRecord);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void flush();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void close() throws SecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void setFormatter(Formatter paramFormatter) throws SecurityException {
/* 122 */     checkPermission();
/*     */     
/* 124 */     paramFormatter.getClass();
/* 125 */     this.formatter = paramFormatter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Formatter getFormatter() {
/* 133 */     return this.formatter;
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
/*     */   public synchronized void setEncoding(String paramString) throws SecurityException, UnsupportedEncodingException {
/* 151 */     checkPermission();
/* 152 */     if (paramString != null) {
/*     */       try {
/* 154 */         if (!Charset.isSupported(paramString)) {
/* 155 */           throw new UnsupportedEncodingException(paramString);
/*     */         }
/* 157 */       } catch (IllegalCharsetNameException illegalCharsetNameException) {
/* 158 */         throw new UnsupportedEncodingException(paramString);
/*     */       } 
/*     */     }
/* 161 */     this.encoding = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEncoding() {
/* 171 */     return this.encoding;
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
/*     */   public synchronized void setFilter(Filter paramFilter) throws SecurityException {
/* 186 */     checkPermission();
/* 187 */     this.filter = paramFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter getFilter() {
/* 196 */     return this.filter;
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
/*     */   public synchronized void setErrorManager(ErrorManager paramErrorManager) {
/* 210 */     checkPermission();
/* 211 */     if (paramErrorManager == null) {
/* 212 */       throw new NullPointerException();
/*     */     }
/* 214 */     this.errorManager = paramErrorManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ErrorManager getErrorManager() {
/* 225 */     checkPermission();
/* 226 */     return this.errorManager;
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
/*     */   protected void reportError(String paramString, Exception paramException, int paramInt) {
/*     */     try {
/* 241 */       this.errorManager.error(paramString, paramException, paramInt);
/* 242 */     } catch (Exception exception) {
/* 243 */       System.err.println("Handler.reportError caught:");
/* 244 */       exception.printStackTrace();
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
/*     */   public synchronized void setLevel(Level paramLevel) throws SecurityException {
/* 262 */     if (paramLevel == null) {
/* 263 */       throw new NullPointerException();
/*     */     }
/* 265 */     checkPermission();
/* 266 */     this.logLevel = paramLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Level getLevel() {
/* 276 */     return this.logLevel;
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
/* 293 */     int i = getLevel().intValue();
/* 294 */     if (paramLogRecord.getLevel().intValue() < i || i == offValue) {
/* 295 */       return false;
/*     */     }
/* 297 */     Filter filter = getFilter();
/* 298 */     if (filter == null) {
/* 299 */       return true;
/*     */     }
/* 301 */     return filter.isLoggable(paramLogRecord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void checkPermission() throws SecurityException {
/* 309 */     if (this.sealed)
/* 310 */       this.manager.checkPermission(); 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/Handler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */