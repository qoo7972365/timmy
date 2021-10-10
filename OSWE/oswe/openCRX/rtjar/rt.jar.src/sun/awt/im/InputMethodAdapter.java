/*     */ package sun.awt.im;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.im.spi.InputMethod;
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
/*     */ public abstract class InputMethodAdapter
/*     */   implements InputMethod
/*     */ {
/*     */   private Component clientComponent;
/*     */   
/*     */   void setClientComponent(Component paramComponent) {
/*  53 */     this.clientComponent = paramComponent;
/*     */   }
/*     */   
/*     */   protected Component getClientComponent() {
/*  57 */     return this.clientComponent;
/*     */   }
/*     */   
/*     */   protected boolean haveActiveClient() {
/*  61 */     return (this.clientComponent != null && this.clientComponent.getInputMethodRequests() != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setAWTFocussedComponent(Component paramComponent) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean supportsBelowTheSpot() {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void stopListening() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyClientWindowChange(Rectangle paramRectangle) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reconvert() {
/* 100 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public abstract void disableInputMethod();
/*     */   
/*     */   public abstract String getNativeInputMethodInfo();
/*     */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/InputMethodAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */