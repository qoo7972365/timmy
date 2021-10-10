/*    */ package sun.awt;
/*    */ 
/*    */ import java.awt.Window;
/*    */ import java.awt.event.WindowEvent;
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
/*    */ public class TimedWindowEvent
/*    */   extends WindowEvent
/*    */ {
/*    */   private long time;
/*    */   
/*    */   public long getWhen() {
/* 36 */     return this.time;
/*    */   }
/*    */   
/*    */   public TimedWindowEvent(Window paramWindow1, int paramInt, Window paramWindow2, long paramLong) {
/* 40 */     super(paramWindow1, paramInt, paramWindow2);
/* 41 */     this.time = paramLong;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TimedWindowEvent(Window paramWindow1, int paramInt1, Window paramWindow2, int paramInt2, int paramInt3, long paramLong) {
/* 47 */     super(paramWindow1, paramInt1, paramWindow2, paramInt2, paramInt3);
/* 48 */     this.time = paramLong;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/TimedWindowEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */