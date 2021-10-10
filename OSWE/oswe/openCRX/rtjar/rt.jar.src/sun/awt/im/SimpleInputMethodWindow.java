/*    */ package sun.awt.im;
/*    */ 
/*    */ import java.awt.Frame;
/*    */ import java.awt.im.InputContext;
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
/*    */ public class SimpleInputMethodWindow
/*    */   extends Frame
/*    */   implements InputMethodWindow
/*    */ {
/* 40 */   InputContext inputContext = null;
/*    */   
/*    */   private static final long serialVersionUID = 5093376647036461555L;
/*    */ 
/*    */   
/*    */   public SimpleInputMethodWindow(String paramString, InputContext paramInputContext) {
/* 46 */     super(paramString);
/* 47 */     if (paramInputContext != null) {
/* 48 */       this.inputContext = paramInputContext;
/*    */     }
/* 50 */     setFocusableWindowState(false);
/*    */   }
/*    */   
/*    */   public void setInputContext(InputContext paramInputContext) {
/* 54 */     this.inputContext = paramInputContext;
/*    */   }
/*    */   
/*    */   public InputContext getInputContext() {
/* 58 */     if (this.inputContext != null) {
/* 59 */       return this.inputContext;
/*    */     }
/* 61 */     return super.getInputContext();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/SimpleInputMethodWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */