/*    */ package sun.java2d.xr;
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
/*    */ public class XcbRequestCounter
/*    */ {
/*    */   private static final long MAX_UINT = 4294967295L;
/*    */   long value;
/*    */   
/*    */   public XcbRequestCounter(long paramLong) {
/* 40 */     this.value = paramLong;
/*    */   }
/*    */   
/*    */   public void setValue(long paramLong) {
/* 44 */     this.value = paramLong;
/*    */   }
/*    */   
/*    */   public long getValue() {
/* 48 */     return this.value;
/*    */   }
/*    */   
/*    */   public void add(long paramLong) {
/* 52 */     this.value += paramLong;
/*    */ 
/*    */     
/* 55 */     if (this.value > 4294967295L)
/* 56 */       this.value = 0L; 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/xr/XcbRequestCounter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */