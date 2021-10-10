/*    */ package sun.awt;
/*    */ 
/*    */ import java.awt.event.InvocationEvent;
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
/*    */ public class PeerEvent
/*    */   extends InvocationEvent
/*    */ {
/*    */   public static final long PRIORITY_EVENT = 1L;
/*    */   public static final long ULTIMATE_PRIORITY_EVENT = 2L;
/*    */   public static final long LOW_PRIORITY_EVENT = 4L;
/*    */   private long flags;
/*    */   
/*    */   public PeerEvent(Object paramObject, Runnable paramRunnable, long paramLong) {
/* 40 */     this(paramObject, paramRunnable, null, false, paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   public PeerEvent(Object paramObject1, Runnable paramRunnable, Object paramObject2, boolean paramBoolean, long paramLong) {
/* 45 */     super(paramObject1, paramRunnable, paramObject2, paramBoolean);
/* 46 */     this.flags = paramLong;
/*    */   }
/*    */   
/*    */   public long getFlags() {
/* 50 */     return this.flags;
/*    */   }
/*    */   
/*    */   public PeerEvent coalesceEvents(PeerEvent paramPeerEvent) {
/* 54 */     return null;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/PeerEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */