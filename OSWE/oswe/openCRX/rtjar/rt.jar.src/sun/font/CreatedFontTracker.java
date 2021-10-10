/*     */ package sun.font;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import sun.awt.AppContext;
/*     */ import sun.misc.ThreadGroupUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreatedFontTracker
/*     */ {
/*     */   public static final int MAX_FILE_SIZE = 33554432;
/*     */   public static final int MAX_TOTAL_BYTES = 335544320;
/*     */   static CreatedFontTracker tracker;
/*     */   int numBytes;
/*     */   
/*     */   public static synchronized CreatedFontTracker getTracker() {
/*  49 */     if (tracker == null) {
/*  50 */       tracker = new CreatedFontTracker();
/*     */     }
/*  52 */     return tracker;
/*     */   }
/*     */   
/*     */   private CreatedFontTracker() {
/*  56 */     this.numBytes = 0;
/*     */   }
/*     */   
/*     */   public synchronized int getNumBytes() {
/*  60 */     return this.numBytes;
/*     */   }
/*     */   
/*     */   public synchronized void addBytes(int paramInt) {
/*  64 */     this.numBytes += paramInt;
/*     */   }
/*     */   
/*     */   public synchronized void subBytes(int paramInt) {
/*  68 */     this.numBytes -= paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static synchronized Semaphore getCS() {
/*  75 */     AppContext appContext = AppContext.getAppContext();
/*  76 */     Semaphore semaphore = (Semaphore)appContext.get(CreatedFontTracker.class);
/*  77 */     if (semaphore == null) {
/*     */ 
/*     */       
/*  80 */       semaphore = new Semaphore(5, true);
/*  81 */       appContext.put(CreatedFontTracker.class, semaphore);
/*     */     } 
/*  83 */     return semaphore;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean acquirePermit() throws InterruptedException {
/*  88 */     return getCS().tryAcquire(120L, TimeUnit.SECONDS);
/*     */   }
/*     */   
/*     */   public void releasePermit() {
/*  92 */     getCS().release();
/*     */   }
/*     */   
/*     */   public void add(File paramFile) {
/*  96 */     TempFileDeletionHook.add(paramFile);
/*     */   }
/*     */   
/*     */   public void set(File paramFile, OutputStream paramOutputStream) {
/* 100 */     TempFileDeletionHook.set(paramFile, paramOutputStream);
/*     */   }
/*     */   
/*     */   public void remove(File paramFile) {
/* 104 */     TempFileDeletionHook.remove(paramFile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TempFileDeletionHook
/*     */   {
/* 112 */     private static HashMap<File, OutputStream> files = new HashMap<>();
/*     */     
/* 114 */     private static Thread t = null;
/*     */     static void init() {
/* 116 */       if (t == null)
/*     */       {
/* 118 */         AccessController.doPrivileged(() -> {
/*     */               ThreadGroup threadGroup = ThreadGroupUtils.getRootThreadGroup();
/*     */               t = new Thread(threadGroup, TempFileDeletionHook::runHooks);
/*     */               t.setContextClassLoader(null);
/*     */               Runtime.getRuntime().addShutdownHook(t);
/*     */               return null;
/*     */             });
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static synchronized void add(File param1File) {
/* 136 */       init();
/* 137 */       files.put(param1File, null);
/*     */     }
/*     */     
/*     */     static synchronized void set(File param1File, OutputStream param1OutputStream) {
/* 141 */       files.put(param1File, param1OutputStream);
/*     */     }
/*     */     
/*     */     static synchronized void remove(File param1File) {
/* 145 */       files.remove(param1File);
/*     */     }
/*     */     
/*     */     static synchronized void runHooks() {
/* 149 */       if (files.isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 153 */       for (Map.Entry<File, OutputStream> entry : files.entrySet()) {
/*     */         
/*     */         try {
/* 156 */           if (entry.getValue() != null) {
/* 157 */             ((OutputStream)entry.getValue()).close();
/*     */           }
/* 159 */         } catch (Exception exception) {}
/* 160 */         ((File)entry.getKey()).delete();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/font/CreatedFontTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */