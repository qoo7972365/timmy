/*    */ package javax.swing.event;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ import javax.swing.table.TableColumnModel;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TableColumnModelEvent
/*    */   extends EventObject
/*    */ {
/*    */   protected int fromIndex;
/*    */   protected int toIndex;
/*    */   
/*    */   public TableColumnModelEvent(TableColumnModel paramTableColumnModel, int paramInt1, int paramInt2) {
/* 76 */     super(paramTableColumnModel);
/* 77 */     this.fromIndex = paramInt1;
/* 78 */     this.toIndex = paramInt2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFromIndex() {
/* 86 */     return this.fromIndex;
/*    */   }
/*    */   public int getToIndex() {
/* 89 */     return this.toIndex;
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/swing/event/TableColumnModelEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */