/*    */ package sun.management;
/*    */ 
/*    */ import java.util.List;
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
/*    */ class HotspotClassLoading
/*    */   implements HotspotClassLoadingMBean
/*    */ {
/*    */   private VMManagement jvm;
/*    */   private static final String JAVA_CLS = "java.cls.";
/*    */   private static final String COM_SUN_CLS = "com.sun.cls.";
/*    */   private static final String SUN_CLS = "sun.cls.";
/*    */   private static final String CLS_COUNTER_NAME_PATTERN = "java.cls.|com.sun.cls.|sun.cls.";
/*    */   
/*    */   HotspotClassLoading(VMManagement paramVMManagement) {
/* 45 */     this.jvm = paramVMManagement;
/*    */   }
/*    */   
/*    */   public long getLoadedClassSize() {
/* 49 */     return this.jvm.getLoadedClassSize();
/*    */   }
/*    */   
/*    */   public long getUnloadedClassSize() {
/* 53 */     return this.jvm.getUnloadedClassSize();
/*    */   }
/*    */   
/*    */   public long getClassLoadingTime() {
/* 57 */     return this.jvm.getClassLoadingTime();
/*    */   }
/*    */   
/*    */   public long getMethodDataSize() {
/* 61 */     return this.jvm.getMethodDataSize();
/*    */   }
/*    */   
/*    */   public long getInitializedClassCount() {
/* 65 */     return this.jvm.getInitializedClassCount();
/*    */   }
/*    */   
/*    */   public long getClassInitializationTime() {
/* 69 */     return this.jvm.getClassInitializationTime();
/*    */   }
/*    */   
/*    */   public long getClassVerificationTime() {
/* 73 */     return this.jvm.getClassVerificationTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<Counter> getInternalClassLoadingCounters() {
/* 84 */     return this.jvm.getInternalCounters("java.cls.|com.sun.cls.|sun.cls.");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/HotspotClassLoading.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */