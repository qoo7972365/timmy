/*     */ package sun.nio.ch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PollArrayWrapper
/*     */   extends AbstractPollArrayWrapper
/*     */ {
/*     */   int interruptFD;
/*     */   
/*     */   PollArrayWrapper(int paramInt) {
/*  50 */     paramInt = (paramInt + 1) * 8;
/*  51 */     this.pollArray = new AllocatedNativeObject(paramInt, false);
/*  52 */     this.pollArrayAddress = this.pollArray.address();
/*  53 */     this.totalChannels = 1;
/*     */   }
/*     */   
/*     */   void initInterrupt(int paramInt1, int paramInt2) {
/*  57 */     this.interruptFD = paramInt2;
/*  58 */     putDescriptor(0, paramInt1);
/*  59 */     putEventOps(0, Net.POLLIN);
/*  60 */     putReventOps(0, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   void release(int paramInt) {}
/*     */ 
/*     */   
/*     */   void free() {
/*  68 */     this.pollArray.free();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addEntry(SelChImpl paramSelChImpl) {
/*  75 */     putDescriptor(this.totalChannels, IOUtil.fdVal(paramSelChImpl.getFD()));
/*  76 */     putEventOps(this.totalChannels, 0);
/*  77 */     putReventOps(this.totalChannels, 0);
/*  78 */     this.totalChannels++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void replaceEntry(PollArrayWrapper paramPollArrayWrapper1, int paramInt1, PollArrayWrapper paramPollArrayWrapper2, int paramInt2) {
/*  89 */     paramPollArrayWrapper2.putDescriptor(paramInt2, paramPollArrayWrapper1.getDescriptor(paramInt1));
/*  90 */     paramPollArrayWrapper2.putEventOps(paramInt2, paramPollArrayWrapper1.getEventOps(paramInt1));
/*  91 */     paramPollArrayWrapper2.putReventOps(paramInt2, paramPollArrayWrapper1.getReventOps(paramInt1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void grow(int paramInt) {
/* 102 */     PollArrayWrapper pollArrayWrapper = new PollArrayWrapper(paramInt);
/*     */ 
/*     */     
/* 105 */     for (byte b = 0; b < this.totalChannels; b++) {
/* 106 */       replaceEntry(this, b, pollArrayWrapper, b);
/*     */     }
/*     */     
/* 109 */     this.pollArray.free();
/* 110 */     this.pollArray = pollArrayWrapper.pollArray;
/* 111 */     this.pollArrayAddress = this.pollArray.address();
/*     */   }
/*     */   
/*     */   int poll(int paramInt1, int paramInt2, long paramLong) {
/* 115 */     return poll0(this.pollArrayAddress + (paramInt2 * 8), paramInt1, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public void interrupt() {
/* 120 */     interrupt(this.interruptFD);
/*     */   }
/*     */   
/*     */   private native int poll0(long paramLong1, int paramInt, long paramLong2);
/*     */   
/*     */   private static native void interrupt(int paramInt);
/*     */   
/*     */   static {
/* 128 */     IOUtil.load();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/PollArrayWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */