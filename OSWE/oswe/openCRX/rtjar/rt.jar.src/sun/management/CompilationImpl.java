/*    */ package sun.management;
/*    */ 
/*    */ import java.lang.management.CompilationMXBean;
/*    */ import javax.management.ObjectName;
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
/*    */ class CompilationImpl
/*    */   implements CompilationMXBean
/*    */ {
/*    */   private final VMManagement jvm;
/*    */   private final String name;
/*    */   
/*    */   CompilationImpl(VMManagement paramVMManagement) {
/* 48 */     this.jvm = paramVMManagement;
/* 49 */     this.name = this.jvm.getCompilerName();
/* 50 */     if (this.name == null) {
/* 51 */       throw new AssertionError("Null compiler name");
/*    */     }
/*    */   }
/*    */   
/*    */   public String getName() {
/* 56 */     return this.name;
/*    */   }
/*    */   
/*    */   public boolean isCompilationTimeMonitoringSupported() {
/* 60 */     return this.jvm.isCompilationTimeMonitoringSupported();
/*    */   }
/*    */   
/*    */   public long getTotalCompilationTime() {
/* 64 */     if (!isCompilationTimeMonitoringSupported()) {
/* 65 */       throw new UnsupportedOperationException("Compilation time monitoring is not supported.");
/*    */     }
/*    */ 
/*    */     
/* 69 */     return this.jvm.getTotalCompileTime();
/*    */   }
/*    */   
/*    */   public ObjectName getObjectName() {
/* 73 */     return Util.newObjectName("java.lang:type=Compilation");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/management/CompilationImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */