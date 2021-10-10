/*    */ package sun.management;
/*    */ 
/*    */ import com.sun.management.UnixOperatingSystemMXBean;
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
/*    */ class OperatingSystemImpl
/*    */   extends BaseOperatingSystemImpl
/*    */   implements UnixOperatingSystemMXBean
/*    */ {
/*    */   OperatingSystemImpl(VMManagement paramVMManagement) {
/* 39 */     super(paramVMManagement);
/*    */   }
/*    */ 
/*    */   
/*    */   public native long getCommittedVirtualMemorySize();
/*    */   
/*    */   public native long getTotalSwapSpaceSize();
/*    */   
/*    */   public native long getFreeSwapSpaceSize();
/*    */   
/*    */   public native long getProcessCpuTime();
/*    */   
/*    */   public native long getFreePhysicalMemorySize();
/*    */   
/*    */   static {
/* 54 */     initialize();
/*    */   }
/*    */   
/*    */   public native long getTotalPhysicalMemorySize();
/*    */   
/*    */   public native long getOpenFileDescriptorCount();
/*    */   
/*    */   public native long getMaxFileDescriptorCount();
/*    */   
/*    */   public native double getSystemCpuLoad();
/*    */   
/*    */   public native double getProcessCpuLoad();
/*    */   
/*    */   private static native void initialize();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/OperatingSystemImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */