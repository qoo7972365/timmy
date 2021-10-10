/*    */ package com.sun.java.swing.plaf.windows;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicDesktopPaneUI;
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
/*    */ public class WindowsDesktopPaneUI
/*    */   extends BasicDesktopPaneUI
/*    */ {
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 48 */     return new WindowsDesktopPaneUI();
/*    */   }
/*    */   
/*    */   protected void installDesktopManager() {
/* 52 */     this.desktopManager = this.desktop.getDesktopManager();
/* 53 */     if (this.desktopManager == null) {
/* 54 */       this.desktopManager = new WindowsDesktopManager();
/* 55 */       this.desktop.setDesktopManager(this.desktopManager);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void installDefaults() {
/* 60 */     super.installDefaults();
/*    */   }
/*    */   
/*    */   protected void installKeyboardActions() {
/* 64 */     super.installKeyboardActions();
/*    */ 
/*    */     
/* 67 */     if (!this.desktop.requestDefaultFocus())
/* 68 */       this.desktop.requestFocus(); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsDesktopPaneUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */