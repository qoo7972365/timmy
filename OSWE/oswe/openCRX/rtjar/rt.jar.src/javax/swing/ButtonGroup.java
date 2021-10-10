/*     */ package javax.swing;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ButtonGroup
/*     */   implements Serializable
/*     */ {
/*  72 */   protected Vector<AbstractButton> buttons = new Vector<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   ButtonModel selection = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(AbstractButton paramAbstractButton) {
/*  89 */     if (paramAbstractButton == null) {
/*     */       return;
/*     */     }
/*  92 */     this.buttons.addElement(paramAbstractButton);
/*     */     
/*  94 */     if (paramAbstractButton.isSelected()) {
/*  95 */       if (this.selection == null) {
/*  96 */         this.selection = paramAbstractButton.getModel();
/*     */       } else {
/*  98 */         paramAbstractButton.setSelected(false);
/*     */       } 
/*     */     }
/*     */     
/* 102 */     paramAbstractButton.getModel().setGroup(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(AbstractButton paramAbstractButton) {
/* 110 */     if (paramAbstractButton == null) {
/*     */       return;
/*     */     }
/* 113 */     this.buttons.removeElement(paramAbstractButton);
/* 114 */     if (paramAbstractButton.getModel() == this.selection) {
/* 115 */       this.selection = null;
/*     */     }
/* 117 */     paramAbstractButton.getModel().setGroup(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSelection() {
/* 127 */     if (this.selection != null) {
/* 128 */       ButtonModel buttonModel = this.selection;
/* 129 */       this.selection = null;
/* 130 */       buttonModel.setSelected(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Enumeration<AbstractButton> getElements() {
/* 140 */     return this.buttons.elements();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ButtonModel getSelection() {
/* 148 */     return this.selection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(ButtonModel paramButtonModel, boolean paramBoolean) {
/* 159 */     if (paramBoolean && paramButtonModel != null && paramButtonModel != this.selection) {
/* 160 */       ButtonModel buttonModel = this.selection;
/* 161 */       this.selection = paramButtonModel;
/* 162 */       if (buttonModel != null) {
/* 163 */         buttonModel.setSelected(false);
/*     */       }
/* 165 */       paramButtonModel.setSelected(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelected(ButtonModel paramButtonModel) {
/* 175 */     return (paramButtonModel == this.selection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getButtonCount() {
/* 184 */     if (this.buttons == null) {
/* 185 */       return 0;
/*     */     }
/* 187 */     return this.buttons.size();
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ButtonGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */