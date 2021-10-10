/*    */ package javax.swing.plaf.synth;
/*    */ 
/*    */ import java.awt.Insets;
/*    */ import java.awt.Rectangle;
/*    */ import javax.swing.JComboBox;
/*    */ import javax.swing.plaf.ComboBoxUI;
/*    */ import javax.swing.plaf.basic.BasicComboPopup;
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
/*    */ class SynthComboPopup
/*    */   extends BasicComboPopup
/*    */ {
/*    */   public SynthComboPopup(JComboBox<Object> paramJComboBox) {
/* 41 */     super(paramJComboBox);
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
/*    */   protected void configureList() {
/* 53 */     this.list.setFont(this.comboBox.getFont());
/* 54 */     this.list.setCellRenderer(this.comboBox.getRenderer());
/* 55 */     this.list.setFocusable(false);
/* 56 */     this.list.setSelectionMode(0);
/* 57 */     int i = this.comboBox.getSelectedIndex();
/* 58 */     if (i == -1) {
/* 59 */       this.list.clearSelection();
/*    */     } else {
/*    */       
/* 62 */       this.list.setSelectedIndex(i);
/* 63 */       this.list.ensureIndexIsVisible(i);
/*    */     } 
/* 65 */     installListListeners();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Rectangle computePopupBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 76 */     ComboBoxUI comboBoxUI = this.comboBox.getUI();
/* 77 */     if (comboBoxUI instanceof SynthComboBoxUI) {
/* 78 */       SynthComboBoxUI synthComboBoxUI = (SynthComboBoxUI)comboBoxUI;
/* 79 */       if (synthComboBoxUI.popupInsets != null) {
/* 80 */         Insets insets = synthComboBoxUI.popupInsets;
/* 81 */         return super.computePopupBounds(paramInt1 + insets.left, paramInt2 + insets.top, paramInt3 - insets.left - insets.right, paramInt4 - insets.top - insets.bottom);
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 88 */     return super.computePopupBounds(paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/plaf/synth/SynthComboPopup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */