/*    */ package java.awt.dnd;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidDnDOperationException
/*    */   extends IllegalStateException
/*    */ {
/*    */   private static final long serialVersionUID = -6062568741193956678L;
/* 41 */   private static String dft_msg = "The operation requested cannot be performed by the DnD system since it is not in the appropriate state";
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidDnDOperationException() {
/* 47 */     super(dft_msg);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InvalidDnDOperationException(String paramString) {
/* 55 */     super(paramString);
/*    */   }
/*    */ }


/* Location:              /Users/timmy/timmy/OSWE/oswe/openCRX/rt.jar!/java/awt/dnd/InvalidDnDOperationException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */