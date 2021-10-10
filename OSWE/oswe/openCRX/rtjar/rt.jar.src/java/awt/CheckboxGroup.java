/*     */ package java.awt;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class CheckboxGroup
/*     */   implements Serializable
/*     */ {
/*  64 */   Checkbox selectedCheckbox = null;
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
/*     */   private static final long serialVersionUID = 3729780091441768983L;
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
/*     */   public Checkbox getSelectedCheckbox() {
/*  90 */     return getCurrent();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Checkbox getCurrent() {
/*  99 */     return this.selectedCheckbox;
/*     */   }
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
/*     */   public void setSelectedCheckbox(Checkbox paramCheckbox) {
/* 119 */     setCurrent(paramCheckbox);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public synchronized void setCurrent(Checkbox paramCheckbox) {
/* 128 */     if (paramCheckbox != null && paramCheckbox.group != this) {
/*     */       return;
/*     */     }
/* 131 */     Checkbox checkbox = this.selectedCheckbox;
/* 132 */     this.selectedCheckbox = paramCheckbox;
/* 133 */     if (checkbox != null && checkbox != paramCheckbox && checkbox.group == this) {
/* 134 */       checkbox.setState(false);
/*     */     }
/* 136 */     if (paramCheckbox != null && checkbox != paramCheckbox && !paramCheckbox.getState()) {
/* 137 */       paramCheckbox.setStateInternal(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 147 */     return getClass().getName() + "[selectedCheckbox=" + this.selectedCheckbox + "]";
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/CheckboxGroup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */