/*     */ package sun.misc;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.LongBuffer;
/*     */ import java.security.AccessController;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PerfCounter
/*     */ {
/*  52 */   private static final Perf perf = AccessController.<Perf>doPrivileged(new Perf.GetPerfAction());
/*     */   
/*     */   private static final int V_Constant = 1;
/*     */   
/*     */   private static final int V_Monotonic = 2;
/*     */   
/*     */   private static final int V_Variable = 3;
/*     */   private static final int U_None = 1;
/*     */   private final String name;
/*     */   private final LongBuffer lb;
/*     */   
/*     */   private PerfCounter(String paramString, int paramInt) {
/*  64 */     this.name = paramString;
/*  65 */     ByteBuffer byteBuffer = perf.createLong(paramString, paramInt, 1, 0L);
/*  66 */     byteBuffer.order(ByteOrder.nativeOrder());
/*  67 */     this.lb = byteBuffer.asLongBuffer();
/*     */   }
/*     */   
/*     */   static PerfCounter newPerfCounter(String paramString) {
/*  71 */     return new PerfCounter(paramString, 3);
/*     */   }
/*     */   
/*     */   static PerfCounter newConstantPerfCounter(String paramString) {
/*  75 */     return new PerfCounter(paramString, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long get() {
/*  83 */     return this.lb.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void set(long paramLong) {
/*  90 */     this.lb.put(0, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void add(long paramLong) {
/*  97 */     long l = get() + paramLong;
/*  98 */     this.lb.put(0, l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void increment() {
/* 105 */     add(1L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTime(long paramLong) {
/* 112 */     add(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addElapsedTimeFrom(long paramLong) {
/* 119 */     add(System.nanoTime() - paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return this.name + " = " + get();
/*     */   }
/*     */   
/*     */   static class CoreCounters {
/* 128 */     static final PerfCounter pdt = PerfCounter.newPerfCounter("sun.classloader.parentDelegationTime");
/* 129 */     static final PerfCounter lc = PerfCounter.newPerfCounter("sun.classloader.findClasses");
/* 130 */     static final PerfCounter lct = PerfCounter.newPerfCounter("sun.classloader.findClassTime");
/* 131 */     static final PerfCounter rcbt = PerfCounter.newPerfCounter("sun.urlClassLoader.readClassBytesTime");
/* 132 */     static final PerfCounter zfc = PerfCounter.newPerfCounter("sun.zip.zipFiles");
/* 133 */     static final PerfCounter zfot = PerfCounter.newPerfCounter("sun.zip.zipFile.openTime");
/*     */   }
/*     */   
/*     */   static class WindowsClientCounters {
/* 137 */     static final PerfCounter d3dAvailable = PerfCounter.newConstantPerfCounter("sun.java2d.d3d.available");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PerfCounter getFindClasses() {
/* 144 */     return CoreCounters.lc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PerfCounter getFindClassTime() {
/* 152 */     return CoreCounters.lct;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PerfCounter getReadClassBytesTime() {
/* 159 */     return CoreCounters.rcbt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PerfCounter getParentDelegationTime() {
/* 167 */     return CoreCounters.pdt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PerfCounter getZipFileCount() {
/* 174 */     return CoreCounters.zfc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PerfCounter getZipFileOpenTime() {
/* 182 */     return CoreCounters.zfot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PerfCounter getD3DAvailable() {
/* 189 */     return WindowsClientCounters.d3dAvailable;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/misc/PerfCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */