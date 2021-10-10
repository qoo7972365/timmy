/*     */ package javax.swing;
/*     */ 
/*     */ import sun.awt.AWTAccessor;
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
/*     */ enum ClientPropertyKey
/*     */ {
/*  65 */   JComponent_INPUT_VERIFIER(true),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   JComponent_TRANSFER_HANDLER(true),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   JComponent_ANCESTOR_NOTIFIER(true),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   PopupFactory_FORCE_HEAVYWEIGHT_POPUP(true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean reportValueNotSerializable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  92 */     AWTAccessor.setClientPropertyKeyAccessor(new AWTAccessor.ClientPropertyKeyAccessor()
/*     */         {
/*     */           public Object getJComponent_TRANSFER_HANDLER() {
/*  95 */             return ClientPropertyKey.JComponent_TRANSFER_HANDLER;
/*     */           }
/*     */         });
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
/*     */   ClientPropertyKey(boolean paramBoolean) {
/* 113 */     this.reportValueNotSerializable = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getReportValueNotSerializable() {
/* 122 */     return this.reportValueNotSerializable;
/*     */   }
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/ClientPropertyKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */