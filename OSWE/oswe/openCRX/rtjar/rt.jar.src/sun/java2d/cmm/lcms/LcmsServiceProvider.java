/*    */ package sun.java2d.cmm.lcms;
/*    */ 
/*    */ import sun.java2d.cmm.CMMServiceProvider;
/*    */ import sun.java2d.cmm.PCMM;
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
/*    */ public final class LcmsServiceProvider
/*    */   extends CMMServiceProvider
/*    */ {
/*    */   protected PCMM getModule() {
/* 34 */     return LCMS.getModule();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/cmm/lcms/LcmsServiceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */