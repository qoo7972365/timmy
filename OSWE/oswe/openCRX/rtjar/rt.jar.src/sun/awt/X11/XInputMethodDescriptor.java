/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.awt.im.spi.InputMethod;
/*    */ import sun.awt.X11InputMethodDescriptor;
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
/*    */ class XInputMethodDescriptor
/*    */   extends X11InputMethodDescriptor
/*    */ {
/*    */   public InputMethod createInputMethod() throws Exception {
/* 38 */     return (InputMethod)new XInputMethod();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XInputMethodDescriptor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */