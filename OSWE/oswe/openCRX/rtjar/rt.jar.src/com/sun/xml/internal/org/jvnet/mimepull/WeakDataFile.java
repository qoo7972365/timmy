/*     */ package com.sun.xml.internal.org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Executor;
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
/*     */ final class WeakDataFile
/*     */   extends WeakReference<DataFile>
/*     */ {
/*  47 */   private static final Logger LOGGER = Logger.getLogger(WeakDataFile.class.getName());
/*     */   
/*  49 */   private static ReferenceQueue<DataFile> refQueue = new ReferenceQueue<>();
/*  50 */   private static List<WeakDataFile> refList = new ArrayList<>();
/*     */   
/*     */   private final File file;
/*     */   
/*     */   static {
/*  55 */     CleanUpExecutorFactory executorFactory = CleanUpExecutorFactory.newInstance();
/*  56 */     if (executorFactory != null) {
/*  57 */       if (LOGGER.isLoggable(Level.FINE)) {
/*  58 */         LOGGER.log(Level.FINE, "Initializing clean up executor for MIMEPULL: {0}", executorFactory.getClass().getName());
/*     */       }
/*  60 */       Executor executor = executorFactory.getExecutor();
/*  61 */       executor.execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */               while (true) {
/*     */                 try {
/*  67 */                   WeakDataFile weak = (WeakDataFile)WeakDataFile.refQueue.remove();
/*  68 */                   if (WeakDataFile.LOGGER.isLoggable(Level.FINE)) {
/*  69 */                     WeakDataFile.LOGGER.log(Level.FINE, "Cleaning file = {0} from reference queue.", weak.file);
/*     */                   }
/*  71 */                   weak.close();
/*  72 */                 } catch (InterruptedException interruptedException) {}
/*     */               } 
/*     */             }
/*     */           });
/*     */       
/*  77 */       hasCleanUpExecutor = true;
/*     */     } 
/*     */   }
/*     */   private final RandomAccessFile raf; private static boolean hasCleanUpExecutor = false;
/*     */   WeakDataFile(DataFile df, File file) {
/*  82 */     super(df, refQueue);
/*  83 */     refList.add(this);
/*  84 */     this.file = file;
/*     */     try {
/*  86 */       this.raf = new RandomAccessFile(file, "rw");
/*  87 */     } catch (IOException ioe) {
/*  88 */       throw new MIMEParsingException(ioe);
/*     */     } 
/*  90 */     if (!hasCleanUpExecutor) {
/*  91 */       drainRefQueueBounded();
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized void read(long pointer, byte[] buf, int offset, int length) {
/*     */     try {
/*  97 */       this.raf.seek(pointer);
/*  98 */       this.raf.readFully(buf, offset, length);
/*  99 */     } catch (IOException ioe) {
/* 100 */       throw new MIMEParsingException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   synchronized long writeTo(long pointer, byte[] data, int offset, int length) {
/*     */     try {
/* 106 */       this.raf.seek(pointer);
/* 107 */       this.raf.write(data, offset, length);
/* 108 */       return this.raf.getFilePointer();
/* 109 */     } catch (IOException ioe) {
/* 110 */       throw new MIMEParsingException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   void close() {
/* 115 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 116 */       LOGGER.log(Level.FINE, "Deleting file = {0}", this.file.getName());
/*     */     }
/* 118 */     refList.remove(this);
/*     */     try {
/* 120 */       this.raf.close();
/* 121 */       boolean deleted = this.file.delete();
/* 122 */       if (!deleted && 
/* 123 */         LOGGER.isLoggable(Level.INFO)) {
/* 124 */         LOGGER.log(Level.INFO, "File {0} was not deleted", this.file.getAbsolutePath());
/*     */       }
/*     */     }
/* 127 */     catch (IOException ioe) {
/* 128 */       throw new MIMEParsingException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   void renameTo(File f) {
/* 133 */     if (LOGGER.isLoggable(Level.FINE)) {
/* 134 */       LOGGER.log(Level.FINE, "Moving file={0} to={1}", new Object[] { this.file, f });
/*     */     }
/* 136 */     refList.remove(this);
/*     */     try {
/* 138 */       this.raf.close();
/* 139 */       boolean renamed = this.file.renameTo(f);
/* 140 */       if (!renamed && 
/* 141 */         LOGGER.isLoggable(Level.INFO)) {
/* 142 */         LOGGER.log(Level.INFO, "File {0} was not moved to {1}", new Object[] { this.file.getAbsolutePath(), f.getAbsolutePath() });
/*     */       }
/*     */     }
/* 145 */     catch (IOException ioe) {
/* 146 */       throw new MIMEParsingException(ioe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void drainRefQueueBounded() {
/*     */     WeakDataFile weak;
/* 153 */     while ((weak = (WeakDataFile)refQueue.poll()) != null) {
/* 154 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 155 */         LOGGER.log(Level.FINE, "Cleaning file = {0} from reference queue.", weak.file);
/*     */       }
/* 157 */       weak.close();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/xml/internal/org/jvnet/mimepull/WeakDataFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */