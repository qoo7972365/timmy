/*    */ package sun.tracing.dtrace;
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
/*    */ class Activation
/*    */ {
/*    */   private SystemResource resource;
/*    */   private int referenceCount;
/*    */   
/*    */   Activation(String paramString, DTraceProvider[] paramArrayOfDTraceProvider) {
/* 38 */     SecurityManager securityManager = System.getSecurityManager();
/* 39 */     if (securityManager != null) {
/* 40 */       RuntimePermission runtimePermission = new RuntimePermission("com.sun.tracing.dtrace.createProvider");
/*    */       
/* 42 */       securityManager.checkPermission(runtimePermission);
/*    */     } 
/* 44 */     this.referenceCount = paramArrayOfDTraceProvider.length;
/* 45 */     for (DTraceProvider dTraceProvider : paramArrayOfDTraceProvider) {
/* 46 */       dTraceProvider.setActivation(this);
/*    */     }
/* 48 */     this
/* 49 */       .resource = new SystemResource(this, JVM.activate(paramString, paramArrayOfDTraceProvider));
/*    */   }
/*    */   
/*    */   void disposeProvider(DTraceProvider paramDTraceProvider) {
/* 53 */     if (--this.referenceCount == 0)
/* 54 */       this.resource.dispose(); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/tracing/dtrace/Activation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */