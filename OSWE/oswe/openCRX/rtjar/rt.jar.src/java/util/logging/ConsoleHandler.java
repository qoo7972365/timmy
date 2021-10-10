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
/*     */ public class ConsoleHandler
/*     */   extends StreamHandler
/*     */ {
/*     */   private void configure() {
/*  73 */     LogManager logManager = LogManager.getLogManager();
/*  74 */     String str = getClass().getName();
/*     */     
/*  76 */     setLevel(logManager.getLevelProperty(str + ".level", Level.INFO));
/*  77 */     setFilter(logManager.getFilterProperty(str + ".filter", null));
/*  78 */     setFormatter(logManager.getFormatterProperty(str + ".formatter", new SimpleFormatter()));
/*     */     try {
/*  80 */       setEncoding(logManager.getStringProperty(str + ".encoding", null));
/*  81 */     } catch (Exception exception) {
/*     */       try {
/*  83 */         setEncoding((String)null);
/*  84 */       } catch (Exception exception1) {}
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
/*     */   public ConsoleHandler() {
/*  99 */     this.sealed = false;
/* 100 */     configure();
/* 101 */     setOutputStream(System.err);
/* 102 */     this.sealed = true;
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
/*     */   public void publish(LogRecord paramLogRecord) {
/* 116 */     super.publish(paramLogRecord);
/* 117 */     flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 127 */     flush();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/ConsoleHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */