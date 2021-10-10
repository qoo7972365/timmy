/*    */ package sun.awt;
/*    */ 
/*    */ import java.awt.AWTEvent;
/*    */ import java.awt.Component;
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
/*    */ public class UngrabEvent
/*    */   extends AWTEvent
/*    */ {
/*    */   private static final int UNGRAB_EVENT_ID = 1998;
/*    */   
/*    */   public UngrabEvent(Component paramComponent) {
/* 48 */     super(paramComponent, 1998);
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     return "sun.awt.UngrabEvent[" + getSource() + "]";
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/UngrabEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */