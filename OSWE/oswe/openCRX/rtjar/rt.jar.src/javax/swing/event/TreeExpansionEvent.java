/*    */ package javax.swing.event;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ import javax.swing.tree.TreePath;
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
/*    */ public class TreeExpansionEvent
/*    */   extends EventObject
/*    */ {
/*    */   protected TreePath path;
/*    */   
/*    */   public TreeExpansionEvent(Object paramObject, TreePath paramTreePath) {
/* 67 */     super(paramObject);
/* 68 */     this.path = paramTreePath;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public TreePath getPath() {
/* 74 */     return this.path;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/TreeExpansionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */