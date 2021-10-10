/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.awt.CheckboxMenuItem;
/*    */ import java.awt.event.ItemEvent;
/*    */ import java.awt.peer.CheckboxMenuItemPeer;
/*    */ import sun.awt.AWTAccessor;
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
/*    */ class XCheckboxMenuItemPeer
/*    */   extends XMenuItemPeer
/*    */   implements CheckboxMenuItemPeer
/*    */ {
/*    */   XCheckboxMenuItemPeer(CheckboxMenuItem paramCheckboxMenuItem) {
/* 42 */     super(paramCheckboxMenuItem);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setState(boolean paramBoolean) {
/* 53 */     repaintIfShowing();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean getTargetState() {
/* 62 */     return AWTAccessor.getCheckboxMenuItemAccessor()
/* 63 */       .getState((CheckboxMenuItem)getTarget());
/*    */   }
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
/*    */   void action(final long when) {
/* 76 */     XToolkit.executeOnEventHandlerThread(getTarget(), new Runnable() {
/*    */           public void run() {
/* 78 */             XCheckboxMenuItemPeer.this.doToggleState(when);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void doToggleState(long paramLong) {
/* 90 */     CheckboxMenuItem checkboxMenuItem = (CheckboxMenuItem)getTarget();
/* 91 */     boolean bool = !getTargetState() ? true : false;
/* 92 */     checkboxMenuItem.setState(bool);
/*    */ 
/*    */ 
/*    */     
/* 96 */     ItemEvent itemEvent = new ItemEvent(checkboxMenuItem, 701, getTargetLabel(), getTargetState() ? 1 : 2);
/* 97 */     XWindow.postEventStatic(itemEvent);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XCheckboxMenuItemPeer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */