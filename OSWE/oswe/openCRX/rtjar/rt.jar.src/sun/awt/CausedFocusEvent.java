/*    */ package sun.awt;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.event.FocusEvent;
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
/*    */ public class CausedFocusEvent
/*    */   extends FocusEvent
/*    */ {
/*    */   private final Cause cause;
/*    */   
/*    */   public enum Cause
/*    */   {
/* 41 */     UNKNOWN,
/* 42 */     MOUSE_EVENT,
/* 43 */     TRAVERSAL,
/* 44 */     TRAVERSAL_UP,
/* 45 */     TRAVERSAL_DOWN,
/* 46 */     TRAVERSAL_FORWARD,
/* 47 */     TRAVERSAL_BACKWARD,
/* 48 */     MANUAL_REQUEST,
/* 49 */     AUTOMATIC_TRAVERSE,
/* 50 */     ROLLBACK,
/* 51 */     NATIVE_SYSTEM,
/* 52 */     ACTIVATION,
/* 53 */     CLEAR_GLOBAL_FOCUS_OWNER,
/* 54 */     RETARGETED;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Cause getCause() {
/* 60 */     return this.cause;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 64 */     return "java.awt.FocusEvent[" + paramString() + ",cause=" + this.cause + "] on " + getSource();
/*    */   }
/*    */ 
/*    */   
/*    */   public CausedFocusEvent(Component paramComponent1, int paramInt, boolean paramBoolean, Component paramComponent2, Cause paramCause) {
/* 69 */     super(paramComponent1, paramInt, paramBoolean, paramComponent2);
/* 70 */     if (paramCause == null) {
/* 71 */       paramCause = Cause.UNKNOWN;
/*    */     }
/* 73 */     this.cause = paramCause;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static FocusEvent retarget(FocusEvent paramFocusEvent, Component paramComponent) {
/* 84 */     if (paramFocusEvent == null) return null;
/*    */     
/* 86 */     return new CausedFocusEvent(paramComponent, paramFocusEvent.getID(), paramFocusEvent.isTemporary(), paramFocusEvent.getOppositeComponent(), (paramFocusEvent instanceof CausedFocusEvent) ? ((CausedFocusEvent)paramFocusEvent)
/* 87 */         .getCause() : Cause.RETARGETED);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/CausedFocusEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */