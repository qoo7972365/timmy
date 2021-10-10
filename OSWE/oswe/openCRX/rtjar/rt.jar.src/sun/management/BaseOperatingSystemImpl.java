/*    */ package sun.management;
/*    */ 
/*    */ import java.lang.management.OperatingSystemMXBean;
/*    */ import javax.management.ObjectName;
/*    */ import sun.misc.Unsafe;
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
/*    */ public class BaseOperatingSystemImpl
/*    */   implements OperatingSystemMXBean
/*    */ {
/*    */   private final VMManagement jvm;
/*    */   
/*    */   protected BaseOperatingSystemImpl(VMManagement paramVMManagement) {
/* 68 */     this.loadavg = new double[1]; this.jvm = paramVMManagement;
/*    */   }
/* 70 */   public String getName() { return this.jvm.getOsName(); } public String getArch() { return this.jvm.getOsArch(); } public String getVersion() { return this.jvm.getOsVersion(); } public double getSystemLoadAverage() { if (unsafe.getLoadAverage(this.loadavg, 1) == 1) {
/* 71 */       return this.loadavg[0];
/*    */     }
/* 73 */     return -1.0D; }
/*    */    public int getAvailableProcessors() {
/*    */     return this.jvm.getAvailableProcessors();
/*    */   } private static final Unsafe unsafe = Unsafe.getUnsafe(); private double[] loadavg; public ObjectName getObjectName() {
/* 77 */     return Util.newObjectName("java.lang:type=OperatingSystem");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/BaseOperatingSystemImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */