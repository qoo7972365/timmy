/*    */ package javax.print.event;
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
/*    */ public class PrintEvent
/*    */   extends EventObject
/*    */ {
/*    */   private static final long serialVersionUID = 2286914924430763847L;
/*    */   
/*    */   public PrintEvent(Object paramObject) {
/* 44 */     super(paramObject);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 51 */     return "PrintEvent on " + getSource().toString();
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/javax/print/event/PrintEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */