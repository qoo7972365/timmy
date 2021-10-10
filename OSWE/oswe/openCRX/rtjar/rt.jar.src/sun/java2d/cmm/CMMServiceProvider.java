/*    */ package sun.java2d.cmm;
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
/*    */ public abstract class CMMServiceProvider
/*    */ {
/*    */   public final PCMM getColorManagementModule() {
/* 30 */     if (CMSManager.canCreateModule()) {
/* 31 */       return getModule();
/*    */     }
/* 33 */     return null;
/*    */   }
/*    */   
/*    */   protected abstract PCMM getModule();
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/CMMServiceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */