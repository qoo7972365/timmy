/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MIMEConfig
/*     */ {
/*     */   private static final int DEFAULT_CHUNK_SIZE = 8192;
/*     */   private static final long DEFAULT_MEMORY_THRESHOLD = 1048576L;
/*     */   private static final String DEFAULT_FILE_PREFIX = "MIME";
/*  43 */   private static final Logger LOGGER = Logger.getLogger(MIMEConfig.class.getName());
/*     */ 
/*     */   
/*     */   boolean parseEagerly;
/*     */ 
/*     */   
/*     */   int chunkSize;
/*     */   
/*     */   long memoryThreshold;
/*     */   
/*     */   File tempDir;
/*     */   
/*     */   String prefix;
/*     */   
/*     */   String suffix;
/*     */ 
/*     */   
/*     */   private MIMEConfig(boolean parseEagerly, int chunkSize, long inMemoryThreshold, String dir, String prefix, String suffix) {
/*  61 */     this.parseEagerly = parseEagerly;
/*  62 */     this.chunkSize = chunkSize;
/*  63 */     this.memoryThreshold = inMemoryThreshold;
/*  64 */     this.prefix = prefix;
/*  65 */     this.suffix = suffix;
/*  66 */     setDir(dir);
/*     */   }
/*     */   
/*     */   public MIMEConfig() {
/*  70 */     this(false, 8192, 1048576L, null, "MIME", null);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isParseEagerly() {
/*  75 */     return this.parseEagerly;
/*     */   }
/*     */   
/*     */   public void setParseEagerly(boolean parseEagerly) {
/*  79 */     this.parseEagerly = parseEagerly;
/*     */   }
/*     */   
/*     */   int getChunkSize() {
/*  83 */     return this.chunkSize;
/*     */   }
/*     */   
/*     */   void setChunkSize(int chunkSize) {
/*  87 */     this.chunkSize = chunkSize;
/*     */   }
/*     */   
/*     */   long getMemoryThreshold() {
/*  91 */     return this.memoryThreshold;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMemoryThreshold(long memoryThreshold) {
/* 102 */     this.memoryThreshold = memoryThreshold;
/*     */   }
/*     */   
/*     */   boolean isOnlyMemory() {
/* 106 */     return (this.memoryThreshold == -1L);
/*     */   }
/*     */   
/*     */   File getTempDir() {
/* 110 */     return this.tempDir;
/*     */   }
/*     */   
/*     */   String getTempFilePrefix() {
/* 114 */     return this.prefix;
/*     */   }
/*     */   
/*     */   String getTempFileSuffix() {
/* 118 */     return this.suffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setDir(String dir) {
/* 125 */     if (this.tempDir == null && dir != null && !dir.equals("")) {
/* 126 */       this.tempDir = new File(dir);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validate() {
/* 135 */     if (!isOnlyMemory())
/*     */       
/*     */       try {
/*     */         
/* 139 */         File tempFile = (this.tempDir == null) ? File.createTempFile(this.prefix, this.suffix) : File.createTempFile(this.prefix, this.suffix, this.tempDir);
/* 140 */         boolean deleted = tempFile.delete();
/* 141 */         if (!deleted && 
/* 142 */           LOGGER.isLoggable(Level.INFO)) {
/* 143 */           LOGGER.log(Level.INFO, "File {0} was not deleted", tempFile.getAbsolutePath());
/*     */         }
/*     */       }
/* 146 */       catch (Exception ioe) {
/* 147 */         this.memoryThreshold = -1L;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/MIMEConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */