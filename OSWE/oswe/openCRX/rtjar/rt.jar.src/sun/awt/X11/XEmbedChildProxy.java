/*    */ package sun.awt.X11;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.Toolkit;
/*    */ import sun.awt.AWTAccessor;
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
/*    */ public class XEmbedChildProxy
/*    */   extends Component
/*    */ {
/*    */   long handle;
/*    */   XEmbeddingContainer container;
/*    */   
/*    */   public XEmbedChildProxy(XEmbeddingContainer paramXEmbeddingContainer, long paramLong) {
/* 36 */     this.handle = paramLong;
/* 37 */     this.container = paramXEmbeddingContainer;
/*    */   }
/*    */   
/*    */   public void addNotify() {
/* 41 */     synchronized (getTreeLock()) {
/* 42 */       if (AWTAccessor.getComponentAccessor().getPeer(this) == null) {
/* 43 */         AWTAccessor.getComponentAccessor()
/* 44 */           .setPeer(this, ((XToolkit)Toolkit.getDefaultToolkit()).createEmbedProxy(this));
/*    */       }
/* 46 */       super.addNotify();
/*    */     } 
/*    */   }
/*    */   
/*    */   XEmbeddingContainer getEmbeddingContainer() {
/* 51 */     return this.container;
/*    */   }
/*    */   long getHandle() {
/* 54 */     return this.handle;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/sun/awt/X11/XEmbedChildProxy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */