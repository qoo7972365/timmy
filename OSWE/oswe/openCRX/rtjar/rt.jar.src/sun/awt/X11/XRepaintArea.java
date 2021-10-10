/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import sun.awt.RepaintArea;
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
/*    */ final class XRepaintArea
/*    */   extends RepaintArea
/*    */ {
/*    */   protected void updateComponent(Component paramComponent, Graphics paramGraphics) {
/* 48 */     if (paramComponent != null)
/*    */     {
/*    */       
/* 51 */       super.updateComponent(paramComponent, paramGraphics);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void paintComponent(Component paramComponent, Graphics paramGraphics) {
/* 59 */     if (paramComponent != null) {
/* 60 */       XComponentPeer xComponentPeer = (XComponentPeer)paramComponent.getPeer();
/* 61 */       if (xComponentPeer != null) {
/* 62 */         xComponentPeer.paintPeer(paramGraphics);
/*    */       }
/* 64 */       super.paintComponent(paramComponent, paramGraphics);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XRepaintArea.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */