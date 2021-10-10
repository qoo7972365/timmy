/*     */ package java.util.logging;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MemoryHandler
/*     */   extends Handler
/*     */ {
/*     */   private static final int DEFAULT_SIZE = 1000;
/*     */   private volatile Level pushLevel;
/*     */   private int size;
/*     */   private Handler target;
/*     */   private LogRecord[] buffer;
/*     */   int start;
/*     */   int count;
/*     */   
/*     */   private void configure() {
/* 101 */     LogManager logManager = LogManager.getLogManager();
/* 102 */     String str = getClass().getName();
/*     */     
/* 104 */     this.pushLevel = logManager.getLevelProperty(str + ".push", Level.SEVERE);
/* 105 */     this.size = logManager.getIntProperty(str + ".size", 1000);
/* 106 */     if (this.size <= 0) {
/* 107 */       this.size = 1000;
/*     */     }
/* 109 */     setLevel(logManager.getLevelProperty(str + ".level", Level.ALL));
/* 110 */     setFilter(logManager.getFilterProperty(str + ".filter", null));
/* 111 */     setFormatter(logManager.getFormatterProperty(str + ".formatter", new SimpleFormatter()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemoryHandler() {
/* 119 */     this.sealed = false;
/* 120 */     configure();
/* 121 */     this.sealed = true;
/*     */     
/* 123 */     LogManager logManager = LogManager.getLogManager();
/* 124 */     String str1 = getClass().getName();
/* 125 */     String str2 = logManager.getProperty(str1 + ".target");
/* 126 */     if (str2 == null) {
/* 127 */       throw new RuntimeException("The handler " + str1 + " does not specify a target");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 132 */       Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(str2);
/* 133 */       this.target = (Handler)clazz.newInstance();
/* 134 */     } catch (ClassNotFoundException|InstantiationException|IllegalAccessException classNotFoundException) {
/* 135 */       throw new RuntimeException("MemoryHandler can't load handler target \"" + str2 + "\"", classNotFoundException);
/*     */     } 
/* 137 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/* 142 */     this.buffer = new LogRecord[this.size];
/* 143 */     this.start = 0;
/* 144 */     this.count = 0;
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
/*     */   public MemoryHandler(Handler paramHandler, int paramInt, Level paramLevel) {
/* 161 */     if (paramHandler == null || paramLevel == null) {
/* 162 */       throw new NullPointerException();
/*     */     }
/* 164 */     if (paramInt <= 0) {
/* 165 */       throw new IllegalArgumentException();
/*     */     }
/* 167 */     this.sealed = false;
/* 168 */     configure();
/* 169 */     this.sealed = true;
/* 170 */     this.target = paramHandler;
/* 171 */     this.pushLevel = paramLevel;
/* 172 */     this.size = paramInt;
/* 173 */     init();
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
/*     */   public synchronized void publish(LogRecord paramLogRecord) {
/* 193 */     if (!isLoggable(paramLogRecord)) {
/*     */       return;
/*     */     }
/* 196 */     int i = (this.start + this.count) % this.buffer.length;
/* 197 */     this.buffer[i] = paramLogRecord;
/* 198 */     if (this.count < this.buffer.length) {
/* 199 */       this.count++;
/*     */     } else {
/* 201 */       this.start++;
/* 202 */       this.start %= this.buffer.length;
/*     */     } 
/* 204 */     if (paramLogRecord.getLevel().intValue() >= this.pushLevel.intValue()) {
/* 205 */       push();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void push() {
/* 215 */     for (byte b = 0; b < this.count; b++) {
/* 216 */       int i = (this.start + b) % this.buffer.length;
/* 217 */       LogRecord logRecord = this.buffer[i];
/* 218 */       this.target.publish(logRecord);
/*     */     } 
/*     */     
/* 221 */     this.start = 0;
/* 222 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/* 233 */     this.target.flush();
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
/*     */   public void close() throws SecurityException {
/* 245 */     this.target.close();
/* 246 */     setLevel(Level.OFF);
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
/*     */   public synchronized void setPushLevel(Level paramLevel) throws SecurityException {
/* 259 */     if (paramLevel == null) {
/* 260 */       throw new NullPointerException();
/*     */     }
/* 262 */     checkPermission();
/* 263 */     this.pushLevel = paramLevel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Level getPushLevel() {
/* 272 */     return this.pushLevel;
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
/*     */   public boolean isLoggable(LogRecord paramLogRecord) {
/* 290 */     return super.isLoggable(paramLogRecord);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/MemoryHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */