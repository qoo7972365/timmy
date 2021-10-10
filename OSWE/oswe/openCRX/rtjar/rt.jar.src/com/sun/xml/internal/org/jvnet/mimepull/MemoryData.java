/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
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
/*     */ final class MemoryData
/*     */   implements Data
/*     */ {
/*  41 */   private static final Logger LOGGER = Logger.getLogger(MemoryData.class.getName());
/*     */   
/*     */   private final byte[] data;
/*     */   private final int len;
/*     */   private final MIMEConfig config;
/*     */   
/*     */   MemoryData(ByteBuffer buf, MIMEConfig config) {
/*  48 */     this.data = buf.array();
/*  49 */     this.len = buf.limit();
/*  50 */     this.config = config;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  56 */     return this.len;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] read() {
/*  61 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public long writeTo(DataFile file) {
/*  66 */     return file.writeTo(this.data, 0, this.len);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Data createNext(DataHead dataHead, ByteBuffer buf) {
/*  77 */     if (!this.config.isOnlyMemory() && dataHead.inMemory >= this.config.memoryThreshold) {
/*     */       try {
/*  79 */         String prefix = this.config.getTempFilePrefix();
/*  80 */         String suffix = this.config.getTempFileSuffix();
/*  81 */         File tempFile = TempFiles.createTempFile(prefix, suffix, this.config.getTempDir());
/*     */         
/*  83 */         tempFile.deleteOnExit();
/*  84 */         if (LOGGER.isLoggable(Level.FINE)) {
/*  85 */           LOGGER.log(Level.FINE, "Created temp file = {0}", tempFile);
/*     */         }
/*     */         
/*  88 */         tempFile.deleteOnExit();
/*  89 */         if (LOGGER.isLoggable(Level.FINE)) LOGGER.log(Level.FINE, "Created temp file = {0}", tempFile); 
/*  90 */         dataHead.dataFile = new DataFile(tempFile);
/*  91 */       } catch (IOException ioe) {
/*  92 */         throw new MIMEParsingException(ioe);
/*     */       } 
/*     */       
/*  95 */       if (dataHead.head != null) {
/*  96 */         for (Chunk c = dataHead.head; c != null; c = c.next) {
/*  97 */           long pointer = c.data.writeTo(dataHead.dataFile);
/*  98 */           c.data = new FileData(dataHead.dataFile, pointer, this.len);
/*     */         } 
/*     */       }
/* 101 */       return new FileData(dataHead.dataFile, buf);
/*     */     } 
/* 103 */     return new MemoryData(buf, this.config);
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/MemoryData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */