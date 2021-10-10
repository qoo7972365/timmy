/*    */ package sun.awt.event;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.event.PaintEvent;
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
/*    */ public class IgnorePaintEvent
/*    */   extends PaintEvent
/*    */ {
/*    */   public IgnorePaintEvent(Component paramComponent, int paramInt, Rectangle paramRectangle) {
/* 40 */     super(paramComponent, paramInt, paramRectangle);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/event/IgnorePaintEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */