/*    */ package sun.awt.im;
/*    */ 
/*    */ import java.awt.im.InputContext;
/*    */ import javax.swing.JFrame;
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
/*    */ public class InputMethodJFrame
/*    */   extends JFrame
/*    */   implements InputMethodWindow
/*    */ {
/* 41 */   InputContext inputContext = null;
/*    */   
/*    */   private static final long serialVersionUID = -4705856747771842549L;
/*    */ 
/*    */   
/*    */   public InputMethodJFrame(String paramString, InputContext paramInputContext) {
/* 47 */     super(paramString);
/*    */     
/* 49 */     if (JFrame.isDefaultLookAndFeelDecorated()) {
/*    */       
/* 51 */       setUndecorated(true);
/* 52 */       getRootPane().setWindowDecorationStyle(0);
/*    */     } 
/* 54 */     if (paramInputContext != null) {
/* 55 */       this.inputContext = paramInputContext;
/*    */     }
/* 57 */     setFocusableWindowState(false);
/*    */   }
/*    */   
/*    */   public void setInputContext(InputContext paramInputContext) {
/* 61 */     this.inputContext = paramInputContext;
/*    */   }
/*    */   
/*    */   public InputContext getInputContext() {
/* 65 */     if (this.inputContext != null) {
/* 66 */       return this.inputContext;
/*    */     }
/* 68 */     return super.getInputContext();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/im/InputMethodJFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */