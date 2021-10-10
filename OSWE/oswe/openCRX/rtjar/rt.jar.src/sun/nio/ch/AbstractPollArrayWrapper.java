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
/*    */ public abstract class AbstractPollArrayWrapper
/*    */ {
/*    */   static final short SIZE_POLLFD = 8;
/*    */   static final short FD_OFFSET = 0;
/*    */   static final short EVENT_OFFSET = 4;
/*    */   static final short REVENT_OFFSET = 6;
/*    */   protected AllocatedNativeObject pollArray;
/* 50 */   protected int totalChannels = 0;
/*    */ 
/*    */   
/*    */   protected long pollArrayAddress;
/*    */ 
/*    */   
/*    */   int getEventOps(int paramInt) {
/* 57 */     int i = 8 * paramInt + 4;
/* 58 */     return this.pollArray.getShort(i);
/*    */   }
/*    */   
/*    */   int getReventOps(int paramInt) {
/* 62 */     int i = 8 * paramInt + 6;
/* 63 */     return this.pollArray.getShort(i);
/*    */   }
/*    */   
/*    */   int getDescriptor(int paramInt) {
/* 67 */     int i = 8 * paramInt + 0;
/* 68 */     return this.pollArray.getInt(i);
/*    */   }
/*    */   
/*    */   void putEventOps(int paramInt1, int paramInt2) {
/* 72 */     int i = 8 * paramInt1 + 4;
/* 73 */     this.pollArray.putShort(i, (short)paramInt2);
/*    */   }
/*    */   
/*    */   void putReventOps(int paramInt1, int paramInt2) {
/* 77 */     int i = 8 * paramInt1 + 6;
/* 78 */     this.pollArray.putShort(i, (short)paramInt2);
/*    */   }
/*    */   
/*    */   void putDescriptor(int paramInt1, int paramInt2) {
/* 82 */     int i = 8 * paramInt1 + 0;
/* 83 */     this.pollArray.putInt(i, paramInt2);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/AbstractPollArrayWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */