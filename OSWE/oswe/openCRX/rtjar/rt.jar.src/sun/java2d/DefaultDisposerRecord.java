/*    */ package sun.java2d;
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
/*    */ public class DefaultDisposerRecord
/*    */   implements DisposerRecord
/*    */ {
/*    */   private long dataPointer;
/*    */   private long disposerMethodPointer;
/*    */   
/*    */   public DefaultDisposerRecord(long paramLong1, long paramLong2) {
/* 37 */     this.disposerMethodPointer = paramLong1;
/* 38 */     this.dataPointer = paramLong2;
/*    */   }
/*    */   
/*    */   public void dispose() {
/* 42 */     invokeNativeDispose(this.disposerMethodPointer, this.dataPointer);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getDataPointer() {
/* 47 */     return this.dataPointer;
/*    */   }
/*    */   
/*    */   public long getDisposerMethodPointer() {
/* 51 */     return this.disposerMethodPointer;
/*    */   }
/*    */   
/*    */   public static native void invokeNativeDispose(long paramLong1, long paramLong2);
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/java2d/DefaultDisposerRecord.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */