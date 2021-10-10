/*    */ package com.sun.java.swing.plaf.motif;
/*    */ 
/*    */ import javax.swing.JComponent;
/*    */ import javax.swing.plaf.ComponentUI;
/*    */ import javax.swing.plaf.basic.BasicLabelUI;
/*    */ import sun.awt.AppContext;
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
/*    */ public class MotifLabelUI
/*    */   extends BasicLabelUI
/*    */ {
/* 49 */   private static final Object MOTIF_LABEL_UI_KEY = new Object();
/*    */   
/*    */   public static ComponentUI createUI(JComponent paramJComponent) {
/* 52 */     AppContext appContext = AppContext.getAppContext();
/*    */     
/* 54 */     MotifLabelUI motifLabelUI = (MotifLabelUI)appContext.get(MOTIF_LABEL_UI_KEY);
/* 55 */     if (motifLabelUI == null) {
/* 56 */       motifLabelUI = new MotifLabelUI();
/* 57 */       appContext.put(MOTIF_LABEL_UI_KEY, motifLabelUI);
/*    */     } 
/* 59 */     return motifLabelUI;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/com/sun/java/swing/plaf/motif/MotifLabelUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */