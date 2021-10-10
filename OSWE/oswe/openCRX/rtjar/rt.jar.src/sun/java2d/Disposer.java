/*     */ package sun.java2d;
/*     */ 
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
/*     */ import sun.misc.ThreadGroupUtils;
/*     */ import sun.security.action.GetPropertyAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Disposer
/*     */   implements Runnable
/*     */ {
/*  54 */   private static final ReferenceQueue queue = new ReferenceQueue();
/*  55 */   private static final Hashtable records = new Hashtable<>();
/*     */   
/*     */   private static Disposer disposerInstance;
/*     */   public static final int WEAK = 0;
/*     */   public static final int PHANTOM = 1;
/*  60 */   public static int refType = 1; private static ArrayList<DisposerRecord> deferredRecords;
/*     */   public static volatile boolean pollingQueue;
/*     */   
/*  63 */   static { AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run() {
/*  66 */             System.loadLibrary("awt");
/*  67 */             return null;
/*     */           }
/*     */         });
/*  70 */     initIDs();
/*  71 */     String str = AccessController.<String>doPrivileged(new GetPropertyAction("sun.java2d.reftype"));
/*     */     
/*  73 */     if (str != null) {
/*  74 */       if (str.equals("weak")) {
/*  75 */         refType = 0;
/*  76 */         System.err.println("Using WEAK refs");
/*     */       } else {
/*  78 */         refType = 1;
/*  79 */         System.err.println("Using PHANTOM refs");
/*     */       } 
/*     */     }
/*  82 */     disposerInstance = new Disposer();
/*  83 */     AccessController.doPrivileged(() -> {
/*     */           ThreadGroup threadGroup = ThreadGroupUtils.getRootThreadGroup();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           Thread thread = new Thread(threadGroup, disposerInstance, "Java2D Disposer");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           thread.setContextClassLoader(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           thread.setDaemon(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           thread.setPriority(10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           thread.start();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           return null;
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 170 */     deferredRecords = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     pollingQueue = false; }
/*     */ 
/*     */   
/*     */   public static interface PollDisposable {}
/*     */   
/*     */   public static void addRecord(Object paramObject, long paramLong1, long paramLong2) {
/*     */     disposerInstance.add(paramObject, new DefaultDisposerRecord(paramLong1, paramLong2));
/*     */   }
/*     */   
/*     */   public static void addRecord(Object paramObject, DisposerRecord paramDisposerRecord) {
/*     */     disposerInstance.add(paramObject, paramDisposerRecord);
/*     */   }
/*     */   
/* 203 */   public static void pollRemove() { if (pollingQueue) {
/*     */       return;
/*     */     }
/*     */     
/* 207 */     pollingQueue = true;
/* 208 */     byte b1 = 0;
/* 209 */     byte b2 = 0; try {
/*     */       Reference reference;
/* 211 */       while ((reference = queue.poll()) != null && b1 < '✐' && b2 < 100)
/*     */       {
/* 213 */         b1++;
/* 214 */         reference.clear();
/* 215 */         DisposerRecord disposerRecord = (DisposerRecord)records.remove(reference);
/* 216 */         if (disposerRecord instanceof PollDisposable) {
/* 217 */           disposerRecord.dispose();
/* 218 */           reference = null;
/* 219 */           disposerRecord = null; continue;
/*     */         } 
/* 221 */         if (disposerRecord == null) {
/*     */           continue;
/*     */         }
/* 224 */         b2++;
/* 225 */         if (deferredRecords == null) {
/* 226 */           deferredRecords = new ArrayList<>(5);
/*     */         }
/* 228 */         deferredRecords.add(disposerRecord);
/*     */       }
/*     */     
/* 231 */     } catch (Exception exception) {
/* 232 */       System.out.println("Exception while removing reference.");
/*     */     } finally {
/* 234 */       pollingQueue = false;
/*     */     }  }
/*     */ 
/*     */   
/*     */   synchronized void add(Object paramObject, DisposerRecord paramDisposerRecord) {
/*     */     WeakReference weakReference;
/*     */     if (paramObject instanceof DisposerTarget)
/*     */       paramObject = ((DisposerTarget)paramObject).getDisposerReferent(); 
/*     */     if (refType == 1) {
/*     */       PhantomReference phantomReference = new PhantomReference(paramObject, queue);
/*     */     } else {
/*     */       weakReference = new WeakReference(paramObject, queue);
/*     */     } 
/*     */     records.put(weakReference, paramDisposerRecord);
/*     */   }
/* 249 */   public static void addReference(Reference paramReference, DisposerRecord paramDisposerRecord) { records.put(paramReference, paramDisposerRecord); }
/*     */   public void run() { while (true) { try { while (true) { Reference reference = queue.remove(); reference.clear(); DisposerRecord disposerRecord = (DisposerRecord)records.remove(reference); disposerRecord.dispose(); reference = null; disposerRecord = null; clearDeferredRecords(); }  break; } catch (Exception exception) { System.out.println("Exception while removing reference."); }  }  }
/*     */   private static void clearDeferredRecords() { if (deferredRecords == null || deferredRecords.isEmpty())
/*     */       return;  for (byte b = 0; b < deferredRecords.size(); b++) { try { DisposerRecord disposerRecord = deferredRecords.get(b); disposerRecord.dispose(); } catch (Exception exception) { System.out.println("Exception while disposing deferred rec."); }  }
/* 253 */      deferredRecords.clear(); } public static void addObjectRecord(Object paramObject, DisposerRecord paramDisposerRecord) { records.put(new WeakReference(paramObject, queue), paramDisposerRecord); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReferenceQueue getQueue() {
/* 259 */     return queue;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/Disposer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */