/*    */ package javax.imageio.metadata;
/*    */ 
/*    */ import javax.imageio.IIOException;
/*    */ import org.w3c.dom.Node;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class IIOInvalidTreeException
/*    */   extends IIOException
/*    */ {
/* 52 */   protected Node offendingNode = null;
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
/*    */   public IIOInvalidTreeException(String paramString, Node paramNode) {
/* 65 */     super(paramString);
/* 66 */     this.offendingNode = paramNode;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IIOInvalidTreeException(String paramString, Throwable paramThrowable, Node paramNode) {
/* 85 */     super(paramString, paramThrowable);
/* 86 */     this.offendingNode = paramNode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Node getOffendingNode() {
/* 95 */     return this.offendingNode;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/imageio/metadata/IIOInvalidTreeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */