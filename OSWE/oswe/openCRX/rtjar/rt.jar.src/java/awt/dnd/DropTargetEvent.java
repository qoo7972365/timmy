/*    */ package java.awt.dnd;
/*    */ 
/*    */ import java.util.EventObject;
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
/*    */ public class DropTargetEvent
/*    */   extends EventObject
/*    */ {
/*    */   private static final long serialVersionUID = 2821229066521922993L;
/*    */   protected DropTargetContext context;
/*    */   
/*    */   public DropTargetEvent(DropTargetContext paramDropTargetContext) {
/* 58 */     super(paramDropTargetContext.getDropTarget());
/*    */     
/* 60 */     this.context = paramDropTargetContext;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DropTargetContext getDropTargetContext() {
/* 71 */     return this.context;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/DropTargetEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */