/*     */ package java.util.logging;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Logging
/*     */   implements LoggingMXBean
/*     */ {
/*  49 */   private static LogManager logManager = LogManager.getLogManager();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getLoggerNames() {
/*  58 */     Enumeration<String> enumeration = logManager.getLoggerNames();
/*  59 */     ArrayList<String> arrayList = new ArrayList();
/*     */     
/*  61 */     while (enumeration.hasMoreElements()) {
/*  62 */       arrayList.add(enumeration.nextElement());
/*     */     }
/*  64 */     return arrayList;
/*     */   }
/*     */   
/*  67 */   private static String EMPTY_STRING = "";
/*     */   public String getLoggerLevel(String paramString) {
/*  69 */     Logger logger = logManager.getLogger(paramString);
/*  70 */     if (logger == null) {
/*  71 */       return null;
/*     */     }
/*     */     
/*  74 */     Level level = logger.getLevel();
/*  75 */     if (level == null) {
/*  76 */       return EMPTY_STRING;
/*     */     }
/*  78 */     return level.getLevelName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLoggerLevel(String paramString1, String paramString2) {
/*  83 */     if (paramString1 == null) {
/*  84 */       throw new NullPointerException("loggerName is null");
/*     */     }
/*     */     
/*  87 */     Logger logger = logManager.getLogger(paramString1);
/*  88 */     if (logger == null) {
/*  89 */       throw new IllegalArgumentException("Logger " + paramString1 + "does not exist");
/*     */     }
/*     */ 
/*     */     
/*  93 */     Level level = null;
/*  94 */     if (paramString2 != null) {
/*     */       
/*  96 */       level = Level.findLevel(paramString2);
/*  97 */       if (level == null) {
/*  98 */         throw new IllegalArgumentException("Unknown level \"" + paramString2 + "\"");
/*     */       }
/*     */     } 
/*     */     
/* 102 */     logger.setLevel(level);
/*     */   }
/*     */   
/*     */   public String getParentLoggerName(String paramString) {
/* 106 */     Logger logger1 = logManager.getLogger(paramString);
/* 107 */     if (logger1 == null) {
/* 108 */       return null;
/*     */     }
/*     */     
/* 111 */     Logger logger2 = logger1.getParent();
/* 112 */     if (logger2 == null)
/*     */     {
/* 114 */       return EMPTY_STRING;
/*     */     }
/* 116 */     return logger2.getName();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/util/logging/Logging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */