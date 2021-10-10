/*    */ package sun.awt;
/*    */ 
/*    */ import java.awt.AWTEvent;
/*    */ import java.awt.ActiveEvent;
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
/*    */ public class ModalityEvent
/*    */   extends AWTEvent
/*    */   implements ActiveEvent
/*    */ {
/*    */   public static final int MODALITY_PUSHED = 1300;
/*    */   public static final int MODALITY_POPPED = 1301;
/*    */   private ModalityListener listener;
/*    */   
/*    */   public ModalityEvent(Object paramObject, ModalityListener paramModalityListener, int paramInt) {
/* 42 */     super(paramObject, paramInt);
/* 43 */     this.listener = paramModalityListener;
/*    */   }
/*    */   
/*    */   public void dispatch() {
/* 47 */     switch (getID()) {
/*    */       case 1300:
/* 49 */         this.listener.modalityPushed(this);
/*    */         return;
/*    */       
/*    */       case 1301:
/* 53 */         this.listener.modalityPopped(this);
/*    */         return;
/*    */     } 
/*    */     
/* 57 */     throw new Error("Invalid event id.");
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/ModalityEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */