/*    */ package sun.management;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import sun.management.counter.Counter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class HotspotThread
/*    */   implements HotspotThreadMBean
/*    */ {
/*    */   private VMManagement jvm;
/*    */   private static final String JAVA_THREADS = "java.threads.";
/*    */   private static final String COM_SUN_THREADS = "com.sun.threads.";
/*    */   private static final String SUN_THREADS = "sun.threads.";
/*    */   private static final String THREADS_COUNTER_NAME_PATTERN = "java.threads.|com.sun.threads.|sun.threads.";
/*    */   
/*    */   HotspotThread(VMManagement paramVMManagement) {
/* 48 */     this.jvm = paramVMManagement;
/*    */   }
/*    */   
/*    */   public native int getInternalThreadCount();
/*    */   
/*    */   public Map<String, Long> getInternalThreadCpuTimes() {
/* 54 */     int i = getInternalThreadCount();
/* 55 */     if (i == 0) {
/* 56 */       return Collections.emptyMap();
/*    */     }
/* 58 */     String[] arrayOfString = new String[i];
/* 59 */     long[] arrayOfLong = new long[i];
/* 60 */     int j = getInternalThreadTimes0(arrayOfString, arrayOfLong);
/* 61 */     HashMap<Object, Object> hashMap = new HashMap<>(j);
/* 62 */     for (byte b = 0; b < j; b++) {
/* 63 */       hashMap.put(arrayOfString[b], new Long(arrayOfLong[b]));
/*    */     }
/* 65 */     return (Map)hashMap;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public native int getInternalThreadTimes0(String[] paramArrayOfString, long[] paramArrayOflong);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Counter> getInternalThreadingCounters() {
/* 77 */     return this.jvm.getInternalCounters("java.threads.|com.sun.threads.|sun.threads.");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/HotspotThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */