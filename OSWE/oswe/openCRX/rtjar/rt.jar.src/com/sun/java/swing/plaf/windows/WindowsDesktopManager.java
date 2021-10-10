/*    */ package com.sun.java.swing.plaf.windows;
/*    */ 
/*    */ import java.beans.PropertyVetoException;
/*    */ import java.io.Serializable;
/*    */ import java.lang.ref.WeakReference;
/*    */ import javax.swing.DefaultDesktopManager;
/*    */ import javax.swing.JInternalFrame;
/*    */ import javax.swing.plaf.UIResource;
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
/*    */ public class WindowsDesktopManager
/*    */   extends DefaultDesktopManager
/*    */   implements Serializable, UIResource
/*    */ {
/*    */   private WeakReference<JInternalFrame> currentFrameRef;
/*    */   
/*    */   public void activateFrame(JInternalFrame paramJInternalFrame) {
/* 64 */     JInternalFrame jInternalFrame = (this.currentFrameRef != null) ? this.currentFrameRef.get() : null;
/*    */     try {
/* 66 */       super.activateFrame(paramJInternalFrame);
/* 67 */       if (jInternalFrame != null && paramJInternalFrame != jInternalFrame) {
/*    */ 
/*    */         
/* 70 */         if (jInternalFrame.isMaximum() && paramJInternalFrame
/* 71 */           .getClientProperty("JInternalFrame.frameType") != "optionDialog")
/*    */         {
/*    */ 
/*    */ 
/*    */           
/* 76 */           if (!jInternalFrame.isIcon()) {
/* 77 */             jInternalFrame.setMaximum(false);
/* 78 */             if (paramJInternalFrame.isMaximizable()) {
/* 79 */               if (!paramJInternalFrame.isMaximum()) {
/* 80 */                 paramJInternalFrame.setMaximum(true);
/* 81 */               } else if (paramJInternalFrame.isMaximum() && paramJInternalFrame.isIcon()) {
/* 82 */                 paramJInternalFrame.setIcon(false);
/*    */               } else {
/* 84 */                 paramJInternalFrame.setMaximum(false);
/*    */               } 
/*    */             }
/*    */           } 
/*    */         }
/* 89 */         if (jInternalFrame.isSelected()) {
/* 90 */           jInternalFrame.setSelected(false);
/*    */         }
/*    */       } 
/*    */       
/* 94 */       if (!paramJInternalFrame.isSelected()) {
/* 95 */         paramJInternalFrame.setSelected(true);
/*    */       }
/* 97 */     } catch (PropertyVetoException propertyVetoException) {}
/* 98 */     if (paramJInternalFrame != jInternalFrame)
/* 99 */       this.currentFrameRef = new WeakReference<>(paramJInternalFrame); 
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/windows/WindowsDesktopManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */