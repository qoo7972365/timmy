/*    */ package sun.nio.ch;
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
/*    */ class AllocatedNativeObject
/*    */   extends NativeObject
/*    */ {
/*    */   AllocatedNativeObject(int paramInt, boolean paramBoolean) {
/* 53 */     super(paramInt, paramBoolean);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   synchronized void free() {
/* 60 */     if (this.allocationAddress != 0L) {
/* 61 */       unsafe.freeMemory(this.allocationAddress);
/* 62 */       this.allocationAddress = 0L;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/AllocatedNativeObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */