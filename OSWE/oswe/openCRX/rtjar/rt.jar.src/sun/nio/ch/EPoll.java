/*     */ package sun.nio.ch;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import sun.misc.Unsafe;
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
/*     */ class EPoll
/*     */ {
/*  38 */   private static final Unsafe unsafe = Unsafe.getUnsafe();
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
/*  53 */   private static final int SIZEOF_EPOLLEVENT = eventSize();
/*  54 */   private static final int OFFSETOF_EVENTS = eventsOffset();
/*  55 */   private static final int OFFSETOF_FD = dataOffset();
/*     */ 
/*     */   
/*     */   static final int EPOLL_CTL_ADD = 1;
/*     */ 
/*     */   
/*     */   static final int EPOLL_CTL_DEL = 2;
/*     */   
/*     */   static final int EPOLL_CTL_MOD = 3;
/*     */   
/*     */   static final int EPOLLONESHOT = 1073741824;
/*     */ 
/*     */   
/*     */   static long allocatePollArray(int paramInt) {
/*  69 */     return unsafe.allocateMemory((paramInt * SIZEOF_EPOLLEVENT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void freePollArray(long paramLong) {
/*  76 */     unsafe.freeMemory(paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static long getEvent(long paramLong, int paramInt) {
/*  83 */     return paramLong + (SIZEOF_EPOLLEVENT * paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getDescriptor(long paramLong) {
/*  90 */     return unsafe.getInt(paramLong + OFFSETOF_FD);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getEvents(long paramLong) {
/*  97 */     return unsafe.getInt(paramLong + OFFSETOF_EVENTS);
/*     */   }
/*     */ 
/*     */   
/*     */   private static native int eventSize();
/*     */ 
/*     */   
/*     */   private static native int eventsOffset();
/*     */ 
/*     */   
/*     */   private static native int dataOffset();
/*     */   
/*     */   static native int epollCreate() throws IOException;
/*     */   
/*     */   static native int epollCtl(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   static native int epollWait(int paramInt1, long paramLong, int paramInt2) throws IOException;
/*     */   
/*     */   static {
/* 116 */     IOUtil.load();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/nio/ch/EPoll.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */